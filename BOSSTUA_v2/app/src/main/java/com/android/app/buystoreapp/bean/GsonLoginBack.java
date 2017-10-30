package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonLoginBack implements Serializable {
    private static final long serialVersionUID = -5651197790561028387L;
    private String result;
    private String resultNote;
    private UserInfoBean userinfoBean;

    public GsonLoginBack(String result, String resultNote,
            UserInfoBean userinfoBean) {
        super();
        this.result = result;
        this.resultNote = resultNote;
        this.userinfoBean = userinfoBean;
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

    public UserInfoBean getUserinfoBean() {
        return userinfoBean;
    }

    public void setUserinfoBean(UserInfoBean userinfoBean) {
        this.userinfoBean = userinfoBean;
    }

    @Override
    public String toString() {
        return "GsonLoginBack [result=" + result + ", resultNote=" + resultNote
                + ", userinfoBean=" + userinfoBean + "]";
    }

}
