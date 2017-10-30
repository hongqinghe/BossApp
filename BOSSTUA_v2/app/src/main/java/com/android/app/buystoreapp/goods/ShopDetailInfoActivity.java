package com.android.app.buystoreapp.goods;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystore.utils.SmartImageView;
import com.android.app.buystoreapp.BuyTicketActivity;
import com.android.app.buystoreapp.ContactActivity;
import com.android.app.buystoreapp.PopupWindowActivity;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.TabPersonal;
import com.android.app.buystoreapp.adapter.GalleryOtherAdapter;
import com.android.app.buystoreapp.adapter.GridAdapter;
import com.android.app.buystoreapp.adapter.LeaveMeassageAdapter;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.CommodityBean;
import com.android.app.buystoreapp.bean.GsonBackOnlyResult;
import com.android.app.buystoreapp.bean.GsonLeaveMeassageBck;
import com.android.app.buystoreapp.bean.GsonShopDetailback;
import com.android.app.buystoreapp.bean.GuessLikeBean;
import com.android.app.buystoreapp.bean.LeaveMeassageBean;
import com.android.app.buystoreapp.bean.ShopDetailImage;
import com.android.app.buystoreapp.bean.ShopDetailInfoBean;
import com.android.app.buystoreapp.bean.ShopDetailInfoBean.ComAndReply;
import com.android.app.buystoreapp.bean.UserInfoBean;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.buystoreapp.im.ChatActivity;
import com.android.app.buystoreapp.im.Constant;
import com.android.app.buystoreapp.managementservice.MymoreResourcesandServiceMainActivity;
import com.android.app.buystoreapp.managementservice.RapidlyBean;
import com.android.app.buystoreapp.managementservice.ReportActivity;
import com.android.app.buystoreapp.other.CustomDialog;
import com.android.app.buystoreapp.other.ImageUtil;
import com.android.app.buystoreapp.setting.LoginActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.buystoreapp.widget.GridViewForScrollView;
import com.android.app.buystoreapp.widget.KeyboardListenRelativeLayout;
import com.android.app.buystoreapp.widget.ReboundScrollView;
import com.android.app.bystoreapp.dialog.CallDialog;
import com.android.app.bystoreapp.dialog.CallDialog.onBtnClick;
import com.android.app.utils.HttpUtils;
import com.android.app.utils.UmengShareUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;
import com.viewpagerindicator.CirclePageIndicator;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情界面
 *
 * @author likaihang
 */
public class ShopDetailInfoActivity extends BaseAct implements OnClickListener, AdapterView
        .OnItemClickListener, ReboundScrollView.ScrollViewListener, AdapterView
        .OnItemLongClickListener {
    private static final int HANDLE_UP_TO_TOP = 0x10;
    private static final int HANDLE_UPDATE_VIEW_WITH_DATAS = 0x11;
    private static final int GET_LEAVEMESSAGE = 0x12;
    private static final int SEND_LEAVE_MESSAGE = 0x13;
    private static final int LEAVE_MESSAGE_MORE = 0x14;
    private static final int ATTENTION_CLICK = 0x15;
    private static final int OPEN_DIALLOG = 0x16;

    private ReboundScrollView mScrollView;

    private final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng" +
            ".share");

    private ImageView mBack;
    private ImageView mShoppingCar;

    final String _url = "http://m.bosstuan.cn/fx.html?proid=";
    private UmengShareUtils umengShareUtils;
