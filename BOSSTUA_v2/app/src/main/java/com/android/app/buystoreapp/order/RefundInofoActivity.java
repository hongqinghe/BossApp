package com.android.app.buystoreapp.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.adapter.RefundInfoAdapter;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.RefundBackBean;
import com.android.app.buystoreapp.im.ChatActivity;
import com.android.app.buystoreapp.im.Constant;
import com.android.app.buystoreapp.other.CustomDialog;
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
 * 退款流程
 * Created by 尚帅波 on 2016/12/17.
 */
public class RefundInofoActivity extends BaseAct implements View.OnClickListener,
        RefundInfoAdapter.OnRefundBtnClickListener {

    private ImageView iv_back;
    private ListView listView;
    private RefundInfoAdapter refundInfoAdapter;
    private String orderId;
    private int userStatus;
    private OrderBean.OrderlistBean orderBean;
    private List<RefundBackBean.BeanBean> refundList = new ArrayList<>();
    private static final int REFUND_SUCCESS = 100;//确认退款
//    private static final int REFUND_SUCCESS = 100;
//    private static final int REFUND_SUCCESS = 100;
//    private static final int REFUND_SUCCESS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_refund_inofo);
        orderId = getIntent().getStringExtra("orderId");
        userStatus = getIntent().getIntExtra("userStatus", 0);
        orderBean = (OrderBean.OrderlistBean) getIntent().getSerializableExtra("orderBean");
        initView();
        addIncludeLoading(true);
        initErrorPage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFUND_SUCCESS:
                    load();
                    ToastUtil.showMessageDefault(mContext, "确认退款成功");
                    break;
            }
        }
    };

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.refundList);
        refundInfoAdapter = new RefundInfoAdapter(mContext, refundList, userStatus);
        listView.setAdapter(refundInfoAdapter);
        refundInfoAdapter.setOnRefundBtnClickListener(this);
    }

    @Override
    protected void load() {
        super.load();
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getOrderFeedback");
            obj.put("orderId", orderId);
            obj.put("status", userStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("shang", new String(bytes));
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                RefundBackBean refundBackBean = gson.fromJson(new String(bytes), new
                        TypeToken<RefundBackBean>() {
                        }.getType());
                String result = refundBackBean.getResult();
                String resultNote = refundBackBean.getResultNote();
                if (result.equals("0")) {
                    refundList.clear();
                    refundList.addAll(refundBackBean.getBean());
                    refundInfoAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showMessageDefault(mContext, resultNote);
                    showErrorPageState(1);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                showErrorPageState(1);
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefundBtnClick(View view, int userStatus, int style, int isClick) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                if (userStatus == 0) {//聊一聊
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    intent.putExtra(Constant.EXTRA_USER_NAME, orderBean.getSellNickname());
                    intent.putExtra(Constant.EXTRA_USER_ICON, orderBean.getSellHeadicon());
                    intent.putExtra(Constant.EXTRA_USER_ID, orderBean.getSelluserId());
                    intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                    startActivity(intent);
                } else {//同意退款
                    if (isClick == 1) {
                        CustomDialog.initDialog(mContext);
                        CustomDialog.tvTitle.setText("是否确认退款？");
                        CustomDialog.btnLeft.setText("确定");
                        CustomDialog.btnRight.setText("再想想");
                        CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CustomDialog.dialog.dismiss();
                                updateOrder(orderId, 2, 14, REFUND_SUCCESS);
                            }
                        });
                        CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CustomDialog.dialog.dismiss();

                            }
                        });
                    } else {
                        ToastUtil.showMessageDefault(mContext, "您已处理该状态,请不要重复处理");
                    }
                }
                break;
            case R.id.btn_confirm:
                if (isClick == 1) {
                    if (userStatus == 0) {
                        if (style == 2) {//申请申诉
                            CustomDialog.initDialog(mContext);
                            CustomDialog.tvTitle.setText("确定发起申诉？");
                            CustomDialog.btnLeft.setText("确定");
                            CustomDialog.btnRight.setText("再想想");
                            CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();
                                    Intent intent = new Intent(mContext, ApplyRefundAct.class);
                                    intent.putExtra("orderId", orderId);
                                    intent.putExtra("status", 0);
                                    startActivity(intent);
                                }
                            });
                            CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();
                                }
                            });
                        } else {//取消申诉

                        }
                    } else {//拒绝退款
                        CustomDialog.initDialog(mContext);
                        CustomDialog.tvTitle.setText("是否拒绝退款？");
                        CustomDialog.btnLeft.setText("确定");
                        CustomDialog.btnRight.setText("再想想");
                        CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CustomDialog.dialog.dismiss();
                                Intent intent = new Intent(mContext, ApplyRefundAct.class);
                                intent.putExtra("orderId", orderId);
                                intent.putExtra("status", 2);
                                startActivity(intent);
                            }
                        });
                        CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CustomDialog.dialog.dismiss();

                            }
                        });
                    }
                } else {
                    ToastUtil.showMessageDefault(mContext, "您已处理该状态,请不要重复处理");
                }
                break;
        }
    }

    /**
     * 改变订单状态
     *
     * @param orderId
     * @param style
     * @param status
     */
    private void updateOrder(String orderId, int style, int status, final int msg) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "updateOrder");
            obj.put("style", style);
            obj.put("status", status);
            obj.put("orderId", orderId);
            obj.put("userStatus", userStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("改变状态--", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(new String(bytes));
                    if (obj.getString("result").equals("0")) {
                        handler.sendEmptyMessage(msg);
                    } else {
                        ToastUtil.showMessageDefault(mContext, obj.getString("resultNote"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {

            }
        });
    }
}
