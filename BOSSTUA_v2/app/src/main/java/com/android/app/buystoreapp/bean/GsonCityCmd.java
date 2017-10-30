package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonCityCmd implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3125763701350760379L;

    private String cmd;

    public GsonCityCmd(String cmd) {
        super();
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "GsonCityCmd [cmd=" + cmd + "]";
    }

}
