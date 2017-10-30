package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 举报的发送请求
 * Created by 尚帅波 on 2016/10/20.
 */
public class AddReportCmd implements Serializable{

    private static final long serialVersionUID = 1927502384202260596L;
    /**
     * cmd : addReport
     * passiveUserID : 20151102100140
     * passiveProID : 20151102100140
     * reportUserID : 20151102100140
     * reasonFixed : 20151102100140
     * reasonUnset : 20151102100140
     * imagList : [{"webrootpath":"bosstuan/afeefn.png"},{"webrootpath":"bosstuan1/baafefabvn.png"}]
     */

    private String cmd;
    private String passiveUserID;
    private String passiveProID;
    private String reportUserID;
    private String reasonFixed;
    private String reasonUnset;
    /**
     * webrootpath : bosstuan/afeefn.png
     */

    private List<ImagListBean> imagList;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getPassiveUserID() {
        return passiveUserID;
    }

    public void setPassiveUserID(String passiveUserID) {
        this.passiveUserID = passiveUserID;
    }

    public String getPassiveProID() {
        return passiveProID;
    }

    public void setPassiveProID(String passiveProID) {
        this.passiveProID = passiveProID;
    }

    public String getReportUserID() {
        return reportUserID;
    }

    public void setReportUserID(String reportUserID) {
        this.reportUserID = reportUserID;
    }

    public String getReasonFixed() {
        return reasonFixed;
    }

    public void setReasonFixed(String reasonFixed) {
        this.reasonFixed = reasonFixed;
    }

    public String getReasonUnset() {
        return reasonUnset;
    }

    public void setReasonUnset(String reasonUnset) {
        this.reasonUnset = reasonUnset;
    }

    public List<ImagListBean> getImagList() {
        return imagList;
    }

    public void setImagList(List<ImagListBean> imagList) {
        this.imagList = imagList;
    }

    public static class ImagListBean {
        private String webrootpath;

        public String getWebrootpath() {
            return webrootpath;
        }

        public void setWebrootpath(String webrootpath) {
            this.webrootpath = webrootpath;
        }
    }
}
