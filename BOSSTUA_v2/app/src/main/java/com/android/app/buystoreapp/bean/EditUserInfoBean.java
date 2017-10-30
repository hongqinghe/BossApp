package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class EditUserInfoBean implements Serializable {
    private static final long serialVersionUID = -1468367843186328890L;
    private boolean isLogin;
    private String userName;
    private String userIcon;
    private String nikeName;
    private String score;
    private String passWord;

    public EditUserInfoBean() {

    }

    public EditUserInfoBean(String userName) {
        super();
        this.userName = userName;
    }

    public EditUserInfoBean(String userName, String passWord) {
        super();
        this.userName = userName;
        this.passWord = passWord;
    }

    public EditUserInfoBean(String userName, String userIcon, String nickName) {
        super();
        this.userName = userName;
        this.userIcon = userIcon;
        this.nikeName = nickName;
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
        return nikeName;
    }

    public void setNickName(String nickName) {
        this.nikeName = nickName;
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
                + ", userIcon=" + userIcon + ", nickName=" + nikeName
                + ", score=" + score + ", passWord=" + passWord + "]";
    }

}
