package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public  class LnbListBean implements Serializable{
    private int attentionIsOff;
    private String headicon;
    private String nickname;
    private int userAttentionNum;
    private int userLevelMark;
    private String userid;
    private String userPosition;
    private String companyBrand;

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public int getAttentionIsOff() {
        return attentionIsOff;
    }

    public void setAttentionIsOff(int attentionIsOff) {
        this.attentionIsOff = attentionIsOff;
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

    public int getUserAttentionNum() {
        return userAttentionNum;
    }

    public void setUserAttentionNum(int userAttentionNum) {
        this.userAttentionNum = userAttentionNum;
    }

    public int getUserLevelMark() {
        return userLevelMark;
    }

    public void setUserLevelMark(int userLevelMark) {
        this.userLevelMark = userLevelMark;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCompanyBrand() {
        return companyBrand;
    }

    public void setCompanyBrand(String companyBrand) {
        this.companyBrand = companyBrand;
    }
}