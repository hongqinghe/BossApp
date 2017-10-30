package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class GsonCityBack implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -985591950220452167L;

    private String result;
    private String resultNote;
    private List<CityInfoBean> cityList;

    public GsonCityBack(String result, String resultNote, List<CityInfoBean> cityList) {
        super();
        this.result = result;
        this.resultNote = resultNote;
        this.cityList = cityList;
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

    public List<CityInfoBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityInfoBean> cityList) {
        this.cityList = cityList;
    }

    @Override
    public String toString() {
        return "GsonCityBack [result=" + result + ", resultNote=" + resultNote
                + ", cityList=" + cityList + "]";
    }
}
