package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.BuyTicketHistoryBean;

import java.util.List;

public class BuyTicketHistoryAdapter extends BaseAdapter{
	
	private List<BuyTicketHistoryBean> mlist;
	private Context mContext;
	public BuyTicketHistoryAdapter(List<BuyTicketHistoryBean> list,Context context){
		this.mlist = list;
		this.mContext = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = View.inflate(mContext,R.layout.item_buy_ticket_layout, null);
		BuyTicketHistoryBean bean = mlist.get(position);
		((TextView)convertView.findViewById(R.id.TicketCount)).setText(bean.getTicketCount()+"张");
		((TextView)convertView.findViewById(R.id.Price)).setText("-¥"+bean.getPayMoney());
		((TextView)convertView.findViewById(R.id.Datetime)).setText(bean.getPayDate());
		return convertView;
	}

}
