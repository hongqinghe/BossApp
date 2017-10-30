package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonLoginCmd implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5270148973599493252L;
    private String cmd;
    private UserInfoBean params;

    public GsonLoginCmd(String cmd, UserInfoBean params) {
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

    public UserInfoBean getParams() {
        return params;
    }

    public void setParams(UserInfoBean params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "GsonLoginCmd [cmd=" + cmd + ", params=" + params + "]";
    }

}
