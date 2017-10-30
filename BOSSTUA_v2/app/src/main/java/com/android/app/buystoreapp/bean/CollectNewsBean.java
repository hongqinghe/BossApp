package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */
public class CollectNewsBean implements Serializable {

    /**
     * result : 0
     * resultNote : 成功
     * ucnbList : [{"newsContent":"60","newsIcon":"/bossgroupimage/news/100/100.jpg","newsId":"100","newsSeeNum":0,"newsTitle":"关于\u201c腾讯抄你你咋办\u201d的终极答案"}]
     */

    private String result;
    private String resultNote;
    /**
     * newsContent : 60
     * newsIcon : /bossgroupimage/news/100/100.jpg
     * newsId : 100
     * newsSeeNum : 0
     * newsTitle : 关于“腾讯抄你你咋办”的终极答案
     */

    private List<UcnbListBean> ucnbList;

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

    public List<UcnbListBean> getUcnbList() {
        return ucnbList;
    }

    public void setUcnbList(List<UcnbListBean> ucnbList) {
        this.ucnbList = ucnbList;
    }


}
