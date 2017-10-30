package com.android.app.buystoreapp.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.adapter.HomeProListAdapter;
import com.android.app.buystoreapp.adapter.NewsAdapter;
import com.android.app.buystoreapp.base.BaseFragment;
import com.android.app.buystoreapp.bean.CityInfoBean;
import com.android.app.buystoreapp.bean.GsonHomeBean;
import com.android.app.buystoreapp.bean.HomeProBean;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.buystoreapp.goods.ShopDetailInfoActivity;
import com.android.app.buystoreapp.managementservice.MymoreResourcesandServiceMainActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.view.SwipeRefreshLayoutUpDown;
import com.view.SwipeRefreshLayoutUpDown.OnLoadListener;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoundsContentFragment extends BaseFragment implements
        OnLoadListener, SwipeRefreshLayoutUpDown.OnRefreshListener, AdapterView.OnItemClickListener ,HomeProListAdapter.OnClickToMoreInterface{
    private ListView mNewsListView;
    private NewsAdapter mNewsAdapter;
    private String mCurrentUserName;
    private int mCurrentPosition = 0;
    private static final int HANDLE_REFRESH = 0x18;

    private String[] lab = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
    private HomeProListAdapter mHomeCommodityAdapter;
    private List<HomeProBean> recommendCommodityList = new ArrayList<HomeProBean>();
    private int nowPage = 1;
    private int totalPage;

    SwipeRefreshLayoutUpDown mSwipeLayout;
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
                    break;
                default:
                    break;
            }
        }

    };
    /**
     * 1全部,2约单,3转让,4需求,5交换,6招标,7众邦,8租赁,9合作,10共享,11免费,12经历
     */
    private String mNewsType = "1";
    private Context mContext;
    private int where;
    private String thisUser;
    private String cityID;
    private String userLat;
    private String userLong;
    private ListView mBusinessList;
    private int cityLevel = 2;
    private String cityName;


    public FoundsContentFragment() {
    }

    @SuppressLint("ValidFragment")
    public FoundsContentFragment(Context context, int position) {
        mContext = context;
        mCurrentPosition = position;
        LogUtils.d("position =" + position);
        mCurrentUserName = SharedPreferenceUtils.getCurrentUserInfo(mContext)
                .getUserName();
    }

    public FoundsContentFragment getInstance(Context context, int position) {

        return null;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.news_content, container, false);
        mBusinessList = (ListView) parent.findViewById(R.id.business_list);
        mBusinessList.setOnItemClickListener(this);
        mHomeCommodityAdapter = new HomeProListAdapter(mContext,
                recommendCommodityList);
        mBusinessList.setAdapter(mHomeCommodityAdapter);
        mHomeCommodityAdapter.setOnClickInterface(this);


        where = getActivity().getIntent().getIntExtra("where", 0);

        thisUser = SharedPreferenceUtils.getCurrentUserInfo(getActivity()).getUserId();
        CityInfoBean cityInfo = SharedPreferenceUtils
                .getCurrentCityInfo(getActivity());
        cityLevel = cityInfo.getLevel();
        cityID = cityInfo.getId();
        if ("".equals(cityID)) {
            cityID = "110100";
        }
        cityName = CrashApplication.cityName;
        userLat = "" + CrashApplication.latitude;//cityInfo.getCityLat();
        userLong = "" + CrashApplication.longitude;//cityInfo.getCityLong();
        mSwipeLayout = (SwipeRefreshLayoutUpDown) parent.findViewById(R.id.main_business_list_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light, R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setMode(SwipeRefreshLayoutUpDown.Mode.BOTH);
        mSwipeLayout.setLoadNoFull(true);


        return parent;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initErrorPage();
        addIncludeLoading();
        load();
    }

    @Override
    protected void load() {
        startWhiteLoadingAnim();
        refresh();
    }

    private void refresh() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getProductHome");
            obj.put("state", "00000000");
            obj.put("thisUser", thisUser);
            obj.put("classify", 0);
            obj.put("nowPage", nowPage);
            obj.put("userLat", userLat);
            obj.put("userLong", userLong);
            obj.put("screeningAll", lab[mCurrentPosition]);
            obj.put("statePriceLow", 0.0);
            obj.put("statePriceHigh", 0.0);
            obj.put("cityId", cityID);
            obj.put("cityName", cityName);
            obj.put("stateClassificationId", "");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("发现提交数据 obj==", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Log.e("发现返回数据 bytes==", "" + new String(arg2));
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
                            //ToastUtil.showMessageDefault(mContext, "暂无商品数据");
                            showEmpty("换个分类试试！");
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
                handler.sendEmptyMessageDelayed(HANDLE_CANCEL_REFRESH,
                        HANDLE_CANCEL_WAIT);
                stopLoadingAnim();
                showErrorPageState(nowPage);
            }
        });
    }


    private void getloadMore() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getProductHome");
            obj.put("state", 20000000);
            obj.put("thisUser", thisUser);
            obj.put("classify", 0);
            obj.put("nowPage", nowPage);
            obj.put("userLat", userLat);
            obj.put("userLong", userLong);
            obj.put("screeningAll", lab[mCurrentPosition]);
            obj.put("statePriceLow", 0.0);
            obj.put("statePriceHigh", 0.0);
            obj.put("cityId", cityID);
            obj.put("cityName", cityName);
            obj.put("stateClassificationId", "");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("发现提交数据 obj==", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Log.e("发现返回数据 bytes==", "" + new String(arg2));
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
                        recommendCommodityList.addAll(gsonHomeBack.getCommodityProList());
                        if (recommendCommodityList == null || recommendCommodityList.size() == 0) {
                            //ToastUtil.showMessageDefault(mContext, "暂无商品数据");
                            showEmpty("换个分类试试！");
                        }
                        handler.obtainMessage(HANDLE_LOADMORE).sendToTarget();
                    } else {
                        handler.sendEmptyMessageDelayed(HANDLE_CANCEL_LOAD,
                                HANDLE_CANCEL_WAIT);
                    }
                } catch (Exception e) {
                    Log.e("mikes", "RefreshTask error:", e);
                    handler.sendEmptyMessageDelayed(HANDLE_CANCEL_LOAD,
                            HANDLE_CANCEL_WAIT);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                stopLoadingAnim();
                handler.sendEmptyMessageDelayed(HANDLE_CANCEL_LOAD,
                        HANDLE_CANCEL_WAIT);
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
                showErrorPageState(nowPage);
                handler.sendEmptyMessageDelayed(HANDLE_CANCEL_LOAD,
                        HANDLE_CANCEL_WAIT);
            }
        });
    }


    /**
     * isVisibleToUser 设置fragment是否可见 在可见的时候加载，不可见的时候不加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            // 延迟加载，在可见的时候加载
            mNewsType = String.valueOf(mCurrentPosition);
            refresh();
        } else {
        }
    }

    @Override
    public void onLoad() {
        if (nowPage < totalPage) {
            nowPage++;
            getloadMore();
        } else {
            LogUtils.d("onLoad  no more datas");
            handler.sendEmptyMessageDelayed(HANDLE_CANCEL_LOAD,
                    HANDLE_CANCEL_WAIT);
            ToastUtil.showMessageDefault(mContext, mContext.getString(R.string.no_more_data));
//            mSwipeLayout.setLoading(false);
        }
    }

    @Override
    public void onRefresh() {
        nowPage = 1;
        refresh();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ShopDetailInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("proName", recommendCommodityList.get(position).getProName());
        bundle.putString("proId", recommendCommodityList.get(position).getProId());
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

    @Override
    public void toMoreClick(int i) {
        Intent intent = new Intent(getActivity(), MymoreResourcesandServiceMainActivity.class);
        intent.putExtra("proUserId", recommendCommodityList.get(i).getUserId());
        startActivity(intent);
    }
}
