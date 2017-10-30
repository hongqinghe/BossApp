package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonEditUserCmd implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5270148973599493252L;
    private String cmd;
    private EditUserInfoBean params;

    public GsonEditUserCmd(String cmd, EditUserInfoBean params) {
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

    public EditUserInfoBean getParams() {
        return params;
    }

    public void setParams(EditUserInfoBean params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "GsonLoginCmd [cmd=" + cmd + ", params=" + params + "]";
    }

}
