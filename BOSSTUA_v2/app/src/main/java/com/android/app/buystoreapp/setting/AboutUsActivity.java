package com.android.app.buystoreapp.setting;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AboutUsActivity extends Activity {
    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    @ViewInject(R.id.tv_boss_svrsion)
    private TextView tv_boss_svrsion;//版本
//    @ViewInject(R.id.rl_agreement)
//    private RelativeLayout rl_agreement;//软件许可使用协议
//    @ViewInject(R.id.rl_clause)
//    private RelativeLayout rl_clause;//使用条款和隐私协议

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.about_us_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_action_bar);
        ViewUtils.inject(this);
        mTitleText.setText("关于我们");
       initView();
    }

    private void initView() {
        tv_boss_svrsion.setText("Boss团Android"+getVersionCode(this)+"版本");

       /* rl_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutUsActivity.this, ExplainWebViewActivity.class);
                i.putExtra("flag",3000);
                startActivity(i);
            }
        });

        rl_clause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutUsActivity.this, ExplainWebViewActivity.class);
                i.putExtra("flag",4000);
                startActivity(i);
            }
        });*/
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
    /***
     * 获取版本号
     */

    public static String getVersionCode(Context context) {
        return getPackageInfo(context).versionName;
    }
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

}
