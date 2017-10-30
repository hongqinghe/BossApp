package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by 尚帅波 on 2016/10/5.
 */
public class EditAddressCmd implements Serializable{
    private static final long serialVersionUID = -3907320337255628096L;

    /**
     * cmd : editAdress
     * adressID : 2nIYqov1uJHIaEAc
     * name : 李四
     * phone : 15707977552
     * adress : 河南省郑州市中州大道货站街
     * postcode : 0101100
     * isDefault : 0
     * receiverArea : 河南省郑州市
     * receiverStreet : 中州大道货站街
     * userId : RB7y5XLBxr6XBPwz
     */

    private String cmd;
    private String adressID;
    private String name;
    private String phone;
    private String adress;
    private String postcode;
    private int isDefault;
    private String receiverArea;
    private String receiverStreet;
    private String userId;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getAdressID() {
        return adressID;
    }

    public void setAdressID(String adressID) {
        this.adressID = adressID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
    }

    public String getReceiverStreet() {
        return receiverStreet;
    }

    public void setReceiverStreet(String receiverStreet) {
        this.receiverStreet = receiverStreet;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
