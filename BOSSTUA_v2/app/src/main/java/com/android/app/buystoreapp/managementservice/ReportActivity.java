package com.android.app.buystoreapp.managementservice;

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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.Bimp;
import com.android.app.buystore.utils.FileUtils;
import com.android.app.buystore.utils.PhotoActivity;
import com.android.app.buystore.utils.PicChoiceActivity;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.AddReportCmd;
import com.android.app.buystoreapp.other.ImageUtil;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.android.app.utils.LogUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 举报
 * <p/>
 * weilin
 */
public class ReportActivity extends BaseAct {
    private ImageButton ib_report_back;//返回


    private EditText et_report_context;//您可以写下对举报问题的描述

    private TextView tv_report_number;//0/200

    private ImageView iv_report_image;//穿过来商品的图标

    private TextView tv_report_title;//传过来的标题

    private RadioGroup rg_choose_ischecked;//虚假、违法信息

    private Button btn_report_submit;


    private GridView noScrollgridview_report;
    private GridAdapter adapter;

    private int num = 200;

    private String passiveUserID;//被举报人ID
    private String passiveProID;//被举报人商品ID
    private String proName;//被举报商品标题
    private String proImageMin;//被举报商品图标
    private String radioButton;//选中的radioButton

    private List<String> paths; //存放选择前三张图片的路径
    private List<AddReportCmd.ImagListBean> imgStr = new ArrayList<AddReportCmd.ImagListBean>(); //存放选择前三张图片的string字符串

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ViewUtils.inject(this);
        passiveUserID = getIntent().getStringExtra("passiveUserID");
        passiveProID = getIntent().getStringExtra("passiveProID");
        proName = getIntent().getStringExtra("proName");
        proImageMin = getIntent().getStringExtra("proImageMin");

