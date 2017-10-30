package com.android.app.buystoreapp.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 尚帅波 on 2016/10/11.
 */
public class OrderBean implements Serializable {
    private static final long serialVersionUID = 4472141302655785439L;

    private String result;
    private String resultNote;

    private List<OrderlistBean> orderlist;

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

    public List<OrderlistBean> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<OrderlistBean> orderlist) {
        this.orderlist = orderlist;
    }

    public static class OrderlistBean implements Serializable{
        private static final long serialVersionUID = -131904481617342714L;
        private int freightTotalPrice;
        private String nickname;
        private double orderAmount;
        private String orderId;
        private String proNum;
        private int status;
        private String theAwb;
        private String logisticsCode;
        private String buyUserId;
        private String buyNickname;
        private String buyHeadicon;
        private String selluserId;
        private String sellNickname;
        private String sellHeadicon;
/*
*           "theAwb":"20161019",                         	//运单号
            "logisticsCode":"20161019",                         	//物流code
            "buyUserId":"20161019",                         	//买家ID
            "sellUserId":"20161019",                         	//卖家ID

* */
        private List<OrderProduct> orderProductList;

        public String getSellNickname() {
            return sellNickname;
        }

        public void setSellNickname(String sellNickname) {
            this.sellNickname = sellNickname;
        }

        public String getSellHeadicon() {
            return sellHeadicon;
        }

        public void setSellHeadicon(String sellHeadicon) {
            this.sellHeadicon = sellHeadicon;
        }

        public String getTheAwb() {
            return theAwb;
        }

        public void setTheAwb(String theAwb) {
            this.theAwb = theAwb;
        }

        public String getLogisticsCode() {
            return logisticsCode;
        }

        public void setLogisticsCode(String logisticsCode) {
            this.logisticsCode = logisticsCode;
        }

        public String getBuyUserId() {
            return buyUserId;
        }

        public void setBuyUserId(String buyUserId) {
            this.buyUserId = buyUserId;
        }

        public String getBuyNickname() {
            return buyNickname;
        }

        public void setBuyNickname(String buyNickname) {
            this.buyNickname = buyNickname;
        }

        public String getBuyHeadicon() {
            return buyHeadicon;
        }

        public void setBuyHeadicon(String buyHeadicon) {
            this.buyHeadicon = buyHeadicon;
        }

        public String getSelluserId() {
            return selluserId;
        }

        public void setSelluserId(String selluserId) {
            this.selluserId = selluserId;
        }

        public double getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
        }

        public int getFreightTotalPrice() {
            return freightTotalPrice;
        }

        public void setFreightTotalPrice(int freightTotalPrice) {
            this.freightTotalPrice = freightTotalPrice;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }


        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getProNum() {
            return proNum;
        }

        public void setProNum(String proNum) {
            this.proNum = proNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<OrderProduct> getOrderProductList() {
            return orderProductList;
        }

        public void setOrderProductList(List<OrderProduct> orderProductList) {
            this.orderProductList = orderProductList;
        }

    }
}
