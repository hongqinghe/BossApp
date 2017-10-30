package com.android.app.buystoreapp.other;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.managementservice.QueryComplanyBean;
import com.android.app.buystoreapp.managementservice.SubscribeActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * 企业认证
 * Created by 尚帅波 on 2016/9/28.
 */
public class CompanyCertifiActivity extends BaseAct implements View.OnClickListener {
    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    private EditText et_name, et_companyName, et_companyPhoto, et_incenseNumber;
    private ImageView iv_photoUp;
    private LinearLayout ll_upAdd, ll_photoUp;
    private Button btn_confirm;

    private PopupWindow popupWindow;
    private static int TYPE_CAMERA = 1;  //照相请求相应码
    private static int TYPE_IMAGE = 2;  //相册请求相应码

    private String mPhotoPath; //拍照获得图片路径  或者相册选择的图片路径
    private String strImg; //压缩后的照片str
    private String userId;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SubscribeActivity.HANDLE_LOADMORE:
                    if (queryComplanyBean.getComplyList() != null) {
                        et_name.setText(userName);
                        et_companyName.setText(companyName);
                        et_companyPhoto.setText(phone);
                        et_incenseNumber.setText(cardId);
                        if (!TextUtils.isEmpty(path)) {
                            ll_photoUp.setVisibility(View.GONE);
                            iv_photoUp.setVisibility(View.VISIBLE);
                            Picasso.with(CompanyCertifiActivity.this)
                                    .load(path)
                                    .placeholder(R.drawable.ic_boss_default)
                                    .into(iv_photoUp);
                        }

                        if (queryComplanyBean.getComplyList().getStatus() == 0) {
                            btn_confirm.setEnabled(true);
                            btn_confirm.setText("审核未通过,请重新提交");
                        } else if (queryComplanyBean.getComplyList().getStatus() == 1) {
                            btn_confirm.setEnabled(true);
                            btn_confirm.setText("审核已通过");
                        } else if (queryComplanyBean.getComplyList().getStatus() == 2) {
                            btn_confirm.setEnabled(false);
                            btn_confirm.setText("正在审核中...");
                        }
                    }

