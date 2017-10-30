package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class MyOrderBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7117964927680261343L;
    /**
     * 订单ID
     */
    private String orderID;
    /**
     * 物流公司名称
     */
    private String wlName;
    /**
     * 物流公司编码
     */
    private String wlCode;
    /**
     * 物流运单号
     */
    private String wlNumber;
    /**
     * 订单状态 0 已经处理（不可取消） 1 未处理（可取消） 2 已发货（不可取消） 3 已完成 （可以删除，可以评论）
     */
    private String orderState;
    /**
     * 订单总金额
     */
    private String totalMoney;
    /**
     * 客服电话
     */
    private String phone;
    /**
     * 收货人名称
     */
    private String receiveName;
    /**
     * 收货人电话
     */
    private String receivePhone;
    /**
     * 收货人联系地址
     */
    private String receiveAdress;
    /**
     * 订单商品列表 可反映订单下商品数量
     */
    private List<CommodityBean> orderList;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getWlName() {
        return wlName;
    }

    public void setWlName(String wlName) {
        this.wlName = wlName;
    }

    public String getWlCode() {
        return wlCode;
    }

    public void setWlCode(String wlCode) {
        this.wlCode = wlCode;
    }

    public String getWlNumber() {
        return wlNumber;
    }

    public void setWlNumber(String wlNumber) {
        this.wlNumber = wlNumber;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getReceiveAdress() {
        return receiveAdress;
    }

    public void setReceiveAdress(String receiveAdress) {
        this.receiveAdress = receiveAdress;
    }

    public List<CommodityBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<CommodityBean> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "MyOrderBean [orderID=" + orderID + ", wlName=" + wlName
                + ", wlCode=" + wlCode + ", wlNumber=" + wlNumber
                + ", orderState=" + orderState + ", totalMoney=" + totalMoney
                + ", phone=" + phone + ", receiveName=" + receiveName
                + ", receivePhone=" + receivePhone + ", receiveAdress="
                + receiveAdress + ", orderList=" + orderList + "]";
    }

}
