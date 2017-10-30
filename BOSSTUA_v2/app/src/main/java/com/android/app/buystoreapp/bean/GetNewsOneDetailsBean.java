package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/17.
 */
public  class GetNewsOneDetailsBean implements Serializable {
    private String headicon;
    private String newsContent;
    private String newsCreatetime;
    private String newsIcon;
    private String newsId;
    private int newsSeeNum;
    private String newsSubtitle;
    private String newsTitle;
    private String newsUrl;
    private String nickname;
    private String subscribeId;
    private int subscribeIsOff;
    private String userId;
    private int userLeveLmark;
    private String userPosition;
    private int userSubscribeNum;
    private int isCollect;

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public String getHeadicon() {
        return headicon;
    }

    public void setHeadicon(String headicon) {
        this.headicon = headicon;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsCreatetime() {
        return newsCreatetime;
    }

    public void setNewsCreatetime(String newsCreatetime) {
        this.newsCreatetime = newsCreatetime;
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

    public String getNewsSubtitle() {
        return newsSubtitle;
    }

    public void setNewsSubtitle(String newsSubtitle) {
        this.newsSubtitle = newsSubtitle;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSubscribeId() {
        return subscribeId;
    }

    public void setSubscribeId(String subscribeId) {
        this.subscribeId = subscribeId;
    }

    public int getSubscribeIsOff() {
        return subscribeIsOff;
    }

    public void setSubscribeIsOff(int subscribeIsOff) {
        this.subscribeIsOff = subscribeIsOff;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserLeveLmark() {
        return userLeveLmark;
    }

    public void setUserLeveLmark(int userLeveLmark) {
        this.userLeveLmark = userLeveLmark;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public int getUserSubscribeNum() {
        return userSubscribeNum;
    }

    public void setUserSubscribeNum(int userSubscribeNum) {
        this.userSubscribeNum = userSubscribeNum;
    }
}
