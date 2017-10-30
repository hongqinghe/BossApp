package com.android.app.buystoreapp;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.adapter.BossSecuritiesAdapter;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.BossListBean;
import com.android.app.buystoreapp.bean.BossSecuritiesBran;
import com.android.app.buystoreapp.bean.BuyBossBean;
import com.android.app.buystoreapp.bean.BuyBossListBean;
import com.android.app.buystoreapp.managementservice.BuyBossAdapter;
import com.android.app.buystoreapp.managementservice.SubscribeActivity;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MyBossTicketDetail extends BaseAct implements OnClickListener {

    private String mCurrentUserName;
    private String count = "0";

    private TextView TicketCount, usehistory, buyhistory;
    private Button BuyBtn;
    private ListView lv_bossTicket;
    private View forst_line;

    private int linewidth = 0;
    private int current_index = 0;
    private String userId;
    public static int isChangePhone;

    private List<BuyBossListBean> buyList = new ArrayList<>();
    private List<BossListBean> securitiesList = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SubscribeActivity.HANDLE_LOADMORE:
                    TicketCount.setText(count);
                    buyBossAdapter.notifyDataSetChanged();

                    break;
                case BOSSSECURITES_SUCCESS:
                    TicketCount.setText(count);
                    securitiesAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private BossSecuritiesAdapter securitiesAdapter;
    private BuyBossAdapter buyBossAdapter;
    public static final int BOSSSECURITES_SUCCESS = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_boss_ticket_detail);
        mCurrentUserName = SharedPreferenceUtils.getCurrentUserInfo(this)
                .getUserName();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        isChangePhone = getIntent().getIntExtra("isChangePhone",0);
        initView();
        initLine();
        addIncludeLoading(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        count = SharedPreferenceUtils.getBossTicketCount(mContext);
        TicketCount.setText("" + count);
        load();
    }

    @Override
    protected void load() {
        super.load();

        startWhiteLoadingAnim();
        getBuyBossRecord();
    }


    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string.boss_quan));
        findViewById(R.id.iv_back).setOnClickListener(this);
        count = SharedPreferenceUtils.getBossTicketCount(this);

        TicketCount = (TextView) findViewById(R.id.TicketCount);
        TicketCount.setText(count + "");

        BuyBtn = (Button) findViewById(R.id.BuyBtn);
        usehistory = (TextView) findViewById(R.id.usehistory);
        buyhistory = (TextView) findViewById(R.id.buyhistory);
        lv_bossTicket = (ListView) findViewById(R.id.lv_bossTicket);
        forst_line = findViewById(R.id.forst_line);

        BuyBtn.setOnClickListener(this);
        usehistory.setOnClickListener(this);
        buyhistory.setOnClickListener(this);

        buyBossAdapter = new BuyBossAdapter(this, buyList);
        lv_bossTicket.setAdapter(buyBossAdapter);


    }


    /**
     * 初始化页签下划线
     */
    private void initLine() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //获取屏幕宽度
        int screenWidth = dm.widthPixels;
        Matrix matrix = new Matrix();
        linewidth = screenWidth / 2;
        matrix.postTranslate(0, 0);
    }

    /**
     * 页签下划线移动动画
     *
     * @param index
     */
    private void anim(int index) {
        Animation animation = new TranslateAnimation(linewidth * current_index, linewidth * index, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(200);
        forst_line.startAnimation(animation);
        current_index = index;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.usehistory:
                buyhistory.setTextColor(getResources().getColor(R.color.ticket_text_hui));
                usehistory.setTextColor(getResources().getColor(R.color.bill_text_lv));
                anim(1);
                getBossSecuritiesRecord();
                securitiesAdapter = new BossSecuritiesAdapter(this, securitiesList);
                lv_bossTicket.setAdapter(securitiesAdapter);
                break;
            case R.id.buyhistory:
                buyhistory.setTextColor(getResources().getColor(R.color.bill_text_lv));
                usehistory.setTextColor(getResources().getColor(R.color.ticket_text_hui));
                anim(0);
                load();
                buyBossAdapter = new BuyBossAdapter(this, buyList);
                lv_bossTicket.setAdapter(buyBossAdapter);
                break;
            case R.id.BuyBtn:
                Intent intent = new Intent(this, BuyTicketActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_back:
                this.finish();
                break;
            default:
                break;
        }
    }

    /**
     * 查询Boss卷购买记录
     */
    public void getBuyBossRecord() {


        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getBuyBossRecord");
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("查询Boss卷购买记录提交obj", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                Log.e("查询Boss卷购买记录返回数据bytes", new String(bytes));
                stopLoadingAnim();
                Gson gson = new Gson();
                BuyBossBean buyBossBean = gson.fromJson(new String(bytes), new TypeToken<BuyBossBean>() {
                }.getType());
                count = buyBossBean.getBossTicket() + "";
                String result = buyBossBean.getResult();
                if ("0".equals(result)) {
                    securitiesList.clear();
                    buyList.clear();
                    buyList.addAll(buyBossBean.getBuyBossList());
                    mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });


    }

    /**
     * 查看Boss券使用记录
     */
    public void getBossSecuritiesRecord() {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getBossSecuritiesRecord");
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("查询Boss卷使用记录提交obj", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                Log.e("查询Boss卷使用记录返回数据bytes", new String(bytes));
                stopLoadingAnim();
                Gson gson = new Gson();
                BossSecuritiesBran bossSecuritiesBran = gson.fromJson(new String(bytes), new TypeToken<BossSecuritiesBran>() {
                }.getType());
                String result = bossSecuritiesBran.getResult();
                if ("0".equals(result)) {
                    count = String.valueOf(bossSecuritiesBran.getBossTicket());
                    buyList.clear();
                    securitiesList.clear();
                    securitiesList.addAll(bossSecuritiesBran.getBossList());
                    mHandler.obtainMessage(BOSSSECURITES_SUCCESS).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });


    }

}
