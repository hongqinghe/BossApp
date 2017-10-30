package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/4.
 */
public  class BilDetailsBean implements Serializable{
    private double bilAmount;
    private String bilDate;
    private String bilId;
    private int bilState;
    private String famDateTime;
    private String headicon;
    private String proTitle;
    private int style;

    public double getBilAmount() {
        return bilAmount;
    }

    public void setBilAmount(double bilAmount) {
        this.bilAmount = bilAmount;
    }

    public String getBilDate() {
        return bilDate;
    }

    public void setBilDate(String bilDate) {
        this.bilDate = bilDate;
    }

    public String getBilId() {
        return bilId;
    }

    public void setBilId(String bilId) {
        this.bilId = bilId;
    }

    public int getBilState() {
        return bilState;
    }

    public void setBilState(int bilState) {
        this.bilState = bilState;
    }

    public String getFamDateTime() {
        return famDateTime;
    }

    public void setFamDateTime(String famDateTime) {
        this.famDateTime = famDateTime;
    }

    public String getHeadicon() {
        return headicon;
    }

    public void setHeadicon(String headicon) {
        this.headicon = headicon;
    }

    public String getProTitle() {
        return proTitle;
    }

    public void setProTitle(String proTitle) {
        this.proTitle = proTitle;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }}