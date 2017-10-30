package com.android.app.buystoreapp.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseFragment;
import com.android.app.buystoreapp.bean.GsonConfirmOrderBack;
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
 * 待付款
 * Created by 尚帅波 on 2016/9/21.
 */
public class WaitPayFragment extends BaseFragment {
    private View view;
    private List<OrderBean.OrderlistBean> orderList = new ArrayList<OrderBean.OrderlistBean>();
    private ListView lv_fragment_list;

    private OrderListAdapter adapter;

    private String price;//总价格
    private String body;//商品名称
    private List<String> proName = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    CustomDialog.initDialog(getActivity());
                    CustomDialog.tvTitle.setText("订单取消后不能恢复，是否取消订单？");
                    CustomDialog.btnLeft.setText("确定");
                    CustomDialog.btnRight.setText("再想想");
                    CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog.dialog.dismiss();
                            String orderId = orderList.get(msg.arg1).getOrderId();
                            updateOrder(orderId, 2, 11);
                            ToastUtil.showMessageDefault(getActivity(), "取消订单中...");
                        }
                    });
                    CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog.dialog.dismiss();
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
            }
        }
    };
    private String orderID;//大订单号
    private int userStatus;

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
            view = inflater.inflate(R.layout.fragment_list, container, false);
            initView();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load();
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
                    proName.addAll(gsonConfirmOrderBack.getProName());
                    List<String> proName = new ArrayList<>();
                    proName.addAll(gsonConfirmOrderBack.getProName());
                    Intent intent = new Intent(getActivity(), PayOrderActivity.class);
                    intent.putExtra("orderID", orderID);
                    intent.putExtra("price", price);
                    intent.putExtra("body", body);
                    intent.putExtra("flag","2");
                    intent.putExtra("proName", (Serializable) proName);
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
        Log.d("改变状态--",obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(new String(bytes));
                    if (obj.getString("result").equals("0")) {
                        load();
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

    @Override
    protected void load() {
        startWhiteLoadingAnim();
        String thisUser = SharedPreferenceUtils.getCurrentUserInfo(getActivity()).getUserId();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd","OrderStatus");
            obj.put("userId",thisUser);
            obj.put("status","1");
            obj.put("style",userStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("待支付--", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                OrderBean orderBean = gson.fromJson(new String(bytes), new TypeToken<OrderBean>() {
                }.getType());
                String result = orderBean.getResult();
                String resultNote = orderBean.getResultNote();
                if (result.equals("0")) {
                    if (orderBean.getOrderlist().size() > 0) {
                        orderList.clear();
                        orderList.addAll(orderBean.getOrderlist());
                    } else {
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
}
