package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonAppVersionBack implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -4742176730824227766L;
    private String result;
    private String resultNote;
    private String downurl;
    private String id;
    private String upmsg;
    private String uptime;
    private String vcode;
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getResultNote() {
        return resultNote;
    }
    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }
    public String getDownurl() {
        return downurl;
    }
    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUpmsg() {
        return upmsg;
    }
    public void setUpmsg(String upmsg) {
        this.upmsg = upmsg;
    }
    public String getUptime() {
        return uptime;
    }
    public void setUptime(String uptime) {
        this.uptime = uptime;
    }
    public String getVcode() {
        return vcode;
    }
    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
    @Override
    public String toString() {
        return "GsonAppVersionBack [result=" + result + ", resultNote="
                + resultNote + ", downurl=" + downurl + ", id=" + id
                + ", upmsg=" + upmsg + ", uptime=" + uptime + ", vcode="
                + vcode + "]";
    }
    
    
}
