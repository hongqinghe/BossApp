package com.android.app.buystoreapp.bean;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/11/21.
 */
public class BitmapListBean implements Serializable {
    private List<Bitmap> bitmaps;

    public List<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }
}
