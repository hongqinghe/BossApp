package com.android.app.buystore.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.app.buystoreapp.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SmartImageView extends ImageView {

    private static final int LOADING_THREADS = 4;
    private static ExecutorService threadPool = Executors
            .newFixedThreadPool(LOADING_THREADS);

    private SmartImageTask currentTask;

    private Drawable mLoadingDrawable;
    private Drawable mFailDrawable;

    public SmartImageView(Context context) {
        this(context, null);
    }

    public SmartImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SmartImageView, defStyle, 0);
        mLoadingDrawable = a.getDrawable(R.styleable.SmartImageView_onLoading);
        mFailDrawable = a.getDrawable(R.styleable.SmartImageView_onFail);
        a.recycle();
    }

    public void setImageUrl(String url) {
        setImage(new WebImage(url), null);
    }

    public void setImageUrl(String url,
            SmartImageTask.OnCompleteListener completeListener) {
        setImage(new WebImage(url), completeListener);
    }

    public void setImage(final SmartImage image,
            final SmartImageTask.OnCompleteListener completeListener) {
        if (mLoadingDrawable != null) {
            setImageDrawable(mLoadingDrawable);
        }

        if (currentTask != null) {
            currentTask.cancel();
            currentTask = null;
        }

        currentTask = new SmartImageTask(getContext(), image);
        currentTask
                .setOnCompleteHandler(new SmartImageTask.OnCompleteHandler() {
                    @Override
                    public void onComplete(Bitmap bitmap) {
                        if (bitmap != null) {
                            setImageBitmap(bitmap);
                            if (completeListener != null) {
                                completeListener.onSuccess(bitmap);
                            }
                        } else {
                            if (mFailDrawable != null) {
                                setImageDrawable(mFailDrawable);
                            }
                            if (completeListener != null) {
                                completeListener.onFail();
                            }
                        }
                    }
                });

        threadPool.execute(currentTask);
    }

    public static void cancelAllTasks() {
        threadPool.shutdownNow();
        threadPool = Executors.newFixedThreadPool(LOADING_THREADS);
    }

}
