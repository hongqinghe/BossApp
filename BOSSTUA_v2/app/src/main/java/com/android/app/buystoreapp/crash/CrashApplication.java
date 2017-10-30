package com.android.app.buystoreapp.crash;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.CityInfoBean;
import com.android.app.buystoreapp.bean.GsonCityBack;
import com.android.app.buystoreapp.im.MessageListActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrashApplication extends MultiDexApplication implements AMapLocationListener {
    public static double latitude = 0;
    public static double longitude = 0;
    public static String cityName;
    public static String street;
    public static String adname;
    public AMapLocationClientOption mLocationOption = null;
    private static Map<String, String> cityIndex;
    private static List<CityInfoBean> citys;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    public static ArrayList<BaseAct> allActivity = new ArrayList<BaseAct>();
    public static Context contex;
    public static String device_token = "";

    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
        contex = getApplicationContext();
        LogUtils.customTagPrefix = "mikes";
        LogUtils.allowD = true;
        LogUtils.allowE = true;
        /*
        * 友盟初始化
        */
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {
                device_token = s;
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
        /**
         * 自定义行为的回调处理
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                if ("1".equals(msg.custom) || "2".equals(msg.custom) || "3".equals(msg.custom)) {
                    Intent intent = new Intent(getApplicationContext(), MessageListActivity.class);
                    intent.putExtra("title", msg.title);
                    intent.putExtra("state", Integer.valueOf(msg.custom));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        // 初始化SharedPreferences
        SharedPreferenceUtils.getInstance().init(contex);
        /*
        * 高德地图定位初始化
        * */

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
//        //获取最近3s内精度最高的一次定位结果：
        mLocationOption.setOnceLocationLatest(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        RequestCityList();

        /*
        * 环信初始化
        * */
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(getApplicationContext(), options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                latitude = aMapLocation.getLatitude();
                longitude = aMapLocation.getLongitude();
                cityName = aMapLocation.getCity();
                street = aMapLocation.getStreet();
                adname = aMapLocation.getDistrict();
                if (mLocationClient.isStarted()) {
                    mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁();
                }
                Log.d("-aMapLocation-", cityName + street);
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    public void RequestCityList() {
        RequestParams params = new RequestParams();
        params.put("json", "{\"cmd\": \"getCity\"}");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getResources().getString(R.string.web_url), params, new com.loopj.android.http.AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                // TODO Auto-generated method stub
                GsonCityBack gsonCityBack = new Gson().fromJson(
                        new String(arg2),
                        new TypeToken<GsonCityBack>() {
                        }.getType());

                String result = gsonCityBack.getResult();
                if ("1".equals(result)) {
                    return;
                }
                citys = gsonCityBack.getCityList();
                cityIndex = new HashMap<String, String>();
                for (CityInfoBean bean : citys) {
                    cityIndex.put(bean.getAreaname(), bean.getId());
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                // TODO Auto-generated method stub

            }
        });
    }

    public static List<CityInfoBean> getCityList() {
        if (citys == null || citys.size() == 0) {
            return null;
        }
        return citys;
    }

    public static CityInfoBean getCityByName(String cityName) {
        if (cityIndex == null || cityIndex.size() == 0) {
            return null;
        }
        cityName = cityName.replace("市", "");
        String cityId = cityIndex.get(cityName);
        if (TextUtils.isEmpty(cityId)) {
            return null;
        }
        CityInfoBean bean = new CityInfoBean();
        bean.setId(cityId);
        bean.setAreaname(cityName);
        bean.setStreet(street);
        bean.setAreaname(adname);
        return bean;
    }

    public static BaseAct getActivityByName(String name) {
        for (BaseAct baseAct : allActivity) {
            if (baseAct.getClass().getName().indexOf(name) >= 0) {
                return baseAct;
            }
        }
        return null;
    }
}
