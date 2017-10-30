package com.android.app.buystoreapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.pay.demo.AlipayKeys;
import com.alipay.sdk.pay.demo.PayResult;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class BossTicketPay extends BaseAct implements OnClickListener {
    private int alipay = 2;
    private int wechatpay = 1;
    private String price = "0";
    private String orderId = "";
    private String boday = "";
    private String userName = "";
    private int count = 0;
    private String url = "";
    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;
    private IWXAPI api;
    private PayReq req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_select_pay);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_action_bar);
        ViewUtils.inject(this);
        mTitleText.setText("订单支付");
        BindClick();
        Init();
        api = WXAPIFactory.createWXAPI(mContext, null);
        api.registerApp("wxa2b94b4c31229c30");
        req = new PayReq();
        initErrorPage();
        addIncludeLoading(true);
    }

    @OnClick(R.id.id_custom_back_image)
    public void onCustomBarBackClicked(View v) {
        switch (v.getId()) {
            case R.id.id_custom_back_image:
                this.finish();
            default:
                break;
        }
    }

    private void BindClick() {
        findViewById(R.id.ll_zhifubao).setOnClickListener(this);
        findViewById(R.id.ll5_weixin).setOnClickListener(this);
    }

    private void Init() {
        count = getIntent().getIntExtra("count", 0);
        userName = SharedPreferenceUtils.getCurrentUserInfo(this)
                .getUserId();
        url = getString(R.string.web_url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_zhifubao:
                Log.e("支付宝", "onClick");
                getOrderId(alipay);
                break;
            case R.id.ll5_weixin:
                Log.e("微信", "onClick");
                getOrderId(wechatpay);
                break;
            default:
                break;
        }
    }

    private void getOrderId(final int channel) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("userName", userName);
            obj.put("Count", count + "");
            obj.put("PayFlag", channel);
            obj.put("cmd", "buyVouchers");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("json", obj.toString());
        client.get(url, params, new com.loopj.android.http.AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Log.d("购买boss券返回数据--", new String(arg2));
                try {
                    JSONObject obj = new JSONObject(new String(arg2));
                    if ("0".equals(obj.getString("result"))) {
                        orderId = obj.getString("orderId");
                        price = obj.getString("price");
                        if (channel == alipay) {
                            AliPay();
                        } else {
                            sendWeiXinPay();
                        }
                    } else {
                        Toast.makeText(BossTicketPay.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                Toast.makeText(BossTicketPay.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AliPay() {
        AlipayKeys alipayKeys = new AlipayKeys();
        alipayKeys.pay(this, orderId, "boss ticket", price + "", mHandler);
//        alipayKeys.pay(this, orderId, "boss ticket", 0.01 + "", mHandler);
        Toast.makeText(this, "请求支付中...请稍后", Toast.LENGTH_SHORT)
                .show();
    }

    public void sendWeiXinPay() {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "weChatPayment");
            obj.put("buyOrderId", orderId);
            obj.put("money", price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                stopLoadingAnim();
                try {
                    JSONObject obj = new JSONObject(new String(arg2));
                    String result = obj.getString("result");
                    String resultNote = obj.getString("resultNote");
                    JSONObject objs = obj.getJSONObject("results");
                    if (result.equals("0")) {
                        req.appId = objs.getString("appid");
                        req.partnerId = objs.getString("mch_id");
                        req.prepayId = objs.getString("prepay_id");
                        req.packageValue = objs.getString("packages");
                        req.nonceStr = objs.getString("nonce_str");
                        req.timeStamp = objs.getString("timestamp");
                        req.sign = objs.getString("sign");
                        mHandler.sendEmptyMessage(1111);
                    } else {
                        ToastUtil.showMessageDefault(mContext, resultNote);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                stopLoadingAnim();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
            }
        });
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1111:
                    api.sendReq(req);
                    break;
                case AlipayKeys.SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();
                    Log.d(AlipayKeys.TAG, "resultStatus=" + resultStatus
                            + " ,AliPay result=" + resultInfo);

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        RequestTicketCount();
                        Toast.makeText(BossTicketPay.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                        if (MyBossTicketDetail.isChangePhone == 1) {
                            for (Activity activity : CrashApplication.allActivity) {
                                if (activity instanceof MyBossTicketDetail) {
                                    activity.finish();
                                }
                            }
                        }
                        BossTicketPay.this.finish();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(BossTicketPay.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(BossTicketPay.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };

    private void RequestTicketCount() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getuserBossNum");
            obj.put("userName", userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams();
        params.put("json", obj.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, params, new com.loopj.android.http.AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Log.e("shang","buy ticket back------------"+new String(arg2));
                try {
                    JSONObject obj = new JSONObject(new String(arg2));
                    if ("0".equals(obj.getString("result"))) {
                        SharedPreferenceUtils.saveBossTicketCount(BossTicketPay.this, obj
                                .getInt("bossTicket") + "");
                    } else {
                        Toast.makeText(BossTicketPay.this, "请求失败，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {

            }
        });
    }
}
