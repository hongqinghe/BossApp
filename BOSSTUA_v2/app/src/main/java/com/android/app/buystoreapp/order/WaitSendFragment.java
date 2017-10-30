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
 * 代发货
 * Created by 尚帅波 on 2016/9/21.
 */
public class WaitSendFragment extends BaseFragment {
    private View view;
    private List<OrderBean.OrderlistBean> orderList = new ArrayList<OrderBean.OrderlistBean>();
    private ListView lv_fragment_list;

    private OrderListAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 3:
                    CustomDialog.initDialog(getActivity());
                    CustomDialog.tvTitle.setText("是否申请退款？");
                    CustomDialog.btnLeft.setText("确定");
                    CustomDialog.btnRight.setText("再想想");
                    CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog.dialog.dismiss();
                           /* String orderID = orderList.get(msg.arg1).getOrderId();
                            updateOrder(orderID,"2","5");*/
                            Intent intent = new Intent(getActivity(),ApplyRefundAct.class);
                            String orderId = orderList.get(msg.arg1).getOrderId();
                            intent.putExtra("orderId",orderId);
                            intent.putExtra("status",1);
                            getActivity().startActivity(intent);
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
                case 14:
                    String orderId = orderList.get(msg.arg1).getOrderId();
                    int status = orderList.get(msg.arg1).getStatus();
                    Intent intent1 = new Intent(getActivity(), OrderDetailActivity.class);
                    intent1.putExtra("orderId", orderId);
                    intent1.putExtra("status", status);
                    intent1.putExtra("userStatus",userStatus);
                    getActivity().startActivity(intent1);
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    @Override
    protected void load() {
        startWhiteLoadingAnim();
        String thisUser = SharedPreferenceUtils.getCurrentUserInfo(getActivity()).getUserId();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd","OrderStatus");
            obj.put("userId",thisUser);
            obj.put("status","2");
            obj.put("style",userStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("待发货--",obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("--查看订单--",new String(bytes));
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
}
