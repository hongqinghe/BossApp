package com.alipay.sdk.pay.demo;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class AlipayKeys {
    public static final String TAG = "alipay";
    
    // 商户PID
    public static final String PARTNER = "2088021522178541";
    // 商户收款账号
    public static final String SELLER = "bosstuan@bosstuan.cn";
    // 商户私钥，pkcs8格式
  //  public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKJwV6fs5gdJ49nS95rIOr4OtL9ghuc8lnpkhHV++APJdqSMQvphNrqvg5+GWbpUstLLX8YyplTa2blpWozI8M68eoQOiqJyLn/pJuhRyP5GA4rqJUeRjmAuzbi265NIEPZJdiLJERitqvgnwjoUddHdtfHfuYTePFqgql48A+p7AgMBAAECgYA6/iPHd7CeqwYV4JlN3DQBPCQGcM7tgcKAlqrIZSE7dtogrlPQzKdsqztZHxjNI9+B0JxqxZBEtuplfM6G3J8fUXH9YfZDUQOPGMfTQs+31AcS350XDnFwZdWPCR30M6WSnXIw78EKHC22Awv9v7vRwAdRnLJf64A0Eyc0bXYASQJBANSxrp8he8eMx6rOKghRoFmWUq+RA4/EIZE0jFmqbog4yvosqRhCkxIKUivJEDpkVJ3eybt6uHyPie8FjoJrNG0CQQDDgzEC0tBDBZ5tBGJ9XQ0bWD8uMxcQNVIfsNkr15qNQFY28BcSIv0zKyvB5EReMqTza12TlEo8sfNH6SiaqzmHAkEAuf2qgrMIB6bez0k7Tkz62HxwT+CSabyL0etOs/Yqc+qd7IjzUSiebQ8jbfCDxVwI9ohkhhI5k4r9hkuoo/lVPQJAFmqCQgqdqjGIuoMYL7ttd0ck500gRdF3Ov7xx18O70cDG4Vnf8LXOPioKbDcTdiMpQQYVIVaBZ95fzMyXplnHQJAaLRCla1+UQNuTXidIg1OM0Lp9qSTt+qRInhIa9+Sg0hFrlnwljG8Zxb7oVNZWYieQ6fgYRLwcoQKYYLa0pM/0A==";
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMiaBTOweo9JaKQ3Kmup7W6WLLsoKOQ3gx6XwHo/Rl/qSSf8V1duNtyCbIc9FQ+Yq7amfwNcOl6zua75zrC0Ob8jnn2yWHAhDNO4Azp4FQPxGovpN+UVKvgNMNjD/z59d31EI0WAPnDx3dSFjceRszYrHP+UD71ZaqzWkYF5ccvBAgMBAAECgYAXoU0LkbZeOp+A+J6eoB2/8SDDyy9F8uQXLnkV7W40zjxf4XHIcssznol6RM++TpWrpmE/3BVkIne1DOg0GIbUr3OgcWcr83hZJAYN8QwRdeKJFMy993l8Cmz0so7aZZi7+fjB3DY9U88vGxUyNuhi7Xfr7JwgdXdfTEGoI1w+oQJBAP7SG56wE9kQtGgW439NvuHZRMrmCqlE4i5L3d1oTpsyNqwmDuisaqmrwIfZtggrfCYfm8B0ehBlxbfPzzdQmWcCQQDJh62EJrlPAUZt7VXwwNHcc/tE27WsHa2/0rRZ3u4lBMJkXepKXoIaJtbmex8yKkkUSCd05ee1hrwGDa/4PDCXAkBarDjXxp/JZYBMaZiZ+/goNT+dUy4H/VLeVnCkEDGoV73Mk95KPM/XgB4KORXk4lRS3Hx3hvrISu6H8ZIyhKORAkEAuD6+uC7fBAfaZ+2vwGfIdY/jjNSBh1Wq39C26eMSPe3X+wKRjGRS9a3EkvrFTouyPESrLghyilcf1zj0RDJjCQJBAJGNkqIal2Gbi/OUcOhIcAwQ5JhKwWNb+5R4Fvml2/gu3vSwE3ljEUEJP0H/8DwwQMh4JTnbFJ8WN6Kk1tegEBE=";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "";
    
    public static final int SDK_PAY_FLAG = 1;
    
    public void pay(final Activity context,String subject,String body,String price, final Handler hanlder) {
        // 订单
        String orderInfo = getOrderInfo(subject, body, price);

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();
        Log.d(TAG, "payInfo: " + payInfo);
        
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(context);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                hanlder.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    
    /**
     * create the order info. 创建订单信息
     * 
     */
    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + subject + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径 http://116.255.239.201:8080/buyService/paynotify
           orderInfo += "&notify_url=" + "\"" + "http://59.110.5.164/buyService/paynotify" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
    
    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     * 
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }
    
    /**
     * sign the order info. 对订单信息进行签名
     * 
     * @param content
     *            待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }
    
    /**
     * get the sign type we use. 获取签名方式
     * 
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
