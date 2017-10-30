package com.android.app.buystoreapp.managementservice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.other.ImageUtil;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 急速聊选择状态页面
 * <p/>
 * weilin
 */
public class MyStateSettingActivity extends BaseAct implements View.OnClickListener {

    private ImageButton cb_state_setting_charge;//选择收费

    private ImageButton cb_state_setting_free;//选择免费

    private ImageButton cb_state_setting_close;//选择关闭

    private RelativeLayout rl_state_setting_true;//选择关闭

    private ImageButton iv_back;

    private AddOrUpFastBean addOrUpFastBean = new AddOrUpFastBean();


    private int flog = 1;

    public static final int HANDLE_LOADMORE = 100;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLE_LOADMORE:
                    ToastUtil.showMessageDefault(MyStateSettingActivity.this, "名片设置成功");
                    finish();
                    MyRapidlyChatSetUpActivity.myRapidlyChatSetUpActivity.finish();
                    break;
            }
        }
    };
    private String userId;
    private int vip;
    private String note;
    private String we_chat;
    private String tell;
    private String phone;
    private String qq;
    private String email;

    @Override
    protected void load() {
        super.load();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_state_setting);
        initView();
        setListener();
        vip = SharedPreferenceUtils.getCurrentUserInfo(this).getUserLevelMark();
        flog = getIntent().getExtras().getInt("flog");
        note = getIntent().getExtras().getString("note");
        we_chat = getIntent().getExtras().getString("we_chat");
        tell = getIntent().getExtras().getString("tell");
        phone = getIntent().getExtras().getString("phone");
        qq = getIntent().getExtras().getString("qq");
        email = getIntent().getExtras().getString("email");
        initErrorPage();
        addIncludeLoading(true);
        ischecked(flog);
        Log.e("Intentflog", "Intentflogflog==============" + getIntent().getExtras().get("flog"));


    }

    @Override
    protected void onResume() {
        super.onResume();

        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
    }

    public void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string.state_set));
        cb_state_setting_charge = (ImageButton) findViewById(R.id.cb_state_setting_charge);
        cb_state_setting_free = (ImageButton) findViewById(R.id.cb_state_setting_free);
        cb_state_setting_close = (ImageButton) findViewById(R.id.cb_state_setting_close);
        rl_state_setting_true = (RelativeLayout) findViewById(R.id.rl_state_setting_true);
        iv_back = (ImageButton) findViewById(R.id.iv_back);
    }

    public void setListener() {
        cb_state_setting_charge.setOnClickListener(this);
        cb_state_setting_free.setOnClickListener(this);
        cb_state_setting_close.setOnClickListener(this);
        rl_state_setting_true.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_state_setting_charge:
                cb_state_setting_charge.setImageResource(R.drawable.ic_checked);
                cb_state_setting_free.setImageResource(R.drawable.ic_uncheck);
                cb_state_setting_close.setImageResource(R.drawable.ic_uncheck);
                flog = 1;
                break;

            case R.id.cb_state_setting_free:
                if (vip > 1) {
                    cb_state_setting_charge.setImageResource(R.drawable.ic_uncheck);
                    cb_state_setting_free.setImageResource(R.drawable.ic_checked);
                    cb_state_setting_close.setImageResource(R.drawable.ic_uncheck);
                    flog = 2;
                } else {
                    ToastUtil.showMessageDefault(MyStateSettingActivity.this, "钻石以上会员可以选择此项");
                }
                break;

            case R.id.cb_state_setting_close:
                cb_state_setting_charge.setImageResource(R.drawable.ic_uncheck);
                cb_state_setting_free.setImageResource(R.drawable.ic_uncheck);
                cb_state_setting_close.setImageResource(R.drawable.ic_checked);
                flog = 3;
                break;

            case R.id.rl_state_setting_true:
                startWhiteLoadingAnim();
                addFastChat();
                break;

            case R.id.iv_back:
                this.finish();
                break;
        }
    }

    /**
     * 添加名片
     */
    private void addFastChat() {
        List<RapidlyBean.FastChatListBean> list = (List<RapidlyBean.FastChatListBean>) getIntent().getSerializableExtra("list");
        AddOrUpFastBean.ImagepathBean imgListBean1 = new AddOrUpFastBean.ImagepathBean();
        if (!TextUtils.isEmpty(getIntent().getExtras().getString("positive"))) {
            try {
                imgListBean1.setWebrootpath(ImageUtil.bitMapToString(ImageUtil.revitionImageSize(getIntent().getExtras().getString("positive"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (list.size() != 0) {
                imgListBean1.setId(list.get(0).getImgList().get(0).getId());
            } else {
                imgListBean1.setId("");
            }
        } else {
            if (list.size() != 0) {
                imgListBean1.setWebrootpath("");
                imgListBean1.setId(list.get(0).getImgList().get(0).getId());
            } else {
                imgListBean1.setWebrootpath("");
                imgListBean1.setId("");
            }
        }
        imgListBean1.setImageType("1");

        AddOrUpFastBean.ImagepathBean imgListBean2 = new AddOrUpFastBean.ImagepathBean();
        if (!TextUtils.isEmpty(getIntent().getExtras().getString("unpositive"))) {
            try {
                imgListBean2.setWebrootpath(ImageUtil.bitMapToString(ImageUtil.revitionImageSize(getIntent().getExtras().getString("unpositive"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (list.size() != 0) {
                imgListBean2.setId(list.get(0).getImgList().get(1).getId());
            } else {
                imgListBean2.setId("");
            }
        } else {
            if (list.size() != 0) {
                imgListBean2.setWebrootpath("");
                imgListBean2.setId(list.get(0).getImgList().get(1).getId());
            } else {
                imgListBean2.setWebrootpath("");
                imgListBean2.setId("");
            }
        }
        imgListBean2.setImageType("0");
        addOrUpFastBean.imagepath = new ArrayList<AddOrUpFastBean.ImagepathBean>();
        addOrUpFastBean.imagepath.add(imgListBean1);
        addOrUpFastBean.imagepath.add(imgListBean2);
        addOrUpFastBean = new AddOrUpFastBean(
                note, we_chat, tell, "addFastChat", phone, qq, email, flog, userId, addOrUpFastBean.imagepath);
        JSONObject obj = null;
        try {
            obj = new JSONObject(new Gson().toJson(addOrUpFastBean));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("obj========", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                try {
                    String str = new String(bytes);
                    JSONObject object = new JSONObject(str);
                    String result = (String) object.get("result");
                    if ("0".equals(result)) {
                        mHandler.obtainMessage(HANDLE_LOADMORE).sendToTarget();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(MyStateSettingActivity.this, getResources().getString(R.string.service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(MyStateSettingActivity.this, getResources().getString(R.string.service_error_hint));
            }
        });

    }


    public void ischecked(int flog) {
        switch (flog) {
            case 1:
                cb_state_setting_charge.setImageResource(R.drawable.ic_checked);
                cb_state_setting_free.setImageResource(R.drawable.ic_uncheck);
                cb_state_setting_close.setImageResource(R.drawable.ic_uncheck);
                break;
            case 2:
                cb_state_setting_charge.setImageResource(R.drawable.ic_uncheck);
                cb_state_setting_free.setImageResource(R.drawable.ic_checked);
                cb_state_setting_close.setImageResource(R.drawable.ic_uncheck);
                break;
            case 3:
                cb_state_setting_charge.setImageResource(R.drawable.ic_uncheck);
                cb_state_setting_free.setImageResource(R.drawable.ic_uncheck);
                cb_state_setting_close.setImageResource(R.drawable.ic_checked);
                break;
        }

    }


}
