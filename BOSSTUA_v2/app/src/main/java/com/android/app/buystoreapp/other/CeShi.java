package com.android.app.buystoreapp.other;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 尚帅波 on 2016/10/14.
 */
public class CeShi implements Serializable{
    private static final long serialVersionUID = -8765686790594654589L;
    private String shopName;
    private List<Bitmap> bitmaps;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }
}
