package com.android.app.buystoreapp.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystoreapp.BaseWebActivity;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.GsonAppVersionBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.ResType;
import com.lidroid.xutils.view.annotation.ResInject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

public class AppVersionActivity extends Activity {
    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;
    
    @ViewInject(R.id.id_app_version_uptime)
    TextView upTime;
    @ViewInject(R.id.id_app_version_upmsg)
    TextView upMsg;
    @ViewInject(R.id.id_app_version_vcode)
    TextView upCode;
    @ViewInject(R.id.id_app_version_download)
    Button downButton;
    
    @ResInject(id= R.string.web_url,type=ResType.String)
    private String webUrl;
    private String mDownloadUrl;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.app_version_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_action_bar);
        ViewUtils.inject(this);
        mTitleText.setText("版本信息");
        
        requestAppVersion();
    }

    @OnClick(R.id.id_custom_back_image)
    public void onCustomBarBackClicked(View v) {
        switch (v.getId()) {
        case R.id.id_custom_back_image:
            this.finish();
            break;
        default:
            break;
        }
    }
    
    @OnClick(R.id.id_app_version_download)
    public void onVersionDownloadClicked(View v) {
        Intent intent = new Intent(this,
                BaseWebActivity.class);
        intent.putExtra("type", "download");
        intent.putExtra("url", mDownloadUrl);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    
    private void requestAppVersion() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        final Gson gson = new Gson();
        String param = "{\"cmd\":\"appVersion\",\"type\":\"1\"}";
        requestParams.put("json", param);

        client.post(webUrl, requestParams, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                try {
                    LogUtils.d("requestAppVersion result=" + new String(arg2));
                    GsonAppVersionBack gsonAppVersionBack = gson.fromJson(new String(arg2),
                            new TypeToken<GsonAppVersionBack>(){}.getType());
                    String result = gsonAppVersionBack.getResult();
                    String resultNote = gsonAppVersionBack.getResultNote();
                    if ("0".equals(result)) {
                        upMsg.setText(String.format(getString(R.string.about_us_publish_msg), gsonAppVersionBack.getUpmsg()));
                        upTime.setText(String.format(getString(R.string.about_us_publish_date), gsonAppVersionBack.getUptime()));
                        upCode.setText(String.format(getString(R.string.about_us_authority_info), gsonAppVersionBack.getVcode()));
                        mDownloadUrl = gsonAppVersionBack.getDownurl();
                        downButton.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(AppVersionActivity.this, resultNote, Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e) {
                    Log.e("mikes", "update user info error:", e);
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                    Throwable arg3) {
            }
        });
    }
}
