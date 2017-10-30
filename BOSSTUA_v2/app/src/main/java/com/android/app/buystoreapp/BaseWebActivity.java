package com.android.app.buystoreapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.ProgressWebView;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.bean.GsonBackOnlyResult;
import com.android.app.buystoreapp.bean.GsonStarNewsCmd;
import com.android.app.buystoreapp.bean.NewsInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import org.apache.http.Header;

public class BaseWebActivity extends Activity {

    protected ProgressWebView mWebView;
    private String mNewsID;
    private String currentUserName;
    private String mNewsUrl;
    private String mNewsSubTitle;
    private String mNewsTitle;
    private boolean isStared;
    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;
    @ViewInject(R.id.id_custom_favourite_action)
    private ImageView mFavouriteImage;
    @ViewInject(R.id.id_custom_share_action)
    private ImageView mShareImage;

    private String shared_content = "Boss团，为你而来，与你同在";
    private String shared_url = "";

    /**
     * 友盟分享设置
     */
    // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.share");
    
    final String wxAppID = "wxa2b94b4c31229c30";
    final String wxAppSecret = "3e29b40ff998d4b6b9bd5d472cb86ed8";

    String tecentAppID = "1104758747";
    String tecentAppSecret = "a7yztF1o6vJCthEx";
    /**
     * 微博还在审核，暂时分享用不了
     */
    String sinaAppID = "2695404996";
    String sinaAppSecret = "bd70de650af93207b1e7ec81167e2547";

