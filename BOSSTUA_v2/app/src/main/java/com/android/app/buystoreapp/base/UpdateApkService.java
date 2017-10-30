package com.android.app.buystoreapp.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.VersionUpdateBack;
import com.android.app.buystoreapp.bean.VersionUpdateBean;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.buystoreapp.other.CustomDialog;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.Config;
import com.android.app.utils.HttpUtils;
import com.android.app.utils.NetWorkUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by shangshuaibo on 2016/12/27 18:49
 */
public class UpdateApkService extends Service {
    private VersionUpdateBean versionUpdateBean;
    private Activity mContext = CrashApplication.getActivityByName("BossBuyActivity");
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;
    private NotificationManager manager;
    private Notification notif;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        versionUpdate();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopSelf();
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    notif.contentView.setTextViewText(R.id.content_view_text1, "正在下载...  " +
                            progress + "%");
                    notif.contentView.setProgressBar(R.id.content_view_progress, 100, progress,
                            false);
                    manager.notify(0, notif);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    notif.contentView.setTextViewText(R.id.content_view_text1, "下载完成！");
                    notif.contentView.setProgressBar(R.id.content_view_progress, 100, 100, false);
                    manager.notify(0, notif);
//                    manager.cancel(0);
                    installApk();
                    break;
            }
        }
    };

    /**
     * 检测版本更新
     */
    private void versionUpdate() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "appVersion");
            obj.put("type", 2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(getApplicationContext(), obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Gson gson = new Gson();
                VersionUpdateBack versionUpdateBack = gson.fromJson(new String(bytes), new
                        TypeToken<VersionUpdateBack>() {
                        }.getType());
                if ("0".equals(versionUpdateBack.getResult())) {
                    if (versionUpdateBack.getAppVersion().getVersionCode() > Config
                            .getVersionCode(getApplicationContext())) {
                        versionUpdateBean = versionUpdateBack.getAppVersion();
                        showNoticeDialog();
                    } else {
                        // 没有新版本,停止服务
                        stopSelf();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {

            }
        });
    }


    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("检测到新版本");
        builder.setMessage(versionUpdateBean.getUpmsg());
        // 更新
        builder.setPositiveButton("现在更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (NetWorkUtil.getConnectedType(mContext) == 1) {
                    ToastUtil.showMessageDefault(mContext, "已切换至后台更新");
                    showDownloadDialog();
                }else if (NetWorkUtil.getConnectedType(mContext) == 0){
                    is4G();
                }else {
                    ToastUtil.showMessageDefault(mContext, "没有网络哦,请稍后更新");
                }
            }
        });
        // 稍后更新
        builder.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void is4G(){
        CustomDialog.initDialog(mContext);
        CustomDialog.tvTitle.setText("您当前网络连接为移动连接,是否继续下载");
        CustomDialog.btnLeft.setText("取消");
        CustomDialog.btnRight.setText("确定");
        CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.dialog.dismiss();
            }
        });
        CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.dialog.dismiss();
                ToastUtil.showMessageDefault(mContext, "已切换至后台更新");
                showDownloadDialog();
            }
        });
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notif = new Notification();
        notif.icon = R.drawable.ic_launcher;
        notif.tickerText = "更新通知";
        //通知栏显示所用到的布局文件
        notif.contentView = new RemoteViews(mContext.getPackageName(), R.layout.notify_layout);
        notif.flags |= Notification.FLAG_INSISTENT;
        manager.notify(0, notif);
        // 下载文件
        downloadApk();
    }


    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }
    int downloadCount = 0;
    /**
     * 下载文件线程
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/boss/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(versionUpdateBean.getDownurl());
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();
                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File apkFile = new File(mSavePath, "boss.apk");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        //为了防止频繁的通知导致应用吃紧，百分比增加5才通知一次
                        if (downloadCount == 0 || progress - 5 > downloadCount) {
                            downloadCount += 5;
                            mHandler.sendEmptyMessage(DOWNLOAD);
                        }
                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 安装APK文件
     */
    private void installApk() {

        File apkfile = new File(mSavePath, "boss.apk");
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android" +
                ".package-archive");
        mContext.startActivity(intent);

    }
}
