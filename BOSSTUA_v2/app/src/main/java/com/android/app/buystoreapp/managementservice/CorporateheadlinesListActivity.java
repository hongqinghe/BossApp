package com.android.app.buystoreapp.managementservice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.adapter.NewsAdapter;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.GsonNewsBack;
import com.android.app.buystoreapp.bean.GsonNewsCmd;
import com.android.app.buystoreapp.bean.NewsInfo;
import com.android.app.buystoreapp.bean.UserInfoBean;
import com.android.app.buystoreapp.setting.LoginActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.view.SwipeRefreshLayoutUpDown;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class CorporateheadlinesListActivity extends BaseAct implements SwipeRefreshLayoutUpDown
        .OnRefreshListener, SwipeRefreshLayoutUpDown.OnLoadListener {
    private NewsAdapter adapter;
    private ListView mNewsListView;
    private int totalPage;
    private int nowPage = 1;
    private String mNewsType = "1";
    private String mCurrentUserName;
    private List<NewsInfo> mTabNewsData = new ArrayList<NewsInfo>();
    private String url;
    //    private  String url="http://218.241.30.183:8080/buyService/service?";
    private UserInfoBean mUserInfo;
    private boolean isLogin = false;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corporateheadlines_list);
        mCurrentUserName = SharedPreferenceUtils.getCurrentUserInfo(this)
                .getUserName();

        mUserInfo = SharedPreferenceUtils.getCurrentUserInfo(this);
        isLogin = mUserInfo.isLogin();
        userId = mUserInfo.getUserId();
        url = mContext.getResources().getString(R.string.web_url);
        initView();
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        load();

    }

    @Override
    protected void load() {
        super.load();
        requestNewsFromHttp();
    }

    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string
                .corporate_head_lines));
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CorporateheadlinesListActivity.this.finish();
            }
        });
        mNewsListView = (ListView) findViewById(R.id.lv_corporate_headlines_list);
        adapter = new NewsAdapter(getApplicationContext(),
                mTabNewsData);
        mNewsListView.setAdapter(adapter);
        mNewsListView.setOnItemClickListener(mNewsItemListener);
    }


    private AdapterView.OnItemClickListener mNewsItemListener = new AdapterView
            .OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                long arg3) {
            if (isLogin) {

                Intent intent = new Intent(getApplicationContext(),
//                    BaseWebActivity.class);
                        NewInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("newsId", mTabNewsData.get(position).getNewsID());
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                ToastUtil.showMessageDefault(CorporateheadlinesListActivity.this,
                        getString(R.string.personal_no_login_toast));
                Intent loginIntent = new Intent(CorporateheadlinesListActivity.this,
                        LoginActivity.class);
                startActivity(loginIntent);
            }
        }
    };

    private void requestNewsFromHttp() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        final Gson gson = new Gson();
        GsonNewsCmd gsonNewsCmd = new GsonNewsCmd("getNews", mCurrentUserName,
                mNewsType, "20", String.valueOf(nowPage), "1");
        String param = gson.toJson(gsonNewsCmd);
        params.put("json", param);
        Log.e("shang", "requestNewsFromHttp:" + param);

        client.get(url, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        stopLoadingAnim();
                        hideErrorPageState();
                        LogUtils.d("requestNewsFromHttp,result="
                                + new String(arg2));
                        try {
                            GsonNewsBack gsonNewsBack = gson.fromJson(
                                    new String(arg2),
                                    new TypeToken<GsonNewsBack>() {
                                    }.getType());
                            String result = gsonNewsBack.getResult();

                            if ("0".equals(result)) {
                                totalPage = Integer.valueOf(gsonNewsBack
                                        .getTotalPage());
                                mTabNewsData.clear();
                                mTabNewsData.addAll(gsonNewsBack.getNewsList());
                                adapter.notifyDataSetChanged();
                            } else {// failure
                            }
//                            mSwipeLayout.setRefreshing(false);
                        } catch (Exception e) {
                            LogUtils.e("requestNewsFromHttp,error:", e);
//                            mSwipeLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
//                        mSwipeLayout.setRefreshing(false);
                        stopLoadingAnim();
                        ToastUtil.showMessageDefault(CorporateheadlinesListActivity.this,
                                getResources().getString(R.string.service_error_hint));
                    }
                });
    }

    private void loadmoreNewsFromHttp() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        final Gson gson = new Gson();
        GsonNewsCmd gsonNewsCmd = new GsonNewsCmd("getNews", mCurrentUserName,
                mNewsType, "20", String.valueOf(nowPage), "1");
        String param = gson.toJson(gsonNewsCmd);
        params.put("json", param);
        LogUtils.d("loadmoreNewsFromHttp:" + param);

        client.get(url, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        LogUtils.d("loadmoreNewsFromHttp,result="
                                + new String(arg2));
                        try {
                            GsonNewsBack gsonNewsBack = gson.fromJson(
                                    new String(arg2),
                                    new TypeToken<GsonNewsBack>() {
                                    }.getType());
                            String result = gsonNewsBack.getResult();

                            if ("0".equals(result)) {
                                totalPage = Integer.valueOf(gsonNewsBack
                                        .getTotalPage());
                                mTabNewsData.addAll(gsonNewsBack.getNewsList());
                                adapter.notifyDataSetChanged();
                            }
//                            mSwipeLayout.setLoading(false);
                        } catch (Exception e) {
                            LogUtils.e("loadmoreNewsFromHttp,error:", e);
//                            mSwipeLayout.setLoading(false);
                        }
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
//                        mSwipeLayout.setLoading(false);
                        stopLoadingAnim();
                        ToastUtil.showMessageDefault(CorporateheadlinesListActivity.this,
                                getResources().getString(R.string.service_error_hint));
                    }
                });
    }


    @Override
    public void onLoad() {
        if (nowPage < totalPage) {
            nowPage++;
            loadmoreNewsFromHttp();
        } else {
            LogUtils.d("onLoad  no more datas");
            Toast.makeText(mContext, mContext.getString(R.string.no_more_data),
                    Toast.LENGTH_SHORT).show();
//            mSwipeLayout.setLoading(false);
        }
    }

    @Override
    public void onRefresh() {
        nowPage = 1;
        requestNewsFromHttp();
    }
}
