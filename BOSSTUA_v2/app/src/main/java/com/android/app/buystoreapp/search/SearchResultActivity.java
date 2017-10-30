package com.android.app.buystoreapp.search;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.FlowLayout;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.adapter.LnbListAdapter;
import com.android.app.buystoreapp.adapter.LpebListAdapter;
import com.android.app.buystoreapp.adapter.SearchLpdbListAdapter;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.CityInfoBean;
import com.android.app.buystoreapp.bean.LnbListBean;
import com.android.app.buystoreapp.bean.LpdbListBean;
import com.android.app.buystoreapp.bean.LpebListBean;
import com.android.app.buystoreapp.bean.SearchBean;
import com.android.app.buystoreapp.bean.UserInfoBean;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.buystoreapp.dao.SearchDao;
import com.android.app.buystoreapp.goods.ShopDetailInfoActivity;
import com.android.app.buystoreapp.managementservice.MymoreResourcesandServiceMainActivity;
import com.android.app.buystoreapp.other.CustomDialog;
import com.android.app.buystoreapp.setting.LoginActivity;
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
import java.util.Timer;
import java.util.TimerTask;


public class SearchResultActivity extends BaseAct implements View.OnClickListener, LpebListAdapter.OnAttentionListener
        ,SearchLpdbListAdapter.OnMyMoreClick,LnbListAdapter.OnAttentionListener{

    private SearchLpdbListAdapter searchLpdbListAdapter;
    private LpebListAdapter lpebListAdapter;
    private LnbListAdapter lnbListAdapter;
    //    private List<CommodityBean> scrollCommodityList = new ArrayList<CommodityBean>();
    private List<LpdbListBean> lpdbList = new ArrayList<LpdbListBean>();
    private List<LpebListBean> lpebList = new ArrayList<LpebListBean>();
    private List<LnbListBean> lnbList = new ArrayList<LnbListBean>();

    private ImageView search_back, delete_edit, search_clean;
    private LinearLayout tab_service, tab_person, tab_company, search_notes;
    private FlowLayout search_notes_content;
    private View tab_service_line, tab_person_line, tab_company_line;
    private EditText serach_edit;
    public static final int FLAG_LOGIN = 3;
    private int flag = 1;
    private List<String> list = new ArrayList<>();


    private String cityID;


    private static final int HANDLE_UPDATE_VIEW_LEFT = 101;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case HANDLE_UPDATE_VIEW_LEFT:
                    break;
                default:
                    break;
            }
        }
    };
    private String userId;
    private String proLong;
    private String proLat;
    private ListView mListView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLE_LPD:
                    lpebList.clear();
                    lnbList.clear();
                    searchLpdbListAdapter = new SearchLpdbListAdapter(mContext, lpdbList);
                    searchLpdbListAdapter.setOnMyMoreClick(SearchResultActivity.this);
                    mListView.setAdapter(searchLpdbListAdapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(mContext, ShopDetailInfoActivity.class);
                            intent.putExtra("proId",lpdbList.get(i).getProId());
                            intent.putExtra("proName",lpdbList.get(i).getProName());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });
                    break;
                case HANDLE_LPE:
                    lpdbList.clear();
                    lnbList.clear();
                    lpebListAdapter = new LpebListAdapter(mContext, lpebList);
                    lpebListAdapter.setOnAttentionListener(SearchResultActivity.this);
                    mListView.setAdapter(lpebListAdapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(mContext, MymoreResourcesandServiceMainActivity.class);
                            intent.putExtra("proUserId", lpebList.get(i).getUserid());
                            startActivity(intent);
                        }
                    });
                    break;
                case HANDLE_LNP:
                    lpdbList.clear();
                    lpebList.clear();
                   /* lnbListAdapter = new LnbListAdapter(mContext, lnbList);
                    mListView.setAdapter(lnbListAdapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(mContext, NewInfoActivity.class);
                            intent.putExtra("newsId",lnbList.get(i).getNewsId());
                            startActivity(intent);
                        }
                    });*/
                    lnbListAdapter = new LnbListAdapter(mContext, lnbList);
                    lnbListAdapter.setOnAttentionListener(SearchResultActivity.this);
                    mListView.setAdapter(lnbListAdapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(mContext, MymoreResourcesandServiceMainActivity.class);
                            intent.putExtra("proUserId", lnbList.get(i).getUserid());
                            startActivity(intent);
                        }
                    });
                    break;
                case UNATTENTION_OK:
                    //flag = 2;
                    load();
                   /* if (list.size() == 0) {
                        showEmpty("您还没有关注任何人");
                    }*/

                    ToastUtil.showMessageDefault(SearchResultActivity.this, resultNote);
