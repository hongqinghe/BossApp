package com.android.app.buystoreapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.LayoutAnimation;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.adapter.HomeProListAdapter;
import com.android.app.buystoreapp.base.BaseFragment;
import com.android.app.buystoreapp.bean.ChanceInfoBean;
import com.android.app.buystoreapp.bean.ChanceInfoBean.onGridViewItemClickListener;
import com.android.app.buystoreapp.bean.CityInfoBean;
import com.android.app.buystoreapp.bean.GsonBannerBack;
import com.android.app.buystoreapp.bean.GsonClassifyBack;
import com.android.app.buystoreapp.bean.GsonCompanyBack;
import com.android.app.buystoreapp.bean.GsonHomeBean;
import com.android.app.buystoreapp.bean.HomeProBean;
import com.android.app.buystoreapp.bean.UserInfoBean;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.buystoreapp.goods.ShopDetailInfoActivity;
import com.android.app.buystoreapp.managementservice.CorporateheadlinesListActivity;
import com.android.app.buystoreapp.managementservice.MymoreResourcesandServiceMainActivity;
import com.android.app.buystoreapp.managementservice.NewInfoActivity;
import com.android.app.buystoreapp.setting.LoginActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.buystoreapp.widget.MyListView;
import com.android.app.utils.HttpUtils;
import com.android.app.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.view.SwipeRefreshLayoutUpDown;
import com.view.SwipeRefreshLayoutUpDown.OnLoadListener;
import com.view.SwipeRefreshLayoutUpDown.OnRefreshListener;
import com.viewpagerindicator.CirclePageIndicator;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TabHome extends BaseFragment implements OnItemClickListener, OnRefreshListener, OnLoadListener, AbsListView.OnScrollListener, RadioGroup.OnCheckedChangeListener, HomeProListAdapter.OnClickToMoreInterface {
    private static final int HANDLE_CLASS = 0xe6;
    private static final int GET_COMPANY_NEWS = 0xe7;
    private BossBuyActivity mContext;
    private LayoutInflater mInflater;

    private View headerView;

    SwipeRefreshLayoutUpDown mSwipeLayout;

    // private View emptyView;
    //private View emptyFailureView;

    private MyListView mListView;
    private HomeProListAdapter mHomeCommodityAdapter;
    private List<HomeProBean> recommendCommodityList = new ArrayList<HomeProBean>();
    //    private List<CommodityBean> scrollCommodityList = new ArrayList<CommodityBean>();
    private List<GsonBannerBack.ProRecommendImg> scrollCommodityList = new ArrayList<GsonBannerBack.ProRecommendImg>();
    private List<GsonCompanyBack.CompanyNews> newsList = new ArrayList<GsonCompanyBack.CompanyNews>();
    private ArrayList<String> mAdvImageList = new ArrayList<String>();
    List<View> mAdvViewList = new ArrayList<View>();
    private ScheduledExecutorService scheduledExecutorService;
    private int curPos;

    private int totalPage;
    private int nowPage = 1;
    private static final String PAGE_SIZE = "20";
    private static final int HANDLE_LOADMORE = 0xe1;
    private static final int HANDLE_REFRESH = 0xe2;
    private static final int HANDLE_ADV = 0xe3;
    private static final int HANDLE_CANCEL_REFRESH = 0xe4;
    private static final int HANDLE_CANCEL_LOAD = 0xe5;
    private static final int HANDLE_CANCEL_WAIT = 100;
    private static final int HANDLE_REQUEST_CITY = 0x10;

    private String cityID;
    private String userLat;
    private String userLong;
    private LinearLayout llDangLing;
    //    private ImageView banner;
    private GridView gvNews;
    private RadioGroup mGroup;
    private int checkId;//选中的id
    private RadioButton mRadiorecom;
    private RadioButton mRadioheat;
    private RadioButton mRadiofresh;
    private RadioButton mRadionearby;
    private RadioGroup mGroupcopy;
    private RadioButton mRadiorecomcopy;
    private RadioButton mRadioheatcopy;
    private RadioButton mRadiofreshcopy;
    private RadioButton mRadionearbycopy;
    private GridViewGallery mGallery;//存放布局的视图容器
    private LinearLayout ll_head_news;//跳转到企业头条
    private LinearLayout mLayout;
    private List<ChanceInfoBean> chanceList = new ArrayList<ChanceInfoBean>();//数据
    private List<ChanceInfoBean> list;
    private String cityName;
    private String orderId = "0";
    private String thisUser;
    private UserInfoBean mUserInfo;
    private boolean isLogin;
    private int classify = 2;
    private ViewPager mAdvViewPager;
    private AdvPagerAdapter mAdvPagerAdapter;
    private boolean isAutoPlay = true;
    private int cityLevel = 2;
    private GvAdapter gvAdapter;
    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CityInfoBean cityInfo = SharedPreferenceUtils
                .getCurrentCityInfo(getActivity());
        cityLevel = cityInfo.getLevel();
        cityID = cityInfo.getId();
        if ("".equals(cityID)) {
            /*Intent intent = new Intent(getActivity(), CityActivity.class);
            startActivityForResult(intent, HANDLE_REQUEST_CITY);*/
            cityID = "110100";
        }
        cityName = CrashApplication.cityName;
        userLat = "" + CrashApplication.latitude;//cityInfo.getCityLat();
        userLong = "" + CrashApplication.longitude;//cityInfo.getCityLong();
        mUserInfo = SharedPreferenceUtils.getCurrentUserInfo(getActivity());
        isLogin = mUserInfo.isLogin();
        userId = mUserInfo.getUserId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mInflater = inflater;
        View parent = mInflater.inflate(R.layout.main_tab_home, null, false);

        Bundle bundle = getArguments();

        //头部布局
        headerView = mInflater.inflate(R.layout.home_header_view, null, false);
//        banner = (ImageView) headerView.findViewById(R.id.iv_home_banner);//头部广告
        mLayout = (LinearLayout) headerView.findViewById(R.id.ll_gallery);//头部分类
        gvNews = (GridView) headerView.findViewById(R.id.gv_company_news);//头部今日头条

        gvAdapter = new GvAdapter(mContext);
        gvNews.setAdapter(gvAdapter);
        gvNews.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isLogin) {
                    Intent intent = new Intent(getActivity(), NewInfoActivity.class);
                    intent.putExtra("newsId", newsList.get(i).getNewsId());
                    startActivity(intent);
                } else {
                    ToastUtil.showMessageDefault(getActivity(),
                            getString(R.string.personal_no_login_toast));
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        });
        ll_head_news = (LinearLayout) headerView.findViewById(R.id.ll_head_news);
        ll_head_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent headlines = new Intent(getActivity(), CorporateheadlinesListActivity.class);
                startActivity(headlines);
            }
        });
        if (bundle != null) {
            //根据bundle重新获取数据
            if (bundle.getString("bundle").equals("resource")) {
//                banner.setBackgroundResource(R.drawable.bg_resource);
                classify = 1;
                initHeaderClassify(classify);
            } else {
//                banner.setBackgroundResource(R.drawable.bg_service);
                classify = 2;
                initHeaderClassify(classify);
            }
        } else {
            initHeaderClassify(classify);
        }
        initAdvertisement();
        //悬浮部分
        llDangLing = (LinearLayout) parent.findViewById(R.id.ll_tab);
        mGroup = (RadioGroup) parent.findViewById(R.id.rg_tab_group);
        mRadiorecom = (RadioButton) parent.findViewById(R.id.rb_tab_recommend);//推荐
        mRadioheat = (RadioButton) parent.findViewById(R.id.rb_tab_heat);//人气
        mRadiofresh = (RadioButton) parent.findViewById(R.id.rb_tab_fresh);//最新
        mRadionearby = (RadioButton) parent.findViewById(R.id.rb_tab_nearby);//附近
        mGroup.setOnCheckedChangeListener(this);

        mListView = (MyListView) parent.findViewById(R.id.list);
        headerView.setVisibility(View.VISIBLE);
        mListView.addHeaderView(headerView);

        //悬浮的重复部分
        View v = View.inflate(getActivity(), R.layout.stick_action, null);//悬浮tab
        mGroupcopy = (RadioGroup) v.findViewById(R.id.rg_tab_group_cpy);//RadioGroup
        mRadiorecomcopy = (RadioButton) v.findViewById(R.id.rb_tab_recommend_copy);//推荐
        mRadioheatcopy = (RadioButton) v.findViewById(R.id.rb_tab_heat_copy);//人气
        mRadiofreshcopy = (RadioButton) v.findViewById(R.id.rb_tab_fresh_copy);//最新
        mRadionearbycopy = (RadioButton) v.findViewById(R.id.rb_tab_nearby_copy);//附近
        mGroupcopy.setOnCheckedChangeListener(this);

        mListView.addHeaderView(v);//悬浮部分View.inflate(getContext(),R.layout.stick_action,null)
        mListView.setOnScrollListener(this);
        mListView.setLayoutAnimation(LayoutAnimation.getAnimationController());
        mListView.setOnItemClickListener(this);
        mHomeCommodityAdapter = new HomeProListAdapter(mContext,
                recommendCommodityList);
        mHomeCommodityAdapter.setOnClickInterface(this);
        mListView.setAdapter(mHomeCommodityAdapter);

       /* mHomeCommodityAdapter.setmOnClickfollowlister(new HomeProListAdapter.OnClickfollowlister() {
            @Override
            public void onClickfollow(String thisUser, String proUser) {
                follow(thisUser,proUser);
            }
        });*/
