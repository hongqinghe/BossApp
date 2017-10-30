package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc
 * Created by likaihang on 16/09/21.
 */
public class RelaseImageBean implements Serializable {
    private static final long serialVersionUID = -6262258036660656391L;
    /**
     * "proImageUrl":"/bosstuan/abc.png" ,       //图片URL
     * "proImageName":"abc.png"                    //图片名称
     */
    public String proImageUrl;
    public String proImageName;
    public int isCover;//封面

    public int getIsCover() {
        return isCover;
    }

    public void setIsCover(int isCover) {
        this.isCover = isCover;
    }

    public String getProImageUrl() {
        return proImageUrl;
    }

    public void setProImageUrl(String proImageUrl) {
        this.proImageUrl = proImageUrl;
    }

    public String getProImageName() {
        return proImageName;
    }

    public void setProImageName(String proImageName) {
        this.proImageName = proImageName;
    }

    @Override
    public String toString() {
        return "RelaseImageBean{" +
                "proImageUrl='" + proImageUrl + '\'' +
                ", proImageName='" + proImageName + '\'' +
                '}';
    }
}
