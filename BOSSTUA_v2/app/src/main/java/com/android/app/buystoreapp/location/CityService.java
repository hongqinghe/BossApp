package com.android.app.buystoreapp.location;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class CityService extends Service implements AMapLocationListener {

    private String city = "";
    private double lat, lon;
    /** 声明mlocationClient对象 */
    private AMapLocationClient mlocationClient = null;

    /** 声明mLocationOption对象 */
    private AMapLocationClientOption mLocationOption = null;
    private boolean isFirstLoc = true;
    Intent locationIntent = new Intent("com.android.app.getCityName");
    
    private int mStartId;

    @Override
    public void onCreate() {
        super.onCreate();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mStartId = startId;
        initLocation();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /**
     * 获取定位坐标
     */
    public void initLocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        //如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    /**
     * 高德定位回调
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {

        }
        if (amapLocation == null || !isFirstLoc)
            return;
        isFirstLoc = false;
        lat = amapLocation.getLatitude();
        lon = amapLocation.getLongitude();
        if (mlocationClient.isStarted()) {
            mlocationClient.stopLocation();
        }
        city =amapLocation.getCity();
        locationIntent.putExtra("cityName", city);
        locationIntent.putExtra("cityLon", String.valueOf(lon));
        locationIntent.putExtra("cityLat", String.valueOf(lat));
        locationIntent.putExtra("cityID", "");
        Log.d("mikes", "cityService  AMapLocation, city="
                + city + ",lon=" + lon + ",lat=" + lat);
        sendBroadcast(locationIntent);
        stopSelf(mStartId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mlocationClient.unRegisterLocationListener(this);
        mlocationClient = null;
    }
}
