package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc
 * Created by likaihang on 16/09/06.
 */
public class GsonClassifyCmd implements Serializable {
    private static final long serialVersionUID = 5697498280198799885L;
    private String cmd;
    private int classify;

    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public GsonClassifyCmd(String cmd,int classify) {
        super();
        this.cmd = cmd;
        this.classify = classify;
    }


    @Override
    public String toString() {
        return "GsonClassifyCmd [cmd=" + cmd + "]";
    }
}
