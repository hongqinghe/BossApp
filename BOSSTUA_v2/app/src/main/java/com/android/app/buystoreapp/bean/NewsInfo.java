package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class NewsInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5303625574525502622L;
    private String newsID;
    private String newsTitle;
    private String newsSubTitle;
    private String newsImage;
    private String newsUrl;
    private String stateType;

    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsSubTitle() {
        return newsSubTitle;
    }

    public void setNewsSubTitle(String newsSubTitle) {
        this.newsSubTitle = newsSubTitle;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }

    @Override
    public String toString() {
        return "NewsInfo [newsID=" + newsID + ", newsTitle=" + newsTitle
                + ", newsSubTitle=" + newsSubTitle + ", newsImage=" + newsImage
                + ", newsUrl=" + newsUrl + ", stateType=" + stateType + "]";
    }

}
