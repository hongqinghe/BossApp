package com.android.app.buystoreapp.other;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 尚帅波 on 2016/9/23.
 */
public class AreaBean implements Serializable{

    private static final long serialVersionUID = -1255203786098397070L;

    /**
     * areasList : [{"areaname":"北京","id":110000,"lat":"39.904987","level":1,"lng":"116.405289"},
     * {"areaname":"天津","id":120000,"lat":"39.125595","level":1,"lng":"117.190186"},
     * {"areaname":"河北省","id":130000,"lat":"38.045475","level":1,"lng":"114.502464"},
     * {"areaname":"山西省","id":140000,"lat":"37.857014","level":1,"lng":"112.549248"},
     * {"areaname":"内蒙古自治区","id":150000,"lat":"40.81831","level":1,"lng":"111.670799"},
     * {"areaname":"辽宁省","id":210000,"lat":"41.796768","level":1,"lng":"123.429092"},
     * {"areaname":"吉林省","id":220000,"lat":"43.886841","level":1,"lng":"125.324501"},
     * {"areaname":"黑龙江省","id":230000,"lat":"45.756966","level":1,"lng":"126.642464"},
     * {"areaname":"上海","id":310000,"lat":"31.231707","level":1,"lng":"121.472641"},
     * {"areaname":"江苏省","id":320000,"lat":"32.041546","level":1,"lng":"118.76741"},
     * {"areaname":"浙江省","id":330000,"lat":"30.287458","level":1,"lng":"120.15358"},
     * {"areaname":"安徽省","id":340000,"lat":"31.861191","level":1,"lng":"117.283043"},
     * {"areaname":"福建省","id":350000,"lat":"26.075302","level":1,"lng":"119.306236"},
     * {"areaname":"江西省","id":360000,"lat":"28.676493","level":1,"lng":"115.892151"},
     * {"areaname":"山东省","id":370000,"lat":"36.675808","level":1,"lng":"117.000923"},
     * {"areaname":"河南省","id":410000,"lat":"34.757977","level":1,"lng":"113.665413"},
     * {"areaname":"湖北省","id":420000,"lat":"30.584354","level":1,"lng":"114.298569"},
     * {"areaname":"湖南省","id":430000,"lat":"28.19409","level":1,"lng":"112.982277"},
     * {"areaname":"广东省","id":440000,"lat":"23.125177","level":1,"lng":"113.28064"},
     * {"areaname":"广西壮族自治区","id":450000,"lat":"22.82402","level":1,"lng":"108.320007"},
     * {"areaname":"海南省","id":460000,"lat":"20.031971","level":1,"lng":"110.331192"},
     * {"areaname":"重庆","id":500000,"lat":"29.533155","level":1,"lng":"106.504959"},
     * {"areaname":"四川省","id":510000,"lat":"30.659462","level":1,"lng":"104.065735"},
     * {"areaname":"贵州省","id":520000,"lat":"26.578342","level":1,"lng":"106.713478"},
     * {"areaname":"云南省","id":530000,"lat":"25.040609","level":1,"lng":"102.71225"},
     * {"areaname":"西藏自治区","id":540000,"lat":"29.66036","level":1,"lng":"91.13221"},
     * {"areaname":"陕西省","id":610000,"lat":"34.263161","level":1,"lng":"108.948021"},
     * {"areaname":"甘肃省","id":620000,"lat":"36.058041","level":1,"lng":"103.823555"},
     * {"areaname":"青海省","id":630000,"lat":"36.623177","level":1,"lng":"101.778915"},
     * {"areaname":"宁夏回族自治区","id":640000,"lat":"38.46637","level":1,"lng":"106.278175"},
     * {"areaname":"新疆维吾尔自治区","id":650000,"lat":"43.792816","level":1,"lng":"87.617729"},
     * {"areaname":"香港特别行政区","id":810000,"lat":"22.320047","level":1,"lng":"114.173355"},
     * {"areaname":"澳门特别行政区","id":820000,"lat":"22.198952","level":1,"lng":"113.549088"},
     * {"areaname":"台湾","id":710000,"lat":"25.044333","level":1,"lng":"121.509064"}]
     * result : 0
     * resultNote : 加载成功
     */

    private String result;
    private String resultNote;
    /**
     * areaname : 北京
     * id : 110000
     * lat : 39.904987
     * level : 1
     * lng : 116.405289
     */

    private List<AreasListBean> areasList;

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

    public List<AreasListBean> getAreasList() {
        return areasList;
    }

    public void setAreasList(List<AreasListBean> areasList) {
        this.areasList = areasList;
    }

    @Override
    public String toString() {
        return "AreaBean{" +
                "result='" + result + '\'' +
                ", resultNote='" + resultNote + '\'' +
                ", areasList=" + areasList +
                '}';
    }

    public static class AreasListBean implements Serializable{
        private static final long serialVersionUID = -5910938261271801452L;
        private String areaname;
        private int id;
        private String lat;
        private int level;
        private String lng;

        public String getAreaname() {
            return areaname;
        }

        public void setAreaname(String areaname) {
            this.areaname = areaname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        @Override
        public String toString() {
            return "AreasListBean{" +
                    "areaname='" + areaname + '\'' +
                    ", id=" + id +
                    ", lat='" + lat + '\'' +
                    ", level=" + level +
                    ", lng='" + lng + '\'' +
                    '}';
        }
    }
}
