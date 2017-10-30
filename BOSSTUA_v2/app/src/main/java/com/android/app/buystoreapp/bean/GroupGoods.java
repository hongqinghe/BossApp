package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc
 * Created by likaihang on 16/10/27.
 */
 /*
                        "weekEnd":"5",                         	//结束时间（单位：周）
                        "weekStart":"1",                      	//开始时间（单位：周）
                        "dayTimeEnd":"13:12",                     	//结束时间（单位：小时）
                        "dayTimeStart":"13:8",                    	//开始时间（单位：小时）
                        "modes":0,          	 //服务方式0线上 1 线下2 货运	（int类型）
                        "freightMode":0, 	//货运方式  0  免运费1货到付款  （int类型）
                        "freightPrice":"" 	                   //货运价格（单价）
                        "proStatus":1                     	//商品状态0下架1上架2已删除*/
public class GroupGoods implements Serializable {
    private static final long serialVersionUID = 7229095537521676491L;
    private String moreGroId;//组合id
    private String moreGroName;//组合名称
    private String moreGroPrice;//组合价格
    private int moreGroSurplus;//组合库存
    private String proImageMin;//商品图片
    private String proName;//商品名称
    private int count;//组合数量
    private String shopCarId;//购物车id

    private String weekEnd;
    private String weekStart;
    private String dayTimeEnd;
    private String dayTimeStart;
    private int modes;
    private int freightMode;
    private String freightPrice;
    private int proStatus;

    /**
     * 商品是否被选中
     */
    private boolean isChildSelected;

    public boolean isChildSelected() {
        return isChildSelected;
    }

    public void setIsChildSelected(boolean childSelected) {
        isChildSelected = childSelected;
    }

    public String getMoreGroId() {
        return moreGroId;
    }

    public void setMoreGroId(String moreGroId) {
        this.moreGroId = moreGroId;
    }

    public String getMoreGroName() {
        return moreGroName;
    }

    public void setMoreGroName(String moreGroName) {
        this.moreGroName = moreGroName;
    }

    public String getMoreGroPrice() {
        return moreGroPrice;
    }

    public void setMoreGroPrice(String moreGroPrice) {
        this.moreGroPrice = moreGroPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMoreGroSurplus() {
        return moreGroSurplus;
    }

    public void setMoreGroSurplus(int moreGroSurplus) {
        this.moreGroSurplus = moreGroSurplus;
    }

    public String getProImageMin() {
        return proImageMin;
    }

    public void setProImageMin(String proImageMin) {
        this.proImageMin = proImageMin;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getShopCarId() {
        return shopCarId;
    }

    public void setShopCarId(String shopCarId) {
        this.shopCarId = shopCarId;
    }

    public String getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(String weekEnd) {
        this.weekEnd = weekEnd;
    }

    public String getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(String weekStart) {
        this.weekStart = weekStart;
    }

    public String getDayTimeEnd() {
        return dayTimeEnd;
    }

    public void setDayTimeEnd(String dayTimeEnd) {
        this.dayTimeEnd = dayTimeEnd;
    }

    public String getDayTimeStart() {
        return dayTimeStart;
    }

    public void setDayTimeStart(String dayTimeStart) {
        this.dayTimeStart = dayTimeStart;
    }

    public int getModes() {
        return modes;
    }

    public void setModes(int modes) {
        this.modes = modes;
    }

    public int getFreightMode() {
        return freightMode;
    }

    public void setFreightMode(int freightMode) {
        this.freightMode = freightMode;
    }

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public int getProStatus() {
        return proStatus;
    }

    public void setProStatus(int proStatus) {
        this.proStatus = proStatus;
    }
}
