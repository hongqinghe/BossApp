package com.android.app.buystoreapp.order;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.android.app.buystoreapp.R;

/**
 * $desc
 * Created by likaihang on 16/11/11.
 */
public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        String url = getIntent().getStringExtra("url");
        WebView webView = (WebView) findViewById(R.id.webView);
       /* webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setBlockNetworkImage(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应手机屏幕
        webView.getSettings().setLoadWithOverviewMode(true);*/
        webView.loadUrl(url);
    }
}