//         mListView.setEmptyView(emptyView);

        return parent;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeLayout = (SwipeRefreshLayoutUpDown) view
                .findViewById(R.id.main_home_list_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light, R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setMode(SwipeRefreshLayoutUpDown.Mode.BOTH);
        mSwipeLayout.setLoadNoFull(false);
        onRefresh();
        startWhiteLoadingAnim();
        load();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAutoPlay) {
            startPlay();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (BossBuyActivity) activity;
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdvImageList.clear();
        stopPlay();
    }

    @Override
    protected void load() {
//        startWhiteLoadingAnim();
        getBanner();
        getCompanyNews();
        refresh();
    }

    /**
     * 获取企业头条信息
     * creat at @time 16/10/24 15:10
     */
    private void getCompanyNews() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getCompanyNews");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Gson gson = new Gson();
                GsonCompanyBack gsonCompanyBack = gson.fromJson(new String(bytes),
                        new TypeToken<GsonCompanyBack>() {
                        }.getType());
                String result = gsonCompanyBack.getResult();
                String resultNote = gsonCompanyBack.getResultNote();
                if ("0".equals(result)) {
                    if (gsonCompanyBack.getCompanyNews().size() > 0) {
                        newsList.clear();
                        newsList.addAll(gsonCompanyBack.getCompanyNews());
                        mHandler.obtainMessage(GET_COMPANY_NEWS).sendToTarget();
                    }
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

    /**
     * 获取BANNER
     * creat at @time 16/10/24 13:26
     */
    private void getBanner() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getRecommendImage");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Gson gson = new Gson();
                GsonBannerBack gsonBannerBack = gson.fromJson(new String(bytes),
                        new TypeToken<GsonBannerBack>() {
                        }.getType());
                if ("0".equals(gsonBannerBack.getResult())) {
                    if (gsonBannerBack.getImageCount() != 0) {
                        scrollCommodityList.clear();
                        scrollCommodityList.addAll(gsonBannerBack.getProRecommendImg());
                    } else {

                    }
                } else {
                    ToastUtil.showMessageDefault(mContext, gsonBannerBack.getResultNote());
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

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position,
                            long arg3) {
        Intent intent = new Intent(getActivity(), ShopDetailInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("proName", recommendCommodityList.get(position - 2).getProName());
        bundle.putString("proId", recommendCommodityList.get(position - 2).getProId());
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private OnPageChangeListener advPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageSelected(int pos) {
            curPos = pos;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 0:
                    if (mAdvViewPager.getCurrentItem() == mAdvViewPager
                            .getAdapter().getCount() - 1 && !isAutoPlay) {
                        mAdvViewPager.setCurrentItem(0);
                    } else if (mAdvViewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        mAdvViewPager.setCurrentItem(mAdvViewPager.getAdapter()
                                .getCount() - 1);
                    }
                    break;
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
                default:
                    break;
            }
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLE_ADV:
                    mAdvViewPager.setCurrentItem(curPos);
                    break;
                case HANDLE_REFRESH:
                    mSwipeLayout.setRefreshing(false);
                    headerView.setVisibility(View.VISIBLE);
                    mHomeCommodityAdapter.notifyDataSetChanged();
                    initAdvCommodity();
                    break;
                case HANDLE_LOADMORE:
                    mSwipeLayout.setLoading(false);
                    mHomeCommodityAdapter.notifyDataSetChanged();
                    break;
                case HANDLE_CANCEL_REFRESH:
                    mSwipeLayout.setRefreshing(false);
                    break;
                case HANDLE_CANCEL_LOAD:
                    mSwipeLayout.setLoading(false);
                    break;
                case HANDLE_CLASS:
//                    chanceList.clear();
                    list = new ArrayList<ChanceInfoBean>();
                    for (int i = 0; i < chanceList.size(); i++) {
                        ChanceInfoBean data = new ChanceInfoBean(chanceList.get(i).getCategoryName(), chanceList.get(i).getCategoryIcon(), i + 100);
                        list.add(data);
                        data.setOnClickListener(new onGridViewItemClickListener() {

                            @Override
                            public void ongvItemClickListener(int position) {
                                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                                intent.putExtra("where", 2);
                                intent.putExtra("categoryName", chanceList.get(position).getCategoryName());
                                intent.putExtra("categoryDes", chanceList.get(position).getCategoryDes());
                                intent.putExtra("categoryViews", chanceList.get(position).getCategoryViews());
                                intent.putExtra("categoryBkground", chanceList.get(position).getCategoryBkground());
                                intent.putExtra("categoryID", chanceList.get(position).getCategoryID());
                                startActivity(intent);
                            }
                        });
                    }

                    mGallery = new GridViewGallery(mContext, list);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    mLayout.addView(mGallery, param);
                    break;
                case GET_COMPANY_NEWS:
                    //刷新企业头条
                    gvAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem >= 1) {
            llDangLing.setVisibility(View.VISIBLE);
        } else {
            llDangLing.setVisibility(View.GONE);
        }
    }

    //分类接口
    private void initHeaderClassify(int cla) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getCategoty");
            obj.put("classify", cla);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Log.d("==getCategoty==", new String(arg2));
                Gson gson = new Gson();
                GsonClassifyBack gsonClassifyBack = gson.fromJson(new String(arg2), new TypeToken<GsonClassifyBack>() {
                }.getType());
                String result = gsonClassifyBack.getResult();
                if (result.equals("0")) {
                    chanceList.addAll(gsonClassifyBack.getCategoryList());
                    mHandler.obtainMessage(HANDLE_CLASS).sendToTarget();
                } else {

                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(1);
            }
        });
    }

    private void getloadMore() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getProductHome");
            obj.put("state", "2000000" + orderId);
            obj.put("thisUser", thisUser);
            obj.put("nowPage", nowPage);
            obj.put("classify", classify);
            obj.put("userLat", userLat);
            obj.put("userLong", userLong);
            obj.put("screeningAll", "A");
            obj.put("statePriceLow", 0.0);
            obj.put("statePriceHigh", 0.0);
            obj.put("cityId", cityID);
            obj.put("cityName", cityName);
            obj.put("stateClassificationId", "");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("----上拉加载-----", obj.toString());
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
                        mHandler.obtainMessage(HANDLE_LOADMORE).sendToTarget();
                    } else {
                        mHandler.sendEmptyMessageDelayed(HANDLE_CANCEL_LOAD,
                                HANDLE_CANCEL_WAIT);
                    }
                } catch (Exception e) {
                    mHandler.sendEmptyMessageDelayed(HANDLE_CANCEL_LOAD,
                            HANDLE_CANCEL_WAIT);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                mHandler.sendEmptyMessageDelayed(HANDLE_CANCEL_LOAD,
                        HANDLE_CANCEL_WAIT);
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(nowPage);
            }
        });
    }

    private void refresh() {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getProductHome");
            obj.put("state", "2000000" + orderId);
            obj.put("thisUser", thisUser);
            obj.put("nowPage", nowPage);
            obj.put("classify", classify);
            obj.put("userLat", userLat);
            obj.put("userLong", userLong);
            obj.put("screeningAll", "A");
            obj.put("statePriceLow", 0.0);
            obj.put("statePriceHigh", 0.0);
            obj.put("cityId", cityID);
            obj.put("cityName", cityName);
            obj.put("stateClassificationId", "");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("----getProductHome-----", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                LogUtil.LogAll("首页列表数据", new String(arg2));
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
                        if (gsonHomeBack.getCommodityProList().size() > 0) {

                            if (nowPage == 1) {
                                recommendCommodityList.clear();
                            }
                            recommendCommodityList.addAll(gsonHomeBack.getCommodityProList());
                        } else {
                            recommendCommodityList.clear();
                        }
                        if (recommendCommodityList == null || recommendCommodityList.size() == 0) {
                            Toast.makeText(getActivity(), "暂无商品数据", Toast.LENGTH_SHORT).show();
                        }
                        mHandler.obtainMessage(HANDLE_REFRESH).sendToTarget();
                    } else {
                        // emptyView.setVisibility(View.GONE);
                        // mListView.setEmptyView(emptyFailureView);
                        mHandler.sendEmptyMessageDelayed(HANDLE_CANCEL_REFRESH,
                                HANDLE_CANCEL_WAIT);
                    }
                } catch (Exception e) {
                    Log.e("mikes", "RefreshTask error:", e);
                    mHandler.sendEmptyMessageDelayed(HANDLE_CANCEL_REFRESH,
                            HANDLE_CANCEL_WAIT);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                stopLoadingAnim();
                mHandler.sendEmptyMessageDelayed(HANDLE_CANCEL_REFRESH,
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
            getloadMore();
        } else {
            LogUtils.d("onLoad  no more datas");
            Toast.makeText(getActivity(),
                    getActivity().getString(R.string.no_more_data),
                    Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(HANDLE_CANCEL_LOAD,
                    HANDLE_CANCEL_WAIT);
        }
    }

    @Override
    public void onRefresh() {
        LogUtils.d("onrefresh");
        nowPage = 1;
        load();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == mGroup) {
            checkId = mGroup.getCheckedRadioButtonId();
            switch (checkId) {
                case R.id.rb_tab_recommend:
                    mRadiorecomcopy.setChecked(true);
                    orderId = "1";
                    break;
                case R.id.rb_tab_heat:
                    mRadioheatcopy.setChecked(true);
                    orderId = "2";
                    break;
                case R.id.rb_tab_fresh:
                    mRadiofreshcopy.setChecked(true);
                    orderId = "3";
                    break;
                case R.id.rb_tab_nearby:
                    mRadionearbycopy.setChecked(true);
                    orderId = "4";
                    break;
            }
        } else {
            checkId = mGroupcopy.getCheckedRadioButtonId();
            switch (checkId) {
                case R.id.rb_tab_recommend_copy:
                    mRadiorecom.setChecked(true);
                    orderId = "1";
                    break;
                case R.id.rb_tab_heat_copy:
                    mRadioheat.setChecked(true);
                    orderId = "2";
                    break;
                case R.id.rb_tab_fresh_copy:
                    mRadiofresh.setChecked(true);
                    orderId = "3";
                    break;
                case R.id.rb_tab_nearby_copy:
                    mRadionearby.setChecked(true);
                    orderId = "4";
                    break;
            }
        }
//        mListView.setSelection(1);
        nowPage = 1;
        refresh();
    }

    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new AdvAutoTask(), 3, 5,
                TimeUnit.SECONDS);
    }

    private void stopPlay() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }

    @Override
    public void toMoreClick(int i) {
        Intent intent = new Intent(getActivity(), MymoreResourcesandServiceMainActivity.class);
        intent.putExtra("proUserId", recommendCommodityList.get(i).getUserId());
        startActivity(intent);
    }

    @Override
    public void toDetailClick(int i) {
        Intent intent = new Intent(getActivity(), ShopDetailInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("proName", recommendCommodityList.get(i).getProName());
        bundle.putString("proId", recommendCommodityList.get(i).getProId());
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    class AdvPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mAdvViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mAdvViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mAdvViewList.get(position));
            return mAdvViewList.get(position);
        }
    }

    class AdvAutoTask implements Runnable {
        @Override
        public void run() {
            curPos = (curPos + 1) % mAdvViewList.size();
            mHandler.obtainMessage(HANDLE_ADV).sendToTarget();
        }
    }

    private void initAdvertisement() {
        mAdvViewPager = (ViewPager) headerView
                .findViewById(R.id.id_home_viewPager);

        mAdvPagerAdapter = new AdvPagerAdapter();
        mAdvViewPager.setAdapter(mAdvPagerAdapter);
        mAdvViewPager.setOnPageChangeListener(advPageChangeListener);
        mAdvViewPager.setCurrentItem(0);

        CirclePageIndicator indicator = (CirclePageIndicator) headerView
                .findViewById(R.id.id_home_indicator);
        indicator.setViewPager(mAdvViewPager);
    }

    private void initAdvCommodity() {
        mAdvViewList.clear();
        if (scrollCommodityList.size() == 0) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(18);
            // textView.setText("抱歉,没有滚动图片");
            ViewGroup advContainer = (ViewGroup) mInflater.inflate(
                    R.layout.home_adv_container, null);
            advContainer.addView(textView);
            mAdvViewList.add(advContainer);
        } else {
            for (int i = 0; i < scrollCommodityList.size(); i++) {
                final int position = i;
                ImageView scrollImage = new ImageView(mContext);
                scrollImage.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                scrollImage.setScaleType(ScaleType.FIT_XY);
                scrollImage.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(getActivity()
                                .getApplicationContext(),
                                ShopDetailInfoActivity.class);
                        Bundle bundle = new Bundle();
                       /* bundle.putSerializable("commodity",
                                scrollCommodityList.get(position));*/
                        bundle.putString("proId", scrollCommodityList.get(position).getProId());
                        bundle.putString("proName", scrollCommodityList.get(position).getProName());
                        intent.putExtras(bundle);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        getActivity().startActivity(intent);
                    }
                });

                if (TextUtils.isEmpty(scrollCommodityList.get(i).getProImageUrl())) {
                    Picasso.with(mContext).load(R.drawable.ic_default)
                            .into(scrollImage);
                } else {
                    Picasso.with(mContext)
                            .load(scrollCommodityList.get(i).getProImageUrl())
                            .into(scrollImage);
                }
//                scrollImage.setBackgroundResource(scrollCommodityList.get(i));
                ViewGroup advContainer = (ViewGroup) mInflater.inflate(
                        R.layout.home_adv_container, null);
                advContainer.addView(scrollImage);
                mAdvViewList.add(advContainer);
            }
        }

        mAdvPagerAdapter.notifyDataSetChanged();
    }

    private class GvAdapter extends BaseAdapter {

        public GvAdapter(Context context) {

        }

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public Object getItem(int position) {
            return newsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_news, null);
                holder.author = (TextView) convertView.findViewById(R.id.tv_news_author);
                holder.count = (TextView) convertView.findViewById(R.id.tv_news_count);
                holder.header = (ImageView) convertView.findViewById(R.id.iv_news_header);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            GsonCompanyBack.CompanyNews data = newsList.get(position);
            holder.count.setText(data.getCompanyNewsTitle());
            holder.author.setText(data.getUserName());
            if (data.getUserIcon() != null) {
                Picasso.with(mContext).load(data.getUserIcon()).placeholder(R.drawable.ic_default).into(holder.header);
            }
            return convertView;
        }

        private class ViewHolder {
            TextView count;
            TextView author;
            ImageView header;
        }
    }
}