package com.android.app.buystoreapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.app.buystore.utils.BadgeView;
import com.android.app.buystore.utils.CircleImageView;
import com.android.app.buystore.utils.DataCleanManager;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.base.BaseFragment;
import com.android.app.buystoreapp.base.UpdateApkService;
import com.android.app.buystoreapp.bean.GsonBackUserInfo;
import com.android.app.buystoreapp.bean.MarksBean;
import com.android.app.buystoreapp.bean.UserInfoBean;
import com.android.app.buystoreapp.bean.VersionUpdateBack;
import com.android.app.buystoreapp.goods.ShoppingCartActivity;
import com.android.app.buystoreapp.managementservice.AttentionActivity;
import com.android.app.buystoreapp.managementservice.BondActivity;
import com.android.app.buystoreapp.managementservice.CollectionActivity;
import com.android.app.buystoreapp.managementservice.ManagementServiceActivity;
import com.android.app.buystoreapp.managementservice.MyRapidlyChatSetUpActivity;
import com.android.app.buystoreapp.managementservice.PersonalDataActivity;
import com.android.app.buystoreapp.managementservice.PersonalSettingsActivity;
import com.android.app.buystoreapp.managementservice.SeeMeActivity;
import com.android.app.buystoreapp.managementservice.SesameCreditActivity;
import com.android.app.buystoreapp.managementservice.SubscribeActivity;
import com.android.app.buystoreapp.order.MyOrderActivity;
import com.android.app.buystoreapp.order.RefundActivity;
import com.android.app.buystoreapp.order.SellerOrderActivity;
import com.android.app.buystoreapp.other.CompanyCertifiActivity;
import com.android.app.buystoreapp.other.ProfessionCertifiActivity;
import com.android.app.buystoreapp.other.RealCertifiActivity;
import com.android.app.buystoreapp.other.VipActivity;
import com.android.app.buystoreapp.setting.AboutUsActivity;
import com.android.app.buystoreapp.setting.AppFanKuiActivity;
import com.android.app.buystoreapp.setting.AppSupportHelpActivity;
import com.android.app.buystoreapp.setting.LoginActivity;
import com.android.app.buystoreapp.wallet.MyWalletActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.buystoreapp.widget.MyGridLayout;
import com.android.app.buystoreapp.widget.MyGridLayout.OnItemClickListener;
import com.android.app.utils.Config;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TabPersonal extends BaseFragment implements OnClickListener {
    private static final int GET_ORDER_COUNT = 0xe0;
    private String[] titleArray;
    private TypedArray imageArray;

    private UserInfoBean mUserInfo = null;
    private String userIcon = "";

    public static final int FLAG_REGISTER = 2;
    public static final int FLAG_LOGIN = 3;
    public static final int FLAG_MODIFY = 4;

    private boolean isLogin = false;
    private String mCurrentUserName;

    private OnClickListener personalBacListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),
                    PersonalDataActivity.class);
            startActivity(intent);
        }
    };
    private MyGridLayout gi_1;
    private String[] titleOrder;
    private TypedArray imageOrder;
    private String[] titeFollow;
    private TypedArray imageFollow;
    private String[] titleService;
    private TypedArray imageService;
    private String[] titleManger;
    private TypedArray imageManger;
    private String[] titleOther;
    private TypedArray imageOther;
    private MyGridLayout ml_order;
    private MyGridLayout ml_follow;
    private MyGridLayout ml_service;
    private MyGridLayout ml_manger;
    private MyGridLayout ml_other;
    private ImageView edit;
    private CircleImageView personal_bac;


    private Dialog showDeleteFolderFileDialog, showDeleteingDialog, showDeleteOkDialog;
    private LinearLayout mLogin;
    private LinearLayout ll_1;
    private LinearLayout ll_2;
    private String userId;
    private int forTheGoods;//待收货
    private int forThePayment;//代付款
    private int refundOrders;//退款订单
    private int sendTheGoods;//代发货
    private int toEvaluate;//待评价
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_ORDER_COUNT:
                    upOrderInfo();
                    break;
            }
        }
    };
    private TextView tvname;
    private TextView tvprofession;
    private ImageView ivlevel;
    private TextView ivmoney;
    private ImageView ivverified;
    private ImageView ivcredit;
    private ImageView ivcompany;
    private ImageView ivprofession;
    private View parent;
    private Context context;
    private String nickName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        context = inflater.getContext();
        parent = inflater.inflate(R.layout.main_tab_personal, container,
                false);
        titleArray = getResources().getStringArray(R.array.personal_setting);
        imageArray = getResources().obtainTypedArray(
                R.array.personal_setting_icons);

        titleOrder = getResources().getStringArray(R.array.personal_setting_order);
        imageOrder = getResources().obtainTypedArray(R.array.personal_order_icons);

        titeFollow = getResources().getStringArray(R.array.personal_setting_follow);
        imageFollow = getResources().obtainTypedArray(R.array.personal_follow_icons);

        titleService = getResources().getStringArray(R.array.personal_service);
        imageService = getResources().obtainTypedArray(R.array.personal_service_icons);

        titleManger = getResources().getStringArray(R.array.personal_manger);
        imageManger = getResources().obtainTypedArray(R.array.personal_manger_icons);

        titleOther = getResources().getStringArray(R.array.personal_other);
        imageOther = getResources().obtainTypedArray(R.array.personal_other_icons);

        edit = (ImageView) parent.findViewById(R.id.iv_person_edit);
        edit.setOnClickListener(this);
        personal_bac = (CircleImageView) parent.findViewById(R.id.iv_person_head);

        tvname = (TextView) parent.findViewById(R.id.tv_user_name);
        tvprofession = (TextView) parent.findViewById(R.id.tv_user_profession);
        ivlevel = (ImageView) parent.findViewById(R.id.iv_user_level);
        ivmoney = (TextView) parent.findViewById(R.id.tv_user_money);
        ivverified = (ImageView) parent.findViewById(R.id.iv_info_verified);
        ivcredit = (ImageView) parent.findViewById(R.id.iv_user_info_credit);
        ivcompany = (ImageView) parent.findViewById(R.id.iv_user_info_company);
        ivprofession = (ImageView) parent.findViewById(R.id.iv_user_info_profession);

        mLogin = (LinearLayout) parent.findViewById(R.id.ll_login_now);
        mLogin.setOnClickListener(this);
        ll_1 = (LinearLayout) parent.findViewById(R.id.ll_personal_1);
        ll_2 = (LinearLayout) parent.findViewById(R.id.ll_personal_2);
        init(parent);
        return parent;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startWhiteLoadingAnim();
    }

    @Override
    protected void load() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mUserInfo = SharedPreferenceUtils.getCurrentUserInfo(getActivity());
        isLogin = mUserInfo.isLogin();
        userId = mUserInfo.getUserId();
        nickName = mUserInfo.getNickName();
        saveLevelMark();
        if (isLogin) {
            startWhiteLoadingAnim();
            getOrderNum();
            mCurrentUserName = mUserInfo.getUserName();
            if (!"".equals(SharedPreferenceUtils.getNickName(context))) {
                tvname.setText(SharedPreferenceUtils.getNickName(context) + "");
            } else {
                tvname.setText(nickName + "");
            }
            ivmoney.setText(mUserInfo.getUserTreasure());
//            if ("".equals(SharedPreferenceUtils.getUserPosition(context))) {
//                tvprofession.setText("");
//            } else {
//                tvprofession.setText("・" + SharedPreferenceUtils.getUserPosition(context));
//            }
            if (mUserInfo.getUserLevelMark() == 1) {
                ivlevel.setImageResource(R.drawable.vip_bojin);
            } else if (mUserInfo.getUserLevelMark() == 2) {
                ivlevel.setImageResource(R.drawable.vip_zuanshi);
            } else if (mUserInfo.getUserLevelMark() == 3) {
                ivlevel.setImageResource(R.drawable.vip_diwang);
            } else {
                ivlevel.setVisibility(View.GONE);
            }
            if (mUserInfo.getBindingMark1() == 1) {
                ivverified.setVisibility(View.VISIBLE);
                ivverified.setImageResource(R.drawable.iv_user_info_verified);
            } else {
                ivverified.setVisibility(View.GONE);
            }
            if (mUserInfo.getBindingMark2() == 1) {
                ivcredit.setVisibility(View.VISIBLE);
                ivcredit.setImageResource(R.drawable.iv_user_info_credit_light);
            } else {
                ivcredit.setVisibility(View.GONE);
            }
            if (mUserInfo.getBindingMark3() == 1) {
                ivcompany.setVisibility(View.VISIBLE);
                ivcompany.setImageResource(R.drawable.iv_user_info_company_light);
            } else {
                ivcompany.setVisibility(View.GONE);
            }
            if (mUserInfo.getBindingMark4() == 1) {
                ivprofession.setVisibility(View.VISIBLE);
                ivprofession.setImageResource(R.drawable.iv_user_info_profession_light);
            } else {
                ivprofession.setVisibility(View.GONE);
            }

            if (!"".equals(SharedPreferenceUtils.getHeadicon(context))) {
                userIcon = SharedPreferenceUtils.getHeadicon(context);
            } else {
                userIcon = mUserInfo.getUserIcon();
            }

            Picasso.with(getActivity()).load(userIcon)
                    //.resize(80, 80)
                    .placeholder(R.drawable
                            .ic_person_head_dafult).into(personal_bac);
            mLogin.setVisibility(View.GONE);
            ll_1.setVisibility(View.VISIBLE);
            ll_2.setVisibility(View.VISIBLE);
        } else {
            stopLoadingAnim();
            mLogin.setVisibility(View.VISIBLE);
            ll_1.setVisibility(View.GONE);
            ll_2.setVisibility(View.GONE);
            personal_bac.setOnClickListener(mLoginClickListener);
            Picasso.with(getActivity()).load(R.drawable
                    .ic_person_head_dafult)
                    .resize(80, 80)
                    .into(personal_bac);
        }
    }

    /**
     * 查询并保存认证信息和vip等级
     */
    private void saveLevelMark() {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getMarks");
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                MarksBean marksBean = gson.fromJson(new String(bytes), new
                        TypeToken<MarksBean>() {
                        }.getType());
                String result = marksBean.getResult();
                MarksBean.BeanBean bean = marksBean.getBean();
                if ("0".equals(result)) {
                    SharedPreferenceUtils.saveMarkInfo(bean, mContext);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable
                    throwable) {
                stopLoadingAnim();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
            }
        });
    }

    private void getOrderNum() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getOrderCountByStatus");
            obj.put("thisUserId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(getActivity(), obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("-getOrderCountByStatus-", new String(bytes));
                stopLoadingAnim();
                Gson gson = new Gson();
                GsonBackUserInfo gsonBackUserInfo = gson.fromJson(new String(bytes),
                        new TypeToken<GsonBackUserInfo>() {
                        }.getType());
                String result = gsonBackUserInfo.getResult();
                String resultNote = gsonBackUserInfo.getResultNote();
                if ("0".equals(result)) {
                    GsonBackUserInfo.OrderCount count = gsonBackUserInfo.getOrderCount();
                    forTheGoods = count.getForTheGoods();
                    forThePayment = count.getForThePayment();
                    refundOrders = count.getRefundOrders();
                    sendTheGoods = count.getSendTheGoods();
                    toEvaluate = count.getToEvaluate();
                    mHandler.obtainMessage(GET_ORDER_COUNT).sendToTarget();
                } else {
                    ToastUtil.showMessageDefault(getActivity(), resultNote);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(getContext(), getString(R.string.network_is_bad));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(getContext(), getString(R.string.network_is_bad));
            }
        });
    }

    public void init(View view) {
        gi_1 = (MyGridLayout) view.findViewById(R.id.gl_1);
        gi_1.setGridAdapter(new MyGridLayout.GridAdatper() {
            @Override
            public View getView(int index) {
                View v = LayoutInflater.from(getActivity()).inflate(R.layout
                        .person_mygrid_item_layout, null);
                ImageView iv = (ImageView) v.findViewById(R.id.iv_grid_item_pric);
                TextView tv = (TextView) v.findViewById(R.id.tv_grid_item_name);
                iv.setImageResource(imageArray.getResourceId(index, 0));
                tv.setText(titleArray[index]);
                return v;
            }

            @Override
            public int getCount() {
                return titleArray.length;
            }
        });
        gi_1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View v, int index) {
                if (isLogin) {
                    switch (index) {

                        case 0://我的钱包
                            Intent intent0 = new Intent(getActivity(), MyWalletActivity.class);
                            startActivity(intent0);
                            break;
                        case 1://boss券
                            Intent intent = new Intent(getActivity(), MyBossTicketDetail.class);
                            startActivity(intent);
                            break;
                        case 2://购物车
                            Intent shopCarIntent = new Intent(getActivity(),
                                    ShoppingCartActivity.class);
                            shopCarIntent.putExtra("userName", mCurrentUserName);
                            startActivity(shopCarIntent);
                            break;
                    }
                } else {
                    ToastUtil.showMessageDefault(getActivity(),
                            getString(R.string.personal_no_login_toast));
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    loginIntent.putExtra("type", FLAG_LOGIN);
                    startActivity(loginIntent);
                }
            }
        });
        ml_order = (MyGridLayout) view.findViewById(R.id.ml_order);

        view.findViewById(R.id.ll_my_order).setOnClickListener(this);
        ml_order.setGridAdapter(new MyGridLayout.GridAdatper() {
            @Override
            public View getView(int index) {
                View v = LayoutInflater.from(getActivity()).inflate(R.layout
                        .person_mygrid_item_message_layout, null);
                ImageView iv = (ImageView) v.findViewById(R.id.iv_grid_item_pric);
                TextView tv = (TextView) v.findViewById(R.id.tv_grid_item_name);
                tv.setTextSize(12);
                iv.setImageResource(imageOrder.getResourceId(index, 0));
                tv.setText(titleOrder[index]);
                return v;
            }

            @Override
            public int getCount() {
                return titleOrder.length;
            }
        });
        ml_order.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                if (isLogin) {
                    //跳到订单界面
                    Intent intent = new Intent(getActivity(), MyOrderActivity.class);
                    switch (index) {
                        case 0:
                            intent.putExtra("index", 1);
                            startActivity(intent);
                            break;
                        case 1:
                            intent.putExtra("index", 2);
                            startActivity(intent);
                            break;
                        case 2:
                            intent.putExtra("index", 3);
                            startActivity(intent);
                            break;
                        case 3:
                            intent.putExtra("index", 4);
                            startActivity(intent);
                            break;
                        case 4://跳到退款界面
                            Intent refundIntent = new Intent(getActivity(), RefundActivity.class);
                            startActivity(refundIntent);
                            break;

                    }
                } else {
                    ToastUtil.showMessageDefault(getActivity(),
                            getString(R.string.personal_no_login_toast));
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    loginIntent.putExtra("type", FLAG_LOGIN);
                    startActivity(loginIntent);
                }
            }
        });
        ml_follow = (MyGridLayout) view.findViewById(R.id.ml_follow);
        ml_follow.setGridAdapter(new MyGridLayout.GridAdatper() {
            @Override
            public View getView(int index) {
                View v = LayoutInflater.from(getActivity()).inflate(R.layout
                        .person_mygrid_item_layout, null);
                ImageView iv = (ImageView) v.findViewById(R.id.iv_grid_item_pric);
                TextView tv = (TextView) v.findViewById(R.id.tv_grid_item_name);
                tv.setTextSize(12);
                iv.setImageResource(imageFollow.getResourceId(index, 0));
                tv.setText(titeFollow[index]);
                return v;
            }

            @Override
            public int getCount() {
                return titeFollow.length;
            }
        });
        ml_follow.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                if (isLogin) {
                    switch (index) {
                        case 0://关注
                            Intent intentattention = new Intent(getActivity(), AttentionActivity
                                    .class);
                            startActivity(intentattention);
                            break;
                        case 1://收藏
                            Intent intentCollectionMainActivity = new Intent(getActivity(),
                                    CollectionActivity.class);
                            startActivity(intentCollectionMainActivity);
                            break;
                        case 2://订阅
                            Intent intent2 = new Intent(getActivity(), SubscribeActivity.class);
                            startActivity(intent2);
                            break;
                    }
                } else {
                    ToastUtil.showMessageDefault(getActivity(),
                            getString(R.string.personal_no_login_toast));
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    loginIntent.putExtra("type", FLAG_LOGIN);
                    startActivity(loginIntent);
                }
            }
        });
        ml_service = (MyGridLayout) view.findViewById(R.id.ml_service);
        ml_service.setGridAdapter(new MyGridLayout.GridAdatper() {
            @Override
            public View getView(int index) {
                View v = LayoutInflater.from(getActivity()).inflate(R.layout
                        .person_mygrid_item_layout, null);
                ImageView iv = (ImageView) v.findViewById(R.id.iv_grid_item_pric);
                TextView tv = (TextView) v.findViewById(R.id.tv_grid_item_name);
                tv.setTextSize(12);
                iv.setImageResource(imageService.getResourceId(index, 0));
                tv.setText(titleService[index]);
                return v;
            }

            @Override
            public int getCount() {
                return titleService.length;
            }
        });
        /**
         * 我的服务这点击事件
         *
         * */
        ml_service.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                if (isLogin) {
                    switch (index) {
                        case 0://管理服务
                            Intent intentmanagement = new Intent(getActivity(),
                                    ManagementServiceActivity.class);
                            startActivity(intentmanagement);
                            break;
                        case 1://已售订单
                            Intent intent = new Intent(getActivity(), SellerOrderActivity.class);
                            intent.putExtra("index", 0);
                            startActivity(intent);
                            break;
                        case 2://vip服务
                            Intent vipIntent = new Intent(getActivity(), VipActivity.class);
                            startActivity(vipIntent);
                            break;
                        case 3://急速聊
                            Intent intentrapidlychat = new Intent(getActivity(),
                                    MyRapidlyChatSetUpActivity.class);
                            startActivity(intentrapidlychat);
                            break;
                        case 4://谁看过我
                            Intent intentEv = new Intent(getActivity(), SeeMeActivity.class);
                            startActivity(intentEv);

                            break;

                    }
                } else {
                    ToastUtil.showMessageDefault(getActivity(),
                            getString(R.string.personal_no_login_toast));
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    loginIntent.putExtra("type", FLAG_LOGIN);
                    startActivity(loginIntent);
                }
            }
        });
        ml_manger = (MyGridLayout) view.findViewById(R.id.ml_manger);
        ml_manger.setGridAdapter(new MyGridLayout.GridAdatper() {
            @Override
            public View getView(int index) {
                View v = LayoutInflater.from(getActivity()).inflate(R.layout
                        .person_mygrid_item_layout, null);
                ImageView iv = (ImageView) v.findViewById(R.id.iv_grid_item_pric);
                TextView tv = (TextView) v.findViewById(R.id.tv_grid_item_name);
                tv.setTextSize(12);
                iv.setImageResource(imageManger.getResourceId(index, 0));
                tv.setText(titleManger[index]);
                return v;
            }

            @Override
            public int getCount() {
                return titleManger.length;
            }
        });

        ml_manger.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                if (isLogin) {
                    switch (index) {
                        case 0://实名认证
                            Intent intent01 = new Intent(getActivity(), RealCertifiActivity.class);
                            startActivity(intent01);
                            break;
                        case 1:
                            Intent intent02 = new Intent(getActivity(), CompanyCertifiActivity
                                    .class);
                            startActivity(intent02);
                            break;
                        case 2:
                            Intent intent03 = new Intent(getActivity(), ProfessionCertifiActivity
                                    .class);
                            startActivity(intent03);
                            break;
                        case 3:
                            Intent ratedIntent = new Intent(getActivity(), SesameCreditActivity
                                    .class);
                            startActivity(ratedIntent);
                            break;
                        case 4:
                            Intent i = new Intent(getActivity(), BondActivity.class);
                            startActivity(i);
                            break;
                    }
                } else {
                    ToastUtil.showMessageDefault(getActivity(),
                            getString(R.string.personal_no_login_toast));
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    loginIntent.putExtra("type", FLAG_LOGIN);
                    startActivity(loginIntent);
                }
            }
        });

        ml_other = (MyGridLayout) view.findViewById(R.id.ml_other);
        ml_other.setGridAdapter(new MyGridLayout.GridAdatper() {
            @Override
            public View getView(int index) {
                View v = LayoutInflater.from(getActivity()).inflate(R.layout
                        .person_mygrid_item_layout, null);
                ImageView iv = (ImageView) v.findViewById(R.id.iv_grid_item_pric);
                TextView tv = (TextView) v.findViewById(R.id.tv_grid_item_name);
                tv.setTextSize(12);
                iv.setImageResource(imageOther.getResourceId(index, 0));
                tv.setText(titleOther[index]);
                return v;
            }

            @Override
            public int getCount() {
                return titleOther.length;
            }
        });
        ml_other.setOnItemClickListener(new OnItemClickListener() {


            @Override
            public void onItemClick(View v, int index) {
                switch (index) {
                    case 0://建议反馈
                        Intent fankuiIntent = new Intent(getActivity(),
                                AppFanKuiActivity.class);
                        startActivity(fankuiIntent);
                        break;
                    case 1://联系客服
                        Intent support = new Intent(getActivity(),
                                AppSupportHelpActivity.class);
                        startActivity(support);
                        break;
                    case 2:
                        versionUpdate();
                        break;
                    case 3://邀请好友
                        if (isLogin) {
                            //跳转
                            Intent inviteIntent = new Intent(getActivity(), InviteFriendActivity
                                    .class);
                            startActivity(inviteIntent);
                        } else {
                            ToastUtil.showMessageDefault(getActivity(),
                                    getString(R.string.personal_no_login_toast));
                            Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                            loginIntent.putExtra("type", FLAG_LOGIN);
                            startActivity(loginIntent);
                        }
                        break;
                    case 4:
                        try {
                            showDeleteFolderFileDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:// 关于我们
                        Intent aboutusIntent = new Intent(getActivity(),
                                AboutUsActivity.class);
                        startActivity(aboutusIntent);
                        break;
                }
            }

            /**
             * 检测版本更新
             */

            private void versionUpdate() {
                startWhiteLoadingAnim();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("cmd", "appVersion");
                    obj.put("type", 2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpUtils.post(getContext(), obj, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        Log.d("更新返回数据-", new String(bytes));
                        stopLoadingAnim();
                        Gson gson = new Gson();
                        VersionUpdateBack versionUpdateBack = gson.fromJson(new String(bytes),
                                new TypeToken<VersionUpdateBack>() {
                        }.getType());
                        if ("0".equals(versionUpdateBack.getResult())) {
                            if (versionUpdateBack.getAppVersion().getVersionCode() > Config
                                    .getVersionCode(getContext())) {
                                /*String url = versionUpdateBack.getAppVersion().getDownurl();
                                if (!TextUtils.isEmpty(url)) {
                                    Intent upload = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(upload);
                                }*/
                                Intent intent = new Intent(mContext, UpdateApkService.class);
                                mContext.startService(intent);
                            } else {
                                ToastUtil.showMessageDefault(context, "您当前使用已是最新版本！");
                                return;
                            }
                        }

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable
                            throwable) {
                        stopLoadingAnim();
                    }
                }, new HttpUtils.RequestNetworkError() {
                    @Override
                    public void networkError() {
                        stopLoadingAnim();
                    }
                });
            }

            private void showDeleteFolderFileDialog() throws Exception {
                View layout = getActivity().getLayoutInflater().inflate(R.layout
                        .managenment_dialog, null);
                TextView tv_set_warning = (TextView) layout.findViewById(R.id.tv_set_warning);
                long size = DataCleanManager.getCacheSize(getActivity().getCacheDir());
                tv_set_warning.setText("确认" + String.format(getString(R.string
                        .app_setting_clean_cache), Formatter.formatFileSize(getActivity(), size))
                        + "?");
                Button btn_true = (Button) layout.findViewById(R.id.btn_true);
                btn_true.setText("取消");
                Button btn_false = (Button) layout.findViewById(R.id.btn_false);
                btn_false.setText("确认");
                btn_true.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDeleteFolderFileDialog.dismiss();

                    }
                });
                btn_false.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDeleteFolderFileDialog.dismiss();
                        showDeleteingDialog();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    DataCleanManager.deleteFolderFile(getActivity().getCacheDir()
                                            .getAbsolutePath(), true);
                                    Thread.sleep(500);
                                    showDeleteingDialog.dismiss();
                                    long size = DataCleanManager.getCacheSize(getActivity()
                                            .getCacheDir());
                                    if (size == 0) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                showDeleteOkDialog();
                                            }
                                        });
                                        Thread.sleep(500);
                                        showDeleteOkDialog.dismiss();
                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
