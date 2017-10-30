package com.android.app.buystoreapp.managementservice;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.MyBossTicketDetail;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 账号安全
 * weilin
 */
public class AccountSafeActivity extends BaseAct implements View.OnClickListener {
    private TextView tv_title;
    private RelativeLayout rl_change_password;//更换密码
    private ImageButton iv_back;//返回按钮
    private Button btn_phone_binding_or_change;//手机号修改按钮
    private Button btn_qq_binding_or_change;//绑定qq
    private Button btn_email_binding_or_change;//绑定email
    private TextView tv_set_phone;
    private EditText et_set_qq;
    private EditText et_set_email;
    private int flag;

    public static AccountSafeActivity accountSafeActivity;
    private String userId;
    private String email;
    private String qQ;
    private String phone;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SubscribeActivity.HANDLE_LOADMORE:
                    if ("".equals(qQ)) {
                    } else {
                        et_set_qq.setText(qQ);
                        btn_qq_binding_or_change.setText("修改");
                    }
                    if ("".equals(email)) {
                    } else {
                        et_set_email.setText(email);
                        btn_email_binding_or_change.setText("修改");
                    }
                    if (phone.equals("")) {
                        tv_set_phone.setText(userName);
                    } else {
                        tv_set_phone.setText(phone);
                    }
                    toRight(et_set_qq);
                    toRight(et_set_email);
                    break;
                case UPDATAE_SUCCESS:
                    switch (flag) {
                        case 2:
                            if ("绑定".equals(btn_qq_binding_or_change.getText().toString().trim())) {
                                ToastUtil.showMessageDefault(AccountSafeActivity.this, "绑定成功");
                            } else if ("修改".equals(btn_qq_binding_or_change.getText().toString().trim())) {
                                ToastUtil.showMessageDefault(AccountSafeActivity.this, "修改成功");
                            }
                            break;
                        case 3:
                            if ("绑定".equals(btn_email_binding_or_change.getText().toString().trim())) {
                                ToastUtil.showMessageDefault(AccountSafeActivity.this, "绑定成功");
                            } else if ("修改".equals(btn_email_binding_or_change.getText().toString().trim())) {
                                ToastUtil.showMessageDefault(AccountSafeActivity.this, "修改成功");
                            }
                            break;
                    }

