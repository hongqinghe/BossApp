package com.android.app.buystoreapp.managementservice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.BossBuyActivity;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.AddressBeanNew;
import com.android.app.buystoreapp.bean.UserInfoBean;
import com.android.app.buystoreapp.other.CustomDialog;
import com.android.app.buystoreapp.setting.AddAddressActivity;
import com.android.app.buystoreapp.setting.MyAddressListActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人设置页面
 * 魏林编写
 */
public class PersonalSettingsActivity extends BaseAct implements View.OnClickListener {

    private ImageButton iv_back;

    /**
     * 个人资料
     */
    private RelativeLayout rl_personal_data;

    /**
     * 账号安全
     */
    private RelativeLayout rl_account_security;

    /**
     * 地址管理
     */
    private RelativeLayout rl_address_management;

    /**
     * 退出当前账户
     */
    private Button ib_personal_setting_exit;


    public static PersonalSettingsActivity personalSettingsActivity;
    private String userId;

    private List<AddressBeanNew.AdressListBean> list = new ArrayList<AddressBeanNew
            .AdressListBean>();
    private Handler mHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SubscribeActivity.HANDLE_LOADMORE:
                    if (list.size() == 0) {
                        Intent addAddress = new Intent(PersonalSettingsActivity.this,
                                AddAddressActivity.class);
                        addAddress.putExtra("size", 0);
                        startActivity(addAddress);
                        ToastUtil.showMessageDefault(PersonalSettingsActivity.this,
                                "您还没有添加地址，请先添加地址");
                    } else {
                        Intent addressList = new Intent(PersonalSettingsActivity.this,
                                MyAddressListActivity.class);
                        startActivity(addressList);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_settings);
        initView();
        setListener();
        personalSettingsActivity = this;
        initErrorPage();
        addIncludeLoading(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
    }

    @Override
    protected void load() {
        super.load();
        getReceive();
    }

    private void setListener() {
        iv_back.setOnClickListener(this);
        rl_personal_data.setOnClickListener(this);
        rl_account_security.setOnClickListener(this);
        rl_address_management.setOnClickListener(this);
        ib_personal_setting_exit.setOnClickListener(this);
    }

    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string
                .personal_set));
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        rl_personal_data = (RelativeLayout) findViewById(R.id.rl_personal_data);
        rl_account_security = (RelativeLayout) findViewById(R.id.rl_account_security);
        rl_address_management = (RelativeLayout) findViewById(R.id.rl_address_management);
        ib_personal_setting_exit = (Button) findViewById(R.id.ib_personal_setting_exit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.rl_personal_data://个人资料
                Intent intentData = new Intent(PersonalSettingsActivity.this,
                        PersonalDataActivity.class);
                startActivity(intentData);
                break;
            case R.id.rl_account_security://账号安全
                Intent intentsafe = new Intent(PersonalSettingsActivity.this, AccountSafeActivity
                        .class);
                startActivity(intentsafe);
                break;
            case R.id.rl_address_management://地址管理
                /**
                 * 在次需要做判断，发送网络请求看地址集合是否为空  为空跳转到添加地址页面（AddAddressActivity）
                 * 如果集合不为空则跳转到地址列表（MyAddressListActivity）
                 * */

                startWhiteLoadingAnim();
                load();


                break;
            case R.id.ib_personal_setting_exit://退出当前账户
                CustomDialog.initDialog(PersonalSettingsActivity.this);
                CustomDialog.tvTitle.setText("是否确定注销当前用户?");
                CustomDialog.btnLeft.setText("确定");
                CustomDialog.btnRight.setText("取消");
                CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog.dialog.dismiss();
                        exit();
                        startActivity(new Intent(PersonalSettingsActivity.this, BossBuyActivity
                                .class));
                        PersonalSettingsActivity.this.finish();
                    }
                });
                CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog.dialog.dismiss();
                    }
                });
                break;
        }
    }

    /**
     * 注销用户
     */
    private void exit() {
        UserInfoBean userInfo = new UserInfoBean();
        SharedPreferenceUtils.saveCurrentUserInfo(userInfo, this, false);
        SharedPreferenceUtils.saveUserPosition(this, "");
        SharedPreferenceUtils.saveNickName(this, "");
        SharedPreferenceUtils.saveHeadicon(this, "");
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("shang", "huan xin logout success--------");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("shang", "huan xin onError--------" + s);
            }

            @Override
            public void onProgress(int i, String s) {
                Log.e("shang", "huan xin onProgress--------" + s);
            }
        });
    }

    public void getReceive() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getReceive");
            obj.put("thisUser", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.e("PersonalSetting\n" +
                "getReceive=" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                AddressBeanNew addressBeanNew = gson.fromJson(new String(bytes), new
                        TypeToken<AddressBeanNew>() {
                }.getType());
                String result = addressBeanNew.getResult();
                if ("0".equals(result)) {
                    list.clear();
                    list.addAll(addressBeanNew.getAdressList());
                    mHander.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(PersonalSettingsActivity.this, getResources()
                        .getString(R.string.service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(PersonalSettingsActivity.this, getResources()
                        .getString(R.string.service_error_hint));
            }
        });
    }
}
