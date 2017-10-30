package com.android.app.buystoreapp.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.app.buystoreapp.BaseWebActivity;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.GsonConfirmOrderBack;
import com.android.app.buystoreapp.bean.GsonWayBillBack;
import com.android.app.buystoreapp.bean.ReceiveAddressBean;
import com.android.app.buystoreapp.goods.CommentBuyActivity;
import com.android.app.buystoreapp.im.ChatActivity;
import com.android.app.buystoreapp.im.Constant;
import com.android.app.buystoreapp.other.CustomDialog;
import com.android.app.buystoreapp.other.CustomListView;
import com.android.app.buystoreapp.setting.PayOrderActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends BaseAct implements View.OnClickListener {
    private static final int GET_LOGISTICS = 0x01;
    private String orderId;
    private int status;
    private OrdersDetailsBean.OrderDetailsBean orderDetail;
    private List<OrderProduct> orderProduct = new ArrayList<OrderProduct>();

    private TextView tv_shopName, tv_tranTypeMoney, tv_count_type, tv_allMoney, harvest_name,
            tv_harvest_phone, tv_harvest_address, tv_order_code, tv_orderType, tv_orderTypeTime,
            tv_leaveMessage;
    private ImageView iv_orderType, iv_back;
    private LinearLayout ll_orderType;
    private CustomListView lv_order_all;
    private Button btn1_order, btn2_order;

    private OrderProductAdapter adapter;
    private int userStatus;
    private LinearLayout ll_send_waybill;
    private EditText et_send_waybill_num;
    private String sendNum;
    private TextView tv_send_waybill_company;
    private List<GsonWayBillBack.Details> billlist = new ArrayList<GsonWayBillBack.Details>();
    private String price;
    private String body;
    private String orderID;
    private String theAwb = "";
    private String logisticsCode = "";
    private String tranType = "";//服务方式编码  0线上 1线下  20免运费  21货到付款 22货运
    private TextView empty_wuliu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_detail);

        orderId = getIntent().getStringExtra("orderId");
        status = getIntent().getIntExtra("status", 0);
        userStatus = getIntent().getIntExtra("userStatus", 0);
        initView();
        initErrorPage();
        addIncludeLoading(true);
        load();
    }

    private String code = "";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 111:
                    //tv_shopName.setText(orderDetail.getShopName());
                    if (tv_orderTypeTime != null) {
                        tv_orderTypeTime.setText(orderDetail.getRemainingTime());//加上倒计时效果
                    }
                    tv_leaveMessage.setText(orderDetail.getUserComment());
                    tv_count_type.setText("共" + orderDetail.getProNum() + "件 商品");
                    tranType = orderDetail.getServiceMethod();
                    if (tranType.equals("0")) {
                        tv_tranTypeMoney.setText("线上");
                    } else if (tranType.equals("1")) {
                        tv_tranTypeMoney.setText("线下");
                    } else if (tranType.equals("20")) {
                        tv_tranTypeMoney.setText("免运费");
                    } else if (tranType.equals("21")) {
                        tv_tranTypeMoney.setText("货到付款");
                    } else if (tranType.equals("22")) {
                        tv_tranTypeMoney.setText("运送");
                    }
