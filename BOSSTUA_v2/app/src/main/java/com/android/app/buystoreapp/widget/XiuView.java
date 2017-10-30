package com.android.app.buystoreapp.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.app.buystoreapp.R;


public class XiuView extends FrameLayout {
	 
    /** 
     * 按钮 
     */  
    private ImageView iv_btn;  
  
    /** 
     * 循环外圈 
     */  
    private ImageView iv_out_circle;  
  
    /** 
     * 装波纹的容器 
     */  
    private FrameLayout fl_move_circle;  
  
    /** 
     * 按钮点击监听 
     */  
    private OnBtnPressListener onBtnPressListener;  
  
    public void setOnBtnPressListener(OnBtnPressListener onBtnPressListener) {  
        this.onBtnPressListener = onBtnPressListener;  
    }  
    
    private Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            iv_out_circle.setVisibility(VISIBLE);  
            startOutCircleAnim();  
        }  
    };  
  
    public XiuView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        initView();  
    }  
  
    /** 
     * 初始化控件个监听按钮 
     */  
    private void initView() {  
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_layout, null, false);
        addView(v, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);  
        iv_btn = (ImageView) findViewById(R.id.iv_btn);  
        iv_out_circle = (ImageView) findViewById(R.id.iv_out_circle);  
        fl_move_circle = (FrameLayout) findViewById(R.id.fl_move_circle);  
        iv_btn.setOnTouchListener(new OnTouchListener() {  
            @Override  
            public boolean onTouch(View v, MotionEvent event) {  
                switch (event.getAction()) {  
                    case MotionEvent.ACTION_DOWN://手指按钮  
                        //手指又按下，取消掉显示循环波纹的消息  
                        handler.removeMessages(1);  
                        pressDown();
                        if (onBtnPressListener != null) {  
                            onBtnPressListener.btnDown();  
                        }  
                        break;  
                    case MotionEvent.ACTION_UP://手指抬起  
                        //取消掉循环的波纹  
                        iv_out_circle.setVisibility(GONE);  
                        pressUp();  
                        addMoveCircle();  
                        //发送延时消息，3秒后继续显示循环波纹  
                        handler.sendEmptyMessageDelayed(1, 3000);  
                        if (onBtnPressListener != null) {  
                            onBtnPressListener.btnClick();  
                        }  
                        break;  
                }  
                return true;  
            }  
        });  
        startOutCircleAnim();  
    }  
  
    /** 
     * 发散波纹 
     */  
    private void addMoveCircle() {  
        final ImageView imageView = new ImageView(getContext());  
        LayoutParams lp = new LayoutParams(dip2px(getContext(), 100), dip2px(getContext(), 100));  
        lp.gravity = Gravity.CENTER;  
        imageView.setLayoutParams(lp);  
        imageView.setImageResource(R.drawable.outcircle);  
        fl_move_circle.addView(imageView);  
        ObjectAnimator outCircleAnimX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 5f);  
        ObjectAnimator outCircleAnimY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 5f);  
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(imageView, "alpha", 0.6f, 0);  
        outCircleAnimX.setDuration(1500);  
        outCircleAnimY.setDuration(1500);  
        alphaAnim.setDuration(1500);  
        AnimatorSet animatorSet = new AnimatorSet();  
        animatorSet.playTogether(outCircleAnimX, outCircleAnimY, alphaAnim);  
        animatorSet.start();  
        animatorSet.addListener(new Animator.AnimatorListener() {  
            @Override  
            public void onAnimationStart(Animator animation) {  
  
            }  
  
            @Override  
            public void onAnimationEnd(Animator animation) {  
                //移除掉刚才添加的波纹  
                fl_move_circle.removeView(imageView);  
            }  
  
            @Override  
            public void onAnimationCancel(Animator animation) {  
  
            }  
  
            @Override  
            public void onAnimationRepeat(Animator animation) {  
  
            }  
        });  
    }  
  
    /** 
     * 开始循环的放大缩小波纹 
     */  
    private void startOutCircleAnim() {  
        ObjectAnimator outCircleAlpha = ObjectAnimator.ofFloat(iv_out_circle, "alpha", 0.2f, 0.6f);  
        outCircleAlpha.setDuration(1000);  
  
        ObjectAnimator outCircleAnimX = ObjectAnimator.ofFloat(iv_out_circle, "scaleX", 1f, 1.18f, 1f);  
        ObjectAnimator outCircleAnimY = ObjectAnimator.ofFloat(iv_out_circle, "scaleY", 1f, 1.18f, 1f);  
        outCircleAnimX.setDuration(2000);  
        outCircleAnimY.setDuration(2000);  
        outCircleAnimX.setRepeatCount(ValueAnimator.INFINITE);  
        outCircleAnimY.setRepeatCount(ValueAnimator.INFINITE);  
        outCircleAnimX.setInterpolator(new LinearInterpolator());  
        outCircleAnimY.setInterpolator(new LinearInterpolator());  
        AnimatorSet animatorSet = new AnimatorSet();  
        animatorSet.playTogether(outCircleAnimX, outCircleAnimY, outCircleAlpha);  
        animatorSet.start();  
    }  
  
    /** 
     * 按下按钮 
     */  
    private void pressDown() {  
        AnimatorSet animatorSet = new AnimatorSet();  
        ObjectAnimator scaleXIn = ObjectAnimator.ofFloat(iv_btn, "scaleX", 1f, 0.94f);  
        scaleXIn.setDuration(400);  
        scaleXIn.setInterpolator(new LinearInterpolator());  
        ObjectAnimator scaleYIn = ObjectAnimator.ofFloat(iv_btn, "scaleY", 1f, 0.94f);  
        scaleYIn.setDuration(400);  
        scaleYIn.setInterpolator(new LinearInterpolator());  
        animatorSet.playTogether(scaleXIn, scaleYIn);  
        animatorSet.start();  
    }  
  
    /** 
     * 抬起按钮 
     */  
    private void pressUp() {  
        AnimatorSet animatorSet1 = new AnimatorSet();  
        ObjectAnimator scaleXOut = ObjectAnimator.ofFloat(iv_btn, "scaleX", 0.94f, 1.06f, 1f);  
        scaleXOut.setDuration(500);  
        scaleXOut.setInterpolator(new LinearInterpolator());  
        ObjectAnimator scaleYOut = ObjectAnimator.ofFloat(iv_btn, "scaleY", 0.94f, 1.06f, 1f);  
        scaleYOut.setDuration(500);  
        scaleYOut.setInterpolator(new LinearInterpolator());  
        animatorSet1.playTogether(scaleXOut, scaleYOut);  
        animatorSet1.start();  
    }  
  
    /** 
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 按钮点击监听 
     */  
    public interface OnBtnPressListener {  
        void btnClick();  
        void btnDown();  
    }  
    /** 
     * 模拟点击 
     */  
    public void onceClick(){  
        //取消掉循环的波纹  
        iv_out_circle.setVisibility(GONE);  
        pressDown();  
        pressUp();  
        addMoveCircle();  
    }  
}
