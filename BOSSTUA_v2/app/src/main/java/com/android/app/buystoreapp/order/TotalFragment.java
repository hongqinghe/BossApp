package com.android.app.buystoreapp.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.BaseWebActivity;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseFragment;
import com.android.app.buystoreapp.bean.GsonConfirmOrderBack;
import com.android.app.buystoreapp.goods.CommentBuyActivity;
import com.android.app.buystoreapp.im.ChatActivity;
import com.android.app.buystoreapp.im.Constant;
import com.android.app.buystoreapp.other.CustomDialog;
import com.android.app.buystoreapp.setting.PayOrderActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 全部订单
 * Created by 尚帅波 on 2016/10/4.
 */
public class TotalFragment extends BaseFragment {

    private View view;
    private List<OrderBean.OrderlistBean> orderList = new ArrayList<OrderBean.OrderlistBean>();
    private ListView lv_fragment_list;

    private OrderListAdapter adapter;
    private String price;
    private String body;
    private int position;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            position = msg.arg1;
            switch (msg.what) {
                case 1:
                    CustomDialog.initDialog(getActivity());
                    CustomDialog.tvTitle.setText("订单取消后不能恢复，是否取消订单？");
                    CustomDialog.btnLeft.setText("确定");
                    CustomDialog.btnRight.setText("再想想");
                    CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog.dialog.dismiss();
                        }
                    });
                    CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog.dialog.dismiss();
                            String orderId = orderList.get(position).getOrderId();
                            updateOrder(orderId,2,11);
                            ToastUtil.showMessageDefault(getActivity(), "取消订单中...");
                        }
                    });
                    break;
                case 2:
                    String orderId = orderList.get(msg.arg1).getOrderId();
                    price = String.valueOf(orderList.get(msg.arg1).getOrderAmount());
                    body = orderList.get(msg.arg1).getNickname();

                    String[] arr = {orderId};
                    getBuyOrderId(arr);
                    break;
                case 3:
                    CustomDialog.initDialog(getActivity());
                    CustomDialog.tvTitle.setText("是否申请退款？");
                    CustomDialog.btnLeft.setText("确定");
                    CustomDialog.btnRight.setText("再想想");
                    CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog.dialog.dismiss();
                            Intent intent = new Intent(getActivity(),ApplyRefundAct.class);
                            String orderId = orderList.get(position).getOrderId();
                            intent.putExtra("orderId",orderId);
                            intent.putExtra("status",1);
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
                case 4:
                    if (msg.arg2 == 0) {
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_NAME, orderList.get(msg.arg1).getSellNickname());
                        intent.putExtra(Constant.EXTRA_USER_ICON, orderList.get(msg.arg1).getSellHeadicon());
                        intent.putExtra(Constant.EXTRA_USER_ID, orderList.get(msg.arg1).getSelluserId());
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_NAME, orderList.get(msg.arg1).getBuyNickname());
                        intent.putExtra(Constant.EXTRA_USER_ICON, orderList.get(msg.arg1).getBuyHeadicon());
                        intent.putExtra(Constant.EXTRA_USER_ID, orderList.get(msg.arg1).getBuyUserId());
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        startActivity(intent);
                    }
                    break;
                case 5:
                    String theAwb = orderList.get(msg.arg1).getTheAwb();
                    String logisticsCode = orderList.get(msg.arg1).getLogisticsCode();
                    if (TextUtils.isEmpty(theAwb) || TextUtils.isEmpty(logisticsCode)) {
                        ToastUtil.showMessageDefault(getActivity(), "尊贵的Boss,您购买的资源服务无需快递物流,详情咨询服务者");
                        return;//正式上线打开
                    }

                   /* StringBuilder wlUrl = new StringBuilder();
                    wlUrl.append("http://m.kuaidi100.com/index_all.html?type=")
                            .append(logisticsCode)
                            .append("&postid=")
                            .append(theAwb);*/
                    String url = "http://m.kuaidi100.com/index_all.html?type="+logisticsCode+"&postid="+theAwb+"&callbackurl=null#result";
       /* wlUrl.append("http://m.kuaidi100.com/index_all.html?type=")
        .append("yuantong")
        .append("&postid=").append("500199715365");*/
//                    LogUtils.d("wlurl=" + wlUrl.toString());

                    Intent webIntent = new Intent(getActivity(), BaseWebActivity.class);
