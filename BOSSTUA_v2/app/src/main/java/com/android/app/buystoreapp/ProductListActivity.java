package com.android.app.buystoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystore.utils.expandtab.ExpandTabView;
import com.android.app.buystore.utils.expandtab.ViewFilter;
import com.android.app.buystore.utils.expandtab.ViewLeft;
import com.android.app.buystore.utils.expandtab.ViewMiddle;
import com.android.app.buystore.utils.expandtab.ViewRight;
import com.android.app.buystoreapp.adapter.BusinessAdapter;
import com.android.app.buystoreapp.adapter.HomeProListAdapter;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.CityInfoBean;
import com.android.app.buystoreapp.bean.GsonHomeBean;
import com.android.app.buystoreapp.bean.HomeProBean;
import com.android.app.buystoreapp.bean.ShopBean;
import com.android.app.buystoreapp.goods.ShopDetailInfoActivity;
import com.android.app.buystoreapp.managementservice.MymoreResourcesandServiceMainActivity;
import com.android.app.buystoreapp.search.SearchResultActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.view.SwipeRefreshLayoutUpDown;
import com.view.SwipeRefreshLayoutUpDown.OnLoadListener;
import com.view.SwipeRefreshLayoutUpDown.OnRefreshListener;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品列表总分类
 * Created by likaihang on 16/08/30.
 */
