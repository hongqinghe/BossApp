package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonShopStoreCmd implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7165273170239420552L;
    private String cmd;
    private String userName;
    private String shopID;

    public GsonShopStoreCmd(String cmd, String userName, String shopID) {
        super();
        this.cmd = cmd;
        this.userName = userName;
        this.shopID = shopID;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "GsonShopStoreCmd [cmd=" + cmd + ", userName=" + userName
                + ", shopID=" + shopID + "]";
    }
}
