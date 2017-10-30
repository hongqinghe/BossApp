package com.android.app.buystoreapp.managementservice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseFragment;
import com.android.app.buystoreapp.bean.CollectNewsBean;
import com.android.app.buystoreapp.bean.UcnbListBean;
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

public class CorporateHeadlinesFragment extends BaseFragment {
    private List<UcnbListBean> dataList = new ArrayList<UcnbListBean>();
    private View view;
    private ListView lv_fragment_list;
    public static CorporateHeadlinesAdapter adapter;
    private String userId;

    public CorporateHeadlinesFragment() {
    }

    @SuppressLint("ValidFragment")
    public CorporateHeadlinesFragment(List<UcnbListBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_list, null);
            initView();
        }
        return view;
    }

    @Override
    public void load() {
        getUserCollectNews();
    }

    @Override
    public void onResume() {
        super.onResume();
        userId = SharedPreferenceUtils.getCurrentUserInfo(getActivity()).getUserId();
        load();
    }

    private void initView() {
//        if (dataList.size() == 0) {
//            showEmpty("您还没有收藏新闻");
//        }
        lv_fragment_list = (ListView) view.findViewById(R.id.lv_fragment_list);
        adapter = new CorporateHeadlinesAdapter(getActivity(), dataList);
        lv_fragment_list.setAdapter(adapter);
        lv_fragment_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), NewInfoActivity.class);
                intent.putExtra("newsId", dataList.get(i).getNewsId());
//                startActivityForResult(intent,1000);
                startActivity(intent);
            }
        });

    }
    /**
     * 查询企业头条列表
     */
    public void getUserCollectNews() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getUserCollectNews");
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("查询企业头条列表提交数据 obj=", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("查询企业头条列表返回数据 bytes=", new String(bytes));
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                CollectNewsBean collectNewsBean = gson.fromJson(new String(bytes), new TypeToken<CollectNewsBean>() {
                }.getType());
                String result = collectNewsBean.getResult();
                if ("0".equals(result)) {
                    dataList.clear();
                    dataList.addAll(collectNewsBean.getUcnbList());
                   if (dataList.size()==0){
                       showEmpty("您还没有收藏新闻咨询");
                   }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(getActivity(), getResources().getString(R.string.service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(1);
            }
        });
    }
}
