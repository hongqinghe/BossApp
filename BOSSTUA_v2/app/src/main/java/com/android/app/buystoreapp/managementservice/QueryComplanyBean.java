package com.android.app.buystoreapp.managementservice;

/**
 * Created by Administrator on 2016/10/25.
 */
public class QueryComplanyBean {

    /**
     * cardId : 54646464
     * companyId : 9KdAAMUJ5If067k3
     * companyName : 魏林
     * path : http://192.168.16.115:8080/bossgroupimage/2016-10-25/companyImage/201610251208175z5L.jpg
     * phone : 9565656
     * userId : VS00KB8DPpgNcfrw
     */

    private ComplyListBean complyList;
    /**
     * complyList : {"cardId":"54646464","companyId":"9KdAAMUJ5If067k3","companyName":"魏林","path":"http://192.168.16.115:8080/bossgroupimage/2016-10-25/companyImage/201610251208175z5L.jpg","phone":"9565656","userId":"VS00KB8DPpgNcfrw"}
     * result : 0
     * resultNote : 加载成功
     */

    private String result;
    private String resultNote;

    public ComplyListBean getComplyList() {
        return complyList;
    }

    public void setComplyList(ComplyListBean complyList) {
        this.complyList = complyList;
    }

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

    public static class ComplyListBean {
        private String cardId;
        private String companyId;
        private String companyName;
        private String path;
        private String phone;
        private String userId;
        private String userName;
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
