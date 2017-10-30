package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * Created by 尚帅波 on 2016/10/3.
 */
public class AddAddressCmd implements Serializable {
    private static final long serialVersionUID = 3240693747942759285L;


    /**
     * cmd :  addReceiverAddressr
     * userid : 2335
     * receiver_name : lisi
     * receiver_phone : 432523522
     * receiver_add : 2352dddss
     * receiver_postcode : 53453
     * is_defalt : 1
     * receiver_area : 23523sdgs
     * receiver_street : 232ddfs
     */

    private String cmd;
    private String userid;
    private String receiver_name;
    private String receiver_phone;
    private String receiver_add;
    private String receiver_postcode;
    private int is_defalt;
    private String receiver_area;
    private String receiver_street;

    public AddAddressCmd() {
    }

    public AddAddressCmd(String cmd, String userid, String receiver_name, String receiver_phone,
                         String receiver_add, String receiver_postcode, int is_defalt, String
                                  receiver_area, String receiver_street) {
        this.cmd = cmd;
        this.userid = userid;
        this.receiver_name = receiver_name;
        this.receiver_phone = receiver_phone;
        this.receiver_add = receiver_add;
        this.receiver_postcode = receiver_postcode;
        this.is_defalt = is_defalt;
        this.receiver_area = receiver_area;
        this.receiver_street = receiver_street;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getReceiver_add() {
        return receiver_add;
    }

    public void setReceiver_add(String receiver_add) {
        this.receiver_add = receiver_add;
    }

    public String getReceiver_postcode() {
        return receiver_postcode;
    }

    public void setReceiver_postcode(String receiver_postcode) {
        this.receiver_postcode = receiver_postcode;
    }

    public int getIs_defalt() {
        return is_defalt;
    }

    public void setIs_defalt(int is_defalt) {
        this.is_defalt = is_defalt;
    }

    public String getReceiver_area() {
        return receiver_area;
    }

    public void setReceiver_area(String receiver_area) {
        this.receiver_area = receiver_area;
    }

    public String getReceiver_street() {
        return receiver_street;
    }

    public void setReceiver_street(String receiver_street) {
        this.receiver_street = receiver_street;
    }

    @Override
    public String toString() {
        return "AddAddressCmd{" +
                "cmd='" + cmd + '\'' +
                ", userid='" + userid + '\'' +
                ", receiver_name='" + receiver_name + '\'' +
                ", receiver_phone='" + receiver_phone + '\'' +
                ", receiver_add='" + receiver_add + '\'' +
                ", receiver_postcode='" + receiver_postcode + '\'' +
                ", is_defalt=" + is_defalt +
                ", receiver_area='" + receiver_area + '\'' +
                ", receiver_street='" + receiver_street + '\'' +
                '}';
    }
}
