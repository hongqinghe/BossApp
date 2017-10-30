package com.android.app.buystoreapp;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.setting.LoginActivity;
import com.android.app.buystoreapp.setting.RegisterActivity;
import com.android.app.buystoreapp.widget.RadarView;
import com.android.app.buystoreapp.widget.XiuView;
import com.android.app.buystoreapp.widget.XiuView.OnBtnPressListener;

/**
 * $desc
 * Created by likaihang on 16/11/23.
 */
public class SplashActivity extends BaseAct implements View.OnClickListener {
    private RadarView mRadarView;
    private XiuView mXiuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        initView();
    }

    private void initView() {
        mRadarView = (RadarView) findViewById(R.id.radar_view);
        mRadarView.setSearching(true);
        mRadarView.addPoint();
        mRadarView.addPoint();
        mRadarView.addPoint();
        mRadarView.addPoint();
        mRadarView.addPoint();
        mRadarView.addPoint();
        mRadarView.addPoint();
        mRadarView.addPoint();
        mRadarView.addPoint();
        mRadarView.addPoint();
        mRadarView.addPoint();
        mXiuView = (XiuView) findViewById(R.id.xiu_view);
        mXiuView.setOnBtnPressListener(new OnBtnPressListener() {

            @Override
            public void btnDown() {
                mRadarView.clearView();
            }

            @Override
            public void btnClick() {
                mRadarView.addPoint();
                mRadarView.addPoint();
                mRadarView.addPoint();
                mRadarView.addPoint();
                mRadarView.addPoint();
                mRadarView.addPoint();
                mRadarView.addPoint();
                mRadarView.addPoint();
                mRadarView.addPoint();
                mRadarView.addPoint();
                mRadarView.addPoint();
            }
        });
        TextView to = (TextView) findViewById(R.id.tv_splash_to);
        to.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        to.setOnClickListener(this);
        findViewById(R.id.bt_splash_login).setOnClickListener(this);
        findViewById(R.id.bt_splash_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_splash_to:
                startActivity(new Intent(this, BossBuyActivity.class));
                finish();
                break;
            case R.id.bt_splash_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.bt_splash_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
        }
    }
}