//                    Intent webIntent = new Intent(getActivity(), TestActivity.class);
                    webIntent.putExtra("url", url);
                    webIntent.putExtra("type", "order");
                    startActivity(webIntent);
                    break;
                case 6://确认收货
                    orderId = orderList.get(msg.arg1).getOrderId();
                    updateOrder(orderId,2,4);
                    break;
                case 7:
                    CustomDialog.initDialog(getActivity());
                    CustomDialog.tvTitle.setText("订单删除后不能恢复，是否删除订单？");
                    CustomDialog.btnLeft.setText("确定");
                    CustomDialog.btnRight.setText("再想想");
                    CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog.dialog.dismiss();
                            String orderId = orderList.get(position).getOrderId();
                            updateOrder(orderId, 2, 12);
                            ToastUtil.showMessageDefault(getActivity(), "删除订单中...");
                        }
                    });
                    CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog.dialog.dismiss();
                        }
                    });
                    break;
                case 8:
                    int arg1 = msg.arg1;
                    orderId = orderList.get(msg.arg1).getOrderId();
                    List<OrderProduct> orderProduct = orderList.get(arg1).getOrderProductList();
                    Intent ratedIntent = new Intent(getActivity(), RatedActivity.class);
                    ratedIntent.putExtra("orderId", orderId);
                    ratedIntent.putExtra("orderProduct", (Serializable) orderProduct);
                    startActivity(ratedIntent);
                    break;
                case 9://回复评价（跳转评价列表）
                    Intent i = new Intent(getActivity(), CommentBuyActivity.class);
                    i.putExtra("proId", orderList.get(msg.arg1).getOrderProductList().get(0).getProId());//商品id
                    i.putExtra("proUserId", orderList.get(msg.arg1).getSelluserId());//发布人id
                    i.putExtra("indexId", 0);//评价类别
                    startActivity(i);
                    break;
                case 10://发起申诉
                    CustomDialog.initDialog(getActivity());
                    CustomDialog.tvTitle.setText("确定发起申诉？");
                    CustomDialog.btnLeft.setText("确定");
                    CustomDialog.btnRight.setText("再想想");
                    CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog.dialog.dismiss();
                            Intent intent = new Intent(getActivity(),ApplyRefundAct.class);
                            String orderId = orderList.get(position).getOrderId();
                            intent.putExtra("orderId",orderId);
                            intent.putExtra("status",0);
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
                case 11://拒绝退款
                    CustomDialog.initDialog(getActivity());
                    CustomDialog.tvTitle.setText("是否拒绝退款？");
                    CustomDialog.btnLeft.setText("确定");
                    CustomDialog.btnRight.setText("再想想");
                    CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog.dialog.dismiss();
                            Intent intent = new Intent(getActivity(),ApplyRefundAct.class);
                            String orderId = orderList.get(position).getOrderId();
                            intent.putExtra("orderId",orderId);
                            intent.putExtra("status",2);
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
                case 13://确认退款
                    CustomDialog.initDialog(getActivity());
                    CustomDialog.tvTitle.setText("是否确认退款？");
                    CustomDialog.btnLeft.setText("确定");
                    CustomDialog.btnRight.setText("再想想");
                    CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog.dialog.dismiss();
                            String orderId = orderList.get(position).getOrderId();
                            updateOrder(orderId,2,14);
                        }
                    });
                    CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog.dialog.dismiss();

                        }
                    });
                    break;
                case 14://确认发货
                    orderId = orderList.get(msg.arg1).getOrderId();
                    int status = orderList.get(msg.arg1).getStatus();
                    Intent intent1 = new Intent(getActivity(), OrderDetailActivity.class);
                    intent1.putExtra("orderId", orderId);
                    intent1.putExtra("status", status);
                    intent1.putExtra("userStatus",userStatus);
                    startActivity(intent1);
                    break;
                case 15://查看评价（跳转评价列表）
                    Intent intent2 = new Intent(getActivity(), CommentBuyActivity.class);
                   intent2.putExtra("proId", orderList.get(msg.arg1).getOrderProductList().get(0).getProId());//商品id
                   intent2.putExtra("proUserId", orderList.get(msg.arg1).getSelluserId());//发布人id
                   intent2.putExtra("indexId", 0);//评价类别
                    startActivity(intent2);
                    break;
                case 16://退款详情
                    Intent refundIntent = new Intent(mContext, RefundInofoActivity.class);
                    refundIntent.putExtra("orderId",orderList.get(msg.arg1).getOrderId());
                    refundIntent.putExtra("userStatus",userStatus);
                    startActivity(refundIntent);
                    break;
            }
        }
    };
    private int userStatus;
    private String orderID;

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
                    Intent intent = new Intent(getActivity(), PayOrderActivity.class);
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
     * 改变订单状态
     *
     * @author likaihang
     * creat at @time 16/11/07 13:49
     */
    private void updateOrder(String orderId,int style,int status) {
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
        Log.d("改变状态--",obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(new String(bytes));
                    if (obj.getString("result").equals("0")){
                        load();
                    }else {
                        ToastUtil.showMessageDefault(mContext,obj.getString("resultNote"));
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userStatus = getArguments().getInt("userStatus");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_list, null);
            initView();
        }
        return view;
    }

    @Override
    protected void load() {
        startWhiteLoadingAnim();
        String thisUser = SharedPreferenceUtils.getCurrentUserInfo(getActivity()).getUserId();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd","OrderStatus");
            obj.put("userId",thisUser);
            obj.put("status","0");
            obj.put("style",userStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("全部--",obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("获取订单返回值--", new String(bytes));
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                OrderBean orderBean = gson.fromJson(new String(bytes), new TypeToken<OrderBean>() {
                }.getType());
                String result = orderBean.getResult();
                String resultNote = orderBean.getResultNote();
                if (result.equals("0")) {
                    if (orderBean.getOrderlist().size()>0) {
                        orderList.clear();
                        orderList.addAll(orderBean.getOrderlist());
                    }else {
                        showEmpty(getString(R.string.order_is_empty));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showMessageDefault(getActivity(), resultNote);
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

    private void initView() {
        lv_fragment_list = (ListView) view.findViewById(R.id.lv_fragment_list);
        adapter = new OrderListAdapter(getActivity(), orderList,userStatus, handler);
        lv_fragment_list.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

}
