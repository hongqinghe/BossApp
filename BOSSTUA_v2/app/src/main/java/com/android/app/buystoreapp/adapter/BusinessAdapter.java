package com.android.app.buystoreapp.adapter;

import java.util.List;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.ShopBean;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class BusinessAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ShopBean> mDataList;
    private Context context;
    private boolean mFavourited;

    public BusinessAdapter(Context context, List<ShopBean> data) {
        mInflater = LayoutInflater.from(context);
        mDataList = data;
        this.context = context;
    }
    
    public BusinessAdapter(Context context, List<ShopBean> data,boolean isFavourite) {
        mInflater = LayoutInflater.from(context);
        mDataList = data;
        this.context = context;
        mFavourited = isFavourite;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.business_item, parent,
                    false);
            holder.itemImage = (ImageView) convertView
                    .findViewById(R.id.id_business_item_image);
            holder.itemName = (TextView) convertView
                    .findViewById(R.id.id_business_item_name);
            holder.itemIntro = (TextView) convertView
                    .findViewById(R.id.id_business_item_intro);
            holder.itemRating = (RatingBar) convertView
                    .findViewById(R.id.id_business_item_rating_bar);
            holder.itemAddress = (TextView) convertView
                    .findViewById(R.id.id_business_item_address);
            holder.itemTalkNum = (TextView) convertView
                    .findViewById(R.id.id_business_item_talknum);
            holder.itemDistance = (TextView) convertView
                    .findViewById(R.id.id_business_item_distance);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // only set item content, do not set listener
        String icon = mDataList.get(position).getShopIcon();
        if (!TextUtils.isEmpty(icon)) {
            Picasso.with(context).load(icon).placeholder(R.drawable.ic_default).error(R.drawable.ic_default).into(holder.itemImage);
        } else {
            Picasso.with(context).load(R.drawable.ic_default).into(holder.itemImage);
        }

        holder.itemName.setText(mDataList.get(position).getShopName());
        holder.itemIntro.setText(mDataList.get(position).getShopIntro());

        String talkNum = mDataList.get(position).getShopTalkNum();
        String formatTalkNum = String.format(
                context.getResources()
                        .getString(R.string.business_item_talknum), TextUtils
                        .isEmpty(talkNum) ? "0" : talkNum);
        holder.itemTalkNum.setText(formatTalkNum);

        try {
            String rating = mDataList.get(position).getShopScore();
            float formatRating = Float.valueOf(TextUtils.isEmpty(rating) ? "0" : rating);
            holder.itemRating.setRating(formatRating);
        } catch (NumberFormatException e) {
            holder.itemRating.setRating(0f);
        }
        
        String address = mDataList.get(position).getShopAddress();
        holder.itemAddress.setText(address);
        
        if (mFavourited) holder.itemDistance.setVisibility(View.GONE);
        String distance = mDataList.get(position).getShopDistance();
        String distanceFormat = String.format(
                context.getResources().getString(
                        R.string.business_item_distance), TextUtils.isEmpty(distance) ? "0" : distance);
        holder.itemDistance.setText(distanceFormat);

        return convertView;
    }

    class ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemIntro;
        RatingBar itemRating;
        TextView itemTalkNum;
        TextView itemAddress;
        TextView itemDistance;
    }
}
