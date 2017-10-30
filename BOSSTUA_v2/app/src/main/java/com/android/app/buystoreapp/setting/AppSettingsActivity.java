package com.android.app.buystoreapp.setting;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.CameraUtils;
import com.android.app.buystore.utils.ImageUtils;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.BossBuyActivity;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.AddressBeanNew;
import com.android.app.buystoreapp.bean.EditUserInfoBean;
import com.android.app.buystoreapp.bean.GsonEditUserCmd;
import com.android.app.buystoreapp.bean.GsonLoginBack;
import com.android.app.buystoreapp.bean.UserInfoBean;
import com.android.app.buystoreapp.other.CustomAnimDialog;
import com.android.app.buystoreapp.other.CustomDialog;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.ResType;
import com.lidroid.xutils.view.annotation.ResInject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import java.io.ByteArrayOutputStream;

public class AppSettingsActivity extends Activity {
    @ResInject(id = R.string.web_url, type = ResType.String)
    private String webUrl;

    private static final int DIALOG_USERICON = 0xb1;
    private static final int DIALOG_NICKNAME = 0xb2;

    private static final int CODE_GALLERY_REQUEST = 0xa1;
    private static final int CODE_CAMERA_REQUEST = 0xa2;
    private static final int CODE_RESIZE_REQUEST = 0xa3;

    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    //    @ViewInject(R.id.id_app_setting_clean_cache)
//    private Button mCleanCacheButton;
    @ViewInject(R.id.id_updateuserinfo_userinfo_exit)
    private Button mEditButton;
    @ViewInject(R.id.id_updateuserinfo_userinfo_usericon)
    private ImageView muserIcon;
    @ViewInject(R.id.id_updateuserinfo_userinfo_username)
    private EditText muserName;
    @ViewInject(R.id.id_updateuserinfo_userinfo_nickname)
    private TextView mnickName;
    //    @ViewInject(R.id.id_updateuserinfo_userinfo_score)
//    private EditText muserScore;
    @ViewInject(R.id.id_updateuserinfo_safe)
    private View muserSafe;
    @ViewInject(R.id.id_updateuserinfo_address)
    private View muserAddress;

    private Bitmap userPhoto;
    private ProgressDialog progressDialog;
    private static final int ICON_WIDTH_AND_HEIGHT = 200;

    // private List<AddressBeanNew.AdressListBean> addressList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.app_setting_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_action_bar);
        ViewUtils.inject(this);
        mTitleText.setText("基本设置");
        mEditButton.setVisibility(View.GONE);