//    private TextView mTitle;
//    private TextView mPrice;
//    private TextView mMarketPrice;
//    private TextView mCount;
//    private TextView mAddress;

    //    private ImageView mStar;
    private View mStarContainer;
    private View mStoreContainer;
    private Button mAddCart;
    private TextView mBuyNow;

    private AdvAdapter mAdvAdapter;
    private ViewPager mAdvPager;
    CirclePageIndicator advIndicator;
    private List<View> mAdvList = new ArrayList<View>();
    private List<ShopDetailImage> imageUrlList = new ArrayList<ShopDetailImage>();// 图片地址
    private LayoutInflater mInflater;

    private TabWidget mTabWidget;
    private List<View> mDetailList = new ArrayList<View>();

    //    private HomeProBean mCommodityBean;
    private ComAndReply comAndReply;

    boolean isStar = false;
    private String mCurrentUserName;
    private String mCommodityNum = "1";
    private boolean isLogin;
    private String mCommodityID;
    private String url;

    private List<CommodityBean> mUserShopCar = new ArrayList<CommodityBean>();
    private List<RapidlyBean.FastChatListBean> chatlist = new ArrayList<RapidlyBean
            .FastChatListBean>();

    UserInfoBean mUserInfo;
    private boolean scrollBotm = false;
    private String desc = "Boss团为你而来，与你同在";
    private RecyclerView mRecyclerView;
    private GalleryOtherAdapter mAdapter;
    private List<GuessLikeBean.LikeProBean> mDatas = new ArrayList<>();
    //猜你喜欢集合
    private RelativeLayout popupwindow;
    private RelativeLayout title;
    private int imageHeight;
    private ImageView coment;
    private FrameLayout banner;
    private ListView comlist;
    private LeaveMeassageAdapter lmAdapter;
    private List<LeaveMeassageBean> list = new ArrayList<LeaveMeassageBean>();//留言集合
    private FrameLayout fl;
    private TextView messagemore;
    private TextView mName;
    private TextView mProfession;
    private TextView mScanNum;
    private TextView mAllMoney;
    private ImageView mVerified;
    private ImageView mCompany;
    private ImageView mProInfo;
    private TextView mAttention;
    private TextView mTitle;
    private TextView mDes;
    private TextView mTimeWork;
    private TextView mFireghtPric;
    private TextView mSalenum;
    private TextView mSurplus;
    private TextView mServiceIdea;
    private ImageView mCredit;
    private TextView mPrice;
    private KeyboardListenRelativeLayout keyboardlayout;
    private LinearLayout ll;
    private LinearLayout ll_bottom;
    private String proName;
    private TextView commentgood;
    private TextView commentmindle;
    private TextView commentbad;
    private int indexId = 0;
    private LinearLayout mModes;
    private TextView brow;
    private TextView distance;
    private int isopen;
    private LinearLayout llMessageEmpty;
    private InputMethodManager imm;
    private Button mesagesend;
    private String mCurrentUserId;
    private EditText edit;
    private String commentContent;//留言/回复 内容
    private boolean isLeave = true;// 留言or回复
    private String objectId;//留言主键id
    private String crComnId = "";//留言id
    private String objectUserId = "";
    private int status = 1;
    private ImageView level;
    private int width;
    private int hight;
    private ImageView header;
    private String imageMinurl;
    private String myTicket;
    private TextView unit;
    private PopupWindow pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.goods_buy_layout);
        mCommodityID = getIntent().getExtras().getString("proId");//商品id
        proName = getIntent().getExtras().getString("proName");//商品名称
        mInflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initViews();
        initListeners();

        url = getString(R.string.web_url);

        popupwindow = (RelativeLayout) findViewById(R.id.ll_choice_more);
        popupwindow.setOnClickListener(this);

        //mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview_horizontal);
        //设置布局管理器
       /* LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new GalleryOtherAdapter(ShopDetailInfoActivity.this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new GalleryOtherAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, ShopDetailInfoActivity.class);
                intent.putExtra("proId", mDatas.get(position).getProId());
                intent.putExtra("proName", mDatas.get(position).getProName());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });*/
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
//        load();
    }

    @Override
    protected void load() {
        requestShopDetailInfo(mCommodityID, CrashApplication.latitude + "", CrashApplication
                .longitude + "");
        getComment();//获取留言
        //猜你喜欢
        getGases(proName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserInfo = SharedPreferenceUtils.getCurrentUserInfo(this);
        isLogin = mUserInfo.isLogin();
        mCurrentUserName = mUserInfo.getNickName();
        mCurrentUserId = mUserInfo.getUserId();
        myTicket = SharedPreferenceUtils.getCurrentUserInfo(mContext).getTicketCount();
        load();
    }

    private void initViews() {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboardlayout = (KeyboardListenRelativeLayout) findViewById(R.id.rl_goods_buy);
        ll = (LinearLayout) findViewById(R.id.line);
        edit = (EditText) findViewById(R.id.edit);
        mesagesend = (Button) findViewById(R.id.bt_leave_message_send);
        ll_bottom = (LinearLayout) findViewById(R.id.goodsdetail_bottom);
        mBack = (ImageView) findViewById(R.id.id_goodsdetail_back);
        mShoppingCar = (ImageView) findViewById(R.id.id_goodsdetail_shoppingcar);
        mScrollView = (ReboundScrollView) findViewById(R.id.id_goods_buy_scroll);
        mAdvPager = (ViewPager) findViewById(R.id.id_goodsdetail_adv_viewPager);
        advIndicator = (CirclePageIndicator) findViewById(R.id.id_goodsdetail_adv_indicator);
        coment = (ImageView) findViewById(R.id.iv_goods_comment);
        header = (ImageView) findViewById(R.id.id_home_item_image);

        mName = (TextView) findViewById(R.id.tv_user_name);
        mProfession = (TextView) findViewById(R.id.tv_user_profession);

        mScanNum = (TextView) findViewById(R.id.tv_goods_scan_num);
        mAllMoney = (TextView) findViewById(R.id.tv_user_money);

        mVerified = (ImageView) findViewById(R.id.iv_info_verified);
        mCredit = (ImageView) findViewById(R.id.iv_user_info_credit);
        mCompany = (ImageView) findViewById(R.id.iv_user_info_company);
        mProInfo = (ImageView) findViewById(R.id.iv_user_info_profession);
        level = (ImageView) findViewById(R.id.iv_goods_details_leve);

        mAttention = (TextView) findViewById(R.id.tv_attention_button);

        mTitle = (TextView) findViewById(R.id.id_home_item_title);
        mDes = (TextView) findViewById(R.id.id_home_item_intro);
        mTimeWork = (TextView) findViewById(R.id.tv_time_work);
        mFireghtPric = (TextView) findViewById(R.id.tv_fireght_money);
        mPrice = (TextView) findViewById(R.id.id_home_item_price);
        mSalenum = (TextView) findViewById(R.id.id_home_item_salenum);

        mSurplus = (TextView) findViewById(R.id.tv_surplus);

        mServiceIdea = (TextView) findViewById(R.id.tv_service_idea);
        mModes = (LinearLayout) findViewById(R.id.ll_service_idea);
        brow = (TextView) findViewById(R.id.tv_goods_detail_browse);
        unit = (TextView) findViewById(R.id.tv_pro_unit);

        distance = (TextView) findViewById(R.id.id_home_item_distance);
       /* Double dis = Double.valueOf(mCommodityBean.getProDistance());
        distance.setText((Math.round(dis / 100000d) / 10d) + "km");*/

        banner = (FrameLayout) findViewById(R.id.fl_goods_details_banner);//图片展示区
        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        hight = dm.heightPixels;
        LinearLayout.LayoutParams parms = (LinearLayout.LayoutParams) banner.getLayoutParams();
        parms.height = width;
        banner.setLayoutParams(parms);
        messagemore = (TextView) findViewById(R.id.tv_leave_mesage_more);
        mStoreContainer = findViewById(R.id.id_goodsdetail_store_container);
//        mStar = (ImageView) findViewById(R.id.id_goodsdetail_star);
        mStarContainer = findViewById(R.id.id_goodsdetail_star_container);
        mAddCart = (Button) findViewById(R.id.id_goodsdetail_addcart);
        mBuyNow = (TextView) findViewById(R.id.id_goodsdetail_buynow);
        comlist = (ListView) findViewById(R.id.lv_leave_list);
        comlist.setOnItemClickListener(this);
        comlist.setOnItemLongClickListener(this);
        llMessageEmpty = (LinearLayout) findViewById(R.id.ll_leave_message_empty);

        mAdvAdapter = new AdvAdapter();
        mAdvPager.setAdapter(mAdvAdapter);
//        mAdvPager.setCurrentItem(0);
    }

    private void initListeners() {
        messagemore.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mShoppingCar.setOnClickListener(this);
        coment.setOnClickListener(this);
        banner.setOnClickListener(this);
        mesagesend.setOnClickListener(this);
        mAdvPager.setOnClickListener(this);
//        mTitle.setOnClickListener(this);
//        mPrice.setOnClickListener(this);
//        mCount.setOnClickListener(this);
//        mAddress.setOnClickListener(this);
        mStoreContainer.setOnClickListener(this);
        mStarContainer.setOnClickListener(this);
        mAddCart.setOnClickListener(this);
        mBuyNow.setOnClickListener(this);
        mAttention.setOnClickListener(this);
        llMessageEmpty.setOnClickListener(this);
        findViewById(R.id.CallText).setOnClickListener(this);
        findViewById(R.id.shareImage).setOnClickListener(this);
        findViewById(R.id.ll_goods_details_more_resourse).setOnClickListener(this);
        findViewById(R.id.ll_goods_comment).setOnClickListener(this);
        findViewById(R.id.ll_leave_message).setOnClickListener(this);
        commentgood = (TextView) findViewById(R.id.tv_commend_good);
        commentgood.setOnClickListener(this);
        commentmindle = (TextView) findViewById(R.id.tv_commend_mindle);
        commentmindle.setOnClickListener(this);
        commentbad = (TextView) findViewById(R.id.tv_commend_bad);
        commentbad.setOnClickListener(this);
        title = (RelativeLayout) findViewById(R.id.rl_title);
        // 获取顶部title高度后，设置滚动监听
        ViewTreeObserver vto = banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                title.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                imageHeight = banner.getHeight();
                mScrollView.setScrollViewListener(ShopDetailInfoActivity.this);
            }
        });
        keyboardlayout.setOnKeyboardStateChangedListener(new KeyboardListenRelativeLayout
                .IOnKeyboardStateChangedListener() {
            @Override
            public void onKeyboardStateChanged(int state) {
                switch (state) {
                    case KeyboardListenRelativeLayout.KEYBOARD_STATE_HIDE:
                        ll.setVisibility(View.GONE);
                        ll_bottom.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    private void initAllAdvImageFromUrl() {
        for (int i = 0; i < imageUrlList.size(); i++) {
            SmartImageView smartImageView = new SmartImageView(this);
            smartImageView.setImageUrl(imageUrlList.get(i)
                    .getProImageUrl());
            DisplayMetrics dm = getResources().getDisplayMetrics();
            width = dm.widthPixels;
            hight = dm.heightPixels;
            smartImageView.setLayoutParams(new LayoutParams(
                    width, width));
            smartImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup advContainer = (ViewGroup) mInflater.inflate(
                    R.layout.home_adv_container, null);
            advContainer.addView(smartImageView);
            mAdvList.add(advContainer);
        }
        mAdvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        commentContent = edit.getText().toString().trim();
        int id = view.getId();
        if (id == R.id.id_goodsdetail_star_container
                || id == R.id.id_goodsdetail_addcart
                || id == R.id.id_goodsdetail_shoppingcar
                || id == R.id.id_goodsdetail_buynow
                || id == R.id.CallText
                || id == R.id.tv_attention_button
                || id == R.id.id_goodsdetail_store_container
                || id == R.id.ll_leave_message_empty
                || id == R.id.bt_leave_message_send
                || id == R.id.iv_goods_comment
                || id == R.id.ll_choice_more) {
            if (isLogin) {
                if (mCurrentUserId.equals(comAndReply.getUserId()) && (id == R.id
                        .id_goodsdetail_addcart
                        || id == R.id.id_goodsdetail_shoppingcar
                        || id == R.id.id_goodsdetail_buynow
                        || id == R.id.CallText
                        || id == R.id.tv_attention_button
                        || id == R.id.id_goodsdetail_store_container
                        || id == R.id.ll_leave_message_empty
                        || id == R.id.iv_goods_comment
                        || id == R.id.ll_choice_more)) {

                    ToastUtil.showMessageDefault(mContext, "您不能对自己操作！");
                } else {
                    switch (id) {
                        case R.id.ll_choice_more:
                        case R.id.id_goodsdetail_addcart:
                        case R.id.id_goodsdetail_buynow:// 立即购买
                            Intent pop = new Intent(ShopDetailInfoActivity.this,
                                    PopupWindowActivity.class);
                            pop.putExtra("nickName", comAndReply.getNickName());//商家名称
                            pop.putExtra("proUserId", comAndReply.getUserId());//商家id
                            pop.putExtra("modes", comAndReply.getModes());//服务方式

                            pop.putExtra("freightMode", comAndReply.getFreightMode());//货运方式
                            pop.putExtra("freightPric", comAndReply.getFreightPrice());//运费

                            pop.putExtra("weekstart", comAndReply.getWeekStart());//
                            pop.putExtra("weekend", comAndReply.getWeekEnd());//
                            pop.putExtra("daytimestart", comAndReply.getDayTimeStart());//
                            pop.putExtra("daytimeend", comAndReply.getDayTimeEnd());//

                            pop.putExtra("proId", mCommodityID);//商品id
                            pop.putExtra("proName", proName);//名称
                            pop.putExtra("price", comAndReply.getProCurrentPrice());//价格

                            for (int i = 0; i < imageUrlList.size(); i++) {
                                if (imageUrlList.get(i).getIsCover() == 1) {
                                    imageMinurl = imageUrlList.get(i).getProImageMin();
                                }
                            }
                            pop.putExtra("imageMinurl", imageMinurl);//商品封面图

                            startActivity(pop);
                            break;
                        case R.id.id_goodsdetail_store_container://私聊
                            Intent i = new Intent(this, ChatActivity.class);
                            i.putExtra(Constant.EXTRA_USER_NAME, comAndReply.getNickName());
                            i.putExtra(Constant.EXTRA_USER_ICON, comAndReply.getHeadicon());
                            i.putExtra(Constant.EXTRA_USER_ID, comAndReply.getUserId());
                            i.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                            startActivity(i);
                            break;

                        case R.id.ll_leave_message_empty:
                        case R.id.CallText://留言
                            edit.setHint("说点什么吧...");
                            isLeave = true;
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            ll.requestFocus();
                            ll.setVisibility(View.VISIBLE);
                            ll_bottom.setVisibility(View.GONE);
                            break;

                        case R.id.iv_goods_comment://收藏
                            isStar = !isStar;
                            saveCommodity(isStar);
                            break;
                        case R.id.id_goodsdetail_shoppingcar://举报
                            Intent shopCarIntent = new Intent(this,
                                    ReportActivity.class);
                            shopCarIntent.putExtra("passiveUserID", comAndReply.getUserId());
                            shopCarIntent.putExtra("passiveProID", mCommodityID);
                            shopCarIntent.putExtra("proName", proName);
                            for (int j = 0; j < imageUrlList.size(); j++) {
                                if (imageUrlList.get(j).getIsCover() == 1) {
                                    imageMinurl = imageUrlList.get(j).getProImageMin();
                                }
                            }
                            shopCarIntent.putExtra("proImageMin", imageMinurl);
                            startActivity(shopCarIntent);
                            break;
                        case R.id.tv_attention_button://关注
                            follow(mCurrentUserId, comAndReply.getUserId());
                            break;
                        default:
                            break;
                    }
                }
                switch (id) {
                    case R.id.id_goodsdetail_star_container://名片
                        if (isopen == 0) {
                            Toast.makeText(this, "暂无联系信息", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            getChatInfo();
                        }
//                    if (isStar) {
//                        mStar.setImageResource(R.drawable.ic_shop_star_cancel);
//                    } else {
//                        mStar.setImageResource(R.drawable.ic_shop_star_save);
//                    }
                        break;
                    case R.id.bt_leave_message_send://发表、回复留言
                        if (TextUtils.isEmpty(commentContent)) {
                            ToastUtil.showMessageDefault(this, "内容不能为空！");
                        } else {
                            startLoadingAnim();
                            if (isLeave) {
                                status = 1;
                                objectId = mCommodityID;
                                objectUserId = comAndReply.getUserId();
                                replayMessage(commentContent, crComnId, objectUserId,
                                        objectId, String.valueOf(status));
                            } else {
                                replayMessage(commentContent, crComnId, objectUserId,
                                        objectId, String.valueOf(status));
                            }
                        }
                        break;
                }
            } else {
                Toast.makeText(this,
                        getString(R.string.personal_no_login_toast),
                        Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.putExtra("type", TabPersonal.FLAG_LOGIN);
                startActivityForResult(loginIntent, Activity.RESULT_FIRST_USER);
            }
        }

        switch (id) {
            case R.id.id_goodsdetail_back:
                finish();
                break;
           /* case R.id.ll_choice_more:
                Intent pop = new Intent(ShopDetailInfoActivity.this, PopupWindowActivity.class);
                pop.putExtra("nickName", comAndReply.getNickName());//商家名称
                pop.putExtra("proUserId", comAndReply.getUserId());//商家id
                pop.putExtra("modes", comAndReply.getModes());//服务方式

                pop.putExtra("freightMode", comAndReply.getFreightMode());//货运方式
                pop.putExtra("freightPric", comAndReply.getFreightPrice());//运费

                pop.putExtra("weekstart", comAndReply.getWeekStart());//
                pop.putExtra("weekend", comAndReply.getWeekEnd());//
                pop.putExtra("daytimestart", comAndReply.getDayTimeStart());//
                pop.putExtra("daytimeend", comAndReply.getDayTimeEnd());//

                pop.putExtra("proId", mCommodityID);//商品id
                pop.putExtra("proName", proName);//名称
                pop.putExtra("price", comAndReply.getProCurrentPrice());//价格

                for (int i = 0; i < imageUrlList.size(); i++) {
                    if (imageUrlList.get(i).getIsCover() == 0) {
                        imageMinurl = imageUrlList.get(i).getProImageMin();
                    }
                }
                pop.putExtra("imageMinurl", imageMinurl);//商品封面图

                startActivity(pop);
                break;*/
            case R.id.shareImage://分享
//                initUmengShareCompounets();
//                mController.openShare(this, false);
//                //initUmengShareCompounets();
                UMImage urlImage = new UMImage(this, BitmapFactory.decodeResource(this
                        .getResources(), R.drawable.ic_launcher));
                String uri = _url + comAndReply.getProId();
                umengShareUtils = new UmengShareUtils(this, desc, uri, comAndReply.getProName(),
                        urlImage);
                umengShareUtils.share();
                break;
            case R.id.tv_commend_good:
                indexId = 1;
                Intent i = new Intent(this, CommentBuyActivity.class);
                i.putExtra("proId", comAndReply.getProId());//商品id
                i.putExtra("proUserId", comAndReply.getUserId());//发布人id
                i.putExtra("indexId", indexId);//评价类别
                startActivity(i);
                break;
            case R.id.tv_commend_mindle:
                indexId = 2;
                Intent i1 = new Intent(this, CommentBuyActivity.class);
                i1.putExtra("proId", comAndReply.getProId());//商品id
                i1.putExtra("proUserId", comAndReply.getUserId());//发布人id
                i1.putExtra("indexId", indexId);//评价类别
                startActivity(i1);
                break;
            case R.id.tv_commend_bad:
                indexId = 3;
                Intent i2 = new Intent(this, CommentBuyActivity.class);
                i2.putExtra("proId", comAndReply.getProId());//商品id
                i2.putExtra("proUserId", comAndReply.getUserId());//发布人id
                i2.putExtra("indexId", indexId);//评价类别
                startActivity(i2);
                break;
            case R.id.ll_goods_comment://跳转评价列表
                indexId = 0;
                Intent i3 = new Intent(this, CommentBuyActivity.class);
                i3.putExtra("proId", comAndReply.getProId());//商品id
                i3.putExtra("proUserId", comAndReply.getUserId());//发布人id
                i3.putExtra("indexId", indexId);//评价类别
                startActivity(i3);
                break;
            case R.id.ll_goods_details_more_resourse://我的更多
                Intent intent1 = new Intent(this, MymoreResourcesandServiceMainActivity.class);
                intent1.putExtra("proUserId", comAndReply.getUserId());
                startActivity(intent1);
                break;
            case R.id.tv_leave_mesage_more://更多留言
                lmAdapter = new LeaveMeassageAdapter(this, list, list.size());
                comlist.setAdapter(lmAdapter);
                messagemore.setVisibility(View.GONE);
                lmAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void CallClick() {
        CallDialog dialog = new CallDialog(ShopDetailInfoActivity.this);
        //String myTicket = SharedPreferenceUtils.getCurrentUserInfo(mContext).getTicketCount();
        if (chatlist.get(0).getStatus() == 1) {
            if (myTicket.equals("0")) {
                dialog.setTicket(myTicket, "获取BOSS劵");
                dialog.setClickListener(new onBtnClick() {

                    @Override
                    public void buyBtn() {
                        Intent intent = new Intent(ShopDetailInfoActivity.this, BuyTicketActivity
                                .class);
                        startActivity(intent);
                    }
                });
            } else {
                dialog.setTicket(myTicket, "使用BOSS劵");
                dialog.setClickListener(new onBtnClick() {

                    @Override
                    public void buyBtn() {
                        startWhiteLoadingAnim();
                        useBossTicket();
                    }
                });
            }
        } else if (chatlist.get(0).getStatus() == 2) {
            dialog.setDirect("查看名片");
            dialog.setClickListener(new onBtnClick() {

                @Override
                public void buyBtn() {
                    Intent intent = new Intent(ShopDetailInfoActivity.this, ContactActivity.class);
                    intent.putExtra("proName", comAndReply.getProName());
                    intent.putExtra("userName", comAndReply.getNickName());
                    intent.putExtra("headIcon", comAndReply.getHeadicon());
                    intent.putExtra("aboutus", chatlist.get(0));
                    startActivity(intent);
                }
            });
        } else {
            dialog.setChat("私聊联系");
            dialog.setClickListener(new onBtnClick() {

                @Override
                public void buyBtn() {
                    Intent i = new Intent(ShopDetailInfoActivity.this, ChatActivity.class);
                    i.putExtra(Constant.EXTRA_USER_NAME, comAndReply.getNickName());
                    i.putExtra(Constant.EXTRA_USER_ICON, comAndReply.getHeadicon());
                    i.putExtra(Constant.EXTRA_USER_ID, comAndReply.getUserId());
                    i.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                    startActivity(i);
                }
            });
        }
        dialog.show();
    }

    /**
     * 获取名片信息
     */
    private void getChatInfo() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "queryFastChat");
            obj.put("proid", mCommodityID);
            obj.put("status", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("获取名片--", new String(bytes));
                Gson gson = new Gson();

                RapidlyBean rapidlyBean = gson.fromJson(new String(bytes)
                        , new TypeToken<RapidlyBean>() {
                        }.getType());
                String result = rapidlyBean.getResult();

                if ("0".equals(result)) {
                    if (rapidlyBean.getFastChatList() != null) {
                        chatlist.clear();
                        chatlist.addAll(rapidlyBean.getFastChatList());
                        myHandler.obtainMessage(OPEN_DIALLOG).sendToTarget();
                    }
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

    /**
     * 获取留言
     */
    private void getComment() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        final Gson gson = new Gson();
        String param = "{\"cmd\":\"productComment\",\"proId\":\"aaa\"}";
        param = param.replace("aaa", mCommodityID);
        requestParams.put("json", param);
        client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("getComment", "--" + new String(bytes));
                GsonLeaveMeassageBck leaveMeassageBean = gson
                        .fromJson(new String(bytes),
                                new TypeToken<GsonLeaveMeassageBck>() {
                                }.getType());
                String result = leaveMeassageBean.getResult();
                if (result.equals("0")) {
                    list.clear();
                    list.addAll(leaveMeassageBean.getComment());
                    myHandler.sendEmptyMessage(GET_LEAVEMESSAGE);
                } else {
                    ToastUtil.showMessageDefault(ShopDetailInfoActivity.this, leaveMeassageBean
                            .getResultNote());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    /**
     * 回复留言/发表留言
     *
     * @param commentContent 回复内容
     */
    private void replayMessage(String commentContent, String commentId, String objectUserId,
                               String objectId, String status) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "addProductComment");
            obj.put("proId", mCommodityID);
            obj.put("commentContent", commentContent);
            obj.put("thisUser", mCurrentUserId);
            obj.put("commentId", commentId);
            obj.put("objectUserId", objectUserId);
            obj.put("objectId", objectId);
            obj.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("---replayMessage--", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                Gson gson = new Gson();
                GsonBackOnlyResult gsonUserAuthBack = gson.fromJson(
                        new String(bytes),
                        new TypeToken<GsonBackOnlyResult>() {
                        }.getType());
                String result = gsonUserAuthBack.getResult();
                String resultNote = gsonUserAuthBack
                        .getResultNote();
                if ("0".equals(result)) {
                    ToastUtil.showMessageDefault(getApplicationContext(), resultNote);
                    myHandler.obtainMessage(SEND_LEAVE_MESSAGE).sendToTarget();
                } else {
                    ToastUtil.showMessageDefault(getApplicationContext(), resultNote);
                    return;
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                hideErrorPageState();
            }
        });
    }

    /**
     * 删除留言
     */
    private void deleteMessage(String crId) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "delCommentAndRepley");
            obj.put("crId", crId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                try {
                    JSONObject obj = new JSONObject(new String(bytes));
                    String result = obj.getString("result");
                    String resultNote = obj.getString("resultNote");
                    if ("0".equals(result)) {
                        ToastUtil.showMessageDefault(mContext, resultNote);
                        myHandler.obtainMessage(SEND_LEAVE_MESSAGE).sendToTarget();
                    } else {
                        ToastUtil.showMessageDefault(getApplicationContext(), resultNote);
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                hideErrorPageState();
                stopLoadingAnim();
            }
        });
    }

    /**
     * 猜你喜欢接口
     *
     * @param proName 当前商品名
     */
    private void getGases(String proName) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "GuessYouLike");
            obj.put("proName", proName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("shang", "猜你喜欢提交数据--" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("shang", "猜你喜欢返回值--" + new String(bytes));
                Gson gson = new Gson();
                GuessLikeBean gsonBack = gson.fromJson(new String(bytes), new
                        TypeToken<GuessLikeBean>() {
                        }.getType());
                String result = gsonBack.getResult();
                String resultNote = gsonBack
                        .getResultNote();
                if (result.equals("0")) {
                    mDatas.clear();
                    if (gsonBack.getLikePro() != null) {
                        mDatas.addAll(gsonBack.getLikePro());
                    }
                    myHandler.sendEmptyMessage(10001);

                } else {
                    ToastUtil.showMessageDefault(ShopDetailInfoActivity.this, resultNote);
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

    /**
     * 关注接口
     *
     * @param thisUser 当前用户id
     * @param proUser  发布商品用户的ID
     */
    private void follow(String thisUser, String proUser) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "isAttention");
            obj.put("thisUser", thisUser);
            obj.put("proUser", proUser);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("---关注返回数据----", "" + new String(bytes));
                Gson gson = new Gson();
                GsonBackOnlyResult gsonBack = gson.fromJson(
                        new String(bytes),
                        new TypeToken<GsonBackOnlyResult>() {
                        }.getType());
                String result = gsonBack.getResult();
                String resultNote = gsonBack
                        .getResultNote();
                if (result.equals("0")) {
                    ToastUtil.showMessageDefault(ShopDetailInfoActivity.this, resultNote);
                    //获取新的关注状态
                    myHandler.obtainMessage(ATTENTION_CLICK).sendToTarget();
                    //通知刷新View
                } else {
                    ToastUtil.showMessageDefault(ShopDetailInfoActivity.this, "数据异常！");
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

    /**
     * 使用Boss券
     */
    private void useBossTicket() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("thisUserId", mCurrentUserId);
            obj.put("proUserId", mCommodityID);
            obj.put("cmd", "openUpFastChat");
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Log.d("使用boss卷-", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                stopLoadingAnim();
                String str = new String(arg2);
                try {
                    JSONObject obj = new JSONObject(str);
                    if ("0".equals(obj.getString("result"))) {
                        Intent intent = new Intent(ShopDetailInfoActivity.this, ContactActivity
                                .class);
                        intent.putExtra("proName", comAndReply.getProName());
                        intent.putExtra("userName", comAndReply.getNickName());
                        intent.putExtra("headIcon", comAndReply.getHeadicon());
                        intent.putExtra("aboutus", chatlist.get(0));
                        startActivity(intent);
                        int total = Integer.valueOf(SharedPreferenceUtils.getBossTicketCount
                                (ShopDetailInfoActivity.this)) - 1;
                        if (total >= 0) {
                            SharedPreferenceUtils.saveBossTicketCount(ShopDetailInfoActivity
                                    .this, String.valueOf(total));
                        }
                    } else {
                        Toast.makeText(ShopDetailInfoActivity.this, obj.getString("resultNote"),
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(ShopDetailInfoActivity.this, "请求失败，请稍后再试");
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {

            }
        });
    }

    private void updateCommodityStarState() {
        if (isStar) {
            coment.setImageResource(R.drawable.ic_iscollect);
        } else {
            coment.setImageResource(R.drawable.ic_goods_comment);
        }
    }

    /**
     * 商品收藏
     *
     * @param isStar true 收藏 false 取消
     */
    private void saveCommodity(final boolean isStar) {
        int state = 0;
        if (isStar) {
            state = 0;
        } else {
            state = 1;
        }
       /* AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        String param = "{\"cmd\":\"updateCollect\",\"commodityID\":\"aaa\",\"userName\":\"bbb\",
        \"stateType\":\"ccc\"}";
        param = param.replace("aaa", mCommodityID);
        param = param.replace("bbb", mCurrentUserName);
        param = param.replace("ccc", isStar ? "0" : "1");
        final Gson gson = new Gson();
        requestParams.put("json", param);
        Log.d("mikes", "ShopDetailInfoActivity saveCommodity param=" + param);*/
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "updateCollect");
            obj.put("userId", mCurrentUserId);
            obj.put("type", 1);
            obj.put("collectId", mCommodityID);
            obj.put("state", state);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("----updateCollect----", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Log.d("----updateCollect----", new String(arg2));
                Gson gson = new Gson();
                GsonBackOnlyResult gsonBackOnlyResult = gson.fromJson(
                        new String(arg2), new TypeToken<GsonBackOnlyResult>() {
                        }.getType());

                String result = gsonBackOnlyResult.getResult();
                String resultNote = gsonBackOnlyResult.getResultNote();
                if ("0".equals(result)) {
                    if (isStar) {
                        Toast.makeText(
                                ShopDetailInfoActivity.this,
                                getResources().getString(
                                        R.string.news_star_save_success),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(
                                ShopDetailInfoActivity.this,
                                getResources().getString(
                                        R.string.news_star_cancel_success),
                                Toast.LENGTH_SHORT).show();
                    }
                    updateCommodityStarState();
                } else {
                    Toast.makeText(ShopDetailInfoActivity.this, resultNote,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                ToastUtil.showMessageDefault(mContext, "连接超时！");
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {

            }
        });
    }

    /**
     * 请求用户购物车数据
     */
    private void requestUserShopCar() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        String param = "{\"cmd\":\"getShopCar\",\"userName\":\"aaa\"}";
        param = param.replace("aaa", mCurrentUserName);
        requestParams.put("json", param);

        client.get(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Gson gson = new Gson();
                Log.d("mikes", "requestUserShopCar result=" + new String(arg2));
                GsonShopDetailback gsonShopDetailback = gson.fromJson(
                        new String(arg2), new TypeToken<GsonShopDetailback>() {
                        }.getType());
                try {
                    String result = gsonShopDetailback.getResult();
                    if ("0".equals(result)) {
                        List<CommodityBean> commodityBeans = gsonShopDetailback
                                .getCommodityList();
                        for (CommodityBean commodity : commodityBeans) {
                            if (mCommodityID.equals(commodity.getProId())) {
                                mCommodityNum = commodity.getCommodityNum();
                            } else {
                            }
                        }

                    } else {
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
     * 获取商品详情接口
     *
     * @param commodityID 商品ID
     */
    private void requestShopDetailInfo(String commodityID, String latitude, String longitude) {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "productDetailsV2");
            obj.put("proId", commodityID);
            obj.put("userLong", longitude);
            obj.put("userLat", latitude);
            obj.put("thisUserId", mCurrentUserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                stopLoadingAnim();
                Log.d("商品详情返回值", new String(arg2));
                Gson gson = new Gson();
                try {
                    ShopDetailInfoBean shopDetailInfo = gson
                            .fromJson(new String(arg2),
                                    new TypeToken<ShopDetailInfoBean>() {
                                    }.getType());
                    String result = shopDetailInfo.getResult();
                    if ("0".equals(result)) {
//                        updateCommodityStarState();
                        comAndReply = shopDetailInfo.getComAndReply();
//                        String isFavorite = String.valueOf(comAndReply.getIsCollect());
                        int isCollect = comAndReply.getIsCollect();
                        isStar = isCollect == 1 ? true : false;
                        imageUrlList.clear();
                        imageUrlList.addAll(comAndReply.getSmallImg());
                        isopen = comAndReply.getIsOpenfastChat();
                        desc = comAndReply.getProDes();
//                        isopen = 1;
                        myHandler.sendEmptyMessage(HANDLE_UPDATE_VIEW_WITH_DATAS);
                    }
                } catch (Exception e) {
                    Log.e("mikes", "request detail info error: ", e);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                stopLoadingAnim();
                finish();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
                finish();
            }
        });
    }

    private Handler myHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case HANDLE_UP_TO_TOP:
                    mScrollView.smoothScrollTo(0, 0);
                    break;
                case HANDLE_UPDATE_VIEW_WITH_DATAS:
                    updateCommodityStarState();
                    mAdvList.clear();
                    initAllAdvImageFromUrl();// 更新 商品图片
                    advIndicator.setViewPager(mAdvPager);
                    initAllDetailView();//更新商品信息
                    break;
                case SEND_LEAVE_MESSAGE:
                    getComment();
                   /* if (list.size() < 1) {
                        llMessageEmpty.setVisibility(View.VISIBLE);
                        messagemore.setVisibility(View.GONE);
                    } else {
                        if (list.size() < 4) {
                            messagemore.setVisibility(View.GONE);
                        } else {
                            messagemore.setVisibility(View.VISIBLE);
                        }
                        llMessageEmpty.setVisibility(View.GONE);
                    }
                    lmAdapter.notifyDataSetChanged();*/
                    break;
                case GET_LEAVEMESSAGE:
                    if (list.size() > 0) {
                        if (list.size() < 4) {
                            messagemore.setVisibility(View.GONE);
                            lmAdapter = new LeaveMeassageAdapter(ShopDetailInfoActivity.this,
                                    list, list.size());
                        } else {
                            lmAdapter = new LeaveMeassageAdapter(ShopDetailInfoActivity.this,
                                    list, 3);
                            messagemore.setVisibility(View.VISIBLE);
//                            setListViewHeight(comlist, lmAdapter, 3);
                        }
                        llMessageEmpty.setVisibility(View.GONE);
                    } else {
                        llMessageEmpty.setVisibility(View.VISIBLE);
                        messagemore.setVisibility(View.GONE);
//                        lmAdapter.notifyDataSetChanged();
                    }
                    comlist.setAdapter(lmAdapter);
                    break;
               /* case LEAVE_MESSAGE_MORE:

                    break;*/
                case ATTENTION_CLICK:
                    load();
                    break;
                case OPEN_DIALLOG:
                    CallClick();
                    break;
                case 10001:
                    GridViewForScrollView gridView = (GridViewForScrollView) findViewById(R.id
                            .gridView);
                    gridView.setAdapter(new GridAdapter(mContext, mDatas));
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {
                            Intent intent = new Intent(mContext, ShopDetailInfoActivity.class);
                            intent.putExtra("proId", mDatas.get(position).getProId());
                            intent.putExtra("proName", mDatas.get(position).getProName());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });
                    //mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 控制listView 高度
     *
     * @author likaihang
     * creat at @time 16/10/17 11:08
     */
    private void setListViewHeight(ListView listView, LeaveMeassageAdapter adapter, int count) {
        int totalHeight = 0;
        for (int i = 0; i < count; i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * count);
        listView.setLayoutParams(params);
    }

    /**
     * 详情页接口数据
     */
    private void initAllDetailView() {

        if (!TextUtils.isEmpty(comAndReply.getHeadicon())) {
            Picasso.with(mContext).load(comAndReply.getHeadicon())
                    //.resize(45, 45)
                    .placeholder(R.drawable.iv_header_def).into(header);
        }
        mName.setText(comAndReply.getNickName());
        if (!TextUtils.isEmpty(comAndReply.getUserPosition())) {
            mProfession.setText("・" + comAndReply.getUserPosition());
        }
        mAllMoney.setText(comAndReply.getUserTreasure());
        if (comAndReply.getUserLevelMark() == 1) {
            level.setImageResource(R.drawable.vip_bojin);
        } else if (comAndReply.getUserLevelMark() == 2) {
            level.setImageResource(R.drawable.vip_zuanshi);
        } else if (comAndReply.getUserLevelMark() == 3) {
            level.setImageResource(R.drawable.vip_diwang);
        } else {
            level.setVisibility(View.GONE);
        }
        if (comAndReply.getBindingMark1() == 1) {
            mVerified.setVisibility(View.VISIBLE);
            mVerified.setImageResource(R.drawable.iv_user_info_verified);
        } else {
            mVerified.setVisibility(View.GONE);
        }
        if (comAndReply.getBindingMark2() == 1) {
            mCredit.setVisibility(View.VISIBLE);
            mCredit.setImageResource(R.drawable.iv_user_info_credit_light);
        } else {
            mCredit.setVisibility(View.GONE);
        }
        if (comAndReply.getBindingMark3() == 1) {
            mCompany.setVisibility(View.VISIBLE);
            mCompany.setImageResource(R.drawable.iv_user_info_company_light);
        } else {
            mCompany.setVisibility(View.GONE);
        }
        if (comAndReply.getBindingMark4() == 1) {
            mProInfo.setVisibility(View.VISIBLE);
            mProInfo.setImageResource(R.drawable.iv_user_info_profession_light);
        } else {
            mProInfo.setVisibility(View.GONE);
        }
        mTitle.setText(comAndReply.getProName());
        mDes.setText(comAndReply.getProDes());
        mPrice.setText(comAndReply.getProCurrentPrice());
        String salenum = String.format(
                this.getResources().getString(R.string.home_item_salenum),
                comAndReply.getProSale());
        mSalenum.setText(salenum);
        String proSurplus = String.format(this.getResources().getString(R.string
                .home_item_proSurplus), comAndReply.getMoreGroSurplus());
        brow.setText(comAndReply.getProSeeNum());
        distance.setText(comAndReply.getProDistance());
        mSurplus.setText(proSurplus);
        if (!TextUtils.isEmpty(comAndReply.getMoreGroUnit())) {
            unit.setText("元/" + comAndReply.getMoreGroUnit());
        }


        String time = String.format(this.getResources().getString(R.string
                        .goods_details_time_work), comAndReply.getWeekStart(), comAndReply
                        .getWeekEnd(),
                comAndReply.getDayTimeStart(), comAndReply.getDayTimeEnd());
        mTimeWork.setText(time);
        switch (comAndReply.getModes()) {
            case 0:
                mServiceIdea.setText("线上");
                break;
            case 1:
                mServiceIdea.setText("线下");
                break;
            case 2:
                mServiceIdea.setText("邮寄");
                break;
        }
        if (comAndReply.getModes() == 2) {
            mFireghtPric.setVisibility(View.VISIBLE);
            if (comAndReply.getFreightMode() == 0) {
                mFireghtPric.setText("免运费");
            } else {
                mFireghtPric.setText("运费:" + comAndReply.getFreightPrice() + "元");
            }
        } else {
            mFireghtPric.setVisibility(View.GONE);
        }
        String good = String.format(this.getResources().getString(R.string
                .goods_detail_good_score), String.valueOf(comAndReply.getGoodEvalNum()));
        commentgood.setText(good);
        String mindle = String.format(this.getResources().getString(R.string
                .goods_detail_medium_score), String.valueOf(comAndReply.getMediumEvalNum()));
        commentmindle.setText(mindle);
        String bad = String.format(this.getResources().getString(R.string.goods_detail_bad_score)
                , String.valueOf(comAndReply.getBadEvalNum()));
        commentbad.setText(bad);
        mScanNum.setText(String.valueOf(comAndReply.getUserAttentionNum()));
        if (comAndReply.getIsAttentionOff() == 1) {
            mAttention.setBackgroundResource(R.drawable.corners_gray_bg);
            mAttention.setText("取消关注");
        } else {
            mAttention.setBackgroundResource(R.drawable.corners_orange_bg);
            mAttention.setText("+关注");
        }
    }

    /**
     * 控制Title渐变
     *
     * @param scrollView
     * @param x
     * @param y
     * @param oldx
     * @param oldy
     */
    @Override
    public void onScrollChanged(ReboundScrollView scrollView, int x, int y, int oldx, int oldy) {

        // TODO Auto-generated method stub
        if (y <= 0) {
            title.setBackgroundColor(Color.argb(0, 22, 142, 239));//AGB由相关工具获得，或者美工提供
        } else if (y > 0 && y <= imageHeight) {
            float scale = (float) y / imageHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            title.setBackgroundColor(Color.argb((int) alpha, 22, 142, 239));
        } else {
            title.setBackgroundColor(Color.argb(255, 22, 142, 239));
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!list.get(position).getCommentUserId().equals(mCurrentUserId)) {
            if (!TextUtils.isEmpty(list.get(position).getObjNickName())) {
                status = 3;
            } else {
                status = 2;
            }
            isLeave = false;
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            ll.requestFocus();
            ll.setVisibility(View.VISIBLE);
            ll_bottom.setVisibility(View.GONE);
            edit.setHint("回复" + list.get(position).getCrNickName());
            objectId = list.get(position).getCrId();
            crComnId = list.get(position).getCrComId();
            objectUserId = list.get(position).getCommentUserId();//留言人id
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        if (list.get(position).getCommentUserId().equals(mCurrentUserId) || comAndReply.getUserId
                ().equals(mCurrentUserId)) {
            CustomDialog.initDialog(mContext);
            CustomDialog.tvTitle.setText("是否删除本条留言");
            CustomDialog.btnLeft.setText("取消");
            CustomDialog.btnRight.setText("删除");
            CustomDialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomDialog.dialog.dismiss();
                }
            });
            CustomDialog.btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomDialog.dialog.dismiss();
                    deleteMessage(list.get(position).getCrId());

                }
            });
            return true;
        }
        return false;
    }

    /**
     * 商品图片适配器
     */
    class AdvAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mAdvList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mAdvList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(mAdvList.get(position));
            View view = mAdvList.get(position);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Intent in = new Intent(ShopDetailInfoActivity.this, BigImageActivity.class);
                    in.putExtra("imagelist", (Serializable) imageUrlList);
                    startActivity(in);*/
                    initPopuptWindou(position);
                    pw.setBackgroundDrawable(new BitmapDrawable());
                    pw.setFocusable(true);
                    pw.showAtLocation(v, Gravity.CENTER, 0, 0);
                }
            });
            return mAdvList.get(position);
        }
    }

    private ViewPager vp_big_icon;
    private TextView tv_vp;
    private vpmAdapter vpAdapter;
    private ImageView downLoad;

    private void initPopuptWindou(final int position) {
        View popupWindow_view;
        popupWindow_view = View.inflate(mContext, R.layout.pw_home_detail_vpbig, null);
        popupWindow_view.setFocusable(true);
        vp_big_icon = (ViewPager) popupWindow_view.findViewById(R.id.vp_big_icon);
        int screenWidth = mContext.getWindowManager().getDefaultDisplay().getWidth();
        android.view.ViewGroup.LayoutParams params = vp_big_icon.getLayoutParams();
        params.height = screenWidth;
        params.width = screenWidth;
        vp_big_icon.setLayoutParams(params);
        vpAdapter = new vpmAdapter();
        tv_vp = (TextView) popupWindow_view.findViewById(R.id.tv_vp);
        downLoad = (ImageView) popupWindow_view.findViewById(R.id.downLoad);
        vp_big_icon.setAdapter(vpAdapter);
        vp_big_icon.setOffscreenPageLimit(imageUrlList.size());
        vp_big_icon.setCurrentItem(position);
        downLoad.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadImage(position);
            }
        });
        tv_vp.setText((position + 1) + "/" + imageUrlList.size());
        vp_big_icon.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(final int position) {
                tv_vp.setText("" + (position + 1) + "/" + imageUrlList.size());
                downLoad.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        downLoadImage(position);
                    }
                });
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        pw = new PopupWindow(popupWindow_view, LayoutParams.MATCH_PARENT, LayoutParams
                .MATCH_PARENT, true);
    }

    class vpmAdapter extends PagerAdapter {
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView1 = new ImageView(mContext);
            imageView1.setOnClickListener(new OnClickListener() {
                // ViewPager单独页面设置监听
                @Override
                public void onClick(View v) {
                    pw.dismiss();
                }
            });

            imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(mContext).load(imageUrlList.get(position).getProImageUrl()).placeholder
                    (R.drawable.ic_pic_big).into(imageView1);
            container.addView(imageView1);
            return imageView1;
        }

        @Override
        public int getCount() {
            return imageUrlList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    public void downLoadImage(final int index) {
        new Thread() {
            @Override
            public void run() {
                try {
                    // 判断SD卡是否存在，并且是否具有读写权限
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        URL url = new URL(imageUrlList.get(index).getProImageUrl());
                        // 创建连接
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.connect();
                        // 创建输入流
                        InputStream is = conn.getInputStream();

                        // 创建文件路径
                        File file = ImageUtil.setAlbumDir();
                        String urlStr = imageUrlList.get(index).getProImageUrl();
                        String str = urlStr.substring(urlStr.lastIndexOf("/"), urlStr.length());
                        File fileImage = new File(file, str);
                        FileOutputStream fos = new FileOutputStream(fileImage);

                        byte[] b = new byte[1024];
                        int n;
                        // 读写数据
                        while ((n = is.read(b)) != -1) {
                            fos.write(b, 0, n);
                        }
                        fos.close();
                        is.close();
                        ImageUtil.galleryAddPic(mContext, fileImage.getPath());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showMessageDefault(mContext, "保存成功");
                            }
                        });

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

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

    /**
     * 如需使用sso需要在onActivity中调用此方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void authSSO(int requestCode, int resultCode, Intent data) {
        /** 使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        authSSO(requestCode, resultCode, data);
    }
}
