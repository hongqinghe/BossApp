package com.android.app.buystoreapp.goods;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.adapter.MyExpandableListAdapter;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.GsonShoppingCarBean;
import com.android.app.buystoreapp.bean.ShoppingCarBean;
import com.android.app.buystoreapp.dao.DBHelper;
import com.android.app.buystoreapp.listener.OnShoppingCartChangeListener;
import com.android.app.buystoreapp.listener.ShoppingCartBiz;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.android.app.utils.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ShoppingCartActivity extends BaseAct {
    private static final int GET_SHOPPING_CAR_INFO = 0x10;
    ExpandableListView expandableListView;
    ImageView ivSelectAll;
    TextView btnSettle;
    TextView tvCountMoney;
    TextView tvTitle;
    RelativeLayout rlShoppingCartEmpty;
    RelativeLayout rlBottomBar;

    private List<ShoppingCarBean> mListGoods = new ArrayList<ShoppingCarBean>();
    private MyExpandableListAdapter adapter;
    private ImageButton back;
    private String userId;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_SHOPPING_CAR_INFO:
                    ShoppingCartBiz.updateShopList(mListGoods);
                    updateListView();
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_shopping_cart);
        mContext = this;
        userId = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
        DBHelper.init(getApplicationContext());
        ToastHelper.getInstance().init(getApplicationContext());
        initView();
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        setAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestShoppingCartList();
    }

    @Override
    protected void load() {
        getCarInfo();
    }

    private void getCarInfo() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "shopCar");
            obj.put("userid", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("--shopCar--", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("购物车数据", new String(bytes));
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                GsonShoppingCarBean gsonShopCarBack = gson.fromJson(new String(bytes),
                        new TypeToken<GsonShoppingCarBean>() {
                        }.getType());
                String result = gsonShopCarBack.getResult();
                String resultNote = gsonShopCarBack.getResultNote();
                if ("0".equals(result)) {
                    if (gsonShopCarBack.getList().size() > 0) {
                        mListGoods.clear();
                        mListGoods.addAll(gsonShopCarBack.getList());
                        mHandler.obtainMessage(GET_SHOPPING_CAR_INFO).sendToTarget();
                    } else {
                        showEmpty("您的购物车还是空的！");
                    }
                } else {
                    ToastUtil.showMessageDefault(mContext, resultNote);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(mContext, "连接超时！");
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }

    private void setAdapter() {
        adapter = new MyExpandableListAdapter(this);
//        adapter = new MyExpandableListAdapter(this);
        expandableListView.setAdapter(adapter);
        adapter.setOnShoppingCartChangeListener(new OnShoppingCartChangeListener() {

            public void onDataChange(String selectCount, String selectMoney,String selectedFright) {
                int goodsCount = ShoppingCartBiz.getMoreGroupCount();
//                int goodsCount = ShoppingCartBiz.getMoreGroupCount(mListGoods);
//                int goodsCount = 2;
//                if (!isNetworkOk) {//网络状态判断暂时不显示
//                }
               /* if (goodsCount == 0) {
                    showEmpty(true);
                } else {
                    showEmpty(false);//其实不需要做这个判断，因为没有商品的时候，必须退出去添加商品；
                }*/
                String countMoney = String.format(getResources().getString(R.string.count_money),
                        selectMoney);
/*
                String countMoney = String.format(getResources().getString(R.string.count_money),
                        selectMoney,selectedFright);
*/
                String countGoods = String.format(getResources().getString(R.string.count_goods),
                        selectCount);
                String title = String.format(getResources().getString(R.string.shop_title),
                        selectCount);
//                String title = String.format(getResources().getString(R.string.shop_title),
// goodsCount + "");
                tvCountMoney.setText(countMoney);
                btnSettle.setText(countGoods);
                tvTitle.setText(title);
            }


            public void onSelectItem(boolean isSelectedAll) {
                ShoppingCartBiz.checkItem(isSelectedAll, ivSelectAll);
            }
        });
        //通过监听器关联Activity和Adapter的关系，解耦；
        View.OnClickListener listener = adapter.getAdapterListener();
        if (listener != null) {
            //即使换了一个新的Adapter，也要将“全选事件”传递给adapter处理；
            ivSelectAll.setOnClickListener(adapter.getAdapterListener());
            //结算时，一般是需要将数据传给订单界面的
            btnSettle.setOnClickListener(adapter.getAdapterListener());
        }
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i,
                                        long l) {
                return true;
            }
        });
    }

    public void showEmpty(boolean isEmpty) {
        if (isEmpty) {
            expandableListView.setVisibility(View.GONE);
            rlShoppingCartEmpty.setVisibility(View.VISIBLE);
            rlBottomBar.setVisibility(View.GONE);
        } else {
            expandableListView.setVisibility(View.VISIBLE);
            rlShoppingCartEmpty.setVisibility(View.GONE);
            rlBottomBar.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        expandableListView = $(R.id.expandableListView);
        ivSelectAll = $(R.id.ivSelectAll);
        btnSettle = $(R.id.btnSettle);
        tvCountMoney = $(R.id.tvCountMoney);
        tvTitle = $(R.id.tvTitle);
        rlShoppingCartEmpty = $(R.id.rlShoppingCartEmpty);
        rlBottomBar = $(R.id.rlBottomBar);
        back = $(R.id.ib_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 获取购物车列表的数据（数据和网络请求也是非通用部分）
     */
    private void requestShoppingCartList() {
        ShoppingCartBiz.delAllGoods();
        load();
    }

    private void updateListView() {
        adapter.setList(mListGoods);
        adapter.notifyDataSetChanged();
        expandAllGroup();
    }

    /**
     * 展开所有组
     */
    private void expandAllGroup() {
        for (int i = 0; i < mListGoods.size(); i++) {
            expandableListView.expandGroup(i);
        }
    }

    /**
     * 省去类型转换  将此方法写在基类Activity
     */
    protected <T extends View> T $(int id) {
        return (T) super.findViewById(id);
    }

}
