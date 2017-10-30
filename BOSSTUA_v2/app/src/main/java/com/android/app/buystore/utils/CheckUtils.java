package com.android.app.buystore.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Pattern;

public class CheckUtils {
    private static final String EMAIL_CHECK = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
     private static final String MOBILE_CHECK = "^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";
    private static final int PASSWORD_MIN = 6;
    private static final int PASSWORD_MAX = 20;
    
    public static boolean emailCheck(String username) {
        try {
            Pattern regex = Pattern.compile(EMAIL_CHECK);
            return regex.matcher(username).matches();
        } catch (Exception e) {
            Log.e("mikes", "email check error:", e);
        }
        return false;
    }
    
    public static boolean mobileCheck(String username) {
        try {
            Pattern regex = Pattern.compile(MOBILE_CHECK);
            return regex.matcher(username).matches();
        } catch (Exception e) {
            Log.e("mikes", "mobile check error:", e);
        }
        return false;
    }
    
    public static boolean checkPassword(String pwd, String newPwd) {
        if (pwd.length() < PASSWORD_MIN || pwd.length() > PASSWORD_MAX) return false;
        
        if (TextUtils.isEmpty(newPwd)) {
            //登陆
        } else {
            //注册
            return pwd.length() == newPwd.length() && pwd.equals(newPwd);
        }
        
        return false;
    }
    
    public static boolean checkPassword(String pwd) {
        return !(pwd.length() < PASSWORD_MIN || pwd.length() > PASSWORD_MAX || TextUtils.isEmpty(pwd));
    }
}
