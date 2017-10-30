package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shangshuaibo on 2017/1/10 19:52
 */
public class EditProductBean implements Serializable {

    /**
     * areasId2 : 110114
     * areasIdState : 3
     * dayTimeEnd : 17:00
     * dayTimeStart : 9:00
     * freightMode : 2
     * freightPrice : 0
     * isRecommend : 0
     * listimg : [{"isCover":1,"proId":"z9y68FYA925Y6E0M","proImageId":"3FiA9d4H00E3qAgk",
     * "proImageMin":"/bossgroupimage/2017-01-09/proImgPath/proImgPathMin/20170109172605Y7XV
     * .jpg","proImageName":"","proImageUrl":"/bossgroupimage/2017-01-09/proImgPath
     * /proImgPathMin_2/20170109172605Y7XV.jpg","proName":""}]
     * mgList : [{"moreGroId":"522","moreGroName":"明哥们","moreGroPrice":1,"moreGroSurplus":2,
     * "moreGroUnit":"个","proId":"z9y68FYA925Y6E0M"}]
     * modes : 0
     * proAddress : 昌平区
     * proCatId : 128
     * proDes : 民工漫
     * proId : z9y68FYA925Y6E0M
     * proLat : 40.148936
     * proLong : 116.300964
     * proName : 明哥们
     * proStatus : 1
     * proSurplus : 0
     * result : 0
     * resultNote : 查询成功
     * serveLabel : D
     * serveLabelName : 需求
     * serviceSubject : 0
     * specificAddress : 王于路
     * thisUser : Krt6OA1Vx595E5dh
     * weekEnd : 周五
     * weekStart : 周一
     */

    private int areasId2;
    private int areasIdState;
    private String dayTimeEnd;
    private String dayTimeStart;
    private int freightMode;
    private String freightPrice;
    private int isRecommend;
    private int modes;
    private String proAddress;
    private String proCatId;
    private String proCatName;
    private String proDes;
    private String proId;
    private String proLat;
    private String proLong;
    private String proName;
    private int proStatus;
    private int proSurplus;
    private String result;
    private String resultNote;
    private String serveLabel;
    private String serveLabelName;
    private int serviceSubject;
    private String specificAddress;
    private String thisUser;
    private String weekEnd;
    private String weekStart;
    /**
     * isCover : 1
     * proId : z9y68FYA925Y6E0M
     * proImageId : 3FiA9d4H00E3qAgk
     * proImageMin : /bossgroupimage/2017-01-09/proImgPath/proImgPathMin/20170109172605Y7XV.jpg
     * proImageName :
     * proImageUrl : /bossgroupimage/2017-01-09/proImgPath/proImgPathMin_2/20170109172605Y7XV.jpg
     * proName :
     */

    private List<ListimgBean> listimg;

    public String getProCatName() {
        return proCatName;
    }

    public void setProCatName(String proCatName) {
        this.proCatName = proCatName;
    }

    /**
     * moreGroId : 522
     * moreGroName : 明哥们
     * moreGroPrice : 1
     * moreGroSurplus : 2
     * moreGroUnit : 个
     * proId : z9y68FYA925Y6E0M
     */

    private List<MgListBean> mgList;

    public int getAreasId2() {
        return areasId2;
    }

    public void setAreasId2(int areasId2) {
        this.areasId2 = areasId2;
    }

    public int getAreasIdState() {
        return areasIdState;
    }

    public void setAreasIdState(int areasIdState) {
        this.areasIdState = areasIdState;
    }

    public String getDayTimeEnd() {
        return dayTimeEnd;
    }

    public void setDayTimeEnd(String dayTimeEnd) {
        this.dayTimeEnd = dayTimeEnd;
    }

    public String getDayTimeStart() {
        return dayTimeStart;
    }

    public void setDayTimeStart(String dayTimeStart) {
        this.dayTimeStart = dayTimeStart;
    }

    public int getFreightMode() {
        return freightMode;
    }

    public void setFreightMode(int freightMode) {
        this.freightMode = freightMode;
    }

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public int getModes() {
        return modes;
    }

    public void setModes(int modes) {
        this.modes = modes;
    }

    public String getProAddress() {
        return proAddress;
    }

    public void setProAddress(String proAddress) {
        this.proAddress = proAddress;
    }

    public String getProCatId() {
        return proCatId;
    }

    public void setProCatId(String proCatId) {
        this.proCatId = proCatId;
    }

    public String getProDes() {
        return proDes;
    }

    public void setProDes(String proDes) {
        this.proDes = proDes;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProLat() {
        return proLat;
    }

    public void setProLat(String proLat) {
        this.proLat = proLat;
    }

    public String getProLong() {
        return proLong;
    }

    public void setProLong(String proLong) {
        this.proLong = proLong;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
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

    public int getServiceSubject() {
        return serviceSubject;
    }

    public void setServiceSubject(int serviceSubject) {
        this.serviceSubject = serviceSubject;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }

    public String getThisUser() {
        return thisUser;
    }

    public void setThisUser(String thisUser) {
        this.thisUser = thisUser;
    }

    public String getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(String weekEnd) {
        this.weekEnd = weekEnd;
    }

    public String getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(String weekStart) {
        this.weekStart = weekStart;
    }

    public List<ListimgBean> getListimg() {
        return listimg;
    }

    public void setListimg(List<ListimgBean> listimg) {
        this.listimg = listimg;
    }

    public List<MgListBean> getMgList() {
        return mgList;
    }

    public void setMgList(List<MgListBean> mgList) {
        this.mgList = mgList;
    }

    public static class ListimgBean implements Serializable{
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

    public static class MgListBean implements Serializable{
        private String moreGroId;
        private String moreGroName;
        private int moreGroPrice;
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

        public int getMoreGroPrice() {
            return moreGroPrice;
        }

        public void setMoreGroPrice(int moreGroPrice) {
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
