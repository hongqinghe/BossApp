package com.android.app.buystoreapp.managementservice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.BossBuyActivity;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.GuessLikeBean;
import com.android.app.buystoreapp.goods.ShopDetailInfoActivity;
import com.android.app.buystoreapp.order.MyOrderActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaySuccessActivity extends BaseAct implements View.OnClickListener {
    private ImageButton ib_paySccess_back;
    private TextView tv_pay_hint;
    private Button btn_look_order;
    private Button btn_back_home;
    private GridView gv_recommend;
    private MyGalleryOtherAdapter mAdapter;
    private List<GuessLikeBean.LikeProBean> mDatas = new ArrayList<>();
    private String userId;
    private List<String> proName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        initView();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        proName = (List<String>) getIntent().getSerializableExtra("proName");
        for (int i = 0; i < proName.size(); i++) {
            getGases(proName.get(i));
        }
    }

    private void initView() {
        ib_paySccess_back = (ImageButton) findViewById(R.id.ib_paySccess_back);
        btn_look_order = (Button) findViewById(R.id.btn_look_order);
        btn_back_home = (Button) findViewById(R.id.btn_back_home);
        tv_pay_hint = (TextView) findViewById(R.id.tv_pay_hint);
        gv_recommend = (GridView) findViewById(R.id.gv_recommend);
        gv_recommend.setFocusable(false);
        ib_paySccess_back.setOnClickListener(this);
        btn_look_order.setOnClickListener(this);
        btn_back_home.setOnClickListener(this);

        mAdapter = new MyGalleryOtherAdapter(this, mDatas);
        gv_recommend.setAdapter(mAdapter);

        gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToastUtil.showMessageDefault(PaySuccessActivity.this, i + "");
                Intent intent = new Intent(mContext, ShopDetailInfoActivity.class);
                intent.putExtra("proId", mDatas.get(i).getProId());
                intent.putExtra("proName", mDatas.get(i).getProName());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_paySccess_back:
                finish();
                break;

            case R.id.btn_look_order:
                Intent intent = new Intent(PaySuccessActivity.this, MyOrderActivity.class);
                intent.putExtra("index", 2);
                intent.putExtra("userStatus", 0);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_back_home:
                startActivity(new Intent(PaySuccessActivity.this,
                        BossBuyActivity.class));
                finish();
                break;
        }
    }

    /**
     * 猜你喜欢接口
     *
     * @param proName 当前商品名
     */
    private void getGases(String proName) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "GuessYouLike");
            obj.put("proName", proName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("shang", "猜你喜欢返回值--" + new String(bytes));
                Gson gson = new Gson();
                GuessLikeBean gsonBack = gson.fromJson(new String(bytes), new
                        TypeToken<GuessLikeBean>() {
                        }.getType());
                String result = gsonBack.getResult();
                String resultNote = gsonBack
                        .getResultNote();
                if (result.equals("0")) {
                    if (gsonBack.getLikePro() != null) {
                        mDatas.addAll(gsonBack.getLikePro());
                    }
                    mAdapter.notifyDataSetChanged();

                } else {
                    ToastUtil.showMessageDefault(mContext, resultNote);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
            }
        });
    }
}
