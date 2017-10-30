package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class GsonConfirmOrderBack implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6017040387255953805L;
    private String result;
    private String resultNote;
    private String buyOrderid;
    private List<String> proName;

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

    public String getBuyOrderid() {
        return buyOrderid;
    }

    public void setBuyOrderid(String buyOrderid) {
        this.buyOrderid = buyOrderid;
    }

    public List<String> getProName() {
        return proName;
    }

    public void setProName(List<String> proName) {
        this.proName = proName;
    }

    @Override
    public String toString() {
        return "GsonConfirmOrderBack{" +
                "result='" + result + '\'' +
                ", resultNote='" + resultNote + '\'' +
                ", buyOrderid='" + buyOrderid + '\'' +
                ", proName=" + proName +
                '}';
    }
}
