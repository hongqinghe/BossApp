package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc
 * Created by likaihang on 16/10/10.
 */
public class GroupBean implements Serializable{
    private static final long serialVersionUID = -139290140459569312L;
    private String moreGroId;
        private String moreGroName;
        private String moreGroPrice;
        private int moreGroSurplus;
        private String moreGroUnit;

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

    public int getMoreGroSurplus() {
        return moreGroSurplus;
    }

    public void setMoreGroSurplus(int moreGroSurplus) {
        this.moreGroSurplus = moreGroSurplus;
    }

    public String getMoreGroUnit() {
            return moreGroUnit;
        }

        public void setMoreGroUnit(String moreGroUnit) {
            this.moreGroUnit = moreGroUnit;
        }
}