                    break;
            }
        }
    };
    private String path;
    private String companyName;
    private String cardId;
    private String phone;
    private String userName;
    private Bitmap bitmap;
    private QueryComplanyBean queryComplanyBean;
    private String resultNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_certifi_company);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_action_bar);
        ViewUtils.inject(this);
        mTitleText.setText("企业认证");
        initView();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        load();
    }

    @Override
    protected void load() {
        super.load();
        //网络请求数据
        queryComplany();
    }

    /**
     * 查询企业认证
     */
    private void queryComplany() {
        final JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "queryComplany");
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("--查询企业认证提交数据---", "queryComplany obj" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Log.e("--查询企业认证提交成功返回数据---", "queryComplany  bytes==" + new String(bytes));
                Gson gson = new Gson();
                queryComplanyBean = gson.fromJson(new String(bytes), new TypeToken<QueryComplanyBean>() {
                }.getType());
                String result = queryComplanyBean.getResult();
                resultNote = queryComplanyBean.getResultNote();
                if ("0".equals(result)) {
                    if (queryComplanyBean.getComplyList() != null) {
                        path = queryComplanyBean.getComplyList().getPath();
                        companyName = queryComplanyBean.getComplyList().getCompanyName();
                        cardId = queryComplanyBean.getComplyList().getCardId();
                        phone = queryComplanyBean.getComplyList().getPhone();
                        userName = queryComplanyBean.getComplyList().getUserName();
                        Log.e("path", path);
//                    Log.e("companyName",companyName);
                        Log.e("cardId", cardId);
                        Log.e("phone", phone);
                        Log.e("userName", userName);
                        mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
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
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null) {
            bitmap.recycle();
        }
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_companyName = (EditText) findViewById(R.id.et_companyName);
        et_companyPhoto = (EditText) findViewById(R.id.et_companyPhoto);
        et_incenseNumber = (EditText) findViewById(R.id.et_incenseNumber);
        ll_upAdd = (LinearLayout) findViewById(R.id.ll_upAdd);
        ll_photoUp = (LinearLayout) findViewById(R.id.ll_photoUp);
        iv_photoUp = (ImageView) findViewById(R.id.iv_photoUp);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        ll_upAdd.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    @OnClick(R.id.id_custom_back_image)
    public void onCustomBarBackClicked(View v) {
        switch (v.getId()) {
            case R.id.id_custom_back_image:
                this.finish();
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_upAdd:
                getPopupWindow();
                break;
            case R.id.btn_confirm:
                if (TextUtils.isEmpty(et_name.getText())) {
                    ToastUtil.showMessageDefault(this, "请填写正确的姓名");
                } else if (TextUtils.isEmpty(et_companyName.getText())) {
                    ToastUtil.showMessageDefault(this, "请填写正确的公司名称");
                } else if (TextUtils.isEmpty(et_companyPhoto.getText())) {
                    ToastUtil.showMessageDefault(this, "请填写正确的电话或手机号");
                } else if (TextUtils.isEmpty(et_incenseNumber.getText())) {
                    ToastUtil.showMessageDefault(this, "请填写正确的执照号码");
                } else if (iv_photoUp.getVisibility() == View.GONE) {
                    ToastUtil.showMessageDefault(this, "请上传证件照片");
                } else {
                    startWhiteLoadingAnim();
                    try {
                        if (mPhotoPath != null) {
                            strImg = ImageUtil.bitMapToString(ImageUtil.revitionImageSize
                                    (mPhotoPath));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("cmd", "checkComplany");
                        obj.put("userName", et_name.getText().toString());
                        obj.put("companyName", et_companyName.getText().toString());
                        obj.put("phone", et_companyPhoto.getText().toString());
                        obj.put("cardId", et_incenseNumber.getText().toString());
                        if (mPhotoPath == null) {
                            obj.put("path", "");
                        } else {
                            obj.put("path", strImg);
                        }
                        obj.put("userId", userId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("修改企业认证提交数据obj", obj.toString());
                    HttpUtils.post(CompanyCertifiActivity.this, obj, new AsyncHttpResponseHandler
                            () {

                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            Log.e("修改企业认证返回数据bytes", new String(bytes));
                            stopLoadingAnim();
                            try {
                                JSONObject obj = new JSONObject(new String(bytes));
                                String result = obj.getString("result");
                                if (result.equals("0")) {
                                    ToastUtil.showMessageDefault(mContext, "提交成功，请等待认证结果！");
                                    btn_confirm.setEnabled(false);
                                    btn_confirm.setText("正在审核中...");
                                    finish();
                                } else {
                                    ToastUtil.showMessageDefault(mContext, "提交失败，请稍后尝试！");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable
                                throwable) {
                            ToastUtil.showMessageDefault(mContext, "连接超时！");
                            stopLoadingAnim();
                        }
                    }, new HttpUtils.RequestNetworkError() {
                        @Override
                        public void networkError() {
                            stopLoadingAnim();
                        }
                    });
                }
                break;

            case R.id.btn_camera:
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                //指定图片保存的位置
                File file = ImageUtil.createImageFile();
                //得到图片路径
                mPhotoPath = file.getAbsolutePath();
                //需要将拍摄的照片存储在SDcard的时候,必须加上下边这句代码，但是加上以后onActivityResult参数中 intent 为null
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, TYPE_CAMERA);
                popupWindow.dismiss();
                break;
            case R.id.btn_photo:
                ImageUtil.openPhoto(this, TYPE_IMAGE);
                popupWindow.dismiss();
                break;
            case R.id.btn_cancel:
                popupWindow.dismiss();
                break;
        }
    }

    /**
     * 初始化PopupWindow
     */
    public void getPopupWindow() {
        popupWindow = new PopupWindow(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.custom_popupwindow, null);
        popupWindow.setContentView(view);

        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);

        backgroundAlpha(0.5f);

        popupWindow.setAnimationStyle(R.style.PopupWindow);
        popupWindow.showAtLocation(et_name, Gravity.BOTTOM, 0, 0);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopOnDismissListner());

        Button btn_camera = (Button) view.findViewById(R.id.btn_camera);
        Button btn_photo = (Button) view.findViewById(R.id.btn_photo);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_camera.setOnClickListener(this);
        btn_photo.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    /**
     * popupWindow弹出时，其他地方变暗
     *
     * @param alpha
     */
    private void backgroundAlpha(float alpha) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = alpha;
        this.getWindow().setAttributes(params);
    }


    /**
     * popupWindow关闭事件
     */
    private class PopOnDismissListner implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TYPE_CAMERA) {
            if (resultCode == RESULT_OK) {
                //将拍照的照片添加到图库,这样可以在手机的图库程序中看到程序拍摄的照片
                ImageUtil.galleryAddPic(this, mPhotoPath);
                //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ll_photoUp.setVisibility(View.GONE);
                iv_photoUp.setVisibility(View.VISIBLE);
                bitmap = ImageUtil.loadBitmap(mPhotoPath, 800, 480);
                iv_photoUp.setImageBitmap(bitmap);
            } else {
                //取消照相后，删除已经创建的临时文件
                ImageUtil.deleteImage();
            }
        } else if (requestCode == TYPE_IMAGE) {
            if (resultCode == RESULT_OK) {
                mPhotoPath = ImageUtil.getPhotoPathByLocalUri(this, data);
                ll_photoUp.setVisibility(View.GONE);
                iv_photoUp.setVisibility(View.VISIBLE);
                bitmap = ImageUtil.loadBitmap(mPhotoPath, 800, 480);
                iv_photoUp.setImageBitmap(bitmap);
                //bitmap.recycle();
            } else {

            }
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
