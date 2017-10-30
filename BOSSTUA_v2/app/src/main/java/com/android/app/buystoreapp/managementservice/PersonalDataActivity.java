package com.android.app.buystoreapp.managementservice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.UpdateInfoBean;
import com.android.app.buystoreapp.bean.UpdateModifyBeanBean;
import com.android.app.buystoreapp.location.AddressActivity;
import com.android.app.buystoreapp.other.ImageUtil;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * 个人资料页面
 * weilin
 */
public class PersonalDataActivity extends BaseAct implements View.OnClickListener {
    private static final int UPDATE_SUCCESS = 1001;
    /**
     * 性别 *
     */
    private RadioGroup rg_gender_checked;
    /**
     * 返回
     */
    private ImageButton iv_back;
    /**
     * 头像监听
     */
    private RelativeLayout rl_personal_data_hand;
    /**
     * 头像位置
     */
    private ImageView id_updateuserinfo_userinfo_usericon;
    /**
     * 姓名
     */
    private EditText et_personal_data_name;
    /**
     * 城市监听器
     */
    private RelativeLayout rl_personal_data_city;
    /**
     * 城市内容获取
     */
    private TextView tv_personal_data_city;
    /**
     * 公司/品牌内容获取
     */
    private EditText et_personal_data_company;
    /**
     * 职位内容获取
     */
    private EditText et_personal_data_position;
    /**
     * 保存
     */
    private Button btn_personal_data_save;

    private TextView tv_title;

    private static final int ICON_WIDTH_AND_HEIGHT = 200;


    private PopupWindow popupWindow;
    private static int TYPE_CAMERA = 1000;  //照相请求相应码
    private static int TYPE_IMAGE = 2000;  //相册请求相应码
    private String mPhotoPath; //拍照获得图片路径  或者相册选择的图片路径
    private String userId;
    private PersonalDataBean.InfoBean personalData;
    private RadioButton rg_gender_checked_boy;
    private RadioButton rg_gender_checked_girl;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SubscribeActivity.HANDLE_LOADMORE:
                    InfoData();
                    break;
                case UPDATE_SUCCESS:
                    if (updateModifyData != null) {

                        SharedPreferenceUtils.saveHeadicon(context, updateModifyData.getHeadicon());
                        Log.e("头像url", updateModifyData.getHeadicon() + "");
                        SharedPreferenceUtils.saveNickName(context, updateModifyData.getNikeName());
                        Log.e("昵称", updateModifyData.getNikeName() + "");
                        SharedPreferenceUtils.saveUserPosition(context, updateModifyData.getUserPosition());
                        Log.e("职业", updateModifyData.getUserPosition() + "");
                        ToastUtil.showMessageDefault(context, resultNote);
                        PersonalDataActivity.this.finish();
                    }

