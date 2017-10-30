package com.android.app.buystoreapp.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.CheckUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.GsonBackOnlyResult;
import com.android.app.buystoreapp.bean.GsonUserAuthCmd;
import com.android.app.buystoreapp.bean.UserAuthCodeBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

public class FindPasswordActivity extends Activity implements OnClickListener {
    private Button msendAuthCodeBtn;
    private EditText mfindPwdUsername;
    private EditText mfindPwdAuthCode;
    private Button mfindPwdSubmitBtn;

    private static final int UPDATE_AUTHCODE_TIME_DELAYED = 1000;
    private int AUTHCODE_TIME = 120;// 120s

    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.find_password_layout);

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_action_bar);

        ViewUtils.inject(this);
        mTitleText.setText("密码找回");

        msendAuthCodeBtn = (Button) findViewById(R.id.id_updateuserinfo_findpwd_send_authcode);
        mfindPwdUsername = (EditText) findViewById(R.id.id_updateuserinfo_findpwd_username);
        mfindPwdUsername.setOnFocusChangeListener(userFocusChangeListener);

        mfindPwdAuthCode = (EditText) findViewById(R.id.id_updateuserinfo_findpwd_authcode);
        mfindPwdAuthCode.clearFocus();
        mfindPwdSubmitBtn = (Button) findViewById(R.id.id_updateuserinfo_findpwd_submit);
        msendAuthCodeBtn.setOnClickListener(this);
        mfindPwdSubmitBtn.setOnClickListener(this);
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

    private OnFocusChangeListener userFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View arg0, boolean hasFocus) {
            if (!hasFocus) {
                if (!CheckUtils.mobileCheck(mfindPwdUsername.getText()
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

    private TextWatcher findUsernameWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() > 6) {
                msendAuthCodeBtn.setBackgroundColor(getResources().getColor(
                        R.color.green));
                msendAuthCodeBtn.setEnabled(true);
            } else {
                msendAuthCodeBtn.setBackgroundColor(getResources().getColor(
                        R.color.gray));
                msendAuthCodeBtn.setEnabled(false);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.id_updateuserinfo_findpwd_send_authcode:
            sendAuthCodeHttp();
            break;
        case R.id.id_updateuserinfo_findpwd_submit:
            mfindPwdAuthCode.clearFocus();
            sendSubmitAuthCode();
            break;
        default:
            break;
        }
    }

    private Handler findPwdHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int time = msg.arg1;
            if (time <= 0) {
                AUTHCODE_TIME = 10;
                findPwdHandler.removeMessages(0);
                msendAuthCodeBtn.setEnabled(true);
                msendAuthCodeBtn
                        .setText(getString(R.string.login_send_authcode));
            } else {
                msendAuthCodeBtn.setEnabled(false);
                msendAuthCodeBtn.setText(String.format("%1$d s", time));
                findPwdHandler.sendMessageDelayed(
                        findPwdHandler.obtainMessage(0, AUTHCODE_TIME--, 0),
                        UPDATE_AUTHCODE_TIME_DELAYED);
            }
        }
    };

    private void sendAuthCodeHttp() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        final Gson gson = new Gson();
        UserAuthCodeBean userAuthCode = new UserAuthCodeBean(mfindPwdUsername
                .getText().toString());
        GsonUserAuthCmd gsonUserAuthCmd = new GsonUserAuthCmd(
                "requestAuthCode", userAuthCode);
        String param = gson.toJson(gsonUserAuthCmd);
        requestParams.put("json", param);

        client.get(getString(R.string.web_url), requestParams,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        try {
                            LogUtils.d("result="+new String(arg2));
                            GsonBackOnlyResult gsonUserAuthBack = gson.fromJson(
                                    new String(arg2),
                                    new TypeToken<GsonBackOnlyResult>() {
                                    }.getType());
                            String result = gsonUserAuthBack.getResult();
                            String resultNote = gsonUserAuthBack
                                    .getResultNote();
                            if ("1".equals(result)) {// fail
                                Toast.makeText(getApplicationContext(),
                                        resultNote, Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        getResources()
                                                .getString(
                                                        R.string.request_authcode_success),
                                        Toast.LENGTH_SHORT).show();
                                findPwdHandler.sendMessageDelayed(
                                        findPwdHandler.obtainMessage(0,
                                                AUTHCODE_TIME--, 0),
                                        UPDATE_AUTHCODE_TIME_DELAYED);
                            }
                        } catch (NullPointerException e) {
                            Log.e("mikes", "sendAuthCodeHttp error", e);
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                            Throwable arg3) {

                    }
                });
    }

    private void sendSubmitAuthCode() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        final Gson gson = new Gson();
        UserAuthCodeBean userAuthCode = new UserAuthCodeBean(mfindPwdUsername
                .getText().toString(), mfindPwdAuthCode.getText().toString());
        GsonUserAuthCmd gsonUserAuthCmd = new GsonUserAuthCmd("findPassword",
                userAuthCode);
        String param = gson.toJson(gsonUserAuthCmd);
        requestParams.put("json", param);

        client.get(getString(R.string.web_url), requestParams,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        GsonBackOnlyResult gsonUserAuthBack = gson.fromJson(
                                new String(arg2),
                                new TypeToken<GsonBackOnlyResult>() {
                                }.getType());

                        try {
                            String result = gsonUserAuthBack.getResult();
                            String resultNote = gsonUserAuthBack
                                    .getResultNote();
                            if ("1".equals(result)) {// fail
                                Toast.makeText(getApplicationContext(),
                                        resultNote, Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        resultNote, Toast.LENGTH_SHORT).show();
                                // go to modify password screen
                                Intent intent = new Intent(
                                        FindPasswordActivity.this,
                                        ModifyPasswordActivity.class);
                                intent.putExtra("userName", mfindPwdUsername
                                        .getText().toString());
                                startActivity(intent);
                            }
                        } catch (NullPointerException e) {
                            Log.e("mikes", "sendAuthCodeHttp error", e);
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                            Throwable arg3) {
                    }
                });
    }
}