        initUserInfo();
//        try {
//            long size = DataCleanManager.getCacheSize(this.getCacheDir());
//            mCleanCacheButton.setText(String.format(getString(R.string.app_setting_clean_cache)
// , Formatter.formatFileSize(this, size)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void initUserInfo() {
        UserInfoBean userInfo = SharedPreferenceUtils.getCurrentUserInfo(this);
        String userIconUrl = userInfo.getUserIcon();
        Picasso.with(this).load(userIconUrl).into(muserIcon);
        muserName.setText(userInfo.getUserName());
        mnickName.setText(userInfo.getNickName());
//        muserScore.setText(userInfo.getScore());
    }

    @OnClick(R.id.id_custom_back_image)
    public void onCustomBarBackClicked(View v) {
        switch (v.getId()) {
            case R.id.id_custom_back_image:
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userPhoto != null) {
            userPhoto.recycle();
            userPhoto = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        switch (requestCode) {
            case CODE_CAMERA_REQUEST:
                userPhoto = (Bitmap) data.getExtras().get("data");
                muserIcon.setImageBitmap(userPhoto);
                sendEditUserinfo();
                break;
            case CODE_GALLERY_REQUEST:
                String path = CameraUtils.getPhotoPathByLocalUri(this, data);
                Log.d("mikes", "path=" + path);
                if (path != null
                        && (path.endsWith(".jpg") || path.endsWith(".png")
                        || path.endsWith(".PNG") || path.endsWith(".JPG"))) {
                    BitmapFactory.Options option = new BitmapFactory.Options();
                    option.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(path, option);
                    option.inSampleSize = ImageUtils.calculateInSampleSize(option,
                            ICON_WIDTH_AND_HEIGHT, ICON_WIDTH_AND_HEIGHT);
                    option.inJustDecodeBounds = false;
                    userPhoto = BitmapFactory.decodeFile(path, option);
                    muserIcon.setImageBitmap(userPhoto);
                    sendEditUserinfo();
                }
                break;
            case CODE_RESIZE_REQUEST:

                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CODE_RESIZE_REQUEST);
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (id) {
            case DIALOG_USERICON:
                builder
                        .setTitle(R.string.modify_icon_dialog_title)
                        .setItems(R.array.modify_icon_dialog_choices,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0,
                                                        int which) {
                                        if (which == 0) {
                                            CameraUtils.openCamera(
                                                    AppSettingsActivity.this,
                                                    CODE_CAMERA_REQUEST);
                                        } else if (which == 1) {
                                            CameraUtils.openPhotos(
                                                    AppSettingsActivity.this,
                                                    CODE_GALLERY_REQUEST);
                                        }
                                    }
                                });
                break;
            case DIALOG_NICKNAME:
                builder.setTitle("修改昵称");
                final View contentView = LayoutInflater.from(this).inflate(R.layout
                        .update_userinfo_nickname, null);
                EditText nickEdit = (EditText) contentView.findViewById(R.id
                        .id_update_userinfo_nickname_dialog);
                if (nickEdit != null) {
                    nickEdit.setHint(mnickName.getText());
                }
                builder.setView(contentView);
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        EditText edit = (EditText) contentView.findViewById(R.id
                                .id_update_userinfo_nickname_dialog);
                        mnickName.setText(edit.getText());
                        sendEditUserinfo();
                    }
                })
                        .setNegativeButton("取消", null);
                break;
            default:
                break;
        }
        return builder.create();
    }

    //    R.id.id_app_setting_clean_cache,
    @OnClick({R.id.id_updateuserinfo_userinfo_exit_l, R.id.id_updateuserinfo_safe,
            R.id.id_updateuserinfo_address,
            R.id.id_updateuserinfo_userinfo_usericon,
            R.id.id_updateuserinfo_userinfo_nickname,})
    public void onViewClicked(View v) {
        switch (v.getId()) {
//        case R.id.id_app_setting_clean_cache:
//            DataCleanManager.deleteFolderFile(this.getCacheDir().getAbsolutePath(), true);
//            try {
//                long size = DataCleanManager.getCacheSize(this.getCacheDir());
//                mCleanCacheButton.setText(String.format(getString(R.string
// .app_setting_clean_cache), Formatter.formatFileSize(this, size)));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            break;
            case R.id.id_updateuserinfo_userinfo_exit_l:
                CustomDialog.initDialog(AppSettingsActivity.this);
                CustomDialog.tvTitle.setText("是否确定注销当前用户?");
                CustomDialog.btnLeft.setText("确定");
                CustomDialog.btnRight.setText("取消");
                CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog.dialog.dismiss();
                        exit();
                    }
                });
                CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog.dialog.dismiss();
                    }
                });

                break;
            case R.id.id_updateuserinfo_safe:
