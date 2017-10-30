package com.android.app.buystoreapp.location;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.CityInfoBean;
import com.android.app.buystoreapp.bean.GsonCityBack;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.buystoreapp.widget.CharacterParser;
import com.android.app.buystoreapp.widget.ClearEditText;
import com.android.app.buystoreapp.widget.PinyinComparator;
import com.android.app.buystoreapp.widget.SideBar;
import com.android.app.buystoreapp.widget.SideBar.OnTouchingLetterChangedListener;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityActivity extends BaseAct {
    @ViewInject(R.id.filter_edit)
    private ClearEditText mClearEditText;
    //    @ViewInject(R.id.id_city_search_del)
//    private ImageView mSearchDel;
    @ViewInject(R.id.id_city_list)
    private ListView sortListView;
    private List<CityInfoBean> cityLists = new ArrayList<CityInfoBean>();

    @ViewInject(R.id.id_empty_fail)
    private View emptyFailureView;
    @ViewInject(R.id.id_search_empty)
    private View emptyView;
    private String city = "";
    private String lon = "";
    private String lat = "";
    private String cityID = "";
    private int cityLevel = 2;
    LayoutInflater mInflater;
    private SideBar sideBar;
    private TextView dialog;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
//    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
//    List<CityInfoBean> searchCitys;

    private static final int HANLDE_INIT_LISTVIEW = 0xc1;
    protected static final int HANDLE_LOCATION_SUCCESS = 0xc2;
    private SortAdapter mCityAdapter;
    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case HANLDE_INIT_LISTVIEW:
                    mCityAdapter = new SortAdapter(cityLists);
                    sortListView.setAdapter(mCityAdapter);
                    break;
                case HANDLE_LOCATION_SUCCESS:
                    if (!isLocationInit) {
                        pb.setVisibility(View.GONE);
                        iv.setVisibility(View.VISIBLE);
                        locationText.setText(city);
                    }
                    break;
            }
        }

    };

    private BroadcastReceiver cityReceiver = new BroadcastReceiver() {
        public void onReceive(Context arg0, Intent intent) {
            if (intent != null) {
                Log.d("mikes", "city receiver, city=" + city);
                city = intent.getStringExtra("cityName");
                lon = intent.getStringExtra("cityLon");
                lat = intent.getStringExtra("cityLat");
                myHandler.sendEmptyMessage(HANDLE_LOCATION_SUCCESS);

                // initCityDatas();
            }
        }
    };

    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;
    private TextView locationText;
    private ProgressBar pb;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.city_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_action_bar);
        ViewUtils.inject(this);
        mTitleText.setText("选择您的城市");

        Intent cityService = new Intent(this, CityService.class);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.app.getCityName");
        registerReceiver(cityReceiver, filter);
        startService(cityService);
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        initViews();
        sortListView.addHeaderView(initHeaderView());
        InitLocation();
//        sortListView.setOnItemClickListener(itemListener);
        sortListView.setEmptyView(emptyView);
