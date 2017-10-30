package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by 尚帅波 on 2016/10/6.
 */
public class GsonCommentCmd implements Serializable{
    private static final long serialVersionUID = -346700642090900429L;

    /**
     * cmd : queryProductEvaluate
     * indexId : 0
     * proId : n6OHEqaYHSaJir37
     * nowPage : 1
     */

    private String cmd;
    private String indexId;
    private String proId;
    private int nowPage;
    /**
     * proUserId : 00js8ooWvMb2U8CB
     * pageSize : 3
     */

    private String proUserId;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }


    public String getProUserId() {
        return proUserId;
    }

    public void setProUserId(String proUserId) {
        this.proUserId = proUserId;
    }

    @Override
    public String toString() {
        return "GsonCommentCmd{" +
                "cmd='" + cmd + '\'' +
                ", indexId='" + indexId + '\'' +
                ", proId='" + proId + '\'' +
                ", nowPage=" + nowPage +
                ", proUserId='" + proUserId + '\'' +
                '}';
    }
}
