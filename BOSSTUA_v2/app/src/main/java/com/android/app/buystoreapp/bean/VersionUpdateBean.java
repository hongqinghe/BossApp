package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc
 * Created by likaihang on 16/11/16.
 */
public class VersionUpdateBean implements Serializable {
    private static final long serialVersionUID = 1237289419062629691L;
    private String adtime;
    private String downurl;
    private int id;
    private int state;//0 禁用 1 正常 2  强制更新
    private String strDate;
    private String upmsg;
    private int vtype;
    private int versionCode;
    private String versionName;
    /**
     * 下载状态，true:下载,false:未下载
     */
    private boolean downloadStatus = false;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAdtime() {
        return adtime;
    }

    public void setAdtime(String adtime) {
        this.adtime = adtime;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getUpmsg() {
        return upmsg;
    }

    public void setUpmsg(String upmsg) {
        this.upmsg = upmsg;
    }

    public int getVtype() {
        return vtype;
    }

    public void setVtype(int vtype) {
        this.vtype = vtype;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
    public boolean isDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(boolean downloadStatus) {
        this.downloadStatus = downloadStatus;
    }
}
