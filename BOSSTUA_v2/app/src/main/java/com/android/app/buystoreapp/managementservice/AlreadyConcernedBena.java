package com.android.app.buystoreapp.managementservice;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class AlreadyConcernedBena implements Serializable{

    private static final long serialVersionUID = -5932752643479278165L;
    /**
     * gaabList : [{"headicon":"defalt_usericon.png","nickname":"id要长才厉害","attentionIsOff":1,"attentionUserid":"00js8ooWvMb2U8C","userLevelMark":1,"userAttentionNum":4}]
     * result : 0
     * resultNote : 成功
     */

    private String result;
    private String resultNote;
    /**
     * headicon : defalt_usericon.png
     * nickname : id要长才厉害
     * attentionIsOff : 1
     * attentionUserid : 00js8ooWvMb2U8C
     * userLevelMark : 1
     * userAttentionNum : 4
     */

    public List<GaabListBean> gaabList;

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

    public List<GaabListBean> getGaabList() {
        return gaabList;
    }

    public void setGaabList(List<GaabListBean> gaabList) {
        this.gaabList = gaabList;
    }

    public static class GaabListBean implements Serializable{
        private static final long serialVersionUID = 3828512624101121067L;
        private String headicon;
        private String nickname;
        private int attentionIsOff;
        private String attentionUserid;
        private int userLevelMark;
        private int userAttentionNum;
        private String userPosition;

        public String getUserPosition() {
            return userPosition;
        }

        public void setUserPosition(String userPosition) {
            this.userPosition = userPosition;
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

        public int getAttentionIsOff() {
            return attentionIsOff;
        }

        public void setAttentionIsOff(int attentionIsOff) {
            this.attentionIsOff = attentionIsOff;
        }

        public String getAttentionUserid() {
            return attentionUserid;
        }

        public void setAttentionUserid(String attentionUserid) {
            this.attentionUserid = attentionUserid;
        }

        public int getUserLevelMark() {
            return userLevelMark;
        }

        public void setUserLevelMark(int userLevelMark) {
            this.userLevelMark = userLevelMark;
        }

        public int getUserAttentionNum() {
            return userAttentionNum;
        }

        public void setUserAttentionNum(int userAttentionNum) {
            this.userAttentionNum = userAttentionNum;
        }
    }
}
