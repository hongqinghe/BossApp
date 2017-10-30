package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
public  class UserAllProductBean implements Serializable {
    private static final long serialVersionUID = 518246253038363254L;
    private String distance;
    private String moreGroPrice;
    private String moreGroUnit;
    private String proDes;
    private String serveLabelName;
    private String proName;
    private int proSale;
    private String proSeeNum;
    private int proSurplus;
    /**
     * 商品ID
     * */
    private String proId;
    /**
     * 差评
     * */
    private int badEvalNum;
    /**
     * 好评
     * */
    private int goodEvalNum;
    /**
     * 中评
     * */
    private int mediumEvalNum;

    /**
     * isCover : 0
     * proImageId : 68Qm8u1Pa84teQJl
     * proImageMin : http://localhost:8080/abc.png
     * proImageUrl : http://localhost:8080/a.png
     */

    private List<ImgListBean> imgList;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getServeLabelName() {
        return serveLabelName;
    }

    public void setServeLabelName(String serveLabelName) {
        this.serveLabelName = serveLabelName;
    }

    public String getMoreGroPrice() {
        return moreGroPrice;
    }

    public void setMoreGroPrice(String moreGroPrice) {
        this.moreGroPrice = moreGroPrice;
    }

    public String getMoreGroUnit() {
        return moreGroUnit;
    }

    public void setMoreGroUnit(String moreGroUnit) {
        this.moreGroUnit = moreGroUnit;
    }

    public String getProDes() {
        return proDes;
    }

    public void setProDes(String proDes) {
        this.proDes = proDes;
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

    public int getProSurplus() {
        return proSurplus;
    }

    public void setProSurplus(int proSurplus) {
        this.proSurplus = proSurplus;
    }

    public List<ImgListBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgListBean> imgList) {
        this.imgList = imgList;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public int getBadEvalNum() {
        return badEvalNum;
    }

    public void setBadEvalNum(int badEvalNum) {
        this.badEvalNum = badEvalNum;
    }

    public int getGoodEvalNum() {
        return goodEvalNum;
    }

    public void setGoodEvalNum(int goodEvalNum) {
        this.goodEvalNum = goodEvalNum;
    }

    public int getMediumEvalNum() {
        return mediumEvalNum;
    }

    public void setMediumEvalNum(int mediumEvalNum) {
        this.mediumEvalNum = mediumEvalNum;
    }



    @Override
    public String toString() {
        return "UserAllProductBean{" +
                "distance='" + distance + '\'' +
                ", moreGroPrice='" + moreGroPrice + '\'' +
                ", moreGroUnit='" + moreGroUnit + '\'' +
                ", proDes='" + proDes + '\'' +
                ", serveLabelName='" + serveLabelName + '\'' +
                ", proName='" + proName + '\'' +
                ", proSale=" + proSale +
                ", proSeeNum='" + proSeeNum + '\'' +
                ", proSurplus=" + proSurplus +
                ", imgList=" + imgList +
                '}';
    }
}