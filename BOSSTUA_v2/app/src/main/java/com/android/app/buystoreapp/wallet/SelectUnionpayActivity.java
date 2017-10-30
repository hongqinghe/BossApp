package com.android.app.buystoreapp.wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

public class SelectUnionpayActivity extends Activity implements View.OnClickListener {

    private TextView tv_add_unionpay;
    private ListView lv_unionpay_info;
    private Button btn_confirm;
    private List<UnionpayInfoBeen> datas;

    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_select_unionpay);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_action_bar);
        ViewUtils.inject(this);
        mTitleText.setText("选择银行卡");
        initDada();
        initView();
    }

    private void initDada() {
        datas = new ArrayList<UnionpayInfoBeen>();
        for (int i = 1;i<6;i++){
            UnionpayInfoBeen data = new UnionpayInfoBeen();
            data.setUnionpayName("中国银行(000"+i+")");
            data.setOther("提现至中国银行，手续费0.1%");
            datas.add(data);
        }
    }

    private void initView() {
        tv_add_unionpay = (TextView) findViewById(R.id.tv_add_unionpay);
        lv_unionpay_info = (ListView) findViewById(R.id.lv_unionpay_info);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        tv_add_unionpay.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        UnionpayListAdapter adapter = new UnionpayListAdapter(this, datas);
        lv_unionpay_info.setAdapter(adapter);
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
            case R.id.tv_add_unionpay:
                Intent intent1 = new Intent(this, AddUnionpayActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_confirm:
                finish();
                String a = null;
                String b = null;
                for (UnionpayInfoBeen item : datas){
                    if (item.isCheck() == true){
                        a = item.getUnionpayName();
                        b = item.getOther();
                    }
                }
                ToastUtil.showMessageDefault(this, "选择成功:\n"+a+"\n"+b);
                break;
        }
    }
}
