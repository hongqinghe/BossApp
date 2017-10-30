package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 左侧菜单内容
 * 
 * @author Mikes Lee
 * 
 */
public class CommodityCategory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5340262765467444560L;
    private String categoryNum;
    private String categoryName;
    private String categoryIcon;
    private List<CommoditySubCategory> subCategoryList;

    public String getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(String categoryNum) {
        this.categoryNum = categoryNum;
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

    public List<CommoditySubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<CommoditySubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    @Override
    public String toString() {
        return "CommodityCategory [categoryNum=" + categoryNum
                + ", categoryName=" + categoryName + ", categoryIcon="
                + categoryIcon + ", subCategoryList=" + subCategoryList + "]";
    }

}
