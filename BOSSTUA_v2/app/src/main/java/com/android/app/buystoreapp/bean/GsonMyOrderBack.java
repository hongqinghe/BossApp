package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class GsonMyOrderBack implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3837416681137087944L;
    private String result;
    private String resultNote;
    private List<MyOrderBean> orderLists;

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

    public List<MyOrderBean> getOrderLists() {
        return orderLists;
    }

    public void setOrderLists(List<MyOrderBean> orderLists) {
        this.orderLists = orderLists;
    }

    @Override
    public String toString() {
        return "GsomMyOrderBack [result=" + result + ", resultNote="
                + resultNote + ", orderLists=" + orderLists + "]";
    }

}
