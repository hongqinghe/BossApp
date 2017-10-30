package com.android.app.buystoreapp.other;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.app.buystoreapp.R;

/**
 * Created by 尚帅波 on 2016/9/22.
 */
public class CustomAnimDialog extends Activity {

    public static Dialog dialog;
    public static void initDialog(Context context) {
        dialog = new Dialog(context, R.style.CustomAnimDialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_anim_dialog, null);
        ImageView imv01 = (ImageView) view.findViewById(R.id.imv01);
        ImageView imv02 = (ImageView) view.findViewById(R.id.imv02);
        LinearLayout ll01 = (LinearLayout) view.findViewById(R.id.ll01);
        LinearLayout ll02 = (LinearLayout) view.findViewById(R.id.ll02);

        ll01.setVisibility(View.VISIBLE);
        ll02.setVisibility(View.GONE);
        Animation dialogAnim = AnimationUtils.loadAnimation(context, R.anim.dialoganimotion);
        imv01.startAnimation(dialogAnim);


        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        WindowManager manager = ((Activity) context).getWindowManager();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }
}
