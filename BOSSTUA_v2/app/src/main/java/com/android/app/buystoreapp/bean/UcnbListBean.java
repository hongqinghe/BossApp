package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/17.
 */
public  class UcnbListBean implements Serializable{
    private String newsContent;
    private String newsIcon;
    private String newsId;
    private int newsSeeNum;
    private String newsTitle;

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsIcon() {
        return newsIcon;
    }

    public void setNewsIcon(String newsIcon) {
        this.newsIcon = newsIcon;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public int getNewsSeeNum() {
        return newsSeeNum;
    }

    public void setNewsSeeNum(int newsSeeNum) {
        this.newsSeeNum = newsSeeNum;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
}