public class ProductListActivity extends BaseAct implements OnRefreshListener, OnLoadListener,
        View.OnClickListener, HomeProListAdapter.OnClickToMoreInterface {
    private static final int HANDLE_REFRESH = 0x18;
    private TextView mTitleText;
    private String region = "全国";//地区
    private int where;//上级页面
    private String mOrder = "0";//智能排序
    private String cateid = "0";//分类id
    private String fliter = "0000";//筛选
    private String thisUser;
    private String lab = "A";
    private Double priceHig = 0.0;
    private Double priceLow = 0.0;
    private LinearLayout head;
    private String threeCategoryName;
    private String categoryID;
    private String categoryName;
    private String categoryDes;
    private String categoryViews;
    private String categoryBkground;
//    private String categoryName;

    public ProductListActivity() {
    }

    private ProductListActivity mContext;

    private ExpandTabView expandTabView;
    private ArrayList<View> mViewArray;
    private ViewLeft viewLeft;
    private ViewMiddle viewMiddle;
    private ViewRight viewRight;
    private ViewFilter viewFilter;
    // content view
    private ListView mBusinessList;
    private List<ShopBean> mShopList = new ArrayList<ShopBean>();
    private BusinessAdapter mBusinessAdapter;
    private HomeProListAdapter mHomeCommodityAdapter;
    private List<HomeProBean> recommendCommodityList = new ArrayList<HomeProBean>();
    private int nowPage = 1;
    private int totalPage;
    private String cityID;
    private String userLat;
    private String userLong;
    private String b = "3";
    SwipeRefreshLayoutUpDown mSwipeLayout;


    private AdapterView.OnItemClickListener mBusinessListener = new AdapterView
            .OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position,
                                long arg3) {
            /*Intent intent = new Intent(ProductListActivity.this, ShopStoreActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("shop", mShopList.get(position - 1));
            intent.putExtras(bundle);
            startActivity(intent);*/
        }
    };

    private static final int HANDLE_CANCEL_LOAD = 0x13;
    private static final int HANDLE_CANCEL_REFRESH = 0x14;
    private static final int HANDLE_LOADMORE = 0x15;
    private static final int HANDLE_CANCEL_WAIT = 0x16;
    private static final int HANDLE_UPDATE_VIEW_LEFT = 0x17;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case HANDLE_CANCEL_REFRESH:
                    mSwipeLayout.setRefreshing(false);
                    break;
                case HANDLE_REFRESH:
                    mSwipeLayout.setRefreshing(false);
                    mHomeCommodityAdapter.notifyDataSetChanged();
                    break;
                case HANDLE_CANCEL_LOAD:
                    mSwipeLayout.setLoading(false);
                    break;
                case HANDLE_LOADMORE:
                    mSwipeLayout.setLoading(false);
                    mHomeCommodityAdapter.notifyDataSetChanged();
                    break;
                case HANDLE_UPDATE_VIEW_LEFT:
                    try {
                        expandTabView.setTitle(viewLeft.getShowText(), 0);
                        if (TextUtils.isEmpty(viewLeft.getShowText())) {
                            handler.sendEmptyMessageDelayed(HANDLE_UPDATE_VIEW_LEFT, 300);
                        } else {
                            handler.removeMessages(HANDLE_UPDATE_VIEW_LEFT);
                        }
                    } catch (Exception e) {
                    }
                    break;
                case 10000:
                    Bundle bundle = msg.getData();
                    categoryName = bundle.getString("categoryName");//大分类名称
                    categoryDes = bundle.getString("categoryDes");//大分类描述
                    categoryViews = bundle.getString("categoryViews");//大分类浏览量
                    categoryBkground = bundle.getString("categoryBkground");//大分类背景图片
                    ((TextView) findViewById(R.id.tv_category_name)).setText(categoryName);
                    ((TextView) findViewById(R.id.tv_categry_views)).setText(categoryViews);
                    ((TextView) findViewById(R.id.tv_categry_des)).setText(categoryDes);
                    ImageView background = (ImageView) findViewById(R.id.iv_categry_back);
                    if (!TextUtils.isEmpty(categoryBkground)) {
                        Picasso.with(mContext).load(categoryBkground).placeholder(R.drawable
                                .ic_product_list_title_bg).into(background);
                    }
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_tab_founds);
        mContext = this;
        ViewUtils.inject(this);
        where = getIntent().getIntExtra("where", 0);

        if (where == 1) {
            b = "3";
            threeCategoryName = getIntent().getStringExtra("threeCategoryName");//三级分类名称
            categoryID = getIntent().getStringExtra("categoryID");//三级分类id
            cateid = categoryID;

            categoryName = getIntent().getStringExtra("categoryName");//大分类名称
            categoryDes = getIntent().getStringExtra("categoryDes");//大分类描述
            categoryViews = getIntent().getStringExtra("categoryViews");//大分类浏览量
            categoryBkground = getIntent().getStringExtra("categoryBkground");//大分类背景图片
            ((TextView) findViewById(R.id.tv_category_name)).setText(categoryName);
            ((TextView) findViewById(R.id.tv_categry_views)).setText(categoryViews);
            ((TextView) findViewById(R.id.tv_categry_des)).setText(categoryDes);
            ImageView background = (ImageView) findViewById(R.id.iv_categry_back);
            if (!TextUtils.isEmpty(categoryBkground)) {
                Picasso.with(this).load(categoryBkground).placeholder(R.drawable
                        .ic_product_list_title_bg).into(background);
            }
        } else {
            b = "1";
            categoryName = getIntent().getStringExtra("categoryName");//大分类名称
            categoryID = getIntent().getStringExtra("categoryID");//大级分类ID
            cateid = categoryID;
            categoryDes = getIntent().getStringExtra("categoryDes");//大分类描述
            categoryViews = getIntent().getStringExtra("categoryViews");//大分类浏览量
            categoryBkground = getIntent().getStringExtra("categoryBkground");//大分类背景图片
            ((TextView) findViewById(R.id.tv_category_name)).setText(categoryName);
            ((TextView) findViewById(R.id.tv_categry_views)).setText(categoryViews);
            ((TextView) findViewById(R.id.tv_categry_des)).setText(categoryDes);
            ImageView background = (ImageView) findViewById(R.id.iv_categry_back);
            if (!TextUtils.isEmpty(categoryBkground)) {
                Picasso.with(this).load(categoryBkground).placeholder(R.drawable
                        .ic_product_list_title_bg).into(background);
            }
        }

        thisUser = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        CityInfoBean cityInfoBean = SharedPreferenceUtils.getCurrentCityInfo(this);
        cityID = cityInfoBean.getId();
        if ("".equals(cityID)) {
            cityID = "";
        }
       /* userLat = cityInfoBean.getCityLat();
        userLong = cityInfoBean.getCityLong();*/
        userLat = (TextUtils.isEmpty(cityInfoBean.getCityLat()) ? "0" : cityInfoBean.getCityLat());
        userLong = (TextUtils.isEmpty(cityInfoBean.getCityLong()) ? "0" : cityInfoBean
                .getCityLong());

        initViews();
        initValue();
//        parent.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                initValue();
//            }
//        }, 300);
        initListener();
        head = (LinearLayout) findViewById(R.id.ll_head);
        mBusinessList = (ListView) findViewById(R.id.business_list);
        mHomeCommodityAdapter = new HomeProListAdapter(mContext,
                recommendCommodityList);
        mBusinessList.setAdapter(mHomeCommodityAdapter);
        mHomeCommodityAdapter.setOnClickInterface(this);
        findViewById(R.id.id_custom_back_image).setOnClickListener(this);
//        findViewById(R.id.id_search_now).setOnClickListener(this);
        findViewById(R.id.id_search_edit).setOnClickListener(this);

//        View headerView = new View(this);
//        headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams
// .MATCH_PARENT,
//                110));
//        mBusinessList.addHeaderView(headerView);

