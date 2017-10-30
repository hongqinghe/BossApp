package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/11.
 */
public class OpenVipBean implements Serializable {

    /**
     * vipOrderNum : 20161110599
     * vipPayPrice : 1825
     */

    private BeanBean bean;
    /**
     * bean : {"vipOrderNum":"20161110599","vipPayPrice":1825}
     * result : 0
     * resultNote : 开通成功
     */

    private String result;
    private String resultNote;

    public BeanBean getBean() {
        return bean;
    }

    public void setBean(BeanBean bean) {
        this.bean = bean;
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

    public static class BeanBean {
        private String vipOrderNum;
        private double vipPayPrice;

        public String getVipOrderNum() {
            return vipOrderNum;
        }

        public void setVipOrderNum(String vipOrderNum) {
            this.vipOrderNum = vipOrderNum;
        }

        public double getVipPayPrice() {
            return vipPayPrice;
        }

        public void setVipPayPrice(double vipPayPrice) {
            this.vipPayPrice = vipPayPrice;
        }
    }
}
