package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonBackOnlyResult implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5133079015566031064L;

    private String result;
    private String resultNote;

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

    @Override
    public String toString() {
        return "GsonUserAuthBack [result=" + result + ", resultNote="
                + resultNote + "]";
    }

}
