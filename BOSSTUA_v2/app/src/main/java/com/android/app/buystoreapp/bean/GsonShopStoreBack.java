package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class GsonShopStoreBack implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1751962188437336278L;
    private String result;
    private String resultNote;
    private String shopName;
    private String shopAdress;
    private String shopPhone;
    private String shopIcon;
    private String shopLat;
    private String shopLon;
    private String shopIsFavourite;
    private List<ShopCategoryBean> shopCategoryList;

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

    public String getShopAdress() {
        return shopAdress;
    }

    public void setShopAdress(String shopAdress) {
        this.shopAdress = shopAdress;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public List<ShopCategoryBean> getShopCategoryList() {
        return shopCategoryList;
    }

    public void setShopCategoryList(List<ShopCategoryBean> shopCategoryList) {
        this.shopCategoryList = shopCategoryList;
    }

    public String getShopIcon() {
        return shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public String getShopLat() {
        return shopLat;
    }

    public void setShopLat(String shopLat) {
        this.shopLat = shopLat;
    }

    public String getShopLon() {
        return shopLon;
    }

    public void setShopLon(String shopLon) {
        this.shopLon = shopLon;
    }

    public String getShopIsFavourite() {
        return shopIsFavourite;
    }

    public void setShopIsFavourite(String shopIsFavourite) {
        this.shopIsFavourite = shopIsFavourite;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public String toString() {
        return "GsonShopStoreBack [result=" + result + ", resultNote="
                + resultNote + ", shopName=" + shopName + ", shopAdress="
                + shopAdress + ", shopPhone=" + shopPhone + ", shopIcon="
                + shopIcon + ", shopLat=" + shopLat + ", shopLon=" + shopLon
                + ", shopIsFavourite=" + shopIsFavourite
                + ", shopCategoryList=" + shopCategoryList + "]";
    }

}
