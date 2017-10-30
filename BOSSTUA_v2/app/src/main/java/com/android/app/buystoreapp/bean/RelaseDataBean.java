package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 发布所有数据实体
 * Created by likaihang on 16/09/21.
 */
public class RelaseDataBean implements Serializable {
    private static final long serialVersionUID = -6612810064138391448L;
    /**
     * "thisUser": "userLisi",     //发布人账号
     * "dayTimeEnd":"9:00",        //结束时间
     * "dayTimeStart":"8:00",     //开始时间
     * "freightMode":0,            //货运方式  0  免运费1货到付款        （int类型）
     * "freightPrice":"54.0",     //货运价格
     * "isRecommend":0,            //是否推荐  0不推荐1推荐             （int类型）
     * "listimg":[
     * {
     * "proImageUrl":"/bosstuan/abc.png" ,       //图片URL
     * "proImageName":"abc.png"                    //图片名称
     * }
     * ],
     * "mgList": [
     * {
     * "moreGroPrice":43.0,       //价格                        （double类型）
     * "moreGroName": "红色上衣",    //组合名称
     * "proSurplus":223             //剩余量                     （int类型）
     * }
     * ],
     * "modes":0,                   //服务方式0线上 1 线下2 货运    （int类型）
     * "proAddress":"北京",        //商品服务地址     省 --市 --区
     * "proCatId":"322o4",         //分类Id
     * "proDes":"商品描述",         //商品描述
     * "proLat":"23.989767",      //商品纬度
     * "proLong":"34.987654",     //商品经度
     * "proName":"上衣",            //商品名称
     * "proStatus":1,               //商品状态   0.下架  1.上架      （int类型）
     * "serviceSubject":2,         //服务主体   0.Boss1.机构2.个人  （int类型）
     * "specificAddress":"于兴路永利源小区",       //商品具体地址  街道
     * "weekEnd":"周三",            //结束时间（单位周）
     * "weekStart":"周一"           //开始时间（单位周）
     */
    public String serveLabelName;//服务标签名称
    public String serveLabel;//服务标签编号
    public String proCatId;
    public String proName;//标题
    public String proDes;

    public List<RelaseImageBean> listimg;
    public List<RelaseGroupBean> mgList;

    public String weekEnd;
    public String weekStart;
    public String dayTimeStart;
    public String dayTimeEnd;
    public String proAddress;
    public String specificAddress;
    public String proLat;
    public String proLong;
    public int serviceSubject;
    public int modes;
    public int freightMode;
    public String freightPrice;
    public int proStatus;
    public int isRecommend;
    private String cmd;
    public String proId;
    private String thisUser;
    private String areasId2;//地区编码
    private int areasIdState;//地区状态

    public RelaseDataBean() {
    }

