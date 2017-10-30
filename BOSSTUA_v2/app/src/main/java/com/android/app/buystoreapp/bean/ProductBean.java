package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 尚帅波 on 2016/10/15.
 */
public class ProductBean implements Serializable {
    private static final long serialVersionUID = -952689659712346654L;

    /**
     * gpbpsList : [{"mgList":[{"moreGroId":"117","moreGroName":"银色iphone70","moreGroPrice":455,
     * "moreGroSurplus":4,"moreGroUnit":"元","proId":"AK1XleRXMqaO6RQn"}],"proCoverImag":"",
     * "proId":"S3is87jdf69Mid83","proName":"女款上衣","proSale":0,"proSeeNum":10,"proStatus":1,
     * "proSurplus":0}]
     * result : 0
     * resultNote : 成功
     */

    private String result;
    private String resultNote;
    private int refreshCount;                       //剩余刷新次数   （int类型）
    private int remainingRecommended;               //剩余推荐次数   （int类型）
    private int ifRecommend;                        //是否可以推荐：0不嫩，1可以   （int类型）
    /**
     * mgList : [{"moreGroId":"117","moreGroName":"银色iphone70","moreGroPrice":455,
     * "moreGroSurplus":4,"moreGroUnit":"元","proId":"AK1XleRXMqaO6RQn"}]
     * proCoverImag :
     * proId : S3is87jdf69Mid83
     * proName : 女款上衣
     * proSale : 0
     * proSeeNum : 10
     * proStatus : 1
     * proSurplus : 0
     * productPrice": "13~80"                      //价格区间
     */

    private List<GpbpsListBean> gpbpsList;

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

    public int getRefreshCount() {
        return refreshCount;
    }

    public void setRefreshCount(int refreshCount) {
        this.refreshCount = refreshCount;
    }

    public int getRemainingRecommended() {
        return remainingRecommended;
    }

    public void setRemainingRecommended(int remainingRecommended) {
        this.remainingRecommended = remainingRecommended;
    }

    public int getIfRecommend() {
        return ifRecommend;
    }

    public void setIfRecommend(int ifRecommend) {
        this.ifRecommend = ifRecommend;
    }

    public List<GpbpsListBean> getGpbpsList() {
        return gpbpsList;
    }

    public void setGpbpsList(List<GpbpsListBean> gpbpsList) {
        this.gpbpsList = gpbpsList;
    }

    public static class GpbpsListBean implements Serializable{
        private static final long serialVersionUID = -7010627748781277840L;
        private String mage;
        private String userId;
        private String proCoverImag;
        private String proId;
        private String proName;
        private int proSale;
        private int proSeeNum;
        private int proStatus;
        private int proSurplus;
        private int remainingRefresh;
        private String productPrice;
        private int isRecommend;    //推荐状态：0未推荐，1申请推荐，2首页推荐，3轮播图推荐



        /**
         * moreGroId : 117
         * moreGroName : 银色iphone70
         * moreGroPrice : 455
         * moreGroSurplus : 4
         * moreGroUnit : 元
         * proId : AK1XleRXMqaO6RQn
         */

        private List<MgListBean> mgList;

        public String getMage() {
            return mage;
        }

        public void setMage(String mage) {
            this.mage = mage;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getRemainingRefresh() {
            return remainingRefresh;
        }

        public void setRemainingRefresh(int remainingRefresh) {
            this.remainingRefresh = remainingRefresh;
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

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public List<MgListBean> getMgList() {
            return mgList;
        }

        public void setMgList(List<MgListBean> mgList) {
            this.mgList = mgList;
        }

        public static class MgListBean implements Serializable{
            private static final long serialVersionUID = -4655408551071786377L;
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
