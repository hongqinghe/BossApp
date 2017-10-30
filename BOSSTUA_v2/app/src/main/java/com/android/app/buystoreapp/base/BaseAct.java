/**
 * Activity基类
 */
package com.android.app.buystoreapp.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.utils.HttpUtils;
import com.android.app.utils.NetWorkUtil;
import com.android.app.utils.Util;
import com.umeng.message.PushAgent;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseAct extends FragmentActivity {

    @SuppressWarnings("rawtypes")
    private Class clazz = null;
    public static int mainThreadId;
    public static int titleheight = 0;

    // 由底部进入 提示框view
    private View tipView;
    private Animation inAnimation;
    private Animation outAnimation;
    private Map<Integer, View> map;// 存放提示框view
    private View currentView;
    private LinearLayout tooltipContent;
    private LinearLayout backContent;
    protected ListView listView;

    protected Activity mContext;

    // 首次点击返回按键时间
    private long firstTime = 0;
    private TextView service_error_hint;

    private ImageView service_error_image;
    /**
     * 这个用来给error页面提供数据 主要在页面没有分页的情况下，传入这值
     */
    protected int SERVEICE_ERR_FLAG = 1;
    private String loadingTag;
    public boolean isStopScroll = true;
    private LinearLayout ll_loading;
    private View service_error;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        CrashApplication.allActivity.add(this);

        mContext = this;
        map = new HashMap<Integer, View>();

        mainThreadId = android.os.Process.myTid();

        PushAgent.getInstance(mContext).onAppStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CrashApplication.allActivity.remove(this);
        HttpUtils.client.cancelRequests(this,true);
    }
