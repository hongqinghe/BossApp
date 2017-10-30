package com.android.app.buystoreapp.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.pay.demo.AlipayKeys;
import com.alipay.sdk.pay.demo.PayResult;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.MarksBean;
import com.android.app.buystoreapp.managementservice.PaySuccessActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class PayOrderActivity extends BaseAct implements OnClickListener {
    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;
    @ViewInject(R.id.id_custom_back_image)
    ImageButton back;
    @ViewInject(R.id.ll_zhifubao)
    LinearLayout alipayLayout;
    @ViewInject(R.id.ll5_weixin)
    LinearLayout weiXinLayout;
    private String webUrl;
    private String orderId;
    private String price;
    private String body;
    public static List<String> proName;
    public static String flag = "0";
    private String userId;

    private IWXAPI api;
    private PayReq req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_select_pay);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_action_bar);
        ViewUtils.inject(this);
        mTitleText.setText("订单支付");
        api = WXAPIFactory.createWXAPI(mContext, null);
        api.registerApp("wxa2b94b4c31229c30");
        req = new PayReq();
        orderId = getIntent().getStringExtra("orderID");
        price = getIntent().getStringExtra("price");
        body = getIntent().getStringExtra("body");
        flag = getIntent().getStringExtra("flag");
        proName = (List<String>) getIntent().getSerializableExtra("proName");
        webUrl = getResources().getString(R.string.web_url);
        back.setOnClickListener(this);
        alipayLayout.setOnClickListener(this);
        weiXinLayout.setOnClickListener(this);
        userId = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
        initErrorPage();
        addIncludeLoading(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_zhifubao:
                AlipayKeys alipayKeys = new AlipayKeys();
                alipayKeys.pay(this,
                        orderId, body, price,
                        mHandler);
                Toast.makeText(this,
                        "请求支付中...请稍后", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.ll5_weixin:
                Toast.makeText(this,
                        "请求支付中...请稍后", Toast.LENGTH_SHORT)
                        .show();
                sendWeiXinPay();
                break;
            case R.id.id_custom_back_image:
                finish();
                break;
        }
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
                        Toast.makeText(PayOrderActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                        if ("1".equals(flag)) {//VIP购买
                            saveLevelMark();
                            finish();
                        } else {
                            Intent intent = new Intent(PayOrderActivity.this, PaySuccessActivity
                                    .class);
                            intent.putExtra("proName", (Serializable) proName);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayOrderActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayOrderActivity.this, "支付失败",
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

    /**
     * 保存VIP等级
     */
    private void saveLevelMark() {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getMarks");
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                MarksBean marksBean = gson.fromJson(new String(bytes), new
                        TypeToken<MarksBean>() {
                        }.getType());
                String result = marksBean.getResult();
                MarksBean.BeanBean bean = marksBean.getBean();
                if ("0".equals(result)) {
                    SharedPreferenceUtils.saveMarkInfo(bean, mContext);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable
                    throwable) {
                stopLoadingAnim();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
            }
        });
    }

}