        initView();
        addIncludeLoading(true);
        initErrorPage();
    }


    @Override
    protected void load() {
        startWhiteLoadingAnim();
        String userId = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
        AddReportCmd addReportCmd = new AddReportCmd();
        addReportCmd.setCmd("addReport");
        addReportCmd.setPassiveUserID(passiveUserID);
        addReportCmd.setPassiveProID(passiveProID);
        addReportCmd.setReportUserID(userId);
        addReportCmd.setReasonFixed(radioButton);
        addReportCmd.setReasonUnset(et_report_context.getText().toString() != null ? et_report_context.getText().toString().trim() : "");
        addReportCmd.setImagList(imgStr);
        Gson gson = new Gson();
        String request = gson.toJson(addReportCmd);
        JSONObject obj = null;
        try {
            obj = new JSONObject(request);
            Log.e("obj", obj.toString());
            LogUtil.LogAll("举报提交数据" + obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                JSONObject obj = null;
                try {
                    obj = new JSONObject(new String(bytes));
                    String result = obj.getString("result");
                    String resultNote = obj.getString("resultNote");
                    if (result.equals("0")) {
                        ToastUtil.showMessageDefault(mContext, resultNote);
                        finish();
                    } else {
                        ToastUtil.showMessageDefault(mContext, resultNote);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(mContext, "网络请求失败");
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
            }
        });
    }

    private void initView() {

        ib_report_back = (ImageButton) findViewById(R.id.ib_report_back);//返回
        et_report_context = (EditText) findViewById(R.id.et_report_context);//您可以写下对举报问题的描述
        tv_report_number = (TextView) findViewById(R.id.tv_report_number);//0/200
        iv_report_image = (ImageView) findViewById(R.id.iv_report_image);//穿过来商品的图标
        tv_report_title = (TextView) findViewById(R.id.tv_report_title);//传过来的标题
        rg_choose_ischecked = (RadioGroup) findViewById(R.id.rg_choose_ischecked);//虚假、违法信息
        radioButton = ((RadioButton) findViewById(rg_choose_ischecked.getCheckedRadioButtonId())).getText().toString().trim();
        btn_report_submit = (Button) findViewById(R.id.btn_report_submit);
        noScrollgridview_report = (GridView) findViewById(R.id.noScrollgridview_report);

        tv_report_title.setText(proName);
        if (!TextUtils.isEmpty(proImageMin)) {
            Picasso.with(mContext).load(proImageMin)
                    .placeholder(R.drawable.ic_default)
                    .resize(70, 70)
                    .error(R.drawable.ic_default).into(iv_report_image);
        } else {
            Picasso.with(mContext).load(R.drawable.ic_default).into(iv_report_image);
        }

        rg_choose_ischecked.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton btn = (RadioButton) findViewById(group.getCheckedRadioButtonId());
                radioButton = btn.getText().toString();
            }
        });


        ib_report_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_report_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Bimp.drr.size() > 3) {
                    paths = Bimp.drr.subList(0, 3);
                } else {
                    paths = Bimp.drr;
                }

                for (int i = 0; i < paths.size(); i++) {
                    try {
                        if (!TextUtils.isEmpty(paths.get(i))) {
                            String str = ImageUtil.bitMapToString(ImageUtil.revitionImageSize(paths
                                    .get(i)));
                            AddReportCmd.ImagListBean img = new AddReportCmd.ImagListBean();
                            img.setWebrootpath(str);
                            imgStr.add(img);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                load();
            }
        });

        noScrollgridview_report.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview_report.setAdapter(adapter);
        noScrollgridview_report.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(ReportActivity.this.getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (arg2 == Bimp.bmp.size()) {
                    new PopupWindows(ReportActivity.this, noScrollgridview_report);
                } else {
                    Intent intent = new Intent(ReportActivity.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });

        et_report_context.addTextChangedListener(new TextWatcher() {
            private CharSequence wordNum;//记录输入的字数
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;//实时记录输入的字数

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (wordNum.length() >= num) {
                    Toast.makeText(ReportActivity.this, "只能输入200个字", Toast.LENGTH_SHORT).show();
                }
                int number = s.length();
                //TextView显示剩余字数
                tv_report_number.setText("" + number + "/200");
                selectionStart = et_report_context.getSelectionStart();
                selectionEnd = et_report_context.getSelectionEnd();
                if (wordNum.length() > num) {

                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    et_report_context.setText(s);
                    et_report_context.setSelection(tempSelection);//设置光标在最后
                }
            }
        });
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
            if (Bimp.bmp.size() < 3) {

                return (Bimp.bmp.size() + 1);
            }
            return 3;
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
                if (position == 3) {
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
                    case 2:
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
                            message.what = 2;
                            handler.sendMessage(message);
                            break;
                        } else {
                            try {
                                String path = Bimp.drr.get(Bimp.max);
                                Bitmap bm = Bimp.revitionImageSize(path);
                                Bimp.bmp.add(bm);
                                String newStr = path.substring(
                                        path.lastIndexOf("/") + 1,
                                        path.lastIndexOf("."));
                                FileUtils.saveBitmap(bm, "" + newStr);
                                Bimp.max += 1;
                                Message message = new Message();
                                message.what = 2;
                                handler.sendMessage(message);
                            } catch (IOException e) {

                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }
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

            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
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
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(ReportActivity.this,
                            PicChoiceActivity.class);
                    intent.putExtra("flag", "3");
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    private static final int TAKE_PICTURE_A = 0x000000;
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
        startActivityForResult(openCameraIntent, TAKE_PICTURE_A);
    }

    public static boolean hasSDcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE_A:
                if (Bimp.drr.size() < 3 && resultCode == -1) {
                    Bimp.drr.add(path);
                }
                break;
        }
    }

    protected void onRestart() {
        super.onRestart();
        adapter.update();
        if (adapter.getCount() >= 3) {
            Toast.makeText(this, "只能显示3张图片!", Toast.LENGTH_SHORT).show();
        }

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
}




