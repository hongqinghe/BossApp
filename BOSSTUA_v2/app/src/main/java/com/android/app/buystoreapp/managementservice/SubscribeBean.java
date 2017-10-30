package com.android.app.buystoreapp.managementservice;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class SubscribeBean {

    /**
     * gsabList : [{"headicon":"defalt_usericon.png","nickname":"","subscribeIsOff":0,"subscribeUserid":"K8eMXw49BHzssPQ6","userLevelMark":0,"userSubscribeNum":0}]
     * result : 0
     * resultNote : 成功
     */

    private String result;
    private String resultNote;
    /**
     * headicon : defalt_usericon.png
     * nickname :
     * subscribeIsOff : 0
     * subscribeUserid : K8eMXw49BHzssPQ6
     * userLevelMark : 0
     * userSubscribeNum : 0
     */

    private List<GsabListBean> gsabList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public List<GsabListBean> getGsabList() {
        return gsabList;
    }

    public void setGsabList(List<GsabListBean> gsabList) {
        this.gsabList = gsabList;
    }

    public static class GsabListBean {
        private String headicon;
        private String nickname;
        private int subscribeIsOff;
        private String subscribeUserid;
        private int userLevelMark;
        private int userSubscribeNum;

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

        public int getSubscribeIsOff() {
            return subscribeIsOff;
        }

        public void setSubscribeIsOff(int subscribeIsOff) {
            this.subscribeIsOff = subscribeIsOff;
        }

        public String getSubscribeUserid() {
            return subscribeUserid;
        }

        public void setSubscribeUserid(String subscribeUserid) {
            this.subscribeUserid = subscribeUserid;
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
    }
}
