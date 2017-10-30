package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/10/09.
 */
public class GsonLeaveMeassageBck implements Serializable{
    private static final long serialVersionUID = -4672769415074066678L;
    private String result;
    private String resultNote;
    private List<LeaveMeassageBean> comment;

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

    public List<LeaveMeassageBean> getComment() {
        return comment;
    }

    public void setComment(List<LeaveMeassageBean> comment) {
        this.comment = comment;
    }
}
