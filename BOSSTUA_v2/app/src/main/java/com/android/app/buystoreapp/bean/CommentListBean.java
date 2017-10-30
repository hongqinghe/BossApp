package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/11/21.
 */
public class CommentListBean implements Serializable {
    private static final long serialVersionUID = -3074766091438991737L;
    public String cmd;
    public int style;
    public int status;
    public String orderId;
    public int userStatus;
    public List<CommentBean> listBean;

    public CommentListBean(){

    }
    public CommentListBean(String cmd, int style, int status, String orderId, int userStatus, List<CommentBean> listBean) {
        super();
        this.cmd = cmd;
        this.style = style;
        this.status = status;
        this.orderId = orderId;
        this.userStatus = userStatus;
        this.listBean = listBean;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public List<CommentBean> getListBean() {
        return listBean;
    }

    public void setListBean(List<CommentBean> listBean) {
        this.listBean = listBean;
    }

    @Override
    public String toString() {
        return "CommentListBean{" +
                "cmd='" + cmd + '\'' +
                ", style=" + style +
                ", status=" + status +
                ", orderId='" + orderId + '\'' +
                ", userStatus=" + userStatus +
                ", listBean=" + listBean +
                '}';
    }
}
