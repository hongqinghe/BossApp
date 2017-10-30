package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/9.
 */
public  class ProImageMinBean implements Serializable{
    private int isCover;
    private String proId;
    private String proImageId;
    private String proImageMin;
    private String proImageName;
    private String proImageUrl;
    private String proName;

    public int getIsCover() {
        return isCover;
    }

    public void setIsCover(int isCover) {
        this.isCover = isCover;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProImageId() {
        return proImageId;
    }

    public void setProImageId(String proImageId) {
        this.proImageId = proImageId;
    }

    public String getProImageMin() {
        return proImageMin;
    }

    public void setProImageMin(String proImageMin) {
        this.proImageMin = proImageMin;
    }

    public String getProImageName() {
        return proImageName;
    }

    public void setProImageName(String proImageName) {
        this.proImageName = proImageName;
    }

    public String getProImageUrl() {
        return proImageUrl;
    }

    public void setProImageUrl(String proImageUrl) {
        this.proImageUrl = proImageUrl;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }
}
