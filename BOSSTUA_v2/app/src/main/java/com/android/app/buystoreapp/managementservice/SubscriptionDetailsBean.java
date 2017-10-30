package com.android.app.buystoreapp.managementservice;

import java.io.Serializable;
import java.util.List;

/**
 * 订阅详情实体Bean
 * weilin
 * Created by Administrator on 2016/10/14.
 */
public class SubscriptionDetailsBean implements Serializable{

    private static final long serialVersionUID = -8182631698708567566L;
    /**
     * bindingMark1 : 1
     * bindingMark2 : 0
     * bindingMark3 : 0
     * bindingMark4 : 0
     * gsbuiList : [{"newsId":"101","newsSeeNum":0,"newsTitle":"销售最有用的秘诀，抓住痛点、痒点、兴奋点","newsicon":""}]
     * headicon : defalt_usericon.png
     * isSubscribe : 0
     * nickname :
     * result : 0
     * userPosition :
     * userTreasure :
     * resultNote : 成功
     * userLevelMark : 0
     * userSubscribeNum : 0
     */

    private int bindingMark1;
    private int bindingMark2;
    private int bindingMark3;
    private int bindingMark4;
    private String headicon;
    private int isSubscribe;
    private String nickname;
    private String result;
    private String userPosition;
    private String userTreasure;
    private String resultNote;
    private int userLevelMark;
    private int userSubscribeNum;
    /**
     * newsId : 101
     * newsSeeNum : 0
     * newsTitle : 销售最有用的秘诀，抓住痛点、痒点、兴奋点
     * newsicon :
     */

    private List<GsbuiListBean> gsbuiList;

    public int getBindingMark1() {
        return bindingMark1;
    }

    public void setBindingMark1(int bindingMark1) {
        this.bindingMark1 = bindingMark1;
    }

    public int getBindingMark2() {
        return bindingMark2;
    }

    public void setBindingMark2(int bindingMark2) {
        this.bindingMark2 = bindingMark2;
    }

    public int getBindingMark3() {
        return bindingMark3;
    }

    public void setBindingMark3(int bindingMark3) {
        this.bindingMark3 = bindingMark3;
    }

    public int getBindingMark4() {
        return bindingMark4;
    }

    public void setBindingMark4(int bindingMark4) {
        this.bindingMark4 = bindingMark4;
    }

    public String getHeadicon() {
        return headicon;
    }

    public void setHeadicon(String headicon) {
        this.headicon = headicon;
    }

    public int getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(int isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public int getUserLevelMark() {
        return userLevelMark;
    }

    public void setUserLevelMark(int userLevelMark) {
        this.userLevelMark = userLevelMark;
    }

    public int getUserSubscribeNum() {
        return userSubscribeNum;
    }

    public void setUserSubscribeNum(int userSubscribeNum) {
        this.userSubscribeNum = userSubscribeNum;
    }

    public List<GsbuiListBean> getGsbuiList() {
        return gsbuiList;
    }

    public void setGsbuiList(List<GsbuiListBean> gsbuiList) {
        this.gsbuiList = gsbuiList;
    }

    public static class GsbuiListBean implements Serializable{
        private static final long serialVersionUID = -8370435084264690431L;
        private String newsId;
        private int newsSeeNum;
        private String newsTitle;
        private String newsicon;

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

        public String getNewsicon() {
            return newsicon;
        }

        public void setNewsicon(String newsicon) {
            this.newsicon = newsicon;
        }
    }
}
