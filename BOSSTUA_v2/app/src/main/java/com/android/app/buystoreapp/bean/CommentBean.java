package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * $desc
 * Created by likaihang on 16/09/13.
 */
public class CommentBean implements Serializable{

    private static final long serialVersionUID = -4727350389144386115L;
    /*
        *    "proId":"asdjhfaiwhfawiue",                              //商品ID
                "listimg":[         	                                          //图像String数组
                    "IEJDSH8304HIHGSLJSDI090LKJF9978534JLKSJGFHEGOIG9803239"   //字符串数据流（图片）
                ],
                "evalContent":"nihao",                                     //评价内容
                "evalLevel":"2",                                                 //0.差评1.中评2.好评
                "evalAnonymity":"0",                                        //是否匿名评价 0.否1.是
                "evalUserId":"asdjhfaiwhfawiue"                   //评价人Id

        * */
    public String proId;
    public String evalContent;
    public String evalLevel;
    public String evalAnonymity;
    public String evalUserId;
    public String[] listimg;

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getEvalContent() {
        return evalContent;
    }

    public void setEvalContent(String evalContent) {
        this.evalContent = evalContent;
    }

    public String getEvalLevel() {
        return evalLevel;
    }

    public void setEvalLevel(String evalLevel) {
        this.evalLevel = evalLevel;
    }

    public String getEvalAnonymity() {
        return evalAnonymity;
    }

    public void setEvalAnonymity(String evalAnonymity) {
        this.evalAnonymity = evalAnonymity;
    }

    public String getEvalUserId() {
        return evalUserId;
    }

    public void setEvalUserId(String evalUserId) {
        this.evalUserId = evalUserId;
    }

    public String[] getListimg() {
        return listimg;
    }

    public void setListimg(String[] listimg) {
        this.listimg = listimg;
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "proId='" + proId + '\'' +
                ", evalContent='" + evalContent + '\'' +
                ", evalLevel='" + evalLevel + '\'' +
                ", evalAnonymity='" + evalAnonymity + '\'' +
                ", evalUserId='" + evalUserId + '\'' +
                ", listimg=" + Arrays.toString(listimg) +
                '}';
    }
}
