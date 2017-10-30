package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonNewsCmd implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -6018293307422444468L;

    private String cmd;
    
    /**
     * 用户名为空返回所有资讯，状态都是未收藏
     * 用户名不为空返回资讯列表
     * isFavourite 1 所有资讯列表
     * isFavourite 0 资讯收藏列表
     */
    private String userName;
    
    /**
     * 1 头条
     * 2 失败成功
     * 3 初级管理
     * 4 人脉
     * 5 减压
     * 6 Boss智慧
     * 7 综合
     */
    private String newsType;
    
    /**
     * 每页新闻条数
     */
    private String pageSize;
    
    /**
     * 当前页 1 为第一页
     */
    private String nowPage;
    
    /**
     * 0 资讯收藏列表
     * 1 所有资讯列表
     */
    private String isFavourite;
    
    public  GsonNewsCmd(String cmd, String userName, String newsType,
            String pageSize, String nowPage, String isFavourite) {
        super();
        this.cmd = cmd;
        this.userName = userName;
        this.newsType = newsType;
        this.pageSize = pageSize;
        this.nowPage = nowPage;
        this.isFavourite = isFavourite;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getNowPage() {
        return nowPage;
    }

    public void setNowPage(String nowPage) {
        this.nowPage = nowPage;
    }

    public String getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        this.isFavourite = isFavourite;
    }

    @Override
    public String toString() {
        return "GsonNewsCmd [cmd=" + cmd + ", userName=" + userName
                + ", newsType=" + newsType + ", pageSize=" + pageSize
                + ", nowPage=" + nowPage + ", isFavourite=" + isFavourite + "]";
    }

    
}
