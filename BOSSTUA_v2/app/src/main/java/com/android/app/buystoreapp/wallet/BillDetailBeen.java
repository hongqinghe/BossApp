package com.android.app.buystoreapp.wallet;

import java.io.Serializable;

/**
 * Created by 尚帅波 on 2016/9/18.
 */
public class BillDetailBeen implements Serializable {
    private static final long serialVersionUID = -7734319211322720420L;
    private String date;
    private String time;
    private String imgId;
    private String type;
    private double money;
    private String other;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "BillDetailBeen{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", imgId='" + imgId + '\'' +
                ", type='" + type + '\'' +
                ", money=" + money +
                ", other='" + other + '\'' +
                '}';
    }
}
