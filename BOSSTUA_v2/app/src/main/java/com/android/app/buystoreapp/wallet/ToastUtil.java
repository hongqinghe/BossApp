package com.android.app.buystoreapp.wallet;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {
	
	private static String oldMsg;
	protected static Toast toast = null;
	private static long oneTime = 0;
	private static long twoTime = 0;
	
	private static String oldMsg1;
	protected static Toast toast1 = null;
	private static long oneTime1 = 0;
	private static long twoTime1 = 0;

	public static void showMessageCenter(Context context, String str) {
		if (toast == null) {
			toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (str.equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					toast.show();
				}
			} else {
				oldMsg = str;
				toast.setText(str);
				toast.show();
			}
		}
		oneTime = twoTime;
	}

	public static void showMessageDefault(Context context, String str) {
		if (toast1 == null) {
			toast1 = Toast.makeText(context, str, Toast.LENGTH_SHORT);
			toast1.show();
			oneTime1 = System.currentTimeMillis();
		} else {
			twoTime1 = System.currentTimeMillis();
			if (str.equals(oldMsg1)) {
				if (twoTime1 - oneTime1 > Toast.LENGTH_SHORT) {
					toast1.show();
				}
			} else {
				oldMsg1 = str;
				toast1.setText(str);
				toast1.show();
			}
		}
		oneTime1 = twoTime1;
	}
}
