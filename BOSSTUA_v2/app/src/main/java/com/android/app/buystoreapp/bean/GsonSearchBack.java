package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class GsonSearchBack implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3288684666309373188L;
    private String result;
    private String resultNote;
    private List<CommodityBean> commodityList;
    private List<ShopBean> shopList;

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

    public List<CommodityBean> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<CommodityBean> commodityList) {
        this.commodityList = commodityList;
    }

    public List<ShopBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<ShopBean> shopList) {
        this.shopList = shopList;
    }

    @Override
    public String toString() {
        return "GsonSearchBack [result=" + result + ", resultNote="
                + resultNote + ", commodityList=" + commodityList
                + ", shopList=" + shopList + "]";
    }

}
