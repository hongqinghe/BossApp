package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 右侧商品列表展示内容 新增产品三级分类列表
 * 
 * @author Mikes Lee
 * 
 */
public class CommoditySubCategory implements Serializable {
    private static final long serialVersionUID = -8680615828452589104L;

    // 接口新增加内容 3级分类
    private String categoryIcon;
    private String categoryID;
    private String categoryName;
    private String categoryNum;
    private List<SubCategory> categoryList;// mikes

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

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

    public String getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(String categoryNum) {
        this.categoryNum = categoryNum;
    }

    public List<SubCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<SubCategory> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        return "CommoditySubCategory [categoryIcon=" + categoryIcon
                + ", categoryID=" + categoryID + ", categoryName="
                + categoryName + ", categoryNum=" + categoryNum
                + ", categoryList=" + categoryList + "]";
    }

    public static class SubCategory implements Serializable {
        private static final long serialVersionUID = -137747570126252006L;
        private String categoryID;
        private String categoryIcon;
        private String categoryName;
        private String categoryNum;

        public String getCategoryID() {
            return categoryID;
        }

        public void setCategoryID(String categoryID) {
            this.categoryID = categoryID;
        }

        public String getCategoryIcon() {
            return categoryIcon;
        }

        public void setCategoryIcon(String categoryIcon) {
            this.categoryIcon = categoryIcon;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryNum() {
            return categoryNum;
        }

        public void setCategoryNum(String categoryNum) {
            this.categoryNum = categoryNum;
        }

        @Override
        public String toString() {
            return "SubCategory [categoryID=" + categoryID + ", categoryIcon="
                    + categoryIcon + ", categoryName=" + categoryName
                    + ", categoryNum=" + categoryNum + "]";
        }

    }
}
