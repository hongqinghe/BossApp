package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/09/16.
 */
public class GsonHomeBean implements Serializable{
    private static final long serialVersionUID = 3243375284739673261L;
    private String result;
    private String resultNote;
    private int totalPage;//总页数
    private List<HomeProBean> commodityProList;

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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<HomeProBean> getCommodityProList() {
        return commodityProList;
    }

    public void setCommodityProList(List<HomeProBean> commodityProList) {
        this.commodityProList = commodityProList;
    }
}
