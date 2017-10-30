package com.android.app.buystoreapp.other;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.ListBean;
import com.android.app.buystoreapp.bean.OpenVipBean;
import com.android.app.buystoreapp.bean.SelOpenVipBean;
import com.android.app.buystoreapp.setting.PayOrderActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * VIP特权界面
 * Created by 尚帅波 on 2016/9/27.
 */
public class VipActivity extends BaseAct implements View.OnClickListener {

    private Button btn01_confirm, btn02_confirm, btn03_confirm; //立即开通按钮
    private TextView tv01_usefulTime, tv02_usefulTime, tv03_usefulTime;  //有效时间
    private TextView tv01_original, tv02_original, tv03_original; //原价
    private TextView tv01_currentPrice, tv02_currentPrice, tv03_currentPrice; //现价
    private List<ListBean> vipList = new ArrayList<ListBean>();


    private ImageView iv_back;
    private TextView tv_title;
    private Dialog openVipDialog;
    private String userId;
    private String state = "1";
    private String vipOrderNum;
    private double vipPayPrice;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OPEN_VIP_SUCCESS:
                    openVipDialog();
                    break;
                case SELOPENVIP_SUCCESS:
                    Button[] btn = new Button[]{btn01_confirm, btn02_confirm, btn03_confirm};
                    TextView[] tv = new TextView[]{tv01_usefulTime, tv02_usefulTime,
                            tv03_usefulTime};
                    TextView[] oldPrice = new TextView[]{tv01_original, tv02_original,
                            tv03_original};
                    TextView[] newPrice = new TextView[]{tv01_currentPrice, tv02_currentPrice,
                            tv03_currentPrice};
                    for (int i = 0; i < vipList.size(); i++) {
                        oldPrice[i].setText((int) vipList.get(i).getVipOriginalPrice() + "元/年");
                        newPrice[i].setText((int) vipList.get(i).getVipPresentPrice() + "元/年");
                        if (vipList.size() != 0 || vipList != null) {
                            if (vipList.get(i).getState() == -1) {
                                btn[i].setEnabled(false);
                                btn[i].setTextColor(getResources().getColor(R.color.c_999999));
                                btn[i].setBackgroundResource(R.drawable.vip_false);
                            } else if (vipList.get(i).getState() == 0) {
                                btn[i].setEnabled(true);
                                btn[i].setText(getResources().getString(R.string.vip_text_1));
                                btn[i].setTextColor(getResources().getColor(R.color.c_168eef));
                                btn[i].setBackgroundResource(R.drawable.buy_ticket_type_btn_green);
                            } else if (vipList.get(i).getState() == 1) {
                                btn[i].setEnabled(true);
                                btn[i].setText(getResources().getString(R.string.vip_text_2));
                                btn[i].setTextColor(getResources().getColor(R.color.c_ffffff));
                                btn[i].setBackgroundResource(R.drawable.buy_ticket_type_btn_yellow);
                            }
                            if (!TextUtils.isEmpty(vipList.get(i).getDownDate())) {
                                tv[i].setVisibility(View.VISIBLE);
                                tv[i].setText("有效期至：" + vipList.get(i).getDownDate());
                            } else {
                                tv[i].setVisibility(View.GONE);
                            }
                        }
                    }

