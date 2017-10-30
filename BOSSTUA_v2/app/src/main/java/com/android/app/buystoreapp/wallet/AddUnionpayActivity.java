package com.android.app.buystoreapp.wallet;

import android.app.Activity;
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

public class AddUnionpayActivity extends BaseAct implements View.OnClickListener {

    private EditText et_userName;
    private EditText et_userNumber;
    private EditText tv_bank_name;

    public static Activity addUnionpay;

    //    private LinearLayout ll_unionpay_select;
    private Button btn_mobileVerify;


    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_add_unionpay);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_action_bar);
        ViewUtils.inject(this);
        addUnionpay = this;
        mTitleText.setText("填写银行卡信息");
        initView();
    }

    private void initView() {
        et_userName = (EditText) findViewById(R.id.et_userName);
        et_userNumber = (EditText) findViewById(R.id.et_userNumber);
//        ll_unionpay_select = (LinearLayout) findViewById(R.id.ll_unionpay_select);
        btn_mobileVerify = (Button) findViewById(R.id.btn_mobileVerify);
//        ll_unionpay_select.setOnClickListener(this);
        btn_mobileVerify.setOnClickListener(this);

        tv_bank_name = (EditText) findViewById(R.id.tv_bank_name);
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

    //    ToastUtil.showMessageDefault(this, "银行卡号错误，请输入正确的银行卡号");
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mobileVerify:
                if (TextUtils.isEmpty(et_userName.getText().toString().trim())) {
                    ToastUtil.showMessageDefault(this, "请输入储蓄卡号对应的真实姓名");
                } else if (TextUtils.isEmpty(et_userNumber.getText().toString())) {
                    ToastUtil.showMessageDefault(this, "请填写收款人储蓄卡号");
                } else {
                    if (TextUtils.isEmpty(tv_bank_name.getText().toString().trim())) {
                        ToastUtil.showMessageDefault(this, "请输入储蓄卡归属银行名称");
                    } else {
                        Intent intent = new Intent(this, VerifyUnionpayMobileActivity.class);
                        intent.putExtra("userName", et_userName.getText().toString().trim());
                        intent.putExtra("userNumber", et_userNumber.getText().toString().trim());
                        intent.putExtra("bankName", tv_bank_name.getText().toString().trim());
                        startActivity(intent);
                    }
                }
                break;
        }
    }


    /**
     * 判断是否是银行卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;

    }

    private static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
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
