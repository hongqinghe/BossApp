package com.android.app.buystoreapp.setting;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
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
import com.android.app.buystoreapp.managementservice.ExplainWebViewActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
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

public class RegisterActivity extends BaseAct implements OnClickListener {
    private EditText username;
    private EditText pwd;
    private EditText pwdAgain;
    private Button btnSubmit;
    private EditText et_register_inviteCode;

    private TextView tv_registerpassword_wrong;
    private TextView tv_phone_num_hint;
    private TextView tv_registercode_wrong;
    private TextView tv_register_agreement;
    private EditText et_register_code;

    private Button sendAuthCode;

    private ProgressDialog progressDialog;
    private UserInfoBean mUserInfo;

    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    private static final int HANDLE_LOGIN_REGISTER_SUCCESS = 0;
    private String userId;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLE_LOGIN_REGISTER_SUCCESS:
                    userId = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
                    loginChat(userId, pas);
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
                if (!CheckUtils.mobileCheck(username.getText()
                        .toString())) {
                    Toast.makeText(
                            getBaseContext(),
                            getResources().getString(
                                    R.string.personal_email_mobile_check),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    private String name = "";
    private String pas = "";
    private String code = "1234567";
    private String resultNote = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.register_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_action_bar);
        mContext = this;
        ViewUtils.inject(this);
        mTitleText.setText("注册");
        initErrorPage();
        addIncludeLoading(true);


        sendAuthCode = (Button) findViewById(R.id.id_register_send_authcode);
        sendAuthCode.setOnClickListener(this);

        et_register_code = (EditText) findViewById(R.id.et_register_code);
        tv_phone_num_hint = (TextView) findViewById(R.id.tv_phone_num_hint);
        username = (EditText) findViewById(R.id.id_register_username);
        pwd = (EditText) findViewById(R.id.id_register_pwd);
        pwdAgain = (EditText) findViewById(R.id.id_register_pwd_again);
        btnSubmit = (Button) findViewById(R.id.id_register_submit);
        et_register_inviteCode = (EditText) findViewById(R.id.et_register_inviteCode);
        // username.setOnFocusChangeListener(userFocusChangeListener);
        btnSubmit.setOnClickListener(this);

        tv_registerpassword_wrong = (TextView) findViewById(R.id.tv_registerpassword_wrong);
        tv_registercode_wrong = (TextView) findViewById(R.id.tv_registercode_wrong);
        tv_register_agreement = (TextView) findViewById(R.id.tv_register_agreement);
        SpannableString msp = new SpannableString(tv_register_agreement.getText().toString().trim());
        msp.setSpan(new UnderlineSpan(), 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_register_agreement.setText(msp);
        tv_register_agreement.setMovementMethod(LinkMovementMethod.getInstance());
        tv_register_agreement.setOnClickListener(this);
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

    private void sendRegisterHttp() {

        JSONObject obj = new JSONObject();
        try {
            obj.put("userName", name);
            obj.put("passWord", pas);
            obj.put("cmd", "registerTest");
            obj.put("inviteCode", et_register_inviteCode.getText().toString().trim());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("-----register---", "" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(
                        RegisterActivity.this,
                        getResources().getString(
                                R.string.register_progress_title),
                        getResources().getString(
                                R.string.modify_progress_message), true, false);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Log.d("mikes", "register.... sunccess, json:"
                        + new String(arg2));
                Gson gson = new Gson();
                GsonLoginBack gsonLoginBack = gson.fromJson(new String(arg2),
                        new TypeToken<GsonLoginBack>() {
                        }.getType());
                String result = gsonLoginBack.getResult();
                String resultNote = gsonLoginBack.getResultNote();
                if ("1".equals(result)) {// login failure
                    Toast.makeText(getApplicationContext(), resultNote,
                            Toast.LENGTH_SHORT).show();

                    return;
                } else {
                    Toast.makeText(getApplicationContext(), resultNote,
                            Toast.LENGTH_SHORT).show();
                    mUserInfo = gsonLoginBack.getUserinfoBean();
                    mUserInfo.setLogin(true);
                    SharedPreferenceUtils.saveCurrentUserInfo(mUserInfo,
                            RegisterActivity.this, true);
                    mHandler.obtainMessage(HANDLE_LOGIN_REGISTER_SUCCESS)
                            .sendToTarget();
                }

            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.register_failure),
                        Toast.LENGTH_SHORT).show();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {

            }
        });
    }