    // 微信回调
    SnsPostListener mSnsPostListener = new SnsPostListener() {

        @Override
        public void onStart() {
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int stCode,
                SocializeEntity entity) {
            if (stCode == 200) {
                Toast.makeText(BaseWebActivity.this, "分享成功", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(BaseWebActivity.this,
                        "分享失败 : error code : " + stCode, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_baseweb);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_action_bar);
        ViewUtils.inject(this);
        mWebView = (ProgressWebView) findViewById(R.id.baseweb_webview);

        initWebViewSetting(mWebView.getSettings());
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:function setTop(){" +
                        "document.getElementsByClassName('footer-docs smart-footer ui-footer ui-bar-a ui-footer-fixed slideup')[1].style.display = 'none'" +
                        "}setTop();");
                view.loadUrl("javascript:function setTop(){" +
                        "document.getElementById('div-gpt-ad-1466480176223-0').style.display = 'none'" +
                        "}setTop();");
                view.loadUrl("javascript:function setTop(){" +
                        "document.getElementsByClassName('smart-header ui-header ui-bar-a ui-header-fixed slidedown')[1].style.display = 'none'" +
                        "}setTop();");
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient());


        String type = getIntent().getStringExtra("type");
        if ("download".equals(type)) {
            mTitleText.setText("版本更新");
            String url = getIntent().getStringExtra("url");
            mWebView.loadUrl(url);
        } else if ("order".equals(type)) {
            mTitleText.setText("物流查询");
            String url = getIntent().getStringExtra("url");
            Log.e("物流接口--",url);
            mWebView.loadUrl(url);
        } else if ("message".equals(type)) {
            mTitleText.setText("通知消息");
            String url = getIntent().getStringExtra("url");
            mWebView.loadUrl(url);
        }else if("openShop".equals(type)){
        	mTitleText.setText("我要开店");
        	String url = "http://www.bosstuan.cn";
            mWebView.loadUrl(url);		
        }else {
            mFavouriteImage.setVisibility(View.VISIBLE);
            mShareImage.setVisibility(View.VISIBLE);
            mTitleText.setText("干货");
            currentUserName = SharedPreferenceUtils.getCurrentUserInfo(this)
                    .getUserName();
            initNewsData();
            // 设置友盟分享
            initUmengShareCompounets();
        }
    }

    private void initWebViewSetting(WebSettings ws) {
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
       // ws.setBuiltInZoomControls(true);// 支持缩放
       /* ws.setDisplayZoomControls(false);// 隐藏webview缩放按钮
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);*/
        ws.setBuiltInZoomControls(true);
        ws.setJavaScriptEnabled(true);
        ws.setRenderPriority(WebSettings.RenderPriority.HIGH);
        ws.setBlockNetworkImage(true);
        ws.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);//适应手机屏幕
        ws.setLoadWithOverviewMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isStared) {
            mFavouriteImage
                    .setImageResource(R.drawable.ic_custom_favourite_selected);
        } else {
            mFavouriteImage
                    .setImageResource(R.drawable.ic_custom_favourite_unselected);
        }
    }

    @OnClick(R.id.id_custom_share_action)
    public void doShare(View v) {
        // 是否只有已登录用户才能打开分享选择页
        mController.openShare(this, false);
    }

    @OnClick(R.id.id_custom_back_image)
    public void onCustomBarBackClicked(View v) {
        switch (v.getId()) {
        case R.id.id_custom_back_image:
            this.finish();
            break;
        default:
            break;
        }
    }

    @OnClick(R.id.id_custom_favourite_action)
    public void onFavouriteClicked(View v) {
        if (isStared) {
            isStared = false;
            mFavouriteImage
                    .setImageResource(R.drawable.ic_custom_favourite_unselected);
        } else {
            isStared = true;
            mFavouriteImage
                    .setImageResource(R.drawable.ic_custom_favourite_selected);
        }
        startFavoriteNews(isStared);
    }

    private void initNewsData() {
        NewsInfo newsInfo = (NewsInfo) getIntent().getSerializableExtra(
                "newsInfo");

        mNewsUrl = newsInfo.getNewsUrl();
        mNewsID = newsInfo.getNewsID();
        isStared = "0".equals(newsInfo.getStateType()) ? true : false;
        mNewsSubTitle = newsInfo.getNewsSubTitle();
        mNewsTitle = newsInfo.getNewsTitle();
        //shared_content = mNewsSubTitle;
        if(!TextUtils.isEmpty(mNewsSubTitle)){
        	shared_content = mNewsSubTitle;
        }
        shared_url = "http://m.bosstuan.cn/newshow.html?newsId="+mNewsID;
        mWebView.loadUrl(mNewsUrl);
    }

    private void startFavoriteNews(final boolean isStar) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        final Gson gson = new Gson();
        GsonStarNewsCmd gsonStarNews = new GsonStarNewsCmd("saveNewsFavourite",
                mNewsID, currentUserName, isStar ? "0" : "1");
        params.put("json", gson.toJson(gsonStarNews));
        LogUtils.d("startFavoriteNews,param=" + gson.toJson(gsonStarNews));

        client.get(getResources().getString(R.string.web_url), params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        LogUtils.d("startFavoriteNews result="
                                + new String(arg2));
                        try {
                            GsonBackOnlyResult gsonBackOnlyResult = gson
                                    .fromJson(
                                            new String(arg2),
                                            new TypeToken<GsonBackOnlyResult>() {
                                            }.getType());

                            String result = gsonBackOnlyResult.getResult();
                            String resultNote = gsonBackOnlyResult
                                    .getResultNote();
                            if ("0".equals(result)) {
                                if (isStar) {
                                    Toast.makeText(
                                            BaseWebActivity.this,
                                            getResources()
                                                    .getString(
                                                            R.string.news_star_save_success),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(
                                            BaseWebActivity.this,
                                            getResources()
                                                    .getString(
                                                            R.string.news_star_cancel_success),
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(BaseWebActivity.this,
                                        resultNote, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            LogUtils.e("startFavoriteNews error:", e);
                        }
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                            Throwable arg3) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** 使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    private void initUmengShareCompounets() {
        // com.umeng.socialize.utils.Log.LOG = true;
        // 删除默认分享中的腾讯微博,豆瓣,人人
        mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
                SHARE_MEDIA.DOUBAN, SHARE_MEDIA.TENCENT);
        mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                SHARE_MEDIA.SINA);
        // 设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        // 设置默认分享图片
        mController.setShareMedia(new UMImage(this, R.drawable.ic_launcher));
        mController.setShareContent(shared_content + "," + shared_url);

        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this, wxAppID, wxAppSecret);
        wxHandler.addToSocialSDK();
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this, wxAppID,
                wxAppSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        // 设置微信分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(shared_content);
        weixinContent.setTitle(mNewsTitle);
        weixinContent.setShareImage(new UMImage(this, R.drawable.ic_launcher));
        weixinContent.setTargetUrl(shared_url);
        mController.setShareMedia(weixinContent);
        // 设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setTitle(mNewsTitle);
        circleMedia.setShareContent(shared_content);
        circleMedia.setShareImage(new UMImage(this, R.drawable.ic_launcher));
        circleMedia.setTargetUrl(shared_url);
        mController.setShareMedia(circleMedia);
        // 设置微信回调
        mController.registerListener(mSnsPostListener);

        // 分享给QQ好友 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, tecentAppID,
                tecentAppSecret);
        qqSsoHandler.addToSocialSDK();
        QQShareContent qqShareContent = new QQShareContent();
        // 设置分享文字
        qqShareContent.setShareContent(shared_content);
        // 设置分享title
        qqShareContent.setTitle(mNewsTitle);
        // 设置分享图片
        qqShareContent.setShareImage(new UMImage(this, R.drawable.ic_launcher));
        // 设置点击分享内容的跳转链接
        qqShareContent.setTargetUrl(shared_url);
        mController.setShareMedia(qqShareContent);

        // 分享到QQ空间 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this,
                tecentAppID, tecentAppSecret);
        qZoneSsoHandler.addToSocialSDK();

        UMVideo umVideo = new UMVideo(shared_url);
        umVideo.setThumb(new UMImage(this, R.drawable.ic_launcher));
        umVideo.setTitle(mNewsTitle);
        QZoneShareContent qzone = new QZoneShareContent(umVideo);
        // 设置分享文字
        qzone.setShareContent(shared_content);
        mController.setShareMedia(qzone);
    }

}