                    break;
            }
        }
    };
    private String userName;
    public static final int UPDATAE_SUCCESS = 1000;
    private String updataresultNote;
    private Dialog changePhoneDialog;
    private String ticketCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safe);
        initView();
        setListener();
        accountSafeActivity = this;
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        userName = SharedPreferenceUtils.getCurrentUserInfo(this).getUserName();

        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        load();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ticketCount = SharedPreferenceUtils.getBossTicketCount(mContext);
        Log.e("ticketCount","change phone ticketCount ===== " +ticketCount);
    }

    @Override
    protected void load() {
        super.load();
        getUserSecurityAccount();
    }

    /**
     * 修改资料（账号安全）
     */
    public void updateUserSecurityAccount(String str, int state) {
        flag = state;
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "updateUserSecurityAccount");
            obj.put("userId", userId);
            obj.put("phoneEmailQQ", str);
            obj.put("state", state);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("AccountSafeActivity", "修改资料账号安全提交obj obj==" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                try {
                    JSONObject object = new JSONObject(new String(bytes));
                    String result = (String) object.get("result");
                    updataresultNote = (String) object.get("resultNote");
                    if ("0".equals(result)) {
                        mHandler.obtainMessage(UPDATAE_SUCCESS).sendToTarget();
                    } else if ("1".equals(result)) {
                        ToastUtil.showMessageDefault(AccountSafeActivity.this, updataresultNote);
                    } else if ("2".equals(result)) {
                        ToastUtil.showMessageDefault(AccountSafeActivity.this, updataresultNote);
                    } else if ("3".equals(result)) {
                        ToastUtil.showMessageDefault(AccountSafeActivity.this, updataresultNote);
                    } else if ("4".equals(result)) {
                        ToastUtil.showMessageDefault(AccountSafeActivity.this, updataresultNote);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(AccountSafeActivity.this, getResources().getString(R.string.service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                ToastUtil.showMessageDefault(AccountSafeActivity.this, getResources().getString(R.string.service_error_hint));
            }
        });

    }

    /**
     * 查询资料（账号安全）
     */
    public void getUserSecurityAccount() {
        final JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getUserSecurityAccount");
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("AccounSafeActivity", "查询账号安全资料提交obj  obj==" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Log.e("AccounSafeActivity", "查询账号安全资料返回bytes  bytes==" + new String(bytes));
                try {
                    JSONObject object = new JSONObject(new String(bytes));
                    String result = (String) object.get("result");
                    if ("0".equals(result)) {
                        email = (String) object.get("email");
                        qQ = (String) object.get("qQ");
                        phone = (String) object.get("phone");
                        mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(AccountSafeActivity.this, getResources().getString(R.string.service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }

    public void toRight(EditText v) {
        CharSequence text = v.getText();
        //Debug.asserts(text instanceof Spannable);
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.account_safe));
        rl_change_password = (RelativeLayout) findViewById(R.id.rl_change_password);
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        btn_phone_binding_or_change = (Button) findViewById(R.id.btn_phone_binding_or_change);
        btn_qq_binding_or_change = (Button) findViewById(R.id.btn_qq_binding_or_change);
        btn_email_binding_or_change = (Button) findViewById(R.id.btn_email_binding_or_change);
        et_set_qq = (EditText) findViewById(R.id.et_set_qq);
        et_set_email = (EditText) findViewById(R.id.et_set_email);
        tv_set_phone = (TextView) findViewById(R.id.tv_set_phone);

    }

    private void setListener() {
        rl_change_password.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        btn_phone_binding_or_change.setOnClickListener(this);
        btn_qq_binding_or_change.setOnClickListener(this);
        btn_email_binding_or_change.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_change_password://更换密码
                Intent changepassword = new Intent(AccountSafeActivity.this, ChangePasswordActivity.class);
                startActivity(changepassword);
                break;

            case R.id.btn_phone_binding_or_change://更换手机号
                changePhoneDialog();
//                Intent changephone = new Intent(AccountSafeActivity.this, InputChanePhoneActivity.class);
//                startActivity(changephone);
                break;

            case R.id.iv_back://返回按钮
                this.finish();
                break;

            case R.id.btn_qq_binding_or_change://绑定QQ
                if (TextUtils.isEmpty(et_set_qq.getText().toString().trim())) {
                    btn_qq_binding_or_change.setText("绑定");
                    ToastUtil.showMessageDefault(AccountSafeActivity.this, "请输入要绑定的QQ号");
                } else {
                    btn_qq_binding_or_change.setText("修改");
                    updateUserSecurityAccount(et_set_qq.getText().toString().trim(), 2);
                }


                break;

            case R.id.btn_email_binding_or_change://绑定邮箱
                if (TextUtils.isEmpty(et_set_email.getText().toString().trim())) {
                    btn_email_binding_or_change.setText("绑定");
                    ToastUtil.showMessageDefault(AccountSafeActivity.this, "请输入要绑定的QQ号");
                } else {
                    btn_email_binding_or_change.setText("修改");
                    updateUserSecurityAccount(et_set_email.getText().toString().trim(), 3);
                }

                break;
        }
    }


    private void changePhoneDialog() {
        View layout = this.getLayoutInflater().inflate(R.layout
                .change_phone_dialog, null);
        TextView tv_set_warning = (TextView) layout.findViewById(R.id.tv_set_warning);
        String note = tv_set_warning.getText().toString().trim();
        ColorStateList redColors = ColorStateList.valueOf(0xffff0000);
        SpannableStringBuilder style = new SpannableStringBuilder(note);
        style.setSpan(new TextAppearanceSpan(null, 0, 40, redColors, null), 7, 9, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);  //设置指定位置文字的颜色
        tv_set_warning.setText(style);
        Button btn_true = (Button) layout.findViewById(R.id.btn_true);
        ImageButton btn_false = (ImageButton) layout.findViewById(R.id.btn_false);
        if (Integer.valueOf(ticketCount) < 99) {
            btn_true.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showMessageDefault(mContext,"您的Boss券不足，请购买");
                    Intent i = new Intent(AccountSafeActivity.this, MyBossTicketDetail.class);
                    i.putExtra("isChangePhone",1);
                    startActivity(i);
                    changePhoneDialog.dismiss();
                }
            });
        } else {
            btn_true.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent changePhone = new Intent(AccountSafeActivity.this, InputChanePhoneActivity.class);
                    startActivity(changePhone);
                    changePhoneDialog.dismiss();
                }
            });
        }

        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePhoneDialog.dismiss();
            }
        });
        changePhoneDialog = new Dialog(this, R.style.Dialog1);
        changePhoneDialog.setContentView(layout);
        changePhoneDialog.show();
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