//        mBusinessAdapter = new BusinessAdapter(
//                this, mShopList);
//        mBusinessList.setAdapter(mBusinessAdapter);
        mBusinessList.setOnItemClickListener(mBusinessListener);

        mSwipeLayout = (SwipeRefreshLayoutUpDown) findViewById(R.id.main_business_list_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light, R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setMode(SwipeRefreshLayoutUpDown.Mode.BOTH);
        mSwipeLayout.setLoadNoFull(true);
        head.measure(0, 0);
        initErrorPage();
        addIncludeLoading(head.getMeasuredHeight());
        startWhiteLoadingAnim();
        load();
    }

    @Override
    protected void load() {
        startWhiteLoadingAnim();
        refresh();
    }

    private void initViews() {
        expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
        viewLeft = new ViewLeft(mContext);
        viewMiddle = new ViewMiddle(mContext, handler, categoryName);
        viewRight = new ViewRight(mContext);
        viewFilter = new ViewFilter(mContext);
    }

    private void initValue() {
        mViewArray = new ArrayList<View>();
        mViewArray.add(viewLeft);
        mViewArray.add(viewMiddle);
        mViewArray.add(viewRight);
        mViewArray.add(viewFilter);
        ArrayList<String> mTextArray = new ArrayList<String>();
        mTextArray.add("1");
        mTextArray.add("2");
        mTextArray.add("3");
        mTextArray.add("4");
        try {
            expandTabView.setValue(mTextArray, mViewArray);
            if (TextUtils.isEmpty(viewLeft.getShowText())) {
                handler.sendEmptyMessageDelayed(HANDLE_UPDATE_VIEW_LEFT, 300);
            } else {
                expandTabView.setTitle(viewLeft.getShowText(), 0);
            }

            if (where == 1) {
                expandTabView.setTitle(threeCategoryName, 1);
            } else {
                //expandTabView.setTitle(viewMiddle.getShowText(), 1);
                expandTabView.setTitle(categoryName, 1);
            }
            expandTabView.setTitle(viewRight.getShowText(), 2);
            expandTabView.setTitle("筛选", 3);
        } catch (Exception e) {
            LogUtils.e("init tab child error:", e);
        }
    }

    private void initListener() {

        viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

            @Override
            public void getValue(String showText, String streetid) {
                onRefreshTabTitle(viewLeft, showText);
                nowPage = 1;
                region = showText;
                cityID = streetid;
                //请求一次接口
                load();
//                requestShopFromHttp(mShopType, cityID, "", mOrder,
//                        mShopCategory);
            }

            @Override
            public void initValue(String shopCategory, String showText) {
                nowPage = 1;
                region = showText;
                load();
            }
        });

        viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

            @Override
            public void getValue(String showText, String catogeryid) {
                onRefreshTabTitle(viewMiddle, showText);
                nowPage = 1;
                cateid = catogeryid;
                b = "3";
                load();
            }

        });

        viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

            @Override
            public void getValue(String distance, String showText) {
                onRefreshTabTitle(viewRight, showText);
                nowPage = 1;
                mOrder = distance;
                load();
            }
        });
        viewFilter.setOnSelectListener(new ViewFilter.OnSelectListener() {
            @Override
            public void getValue(int body, int transaction, String from, String to, String
                    severlable) {
                onRefreshTabTitle(viewFilter, "筛选");
                nowPage = 1;
                if (!TextUtils.isEmpty(from)) {
                    priceHig = Double.valueOf(from);
                    fliter = body + transaction + "10";
                }
                if (!TextUtils.isEmpty(to)) {
                    priceLow = Double.valueOf(to);
                    fliter = body + transaction + "01";
                }
                if (!TextUtils.isEmpty(from) && !TextUtils.isEmpty(to)) {
                    priceHig = Double.valueOf(from);
                    priceLow = Double.valueOf(to);
                    fliter = body + transaction + "11";
                }
                lab = severlable;
                load();
            }
        });
    }

    private void onRefreshTabTitle(View view, String showText) {
        expandTabView.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
            expandTabView.setTitle(showText, position);
        }
    }

    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }


   /* private void getloadMore() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getProductHome");
//            obj.put("state","2000000"+orderId);
//            obj.put("thisUser", thisUser);
//            obj.put("classify", classify);
//            obj.put("cityName", cityName);
            obj.put("nowPage", nowPage);
            obj.put("userLat", userLat);
            obj.put("userLong", userLong);
            obj.put("screeningAll","A");
            obj.put("statePriceLow",0.0);
            obj.put("statePriceHigh",0.0);
            obj.put("cityId", cityID);
            obj.put("stateClassificationId","");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                stopLoadingAnim();
                hideErrorPageState();
                try {
                    Gson gson = new Gson();
                    GsonHomeBean gsonHomeBack = gson.fromJson(new String(arg2),
                            new TypeToken<GsonHomeBean>() {
                            }.getType());

                    String result = gsonHomeBack.getResult();
                    if ("0".equals(result)) {
                        totalPage = Integer.valueOf(gsonHomeBack.getTotalPage());
                        recommendCommodityList.addAll(gsonHomeBack
                                .getCommodityProList());
                        handler.obtainMessage(HANDLE_LOADMORE).sendToTarget();
                    } else {
                        handler.sendEmptyMessageDelayed(HANDLE_CANCEL_LOAD,
                                HANDLE_CANCEL_WAIT);
                    }
                } catch (Exception e) {
                    handler.sendEmptyMessageDelayed(HANDLE_CANCEL_LOAD,
                            HANDLE_CANCEL_WAIT);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                handler.sendEmptyMessageDelayed(HANDLE_CANCEL_LOAD,
                        HANDLE_CANCEL_WAIT);
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(nowPage);
            }
        });
    }*/

    private void refresh() {
        String a = "0";

        JSONObject obj = new JSONObject();
        if (!region.equals("全国")) {
            a = "3";
        }
       /* if (where != 1) {
            b = "2";
        }*/

        String state = a + b + mOrder + fliter + "0";
        try {
            obj.put("cmd", "getProductHome");
            obj.put("state", state);
            obj.put("thisUser", thisUser);
            obj.put("classify", 0);
            obj.put("cityName", region);
            obj.put("nowPage", nowPage);
            obj.put("userLat", userLat);
            obj.put("userLong", userLong);
            obj.put("screeningAll", lab);
            obj.put("statePriceLow", priceLow);
            obj.put("statePriceHigh", priceHig);
            obj.put("cityId", cityID);
            obj.put("stateClassificationId", cateid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("----getProductHome-----", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Log.d("----getProductHome-----", "" + new String(arg2));
                stopLoadingAnim();
                hideErrorPageState();
                try {
                    Gson gson = new Gson();
                    GsonHomeBean gsonHomeBack = gson.fromJson(new String(arg2),
                            new TypeToken<GsonHomeBean>() {
                            }.getType());
                    String result = gsonHomeBack.getResult();
                    if ("0".equals(result)) {
                        totalPage = Integer.valueOf(gsonHomeBack.getTotalPage());
                        recommendCommodityList.clear();
                        recommendCommodityList.addAll(gsonHomeBack.getCommodityProList());
                        if (recommendCommodityList == null || recommendCommodityList.size() == 0) {
                            ToastUtil.showMessageDefault(mContext, "暂无商品数据");
                            //showEmpty("换个分类试试！");
                        }
                        handler.obtainMessage(HANDLE_REFRESH).sendToTarget();
                    } else {
                        handler.sendEmptyMessageDelayed(HANDLE_CANCEL_REFRESH,
                                HANDLE_CANCEL_WAIT);
                    }
                } catch (Exception e) {
                    Log.e("mikes", "RefreshTask error:", e);
                    handler.sendEmptyMessageDelayed(HANDLE_CANCEL_REFRESH,
                            HANDLE_CANCEL_WAIT);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                stopLoadingAnim();
                handler.sendEmptyMessageDelayed(HANDLE_CANCEL_REFRESH,
                        HANDLE_CANCEL_WAIT);
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
                showErrorPageState(nowPage);
            }
        });
    }

    @Override
    public void onLoad() {
        LogUtils.d("onLoad");
        if (nowPage < totalPage) {
            nowPage++;
            load();
        } else {
            LogUtils.d("onLoad  no more datas");
            ToastUtil.showMessageDefault(mContext, this.getString(R.string.no_more_data));
            handler.sendEmptyMessageDelayed(HANDLE_CANCEL_LOAD,
                    HANDLE_CANCEL_WAIT);
        }
    }

    @Override
    public void onRefresh() {
        LogUtils.d("onrefresh");
        nowPage = 1;
        recommendCommodityList.clear();
        load();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_custom_back_image:
                finish();
                break;
            case R.id.id_search_edit:
//            case R.id.id_search_now:
                //搜索
                Intent intent2 = new Intent(this, SearchResultActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void toDetailClick(int i) {
        Intent intent = new Intent(this, ShopDetailInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("proName", recommendCommodityList.get(i).getProName());
        bundle.putString("proId", recommendCommodityList.get(i).getProId());
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void toMoreClick(int i) {
        Intent intent = new Intent(this, MymoreResourcesandServiceMainActivity.class);
        intent.putExtra("proUserId", recommendCommodityList.get(i).getUserId());
        startActivity(intent);
    }
}
