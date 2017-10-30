package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class GsonHomeBack implements Serializable {
    /**
     * 首页列表
     */
    private static final long serialVersionUID = 5836944569282341461L;
    private String result;
    private String resultNote;
    private String totalPage;//总页数
//    private List<HomeProBean> commodityProList;
    private List<CommodityBean> commodityProList;

    public List<CommodityBean> getCommodityProList() {
        return commodityProList;
    }

    public void setCommodityProList(List<CommodityBean> commodityProList) {
        this.commodityProList = commodityProList;
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

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "GsonHomeBack [result=" + result + ", resultNote=" + resultNote
                + ", totalPage=" + totalPage + ", commodityProList="
                + commodityProList + "]";
    }

}