    private static final int UPDATE_AUTHCODE_TIME_DELAYED = 1000;
    private int AUTHCODE_TIME = 120;// 120s

    private Handler findPwdHandler = new Handler() {
        public void handleMessage(Message msg) {
            int time = msg.arg1;
            if (time <= 0) {
                AUTHCODE_TIME = 10;
                findPwdHandler.removeMessages(0);
                sendAuthCode.setEnabled(true);
                sendAuthCode.setText(getString(R.string.login_send_authcode));
            } else {
                sendAuthCode.setEnabled(false);
                sendAuthCode.setText(String.format("%1$d s", time));
                findPwdHandler.sendMessageDelayed(
                        findPwdHandler.obtainMessage(0, AUTHCODE_TIME--, 0),
                        UPDATE_AUTHCODE_TIME_DELAYED);
            }
        }

    };

    @Override
    public void onClick(View v) {
        name = username.getText()
                .toString().trim();
        pas = pwd.getText().toString().trim();
        switch (v.getId()) {
            case R.id.id_register_submit:
                tv_phone_num_hint.setVisibility(View.INVISIBLE);
                tv_registercode_wrong.setVisibility(View.INVISIBLE);
                tv_registerpassword_wrong.setVisibility(View.INVISIBLE);
                if (!"".equals(name)) {
                    if (et_register_code.getText().toString().trim().equals(code)) {
                        if (CheckUtils.checkPassword(pwd.getText().toString(), pwdAgain
                                .getText().toString())) {
                            sendRegisterHttp();
                        } else {
                            tv_registerpassword_wrong.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tv_registercode_wrong.setVisibility(View.VISIBLE);
                    }
                } else {
                    tv_phone_num_hint.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.id_register_send_authcode:
                // 短信注册页面
                if (!"".equals(username.getText().toString().toString().trim())) {
                    tv_phone_num_hint.setVisibility(View.INVISIBLE);
                    startWhiteLoadingAnim();
                    requestAuthCode();
                } else {
                    tv_phone_num_hint.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.tv_register_agreement:
                Intent registeragreement = new Intent(RegisterActivity.this, ExplainWebViewActivity.class);
                registeragreement.putExtra("flag", 1000);
                startActivity(registeragreement);
                break;
            default:
                break;
        }
    }

    private void requestAuthCode() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "requestAuthCode");
            obj.put("userName", username.getText().toString().trim());
            obj.put("isRegistered", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("获取验证码提交数据", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("获取验证返回数据", new String(bytes));
                stopLoadingAnim();
                hideErrorPageState();
                try {
                    JSONObject object = new JSONObject(new String(bytes));
                    String result = object.getString("result");

                    if ("0".equals(result)) {
                        code = object.getString("code");
                        countDownTimer();
                    } else if ("1".equals(result)) {
                        resultNote = object.getString("resultNote");
                        tv_phone_num_hint.setText(resultNote);
                        tv_phone_num_hint.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(RegisterActivity.this, getResources().getString(R.string.service_error_hint));

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }

    //环信登录
    private void loginChat(String name, String pasw) {
        EMClient.getInstance().login(name, pasw, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                stopLoadingAnim();
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.e("main", "登录聊天服务器成功！");
                if (CrashApplication.getActivityByName("com.android.app.buystoreapp.BossBuyActivity") == null) {
                    startActivity(new Intent(RegisterActivity.this, BossBuyActivity.class));
                }
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.e("---环信返回码--", code + "");
                Log.e("main", "登录聊天服务器失败！");
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

    // 倒计时
    public void countDownTimer() {
        sendAuthCode.setEnabled(false);
        CountDownTimer timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                sendAuthCode.setText("重新获取" + "(" + millisUntilFinished / 1000 + ")");
            }

            @Override
            public void onFinish() {
                sendAuthCode.setEnabled(true);
                sendAuthCode.setText("重新发送");
            }
        };
        timer.start();
    }
}
