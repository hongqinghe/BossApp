package com.android.app.buystoreapp.managementservice;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */
public class QueryCareerBean {

    /**
     * companyFull : 唯快不破
     * companyPhone : 254271840
     * createTime : 2016-10-26
     * id : 7TC5npn4Nrxt2311
     * imgPath : [{"cardType":"1","cardUrl":"http://192.168.16.115:8080/bossgroupimage/2016-10-26/professionImage/2016102616145347o0.jpg","creatTime":"2016-10-26","id":"KWztpd0CPA3snaUS","userId":"VS00KB8DPpgNcfrw"},{"cardType":"0","cardUrl":"http://192.168.16.115:8080/bossgroupimage/2016-10-26/professionImage/20161026161454ugQG.jpg","creatTime":"2016-10-26","id":"H1ui4mly1lv2204k","userId":"VS00KB8DPpgNcfrw"}]
     * userId : VS00KB8DPpgNcfrw
     * userName : 魏林
     */

    private CareerListBean careerList;
    /**
     * careerList : {"companyFull":"唯快不破","companyPhone":"254271840","createTime":"2016-10-26","id":"7TC5npn4Nrxt2311","imgPath":[{"cardType":"1","cardUrl":"http://192.168.16.115:8080/bossgroupimage/2016-10-26/professionImage/2016102616145347o0.jpg","creatTime":"2016-10-26","id":"KWztpd0CPA3snaUS","userId":"VS00KB8DPpgNcfrw"},{"cardType":"0","cardUrl":"http://192.168.16.115:8080/bossgroupimage/2016-10-26/professionImage/20161026161454ugQG.jpg","creatTime":"2016-10-26","id":"H1ui4mly1lv2204k","userId":"VS00KB8DPpgNcfrw"}],"userId":"VS00KB8DPpgNcfrw","userName":"魏林"}
     * result : 0
     * resultNote : 加载成功
     */

    private String result;
    private String resultNote;

    public CareerListBean getCareerList() {
        return careerList;
    }

    public void setCareerList(CareerListBean careerList) {
        this.careerList = careerList;
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

    public static class CareerListBean {
        private String companyFull;
        private String companyPhone;
        private String createTime;
        private String id;
        private String userId;
        private String userName;
        private  int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        /**
         * cardType : 1
         * cardUrl : http://192.168.16.115:8080/bossgroupimage/2016-10-26/professionImage/2016102616145347o0.jpg
         * creatTime : 2016-10-26
         * id : KWztpd0CPA3snaUS
         * userId : VS00KB8DPpgNcfrw
         */

        private List<ImgPathBean> imgPath;

        public String getCompanyFull() {
            return companyFull;
        }

        public void setCompanyFull(String companyFull) {
            this.companyFull = companyFull;
        }

        public String getCompanyPhone() {
            return companyPhone;
        }

        public void setCompanyPhone(String companyPhone) {
            this.companyPhone = companyPhone;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public List<ImgPathBean> getImgPath() {
            return imgPath;
        }

        public void setImgPath(List<ImgPathBean> imgPath) {
            this.imgPath = imgPath;
        }

        public static class ImgPathBean implements Serializable{
            private static final long serialVersionUID = 376121462931091368L;
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
