package com.android.app.buystoreapp.wallet;

import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.BilDetailsBean;
import com.android.app.buystoreapp.bean.BillDataBean;
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

/**
 * 账单明细
 * Created by 尚帅波 on 2016/9/18.
 */
public class BalanceDetailActivity extends BaseAct implements View.OnClickListener {
    private TextView tv_balance_detail_all, tv_balance_detail_income, tv_balance_detail_cost;
    private View line_balance_detail_all, line_balance_detail_income, line_balance_detail_cost;
    private ViewPager pager_balance_detail;
    private static final int TAB_COUNT = 3;
    private int current_index = 0;
    private static int linewidth = 0;

    private int flag = 0;

    private List<BilDetailsBean> allList = new ArrayList<BilDetailsBean>();

    private String userId;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SubscribeActivity.HANDLE_LOADMORE:
                    switch (flag) {
                        case 0:
                            if (BillAllFragment.adapter != null) {
                                BillAllFragment.adapter.notifyDataSetChanged();
                            }
                            break;
                        case 1:
                            if (BillIntoFragment.adapter != null) {
                                BillIntoFragment.adapter.notifyDataSetChanged();
                            }

                            break;
                        case 2:
                            if (BillCostFragment.adapter != null) {
                                BillCostFragment.adapter.notifyDataSetChanged();
                            }
                            break;
                    }


                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_detail);
        initView();
        initLine();
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        load();
    }

    @Override
    protected void load() {
        selBilDetailsAll(flag);
    }


    private void selBilDetailsAll(int i) {

        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "selBilDetailsAll");
            obj.put("userId", userId);
            obj.put("state", i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("账单明细全部提交数据 obj==", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                hideErrorPageState();
                stopLoadingAnim();
                Log.e("账单明细全部返回数据 bytes==", new String(bytes));
                Gson gson = new Gson();
                BillDataBean billDataBean = gson.fromJson(new String(bytes), new TypeToken<BillDataBean>() {
                }.getType());
                String result = billDataBean.getResult();
                if ("0".equals(result)) {
                    allList.clear();
                    allList.addAll(billDataBean.getBilDetails());
                    mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(BalanceDetailActivity.this, getResources().getString(R.string.service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                ToastUtil.showMessageDefault(BalanceDetailActivity.this, getResources().getString(R.string.service_error_hint));
            }
        });
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
        linewidth = screenWidth / TAB_COUNT;
        matrix.postTranslate(0, 0);
    }

    /**
     * 页签下划线移动动画
     *
     * @param index
     */
    private void anim(int index) {
        int one = linewidth;
        Animation animation = new TranslateAnimation(one * current_index, one * index, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(200);
        line_balance_detail_all.startAnimation(animation);
        current_index = index;
    }

    private void initView() {
        tv_balance_detail_all = (TextView) findViewById(R.id.tv_balance_detail_all);
        tv_balance_detail_income = (TextView) findViewById(R.id.tv_balance_detail_income);
        tv_balance_detail_cost = (TextView) findViewById(R.id.tv_balance_detail_cost);
        line_balance_detail_all = findViewById(R.id.line_balance_detail_all);
        line_balance_detail_income = findViewById(R.id.line_balance_detail_income);
        line_balance_detail_cost = findViewById(R.id.line_balance_detail_cost);
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string.zd_mx));
        findViewById(R.id.iv_back).setOnClickListener(this);
        tv_balance_detail_all.setOnClickListener(this);
        tv_balance_detail_income.setOnClickListener(this);
        tv_balance_detail_cost.setOnClickListener(this);
        pager_balance_detail = (ViewPager) findViewById(R.id.pager_balance_detail);
        pager_balance_detail.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new BillAllFragment(allList);
                    case 1:
                        return new BillIntoFragment(allList);
                    case 2:
                        return new BillCostFragment(allList);
                }
                return null;
            }

            @Override
            public int getCount() {
                return TAB_COUNT;
            }
        });

        pager_balance_detail.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
                switch (position) {
                    case 0:
                        if (positionOffset > 0) {
                            BillIntoFragment.adapter.notifyDataSetChanged();
                        }
                        break;
                    case 1:
                        if (positionOffset > 0) {
                            BillCostFragment.adapter.notifyDataSetChanged();
                        }
                        if (positionOffset < 1) {
                            BillAllFragment.adapter.notifyDataSetChanged();
                        }
                        break;
                    case 2:
                        if (positionOffset < 1) {
                            BillIntoFragment.adapter.notifyDataSetChanged();
                        }
                        break;


                }


            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        anim(position);
                        tv_balance_detail_all.setTextColor(getResources().getColor(R.color
                                .bill_text_lv));
                        tv_balance_detail_income.setTextColor(getResources().getColor(R.color
                                .bill_text_hui));
                        tv_balance_detail_cost.setTextColor(getResources().getColor(R.color
                                .bill_text_hui));
                        //line_balance_detail_all.setVisibility(View.VISIBLE);
                        //line_balance_detail_income.setVisibility(View.INVISIBLE);
                        //line_balance_detail_cost.setVisibility(View.INVISIBLE);
                        flag = 0;
                        selBilDetailsAll(flag);
                        break;
                    case 1:
                        anim(position);
                        tv_balance_detail_all.setTextColor(getResources().getColor(R.color
                                .bill_text_hui));
                        tv_balance_detail_income.setTextColor(getResources().getColor(R.color
                                .bill_text_lv));
                        tv_balance_detail_cost.setTextColor(getResources().getColor(R.color
                                .bill_text_hui));
                        //line_balance_detail_all.setVisibility(View.INVISIBLE);
                        //line_balance_detail_income.setVisibility(View.VISIBLE);
                        //line_balance_detail_cost.setVisibility(View.INVISIBLE);
                        flag = 1;
                        selBilDetailsAll(flag);
                        break;
                    case 2:
                        anim(position);
                        tv_balance_detail_all.setTextColor(getResources().getColor(R.color
                                .bill_text_hui));
                        tv_balance_detail_income.setTextColor(getResources().getColor(R.color
                                .bill_text_hui));
                        tv_balance_detail_cost.setTextColor(getResources().getColor(R.color
                                .bill_text_lv));
                        //line_balance_detail_all.setVisibility(View.INVISIBLE);
                        //line_balance_detail_income.setVisibility(View.INVISIBLE);
                        //line_balance_detail_cost.setVisibility(View.VISIBLE);
                        flag = 2;
                        selBilDetailsAll(flag);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_balance_detail_all:
                pager_balance_detail.setCurrentItem(0);
                flag = 0;
                break;
            case R.id.tv_balance_detail_income:
                pager_balance_detail.setCurrentItem(1);
                flag = 1;
                break;
            case R.id.tv_balance_detail_cost:
                pager_balance_detail.setCurrentItem(2);
                flag = 2;
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
