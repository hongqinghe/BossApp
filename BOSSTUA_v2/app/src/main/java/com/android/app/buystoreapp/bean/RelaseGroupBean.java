package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc
 * Created by likaihang on 16/09/21.
 */
public class RelaseGroupBean implements Serializable{

    private static final long serialVersionUID = 366944324825746624L;
    /**
     * "moreGroPrice":43.0,       //价格                        （double类型）
     "moreGroName": "红色上衣",    //组合名称
     "proSurplus":223             //剩余量                     （int类型）
     * */
    public String moreGroName;
    public double moreGroPrice;
    public int moreGroSurplus;
    public String moreGroUnit; //单位
    public String moreGroId; //id

    public String getMoreGroId() {
        return moreGroId;
    }

    public void setMoreGroId(String moreGroId) {
        this.moreGroId = moreGroId;
    }

    public int getMoreGroSurplus() {
        return moreGroSurplus;
    }

    public void setMoreGroSurplus(int moreGroSurplus) {
        this.moreGroSurplus = moreGroSurplus;
    }

    public String getMoreGroName() {
        return moreGroName;
    }

    public void setMoreGroName(String moreGroName) {
        this.moreGroName = moreGroName;
    }

    public String getMoreGroUnit() {
        return moreGroUnit;
    }

    public void setMoreGroUnit(String moreGroUnit) {
        this.moreGroUnit = moreGroUnit;
    }

    public double getMoreGroPrice() {
        return moreGroPrice;
    }

    public void setMoreGroPrice(double moreGroPrice) {
        this.moreGroPrice = moreGroPrice;
    }


    @Override
    public String toString() {
        return "RelaseGroupBean{" +
                "moreGroName='" + moreGroName + '\'' +
                ", moreGroPrice=" + moreGroPrice +
                ", moreGroSurplus=" + moreGroSurplus +
                ", moreGroUnit='" + moreGroUnit + '\'' +
                '}';
    }
}
