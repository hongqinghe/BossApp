package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * 收藏店铺
 * @author Mikes Lee
 *
 */
public class GsonStarShopCmd implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7914621087864325768L;
    private String cmd;
    private String shopID;
    private String userName;
    private String stateType;

    public GsonStarShopCmd(String cmd, String shopID, String userName,
            String stateType) {
        super();
        this.cmd = cmd;
        this.shopID = shopID;
        this.userName = userName;
        this.stateType = stateType;
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

    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }

    @Override
    public String toString() {
        return "GsonStarShopCmd [cmd=" + cmd + ", shopID=" + shopID
                + ", userName=" + userName + ", stateType=" + stateType + "]";
    }

}
