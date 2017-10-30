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
import com.android.app.buystoreapp.im.ChatActivity;
import com.android.app.buystoreapp.im.Constant;
import com.android.app.buystoreapp.other.CustomDialog;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 待收货
 * Created by 尚帅波 on 2016/9/21.
 */
public class WaitHarvestFragment extends BaseFragment {
    private View view;
    private List<OrderBean.OrderlistBean> orderList = new ArrayList<OrderBean.OrderlistBean>();
    private ListView lv_fragment_list;
    private OrderListAdapter adapter;
    private boolean isfirst = true;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
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

                    StringBuilder wlUrl = new StringBuilder();
                    wlUrl.append("http://m.kuaidi100.com/index_all.html?type=")
                            .append(logisticsCode)
                            .append("&postid=")
                            .append(theAwb)
                            .append("&callbackurl=null#result");
       /* wlUrl.append("http://m.kuaidi100.com/index_all.html?type=")
        .append("yuantong")
        .append("&postid=").append("500199715365");*/
                    LogUtils.d("wlurl=" + wlUrl.toString());

                    Intent webIntent = new Intent(getActivity(), BaseWebActivity.class);
                    webIntent.putExtra("url", wlUrl.toString());
                    webIntent.putExtra("type", "order");
                    startActivity(webIntent);
                    break;
                case 6:
                    CustomDialog.initDialog(mContext);
                    CustomDialog.tvTitle.setText("请确认您已完成收货");
                    CustomDialog.btnLeft.setText("我已收到");
                    CustomDialog.btnRight.setText("还未收到");
                    CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog.dialog.dismiss();
                            String orderId = orderList.get(msg.arg1).getOrderId();
                            updateOrder(orderId, 2, 4);
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
    };
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
            view = inflater.inflate(R.layout.fragment_list, null);
            initView();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        isfirst = false;
        load();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isfirst) {
            if (getUserVisibleHint()) {
                load();
            }
        }
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
            obj.put("cmd", "OrderStatus");
            obj.put("userId", thisUser);
            obj.put("status", "3");
            obj.put("style", userStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("待收货--", obj.toString());
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
                showErrorPageState(1);
            }
        });
    }

    private void initView() {
        lv_fragment_list = (ListView) view.findViewById(R.id.lv_fragment_list);
        adapter = new OrderListAdapter(mContext, orderList, userStatus, handler);
        lv_fragment_list.setAdapter(adapter);
    }

}
