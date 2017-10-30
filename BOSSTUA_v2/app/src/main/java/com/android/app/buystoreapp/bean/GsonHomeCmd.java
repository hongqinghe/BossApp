package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonHomeCmd implements Serializable {

    /**
     * "cmd":"getProductHome",               //接口名称
     "cityId":110100,                     //市级城市ID
     "nowPage":1 ,                           //当前页数                      (int类型)
     "orderId":"2",                          //区分标示码0，推荐1，人气 2，最新 3，附近
     "userLong":"23.989767",              //查看商品时 用户的经度
     "userLat":"34.987654",                //查看商品时 用户的纬度
     "cityName":"北京",             //城市名称如果定位就给城市名称 如果手动就给””(双引号的空)
     "thisUser":"12343312",                //当前用户id
     "classify":1                            //大分类Id   0.特价服务  1. 闲置资源         （数据类型int）
     */
    private static final long serialVersionUID = -7977097973251464154L;
    private String cmd;
    private String cityId;
    private String cityName;
    private String orderId;
    private int nowPage;
    private String thisUser;
    private int classify;
    /**
     * 增加用户经纬度
     */
    private String userLat;
    private String userLong;

    public GsonHomeCmd(String cmd, String cityId,String cityName,String orderId,
            int nowPage,String thisuer,int classify,String userLat, String userLong) {
        super();
        this.cmd = cmd;
        this.cityId = cityId;
        this.cityName = cityName;
        this.orderId = orderId;
        this.nowPage = nowPage;
        this.thisUser = thisuer;
        this.classify = classify;
        this.userLat = userLat;
        this.userLong = userLong;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public String getThisUser() {
        return thisUser;
    }

    public void setThisUser(String thisUser) {
        this.thisUser = thisUser;
    }

    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }

    public String getUserLat() {
        return userLat;
    }

    public void setUserLat(String userLat) {
        this.userLat = userLat;
    }

    public String getUserLong() {
        return userLong;
    }

    public void setUserLong(String userLong) {
        this.userLong = userLong;
    }
}
