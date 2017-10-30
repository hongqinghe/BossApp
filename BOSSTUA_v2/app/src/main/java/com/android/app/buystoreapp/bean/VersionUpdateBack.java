package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc
 * Created by likaihang on 16/11/19.
 */
public class VersionUpdateBack implements Serializable{
    private String result;
    private String resultNote;
    private VersionUpdateBean appVersion;

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

    public VersionUpdateBean getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(VersionUpdateBean appVersion) {
        this.appVersion = appVersion;
    }
}
