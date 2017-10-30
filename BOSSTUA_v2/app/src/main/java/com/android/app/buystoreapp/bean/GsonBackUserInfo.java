package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc My order count.
 * Created by likaihang on 16/10/21.
 */
public class GsonBackUserInfo implements Serializable{
    private static final long serialVersionUID = -4650628898132350854L;
    private String result;
    private String resultNote;
    private OrderCount orderCount;

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

    public OrderCount getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(OrderCount orderCount) {
        this.orderCount = orderCount;
    }

    public class OrderCount {
        private int forTheGoods;
        private int forThePayment;
        private int refundOrders;
        private int sendTheGoods;
        private int toEvaluate;

        public int getForTheGoods() {
            return forTheGoods;
        }

        public void setForTheGoods(int forTheGoods) {
            this.forTheGoods = forTheGoods;
        }

        public int getForThePayment() {
            return forThePayment;
        }

        public void setForThePayment(int forThePayment) {
            this.forThePayment = forThePayment;
        }

        public int getRefundOrders() {
            return refundOrders;
        }

        public void setRefundOrders(int refundOrders) {
            this.refundOrders = refundOrders;
        }

        public int getSendTheGoods() {
            return sendTheGoods;
        }

        public void setSendTheGoods(int sendTheGoods) {
            this.sendTheGoods = sendTheGoods;
        }

        public int getToEvaluate() {
            return toEvaluate;
        }

        public void setToEvaluate(int toEvaluate) {
            this.toEvaluate = toEvaluate;
        }
    }
}
