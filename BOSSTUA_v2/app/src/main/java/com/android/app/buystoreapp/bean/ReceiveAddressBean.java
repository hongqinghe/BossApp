package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc
 * Created by likaihang on 16/11/07.
 */
/*
*                       "wUcq6q0D14AUmvat",           //  地址Id
                      ""永利源小区",                                //  收货人详细地址
                      " "北京北京市昌平区",                  //  收货人所在地区
                      " "李开航",                                   //   收货人姓名
                      ": "18392056646",                    //   收货人联系电话
                      "": "沙河"                                        //   收货人所在街道

* */
public class ReceiveAddressBean implements Serializable{
    private static final long serialVersionUID = -2940060841968678042L;
    private String receiveAddId;
    private String receiverAdd;
    private String receiverArea;
    private String receiverName;
    private String receiverPhone;
    private String receiverStreet;

    public String getReceiveAddId() {
        return receiveAddId;
    }

    public void setReceiveAddId(String receiveAddId) {
        this.receiveAddId = receiveAddId;
    }

    public String getReceiverAdd() {
        return receiverAdd;
    }

    public void setReceiverAdd(String receiverAdd) {
        this.receiverAdd = receiverAdd;
    }

    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverStreet() {
        return receiverStreet;
    }

    public void setReceiverStreet(String receiverStreet) {
        this.receiverStreet = receiverStreet;
    }
}
