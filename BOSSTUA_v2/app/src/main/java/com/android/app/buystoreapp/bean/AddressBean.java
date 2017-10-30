package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class AddressBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2525341248896899380L;
    private String adressID;
    private String name;
    private String adress;
    private String phone;
    /**
     * 1 默认 0 不是默认
     */
    private String isDefault;
    /**
     * 邮编
     */
    private String postcode;

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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "AddressBean [adressID=" + adressID + ", name=" + name
                + ", adress=" + adress + ", phone=" + phone + ", isDefault="
                + isDefault + ", postcode=" + postcode + "]";
    }

}
