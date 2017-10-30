package com.android.app.buystoreapp.order;

import com.android.app.buystoreapp.bean.ReceiveAddressBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 尚帅波 on 2016/10/24.
 */
public class OrdersDetailsBean implements Serializable {

    private static final long serialVersionUID = -3140100224266919660L;

    private String result;
    private String resultNote;

    private List<OrderDetailsBean> orderDetails;

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

    public List<OrderDetailsBean> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailsBean> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public static class OrderDetailsBean implements Serializable{
        private static final long serialVersionUID = 7357033212140909215L;
        private double orderAmount;
        private String orderCreatTime;
        private int proNum;
        private ReceiveAddressBean reAddress;
        private String serviceMethod;
        private int status;
        private String userComment;
        private String orderId;
        private String userid;
        private String buyNickname;
        private String buyHeadicon;
        private String selluserId;
        private String nickname;
        private String sellHeadicon;
        private String remainingTime;


        private List<OrderProduct> orderProduct;

        public String getBuyNickname() {
            return buyNickname;
        }

        public void setBuyNickname(String buyNickname) {
            this.buyNickname = buyNickname;
        }

        public String getSellHeadicon() {
            return sellHeadicon;
        }

        public void setSellHeadicon(String sellHeadicon) {
            this.sellHeadicon = sellHeadicon;
        }

        public String getRemainingTime() {
            return remainingTime;
        }

        public void setRemainingTime(String remainingTime) {
            this.remainingTime = remainingTime;
        }

        public double getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getOrderCreatTime() {
            return orderCreatTime;
        }

        public void setOrderCreatTime(String orderCreatTime) {
            this.orderCreatTime = orderCreatTime;
        }

        public int getProNum() {
            return proNum;
        }

        public void setProNum(int proNum) {
            this.proNum = proNum;
        }

        public ReceiveAddressBean getReAddress() {
            return reAddress;
        }

        public void setReAddress(ReceiveAddressBean reAddress) {
            this.reAddress = reAddress;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSelluserId() {
            return selluserId;
        }

        public void setSelluserId(String selluserId) {
            this.selluserId = selluserId;
        }

        public String getServiceMethod() {
            return serviceMethod;
        }

        public void setServiceMethod(String serviceMethod) {
            this.serviceMethod = serviceMethod;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUserComment() {
            return userComment;
        }

        public void setUserComment(String userComment) {
            this.userComment = userComment;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getBuyHeadicon() {
            return buyHeadicon;
        }

        public void setBuyHeadicon(String buyHeadicon) {
            this.buyHeadicon = buyHeadicon;
        }

        public List<OrderProduct> getOrderProduct() {
            return orderProduct;
        }

        public void setOrderProduct(List<OrderProduct> orderProduct) {
            this.orderProduct = orderProduct;
        }

    }
}
