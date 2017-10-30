package com.android.app.buystoreapp.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.CheckUtils;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.AddressBean;
import com.android.app.buystoreapp.bean.GsonAddressBack;
import com.android.app.buystoreapp.bean.GsonAddressCmd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.ResType;
import com.lidroid.xutils.view.annotation.ResInject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

public class UserAddressItemActivity extends Activity implements
        OnClickListener {
    @ViewInject(R.id.id_user_address_del)
    private Button mDelBtn;
    @ViewInject(R.id.id_user_address_save)
    private Button mEditBtn;

    @ViewInject(R.id.id_user_address_name)
    private EditText mReceiveName;
    @ViewInject(R.id.id_user_address_phone)
    private EditText mReceivePhone;
    @ViewInject(R.id.id_user_address_postcode)
    private EditText mReceivePostcode;
    @ViewInject(R.id.id_user_address_address)
    private EditText mReceiveAddress;
    @ViewInject(R.id.id_user_address_default)
    private CheckBox mSwitchView;
    @ResInject(id = R.string.web_url, type = ResType.String)
    private String url;

    private String mCurrentUserName;
    private String mAddressID;
    private AddressBean addressBean;
    String type;
    
    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.user_address_item_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_action_bar);

        ViewUtils.inject(this);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if ("add".equals(type)) {
            mTitleText.setText("新增收货地址");
            mDelBtn.setVisibility(View.INVISIBLE);
            mDelBtn.setOnClickListener(null);
        } else if ("more".equals(type)) {
            mTitleText.setText("详细收货地址");
            addressBean = (AddressBean) intent
                    .getSerializableExtra("addressBean");
            mAddressID = addressBean.getAdressID();
            if (addressBean == null) {
                Toast.makeText(this, "323982398", Toast.LENGTH_SHORT).show();
                finish();
            }
            mDelBtn.setVisibility(View.VISIBLE);
            mDelBtn.setOnClickListener(this);

            mReceiveName.setText(addressBean.getName());
            mReceiveAddress.setText(addressBean.getAdress());
            mReceivePhone.setText(addressBean.getPhone());
            mReceivePostcode.setText(addressBean.getPostcode());
            boolean isDefault = "1".equals(addressBean.getIsDefault()) ? true
                    : false;
            mSwitchView.setChecked(isDefault);
        }

        mCurrentUserName = SharedPreferenceUtils.getCurrentUserInfo(this)
                .getUserName();

        mEditBtn.setOnClickListener(this);
        mReceivePhone.setOnFocusChangeListener(userFocusChangeListener);
    }

    
    @OnClick(R.id.id_custom_back_image)
    public void onCustomBarBackClicked(View v) {
        switch (v.getId()) {
        case R.id.id_custom_back_image:
            this.finish();
            break;
        default:
            break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.id_user_address_del:
            resuestDeleteAddress();
            break;
        case R.id.id_user_address_save:
            if ("add".equals(type)) {
                requestSaveAddress();
            } else if ("more".equals(type)) {
                requestEditAddress();
            }
            break;
        default:
            break;
        }
    }

    private OnFocusChangeListener userFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View arg0, boolean hasFocus) {
            if (!hasFocus) {
                if (!CheckUtils.mobileCheck(mReceivePhone.getText().toString())) {
                    Toast.makeText(
                            getBaseContext(),
                            getResources().getString(
                                    R.string.personal_email_mobile_check),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void resuestDeleteAddress() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        final Gson gson = new Gson();
        GsonAddressCmd gsonAddressCmd = new GsonAddressCmd("deleteAdress",
                mCurrentUserName, mAddressID);
        requestParams.put("json", gson.toJson(gsonAddressCmd));

        client.get(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                GsonAddressBack gsonAddressBack = gson.fromJson(
                        new String(arg2), new TypeToken<GsonAddressBack>() {
                        }.getType());
                try {
                    String result = gsonAddressBack.getResult();
                    String resultNote = gsonAddressBack.getResultNote();
                    if ("0".equals(result)) {
                        Toast.makeText(UserAddressItemActivity.this,
                                resultNote, Toast.LENGTH_SHORT).show();
                        UserAddressItemActivity.this.finish();
                    } else {

                    }
                } catch (Exception e) {
                    Log.e("mikes", "requestAddressList error:", e);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                    Throwable arg3) {
            }
        });
    }

    private void requestEditAddress() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        final Gson gson = new Gson();
        String receiveName = mReceiveName.getText().toString();
        String phone = mReceivePhone.getText().toString();
        String address = mReceiveAddress.getText().toString();
        String postcode = mReceivePostcode.getText().toString();
        String isDefault = mSwitchView.isChecked() ? "1" : "0";
        GsonAddressCmd gsonAddressCmd = new GsonAddressCmd("editAdress",
                mCurrentUserName, mAddressID, receiveName, phone, address,
                postcode, isDefault);
        requestParams.put("json", gson.toJson(gsonAddressCmd));

        client.get(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                GsonAddressBack gsonAddressBack = gson.fromJson(
                        new String(arg2), new TypeToken<GsonAddressBack>() {
                        }.getType());
                try {
                    String result = gsonAddressBack.getResult();
                    String resultNote = gsonAddressBack.getResultNote();
                    if ("0".equals(result)) {
                        Toast.makeText(UserAddressItemActivity.this,
                                resultNote, Toast.LENGTH_SHORT).show();
                        UserAddressItemActivity.this.finish();
                    } else {

                    }
                } catch (Exception e) {
                    Log.e("mikes", "requestAddressList error:", e);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                    Throwable arg3) {
            }
        });
    }

    private void requestSaveAddress() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        final Gson gson = new Gson();
        String receiveName = mReceiveName.getText().toString();
        String phone = mReceivePhone.getText().toString();
        String address = mReceiveAddress.getText().toString();
        String postcode = mReceivePostcode.getText().toString();
        String isDefault = mSwitchView.isChecked() ? "1" : "0";
        GsonAddressCmd gsonAddressCmd = new GsonAddressCmd("addAdress",
                mCurrentUserName, mAddressID, receiveName, phone, address,
                postcode, isDefault);
        requestParams.put("json", gson.toJson(gsonAddressCmd));
        LogUtils.d("添加收货地址参数"+gson.toJson(gsonAddressCmd));
        client.get(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                GsonAddressBack gsonAddressBack = gson.fromJson(
                        new String(arg2), new TypeToken<GsonAddressBack>() {
                        }.getType());
                try {
                    String result = gsonAddressBack.getResult();
                    String resultNote = gsonAddressBack.getResultNote();
                    if ("0".equals(result)) {
                        Toast.makeText(UserAddressItemActivity.this,
                                resultNote, Toast.LENGTH_SHORT).show();
                        UserAddressItemActivity.this.finish();
                    } else {

                    }
                } catch (Exception e) {
                    Log.e("mikes", "requestAddressList error:", e);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                    Throwable arg3) {
            }
        });
    }
}
