package com.android.app.buystoreapp.setting;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.app.buystore.utils.CheckUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class FindPwdConfirmActivity extends BaseAct implements OnClickListener {
    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    private LinearLayout ll_visibility;
    private EditText id_register_username;
    private EditText et_register_code;
    private EditText id_register_pwd;
    private EditText id_register_pwd_again;

    private TextView tv_registercode_wrong;
    private TextView tv_registerpassword_wrong;
    private TextView tv_v1;
    private TextView tv_register_agreement;
    private TextView tv_phone_num_hint;

    private Button id_register_submit;
    private Button id_register_send_authcode;
    private String code = "1234567";
    private String resultNote = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.register_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_action_bar);
        ViewUtils.inject(this);
        mTitleText.setText(getResources().getString(R.string.foud_password));
        initView();
        initErrorPage();
        addIncludeLoading(true);

    }

    private void initView() {
        tv_v1 = (TextView) findViewById(R.id.tv_v1);
        tv_v1.setVisibility(View.GONE);
        tv_phone_num_hint = (TextView) findViewById(R.id.tv_phone_num_hint);
        tv_register_agreement = (TextView) findViewById(R.id.tv_register_agreement);
        tv_register_agreement.setVisibility(View.GONE);
        ll_visibility = (LinearLayout) findViewById(R.id.ll_visibility);
        ll_visibility.setVisibility(View.GONE);
        id_register_send_authcode = (Button) findViewById(R.id.id_register_send_authcode);
        id_register_submit = (Button) findViewById(R.id.id_register_submit);
        id_register_submit.setText(getResources().getString(R.string.btn_submit));
        id_register_submit.setOnClickListener(this);
        id_register_send_authcode.setOnClickListener(this);
        id_register_username = (EditText) findViewById(R.id.id_register_username);
        et_register_code = (EditText) findViewById(R.id.et_register_code);
        id_register_pwd = (EditText) findViewById(R.id.id_register_pwd);
        id_register_pwd_again = (EditText) findViewById(R.id.id_register_pwd_again);


        tv_registercode_wrong = (TextView) findViewById(R.id.tv_registercode_wrong);
        tv_registerpassword_wrong = (TextView) findViewById(R.id.tv_registerpassword_wrong);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_register_submit:
                tv_phone_num_hint.setVisibility(View.INVISIBLE);
                tv_registercode_wrong.setVisibility(View.INVISIBLE);
                tv_registerpassword_wrong.setVisibility(View.INVISIBLE);
                if (!"".equals(id_register_username.getText().toString().trim())) {
                    if (code.equals(et_register_code.getText().toString().trim())) {
                        if (CheckUtils.checkPassword(id_register_pwd.getText().toString().trim()
                                , id_register_pwd_again.getText().toString().trim())) {
                            findPassword();

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
                if (!"".equals(id_register_username.getText().toString().toString().trim())) {
                    tv_phone_num_hint.setVisibility(View.INVISIBLE);
                    startWhiteLoadingAnim();
                    requestAuthCode();
                } else {
                    tv_phone_num_hint.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void findPassword() {
        final JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "findPassword");
            obj.put("userName", id_register_username.getText().toString().trim());
            obj.put("password", id_register_pwd_again.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("找回密码提交数据", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Log.e("找回密码返回数据", new String(bytes));
                try {
                    JSONObject object = new JSONObject(new String(bytes));
                    String result = object.getString("result");
                    String resultNote = object.getString("resultNote");
                    if ("0".equals(result)) {
                        ToastUtil.showMessageDefault(FindPwdConfirmActivity.this, resultNote);
                        finish();
                    }
                    if ("1".equals(result)) {
                        ToastUtil.showMessageDefault(FindPwdConfirmActivity.this, resultNote);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                hideErrorPageState();
                ToastUtil.showMessageDefault(FindPwdConfirmActivity.this, getResources().getString(R.string.service_error_hint));

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }

    private void requestAuthCode() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "requestAuthCode");
            obj.put("userName", id_register_username.getText().toString().trim());
            obj.put("isRegistered", 1);
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
                ToastUtil.showMessageDefault(FindPwdConfirmActivity.this, getResources().getString(R.string.service_error_hint));

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
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
        id_register_send_authcode.setEnabled(false);
        CountDownTimer timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                id_register_send_authcode.setText("重新获取" + "(" + millisUntilFinished / 1000 + ")");
            }

            @Override
            public void onFinish() {
                id_register_send_authcode.setEnabled(true);
                id_register_send_authcode.setText("重新发送");
            }
        };
        timer.start();
    }
}
