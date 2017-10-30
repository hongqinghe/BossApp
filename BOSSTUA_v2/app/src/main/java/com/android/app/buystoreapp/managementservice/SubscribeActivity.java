package com.android.app.buystoreapp.managementservice;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
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
 * 订阅
 * <p/>
 * 魏林编写
 */
public class SubscribeActivity extends BaseAct implements SubscribeAdapter.OnSubscribeClickInterface {

    private ImageButton iv_back;
    private TextView tv_title;


    private ListView lv_subscribe_list;


    private List<SubscribeBean.GsabListBean> list = new ArrayList<SubscribeBean.GsabListBean>();

    public static final int HANDLE_LOADMORE = 10;
    public static final int DELETE_OK = 20;


    private SubscribeAdapter adapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLE_LOADMORE:
                    if (list.size() == 0) {
                        showEmpty("您还没有订阅");
                    }
                    adapter.notifyDataSetChanged();

                    break;
                case DELETE_OK:
                    ToastUtil.showMessageDefault(SubscribeActivity.this, "取消成功");
                    if (list.size() == 0) {
                        showEmpty("您还没有订阅");
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private Dialog dialog;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_main);
        settitle();
        initView();
        setListener();
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        load();
    }

    @Override
    protected void load() {
        super.load();
        requestSubscribed();
    }


    private void settitle() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("已订阅");
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        tv_title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateSubscribeAll1();
//            }
//        });
    }

    private void setListener() {
        lv_subscribe_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SubscribeActivity.this, SubscriedDetailsActivity.class);
                intent.putExtra("subscribeUserid", list.get(position).getSubscribeUserid());
                startActivity(intent);

            }
        });

    }

    private void initView() {
        lv_subscribe_list = (ListView) findViewById(R.id.lv_subscribe_list);
        adapter = new SubscribeAdapter(SubscribeActivity.this, list);
        adapter.setOnSubscribeClickInterface(this);
        lv_subscribe_list.setAdapter(adapter);
    }


    private void requestSubscribed() {

        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getSubscribeAll");
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.e("SubscribeActivity-----------------------------\n" +
                "requestSubscribed param=" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                LogUtils.e("SubscribeActivity-----------------------------------------------\n " +
                        "requestSubscribed--> result: "
                        + new String(bytes));
                try {
                    Gson gson = new Gson();
                    SubscribeBean subscribeBean = gson.fromJson(new String(bytes)
                            , new TypeToken<SubscribeBean>() {
                            }.getType());
                    String result = subscribeBean.getResult();
                    if ("0".equals(result)) {
                        list.clear();
                        list.addAll(subscribeBean.getGsabList());
                        LogUtils.e("SubscribeActivity-----------------------------------------------\n" +
                                " list" + list);
                        mHandler.obtainMessage(HANDLE_LOADMORE).sendToTarget();
                    }
                } catch (Exception e) {
                    LogUtils.e("SubscribeActivity---------------------------------------------------\n " +
                            "e" + e);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });

    }


    @Override
    public void doClick(int i) {
        showDeleteDialog(i);
    }

    private void showDeleteDialog(final int i) {
        View layout = this.getLayoutInflater().inflate(R.layout.managenment_dialog, null);
        TextView tv_set_warning = (TextView) layout.findViewById(R.id.tv_set_warning);
        tv_set_warning.setText("您确定要取消订阅?");
        Button btn_true = (Button) layout.findViewById(R.id.btn_true);
        Button btn_false = (Button) layout.findViewById(R.id.btn_false);
        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSubscribeAll(i);
                list.remove(i);
                dialog.dismiss();
            }
        });
        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog = new Dialog(this, R.style.Dialog);
        dialog.setContentView(layout);
        dialog.show();
    }

    private void updateSubscribeAll(int i) {
        startLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "updateSubscribeAll");
            obj.put("thisUserId", userId);
            obj.put("subscribeUserId", list.get(i).getSubscribeUserid());
//            obj.put("subscribeUserId", list.get(i).getSubscribeUserid());
            obj.put("subscribeIsOff", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                try {
                    String str = new String(bytes);
                    JSONObject object = new JSONObject(str);
                    String result = (String) object.get("result");
                    Log.e("result", "result=" + result);
                    if ("0".equals(result)) {
                        mHandler.obtainMessage(DELETE_OK).sendToTarget();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });

    }

}

