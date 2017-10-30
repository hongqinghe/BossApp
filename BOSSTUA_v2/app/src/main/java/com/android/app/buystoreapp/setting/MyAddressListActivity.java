package com.android.app.buystoreapp.setting;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.adapter.AddressAdapterNew;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.AddressBeanNew;
import com.android.app.buystoreapp.managementservice.SubscribeActivity;
import com.android.app.buystoreapp.other.CustomDialog;
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

public class MyAddressListActivity extends BaseAct implements AddressAdapterNew.IsDefaultInterface {

    private static final int DELETERECEIVE_SUCCESS = 200;
    private ImageButton ib_addresslist_back;

    public final int EDITADDRESS_SUCCESS = 100;
    private ListView lv_address_list;
    private Button addAdress;
    private List<AddressBeanNew.AdressListBean> list = new ArrayList<AddressBeanNew.AdressListBean>();
    private AddressAdapterNew adapter;
    private String userId;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SubscribeActivity.HANDLE_LOADMORE:
                    if (list.size() == 0) {
                        ToastUtil.showMessageDefault(MyAddressListActivity.this, "您还没有设置默认地址，请添加默认地址");
                        adapter.notifyDataSetChanged();
                        Intent toadd = new Intent(MyAddressListActivity.this, AddAddressActivity.class);
                        toadd.putExtra("size", 0);
                        startActivity(toadd);
                        finish();
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    break;

                case EDITADDRESS_SUCCESS:
                    load();
                    adapter.notifyDataSetChanged();
                    break;
                case DELETERECEIVE_SUCCESS:
                    ToastUtil.showMessageDefault(MyAddressListActivity.this,
                            "删除成功!");
                    CustomDialog.dialog.dismiss();
                    load();
                    break;

            }
        }
    };
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_my_address_show);
        initView();
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();


    }

    public void load() {
        getReceive();
    }


    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string.receipt_address));
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAddressListActivity.this.finish();
            }
        });
        lv_address_list = (ListView) findViewById(R.id.lv_address_list);
        addAdress = (Button) findViewById(R.id.btn_addAddress);
        adapter = new AddressAdapterNew(this, list);
        adapter.setIsDefaultInterface(this);
        lv_address_list.setAdapter(adapter);

        addAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAddressListActivity.this, AddAddressActivity.class);
                intent.putExtra("size", 1);
                startActivity(intent);
            }
        });

        lv_address_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position,
                                           long
                                                   id) {
                CustomDialog.initDialog(MyAddressListActivity.this);
                CustomDialog.tvTitle.setText("您确定要删除该地址吗?");
                CustomDialog.btnLeft.setText("确定");
                CustomDialog.btnRight.setText("取消");
                CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startWhiteLoadingAnim();
                        deleteReceive(position);
                    }
                });

                CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog.dialog.dismiss();
                    }
                });

                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        load();


    }

    /**
     * 删除借口
     */
    private void deleteReceive(int position) {
        AddressBeanNew.AdressListBean adressListBean = list.get(position);
        String addressId = adressListBean.getAdressID();
        final JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "deleteReceive");
            obj.put("receive_add_id", addressId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("deleteReceive  obj", obj.toString());
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
                        mHandler.obtainMessage(DELETERECEIVE_SUCCESS).sendToTarget();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(MyAddressListActivity.this,
                        "删除失败!");
                CustomDialog.dialog.dismiss();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(MyAddressListActivity.this,
                        "删除失败!");
                CustomDialog.dialog.dismiss();
            }
        });

//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        String requset = "{\"cmd\":\"deleteReceive\",\"receive_add_id\":\"" +
//                addressId + "\"}";
//        params.put("json", requset);
//        client.get(Command.CONTEXT_URL, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                String str = new String(bytes);
//                try {
//                    JSONObject obj = new JSONObject(str);
//                    String result = obj.getString("result");
//                    if (result.equals("0")) {
//                        ToastUtil.showMessageDefault(MyAddressListActivity.this,
//                                "删除成功!");
//                        CustomDialog.dialog.dismiss();
//                        onResume();
//
//                    } else if (result.equals("1")) {
//                        ToastUtil.showMessageDefault(MyAddressListActivity.this,
//                                "删除失败!");
//                        CustomDialog.dialog.dismiss();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes,
//                                  Throwable throwable) {
//
//            }
//        });
    }

    /**
     * 查询借口
     */
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
                Log.e("list==", "getReceive bytes=" + new String(bytes));
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                AddressBeanNew addressBeanNew = gson.fromJson(new String(bytes), new TypeToken<AddressBeanNew>() {
                }.getType());
                String result = addressBeanNew.getResult();
                if ("0".equals(result)) {
                    list.clear();
                    list.addAll(addressBeanNew.getAdressList());
                    Log.e("list==", list.size() + "");
                    mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
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

    @Override
    public void change(int i, View view, List<AddressBeanNew.AdressListBean> addressList) {
        if (list.get(i).getIsDefault() == 1) {
            view.setClickable(false);
        } else {
            changeDialog(i, addressList);
        }
    }

    private void changeDialog(final int i, final List<AddressBeanNew.AdressListBean> addressList) {
        View layout = this.getLayoutInflater().inflate(R.layout.managenment_dialog, null);
        TextView tv_set_warning = (TextView) layout.findViewById(R.id.tv_set_warning);
        tv_set_warning.setText("您确定勾选为默认地址？");
        Button btn_true = (Button) layout.findViewById(R.id.btn_true);
        Button btn_false = (Button) layout.findViewById(R.id.btn_false);
        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWhiteLoadingAnim();
                editAdress(i, addressList);
                dialog.dismiss();
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

    /**
     * 修改接口
     */
    private void editAdress(int i, List<AddressBeanNew.AdressListBean> addressList) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "editAdress");
            obj.put("adressID", addressList.get(i).getAdressID());
            obj.put("name", addressList.get(i).getName());
            obj.put("phone", addressList.get(i).getPhone());
            obj.put("adress", addressList.get(i).getAdress());
            obj.put("postcode", "");
            obj.put("isDefault", 1);
            obj.put("receiverArea", addressList.get(i).getReceiverArea());
            obj.put("receiverStreet", addressList.get(i).getReceiverStreet());
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

                        mHandler.obtainMessage(EDITADDRESS_SUCCESS).sendToTarget();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                hideErrorPageState();
                ToastUtil.showMessageDefault(MyAddressListActivity.this, "网络请求失败了，请重试");
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
                hideErrorPageState();
                ToastUtil.showMessageDefault(MyAddressListActivity.this, "网络请求失败了，请重试");
            }
        });
    }
}