//                    tv_tranTypeMoney.setText(orderDetail.getServiceMethod());
                    tv_allMoney.setText("￥" + orderDetail.getOrderAmount());
                    String orderCode = orderDetail.getOrderId();
                    tv_order_code.setText(orderCode);
                    ReceiveAddressBean address = orderDetail.getReAddress();
                    harvest_name.setText(address.getReceiverName());
                    tv_harvest_phone.setText(address.getReceiverPhone());
                    tv_harvest_address.setText(address.getReceiverArea() + address.getReceiverAdd
                            () + address.getReceiverStreet());
                    adapter.notifyDataSetChanged();

                    if (status == 2 && userStatus == 1) {
                        if (tranType.equals("0")) {
                            empty_wuliu.setVisibility(View.VISIBLE);
                        } else if (tranType.equals("1")) {
                            empty_wuliu.setVisibility(View.VISIBLE);
                        } else if (tranType.equals("20")) {
                            ll_send_waybill.setVisibility(View.VISIBLE);
                        } else if (tranType.equals("21")) {
                            ll_send_waybill.setVisibility(View.VISIBLE);
                        } else if (tranType.equals("22")) {
                            ll_send_waybill.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case GET_LOGISTICS:
                    final String[] arr = new String[billlist.size()];
                    for (int j = 0; j < billlist.size(); j++) {
                        arr[j] = billlist.get(j).getLogisticsName();
                    }
                    AlertDialog.Builder aler = null;
                    if (aler == null) {
                        aler = new AlertDialog.Builder(OrderDetailActivity.this);
                        aler.setTitle("");
                        aler.setItems(arr, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                tv_send_waybill_company.setText(arr[which]);
                                code = billlist.get(which).getLogisticsCode();
                            }
                        });

                        aler.create().show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void load() {
        super.load();
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getOrderDetails");
            obj.put("orderId", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("订单详情返回值--", new String(bytes));
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                OrdersDetailsBean ordersDetailsBean = gson.fromJson(new String(bytes), new
                        TypeToken<OrdersDetailsBean>() {
                        }.getType());
                String result = ordersDetailsBean.getResult();
                if (result.equals("0")) {
                    orderDetail = ordersDetailsBean.getOrderDetails().get(0);
                    orderProduct.clear();
                    orderProduct.addAll(orderDetail.getOrderProduct());
                    handler.sendEmptyMessage(111);
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

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_orderType = (ImageView) findViewById(R.id.iv_orderType);
        tv_orderType = (TextView) findViewById(R.id.tv_orderType);
        ll_orderType = (LinearLayout) findViewById(R.id.ll_orderType);
        tv_orderTypeTime = (TextView) findViewById(R.id.tv_orderTypeTime);
        tv_shopName = (TextView) findViewById(R.id.tv_shopName);
        tv_count_type = (TextView) findViewById(R.id.tv_count_type);
        tv_tranTypeMoney = (TextView) findViewById(R.id.tv_tranTypeMoney);
        tv_allMoney = (TextView) findViewById(R.id.tv_allMoney);
        harvest_name = (TextView) findViewById(R.id.harvest_name);
        tv_harvest_phone = (TextView) findViewById(R.id.tv_harvest_phone);
        tv_harvest_address = (TextView) findViewById(R.id.tv_harvest_address);
        tv_order_code = (TextView) findViewById(R.id.tv_order_code);
        tv_leaveMessage = (TextView) findViewById(R.id.tv_leaveMessage);
        lv_order_all = (CustomListView) findViewById(R.id.lv_order_all);
        ll_send_waybill = (LinearLayout) findViewById(R.id.ll_send_waybill);
        empty_wuliu = (TextView) findViewById(R.id.empty_wuliu);
        ll_send_waybill.setVisibility(View.GONE);
        empty_wuliu.setVisibility(View.GONE);
        et_send_waybill_num = (EditText) findViewById(R.id.et_send_waybill_num);
        tv_send_waybill_company = (TextView) findViewById(R.id.tv_send_waybill_company);
        btn1_order = (Button) findViewById(R.id.btn1_order);
        btn2_order = (Button) findViewById(R.id.btn2_order);
        btn1_order.setOnClickListener(this);
        btn2_order.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_send_waybill_company.setOnClickListener(this);

        switch (status) {
            case 1:
                iv_orderType.setImageResource(R.drawable.ic_wait_pay);
                if (userStatus == 0) {
                    tv_orderType.setVisibility(View.GONE);
                    ll_orderType.setVisibility(View.VISIBLE);
                    btn1_order.setText("取消订单");
                    btn2_order.setText("前去支付");
                } else {
                    tv_orderType.setText("买家拍下未付款");
                    btn1_order.setText("取消订单");
                    btn2_order.setText("聊一聊");
                }
                break;
            case 2:
                if (userStatus == 0) {
                    iv_orderType.setImageResource(R.drawable.ic_wait_send);
                    tv_orderType.setText("等待卖家发货");
                    btn1_order.setText("退款");
                    btn2_order.setText("聊一聊");
                } else {
                    tv_orderType.setText("买家已付款，请尽快发货");
                    btn1_order.setText("聊一聊");
                    btn2_order.setText("确认发货");
//                    ll_send_waybill.setVisibility(View.VISIBLE);

                }
                break;
            case 3:
                iv_orderType.setImageResource(R.drawable.ic_wait_harvest);
                if (userStatus == 0) {
                    tv_orderType.setText("您的订单已发货");
                    btn1_order.setText("退款");
                    btn2_order.setText("聊一聊");
                    //btn1_order.setText("查看物流");
                    //btn2_order.setText("确认收货");
                } else {
                    tv_orderType.setText("等待买家确认收货,30天后自动确认收货");
                    btn1_order.setVisibility(View.GONE);
                    btn2_order.setText("聊一聊");
                }
                break;
            case 4:
                iv_orderType.setImageResource(R.drawable.ic_wait_rated);
                if (userStatus == 0) {
                    tv_orderType.setText("您有订单待评价");
                    btn1_order.setVisibility(View.GONE);
                    btn1_order.setText("退款");
                    btn2_order.setText("评价");
                } else {
                    tv_orderType.setText("买家已确认收货");
                    btn1_order.setVisibility(View.GONE);
                    btn2_order.setText("聊一聊");
                }
                break;
            case 5://退款
                iv_orderType.setImageResource(R.drawable.ic_wait_pay);
                if (userStatus == 0) {
                    tv_orderType.setText("已申请退款");
                    btn1_order.setVisibility(View.GONE);
                    btn2_order.setText("聊一聊");
                } else {
                    tv_orderType.setText("买家申请退款");
                    btn1_order.setText("拒绝退款");
                    btn2_order.setText("确认退款");
                }
                break;
            case 6://发起申诉
                iv_orderType.setImageResource(R.drawable.ic_wait_pay);
                tv_orderType.setText("申诉中");
                btn1_order.setVisibility(View.GONE);
                btn2_order.setText("聊一聊");
                break;
            case 7://处理超时
                iv_orderType.setImageResource(R.drawable.ic_wait_pay);
                tv_orderType.setText("处理超时");
                btn1_order.setVisibility(View.GONE);
                btn2_order.setText("聊一聊");
                break;
            case 8://交易成功
                iv_orderType.setImageResource(R.drawable.ic_tick);
                if (userStatus == 0) {
                    tv_orderType.setText("交易成功");
                    btn1_order.setText("聊一聊");
                    btn2_order.setText("查看评价");
                } else {
                    tv_orderType.setText("交易成功,您有新的评价");
                    btn1_order.setText("聊一聊");
                    btn2_order.setText("回复评价");
                }
                break;
            case 9://申诉成功
                iv_orderType.setImageResource(R.drawable.ic_tick);
                tv_orderType.setText("申诉成功");
                btn1_order.setVisibility(View.GONE);
                btn2_order.setText("聊一聊");
                break;
            case 10://申诉失败
                iv_orderType.setImageResource(R.drawable.ic_wait_pay);
                tv_orderType.setText("申诉失败");
                btn1_order.setVisibility(View.GONE);
                btn2_order.setText("聊一聊");
                break;
            case 11://取消订单
                iv_orderType.setImageResource(R.drawable.ic_wait_pay);
                tv_orderType.setText("订单已取消");
                btn1_order.setText("删除订单");
                btn2_order.setText("聊一聊");
                break;
            case 12:
                //删除订单状态不显示不作任何处理
                break;
            case 13://拒绝退款
                iv_orderType.setImageResource(R.drawable.ic_wait_pay);
                if (userStatus == 0) {
                    tv_orderType.setText("卖家拒绝退款");
                    btn1_order.setText("发起申诉");
                    btn2_order.setText("聊一聊");
                } else {
                    tv_orderType.setText("拒绝退款");
                    btn1_order.setVisibility(View.GONE);
                    btn2_order.setText("聊一聊");
                }
                break;
            case 14://退款成功
                iv_orderType.setImageResource(R.drawable.ic_tick);
                if (userStatus == 0) {
                    tv_orderType.setText("卖家已同意退款");
                    btn1_order.setVisibility(View.GONE);
                    btn2_order.setText("聊一聊");
                } else {
                    tv_orderType.setText("已同意退款");
                    btn1_order.setVisibility(View.GONE);
                    btn2_order.setText("聊一聊");
                }
                break;
        }

        adapter = new OrderProductAdapter(mContext, orderProduct);
        lv_order_all.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        }
        if (v.getId() == R.id.tv_send_waybill_company) {
            getLogistics();

        }
        switch (status) {
            case 1:
                if (userStatus == 0) {
                    switch (v.getId()) {
                        case R.id.btn1_order:
                            CustomDialog.initDialog(this);
                            CustomDialog.tvTitle.setText("订单取消后不能恢复，是否取消订单？");
                            CustomDialog.btnLeft.setText("确定");
                            CustomDialog.btnRight.setText("再想想");
                            CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();
                                    updateOrder(orderId, 2, 11);
                                }
                            });
                            CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();
                                }
                            });
                            break;
                        case R.id.btn2_order:
                            String orderId = orderDetail.getOrderId();
                            price = String.valueOf(orderDetail.getOrderAmount());
                            body = orderDetail.getNickname();

                            String[] arr = {orderId};
                            getBuyOrderId(arr);
                            break;
                    }
                } else {
                    switch (v.getId()) {
                        case R.id.btn1_order:
                            CustomDialog.initDialog(this);
                            CustomDialog.tvTitle.setText("此操作不可恢复，请与买家沟通确认！");
                            CustomDialog.btnLeft.setText("确定");
                            CustomDialog.btnRight.setText("再想想");
                            CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();
                                    updateOrder(orderId, 2, 11);
                                }
                            });
                            CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();
                                }
                            });
                            break;
                        case R.id.btn2_order:
                            Intent intent = new Intent(this, ChatActivity.class);
                            intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                            intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon
                                    ());
                            intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                            startActivity(intent);
                            break;
                    }
                }
                break;
            case 2:
                if (userStatus == 0) {
                    switch (v.getId()) {
                        case R.id.btn1_order:
                            CustomDialog.initDialog(this);
                            CustomDialog.tvTitle.setText("是否申请退款？");
                            CustomDialog.btnLeft.setText("确定");
                            CustomDialog.btnRight.setText("再想想");
                            CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();
                                    Intent intent = new Intent(OrderDetailActivity.this,
                                            ApplyRefundAct.class);
                                    String orderId = orderDetail.getOrderId();
                                    intent.putExtra("orderId", orderId);
                                    intent.putExtra("status", 1);
                                    startActivity(intent);
                                }
                            });
                            CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();

                                }
                            });
                            break;
                        case R.id.btn2_order:
                            if (userStatus == 0) {
                                Intent intent = new Intent(this, ChatActivity.class);
                                intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                                intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                                intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(this, ChatActivity.class);
                                intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                                intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                                intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                                startActivity(intent);
                            }
                            break;
                    }
                } else {
                    switch (v.getId()) {
                        case R.id.btn1_order:
                            if (userStatus == 0) {
                                Intent intent = new Intent(this, ChatActivity.class);
                                intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                                intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                                intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(this, ChatActivity.class);
                                intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                                intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                                intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                                startActivity(intent);
                            }
                            break;
                        case R.id.btn2_order:
                            if (checkIsEmpty()) {
                                sendBill(orderId, 2, 3);
                            }
                            break;
                    }
                }
                break;
            case 3:
                theAwb = getIntent().getStringExtra("theAwb");
                logisticsCode = getIntent().getStringExtra("logisticsCode");
                if (userStatus == 0) {
                    switch (v.getId()) {
                        case R.id.btn1_order:
                           /* ToastUtil.showMessageDefault(OrderDetailActivity.this,
                           "跳转到物流查看界面...");
                            if (TextUtils.isEmpty(theAwb) || TextUtils.isEmpty(logisticsCode)) {
                                ToastUtil.showMessageDefault(OrderDetailActivity.this, "暂无物流信息");
                                return;
                            }

                            StringBuilder wlUrl = new StringBuilder();
                            wlUrl.append("http://m.kuaidi100.com/index_all.html?type=")
                                    .append(logisticsCode)
                                    .append("&postid=").append(theAwb);
                            LogUtils.d("wlurl=" + wlUrl.toString());

                            Intent webIntent = new Intent(OrderDetailActivity.this,
                            BaseWebActivity.class);
                            webIntent.putExtra("url", wlUrl.toString());
                            webIntent.putExtra("type", "order");
                            startActivity(webIntent);*/

                            CustomDialog.initDialog(mContext);
                            CustomDialog.tvTitle.setText("是否申请退款？");
                            CustomDialog.btnLeft.setText("确定");
                            CustomDialog.btnRight.setText("再想想");
                            CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();
                                    Intent intent = new Intent(mContext, ApplyRefundAct.class);
                                    intent.putExtra("orderId", orderId);
                                    intent.putExtra("status", 1);
                                    startActivity(intent);
                                }
                            });
                            CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();
                                }
                            });
                            break;
                        case R.id.btn2_order:
                            //updateOrder(orderId, 2, 4);
                            if (userStatus == 0) {
                                Intent intent = new Intent(this, ChatActivity.class);
                                intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                                intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                                intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(this, ChatActivity.class);
                                intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                                intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                                intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                                startActivity(intent);
                            }
                            break;
                    }
                } else {
                    switch (v.getId()) {
                        case R.id.btn1_order:
                            ToastUtil.showMessageDefault(OrderDetailActivity.this, "跳转到物流查看界面...");
                            if (TextUtils.isEmpty(theAwb) || TextUtils.isEmpty(logisticsCode)) {
                                ToastUtil.showMessageDefault(OrderDetailActivity.this, "暂无物流信息");
                                return;
                            }

                            StringBuilder wlUrl = new StringBuilder();
                            wlUrl.append("http://m.kuaidi100.com/index_all.html?type=")
                                    .append(logisticsCode)
                                    .append("&postid=").append(theAwb);
                            LogUtils.d("wlurl=" + wlUrl.toString());

                            Intent webIntent = new Intent(OrderDetailActivity.this,
                                    BaseWebActivity.class);
                            webIntent.putExtra("url", wlUrl.toString());
                            webIntent.putExtra("type", "order");
                            startActivity(webIntent);
                            break;
                        case R.id.btn2_order:
                            if (userStatus == 0) {
                                Intent intent = new Intent(this, ChatActivity.class);
                                intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                                intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                                intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(this, ChatActivity.class);
                                intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                                intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                                intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                                startActivity(intent);
                            }
                            break;
                    }
                }
                break;
            case 4:
                if (userStatus == 0) {
                    switch (v.getId()) {
                        case R.id.btn1_order:
                            CustomDialog.initDialog(this);
                            CustomDialog.tvTitle.setText("是否申请退款？");
                            CustomDialog.btnLeft.setText("确定");
                            CustomDialog.btnRight.setText("再想想");
                            CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();
                                    Intent intent = new Intent(OrderDetailActivity.this,
                                            ApplyRefundAct.class);
                                    String orderId = orderDetail.getOrderId();
                                    intent.putExtra("orderId", orderId);
                                    intent.putExtra("status", 1);
                                    startActivity(intent);
                                }
                            });
                            CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();

                                }
                            });
                            break;
                        case R.id.btn2_order:
                            Intent ratedIntent = new Intent(mContext, RatedActivity.class);
                            String orderId = orderDetail.getOrderId();
                            ratedIntent.putExtra("orderId", orderId);
                            ratedIntent.putExtra("orderProduct", (Serializable) orderProduct);
                            startActivity(ratedIntent);
                            break;
                    }
                } else {
                    switch (v.getId()) {
                        case R.id.btn2_order:
                            if (userStatus == 0) {
                                Intent intent = new Intent(this, ChatActivity.class);
                                intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                                intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                                intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(this, ChatActivity.class);
                                intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                                intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                                intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                                startActivity(intent);
                            }
                            break;
                    }
                }
                break;
            case 5:
                if (userStatus == 0) {
                    switch (v.getId()) {
                        case R.id.btn2_order:
                            if (userStatus == 0) {
                                Intent intent = new Intent(this, ChatActivity.class);
                                intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                                intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                                intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(this, ChatActivity.class);
                                intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                                intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                                intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                                startActivity(intent);
                            }
                            break;
                    }
                } else {
                    switch (v.getId()) {
                        case R.id.btn1_order:
                            CustomDialog.initDialog(this);
                            CustomDialog.tvTitle.setText("是否拒绝退款？");
                            CustomDialog.btnLeft.setText("确定");
                            CustomDialog.btnRight.setText("再想想");
                            CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();
                                    Intent intent = new Intent(OrderDetailActivity.this,
                                            ApplyRefundAct.class);
                                    String orderId = orderDetail.getOrderId();
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
                            break;
                        case R.id.btn2_order:
                            CustomDialog.initDialog(this);
                            CustomDialog.tvTitle.setText("是否确认退款？");
                            CustomDialog.btnLeft.setText("确定");
                            CustomDialog.btnRight.setText("再想想");
                            CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();
                                    updateOrder(orderId, 2, 14);
                                }
                            });
                            CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();

                                }
                            });
                            break;
                    }
                }
                break;
            case 6:
                if (v.getId() == R.id.btn2_order) {
                    if (userStatus == 0) {
                        Intent intent = new Intent(this, ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                        intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                        intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(this, ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                        intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                        intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        startActivity(intent);
                    }
                }
                break;
            case 7:
                if (v.getId() == R.id.btn2_order) {
                    if (userStatus == 0) {
                        Intent intent = new Intent(this, ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                        intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                        intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(this, ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                        intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                        intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        startActivity(intent);
                    }
                }
                break;
            case 8:
                switch (v.getId()) {
                    case R.id.btn1_order:
                        if (userStatus == 0) {
                            Intent intent = new Intent(this, ChatActivity.class);
                            intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                            intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                            intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(this, ChatActivity.class);
                            intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                            intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                            intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                            startActivity(intent);
                        }
                        break;
                    case R.id.btn2_order:
                        Intent i = new Intent(this, CommentBuyActivity.class);
                        i.putExtra("proId", orderDetail.getOrderProduct().get(0).getProId());//商品id
                        i.putExtra("proUserId", orderDetail.getSelluserId());//发布人id
                        i.putExtra("indexId", 0);//评价类别
                        startActivity(i);
                        break;
                }
                break;
            case 9:
                if (v.getId() == R.id.btn2_order) {
                    if (userStatus == 0) {
                        Intent intent = new Intent(this, ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                        intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                        intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(this, ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                        intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                        intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        startActivity(intent);
                    }
                }
                break;
            case 10:
                if (v.getId() == R.id.btn2_order) {
                    if (userStatus == 0) {
                        Intent intent = new Intent(this, ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                        intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                        intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(this, ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                        intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                        intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        startActivity(intent);
                    }
                }
                break;
            case 11:
                switch (v.getId()) {
                    case R.id.btn1_order:
                        CustomDialog.initDialog(this);
                        CustomDialog.tvTitle.setText("确定删除订单？");
                        CustomDialog.btnLeft.setText("确定");
                        CustomDialog.btnRight.setText("再想想");
                        CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CustomDialog.dialog.dismiss();
                                updateOrder(orderId, 2, 12);
                            }
                        });
                        CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CustomDialog.dialog.dismiss();
                            }
                        });
                        break;
                    case R.id.btn2_order:
                        if (userStatus == 0) {
                            Intent intent = new Intent(this, ChatActivity.class);
                            intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                            intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                            intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(this, ChatActivity.class);
                            intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                            intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                            intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                            startActivity(intent);
                        }
                        break;
                }
                break;
            case 13:
                if (userStatus == 0) {
                    switch (v.getId()) {
                        case R.id.btn1_order:
                            CustomDialog.initDialog(this);
                            CustomDialog.tvTitle.setText("确定发起申诉？");
                            CustomDialog.btnLeft.setText("确定");
                            CustomDialog.btnRight.setText("再想想");
                            CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomDialog.dialog.dismiss();
                                    Intent intent = new Intent(OrderDetailActivity.this,
                                            ApplyRefundAct.class);
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
                            break;
                        case R.id.btn2_order:
                            if (userStatus == 0) {
                                Intent intent = new Intent(this, ChatActivity.class);
                                intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                                intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                                intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(this, ChatActivity.class);
                                intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                                intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                                intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                                startActivity(intent);
                            }
                            break;
                    }
                } else {
                    if (v.getId() == R.id.btn2_order) {
                        if (userStatus == 0) {
                            Intent intent = new Intent(this, ChatActivity.class);
                            intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                            intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                            intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(this, ChatActivity.class);
                            intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                            intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                            intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                            startActivity(intent);
                        }
                    }
                }
                break;
            case 14:
                if (v.getId() == R.id.btn2_order) {
                    if (userStatus == 0) {
                        Intent intent = new Intent(this, ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getNickname());
                        intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getSellHeadicon());
                        intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getSelluserId());
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(this, ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_NAME, orderDetail.getBuyNickname());
                        intent.putExtra(Constant.EXTRA_USER_ICON, orderDetail.getBuyHeadicon());
                        intent.putExtra(Constant.EXTRA_USER_ID, orderDetail.getUserid());
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    /**
     * 获取大订单号
     *
     * @author likaihang
     * creat at @time 16/11/09 18:59
     */
    private void getBuyOrderId(String[] orderids) {
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i = 0; i < orderids.length; i++) {
            array.put(orderids[i]);
        }
        try {
            obj.put("cmd", "buyOrderId");
            obj.put("orderIds", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("获取购买订单号提交数据--", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("获取购买订单号返回数据--", new String(bytes));
                Gson gson = new Gson();
                GsonConfirmOrderBack gsonConfirmOrderBack = gson.fromJson(
                        new String(bytes),
                        new TypeToken<GsonConfirmOrderBack>() {
                        }.getType());
                String result = gsonConfirmOrderBack.getResult();
                if ("0".equals(result)) {
                    orderID = gsonConfirmOrderBack.getBuyOrderid();
                    Intent intent = new Intent(mContext, PayOrderActivity.class);
                    intent.putExtra("orderID", orderID);
                    intent.putExtra("price", price);
                    intent.putExtra("body", body);
                    startActivity(intent);
                } else {
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

    /**
     * 确认发货
     *
     * @author likaihang
     * creat at @time 16/11/07 13:49
     */
    private void sendBill(String orderId, int style, int status) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "updateOrder");
            obj.put("style", style);
            obj.put("status", status);
            obj.put("orderId", orderId);
            obj.put("userStatus", userStatus);
            obj.put("theAwb", sendNum);
            obj.put("logisticsCode", code);
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
                        ToastUtil.showMessageDefault(mContext, "提交成功！");
                        finish();
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

    /**
     * 改变订单状态
     *
     * @author likaihang
     * creat at @time 16/11/07 13:49
     */
    private void updateOrder(String orderId, int style, int status) {
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
                        finish();
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

    //获取物流公司信息
    private void getLogistics() {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getLogistics");
            obj.put("wlName", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                Gson gson = new Gson();
                GsonWayBillBack gsonWayBillBack = gson.fromJson(new String(bytes), new
                        TypeToken<GsonWayBillBack>() {
                        }.getType());
                if (gsonWayBillBack.getResult().equals("0")) {
                    billlist.clear();
                    billlist.addAll(gsonWayBillBack.getList());
                    handler.obtainMessage(GET_LOGISTICS).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
            }
        });
    }

    private boolean checkIsEmpty() {
        if (tranType.equals("0") || tranType.equals("1")) {
            return true;
        }
        sendNum = et_send_waybill_num.getText().toString().trim();
        if (!TextUtils.isEmpty(code) && !TextUtils.isEmpty(sendNum)) {
            return true;
        } else {
            ToastUtil.showMessageDefault(mContext, getString(R.string.get_send_name));
        }
        return false;
    }
}
