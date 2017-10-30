package com.android.app.buystoreapp.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.AddressBeanNew;
import com.android.app.buystoreapp.location.AddressActivity;
import com.android.app.buystoreapp.managementservice.MyStreetActivity;
import com.android.app.buystoreapp.managementservice.SubscribeActivity;
import com.android.app.buystoreapp.other.AreaBean;
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


public class AddAddressActivity extends BaseAct implements View.OnClickListener {
    private static final int GETSTREETNAME_SUCCESS = 20;
    private TextView tv_title;
    private ImageView iv_back;

    private EditText addName;
    private EditText addPhone;
    private TextView addAddressarea;
    private TextView addAddressStreet;
    private EditText addAddressDetailed;
    private RelativeLayout saveAddressBtn, rl_getStreet;
    private RelativeLayout rlAddAddress;

    //定义上下文
    private Context context;
    //定义省市区数据列表

    private List<AreaBean.AreasListBean> streetLists = new ArrayList<AreaBean.AreasListBean>();


    private int areaId;

    private String userId;
    private List<String> list04;

    private AddressBeanNew.AdressListBean adressListBean;  //判断是否为编辑界面传来的
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SubscribeActivity.HANDLE_LOADMORE:
                    ToastUtil.showMessageDefault(AddAddressActivity.this, "地址添加成功");
                    if (getIntent().getExtras().getInt("size") == 1||getIntent().getExtras().getInt("size") == 2) {
                        finish();
                    } else {
                        Intent toAddressList = new Intent(AddAddressActivity.this, MyAddressListActivity.class);
                        startActivity(toAddressList);
                        finish();
                    }
                    break;
                case GETSTREETNAME_SUCCESS:
                    list04 = new ArrayList<String>();
                    for (int i = 0; i < streetLists.size(); i++) {
                        list04.add(streetLists.get(i).getAreaname());
                    }
                    Intent intent = new Intent(context, MyStreetActivity.class);
                    intent.putStringArrayListExtra("Streets", (ArrayList<String>) list04);
                    startActivityForResult(intent, 100);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address_add);
        ViewUtils.inject(this);
        adressListBean = (AddressBeanNew.AdressListBean) getIntent   //接收到编辑界面传过来的参数
                ().getSerializableExtra("addressBean");
        context = this;
        initView();
    }

    public void load() {
        saveAddress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_add_address:
//                rlAddAddress.setEnabled(false);
                Intent i = new Intent(this, AddressActivity.class);
                startActivityForResult(i, 1);
                break;
            case R.id.rl_getStreet:
                if (addAddressarea.getText().equals("")) {
                    ToastUtil.showMessageDefault(context, "请选择地区");
                } else {
                    getStreetName();
                }
                break;
            case R.id.rl_address_add_bottom_title://保存按钮

                load();


                break;
            case R.id.iv_back://退出返回
                this.finish();

                break;
        }
    }

    /**
     * 提交保存地址
     */
    private void saveAddress() {

        if (addName.getText().toString().trim().isEmpty()) {
            ToastUtil.showMessageDefault(context, "收货姓名不能为空");
        } else if (addPhone.getText().toString().trim().isEmpty()) {
            ToastUtil.showMessageDefault(context, "收货电话不能为空");
        } else if (addAddressarea.getText().toString().trim().isEmpty()) {
            ToastUtil.showMessageDefault(context, "请选择地区");
        } else if (addAddressStreet.getText().toString().trim().isEmpty()) {
            ToastUtil.showMessageDefault(context, "请选择街道信息");
        } else if (addAddressDetailed.getText().toString().trim().isEmpty()) {
            ToastUtil.showMessageDefault(context, "请填写详细地址");
        } else if (adressListBean == null) {
            addReceiverAddressr();
        } else {

            editAdress();
        }
    }

    private void editAdress() {
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "editAdress");
            obj.put("adressID", adressListBean.getAdressID());
            obj.put("name", addName.getText().toString().trim());
            obj.put("phone", addPhone.getText().toString().trim());
            obj.put("adress", addAddressDetailed.getText().toString().trim());
            obj.put("postcode", "");
            obj.put("isDefault", adressListBean.getIsDefault());
            obj.put("receiverArea", addAddressarea.getText().toString().trim());
            obj.put("receiverStreet", addAddressStreet.getText().toString().trim());
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("AddAddressActivity", "editAdress obj========" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                String str = new String(bytes);
                try {
                    JSONObject object = new JSONObject(str);
                    String result = (String) object.get("result");
                    if ("0".equals(result)) {

                        mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                hideErrorPageState();
                ToastUtil.showMessageDefault(AddAddressActivity.this, "网络请求失败了，请重试");
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
                hideErrorPageState();
                ToastUtil.showMessageDefault(AddAddressActivity.this, "网络请求失败了，请重试");
            }
        });
    }

    private void addReceiverAddressr() {
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "addReceiverAddressr");
            obj.put("userid", userId);
            obj.put("receiver_name", addName.getText().toString().trim());
            obj.put("receiver_phone", addPhone.getText().toString().trim());
            obj.put("receiver_add", addAddressDetailed.getText().toString().trim());
            obj.put("receiver_postcode", "");
            if (getIntent().getExtras().getInt("size") == 0||getIntent().getExtras().getInt("size") == 2) {
                obj.put("is_defalt", 1);
            } else {
                obj.put("is_defalt", 0);
            }

            obj.put("receiver_area", addAddressarea.getText().toString().trim());
            obj.put("receiver_street", addAddressStreet.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("AddAddressActivity", "addReceiverAddressr obj========" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                String str = new String(bytes);
                try {
                    JSONObject object = new JSONObject(str);
                    String result = (String) object.get("result");
                    if ("0".equals(result)) {
                        mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                ToastUtil.showMessageDefault(AddAddressActivity.this, "网络请求失败了，请重试");
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                ToastUtil.showMessageDefault(AddAddressActivity.this, "网络请求失败了，请重试");
            }
        });
    }


    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.add_address));
        iv_back = (ImageView) findViewById(R.id.iv_back);
        addName = (EditText) findViewById(R.id.et_add_name);
        addPhone = (EditText) findViewById(R.id.et_add_phone);
        addAddressDetailed = (EditText) findViewById(R.id.et_add_address_detailed);
        rl_getStreet = (RelativeLayout) findViewById(R.id.rl_getStreet);
        rlAddAddress = (RelativeLayout) findViewById(R.id.rl_add_address);
        addAddressarea = (TextView) findViewById(R.id.tv_add_address_show_area);
        addAddressStreet = (TextView) findViewById(R.id.tv_add_address_show_street);
        saveAddressBtn = (RelativeLayout) findViewById(R.id.rl_address_add_bottom_title);

        //判断如过有上个界面传过来的值，证明打开的是修改地址界面
        if (adressListBean != null) {
            addName.setText(adressListBean.getName());
            addPhone.setText(adressListBean.getPhone());
            addAddressDetailed.setText(adressListBean.getAdress());
            tv_title.setText("修改收货地址");
            addAddressarea.setText(adressListBean.getReceiverArea());
            addAddressStreet.setText(adressListBean.getReceiverStreet());
        }

        rlAddAddress.setOnClickListener(this);
        rl_getStreet.setOnClickListener(this);
        saveAddressBtn.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }


    /**
     * 获取街道列表
     */
    private void getStreetName() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "selectAddress");
            obj.put("id", areaId);
            obj.put("level", 4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("addAddressActivity", "selectAddress obj===" + obj);
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("addAddressActivity", "selectAddress byte===" + new String(bytes));
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                AreaBean areaBean = gson.fromJson(new String(bytes), new TypeToken<AreaBean>() {
                }.getType());
                String result = areaBean.getResult();
                if ("0".equals(result)) {
                    streetLists.clear();
                    streetLists.addAll(areaBean.getAreasList());
                    mHandler.obtainMessage(GETSTREETNAME_SUCCESS).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                ToastUtil.showMessageDefault(AddAddressActivity.this, "网络请求失败了，请重试");
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                ToastUtil.showMessageDefault(AddAddressActivity.this, "网络请求失败了，请重试");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case 1:
                String result = data.getExtras().getString("result");
                areaId = data.getExtras().getInt("areaId");
                Log.e("lin", "result=" + result);
                addAddressarea.setText(result);
                break;

            case 100:
                String streetName = data.getStringExtra("streetName");
                addAddressStreet.setText(streetName);
                break;
        }
    }


    /**
     * 软键盘点非编辑框隐藏
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

}
