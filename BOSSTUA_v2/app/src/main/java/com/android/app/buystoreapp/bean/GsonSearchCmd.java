package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonSearchCmd implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4748919277190668450L;
    private String cmd;
    private String cityID;
    private String searchText;
    private String userLong;
    private String userLat;

    public GsonSearchCmd(String cmd, String cityID, String searchText,
            String userLong, String userLat) {
        super();
        this.cmd = cmd;
        this.cityID = cityID;
        this.searchText = searchText;
        this.userLong = userLong;
        this.userLat = userLat;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getUserLong() {
        return userLong;
    }

    public void setUserLong(String userLong) {
        this.userLong = userLong;
    }

    public String getUserLat() {
        return userLat;
    }

    public void setUserLat(String userLat) {
        this.userLat = userLat;
    }

    @Override
    public String toString() {
        return "GsonSearchCmd [cmd=" + cmd + ", cityID=" + cityID
                + ", searchText=" + searchText + ", userLong=" + userLong
                + ", userLat=" + userLat + "]";
    }

}
