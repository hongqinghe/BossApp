package com.android.app.buystoreapp.managementservice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.other.VipActivity;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SeeMeActivity extends BaseAct {
    private LinearLayout ll_no_member;
    private LinearLayout ll_visibility;
    private ImageView iv_to_vip;
    private TextView id_custom_title_text;
    private ImageButton iv_back;
    private String userId;
    private List<SeeMeBean.GetPproductToViewBeanListBean> list = new ArrayList<SeeMeBean.GetPproductToViewBeanListBean>();
    private SeeMeAdapter adapter;
    private ListView lv_see_me;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SubscribeActivity.HANDLE_LOADMORE:
                    if (list.size() != 0) {
                        adapter.notifyDataSetChanged();
                    } else {
                        showEmpty("还没有人关注过您");
                    }

                    break;
            }
        }
    };
    private int vip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_me);
        vip = SharedPreferenceUtils.getMarkInfo(mContext).getUserLevelMark();
        initView();

    }

    public void load() {
        getPproductToView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();

        Log.e("vip", vip + "");
        if (vip == 0) {
            return;
        }else {
            iv_to_vip.setVisibility(View.GONE);
            initErrorPage();
            addIncludeLoading(true);
            startWhiteLoadingAnim();
            load();
        }

    }

    private void initView() {
        ((TextView)findViewById(R.id.tv_title)).setText(getResources().getString(R.string.see_me));
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_to_vip = (ImageView) findViewById(R.id.iv_to_vip);
        iv_to_vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vipIntent = new Intent(SeeMeActivity.this, VipActivity.class);
                startActivity(vipIntent);
            }
        });
        lv_see_me = (ListView) findViewById(R.id.lv_see_me);
        adapter = new SeeMeAdapter(this, list);
        lv_see_me.setAdapter(adapter);
        lv_see_me.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SeeMeActivity.this, MymoreResourcesandServiceMainActivity.class);
                intent.putExtra("proUserId", list.get(position).getThisUserId());
                Log.e("SeeMeActivity", "proUserId===" + list.get(position).getThisUserId());
                startActivity(intent);

            }
        });
        ll_no_member = (LinearLayout) findViewById(R.id.ll_no_member);
        ll_visibility = (LinearLayout) findViewById(R.id.ll_visibility);
        if (vip != 0) {
            ll_no_member.setVisibility(View.GONE);
            ll_visibility.setVisibility(View.VISIBLE);
        } else {
            ll_no_member.setVisibility(View.VISIBLE);
            ll_visibility.setVisibility(View.GONE);
        }
    }

    public void getPproductToView() {

        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getPproductToView");
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("SeeMeActivity", "e==" + e.toString());
        }
        Log.e("SeeMeActivity", "obj==" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Log.e("SeeMeActivity", "byts===" + new String(bytes));

                Gson gson = new Gson();
                SeeMeBean seeMeBean = gson.fromJson(new String(bytes), new TypeToken<SeeMeBean>() {
                }.getType());
                String result = seeMeBean.getResult();
                if ("0".equals(result)) {
                    list.clear();
                    list.addAll(seeMeBean.getGetPproductToViewBeanList());
                    mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
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
