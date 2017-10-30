package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/3.
 */
public  class YhkListBean implements Serializable  {
    private String id;
    private String visaName;
    private String visanoFour;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisaName() {
        return visaName;
    }

    public void setVisaName(String visaName) {
        this.visaName = visaName;
    }

    public String getVisanoFour() {
        return visanoFour;
    }

    public void setVisanoFour(String visanoFour) {
        this.visanoFour = visanoFour;
    }
}
