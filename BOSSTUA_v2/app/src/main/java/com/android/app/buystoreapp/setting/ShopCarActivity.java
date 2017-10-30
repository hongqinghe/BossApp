package com.android.app.buystoreapp.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.adapter.ShopCarAdapter;
import com.android.app.buystoreapp.adapter.ShopCarAdapter.ShopCarListener;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.CommodityBean;
import com.android.app.buystoreapp.bean.GsonShopCarToOrder;
import com.android.app.buystoreapp.bean.GsonShopDetailback;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车界面 删除，修改购物车商品数量
 * 
 */
public class ShopCarActivity extends BaseAct implements ShopCarListener,
        OnClickListener, OnItemLongClickListener {
    private ListView mListView;

    @ViewInject(R.id.id_shop_car_buyAll)
    private CheckBox mSelectAll;
    @ViewInject(R.id.id_shop_car_total_money)
    private TextView mViewTotalPrice;
    @ViewInject(R.id.id_shop_car_checkout)
    private Button mBtnSubmit;
    @ResInject(id = R.string.web_url, type = ResType.String)
    private String url;

    private ShopCarAdapter mShopCarAdapter;
    private List<CommodityBean> mDatas = new ArrayList<CommodityBean>();

    private String mCurrentUserName;
    private int mItemSelectedNums = 0;
    private float mTotalPrice;
    private boolean isAllSelected = false;
    private int mItemPositionSum = 0;
    private int mItemCount = 0;
    private List<CommodityBean> mSelectedCommodites = new ArrayList<CommodityBean>();

    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    @ViewInject(R.id.id_empty_fail)
    private View emptyFailureView;
    @ViewInject(R.id.id_search_empty)
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.shop_car_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_action_bar);

        ViewUtils.inject(this);
        mCurrentUserName = SharedPreferenceUtils.getCurrentUserInfo(this)
                .getUserName();
        mTitleText.setText("购物车");
        mListView = (ListView) findViewById(R.id.id_shop_car_list);

        mShopCarAdapter = new ShopCarAdapter(this, mDatas);
        mListView.setAdapter(mShopCarAdapter);
        mListView.setEmptyView(emptyView);
        mListView.setOnItemLongClickListener(this);
        requestUserShopCar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mItemSelectedNums == 0) {
            mBtnSubmit.setEnabled(false);
            mBtnSubmit.setBackgroundResource(R.drawable.app_btn_disabled_shape);
        } else {
            mBtnSubmit.setEnabled(true);
            mBtnSubmit.setBackgroundResource(R.drawable.app_btn_enabled_shape);
        }
    }

    @OnClick({ R.id.id_shop_car_buyAll, R.id.id_shop_car_checkout })
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.id_shop_car_checkout:
            Intent intent = new Intent(this, ConfirmOrderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putFloat("totalPrice", mTotalPrice);
            bundle.putInt("selectedNums", mItemSelectedNums);
            Gson gson = new Gson();
            GsonShopCarToOrder gsonShopCarToOrder = new GsonShopCarToOrder(
                    mSelectedCommodites);
            String orderString = gson.toJson(gsonShopCarToOrder);
            bundle.putString("commodites", orderString);
            intent.putExtras(bundle);
            startActivity(intent);
            break;
        case R.id.id_shop_car_buyAll:
            isAllSelected = !isAllSelected;
            mSelectAll.setChecked(isAllSelected);
            selectAllItem(isAllSelected);
            break;
        default:
            break;
        }
    }

    @OnClick({ R.id.id_custom_back_image, R.id.id_custom_edit_action })
    public void onCustomBarBackClicked(View v) {
        switch (v.getId()) {
        case R.id.id_custom_back_image:
            this.finish();
            break;
        default:
            break;
        }
    }

    private void selectAllItem(boolean checked) {
        try {
            SparseBooleanArray itemSelected = mShopCarAdapter
                    .getmItemSelectedState();
            itemSelected.put(mShopCarAdapter.getCount(), true);
            for (int i = 0; i < mShopCarAdapter.getCount(); i++) {
                itemSelected.put(i, checked);
                if (checked) {
                    // 更新订单总价
                    String itemCount = mShopCarAdapter.getItem(i)
                            .getCommodityNum();
                    String itemPrice = mShopCarAdapter.getItem(i)
                            .getProCurrentPrice();
                    // 一项的总价格
                    float itemTotalPrice = Float.valueOf(itemCount)
                            * Float.valueOf(itemPrice);
                    mTotalPrice += itemTotalPrice;
                    mItemSelectedNums = ShopCarActivity.this.mItemCount;
                    mSelectedCommodites.add(mDatas.get(i));
                } else {
                    mTotalPrice = 0;
                    mItemSelectedNums = 0;
                    mSelectedCommodites.remove(mDatas.get(i));
                }
            }
        } catch (Exception e) {
            Log.e("mikesError", "e:", e);
        }
        mViewTotalPrice.setText(String.format(
                getString(R.string.shop_car_total_money_format),
                mTotalPrice));
        updateSubmieBtnState();
        mShopCarAdapter.notifyDataSetChanged();
    }

    @Override
    public void itemSelected(boolean isChecked, int position) {
        position += 1;
        if (!isChecked) {
            mItemPositionSum -= position;
            mSelectAll.setChecked(false);
        } else {
            mItemPositionSum += position;
            if (mItemPositionSum == (mItemCount * (mItemCount + 1) / 2)) {
                mSelectAll.setChecked(true);
            }
        }
        position -= 1;
        // 更新订单总价
        String itemCount = mShopCarAdapter.getItem(position).getCommodityNum();
        String itemPrice = mShopCarAdapter.getItem(position)
                .getProCurrentPrice();
        // 一项的总价格
        float itemTotalPrice = Float.valueOf(itemCount)
                * Float.valueOf(itemPrice);
        if (isChecked) {
            mTotalPrice += itemTotalPrice;
        } else {
            mTotalPrice -= itemTotalPrice;
        }
        mViewTotalPrice.setText(String.format(
                getString(R.string.shop_car_total_money_format), mTotalPrice));

        // 更新提交商品的数量
        if (isChecked) {
            mItemSelectedNums += 1;
            mSelectedCommodites.add(mDatas.get(position));
        } else {
            mSelectedCommodites.remove(mDatas.get(position));
            mItemSelectedNums -= 1;
        }

        updateSubmieBtnState();
    }

    private void updateSubmieBtnState() {
        if (mItemSelectedNums >= 1) {
            mBtnSubmit.setEnabled(true);
            mBtnSubmit.setBackgroundResource(R.drawable.app_btn_enabled_shape);
            mBtnSubmit.setText(String.format(
                    getString(R.string.shop_car_checkout_format),
                    mItemSelectedNums));
        } else {
            mBtnSubmit.setEnabled(false);
            mBtnSubmit.setBackgroundResource(R.drawable.app_btn_disabled_shape);
            mBtnSubmit.setText(R.string.shop_car_checkout);
        }
    }

    @Override
    public boolean itemAddNum(String commodityID, int position) {
        View itemView = mListView.getChildAt(position);
        CheckBox box = (CheckBox) itemView
                .findViewById(R.id.id_shop_car_item_select);
        ImageButton addBtn = ( ImageButton) itemView.findViewById(R.id.id_shop_car_item_add);
        ImageButton minusBtn = ( ImageButton) itemView.findViewById(R.id.id_shop_car_item_minus);
        String itemPriceString = mShopCarAdapter.getItem(position)
                .getProCurrentPrice();
        TextView itemMoney = (TextView) itemView
                .findViewById(R.id.id_shop_car_item_money);// 商品小计
        addBtn.setEnabled(false);
        minusBtn.setEnabled(false);
        // 商品当前数量
        TextView commodityNumView = (TextView) itemView
                .findViewById(R.id.id_shop_car_item_num);

        int itemCount = Integer.valueOf(commodityNumView.getText().toString());
        itemCount += 1;
        // 更新商品数量
        mDatas.get(position).setCommodityNum(String.valueOf(itemCount));
        commodityNumView.setText(String.valueOf(itemCount));
        // 更新单项价格
        float itemPrice = Float.valueOf(itemPriceString);
        float itemTotalPrice = itemCount * itemPrice;
        itemMoney.setText(String.format(
                getString(R.string.shop_car_item_money), itemTotalPrice));
        // 更新订单总价
        if (box.isChecked()) {
            mTotalPrice += itemPrice;
            mViewTotalPrice.setText(String.format(
                    getString(R.string.shop_car_total_money_format),
                    mTotalPrice));
        }
        requestEditShopCar(commodityID, String.valueOf(itemCount),addBtn,minusBtn);
        return true;
    }

    @Override
    public boolean itemMinusNum(String commodityID, int position) {
        View itemView = mListView.getChildAt(position);
        CheckBox box = (CheckBox) itemView
                .findViewById(R.id.id_shop_car_item_select);
        ImageButton addBtn = ( ImageButton) itemView.findViewById(R.id.id_shop_car_item_add);
        ImageButton minusBtn = ( ImageButton) itemView.findViewById(R.id.id_shop_car_item_minus);
        addBtn.setEnabled(false);
        minusBtn.setEnabled(false);
        String itemPriceString = mShopCarAdapter.getItem(position)
                .getProCurrentPrice();
        TextView itemMoney = (TextView) itemView
                .findViewById(R.id.id_shop_car_item_money);// 商品小计
        // 商品当前数量
        TextView commodityNumView = (TextView) itemView
                .findViewById(R.id.id_shop_car_item_num);
        int itemCount = Integer.valueOf(commodityNumView.getText().toString());
        if (itemCount > 1) {
            itemCount -= 1;
            mDatas.get(position).setCommodityNum(String.valueOf(itemCount));
            commodityNumView.setText(String.valueOf(itemCount));
            // 更新单项价格
            float itemPrice = Float.valueOf(itemPriceString);
            float itemTotalPrice = itemCount * itemPrice;
            itemMoney.setText(String.format(
                    getString(R.string.shop_car_item_money), itemTotalPrice));
            // 更新订单总价
            if (box.isChecked()) {
                mTotalPrice -= itemPrice;
                mViewTotalPrice.setText(String.format(
                        getString(R.string.shop_car_total_money_format),
                        mTotalPrice));
            }
            requestEditShopCar(commodityID, String.valueOf(itemCount),addBtn,minusBtn);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void itemDel(String commodityID) {
        requestDeleteShopCar(commodityID);
    }

    private void requestUserShopCar() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        String param = "{\"cmd\":\"getShopCar\",\"userName\":\"aaa\"}";
        param = param.replace("aaa", mCurrentUserName);
        requestParams.put("json", param);

        client.get(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                try {
                    Gson gson = new Gson();
                    GsonShopDetailback gsonShopDetailback = gson.fromJson(
                            new String(arg2),
                            new TypeToken<GsonShopDetailback>() {
                            }.getType());
                    String result = gsonShopDetailback.getResult();
                    String resultNote = gsonShopDetailback.getResultNote();
                    if ("0".equals(result)) {
                        mSelectAll.setEnabled(true);
                        mDatas.clear();
                        mDatas.addAll(gsonShopDetailback.getCommodityList());
                        mItemCount = mDatas.size();
                        mShopCarAdapter.notifyDataSetChanged();
                    } else {
                        if ("您还没有添加商品".equals(resultNote)) {
                            mSelectAll.setEnabled(false);
                            emptyView.setVisibility(View.GONE);
                            mListView.setEmptyView(null);
                            Toast.makeText(ShopCarActivity.this,
                                    "您还没有添加商品，快去选购商品吧!", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            emptyView.setVisibility(View.GONE);
                            mListView.setEmptyView(emptyFailureView);
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                    Throwable arg3) {
                emptyView.setVisibility(View.GONE);
                mListView.setEmptyView(emptyFailureView);
            }
        });
    }

    /**
     * commodityNum为空时删除commodityID的商品
     */
    private void requestDeleteShopCar(String commodityID) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        String param = "{\"cmd\":\"editShopCar\",\"userName\":\"aaa\",\"commodityID\":\"bbb\",\"commodityNum\":\"\"}";
        param = param.replace("aaa", mCurrentUserName);
        param = param.replace("bbb", commodityID);
        requestParams.put("json", param);

        client.get(url, requestParams, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                LogUtils.d("requestDeleteShopCar result=" + new String(arg2));
                try {
                    Gson gson = new Gson();
                    GsonShopDetailback gsonShopDetailback = gson.fromJson(
                            new String(arg2),
                            new TypeToken<GsonShopDetailback>() {
                            }.getType());
                    String result = gsonShopDetailback.getResult();
                    String resultNote = gsonShopDetailback.getResultNote();
                    if ("0".equals(result)) {
                        Toast.makeText(ShopCarActivity.this, "删除成功",
                                Toast.LENGTH_SHORT).show();
                        mDatas.clear();
                        mDatas.addAll(gsonShopDetailback.getCommodityList());
                        mItemCount = mDatas.size();
                        mShopCarAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ShopCarActivity.this, resultNote,
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                    Throwable arg3) {

            }
        });
    }

    /**
     * commodityNum不为空时修改commodityID的商品
     */
    private void requestEditShopCar(String commodityID, String commodityNum,final ImageButton addBtn,final ImageButton minusBtn) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        String param = "{\"cmd\":\"editShopCar\",\"userName\":\"aaa\",\"commodityID\":\"bbb\",\"commodityNum\":\"ccc\"}";
        param = param.replace("aaa", mCurrentUserName);
        param = param.replace("bbb", commodityID);
        param = param.replace("ccc", commodityNum);
        requestParams.put("json", param);

        client.get(url, requestParams, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                LogUtils.d("requestEditShopCar result=" + new String(arg2));
                try {
                    Gson gson = new Gson();
                    GsonShopDetailback gsonShopDetailback = gson.fromJson(
                            new String(arg2),
                            new TypeToken<GsonShopDetailback>() {
                            }.getType());
                    String result = gsonShopDetailback.getResult();
                    String resultNote = gsonShopDetailback.getResultNote();
                    if ("0".equals(result)) {
                        Toast.makeText(ShopCarActivity.this, "修改成功",
                                Toast.LENGTH_SHORT).show();
                        mDatas.clear();
                        mDatas.addAll(gsonShopDetailback.getCommodityList());
                        mItemCount = mDatas.size();
                        mShopCarAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ShopCarActivity.this, resultNote,
                                Toast.LENGTH_SHORT).show();
                    }
                    addBtn.setEnabled(true);
                    minusBtn.setEnabled(true);
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                    Throwable arg3) {
            	 addBtn.setEnabled(true);
                 minusBtn.setEnabled(true);
            }
        });
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
            final int position, long arg3) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("确定要删除该商品吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        itemDel(mShopCarAdapter.getItem(position)
                                .getProId());
                    }
                }).setNegativeButton("取消", null).create();
        dialog.show();
        return false;
    }

}
