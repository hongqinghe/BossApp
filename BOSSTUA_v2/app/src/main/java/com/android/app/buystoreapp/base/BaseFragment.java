/** 
 * Copyright (C) 2014 The Hjk365 mobile client Project
 * All right reserved.
 *
 * @author: shanhouwang@huimai365.com
 * @date: 2014-12-30
 * @Description: 基础碎片
 */
package com.android.app.buystoreapp.base;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.crash.CrashApplication;
import com.android.app.utils.Config;
import com.android.app.utils.NetWorkUtil;
import com.android.app.utils.Util;

public abstract class BaseFragment extends Fragment {

	private static final String TAG = "BaseFragment";

	private boolean superOnCreateViewCalled;


	protected Activity mContext;
	protected BaseAdapter adapter;

	public BaseFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (!superOnCreateViewCalled)
			throw new IllegalStateException(
					"每个子类必须调用超类的onCreateView方法,来获取mFloatWindow对象");
		initErrorPage();
		addIncludeLoading();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		superOnCreateViewCalled = true;
		return super.onCreateView(inflater, container, savedInstanceState);
	}


	protected abstract void load();

	protected boolean loadMore(){
		if (!NetWorkUtil.isNetworkConnected(CrashApplication.contex)) {
			Toast.makeText(CrashApplication.contex, "网络不给力", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	private View loading;

	private ImageView ivIncludeLoading;

	private AnimationDrawable animDraw;

	private View service_error;

	private TextView service_error_hint;

	private ImageView service_error_image;

	private LinearLayout ll_loading;

	/**
	 * @param isNeedToMinusTitleHeight
	 * 
	 *            isNeedToMinusTitleHeight 是否要减去Title的高度，默认减去Title高度为45dp
	 */
	public void addIncludeLoading(boolean isNeedToMinusTitleHeight) {
		initLoading(isNeedToMinusTitleHeight, 45);
	}
	public void addIncludeLoading() {
		initLoading();
	}

	/**
	 * @param titleHeightOfDip
	 * 
	 *            titleHeightOfDip：要减去的Title的高度
	 */
	public void addIncludeLoading(int titleHeightOfDip) {
		initLoading(true, titleHeightOfDip);
	}

	private void initLoading(boolean isNeedToMinusTitleHeight,
			int titleHeightOfDip) {
		if (loading == null) {
			ViewGroup contentView = (ViewGroup) getActivity().getWindow()
					.getDecorView().findViewById(android.R.id.content);
			int h = -1;
			if (isNeedToMinusTitleHeight) {
				h = Util.getWindowHeight(getActivity())
						- Util.dip2px(getActivity(), titleHeightOfDip)
						- Util.getStatusHeight(getActivity());
			} else {
				h = Util.getWindowHeight(getActivity())
						- Util.getStatusHeight(getActivity());
			}
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1,
					h);
			params.gravity = Gravity.BOTTOM;
			loading = LayoutInflater.from(getActivity()).inflate(
					R.layout.include_loading, contentView, false);
			loading.setLayoutParams(params);
			contentView.addView(loading);
		}
		ivIncludeLoading = (ImageView) loading
				.findViewById(R.id.iv_include_loading);
		loading.setClickable(true);
		ivIncludeLoading.setBackgroundResource(R.drawable.anim_loading_64);
		animDraw = (AnimationDrawable) ivIncludeLoading.getBackground();
		stopLoadingAnim();
	}

	/**
	 * @author 新写的loading 用来应对不同loading样式
	 */
	private void initLoading() {
		if (loading == null) {
			FrameLayout contentView = (FrameLayout) getView();

			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1,
					-1);
			params.gravity = Gravity.BOTTOM;
			loading = LayoutInflater.from(getActivity()).inflate(
					R.layout.include_loading, contentView, false);
			loading.setLayoutParams(params);
			contentView.addView(loading);
		}
		ll_loading = (LinearLayout) loading.findViewById(R.id.ll_loading);
		ivIncludeLoading = (ImageView) loading
				.findViewById(R.id.iv_include_loading);
		loading.setClickable(true);
		ivIncludeLoading.setBackgroundResource(R.drawable.anim_loading_64);
		animDraw = (AnimationDrawable) ivIncludeLoading.getBackground();
		stopLoadingAnim();
	}

	/**
	 * 开始加载动画
	 */
	public void startWhiteLoadingAnim() {
		ll_loading.setBackgroundColor(Color.parseColor("#eeeeee"));
		/*
		 * 临时解决去掉引导页动画加载的效果（引导页动画加载时遮挡不全的问题），在无引导页时才有动画效果。
		 */
		boolean isGuided = SharedPreferenceUtils.getInstance().getBoolean(
				Config.GUIDE);
		int oldVersion = SharedPreferenceUtils.getInstance().getInt(
				Config.VERSION);
		int myVersion = Util.getVersionCode(mContext);
		if (myVersion <= oldVersion || isGuided) {
			loading.setVisibility(View.VISIBLE);
			if (animDraw != null) {
				animDraw.start();
			}
		}

		// loading.setVisibility(View.VISIBLE);
		// if (animDraw != null) {
		// animDraw.start();
		// }
	}

	/**
	 * 开始加载动画
	 */
	public void startLoadingAnim() {
		ll_loading.setBackgroundColor(Color.parseColor("#88ffffff"));
		/*
		 * 临时解决去掉引导页动画加载的效果（引导页动画加载时遮挡不全的问题），在无引导页时才有动画效果。
		 */
		boolean isGuided = SharedPreferenceUtils.getInstance().getBoolean(
				Config.GUIDE);
		int oldVersion = SharedPreferenceUtils.getInstance().getInt(
				Config.VERSION);
		int myVersion = Util.getVersionCode(mContext);
		if (myVersion <= oldVersion || isGuided) {
			loading.setVisibility(View.VISIBLE);
			if (animDraw != null) {
				animDraw.start();
			}
		}

		// loading.setVisibility(View.VISIBLE);
		// if (animDraw != null) {
		// animDraw.start();
		// }
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

	/**
	 * 网络异常时加载错误页面 有两种情况: 1.在没有网络的情况下调用showServiceErrorView()；
	 * 2.在有网络，服务器忙造成的网络请求失败的时候调用这个 注意:这个方法一定要写在addIncludeLoading（）方法之前调用
	 */
	public void initErrorPage() {
		if (service_error == null) {
			service_error = View.inflate(getActivity(), R.layout.service_error,
					null);
		}
		ViewGroup parent = (ViewGroup) service_error.getParent();
		if (parent != null) {
			parent.removeView(service_error);
		}
		((FrameLayout) getView()).addView(service_error);
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

	private void errorLoading() {
		if (!NetWorkUtil.isNetworkConnected(CrashApplication.contex)) {
			Toast.makeText(CrashApplication.contex, "网络不给力", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		startLoadingAnim();
		load();
	}

	private void showServiceErrorView() {
		if (service_error != null) {
			service_error_hint.setText(getResources().getString(
					R.string.service_error_hint));
			service_error_image.setImageResource(R.drawable.service_error);
			service_error.setVisibility(View.VISIBLE);
		}
	}

	private void hideServiceErrorView() {
		if (service_error != null) {
			service_error.setVisibility(View.GONE);
		}
	}

	private void showServiceBusyView() {
		if (service_error != null) {
			service_error_hint.setText(getResources().getString(
					R.string.service_error_hint));
			service_error_image.setImageResource(R.drawable.service_error);
			service_error.setVisibility(View.VISIBLE);
		}
	}

	// protected void setErrorPageState(int pageIndex, MessageBean bean) {
	// if (null == bean)
	// return;
	// /**
	// * 网络请求失败
	// */
	// if (pageIndex == 1 && bean.getState().equals(Config.MSG_ERROR)) {
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
	 * 页面为空
	 *
	 * @param tip 空页面文案提示
	 *
	 */
	protected void showEmpty(String tip) {
		stopLoadingAnim();
		if (service_error != null) {
			service_error_hint.setText(tip);
			service_error_image.setImageResource(R.drawable.content_empty);
			service_error.setVisibility(View.VISIBLE);
		}
	}
	/**
	 * 显示error页面
	 * 
	 * @param pageIndex
	 */
	protected void showErrorPageState(int pageIndex) {
		/**
		 * 网络请求失败
		 */
		if (pageIndex == 1) {
			if (!NetWorkUtil.isNetworkConnected(CrashApplication.contex)) {
				showServiceErrorView();
			} else {
				showServiceBusyView();
			}
		}

	}

	/**
	 * 隐藏error页面
	 * 
	 * @param 
	 */
	protected void hideErrorPageState() {
		hideServiceErrorView();
	}

}
