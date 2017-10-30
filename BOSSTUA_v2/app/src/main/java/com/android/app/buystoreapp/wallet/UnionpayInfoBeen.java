package com.android.app.buystoreapp.wallet;

/**
 * Created by 尚帅波 on 2016/9/19.
 */
public class UnionpayInfoBeen {
    private String unionpayName;
    private String other;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getUnionpayName() {
        return unionpayName;
    }

    public void setUnionpayName(String unionpayName) {
        this.unionpayName = unionpayName;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "UnionpayInfoBeen{" +
                "UnionpayName='" + unionpayName + '\'' +
                ", other='" + other + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }
}
