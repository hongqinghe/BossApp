package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.CommodityCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GoodsLeftAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<CommodityCategory> dataList;
    private Context mContext;

    public GoodsLeftAdapter(Context context, List<CommodityCategory> data) {
        mContext = context;
        this.dataList = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.goods_left_item, null);
            holder.categoryName = (TextView) convertView.findViewById(R.id.goods_left_item_name);
            holder.categoryIcon = (ImageView) convertView.findViewById(R.id.goods_left_item_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.categoryName.setText(dataList.get(position).getCategoryName().toString());
        String icon = dataList.get(position).getCategoryIcon();
        if (!TextUtils.isEmpty(icon)) {
            Picasso.with(mContext).load(icon).into(holder.categoryIcon);
        } else {
            Picasso.with(mContext).load(R.drawable.ic_default).into(holder.categoryIcon);
        }
        if (position == selectItem) {
            convertView.setBackgroundColor(Color.GRAY);
            holder.categoryName.setPressed(true);
            holder.categoryName.setSelected(true);
        } else {
            holder.categoryName.setPressed(false);
            holder.categoryName.setSelected(false);
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = -1;

    static class ViewHolder {
        TextView categoryName;
        ImageView categoryIcon;
    }
}
