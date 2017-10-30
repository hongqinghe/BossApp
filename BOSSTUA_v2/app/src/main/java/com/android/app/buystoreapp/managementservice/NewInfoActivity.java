package com.android.app.buystoreapp.managementservice;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.GetNewsOneDetailsBean;
import com.android.app.buystoreapp.bean.GsonBackOnlyResult;
import com.android.app.buystoreapp.bean.NewsOneDetailsBean;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.android.app.utils.UmengShareUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.media.UMImage;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class NewInfoActivity extends BaseAct implements View.OnClickListener {
    private ImageButton ib_share;
    private ImageButton ib_collection;
    private ImageButton iv_back;
    private TextView tv_title;
    private ImageView iv_newinfo_head;
    private TextView tv_newinfo_name;
    private TextView tv_newinfo_position;
    private ImageView iv_newinfo_vip;
    private Button newinfo_acction;
    private WebView baseweb_webview;
    private TextView tv_subscribe_num;
    boolean isStar = false;
    private String userId;
    private String newsId;
    //    public static int flag = 0;
    private GetNewsOneDetailsBean newsDetails;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SubscriedDetailsActivity.HANDLE_LOADMORE:
                    updateCommodityStarState();
                    if (newsDetails != null) {
                        if (!TextUtils.isEmpty(newsDetails.getHeadicon())) {
                            Picasso.with(NewInfoActivity.this)
                                    .load(newsDetails.getHeadicon())
                                    .resize(45, 45)
                                    .placeholder(R.drawable.ic_speed_chat_head_default)
                                    .into(iv_newinfo_head);
                        }
                        tv_newinfo_name.setText(newsDetails.getNickname() + "");
                        if (newsDetails.getUserPosition() != null) {
                            tv_newinfo_position.setText("・" + newsDetails.getUserPosition());
                        } else {
                            tv_newinfo_position.setText("");
                        }
                        if (newsDetails.getUserLeveLmark() == 1) {
                            iv_newinfo_vip.setImageResource(R.drawable.vip_bojin);
                        } else if (newsDetails.getUserLeveLmark() == 2) {
                            iv_newinfo_vip.setImageResource(R.drawable.vip_zuanshi);
                        } else if (newsDetails.getUserLeveLmark() == 3) {
                            iv_newinfo_vip.setImageResource(R.drawable.vip_diwang);
                        } else {
                            iv_newinfo_vip.setVisibility(View.INVISIBLE);
                        }
                        if (newsDetails.getSubscribeIsOff() == 0) {
                            newinfo_acction.setText(getResources().getString(R.string.dy));
                            newinfo_acction.setBackgroundResource(R.drawable.corners_orange_bg);
                        } else if (newsDetails.getSubscribeIsOff() == 1) {
                            newinfo_acction.setText(getResources().getString(R.string.qdy));
                            newinfo_acction.setBackgroundResource(R.drawable.corners_f1f1f1_bg);
                        }
                        if (!TextUtils.isEmpty(newsDetails.getNewsUrl())) {
                            baseweb_webview.loadUrl(newsDetails.getNewsUrl());
                        }
                        tv_title.setText(newsDetails.getNewsTitle());
                        tv_subscribe_num.setText(newsDetails.getUserSubscribeNum() + "");
                    }
                    break;

                case DELETE_OK:
                    if (newsDetails.getSubscribeIsOff() == 0) {
                        ToastUtil.showMessageDefault(NewInfoActivity.this, "订阅成功");
                    } else {
                        ToastUtil.showMessageDefault(NewInfoActivity.this, "取消订阅成功");
                    }
                    load();
                    break;
            }
        }
    };
    private static final int DELETE_OK = 1000;
    private UmengShareUtils umengShareUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info);
        initErrorPage();
        addIncludeLoading(true);
        initView();
        setListener();
        newsId = getIntent().getStringExtra("newsId");
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();

        load();
    }

    private void setListener() {
        iv_back.setOnClickListener(this);
        newinfo_acction.setOnClickListener(this);
        ib_share.setOnClickListener(this);
        ib_collection.setOnClickListener(this);
    }


    private void initView() {
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        ib_share = (ImageButton) findViewById(R.id.ib_share);
        ib_collection = (ImageButton) findViewById(R.id.ib_collection);
        ib_share.setVisibility(View.VISIBLE);
        ib_collection.setVisibility(View.VISIBLE);

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_subscribe_num = (TextView) findViewById(R.id.tv_subscribe_num);

        iv_newinfo_head = (ImageView) findViewById(R.id.iv_newinfo_head);
        tv_newinfo_name = (TextView) findViewById(R.id.tv_newinfo_name);
        tv_newinfo_position = (TextView) findViewById(R.id.tv_newinfo_position);
        iv_newinfo_vip = (ImageView) findViewById(R.id.iv_newinfo_vip);
        newinfo_acction = (Button) findViewById(R.id.newinfo_acction);

        baseweb_webview = (WebView) findViewById(R.id.baseweb_webview);
        baseweb_webview.getSettings().setSupportZoom(false);
        baseweb_webview.getSettings().setBuiltInZoomControls(false);
        baseweb_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    protected void load() {
        super.load();
        startWhiteLoadingAnim();
        getNewsOneDetails();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
//                flag = 100;
//                mySetResult();
                finish();
                break;
            case R.id.newinfo_acction:
                startWhiteLoadingAnim();
                updateSubscribeAll();
                break;
            case R.id.ib_share:
                UMImage urlImage = new UMImage(this, BitmapFactory.decodeResource
                        (this.getResources(), R.drawable.ic_launcher));
                String url = newsDetails.getNewsUrl();
                String desc = newsDetails.getNewsTitle();
                umengShareUtils = new UmengShareUtils(this, desc, url, newsDetails.getNickname(),
                        urlImage);
                umengShareUtils.share();
                break;
            case R.id.ib_collection:
                isStar = !isStar;
                saveCommodity(isStar);
                break;
        }
    }

    /**
     * 商品收藏
     *
     * @param isStar true 收藏 false 取消
     */
    private void saveCommodity(final boolean isStar) {
        int state = 0;
        if (isStar) {
            state = 0;
        } else {
            state = 1;
        }
       /* AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        String param = "{\"cmd\":\"updateCollect\",\"commodityID\":\"aaa\",\"userName\":\"bbb\",\"stateType\":\"ccc\"}";
        param = param.replace("aaa", mCommodityID);
        param = param.replace("bbb", mCurrentUserName);
        param = param.replace("ccc", isStar ? "0" : "1");
        final Gson gson = new Gson();
        requestParams.put("json", param);
        Log.d("mikes", "ShopDetailInfoActivity saveCommodity param=" + param);*/
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "updateCollect");
            obj.put("userId", userId);
            obj.put("type", 3);
            obj.put("collectId", newsDetails.getNewsId());
            obj.put("state", state);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("----updateCollect----", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Log.e("----updateCollect----", new String(arg2));
                Gson gson = new Gson();
                GsonBackOnlyResult gsonBackOnlyResult = gson.fromJson(
                        new String(arg2), new TypeToken<GsonBackOnlyResult>() {
                        }.getType());

                String result = gsonBackOnlyResult.getResult();
                String resultNote = gsonBackOnlyResult.getResultNote();
                if ("0".equals(result)) {
                    if (isStar) {
                        Toast.makeText(
                                NewInfoActivity.this,
                                getResources().getString(
                                        R.string.news_star_save_success),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(
                                NewInfoActivity.this,
                                getResources().getString(
                                        R.string.news_star_cancel_success),
                                Toast.LENGTH_SHORT).show();
                    }
                    updateCommodityStarState();
                } else {
                    Toast.makeText(NewInfoActivity.this, resultNote,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                ToastUtil.showMessageDefault(mContext, "连接超时！");
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {

            }
        });
    }

    private void updateCommodityStarState() {

        if (isStar) {
            ib_collection.setImageResource(R.drawable.ic_collection_yellow);
        } else {
            ib_collection.setImageResource(R.drawable.ic_collection_black);
        }
    }


    public void getNewsOneDetails() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getNewsOneDetails");
            obj.put("newsId", newsId);
            obj.put("thisUserId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("查看新闻详情提交数据 obj=", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("查看新闻详情返回数据 bytes=", new String(bytes));
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                NewsOneDetailsBean newsOneDetailsBean = gson.fromJson(new String(bytes), new TypeToken<NewsOneDetailsBean>() {
                }.getType());
                String result = newsOneDetailsBean.getResult();
                if ("0".equals(result)) {
                    newsDetails = newsOneDetailsBean.getGetNewsOneDetails();
                    int isCollect = newsDetails.getIsCollect();
                    isStar = isCollect == 1 ? true : false;
                    mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(NewInfoActivity.this, getResources().getString(R.string.service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }

    private void updateSubscribeAll() {
        startLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "updateSubscribeAll");
            obj.put("thisUserId", userId);
            obj.put("subscribeUserId", newsDetails.getUserId());
//            obj.put("subscribeUserId", list.get(i).getSubscribeUserid());
            if (newsDetails.getSubscribeIsOff() == 0) {
                obj.put("subscribeIsOff", 1);
            } else if (newsDetails.getSubscribeIsOff() == 1) {
                obj.put("subscribeIsOff", 0);
            }

            Log.e("修改订阅状态提交数据", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("修改订阅状态返回数据", new String(bytes));
                stopLoadingAnim();
                hideErrorPageState();
                try {
                    String str = new String(bytes);
                    JSONObject object = new JSONObject(str);
                    String result = (String) object.get("result");
                    Log.e("result", "result=" + result);
                    if ("0".equals(result)) {
                        mHandler.obtainMessage(DELETE_OK).sendToTarget();
                    }
                } catch (Exception e) {
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


//    private void mySetResult() {
//        Intent intent = new Intent();
//        intent.putExtra("flag","100");
//        setResult(1,intent);
//    }
//    @Override
//    public void onBackPressed()
//    {
//        Log.e(TAG, "onBackPressed");
//        flag=100;
////        mySetResult();
//        super.onBackPressed();
//    }

}
