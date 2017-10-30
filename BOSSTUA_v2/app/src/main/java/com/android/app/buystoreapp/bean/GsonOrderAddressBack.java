package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/10/24.
 */
public class GsonOrderAddressBack implements Serializable{
    private static final long serialVersionUID = 1819547202240688982L;
    private String result;
    private String resultNote;
    private List<OrderAddress> confirmOrderAddress;

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

    public List<OrderAddress> getConfirmOrderAddress() {
        return confirmOrderAddress;
    }

    public void setConfirmOrderAddress(List<OrderAddress> confirmOrderAddress) {
        this.confirmOrderAddress = confirmOrderAddress;
    }

    /*
          "receiveAddId": "0S0W6010JrSqwtVW",         	//收货地址ID
          "receiverAdd": "北京昌平区玉兴路",             	//收货人详细地址
          "receiverArea": "昌平区",                     	//收货人所在地区
          "receiverName": "lisi",                      	//收货人姓名
          "receiverPhone": "23123123",                	//收货人电话
          "receiverStreet": "玉兴路"                    	//收货人所在街道

    * */
    public class OrderAddress {
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
}
