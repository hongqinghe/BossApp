package com.android.app.buystoreapp.managementservice;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/22.
 */
public class QueryCheckNameBean implements Serializable{

    private static final long serialVersionUID = 8930164299753126732L;
    /**
     * cardId : 25555
     * cardImg : [{"cardType":"1","cardUrl":"http://192.168.16.115:8080/bossgroupimage/2016-10-26/cardIdImage/20161026205437L2mE.jpg","creatTime":"2016-10-26","id":"F1T4eh4EnvtwgT80","userId":"VS00KB8DPpgNcfrw"},{"cardType":"0","cardUrl":"http://192.168.16.115:8080/bossgroupimage/2016-10-26/cardIdImage/20161026205437JsWp.jpg","creatTime":"2016-10-26","id":"HxpiYw3uvkukugBc","userId":"VS00KB8DPpgNcfrw"}]
     * creatTime : 2016-10-26
     * id : BvmKOKj6ezJCXOVg
     * status : 0
     * userId : VS00KB8DPpgNcfrw
     * userName : 呜呜呜
     */

    private CheckNameListBean checkNameList;
    /**
     * checkNameList : {"cardId":"25555","cardImg":[{"cardType":"1","cardUrl":"http://192.168.16.115:8080/bossgroupimage/2016-10-26/cardIdImage/20161026205437L2mE.jpg","creatTime":"2016-10-26","id":"F1T4eh4EnvtwgT80","userId":"VS00KB8DPpgNcfrw"},{"cardType":"0","cardUrl":"http://192.168.16.115:8080/bossgroupimage/2016-10-26/cardIdImage/20161026205437JsWp.jpg","creatTime":"2016-10-26","id":"HxpiYw3uvkukugBc","userId":"VS00KB8DPpgNcfrw"}],"creatTime":"2016-10-26","id":"BvmKOKj6ezJCXOVg","status":0,"userId":"VS00KB8DPpgNcfrw","userName":"呜呜呜"}
     * result : 0
     * resultNote : 实名认证审核未通过
     */

    private String result;
    private String resultNote;

    public CheckNameListBean getCheckNameList() {
        return checkNameList;
    }

    public void setCheckNameList(CheckNameListBean checkNameList) {
        this.checkNameList = checkNameList;
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

    public static class CheckNameListBean {
        private String cardId;
        private String creatTime;
        private String id;
        private int status;
        private String userId;
        private String userName;
        /**
         * cardType : 1
         * cardUrl : http://192.168.16.115:8080/bossgroupimage/2016-10-26/cardIdImage/20161026205437L2mE.jpg
         * creatTime : 2016-10-26
         * id : F1T4eh4EnvtwgT80
         * userId : VS00KB8DPpgNcfrw
         */

        private List<CardImgBean> cardImg;

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        public String getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(String creatTime) {
            this.creatTime = creatTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public List<CardImgBean> getCardImg() {
            return cardImg;
        }

        public void setCardImg(List<CardImgBean> cardImg) {
            this.cardImg = cardImg;
        }

        public static class CardImgBean {
            private String cardType;
            private String cardUrl;
            private String creatTime;
            private String id;
            private String userId;

            public String getCardType() {
                return cardType;
            }

            public void setCardType(String cardType) {
                this.cardType = cardType;
            }

            public String getCardUrl() {
                return cardUrl;
            }

            public void setCardUrl(String cardUrl) {
                this.cardUrl = cardUrl;
            }

            public String getCreatTime() {
                return creatTime;
            }

            public void setCreatTime(String creatTime) {
                this.creatTime = creatTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }
    }
}
