package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/18.
 */
public class UpdateInfoBean implements Serializable {

    /**
     * result : 0
     * resultNote : 修改成功
     * updateModifyBean : {"companyBrand":"唯快不破","headicon":"http://192.168.1.122:8080","nikeName":"魏林","userCity":"110100","userCityString":"北京北京市通州区","userPosition":"程序员","userProvince":"110000","useraddress":"110112","userid":"VS00KB8DPpgNcfrw","usersex":"男"}
     */

    private String result;
    private String resultNote;
    /**
     * companyBrand : 唯快不破
     * headicon : http://192.168.1.122:8080
     * nikeName : 魏林
     * userCity : 110100
     * userCityString : 北京北京市通州区
     * userPosition : 程序员
     * userProvince : 110000
     * useraddress : 110112
     * userid : VS00KB8DPpgNcfrw
     * usersex : 男
     */

    private UpdateModifyBeanBean updateModifyBean;

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

    public UpdateModifyBeanBean getUpdateModifyBean() {
        return updateModifyBean;
    }

    public void setUpdateModifyBean(UpdateModifyBeanBean updateModifyBean) {
        this.updateModifyBean = updateModifyBean;
    }


}
