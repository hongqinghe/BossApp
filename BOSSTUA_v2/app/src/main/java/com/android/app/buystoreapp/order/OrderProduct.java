package com.android.app.buystoreapp.order;

import java.io.Serializable;

/**
 * 订单的商品列表
 * Created by 尚帅波 on 2016/10/24.
 */
public class OrderProduct implements Serializable {
    private static final long serialVersionUID = -4237818843736852487L;
    private String proId;
    private String moreName;
    private int proCount;
    private String proImgUrl;
    private String proName;
    private double proPrice;
    private String timeStart;
    private String timeEnd;
    private String weekStart;
    private String weekEnd;
    private double moreGroPrice;
    private int proStatus;

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public void setProPrice(double proPrice) {
        this.proPrice = proPrice;
    }

    public double getMoreGroPrice() {
        return moreGroPrice;
    }

    public void setMoreGroPrice(double moreGroPrice) {
        this.moreGroPrice = moreGroPrice;
    }

    public int getProStatus() {
        return proStatus;
    }

    public void setProStatus(int proStatus) {
        this.proStatus = proStatus;
    }

    public String getMoreName() {
        return moreName;
    }

    public void setMoreName(String moreName) {
        this.moreName = moreName;
    }


    public int getProCount() {
        return proCount;
    }

    public void setProCount(int proCount) {
        this.proCount = proCount;
    }

    public String getProImgUrl() {
        return proImgUrl;
    }

    public void setProImgUrl(String proImgUrl) {
        this.proImgUrl = proImgUrl;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public double getProPrice() {
        return proPrice;
    }

    public void setProPrice(int proPrice) {
        this.proPrice = proPrice;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(String weekStart) {
        this.weekStart = weekStart;
    }

    public String getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(String weekEnd) {
        this.weekEnd = weekEnd;
    }
}
