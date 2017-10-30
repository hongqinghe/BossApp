package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonShopDetailCmd implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2659447637382861356L;
    private String cmd;
    private String categoryID;
    private String shopID;
    private String pageSize;
    private String nowPage;

    public GsonShopDetailCmd(String cmd, String categoryID, String shopID,
            String pageSize, String nowPage) {
        super();
        this.cmd = cmd;
        this.categoryID = categoryID;
        this.shopID = shopID;
        this.pageSize = pageSize;
        this.nowPage = nowPage;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getNowPage() {
        return nowPage;
    }

    public void setNowPage(String nowPage) {
        this.nowPage = nowPage;
    }

    @Override
    public String toString() {
        return "GsonShopDetailCmd [cmd=" + cmd + ", categoryID=" + categoryID
                + ", shopID=" + shopID + ", pageSize=" + pageSize
                + ", nowPage=" + nowPage + "]";
    }

}
