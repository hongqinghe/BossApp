package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class GsonAddressBack implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6563548768939346524L;
    private String result;
    private String resultNote;
    private List<AddressBean> adressList;

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

    public List<AddressBean> getAdressList() {
        return adressList;
    }

    public void setAdressList(List<AddressBean> adressList) {
        this.adressList = adressList;
    }

    @Override
    public String toString() {
        return "GsonAddressBack [result=" + result + ", resultNote="
                + resultNote + ", adressList=" + adressList + "]";
    }

}
