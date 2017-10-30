package com.android.app.buystoreapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.app.buystore.utils.Bimp;
import com.android.app.buystore.utils.FileUtils;
import com.android.app.buystore.utils.PhotoActivity;
import com.android.app.buystore.utils.PicChoiceActivity;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.EditProductBean;
import com.android.app.buystoreapp.bean.RelaseDataBean;
import com.android.app.buystoreapp.bean.RelaseGroupBean;
import com.android.app.buystoreapp.bean.RelaseImageBean;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 发布第二步页面
 * Created by likaihang on 16/08/27.
 */
public class ReleaseStepTowActivity extends BaseAct implements OnClickListener {
    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;
    @ViewInject(R.id.ll_release_choice_class)
    private LinearLayout choice;
    @ViewInject(R.id.iv_release_line_1)
    private ImageView line1;
    @ViewInject(R.id.iv_release_step_2)
    private ImageView step2;
    @ViewInject(R.id.tv_choice_class)
    private TextView category;
    @ViewInject(R.id.rb_release_choice_single)
    private RadioButton single;
    @ViewInject(R.id.rb_release_choice_combination)
    private RadioButton combin;
    @ViewInject(R.id.ll_release_detail_combin)
    private LinearLayout combinName;
    @ViewInject(R.id.ll_release_detail_unit)
    private LinearLayout unit;
    @ViewInject(R.id.tv_release_step_tow_next)
    private TextView next;
    @ViewInject(R.id.et_release_detail_title)
    private EditText detailtitle;
    @ViewInject(R.id.et_release_detail_description)
    private EditText detaildescription;
    @ViewInject(R.id.lv_release_combination_list)//组合列表
    private ListView listview;
    @ViewInject(R.id.et_release_combination_name)
    private EditText mName;
    @ViewInject(R.id.et_release_combination_price)
    private EditText mPrice;
    @ViewInject(R.id.et_release_combination_surplus)
    private EditText mSurplus;
    @ViewInject(R.id.tv_combination_list_add)
    private TextView add;
    //单项资源数据
    @ViewInject(R.id.et_release_single_price)
    private EditText mSinglepric;
    @ViewInject(R.id.et_release_single_surplus)
    private EditText mSinglesurl;
    @ViewInject(R.id.et_release_single_unit)
    private EditText mSingleunit;

