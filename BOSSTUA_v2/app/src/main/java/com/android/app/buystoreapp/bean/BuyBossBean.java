package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
public class BuyBossBean implements Serializable {

    /**
     * bossTicket : 99999
     * buyBossList : [{"buyCount":10,"headicon":"","id":"11","payDate":"2016-03-25","status":2}]
     * result : 0
     * resultNote : 查询成功
     */

    private int bossTicket;
    private String result;
    private String resultNote;
    /**
     * buyCount : 10
     * headicon :
     * id : 11
     * payDate : 2016-03-25
     * status : 2
     */

    private List<BuyBossListBean> buyBossList;

    public int getBossTicket() {
        return bossTicket;
    }

    public void setBossTicket(int bossTicket) {
        this.bossTicket = bossTicket;
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

    public List<BuyBossListBean> getBuyBossList() {
        return buyBossList;
    }

    public void setBuyBossList(List<BuyBossListBean> buyBossList) {
        this.buyBossList = buyBossList;
    }




}

