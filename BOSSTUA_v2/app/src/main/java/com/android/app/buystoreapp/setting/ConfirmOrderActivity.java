package com.android.app.buystoreapp.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.adapter.NewConfirmOrderAdapter;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.AddressBean;
import com.android.app.buystoreapp.bean.ConfirmOrderBean;
import com.android.app.buystoreapp.bean.ConfrimOrderPostBean;
import com.android.app.buystoreapp.bean.GroupGoods;
import com.android.app.buystoreapp.bean.GsonConfirmOrderBack;
import com.android.app.buystoreapp.bean.GsonCreatOrderBack;
import com.android.app.buystoreapp.bean.GsonOrderAddressBack;
import com.android.app.buystoreapp.bean.OrderPro;
import com.android.app.buystoreapp.bean.ScobList;
import com.android.app.buystoreapp.bean.ShoppingCarBean;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 确认订单
 *
 * @author likaihang
 *         creat at @time 16/10/15 11:01
 */
public class ConfirmOrderActivity extends BaseAct implements OnClickListener {
    private static final int PICK_ADDRESS = 0x11;

    private ListView mListView;
    private NewConfirmOrderAdapter mConfirmOrderAdapter;
    private List<ConfirmOrderBean> mConfirmOrderBeans = new ArrayList<ConfirmOrderBean>();
    private TextView mOrderPrice;
    private Button mOrderSubmit;
    private LayoutInflater mInflater;
    private View headerView;

    private View headAddressContainer;
    private View headNoAddress;

    private String url;
    private String mCurrentUserName;
    private List<ScobList> postbean = new ArrayList<ScobList>();

    private List<GsonOrderAddressBack.OrderAddress> mAddressDatas = new
            ArrayList<GsonOrderAddressBack.OrderAddress>();
    private AddressBean mAddressBean = null;
    private List<ShoppingCarBean> mListGoods = new ArrayList<ShoppingCarBean>();
    private double mTotalPrice;
    private int mCommodityNums;

