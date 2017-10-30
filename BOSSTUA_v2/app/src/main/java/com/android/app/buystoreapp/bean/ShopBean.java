package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class ShopBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -100291944306691559L;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 店铺ID
     */
    private String shopID;
    /**
     * /店铺经度
     */
    private String shopLong;
    /**
     * 店铺纬度
     */
    private String shopLat;
    /**
     * 店铺图片地址
     */
    private String shopIcon;
    /**
     * 店铺评分数 满分5分
     */
    private String shopScore;
    /**
     * 店铺介绍
     */
    private String shopIntro;
    /**
     * 距离当前位置的距离
     */
    private String shopDistance;
    /**
     * 店铺评论条数
     */
    private String shopTalkNum;

    /**
     * 商家店铺地址
     */
    private String shopAddress;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopLong() {
        return shopLong;
    }

    public void setShopLong(String shopLong) {
        this.shopLong = shopLong;
    }

    public String getShopLat() {
        return shopLat;
    }

    public void setShopLat(String shopLat) {
        this.shopLat = shopLat;
    }

    public String getShopIcon() {
        return shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public String getShopScore() {
        return shopScore;
    }

    public void setShopScore(String shopScore) {
        this.shopScore = shopScore;
    }

    public String getShopIntro() {
        return shopIntro;
    }

    public void setShopIntro(String shopIntro) {
        this.shopIntro = shopIntro;
    }

    public String getShopDistance() {
        return shopDistance;
    }

    public void setShopDistance(String shopDistance) {
        this.shopDistance = shopDistance;
    }

    public String getShopTalkNum() {
        return shopTalkNum;
    }

    public void setShopTalkNum(String shopTalkNum) {
        this.shopTalkNum = shopTalkNum;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    @Override
    public String toString() {
        return "ShopBean [shopName=" + shopName + ", shopID=" + shopID
                + ", shopLong=" + shopLong + ", shopLat=" + shopLat
                + ", shopIcon=" + shopIcon + ", shopScore=" + shopScore
                + ", shopIntro=" + shopIntro + ", shopDistance=" + shopDistance
                + ", shopTalkNum=" + shopTalkNum + ", shopAddress="
                + shopAddress + "]";
    }

}
