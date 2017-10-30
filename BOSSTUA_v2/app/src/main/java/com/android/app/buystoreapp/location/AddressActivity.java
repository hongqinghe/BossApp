package com.android.app.buystoreapp.location;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.RelaseDataBean;
import com.android.app.buystoreapp.other.AreaBean;
import com.android.app.buystoreapp.other.AreaCmdBean;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/10/04.
 */
public class AddressActivity extends BaseAct implements AdapterView.OnItemClickListener {
    private ListView listview;
    private AreaCmdBean provinceBeen;
    private String provinceName, cityName, areaName;
    private List<AreaBean.AreasListBean> provinceLists = new ArrayList<AreaBean.AreasListBean>();
    private Context context;
    private ChoiceListAdapter adapter;
    private int cityid = 0;
    private int level = 1;
    private TextView title;
    private int areaId;
    private int provinceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.address_layout);
        context = this;
        initView();
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        load();
    }

    @Override
    protected void load() {
        getProvince(cityid, level);
    }

    private void initView() {
        listview = (ListView) findViewById(R.id.lv_address_container);
        listview.setOnItemClickListener(this);
        adapter = new ChoiceListAdapter(context, provinceLists);
        listview.setAdapter(adapter);
        title = (TextView) findViewById(R.id.tv_comment_title);
        title.setText("请选择所需地区");
        findViewById(R.id.ib_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取省列表
     */
    private void getProvince(int id, int level) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd","selectAddress");
            obj.put("id",id);
            obj.put("level",level);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                try {
                    AreaBean AreaBean = gson.fromJson(new String(bytes), new TypeToken<AreaBean>() {
                    }.getType());
                    String result = AreaBean.getResult();
                    String resultNote = AreaBean.getResultNote();
                    if (result.equals("0")) {
                        provinceLists.clear();
                        provinceLists.addAll(AreaBean.getAreasList());
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showMessageDefault(context, resultNote);
                    }
                } catch (Exception e) {
                    ToastUtil.showMessageDefault(context, "没有更多的数据了!!!");
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable
                    throwable) {
                stopLoadingAnim();
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {

            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int leve = provinceLists.get(position).getLevel();
        if (leve == 1) {
            provinceName = provinceLists.get(position).getAreaname();
            title.setText(provinceName);
//            cityid = provinceLists.get(position).getId();
            provinceId = provinceLists.get(position).getId();
            level = 2;
            getProvince(provinceId, level);
        } else if (leve == 2) {
            cityName = provinceLists.get(position).getAreaname();
            title.setText(cityName);
            cityid = provinceLists.get(position).getId();
            RelaseDataBean relaseDataBean = new RelaseDataBean();
            relaseDataBean.setAreasId2(String.valueOf(cityid));
            level = 3;
            getProvince(cityid, level);
        } else {
            areaName = provinceLists.get(position).getAreaname();
            areaId = provinceLists.get(position).getId();
            Intent i = new Intent();
            i.putExtra("result", provinceName + cityName + areaName);
            i.putExtra("cityName", areaName);
            i.putExtra("provinceId",provinceId);
            i.putExtra("cityid",cityid);
            i.putExtra("areaId", areaId);
            this.setResult(1, i);
            finish();
        }
    }

    private class ChoiceListAdapter extends BaseAdapter {
        private Context ctx;
        private List<AreaBean.AreasListBean> list;

        public ChoiceListAdapter(Context context, List<AreaBean.AreasListBean> provinceLists) {
            this.ctx = context;
            this.list = provinceLists;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(ctx, R.layout.listview_item, null);
                holder.poititle = (TextView) convertView
                        .findViewById(R.id.gridview_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String item = list.get(position).getAreaname();
            holder.poititle.setText(item);
            return convertView;
        }

        private class ViewHolder {
            TextView poititle;
        }
    }
}
