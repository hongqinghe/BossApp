package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class BalanceWithdrawalsBean implements Serializable {

    /**
     * totalMoney : 999999.99
     * yhkList : [{"id":"tk8U3Nlu67Gg236c","visaName":"工商银行","visanoFour":"3123"}]
     * zfbList : [{"bindPayTreasureId":"781Nr1HX5riFa1tH","payTreasureNum":"999999999","payTreasureRealName":"刘龙"}]
     */

    private BeanBean bean;
    /**
     * bean : {"totalMoney":999999.99,"yhkList":[{"id":"tk8U3Nlu67Gg236c","visaName":"工商银行","visanoFour":"3123"}],"zfbList":[{"bindPayTreasureId":"781Nr1HX5riFa1tH","payTreasureNum":"999999999","payTreasureRealName":"刘龙"}]}
     * result : 0
     * resultNote : 查询成功
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
        private double totalMoney;
        private double txMoney;
        /**
         * id : tk8U3Nlu67Gg236c
         * visaName : 工商银行
         * visanoFour : 3123
         */

        private List<YhkListBean> yhkList;
        /**
         * bindPayTreasureId : 781Nr1HX5riFa1tH
         * payTreasureNum : 999999999
         * payTreasureRealName : 刘龙
         */

        private List<ZfbListBean> zfbList;


        public double getTxMoney() {
            return txMoney;
        }

        public void setTxMoney(double txMoney) {
            this.txMoney = txMoney;
        }

        public double getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(double totalMoney) {
            this.totalMoney = totalMoney;
        }

        public List<YhkListBean> getYhkList() {
            return yhkList;
        }

        public void setYhkList(List<YhkListBean> yhkList) {
            this.yhkList = yhkList;
        }

        public List<ZfbListBean> getZfbList() {
            return zfbList;
        }

        public void setZfbList(List<ZfbListBean> zfbList) {
            this.zfbList = zfbList;
        }


    }
}
