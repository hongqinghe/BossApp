package com.android.app.bystoreapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.app.buystoreapp.R;

public class CallDialog extends Dialog implements android.view.View.OnClickListener {

    private onBtnClick click;
    private TextView tivketTip;
    private TextView but;

    public void setClickListener(onBtnClick click) {
        this.click = click;
    }

    public CallDialog(Context context) {
        super(context, R.style.TallkDialog);
        // TODO Auto-generated constructor stub
        Init();
    }

    private void Init() {
        setContentView(R.layout.call_dialog);
//		findViewById(R.id.OKBtn).setOnClickListener(this);
        tivketTip = (TextView) findViewById(R.id.tv_boss_ticket);
        but = (TextView) findViewById(R.id.tv_buy_stick);
        but.setOnClickListener(this);
        findViewById(R.id.iv_dialog_closs).setOnClickListener(this);
        setCanceledOnTouchOutside(false);
        // 设置window属性
        WindowManager.LayoutParams a = getWindow().getAttributes();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        a.dimAmount = 0.0f; // 添加背景遮盖
        getWindow().setAttributes(a);
    }


    public interface onBtnClick {
//        public void useBtn();

        void buyBtn();
    }

    public void setTicket(String myTicket, String butString) {
        String result = String.format(this.getContext().getResources().getString(R.string.diallog_ticktet_tip_have), myTicket);
        tivketTip.setText(result);
        but.setText(butString);
    }

    public void setChat(String butString) {
        String result = this.getContext().getResources().getString(R.string.diallog_ticktet_tip_chat);
        tivketTip.setText(result);
        but.setText(butString);
    }

    public void setDirect(String butString) {
        String result = this.getContext().getResources().getString(R.string.diallog_ticktet_tip_direct);
        tivketTip.setText(result);
        but.setText(butString);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_buy_stick) {
            click.buyBtn();
        } else if (v.getId() == R.id.iv_dialog_closs) {
            dismiss();
        }
        dismiss();
    }

}