//                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private static final int HANDLE_LPD = 1000;
    private static final int HANDLE_LPE = 2000;
    private static final int HANDLE_LNP = 3000;
    private String cityName;
    private Dialog dialog;
    private String resultNote;
    private static final int UNATTENTION_OK = 4000;
    private UserInfoBean mUserInfo;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_result);

        CityInfoBean cityInfoBean = SharedPreferenceUtils.getCurrentCityInfo(this);
        cityID = cityInfoBean.getId();
        if ("".equals(cityID)) {
            cityID = "110100";
        }
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        cityName = CrashApplication.cityName;
        proLat = "" + CrashApplication.latitude;//cityInfo.getCityLat();
        proLong = "" + CrashApplication.longitude;//cityInfo.getCityLong();
        initErrorPage();
        addIncludeLoading(true);
        initView();
    }

    @Override
    protected void load() {
        super.load();

        startWhiteLoadingAnim();
        mListView.setVisibility(View.VISIBLE);
        searchServe(serach_edit.getText().toString().trim());
    }

    private void searchServe(String antistop) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "searchServe");
            obj.put("thisUserId", userId);
            obj.put("antistop", antistop);
            obj.put("searchState", flag);
            obj.put("proLong", proLong);
            obj.put("proLat", proLat);
            obj.put("pageNum", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("搜索提交数据obj==", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Log.e("搜索返回数据 bytes==", new String(bytes));
                Gson gson = new Gson();
                SearchBean searchBean = gson.fromJson(new String(bytes),
                        new TypeToken<SearchBean>() {
                        }.getType());
                String result = searchBean.getResult();
                String resultNote = searchBean.getResultNote();
                switch (flag) {
                    case 1:
                        if ("0".equals(result)) {
                            lpdbList.clear();
                            if (searchBean.getBean().getLnbList() != null)
                                lpdbList.addAll(searchBean.getBean().getLpdbList());
                            if (lpdbList == null || lpdbList.size() == 0) {
                                Toast.makeText(SearchResultActivity.this, "未搜索到相关信息", Toast.LENGTH_SHORT).show();
                            }
                            mHandler.obtainMessage(HANDLE_LPD).sendToTarget();
                        }else {
                            Toast.makeText(SearchResultActivity.this, resultNote, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if ("0".equals(result)) {
                            lpebList.clear();
                            if (searchBean.getBean().getLpebList() != null)
                                lpebList.addAll(searchBean.getBean().getLpebList());
                            if (lpebList == null || lpebList.size() == 0) {
                                Toast.makeText(SearchResultActivity.this, "未搜索到相关信息", Toast.LENGTH_SHORT).show();
                            }
                            mHandler.obtainMessage(HANDLE_LPE).sendToTarget();
                        }else {
                            Toast.makeText(SearchResultActivity.this, resultNote, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        if ("0".equals(result)) {
                            lnbList.clear();
                            if (searchBean.getBean().getLnbList() != null)
                                lnbList.addAll(searchBean.getBean().getLnbList());
                            if (lnbList == null || lnbList.size() == 0) {
                                Toast.makeText(SearchResultActivity.this, "未搜索到相关信息", Toast.LENGTH_SHORT).show();
                            }
                            mHandler.obtainMessage(HANDLE_LNP).sendToTarget();
                        }else {
                            Toast.makeText(SearchResultActivity.this, resultNote, Toast.LENGTH_SHORT).show();
                        }
                        break;
                }


            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(SearchResultActivity.this, getResources().getString(R.string.service_error_hint));

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }

    private void initView() {
        search_back = (ImageView) findViewById(R.id.search_back);
        delete_edit = (ImageView) findViewById(R.id.delete_edit);
        search_clean = (ImageView) findViewById(R.id.search_clean);
        tab_service = (LinearLayout) findViewById(R.id.tab_service);
        tab_person = (LinearLayout) findViewById(R.id.tab_person);
        tab_company = (LinearLayout) findViewById(R.id.tab_company);
        search_notes = (LinearLayout) findViewById(R.id.search_notes);
        search_notes_content = (FlowLayout) findViewById(R.id.search_notes_content);
        tab_service_line = findViewById(R.id.tab_service_line);
        tab_person_line = findViewById(R.id.tab_person_line);
        tab_company_line = findViewById(R.id.tab_company_line);
        search_back.setOnClickListener(this);
        delete_edit.setOnClickListener(this);
        search_clean.setOnClickListener(this);
        tab_service.setOnClickListener(this);
        tab_person.setOnClickListener(this);
        tab_company.setOnClickListener(this);
        serach_edit = (EditText) findViewById(R.id.serach_edit);
        mListView = (ListView) findViewById(R.id.list);


        //让软键盘延迟弹出
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) serach_edit.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(serach_edit, 0);
            }
        }, 500);

        //点击软键盘上行回车键监听   /改变了回车键的样式，这个方法不能用，得用下边方法
        serach_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String text = serach_edit.getText().toString().trim();
                if (actionId == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent
                        .ACTION_DOWN) {   //不写这一行，会执行两次搜索
                    if (!TextUtils.isEmpty(text)) {
                        SearchDao searchDao = new SearchDao(mContext);
                        searchDao.insert(text);
                        list.add(text);
                        ToastUtil.showMessageDefault(mContext, "正在搜索");
                    } else {
                        ToastUtil.showMessageDefault(mContext, "请输入搜索内容");
                    }
                }
                return false;
            }
        });


        serach_edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String text = serach_edit.getText().toString().trim();
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent
                        .ACTION_DOWN) {
                    if (!TextUtils.isEmpty(text)) {
                        SearchDao searchDao = new SearchDao(mContext);
                        searchDao.insert(text);
                        //serach_edit失去焦点
                        serach_edit.setFocusable(false);
                        load();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        // 隐藏软键盘
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    } else {
                        ToastUtil.showMessageDefault(mContext, "请输入搜索内容");
                    }
                }
                return false;
            }
        });

        //当点击edittext时获取焦点,并弹出软键盘
        serach_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serach_edit.setFocusable(true);
                serach_edit.setFocusableInTouchMode(true);
                serach_edit.requestFocus();
                serach_edit.findFocus();

                InputMethodManager inputManager = (InputMethodManager) serach_edit.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(serach_edit, 0);
            }
        });


        //EditText文本发生变化时的监听
        serach_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    delete_edit.setVisibility(View.GONE);
                    SearchDao searchDao = new SearchDao(mContext);
                    list = searchDao.query();
                    if (list.size() > 0) {
                        if (list.size() > 10) {
                            list = list.subList(0, 10);
                        }
                        search_notes.setVisibility(View.VISIBLE);
                        search_notes_content.removeAllViews();
                        for (int i = 0; i < list.size(); i++) {
                            TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R
                                    .layout.flow_text, search_notes_content, false);
                            tv.setText(list.get(i));
                            final int finalI = i;
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getSearchTv(finalI);
                                }
                            });
                            search_notes_content.addView(tv);
                        }
                    }
                } else {
                    delete_edit.setVisibility(View.VISIBLE);
                    search_notes.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //获取和失去焦点时操作
        serach_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {  //得到焦点
//                    expandTabView.setVisibility(View.GONE);
                    if (serach_edit.length() == 0) { //输入框为空
                        SearchDao searchDao = new SearchDao(mContext);
                        list = searchDao.query();
                        if (list.size() > 0) {
                            if (list.size() > 10) {
                                list = list.subList(0, 10);
                            }
                            search_notes.setVisibility(View.VISIBLE);
                            search_notes_content.removeAllViews();
                            for (int i = 0; i < list.size(); i++) {
                                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R
                                        .layout.flow_text, search_notes_content, false);
                                tv.setText(list.get(i));
                                final int finalI = i;
                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getSearchTv(finalI);
                                    }
                                });
                                search_notes_content.addView(tv);
                            }
                        }
                    }
                } else {
                    search_notes.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back:   //返回键
                finish();
                break;
            case R.id.delete_edit:   //输入框的错号
                if (serach_edit.length() > 0) {
                    serach_edit.setText("");

                    serach_edit.setFocusable(true);
                    serach_edit.setFocusableInTouchMode(true);
                    serach_edit.requestFocus();
                    serach_edit.findFocus();

                    InputMethodManager inputManager = (InputMethodManager) serach_edit.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(serach_edit, 0);
                }
                break;
            case R.id.search_clean:   //搜索记录的清除按钮
                CustomDialog.initDialog(mContext);
                CustomDialog.tvTitle.setText("清除历史纪录不可恢复，是否确定清除?");
                CustomDialog.btnLeft.setText("确定");
                CustomDialog.btnRight.setText("取消");
                CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog.dialog.dismiss();
                        SearchDao searchDao = new SearchDao(mContext);
                        searchDao.delete();
                        list.clear();
                        search_notes.setVisibility(View.GONE);
                    }
                });
                CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog.dialog.dismiss();
                    }
                });

                break;
            case R.id.tab_service:
                mListView.setVisibility(View.GONE);
                if (tab_service_line.getVisibility() == View.INVISIBLE) {
                    serach_edit.setText("");
                }
                tab_service_line.setVisibility(View.VISIBLE);
                tab_person_line.setVisibility(View.INVISIBLE);
                tab_company_line.setVisibility(View.INVISIBLE);
                serach_edit.setHint("请输入你要找的资源、服务");
                flag = 1;
                break;
            case R.id.tab_person:
                mListView.setVisibility(View.GONE);
                if (tab_person_line.getVisibility() == View.INVISIBLE) {
                    serach_edit.setText("");
                }
                tab_service_line.setVisibility(View.INVISIBLE);
                tab_person_line.setVisibility(View.VISIBLE);
                tab_company_line.setVisibility(View.INVISIBLE);
