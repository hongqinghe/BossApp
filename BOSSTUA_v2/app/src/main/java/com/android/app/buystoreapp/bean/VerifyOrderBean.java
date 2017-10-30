package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/10/26.
 */
public class VerifyOrderBean implements Serializable {
    private static final long serialVersionUID = -4694727519479971071L;
    /*{
        "cmd":"verifyPriceOrNumber",                                 	//接口名称
        "vnList":[
             {
                "shopCarId":"9r4nVQG7pXeEKuBU", //购物车/商品ID（当state为1、2此字段为购物车ID当state为3此字段为组合商品ID）
                 "count":1,                                                      //数量
                 "moreGroPrice":"0.00",                                   	//价格
    }
        "state":1     	//状态1提交到订单和修改购物车2修改购物车 3直接提交订单购买
    }
    */
    private String cmd;
    private int state;
    public List<ShoppingCarSubmit> vnList;

    public VerifyOrderBean() {
    }

    public VerifyOrderBean(String cmd, int state, List<ShoppingCarSubmit> vnList) {
        this.cmd = cmd;
        this.state = state;
        this.vnList = vnList;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<ShoppingCarSubmit> getVnList() {
        return vnList;
    }

    public void setVnList(List<ShoppingCarSubmit> vnList) {
        this.vnList = vnList;
    }
}
