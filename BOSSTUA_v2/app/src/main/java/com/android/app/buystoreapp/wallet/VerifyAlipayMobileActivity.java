package com.android.app.buystoreapp.wallet;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.utils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class VerifyAlipayMobileActivity extends BaseAct implements View.OnClickListener {

    private EditText et_verify_number;
    private Button btn_verify_next;
    private Button btn_get_verification_code;
    private TextView tv_verify_mobile;

    private AddAlipayActivity aaa;
    private VerifyAlipayMobileActivity vam;

    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;
    private String userId="";
    private String resultNote = "";
    private String code = "1234567";
    private String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_verify_alipay_mobile);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_action_bar);
        phone = SharedPreferenceUtils.getCurrentUserInfo(this).getUserName();
        vam = this;
        ViewUtils.inject(this);
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        mTitleText.setText("验证手机号");
        addIncludeLoading(true);
        initView();
    }

    private void initView() {
        tv_verify_mobile = (TextView) findViewById(R.id.tv_verify_mobile);
        if (!TextUtils.isEmpty(phone) && phone.length() > 6) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < phone.length(); i++) {
                char c = phone.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }

            tv_verify_mobile.setText("绑定支付宝帐号需要短信确认,验证码已发送至手机：" + sb.toString() + ",请按提示操作.");
        }
        et_verify_number = (EditText) findViewById(R.id.et_verify_number);
        btn_verify_next = (Button) findViewById(R.id.btn_verify_next);
        btn_get_verification_code = (Button) findViewById(R.id.btn_get_verification_code);
        btn_verify_next.setOnClickListener(this);
        btn_get_verification_code.setOnClickListener(this);
    }

    @OnClick(R.id.id_custom_back_image)
    public void onCustomBarBackClicked(View v) {
        switch (v.getId()) {
            case R.id.id_custom_back_image:
                this.finish();
            default:
                break;
        }
    }

    @Override
    protected void load() {
        super.load();

        startWhiteLoadingAnim();
        addBindPayTreasure();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_verification_code:
                requestAuthCode();
                break;
            case R.id.btn_verify_next:
                if (code.equals(et_verify_number.getText().toString().trim())) {
                    load();
                } else {
                    ToastUtil.showMessageDefault(VerifyAlipayMobileActivity.this, getResources().getString(R.string.register_code_wrong));
                }

                break;
        }
    }

    private void addBindPayTreasure() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "addBindPayTreasure");
            obj.put("userId", userId);
            obj.put("realName", getIntent().getExtras().getString("userName"));
            obj.put("payTreasureNum", getIntent().getExtras().getString("userNumber"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("绑定支付宝", "绑定支付宝提交数据 obj==" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                Log.e("绑定支付宝", "绑定支付宝返回数据 bytes==" + new String(bytes));
                try {
                    JSONObject object = new JSONObject(new String(bytes));
                    String result = (String) object.get("result");
                    String resultNote = (String) object.get("resultNote");
                    if ("0".equals(result)) {

                        ToastUtil.showMessageDefault(vam, resultNote);
                        finish();
                        AddAlipayActivity.maddAlipay.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(vam, getResources().getString(R.string.service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }

    // 倒计时
    public void countDownTimer() {
        btn_get_verification_code.setEnabled(false);
        CountDownTimer timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                btn_get_verification_code.setText("重新获取" + "(" + millisUntilFinished / 1000 + ")");
            }

            @Override
            public void onFinish() {
                btn_get_verification_code.setEnabled(true);
                btn_get_verification_code.setText("重新发送");
            }
        };
        timer.start();
    }

    private void requestAuthCode() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "requestAuthCode");
            obj.put("userName", phone);
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
                    resultNote = object.getString("resultNote");
                    if ("0".equals(result)) {
                        code = object.getString("code");
                        btn_get_verification_code.setBackgroundResource(R.color.gainsboro);
                        btn_get_verification_code.setTextColor(getResources().getColor(R.color.c_999999));
                        btn_get_verification_code.setEnabled(false);
                        countDownTimer();
                    } else if ("1".equals(result)) {
                        ToastUtil.showMessageDefault(VerifyAlipayMobileActivity.this, resultNote);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(VerifyAlipayMobileActivity.this, getResources().getString(R.string.service_error_hint));

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }
}
