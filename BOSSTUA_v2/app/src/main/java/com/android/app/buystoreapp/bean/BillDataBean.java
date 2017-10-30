package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class BillDataBean implements Serializable {
    /**
     * bilDetails : [{"bilAmount":0.01,"bilDate":"2016-11-15","bilId":"IaXtyldjIoV2bwWH","bilState":1,"famDateTime":"今天 17:27:48","headicon":"http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png","proTitle":"卖出商品【凤翼天翔】所得收入","style":4}]
     * result : 0
     * resultNote : 加载成功
     */

    private String result;
    private String resultNote;
    /**
     * bilAmount : 0.01
     * bilDate : 2016-11-15
     * bilId : IaXtyldjIoV2bwWH
     * bilState : 1
     * famDateTime : 今天 17:27:48
     * headicon : http://192.168.1.122:8080/bossgroupimage/userIcon/default/defalt_usericon.png
     * proTitle : 卖出商品【凤翼天翔】所得收入
     * style : 4
     */

    private List<BilDetailsBean> bilDetails;

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

    public List<BilDetailsBean> getBilDetails() {
        return bilDetails;
    }

    public void setBilDetails(List<BilDetailsBean> bilDetails) {
        this.bilDetails = bilDetails;
    }


    }


//    /**
//     * bilDetails : [{"bilAmount":0,"bilDate":"2016-10-19","bilId":"askdhfawei","bilState":2,"headicon":"/bossgroupimage/bossTicketBuy/expendIcon.png","nickname":"Boss_06670133/Boss_李开航/Boss_李开航","proTitle":"李开航/代码代码/李开航","style":0}]
//     * result : 1
//     * resultNote : 加载成功
//     */
//
//    private String result;
//    private String resultNote;
//    /**
//     * bilAmount : 0
//     * bilDate : 2016-10-19
//     * bilId : askdhfawei
//     * bilState : 2
//     * headicon : /bossgroupimage/bossTicketBuy/expendIcon.png
//     * nickname : Boss_06670133/Boss_李开航/Boss_李开航
//     * proTitle : 李开航/代码代码/李开航
//     * style : 0
//     */
//
//    private List<BillDetailsAllBean> bilDetails;
//
//    public String getResult() {
//        return result;
//    }
//
//    public void setResult(String result) {
//        this.result = result;
//    }
//
//    public String getResultNote() {
//        return resultNote;
//    }
//
//    public void setResultNote(String resultNote) {
//        this.resultNote = resultNote;
//    }
//
//    public List<BillDetailsAllBean> getBilDetails() {
//        return bilDetails;
//    }
//
//    public void setBilDetails(List<BillDetailsAllBean> bilDetails) {
//        this.bilDetails = bilDetails;
//    }



