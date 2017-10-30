package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.GroupGoods;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/10/27.
 */
public class ConfirmOrderItemAdapter extends BaseAdapter {
    private Context context;
    private List<GroupGoods> list;

    public ConfirmOrderItemAdapter(Context context, List<GroupGoods> itemlist) {
        this.context = context;
        this.list = itemlist;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.order_list_child_item, null);
            holder = new ViewHolder();
            holder.iv_orderImg = (ImageView) convertView.findViewById(R.id.iv_orderImg);
            holder.tv_orderName = (TextView) convertView.findViewById(R.id.tv_orderName);
            holder.tv_groupName = (TextView) convertView.findViewById(R.id.tv_groupName);
            holder.tv_serviceTime = (TextView) convertView.findViewById(R.id.tv_serviceTime);
            holder.tv_orderPrice = (TextView) convertView.findViewById(R.id.tv_orderPrice);
            holder.tv_orderNum = (TextView) convertView.findViewById(R.id.tv_orderNum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GroupGoods product = list.get(position);
        if (!TextUtils.isEmpty(product.getProImageMin())) {
            Picasso.with(context).load(product.getProImageMin())
                    .resize(100,100)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default).into(holder.iv_orderImg);
        } else {
            Picasso.with(context).load(R.drawable.ic_default).into(holder.iv_orderImg);
        }
        holder.tv_orderName.setText(product.getProName());
        holder.tv_groupName.setText(product.getMoreGroName());
        holder.tv_orderNum.setText(product.getCount() + "");
        holder.tv_orderPrice.setText(product.getMoreGroPrice());
        holder.tv_serviceTime.setText(product.getWeekStart() + "至" + product.getWeekEnd() + "(" +
                product.getDayTimeStart() + "-" + product.getDayTimeEnd() + ")");
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_orderImg;
        private TextView tv_orderName, tv_groupName, tv_serviceTime, tv_orderPrice,
                tv_orderNum;
    }
}
