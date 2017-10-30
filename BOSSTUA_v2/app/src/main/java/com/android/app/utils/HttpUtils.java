package com.android.app.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.app.buystoreapp.crash.CrashApplication;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * $desc 网络请求类
 * Created by likaihang on 16/10/11.
 */
public class HttpUtils {
    //测试环境
    //private static final String BASE_URL = "http://192.168.1.122:8080/buyService/service?";
    private static final String BASE_URL = "http://59.110.5.164/buyService/service?";
//    //李燕飞ip
//    private static final String BASE_URL = "http://192.168.1.117:8080/buyService/service?";
    //线上环境
//private static final String BASE_URL = "http://218.241.30.183:8080/buyService/service?";
    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void get(String url, RequestParams params, Context context, AsyncHttpResponseHandler responseHandler) {
        PersistentCookieStore myPersistentCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myPersistentCookieStore);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * 暂时只调了post请求
     *
     * @author likaihang
     * context 用于取消请求
     * creat at @time 16/10/11 17:27
     */
    public static void post(Context context, JSONObject obj, AsyncHttpResponseHandler responseHandler,RequestNetworkError networkError) {
        if (!NetWorkUtil.isNetworkConnected(CrashApplication.contex)) {
            Toast.makeText(CrashApplication.contex, "网络不给力", Toast.LENGTH_SHORT)
                    .show();
            networkError.networkError();
            return;
        } else {
            client.post(context, BASE_URL, getParams(obj), responseHandler);
        }
    }
    public interface RequestNetworkError {
        void networkError();
    }
    public static void post(String url, RequestParams params, Context context, AsyncHttpResponseHandler responseHandler) {
        PersistentCookieStore myPersistentCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myPersistentCookieStore);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static RequestParams getParams(JSONObject obj) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("json", obj.toString());
        return requestParams;
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
