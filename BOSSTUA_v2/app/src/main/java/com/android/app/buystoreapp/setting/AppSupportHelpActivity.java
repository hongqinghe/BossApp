package com.android.app.buystoreapp.setting;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystoreapp.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AppSupportHelpActivity extends Activity implements View.OnClickListener {
    private static final String PHONE_FORMAT = "%1$s";
    private static final String QQ_FORMAT = "%1$s";
    private static final String EMAIL_FORMAT = "%1$s";

    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    @ViewInject(R.id.id_support_phone)
    private TextView mPhoneText;
    @ViewInject(R.id.id_support_qq)
    private TextView mQqText;
    @ViewInject(R.id.id_support_email)
    private TextView mEmailText;
    @ViewInject(R.id.ib_copy_phone_number)
    private ImageButton ib_copy_phone_number;
    @ViewInject(R.id.ib_copy_qq)
    private ImageButton ib_copy_qq;
    @ViewInject(R.id.ib_copy_email)
    private ImageButton ib_copy_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.app_support_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_action_bar);
        ViewUtils.inject(this);
        mTitleText.setText("联系客服");
        setListener();
    }

    private void setListener() {
        ib_copy_phone_number.setOnClickListener(this);
        ib_copy_qq.setOnClickListener(this);
        ib_copy_email.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String phone = String.format(PHONE_FORMAT, "010-57193001");

        mPhoneText.setText(phone);


        String qq = String.format(QQ_FORMAT, "616662032");

        mQqText.setText(qq);

        String email = String.format(EMAIL_FORMAT, "bosstuan@bosstuan.cn");

        mEmailText.setText(email);
    }

    @OnClick(R.id.id_custom_back_image)
    public void onCustomBarBackClicked(View v) {
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_copy_phone_number:
                Intent intentnumber = new Intent(Intent.ACTION_DIAL);
                intentnumber.setData(Uri.parse("tel:" + mPhoneText.getText().toString()));
                startActivity(intentnumber);
                break;
            case R.id.ib_copy_qq:
                copy(mQqText.getText().toString(),this);
                Toast.makeText(this,"复制成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_copy_email:
                copy(mEmailText.getText().toString(),this);
                Toast.makeText(this,"复制成功",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public static void copy(String content, Context context) {
// 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());


    }
}
