package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.ShopCategoryBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopCategoryAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ShopCategoryBean> mDataList;
    private Context mContext;

    public ShopCategoryAdapter(Context context, List<ShopCategoryBean> data) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDataList = data;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mDataList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.shop_store_item, null);
            holder = new ViewHolder();
            holder.categoryName = (TextView) convertView
                    .findViewById(R.id.id_business_store_name);
            holder.categoryIcon = (ImageView) convertView
                    .findViewById(R.id.id_business_store_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String icon = mDataList.get(position).getCategoryIcon();
        if (!TextUtils.isEmpty(icon)) {
            Picasso.with(mContext).load(icon).placeholder(R.drawable.ic_default).error(R.drawable.ic_default).into(holder.categoryIcon);
        } else {
            Picasso.with(mContext).load(R.drawable.ic_default).into(holder.categoryIcon);
        }

        holder.categoryName.setText(mDataList.get(position).getCategoryName()
                .toString());
        return convertView;
    }

    class ViewHolder {
        ImageView categoryIcon;
        TextView categoryName;
    }
}
