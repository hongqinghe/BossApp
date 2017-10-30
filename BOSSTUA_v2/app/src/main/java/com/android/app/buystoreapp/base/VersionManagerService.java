/**
 * @date: 2016-11-19
 * @Description: 应用下载更新服务(升级更新)
 */

package com.android.app.buystoreapp.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.VersionUpdateBack;
import com.android.app.buystoreapp.bean.VersionUpdateBean;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.utils.Config;
import com.android.app.utils.HttpUtils;
import com.android.app.utils.NetWorkUtil;
import com.android.app.utils.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class VersionManagerService extends Service {

    private VersionUpdateBean versionUpdateBean;
    private File cacheFile;
    private boolean isDownloaded = false;
    private int value;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isDownloaded = false;
        versionUpdate();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopSelf();
        super.onDestroy();

    }

    /**
     * 下载更新
     */

    private void onStaticUpdate() {
        this.cacheFile = getFileCacheOutputStream();
        // 显示对话框
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Activity context = CrashApplication.getActivityByName("BossBuyActivity");
                if (context != null) {
                    new MyStaticInstallDialog(context, versionUpdateBean,
                            cacheFile, R.style.CustomDialog).show();
                }
            }
        }, 1000);

    }

    /**
     * 获取文件缓存路径,默认存储到应用私有文件路径
     *
     * @return 文件存储路径
     * @throws Exception
     * @author:wuzhimin
     */

    public File getFileCacheOutputStream() {
        File cachePath = null;
        // SD卡目录,目前只能存储在扩展卡，才可以启动APK的安装
        cachePath = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + getPackageName() + ".apk");

        return cachePath;
    }

    /**
     * 已经下载完成的，直接安装
     *
     * @param context
     * @param filePath
     * @return whether apk exist
     */

    public static boolean install(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
            i.setDataAndType(Uri.parse("file://" + filePath),
                    "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }
        return false;
    }

    /**
     * 安装apk
     *
     * @param file
     * @param context
     * @author luoaina 要安装的apk的目录
     */

    public void installApk(File file, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 在UI线程，声明一个handler，后续控制对话框在UI线程显示
     */

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

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
                        onStaticUpdate();
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

    class MyStaticInstallDialog extends Dialog implements OnClickListener {

        private Activity context;

        private VersionUpdateBean bean;
        private File cacheFile;
        private int theme;

        public MyStaticInstallDialog(Activity context) {
            super(context);
            this.context = context;
            this.bean = null;
        }

        public MyStaticInstallDialog(Activity context, VersionUpdateBean bean,
                                     File cacheFile, int theme) {
            super(context, theme);
            this.context = context;
            this.bean = bean;
            this.cacheFile = cacheFile;
            this.theme = theme;
        }

        private LinearLayout llDialogCust;
        private TextView tvTitleDialog;
        private LinearLayout llMsgDiaCust;
        private TextView tvMsgDiaCust;
        private Button btLeftDialog;
        private Button btRightDialog;
        private LinearLayout ll_right_dialog;
        private View vDivider;
        private boolean flag = false;

        private TextView tv_progress;
        private ProgressBar download_item_progressBar;
        private FrameLayout lly_pro;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_customer);

            lly_pro = (FrameLayout) findViewById(R.id.lly_pro);
            tv_progress = (TextView) findViewById(R.id.tv_progress);
            download_item_progressBar = (ProgressBar) findViewById(R.id.download_item_progressBar);

            llDialogCust = (LinearLayout) findViewById(R.id.ll_dialog_cust);
            tvTitleDialog = (TextView) findViewById(R.id.tv_title_dialog);
            llMsgDiaCust = (LinearLayout) findViewById(R.id.ll_msg_dia_cust);
            tvMsgDiaCust = (TextView) findViewById(R.id.tv_msg_dia_cust);
            btLeftDialog = (Button) findViewById(R.id.bt_left_dialog);
            btRightDialog = (Button) findViewById(R.id.bt_right_dialog);
            vDivider = findViewById(R.id.v_divider);
            ll_right_dialog = (LinearLayout) findViewById(R.id.ll_right_dialog);
            btLeftDialog.setOnClickListener(this);
            ll_right_dialog.setOnClickListener(this);
            btLeftDialog.setText(context.getString(R.string.exit_cancel));
            if (flag = (null != bean)) {
                this.setCancelable(false);
                this.setCanceledOnTouchOutside(false);
                if (bean.getStrDate().equals("2")) {
                    btLeftDialog.setVisibility(View.GONE);
                    vDivider.setVisibility(View.GONE);
                } else {
                    btLeftDialog.setVisibility(View.VISIBLE);
                    vDivider.setVisibility(View.VISIBLE);
                }
                int dialogWidth = (int) ((double) Util
                        .getWindowWidth(context) * 4 / 5);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        dialogWidth, FrameLayout.LayoutParams.WRAP_CONTENT);
                llDialogCust.setLayoutParams(params);
                tvTitleDialog.setText("（" + bean.getVersionName() + "）");
                if (bean.isDownloadStatus()) {
                    btRightDialog.setText(context.getString(R.string.bt_install));
                    tv_progress.setVisibility(View.GONE);
                } else {
                    btRightDialog
                            .setText(context.getString(R.string.bt_update));
                }
                String[] msgs = bean.getUpmsg().split(";");
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < msgs.length; i++) {
                    sb.append(msgs[i]);
                    if (i < (msgs.length - 1)) {
                        sb.append("\n");
                    }
                }
                tvMsgDiaCust.setText(sb.toString());
            } else {
                int dialogWidth = (int) ((double) Util
                        .getWindowWidth(context) * 9 / 10);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        dialogWidth, LayoutParams.WRAP_CONTENT);
                llDialogCust.setLayoutParams(params);
                btRightDialog.setText(context.getString(R.string.exit));
                tvMsgDiaCust.setText(context
                        .getString(R.string.text_confirm_exit));
                llMsgDiaCust.setGravity(Gravity.CENTER);
            }
            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            llMsgDiaCust.setLayoutParams(params);
        }

        /**
         * 使用异步任务的规则：
         * 1、声明一个类继承AsyncTask，标注三个参数类型
         * 2、第一个参数表示要执行的任务通常是网络上的路径
         * 第二个参数表示进度的刻度 第三个参数表示任务执行的返回结果
         */


        String cacheFilePath = getFileCacheOutputStream().toString();

        public class MyTask extends AsyncTask<String, Integer, String> {

            @Override
            protected void onPreExecute() {
                lly_pro.setVisibility(View.VISIBLE);
                ll_right_dialog.setClickable(false);
                super.onPreExecute();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                download_item_progressBar.setProgress(values[0]);
                if (value == 0) {
                    tv_progress.setVisibility(View.GONE);
                } else {
                    tv_progress.setVisibility(View.VISIBLE);
                    tv_progress.setText("（" + values[0] + "%" + "）");
                }

                super.onProgressUpdate(values);
            }

            @Override
            protected String doInBackground(String... params) {
                // 使用网络连接类HttpClient完成网络资源下载
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(params[0]);
                InputStream inputStream = null;
                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        inputStream = httpEntity.getContent();// 获取读取流

                        File file = new File(cacheFilePath);// 定义文件
                        FileOutputStream fos = new FileOutputStream(file);// 生成输出流

                        long file_len = httpEntity.getContentLength();// 获取文件总长度
                        int current_len = 0;// 当前读取的长度
                        int len = 0;
                        byte[] data = new byte[1024];
                        while ((len = inputStream.read(data)) != -1) {
                            fos.write(data, 0, len);
                            current_len += len;
                            value = (int) ((current_len / (float) file_len) * 100); //
                            // file_len要转成float格式，使进度条显示得更明显
                            publishProgress(value);// 发送更新数据到onProgressUpdate方法
                        }

                        fos.flush();
                        fos.close();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            // TODO 自动生成的 catch 块
                            e.printStackTrace();
                        }
                    }
                    // 关闭连接，释放资源
                    httpClient.getConnectionManager().shutdown();

                }
                return cacheFilePath;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                ll_right_dialog.setClickable(true);
                if (NetWorkUtil.isNetworkConnected(context) && value == 100) {
                    // Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
                    isDownloaded = true;
                    if (btRightDialog != null) {
                        btRightDialog.setText(context.getString(R.string.bt_install));
                        tv_progress.setVisibility(View.GONE);
                    }
                    File file = new File(result);
                    installApk(file, context);
                } else {
                    /**
                     * 断网时连接网络后，再重新下载。
                     */
                    value = 0;
                    publishProgress(value);
                    Toast.makeText(context, "网络不给力", Toast.LENGTH_LONG).show();
                }

            }

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_left_dialog:
                    this.dismiss();
                    // 取消更新,停止服务
                    stopSelf();
                    break;
                case R.id.ll_right_dialog:
                    if (isDownloaded) {
                        installApk(cacheFile, context);
                        // this.dismiss();
                        return;
                    }
                    if (flag) {
                        if (bean.isDownloadStatus()) {
                            /**
                             * 已经下载完成，直接安装
                             */

                            btRightDialog.setText(context.getString(R.string.bt_install));
                            install(getApplicationContext(), cacheFile.getAbsolutePath());
                            tv_progress.setVisibility(View.GONE);
                        } else {
                            String aUrl = bean.getDownurl().trim();
                            new MyTask().execute(aUrl, cacheFilePath);
                        }
                    } else {
//                        AppGlobal.getInstance().destroy();
                        context.finish();
                    }
                    break;
            }
        }
    }

}
