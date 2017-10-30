package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc
 * Created by likaihang on 16/10/29.
 */
public class OrderPro implements Serializable{
    private static final long serialVersionUID = 3810258798656007507L;
    private String moreId;
   private int proCount;
   private String proImgUrl;
   private String shopCarId;

    public String getMoreId() {
        return moreId;
    }

    public void setMoreId(String moreId) {
        this.moreId = moreId;
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

    public String getShopCarId() {
        return shopCarId;
    }

    public void setShopCarId(String shopCarId) {
        this.shopCarId = shopCarId;
    }
}
