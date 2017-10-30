package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/10/10.
 */
public class GsonGroupBack implements Serializable{

    private static final long serialVersionUID = -4656315955754029702L;
    private String result;
    private String resultNote;
    private List<GroupBean> mgList;

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

    public List<GroupBean> getMgList() {
        return mgList;
    }

    public void setMgList(List<GroupBean> mgList) {
        this.mgList = mgList;
    }

}