    public RelaseDataBean(String cmd, String proId, String serveLabel, String serveLabelName, String thisUser, String areasId2, String proCatId, String proName, String proDes, List<RelaseImageBean> listimg, List<RelaseGroupBean> mgList, String weekEnd, String weekStart, String dayTimeStart, String dayTimeEnd, String proAddress, String specificAddress, String proLat, String proLong, int serviceSubject, int modes, int freightMode, String freightPrice, int proStatus, int isRecommend, int areasIdState) {
        super();
        this.cmd = cmd;
        this.proId = proId;
        this.serveLabel = serveLabel;
        this.serveLabelName = serveLabelName;
        this.thisUser = thisUser;
        this.areasId2 = areasId2;
        this.proCatId = proCatId;
        this.proName = proName;
        this.proDes = proDes;
        this.listimg = listimg;
        this.mgList = mgList;
        this.weekEnd = weekEnd;
        this.weekStart = weekStart;
        this.dayTimeStart = dayTimeStart;
        this.dayTimeEnd = dayTimeEnd;
        this.proAddress = proAddress;
        this.specificAddress = specificAddress;
        this.proLat = proLat;
        this.proLong = proLong;
        this.serviceSubject = serviceSubject;
        this.modes = modes;
        this.freightMode = freightMode;
        this.freightPrice = freightPrice;
        this.proStatus = proStatus;
        this.isRecommend = isRecommend;
        this.areasIdState = areasIdState;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public int getAreasIdState() {
        return areasIdState;
    }

    public void setAreasIdState(int areasIdState) {
        this.areasIdState = areasIdState;
    }

    public String getServeLabelName() {
        return serveLabelName;
    }

    public void setServeLabelName(String serveLabelName) {
        this.serveLabelName = serveLabelName;
    }

    public String getServeLabel() {
        return serveLabel;
    }

    public void setServeLabel(String serveLabel) {
        this.serveLabel = serveLabel;
    }

    public String getAreasId2() {
        return areasId2;
    }

    public void setAreasId2(String areasId2) {
        this.areasId2 = areasId2;
    }


    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getThisUser() {
        return thisUser;
    }

    public void setThisUser(String thisUser) {
        this.thisUser = thisUser;
    }

    public String getProCatId() {
        return proCatId;
    }

    public void setProCatId(String proCatId) {
        this.proCatId = proCatId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProDes() {
        return proDes;
    }

    public void setProDes(String proDes) {
        this.proDes = proDes;
    }

    public List<RelaseImageBean> getListimg() {
        return listimg;
    }

    public void setListimg(List<RelaseImageBean> listimg) {
        this.listimg = listimg;
    }

    public List<RelaseGroupBean> getMgList() {
        return mgList;
    }

    public void setMgList(List<RelaseGroupBean> mgList) {
        this.mgList = mgList;
    }

    public String getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(String weekEnd) {
        this.weekEnd = weekEnd;
    }

    public String getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(String weekStart) {
        this.weekStart = weekStart;
    }

    public String getDayTimeStart() {
        return dayTimeStart;
    }

    public void setDayTimeStart(String dayTimeStart) {
        this.dayTimeStart = dayTimeStart;
    }

    public String getDayTimeEnd() {
        return dayTimeEnd;
    }

    public void setDayTimeEnd(String dayTimeEnd) {
        this.dayTimeEnd = dayTimeEnd;
    }

    public String getProAddress() {
        return proAddress;
    }

    public void setProAddress(String proAddress) {
        this.proAddress = proAddress;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }

    public String getProLat() {
        return proLat;
    }

    public void setProLat(String proLat) {
        this.proLat = proLat;
    }

    public String getProLong() {
        return proLong;
    }

    public void setProLong(String proLong) {
        this.proLong = proLong;
    }

    public int getServiceSubject() {
        return serviceSubject;
    }

    public void setServiceSubject(int serviceSubject) {
        this.serviceSubject = serviceSubject;
    }

    public int getModes() {
        return modes;
    }

    public void setModes(int modes) {
        this.modes = modes;
    }

    public int getFreightMode() {
        return freightMode;
    }

    public void setFreightMode(int freightMode) {
        this.freightMode = freightMode;
    }

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public int getProStatus() {
        return proStatus;
    }

    public void setProStatus(int proStatus) {
        this.proStatus = proStatus;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    @Override
    public String toString() {
        return "RelaseDataBean{" +
                "serveLabelName='" + serveLabelName + '\'' +
                ", serveLabel='" + serveLabel + '\'' +
                ", proCatId='" + proCatId + '\'' +
                ", proName='" + proName + '\'' +
                ", proDes='" + proDes + '\'' +
                ", listimg=" + listimg +
                ", mgList=" + mgList +
                ", weekEnd='" + weekEnd + '\'' +
                ", weekStart='" + weekStart + '\'' +
                ", dayTimeStart='" + dayTimeStart + '\'' +
                ", dayTimeEnd='" + dayTimeEnd + '\'' +
                ", proAddress='" + proAddress + '\'' +
                ", specificAddress='" + specificAddress + '\'' +
                ", proLat='" + proLat + '\'' +
                ", proLong='" + proLong + '\'' +
                ", serviceSubject=" + serviceSubject +
                ", modes=" + modes +
                ", freightMode=" + freightMode +
                ", freightPrice='" + freightPrice + '\'' +
                ", proStatus=" + proStatus +
                ", isRecommend=" + isRecommend +
                ", cmd='" + cmd + '\'' +
                ", proId='" + proId + '\'' +
                ", thisUser='" + thisUser + '\'' +
                ", areasId2='" + areasId2 + '\'' +
                ", areasIdState=" + areasIdState +
                '}';
    }
}