//                Intent modifyPwdIntent = new Intent(this,
//                        FindPwdConfirmActivity.class);
//                startActivity(modifyPwdIntent);
                break;
            case R.id.id_updateuserinfo_address:
                // TODO: 2016/9/20
                /**
                 * 在次需要做判断，发送网络请求看地址集合是否为空  为空跳转到添加地址页面（AddAddressActivity）
                 * 如果集合不为空则跳转到地址列表（MyAddressListActivity）
                 * */

                CustomAnimDialog.initDialog(this);

                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                final Gson gson = new Gson();
                String userNane = SharedPreferenceUtils.getCurrentUserInfo(this).getUserName();
                String request = "{\"cmd\":\"getReceive\",\"thisUser\":" + userNane + "}";
                params.put("json", request);
                client.get(webUrl, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        CustomAnimDialog.dialog.dismiss();
                        try {
                            AddressBeanNew addressBeanNew = gson.fromJson(new String(bytes), new
                                    TypeToken<AddressBeanNew>() {
                                    }.getType());
                            String result = addressBeanNew.getResult();
                            if (result.equals("1")) {
                                ToastUtil.showMessageDefault(AppSettingsActivity.this,
                                        "您还没有添加地址哦，请添加地址");
                                Intent addIntent = new Intent(AppSettingsActivity.this,
                                        AddAddressActivity.class);
                                //如过没有地址，给添加地址界面传一个参数，通知他点击添加按钮以后，跳转到地址列表
                                addIntent.putExtra("isMe", 1);
                                startActivity(addIntent);
                            } else if (result.equals("0")) {
                                //addressList.clear();
                                //addressList.addAll(addressBeanNew.getAdressList());
                                Intent addressIntent = new Intent(AppSettingsActivity.this,
                                        MyAddressListActivity.class);
                                //addressIntent.putExtra("addressList", (ArrayList<AddressBeanNew
                                //        .AdressListBean>) addressList);
                                startActivity(addressIntent);
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable
                            throwable) {
                        CustomAnimDialog.dialog.dismiss();
                        ToastUtil.showMessageDefault(AppSettingsActivity.this,"请检查您的网络连接！");
                    }
                });
                break;
            case R.id.id_updateuserinfo_userinfo_usericon:
                showDialog(DIALOG_USERICON);
                break;
            case R.id.id_updateuserinfo_userinfo_nickname:
                showDialog(DIALOG_NICKNAME);
                break;
            default:
                break;
        }
    }

    private String bitmaptoString(Bitmap bitmap) {
        if (bitmap == null)
            return "";
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 30, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    private void sendEditUserinfo() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        final Gson gson = new Gson();
        final String iconString = bitmaptoString(userPhoto);
        EditUserInfoBean userInfo = new EditUserInfoBean(muserName.getText()
                .toString(), iconString, mnickName.getText().toString());
        GsonEditUserCmd gsonEditUserCmd = new GsonEditUserCmd("editUserInfo",
                userInfo);
        String param = gson.toJson(gsonEditUserCmd);
        requestParams.put("json", param);

        client.post(webUrl, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(
                        AppSettingsActivity.this,
                        getResources()
                                .getString(R.string.modify_progress_title),
                        getResources().getString(
                                R.string.modify_progress_message), true, false);
            }

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                LogUtils.d("editUserinfo result=" + new String(arg2));
                try {
                    GsonLoginBack gsonLoginBack = gson.fromJson(
                            new String(arg2), new TypeToken<GsonLoginBack>() {
                            }.getType());
                    String result = gsonLoginBack.getResult();
                    String resultNote = gsonLoginBack.getResultNote();
                    if ("1".equals(result)) {// fail
                        Toast.makeText(getApplicationContext(), resultNote,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), resultNote,
                                Toast.LENGTH_SHORT).show();
                        updateUserinfoAfterModify(gsonLoginBack.getUserinfoBean());
                    }
                } catch (NullPointerException e) {
                    Log.e("mikes", "update user info error:", e);
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.modify_failure),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserinfoAfterModify(UserInfoBean userInfo) {
        Picasso.with(this).load(userInfo.getUserIcon()).into(muserIcon);
        userInfo.setLogin(true);
//        userInfo.setScore(muserScore.getText().toString());
        LogUtils.d("update user info end =" + userInfo.toString());
        SharedPreferenceUtils.saveCurrentUserInfo(userInfo, this, true);
        this.finish();
    }

    /**
     * 注销用户
     */
    private void exit() {
        Log.d("删除会话列表id---",(SharedPreferenceUtils.getCurrentUserInfo(this).getUserId()).toLowerCase());
        EMClient.getInstance().chatManager().deleteConversation((SharedPreferenceUtils.getCurrentUserInfo(this).getUserId()).toLowerCase(),true);
        UserInfoBean userInfo = null;
        SharedPreferenceUtils.saveCurrentUserInfo(userInfo, this, false);
        //环信退出
        EMClient.getInstance().logout(true);//此方法为同步方法
        startActivity(new Intent(AppSettingsActivity.this, BossBuyActivity.class));
        AppSettingsActivity.this.finish();
    }
}
