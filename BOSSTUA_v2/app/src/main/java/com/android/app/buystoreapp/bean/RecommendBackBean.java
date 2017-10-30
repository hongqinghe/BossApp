package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shangshuaibo on 2016/12/26 20:48
 */
public class RecommendBackBean implements Serializable{


    /**
     * ifRecommend : 1
     * isRecommend : 0
     * mage : 0
     * mgList : [{"moreGroId":"428","moreGroName":"最新","moreGroPrice":8.888888888E9,
     * "moreGroSurplus":888,"moreGroUnit":"JJ可口可","proId":"0h9l4YF1jjnSFwXz"}]
     * proCoverImag : /bossgroupimage/2016-12-24/proImgPath/proImgPathMin/20161224185627wQLw.jpg
     * proId : 0h9l4YF1jjnSFwXz
     * proName : 最新
     * proSale : 0
     * proSeeNum : 0
     * proStatus : 1
     * proSurplus : 888
     * productPrice :
     * remainingRecommended : 0
     * remainingRefresh : 0
     * userId : CoF3zsbF2WL7eaQ3
     */

    private GetProductByProStatusBeanBean getProductByProStatusBean;
    /**
     * getProductByProStatusBean : {"ifRecommend":1,"isRecommend":0,"mage":"0",
     * "mgList":[{"moreGroId":"428","moreGroName":"最新","moreGroPrice":8.888888888E9,
     * "moreGroSurplus":888,"moreGroUnit":"JJ可口可","proId":"0h9l4YF1jjnSFwXz"}],
     * "proCoverImag":"/bossgroupimage/2016-12-24/proImgPath/proImgPathMin/20161224185627wQLw
     * .jpg","proId":"0h9l4YF1jjnSFwXz","proName":"最新","proSale":0,"proSeeNum":0,"proStatus":1,
     * "proSurplus":888,"productPrice":"","remainingRecommended":0,"remainingRefresh":0,
     * "userId":"CoF3zsbF2WL7eaQ3"}
     * result : 0
     * resultNote : 成功
     */

    private String result;
    private String resultNote;

    public GetProductByProStatusBeanBean getGetProductByProStatusBean() {
        return getProductByProStatusBean;
    }

    public void setGetProductByProStatusBean(GetProductByProStatusBeanBean getProductByProStatusBean) {
        this.getProductByProStatusBean = getProductByProStatusBean;
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

    public static class GetProductByProStatusBeanBean {
        private int ifRecommend;
        private int isRecommend;
        private String mage;
        private String proCoverImag;
        private String proId;
        private String proName;
        private int proSale;
        private int proSeeNum;
        private int proStatus;
        private int proSurplus;
        private String productPrice;
        private int remainingRecommended;
        private int remainingRefresh;
        private String userId;
        /**
         * moreGroId : 428
         * moreGroName : 最新
         * moreGroPrice : 8.888888888E9
         * moreGroSurplus : 888
         * moreGroUnit : JJ可口可
         * proId : 0h9l4YF1jjnSFwXz
         */

        private List<MgListBean> mgList;

        public int getIfRecommend() {
            return ifRecommend;
        }

        public void setIfRecommend(int ifRecommend) {
            this.ifRecommend = ifRecommend;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public String getMage() {
            return mage;
        }

        public void setMage(String mage) {
            this.mage = mage;
        }

        public String getProCoverImag() {
            return proCoverImag;
        }

        public void setProCoverImag(String proCoverImag) {
            this.proCoverImag = proCoverImag;
        }

        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public int getProSale() {
            return proSale;
        }

        public void setProSale(int proSale) {
            this.proSale = proSale;
        }

        public int getProSeeNum() {
            return proSeeNum;
        }

        public void setProSeeNum(int proSeeNum) {
            this.proSeeNum = proSeeNum;
        }

        public int getProStatus() {
            return proStatus;
        }

        public void setProStatus(int proStatus) {
            this.proStatus = proStatus;
        }

        public int getProSurplus() {
            return proSurplus;
        }

        public void setProSurplus(int proSurplus) {
            this.proSurplus = proSurplus;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public int getRemainingRecommended() {
            return remainingRecommended;
        }

        public void setRemainingRecommended(int remainingRecommended) {
            this.remainingRecommended = remainingRecommended;
        }

        public int getRemainingRefresh() {
            return remainingRefresh;
        }

        public void setRemainingRefresh(int remainingRefresh) {
            this.remainingRefresh = remainingRefresh;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public List<MgListBean> getMgList() {
            return mgList;
        }

        public void setMgList(List<MgListBean> mgList) {
            this.mgList = mgList;
        }

        public static class MgListBean {
            private String moreGroId;
            private String moreGroName;
            private double moreGroPrice;
            private int moreGroSurplus;
            private String moreGroUnit;
            private String proId;

            public String getMoreGroId() {
                return moreGroId;
            }

            public void setMoreGroId(String moreGroId) {
                this.moreGroId = moreGroId;
            }

            public String getMoreGroName() {
                return moreGroName;
            }

            public void setMoreGroName(String moreGroName) {
                this.moreGroName = moreGroName;
            }

            public double getMoreGroPrice() {
                return moreGroPrice;
            }

            public void setMoreGroPrice(double moreGroPrice) {
                this.moreGroPrice = moreGroPrice;
            }

            public int getMoreGroSurplus() {
                return moreGroSurplus;
            }

            public void setMoreGroSurplus(int moreGroSurplus) {
                this.moreGroSurplus = moreGroSurplus;
            }

            public String getMoreGroUnit() {
                return moreGroUnit;
            }

            public void setMoreGroUnit(String moreGroUnit) {
                this.moreGroUnit = moreGroUnit;
            }

            public String getProId() {
                return proId;
            }

            public void setProId(String proId) {
                this.proId = proId;
            }
        }
    }
}
