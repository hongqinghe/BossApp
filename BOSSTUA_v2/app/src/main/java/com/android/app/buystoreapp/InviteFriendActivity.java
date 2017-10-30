package com.android.app.buystoreapp;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.android.app.utils.UmengShareUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.umeng.socialize.media.UMImage;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 邀请朋友
 * Created by 尚帅波 on 2016/10/21.
 */
public class InviteFriendActivity extends BaseAct implements View.OnClickListener, View
        .OnLongClickListener {

    private int[] ids = {R.id.tv01, R.id.tv02, R.id.tv03, R.id.tv04, R.id.tv05, R.id.tv06};
    private LinearLayout ll_copy;
    private TextView tvShare, inviteCount;
    String str ;   //邀请码
    String userId;
    private int count;//邀请人数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        str = SharedPreferenceUtils.getCurrentUserInfo(mContext).getInviteCode();
        userId = SharedPreferenceUtils.getCurrentUserInfo(mContext).getUserId();
        initView();

        addIncludeLoading(true);
        load();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    inviteCount.setText(getResources().getString(R.string.inviteCount, count));
                    break;
            }
        }
    };

    private void initView() {
        for (int i = 0; i < str.length(); i++) {
            ((TextView) findViewById(ids[i])).setText(str.substring(i, i + 1));
        }

        inviteCount = (TextView) findViewById(R.id.inviteCount);

        tvShare = (TextView) findViewById(R.id.share);
        tvShare.setOnClickListener(this);

        ll_copy = (LinearLayout) findViewById(R.id.ll_copy);
        ll_copy.setOnLongClickListener(this);
        ((TextView)findViewById(R.id.tv_title)).setText(getResources().getString(R.string.invite_friends));
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InviteFriendActivity.this.finish();
            }
        });
    }

    @Override
    protected void load() {
        super.load();
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "inviteFriends");
            obj.put("thisUser", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    stopLoadingAnim();
                    JSONObject obj = new JSONObject(new String(bytes));
                    String result = obj.getString("result");
                    count = obj.getInt("invitedNum");
                    handler.sendEmptyMessage(10);
                    if (result.equals("0")) {

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

    @Override
    public void onClick(View v) {
        UMImage umimage = new UMImage(this, BitmapFactory.decodeResource(this
                .getResources(), R.drawable.ic_launcher));
        UmengShareUtils share = new UmengShareUtils(this, "邀请朋友", "http://bosstuan.cn", "Boss团",
                umimage);
        share.share();


    }

    @Override
    public boolean onLongClick(View v) {
        ClipboardManager cmb = (ClipboardManager) InviteFriendActivity.this.getSystemService
                (Context.CLIPBOARD_SERVICE);
        cmb.setText(str.trim());
        ToastUtil.showMessageDefault(InviteFriendActivity.this, "复制邀请码成功！");
        return false;
    }


}
