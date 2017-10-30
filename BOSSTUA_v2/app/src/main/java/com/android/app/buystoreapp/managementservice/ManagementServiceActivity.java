package com.android.app.buystoreapp.managementservice;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;

/**
 * 管理服务
 * <p/>
 * 魏林编写
 */
public class ManagementServiceActivity extends BaseAct implements View.OnClickListener {
    private View line_balance_detail_all;//左线
    private View line_balance_detail_income;//右线
    private TextView tv_management_service_up;//上架
    private TextView tv_management_service_down;//下架
    private ViewPager vp_tv_management_service;//viewPager


    private int current_index = 0;
    private static int linewidth = 0;
    private static int offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_service);
        initView();
        initLine();
        setListener();
    }

    private void initView() {
        line_balance_detail_all = findViewById(R.id.line_balance_detail_all);//左线
        line_balance_detail_income = findViewById(R.id.line_balance_detail_income);//右线
        tv_management_service_up = (TextView) findViewById(R.id.tv_management_service_up);//上架
        tv_management_service_down = (TextView) findViewById(R.id.tv_management_service_down);//下架
        vp_tv_management_service = (ViewPager) findViewById(R.id.vp_tv_management_service);//viewPager
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string.management_service));
    }

    private void setListener() {
        line_balance_detail_all.setOnClickListener(this);
        line_balance_detail_income.setOnClickListener(this);
        tv_management_service_up.setOnClickListener(this);
        tv_management_service_down.setOnClickListener(this);
         vp_tv_management_service.setOffscreenPageLimit(0);
        vp_tv_management_service.setAdapter(new FragmentPagerAdapter
                (getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new ShelvesUpFragment();
                    case 1:
                        return new ShelvesDownFragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }


        });

        vp_tv_management_service.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPager(position);
                switch (position) {
                    case 0:
                        anim(position);
                        tv_management_service_up.setTextColor(getResources().getColor(R.color
                                .bill_text_lv));
                        tv_management_service_down.setTextColor(getResources().getColor(R.color
                                .bill_text_hui));

                        break;
                    case 1:
                        anim(position);
                        tv_management_service_up.setTextColor(getResources().getColor(R.color
                                .bill_text_hui));
                        tv_management_service_down.setTextColor(getResources().getColor(R.color
                                .bill_text_lv));
                        //new ShelvesDownFragment().onResume();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManagementServiceActivity.this.finish();
            }
        });

    }


    /**
     * 初始化页签下划线
     */
    private void initLine() {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        line_balance_detail_all.measure(w, h);
        linewidth = line_balance_detail_all.getMeasuredWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //获取屏幕宽度
        int screenWidth = dm.widthPixels;
        Matrix matrix = new Matrix();
        offset = ((screenWidth / 2 - linewidth) / 2);
        matrix.postTranslate(offset, 0);
    }

    /**
     * 页签下划线移动动画
     *
     * @param index
     */
    private void anim(int index) {
        int one = offset * 2 + linewidth;
        Animation animation = new TranslateAnimation(one * current_index, one * index, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(200);
        line_balance_detail_all.startAnimation(animation);
        current_index = index;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_management_service_up:
                vp_tv_management_service.setCurrentItem(0);
                break;
            case R.id.tv_management_service_down:
                vp_tv_management_service.setCurrentItem(1);
                break;
        }
    }

    /**
     * 别的界面通过下标选择跳转Fragment
     *
     * @param index Fragment的下标
     */
    public void setPager(int index) {

        vp_tv_management_service.setCurrentItem(index);
    }
}