                    break;
            }
        }
    };
    private String gender;
    private String provinceId;
    private String cityid;
    private String areaId;
    private String result;
    private String userSex;
    private Context context;
    private String resultNote;
    private UpdateModifyBeanBean updateModifyData;

    private void InfoData() {
        if (personalData != null) {
            if (!TextUtils.isEmpty(personalData.getHeadicon()))
                Picasso.with(PersonalDataActivity.this)
                        .load(personalData.getHeadicon())
                        .resize(40, 40)
                        .placeholder(R.drawable.ic_boss_default)
                        .into(id_updateuserinfo_userinfo_usericon);
        }
        et_personal_data_name.setText(personalData.getNikeName());

        if ("男".equals(personalData.getUserSex())) {
            rg_gender_checked_boy.setChecked(true);
        } else if ("女".equals(personalData.getUserSex())) {
            rg_gender_checked_girl.setChecked(true);
        }

        tv_personal_data_city.setText(personalData.getUserCityString());

        et_personal_data_company.setText(personalData.getCompanyBrand());

        et_personal_data_position.setText(personalData.getUserPosition());

        userSex = personalData.getUserSex();

        toRight(et_personal_data_name);
        toRight(et_personal_data_company);
        toRight(et_personal_data_position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        initView();
        context = this;
        setListener();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        load();
    }


    private void setListener() {
        btn_personal_data_save.setOnClickListener(this);
        rl_personal_data_city.setOnClickListener(this);
        rl_personal_data_hand.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        rg_gender_checked.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = (RadioButton) findViewById(group.getCheckedRadioButtonId());
                gender = button.getText().toString().trim();
            }
        });
    }

    @Override
    protected void load() {
        super.load();

        getModifyInformation();
    }

    private void getModifyInformation() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getModifyInformation");
            obj.put("userid", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("PersonalDataActivity", "getModifyInformation  obj==" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("PersonalDataActivity", "bytes==" + new String(bytes));
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                PersonalDataBean personalDataBean = gson.fromJson(new String(bytes), new TypeToken<PersonalDataBean>() {
                }.getType());
                String result = personalDataBean.getResult();
                if ("0".equals(result)) {
                    personalData = personalDataBean.getInfo();
                    mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
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

    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string.personal_data));
        btn_personal_data_save = (Button) findViewById(R.id.btn_personal_data_save);
        et_personal_data_name = (EditText) findViewById(R.id.et_personal_data_name);
        rl_personal_data_city = (RelativeLayout) findViewById(R.id.rl_personal_data_city);
        tv_personal_data_city = (TextView) findViewById(R.id.tv_personal_data_city);
        et_personal_data_company = (EditText) findViewById(R.id.et_personal_data_company);
        id_updateuserinfo_userinfo_usericon = (ImageView) findViewById(R.id
                .id_updateuserinfo_userinfo_usericon);
        et_personal_data_position = (EditText) findViewById(R.id.et_personal_data_position);
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        rg_gender_checked = (RadioGroup) findViewById(R.id.rg_gender_checked);
        rl_personal_data_hand = (RelativeLayout) findViewById(R.id.rl_personal_data_hand);
        rg_gender_checked_boy = (RadioButton) findViewById(R.id.rg_gender_checked_boy);
        rg_gender_checked_girl = (RadioButton) findViewById(R.id.rg_gender_checked_girl);

    }

    public void toRight(EditText v) {
        CharSequence text = v.getText();
        //Debug.asserts(text instanceof Spannable);
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                this.finish();
                break;

            case R.id.rl_personal_data_hand:
                //showDialog(DIALOG_USERICON);
                getPopupWindow();
                break;
            case R.id.rl_personal_data_city:
                Intent i = new Intent(this, AddressActivity.class);
                startActivityForResult(i, 1);
                break;
            case R.id.btn_personal_data_save:

                try {
                    startWhiteLoadingAnim();
                    updateModifyInformation();
                } catch (IOException e) {
                    e.printStackTrace();
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

    private void updateModifyInformation() throws IOException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "updateModifyInformation");
            obj.put("userid", userId);
            if (mPhotoPath != null) {
                obj.put("headicon", ImageUtil.bitMapToString(ImageUtil.revitionImageSize(mPhotoPath)));
            } else {
                obj.put("headicon", "");
            }
            obj.put("nikeName", et_personal_data_name.getText().toString().trim());
            if (gender == null) {
                obj.put("usersex", userSex);
            } else {
                obj.put("usersex", gender);
            }

            if (cityid == null) {
                obj.put("userCity", personalData.getUserCity());
            } else {
                obj.put("userCity", cityid);
            }

            if (provinceId == null) {
                obj.put("userProvince", personalData.getUserProvince());
            } else {
                obj.put("userProvince", provinceId);
            }
            if (areaId == null) {
                obj.put("useraddress", personalData.getUseraddress());
            } else {
                obj.put("useraddress", areaId);
            }
            obj.put("userPosition", et_personal_data_position.getText().toString().trim());
            obj.put("companyBrand", et_personal_data_company.getText().toString().trim());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("PersonalDataActivity", "updateModifyInformation  obj== " + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Log.e("PersonalDataActivity", "updateModifyInformation  bytes==" + new String(bytes));
                Gson gson = new Gson();
                UpdateInfoBean updateInfoBean = gson.fromJson(new String(bytes), new TypeToken<UpdateInfoBean>() {
                }.getType());
                String result = updateInfoBean.getResult();
                resultNote = updateInfoBean.getResultNote();
                if ("0".equals(result)) {
                    updateModifyData = updateInfoBean.getUpdateModifyBean();
                    mHandler.obtainMessage(UPDATE_SUCCESS).sendToTarget();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(PersonalDataActivity.this, getResources().getString(R.string.service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                ToastUtil.showMessageDefault(PersonalDataActivity.this, getResources().getString(R.string.service_error_hint));
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
        popupWindow.showAtLocation(btn_personal_data_save, Gravity.BOTTOM, 0, 0);
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
//        Log.e("requestCode", "requestCode==" + requestCode);
//        Log.e("resultCode", "resultCode==" + resultCode);
        if (requestCode == TYPE_CAMERA) {
            if (resultCode == RESULT_OK) {
                //将拍照的照片添加到图库,这样可以在手机的图库程序中看到程序拍摄的照片
                ImageUtil.galleryAddPic(this, mPhotoPath);
                //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Bitmap bitmap = ImageUtil.loadBitmap(mPhotoPath, 800, 480);
                id_updateuserinfo_userinfo_usericon.setImageBitmap(bitmap);
            } else {
                //取消照相后，删除已经创建的临时文件
                ImageUtil.deleteImage();
            }
        } else if (requestCode == TYPE_IMAGE) {
            if (resultCode == RESULT_OK) {
                mPhotoPath = ImageUtil.getPhotoPathByLocalUri(this, data);
                Bitmap bitmap = ImageUtil.loadBitmap(mPhotoPath, ICON_WIDTH_AND_HEIGHT,
                        ICON_WIDTH_AND_HEIGHT);
                id_updateuserinfo_userinfo_usericon.setImageBitmap(bitmap);
                //bitmap.recycle();
            } else {

            }
        } else if (requestCode == 1) {
            if (resultCode == 1) {
                result = data.getExtras().getString("result");
                provinceId = String.valueOf(data.getExtras().get("provinceId"));
                cityid = String.valueOf(data.getExtras().get("cityid"));
                areaId = String.valueOf(data.getExtras().get("areaId"));
                tv_personal_data_city.setText(result);
            }


        }

    }


    /**
     * 软键盘点非编辑框隐藏
     */
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
