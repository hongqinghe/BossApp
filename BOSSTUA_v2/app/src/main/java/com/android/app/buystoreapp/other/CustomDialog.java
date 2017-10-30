package com.android.app.buystoreapp.other;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.app.buystoreapp.R;

/**
 * Created by 尚帅波 on 2016/9/22.
 */
public class CustomDialog extends Activity {

    public static TextView tvTitle, btnLeft, btnRight;
    public static Dialog dialog;

    public static void initDialog(Context context) {
        // if (dialog == null){
        //    dialog = new Dialog(context, R.style.CustomDialog);
        //}
        dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_dialog, null);

        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        dialog.show();

        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        btnLeft = (TextView) view.findViewById(R.id.btnLeft);
        btnRight = (TextView) view.findViewById(R.id.btnRight);
    }
}
