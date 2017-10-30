package com.android.app.buystoreapp.managementservice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 订阅管理
 * weilin
 * Created by Administrator on 2016/10/10.
 */
public class SubscriedDetailsActivity extends BaseAct {
    private TextView tv_title;
    private ImageButton iv_back;

    private ImageView iv_user_leve;

    private ListView lv_list;

    private TextView tv_user_name_lin;

    private TextView tv_user_profession;

    private TextView tv_scan_num;

    private TextView tv_user_money;

    private ImageView iv_info_verified;

    private ImageView iv_user_info_credit;

    private ImageView iv_user_info_company;

    private ImageView iv_user_info_profession;

    private ImageView id_home_item_image;

    private TextView tv_attention_button;

    private SubscriptionDetailsBean subscriptionDetailsBean;

    private LinearLayout ll_sub_detauls_title;
    /**
     * 使用的企业头条的adaptyer 布局一样
     */
    private SubscriedDetailsAdapter adapter;

    private List<SubscriptionDetailsBean.GsbuiListBean> list = new ArrayList<SubscriptionDetailsBean.GsbuiListBean>();

    public static final int HANDLE_LOADMORE = 10;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLE_LOADMORE:
                    setTitleData();
                    if (list.size() == 0) {
                        showEmpty("您还没有订阅");
                    } else {
                        ll_sub_detauls_title.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };
    private String userId;
    private String subscribeUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscried_details);
        settitle();
        initView();
        setListener();

        initErrorPage();
        addIncludeLoading(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        subscribeUserId = getIntent().getStringExtra("subscribeUserid");
        startWhiteLoadingAnim();
        load();
    }

    public void load() {
        requestSubscribeDetails();
    }

    /**
     * 网络请求订阅详情数据
     */
    private void requestSubscribeDetails() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getSubscribeByUserId");
            obj.put("thisUserId", userId);
            obj.put("subscribeUserId", subscribeUserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.e("SubscriedDetailsActivity-----------------------------\n" +
                "requestSubscribed param=" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                LogUtils.e("SubscriedDetailsActivity-----------------------------------------------\n " +
                        "requestSubscribed--> result: "
                        + new String(bytes));
                try {
                    Gson gson = new Gson();
                    subscriptionDetailsBean = gson.fromJson(new String(bytes)
                            , new TypeToken<SubscriptionDetailsBean>() {
                            }.getType());
                    String result = subscriptionDetailsBean.getResult();
                    if ("0".equals(result)) {
                        list.clear();
                        list.addAll(subscriptionDetailsBean.getGsbuiList());
                        LogUtils.e("SubscriedDetailsActivity-----------------------------------------------\n" +
                                " list" + list);
                        mHandler.obtainMessage(HANDLE_LOADMORE).sendToTarget();
                    }
                } catch (Exception e) {
                    LogUtils.e("SubscriedDetailsActivity---------------------------------------------------\n " +
                            "e" + e);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }

    /**
     * 初始控件
     */
    private void initView() {
        lv_list = (ListView) findViewById(R.id.lv_list);
        adapter = new SubscriedDetailsAdapter(SubscriedDetailsActivity.this, list);
        lv_list.setAdapter(adapter);
        tv_user_name_lin = (TextView) findViewById(R.id.tv_user_name_lin);
        tv_user_profession = (TextView) findViewById(R.id.tv_user_profession);
        tv_scan_num = (TextView) findViewById(R.id.tv_scan_num);
        tv_user_money = (TextView) findViewById(R.id.tv_user_money);
        iv_info_verified = (ImageView) findViewById(R.id.iv_info_verified);
        iv_user_info_credit = (ImageView) findViewById(R.id.iv_user_info_credit);
        iv_user_info_company = (ImageView) findViewById(R.id.iv_user_info_company);
        iv_user_info_profession = (ImageView) findViewById(R.id.iv_user_info_profession);
        id_home_item_image = (ImageView) findViewById(R.id.id_home_item_image);
        tv_attention_button = (TextView) findViewById(R.id.tv_attention_button);
        ll_sub_detauls_title = (LinearLayout) findViewById(R.id.ll_sub_detauls_title);
        iv_user_leve = (ImageView) findViewById(R.id.iv_user_leve);
    }


    /**
     * 设置监听
     */
    private void setListener() {
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SubscriedDetailsActivity.this, NewInfoActivity.class);
                intent.putExtra("newsId", list.get(position).getNewsId());
                startActivity(intent);
            }
        });
    }


    /**
     * 订阅详情Title设置数据
     */
    private void setTitleData() {
        String iconUrl = subscriptionDetailsBean.getHeadicon().toString();
        if (!TextUtils.isEmpty(iconUrl)) {
            Picasso.with(this).load(iconUrl)
                    .resize(45, 45)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default).into(id_home_item_image);
        }
        tv_user_name_lin.setText(subscriptionDetailsBean.getNickname().toString());
        if (TextUtils.isEmpty(subscriptionDetailsBean.getUserPosition().toString())) {
            tv_user_profession.setText("");
        } else {
            tv_user_profession.setText("・" + subscriptionDetailsBean.getUserPosition().toString());
        }
        if (!"".equals(subscriptionDetailsBean.getUserTreasure())) {
            tv_user_money.setText(subscriptionDetailsBean.getUserTreasure().toString());
        } else {
            tv_user_money.setText("0");
        }
        if (subscriptionDetailsBean.getUserLevelMark() == 1) {
            iv_user_leve.setImageResource(R.drawable.vip_bojin);
        } else if (subscriptionDetailsBean.getUserLevelMark() == 2) {
            iv_user_leve.setImageResource(R.drawable.vip_zuanshi);
        } else if (subscriptionDetailsBean.getUserLevelMark() == 3) {
            iv_user_leve.setImageResource(R.drawable.vip_diwang);
        } else {
            iv_user_leve.setVisibility(View.INVISIBLE);
        }

        if (subscriptionDetailsBean.getBindingMark1() == 1) {
            iv_info_verified.setImageResource(R.drawable.iv_user_info_verified);
        } else {
            iv_info_verified.setVisibility(View.INVISIBLE);
        }
        if (subscriptionDetailsBean.getBindingMark2() == 1) {
            iv_user_info_credit.setImageResource(R.drawable.iv_user_info_credit_light);
        } else {
            iv_user_info_credit.setVisibility(View.INVISIBLE);
        }
        if (subscriptionDetailsBean.getBindingMark3() == 1) {
            iv_user_info_company.setImageResource(R.drawable.iv_user_info_company_light);
        } else {
            iv_user_info_company.setVisibility(View.INVISIBLE);
        }
        if (subscriptionDetailsBean.getBindingMark4() == 1) {
            iv_user_info_profession.setImageResource(R.drawable.iv_user_info_profession_light);
        } else {
            iv_user_info_profession.setVisibility(View.INVISIBLE);
        }

        tv_scan_num.setText(subscriptionDetailsBean.getUserSubscribeNum() + "");

    }

    /**
     * 订阅详情页面标题头设置及返回按钮监听
     */
    private void settitle() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("订阅详情");
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}


