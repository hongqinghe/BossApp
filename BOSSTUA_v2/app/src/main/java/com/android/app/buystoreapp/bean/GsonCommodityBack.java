package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 商品数据返回
 * 
 * @author Mikes Lee
 * 
 */
public class GsonCommodityBack implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8430585796851590976L;
    private String result;
    private String resultNote;
    private List<CommodityCategory> commodityCategoryList;

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

    public List<CommodityCategory> getCommodityCategoryList() {
        return commodityCategoryList;
    }

    public void setCommodityCategoryList(
            List<CommodityCategory> commodityCategoryList) {
        this.commodityCategoryList = commodityCategoryList;
    }

    @Override
    public String toString() {
        return "GsonCommodityBack [result=" + result + ", resultNote="
                + resultNote + ", commodityCategoryList="
                + commodityCategoryList + "]";
    }

}
