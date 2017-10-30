package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/10/29.
 */
public class ScobList implements Serializable{
    private static final long serialVersionUID = -2764000343235352897L;
    private String receiveAddId;
   private String userComment;
   private String serviceMethod;
    private List<OrderPro> orderPro;

    public String getReceiveAddId() {
        return receiveAddId;
    }

    public void setReceiveAddId(String receiveAddId) {
        this.receiveAddId = receiveAddId;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(String serviceMethod) {
        this.serviceMethod = serviceMethod;
    }

    public List<OrderPro> getOrderPro() {
        return orderPro;
    }

    public void setOrderPro(List<OrderPro> orderPro) {
        this.orderPro = orderPro;
    }
}
