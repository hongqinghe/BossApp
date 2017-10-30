package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/11.
 */

public  class ListBean implements Serializable{
    private int state;
    private String vipDescribe;
    private int vipId;
    private String vipImg;
    private double vipOriginalPrice;
    private double vipPresentPrice;
    private String vipUnit;
    private String downDate;

    public String getDownDate() {
        return downDate;
    }

    public void setDownDate(String downDate) {
        this.downDate = downDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getVipDescribe() {
        return vipDescribe;
    }

    public void setVipDescribe(String vipDescribe) {
        this.vipDescribe = vipDescribe;
    }

    public int getVipId() {
        return vipId;
    }

    public void setVipId(int vipId) {
        this.vipId = vipId;
    }

    public String getVipImg() {
        return vipImg;
    }

    public void setVipImg(String vipImg) {
        this.vipImg = vipImg;
    }

    public double getVipOriginalPrice() {
        return vipOriginalPrice;
    }

    public void setVipOriginalPrice(double vipOriginalPrice) {
        this.vipOriginalPrice = vipOriginalPrice;
    }

    public double getVipPresentPrice() {
        return vipPresentPrice;
    }

    public void setVipPresentPrice(double vipPresentPrice) {
        this.vipPresentPrice = vipPresentPrice;
    }

    public String getVipUnit() {
        return vipUnit;
    }

    public void setVipUnit(String vipUnit) {
        this.vipUnit = vipUnit;
    }
}

