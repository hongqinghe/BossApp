package com.android.app.buystoreapp.location;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.adapter.AddressListAdapter;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.wallet.ToastUtil;

import java.util.ArrayList;

/**
 * $desc
 * Created by likaihang on 16/10/04.
 */
public class AddressDetailsActivity extends BaseAct implements TextWatcher, PoiSearch.OnPoiSearchListener, AdapterView.OnItemClickListener {
    private ListView list;
    private EditText edit;
    private AddressListAdapter adapter;
    private Context context;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private String city;
    private ArrayList<PoiItem> poiItems = new ArrayList<PoiItem>();
    private String street;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.address_datils_activity);
        context = this;
        city = getIntent().getExtras().getString("city");
        street = getIntent().getExtras().getString("street");
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        init();
    }

    private void init() {
        list = (ListView)findViewById(R.id.lv_details_list);
        edit = (EditText)findViewById(R.id.et_search_address);
        edit.addTextChangedListener(this);
        list.setOnItemClickListener(this);
        if (!TextUtils.isEmpty(street)) {
            query = new PoiSearch.Query(street, "", city);
            query.setPageSize(20);// 设置每页最多返回多少条poiitem
            query.setPageNum(0);//设置查询页码
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.searchPOIAsyn();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        startWhiteLoadingAnim();
        String str = s.toString().trim();
        if (!TextUtils.isEmpty(str)) {
            query = new PoiSearch.Query(str, "", city);
            query.setPageSize(20);// 设置每页最多返回多少条poiitem
            query.setPageNum(0);//设置查询页码
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.searchPOIAsyn();
        }else {
            Log.d("---street-----",street);
            query = new PoiSearch.Query(street, "", city);
            query.setPageSize(20);// 设置每页最多返回多少条poiitem
            query.setPageNum(0);//设置查询页码
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.searchPOIAsyn();
//            query = new PoiSearch.Query(" ", "", city);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        stopLoadingAnim();
        if (rCode == 1000) {
            if (result != null ) {
                poiItems = result.getPois();
                adapter = new AddressListAdapter(context,poiItems);
                list.setAdapter(adapter);
            }
        } else {
            ToastUtil.showMessageDefault(this, rCode+"");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String str = poiItems.get(position).toString();
        String lat = String.valueOf(poiItems.get(position).getLatLonPoint().getLatitude());
        String lon = String.valueOf(poiItems.get(position).getLatLonPoint().getLongitude());
        String cityinfo = poiItems.get(position).getProvinceName()+poiItems.get(position).getCityName()+poiItems.get(position).getAdName();
        String adName = poiItems.get(position).getAdName();
        Intent i = new Intent();
        i.putExtra("details",str);
        i.putExtra("lat",lat);
        i.putExtra("lon",lon);
        i.putExtra("cityinfo",cityinfo);
        i.putExtra("adname",adName);
        this.setResult(2,i);
        finish();
    }
}
