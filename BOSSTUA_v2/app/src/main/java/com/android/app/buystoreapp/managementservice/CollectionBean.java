package com.android.app.buystoreapp.managementservice;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/21.
 */
public class CollectionBean implements Serializable{

    /**
     * result : 0
     * resultNote : 成功
     * ucpList : [{"bindingMark1":0,"bindingMark2":0,"bindingMark3":0,"bindingMark4":0,"createDate":"2016-11-24","createDateFamt":"6天前","distanceKm":0.029,"distanceKms":"0.029km","mgList":[{"moreGroId":"258","moreGroName":"李开航","moreGroPrice":0.01,"moreGroSurplus":99999808,"moreGroUnit":"","proId":"vqfHv3Wi8QcB913a"}],"nickname":"Boss_李开航","piList":[{"isCover":0,"proId":"vqfHv3Wi8QcB913a","proImageId":"19R20fQL5BOq3vOm","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025130243mg65.jpg","proImageName":"20161025130243mg65.jpg","proImageUrl":"/bossgroupimage/2016-10-25/proImgPath/20161025130243mg65.jpg","proName":""},{"isCover":0,"proId":"vqfHv3Wi8QcB913a","proImageId":"9TNctvBY8fO1SW72","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025130244By5O.jpg","proImageName":"20161025130244By5O.jpg","proImageUrl":"/bossgroupimage/2016-10-25/proImgPath/20161025130244By5O.jpg","proName":""},{"isCover":0,"proId":"vqfHv3Wi8QcB913a","proImageId":"F4l7N6dabi1N7YEj","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025130243i2bf.jpg","proImageName":"20161025130243i2bf.jpg","proImageUrl":"/bossgroupimage/2016-10-25/proImgPath/20161025130243i2bf.jpg","proName":""},{"isCover":0,"proId":"vqfHv3Wi8QcB913a","proImageId":"P5Y5cjc4wHx17W16","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025130244Tznu.jpg","proImageName":"20161025130244Tznu.jpg","proImageUrl":"/bossgroupimage/2016-10-25/proImgPath/20161025130244Tznu.jpg","proName":""},{"isCover":0,"proId":"vqfHv3Wi8QcB913a","proImageId":"qML00OL4D93Sf8dh","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025130243r86a.jpg","proImageName":"20161025130243r86a.jpg","proImageUrl":"/bossgroupimage/2016-10-25/proImgPath/20161025130243r86a.jpg","proName":""},{"isCover":0,"proId":"vqfHv3Wi8QcB913a","proImageId":"u9vb1XI7bjmG4D46","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025130244qtnp.jpg","proImageName":"20161025130244qtnp.jpg","proImageUrl":"/bossgroupimage/2016-10-25/proImgPath/20161025130244qtnp.jpg","proName":""}],"proDes":"快快快","proDistance":"0.0","proId":"vqfHv3Wi8QcB913a","proName":"李开航","proSale":0,"proSeeNum":35,"proSurplus":99999808,"serveLabel":"E","serveLabelName":"交换","userHeadicon":"http://192.168.1.122:8080/bossgroupimage/2016-11-17/userIcon/userIconMin/20161117170158R8b6.jpg","userId":"CM64g7lIxocfCPyR","userLevelMark":3,"userPosition":"程序员","userTreasure":"1000000"}]
     */

    private String result;
    private String resultNote;
    /**
     * bindingMark1 : 0
     * bindingMark2 : 0
     * bindingMark3 : 0
     * bindingMark4 : 0
     * createDate : 2016-11-24
     * createDateFamt : 6天前
     * distanceKm : 0.029
     * distanceKms : 0.029km
     * mgList : [{"moreGroId":"258","moreGroName":"李开航","moreGroPrice":0.01,"moreGroSurplus":99999808,"moreGroUnit":"","proId":"vqfHv3Wi8QcB913a"}]
     * nickname : Boss_李开航
     * piList : [{"isCover":0,"proId":"vqfHv3Wi8QcB913a","proImageId":"19R20fQL5BOq3vOm","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025130243mg65.jpg","proImageName":"20161025130243mg65.jpg","proImageUrl":"/bossgroupimage/2016-10-25/proImgPath/20161025130243mg65.jpg","proName":""},{"isCover":0,"proId":"vqfHv3Wi8QcB913a","proImageId":"9TNctvBY8fO1SW72","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025130244By5O.jpg","proImageName":"20161025130244By5O.jpg","proImageUrl":"/bossgroupimage/2016-10-25/proImgPath/20161025130244By5O.jpg","proName":""},{"isCover":0,"proId":"vqfHv3Wi8QcB913a","proImageId":"F4l7N6dabi1N7YEj","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025130243i2bf.jpg","proImageName":"20161025130243i2bf.jpg","proImageUrl":"/bossgroupimage/2016-10-25/proImgPath/20161025130243i2bf.jpg","proName":""},{"isCover":0,"proId":"vqfHv3Wi8QcB913a","proImageId":"P5Y5cjc4wHx17W16","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025130244Tznu.jpg","proImageName":"20161025130244Tznu.jpg","proImageUrl":"/bossgroupimage/2016-10-25/proImgPath/20161025130244Tznu.jpg","proName":""},{"isCover":0,"proId":"vqfHv3Wi8QcB913a","proImageId":"qML00OL4D93Sf8dh","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025130243r86a.jpg","proImageName":"20161025130243r86a.jpg","proImageUrl":"/bossgroupimage/2016-10-25/proImgPath/20161025130243r86a.jpg","proName":""},{"isCover":0,"proId":"vqfHv3Wi8QcB913a","proImageId":"u9vb1XI7bjmG4D46","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025130244qtnp.jpg","proImageName":"20161025130244qtnp.jpg","proImageUrl":"/bossgroupimage/2016-10-25/proImgPath/20161025130244qtnp.jpg","proName":""}]
     * proDes : 快快快
     * proDistance : 0.0
     * proId : vqfHv3Wi8QcB913a
     * proName : 李开航
     * proSale : 0
     * proSeeNum : 35
     * proSurplus : 99999808
     * serveLabel : E
     * serveLabelName : 交换
     * userHeadicon : http://192.168.1.122:8080/bossgroupimage/2016-11-17/userIcon/userIconMin/20161117170158R8b6.jpg
     * userId : CM64g7lIxocfCPyR
     * userLevelMark : 3
     * userPosition : 程序员
     * userTreasure : 1000000
     */

