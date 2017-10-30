package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class GsonNewsBack implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3294670269569390228L;
    private String result;
    private String resultNote;
    private String totalPage;
    private List<NewsInfo> newsList;

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

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<NewsInfo> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsInfo> newsList) {
        this.newsList = newsList;
    }

    @Override
    public String toString() {
        return "GsonNewsBack [result=" + result + ", resultNote=" + resultNote
                + ", totalPage=" + totalPage + ", newsList=" + newsList + "]";
    }

}
