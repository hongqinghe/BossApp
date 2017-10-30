package com.android.app.buystoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystore.utils.NoScrollGridView;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.GroupBean;
import com.android.app.buystoreapp.bean.GroupGoods;
import com.android.app.buystoreapp.bean.GsonBackOnlyResult;
import com.android.app.buystoreapp.bean.GsonGroupBack;
import com.android.app.buystoreapp.bean.ShoppingCarBean;
import com.android.app.buystoreapp.bean.ShoppingCarSubmit;
import com.android.app.buystoreapp.bean.VerifyOrderBean;
import com.android.app.buystoreapp.goods.GoodsDetailsXhAdapter;
import com.android.app.buystoreapp.setting.ConfirmOrderActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by likaihang on 16/08/19.
 */
public class PopupWindowActivity extends BaseAct implements View.OnClickListener {
    private ImageView closs;
    @ViewInject(R.id.tv_pop_goods_money)
    private TextView money;//价格

    /***
     * 单个商品最大限购数量
     */
    private int goodsMaxNum = 99;
    //可选组合
    @ViewInject(R.id.gv_specification)
    private List<GroupBean> groupList = new ArrayList<GroupBean>();
    private List<ShoppingCarBean> selectcarlist = new ArrayList<ShoppingCarBean>();

