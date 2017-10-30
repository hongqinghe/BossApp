package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class GsonShopback implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3571116962566916539L;
    private String result;
    private String resultNote;
    private String totalPage;
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

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<ShopBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<ShopBean> shopList) {
        this.shopList = shopList;
    }

    @Override
    public String toString() {
        return "GsonShopback [result=" + result + ", resultNote=" + resultNote
                + ", totalPage=" + totalPage + ", shopList=" + shopList + "]";
    }

}
