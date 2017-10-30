package com.android.app.buystoreapp;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.app.buystore.utils.BadgeView;
import com.android.app.buystore.utils.CustomLinearlayout;
import com.android.app.buystore.utils.ITransferListener;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.base.UpdateApkService;
import com.android.app.buystoreapp.base.VersionManagerService;
import com.android.app.buystoreapp.bean.CityInfoBean;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.buystoreapp.location.CityActivity;
import com.android.app.buystoreapp.search.SearchResultActivity;
import com.android.app.buystoreapp.setting.LoginActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.bystoreapp.dialog.RelaseDialog;
import com.hyphenate.chat.EMClient;
import com.lidroid.xutils.util.LogUtils;
import com.umeng.socialize.utils.Log;

import java.lang.reflect.Method;

public class BossBuyActivity extends BaseAct implements
        OnClickListener, ITransferListener, RadioGroup.OnCheckedChangeListener {
    private static final int HANDLE_REQUEST_CITY = 0x10;
    private static final int BAIDU_LOCATION = 0x11;

    /**
     * the five tabs of bottom
     */

    private CustomLinearlayout mTabHomeLayout;
    private CustomLinearlayout mTabGoodsLayout;
    private CustomLinearlayout mTabBusinessLayout;
    private CustomLinearlayout mTabNewsLayout;
    private CustomLinearlayout mTabPersonalLayout;

    private View mTopHomeLayout;
    private View mTopNewsLayout;
    private View mTopSettingLayout;

    /**
     * the top views
     */
    private View cityContainer;
    private TextView mTextCity;
    private View mGoodsSearch;
    private ImageView mTextLocation;
    /**
     * the navigation line of tab's bottom
     */
    private int currentIndex;
    public int screenWidth;
    public int screenHeight;

    private static final String TAG_HOME = "home";
    private static final String TAG_GOODS = "goods";
    private static final String TAG_BUSINESS = "business";
    private static final String TAG_NEWS = "news";
    private static final String TAG_PERSONAL = "personal";

    int flag = 0;
    long exitTime = 0;

    private String cityName = "";
    private String cityLon = "";
    private String cityLat = "";
    private String cityID = "";
    Intent cityService = new Intent();
    private boolean isRegister = false;

    private BroadcastReceiver cityReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String cityName = intent.getStringExtra("cityName");
                String cityLon = intent.getStringExtra("cityLon");
                String cityLat = intent.getStringExtra("cityLat");
                String cityID = intent.getStringExtra("cityID");
                int cityLevel = intent.getIntExtra("level", 2);
                LogUtils.d("city receiver, city=-" + cityName + ",cityLon="
                        + cityLon + ",cityLat=" + cityLat + ",cityID=" + cityID);
                Log.d("lulu", "接受定位广播");
                checkCurrentCity(cityName, cityLon, cityLat, cityID, cityLevel);
            }
        }
    };
    private String titlecontent;
    private RadioGroup mTopTitleGroup;
    private RadioButton mService;
    private RadioButton mResource;
    private FragmentTransaction ftt;
    private int messageNum = 12;
    private BadgeView badge1;
    private boolean islogin;
    private int cityLevel;
    //    private NewMessageReciver reciver;
    private LinearLayout view;
    private AlertDialog dialog;
    private Class clazz = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_buy_store);
        if (SharedPreferenceUtils.getAppIsFirstLaunch(this)) {
//            findViewById(R.id.FirstImageView).setVisibility(View.GONE);
            Intent intent = new Intent(this, CityActivity.class);
            startActivityForResult(intent, HANDLE_REQUEST_CITY);
        } else {
//            findViewById(R.id.FirstImageView).setVisibility(View.VISIBLE);
        }
        SharedPreferenceUtils.saveAppWhenFirstLaunch(this, false);
