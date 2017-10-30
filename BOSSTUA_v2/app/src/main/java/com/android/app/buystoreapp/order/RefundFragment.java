package com.android.app.buystoreapp.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseFragment;
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
 * 退款
 * Created by 尚帅波 on 2016/12/17.
 */
public class RefundFragment extends BaseFragment {
    private View view;
    private List<OrderBean.OrderlistBean> orderList = new ArrayList<OrderBean.OrderlistBean>();
    private ListView lv_fragment_list;

    private OrderListAdapter adapter;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 4:
                case 16:
                   /* Intent intent = new Intent(RefundActivity.this, ChatActivity.class);
                    intent.putExtra(Constant.EXTRA_USER_NAME, orderList.get(msg.arg1)
                            .getBuyNickname());
                    intent.putExtra(Constant.EXTRA_USER_ICON, orderList.get(msg.arg1)
                            .getSellHeadicon());
                    intent.putExtra(Constant.EXTRA_USER_ID, orderList.get(msg.arg1).getSelluserId
                            ());
                    intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                    startActivity(intent);*/
                    Intent intent = new Intent(mContext, RefundInofoActivity.class);
                    intent.putExtra("orderId",orderList.get(msg.arg1).getOrderId());
                    intent.putExtra("userStatus",1);
                    startActivity(intent);
                case 10:

                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void initView() {
        lv_fragment_list = (ListView) view.findViewById(R.id.lv_fragment_list);
        adapter = new OrderListAdapter(getActivity(), orderList, 1, handler);
        lv_fragment_list.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load();
    }


    @Override
    protected void load() {
        startWhiteLoadingAnim();
        final Gson gson = new Gson();
        String thisUser = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
        OrderStatusCmd orderStatusCmd = new OrderStatusCmd();
        orderStatusCmd.setCmd("OrderStatus");
        orderStatusCmd.setStyle(1);
        orderStatusCmd.setUserId(thisUser);
        orderStatusCmd.setStatus("5");
        String request = gson.toJson(orderStatusCmd);
        JSONObject obj = null;
        try {
            obj = new JSONObject(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                OrderBean orderBean = gson.fromJson(new String(bytes), new TypeToken<OrderBean>() {
                }.getType());
                String result = orderBean.getResult();
                String resultNote = orderBean.getResultNote();
                if (result.equals("0")) {
                    if (orderBean.getOrderlist().size() > 0) {
                        orderList.clear();
                        orderList.addAll(orderBean.getOrderlist());
                        adapter.notifyDataSetChanged();
                    } else {
                        showEmpty(getString(R.string.order_is_empty));
                    }
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


}
