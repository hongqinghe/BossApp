package com.android.app.buystoreapp.setting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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

public class ModifyPasswordActivity extends Activity implements OnClickListener {
    private EditText modifyPwd;
    private EditText modifyPwdAgain;
    private Button submitBtn;
    private EditText userPhone;

    String webUrl;
    String userName;
    
    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.modify_password);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_action_bar);
        
        ViewUtils.inject(this);
        mTitleText.setText("修改密码");
        
        webUrl = getResources().getString(R.string.web_url);
       // userName = SharedPreferenceUtils.getCurrentUserInfo(this).getUserName();
        /*if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this,
                    getResources().getString(R.string.modify_pwd_error),
                    Toast.LENGTH_SHORT).show();
            finish();
        }*/
      //  userPhone = (EditText) findViewById(R.id.id_phone);
        userName = getIntent().getStringExtra("phone");
        modifyPwd = (EditText) findViewById(R.id.id_modify_pwd);
        modifyPwdAgain = (EditText) findViewById(R.id.id_modify_pwd_again);
        submitBtn = (Button) findViewById(R.id.id_modify_pwd_submit);
        submitBtn.setOnClickListener(this);
      //  userPhone.setOnFocusChangeListener(userFocusChangeListener);
        LogUtils.d("modify passsword");
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
    public void onClick(View v) {
        if (CheckUtils.checkPassword(modifyPwd.getText().toString(),modifyPwdAgain.getText().toString())) {
            sendModifyPwdHttp();
        } else {
            Toast.makeText(this,
                    getResources().getString(R.string.login_pwd_error),
                    Toast.LENGTH_SHORT).show();
        }
    }
    /*private OnFocusChangeListener userFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View arg0, boolean hasFocus) {
            if (!hasFocus) {
                if (TextUtils.isEmpty(userPhone.getText()
                        .toString()) ||!CheckUtils.mobileCheck(userPhone.getText()
                                .toString())) {
                    Toast.makeText(
                            getBaseContext(),
                            getResources().getString(
                                    R.string.personal_email_mobile_check),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
     };*/
    private void sendModifyPwdHttp() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        final Gson gson = new Gson();
       /* userName = userPhone.getText()
                .toString().trim();*/
        UserAuthCodeBean userAuthCode = new UserAuthCodeBean();
        userAuthCode.setUserName(userName);
        userAuthCode.setNewPassword(modifyPwd.getText().toString());
        GsonUserAuthCmd gsonUserAuthCmd = new GsonUserAuthCmd("changePassword",
                userAuthCode);
        String param = gson.toJson(gsonUserAuthCmd);
        Log.d("mikes", "param:" + param);
        requestParams.put("json", param);

        client.get(webUrl, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                GsonBackOnlyResult gsonUserAuthBack = gson.fromJson(new String(
                        arg2), new TypeToken<GsonBackOnlyResult>() {
                }.getType());

                try {
                    String result = gsonUserAuthBack.getResult();
                    String resultNote = gsonUserAuthBack.getResultNote();
                    if ("1".equals(result)) {// fail
                        Toast.makeText(getApplicationContext(), resultNote,
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Toast.makeText(getApplicationContext(), resultNote,
                                Toast.LENGTH_SHORT).show();
                        ModifyPasswordActivity.this.finish();
                    }
                } catch (NullPointerException e) {
                    Log.e("mikes", "sendAuthCodeHttp error", e);
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                    Throwable arg3) {
                Toast.makeText(ModifyPasswordActivity.this,
                        getResources().getString(R.string.modify_pwd_error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
