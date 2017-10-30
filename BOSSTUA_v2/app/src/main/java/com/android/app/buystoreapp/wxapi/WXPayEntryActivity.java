package com.android.app.buystoreapp.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.BossTicketPay;
import com.android.app.buystoreapp.MyBossTicketDetail;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.MarksBean;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.buystoreapp.managementservice.PaySuccessActivity;
import com.android.app.buystoreapp.setting.PayOrderActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class WXPayEntryActivity extends BaseAct implements IWXAPIEventHandler {
    private IWXAPI api;
    private String userName = "";
    int count = 0;
    private String url = "";
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, null);
        api.handleIntent(getIntent(), this);
        userName = SharedPreferenceUtils.getCurrentUserInfo(this)
                .getUserId();
        url = getString(R.string.web_url);
        userId = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
        initErrorPage();
        addIncludeLoading(true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {// (0:正常支付)-----(-1:错误)------(-2：用户取消)
                if ("1".equals(PayOrderActivity.flag)) {//VIP购买
                    saveLevelMark();
                    for (Activity act : CrashApplication.allActivity) {
                        if (act instanceof PayOrderActivity) {
                            act.finish();
                        }
                    }
                } else if ("2".equals(PayOrderActivity.flag)) {//订单购买
                    Intent intent = new Intent(this, PaySuccessActivity.class);
                    intent.putExtra("proName", (Serializable) PayOrderActivity.proName);
                    startActivity(intent);
                    for (Activity act : CrashApplication.allActivity) {
                        if (act instanceof PayOrderActivity) {
                            act.finish();
                        }
                    }
                } else {  //boss券购买
                    RequestTicketCount();
                    Toast.makeText(this, "支付成功",
                            Toast.LENGTH_SHORT).show();
                    for (Activity act : CrashApplication.allActivity) {
                        if (act instanceof BossTicketPay) {
                            act.finish();
                        }
                    }
                    if (MyBossTicketDetail.isChangePhone == 1) {
                        for (Activity activity : CrashApplication.allActivity) {
                            if (activity instanceof MyBossTicketDetail) {
                                activity.finish();
                            }
                        }
                    }
                }
                ToastUtil.showMessageDefault(mContext, "支付成功");
            } else if (resp.errCode == -1) {
                ToastUtil.showMessageDefault(mContext, "请求错误,请稍候再试");
            } else {
                ToastUtil.showMessageDefault(mContext, "取消支付");
            }
            finish();

        }
    }

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
                try {
                    JSONObject obj = new JSONObject(new String(arg2));
                    if ("0".equals(obj.getString("result"))) {
                        SharedPreferenceUtils.saveBossTicketCount(WXPayEntryActivity.this, obj
                                .getInt("bossTicket") + "");
                    } else {
                        Toast.makeText(WXPayEntryActivity.this, "请求失败，请稍后再试", Toast.LENGTH_SHORT)
                                .show();
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