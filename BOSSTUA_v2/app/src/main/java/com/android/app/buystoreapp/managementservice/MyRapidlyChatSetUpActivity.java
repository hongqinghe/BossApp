package com.android.app.buystoreapp.managementservice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.CameraUtils;
import com.android.app.buystore.utils.ImageUtils;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 名片设置提交数据页面
 * <p/>
 * 魏林编写代码
 */
public class MyRapidlyChatSetUpActivity extends BaseAct implements View.OnClickListener {
    private Button btn_rapidly_next;//按钮下一步

    private EditText et_rapidly_phone;//得到手机号
    private RelativeLayout rl_edit;//为备注的edit设置焦点
    private EditText et_rapidly_remarks;
    private TextView tv_number;//备注的edit字数0/25
    private ScrollView sv_up_down;//srcollView的ID
    private ImageView iv_rapidly_positive;//名片正面
    private ImageView iv_rapidly_unpositive;//名片背面
    private EditText et_rapidly_tell;//座机
    private EditText et_rapidly_qq;//QQ
    private EditText et_rapidly_wechat;//微信
    private EditText et_rapidly_email;//邮箱
    private List<RapidlyBean.FastChatListBean> list = new ArrayList<RapidlyBean.FastChatListBean>();

    public static MyRapidlyChatSetUpActivity myRapidlyChatSetUpActivity;

    private int num = 25;


    public final static int SMALL_CAPTURE = 0;
    public final static int BIG_CAPTURE = 1;

    private Bitmap userPhoto;

