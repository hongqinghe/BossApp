package com.android.app.buystoreapp.managementservice;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
public class SeeMeBean implements Serializable{

    private static final long serialVersionUID = 779192820493546301L;
    /**
     * getPproductToViewBeanList : [{"thisUserHeadicon":"","thisUserId":"00js8ooWvMb2U8CB","toViewTime":"2016-10-11"}]
     * result : 0
     * resultNote : 成功
     */

    private String result;
    private String resultNote;
    /**
     * thisUserHeadicon :
     * thisUserId : 00js8ooWvMb2U8CB
     * toViewTime : 2016-10-11
     */

    private List<GetPproductToViewBeanListBean> getPproductToViewBeanList;

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

    public List<GetPproductToViewBeanListBean> getGetPproductToViewBeanList() {
        return getPproductToViewBeanList;
    }

    public void setGetPproductToViewBeanList(List<GetPproductToViewBeanListBean> getPproductToViewBeanList) {
        this.getPproductToViewBeanList = getPproductToViewBeanList;
    }

    public static class GetPproductToViewBeanListBean implements Serializable{
        private static final long serialVersionUID = 6276291326617360204L;
        private String thisUserHeadicon;
        private String thisUserId;
        private String toViewTime;
        private String nickname;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getThisUserHeadicon() {
            return thisUserHeadicon;
        }

        public void setThisUserHeadicon(String thisUserHeadicon) {
            this.thisUserHeadicon = thisUserHeadicon;
        }

        public String getThisUserId() {
            return thisUserId;
        }

        public void setThisUserId(String thisUserId) {
            this.thisUserId = thisUserId;
        }

        public String getToViewTime() {
            return toViewTime;
        }

        public void setToViewTime(String toViewTime) {
            this.toViewTime = toViewTime;
        }
    }
}
