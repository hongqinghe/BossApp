package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/11/14.
 */
public class GsonMessageBack implements Serializable {
    private static final long serialVersionUID = -4985120656032089432L;
    private List<LeaveList> leaveList;
    private List<OrderList> orderList;
    private List<SystemList> systemList;
    private String result;
    private String resultNote;
    private int state;

    public List<LeaveList> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(List<LeaveList> leaveList) {
        this.leaveList = leaveList;
    }

    public List<OrderList> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderList> orderList) {
        this.orderList = orderList;
    }

    public List<SystemList> getSystemList() {
        return systemList;
    }

    public void setSystemList(List<SystemList> systemList) {
        this.systemList = systemList;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