    private TextView mHeadAddressName;
    private TextView mHeadAddress;
    private TextView mHeadAddressPhone;

    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;
    private String userid;
    private GroupGoods goods;
    private String modes = " ";
    private String comment = " ";
    private boolean isNoAddress = false;
    private String addressid;
    private List<String> proName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.confirm_order_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_action_bar);
        mContext = this;
        ViewUtils.inject(this);
        mTitleText.setText("订单确认");
        initIntentDatas();
        userid = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();

        mOrderPrice = (TextView) findViewById(R.id.id_confirm_order_price);
        mOrderPrice.setText("￥" + mTotalPrice);
        mOrderSubmit = (Button) findViewById(R.id.id_confirm_order_submit);
        mOrderSubmit.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.id_confirm_order_list);

        url = getString(R.string.web_url);
        mCurrentUserName = SharedPreferenceUtils.getCurrentUserInfo(this)
                .getUserName();

        mInflater = LayoutInflater.from(this);
        headerView = mInflater.inflate(R.layout.confirm_order_header, null);
        headAddressContainer = headerView.findViewById(R.id.id_confirm_order_address_container);
        headNoAddress = headerView.findViewById(R.id.id_confirm_order_no_address);

        mHeadAddressName = (TextView) headerView
                .findViewById(R.id.tv_order_address_recipients_name);
        mHeadAddress = (TextView) headerView
                .findViewById(R.id.tv_item_address_harvest_address);
        mHeadAddressPhone = (TextView) headerView.findViewById(R.id.tv_order_address_phone_number);
        headerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
               /* Intent addressIntent = new Intent(ConfirmOrderActivity.this,
                        UserAddressActivity.class);
                addressIntent.putExtra("where", 1);
                startActivityForResult(addressIntent, PICK_ADDRESS);*/
                if (isNoAddress) {
                    Intent intent = new Intent(ConfirmOrderActivity.this, AddAddressActivity.class);
                    intent.putExtra("size", 2);
                    startActivity(intent);

                } else {
                    startActivity(new Intent(ConfirmOrderActivity.this, MyAddressListActivity
                            .class));
                }
            }
        });
        mListView.addFooterView(headerView);
        mConfirmOrderAdapter = new NewConfirmOrderAdapter(this, mListGoods);
        mListView.setAdapter(mConfirmOrderAdapter);
        initErrorPage();
        addIncludeLoading(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestAddressList();
    }

    @Override
    public void onClick(View v) {
        if (!isNoAddress) {
            addressid = mAddressDatas.get(0).getReceiveAddId();//地址id
        }

        switch (v.getId()) {
            case R.id.id_confirm_order_submit:
                postbean.clear();
                for (int i = 0; i < mListGoods.size(); i++) {
                    ScobList sco = new ScobList();
                    sco.setReceiveAddId(addressid);
                    LinearLayout view = (LinearLayout) mListView.getChildAt(i);
                    TextView mo = (TextView) view.findViewById(R.id.tv_fireght_modes);
                    EditText leave = (EditText) view.findViewById(R.id.et_order_leave_message);

                    List<GroupGoods> itemList = mListGoods.get(i).getQueryCar();
                    int m = 0;
                    String f = "";
                    for (int j = 0; j < itemList.size(); j++) {
                        int mod = itemList.get(j).getModes();
                        if (m < mod) {
                            m = mod;
                        }
                        if (m == 2){
                            f = String.valueOf(itemList.get(j).getFreightMode());
                        }
                    }
                    modes = m + f;
                   /* if (mo != null) {
                        modes = mo.getText().toString();
                    }*/
                    if (leave != null) {
                        comment = leave.getText().toString().trim();
                    }
                    if (!TextUtils.isEmpty(modes)) {
                        sco.setServiceMethod(modes);//服务方式
                    } else {
                        sco.setServiceMethod("");
                    }
                    if (!TextUtils.isEmpty(comment)) {
                        sco.setUserComment(comment);//买家留言
                    } else {
                        sco.setUserComment("");
                    }
                    List<OrderPro> scobList = new ArrayList<OrderPro>();
                    for (int j = 0; j < mListGoods.get(i).getQueryCar().size(); j++) {
                        goods = mListGoods.get(i).getQueryCar().get(j);
                        OrderPro orderpro = new OrderPro();
                        orderpro.setProCount(goods.getCount());
                        orderpro.setShopCarId(goods.getShopCarId());
                        orderpro.setProImgUrl(goods.getProImageMin());
                        orderpro.setMoreId(goods.getMoreGroId());
                        scobList.add(orderpro);
                        sco.setOrderPro(scobList);
                        postbean.add(sco);
                    }
                }
               /* CommodityBean commodityBean = mSelectedCommodites.get(i);
                String sumMoney = String.valueOf(Integer.valueOf(commodityBean
                        .getCommodityNum())
                        * Float.valueOf(commodityBean.getProCurrentPrice()));
                ConfirmOrderBean confirmOrderBean = new ConfirmOrderBean(
                        commodityBean.getProId(),
                        commodityBean.getCommodityNum(), sumMoney,
                        commodityBean.getLeaveMessage());
                mConfirmOrderBeans.add(confirmOrderBean);*/

                if (addressid != null) {
                    //生成订单
                    requestSendCommodites();
                } else {
                    ToastUtil.showMessageDefault(mContext, "您还没有收货地址！");
                }
                break;
            case R.id.id_confirm_order_no_address:

                break;
            case R.id.id_confirm_order_address_container:
                startActivity(new Intent(this, MyAddressListActivity.class));
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.id_custom_back_image)
    public void onCustomBarBackClicked(View v) {
        switch (v.getId()) {
            case R.id.id_custom_back_image:
                mListGoods.clear();
                this.finish();
                break;
            default:
                break;
        }
    }

    private void initIntentDatas() {
        mTotalPrice = getIntent().getDoubleExtra("totalPrice", 0d);
//        mCommodityNums = intent.getIntExtra("selectedNums", 0);

        mListGoods = (List<ShoppingCarBean>) getIntent().getSerializableExtra("selectcarlist");
//        span = getIntent().getStringExtra("totalPrice");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == PICK_ADDRESS && data != null) {
            LogUtils.d("onActivityResult PICK_ADDRESS ");
            headAddressContainer.setVisibility(View.VISIBLE);
            headNoAddress.setVisibility(View.GONE);
            mAddressBean = (AddressBean) data.getSerializableExtra("address");
            initHeadAddressViews();
        }
    }

    private void requestAddressList() {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "confirmOrderAddress");
            obj.put("userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                stopLoadingAnim();
                Log.d("mikes", "requestAddressList result=" + new String(arg2));
                Gson gson = new Gson();
                GsonOrderAddressBack gsonAddressBack = gson.fromJson(
                        new String(arg2), new TypeToken<GsonOrderAddressBack>() {
                        }.getType());
                String result = gsonAddressBack.getResult();
                if ("0".equals(result)) {
                    if (gsonAddressBack.getConfirmOrderAddress().size() > 0) {
                        mAddressDatas.clear();
                        mAddressDatas.addAll(gsonAddressBack.getConfirmOrderAddress());
                        headAddressContainer.setVisibility(View.VISIBLE);
                        headNoAddress.setVisibility(View.GONE);
                        initHeadAddressViews();
                        isNoAddress = false;
                    } else {
                        isNoAddress = true;
                        headAddressContainer.setVisibility(View.GONE);
                        headNoAddress.setVisibility(View.VISIBLE);
                    }
//                        findDefaultAddress();
                } else {
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                stopLoadingAnim();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
            }
        });
    }

   /* private void findDefaultAddress() {
        for (int i = 0; i < mAddressDatas.size(); i++) {
            if ("1".equals(mAddressDatas.get(i).getIsDefault())) {
                mAddressBean = mAddressDatas.get(i);
                initHeadAddressViews();
                break;
            }
        }
    }*/

    private void initHeadAddressViews() {

        mHeadAddressName.setText(mAddressDatas.get(0).getReceiverName());
        mHeadAddressPhone.setText(mAddressDatas.get(0).getReceiverPhone());
        mHeadAddress.setText(mAddressDatas.get(0).getReceiverArea() + mAddressDatas.get(0)
                .getReceiverStreet() + mAddressDatas.get(0).getReceiverAdd());
    }

    private void requestSendCommodites() {
        ConfrimOrderPostBean data = new ConfrimOrderPostBean("createOrder", userid, mTotalPrice,
                postbean);
        JSONObject obj = null;
        try {
            obj = new JSONObject(new Gson().toJson(data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.d("生成订单提交数据--"
                + obj);
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                try {
                    LogUtils.d("生成订单返回数据"
                            + new String(arg2));
                    Gson gson = new Gson();
                    GsonCreatOrderBack gsonCreatOrderBack = gson.fromJson(
                            new String(arg2),
                            new TypeToken<GsonCreatOrderBack>() {
                            }.getType());
                    String result = gsonCreatOrderBack.getResult();
                    if ("0".equals(result)) {
                        String[] orderids = gsonCreatOrderBack.getOrderids();
                        getBuyOrderId(orderids);
                    } else {
                        finish();
                        ToastUtil.showMessageDefault(mContext, "订单提交失败，请重试");
                    }
                } catch (Exception e) {
                    LogUtils.e("requestSendCommodites error:", e);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {

            }
        });
    }

    private void getBuyOrderId(String[] orderids) {
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i = 0; i < orderids.length; i++) {
            array.put(orderids[i]);
        }
        try {
            obj.put("cmd", "buyOrderId");
            obj.put("orderIds", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("获取购买订单号提交数据--", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("获取购买订单号返回数据--", new String(bytes));
                Gson gson = new Gson();
                GsonConfirmOrderBack gsonConfirmOrderBack = gson.fromJson(
                        new String(bytes),
                        new TypeToken<GsonConfirmOrderBack>() {
                        }.getType());
                String result = gsonConfirmOrderBack.getResult();
                if ("0".equals(result)) {
                    Toast.makeText(ConfirmOrderActivity.this,
                            "订单提交成功，请立即支付", Toast.LENGTH_SHORT).show();
                    proName.addAll(gsonConfirmOrderBack.getProName());
                    String orderID = gsonConfirmOrderBack.getBuyOrderid();
                    showPayDialog(orderID);
                } else {
                    Toast.makeText(ConfirmOrderActivity.this,
                            "订单提交失败，请稍后重试", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {

            }
        });
    }

    private void showPayDialog(final String orderID) {
        StringBuilder sb = new StringBuilder();
        for (ShoppingCarBean commodityBean : mListGoods) {
            sb.append(commodityBean.getNickname()
                    + ",");
        }
        String price = String.valueOf(mTotalPrice);
        Intent intent = new Intent(ConfirmOrderActivity.this, PayOrderActivity.class);
        intent.putExtra("orderID", orderID);
        intent.putExtra("price", price);
        intent.putExtra("body", sb.toString());
        intent.putExtra("flag","2");
        intent.putExtra("proName", (Serializable) proName);
        startActivity(intent);
        finish();
        
       /* AlipayKeys alipayKeys = new AlipayKeys();
        alipayKeys.pay(ConfirmOrderActivity.this,
                orderID, sb.toString(),  String.valueOf(mTotalPrice),
                mHandler);
        Toast.makeText(ConfirmOrderActivity.this,
                "请求支付中...请稍后", Toast.LENGTH_SHORT)
                .show();*/

    }
    /*private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case AlipayKeys.SDK_PAY_FLAG: {
                PayResult payResult = new PayResult((String) msg.obj);

                // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                String resultInfo = payResult.getResult();

                String resultStatus = payResult.getResultStatus();
                Log.d(AlipayKeys.TAG, "resultStatus=" + resultStatus
                        + " ,AliPay result=" + resultInfo);

                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    Toast.makeText(ConfirmOrderActivity.this, "支付成功",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ConfirmOrderActivity.this,MyOrderActivity.class));
                    ConfirmOrderActivity.this.finish();
                } else {
                    // 判断resultStatus 为非“9000”则代表可能支付失败
                    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        Toast.makeText(ConfirmOrderActivity.this, "支付结果确认中",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        Toast.makeText(ConfirmOrderActivity.this, "支付失败",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
            default:
                break;
            }
        };
    };*/
}
