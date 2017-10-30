package com.android.app.buystore.utils;

import android.content.Context;

public class DataUtils{
	public static int dip2px(Context mContext,double dp){
		final float scale = mContext.getResources().getDisplayMetrics().density; 
        return (int)(dp * scale + 0.5f); 
	}
}
