package com.android.app.buystoreapp;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystoreapp.managementservice.RapidlyBean;
import com.squareup.picasso.Picasso;

public class ContactActivity extends Activity implements OnClickListener, RadioGroup.OnCheckedChangeListener {
    private RapidlyBean.FastChatListBean aboutus;
    private TextView info;
    private RadioGroup group;
    private Drawable drawable;
    private ImageView sendSms;
    private ImageView head;
    private String headIcon;
    private TextView name;
    private String userName;
    private TextView work;
    private ImageView card1;
    private ImageView card2;
    private String proName;
//    private LinearLayout front;
//    private LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact);
//        getChatInfo();
        Init();
    }

    private void Init() {
        aboutus = (RapidlyBean.FastChatListBean) getIntent().getSerializableExtra("aboutus");
        headIcon = getIntent().getStringExtra("headIcon");
        userName = getIntent().getStringExtra("userName");
        proName = getIntent().getStringExtra("proName");
//		((TextView)findViewById(R.id.phoneNum)).setText(aboutus.getPhone());
//		((TextView)findViewById(R.id.wechatNum)).setText(aboutus.getWeixin());
//		((TextView)findViewById(R.id.qqNum)).setText(aboutus.getQq());
//		((TextView)findViewById(R.id.webaddress)).setText(aboutus.getPcAddress());
//		((TextView)findViewById(R.id.IOSaddress)).setText(aboutus.getIosAddress());
//		((TextView)findViewById(R.id.Androidaddress)).setText(aboutus.getAndroidAddress());
//		((TextView)findViewById(R.id.Remark)).setText(aboutus.getRemark());
        findViewById(R.id.iv_back).setOnClickListener(this);
        ((TextView)findViewById(R.id.tv_title)).setText(getResources().getString(R.string.speed_chat_title));
//        front = (LinearLayout) findViewById(R.id.ll_card_front);
//        back = (LinearLayout) findViewById(R.id.ll_card_back);
//        front.setEnabled(false);
//        back.setEnabled(true);
//        front.setOnClickListener(this);
//        back.setOnClickListener(this);
        sendSms = (ImageView) findViewById(R.id.iv_speed_send_sms);
        info = (TextView) findViewById(R.id.tv_speed_user_info);
        info.setText(aboutus.getPhone());
        info.setCompoundDrawables(null, null, drawable, null);
        info.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Phone(aboutus.getPhone());
            }
        });
        group = (RadioGroup) findViewById(R.id.rg_speed_group);
        head = (ImageView) findViewById(R.id.iv_speed_user_header);
        name = (TextView) findViewById(R.id.tv_speed_user_name);
        work = (TextView) findViewById(R.id.tv_speed_user_work);
        card1 = (ImageView) findViewById(R.id.iv_chat_1);
        card2 = (ImageView) findViewById(R.id.iv_chat_2);
        Picasso.with(this).load(aboutus.getImgList().get(0).getWebrootpath()).placeholder(R.drawable.ic_default).into(card1);
        Picasso.with(this).load(aboutus.getImgList().get(1).getWebrootpath()).placeholder(R.drawable.ic_default).into(card2);
        work.setText(aboutus.getNote());
        name.setText(userName);
        Picasso.with(this).load(headIcon)
                .resize(80,80)
                .placeholder(R.drawable.ic_default).into(head);
        sendSms.setOnClickListener(this);
        group.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        String phone = aboutus.getPhone();
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_speed_send_sms:
                String msgBody = "您好，十万火急，我对您在Boss团上发布的“" + proName + "”非常的感兴趣，等待回复";
                doSendSMSTo(phone, msgBody);
                break;
           /* case R.id.ll_card_back:
                System.out.println("点击1");

                back.bringToFront();//图层置顶
                AnimationSet animationSet = new AnimationSet(true);
                TranslateAnimation translateAnimation = new TranslateAnimation(1, 0f, 1, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.5f);
                translateAnimation.setDuration(1000);
                animationSet.addAnimation(translateAnimation);
                front.startAnimation(animationSet);
                animationSet.setFillAfter(true);

                AnimationSet a2 = new AnimationSet(true);
                TranslateAnimation t2 = new TranslateAnimation(1, 0f, 1, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f);
                t2.setDuration(1000);
                a2.addAnimation(t2);
                back.startAnimation(a2);
//                a2.setFillAfter(true);

                front.setEnabled(true);
                back.setEnabled(false);
                break;
            case R.id.ll_card_front:
                System.out.println("点击2");
                back.bringToFront();//图层置顶
                AnimationSet a3 = new AnimationSet(true);
                TranslateAnimation t3 = new TranslateAnimation(1, 0f, 1, 0f, Animation.RELATIVE_TO_SELF, -0.5f, Animation.RELATIVE_TO_SELF, 0f);
                t3.setDuration(1000);
                a3.addAnimation(t3);
                front.startAnimation(a3);
                a3.setFillAfter(true);

                AnimationSet a4 = new AnimationSet(true);
                TranslateAnimation t4 = new TranslateAnimation(1, 0f, 1, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f);
                t4.setDuration(1000);
                a4.addAnimation(t4);
                back.startAnimation(a4);
//                a4.setFillAfter(true);

                front.setEnabled(false);
                back.setEnabled(true);
                break;*/
        }
    }

    private void Phone(String phone) {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                + phone.replaceAll("-", "")));
        // 开始处理意图 执行
        startActivity(i);
    }

    @SuppressWarnings("deprecation")
    private void Copy(String str) {
        ClipboardManager cmb = (ClipboardManager) this
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(str.trim());
        Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 调起系统发短信功能
     *
     * @param phoneNumber
     * @param message
     */
    public void doSendSMSTo(String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (group.getCheckedRadioButtonId()) {
            case R.id.mobile:
                info.setText(aboutus.getPhone());
                drawable = getResources()
                        .getDrawable(R.drawable.ic_speed_chat_call);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());
               /* info.setCompoundDrawables(null, null, drawable, null);
                info.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Phone(aboutus.getPhone());
                    }
                });*/
                break;
            case R.id.tele:
                info.setText(aboutus.getTell());
                drawable = getResources()
                        .getDrawable(R.drawable.ic_speed_chat_call);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());
                info.setCompoundDrawables(null, null, drawable, null);
                info.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Phone(aboutus.getTell());
                    }
                });
                break;
            case R.id.email:
                info.setText(aboutus.getEmail());
                drawable = getResources()
                        .getDrawable(R.drawable.ic_speed_chat_copy);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());
                info.setCompoundDrawables(null, null, drawable, null);
                info.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Copy(aboutus.getEmail());
                    }
                });
                break;
            case R.id.wchat:
                info.setText(aboutus.getWeChat());
                drawable = getResources()
                        .getDrawable(R.drawable.ic_speed_chat_copy);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());
                info.setCompoundDrawables(null, null, drawable, null);
                info.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Copy(aboutus.getWeChat());
                    }
                });
                break;
            case R.id.qq:
                info.setText(aboutus.getQq());
                drawable = getResources()
                        .getDrawable(R.drawable.ic_speed_chat_copy);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());
                info.setCompoundDrawables(null, null, drawable, null);
                info.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Copy(aboutus.getQq());
                    }
                });
                break;
        }
    }
}