//        findViewById(R.id.FirstImageView).setOnClickListener(this);

        /*//启动UMeng消息推送
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.enable(new IUmengRegisterCallback() {

            @Override
            public void onRegistered(String arg0) {
                Log.e("------------->umeng push agent", arg0);
            }
        });
        mPushAgent.enable();
        Log.e("------------->umeng push agent", mPushAgent.getRegistrationId());*/
        //mPushAgent.onAppStart();
        startAppVersionUpdate();
        initView();
        initScreenWH();
        initFragments();

        if (!isRegister) {
            LogUtils.d("register location receiver");
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.android.app.getCityName");
            registerReceiver(cityReceiver, filter);
            isRegister = true;
            cityService.setAction("com.android.app.buystoreapp.location");
            cityService.setPackage(getPackageName());
            startService(cityService);//模拟器运行时需禁掉
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        LogUtils.d("create end");
    }

    /*
         * 启动应用版本更新服务
         */
    private void startAppVersionUpdate() {
        // 启动应用静默下载安装服务
//        Intent intent = new Intent(this, VersionManagerService.class);
        Intent intent = new Intent(this, UpdateApkService.class);
        this.startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        islogin = SharedPreferenceUtils.getCurrentUserInfo(this).isLogin();

        Log.d("---", "messageNum=" + messageNum);
        /*reciver = new NewMessageReciver();
        //创建一个获取消息的过滤器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.app.newMessage");
        registerReceiver(reciver, intentFilter);*/

        if (islogin) {
            messageNum = EMClient.getInstance().chatManager().getUnreadMsgsCount();
            view = (LinearLayout) mTabNewsLayout.findViewById(R.id.id_custom_layout_container);
            badgeViewNotify(mTabNewsLayout);
        }

    }

    private void badgeViewNotify(View view) {
        if (badge1 == null) {
            badge1 = new BadgeView(this, view); //创建一个BadgeView对象，view为你需要显示提醒的控件
        }
        if (messageNum > 0) {
            badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT); //显示的位置
            badge1.setTextColor(Color.WHITE);  //文本颜色
            badge1.setBadgeBackgroundColor(Color.RED);  //背景颜色
            badge1.setTextSize(11); //文本大小
            badge1.setBadgeMargin(0, 0); //水平和竖直方向的间距
            badge1.setText(messageNum + ""); //显示类容
            badge1.show();
        } else {
            badge1.hide();
        }
    }

    @Override
    protected void onPause() {
        if (isRegister) {
            isRegister = false;
            LogUtils.d("unregister location receiver");
            unregisterReceiver(cityReceiver);
        }
//        unregisterReceiver(reciver);
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.d("onNewIntent");
        initFragments();
        updateBottomView(0);
        updateTopView(0);
    }

    private void initView() {
        LogUtils.d("BuyStoreActivity initView..");
        mTabHomeLayout = (CustomLinearlayout) findViewById(R.id.id_tab_home);
        mTabGoodsLayout = (CustomLinearlayout) findViewById(R.id.id_tab_goods);
        mTabBusinessLayout = (CustomLinearlayout) findViewById(R.id.id_tab_business);
        mTabNewsLayout = (CustomLinearlayout) findViewById(R.id.id_tab_news);
        mTabPersonalLayout = (CustomLinearlayout) findViewById(R.id.id_tab_personal);

        mTopHomeLayout = findViewById(R.id.id_top_home);
        mTopNewsLayout = findViewById(R.id.id_top_news);
        mTopSettingLayout = findViewById(R.id.id_top_setting);

        mTopTitleGroup = (RadioGroup) findViewById(R.id.rg_home_title);
        mService = (RadioButton) findViewById(R.id.rb_home_title_service);//特价服务
        mResource = (RadioButton) findViewById(R.id.rb_home_title_resource);//闲置资源

        cityContainer = findViewById(R.id.id_container_city);
        mTextCity = (TextView) findViewById(R.id.id_city);

        mTextCity.setText(TextUtils.isEmpty(getLastCity().getAreaname()) ? "北京市"
                : getLastCity().getAreaname());

        mGoodsSearch = findViewById(R.id.id_goods_search);
        mTextLocation = (ImageView) findViewById(R.id.id_location);
        mTabHomeLayout.setOnClickListener(this);
        mTabGoodsLayout.setOnClickListener(this);
        mTabBusinessLayout.setOnClickListener(this);
        mTabNewsLayout.setOnClickListener(this);
        mTabPersonalLayout.setOnClickListener(this);

        cityContainer.setOnClickListener(this);
        mGoodsSearch.setOnClickListener(this);
        mTextLocation.setOnClickListener(this);

        mTopTitleGroup.setOnCheckedChangeListener(this);
    }

    private void initScreenWH() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        screenHeight = outMetrics.heightPixels;
    }

    private void initFragments() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.id_fragment_container, new TabHome(), TAG_HOME);
        ft.commit();
    }

    /**
     * @param currentCity
     * @param currentCityLon
     * @param currentCityLat
     * @param currentCityID
     */
    private void saveCurrentCity(String currentCity, String currentCityLon,
                                 String currentCityLat, String currentCityID, int cityLevel) {
        LogUtils.d("saveCurrentCity" + ",currentCity=" + currentCity
                + ",currentLon=" + currentCityLon + ",currentCityLat="
                + currentCityLat + ",CityID=" + currentCityID);
        CityInfoBean cityInfoBean = new CityInfoBean();
        cityInfoBean.setAreaname(currentCity);
        cityInfoBean.setCityLong(currentCityLon);
        cityInfoBean.setCityLat(currentCityLat);
        cityInfoBean.setId(currentCityID);
        cityInfoBean.setLevel(cityLevel);
        SharedPreferenceUtils.saveCurrentCityInfo(this, cityInfoBean);
    }

    private CityInfoBean getLastCity() {
        return SharedPreferenceUtils.getCurrentCityInfo(this);
    }

    private void checkCurrentCity(final String currentCity,
                                  final String currentLon, final String currentLat,
                                  final String currentCityID, final int currentLevel) {
        CityInfoBean lastCity = getLastCity();
        String latCityName = lastCity.getAreaname();
        String lastCityLon = lastCity.getCityLong();
        String lastCityLat = lastCity.getCityLat();
        String lastCityID = lastCity.getId();
        int lastLevel = lastCity.getLevel();
        LogUtils.d("checkCurrentCity lastCityName=" + latCityName
                + ",currentCityName=" + currentCity);
        if (!TextUtils.isEmpty(currentCity)) {
            if (!latCityName.equals(currentCity)) {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(
                                getResources()
                                        .getString(R.string.city_change_title))
                        .setMessage(
                                String.format(
                                        getResources().getString(
                                                R.string.city_change_message),
                                        currentCity))
                        .setPositiveButton(
                                getResources().getString(R.string.btn_ok),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                        cityName = currentCity;
                                        cityLon = currentLon;
                                        cityLat = currentLat;
                                        mTextCity.setText(currentCity);
                                        saveCurrentCity(cityName, cityLon, cityLat, "", 2);
//                                        getCityIDFromHttp(cityName, cityLat, cityLon);
                                    }
                                })
                        .setNegativeButton(
                                getResources().getString(R.string.btn_cancle), null)
                        .create();
                dialog.show();
            } else {
                cityName = latCityName;
                cityLon = lastCityLon;
                cityLat = lastCityLat;
                cityID = lastCityID;
                cityLevel = lastLevel;
                mTextCity.setText(latCityName);
//                getCityIDFromHttp(cityName, cityLat, cityLon);
            }
        } else {
            dialog = new AlertDialog.Builder(this)
                    .setTitle("定位检测")
                    .setMessage("系统定位失败，请检查网络和GPS设置")
                    .setPositiveButton(
                            getResources().getString(R.string.btn_ok),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    OpenNotify();
                                }
                            })
                    .setNegativeButton(
                            getResources().getString(R.string.btn_cancle), null)
                    .create();
            dialog.show();
        }
    }

    /**
     * 打开系统通知栏
     *
     * @author likaihang
     * creat at @time 16/10/13 10:44
     */
    public void OpenNotify() {
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        try {
            Object service = getSystemService("statusbar");
            Class<?> statusbarManager = Class
                    .forName("android.app.StatusBarManager");
            Method expand = null;
            if (service != null) {
                if (currentApiVersion <= 16) {
                    expand = statusbarManager.getMethod("expand");
                } else {
                    expand = statusbarManager
                            .getMethod("expandNotificationsPanel");
                }
                expand.setAccessible(true);
                expand.invoke(service);
            }

        } catch (Exception e) {
        }

    }

    @Override
    public void onClick(View view) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit);
        int id = view.getId();
        switch (id) {
            case R.id.id_tab_home:
                updateTopTitle(getResources().getString(R.string.top_search_all));
                currentIndex = 0;
                flag = 0;
                transaction.replace(R.id.id_fragment_container, new TabHome(), TAG_HOME);
                break;
            case R.id.id_tab_goods://发现
                updateTopNewsTitle(getResources().getString(R.string.found));
                currentIndex = 1;
                flag = 1;
                transaction.replace(R.id.id_fragment_container, new TabFounds(), TAG_GOODS);
                break;
            case R.id.id_tab_business://发布
                new RelaseDialog(this, new RelaseDialog.PriorityListener() {
                    @Override
                    public void callback(String i, String serveLabelName) {
                        if (!TextUtils.isEmpty(i)) {
                            Intent intent = new Intent(BossBuyActivity.this, ReleaseChoiceClassActivity.class);
                            intent.putExtra("tab", i);
                            intent.putExtra("serveLabelName", serveLabelName);
                            startActivity(intent);
                        }
                    }
                }).show();
                break;
            case R.id.id_tab_news://消息
//                if (badge1 !=null){
//                    badge1.hide();
//                }
                if (islogin) {
                    currentIndex = 3;
                    updateBottomView(currentIndex);
                    updateTopNewsTitle(getResources().getString(R.string.news));
                    transaction.replace(R.id.id_fragment_container, new TabMessage(), TAG_NEWS);

                } else {

                    ToastUtil.showMessageDefault(BossBuyActivity.this,
                            getString(R.string.personal_no_login_toast));
                    Intent loginIntent = new Intent(BossBuyActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }
                break;
            case R.id.id_tab_personal://个人中心
                currentIndex = 4;
                transaction.replace(R.id.id_fragment_container, new TabPersonal(),
                        TAG_PERSONAL);
                break;
            default:
                break;
        }
        transaction.commit();
        updateBottomView(currentIndex);
        updateTopView(currentIndex);

        switch (id) {
            case R.id.id_container_city:
                Intent intent = new Intent(this, CityActivity.class);
                startActivityForResult(intent, HANDLE_REQUEST_CITY);
                break;
            case R.id.id_goods_search:
                Intent intent2 = new Intent(this, SearchResultActivity.class);
                intent2.putExtra("searchType", flag);
                startActivity(intent2);
                break;
            case R.id.id_location:
                Intent i = new Intent(this, CategoriesActivity.class);
                startActivity(i);
                break;
           /* case R.id.FirstImageView:
                findViewById(R.id.FirstImageView).setVisibility(View.GONE);
                //Intent it = new Intent(this, CityActivity.class);
                //startActivityForResult(it, HANDLE_REQUEST_CITY);
                break;*/
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ftt = getSupportFragmentManager().beginTransaction();
        ftt.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit);
        if (resultCode == RESULT_OK && data != null && data.getStringExtra("cityID") != null) {
            switch (requestCode) {
                case HANDLE_REQUEST_CITY:
                    cityName = data.getStringExtra("cityName");
                    cityLon = data.getStringExtra("cityLon");
                    cityLat = data.getStringExtra("cityLat");
                    cityID = data.getStringExtra("cityID");
                    cityLevel = data.getIntExtra("level", 2);
                    mTextCity.setText(cityName);
                    saveCurrentCity(cityName, cityLon, cityLat, cityID, cityLevel);

                    switch (flag) {
                        case 0:
                            updateTopTitle(getResources().getString(R.string.top_search_all));
                            currentIndex = 0;
                            ftt.replace(R.id.id_fragment_container, new TabHome(), TAG_HOME);
                            break;
                        case 1:
//                            updateTopTitle(getResources().getString(R.string.top_search_commodity));
//                            currentIndex = 1;
//                            ftt.replace(R.id.id_fragment_container, new ReleaseChoiceClassActivity(), TAG_GOODS);
                            new RelaseDialog(this, new RelaseDialog.PriorityListener() {
                                @Override
                                public void callback(String i, String serveLabelName) {
                                    if (i != null) {
                                        Intent intent = new Intent(BossBuyActivity.this, ReleaseChoiceClassActivity.class);
                                        intent.putExtra("tab", i);
                                        intent.putExtra("serveLabelName", serveLabelName);
                                        startActivity(intent);
                                    }
                                }
                            }).show();
                            break;
                        case 2:
                            updateTopTitle(getResources().getString(R.string.top_search_shop));
                            currentIndex = 2;
                            ftt.replace(R.id.id_fragment_container, new TabFounds(),
                                    TAG_BUSINESS);
                            break;
                    }
                    ftt.commit();
                    updateBottomView(currentIndex);
                    updateTopView(currentIndex);
                    break;
                case BAIDU_LOCATION:
                    cityName = data.getStringExtra("cityName");
                    mTextCity.setText(cityName);
                    cityLon = null;
                    cityLat = null;
                    cityID = data.getStringExtra("cityID");
                    cityLevel = data.getIntExtra("level", 2);
                    saveCurrentCity(cityName, cityLon, cityLat, cityID, cityLevel);

                    switch (flag) {
                        case 0:
                            updateTopTitle(getResources().getString(R.string.top_search_all));
                            currentIndex = 0;
                            ftt.replace(R.id.id_fragment_container, new TabHome(), TAG_HOME);
                            break;
                        case 1:
//                            updateTopTitle(getResources().getString(R.string.top_search_commodity));
//                            currentIndex = 1;
//                            ftt.replace(R.id.id_fragment_container, new ReleaseChoiceClassActivity(), TAG_GOODS);
                            new RelaseDialog(this, new RelaseDialog.PriorityListener() {
                                @Override
                                public void callback(String i, String serveLabelName) {
                                    if (i != null) {
                                        Intent intent = new Intent(BossBuyActivity.this, ReleaseChoiceClassActivity.class);
                                        intent.putExtra("tab", i);
                                        intent.putExtra("serveLabelName", serveLabelName);
                                        startActivity(intent);
                                    }
                                }
                            }).show();
                            break;
                        case 2:
//                    updateTopTitle(getResources().getString(R.string.top_search_shop));
                            currentIndex = 2;
                            ftt.replace(R.id.id_fragment_container, new TabFounds(),
                                    TAG_BUSINESS);
                            break;
                    }
                    ftt.commit();
                    updateBottomView(currentIndex);
                    updateTopView(currentIndex);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            clazz = this.getClass();
            if (clazz.equals(BossBuyActivity.class)) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    ToastUtil.showMessageDefault(this,
                            getResources().getString(R.string.click_oncemore_exit));
                    exitTime = System.currentTimeMillis();
                    saveCurrentCity(cityName, cityLon, cityLat, cityID, cityLevel);
                    return true;
                } else {
                    for (BaseAct baseAct : CrashApplication.allActivity) {
                        baseAct.finish();
                    }
                    System.exit(0);
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //控制底部导航条UI
    private void updateBottomView(int position) {
        switch (position) {
            case 0:
                mTabHomeLayout.setImgAndText(R.drawable.ic_home_selected, getResources().getColor(R.color.c_168eef));
                mTabGoodsLayout.setImgAndText(R.drawable.ic_found_default, getResources().getColor(R.color.c_707070));
                mTabBusinessLayout.setImgAndText(R.drawable.ic_release_selected, getResources().getColor(R.color.c_168eef));
                mTabNewsLayout.setImgAndText(R.drawable.ic_message_default, getResources().getColor(R.color.c_707070));
                mTabPersonalLayout.setImgAndText(R.drawable.ic_self_default, getResources().getColor(R.color.c_707070));
                break;
            case 1:
                mTabHomeLayout.setImgAndText(R.drawable.ic_home_default, getResources().getColor(R.color.c_707070));
                mTabGoodsLayout.setImgAndText(R.drawable.ic_found_selected, getResources().getColor(R.color.c_168eef));
                mTabBusinessLayout.setImgAndText(R.drawable.ic_release_selected, getResources().getColor(R.color.c_168eef));
                mTabNewsLayout.setImgAndText(R.drawable.ic_message_default, getResources().getColor(R.color.c_707070));
                mTabPersonalLayout.setImgAndText(R.drawable.ic_self_default, getResources().getColor(R.color.c_707070));
                break;
            case 2:
                mTabHomeLayout.setImgAndText(R.drawable.ic_home_default, getResources().getColor(R.color.c_707070));
                mTabGoodsLayout.setImgAndText(R.drawable.ic_found_default, getResources().getColor(R.color.c_707070));
                mTabBusinessLayout.setImgAndText(R.drawable.ic_release_selected, getResources().getColor(R.color.c_168eef));
                mTabNewsLayout.setImgAndText(R.drawable.ic_message_default, getResources().getColor(R.color.c_707070));
                mTabPersonalLayout.setImgAndText(R.drawable.ic_self_default, getResources().getColor(R.color.c_707070));
                break;
            case 3:
                mTabHomeLayout.setImgAndText(R.drawable.ic_home_default, getResources().getColor(R.color.c_707070));
                mTabGoodsLayout.setImgAndText(R.drawable.ic_found_default, getResources().getColor(R.color.c_707070));
                mTabBusinessLayout.setImgAndText(R.drawable.ic_release_selected, getResources().getColor(R.color.c_168eef));
                mTabNewsLayout.setImgAndText(R.drawable.ic_message_selected, getResources().getColor(R.color.c_168eef));
                mTabPersonalLayout.setImgAndText(R.drawable.ic_self_default, getResources().getColor(R.color.c_707070));
                break;
            case 4:
                mTabHomeLayout.setImgAndText(R.drawable.ic_home_default, getResources().getColor(R.color.c_707070));
                mTabGoodsLayout.setImgAndText(R.drawable.ic_found_default, getResources().getColor(R.color.c_707070));
                mTabBusinessLayout.setImgAndText(R.drawable.ic_release_selected, getResources().getColor(R.color.c_168eef));
                mTabNewsLayout.setImgAndText(R.drawable.ic_message_default, getResources().getColor(R.color.c_707070));
                mTabPersonalLayout.setImgAndText(R.drawable.ic_self_selected, getResources().getColor(R.color.c_168eef));
                break;
            default:
                break;
        }
    }

    private void resetTopView() {
        mTopHomeLayout.setVisibility(View.GONE);
        mTopNewsLayout.setVisibility(View.GONE);
        mTopSettingLayout.setVisibility(View.GONE);
    }

    //控制顶部标题栏UI
    private void updateTopView(int tabId) {
        resetTopView();
        switch (tabId) {
            case 0:
                mTopHomeLayout.setVisibility(View.VISIBLE);
                mTextLocation.setVisibility(View.VISIBLE);
                mTopNewsLayout.setVisibility(View.GONE);
                mTopSettingLayout.setVisibility(View.GONE);
                break;
            case 1:
            case 2:
                mTopHomeLayout.setVisibility(View.GONE);
                mTextLocation.setVisibility(View.GONE);
                mTopNewsLayout.setVisibility(View.VISIBLE);
                mTopSettingLayout.setVisibility(View.GONE);
                break;
            case 3:
                mTopHomeLayout.setVisibility(View.GONE);
                mTopNewsLayout.setVisibility(View.VISIBLE);
                mTopSettingLayout.setVisibility(View.GONE);
                break;
            case 4:
                mTopHomeLayout.setVisibility(View.GONE);
                mTopNewsLayout.setVisibility(View.GONE);
                mTopSettingLayout.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void updateTopTitle(String hint) {
        ((TextView) findViewById(R.id.id_top_serach_hint)).setHint(hint);
    }

    private void updateTopNewsTitle(String title) {
        mTopHomeLayout.setVisibility(View.GONE);
        mTopNewsLayout.setVisibility(View.VISIBLE);
        mTopSettingLayout.setVisibility(View.GONE);
        ((TextView) findViewById(R.id.id_home_top_news_view)).setText(title);
    }

    @Override
    public void goBusinessFromHomeButton(String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(TAG_HOME);
        ft.replace(R.id.id_fragment_container, new TabFounds(),
                TAG_BUSINESS);
        ft.commit();
        updateTopView(2);
        updateBottomView(2);
        currentIndex = 2;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        TabHome homeFragment = new TabHome();
        Bundle bundle = new Bundle();
        switch (checkedId) {
            case R.id.rb_home_title_service:
                titlecontent = "service";
                bundle.putString("bundle", titlecontent);
                homeFragment.setArguments(bundle);
                ft.replace(R.id.id_fragment_container, homeFragment, TAG_HOME);
                break;
            case R.id.rb_home_title_resource:
                titlecontent = "resource";
                bundle.putString("bundle", titlecontent);
                homeFragment.setArguments(bundle);
                ft.replace(R.id.id_fragment_container, homeFragment, TAG_HOME);
                break;
        }
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

  /*  private class NewMessageReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ToastUtil.showMessageDefault(context, "===" + messageNum);
            Log.d("环信消息广播--", "" + messageNum);
            if (intent.getAction().equals("com.android.app.newMessage")) {
                messageNum = EMClient.getInstance().chatManager().getUnreadMsgsCount();
                Log.d("环信消息广播--", "" + messageNum);
                badgeViewNotify(mTabNewsLayout);
            } else {
                Log.d("fail", "================");
            }
        }
    }*/
}
