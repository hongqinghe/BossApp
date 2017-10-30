package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class GsonConfirmOrderCmd implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3919090029188432706L;
    private String cmd;
    private String userName;
    private String commoditySendAdress;
    private String postCode;
    private String phone;
    private String receivingName;
    private List<ConfirmOrderBean> commodityList;

    /**
     * 
     * @param cmd
     * @param userName
     * @param commoditySendAdress
     * @param postCode
     * @param phone
     * @param receivingName
     * @param commodityList
     */
    public GsonConfirmOrderCmd(String cmd, String userName,
            String commoditySendAdress, String postCode, String phone,
            String receivingName, List<ConfirmOrderBean> commodityList) {
        super();
        this.cmd = cmd;
        this.userName = userName;
        this.commoditySendAdress = commoditySendAdress;
        this.postCode = postCode;
        this.phone = phone;
        this.receivingName = receivingName;
        this.commodityList = commodityList;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommoditySendAdress() {
        return commoditySendAdress;
    }

    public void setCommoditySendAdress(String commoditySendAdress) {
        this.commoditySendAdress = commoditySendAdress;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceivingName() {
        return receivingName;
    }

    public void setReceivingName(String receivingName) {
        this.receivingName = receivingName;
    }

    public List<ConfirmOrderBean> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<ConfirmOrderBean> commodityList) {
        this.commodityList = commodityList;
    }

    @Override
    public String toString() {
        return "GsonConfirmOrderCmd [cmd=" + cmd + ", userName=" + userName
                + ", commoditySendAdress=" + commoditySendAdress
                + ", postCode=" + postCode + ", phone=" + phone
                + ", receivingName=" + receivingName + ", commodityList="
                + commodityList + "]";
    }

}
