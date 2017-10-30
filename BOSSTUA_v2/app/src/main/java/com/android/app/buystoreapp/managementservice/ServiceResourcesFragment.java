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
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.buystoreapp.goods.ShopDetailInfoActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * 服务·资源Fragment
 *
 * 魏林编写
 * */

public class ServiceResourcesFragment extends BaseFragment implements ServiceResourcesAdapter.OnSerivceResourcesClick{


    private View view;
    public static ServiceResourcesAdapter adapter;
    private List<CollectionBean.UcpListBean> dataList=new ArrayList<CollectionBean.UcpListBean>();

    private ListView lv_fragment_list;
    private String userId;
    private double longitude;
    private double latitude;

    public  ServiceResourcesFragment() {

    }


    @SuppressLint("ValidFragment")
    public  ServiceResourcesFragment(List<CollectionBean.UcpListBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (view==null)
        {
            view=inflater.inflate(R.layout.fragment_list,null);
            ViewUtils.inject(getActivity());
            initView();


        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        userId = SharedPreferenceUtils.getCurrentUserInfo(getActivity()).getUserId();
        longitude= CrashApplication.longitude;
        latitude = CrashApplication.latitude;
        load();
    }

    @Override
    public void load() {
        userCollectProduct();
    }

    private void initView() {
        lv_fragment_list= (ListView) view.findViewById(R.id.lv_fragment_list);
        adapter=new ServiceResourcesAdapter(getActivity(),dataList);
        adapter.setOnSerivceResourcesClick(this);
        lv_fragment_list.setAdapter(adapter);
        lv_fragment_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toMoreResources = new Intent(getActivity(),MymoreResourcesandServiceMainActivity.class);
                toMoreResources.putExtra("proUserId",dataList.get(position).getUserId());
                toMoreResources.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(toMoreResources,1000);
            }
        });
    }

    public void userCollectProduct() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "userCollectProduct");
            obj.put("userId", userId);
            obj.put("longitude", longitude);
            obj.put("latitude", latitude);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("collectionActivity", "obj==" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Log.e("collectionActivity", "bytes==" + new String(bytes));
                Gson gson = new Gson();
                CollectionBean collectionBean = gson.fromJson(new String(bytes), new TypeToken<CollectionBean>() {
                }.getType());
                String result = collectionBean.getResult();
                if ("0".equals(result)) {
                    dataList.clear();
                    if (collectionBean.getUcpList() != null) {
                        dataList.addAll(collectionBean.getUcpList());
                    }
                    if (dataList.size()==0){
                        showEmpty("您还没有收藏商品");
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                  stopLoadingAnim();
                ToastUtil.showMessageDefault(getActivity(),getResources().getString(R.string.service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(1);
            }
        });
    }

    @Override
    public void toDetails(int i) {
        Intent intent = new Intent(mContext, ShopDetailInfoActivity.class);
        intent.putExtra("proId", dataList.get(i).getProId());
        intent.putExtra("proName", dataList.get(i).getProName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

