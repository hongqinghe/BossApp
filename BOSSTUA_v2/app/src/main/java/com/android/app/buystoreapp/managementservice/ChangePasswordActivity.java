package com.android.app.buystoreapp.managementservice;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.android.app.buystoreapp.bean.UserInfoBean;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends BaseAct implements View.OnClickListener {
    private ImageButton iv_back;//返回按钮
    private Button btn_save_password;//保存按钮
    private EditText et_old_password;
    private EditText et_new_password;
    private EditText et_new_password_tow;
    private String userId;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case SubscribeActivity.HANDLE_LOADMORE:
                    ToastUtil.showMessageDefault(ChangePasswordActivity.this, resultNote);
                    exit();
                    AccountSafeActivity.accountSafeActivity.finish();
                    PersonalSettingsActivity.personalSettingsActivity.finish();
                    break;
            }
        }
    };
    private String resultNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        setListener();
        initErrorPage();
        addIncludeLoading(true);
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
    }

    private void initView() {
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        btn_save_password = (Button) findViewById(R.id.btn_save_password);
        et_old_password = (EditText) findViewById(R.id.et_old_password);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        et_new_password_tow = (EditText) findViewById(R.id.et_new_password_tow);
        ((TextView)findViewById(R.id.tv_title)).setText(getResources().getString(R.string.change_password));
    }

    private void setListener() {
        iv_back.setOnClickListener(this);
        btn_save_password.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回按钮
                this.finish();
                break;
            case R.id.btn_save_password://保存按钮
                if (et_new_password.getText().toString().trim().equals(et_new_password_tow.getText().toString().trim())) {

                    startWhiteLoadingAnim();
                    changePasswordByOld();
                } else {
                    et_new_password.setText(null);
                    et_new_password_tow.setText(null);
                    et_new_password.setFocusable(true);
                    et_new_password.setFocusableInTouchMode(true);
                    InputMethodManager imm = (InputMethodManager) et_new_password.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);

                    ToastUtil.showMessageDefault(ChangePasswordActivity.this, getResources().getString(R.string.register_pwd_error));
                }

                break;
        }

    }

    /**
     * 修改密码
     */
    private void changePasswordByOld() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "changePasswordByOld");
            obj.put("userId", userId);
            obj.put("oldPassword", et_old_password.getText().toString().trim());
            obj.put("newPassword", et_new_password.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("修改密码提交数据 obj==" , obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("修改密码返回数据  bytes==" , new String(bytes));
                stopLoadingAnim();
                try {
                    JSONObject object = new JSONObject(new String(bytes));
                    String result = (String) object.get("result");
                     resultNote = (String) object.get("resultNote");
                    if ("0".equals(result)) {
                        mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
                        finish();
                    } else {
                        ToastUtil.showMessageDefault(ChangePasswordActivity.this, resultNote);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(ChangePasswordActivity.this, getResources().getString(R.string.service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                ToastUtil.showMessageDefault(ChangePasswordActivity.this, getResources().getString(R.string.service_error_hint));
            }
        });
    }

    /**
     * 注销用户
     */
    private void exit() {
        UserInfoBean userInfo = new UserInfoBean();
        SharedPreferenceUtils.saveCurrentUserInfo(userInfo, this, false);
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
