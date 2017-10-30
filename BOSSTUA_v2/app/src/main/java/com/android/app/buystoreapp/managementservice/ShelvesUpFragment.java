package com.android.app.buystoreapp.managementservice;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.ReleaseStepTowActivity;
import com.android.app.buystoreapp.base.BaseFragment;
import com.android.app.buystoreapp.bean.ProductBean;
import com.android.app.buystoreapp.bean.RecommendBackBean;
import com.android.app.buystoreapp.goods.ShopDetailInfoActivity;
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
 * 管理服务上架碎片
 * 魏林编写
 * Created by Administrator on 2016/9/26.
 */
public class ShelvesUpFragment extends BaseFragment implements ManagementAdapter.OnClickInterface {
    private View view;
    private ListView lv_fragment_list;
    private List<ProductBean.GpbpsListBean> gpbpsLists = new ArrayList<ProductBean.GpbpsListBean>();
    private int refreshCount;  //剩余刷新次数
    private int remainingRecommended;  //剩余推荐次数
    private int ifRecommend;  //是否可以推荐：0不嫩，1可以
    private int userLevelMark;  //VIP等级
    private String userId;  //
    private String userNikeName;  //
    private ManagementAdapter adapter;
    final String _url = "http://m.bosstuan.cn/fx.html?proid=";
    private UmengShareUtils umengShareUtils;
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
            obj.put("proStatus", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("根据状态查询列表---", new String(bytes));
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
                        adapter.setOnClickInterface(ShelvesUpFragment.this);
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
            case R.id.btn_mangement_recommend://推荐
                if (userLevelMark > 2) {
                    isRecommend(i);
                } else {
                    ToastUtil.showMessageDefault(mContext, "您还没有该权限，该权限仅帝王VIP用户可用!");
                }
                break;
            case R.id.btn_mangement_edit://编辑
                Intent intent = new Intent(mContext, ReleaseStepTowActivity.class);
                intent.putExtra("proId", gpbpsLists.get(i).getProId());
                startActivity(intent);
                break;
            case R.id.btn_mangement_freshen://刷新
                if (userLevelMark > 0) {
                    freshen(i);
                } else {
                    ToastUtil.showMessageDefault(mContext, "您还没有该权限，该权限仅VIP用户可用!");
                }
                break;

            case R.id.btn_mangement_up_or_down://下架
                showDownDialog(i);
                break;

            case R.id.btn_mangement_share://分享
                UMImage urlImage = new UMImage(getActivity(), BitmapFactory.decodeResource
                        (getActivity().getResources(), R.drawable.ic_launcher));
                String url = _url + gpbpsLists.get(i).getProId();
                String desc = gpbpsLists.get(i).getProName();
                umengShareUtils = new UmengShareUtils(getActivity(), desc, url, userNikeName,
                        urlImage);
                umengShareUtils.share();
                break;
            case R.id.ll_managment://跳转到商品详情
                skipShopDetail(i);
                break;
        }
    }

    private void skipShopDetail(int i) {
        Intent intent = new Intent(mContext, ShopDetailInfoActivity.class);
//        intent.putExtra("commodity", homeProBean);
        intent.putExtra("proId", gpbpsLists.get(i).getProId());
        intent.putExtra("proName", gpbpsLists.get(i).getProName());
        Log.e("proName", gpbpsLists.get(i).getProName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void recommend(int i, int type) {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "updateProductIsRecommendById");
            obj.put("proId", gpbpsLists.get(i).getProId());
            obj.put("isRecommend", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                RecommendBackBean recommendBackBean = gson.fromJson(new String(bytes), new
                        TypeToken<RecommendBackBean>() {
                        }.getType());
                String result = recommendBackBean.getResult();
                String resultNote = recommendBackBean.getResultNote();
                if (result.equals("0")) {
                    load();
                }
                ToastUtil.showMessageDefault(mContext, resultNote);
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

    private void freshen(final int i) {
        CustomDialog.initDialog(mContext);
        CustomDialog.tvTitle.setText("每天3次刷新机会，您还有"+refreshCount+"次机会,是否确定刷新？");
        CustomDialog.btnLeft.setText("确定刷新");
        CustomDialog.btnRight.setText("我点错了");
        CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.dialog.dismiss();
                startWhiteLoadingAnim();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("cmd", "getProductRefreshById");
                    obj.put("proId", gpbpsLists.get(i).getProId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        stopLoadingAnim();
                        hideErrorPageState();
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(new String(bytes));
                            String result = obj.getString("result");
                            String resultNote = obj.getString("resultNote");
                            if (result.equals("0")) {
                                ToastUtil.showMessageDefault(mContext, resultNote);
                                load();
                            } else {
                                ToastUtil.showMessageDefault(mContext, resultNote);
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
        });
        CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.dialog.dismiss();
            }
        });


    }

    private void isRecommend(final int i) {
        if (gpbpsLists.get(i).getIsRecommend() == 0) {
            if (remainingRecommended == 0) {
                ToastUtil.showMessageDefault(mContext, "每月只能推荐2次,您当月没有推荐次数了");
            }else if (ifRecommend == 0) {
                ToastUtil.showMessageDefault(mContext, "您每次可推荐1个资源服务，请先取消已推荐的资源服务");
            } else {
                CustomDialog.initDialog(mContext);
                CustomDialog.tvTitle.setText("每月只能推荐2次,推荐后需1到15个工作日审核,您当月还有"+remainingRecommended+"次推荐机会");
                CustomDialog.btnLeft.setText("确定推荐");
                CustomDialog.btnRight.setText("我点错了");
                CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog.dialog.dismiss();
                        recommend(i, 1);
                    }
                });
                CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog.dialog.dismiss();
                    }
                });
            }
        } else {
            CustomDialog.initDialog(mContext);
            CustomDialog.tvTitle.setText("取消后不可恢复,是否取消当前推荐状态");
            CustomDialog.btnLeft.setText("确定取消");
            CustomDialog.btnRight.setText("我点错了");
            CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomDialog.dialog.dismiss();
                    recommend(i, 0);
                }
            });
            CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomDialog.dialog.dismiss();
                }
            });
        }

    }

    private void showDownDialog(final int i) {
        CustomDialog.initDialog(mContext);
        CustomDialog.tvTitle.setText("您确定要下架吗？");
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
                    obj.put("proStatus", 0);
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
                                ToastUtil.showMessageDefault(mContext, "下架成功！");
                            } else {
                                ToastUtil.showMessageDefault(mContext, "下架失败！");
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
