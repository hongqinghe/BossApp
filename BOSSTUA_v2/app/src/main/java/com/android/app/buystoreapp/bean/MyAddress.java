package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class MyAddress implements Serializable {
    private static final long serialVersionUID = -6965118461317766630L;
    private int id;
    private String  username ;
    private String  userphone;
    private String  userarea ;
    private String  userstreet ;
    private String   userdetailed;

    public MyAddress() {
    }

    public MyAddress(int id, String username, String userarea, String userstreet, String userdetailed, String userphone) {
        this.id = id;
        this.username = username;
        this.userarea = userarea;
        this.userstreet = userstreet;
        this.userdetailed = userdetailed;
        this.userphone = userphone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUserarea() {
        return userarea;
    }

    public void setUserarea(String userarea) {
        this.userarea = userarea;
    }

    public String getUserstreet() {
        return userstreet;
    }

    public void setUserstreet(String userstreet) {
        this.userstreet = userstreet;
    }

    public String getUserdetailed() {
        return userdetailed;
    }

    public void setUserdetailed(String userdetailed) {
        this.userdetailed = userdetailed;
    }

    @Override
    public String toString() {
        return "MyAddress{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userphone='" + userphone + '\'' +
                ", userarea='" + userarea + '\'' +
                ", userstreet='" + userstreet + '\'' +
                ", userdetailed='" + userdetailed + '\'' +
                '}';
    }
}