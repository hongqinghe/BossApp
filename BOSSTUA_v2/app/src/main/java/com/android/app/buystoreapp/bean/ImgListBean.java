package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/14.
 */
public  class ImgListBean implements Serializable {
    private static final long serialVersionUID = -4026788719395705758L;
    private int isCover;
    private String proImageId;
    private String proImageMin;
    private String proImageUrl;

    public int getIsCover() {
        return isCover;
    }

    public void setIsCover(int isCover) {
        this.isCover = isCover;
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

    public String getProImageUrl() {
        return proImageUrl;
    }

    public void setProImageUrl(String proImageUrl) {
        this.proImageUrl = proImageUrl;
    }
}
