package com.android.app.bystoreapp.dialog;

import com.android.app.buystoreapp.R;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class TicketCountDialog extends Dialog implements android.view.View.OnClickListener{
	private OnOtherTicketChooseListener listener;
	private EditText countPrice;
	public TicketCountDialog(Context context) {
		super(context,R.style.Dialog);
		// TODO Auto-generated constructor stub
		Init();
		BindClick();
	}
	
	private void Init(){
		setContentView(R.layout.ticket_count_dialog);
		countPrice = (EditText)findViewById(R.id.CountEdit);
	}
	
	private void BindClick(){
		findViewById(R.id.cancelBtn).setOnClickListener(this);
		findViewById(R.id.OkBtn).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.cancelBtn:
			dismiss();
			break;
		case R.id.OkBtn:
			if(!TextUtils.isEmpty(countPrice.getText().toString())){
				String str = countPrice.getText().toString();
				//int p = Integer.parseInt(str);
				int p = 0;
				try{
					p = Integer.parseInt(str);
				}catch(Exception e){
					
				}
				if(p != 0 && listener != null){
					listener.otherPrice(p);
				}
				dismiss();
			}
			break;
		default:
			break;
		}
	}
	
	public void setOnOtherTicketChooseListener(OnOtherTicketChooseListener lis){
		this.listener = lis;
	}
	
	public interface OnOtherTicketChooseListener{
		void otherPrice(int price);
	}

}