    //购买数量
    private ImageView minusGoods;
    private ImageView addGoods;
    private TextView goodsNumber;
    private TextView buy;
    private TextView addCar;
    private String proId;
    private NoScrollGridView gridview;
    private GoodsDetailsXhAdapter xhAdapter;
    private String proName;
    private String price;
    private String userId;
    private int supluse;//库存数
    private String groupprice;//组合价格
    private String groupId;//组合id
    private TextView mGroupName;
    private ImageView image;
    private TextView mSupluser;
    private TextView mMoney;
    private String icon;//封面
    private VerifyOrderBean data;
    private String nickName;
    private String proUserId;
    private String groupName;
    private int modes;
    private String weekstart;
    private String weekend;
    private String daytimestart;
    private String daytimeend;
    private int freightMode;
    private String freightPric;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_datiles_popu_windows);
        overridePendingTransition(R.anim.popupwindow_anim_in, R.anim.popupwindow_anim_out);
        mContext = this;
        proId = getIntent().getStringExtra("proId");
        proName = getIntent().getStringExtra("proName");
        price = getIntent().getStringExtra("price");
        icon = getIntent().getStringExtra("imageMinurl");
        nickName = getIntent().getStringExtra("nickName");//商家名称
        proUserId = getIntent().getStringExtra("proUserId");//商家id
        modes = getIntent().getIntExtra("modes", 0);//服务方式

        freightMode = getIntent().getIntExtra("freightMode", 0);
        freightPric = getIntent().getStringExtra("freightPric");

        weekstart = getIntent().getStringExtra("weekstart");//
        weekend = getIntent().getStringExtra("weekend");//
        daytimestart = getIntent().getStringExtra("daytimestart");//
        daytimeend = getIntent().getStringExtra("daytimeend");//

        initlistener();
        initwindow();
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        getMoreGroup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userId = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.popupwindow_anim_in, R.anim.popupwindow_anim_out);
    }

    /**
     * 获取组合数据
     *
     * @author likaihang
     * creat at @time 16/11/08 19:37
     */
    private void getMoreGroup() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getMoreGroup");
            obj.put("proId", proId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("--getMoreGroup--", "" + new String(bytes));
                stopLoadingAnim();
                Gson gson = new Gson();
                GsonGroupBack gsonGroupBack = gson.fromJson(new String(bytes),
                        new TypeToken<GsonGroupBack>() {
                        }.getType());
                String result = gsonGroupBack.getResult();
                String resultNote = gsonGroupBack.getResultNote();
                if ("0".equals(result)) {
                    groupList.clear();
                    groupList.addAll(gsonGroupBack.getMgList());
                    xhAdapter.setData(groupList);
                    groupId = gsonGroupBack.getMgList().get(0).getMoreGroId();
                    supluse = gsonGroupBack.getMgList().get(0).getMoreGroSurplus();
                    groupprice = gsonGroupBack.getMgList().get(0).getMoreGroPrice();
                    String s = String.format(getResources().getString(R.string.goods_group_proSurplus), String.valueOf(supluse));
                    mSupluser.setText(s);
                    mMoney.setText(groupprice);
//                    xhAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showMessageDefault(getApplicationContext(), resultNote);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                ToastUtil.showMessageDefault(getApplicationContext(), getResources().getString(R.string.network_is_bad));
                stopLoadingAnim();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
            }
        });
    }

    /**
     * 加入购物车
     *
     * @param count 本商品购买个数
     */
    private void saveBuyCar(final String count) {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "addShopCar");
            obj.put("proId", proId);
            obj.put("proName", proName);
            obj.put("price", groupprice);
            obj.put("moreId", groupId);
            obj.put("count", count);
            obj.put("thisUser", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("mikes", "addcar param=" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        stopLoadingAnim();
                        Gson gson = new Gson();
                        GsonBackOnlyResult gsonBackOnlyResult = gson.fromJson(
                                new String(arg2), new TypeToken<GsonBackOnlyResult>() {
                                }.getType());

                        String result = gsonBackOnlyResult.getResult();
                        String resultNote = gsonBackOnlyResult.getResultNote();
                        if ("0".equals(result)) {
//                            mCommodityNum = String.valueOf(Integer
//                                    .valueOf(mCommodityNum) + 1);
                            ToastUtil.showMessageDefault(mContext, getResources().getString(
                                    R.string.buycar_save_success));
                        } else {
                            ToastUtil.showMessageDefault(mContext, resultNote);
                        }
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        stopLoadingAnim();
                        ToastUtil.showMessageDefault(mContext, "请求失败！");
                    }
                }, new HttpUtils.RequestNetworkError() {
                    @Override
                    public void networkError() {
                        stopLoadingAnim();
                    }
                }
        );
    }

    /**
     * 弹窗底部显示
     *
     * @author likaihang
     * creat at @time 16/08/19 12:43
     */
    public void initwindow() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        lp.width = dm.widthPixels;//让dialog的宽占满屏幕的宽
        lp.gravity = Gravity.BOTTOM;//出现在底部
        window.setAttributes(lp);
        mGroupName = (TextView) findViewById(R.id.tv_group_name);
        mGroupName.setText(proName);
        image = (ImageView) findViewById(R.id.iv_group_goods_image);
        Picasso.with(this).load(icon)
                //.resize(100,100)
                .placeholder(R.drawable.ic_default).into(image);
        mSupluser = (TextView) findViewById(R.id.tv_group_supluser);
        mMoney = (TextView) findViewById(R.id.tv_pop_goods_money);
    }

    public void initlistener() {
        minusGoods = (ImageView) findViewById(R.id.iv_goods_minus_goods);
        minusGoods.setOnClickListener(this);
        addGoods = (ImageView) findViewById(R.id.iv_goods_add_goods);
        addGoods.setOnClickListener(this);
        closs = (ImageView) findViewById(R.id.iv_popup_closs);
        closs.setOnClickListener(this);
        goodsNumber = (TextView) findViewById(R.id.tv_goods_number);
        buy = (TextView) findViewById(R.id.tv_buy_now);
        buy.setOnClickListener(this);
        addCar = (TextView) findViewById(R.id.tv_add_shopping_car);
        addCar.setOnClickListener(this);
        gridview = (NoScrollGridView) findViewById(R.id.gv_specification);
        xhAdapter = new GoodsDetailsXhAdapter(this);
        gridview.setAdapter(xhAdapter);
        xhAdapter.setSeclection(0);
        gridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                xhAdapter.setSeclection(position);
                GroupBean getNormsBean = xhAdapter
                        .getNormsBean(position);
                groupName = getNormsBean.getMoreGroName();//组合名称
                groupId = getNormsBean.getMoreGroId();//组合id
                groupprice = getNormsBean.getMoreGroPrice();//组合单价
                supluse = getNormsBean.getMoreGroSurplus();//组合库存
                String s = String.format(getResources().getString(R.string.goods_group_proSurplus), String.valueOf(supluse));
                mSupluser.setText(s);
                mMoney.setText(groupprice);
                xhAdapter.notifyDataSetChanged();
                    /*if (!productId.equals(pId)) {
                        productId = pId;
                        isRefresh = false;
                    }*/
            }
        });
    }

    @Override
    public void onClick(View v) {
        num = Integer.parseInt(goodsNumber.getText().toString());
        switch (v.getId()) {
            case R.id.iv_popup_closs:
                finish();
                break;
            case R.id.iv_goods_minus_goods:
                int minusNum = num - 1;
                if (num > 1) {
                    goodsNumber.setText(minusNum + "");
                }
                if (1 == minusNum) {
                    minusGoods.setImageResource(R.drawable.icon_minus_hid);
                }
                // 点击减号若goodsMaxNum=1，则加号仍置灰
                if (goodsMaxNum - 1 == minusNum && goodsMaxNum != 1) {
                    addGoods.setImageResource(R.drawable.icon_add);
                }
                break;
            case R.id.iv_goods_add_goods:
                int addNum = num + 1;
                if (num < goodsMaxNum) {
                    goodsNumber.setText(addNum + "");
                }
                if (goodsMaxNum - 1 == num) {
                    addGoods.setImageResource(R.drawable.icon_add_hid);
                }
                if (2 == addNum && goodsMaxNum > 1) {
                    minusGoods.setImageResource(R.drawable.icon_minus);
                }
                if (num > supluse) {
                    ToastUtil.showMessageDefault(mContext, "没有那么多库存量了！");
                }
                break;
            case R.id.tv_buy_now:
                if (!proUserId.equals(userId)){

                if (num > supluse) {
                    ToastUtil.showMessageDefault(mContext, "没有那么多库存量了！");
                } else {
                    selectcarlist.clear();
                    data = new VerifyOrderBean();
                    data.vnList = new ArrayList<ShoppingCarSubmit>();
                    List<GroupGoods> grouplist = new ArrayList<GroupGoods>();

                    //t添加当前商品商家信息
                    ShoppingCarBean selectcar = new ShoppingCarBean();
                    selectcar.setUserid(proUserId);
                    selectcar.setNickname(nickName);
//                                        selectcar.setQueryCar(mListGoods.get(i).getQueryCar());

                    //添加商品信息
                    GroupGoods group = new GroupGoods();
                    group.setCount(num);//组合数量
                    group.setFreightMode(freightMode);//货运方式
                    group.setFreightPrice(freightPric);//运费
                    group.setModes(modes);//服务方式
                    group.setMoreGroId(groupId);//组合id
                    group.setMoreGroName(groupName);//组合名称
                    group.setMoreGroPrice(groupprice);//组合价格
                    group.setProImageMin(icon);//缩略图
                    group.setProName(proName);//商品名称
                    group.setDayTimeEnd(weekstart);//时间
                    group.setDayTimeStart(weekend);
                    group.setWeekEnd(daytimestart);
                    group.setWeekStart(daytimeend);
                    grouplist.add(group);

                    selectcar.setQueryCar(grouplist);
                    selectcarlist.add(selectcar);

                    //添加比对信息
                    ShoppingCarSubmit bean = new ShoppingCarSubmit();
                    bean.count = num;//数量
                    bean.moreGroPrice = String.valueOf(num * Double.valueOf(groupprice));//
                    bean.shopCarId = groupId;
                    data.vnList.add(bean);

                    buyNow(data.vnList);
                }
                /*Intent intent = new Intent(mContext,
                        ConfirmOrderActivity.class);
                Bundle bundle = new Bundle();
//                int selectNums = 1;
                float totalPrice = num
                        * Float.valueOf(groupprice);
                bundle.putFloat("totalPrice", totalPrice);
//                bundle.putInt("selectedNums", selectNums);
                        *//*mCommodityBean.setCommodityNum(String
                                .valueOf(selectNums));
                        mUserShopCar.clear();
                        mUserShopCar.add(mCommodityBean);
                GsonShopCarToOrder gsonShopCarToOrder = new GsonShopCarToOrder(
                        mUserShopCar);
                String orderString = new Gson().toJson(gsonShopCarToOrder);
                bundle.putString("commodites", orderString);*//*
                intent.putExtras(bundle);
                startActivity(intent);*/
                }else {
                    ToastUtil.showMessageDefault(mContext,"您不能对自己操作！");
                }
                break;
            case R.id.tv_add_shopping_car:
                if (!proUserId.equals(userId)) {
                    if (num > supluse) {
                        ToastUtil.showMessageDefault(mContext, "没有那么多库存量了！");
                    } else {
                        saveBuyCar(String.valueOf(num));
                    }
                }else{
                    ToastUtil.showMessageDefault(mContext,"您不能对自己操作！");
                }
                break;
        }
    }

    private void buyNow(List<ShoppingCarSubmit> mlist) {
        startWhiteLoadingAnim();
        data = new VerifyOrderBean("verifyPriceOrNumber", 3, mlist);
        JSONObject obj = null;
        try {
            obj = new JSONObject(new Gson().toJson(data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("结算提交数据--", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                Log.d("verifyPriceOrNumber---", new String(bytes));
                JSONObject obj = null;
                try {
                    obj = new JSONObject(new String(bytes));
                    String result = obj.getString("result");
                    String resultNote = obj.getString("resultNote");
                    String moreGroSurplus = obj.getString("moreGroSurplus");
                    if ("0".equals(result)) {
                        String totalPrice = null;
                        if (!TextUtils.isEmpty(freightPric)){
                            totalPrice = String.valueOf(num * (Double.parseDouble(groupprice)+Double.parseDouble(freightPric)));
                        }else {
                            totalPrice = String.valueOf(num * Double.valueOf(groupprice));
                        }
                        Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                        intent.putExtra("totalPrice", Double.valueOf(totalPrice));
                        intent.putExtra("selectcarlist", (Serializable) selectcarlist);
                        mContext.startActivity(intent);
                    } else {
                        String shopCarId = obj.getString("shopCarId");
                        Log.d("result----", shopCarId + "\n" + moreGroSurplus);
                        ToastUtil.showMessageDefault(mContext, shopCarId + "\n" + resultNote);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(mContext, "请求失败！");
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
            }
        });
    }
}
