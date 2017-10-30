package com.android.app.buystoreapp.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class RechargeActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_recharge_wechat, rl_recharge_alipay, rl_recharge_unionpay;
    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_recharge);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_action_bar);
        ViewUtils.inject(this);
        mTitleText.setText("余额充值");
        initView();


    }

    private void initView() {
        rl_recharge_wechat = (RelativeLayout) findViewById(R.id.rl_recharge_wechat);
        rl_recharge_alipay = (RelativeLayout) findViewById(R.id.rl_recharge_alipay);
        rl_recharge_unionpay = (RelativeLayout) findViewById(R.id.rl_recharge_unionpay);
        rl_recharge_wechat.setOnClickListener(this);
        rl_recharge_alipay.setOnClickListener(this);
        rl_recharge_unionpay.setOnClickListener(this);
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
            case R.id.rl_recharge_wechat:
                ToastUtil.showMessageCenter(this, "跳转到微信支付界面");
                break;
            case R.id.rl_recharge_alipay:
                ToastUtil.showMessageCenter(this, "跳转到支付宝支付界面");
                break;
            case R.id.rl_recharge_unionpay:
                ToastUtil.showMessageCenter(this, "跳转到银联支付界面");
//                Intent intent = new Intent(this, AreaActivity.class);
//                startActivity(intent);
                break;
        }
    }
}
