package com.android.app.buystoreapp.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AddAlipayActivity extends BaseAct implements View.OnClickListener {

    private EditText et_userName, et_userNumber, et_userNumber_repeat;
    private Button btn_mobileVerify;
    public static AddAlipayActivity maddAlipay;
    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_add_alipay);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_action_bar);
        maddAlipay = this;
        ViewUtils.inject(this);
        mTitleText.setText("添加支付宝账户");
        initView();
    }

    @Override
    protected void load() {
        super.load();

    }

    private void initView() {
        et_userName = (EditText) findViewById(R.id.et_userName);
        et_userNumber = (EditText) findViewById(R.id.et_userNumber);
        et_userNumber_repeat = (EditText) findViewById(R.id.et_userNumber_repeat);
        btn_mobileVerify = (Button) findViewById(R.id.btn_mobileVerify);
        btn_mobileVerify.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mobileVerify:
                if (!TextUtils.isEmpty(et_userName.getText().toString().trim())) {
                    if (!TextUtils.isEmpty(et_userNumber.getText().toString().trim())) {
                        if (et_userNumber.getText().toString().trim().equals(et_userNumber_repeat.getText().toString().trim())) {
                            Intent intent = new Intent(this, VerifyAlipayMobileActivity.class);
                            intent.putExtra("userName", et_userName.getText().toString().trim());
                            intent.putExtra("userNumber", et_userNumber.getText().toString().trim());
                            startActivity(intent);
                        } else {
                            ToastUtil.showMessageCenter(maddAlipay, "两次输入的帐号不一致，请重新输入");
                        }
                    }else {
                        ToastUtil.showMessageDefault(this,getResources().getString(R.string.zfb_empty));
                    }
                }else {
                    ToastUtil.showMessageDefault(this,getResources().getString(R.string.name_empty));
                }
                break;
        }
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
