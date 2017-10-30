package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class ShopCategoryBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1495677881898531001L;
    /**
     * 1级分类ID
     */
    private String categoryID;
    /**
     * 1级分类名称
     */
    private String categoryName;
    /**
     * 分类图标
     */
    private String categoryIcon;

    /**
     * 该分类内部商品的个数
     */
    private String categoryNum;

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(String categoryNum) {
        this.categoryNum = categoryNum;
    }

    @Override
    public String toString() {
        return "ShopCategoryBean [categoryID=" + categoryID + ", categoryName="
                + categoryName + ", categoryIcon=" + categoryIcon
                + ", categoryNum=" + categoryNum + "]";
    }

}
