package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class SearchBean implements Serializable {

    /**
     * lnbList : [{"newsIcon":"D:/bossgroupimage/news/1460/1460.png","newsId":"1460","newsTitle":"asd","subscribeIsOff":0,"userSubscribeNum":0}]
     * lpdbList : [{"bindingMark1":"0","bindingMark2":"0","bindingMark3":"0","bindingMark4":"0","distanceKm":"12222.083Km","headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","loginTime":"6小时前来过","moreGroSurplus":1,"moreGroUnit":"","nickname":"Boss_21742154","priceInterval":"0.01","proDes":"我的人家说明他家门口一天你就可以理解成绩好","proId":"2BrrRnNHak3RKRBu","proImageMin":[{"isCover":1,"proId":"","proImageId":"7fS5bvo00E3QEB9w","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-31/proImgPath/proImgPathMin/20161031145453frRo.jpg","proImageName":"","proImageUrl":"","proName":""}],"proName":"ios","proSale":0,"proSeeNum":"10","serveLabel":"L","serveLabelName":"经历","userId":"1LcPDz1IkvC5m665","userLevelMark":"3","userPosition":"","userTreasure":"0"},{"bindingMark1":"0","bindingMark2":"0","bindingMark3":"0","bindingMark4":"0","distanceKm":"12222.171Km","headicon":"http://192.168.1.122:8080/bossgroupimage/2016-11-02/userIcon/userIconMin/20161102170940C1hL.jpg","loginTime":"8小时前来过","moreGroSurplus":0,"moreGroUnit":"","nickname":"Boss_15836730","priceInterval":"0.01","proDes":"我们在o！我们都市田园生活在一起！","proId":"jr4o25JobBueHVMF","proImageMin":[{"isCover":1,"proId":"","proImageId":"A86i709jHAh4q2O5","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-31/proImgPath/proImgPathMin/2016103114474943oq.jpg","proImageName":"","proImageUrl":"","proName":""}],"proName":"ios","proSale":0,"proSeeNum":"11","serveLabel":"J","serveLabelName":"共享","userId":"VzjCQFsgahm444ea","userLevelMark":"3","userPosition":"","userTreasure":"0"},{"bindingMark1":"0","bindingMark2":"0","bindingMark3":"0","bindingMark4":"0","distanceKm":"12222.048Km","headicon":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/userIcon/userIconMin/20161025154134hKt8.jpg","loginTime":"22小时前来过","moreGroSurplus":99999955,"moreGroUnit":"","nickname":"Boss_李开航","priceInterval":"0.01","proDes":"用了四年的笔记本,便宜卖了","proId":"uxFikt91OOHgvGTp","proImageMin":[{"isCover":0,"proId":"","proImageId":"884ea61EoD435wND","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/2016102518451628YC.jpg","proImageName":"","proImageUrl":"","proName":""},{"isCover":0,"proId":"","proImageId":"GJqzVd3wpaj4h5zQ","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025184516tN3F.jpg","proImageName":"","proImageUrl":"","proName":""},{"isCover":0,"proId":"","proImageId":"N0r5kh219iUS3ri8","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025184516p9dy.jpg","proImageName":"","proImageUrl":"","proName":""},{"isCover":0,"proId":"","proImageId":"OIHU8bzBN07Qt6F0","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025184515993V.jpg","proImageName":"","proImageUrl":"","proName":""},{"isCover":1,"proId":"","proImageId":"PcOHJhipfF2F5ns6","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/2016102518451599YS.jpg","proImageName":"","proImageUrl":"","proName":""}],"proName":"华硕N55S","proSale":0,"proSeeNum":"19","serveLabel":"","serveLabelName":"交换","userId":"CM64g7lIxocfCPyR","userLevelMark":"3","userPosition":"程序员","userTreasure":"0"}]
     * lpebList : [{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","nickname":"Boss_21742154","userAttentionNum":3,"userLevelMark":3,"userid":"1LcPDz1IkvC5m665"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","nickname":"Boss_48661881","userAttentionNum":0,"userLevelMark":3,"userid":"aSH6jGWueOnt9B88"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","nickname":"Boss_58679230","userAttentionNum":0,"userLevelMark":3,"userid":"Bv0dHboSW3nogQ7U"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/userIcon/userIconMin/20161025154134hKt8.jpg","nickname":"Boss_李开航","userAttentionNum":7,"userLevelMark":3,"userid":"CM64g7lIxocfCPyR"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","nickname":"Boss_12444673","userAttentionNum":0,"userLevelMark":3,"userid":"GjIT2dxQJilf9Sj0"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","nickname":"Boss_14876767","userAttentionNum":0,"userLevelMark":3,"userid":"hOHBiHOEf3Wn4KTB"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","nickname":"Boss_08427552","userAttentionNum":0,"userLevelMark":3,"userid":"n0faNDh94S2e5K12"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","nickname":"Boss_06670133","userAttentionNum":4,"userLevelMark":3,"userid":"q4o2SND4imdWk79G"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/2016-11-02/userIcon/userIconMin/20161102170940C1hL.jpg","nickname":"Boss_15836730","userAttentionNum":1,"userLevelMark":3,"userid":"VzjCQFsgahm444ea"}]
     * searchState : 1
     */

    private BeanBean bean;
    /**
     * bean : {"lnbList":[{"newsIcon":"D:/bossgroupimage/news/1460/1460.png","newsId":"1460","newsTitle":"asd","subscribeIsOff":0,"userSubscribeNum":0}],"lpdbList":[{"bindingMark1":"0","bindingMark2":"0","bindingMark3":"0","bindingMark4":"0","distanceKm":"12222.083Km","headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","loginTime":"6小时前来过","moreGroSurplus":1,"moreGroUnit":"","nickname":"Boss_21742154","priceInterval":"0.01","proDes":"我的人家说明他家门口一天你就可以理解成绩好","proId":"2BrrRnNHak3RKRBu","proImageMin":[{"isCover":1,"proId":"","proImageId":"7fS5bvo00E3QEB9w","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-31/proImgPath/proImgPathMin/20161031145453frRo.jpg","proImageName":"","proImageUrl":"","proName":""}],"proName":"ios","proSale":0,"proSeeNum":"10","serveLabel":"L","serveLabelName":"经历","userId":"1LcPDz1IkvC5m665","userLevelMark":"3","userPosition":"","userTreasure":"0"},{"bindingMark1":"0","bindingMark2":"0","bindingMark3":"0","bindingMark4":"0","distanceKm":"12222.171Km","headicon":"http://192.168.1.122:8080/bossgroupimage/2016-11-02/userIcon/userIconMin/20161102170940C1hL.jpg","loginTime":"8小时前来过","moreGroSurplus":0,"moreGroUnit":"","nickname":"Boss_15836730","priceInterval":"0.01","proDes":"我们在o！我们都市田园生活在一起！","proId":"jr4o25JobBueHVMF","proImageMin":[{"isCover":1,"proId":"","proImageId":"A86i709jHAh4q2O5","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-31/proImgPath/proImgPathMin/2016103114474943oq.jpg","proImageName":"","proImageUrl":"","proName":""}],"proName":"ios","proSale":0,"proSeeNum":"11","serveLabel":"J","serveLabelName":"共享","userId":"VzjCQFsgahm444ea","userLevelMark":"3","userPosition":"","userTreasure":"0"},{"bindingMark1":"0","bindingMark2":"0","bindingMark3":"0","bindingMark4":"0","distanceKm":"12222.048Km","headicon":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/userIcon/userIconMin/20161025154134hKt8.jpg","loginTime":"22小时前来过","moreGroSurplus":99999955,"moreGroUnit":"","nickname":"Boss_李开航","priceInterval":"0.01","proDes":"用了四年的笔记本,便宜卖了","proId":"uxFikt91OOHgvGTp","proImageMin":[{"isCover":0,"proId":"","proImageId":"884ea61EoD435wND","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/2016102518451628YC.jpg","proImageName":"","proImageUrl":"","proName":""},{"isCover":0,"proId":"","proImageId":"GJqzVd3wpaj4h5zQ","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025184516tN3F.jpg","proImageName":"","proImageUrl":"","proName":""},{"isCover":0,"proId":"","proImageId":"N0r5kh219iUS3ri8","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025184516p9dy.jpg","proImageName":"","proImageUrl":"","proName":""},{"isCover":0,"proId":"","proImageId":"OIHU8bzBN07Qt6F0","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/20161025184515993V.jpg","proImageName":"","proImageUrl":"","proName":""},{"isCover":1,"proId":"","proImageId":"PcOHJhipfF2F5ns6","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/proImgPath/proImgPathMin/2016102518451599YS.jpg","proImageName":"","proImageUrl":"","proName":""}],"proName":"华硕N55S","proSale":0,"proSeeNum":"19","serveLabel":"","serveLabelName":"交换","userId":"CM64g7lIxocfCPyR","userLevelMark":"3","userPosition":"程序员","userTreasure":"0"}],"lpebList":[{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","nickname":"Boss_21742154","userAttentionNum":3,"userLevelMark":3,"userid":"1LcPDz1IkvC5m665"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","nickname":"Boss_48661881","userAttentionNum":0,"userLevelMark":3,"userid":"aSH6jGWueOnt9B88"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","nickname":"Boss_58679230","userAttentionNum":0,"userLevelMark":3,"userid":"Bv0dHboSW3nogQ7U"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/2016-10-25/userIcon/userIconMin/20161025154134hKt8.jpg","nickname":"Boss_李开航","userAttentionNum":7,"userLevelMark":3,"userid":"CM64g7lIxocfCPyR"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","nickname":"Boss_12444673","userAttentionNum":0,"userLevelMark":3,"userid":"GjIT2dxQJilf9Sj0"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","nickname":"Boss_14876767","userAttentionNum":0,"userLevelMark":3,"userid":"hOHBiHOEf3Wn4KTB"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","nickname":"Boss_08427552","userAttentionNum":0,"userLevelMark":3,"userid":"n0faNDh94S2e5K12"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","nickname":"Boss_06670133","userAttentionNum":4,"userLevelMark":3,"userid":"q4o2SND4imdWk79G"},{"attentionIsOff":0,"headicon":"http://192.168.1.122:8080/bossgroupimage/2016-11-02/userIcon/userIconMin/20161102170940C1hL.jpg","nickname":"Boss_15836730","userAttentionNum":1,"userLevelMark":3,"userid":"VzjCQFsgahm444ea"}],"searchState":1}
     * result : 0
     * resultNote : 查询成功
     */

    private String result;
    private String resultNote;

    public BeanBean getBean() {
        return bean;
    }

    public void setBean(BeanBean bean) {
        this.bean = bean;
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

    public static class BeanBean {
        private int searchState;
        /**
         * newsIcon : D:/bossgroupimage/news/1460/1460.png
         * newsId : 1460
         * newsTitle : asd
         * subscribeIsOff : 0
         * userSubscribeNum : 0
         */

        private List<LnbListBean> lnbList;
        /**
         * bindingMark1 : 0
         * bindingMark2 : 0
         * bindingMark3 : 0
         * bindingMark4 : 0
         * distanceKm : 12222.083Km
         * headicon : http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png
         * loginTime : 6小时前来过
         * moreGroSurplus : 1
         * moreGroUnit :
         * nickname : Boss_21742154
         * priceInterval : 0.01
         * proDes : 我的人家说明他家门口一天你就可以理解成绩好
         * proId : 2BrrRnNHak3RKRBu
         * proImageMin : [{"isCover":1,"proId":"","proImageId":"7fS5bvo00E3QEB9w","proImageMin":"http://192.168.1.122:8080/bossgroupimage/2016-10-31/proImgPath/proImgPathMin/20161031145453frRo.jpg","proImageName":"","proImageUrl":"","proName":""}]
         * proName : ios
         * proSale : 0
         * proSeeNum : 10
         * serveLabel : L
         * serveLabelName : 经历
         * userId : 1LcPDz1IkvC5m665
         * userLevelMark : 3
         * userPosition :
         * userTreasure : 0
         */

        private List<LpdbListBean> lpdbList;
        /**
         * attentionIsOff : 0
         * headicon : http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png
         * nickname : Boss_21742154
         * userAttentionNum : 3
         * userLevelMark : 3
         * userid : 1LcPDz1IkvC5m665
         */

        private List<LpebListBean> lpebList;

        public int getSearchState() {
            return searchState;
        }

        public void setSearchState(int searchState) {
            this.searchState = searchState;
        }

        public List<LnbListBean> getLnbList() {
            return lnbList;
        }

        public void setLnbList(List<LnbListBean> lnbList) {
            this.lnbList = lnbList;
        }

        public List<LpdbListBean> getLpdbList() {
            return lpdbList;
        }

        public void setLpdbList(List<LpdbListBean> lpdbList) {
            this.lpdbList = lpdbList;
        }

        public List<LpebListBean> getLpebList() {
            return lpebList;
        }

        public void setLpebList(List<LpebListBean> lpebList) {
            this.lpebList = lpebList;
        }






    }
}
