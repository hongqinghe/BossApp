package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class UserInfoBean implements Serializable {
    private static final long serialVersionUID = -1468367843186328890L;
    private boolean isLogin;
    private String userId;
    private String userName;
    private String userIcon;
    private String nickName;
    private String score;
    private String passWord;
    private String ticketCount;
    private String inviteCode;//邀请码
    private int bindingMark1;
    private int bindingMark2;
    private int bindingMark3;
    private int bindingMark4;
    private int userLevelMark;
    private String userTreasure;

    /*
    "userId":"WzRxdVYMG6f4W57N",                     	//用户ID
    "userName":"13693034344",                         	//用户登录名
    "bindingMark1": 0,                                                   //实名认证：0未开通，1已开通
    "bindingMark2": 0,                                                   //芝麻信用：0未开通，1已开通
    "bindingMark3": 0,                                                   //职业认证：0未开通，1已开通
    "bindingMark4": 0,                                                   //企业认证：0未开通，1已开通
    "userLevelMark": 0,                                                 //等级
    "userTreasure": "0"                                                  //财富值   （注意是String类型）*/
    public UserInfoBean() {

    }

    public UserInfoBean(String userName) {
        super();
        this.userName = userName;
    }

    public UserInfoBean(String userName, String passWord) {
        super();
        this.userName = userName;
        this.passWord = passWord;
    }

    public UserInfoBean(String userName, String userIcon, String nickName) {
        super();
        this.userName = userName;
        this.userIcon = userIcon;
        this.nickName = nickName;
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

    public int getUserLevelMark() {
        return userLevelMark;
    }

    public void setUserLevelMark(int userLevelMark) {
        this.userLevelMark = userLevelMark;
    }

    public String getUserTreasure() {
        return userTreasure;
    }

    public void setUserTreasure(String userTreasure) {
        this.userTreasure = userTreasure;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getUserName() {
        return userName;
    }

    public String getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(String ticketcount) {
        this.ticketCount = ticketcount;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "UserInfo [isLogin=" + isLogin + ", userName=" + userName
                + ", userIcon=" + userIcon + ", nickName=" + nickName
                + ", score=" + score + ", passWord=" + passWord + "]";
    }

}
