package com.android.app.buystore.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.android.app.buystoreapp.bean.CityInfoBean;
import com.android.app.buystoreapp.bean.MarksBean;
import com.android.app.buystoreapp.bean.UserInfoBean;
import com.android.app.utils.Config;

public class SharedPreferenceUtils {
//    private static final String userIconDefault = "http://a.hiphotos.baidu
// .com/image/pic/item/cdbf6c81800a19d8fc5fdb0f31fa828ba61e465f.jpg";


    private static SharedPreferenceUtils instance;
    private static Editor editor;
    private static SharedPreferences sp;

    public static SharedPreferenceUtils getInstance() {
        if (null == instance) {
            instance = new SharedPreferenceUtils();
        }
        return instance;
    }

    public void init(Context context) {
        sp = context.getSharedPreferences(Config.SP_NAME,
                Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void setBoolean(String key, boolean value) {
        Editor e = sp.edit();
        e.putBoolean(key, value);
        e.commit();
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public void setInt(String key, int versionCode) {
        Editor e = sp.edit();
        e.putInt(key, versionCode);
        e.commit();
    }

    public int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public static void saveCurrentUserInfo(UserInfoBean userInfo,
                                           Context context, boolean isLogin) {
        editor.putBoolean("isLogin", isLogin);
        editor.putString("userIcon", userInfo.getUserIcon());
        editor.putString("userName", userInfo.getUserName());
        editor.putString("nickName", userInfo.getNickName());
        editor.putString("score", userInfo.getScore());
        editor.putString("ticketCount", userInfo.getTicketCount());
        editor.putString("thisUser", userInfo.getUserId());
        editor.putString("inviteCode", userInfo.getInviteCode());
        editor.putInt("bindingMark1", userInfo.getBindingMark1());
        editor.putInt("bindingMark2", userInfo.getBindingMark2());
        editor.putInt("bindingMark3", userInfo.getBindingMark3());
        editor.putInt("bindingMark4", userInfo.getBindingMark4());
        editor.putInt("userLevelMark", userInfo.getUserLevelMark());
        editor.putString("userTreasure", userInfo.getUserTreasure());
        editor.commit();
    }

    public static UserInfoBean getCurrentUserInfo(Context context) {
        UserInfoBean userInfo = new UserInfoBean();
        userInfo.setLogin(sp.getBoolean("isLogin", false));
        userInfo.setUserName(sp.getString("userName", ""));
        userInfo.setUserIcon(sp.getString("userIcon", ""));
        userInfo.setNickName(sp.getString("nickName", "boss"));
        userInfo.setScore(sp.getString("score", "0"));
        userInfo.setTicketCount(sp.getString("ticketCount", "0"));
        userInfo.setUserId((sp.getString("thisUser", "")));
        userInfo.setInviteCode(sp.getString("inviteCode", ""));
        userInfo.setBindingMark1(sp.getInt("bindingMark1", 0));
        userInfo.setBindingMark2(sp.getInt("bindingMark2", 0));
        userInfo.setBindingMark3(sp.getInt("bindingMark3", 0));
        userInfo.setBindingMark4(sp.getInt("bindingMark4", 0));
        userInfo.setUserLevelMark(sp.getInt("userLevelMark", 0));
        userInfo.setUserTreasure((sp.getString("userTreasure", "")));
        return userInfo;
    }

    public static void saveMarkInfo(MarksBean.BeanBean marksBean, Context context) {
        editor.putInt("bindingMark1", marksBean.getBindingMark1());
        editor.putInt("bindingMark2", marksBean.getBindingMark2());
        editor.putInt("bindingMark3", marksBean.getBindingMark3());
        editor.putInt("bindingMark4", marksBean.getBindingMark4());
        editor.putInt("userLevelMark", marksBean.getUserLevelMark());
        editor.putString("userTreasure", marksBean.getUserTreasure());
        editor.commit();
    }

    public static MarksBean.BeanBean getMarkInfo(Context context) {
        MarksBean.BeanBean marksBean = new MarksBean.BeanBean();
        marksBean.setBindingMark1(sp.getInt("bindingMark1", 0));
        marksBean.setBindingMark2(sp.getInt("bindingMark2", 0));
        marksBean.setBindingMark3(sp.getInt("bindingMark3", 0));
        marksBean.setBindingMark4(sp.getInt("bindingMark4", 0));
        marksBean.setUserLevelMark(sp.getInt("userLevelMark", 0));
        marksBean.setUserTreasure((sp.getString("userTreasure", "")));
        return marksBean;
    }

    public static void saveHeadicon(Context context, String icon) {
        editor.putString("headicon", icon).commit();
    }

    public static String getHeadicon(Context context) {
        return sp.getString("headicon", "");
    }

    public static void saveNickName(Context context, String nikeName) {
        editor.putString("nikeName", nikeName).commit();
    }

    public static String getNickName(Context context) {
        return sp.getString("nikeName", "");
    }


    public static void saveUserPosition(Context context, String userPosition) {
        editor.putString("userPosition", userPosition).commit();
    }

    public static String getUserPosition(Context context) {
        return sp.getString("userPosition", "");
    }


    public static void saveBossTicketCount(Context context, String count) {
        editor.putString("ticketCount", count);
        editor.commit();
    }

    public static String getBossTicketCount(Context context) {
        return sp.getString("ticketCount", "0");
    }

    public static void saveCurrentCityInfo(Context context,
                                           CityInfoBean cityInfoBean) {
        editor.putString("areaname", cityInfoBean.getAreaname());
        editor.putString("id", cityInfoBean.getId());
        editor.putString("cityLong", cityInfoBean.getCityLong());
        editor.putString("cityLat", cityInfoBean.getCityLat());
        editor.putString("isRecommendCity", cityInfoBean.getIsRecommendCity());
        editor.putString("street", cityInfoBean.getStreet());
        editor.commit();
    }

    public static CityInfoBean getCurrentCityInfo(Context context) {
        CityInfoBean cityInfo = new CityInfoBean();
        cityInfo.setId(sp.getString("id", ""));
        cityInfo.setAreaname(sp.getString("areaname", ""));
        cityInfo.setCityLong(sp.getString("cityLong", ""));
        cityInfo.setCityLat(sp.getString("cityLat", ""));
        cityInfo.setIsRecommendCity(sp.getString("isRecommendCity", ""));
        cityInfo.setStreet(sp.getString("street", ""));
        return cityInfo;
    }

    public static boolean getAppIsFirstLaunch(Context context) {

        return sp.getBoolean("launch", true);
    }

    public static void saveAppWhenFirstLaunch(Context context, boolean isFirstLaunch) {
        editor.putBoolean("launch", isFirstLaunch).apply();
    }

}
