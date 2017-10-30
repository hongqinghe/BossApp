package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/10/18.
 */
public class GsonShoppingCarBean implements Serializable {
    private static final long serialVersionUID = 2455520848614453756L;
    private String result;
    private String resultNote;
    private List<ShoppingCarBean> list;

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

    public List<ShoppingCarBean> getList() {
        return list;
    }

    public void setList(List<ShoppingCarBean> list) {
        this.list = list;
    }
}
