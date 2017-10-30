package com.android.app.buystoreapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.android.app.buystore.utils.SharedPreferenceUtils;

/**
 * 应用启动分发页面
 */
public class WelcomeBossBuyActivity extends Activity implements OnClickListener {
    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome_bossbuy_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mHandler.postDelayed(mRunnable, 2000); // 在Handler中执行子线程并延迟2s。
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (SharedPreferenceUtils.getAppIsFirstLaunch(WelcomeBossBuyActivity.this)) {
                    startActivity(new Intent(WelcomeBossBuyActivity.this, SplashActivity.class));
                } else {
                    startActivity(new Intent(WelcomeBossBuyActivity.this, BossBuyActivity.class));
                }
                finish();
            }
        };
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    }
}
