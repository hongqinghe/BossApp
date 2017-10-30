package com.android.app.buystoreapp.managementservice;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/24.
 */
public class PersonalDataBean implements Serializable {

    private static final long serialVersionUID = -4408785716389498558L;
    /**
     * companyBrand :
     * headicon :
     * nikeName :
     * userCity :
     * userCityString :
     * userPosition :
     * userProvince :
     * useraddress :
     * userid : MqSbXioihewoIWO3
     * usersex :
     */

    private InfoBean info;
    /**
     * info : {"companyBrand":"","headicon":"","nikeName":"","userCity":"","userCityString":"","userPosition":"","userProvince":"","useraddress":"","userid":"MqSbXioihewoIWO3","usersex":""}
     * result : 0
     * resultNote : 加载成功
     */

    private String result;
    private String resultNote;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

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

    public static class InfoBean {
        private String companyBrand;
        private String headicon;
        private String nikeName;
        private String userCity;
        private String userCityString;
        private String userPosition;
        private String userProvince;
        private String useraddress;
        private String userid;
        private String userSex;

        public String getCompanyBrand() {
            return companyBrand;
        }

        public void setCompanyBrand(String companyBrand) {
            this.companyBrand = companyBrand;
        }

        public String getHeadicon() {
            return headicon;
        }

        public void setHeadicon(String headicon) {
            this.headicon = headicon;
        }

        public String getNikeName() {
            return nikeName;
        }

        public void setNikeName(String nikeName) {
            this.nikeName = nikeName;
        }

        public String getUserCity() {
            return userCity;
        }

        public void setUserCity(String userCity) {
            this.userCity = userCity;
        }

        public String getUserCityString() {
            return userCityString;
        }

        public void setUserCityString(String userCityString) {
            this.userCityString = userCityString;
        }

        public String getUserPosition() {
            return userPosition;
        }

        public void setUserPosition(String userPosition) {
            this.userPosition = userPosition;
        }

        public String getUserProvince() {
            return userProvince;
        }

        public void setUserProvince(String userProvince) {
            this.userProvince = userProvince;
        }

        public String getUseraddress() {
            return useraddress;
        }

        public void setUseraddress(String useraddress) {
            this.useraddress = useraddress;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUserSex() {
            return userSex;
        }

        public void setUserSex(String usersex) {
            this.userSex = usersex;
        }
    }
}
