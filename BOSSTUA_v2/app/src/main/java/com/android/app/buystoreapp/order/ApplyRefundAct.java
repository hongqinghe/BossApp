package com.android.app.buystoreapp.order;

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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.app.buystore.utils.Bimp;
import com.android.app.buystore.utils.FileUtils;
import com.android.app.buystore.utils.PhotoActivity;
import com.android.app.buystore.utils.PicChoiceActivity;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.android.app.utils.LogUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * $desc 申诉/退款
 * Created by likaihang on 16/11/09.
 */
public class ApplyRefundAct extends BaseAct implements View.OnClickListener, TextWatcher {
    private String orderId;
    private EditText mReason;
    private String reason;
    private int status;// 0申诉  1退款  2拒绝退款
    private TextView size;
    private String userid;
    private GridView noScrollgridview;
    private GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.apply_refund_layout);
        userid = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
        orderId = getIntent().getStringExtra("orderId");
        status = getIntent().getIntExtra("status", 0);
        init();
        initErrorPage();
        addIncludeLoading(true);
    }

    private void init() {
        findViewById(R.id.iv_back).setOnClickListener(this);

        findViewById(R.id.tv_post_apply).setOnClickListener(this);
        mReason = (EditText) findViewById(R.id.et_refund_reason);
        if (status == 0) {
            ((TextView) findViewById(R.id.tv_title)).setText("申诉申请");
            mReason.setHint(getString(R.string.apply_refund_order_app_reason));
        } else if (status == 1) {
            ((TextView) findViewById(R.id.tv_title)).setText("退款申请");
            mReason.setHint(getString(R.string.apply_refund_order_reason));
        } else {
            ((TextView) findViewById(R.id.tv_title)).setText("拒绝退款");
            mReason.setHint(getString(R.string.apply_refund_order_reduse_reason));
        }
        mReason.addTextChangedListener(this);
        size = (TextView) findViewById(R.id.tv_size);
        Init();
    }

    /**
     * 改变订单状态
     *
     * @author likaihang
     * creat at @time 16/11/07 13:49
     */
    private void updateOrder(int status, int userStatus, int courierStatus, String[] array) {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < array.length; i++) {
            jsonArray.put(array[i]);
        }
        try {
            obj.put("cmd", "updateOrder");
            obj.put("style", 2);
            obj.put("status", status);
            obj.put("orderId", orderId);
            obj.put("userStatus", userStatus);
            obj.put("courierStatus", courierStatus);
            obj.put("courierWhy", reason);
            obj.put("thisUserId", userid);
            obj.put("courierListimg", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtil.LogAll("申请提交--", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                JSONObject obj = null;
                try {
                    obj = new JSONObject(new String(bytes));
                    if (obj.getString("result").equals("0")) {
                        finish();
                    } else {
                        ToastUtil.showMessageDefault(mContext, obj.getString("resultNote"));
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
                stopLoadingAnim();
            }
        });
    }

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
    public void onClick(View v) {
        reason = mReason.getText().toString().trim();
        String[] arr = new String[Bimp.bmp.size()];
        if (Bimp.bmp.size() > 0) {
            for (int i = 0; i < Bimp.drr.size(); i++) {
                arr[i] = bitmaptoString(Bimp.bmp.get(i));
            }
        }

        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_post_apply:
                //提交
                if (!TextUtils.isEmpty(reason)) {
                    if (status == 0) {//申诉
                        updateOrder(6, 0, 2, arr);
                    } else if (status == 1) {//退款
                        updateOrder(5, 0, 1, arr);
                    } else {//拒绝
                        updateOrder(13, 1, 3, arr);
                    }
                } else {
                    ToastUtil.showMessageDefault(mContext, "理由不能为空！");
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() < 100) {
            size.setText(s.length() + "/100");
        } else {
            ToastUtil.showMessageDefault(mContext, "输入内容超限！");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    public void Init() {
        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.bmp.size()) {
                    new PopupWindows(ApplyRefundAct.this, noScrollgridview);
                } else {
                    Intent intent = new Intent(ApplyRefundAct.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
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
                    Intent intent = new Intent(ApplyRefundAct.this,
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
