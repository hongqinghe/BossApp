package com.android.app.buystoreapp.im;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.BossBuyActivity;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.GsonMessageBack;
import com.android.app.buystoreapp.bean.LeaveList;
import com.android.app.buystoreapp.bean.OrderList;
import com.android.app.buystoreapp.bean.SystemList;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.buystoreapp.goods.ShopDetailInfoActivity;
import com.android.app.buystoreapp.order.MyOrderActivity;
import com.android.app.buystoreapp.order.SellerOrderActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.buystoreapp.widget.SwipeListView;
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
 * $desc
 * Created by likaihang on 16/09/13.
 */
public class MessageListActivity extends BaseAct implements View.OnClickListener, AdapterView
        .OnItemClickListener {
    private static final int GET_MESSAGE = 0x01;
    private TextView title;
    private SwipeListView mListView;
    private String thisUser;
    private int state = 0;
    private List<OrderList> order = new ArrayList<OrderList>();
    private List<LeaveList> leave = new ArrayList<LeaveList>();
    private List<SystemList> system = new ArrayList<SystemList>();
    private SwipeAdapter adapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_MESSAGE:
                    if (state == 1) {
                        adapter = new SwipeAdapter(mContext, state, order, mListView
                                .getRightViewWidth(), onRightclick);
                        mListView.setAdapter(adapter);
                    } else if (state == 2) {
                        adapter = new SwipeAdapter(mContext, state, leave, mListView
                                .getRightViewWidth(), onRightclick);
                        mListView.setAdapter(adapter);
                    } else {
                        adapter = new SwipeAdapter(mContext, state, system, mListView
                                .getRightViewWidth(), onRightclick);
                        mListView.setAdapter(adapter);
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private String id;
    private SwipeAdapter.IOnItemRightClickListener onRightclick = new SwipeAdapter
            .IOnItemRightClickListener() {
        @Override
        public void onRightClick(View v, int position) {
            if (state == 1) {
                id = order.get(position).getMessageRecordId();
            } else if (state == 2) {
                id = leave.get(position).getMessageRecordId();
            } else {
//                id = system.get(position).get
            }
            deletMessage(id);
        }
    };

    /**
     * 删除消息
     *
     * @author likaihang
     * creat at @time 16/11/14 15:32
     */
    private void deletMessage(String id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "delMessageRecord");
            obj.put("messageRecordId", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                try {
                    JSONObject json = new JSONObject(new String(bytes));
                    ToastUtil.showMessageDefault(mContext, json.getString("resultNote"));
                    load();
                } catch (JSONException e) {
                    e.printStackTrace();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.message_list_layout);
        state = getIntent().getIntExtra("state", 0);
        thisUser = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
        initview();
        initErrorPage();
        addIncludeLoading(true);
        load();
    }

    @Override
    protected void load() {
        startWhiteLoadingAnim();
        getMessageList(state);
    }

    public void initview() {
        String tit = getIntent().getExtras().getString("title");
        title = (TextView) findViewById(R.id.tv_comment_title);
        title.setText(tit);
        findViewById(R.id.ib_back).setOnClickListener(this);
        mListView = (SwipeListView) findViewById(R.id.lv_message_list);
        adapter = new SwipeAdapter(this, state, order, mListView.getRightViewWidth(), onRightclick);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }

    /**
     * 获取消息列表
     *
     * @author likaihang
     * creat at @time 16/11/14 12:57
     */
    private void getMessageList(int s) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "selMessageRecord");
            obj.put("userId", thisUser);
            obj.put("state", s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                Log.d("获取消息列表--", new String(bytes));
                Gson gson = new Gson();
                GsonMessageBack gsonMessageBack = gson.fromJson(new String(bytes),
                        new TypeToken<GsonMessageBack>() {
                        }.getType());
                if ("0".equals(gsonMessageBack.getResult())) {
                    if (state == 1) {
                        order.clear();
                        order.addAll(gsonMessageBack.getOrderList());
                    } else if (state == 2) {
                        leave.clear();
                        leave.addAll(gsonMessageBack.getLeaveList());
                    } else {
                        system.clear();
                        system.addAll(gsonMessageBack.getSystemList());
                    }
                    mHandler.obtainMessage(GET_MESSAGE).sendToTarget();
                } else {
                    ToastUtil.showMessageDefault(mContext, gsonMessageBack.getResultNote());
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

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ib_back) {
            if (CrashApplication.getActivityByName("com.android.app.buystoreapp.BossBuyActivity")
                    == null) {
                startActivity(new Intent(this, BossBuyActivity.class));
            } else {
                finish();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (state == 1) {
            if (order.get(position).getOrderState().equals("0")) {
                Intent intent = new Intent(this, MyOrderActivity.class);
                intent.putExtra("index", 0);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, SellerOrderActivity.class);
                intent.putExtra("index", 0);
                startActivity(intent);
            }
        } else if (state == 2) {
            Intent intent = new Intent(this, ShopDetailInfoActivity.class);
            intent.putExtra("proName", leave.get(position).getProName());
            intent.putExtra("proId", leave.get(position).getProId());
            startActivity(intent);
        } else {
            //系统消息
            if (system.get(position).getOrderState() == 2) {
                //跳转webView

            } else if (system.get(position).getOrderState() == 3) {

            }
        }
    }
}
