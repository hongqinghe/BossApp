package com.android.app.buystoreapp.goods;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.GroupBean;

import java.util.ArrayList;
import java.util.List;

public class GoodsDetailsXhAdapter extends BaseAdapter {

	private List<GroupBean> data;
	private Context context;


	public GoodsDetailsXhAdapter(Context context) {
		this.context = context;
		if (null == data) {
			data = new ArrayList<GroupBean>();
		}
	}

	public void setData(List<GroupBean> list) {
		this.data.clear();
		this.data = list;
		this.notifyDataSetChanged();
	}
	private int clickStatus = 0;

	//  定义一个公有方法，在activity中调用
	public void setSeclection(int position) {
		clickStatus = position;
	}
	public GroupBean getNormsBean(int index) {

		return data.get(index);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int item) {
		return item;
	}

	public long getItemId(int id) {
		return id;
	}

	// 创建View方法
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(context,
					R.layout.goods_details_xh_item, null);

			holder.textView = (TextView) convertView.findViewById(R.id.tv_xh);

			holder.llBorder = (LinearLayout) convertView
					.findViewById(R.id.ly_border);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		GroupBean bean = data.get(position);
		holder.textView.setText(bean.getMoreGroName());

			/*// 选中
			if ("0".equals(bean.getDefflag())) {
				holder.llBorder
						.setBackgroundResource(R.drawable.goods_detail_red_border);
			} else {
				holder.llBorder
						.setBackgroundResource(R.drawable.goods_detail_border);
			}*/
		if (clickStatus==position) {
			holder.llBorder.setBackgroundResource(R.drawable.goods_detail_group_selected);
			holder.textView.setTextColor(Color.WHITE);
		}else {
			holder.llBorder.setBackgroundResource(R.drawable.goods_detail_border);
			holder.textView.setTextColor(context.getResources().getColor(
					R.color.c_999999));
		}

		return convertView;
	}
	static class Holder {
		TextView textView;
		LinearLayout llBorder;
	}

}