/*
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			clazz = this.getClass();
			if (clazz.equals(BossBuyActivity.class)) {
				long secondTime = System.currentTimeMillis();
				if (secondTime - firstTime > 2000) {
					// 如果两次按键时间间隔大于2秒，则不退出
					Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
					firstTime = secondTime;// 更新firstTime
					return true;
				} else {
					// 两次按键小于2秒时，退出应用
					for (BaseAct baseAct : CrashApplication.allActivity) {
						baseAct.finish();
					}
					System.exit(0);
				}
			} else if (clazz.equals(Person.OrderBy.class)) {
				// OrderSuccessAct页面，使回退键失效
				return false;
			} 
		}

		return super.onKeyDown(keyCode, event);
	}*/

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void addAlert(View v) {

        if (inAnimation == null) {
            inAnimation = AnimationUtils.loadAnimation(this, R.anim.window_in);
            outAnimation = AnimationUtils
                    .loadAnimation(this, R.anim.window_out);
        }

        if (tipView == null) {
            tipView = View.inflate(this, R.layout.tooltip, null);
            ViewGroup contentView = (ViewGroup) this.getWindow().getDecorView()
                    .findViewById(android.R.id.content);
            contentView.addView(tipView);

        }

        if (tooltipContent == null) {
            tooltipContent = (LinearLayout) this
                    .findViewById(R.id.ll_tooltip_content);
        }
        if (backContent == null) {
            backContent = (LinearLayout) this
                    .findViewById(R.id.ll_back_content);
            backContent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideAlert();
                }
            });
        }

        tooltipContent.addView(v);
        v.setVisibility(View.GONE);
        map.put(v.getId(), v);

    }

    private View loading;

    private ImageView ivIncludeLoading;

    private AnimationDrawable animDraw;

    /**
     * @param isNeedToMinusTitleHeight isNeedToMinusTitleHeight 是否要减去Title的高度，默认减去Title高度为45dp
     */
    public void addIncludeLoading(boolean isNeedToMinusTitleHeight) {
        initLoading(isNeedToMinusTitleHeight, 45);
    }

    /**
     * @param titleHeightOfDip titleHeightOfDip：要减去的Title的高度
     */
    public void addIncludeLoading(int titleHeightOfDip) {
        initLoading(true, titleHeightOfDip);
    }

    private void initLoading(boolean isNeedToMinusTitleHeight,
                             int titleHeightOfDip) {
        if (loading == null) {
            ViewGroup contentView = (ViewGroup) this.getWindow().getDecorView()
                    .findViewById(android.R.id.content);
            int h = -1;
            if (isNeedToMinusTitleHeight) {
                h = Util.getWindowHeight(this)
                        - Util.dip2px(this, titleHeightOfDip)
                        - Util.getStatusHeight(this);
            } else {
                h = Util.getWindowHeight(this)
                        - Util.getStatusHeight(this);
            }
            LayoutParams params = new LayoutParams(-1,
                    h);
            params.gravity = Gravity.BOTTOM;
            loading = LayoutInflater.from(this).inflate(
                    R.layout.include_loading, contentView, false);
            loading.setLayoutParams(params);
            contentView.addView(loading);
        }
        // 主布局,控制颜色变化
        ivIncludeLoading = (ImageView) loading
                .findViewById(R.id.iv_include_loading);
        ll_loading = (LinearLayout) loading.findViewById(R.id.ll_loading);
        loading.setClickable(true);

        ivIncludeLoading.setBackgroundResource(R.drawable.anim_loading_64);
        animDraw = (AnimationDrawable) ivIncludeLoading.getBackground();

        stopLoadingAnim();
    }

    /**
     * 测量view的高度的工具
     *
     * @param view
     * @return
     */
    public int getViewHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        int measuredHeight = view.getMeasuredHeight();
        return measuredHeight;

    }

    /**
     * 用来控制loading样式的高度
     *
     * @param height 这个是输入dp值
     */
    public void setLoadingHeight_dp(int height) {
        int layout_height = Util.getWindowHeight(this)
                - Util.dip2px(this, height)
                - Util.getStatusHeight(this);
        LayoutParams layoutParams = (LayoutParams) loading
                .getLayoutParams();
        layoutParams.height = layout_height;
        loading.setLayoutParams(layoutParams);

    }

    /**
     * 用来控制loading样式的高度
     *
     * @param height 这个是输入px值
     */
    public void setLoadingHeight_px(int height) {
        int layout_height = Util.getWindowHeight(this) - height
                - Util.getStatusHeight(this);
        LayoutParams layoutParams = (LayoutParams) loading
                .getLayoutParams();
        layoutParams.height = layout_height;
        loading.setLayoutParams(layoutParams);
    }

    public void startLoadingAnim(String tag) {
        this.loadingTag = tag;
        startLoadingAnim();
    }

    /**
     * 开始加载动画
     */
    public void startWhiteLoadingAnim(String tag) {
        this.loadingTag = tag;
        startWhiteLoadingAnim();
    }

    public void stopLoadingAnim(String tag) {
        if (TextUtils.isEmpty(loadingTag) || TextUtils.equals(loadingTag, tag)) {
            stopLoadingAnim();
        }
    }

    /**
     * 开始加载动画
     * 透明遮罩
     */
    public void startWhiteLoadingAnim() {
        ll_loading.setBackgroundColor(Color.parseColor("#20ffffff"));
        if (null == loading) {
            return;
        }
        loading.setVisibility(View.VISIBLE);
        if (animDraw != null) {
            animDraw.start();
        }
    }

    /**
     * 开始加载动画
     * 纯白遮罩
     */
    public void startLoadingAnim() {
        ll_loading.setBackgroundColor(Color.parseColor("#ffffff"));
        if (null == loading) {
            return;
        }
        loading.setVisibility(View.VISIBLE);
        if (animDraw != null) {
            animDraw.start();
        }
    }

    /**
     * 停止加载动画
     */
    public void stopLoadingAnim() {
        if (animDraw != null) {
            animDraw.stop();
        }
        if (loading != null) {
            loading.setVisibility(View.GONE);
        }
    }

    /***
     * view.getId()
     */
    protected void hideAlert() {

        if (tipView != null && currentView != null) {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    tipView.setVisibility(View.GONE);
                }

            }, 300);
            currentView.setVisibility(View.GONE);
            currentView.startAnimation(outAnimation);
        }
    }

    /***
     * @param id view.getId()
     */
    protected void showAlert(int id) {
        currentView = map.get(id);
        if (tipView != null && currentView != null) {
            tipView.setVisibility(View.VISIBLE);
            tipView.setClickable(true);
            currentView.setVisibility(View.VISIBLE);
            currentView.startAnimation(inAnimation);
        }
    }


    /**
     * 有加载事件
     */
    protected void load() {
    }


    /**
     * 加载更多
     */
    protected boolean loadMore() {
        if (!NetWorkUtil.isNetworkConnected(CrashApplication.contex)) {
            Toast.makeText(CrashApplication.contex, "网络不给力", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }


    /**
     * 给子页面添加网络异常，和服务器忙的错误提示页面 注意：这个方法一定要在addIncludeLoading（）方法前调用
     */
    public void initErrorPage() {

        service_error = View.inflate(this, R.layout.service_error, null);
        // 获取根视图
        ViewGroup contentView = (ViewGroup) this.getWindow().getDecorView()
                .findViewById(android.R.id.content);
        // 获取异常页面的高度
        int height = Util.getWindowHeight(this)
                - Util.dip2px(this, 45)
                - Util.getStatusHeight(this);
        LayoutParams layoutParams = new LayoutParams(-1, height);
        layoutParams.gravity = Gravity.BOTTOM;
        service_error.setLayoutParams(layoutParams);
        contentView.addView(service_error);
        service_error_hint = (TextView) service_error
                .findViewById(R.id.service_error_hint);
        service_error_image = (ImageView) service_error
                .findViewById(R.id.service_error_image);
        Button service_error_btn = (Button) service_error
                .findViewById(R.id.service_error_btn);
        service_error_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                errorLoading();
            }
        });
        service_error.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                errorLoading();
            }
        });

        hideServiceErrorView();

    }

    /**
     * 显示服务异常页面
     */
    private void showServiceErrorView() {
        if (service_error != null) {
            service_error_hint.setText(getResources().getString(
                    R.string.service_error_hint));
            service_error_image.setImageResource(R.drawable.service_error);
            service_error.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏服务异常页面
     */
    private void hideServiceErrorView() {
        if (service_error != null) {
            service_error.setVisibility(View.GONE);
        }
    }

    /**
     * 屏幕被点击或者按钮被点击
     */
    private void errorLoading() {
        if (!NetWorkUtil.isNetworkConnected(CrashApplication.contex)) {
            Toast.makeText(CrashApplication.contex, "网络不给力", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        startLoadingAnim();
        load();
    }

    /**
     * 显示服务繁忙的页面
     */
    private void showServiceBusyView() {
        if (service_error != null) {
            service_error_hint.setText(getResources().getString(
                    R.string.service_error_hint));
            service_error_image.setImageResource(R.drawable.service_error);
            service_error.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置应该显示哪个状态的页面
     *
     */
    // protected void setErrorPageState(int pageIndex, MessageBean bean) {
    // if (null == bean)
    // return;
    // /**
    // * 网络请求失败
    // */
    // if (pageIndex == 1 && bean.getState().equals(CommonConfig.MSG_ERROR)) {
    // if (!NetWorkUtil.isNetworkConnected(CrashApplication.contex)) {
    // showServiceErrorView();
    // } else {
    // showServiceBusyView();
    // }
    // /**
    // * 请求成功
    // */
    // } else {
    // hideServiceErrorView();
    // }
    //
    // }

    /**
     * 显示错误提示页面
     *
     * @param pageIndex
     */
    protected void showErrorPageState(int pageIndex) {
        stopLoadingAnim();
        if (pageIndex == 1) {
            if (!NetWorkUtil.isNetworkConnected(CrashApplication.contex)) {
                showServiceErrorView();
            } else {
                showServiceBusyView();
            }
        }
    }

    /**
     * 页面为空
     *
     * @param tip 空页面文案提示
     *            creat at @time 16/10/14 9:59
     */
    protected void showEmpty(String tip) {
        stopLoadingAnim();
        if (service_error != null) {
            service_error_hint.setText(tip);
            service_error_image.setImageResource(R.drawable.content_empty);
            service_error.setVisibility(View.VISIBLE);
        }
    }

    protected void hideErrorPageState() {
        hideServiceErrorView();
    }

}