    @ViewInject(R.id.sv_release)
    private ScrollView scrollView;
    @ViewInject(R.id.tv_text_watcher)
    private TextView watcher;
    private String title;//标题
    private String description;//描述
    private CombinListAdapter mAdapter;
    private GridView noScrollgridview;
    private GridAdapter adapter;
    private String res;
    private String cateId;
    private RelaseDataBean data;
    private String comName;
    private String comPrice;
    private String comSurplus;
    private String sinPrice;
    private String sinSurplus;
    private String sinUnit;
    private int tab = 1;
    private String serveLabelName;
    public static ReleaseStepTowActivity releaseStepTowActivity;
    private String lable;
    //    private boolean singleIsEmpty;//用于返回判断是否已填写
    private String proId;//从管理服务传过来的商品id
    private EditProductBean editProduct;
    private String moreGroId;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    detailtitle.setText(editProduct.getProName());
                    detaildescription.setText(editProduct.getProDes());
                    lable = editProduct.getServeLabel();
                    serveLabelName = editProduct.getServeLabelName();
                    cateId = editProduct.getProCatId();
                    res = editProduct.getProCatName();
                    category.setText(res);
                    if (editProduct.getMgList().size() == 1) {
                        ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id
                                .rb_release_choice_single);
                        tab = 1;
                        combinName.setVisibility(View.GONE);
                        unit.setVisibility(View.VISIBLE);
                        moreGroId = editProduct.getMgList().get(0).getMoreGroId();
                        mSinglepric.setText(editProduct.getMgList().get(0).getMoreGroPrice() + "");
                        mSinglesurl.setText(editProduct.getMgList().get(0).getMoreGroSurplus() +
                                "");
                        mSingleunit.setText(editProduct.getMgList().get(0).getMoreGroUnit());
                    } else {
                        ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id
                                .rb_release_choice_combination);
                        tab = 2;
                        combinName.setVisibility(View.VISIBLE);
                        unit.setVisibility(View.GONE);
                        for (int i = 0; i < editProduct.getMgList().size(); i++) {
                            RelaseGroupBean group = new RelaseGroupBean();
                            group.moreGroId = editProduct.getMgList().get(i).getMoreGroId();
                            group.moreGroName = editProduct.getMgList().get(i).getMoreGroName();
                            group.moreGroPrice = editProduct.getMgList().get(i).getMoreGroPrice();
                            group.moreGroSurplus = editProduct.getMgList().get(i)
                                    .getMoreGroSurplus();
                            data.mgList.add(group);
                            mAdapter.arr.add(group);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.release_step_tow_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_action_bar);
        releaseStepTowActivity = this;
        ViewUtils.inject(this);
        proId = getIntent().getStringExtra("proId");
        init();
        addIncludeLoading(true);
        initErrorPage();
        if (proId != null && proId != "") {
            getProduct();
        }
    }

    /**
     * 得到编辑的数据
     */
    private void getProduct() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getProduct");
            obj.put("proId", proId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                Gson gson = new Gson();
                editProduct = gson.fromJson(new String(bytes), new
                        TypeToken<EditProductBean>() {
                        }.getType());
                String result = editProduct.getResult();
                if (result.equals("0")) {
                    handler.sendEmptyMessage(1000);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
            }
        });
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

    public void init() {
        scrollView.smoothScrollTo(0, 0);
        mTitleText.setText("发布资源・服务");
        line1.setBackgroundResource(R.drawable.release_step_line_lit);
        step2.setBackgroundResource(R.drawable.release_step_lit_2);
        Intent i = getIntent();
        res = i.getStringExtra("categoryName");
        cateId = i.getStringExtra("categoryID");
        serveLabelName = i.getStringExtra("serveLabelName");
        lable = i.getStringExtra("serveLabel");
        category.setText(res);
        choice.setOnClickListener(this);
        single.setOnClickListener(this);
        combin.setOnClickListener(this);
        add.setOnClickListener(this);
        next.setOnClickListener(this);
        detaildescription.addTextChangedListener(textwatcher);
        Init();
        data = new RelaseDataBean();
        data.mgList = new ArrayList<RelaseGroupBean>();
        mAdapter = new CombinListAdapter(this, data.mgList);
        listview.setAdapter(mAdapter);

        mSinglepric.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mSinglepric.getText().toString().trim().equals("0")) {
                    mSinglepric.setText("");
                }
            }
        });

        mSinglesurl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mSinglesurl.getText().toString().trim().equals("0")) {
                    mSinglesurl.setText("");
                }
            }
        });

    }

    private TextWatcher textwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() < 2500) {
                watcher.setText(s.length() + "/2500");
            } else {
                ToastUtil.showMessageDefault(ReleaseStepTowActivity.this, "超过了字数限制！");
            }
        }
    };

    private String bitmaptoString(Bitmap bitmap) {
        if (bitmap == null)
            return "";
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        singleIsEmpty = TextUtils.isEmpty(mSinglepric.getText().toString().trim());

    }

    @Override
    public void onClick(View v) {
        title = detailtitle.getText().toString().trim();
        description = detaildescription.getText().toString().trim();
        comName = mName.getText().toString().trim();
        comPrice = mPrice.getText().toString().trim();
        comSurplus = mSurplus.getText().toString().trim();
        sinPrice = mSinglepric.getText().toString().trim();
        sinSurplus = mSinglesurl.getText().toString().trim();
        sinUnit = mSingleunit.getText().toString().trim();
        data.proId = proId;
        data.serveLabel = lable;
        data.serveLabelName = serveLabelName;
        data.proCatId = cateId;
        data.proName = title;
        data.proDes = description;
        data.listimg = new ArrayList<RelaseImageBean>();

        List<String> list = new ArrayList<String>();
        if (Bimp.bmp.size() > 0) {
            for (int i = 0; i < Bimp.drr.size(); i++) {
                RelaseImageBean imageBean = new RelaseImageBean();
                String Str = Bimp.drr.get(i).substring(
                        Bimp.drr.get(i).lastIndexOf("/") + 1,
                        Bimp.drr.get(i).lastIndexOf("."));
                list.add(FileUtils.SDPATH + Str + ".JPEG");
                if (i == 0) {
                    imageBean.isCover = 1;
                } else {
                    imageBean.isCover = 0;
                }
                imageBean.proImageName = Str;
                imageBean.proImageUrl = bitmaptoString(Bimp.bmp.get(i));
                data.listimg.add(imageBean);
            }
           /* Bimp.max = 0;
            Bimp.drr.clear();
            Bimp.bmp.clear();
            //删除临时文件
            FileUtils.deleteDir();*/
        }
        switch (v.getId()) {
            case R.id.ll_release_choice_class:
                this.finish();
                break;
            case R.id.rb_release_choice_single:
                tab = 1;
                combinName.setVisibility(View.GONE);
                unit.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_release_choice_combination:
                tab = 2;
                combinName.setVisibility(View.VISIBLE);
                unit.setVisibility(View.GONE);
                break;
            case R.id.tv_release_step_tow_next:
                //check null
                if (/*singleIsEmpty && */tab == 1 && !TextUtils.isEmpty(sinPrice) && !TextUtils
                        .isEmpty(sinSurplus) && !TextUtils.isEmpty(sinUnit)) {
                    data.mgList.clear();
                    if (editProduct != null && editProduct.getMgList().size() > 1) {
                        for (int i = 0; i < editProduct.getMgList().size(); i++) {
                            RelaseGroupBean group = new RelaseGroupBean();
                            group.moreGroId = editProduct.getMgList().get(i).getMoreGroId();
                            group.moreGroName = "";
                            group.moreGroPrice = 0;
                            group.moreGroSurplus = 0;
                            data.mgList.add(group);
                        }
                    }
                    RelaseGroupBean group = new RelaseGroupBean();
//                            (RelaseGroupBean) data.mgList;
                    group.moreGroId = moreGroId;
                    group.moreGroName = title;
                    group.moreGroPrice = Double.parseDouble(sinPrice);
                    group.moreGroSurplus = Integer.parseInt(sinSurplus);
                    group.moreGroUnit = sinUnit;

                    data.mgList.add(group);
                }
                if (tab == 2) {
                    int num = data.mgList.size();
                    data.mgList.clear();
                    for (int i = 0; i < num; i++) {
                        View view = listview.getChildAt(i);
                        EditText moreGroName = (EditText) view.findViewById(R.id
                                .tv_release_combination_name);
                        EditText moreGroPrice = (EditText) view.findViewById(R.id
                                .tv_release_combination_price);
                        EditText moreGroSurplus = (EditText) view.findViewById(R.id
                                .tv_release_combination_surplus);
                        if (editProduct != null) {
                            if (i < editProduct.getMgList().size()) {
                                RelaseGroupBean group = new RelaseGroupBean();
                                group.moreGroId = editProduct.getMgList().get(i).getMoreGroId();
                                group.moreGroName = moreGroName.getText().toString().trim();
                                group.moreGroPrice = Double.valueOf(moreGroPrice.getText()
                                        .toString().trim());
                                group.moreGroSurplus = Integer.valueOf(moreGroSurplus.getText()
                                        .toString().trim());
                                data.mgList.add(group);
                            } else {
                                RelaseGroupBean group = new RelaseGroupBean();
                                group.moreGroId = "";
                                group.moreGroName = moreGroName.getText().toString().trim();
                                group.moreGroPrice = Double.valueOf(moreGroPrice.getText()
                                        .toString().trim());
                                group.moreGroSurplus = Integer.valueOf(moreGroSurplus.getText()
                                        .toString().trim());
                                data.mgList.add(group);
                            }
                        } else {
                            RelaseGroupBean group = new RelaseGroupBean();
                            group.moreGroId = "";
                            group.moreGroName = moreGroName.getText().toString().trim();
                            group.moreGroPrice = Double.valueOf(moreGroPrice.getText().toString()
                                    .trim());
                            group.moreGroSurplus = Integer.valueOf(moreGroSurplus.getText()
                                    .toString().trim());
                            data.mgList.add(group);
                        }
                    }
                }
                if (checkIsEmpty()) {
                    Intent intent = new Intent(this, ReleaseStepTwoNextAct.class);
                    intent.putExtra("data", data);
                    intent.putExtra("editProduct", editProduct);
                    startActivity(intent);
                }
                break;
            case R.id.tv_combination_list_add:
                if (!TextUtils.isEmpty(comName) && !TextUtils.isEmpty(comPrice) && !TextUtils
                        .isEmpty(comSurplus)) {
                    RelaseGroupBean group = new RelaseGroupBean();
//                            (RelaseGroupBean) data.mgList;
                    group.moreGroId = "";
                    group.moreGroName = comName;
                    group.moreGroPrice = Double.parseDouble(comPrice);
                    group.moreGroSurplus = Integer.parseInt(comSurplus);

                    data.mgList.add(group);
                    mAdapter.arr.add(group);
                    mAdapter.notifyDataSetChanged();

                    mName.setText("");
                    mPrice.setText("");
                    mSurplus.setText("");
                } else {
                    ToastUtil.showMessageDefault(this, "请填写完整的组合信息！");
                }
                break;
        }
    }

    public boolean checkIsEmpty() {
        if (TextUtils.isEmpty(res)) {
            ToastUtil.showMessageCenter(this, "请选择分类！");
            return false;
        } else if (TextUtils.isEmpty(title)) {
            ToastUtil.showMessageCenter(this, "标题不能为空！");
            return false;
        } else if (TextUtils.isEmpty(description)) {
            ToastUtil.showMessageCenter(this, "描述不能为空！");
            return false;
        } else if (Bimp.drr.size() < 1) {
            ToastUtil.showMessageCenter(this, "请至少上传一张图片！");
            return false;
        } else if (tab == 1 && data.mgList.size() < 1) {
            ToastUtil.showMessageDefault(this, "请至少添加一组信息！");
            return false;
        } else if (tab == 2 && data.mgList.size() < 2) {
            ToastUtil.showMessageDefault(this, "请至少添加两组信息！");
            return false;
        }
        return true;
    }

    public void Init() {
        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.bmp.size()) {
                    new PopupWindows(ReleaseStepTowActivity.this, noScrollgridview);
                } else {
                    Intent intent = new Intent(ReleaseStepTowActivity.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });
