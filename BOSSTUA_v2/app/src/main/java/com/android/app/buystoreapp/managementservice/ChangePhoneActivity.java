package com.android.app.buystoreapp.managementservice;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.BossBuyActivity;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.UserInfoBean;
import com.android.app.buystoreapp.other.CustomDialog;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangePhoneActivity extends BaseAct implements View.OnClickListener {
    private ImageButton iv_back;//返回按钮
    private TextView tv_set_change_phone_num;//设置手机号
    private EditText et_input_verification_code;//输入验证码
    private Button btn_get_verification_code;//获取验证码
    private Button btn_change_phone_next;//下一步
    private String userId;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SubscribeActivity.HANDLE_LOADMORE:
                    exit();
                    Intent intent = new Intent(ChangePhoneActivity.this, BossBuyActivity.class);
                    startActivity(intent);
                    finish();
                    InputChanePhoneActivity.inputChanePhoneActivity.finish();
                    ToastUtil.showMessageDefault(ChangePhoneActivity.this, "手机号修改成功，请重新登录");
                    break;
            }
        }
    };
    private String code="12345676";
    private String phone;

    private void exit() {
        UserInfoBean userInfoBean = new UserInfoBean();
        SharedPreferenceUtils.saveCurrentUserInfo(userInfoBean, this, false);
    }

    private String resultNote;

    @Override
    protected void load() {
        super.load();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        phone = getIntent().getExtras().getString("phoneNum");
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        initView();
        setlistener();
        initErrorPage();
        addIncludeLoading(true);
    }

    private void initView() {
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        tv_set_change_phone_num = (TextView) findViewById(R.id.tv_set_change_phone_num);
        et_input_verification_code = (EditText) findViewById(R.id.et_input_verification_code);
        btn_get_verification_code = (Button) findViewById(R.id.btn_get_verification_code);
        btn_change_phone_next = (Button) findViewById(R.id.btn_change_phone_next);
        if(!TextUtils.isEmpty(phone) && phone.length() > 6 ){
            StringBuilder sb  =new StringBuilder();
            for (int i = 0; i < phone.length(); i++) {
                char c = phone.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }

            tv_set_change_phone_num.setText("修改手机号需要短信确认，验证码已发送至手机:"
                    +sb.toString()
                    + ",请按提示操作");
        }
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string.change_phone));
    }

    private void setlistener() {
        iv_back.setOnClickListener(this);
        btn_get_verification_code.setOnClickListener(this);
        btn_change_phone_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回按钮
                this.finish();
                break;
            case R.id.btn_get_verification_code:
                requestAuthCode();
                break;
            case R.id.btn_change_phone_next:
                if (code.equals(et_input_verification_code.getText().toString().trim())) {
                    CustomDialog.initDialog(mContext);
                    CustomDialog.tvTitle.setText("尊贵的Boss，由于平台运营成本，此次修改需要消耗您99个Boss券");
                    CustomDialog.btnLeft.setText("确定更换");
                    CustomDialog.btnRight.setText("我再想想");
                    CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startWhiteLoadingAnim();
                            updateUserSecurityAccount();
                        }
                    });

                    CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog.dialog.dismiss();
                        }
                    });

                } else {
                    ToastUtil.showMessageDefault(ChangePhoneActivity.this, getResources().getString(R.string.register_code_wrong));
                }
                break;
        }

    }

    private void updateUserSecurityAccount() {

        startLoadingAnim();

        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "updateUserSecurityAccount");
            obj.put("userId", userId);
            obj.put("phoneEmailQQ", phone);
            obj.put("state", 1);// //1手机号，2QQ号，3邮箱

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
        }
        Log.e("修改手机提交obj", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("修改手机返回bytes", new String(bytes));
                stopLoadingAnim();
                try {
                    JSONObject object = new JSONObject(new String(bytes));
                    String result = (String) object.get("result");
                    resultNote = (String) object.get("resultNote");
                    if ("0".equals(result)) {
                        mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
                    } else if ("1".equals(result)) {
                        ToastUtil.showMessageDefault(ChangePhoneActivity.this, resultNote);
                    } else if ("2".equals(result)) {
                        ToastUtil.showMessageDefault(ChangePhoneActivity.this, resultNote);
                    } else if ("3".equals(result)) {
                        ToastUtil.showMessageDefault(ChangePhoneActivity.this, resultNote);
                    } else if ("4".equals(result)) {
                        ToastUtil.showMessageDefault(ChangePhoneActivity.this, resultNote);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(ChangePhoneActivity.this, getResources().getString(R.string.service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }

    // 倒计时
    public void countDownTimer() {
        CountDownTimer timer = new CountDownTimer(60000, 1000) {


            @Override
            public void onTick(long millisUntilFinished) {
                btn_get_verification_code.setText("重新获取" + "(" + millisUntilFinished / 1000 + ")");
                btn_get_verification_code.setTextSize(15);
            }

            @Override
            public void onFinish() {
                btn_get_verification_code.setBackgroundResource(R.color.c_168eef);
                btn_get_verification_code.setTextColor(getResources().getColor(R.color.c_ffffff));
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
                    resultNote = (String) object.get("resultNote");
                    if ("0".equals(result)) {
                        code = object.getString("code");
                        btn_get_verification_code.setBackgroundResource(R.color.gainsboro);
                        btn_get_verification_code.setTextColor(getResources().getColor(R.color.c_999999));
                        btn_get_verification_code.setEnabled(false);
                        countDownTimer();
                    } else if ("1".equals(result)) {
                        ToastUtil.showMessageDefault(ChangePhoneActivity.this, resultNote);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(ChangePhoneActivity.this, getResources().getString(R.string.service_error_hint));

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }
}
