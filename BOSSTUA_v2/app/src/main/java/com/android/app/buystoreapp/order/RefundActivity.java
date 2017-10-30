package com.android.app.buystoreapp.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RefundActivity extends BaseAct {

    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    private List<OrderBean.OrderlistBean> orderList = new ArrayList<OrderBean.OrderlistBean>();
    private ListView lv_fragment_list;

    private OrderListAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
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
                    Intent intent = new Intent(RefundActivity.this, RefundInofoActivity.class);
                    intent.putExtra("orderId",orderList.get(msg.arg1).getOrderId());
                    intent.putExtra("orderBean",orderList.get(msg.arg1));
                    intent.putExtra("userStatus",0);
                    startActivity(intent);

                    break;
                case 10:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.fragment_list);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_action_bar);
        ViewUtils.inject(this);
        mTitleText.setText("退款/售后");
        initView();
        addIncludeLoading(true);
        initErrorPage();
    }

    private void initView() {
        lv_fragment_list = (ListView) findViewById(R.id.lv_fragment_list);
        adapter = new OrderListAdapter(RefundActivity.this, orderList, 0, handler);
        lv_fragment_list.setAdapter(adapter);
    }

    @Override
    protected void load() {
        super.load();
        startWhiteLoadingAnim();
        final Gson gson = new Gson();
        String thisUser = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
        OrderStatusCmd orderStatusCmd = new OrderStatusCmd();
        orderStatusCmd.setCmd("OrderStatus");
        orderStatusCmd.setStyle(0);
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

    @Override
    public void onResume() {
        super.onResume();
        load();
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
}
