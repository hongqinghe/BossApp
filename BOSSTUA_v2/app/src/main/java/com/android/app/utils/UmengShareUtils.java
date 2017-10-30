package com.android.app.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.app.buystoreapp.R;
import com.umeng.socialize.bean.CustomPlatform;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.OnSnsPlatformClickListener;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * 友盟分享工具类
 *
 * @author tangxian
 */
public class UmengShareUtils {

    private Activity mActivity;

    /**
     * 友盟分享服务
     */
    private final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng" +
            ".share");


    final String WXAppId = "wxa2b94b4c31229c30";
    final String WXAppSecret = "6183fb2928f74bbbbb52db2c1e309f60";
    final String QQAppId = "101377560";
//    final String QQAppKey = "a7yztF1o6vJCthEx";
    final String QQAppKey = "669af7268069a769ec93a094fddafbc8";
//	/**QQ app id*/
//	private static final String QQAppId = "100424468";
//
//	/**QQ app key*/
//	private static final String QQAppKey = "c7394704798a158208a74ab60104f0ba";
//
//
//	// 注意：在微信授权的时候，必须传递appSecret
//	// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
//	private final String WXAppId = "wx00e9e0be2a3b5b5e";
//	private final String WXAppSecret = "c6e74a086068107f713404b01e460597";

    /**
     * 要分享的内容
     */
    private String content;

    /**
     * 分享的url
     */
    private String url = "http://www.meituan.com/shop/1780292.html";

    private UMImage image;

    /**
     * 分享的标题
     */
    private String title = "友盟分享测试";

    public UmengShareUtils(final Activity mActivity, String content, final String url, String
			title, UMImage urlImage) {
        this.mActivity = mActivity;
        this.content = content;
        this.url = url;
        this.title = title;
        this.image = urlImage;
        configPlatforms();
        setShareContent();
        //设置分享面板显示的内容
        mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
                SHARE_MEDIA.DOUBAN, SHARE_MEDIA.TENCENT);
        mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                SHARE_MEDIA.SINA);
        CustomPlatform copyLink = new CustomPlatform("copy_link", "复制链接", R.drawable.ic_copy_link);
        copyLink.mClickListener = new OnSnsPlatformClickListener() {

            @Override
            public void onClick(Context arg0, SocializeEntity arg1, SnsPostListener arg2) {
                copy(url, mActivity);
                Toast.makeText(mActivity, "复制链接成功",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mController.getConfig().addCustomPlatform(copyLink);
    }


    @SuppressLint("NewApi")
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context
				.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }


    /**
     * 调用分享面板
     */
    public void share() {
        mController.openShare(mActivity, false);
    }


    private void configPlatforms() {
        // 添加新浪SSO授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        // 添加QQ、QZone平台
        addQQQZonePlatform();

        // 添加微信、微信朋友圈平台
        addWXPlatform();
    }


    /**
     * @return
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     * image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     * 要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     * : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     */
    private void addQQQZonePlatform() {

        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mActivity, QQAppId,
                QQAppKey);
        qqSsoHandler.setTitle(content);
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(mActivity, QQAppId,
                QQAppKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    /**
     * @return
     * @功能描述 : 添加微信平台分享
     */
    private void addWXPlatform() {

        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(mActivity, WXAppId, WXAppSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(mActivity, WXAppId, WXAppSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    /**
     * 根据不同的平台设置不同的分享内容</br>
     */
    public void setShareContent() {
        //分享图片
        UMImage urlImage = image;

        //设置微信分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(content);
        weixinContent.setTitle(title);
        weixinContent.setTargetUrl(url);
        weixinContent.setShareMedia(urlImage);
        mController.setShareMedia(weixinContent);

        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(content);
        circleMedia.setTitle(title);
        circleMedia.setTargetUrl(url);
        circleMedia.setShareMedia(urlImage);
        mController.setShareMedia(circleMedia);


        // 设置QQ空间分享内容
        // 分享到QQ空间 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(mActivity,
                QQAppId, QQAppKey);
        qZoneSsoHandler.addToSocialSDK();
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent(content);
        qzone.setTitle(title);
        qzone.setTargetUrl(url);
        qzone.setShareMedia(urlImage);
        mController.setShareMedia(qzone);

        //设置QQ分享内容
        // 分享给QQ好友 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mActivity, QQAppId,
                QQAppKey);
        qqSsoHandler.addToSocialSDK();
        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setTitle(title);
        qqShareContent.setShareContent(content);
        qqShareContent.setTargetUrl(url);
        qqShareContent.setShareMedia(urlImage);
        mController.setShareMedia(qqShareContent);

        //设置新浪分享内容
        SinaShareContent sinaContent = new SinaShareContent();
        sinaContent.setShareContent(content);
        sinaContent.setTitle(title);
        sinaContent.setTargetUrl(url);
        sinaContent.setShareMedia(urlImage);
        mController.setShareMedia(sinaContent);

        mController.registerListener(mSnsPostListener);
        mController.getConfig().closeToast();
    }

    //	 微信回调
    SnsPostListener mSnsPostListener = new SnsPostListener() {

        @Override
        public void onStart() {
            Toast.makeText(mActivity, "开始分享", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int stCode,
                               SocializeEntity entity) {
            if (stCode == 200) {
                Toast.makeText(mActivity, "分享成功", Toast.LENGTH_SHORT)
                        .show();
            } else if (stCode == 40000) {
                Toast.makeText(mActivity, "取消分享", Toast.LENGTH_SHORT)
                        .show();
            }else {
                Toast.makeText(mActivity, "分享失败 : error code : " + stCode, Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 如需使用sso需要在onActivity中调用此方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void authSSO(int requestCode, int resultCode, Intent data) {
        /** 使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

}
