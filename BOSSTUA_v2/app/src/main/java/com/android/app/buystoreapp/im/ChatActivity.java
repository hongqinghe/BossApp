package com.android.app.buystoreapp.im;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.utils.EaseUserUtils;


/**
 * 聊天页面，需要fragment的使用
 */
public class ChatActivity extends EaseBaseActivity {
    public static ChatActivity activityInstance;
    private MyChatFragment chatFragment;
    String toChatUsername;
    private String userIcon;
    private String userName;
    private String id;
    private String myIcon;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        activityInstance = this;
        //我的信息
         id = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
         myIcon = SharedPreferenceUtils.getCurrentUserInfo(this).getUserIcon();
        //对方id
        toChatUsername = getIntent().getExtras().getString("userId");
        //对方头像
        userIcon = getIntent().getExtras().getString("userIcon");
//        userIcon = "http://192.168.1.122:8080/bossgroupimage/2016-10-25/userIcon/userIconMin/20161025154134hKt8.jpg ";
        //对方昵称
        userName = getIntent().getExtras().getString("userName");

        EaseUserUtils.ueserid = id;
        EaseUserUtils.usericon = myIcon;
        //可以直接new EaseChatFratFragment使用
        chatFragment = new MyChatFragment();

        //传入参数
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();

       /* EaseUI easeUI = EaseUI.getInstance();
        //需要EaseUI库显示用户头像和昵称设置此provider
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {

            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });*/
    }

    private EaseUser getUserInfo(String username) {
        EaseUser user = null;
        //设置自己的头像
        if (id.toLowerCase().equals(EMClient.getInstance().getCurrentUser())) {
            //我的id和头像
            user = new EaseUser(id);
            user.setAvatar(myIcon);
            return user;
        } else {
            //设置别人的头像
            if (user == null) {
                user = new EaseUser(toChatUsername);
                user.setNick(userName);
                user.setAvatar(userIcon);
            } else {
                if (TextUtils.isEmpty(user.getNick())) { // 如果名字为空，则显示环信号码
                    user.setNick(user.getUsername());
                }
            }
            return user;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideSoftKeyboard();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }

    public String getToChatUsername() {
        return toChatUsername;
    }
}
