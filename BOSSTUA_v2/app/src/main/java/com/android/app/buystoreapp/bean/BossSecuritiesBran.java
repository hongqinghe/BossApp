package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
public class BossSecuritiesBran implements Serializable{

    /**
     * bossList : [{"headicon":"","id":"jldfa09sdjfeooig","nickname":"俺村俺最牛","payCount":1,"payDate":"2016-10-27","status":1}]
     * bossTicket : 99999
     * result : 0
     * resultNote : 查询成功
     */

    private int bossTicket;
    private String result;
    private String resultNote;
    /**
     * headicon :
     * id : jldfa09sdjfeooig
     * nickname : 俺村俺最牛
     * payCount : 1
     * payDate : 2016-10-27
     * status : 1
     */

    private List<BossListBean> bossList;

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

    public List<BossListBean> getBossList() {
        return bossList;
    }

    public void setBossList(List<BossListBean> bossList) {
        this.bossList = bossList;
    }


}
