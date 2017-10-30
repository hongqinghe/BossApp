package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
public  class UserAndProManagerBean implements Serializable {
    private static final long serialVersionUID = 3832152536897554256L;
    private int attentionIsOff;
    private String bindingMark1;
    private String bindingMark2;
    private String bindingMark3;
    private String bindingMark4;
    private String headicon;
    private String nickname;
    private String userAttentionNum;
    private String userLevelMark;
    private String userPosition;
    private String userTreasure;
    private String companyBrand;

    public String getCompanyBrand() {
        return companyBrand;
    }

    public void setCompanyBrand(String companyBrand) {
        this.companyBrand = companyBrand;
    }

    /**
     * imgList : [{"isCover":0,"proImageId":"68Qm8u1Pa84teQJl",
     * "proImageMin":"http://localhost:8080/abc.png","proImageUrl":"http://localhost:8080/a
     * .png"}]
     * distance : 739
     * moreGroPrice : 43.0~455.0
     * moreGroUnit : 元
     * proDes : 清仓大处理
     * proName : iphone70
     * proSale : 48
     * proSeeNum : 1
     * proSurplus : 0
     */

    private List<UserAllProductBean> userAllProduct;

    public int getAttentionIsOff() {
        return attentionIsOff;
    }

    public void setAttentionIsOff(int attentionIsOff) {
        this.attentionIsOff = attentionIsOff;
    }

    public String getBindingMark1() {
        return bindingMark1;
    }

    public void setBindingMark1(String bindingMark1) {
        this.bindingMark1 = bindingMark1;
    }

    public String getBindingMark2() {
        return bindingMark2;
    }

    public void setBindingMark2(String bindingMark2) {
        this.bindingMark2 = bindingMark2;
    }

    public String getBindingMark3() {
        return bindingMark3;
    }

    public void setBindingMark3(String bindingMark3) {
        this.bindingMark3 = bindingMark3;
    }

    public String getBindingMark4() {
        return bindingMark4;
    }

    public void setBindingMark4(String bindingMark4) {
        this.bindingMark4 = bindingMark4;
    }

    public String getHeadicon() {
        return headicon;
    }

    public void setHeadicon(String headicon) {
        this.headicon = headicon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserAttentionNum() {
        return userAttentionNum;
    }

    public void setUserAttentionNum(String userAttentionNum) {
        this.userAttentionNum = userAttentionNum;
    }

    public String getUserLevelMark() {
        return userLevelMark;
    }

    public void setUserLevelMark(String userLevelMark) {
        this.userLevelMark = userLevelMark;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getUserTreasure() {
        return userTreasure;
    }

    public void setUserTreasure(String userTreasure) {
        this.userTreasure = userTreasure;
    }

    public List<UserAllProductBean> getUserAllProduct() {
        return userAllProduct;
    }

    public void setUserAllProduct(List<UserAllProductBean> userAllProduct) {
        this.userAllProduct = userAllProduct;
    }


}