//                                    Log.e("lin",e);
                                }

                            }

                        }).start();
                    }
                });
                showDeleteFolderFileDialog = new Dialog(getActivity(), R.style.Dialog);
                showDeleteFolderFileDialog.setContentView(layout);
                showDeleteFolderFileDialog.show();
            }


            private void showDeleteingDialog() {
                View layout = getActivity().getLayoutInflater().inflate(R.layout.dialog_up_ok,
                        null);
                ImageView iv_set_image = (ImageView) layout.findViewById(R.id.iv_set_image);
                iv_set_image.setBackgroundResource(R.drawable.ic_delete_cache);
                TextView tv_set_text = (TextView) layout.findViewById(R.id.tv_set_text);
                tv_set_text.setText("正在清除缓存...");
                showDeleteingDialog = new Dialog(getActivity(), R.style.LoadDialog);
                showDeleteingDialog.setContentView(layout);
                showDeleteingDialog.setCanceledOnTouchOutside(false);
                showDeleteingDialog.show();
            }
        });

    }

    private void showDeleteOkDialog() {
        View layout = getActivity().getLayoutInflater().inflate(R.layout.dialog_up_ok, null);
        ImageView iv_set_image = (ImageView) layout.findViewById(R.id.iv_set_image);
        iv_set_image.setBackgroundResource(R.drawable.ic_tick);
        TextView tv_set_text = (TextView) layout.findViewById(R.id.tv_set_text);
        tv_set_text.setText("清除缓存成功");
        showDeleteOkDialog = new Dialog(getActivity(), R.style.LoadDialog);
        showDeleteOkDialog.setContentView(layout);
        showDeleteOkDialog.setCanceledOnTouchOutside(false);
        showDeleteOkDialog.show();

    }

    private ArrayList<HashMap<String, Object>> initData() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < titleArray.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("title", titleArray[i]);
            map.put("imageUrl", imageArray.getResourceId(i, 0));
            data.add(map);
        }
        return data;
    }

    private void upOrderInfo() {
        if (ml_order != null) {
            ml_order.removeAllViews();
        }
        //登录后初始化订单数信息
        ml_order.setGridAdapter(new MyGridLayout.GridAdatper() {
            @Override
            public View getView(int index) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View v = inflater.inflate(R.layout.person_mygrid_item_message_layout, null);
                ImageView iv = (ImageView) v.findViewById(R.id.iv_grid_item_pric);
                TextView tv = (TextView) v.findViewById(R.id.tv_grid_item_name);
                tv.setTextSize(12);
                iv.setImageResource(imageOrder.getResourceId(index, 0));
                tv.setText(titleOrder[index]);
                switch (index) {
                    case 0:
                        if (forThePayment != 0) {
                            //创建一个BadgeView对象，view为你需要显示提醒的控件
                            BadgeView badge1 = new BadgeView(context, iv);
                            badge1.setText(String.valueOf(forThePayment)); //显示类容
                            badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT); //显示的位置
                            badge1.setTextColor(Color.WHITE);  //文本颜色
                            badge1.setBadgeBackgroundColor(Color.RED);  //背景颜色
                            badge1.setTextSize(10); //文本大小
                            badge1.setBadgeMargin(0, 0); //水平和竖直方向的间距
                            badge1.show();
                        }
                        break;
                    case 1:
                        if (sendTheGoods != 0) {
                            BadgeView badge2 = new BadgeView(context, iv);
                            //创建一个BadgeView对象，view为你需要显示提醒的控件
                            badge2.setText(String.valueOf(sendTheGoods)); //显示类容
                            badge2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT); //显示的位置
                            badge2.setTextColor(Color.WHITE);  //文本颜色
                            badge2.setBadgeBackgroundColor(Color.RED);  //背景颜色
                            badge2.setTextSize(10); //文本大小
                            badge2.setBadgeMargin(0, 0); //水平和竖直方向的间距
                            badge2.show();
                        }
                        break;
                    case 2:
                        if (forTheGoods != 0) {
                            BadgeView badge2 = new BadgeView(context, iv);
                            //创建一个BadgeView对象，view为你需要显示提醒的控件
                            badge2.setText(String.valueOf(forTheGoods)); //显示类容
                            badge2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT); //显示的位置
                            badge2.setTextColor(Color.WHITE);  //文本颜色
                            badge2.setBadgeBackgroundColor(Color.RED);  //背景颜色
                            badge2.setTextSize(10); //文本大小
                            badge2.setBadgeMargin(0, 0); //水平和竖直方向的间距
                            badge2.show();
                        }
                        break;
                    case 3:
                        if (toEvaluate != 0) {
                            BadgeView badge2 = new BadgeView(context, iv);
                            //创建一个BadgeView对象，view为你需要显示提醒的控件
                            badge2.setText(String.valueOf(toEvaluate)); //显示类容
                            badge2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT); //显示的位置
                            badge2.setTextColor(Color.WHITE);  //文本颜色
                            badge2.setBadgeBackgroundColor(Color.RED);  //背景颜色
                            badge2.setTextSize(10); //文本大小
                            badge2.setBadgeMargin(0, 0); //水平和竖直方向的间距
                            badge2.show();
                        }
                        break;
                    case 4:
                        if (refundOrders != 0) {
                            BadgeView badge2 = new BadgeView(context, iv);
                            //创建一个BadgeView对象，view为你需要显示提醒的控件
                            badge2.setText(String.valueOf(refundOrders)); //显示类容
                            badge2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT); //显示的位置
                            badge2.setTextColor(Color.WHITE);  //文本颜色
                            badge2.setBadgeBackgroundColor(Color.RED);  //背景颜色
                            badge2.setTextSize(10); //文本大小
                            badge2.setBadgeMargin(0, 0); //水平和竖直方向的间距
                            badge2.show();
                        }
                        break;
                }
                return v;
            }

            @Override
            public int getCount() {
                return titleOrder.length;
            }
        });
        ml_order.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                if (isLogin) {
                    //跳到订单界面
                    Intent intent = new Intent(getActivity(), MyOrderActivity.class);
                    switch (index) {
                        case 0:
                            intent.putExtra("index", 1);
                            startActivity(intent);
                            break;
                        case 1:
                            intent.putExtra("index", 2);
                            startActivity(intent);
                            break;
                        case 2:
                            intent.putExtra("index", 3);
                            startActivity(intent);
                            break;
                        case 3:
                            intent.putExtra("index", 4);
                            startActivity(intent);
                            break;
                        case 4://跳到退款界面
                            Intent refundIntent = new Intent(getActivity(), RefundActivity.class);
                            startActivity(refundIntent);
                            break;

                    }
                } else {
                    ToastUtil.showMessageDefault(getActivity(),
                            getString(R.string.personal_no_login_toast));
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    loginIntent.putExtra("type", FLAG_LOGIN);
                    startActivity(loginIntent);
                }
            }
        });
        personal_bac.setOnClickListener(personalBacListener);
    }

    private void updateTopViewsAfterLogin() {
        userIcon = mUserInfo.getUserIcon();
        Picasso.with(getActivity()).load(userIcon).placeholder(R.drawable.ic_person_head_dafult)
                .into(personal_bac);
        personal_bac.setOnClickListener(personalBacListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("mikes", "fragment  onActivityResult, requestCode=" + requestCode);
        if (resultCode == Activity.RESULT_OK && data != null) {
            mUserInfo = (UserInfoBean) data.getSerializableExtra("userInfo");
            updateTopViewsAfterLogin();
        }
    }

    private OnClickListener mLoginClickListener = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onClick(View v) {
        if (isLogin) {
            switch (v.getId()) {
                case R.id.iv_person_edit:
                    Intent settingIntent = new Intent(getActivity(), PersonalSettingsActivity
                            .class);
                    startActivity(settingIntent);
                    break;
                case R.id.ll_my_order://跳到全部订单
                    Intent intent = new Intent(getActivity(), MyOrderActivity.class);
                    intent.putExtra("index", 0);
                    startActivity(intent);
                    break;
            }
        } else {
            ToastUtil.showMessageDefault(getActivity(),
                    getString(R.string.personal_no_login_toast));
            Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
            loginIntent.putExtra("type", FLAG_LOGIN);
            startActivity(loginIntent);
        }
       /* switch (v.getId()) {
            case R.id.ll_login_now:
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                loginIntent.putExtra("type", FLAG_LOGIN);
                startActivity(loginIntent);
                break;
        }*/
    }
}
