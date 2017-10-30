package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class GsonShopCarToOrder implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5218351378196928616L;
    private List<CommodityBean> commoditesList;
    
    

    public GsonShopCarToOrder(List<CommodityBean> commoditesList) {
        super();
        this.commoditesList = commoditesList;
    }

    public List<CommodityBean> getCommoditesList() {
        return commoditesList;
    }

    public void setCommoditesList(List<CommodityBean> commoditesList) {
        this.commoditesList = commoditesList;
    }

    @Override
    public String toString() {
        return "GsonShopCarToOrder [commoditesList=" + commoditesList + "]";
    }

}
