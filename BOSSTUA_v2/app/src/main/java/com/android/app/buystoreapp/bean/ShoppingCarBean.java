package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/10/18.
 */
public class ShoppingCarBean implements Serializable {
    private static final long serialVersionUID = -4827043928164136585L;
    private int count;
    /**
     * 购物车商品数量
     */
    public static final String KEY_NUM = "num";
    /**
     * 购物车规格ID
     */
    public static final String KEY_PRODUCT_ID = "product_id";
    private String userid;//当前用户id
    private String nickname;//卖家名称
    private List<GroupGoods> queryCar;
    /**
     * 货运方式
     */
    private String frightModes;
    /**
     * 留言
     */
    private String comment;
    /**
     * 组是否被选中
     */
    private boolean isGroupSelected;

    public String getFrightModes() {
        return frightModes;
    }

    public void setFrightModes(String frightModes) {
        this.frightModes = frightModes;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isGroupSelected() {
        return isGroupSelected;
    }

    public void setIsGroupSelected(boolean groupSelected) {
        isGroupSelected = groupSelected;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<GroupGoods> getQueryCar() {
        return queryCar;
    }

    public void setQueryCar(List<GroupGoods> queryCar) {
        this.queryCar = queryCar;
    }
}
