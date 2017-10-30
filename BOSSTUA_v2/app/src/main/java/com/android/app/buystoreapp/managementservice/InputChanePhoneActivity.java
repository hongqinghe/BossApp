package com.android.app.buystoreapp.managementservice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.wallet.ToastUtil;

public class InputChanePhoneActivity extends BaseAct implements View.OnClickListener {
    private ImageButton iv_back;//返回按钮
    private EditText et_get_phone;//输入手机号
    private Button btn_change_phone_next;//下一步
    public static InputChanePhoneActivity inputChanePhoneActivity;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_chane_phone);
        initView();
        setListener();
        userName = SharedPreferenceUtils.getCurrentUserInfo(this).getUserName();
        inputChanePhoneActivity = this;
    }

    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string.change_phone));
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        et_get_phone = (EditText) findViewById(R.id.et_get_phone);
        btn_change_phone_next = (Button) findViewById(R.id.btn_change_phone_next);
    }

    private void setListener() {
        iv_back.setOnClickListener(this);
        et_get_phone.setOnClickListener(this);
        btn_change_phone_next.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回按钮
                this.finish();
                break;


            case R.id.btn_change_phone_next://获取电话号码
                if (userName.equals(et_get_phone.getText().toString().trim())) {
                    ToastUtil.showMessageDefault(InputChanePhoneActivity.this, getResources().getString(R.string.phone_same));
                } else {
                    if ("".equals(et_get_phone.getText().toString().trim())) {
                        ToastUtil.showMessageDefault(this, getResources().getString(R.string.personal_email_mobile_check));
                        return;
                    } else {
                        String phoneNum = et_get_phone.getText().toString().trim();
                        Intent changephone = new Intent(InputChanePhoneActivity.this, ChangePhoneActivity.class);
                        changephone.putExtra("phoneNum", phoneNum);
                        startActivity(changephone);
                    }
                }
                break;
        }
    }

    /**
     * 软键盘点非编辑框隐藏
     */
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
