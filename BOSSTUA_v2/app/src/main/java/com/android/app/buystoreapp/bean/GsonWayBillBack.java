package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/11/10.
 */
public class GsonWayBillBack implements Serializable {
    private static final long serialVersionUID = -4650323588389918606L;
    private String result;
    private String resultNote;
    private List<Details> list;

    public List<Details> getList() {
        return list;
    }

    public void setList(List<Details> list) {
        this.list = list;
    }

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

    /*
    *  "logisticsCode":"yuantong",                              //物流编码
                "logisticsName":"圆通速递"                                 //物流名称

    * */
    public class Details implements Serializable {
        private String logisticsCode;
        private String logisticsName;

        public String getLogisticsCode() {
            return logisticsCode;
        }

        public void setLogisticsCode(String logisticsCode) {
            this.logisticsCode = logisticsCode;
        }

        public String getLogisticsName() {
            return logisticsName;
        }

        public void setLogisticsName(String logisticsName) {
            this.logisticsName = logisticsName;
        }
    }
}