    private int flag = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SubscribeActivity.HANDLE_LOADMORE:
                    if (list.size() != 0) {
                        inputData();
                    }
                    break;
            }
        }


        private void inputData() {
            et_rapidly_phone.setText(list.get(0).getPhone());
            et_rapidly_tell.setText(list.get(0).getTell());
            et_rapidly_qq.setText(list.get(0).getQq());
            et_rapidly_wechat.setText(list.get(0).getWeChat());
            et_rapidly_email.setText(list.get(0).getEmail());
            et_rapidly_remarks.setText(list.get(0).getNote());

            if (list.get(0).getImgList().get(0).getWebrootpath() != null) {
                Picasso.with(MyRapidlyChatSetUpActivity.this)
                        .load(list.get(0).getImgList().get(0).getWebrootpath())
                        .placeholder(R.drawable.ic_business_card)
                        .into(iv_rapidly_positive);
            }
            if (list.get(0).getImgList().get(1).getWebrootpath() != null) {
                Picasso.with(MyRapidlyChatSetUpActivity.this)
                        .load(list.get(0).getImgList().get(1).getWebrootpath())
                        .placeholder(R.drawable.ic_business_card)
                        .into(iv_rapidly_unpositive);
            }
        }


    };
    private String userId;
    private String imageFilePath;
    private String upload1;
    private String upload2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rapidly_chat_set_up);
        myRapidlyChatSetUpActivity = this;
        initView();
        setListener();
        initErrorPage();
        addIncludeLoading(true);
        startWhiteLoadingAnim();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        load();
    }

    @Override
    protected void load() {
        super.load();
        queryFastChat();
    }

    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string
                .rapidly_chat_set_up));

        findViewById(R.id.iv_back).setOnClickListener(this);

        btn_rapidly_next = (Button) findViewById(R.id.btn_rapidly_next);//按钮下一步
        et_rapidly_phone = (EditText) findViewById(R.id.et_rapidly_phone);//得到手机号
        rl_edit = (RelativeLayout) findViewById(R.id.rl_edit);//为备注的edit设置焦点
        et_rapidly_remarks = (EditText) findViewById(R.id.et_rapidly_remarks);//备注
        tv_number = (TextView) findViewById(R.id.tv_number);//备注的edit字数0/25
        sv_up_down = (ScrollView) findViewById(R.id.sv_up_down);//srcollView的ID
        iv_rapidly_positive = (ImageView) findViewById(R.id.iv_rapidly_positive);//名片正面
        iv_rapidly_unpositive = (ImageView) findViewById(R.id.iv_rapidly_unpositive);//名片背面
        et_rapidly_tell = (EditText) findViewById(R.id.et_rapidly_tell);//座机
        et_rapidly_qq = (EditText) findViewById(R.id.et_rapidly_qq);//QQ
        et_rapidly_wechat = (EditText) findViewById(R.id.et_rapidly_wechat);//微信
        et_rapidly_email = (EditText) findViewById(R.id.et_rapidly_email);//邮箱
    }


    private void setListener() {
        btn_rapidly_next.setOnClickListener(this);//按钮下一步
        rl_edit.setOnClickListener(this);//为备注的edit设置焦点

        iv_rapidly_positive.setOnClickListener(this);
        iv_rapidly_unpositive.setOnClickListener(this);
        /**
         * 给字数设置限制
         * */
        et_rapidly_remarks.addTextChangedListener(new TextWatcher() {
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
                    ToastUtil.showMessageDefault(MyRapidlyChatSetUpActivity.this, "只能输入25个字!!!");
                }
                int number = s.length();
                //TextView显示剩余字数
                tv_number.setText("" + number + "/25");
                selectionStart = et_rapidly_remarks.getSelectionStart();
                selectionEnd = et_rapidly_remarks.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    et_rapidly_remarks.setText(s);
                    et_rapidly_remarks.setSelection(tempSelection);//设置光标在最后
                }
            }
        });
    }


    /**
     * 点击监听器
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rapidly_next://按钮下一步
                putData();
                break;

            case R.id.rl_edit:
                sv_up_down.fullScroll(ScrollView.FOCUS_DOWN);
                et_rapidly_remarks.requestFocus();
                InputMethodManager imm = (InputMethodManager) et_rapidly_remarks.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                sv_up_down.fullScroll(ScrollView.FOCUS_DOWN);
                break;

            case R.id.iv_rapidly_positive:
                flag = 1;
                new PopupWindows(MyRapidlyChatSetUpActivity.this, iv_rapidly_positive, 1);
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(MyRapidlyChatSetUpActivity.this.getCurrentFocus
                                ().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                break;
            case R.id.iv_rapidly_unpositive:
                flag = 2;
                new PopupWindows(MyRapidlyChatSetUpActivity.this, iv_rapidly_unpositive, 2);
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(MyRapidlyChatSetUpActivity.this.getCurrentFocus
                                ().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                break;

            case R.id.et_rapidly_remarks:
                sv_up_down.fullScroll(ScrollView.FOCUS_DOWN);
                break;

            case R.id.iv_back:
                this.finish();
                break;

        }
    }

    private void putData() {
        if (et_rapidly_phone.getText().toString().trim().isEmpty()) {
            et_rapidly_phone.requestFocus();//edit获取焦点
            InputMethodManager imm = (InputMethodManager)
                    et_rapidly_phone.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            sv_up_down.setFocusable(true);
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent intentnext = new Intent(this, MyStateSettingActivity.class);
            intentnext.putExtra("phone", et_rapidly_phone.getText().toString().trim());
            intentnext.putExtra("tell", et_rapidly_tell.getText().toString().trim());
            intentnext.putExtra("qq", et_rapidly_qq.getText().toString().trim());
            intentnext.putExtra("we_chat", et_rapidly_wechat.getText().toString().trim());
            intentnext.putExtra("email", et_rapidly_email.getText().toString().trim());
            intentnext.putExtra("list", (Serializable) list);
            if (!TextUtils.isEmpty(upload1)) {
                intentnext.putExtra("positive", upload1);
                if (list.size() != 0) {
                    intentnext.putExtra("positiveId", list.get(0).getImgList().get(0).getId());
                } else {
                    intentnext.putExtra("positiveId", "");
                }
            } else {
                if (list.size() != 0) {
                    intentnext.putExtra("positive", "");
                    intentnext.putExtra("positiveId", list.get(0).getImgList().get(0).getId());
                } else {
                    intentnext.putExtra("positive", "");
                }
            }
            if (!TextUtils.isEmpty(upload2)) {
                intentnext.putExtra("unpositive", upload2);
                if (list.size() != 0) {
                    intentnext.putExtra("unpositiveId", list.get(0).getImgList().get(1).getId());
                } else {
                    intentnext.putExtra("unpositiveId", "");
                }
            } else {
                if (list.size() != 0) {
                    intentnext.putExtra("unpositive", "");
                    intentnext.putExtra("unpositiveId", list.get(0).getImgList().get(1).getId());
                } else {
                    intentnext.putExtra("unpositive", "");
                }
            }
            intentnext.putExtra("note", et_rapidly_remarks.getText().toString().trim());
            if (list.size() != 0) {
                intentnext.putExtra("flog", list.get(0).getStatus());
            } else {
                intentnext.putExtra("flog", 1);
            }
            Log.e("intentnext", "flog=====" + intentnext.getExtras().get("flog"));
            startActivity(intentnext);


        }
    }


    public class PopupWindows extends PopupWindow {


        public PopupWindows(Context mContext, View parent, final int index) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            setHeight(ViewGroup.LayoutParams.FILL_PARENT);
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
                    imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/filename.jpg";
                    File temp = new File(imageFilePath);
                    Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
                    it.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
                    startActivityForResult(it, SMALL_CAPTURE);
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CameraUtils.openPhotos(MyRapidlyChatSetUpActivity.this, BIG_CAPTURE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //正面
        /**
         * 1返回缩略图
         */
        if (resultCode == RESULT_OK && requestCode == SMALL_CAPTURE) {
            if (imageFilePath != null
                    && (imageFilePath.endsWith(".jpg") || imageFilePath.endsWith(".png")
                    || imageFilePath.endsWith(".PNG") || imageFilePath.endsWith(".JPG"))) {
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imageFilePath, option);
                option.inSampleSize = ImageUtils.calculateInSampleSize(option,
                        800, 480);
                option.inJustDecodeBounds = false;
                userPhoto = BitmapFactory.decodeFile(imageFilePath, option);
                if (flag == 1) {
                    iv_rapidly_positive.setImageBitmap(userPhoto);
                    upload1 = imageFilePath;
                } else {
                    iv_rapidly_unpositive.setImageBitmap(userPhoto);
                    upload2 = imageFilePath;
                }
            }
        } else if (requestCode == BIG_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            String path = CameraUtils.getPhotoPathByLocalUri(this, data);
            Log.d("mikes", "path=" + path);
            if (path != null
                    && (path.endsWith(".jpg") || path.endsWith(".png")
                    || path.endsWith(".PNG") || path.endsWith(".JPG"))) {
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, option);
                option.inSampleSize = ImageUtils.calculateInSampleSize(option,
                        800, 480);
                option.inJustDecodeBounds = false;
                userPhoto = BitmapFactory.decodeFile(path, option);
                if (flag == 1) {
                    iv_rapidly_positive.setImageBitmap(userPhoto);
                    upload1 = path;
                } else {
                    iv_rapidly_unpositive.setImageBitmap(userPhoto);
                    upload2 = path;
                }
            }
        }

    }


    /**
     * 退出后将图片释放
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userPhoto != null) {
            userPhoto.recycle();
            userPhoto = null;
        }
    }

    /**
     * 查询名片
     */
    private void queryFastChat() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "queryFastChat");
            obj.put("proid", userId);
            obj.put("status", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("查询名片提交数据 obj==", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Log.e("查询名片返回数据  bytes===", new String(bytes));
                try {
                    Gson gson = new Gson();

                    RapidlyBean rapidlyBean = gson.fromJson(new String(bytes)
                            , new TypeToken<RapidlyBean>() {
                            }.getType());
                    String result = rapidlyBean.getResult();

                    if ("0".equals(result)) {
                        if (rapidlyBean.getFastChatList() == null) {
                            list = new ArrayList<RapidlyBean.FastChatListBean>();
                        } else {
                            list.clear();
                            list.addAll(rapidlyBean.getFastChatList());
                        }

                        LogUtils.e("---MyRapidlyChatSetUpActivity---" +
                                " list" + list);
                        mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
                    }
                } catch (Exception e) {
                    LogUtils.e("---MyRapidlyChatSetUpActivity---" +
                            "e==" + e.toString());
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

