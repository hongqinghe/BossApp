package com.android.app.buystoreapp.managementservice;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.MyMoreBean;
import com.android.app.buystoreapp.bean.UserAllProductBean;
import com.android.app.buystoreapp.bean.UserAndProManagerBean;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.buystoreapp.goods.ShopDetailInfoActivity;
import com.android.app.buystoreapp.im.ChatActivity;
import com.android.app.buystoreapp.im.Constant;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.buystoreapp.widget.RoundImageView;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.view.SwipeRefreshLayoutUpDown;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的更多资源・服务
 * <p/>
 * 魏林编写
 */
public class MymoreResourcesandServiceMainActivity extends BaseAct implements View.OnClickListener,
        SwipeRefreshLayoutUpDown.OnLoadListener, SwipeRefreshLayoutUpDown.OnRefreshListener,
        AdapterView.OnItemClickListener, MymoreResourcesandServiceAdapter.OnMyMoreClick {


    private MymoreResourcesandServiceAdapter adapter;
    private List<UserAllProductBean> allProductList = new
            ArrayList<UserAllProductBean>();
    private UserAndProManagerBean userAndPro;

    private TextView tv_company;

    private LinearLayout ll_sub_detauls_title;
    private RoundImageView id_home_item_image;  //用户头像
    private TextView tv_user_name_lin;  //姓名
    private TextView tv_user_profession;  //职业
    private TextView tv_user_money;  //钱包钱数
    private TextView tv_scan_num;  //关注数量
    private TextView tv_attention_button;  //关注/取消关注
    private ImageView iv_user_leve;  //等级
    private ImageView iv_info_verified;  //  实名认证
    private ImageView iv_user_info_credit;   //芝麻信用
    private ImageView iv_user_info_company;  //企业认证
    private ImageView iv_user_info_profession;  //职业认证
    private ListView lv_list;   //list列表
    private SwipeRefreshLayoutUpDown swipe;

    private String proUserId;  //发布商品人的Id
    private String thisUserId;  //当前用户Id
    private int nowPage = 1;  //当前页
    private int totaPage;
    private int attentionIsOff;//是否关注
    private Dialog dialog;

    private static final int LOAD_END = 100;  //加载结束
    private static final int LOAD_CANCLE = 101;  //加载取消
    private static final int REFRESH_END = 102;  //刷新结束
    private static final int REFRESH_CANCLE = 103;  //刷新取消


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymore_resourcesand_service_main);
//        mTitleText.setText("我的更多资源・服务");

        proUserId = getIntent().getStringExtra("proUserId");
        thisUserId = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
        initView();

        initErrorPage();
        addIncludeLoading(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_END:
                    if (!TextUtils.isEmpty(userAndPro.getHeadicon())) {
                        Picasso.with(mContext)
                                .load(userAndPro.getHeadicon())
                                //.resize(45, 45)
                                .placeholder(R.drawable.ic_default)
                                .error(R.drawable.ic_default).into(id_home_item_image);
                    }
                    if (!TextUtils.isEmpty(userAndPro.getCompanyBrand())) {
                        tv_company.setText(userAndPro.getCompanyBrand());
                    } else {
                        tv_company.setVisibility(View.GONE);
                    }
                    tv_user_name_lin.setText(userAndPro.getNickname());
                    if (!TextUtils.isEmpty(userAndPro.getUserPosition())) {
                        tv_user_profession.setText("・" + userAndPro.getUserPosition());
                    } else {
                        tv_user_profession.setText("");
                    }
                    if ("".equals(userAndPro.getUserTreasure())) {
                        tv_user_money.setText("0");
                    } else {
                        tv_user_money.setText(userAndPro.getUserTreasure());
                    }
                    tv_scan_num.setText(userAndPro.getUserAttentionNum());
                    String level = userAndPro.getUserLevelMark();
                    if (level.equals("0")) {
                        iv_user_leve.setVisibility(View.GONE);
                    } else if (level.equals("1")) {
                        Picasso.with(mContext).load(R.drawable.vip_bojin).into(iv_user_leve);
                    } else if (level.equals("2")) {
                        Picasso.with(mContext).load(R.drawable.vip_zuanshi).into(iv_user_leve);
                    } else if (level.equals("3")) {
                        Picasso.with(mContext).load(R.drawable.vip_diwang).into(iv_user_leve);
                    }
                    if (userAndPro.getBindingMark1().equals("0")) {
                        iv_info_verified.setVisibility(View.GONE);
                    }
                    if (userAndPro.getBindingMark2().equals("0")) {
                        iv_user_info_credit.setVisibility(View.GONE);
                    }
                    if (userAndPro.getBindingMark3().equals("0")) {
                        iv_user_info_company.setVisibility(View.GONE);
                    }
                    if (userAndPro.getBindingMark4().equals("0")) {
                        iv_user_info_profession.setVisibility(View.GONE);
                    }
                    if (attentionIsOff == 1) {
                        tv_attention_button.setText("取消关注");
                        tv_attention_button.setTextColor(getResources().getColor(R.color.c_515151));
                        tv_attention_button.setBackgroundResource(R.drawable.corners_f1f1f1_bg);
                    } else if (attentionIsOff == 0) {
                        tv_attention_button.setText("+关注");
                        tv_attention_button.setTextColor(getResources().getColor(R.color.c_7f7f7f));
                        tv_attention_button.setBackgroundResource(R.drawable.corners_orange_bg);
                    }
                    swipe.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                    break;
                case REFRESH_CANCLE:
                    swipe.setRefreshing(false);
                    break;

                case LOAD_END:
                    swipe.setLoading(false);
                    adapter.notifyDataSetChanged();
                    break;
                case LOAD_CANCLE:
                    swipe.setLoading(false);
                    break;
            }
        }
    };

    private void initView() {
        ll_sub_detauls_title = (LinearLayout) findViewById(R.id.ll_sub_detauls_title);
        tv_company = (TextView) findViewById(R.id.tv_company);
        tv_user_name_lin = (TextView) findViewById(R.id.tv_user_name_lin);
        tv_user_profession = (TextView) findViewById(R.id.tv_user_profession);
        tv_user_money = (TextView) findViewById(R.id.tv_user_money);
        tv_scan_num = (TextView) findViewById(R.id.tv_scan_num);
        tv_attention_button = (TextView) findViewById(R.id.tv_attention_button);
        tv_attention_button.setOnClickListener(this);

        id_home_item_image = (RoundImageView) findViewById(R.id.id_home_item_image);
        iv_user_leve = (ImageView) findViewById(R.id.iv_user_leve);
        iv_info_verified = (ImageView) findViewById(R.id.iv_info_verified);
        iv_user_info_credit = (ImageView) findViewById(R.id.iv_user_info_credit);
        iv_user_info_company = (ImageView) findViewById(R.id.iv_user_info_company);
        iv_user_info_profession = (ImageView) findViewById(R.id.iv_user_info_profession);
        findViewById(R.id.tv_chat).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string
                .more_resourcesand_service));
        findViewById(R.id.iv_back).setOnClickListener(this);

        lv_list = (ListView) findViewById(R.id.lv_list);
        adapter = new MymoreResourcesandServiceAdapter(mContext, allProductList);
        adapter.setOnMyMoreClick(this);
        lv_list.setAdapter(adapter);
        lv_list.setOnItemClickListener(this);


        swipe = (SwipeRefreshLayoutUpDown) findViewById(R.id.myMore_list_container);
        swipe.setOnLoadListener(this);
        swipe.setOnRefreshListener(this);
        swipe.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light, R.color.holo_orange_light,
                R.color.holo_red_light);
        swipe.setLoadNoFull(false);
    }

    @Override
    protected void load() {
        super.load();
        startWhiteLoadingAnim();

        String proLong = "" + CrashApplication.longitude;
        String userLat = "" + CrashApplication.latitude;
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "myAllProduct");
            obj.put("proUserId", proUserId);
            obj.put("thisUserId", thisUserId);
            obj.put("proLong", proLong);
            obj.put("userLat", userLat);
            obj.put("nowPage", nowPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("我的更多服务资源返回数据", new String(bytes));
                hideErrorPageState();
                stopLoadingAnim();
                Gson gson = new Gson();
                MyMoreBean myMoreBean = gson.fromJson(new String(bytes), new
                        TypeToken<MyMoreBean>() {
                        }.getType());
                String result = myMoreBean.getResult();
                Log.e("result", new String(bytes));
                if (result.equals("0")) {
                    ll_sub_detauls_title.setVisibility(View.VISIBLE);
                    totaPage = myMoreBean.getTotaPage();
                    userAndPro = myMoreBean.getUserAndProManager().get(0);
                    attentionIsOff = userAndPro.getAttentionIsOff();
                    allProductList.clear();
                    allProductList.addAll(userAndPro.getUserAllProduct());
                    handler.sendEmptyMessage(REFRESH_END);
                } else {
                    if ("1".equals(result)) {
                        userAndPro = myMoreBean.getUserAndProManager().get(0);
                        handler.obtainMessage(REFRESH_END).sendToTarget();
                    }
                    handler.sendEmptyMessage(REFRESH_CANCLE);
                    ToastUtil.showMessageDefault(mContext, myMoreBean.getResultNote());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                handler.sendEmptyMessage(REFRESH_CANCLE);
                ToastUtil.showMessageDefault(mContext, "数据获取失败");
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
            }
        });
    }


    private void moreLoad() {
        startWhiteLoadingAnim();
        String thisUserId = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
        String proLong = SharedPreferenceUtils.getCurrentCityInfo(mContext).getCityLong();
        String userLat = SharedPreferenceUtils.getCurrentCityInfo(mContext).getCityLat();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "myAllProduct");
            obj.put("proUserId", proUserId);
            obj.put("thisUserId", thisUserId);
            obj.put("proLong", proLong);
            obj.put("userLat", userLat);
            obj.put("nowPage", nowPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                hideErrorPageState();
                stopLoadingAnim();
                Gson gson = new Gson();
                MyMoreBean myMoreBean = gson.fromJson(new String(bytes), new
                        TypeToken<MyMoreBean>() {
                        }.getType());
                String result = myMoreBean.getResult();
                if (result.equals("0")) {
                    totaPage = myMoreBean.getTotaPage();
                    userAndPro = myMoreBean.getUserAndProManager().get(0);
                    attentionIsOff = userAndPro.getAttentionIsOff();
                    allProductList.addAll(userAndPro.getUserAllProduct());
                    handler.sendEmptyMessage(LOAD_END);
                } else {
                    handler.sendEmptyMessage(LOAD_CANCLE);
                    ToastUtil.showMessageDefault(mContext, myMoreBean.getResultNote());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                handler.sendEmptyMessage(LOAD_CANCLE);
                ToastUtil.showMessageDefault(mContext, "数据获取失败");
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
            }
        });
    }

    /**
     * 窗口返回按键
     */
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
            case R.id.tv_attention_button: //关注
                showDeleteDialog();
                break;
            case R.id.tv_chat:
                Intent i = new Intent(this, ChatActivity.class);
                i.putExtra(Constant.EXTRA_USER_NAME, userAndPro.getNickname());
                i.putExtra(Constant.EXTRA_USER_ICON, userAndPro.getHeadicon());
                i.putExtra(Constant.EXTRA_USER_ID, proUserId);
                i.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                startActivity(i);
                break;
            case R.id.iv_back:
                this.finish();
                break;
        }
    }

    @Override
    public void onLoad() {
        if (nowPage < totaPage) {
            nowPage++;
            moreLoad();
        } else {
            ToastUtil.showMessageDefault(mContext, "没有更多数据了");
            handler.sendEmptyMessage(LOAD_END);
        }
    }

    @Override
    public void onRefresh() {
        nowPage = 1;
        load();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserAllProductBean product = allProductList.get(position);
        Intent intent = new Intent(mContext, ShopDetailInfoActivity.class);
        intent.putExtra("proId", product.getProId());
        intent.putExtra("proName", product.getProName());
        Log.e("proName", product.getProName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void toDetailClick(int i) {
        UserAllProductBean product = allProductList.get(i);
        Intent intent = new Intent(mContext, ShopDetailInfoActivity.class);
        intent.putExtra("proId", product.getProId());
        intent.putExtra("proName", product.getProName());
        Log.e("proName", product.getProName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showDeleteDialog() {

        View layout = this.getLayoutInflater().inflate(R.layout.managenment_dialog, null);
        TextView tv_set_warning = (TextView) layout.findViewById(R.id.tv_set_warning);
        if (attentionIsOff == 0) {
            tv_set_warning.setText("您确定要关注对方?");
        } else {
            tv_set_warning.setText("您确定要取消关注?");
        }
        Button btn_true = (Button) layout.findViewById(R.id.btn_true);
        Button btn_false = (Button) layout.findViewById(R.id.btn_false);
        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAttention();
                dialog.dismiss();
                adapter.notifyDataSetChanged();
            }
        });
        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog = new Dialog(this, R.style.Dialog);
        dialog.setContentView(layout);
        dialog.show();
    }

    public void isAttention() {
        startLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "isAttention");
            obj.put("thisUser", thisUserId);
            obj.put("proUser", proUserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                try {
                    String str = new String(bytes);
                    JSONObject obj = new JSONObject(str);
                    String result = (String) obj.get("result");
                    String resultNote = (String) obj.get("resultNote");
                    if ("0".equals(result)) {
                        ToastUtil.showMessageDefault(mContext, resultNote);
                        load();
                    }
                } catch (Exception e) {
                    LogUtils.e("AttentionActivity e" + e);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }
}


