package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/09/16.
 */
public class HomeProBean implements Serializable{
    private static final long serialVersionUID = 3267711941985500331L;
    /**
     * 单位
     */
    private String moreGroUnit;
    /**
     *商品名称
     */
    private String proName;
    /**
     *商品原价
     */
    private String proOriginalPrice;
    /**
     *商品现价
     */
    private String proCurrentPrice;
    /**
     *商品描述
     */
    private String proDes;
    /**
     *距离
     */
    private String proDistance;
    /**
     *图片数量
     */
    private  int proImageNum;
    /**
     *已售
     */
    private int proSale;
    /**
     *浏览量
     */
    private String proSeeNum;
    /**
     *剩余
     */
    private int proSurplus;
    /**
     *头像
     */
    private String userHeadIcon;
    /**
     *等级
     */
    private int userLevelMark;
    /**
     *姓名
     */
    private String userName;
    /**
     *职业
     */
    private String userPosition;
    /**
     *财富值
     */
    private String userTreasure;
    /**
     *关注状态 0表示没有关注1表示已经关注
     */
    private int attentionIsOff;
    /**
     *关注量
     */
    private int attentionNum;
    /**
     *实名认证 0未绑定  1绑定（下同）
     */
    private int bindingMark1;
    /**
     *芝麻信用
     */
    private int bindingMark2;
    /**
     *企业认证
     */
    private int bindingMark3;
    /**
     *职业认证
     */
    private int bindingMark4;
    /**
     *商品图片列表
     */
    private List<ProImageBean> imageList;
    /**
     * 商品ID
     * */
    private String proId;
    /**
     * 用户ID
     * */
    private String userId;
    /**
     * 差评
     * */
    private int badEvalNum;
    /**
     * 好评
     * */
    private int goodEvalNum;
    /**
     * 中评
     * */
    private int mediumEvalNum;
    /**
     * 最后上线时间
     * */
    private String loginTime;
    /**
     * 服务标签编号
     * */
    private String serveLabel;
    /**
     * 服务标签名称
     * */
    private String serveLabelName;

    public String getServeLabel() {
        return serveLabel;
    }

    public void setServeLabel(String serveLabel) {
        this.serveLabel = serveLabel;
    }

    public String getServeLabelName() {
        return serveLabelName;
    }

    public void setServeLabelName(String serveLabelName) {
        this.serveLabelName = serveLabelName;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public int getBadEvalNum() {
        return badEvalNum;
    }

    public void setBadEvalNum(int badEvalNum) {
        this.badEvalNum = badEvalNum;
    }

    public int getGoodEvalNum() {
        return goodEvalNum;
    }

    public void setGoodEvalNum(int goodEvalNum) {
        this.goodEvalNum = goodEvalNum;
    }

    public int getMediumEvalNum() {
        return mediumEvalNum;
    }

    public void setMediumEvalNum(int mediumEvalNum) {
        this.mediumEvalNum = mediumEvalNum;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProOriginalPrice() {
        return proOriginalPrice;
    }

    public void setProOriginalPrice(String proOriginalPrice) {
        this.proOriginalPrice = proOriginalPrice;
    }

    public String getProCurrentPrice() {
        return proCurrentPrice;
    }

    public void setProCurrentPrice(String proCurrentPrice) {
        this.proCurrentPrice = proCurrentPrice;
    }

    public String getProDes() {
        return proDes;
    }

    public void setProDes(String proDes) {
        this.proDes = proDes;
    }

    public String getProDistance() {
        return proDistance;
    }

    public void setProDistance(String proDistance) {
        this.proDistance = proDistance;
    }

    public int getProImageNum() {
        return proImageNum;
    }

    public void setProImageNum(int proImageNum) {
        this.proImageNum = proImageNum;
    }

    public int getProSale() {
        return proSale;
    }

    public void setProSale(int proSale) {
        this.proSale = proSale;
    }

    public String getProSeeNum() {
        return proSeeNum;
    }

    public void setProSeeNum(String proSeeNum) {
        this.proSeeNum = proSeeNum;
    }

    public int getProSurplus() {
        return proSurplus;
    }

    public void setProSurplus(int proSurplus) {
        this.proSurplus = proSurplus;
    }

    public String getUserHeadIcon() {
        return userHeadIcon;
    }

    public void setUserHeadIcon(String userHeadIcon) {
        this.userHeadIcon = userHeadIcon;
    }

    public int getUserLevelMark() {
        return userLevelMark;
    }

    public void setUserLevelMark(int userLevelMark) {
        this.userLevelMark = userLevelMark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getUserTreasure() {
        return userTreasure;
    }

    public void setUserTreasure(String userTreasure) {
        this.userTreasure = userTreasure;
    }

    public int getAttentionIsOff() {
        return attentionIsOff;
    }

    public void setAttentionIsOff(int attentionIsOff) {
        this.attentionIsOff = attentionIsOff;
    }

    public int getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(int attentionNum) {
        this.attentionNum = attentionNum;
    }

    public int getBindingMark1() {
        return bindingMark1;
    }

    public void setBindingMark1(int bindingMark1) {
        this.bindingMark1 = bindingMark1;
    }

    public int getBindingMark2() {
        return bindingMark2;
    }

    public void setBindingMark2(int bindingMark2) {
        this.bindingMark2 = bindingMark2;
    }

    public int getBindingMark3() {
        return bindingMark3;
    }

    public void setBindingMark3(int bindingMark3) {
        this.bindingMark3 = bindingMark3;
    }

    public int getBindingMark4() {
        return bindingMark4;
    }

    public void setBindingMark4(int bindingMark4) {
        this.bindingMark4 = bindingMark4;
    }

    public List<ProImageBean> getImageList() {
        return imageList;
    }

    public void setImageList(List<ProImageBean> imageList) {
        this.imageList = imageList;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMoreGroUnit() {
        return moreGroUnit;
    }

    public void setMoreGroUnit(String moreGroUnit) {
        this.moreGroUnit = moreGroUnit;
    }
}