//        mSearchEdit.addTextChangedListener(new SearchWatcher());

    }

    private void initViews() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mCityAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });
        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(getApplication(), ((CityInfoBean)mCityAdapter.getItem(position-1)).getAreaname(), Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    ClickOK();
                    return;
                }
                city = ((CityInfoBean)mCityAdapter.getItem(position-1)).getAreaname();
                cityID = ((CityInfoBean)mCityAdapter.getItem(position-1)).getId();
                ClickOK();
            }
        });
        load();

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<CityInfoBean> filterDateList = new ArrayList<CityInfoBean>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = cityLists;
        } else {
            filterDateList.clear();
            for (CityInfoBean sortModel : cityLists) {
                String name = sortModel.getAreaname();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
//        mCityAdapter = new SortAdapter(cityLists);
        mCityAdapter.updateListView(filterDateList);
//        sortListView.setOnItemClickListener(searchItemClickListener);
    }

    private void InitLocation() {
        if (!TextUtils.isEmpty(CrashApplication.cityName)) {
            pb.setVisibility(View.GONE);
            iv.setVisibility(View.VISIBLE);
            locationText.setText(city);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(cityReceiver);
        super.onDestroy();
    }

    @OnClick(R.id.id_custom_back_image)
    public void onCustomBarBackClicked(View v) {
        switch (v.getId()) {
            case R.id.id_custom_back_image:
                setResult(RESULT_CANCELED);
                this.finish();
                break;
            default:
                break;
        }
    }

   /* @OnClick(R.id.id_city_search)
    public void onDelClicked(View v) {
        if (mSearchEdit.length() > 0) {
            mSearchEdit.setText("");
            mCityAdapter = new CityAdapter(cityLists);
            sortListView.setAdapter(mCityAdapter);
            sortListView.setOnItemClickListener(itemListener);
        }
    }*/

    public void ClickOK() {
        Intent data = new Intent();
        data.putExtra("cityName", city);
        data.putExtra("cityLon", lon);
        data.putExtra("cityLat", lat);
        data.putExtra("cityID", cityID);
        data.putExtra("level", cityLevel);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        this.finish();
        super.onBackPressed();
    }

    private void initCityDatas() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd","getCity");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(this, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                stopLoadingAnim();
                hideErrorPageState();
                try {
                    Gson gson = new Gson();
                    GsonCityBack gsonCityBack = gson.fromJson(
                            new String(arg2),
                            new TypeToken<GsonCityBack>() {
                            }.getType());

                    String result = gsonCityBack.getResult();

                    if ("1".equals(result)) {// fail
                        showErrorPageState(SERVEICE_ERR_FLAG);
                        emptyView.setVisibility(View.GONE);
                        sortListView.setEmptyView(emptyFailureView);
                    } else {
                        cityLists.clear();
                        PYSort(gsonCityBack.getCityList());
                        // 根据a-z进行排序源数据
                        Collections.sort(cityLists, pinyinComparator);

                        //cityLists.addAll(gsonCityBack.getCityList());
                        myHandler
                                .sendEmptyMessage(HANLDE_INIT_LISTVIEW);
                    }
                } catch (Exception e) {
                    LogUtils.e("initCityDatas error:", e);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }
    private void PYSort(List<CityInfoBean> citys){
        CharacterParser cp = new CharacterParser();
    	Map<String,List<CityInfoBean>> index = new HashMap<String,List<CityInfoBean>>();
    	for(CityInfoBean bean : citys){
    		String py = cp.convert(bean.getAreaname().trim().substring(0, 1).trim());
            String sortString = py.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                bean.setSortLetters(sortString.toUpperCase());
            }else{
                bean.setSortLetters("#");
            }
    		if(!index.containsKey(sortString)){
    			index.put(sortString, new ArrayList<CityInfoBean>());
    		}
    		List<CityInfoBean> items = index.get(sortString);
    		items.add(bean);
    		index.put(sortString, items);
    	}

    	String[] chars = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    	for(String str : chars){
    		if(!index.containsKey(str)){continue;}
    		List<CityInfoBean> items = index.get(str);
            cityLists.addAll(items);
    	}
    	
    }

    private boolean isLocationInit = true;

    private View initHeaderView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View headView = inflater.inflate(R.layout.city_header, null);
        /*headView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                128));*/
        locationText = (TextView) headView
                .findViewById(R.id.id_city_head_current_city);
        // locationText.setText(city);
        pb = (ProgressBar) headView
                .findViewById(R.id.progressBar1);
        iv = (ImageView) headView
                .findViewById(R.id.id_city_head_img);
        if (TextUtils.isEmpty(city)) {
            pb.setVisibility(View.VISIBLE);
            iv.setVisibility(View.GONE);
            isLocationInit = false;
        } else {
            pb.setVisibility(View.GONE);
            iv.setVisibility(View.VISIBLE);
            isLocationInit = true;
        }

        String citygroup[][] = {
                {"北京", "上海", "广州", "深圳", "天津"},
                {"重庆", "郑州", "成都", "杭州", "石家庄"},
                {"长沙", "南京", "青岛", "合肥", "太原"},
                {"南昌", "沈阳", "哈尔滨", "长春", "西安"},
                {"福州", "昆明", "南宁", "乌鲁木齐", "兰州"}};
        String cityids[][] = {
                {"110100", "310100", "440100", "440300", "120100"},
                {"500100", "410100", "510100", "330100", "130100"},
                {"430100", "320100", "370200", "340100", "140100"},
                {"360100", "210100", "230100", "220100", "610100"},
                {"350100", "530100", "450100", "650100", "620100"}};
        LinearLayout hotCityLayout = (LinearLayout) headView.findViewById(R.id.hotCityLayout);
        for (int i = 0; i < citygroup.length; ++i) {
            View hot_layout = inflater.inflate(R.layout.hot_city_layout, null);
            int ids[] = {R.id.hotBtn1, R.id.hotBtn2, R.id.hotBtn3, R.id.hotBtn4, R.id.hotBtn5};
            for (int j = 0; j < 5; ++j) {
                if (!TextUtils.isEmpty(citygroup[i][j])) {
                    hot_layout.findViewById(ids[j]).setVisibility(View.VISIBLE);
                    ((Button) hot_layout.findViewById(ids[j])).setText(citygroup[i][j]);
                } else {
                    hot_layout.findViewById(ids[j]).setVisibility(View.INVISIBLE);
                }
                hot_layout.findViewById(ids[j]).setTag(R.id.hot_city_item_first, cityids[i][j]);
                hot_layout.findViewById(ids[j]).setTag(R.id.hot_city_item_second, citygroup[i][j]);
                hot_layout.findViewById(ids[j]).setOnClickListener(hotClickListener);
            }
            hotCityLayout.addView(hot_layout);
        }

        return headView;
    }

    private OnClickListener hotClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stu
            cityID = (String) v.getTag(R.id.hot_city_item_first);
            city = (String) v.getTag(R.id.hot_city_item_second);
            cityLevel = 2;
            ClickOK();
        }
    };

    private OnItemClickListener itemListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                long arg3) {
            if (position == 0) {
                ClickOK();
                return;
            }
            city = cityLists.get(position - 1).getAreaname();
            /*lon = cityLists.get(position - 1).getCityLong();
            lat = cityLists.get(position - 1).getCityLat();*/
            cityID = cityLists.get(position - 1).getId();
            cityLevel = cityLists.get(position-1).getLevel();
            ClickOK();
        }
    };

    private OnItemClickListener searchItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                long arg3) {
            if (position == 0) {
               /* ClickOK();
                return;*/
                if (TextUtils.isEmpty(city)) {
                    //Toast.makeText(CityActivity.this,"", duration)
                    return;
                } else {
                    ClickOK();
                    return;
                }

            }
           
           /* lon = searchCitys.get(position - 1).getCityLong();
            lat = searchCitys.get(position - 1).getCityLat();*/
            city = cityLists.get(position - 1).getAreaname();
            cityID = cityLists.get(position - 1).getId();
            cityLevel = cityLists.get(position - 1).getLevel();
            ClickOK();
        }
    };

    class SortAdapter extends BaseAdapter implements SectionIndexer {
        private List<CityInfoBean> list = null;
        public SortAdapter(List<CityInfoBean> list) {
            this.list = list;
        }

        public void updateListView(List<CityInfoBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        public int getCount() {
            return this.list.size();
        }

        public Object getItem(int position) {
            return list.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup arg2) {
            ViewHolder viewHolder = null;
            final CityInfoBean mContent = list.get(position);
            if (view == null) {
                viewHolder = new ViewHolder();
                view = mInflater.inflate(R.layout.city_adapter, null);
                viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
                viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            int section = getSectionForPosition(position);

            if (position == getPositionForSection(section)) {
                viewHolder.tvLetter.setVisibility(View.VISIBLE);
                viewHolder.tvLetter.setText(mContent.getSortLetters());
            } else {
                viewHolder.tvLetter.setVisibility(View.GONE);
            }

            viewHolder.tvTitle.setText(this.list.get(position).getAreaname());

            return view;

        }

        class ViewHolder {
            TextView tvLetter;
            TextView tvTitle;
        }

        /**
         * 根据ListView的当前位置获取分类的首字母的Char ascii值
         */
        public int getSectionForPosition(int position) {
            return list.get(position).getSortLetters().charAt(0);
        }
        /**
         * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
         */
        public int getPositionForSection(int section) {
            for (int i = 0; i < getCount(); i++) {
                String sortStr = list.get(i).getSortLetters();
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }

            return -1;
        }

        private String getAlpha(String str) {
            String sortStr = str.trim().substring(0, 1).toUpperCase();
            if (sortStr.matches("[A-Z]")) {
                return sortStr;
            } else {
                return "#";
            }
        }

        @Override
        public Object[] getSections() {
            return null;
        }
    }

    @Override
    protected void load() {
        initCityDatas();
    }
}
