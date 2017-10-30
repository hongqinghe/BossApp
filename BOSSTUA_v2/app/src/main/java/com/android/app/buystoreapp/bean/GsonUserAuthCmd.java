package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonUserAuthCmd implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1446271084014999660L;
    private String cmd;
    private UserAuthCodeBean params;

    public GsonUserAuthCmd(String cmd, UserAuthCodeBean params) {
        super();
        this.cmd = cmd;
        this.params = params;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public UserAuthCodeBean getParams() {
        return params;
    }

    public void setParams(UserAuthCodeBean params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "GsonUserAuthCmd [cmd=" + cmd + ", params=" + params + "]";
    }

}
