package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc
 * Created by likaihang on 16/10/26.
 */
public class ShoppingCarSubmit implements Serializable {
    private static final long serialVersionUID = 7786484218761071341L;
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
    public String shopCarId;
    public int count;
    public String moreGroPrice;

    public String getShopCarId() {
        return shopCarId;
    }

    public void setShopCarId(String shopCarId) {
        this.shopCarId = shopCarId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMoreGroPrice() {
        return moreGroPrice;
    }

    public void setMoreGroPrice(String moreGroPrice) {
        this.moreGroPrice = moreGroPrice;
    }
}
