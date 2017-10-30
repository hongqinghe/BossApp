package com.android.app.buystoreapp.order;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 卖家已售订单
 * Created by 尚帅波 on 2016/12/17.
 */
public class SellerOrderActivity extends BaseAct implements View.OnClickListener {

    private int[] ids = {R.id.tv_total, R.id.tv_wait_pay, R.id.tv_wait_send, R.id
            .tv_wait_harvest, R.id.tv_wait_rated, R.id.tv_wait_refund};
    private String[] titleName;
    private Fragment[] fragments = {new TotalFragment(), new WaitPayFragment(), new
            WaitSendFragment(), new WaitHarvestFragment(), new WaitRatedFragment()
            , new RefundFragment()};

    private View forst_line;
    private ViewPager pager_order;

    private int current_index;//这是上一个界面穿过来的index
    private static int linewidth = 0;

    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_seller_order);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_action_bar);
        ViewUtils.inject(this);

        //得到上一个界面穿过来的index
        current_index = getIntent().getIntExtra("index", 0);
        mTitleText.setText("已售订单");
        initView();
        initLine();
        setPager(current_index);
    }

    private void initView() {
        titleName = getResources().getStringArray(R.array.seller_order_details);
        forst_line = findViewById(R.id.forst_line);
        pager_order = (ViewPager) findViewById(R.id.pager_order);

        for (int i = 0; i < ids.length; i++) {
            ((TextView) findViewById(ids[i])).setText(titleName[i]);
            findViewById(ids[i]).setOnClickListener(this);
        }
        pager_order.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                for (int i = 0; i < fragments.length; i++) {
                    if (position == i) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("userStatus", 1);
                        fragments[i].setArguments(bundle);
                        return fragments[i];
                    }
                }
                return null;
            }

            @Override
            public int getCount() {
                return ids.length;
            }
        });

        pager_order.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            /**
             * pager页数发生变化后执行
             * @param position 最后停留的位置
             */
            @Override
            public void onPageSelected(int position) {
                setPager(position);
                anim(position);
                for (int i = 0; i < ids.length; i++) {
                    if (position == i) {
                        ((TextView) findViewById(ids[i])).setTextColor(getResources().getColor(R
                                .color.bill_text_lv));
                    } else {

                        ((TextView) findViewById(ids[i])).setTextColor(getResources().getColor(R
                                .color.bill_text_hui));
                    }
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
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //获取屏幕宽度
        int screenWidth = dm.widthPixels;
        Matrix matrix = new Matrix();
        linewidth = screenWidth / ids.length;
        matrix.postTranslate(0, 0);
    }

    /**
     * 页签下划线移动动画
     *
     * @param index
     */
    private void anim(int index) {
        int one = linewidth;
        Animation animation = new TranslateAnimation(one * current_index, one * index, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(200);
        forst_line.startAnimation(animation);
        current_index = index;
    }

    @OnClick(R.id.id_custom_back_image)
    public void onCustomBarBackClicked(View v) {
        switch (v.getId()) {
            case R.id.id_custom_back_image:
                this.finish();
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < ids.length; i++) {
            if (v.getId() == ids[i]) {
                pager_order.setCurrentItem(i);
            }
        }
    }

    /**
     * 别的界面通过下标选择跳转Fragment
     *
     * @param index Fragment的下标
     */
    public void setPager(int index) {
        pager_order.setCurrentItem(index);
    }
}
