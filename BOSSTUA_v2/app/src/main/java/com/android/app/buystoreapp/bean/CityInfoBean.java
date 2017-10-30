package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class CityInfoBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5717526695040998257L;

    private String areaname;
    private String id;
    private String cityLong;// 经度
    private String cityLat;// 纬度
    private String isRecommendCity;// 推荐城市 0 yes, 1 no
    private String sortLetters;  //显示数据拼音的首字母
    private String street;//街道信息
    private int level;//等级
    private String adname;//城区名称

    public String getAdname() {
        return adname;
    }

    public void setAdname(String adname) {
        this.adname = adname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

/*   public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }*/

    public String getCityLong() {
        return cityLong;
    }

    public void setCityLong(String cityLong) {
        this.cityLong = cityLong;
    }

    public String getCityLat() {
        return cityLat;
    }

    public void setCityLat(String cityLat) {
        this.cityLat = cityLat;
    }

    public String getIsRecommendCity() {
        return isRecommendCity;
    }

    public void setIsRecommendCity(String isRecommendCity) {
        this.isRecommendCity = isRecommendCity;
    }

    @Override
    public String toString() {
        return "City [areaname=" + areaname + ", id=" + id
                + ", cityLong=" + cityLong + ", cityLat=" + cityLat
                + ", isRecommendCity=" + isRecommendCity + ",sortLetters=" + sortLetters+"]";
    }

}
