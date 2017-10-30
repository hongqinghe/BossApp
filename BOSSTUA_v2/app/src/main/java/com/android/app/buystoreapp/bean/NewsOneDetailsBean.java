package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/17.
 */
public class NewsOneDetailsBean implements Serializable{

    /**
     * headicon : defalt_usericon.png
     * newsContent : 60
     * newsCreatetime : 2015-11-16
     * newsIcon : /bossgroupimage/news/94/94.jpg
     * newsId : 94
     * newsSeeNum : 0
     * newsSubtitle : 李想
     * newsTitle : 李想用十六年的创业经验告诉你，做到第一名（下）
     * newsUrl : http://218.241.30.183:8080/bossgroupbuying/news/displayNews?newsId=94
     * nickname :
     * subscribeId :
     * subscribeIsOff : 0
     * userId : K8eMXw49BHzssPQ6
     * userLeveLmark : 0
     * userPosition :
     * userSubscribeNum : 0
     */

    private GetNewsOneDetailsBean getNewsOneDetails;
    /**
     * getNewsOneDetails : {"headicon":"defalt_usericon.png","newsContent":"60","newsCreatetime":"2015-11-16","newsIcon":"/bossgroupimage/news/94/94.jpg","newsId":"94","newsSeeNum":0,"newsSubtitle":"李想","newsTitle":"李想用十六年的创业经验告诉你，做到第一名（下）","newsUrl":"http://218.241.30.183:8080/bossgroupbuying/news/displayNews?newsId=94","nickname":"","subscribeId":"","subscribeIsOff":0,"userId":"K8eMXw49BHzssPQ6","userLeveLmark":0,"userPosition":"","userSubscribeNum":0}
     * result : 0
     * resultNote : 成功
     */

    private String result;
    private String resultNote;

    public GetNewsOneDetailsBean getGetNewsOneDetails() {
        return getNewsOneDetails;
    }

    public void setGetNewsOneDetails(GetNewsOneDetailsBean getNewsOneDetails) {
        this.getNewsOneDetails = getNewsOneDetails;
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


}
