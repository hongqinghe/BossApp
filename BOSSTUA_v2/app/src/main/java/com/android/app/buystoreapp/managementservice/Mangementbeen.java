package com.android.app.buystoreapp.managementservice;

import java.io.Serializable;

/**
 * 管理服务Been
 *
 * 魏林编写
 * Created by Administrator on 2016/9/27.
 */
public class Mangementbeen implements Serializable{
    private static final long serialVersionUID = -47024819496085730L;
    private String proId;//id
    private String image; //图片
    private String titlename; //标题
    private String unitrice;//998元/单
    private String soldorders;//已售[69]
    private String  remainingorders;//仅剩[998]
    private String othernumber;//15.6K
    private boolean uoordown;//是否上架

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitlename() {
        return titlename;
    }

    public void setTitlename(String titlename) {
        this.titlename = titlename;
    }

    public String getUnitrice() {
        return unitrice;
    }

    public void setUnitrice(String unitrice) {
        this.unitrice = unitrice;
    }

    public String getSoldorders() {
        return soldorders;
    }

    public void setSoldorders(String soldorders) {
        this.soldorders = soldorders;
    }

    public String getRemainingorders() {
        return remainingorders;
    }

    public void setRemainingorders(String remainingorders) {
        this.remainingorders = remainingorders;
    }

    public String getOthernumber() {
        return othernumber;
    }

    public void setOthernumber(String othernumber) {
        this.othernumber = othernumber;
    }

    public boolean isUoordown() {
        return uoordown;
    }

    public void setUoordown(boolean uoordown) {
        this.uoordown = uoordown;
    }

    @Override
    public String toString() {
        return "Mangementbeen{" +
                "image='" + image + '\'' +
                ", titlename='" + titlename + '\'' +
                ", nitrice='" + unitrice + '\'' +
                ", soldorders='" + soldorders + '\'' +
                ", remainingorders='" + remainingorders + '\'' +
                ", othernumber='" + othernumber + '\'' +
                ", uoordown=" + uoordown +
                '}';
    }
}