//                expandTabView.setVisibility(View.GONE);
                serach_edit.setHint("请输入你要找的人脉、职业");
                flag = 2;
                break;
            case R.id.tab_company:
                mListView.setVisibility(View.GONE);
                /*lpdbList.clear();
                lpebList.clear();
                lnbList.clear();
                searchLpdbListAdapter.notifyDataSetChanged();
                lpebListAdapter.notifyDataSetChanged();
                lnbListAdapter.notifyDataSetChanged();*/
                if (tab_company_line.getVisibility() == View.INVISIBLE) {
                    serach_edit.setText("");
                }
                tab_service_line.setVisibility(View.INVISIBLE);
                tab_person_line.setVisibility(View.INVISIBLE);
                tab_company_line.setVisibility(View.VISIBLE);
//                expandTabView.setVisibility(View.GONE);
                serach_edit.setHint("请输入你要找的品牌、企业");
                flag = 3;
                break;
        }
    }


    //得到搜索记录
    private void getSearchTv(int i) {
//        startWhiteLoadingAnim();
//        searchServe(list.get(i).toString());
////        ToastUtil.showMessageDefault(mContext, list.get(i).toString());
        serach_edit.setText(list.get(i).toString().trim());
        serach_edit.setFocusable(true);
        serach_edit.setFocusableInTouchMode(true);
        serach_edit.requestFocus();
        serach_edit.findFocus();

        InputMethodManager inputManager = (InputMethodManager) serach_edit.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(serach_edit, 0);

    }

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

    @Override
    public void doCheck(int i) {
        if (isLogin) {
            showDeleteDialog(i);
        }else {
            ToastUtil.showMessageDefault(this,
                    getString(R.string.personal_no_login_toast));
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra("type", FLAG_LOGIN);
            startActivity(loginIntent);
        }
    }

    private void showDeleteDialog(final int i) {

        View layout = this.getLayoutInflater().inflate(R.layout.managenment_dialog, null);
        TextView tv_set_warning = (TextView) layout.findViewById(R.id.tv_set_warning);
        if (lpebList.get(i).getAttentionIsOff() == 0) {
            tv_set_warning.setText("您确定要关注对方?");
        } else {
            tv_set_warning.setText("您确定要取消关注?");
        }
        Button btn_true = (Button) layout.findViewById(R.id.btn_true);
        Button btn_false = (Button) layout.findViewById(R.id.btn_false);
        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAttention(i);
//                list.remove(i);
                dialog.dismiss();
                lpebListAdapter.notifyDataSetChanged();
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

    public void isAttention(int i) {
        startLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "isAttention");
            obj.put("thisUser", SharedPreferenceUtils.getCurrentUserInfo(this).getUserId());
//            obj.put("thisUser", "WzRxdVYMG6f4W57N");
            obj.put("proUser", lpebList.get(i).getUserid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.e("AttentionActivity/isAttention param=" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                LogUtils.e("AttentionActivity isAttention--> result: "
                        + new String(bytes));
                try {
                    String str = new String(bytes);
                    JSONObject obj = new JSONObject(str);
                    String result = (String) obj.get("result");
                    resultNote = (String) obj.get("resultNote");
                    if ("0".equals(result)) {
                        mHandler.obtainMessage(UNATTENTION_OK).sendToTarget();
                    }
                } catch (Exception e) {
                    LogUtils.e("AttentionActivity e" + e);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
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
    public void doCheck01(int i) {
        if (isLogin) {
            showDeleteDialog01(i);
        } else {
            ToastUtil.showMessageDefault(this,
                    getString(R.string.personal_no_login_toast));
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra("type", FLAG_LOGIN);
            startActivity(loginIntent);
        }
    }
    private void showDeleteDialog01(final int i) {

        View layout = this.getLayoutInflater().inflate(R.layout.managenment_dialog, null);
        TextView tv_set_warning = (TextView) layout.findViewById(R.id.tv_set_warning);
        if (lnbList.get(i).getAttentionIsOff() == 0) {
            tv_set_warning.setText("您确定要关注对方?");
        } else {
            tv_set_warning.setText("您确定要取消关注?");
        }
        Button btn_true = (Button) layout.findViewById(R.id.btn_true);
        Button btn_false = (Button) layout.findViewById(R.id.btn_false);
        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAttention01(i);
//                list.remove(i);
                dialog.dismiss();
                lnbListAdapter.notifyDataSetChanged();
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

    public void isAttention01(int i) {
        startLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "isAttention");
            obj.put("thisUser", SharedPreferenceUtils.getCurrentUserInfo(this).getUserId());
//            obj.put("thisUser", "WzRxdVYMG6f4W57N");
            obj.put("proUser", lnbList.get(i).getUserid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.e("AttentionActivity/isAttention param=" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                LogUtils.e("AttentionActivity isAttention--> result: "
                        + new String(bytes));
                try {
                    String str = new String(bytes);
                    JSONObject obj = new JSONObject(str);
                    String result = (String) obj.get("result");
                    resultNote = (String) obj.get("resultNote");
                    if ("0".equals(result)) {
                        mHandler.obtainMessage(UNATTENTION_OK).sendToTarget();
                    }
                } catch (Exception e) {
                    LogUtils.e("AttentionActivity e" + e);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
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
    protected void onResume() {
        super.onResume();
        mUserInfo = SharedPreferenceUtils.getCurrentUserInfo(this);
        isLogin = mUserInfo.isLogin();
    }

    @Override
    public void toDetailClick(int i) {
        Intent intent = new Intent(SearchResultActivity.this, ShopDetailInfoActivity.class);
        intent.putExtra("proId", lpdbList.get(i).getProId());
        intent.putExtra("proName", lpdbList.get(i).getProName());
        Log.e("proName", lpdbList.get(i).getProName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void toMyMoreClick(int i) {
        Intent intent = new Intent(SearchResultActivity.this, MymoreResourcesandServiceMainActivity.class);
        intent.putExtra("proUserId",lpdbList.get(i).getUserId() );
        startActivity(intent);
    }


}
