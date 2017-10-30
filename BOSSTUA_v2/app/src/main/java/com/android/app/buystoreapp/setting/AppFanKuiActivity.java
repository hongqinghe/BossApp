package com.android.app.buystoreapp.setting;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.managementservice.SubscribeActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class AppFanKuiActivity extends BaseAct implements View.OnClickListener {
    private ImageButton id_custom_back_image;
    private TextView id_custom_title_text;
    private EditText et_app_fankui_title;
    private EditText et_proposed_feedback_context;
    private TextView tv_number;
    private RelativeLayout rl_edit;
    private Button btn_app_fankui;
    private int num = 250;
    private String userId;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case SubscribeActivity.HANDLE_LOADMORE:
                    ToastUtil.showMessageDefault(AppFanKuiActivity.this,"非常感谢您提交的反馈，在您的参与下Boss团会变得越来越好");
                    AppFanKuiActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.app_fankui_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_action_bar);
        ViewUtils.inject(this);

        initView();
        setListener();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
    }

    public void load() {
        UserFeedBack();
    }

    private void setListener() {
        id_custom_back_image.setOnClickListener(this);
        rl_edit.setOnClickListener(this);
        btn_app_fankui.setOnClickListener(this);

        /**
         * 给字数设置限制
         * */
        et_proposed_feedback_context.addTextChangedListener(new TextWatcher() {
            private CharSequence wordNum;//记录输入的字数
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;//实时记录输入的字数
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (wordNum.length() >= num) {
                    ToastUtil.showMessageDefault(AppFanKuiActivity.this, "只能输入250个字!!!");
                }
                int number = s.length();
                //TextView显示剩余字数
                tv_number.setText(number + "/250");
                selectionStart = et_proposed_feedback_context.getSelectionStart();
                selectionEnd = et_proposed_feedback_context.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    et_proposed_feedback_context.setText(s);
                    et_proposed_feedback_context.setSelection(tempSelection);//设置光标在最后
                }
            }
        });
    }

    private void initView() {
        id_custom_back_image = (ImageButton) findViewById(R.id.id_custom_back_image);
        id_custom_title_text = (TextView) findViewById(R.id.id_custom_title_text);
        id_custom_title_text.setText("建议反馈");
        et_proposed_feedback_context = (EditText) findViewById(R.id.et_proposed_feedback_context);
        et_app_fankui_title = (EditText) findViewById(R.id.et_app_fankui_title);
        tv_number = (TextView) findViewById(R.id.tv_number);
        rl_edit = (RelativeLayout) findViewById(R.id.rl_edit);
        btn_app_fankui = (Button) findViewById(R.id.btn_app_fankui);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_custom_back_image:
                this.finish();
                break;

            case R.id.btn_app_fankui:
                if (et_app_fankui_title.getText().toString().trim().isEmpty()) {
                    ToastUtil.showMessageDefault(this, "请输入反馈主题");
                    return;
                } else if (et_proposed_feedback_context.getText().toString().trim().isEmpty()) {
                    ToastUtil.showMessageDefault(this, "请输入反馈内容");
                    return;
                }else{

                    initErrorPage();
                    addIncludeLoading(true);
                    startWhiteLoadingAnim();
                    load();
                }

                break;
            case R.id.rl_edit:
                et_proposed_feedback_context.requestFocus();
                InputMethodManager imm = (InputMethodManager) et_proposed_feedback_context.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                break;
        }

    }

    private void UserFeedBack() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "UserFeedBack");
            obj.put("feedbackContent", et_proposed_feedback_context.getText().toString().trim());
            obj.put("feedbackTitle", et_app_fankui_title.getText().toString().trim());
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("AppFanKuiActivity", "obj=" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Log.e("AppFanKuiActivity","UserFeedBack byts="+new String(bytes));
                String str = new String(bytes);
                try {
                    JSONObject object = new JSONObject(str);
                    String result = (String) object.get("result");
                    if ("0".equals(result)){
                        mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
                    }else {
                        ToastUtil.showMessageDefault(mContext,object.getString("resultNote"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                hideErrorPageState();
                ToastUtil.showMessageDefault(AppFanKuiActivity.this,"网络请求失败了，请重试");
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
                hideErrorPageState();
                ToastUtil.showMessageDefault(AppFanKuiActivity.this,"网络请求失败了，请重试");
            }
        });

    }
}
