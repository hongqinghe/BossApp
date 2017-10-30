package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public  class LpdbListBean implements Serializable{
    private String bindingMark1;
    private String bindingMark2;
    private String bindingMark3;
    private String bindingMark4;
    private String distanceKm;
    private String headicon;
    private String loginTime;
    private int moreGroSurplus;
    private String moreGroUnit;
    private String nickname;
    private String priceInterval;
    private String proDes;
    private String proId;
    private String proName;
    private int proSale;
    private String proSeeNum;
    private String serveLabel;
    private String serveLabelName;
    private String userId;
    private String userLevelMark;
    private String userPosition;
    private String userTreasure;
    /**
     * isCover : 1
     * proId :
     * proImageId : 7fS5bvo00E3QEB9w
     * proImageMin : http://192.168.1.122:8080/bossgroupimage/2016-10-31/proImgPath/proImgPathMin/20161031145453frRo.jpg
     * proImageName :
     * proImageUrl :
     * proName :
     */

    private List<ProImageMinBean> proImageMin;

    public String getBindingMark1() {
        return bindingMark1;
    }

    public void setBindingMark1(String bindingMark1) {
        this.bindingMark1 = bindingMark1;
    }

    public String getBindingMark2() {
        return bindingMark2;
    }

    public void setBindingMark2(String bindingMark2) {
        this.bindingMark2 = bindingMark2;
    }

    public String getBindingMark3() {
        return bindingMark3;
    }

    public void setBindingMark3(String bindingMark3) {
        this.bindingMark3 = bindingMark3;
    }

    public String getBindingMark4() {
        return bindingMark4;
    }

    public void setBindingMark4(String bindingMark4) {
        this.bindingMark4 = bindingMark4;
    }

    public String getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(String distanceKm) {
        this.distanceKm = distanceKm;
    }

    public String getHeadicon() {
        return headicon;
    }

    public void setHeadicon(String headicon) {
        this.headicon = headicon;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public int getMoreGroSurplus() {
        return moreGroSurplus;
    }

    public void setMoreGroSurplus(int moreGroSurplus) {
        this.moreGroSurplus = moreGroSurplus;
    }

    public String getMoreGroUnit() {
        return moreGroUnit;
    }

    public void setMoreGroUnit(String moreGroUnit) {
        this.moreGroUnit = moreGroUnit;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPriceInterval() {
        return priceInterval;
    }

    public void setPriceInterval(String priceInterval) {
        this.priceInterval = priceInterval;
    }

    public String getProDes() {
        return proDes;
    }

    public void setProDes(String proDes) {
        this.proDes = proDes;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getProSale() {
        return proSale;
    }

    public void setProSale(int proSale) {
        this.proSale = proSale;
    }

    public String getProSeeNum() {
        return proSeeNum;
    }

    public void setProSeeNum(String proSeeNum) {
        this.proSeeNum = proSeeNum;
    }

    public String getServeLabel() {
        return serveLabel;
    }

    public void setServeLabel(String serveLabel) {
        this.serveLabel = serveLabel;
    }

    public String getServeLabelName() {
        return serveLabelName;
    }

    public void setServeLabelName(String serveLabelName) {
        this.serveLabelName = serveLabelName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserLevelMark() {
        return userLevelMark;
    }

    public void setUserLevelMark(String userLevelMark) {
        this.userLevelMark = userLevelMark;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getUserTreasure() {
        return userTreasure;
    }

    public void setUserTreasure(String userTreasure) {
        this.userTreasure = userTreasure;
    }

    public List<ProImageMinBean> getProImageMin() {
        return proImageMin;
    }

    public void setProImageMin(List<ProImageMinBean> proImageMin) {
        this.proImageMin = proImageMin;
    }

}
