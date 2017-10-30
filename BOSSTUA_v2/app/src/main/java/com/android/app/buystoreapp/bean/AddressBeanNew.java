package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 尚帅波 on 2016/10/5.
 */
public class AddressBeanNew implements Serializable{
    private static final long serialVersionUID = -1781219846051035302L;

    /**
     * adressList : [{"adress":"山东省临沂市兰山区北园路前园花园小区","adressID":"17QrjgbY5WYgs93x","isDefault":0,
     * "name":"徐然","phone":"2147483647","postcode":"276000","receiverArea":"临沂市兰山区",
     * "receiverStreet":"北园路"}]
     * result : 0
     * resultNote : 查询成功
     */

    private String result;
    private String resultNote;
    /**
     * adress : 山东省临沂市兰山区北园路前园花园小区
     * adressID : 17QrjgbY5WYgs93x
     * isDefault : 0
     * name : 徐然
     * phone : 2147483647
     * postcode : 276000
     * receiverArea : 临沂市兰山区
     * receiverStreet : 北园路
     */

    private List<AdressListBean> adressList;

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

    public List<AdressListBean> getAdressList() {
        return adressList;
    }

    public void setAdressList(List<AdressListBean> adressList) {
        this.adressList = adressList;
    }

    public static class AdressListBean implements Serializable{

        private static final long serialVersionUID = 2416819718698488244L;

        private boolean isCheck;
        private String adress;
        private String adressID;
        private int isDefault;
        private String name;
        private String phone;
        private String postcode;
        private String receiverArea;
        private String receiverStreet;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public String getAdressID() {
            return adressID;
        }

        public void setAdressID(String adressID) {
            this.adressID = adressID;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
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

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
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
    }
}
