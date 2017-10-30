package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/09/06.
 */
public class GsonClassifyBack implements Serializable{
    private static final long serialVersionUID = 3877800303484329105L;
    private String result;
    private String resultNote;
    private List<ChanceInfoBean> categoryList;

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

    public List<ChanceInfoBean> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ChanceInfoBean> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        return "GsonClassifyBack [result=" + result + ", resultNote="
                + resultNote + ", classifylist="
                + categoryList + "]";
    }
}
