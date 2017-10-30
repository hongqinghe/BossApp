package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonShopCmd implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1068339870473883378L;
    private String cmd;
    /**
     * 0 品牌街 1 推荐街 2 活动街 3 二手街 4 维修街 5 回收街 6 默认全部
     */
    private String shopType;
    private String cityID;
    /**
     * 此字段不为空的事后返回搜索结果 参数为空时返回所有结果
     */
    private String searchText;
    /**
     * 0 距离排序 1 价格排序 3 ,好评排序
     */
    private String orderBy;
    /**
     * 商铺分类ID 为空时候显示全部
     */
    private String shopCategoryID;
    private String pageSize;
    private String nowPage;

    /**
     * 用户经纬度
     */
    private String userLat;
    private String userLong;

    /**
     * 
     * @param cmd
     * @param shopType
     * @param cityID
     * @param searchText
     * @param orderBy
     * @param shopCategoryID
     * @param pageSize
     * @param nowPage
     * @param userLat
     * @param userLong
     */
    public GsonShopCmd(String cmd, String shopType, String cityID,
            String searchText, String orderBy, String shopCategoryID,
            String pageSize, String nowPage, String userLat, String userLong) {
        super();
        this.cmd = cmd;
        this.shopType = shopType;
        this.cityID = cityID;
        this.searchText = searchText;
        this.orderBy = orderBy;
        this.shopCategoryID = shopCategoryID;
        this.pageSize = pageSize;
        this.nowPage = nowPage;
        this.userLat = userLat;
        this.userLong = userLong;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getShopCategoryID() {
        return shopCategoryID;
    }

    public void setShopCategoryID(String shopCategoryID) {
        this.shopCategoryID = shopCategoryID;
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

    public String getUserLat() {
        return userLat;
    }

    public void setUserLat(String userLat) {
        this.userLat = userLat;
    }

    public String getUserLong() {
        return userLong;
    }

    public void setUserLong(String userLong) {
        this.userLong = userLong;
    }

    @Override
    public String toString() {
        return "GsonShopCmd [cmd=" + cmd + ", shopType=" + shopType
                + ", cityID=" + cityID + ", searchText=" + searchText
                + ", orderBy=" + orderBy + ", shopCategoryID=" + shopCategoryID
                + ", pageSize=" + pageSize + ", nowPage=" + nowPage
                + ", userLat=" + userLat + ", userLong=" + userLong + "]";
    }

}
