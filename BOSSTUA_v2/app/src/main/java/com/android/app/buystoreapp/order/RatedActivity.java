package com.android.app.buystoreapp.order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.BitmapListBean;
import com.android.app.buystoreapp.bean.CommentBean;
import com.android.app.buystoreapp.bean.CommentListBean;
import com.android.app.buystoreapp.other.ImageUtil;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RatedActivity extends BaseAct implements View.OnClickListener {

    private ImageView iv_back;
    private ListView lv_rated;
    private CheckBox cb_ischeck;
    private Button btn_submit_evaluation;
    private List<OrderProduct> orderProduct = new ArrayList<OrderProduct>();
    private List<BitmapListBean> list = new ArrayList<BitmapListBean>();
    private RatedListAdapter adapter;

    private PopupWindow popupWindow;
    private static int TYPE_CAMERA = 1;  //照相请求相应码
    private static int TYPE_IMAGE = 2;  //相册请求相应码

    private String mPhotoPath; //拍照获得图片路径  或者相册选择的图片路径
    private CommentListBean data;

    private String orderId;
    private String evalLevel = "2";
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rated);
        orderProduct = (List<OrderProduct>) getIntent().getSerializableExtra("orderProduct");
        orderId = getIntent().getStringExtra("orderId");
        data = new CommentListBean();
        data.listBean = new ArrayList<CommentBean>();
        initdata();
        initView();
        addIncludeLoading(true);
        initErrorPage();
    }

    private void initdata() {
        for (int i = 0; i < orderProduct.size(); i++) {
            BitmapListBean data = new BitmapListBean();
            List<Bitmap> bitmaps = new ArrayList<Bitmap>();
            data.setBitmaps(bitmaps);
            list.add(i, data);
        }
    }

    @Override
    protected void load() {
        super.load();
        startWhiteLoadingAnim();
    }

    private int position;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 101:
                    position = msg.arg1;
                    getPopupWindow();
            }
        }
    };

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        cb_ischeck = (CheckBox) findViewById(R.id.cb_ischeck);
        cb_ischeck.setChecked(true);
        btn_submit_evaluation = (Button) findViewById(R.id.btn_submit_evaluation);
        btn_submit_evaluation.setOnClickListener(this);
        lv_rated = (ListView) findViewById(R.id.lv_rated);
        adapter = new RatedListAdapter(this, orderProduct, handler, list);
        lv_rated.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit_evaluation:
                for (int i = 0; i < orderProduct.size(); i++) {
                    CommentBean bean = new CommentBean();
                    EditText et = (EditText) lv_rated.getChildAt(i).findViewById(R.id.rated_context);
                    bean.evalContent = et.getText().toString().trim();
                    RadioGroup rg_chacked = (RadioGroup) lv_rated.getChildAt(i).findViewById(R.id.rg_chacked);
                    String str = ((RadioButton) findViewById(rg_chacked.getCheckedRadioButtonId())).getText().toString().trim();
                    /*rg_chacked.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            switch (checkedId) {
                                case R.id.rb_praise:
                                    evalLevel = "2";
                                    break;
                                case R.id.rb_comment:
                                    evalLevel = "1";
                                    break;
                                case R.id.rb_bad:
                                    evalLevel = "0";
                                    break;
                            }
                            ToastUtil.showMessageDefault(mContext,evalLevel);
                        }
                    });*/
                    if (str.equals("好评")){
                        evalLevel = "2";
                    }else if (str.equals("中评")){
                        evalLevel = "1";
                    }else if (str.equals("差评")){
                        evalLevel = "0";
                    }
                    bean.evalLevel = evalLevel;
                    if (cb_ischeck.isChecked()) {
                        bean.evalAnonymity = "0";
                    } else {
                        bean.evalAnonymity = "1";
                    }
                    bean.evalUserId = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
                    bean.proId = orderProduct.get(i).getProId();
                    List<Bitmap> bitmaps = list.get(position).getBitmaps();
                    bean.listimg = new String[bitmaps.size()];
                    for (int j = 0; j < bitmaps.size(); j++) {
                        Log.d("listbitmap---", bitmaps.get(j) + "");
                        bean.listimg[j] = bitmaptoString(bitmaps.get(j));
                    }
                    data.listBean.add(bean);
                }
                Log.d("数据长度--", data.listBean.size() + "");
                comentOrder();
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

    private void comentOrder() {
        startWhiteLoadingAnim();
        CommentListBean date = new CommentListBean("updateOrder", 2, 8, orderId, 0, data.listBean);
        JSONObject obj = null;
        try {
            obj = new JSONObject(new Gson().toJson(date));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        LogUtil.LogAll("obj.tostring--", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                Log.d("提交评价返回值--",new String(bytes));
                JSONObject obj = null;
                try {
                    obj = new JSONObject(new String(bytes));
                    if (obj.getString("result").equals("0")) {
                        ToastUtil.showMessageDefault(mContext,"提交成功！");
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
        popupWindow.showAtLocation(iv_back, Gravity.BOTTOM, 0, 0);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TYPE_CAMERA) {
            if (resultCode == RESULT_OK) {
                //将拍照的照片添加到图库,这样可以在手机的图库程序中看到程序拍摄的照片
                ImageUtil.galleryAddPic(this, mPhotoPath);
                //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Bitmap bitmap = ImageUtil.loadBitmap(mPhotoPath, 1000, 1000);

                list.get(position).getBitmaps().add(bitmap);
                adapter.notifyDataSetChanged();

            } else {
                //取消照相后，删除已经创建的临时文件
                ImageUtil.deleteImage();
            }
        } else if (requestCode == TYPE_IMAGE) {
            if (resultCode == RESULT_OK) {
                mPhotoPath = ImageUtil.getPhotoPathByLocalUri(this, data);
                Bitmap bitmap = ImageUtil.loadBitmap(mPhotoPath, 1000, 1000);

                list.get(position).getBitmaps().add(bitmap);
                adapter.notifyDataSetChanged();

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

    //点击屏幕让软键盘隐藏
     /*rl_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });*/
}
