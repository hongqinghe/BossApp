package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class UserAuthCodeBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4728988001080208445L;

    private String userName;
    private String newPassword;
    private String authCode;

    public UserAuthCodeBean() {
    }

    public UserAuthCodeBean(String userName) {
        super();
        this.userName = userName;
    }

    public UserAuthCodeBean(String userName, String authCode) {
        super();
        this.userName = userName;
        this.authCode = authCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "UserAuthCode [userName=" + userName + ", newPassword="
                + newPassword + ", authCode=" + authCode + "]";
    }

}
