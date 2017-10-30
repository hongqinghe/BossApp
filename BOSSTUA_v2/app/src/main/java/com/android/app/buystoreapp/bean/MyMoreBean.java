package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 尚帅波 on 2016/10/15.
 */
public class MyMoreBean implements Serializable {
    private static final long serialVersionUID = -5828880906664695778L;


    /**
     * userAndProManager : [{"attentionIsOff":1,"bindingMark1":"1","bindingMark2":"1",
     * "bindingMark3":"1","bindingMark4":"1","headicon":"http://localhost:8080/abc.png",
     * "nickname":"boss_李四","userAttentionNum":"2","userLevelMark":"2","userPosition":"老师",
     * "userTreasure":"4332","userAllProduct":[{"imgList":[{"isCover":0,
     * "proImageId":"68Qm8u1Pa84teQJl","proImageMin":"http://localhost:8080/abc.png",
     * "proImageUrl":"http://localhost:8080/a.png"}],"distance":739,"moreGroPrice":"43.0~455.0",
     * "moreGroUnit":"元","proDes":"清仓大处理","proName":"iphone70","proSale":48,"proSeeNum":1,
     * "proSurplus":0}]}]
     * result : 0
     * resultNote : 加载成功
     * totaPage : 0
     */

    private String result;
    private String resultNote;
    private int totaPage;
    /**
     * attentionIsOff : 1
     * bindingMark1 : 1
     * bindingMark2 : 1
     * bindingMark3 : 1
     * bindingMark4 : 1
     * headicon : http://localhost:8080/abc.png
     * nickname : boss_李四
     * userAttentionNum : 2
     * userLevelMark : 2
     * userPosition : 老师
     * userTreasure : 4332
     * userAllProduct : [{"imgList":[{"isCover":0,"proImageId":"68Qm8u1Pa84teQJl",
     * "proImageMin":"http://localhost:8080/abc.png","proImageUrl":"http://localhost:8080/a
     * .png"}],"distance":739,"moreGroPrice":"43.0~455.0","moreGroUnit":"元","proDes":"清仓大处理","proName":"iphone70","proSale":48,"proSeeNum":1,"proSurplus":0}]
     */

    private List<UserAndProManagerBean> userAndProManager;

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

    public int getTotaPage() {
        return totaPage;
    }

    public void setTotaPage(int totaPage) {
        this.totaPage = totaPage;
    }

    public List<UserAndProManagerBean> getUserAndProManager() {
        return userAndProManager;
    }

    public void setUserAndProManager(List<UserAndProManagerBean> userAndProManager) {
        this.userAndProManager = userAndProManager;
    }


}
