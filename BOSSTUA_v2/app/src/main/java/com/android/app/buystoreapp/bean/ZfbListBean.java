package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/3.
 */
public  class ZfbListBean implements Serializable   {
    private String bindPayTreasureId;
    private String payTreasureNum;
    private String payTreasureRealName;

    public String getBindPayTreasureId() {
        return bindPayTreasureId;
    }

    public void setBindPayTreasureId(String bindPayTreasureId) {
        this.bindPayTreasureId = bindPayTreasureId;
    }

    public String getPayTreasureNum() {
        return payTreasureNum;
    }

    public void setPayTreasureNum(String payTreasureNum) {
        this.payTreasureNum = payTreasureNum;
    }

    public String getPayTreasureRealName() {
        return payTreasureRealName;
    }

    public void setPayTreasureRealName(String payTreasureRealName) {
        this.payTreasureRealName = payTreasureRealName;
    }
}
