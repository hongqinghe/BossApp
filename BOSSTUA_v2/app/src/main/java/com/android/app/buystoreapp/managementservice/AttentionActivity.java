package com.android.app.buystoreapp.managementservice;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

public class AttentionActivity extends BaseAct implements AttentionAdapter.OnAttentionListener {


    private ListView lv_attention_list;

    protected Activity mContext;

    private List<AlreadyConcernedBena.GaabListBean> list = new ArrayList<AlreadyConcernedBena.GaabListBean>();

    public static final int HANDLE_LOADMORE = 10;
    public static final int UNATTENTION_OK = 20;


    private AttentionAdapter adapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLE_LOADMORE:
                    if (list.size() == 0) {
                        showEmpty("您还没有关注任何人");
                    }

                    adapter.notifyDataSetChanged();
                    break;
                case UNATTENTION_OK:
                    load();
                    ToastUtil.showMessageDefault(AttentionActivity.this, resultNote);
                    break;
            }
        }
    };
    private Dialog dialog;
    private String resultNote;
    private ImageButton iv_back;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_main);
        initView();
        settitle();
        setListener();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        load();
    }

    private void settitle() {
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string.attention));
         findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttentionActivity.this.finish();
            }
        });
//         iv_back= (ImageButton) findViewById(R.id.iv_back);
//        iv_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AttentionActivity.this.finish();
//            }
//        });
    }

    @Override
    protected void load() {
        super.load();
        requestAlreadyConcerned();
    }

    private void requestAlreadyConcerned() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getAttentionAll");
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.e("查询关注接口提交数据 obj==" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                LogUtils.e("查询关注接口返回数据 bytes==" + new String(bytes));
                try {
                    Gson gson = new Gson();
                    AlreadyConcernedBena alreadyConcernedBena = gson.fromJson(new String(bytes)
                            , new TypeToken<AlreadyConcernedBena>() {
                            }.getType());
                    String result = alreadyConcernedBena.getResult();
                    if ("0".equals(result)) {
                        list.clear();
                        list.addAll(alreadyConcernedBena.getGaabList());
                        LogUtils.e("AttentionActivity list" + list);
                        mHandler.obtainMessage(HANDLE_LOADMORE).sendToTarget();
                    }
                } catch (Exception e) {
                    LogUtils.e("AttentionActivity e" + e);
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

    private void setListener() {
        lv_attention_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentattention = new Intent(AttentionActivity.this, MymoreResourcesandServiceMainActivity.class);
                /** 传递被关注人Id* */
                intentattention.putExtra("proUserId", list.get(position).getAttentionUserid());
                startActivity(intentattention);
            }
        });
    }

    private void initView() {
        lv_attention_list = (ListView) findViewById(R.id.lv_attention_list);
        adapter = new AttentionAdapter(AttentionActivity.this, list);
        adapter.setOnAttentionListener(this);
        lv_attention_list.setAdapter(adapter);
    }


    @Override
    public void doCheck(int i) {
        showDeleteDialog(i);
    }

    private void showDeleteDialog(final int i) {
        View layout = this.getLayoutInflater().inflate(R.layout.managenment_dialog, null);
        TextView tv_set_warning = (TextView) layout.findViewById(R.id.tv_set_warning);
        tv_set_warning.setText("您确定要取消关注?");
        Button btn_true = (Button) layout.findViewById(R.id.btn_true);
        Button btn_false = (Button) layout.findViewById(R.id.btn_false);
        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAttention(i);
//                list.remove(i);
                dialog.dismiss();
                adapter.notifyDataSetChanged();
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

    public void isAttention(int i) {
        startLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "isAttention");
            obj.put("thisUser", SharedPreferenceUtils.getCurrentUserInfo(this).getUserId());
            obj.put("proUser", list.get(i).getAttentionUserid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.e("关注接口提交数据 obj==" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                LogUtils.e("关注接口返回数据 bytes==" + new String(bytes));
                try {
                    String str = new String(bytes);
                    JSONObject obj = new JSONObject(str);
                    String result = (String) obj.get("result");
                    resultNote = (String) obj.get("resultNote");
                    if ("0".equals(result)) {
                        mHandler.obtainMessage(UNATTENTION_OK).sendToTarget();
                    }
                } catch (Exception e) {
                    LogUtils.e("AttentionActivity e" + e);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
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