    private List<UcpListBean> ucpList;

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

    public List<UcpListBean> getUcpList() {
        return ucpList;
    }

    public void setUcpList(List<UcpListBean> ucpList) {
        this.ucpList = ucpList;
    }

    public static class UcpListBean {
        private int bindingMark1;
        private int bindingMark2;
        private int bindingMark3;
        private int bindingMark4;
        private String createDate;
        private String createDateFamt;
        private double distanceKm;
        private String distanceKms;
        private String nickname;
        private String proDes;
        private String proDistance;
        private String proId;
        private String proName;
        private int proSale;
        private int proSeeNum;
        private int proSurplus;
        private String serveLabel;
        private String serveLabelName;
        private String userHeadicon;
        private String userId;
        private int userLevelMark;
        private String userPosition;
        private String userTreasure;
        /**
         * moreGroId : 258
         * moreGroName : 李开航
         * moreGroPrice : 0.01
         * moreGroSurplus : 99999808
         * moreGroUnit :
         * proId : vqfHv3Wi8QcB913a
         */

        private List<MgListBean> mgList;
        /**
         * isCover : 0
         * proId : vqfHv3Wi8QcB913a
         * proImageId : 19R20fQL5BOq3vOm
         * proImageMin : http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025130243mg65.jpg
         * proImageName : 20161025130243mg65.jpg
         * proImageUrl : /bossgroupimage/2016-10-25/proImgPath/20161025130243mg65.jpg
         * proName :
         */

        private List<PiListBean> piList;

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

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreateDateFamt() {
            return createDateFamt;
        }

        public void setCreateDateFamt(String createDateFamt) {
            this.createDateFamt = createDateFamt;
        }

        public double getDistanceKm() {
            return distanceKm;
        }

        public void setDistanceKm(double distanceKm) {
            this.distanceKm = distanceKm;
        }

        public String getDistanceKms() {
            return distanceKms;
        }

        public void setDistanceKms(String distanceKms) {
            this.distanceKms = distanceKms;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getProDes() {
            return proDes;
        }

        public void setProDes(String proDes) {
            this.proDes = proDes;
        }

        public String getProDistance() {
            return proDistance;
        }

        public void setProDistance(String proDistance) {
            this.proDistance = proDistance;
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

        public int getProSurplus() {
            return proSurplus;
        }

        public void setProSurplus(int proSurplus) {
            this.proSurplus = proSurplus;
        }

        public String getServeLabel() {
            return serveLabel;
        }

        public void setServeLabel(String serveLabel) {
            this.serveLabel = serveLabel;
        }

        public String getServeLabelName() {
            return serveLabelName;
        }

        public void setServeLabelName(String serveLabelName) {
            this.serveLabelName = serveLabelName;
        }

        public String getUserHeadicon() {
            return userHeadicon;
        }

        public void setUserHeadicon(String userHeadicon) {
            this.userHeadicon = userHeadicon;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getUserLevelMark() {
            return userLevelMark;
        }

        public void setUserLevelMark(int userLevelMark) {
            this.userLevelMark = userLevelMark;
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

        public List<MgListBean> getMgList() {
            return mgList;
        }

        public void setMgList(List<MgListBean> mgList) {
            this.mgList = mgList;
        }

        public List<PiListBean> getPiList() {
            return piList;
        }

        public void setPiList(List<PiListBean> piList) {
            this.piList = piList;
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

        public static class PiListBean {
            private int isCover;
            private String proId;
            private String proImageId;
            private String proImageMin;
            private String proImageName;
            private String proImageUrl;
            private String proName;

            public int getIsCover() {
                return isCover;
            }

            public void setIsCover(int isCover) {
                this.isCover = isCover;
            }

            public String getProId() {
                return proId;
            }

            public void setProId(String proId) {
                this.proId = proId;
            }

            public String getProImageId() {
                return proImageId;
            }

            public void setProImageId(String proImageId) {
                this.proImageId = proImageId;
            }

            public String getProImageMin() {
                return proImageMin;
            }

            public void setProImageMin(String proImageMin) {
                this.proImageMin = proImageMin;
            }

            public String getProImageName() {
                return proImageName;
            }

            public void setProImageName(String proImageName) {
                this.proImageName = proImageName;
            }

            public String getProImageUrl() {
                return proImageUrl;
            }

            public void setProImageUrl(String proImageUrl) {
                this.proImageUrl = proImageUrl;
            }

            public String getProName() {
                return proName;
            }

            public void setProName(String proName) {
                this.proName = proName;
            }
        }
    }
}
