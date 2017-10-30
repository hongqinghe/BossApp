package com.android.app.buystoreapp.managementservice;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;

/**
 * 收藏
 */
public class CollectionActivity extends BaseAct implements View.OnClickListener {
    private TextView tv_title;
    private View line_balance_detail_all;//左线
    private View line_balance_detail_income;//右线
    private TextView tv_service_resources;//服务·资源
    private TextView tv_corporate_headlines;//企业头条
    private ViewPager vp_collection_pager;//viewPager

    private int current_index = 0;
    private static int linewidth = 0;
    private static int offset = 0;

    /**
     * 网络请求数据
     */


    private ImageButton iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_main);
        initView();
        initLine();
        setListener();
        PageSetAdapter();
        initErrorPage();
        addIncludeLoading(true);

    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("收藏");
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        line_balance_detail_income = findViewById(R.id.line_balance_detail_income);
        tv_service_resources = (TextView) findViewById(R.id.tv_service_resources);
        tv_corporate_headlines = (TextView) findViewById(R.id.tv_corporate_headlines);
        vp_collection_pager = (ViewPager) findViewById(R.id.vp_collection_pager);
        line_balance_detail_all = findViewById(R.id.line_balance_detail_all);

    }


    private void PageSetAdapter() {
        vp_collection_pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new ServiceResourcesFragment();
                    case 1:
                        return new CorporateHeadlinesFragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

    }

    private void setListener() {
        line_balance_detail_all.setOnClickListener(this);
        line_balance_detail_income.setOnClickListener(this);
        tv_service_resources.setOnClickListener(this);
        tv_corporate_headlines.setOnClickListener(this);
        vp_collection_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPager(position);
                switch (position) {
                    case 0:
                        anim(position);
                        tv_service_resources.setTextColor(getResources().getColor(R.color
                                .bill_text_lv));
                        tv_corporate_headlines.setTextColor(getResources().getColor(R.color
                                .bill_text_hui));

                        break;
                    case 1:
                        anim(position);
                        Log.e("lin", "p=" + position);
                        tv_service_resources.setTextColor(getResources().getColor(R.color
                                .bill_text_hui));
                        tv_corporate_headlines.setTextColor(getResources().getColor(R.color
                                .bill_text_lv));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
            case R.id.tv_service_resources:
                vp_collection_pager.setCurrentItem(0);
                break;
            case R.id.tv_corporate_headlines:
                vp_collection_pager.setCurrentItem(1);
                break;
        }
    }

    /**
     * 别的界面通过下标选择跳转Fragment
     *
     * @param index Fragment的下标
     */
    public void setPager(int index) {

        vp_collection_pager.setCurrentItem(index);
    }

}

