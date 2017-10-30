package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/10/29.
 */
/*{
  "cmd": "createOrder",
  "thisUser": "yW1lG6E2vxoQLsj7",
  "scobList": [
    {
      "receiveAddId": "1",
      "userComment": "请快速发货，谢谢您",
      "serviceMethod": "EMS或者免邮",
      "orderPro": [
        {
          "proId": "6jk496XbmgS4xddj",
          "proCount": 20,
          "proImgUrl": "bossosstuan.png",
          "shopCarId": "6jk496koeuS4xddj"
        }
      ]
    }
  ]
}*/
public class ConfrimOrderPostBean implements Serializable{
    private static final long serialVersionUID = -2535908267224092294L;
    private String cmd;
   private String thisUser;
    private double totalPrice;
   private List<ScobList> scobList;

    public ConfrimOrderPostBean(String cmd, String thisUser, double totalPrice,List<ScobList> scobList) {
        this.cmd = cmd;
        this.thisUser = thisUser;
        this.scobList = scobList;
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getThisUser() {
        return thisUser;
    }

    public void setThisUser(String thisUser) {
        this.thisUser = thisUser;
    }

    public List<ScobList> getScobList() {
        return scobList;
    }

    public void setScobList(List<ScobList> scobList) {
        this.scobList = scobList;
    }
}
