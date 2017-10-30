package com.android.app.buystore.utils.expandtab;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
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

public class ViewLeft extends RelativeLayout implements ViewBaseAction, AdapterView.OnItemClickListener {

    private ListView listview;
    private String[] items;
    private String[] itemsVaule;
    private OnSelectListener mOnSelectListener;
    //    private TextAdapter adapter;
    private String mDistance = "1";
    private static String showText = "全国";
    private int cityid = 0;
    private int level = 1;
    private String cityID;
    private String url;
    private ArrayList<String> mCategoryNameList = new ArrayList<String>();
    private ArrayList<String> mCategoryValueList = new ArrayList<String>();
    private Context mContext;
    private String provinceName, cityName, areaName;
    private List<AreaBean.AreasListBean> provinceLists = new ArrayList<AreaBean.AreasListBean>();
    private Context context;
    private AreaCmdBean provinceBeen;
    private ChoiceListAdapter adapter;

    public String getShowText() {
        return showText;
    }

    public ViewLeft(Context context) {
        super(context);
        init(context);
    }

    public ViewLeft(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ViewLeft(Context context, AttributeSet attrs) {
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
        inflater.inflate(R.layout.view_left, this, true);
//        setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_left));
        listview = (ListView) findViewById(R.id.listView);
        listview.setOnItemClickListener(this);
        adapter = new ChoiceListAdapter(context, provinceLists);
        listview.setAdapter(adapter);
        getProvince(cityid, level);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int leve = provinceLists.get(position).getLevel();
        if (leve == 1) {
            provinceName = provinceLists.get(position).getAreaname();
            cityid = provinceLists.get(position).getId();
            level = 2;
            getProvince(cityid, level);
        } else if (leve == 2) {
            cityName = provinceLists.get(position).getAreaname();
            cityid = provinceLists.get(position).getId();
            RelaseDataBean relaseDataBean = new RelaseDataBean();
            relaseDataBean.setAreasId2(String.valueOf(cityid));
            level = 3;
            getProvince(cityid, level);
        } else {
            showText = provinceLists.get(position).getAreaname();
            String streetid = String.valueOf(provinceLists.get(position).getId());
            mOnSelectListener.getValue(showText, streetid);
            cityid = 0;
            level = 1;
            getProvince(cityid, level);
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

    public interface OnSelectListener {
        void getValue(String showText, String streetid);

        void initValue(String shopCategory, String showText);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (TextUtils.isEmpty(showText)) {
                getProvince(cityid, level);
            } else {
                handler.removeMessages(0);
            }
        }

    };
    //private String initShopCategory;

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }
   /* private void updateItems() {
        int len = mCategoryValueList.size();
        if(mOnSelectListener != null){
        	mOnSelectListener.initValue(mCategoryValueList.get(0), mCategoryNameList.get(0));
        }
        items = new String[len];
        itemsVaule = new String[len];
      //  initShopCategory = mCategoryValueList.get(0);
        for (int i = 0; i < len; i++) {
            items[i] = mCategoryNameList.get(i);
            itemsVaule[i] = mCategoryValueList.get(i);
        }
        if (len >= 1) {
            mDistance = itemsVaule[0];
            showText = items[0];
        }

        adapter = new TextAdapter(mContext, items,
                R.drawable.choose_item_right,
                R.drawable.choose_eara_item_selector);
        adapter.setTextSize(17);
        if (mDistance != null) {
            for (int i = 0; i < itemsVaule.length; i++) {
                if (itemsVaule[i].equals(mDistance)) {
                    adapter.setSelectedPositionNoNotify(i);
                    showText = items[i];
                    break;
                }
            }
        }

        listview.setAdapter(adapter);
        adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                if (mOnSelectListener != null) {
                    showText = items[position];
                    mOnSelectListener.getValue(itemsVaule[position],
                            items[position]);
                }
            }
        });
        adapter.notifyDataSetChanged();
    }*/

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
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {

            }
        });
    }
}