//        activity_selectimg_send = (TextView) findViewById(R.id.activity_selectimg_send);
//        activity_selectimg_send.setOnClickListener(new OnClickListener() {
//
//            public void onClick(View v) {
//                List<String> list = new ArrayList<String>();
//                for (int i = 0; i < Bimp.drr.size(); i++) {
//                    String Str = Bimp.drr.get(i).substring(
//                            Bimp.drr.get(i).lastIndexOf("/") + 1,
//                            Bimp.drr.get(i).lastIndexOf("."));
//                    list.add(FileUtils.SDPATH + Str + ".JPEG");
//                }
//                // 高清的压缩图片全部就在  list 路径里面了
//                // 高清的压缩过的 bmp 对象  都在 Bimp.bmp里面
//                // 完成上传服务器后 .........
//                FileUtils.deleteDir();
//            }
//        });
    }

    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater; // 视图容器
        private int selectedPosition = -1;// 选中的位置
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if (Bimp.bmp.size() < 9) {

                return (Bimp.bmp.size() + 1);
            }
            return 9;
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        /**
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int coord = position;
            ViewHolder holder = null;
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.bmp.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.bmp.get(position));
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.drr.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            try {
                                if (Bimp.drr.size() > 0) {
                                    String path = Bimp.drr.get(Bimp.max);
                                    System.out.println(path);
                                    Bitmap bm = Bimp.revitionImageSize(path);
                                    Bimp.bmp.add(bm);
                                    String newStr = path.substring(
                                            path.lastIndexOf("/") + 1,
                                            path.lastIndexOf("."));
                                    FileUtils.saveBitmap(bm, "" + newStr);
                                    Bimp.max += 1;
                                    Message message = new Message();
                                    message.what = 1;
                                    handler.sendMessage(message);
                                }
                            } catch (IOException e) {

                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(LayoutParams.MATCH_PARENT);
            setHeight(LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(ReleaseStepTowActivity.this,
                            PicChoiceActivity.class);
                    intent.putExtra("flag", "9");
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        StringBuffer sDir = new StringBuffer();
        if (hasSDcard()) {
            sDir.append(Environment.getExternalStorageDirectory() + "/MyPicture/");
        } else {
            String dataPath = Environment.getRootDirectory().getPath();
            sDir.append(dataPath + "/MyPicture/");
        }

        File fileDir = new File(sDir.toString());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File file = new File(fileDir, String.valueOf(System.currentTimeMillis()) + ".jpg");

        path = file.getPath();
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    public static boolean hasSDcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.drr.size() < 9 && resultCode == -1) {
                    Bimp.drr.add(path);
                }
                break;
        }
    }

    private class CombinListAdapter extends BaseAdapter {
        private final LayoutInflater inflater;
        private Context context;
        public List<RelaseGroupBean> arr;

        public CombinListAdapter(Context context, List<RelaseGroupBean> list) {
            super();
            this.context = context;
            this.arr = list;
            inflater = LayoutInflater.from(context);
            arr = new ArrayList<RelaseGroupBean>();
            for (int i = 0; i < arr.size(); i++) {
                arr.add((RelaseGroupBean) list);
            }
        }

        @Override
        public int getCount() {
            return arr.size();
        }

        @Override
        public Object getItem(int position) {
            return arr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.release_combination_list_item, null);
                holder.mName = (EditText) convertView.findViewById(R.id
                        .tv_release_combination_name);//名称
                holder.mPrice = (EditText) convertView.findViewById(R.id
                        .tv_release_combination_price);//价格
                holder.mMurplus = (EditText) convertView.findViewById(R.id
                        .tv_release_combination_surplus);//库存
                holder.dele = (ImageView) convertView.findViewById(R.id.iv_combin_dele);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (editProduct != null && position < editProduct.getMgList().size()) {
                holder.dele.setVisibility(View.GONE);
            } else {
                holder.dele.setVisibility(View.VISIBLE);
            }
            holder.mName.setText(arr.get(position).getMoreGroName());
            double pri = arr.get(position).getMoreGroPrice();
            holder.mPrice.setText(pri + "");
            holder.mMurplus.setText(arr.get(position).getMoreGroSurplus() + "");
            holder.dele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arr.remove(position);
                    mAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }

        public class ViewHolder {
            public EditText mName;
            public EditText mPrice;
            public EditText mMurplus;
            public ImageView dele;
        }
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
    protected void onDestroy() {
        super.onDestroy();
        Bimp.max = 0;
        Bimp.drr.clear();
        Bimp.bmp.clear();
        //删除临时文件
        FileUtils.deleteDir();
    }
}
