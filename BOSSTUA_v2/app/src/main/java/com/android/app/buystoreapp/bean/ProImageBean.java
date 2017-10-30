package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc
 * Created by likaihang on 16/09/14.
 */
public class ProImageBean implements Serializable {
    private static final long serialVersionUID = 9190290520419655319L;
    /**
     *是否封面
     */
    private int isCover;
    private String proImageId;
    /**
     *URL
     */
    private String proImageUrl;
private String proImageMin;



    public String getProImageMin() {
        return proImageMin;
    }

    public void setProImageMin(String proImageMin) {
        this.proImageMin = proImageMin;
    }

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

    public String getProImageUrl() {
        return proImageUrl;
    }

    public void setProImageUrl(String proImageUrl) {
        this.proImageUrl = proImageUrl;
    }

}
