package com.android.app.buystore.utils.expandtab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.adapter.GoodsLeftAdapter;
import com.android.app.buystoreapp.adapter.GoodsRightAdapter;
import com.android.app.buystoreapp.bean.ChanceInfoBean;
import com.android.app.buystoreapp.bean.CommodityCategory;
import com.android.app.buystoreapp.bean.CommoditySubCategory;
import com.android.app.buystoreapp.bean.GsonClassifyBack;
import com.android.app.buystoreapp.bean.GsonCommodityBack;
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

public class ViewMiddle extends LinearLayout implements GoodsRightAdapter.GoodsRightItemListener {
    private Context mContext;
    private static String showText = "不限";
    private String cityID;
    private String categoryName; //
    private ListView mGoodsLeftListView;
    private GoodsLeftAdapter mGoodsLeftAdapter;
    private ListView mGoodsRightListView;
    private GoodsRightAdapter mGoodsRightAdapter;
    private ArrayList<CommodityCategory> mCommodityCategoriesList = new ArrayList<CommodityCategory>();
    private List<CommoditySubCategory> mCommoditySubCategoriesList = new ArrayList<CommoditySubCategory>();
    private List<ChanceInfoBean> chanceList = new ArrayList<ChanceInfoBean>();//数据
    private AdapterView.OnItemClickListener mGoodsLeftListener = new AdapterView.OnItemClickListener() {
        @SuppressLint("ResourceAsColor")
        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position,
                                long arg3) {
            updateDataForRight(position);
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("categoryName", chanceList.get(position).getCategoryName());
            bundle.putString("categoryDes", chanceList.get(position).getCategoryDes());
            bundle.putString("categoryViews", chanceList.get(position).getCategoryViews());
            bundle.putString("categoryBkground", chanceList.get(position).getCategoryBkground());
            msg.setData(bundle);
            msg.what = 10000;
            handler.sendMessage(msg);
        }
    };
    private OnSelectListener mOnSelectListener;
    private AdapterView.OnItemSelectedListener mGoodsLeftSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View view, int position,
                                   long arg3) {
            mGoodsLeftAdapter.setSelectItem(position);
            mGoodsLeftAdapter.notifyDataSetInvalidated();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    };
    private String categoryid;

    private Handler handler;

    public String getShowText() {
        return showText;
    }

   /* public ViewMiddle(Context context, String distance) {
        super(context);
        mDistance = distance;
        for (int i = 0; i < itemsVaule.length; i++) {
            if (itemsVaule[i].equals(mDistance)) {
                showText = items[i];
                break;
            }
        }
        init(context);
    }*/

    public ViewMiddle(Context context,Handler handler,String categoryName) {
        super(context);
        this.handler = handler;
        this.categoryName = categoryName;
        init(context);
        requestCommodity();
        for (int i = 1; i < 3; i++) {
            initHeaderClassify(i);
        }
    }

    public ViewMiddle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ViewMiddle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        cityID = SharedPreferenceUtils.getCurrentCityInfo(context).getId();
        if ("".equals(cityID)) {
            cityID = "110100";
        }
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_distance, this, true);
        mGoodsLeftListView = (ListView) findViewById(R.id.id_goods_left_list);
        mGoodsLeftAdapter = new GoodsLeftAdapter(context, mCommodityCategoriesList);
        mGoodsLeftListView.setAdapter(mGoodsLeftAdapter);
        mGoodsLeftListView.setOnItemClickListener(mGoodsLeftListener);
        mGoodsLeftListView.setOnItemSelectedListener(mGoodsLeftSelectedListener);

        mGoodsRightListView = (ListView) findViewById(R.id.id_goods_right_list);
        mGoodsRightAdapter = new GoodsRightAdapter(context, mCommoditySubCategoriesList, this);
        mGoodsRightListView.setAdapter(mGoodsRightAdapter);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }
    public interface OnSelectListener {
        void getValue(String showText, String categoryid);
    }

    private void initDataForLeft() {
        mGoodsLeftAdapter.notifyDataSetChanged();
        for (int i = 0; i < mCommodityCategoriesList.size(); i++) {
           if (mCommodityCategoriesList.get(i).getCategoryName().equals(categoryName)){
               updateDataForRight(i);
           }
        }

    }

    private void updateDataForRight(int i) {
        if (mCommodityCategoriesList.get(i).getSubCategoryList().size() == 0) {
            mCommoditySubCategoriesList.clear();
            mGoodsRightAdapter.notifyDataSetChanged();
        } else {
            mCommoditySubCategoriesList.clear();
            mCommoditySubCategoriesList.addAll(mCommodityCategoriesList.get(i).getSubCategoryList());
            mGoodsRightAdapter.notifyDataSetChanged();
        }
    }

    //分类接口
    private void initHeaderClassify(final int i) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getCategoty");
            obj.put("classify",i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Log.d("==getCategoty==", new String(arg2));
                Gson gson = new Gson();
                GsonClassifyBack gsonClassifyBack = gson.fromJson(new String(arg2), new TypeToken<GsonClassifyBack>() {
                }.getType());
                String result = gsonClassifyBack.getResult();
                if (result.equals("0")) {
                    chanceList.addAll(gsonClassifyBack.getCategoryList());
                } else {

                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
            }
        });
    }


    private void requestCommodity(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd","getCommodityCategoty");
            obj.put("cityID",cityID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.d("requestCommodity param=" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] arg2) {
                LogUtils.d("ReleaseChoiceClassActivity requestCommodity--> result: "
                        + new String(arg2));
                try {
                    Gson gson = new Gson();
                    GsonCommodityBack gsonCommodityBack = gson.fromJson(new String(
                            arg2), new TypeToken<GsonCommodityBack>() {
                    }.getType());

                    String result = gsonCommodityBack.getResult();
                    if ("0".equals(result)) {
                        if (gsonCommodityBack
                                .getCommodityCategoryList().size() == 0) {
                        } else {
                            mCommodityCategoriesList.addAll(gsonCommodityBack
                                    .getCommodityCategoryList());
                            initDataForLeft();
                        }
                    } else {
                    }
                } catch (Exception e) {
                    LogUtils.e("requestCommodity error:", e);
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

    @Override
    public void onItemClick(String categoryID,String categoryName) {
        /*Intent intent = new Intent(this,
                ProductListActivity.class);
        intent.putExtra("categoryID", categoryID);
        intent.putExtra("cityID", cityID);
        intent.putExtra("where", 1);
        this.startActivity(intent);*/
        showText = categoryName;
        categoryid = categoryID;
        mOnSelectListener.getValue(showText,categoryid);
    }

}
