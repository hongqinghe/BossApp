package com.android.app.buystoreapp.bean;

import java.util.Collections;
import java.util.List;

public class ChanceInfoBean implements Comparable<ChanceInfoBean> {
    private static final long serialVersionUID = 1181210975001303118L;
    //    private int id;
    //name
    private String categoryName;//分类名称
    private String categoryDes;//描述
    private int categoryUpId;//0表示特价服务  1表示闲置资源
    private int categoryNum;//
//    private Drawable icon;
    //iconUrl
    private String categoryIcon;//图标
    private String categoryID;//分类ID
    private String categoryViews;//浏览量
    private String categoryBkground;//背景图
    private int iconID;
    private List<CategoryBean> categoryList;
//    private String describtion;
//    private int type;
    // 排序标记
    private int order;
    private onGridViewItemClickListener onClickListener;


    public ChanceInfoBean(String name, String iconUrl,int order) {
        super();
        this.categoryName = name;
        this.categoryIcon = iconUrl;
        this.order = order;
    }

    public ChanceInfoBean(String name, int iconID, int order, onGridViewItemClickListener onClickListener) {
        super();
        this.categoryName = name;
//        this.iconID = iconID;
//        this.order = order;
        this.onClickListener = onClickListener;
    }


    public ChanceInfoBean( String name, String iconUrl) {
        super();
//        this.id = id;
        this.categoryName = name;
//        this.icon = icon;
        this.categoryIcon = iconUrl;
//        this.iconID = iconID;
//        this.type = type;
//        this.order = order;
//        this.describtion = describtion;
    }

    public onGridViewItemClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(onGridViewItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
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

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }


    public String getCategoryViews() {
        return categoryViews;
    }

    public void setCategoryViews(String categoryViews) {
        this.categoryViews = categoryViews;
    }

    public String getCategoryBkground() {
        return categoryBkground;
    }

    public void setCategoryBkground(String categoryBkground) {
        this.categoryBkground = categoryBkground;
    }

    public String getCategoryDes() {
        return categoryDes;
    }

    public void setCategoryDes(String categoryDes) {
        this.categoryDes = categoryDes;
    }

    public List<CategoryBean> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryBean> categoryList) {
        this.categoryList = categoryList;
    }


    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public int getCategoryUpId() {
        return categoryUpId;
    }

    public void setCategoryUpId(int categoryUpId) {
        this.categoryUpId = categoryUpId;
    }

    public int getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(int categoryNum) {
        this.categoryNum = categoryNum;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//
//    public Drawable getIcon() {
//        return icon;
//    }
//
//    public void setIcon(Drawable icon) {
//        this.icon = icon;
//    }
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
//
//    public String getDescribtion() {
//        return describtion;
//    }
//
//    public void setDescribtion(String describtion) {
//        this.describtion = describtion;
//    }
//
    @Override
    public int compareTo(ChanceInfoBean info) {
        if (info != null) {
            if (this.getOrder() > info.getOrder()) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return 0;
        }
    }


    //得到排序的List
    public static List<ChanceInfoBean> getOrderList(List<ChanceInfoBean> list) {
        Collections.sort(list);
        return list;
    }

    public interface onGridViewItemClickListener {
        void ongvItemClickListener(int position);
    }

}
