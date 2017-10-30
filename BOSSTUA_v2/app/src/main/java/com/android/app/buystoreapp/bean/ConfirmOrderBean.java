package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class ConfirmOrderBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4325330091380951656L;
    private String commodityID;
    private String commodityNum;
    private String sumMoney;
    private String leaveMessage;

    /**
     * 
     * @param commodityID
     * @param commodityNum
     * @param sumMoney
     * @param leaveMessage
     */
    public ConfirmOrderBean(String commodityID, String commodityNum,
            String sumMoney, String leaveMessage) {
        super();
        this.commodityID = commodityID;
        this.commodityNum = commodityNum;
        this.sumMoney = sumMoney;
        this.leaveMessage = leaveMessage;
    }

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
    }

    public String getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(String commodityNum) {
        this.commodityNum = commodityNum;
    }

    public String getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(String sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getLeaveMessage() {
        return leaveMessage;
    }

    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }

    @Override
    public String toString() {
        return "ConfirmOrderBean [commodityID=" + commodityID
                + ", commodityNum=" + commodityNum + ", sumMoney=" + sumMoney
                + ", leaveMessage=" + leaveMessage + "]";
    }

}
