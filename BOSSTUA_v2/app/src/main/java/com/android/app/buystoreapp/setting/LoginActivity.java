package com.android.app.buystoreapp.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.CheckUtils;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.BossBuyActivity;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.GsonLoginBack;
import com.android.app.buystoreapp.bean.UserInfoBean;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseAct implements
        OnClickListener {

    private static final int ALL_LOGIN_SUCCESS = 1;
    private static final int ALL_LOGIN_FAIL = 2;
    private EditText username;
    private EditText pwd;
    private Button btnOk;
    private Button btnFindPwd;
    private Button btnRegister;
    private UserInfoBean mUserInfo;

    private TextView tv_phone_num_hint;
    private TextView tv_registerpassword_wrong;
//    private ProgressDialog progressDialog;

    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    private static final int HANDLE_LOGIN_REGISTER_SUCCESS = 0;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLE_LOGIN_REGISTER_SUCCESS:
                    userId = SharedPreferenceUtils.getCurrentUserInfo(context).getUserId();
                    loginChat(userId, pasw);
                    break;
                case ALL_LOGIN_SUCCESS:
                    stopLoadingAnim();
                    if (CrashApplication.getActivityByName("com.android.app.buystoreapp.BossBuyActivity") == null) {
                        startActivity(new Intent(LoginActivity.this, BossBuyActivity.class));
                    }
                    LoginActivity.this.finish();
                    break;
                case ALL_LOGIN_FAIL:
                    stopLoadingAnim();
                    if (CrashApplication.getActivityByName("com.android.app.buystoreapp.BossBuyActivity") == null) {
                        startActivity(new Intent(LoginActivity.this, BossBuyActivity.class));
                    }
                    LoginActivity.this.finish();
                    break;
                default:
                    break;
            }
        }

    };

    private OnFocusChangeListener userFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View arg0, boolean hasFocus) {
            if (!hasFocus) {
                if (!CheckUtils.mobileCheck(pasw)) {
                    Toast.makeText(
                            getBaseContext(),
                            getResources().getString(
                                    R.string.personal_email_mobile_check),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    private String name;
    private String pasw;
    private LoginActivity context;
    private String userId;
    private String resultNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.login_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_action_bar);
        context = this;
        ViewUtils.inject(this);
        mTitleText.setText("登录");

        initViews();
        addIncludeLoading(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private void initViews() {
        tv_phone_num_hint = (TextView) findViewById(R.id.tv_phone_num_hint);
        tv_registerpassword_wrong = (TextView) findViewById(R.id.tv_registerpassword_wrong);
        username = (EditText) findViewById(R.id.id_login_username);
//        username.setOnFocusChangeListener(userFocusChangeListener);
        pwd = (EditText) findViewById(R.id.id_login_pwd);
        btnOk = (Button) findViewById(R.id.id_login_ok);
        btnOk.setOnClickListener(this);
        btnFindPwd = (Button) findViewById(R.id.id_login_findpwd);
        btnFindPwd.setOnClickListener(this);
        btnRegister = (Button) findViewById(R.id.id_register_immeriately);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        name = username.getText().toString().trim();
        pasw = pwd.getText().toString().trim();
        switch (v.getId()) {
            case R.id.id_login_ok:
                tv_phone_num_hint.setVisibility(View.INVISIBLE);
                tv_registerpassword_wrong.setVisibility(View.INVISIBLE);
                if (!TextUtils.isEmpty(username.getText().toString().trim())) {
                    if (CheckUtils.checkPassword(pasw)) {
                        sendLoginHttp();

//                    loginChat("lkh", "123");
                    } else {
                        tv_registerpassword_wrong.setText(getResources().getString(R.string.login_pwd_error));
                        tv_registerpassword_wrong.setVisibility(View.VISIBLE);
//                    Toast.makeText(this,
//                            getResources().getString(R.string.login_pwd_error),
//                            Toast.LENGTH_SHORT).show();
                    }
                } else {
                    tv_phone_num_hint.setText(getResources().getString(R.string.username_null));
                    tv_phone_num_hint.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.id_login_findpwd:
                Intent intent = new Intent(this, FindPwdConfirmActivity.class);
                startActivity(intent);
                break;
            case R.id.id_register_immeriately:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            default:
                break;
        }
    }

    //环信登录
    private void loginChat(String name, String pasw) {
        EMClient.getInstance().login(name, pasw, new EMCallBack() {//回调
            @Override
            public void onSuccess() {

                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.e("main", "登录聊天服务器成功！");
                mHandler.obtainMessage(ALL_LOGIN_SUCCESS)
                        .sendToTarget();

            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.e("---环信返回码--", code + "");
                Log.e("main", "登录聊天服务器失败！");
                mHandler.sendEmptyMessage(ALL_LOGIN_FAIL);
            }
        });
    }

    private void sendLoginHttp() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "login");
            obj.put("userName", name);
            obj.put("passWord", pasw);
            obj.put("deviceToken", CrashApplication.device_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("--登录提交数据---", obj.toString());
        HttpUtils.post(context, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                startWhiteLoadingAnim();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Log.d("login--", new String(arg2));
                Gson gson = new Gson();
                GsonLoginBack gsonLoginBack = gson.fromJson(new String(arg2),
                        new TypeToken<GsonLoginBack>() {
                        }.getType());

                String result = gsonLoginBack.getResult();
                resultNote = gsonLoginBack.getResultNote();
                if ("1".equals(result)) {// login failure
                    if ("密码错误".equals(resultNote)) {
                        tv_registerpassword_wrong.setText(resultNote);
                        tv_registerpassword_wrong.setVisibility(View.VISIBLE);
                    } else if ("用户名不存在".equals(resultNote)) {
                        tv_phone_num_hint.setText(resultNote);
                        tv_phone_num_hint.setVisibility(View.VISIBLE);
                    }
                } else {
                    mUserInfo = gsonLoginBack.getUserinfoBean();
                    mUserInfo.setLogin(true);
                    SharedPreferenceUtils.saveCurrentUserInfo(mUserInfo,
                            LoginActivity.this, true);
                    mHandler.obtainMessage(HANDLE_LOGIN_REGISTER_SUCCESS)
                            .sendToTarget();
                }
                stopLoadingAnim();
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
//                progressDialog.dismiss();
                stopLoadingAnim();
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.login_failure),
                        Toast.LENGTH_SHORT).show();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
