package com.android.app.buystoreapp.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.utils.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class MyWalletActivity extends BaseAct implements OnClickListener {

    private TextView tv_my_wallet_data;
    private Button bt_my_wallet_withdraw, bt_my_wallet_recharge, bt_my_wallet_balance;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        initErrorPage();
        addIncludeLoading(true);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();

        startWhiteLoadingAnim();
        load();
    }

    @Override
    protected void load() {
        super.load();
        getUserMoney();
    }

    private void initView() {
        tv_my_wallet_data = (TextView) findViewById(R.id.tv_my_wallet_data);
        bt_my_wallet_withdraw = (Button) findViewById(R.id.bt_my_wallet_withdraw);
//        bt_my_wallet_recharge = (Button) findViewById(R.id.bt_my_wallet_recharge);
        bt_my_wallet_balance = (Button) findViewById(R.id.bt_my_wallet_balance);
        bt_my_wallet_withdraw.setOnClickListener(this);
//        bt_my_wallet_recharge.setOnClickListener(this);
        bt_my_wallet_balance.setOnClickListener(this);

        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string.my_wallet));
        findViewById(R.id.iv_back).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_my_wallet_withdraw:
                Intent intent1 = new Intent(this, WithDrawActivity.class);
                startActivity(intent1);
                break;
//            case R.id.bt_my_wallet_recharge:
//                Intent intent2 = new Intent(this,RechargeActivity.class);
//                startActivity(intent2);
//                break;
            case R.id.bt_my_wallet_balance:
                Intent intent3 = new Intent(this, BalanceDetailActivity.class);
                startActivity(intent3);
                break;
            case R.id.iv_back:
                this.finish();
                break;
        }
    }


    public void getUserMoney() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getUserMoney");
            obj.put("thisUserId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("查询帐号余额提交数据 obj == ", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("查询帐号余额返回数据 bytes==", new String(bytes));
                stopLoadingAnim();
                hideErrorPageState();
                try {
                    JSONObject object = new JSONObject(new String(bytes));
                    String result = object.getString("result");
                    if ("0".equals(result)) {
                        double totalMoney = object.getDouble("totalMoney");
                        tv_my_wallet_data.setText("￥" + totalMoney);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(MyWalletActivity.this, getResources().getString(R.string.service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }
}
