package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc
 * Created by likaihang on 16/10/09.
 */
public class ShopDetailImage implements Serializable {
    private static final long serialVersionUID = -484980113871053850L;
    private int isCover;
    private String proImageMin;
    private String proImageUrl;

    public int getIsCover() {
        return isCover;
    }

    public void setIsCover(int isCover) {
        this.isCover = isCover;
    }

    public String getProImageMin() {
        return proImageMin;
    }

    public void setProImageMin(String proImageMin) {
        this.proImageMin = proImageMin;
    }

    public String getProImageUrl() {
        return proImageUrl;
    }

    public void setProImageUrl(String proImageUrl) {
        this.proImageUrl = proImageUrl;
    }
}
