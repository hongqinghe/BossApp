package com.android.app.buystoreapp.managementservice;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseFragment;
import com.android.app.buystoreapp.bean.ProductBean;
import com.android.app.buystoreapp.other.CustomDialog;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.android.app.utils.UmengShareUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.umeng.socialize.media.UMImage;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理服务下架碎片
 * 魏林编辑
 * Created by Administrator on 2016/9/26.
 */
public class ShelvesDownFragment extends BaseFragment implements ManagementAdapter
        .OnClickInterface {
    private View view;
    private ListView lv_fragment_list;
    private List<ProductBean.GpbpsListBean> gpbpsLists = new ArrayList<ProductBean.GpbpsListBean>();
    private int refreshCount;  //剩余刷新次数
    private int remainingRecommended;  //剩余推荐次数
    private int ifRecommend;  //是否可以推荐：0不嫩，1可以
    private int userLevelMark;  //VIP等级
    private String userId;  //
    private ManagementAdapter adapter;
    final String _url = "http://m.bosstuan.cn/fx.html?proid=";
    private UmengShareUtils umengShareUtils;
    private String userNikeName;
    private boolean isfirst = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        userLevelMark = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserLevelMark();
        userId = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
        userNikeName = SharedPreferenceUtils.getCurrentUserInfo(mContext).getNickName();
        isfirst = false;
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_list, container, false);
        }
        return view;
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    protected void load() {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getProductByProStatus");
            obj.put("userId", userId);
            obj.put("proStatus", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                ProductBean productBean = gson.fromJson(new String(bytes), new
                        TypeToken<ProductBean>() {
                        }.getType());
                String result = productBean.getResult();
                if (result.equals("0")) {
                    refreshCount = productBean.getRefreshCount();
                    remainingRecommended = productBean.getRemainingRecommended();
                    ifRecommend = productBean.getIfRecommend();
                    gpbpsLists.clear();
                    gpbpsLists.addAll(productBean.getGpbpsList());
                    if (gpbpsLists.size() == 0) {
                        showEmpty("您还没有发布过商品呦");
                    } else {
                        //adapter.notifyDataSetChanged();
                        adapter = new ManagementAdapter(mContext, gpbpsLists, refreshCount,
                                userLevelMark, remainingRecommended);
                        adapter.setOnClickInterface(ShelvesDownFragment.this);
                        lv_fragment_list.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                ToastUtil.showMessageDefault(mContext, "连接超时！");
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
       /* adapter = new ManagementAdapter(mContext, gpbpsLists,refreshCount);
        adapter.setOnClickInterface(this);
        lv_fragment_list.setAdapter(adapter);*/
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    @Override
    public void doClic(View view, final int i) {
        switch (view.getId()) {

            case R.id.btn_mangement_edit://编辑
                Toast.makeText(getActivity(), "已下架==编辑" + i, Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_mangement_up_or_down://上架
                showUpOkDialog(i);
                break;

            case R.id.btn_mangement_delete://删除
                showDeleteDialog(i);
                break;
            /**
             * 友盟分享
             * */
            case R.id.btn_mangement_share://分享
                UMImage urlImage = new UMImage(getActivity(), BitmapFactory.decodeResource
                        (getActivity().getResources(), R.drawable.ic_launcher));

                umengShareUtils = new UmengShareUtils(getActivity(), gpbpsLists.get(i).getProName
                        (), _url + gpbpsLists.get(i).getProId(), userNikeName, urlImage);
                umengShareUtils.share();
                break;
//            case R.id.iv_management_photo:
//                Intent intent = new Intent(mContext, ShopDetailInfoActivity.class);
////        intent.putExtra("commodity", homeProBean);
//                intent.putExtra("proId",gpbpsLists.get(i).getProId());
//                intent.putExtra("proName",gpbpsLists.get(i).getProName());
//                Log.e("proName",gpbpsLists.get(i).getProName());
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                break;
        }

    }

    private void showUpOkDialog(final int i) {
        CustomDialog.initDialog(mContext);
        CustomDialog.tvTitle.setText("您确定要上架吗？");
        CustomDialog.btnLeft.setText("确定");
        CustomDialog.btnRight.setText("取消");
        CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.dialog.dismiss();
                startWhiteLoadingAnim();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("cmd", "updateProductProStatusById");
                    obj.put("proId", gpbpsLists.get(i).getProId());
                    obj.put("proStatus", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        stopLoadingAnim();
                        hideErrorPageState();
                        try {
                            JSONObject obj = new JSONObject(new String(bytes));
                            String result = obj.getString("result");
                            if (result.equals("0")) {
                                load();
                                ToastUtil.showMessageDefault(mContext, "上架成功！");
                            } else {
                                ToastUtil.showMessageDefault(mContext, "上架失败！");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable
                            throwable) {
                        ToastUtil.showMessageDefault(mContext, "连接超时！");
                        stopLoadingAnim();
                    }
                }, new HttpUtils.RequestNetworkError() {
                    @Override
                    public void networkError() {
                        stopLoadingAnim();
                    }
                });
            }
        });
        CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.dialog.dismiss();
            }
        });
    }

    private void showDeleteDialog(final int i) {
        CustomDialog.initDialog(mContext);
        CustomDialog.tvTitle.setText("您确定要删除该商品吗？");
        CustomDialog.btnLeft.setText("确定");
        CustomDialog.btnRight.setText("取消");
        CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.dialog.dismiss();
                startWhiteLoadingAnim();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("cmd", "updateProductProStatusById");
                    obj.put("proId", gpbpsLists.get(i).getProId());
                    obj.put("proStatus", 2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        stopLoadingAnim();
                        hideErrorPageState();
                        try {
                            JSONObject obj = new JSONObject(new String(bytes));
                            String result = obj.getString("result");
                            if (result.equals("0")) {
                                load();
                                ToastUtil.showMessageDefault(mContext, "删除成功！");
                            } else {
                                ToastUtil.showMessageDefault(mContext, "删除失败！");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable
                            throwable) {
                        ToastUtil.showMessageDefault(mContext, "连接超时！");
                        stopLoadingAnim();
                    }
                }, new HttpUtils.RequestNetworkError() {
                    @Override
                    public void networkError() {
                        stopLoadingAnim();
                    }
                });
            }
        });
        CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.dialog.dismiss();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umengShareUtils.authSSO(requestCode, resultCode, data);
    }
}