                    break;
            }
        }
    };
    private static final int OPEN_VIP_SUCCESS = 1002;
    private static final int SELOPENVIP_SUCCESS = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vip);
        initView();
        initErrorPage();
        addIncludeLoading(true);
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startWhiteLoadingAnim();
        load();
    }

    @Override
    protected void load() {
        super.load();
        selOpenVip();
    }

    private void selOpenVip() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "selOpenVip");
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("查询Vip状态提交数据 obj==", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Log.e("查询Vip状态返回数据 bytes==", new String(bytes));
                Gson gson = new Gson();
                SelOpenVipBean selOpenVipBean = gson.fromJson(new String(bytes), new
                        TypeToken<SelOpenVipBean>() {
                }.getType());
                String result = selOpenVipBean.getResult();
                if ("0".equals(result)) {
                    vipList.clear();
                    vipList.addAll(selOpenVipBean.getList());
                    mHandler.obtainMessage(SELOPENVIP_SUCCESS).sendToTarget();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(VipActivity.this, getResources().getString(R.string
                        .service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }

    private void initView() {
        btn01_confirm = (Button) findViewById(R.id.btn01_confirm);
        btn02_confirm = (Button) findViewById(R.id.btn02_confirm);
        btn03_confirm = (Button) findViewById(R.id.btn03_confirm);
        tv01_usefulTime = (TextView) findViewById(R.id.tv01_usefulTime);
        tv02_usefulTime = (TextView) findViewById(R.id.tv02_usefulTime);
        tv03_usefulTime = (TextView) findViewById(R.id.tv03_usefulTime);
        tv01_original = (TextView) findViewById(R.id.tv01_original);
        tv02_original = (TextView) findViewById(R.id.tv02_original);
        tv03_original = (TextView) findViewById(R.id.tv03_original);
        tv01_currentPrice = (TextView) findViewById(R.id.tv01_currentPrice);
        tv02_currentPrice = (TextView) findViewById(R.id.tv02_currentPrice);
        tv03_currentPrice = (TextView) findViewById(R.id.tv03_currentPrice);
        btn01_confirm.setOnClickListener(this);
        btn02_confirm.setOnClickListener(this);
        btn03_confirm.setOnClickListener(this);

        tv01_original.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv02_original.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv03_original.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        iv_back = (ImageView) findViewById(R.id.iv_back);
//        text_title = (TextView) findViewById(R.id.text_title);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("VIP特权");
//        text_title.setVisibility(View.VISIBLE);
//        text_title.setText("帮助");
        iv_back.setOnClickListener(this);
//        text_title.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn01_confirm:
                state = "1";
                openVip();

                break;
            case R.id.btn02_confirm:
                state = "2";
                openVip();

                break;
            case R.id.btn03_confirm:
                state = "3";
                openVip();
                break;
            case R.id.iv_back:
                finish();
                break;
//            case R.id.text_title:
//                ToastUtil.showMessageDefault(this, "跳转到H5");
//                break;
        }
    }

    private void openVip() {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "openVip");
            obj.put("userId", userId);
            obj.put("state", state);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("开通Vip提交数据 obj==", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Log.e("开通Vip返回数据 bytes==", new String(bytes));
                Gson gson = new Gson();
                OpenVipBean openVipBean = gson.fromJson(new String(bytes), new
                        TypeToken<OpenVipBean>() {
                }.getType());
                String result = openVipBean.getResult();
                if ("0".equals(result)) {
                    vipOrderNum = openVipBean.getBean().getVipOrderNum();
                    vipPayPrice = openVipBean.getBean().getVipPayPrice();
                    mHandler.obtainMessage(OPEN_VIP_SUCCESS).sendToTarget();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(VipActivity.this, getResources().getString(R.string
                        .service_error_hint));

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }


    private void openVipDialog() {
        View layout = this.getLayoutInflater().inflate(R.layout
                .managenment_dialog, null);
        TextView tv_set_warning = (TextView) layout.findViewById(R.id.tv_set_warning);
        if (vipPayPrice == 0) {
            tv_set_warning.setText("您的当前Vip天数可直接兑换高一级的Vip,您确定兑换?");
        } else {
            tv_set_warning.setText("开通Vip需要支付" + vipPayPrice + "元");
        }
        Button btn_true = (Button) layout.findViewById(R.id.btn_true);
        Button btn_false = (Button) layout.findViewById(R.id.btn_false);
        btn_false.setText("再想想");
        if (vipPayPrice == 0) {
            btn_true.setText("兑换");
        } else {
            btn_true.setText("去支付");
        }
        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVipDialog.dismiss();
                if (vipPayPrice == 0) {
                    updateOpenVip();
                } else {
                    Intent intent = new Intent(VipActivity.this, PayOrderActivity.class);
                    intent.putExtra("orderID", vipOrderNum);
                    intent.putExtra("price", vipPayPrice + "");
                    intent.putExtra("body", "Boss团");
                    intent.putExtra("flag", "1");
                    startActivityForResult(intent, 1000);
                }


            }
        });
        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVipDialog.dismiss();

            }
        });
        openVipDialog = new Dialog(this, R.style.Dialog);
        openVipDialog.setContentView(layout);
        openVipDialog.show();
    }

    private void updateOpenVip() {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "updateOpenVip");
            obj.put("userId", userId);
            obj.put("vipOrderNum", vipOrderNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("修改Vip状态提交数据 obj==", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("修改Vip状态返回数据 bytes==", new String(bytes));
                stopLoadingAnim();
                hideErrorPageState();
                try {
                    JSONObject object = new JSONObject(new String(bytes));
                    String result = object.getString("result");
                    if ("0".equals(result)) {
                        ToastUtil.showMessageDefault(VipActivity.this, "Vip兑换成功");
                        load();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(VipActivity.this, getResources().getString(R.string
                        .service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }
}
