package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc
 * Created by likaihang on 16/11/09.
 */
public class GsonCreatOrderBack implements Serializable{
    private static final long serialVersionUID = 846659567496056075L;
    private String result;
    private String resultNote;
    private String[] orderids;

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

    public String[] getOrderids() {
        return orderids;
    }

    public void setOrderids(String[] orderids) {
        this.orderids = orderids;
    }
}
