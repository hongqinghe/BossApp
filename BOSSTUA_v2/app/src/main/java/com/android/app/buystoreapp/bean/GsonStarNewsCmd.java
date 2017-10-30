package com.android.app.buystoreapp.bean;

public class GsonStarNewsCmd {
    private String cmd;
    private String newsID;
    private String userName;
    private String stateType;

    public GsonStarNewsCmd(String cmd, String newsID, String userName,
            String stateType) {
        super();
        this.cmd = cmd;
        this.newsID = newsID;
        this.userName = userName;
        this.stateType = stateType;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
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
        return "GsonStarNews [cmd=" + cmd + ", newsID=" + newsID
                + ", userName=" + userName + ", stateType=" + stateType + "]";
    }

}
