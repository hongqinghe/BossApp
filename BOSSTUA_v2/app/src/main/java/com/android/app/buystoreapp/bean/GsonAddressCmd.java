package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonAddressCmd implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4719922349432726861L;
    private String cmd;
    private String userName;
    private String name;
    private String phone;

    private String adress;
    private String isDefault;
    private String postcode;
    private String adressID;

    /**
     * 请求收货地址列表 返回的列表第一个为默认收货地址
     * 
     * @param cmd
     * @param userName
     */
    public GsonAddressCmd(String cmd, String userName) {
        super();
        this.cmd = cmd;
        this.userName = userName;
    }

    /**
     * 增加收货地址
     * 
     * @param cmd
     * @param userName
     * @param name
     * @param phone
     * @param adress
     * @param isDefault
     * @param postcode
     */
    public GsonAddressCmd(String cmd, String userName, String name,
            String phone, String adress, String postcode, String isDefault) {
        super();
        this.cmd = cmd;
        this.userName = userName;
        this.name = name;
        this.phone = phone;
        this.adress = adress;
        this.isDefault = isDefault;
        this.postcode = postcode;
    }

    /**
     * 
     * @param cmd
     * @param userName
     * @param adressID
     * @param name
     * @param phone
     * @param adress
     * @param postcode
     * @param isDefault
     */
    public GsonAddressCmd(String cmd, String userName, String adressID,
            String name, String phone, String adress, String postcode,
            String isDefault) {
        super();
        this.cmd = cmd;
        this.userName = userName;
        this.name = name;
        this.phone = phone;
        this.adress = adress;
        this.isDefault = isDefault;
        this.postcode = postcode;
        this.adressID = adressID;
    }

    /**
     * 删除收货地址
     * 
     * @param cmd
     * @param userName
     * @param adressID
     */
    public GsonAddressCmd(String cmd, String userName, String adressID) {
        super();
        this.cmd = cmd;
        this.userName = userName;
        this.adressID = adressID;
    }

    public String getAdressID() {
        return adressID;
    }

    public void setAdressID(String adressID) {
        this.adressID = adressID;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return "GsonAddressCmd [cmd=" + cmd + ", userName=" + userName
                + ", name=" + name + ", phone=" + phone + ", adress=" + adress
                + ", isDefault=" + isDefault + ", postcode=" + postcode
                + ", adressID=" + adressID + "]";
    }

}
