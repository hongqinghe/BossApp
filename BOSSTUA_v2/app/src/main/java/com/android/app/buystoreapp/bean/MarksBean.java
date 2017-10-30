package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by shangshuaibo on 2016/12/15 12:40
 */
public class MarksBean implements Serializable {

    /**
     * result : 0
     * resultNote : 支付成功
     * bean : {"bindingMark1":0,"bindingMark2":0,"bindingMark3":0,"bindingMark4":0,
     * "userLevelMark":1}
     */

    private String result;
    private String resultNote;
    /**
     * bindingMark1 : 0
     * bindingMark2 : 0
     * bindingMark3 : 0
     * bindingMark4 : 0
     * userLevelMark : 1
     */

    private BeanBean bean;

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

    public BeanBean getBean() {
        return bean;
    }

    public void setBean(BeanBean bean) {
        this.bean = bean;
    }

    public static class BeanBean {
        private int bindingMark1;
        private int bindingMark2;
        private int bindingMark3;
        private int bindingMark4;
        private int userLevelMark;
        private String userTreasure;

        public int getBindingMark1() {
            return bindingMark1;
        }

        public void setBindingMark1(int bindingMark1) {
            this.bindingMark1 = bindingMark1;
        }

        public int getBindingMark2() {
            return bindingMark2;
        }

        public void setBindingMark2(int bindingMark2) {
            this.bindingMark2 = bindingMark2;
        }

        public int getBindingMark3() {
            return bindingMark3;
        }

        public void setBindingMark3(int bindingMark3) {
            this.bindingMark3 = bindingMark3;
        }

        public int getBindingMark4() {
            return bindingMark4;
        }

        public void setBindingMark4(int bindingMark4) {
            this.bindingMark4 = bindingMark4;
        }

        public int getUserLevelMark() {
            return userLevelMark;
        }

        public void setUserLevelMark(int userLevelMark) {
            this.userLevelMark = userLevelMark;
        }

        public String getUserTreasure() {
            return userTreasure;
        }

        public void setUserTreasure(String userTreasure) {
            this.userTreasure = userTreasure;
        }
    }
}
