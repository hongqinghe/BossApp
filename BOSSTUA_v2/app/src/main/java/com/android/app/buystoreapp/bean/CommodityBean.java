package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class CommodityBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4846032903364493503L;
//    /**
//     * 商品ID
//     */
//    private String commodityID;
//    /**
//     * 商品名称
//     */
//    private String commodityName;
//    /**
//     * 店铺ID
//     */
//    private String shopID;
//    /**
//     * 店铺到当前位置的距离
//     */
//    private String commodityDitance;
//
//    /**
//     * 商品图片地址
//     */
//    private String commodityIcon;
//    /**
//     * 商品评分数
//     */
//    private String commodityScore;
//    /**
//     * 商品规格介绍
//     */
//    private String commodityIntro;
//    /**
//     * 商品现在价格
//     */
//    private String commodityPrice;
//    /**
//     * 市场价格
//     */
//    private String commodityMarketPrice;
//    /**
//     * 商品产地
//     */
//    private String commodityAdress;
//    /**
//     * 月销量
//     */
//    private String commoditySaleNum;
//
    /**
     * 商品购买数量
     */
    private String commodityNum;

    public String getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(String commodityNum) {
        this.commodityNum = commodityNum;
    }
    //
//    /**
//     * 我的订单中商品购买数量
//     */
//    private String commodityBuyNum;
//
    private String leaveMessage;

    public String getLeaveMessage() {
        return leaveMessage;
    }

    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }
    /* public String getCommodityDitance() {
        return commodityDitance;
    }

    public void setCommodityDitance(String commodityDitance) {
        this.commodityDitance = commodityDitance;
    }

    public String getCommodityBuyNum() {
        return commodityBuyNum;
    }

    public void setCommodityBuyNum(String commodityBuyNum) {
        this.commodityBuyNum = commodityBuyNum;
    }

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getCommodityIcon() {
        return commodityIcon;
    }

    public void setCommodityIcon(String commodityIcon) {
        this.commodityIcon = commodityIcon;
    }

    public String getCommodityScore() {
        return commodityScore;
    }

    public void setCommodityScore(String commodityScore) {
        this.commodityScore = commodityScore;
    }

    public String getCommodityIntro() {
        return commodityIntro;
    }

    public void setCommodityIntro(String commodityIntro) {
        this.commodityIntro = commodityIntro;
    }

    public String getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(String commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getCommodityMarketPrice() {
        return commodityMarketPrice;
    }

    public void setCommodityMarketPrice(String commodityMarketPrice) {
        this.commodityMarketPrice = commodityMarketPrice;
    }

    public String getCommodityAdress() {
        return commodityAdress;
    }

    public void setCommodityAdress(String commodityAdress) {
        this.commodityAdress = commodityAdress;
    }

    public String getCommoditySaleNum() {
        return commoditySaleNum;
    }

    public void setCommoditySaleNum(String commoditySaleNum) {
        this.commoditySaleNum = commoditySaleNum;
    }

    public String getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(String commodityNum) {
        this.commodityNum = commodityNum;
    }

    public String getLeaveMessage() {
        return leaveMessage;
    }

    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }
*/
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
    private ProImageBean imageList;
    /**
     * 商品ID
     * */
    private String proId;
    /**
     * 用户ID
     * */
    private String userId;

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

    public ProImageBean getImageList() {
        return imageList;
    }

    public void setImageList(ProImageBean imageList) {
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
    /* @Override
    public String toString() {
        return "CommodityBean [commodityID=" + commodityID + ", commodityName="
                + commodityName + ", shopID=" + shopID + ", shopDistance="
                + commodityDitance + ", commodityIcon=" + commodityIcon
                + ", commodityScore=" + commodityScore + ", commodityIntro="
                + commodityIntro + ", commodityPrice=" + commodityPrice
                + ", commodityMarketPrice=" + commodityMarketPrice
                + ", commodityAdress=" + commodityAdress
                + ", commoditySaleNum=" + commoditySaleNum + ", commodityNum="
                + commodityNum + ", commodityBuyNum=" + commodityBuyNum
                + ", leaveMessage=" + leaveMessage + "]";
    }*/

}
