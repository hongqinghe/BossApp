package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public class SelOpenVipBean implements Serializable {


    /**
     * list : [{"state":0,"vipDescribe":"-特权一：尊贵身份标示\n-特权二：查看谁看过我\n-特权三：每天免费刷新三次","vipId":1,"vipImg":"http://192.168.1.122:8080/bossgroupimage/vipIcon/platinum.png","vipOriginalPrice":730,"vipPresentPrice":0.01,"vipUnit":"元/年"},{"state":0,"vipDescribe":"-特权一：尊贵身份标示\n-特权二：查看谁看过我\n-特权三：每天免费刷新三次\n-特权四：设置名片，让您的客户可以免费查看您的联系方式","vipId":2,"vipImg":"http://192.168.1.122:8080/bossgroupimage/vipIcon/diamond.png","vipOriginalPrice":3650,"vipPresentPrice":0.01,"vipUnit":"元/年"},{"state":0,"vipDescribe":"-特权一：尊贵身份标示\n-特权二：查看谁看过我\n-特权三：每天免费刷新三次\n-特权四：设置名片，让您的客户可以免费查看您的联系方式\n-特权五：选择一项发布的资源/服务推荐至全国城市首页","vipId":3,"vipImg":"http://192.168.1.122:8080/bossgroupimage/vipIcon/monarch.png","vipOriginalPrice":11998,"vipPresentPrice":0.01,"vipUnit":"元/年"}]
     * result : 0
     * resultNote : 加载成功
     */

    private String result;
    private String resultNote;
    /**
     * state : 0
     * vipDescribe : -特权一：尊贵身份标示
     -特权二：查看谁看过我
     -特权三：每天免费刷新三次
     * vipId : 1
     * vipImg : http://192.168.1.122:8080/bossgroupimage/vipIcon/platinum.png
     * vipOriginalPrice : 730
     * vipPresentPrice : 0.01
     * vipUnit : 元/年
     */

    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }


}
