package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.CommoditySubCategory;
import com.android.app.buystoreapp.bean.CommoditySubCategory.SubCategory;

import java.util.List;

public class GoodsRightAdapter extends BaseAdapter {
    public interface GoodsRightItemListener {
        void onItemClick(String categoryID,String categoryName);
    }

    private LayoutInflater inflater;
    private Context mContext;
    private List<CommoditySubCategory> dataList;
    private GoodsRightItemListener mListener;

    public GoodsRightAdapter(Context context, List<CommoditySubCategory> data
            ,GoodsRightItemListener listener) {
        mContext = context;
        this.dataList = data;
        inflater = LayoutInflater.from(context);
        mListener = listener;
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
        Type_ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.goods_right_item, null);
            viewHolder = new Type_ViewHolder();
            viewHolder.goodsTypeName = (TextView) convertView
                    .findViewById(R.id.id_good_right_item_type);
            viewHolder.goodsGridView = (GridView) convertView
                    .findViewById(R.id.id_good_right_item_grid);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Type_ViewHolder) convertView.getTag();
        }
        viewHolder.goodsTypeName.setText(dataList.get(position).getCategoryName());
        
        ContentAdapter contentAdapter = new ContentAdapter(dataList.get(position).getCategoryList());
        viewHolder.goodsGridView.setAdapter(contentAdapter);

        return convertView;
    }

    static class Type_ViewHolder {
        TextView goodsTypeName;
        GridView goodsGridView;
    }

    class ContentAdapter extends BaseAdapter {
        private List<SubCategory> mCommoditySubCategories;
        
        public ContentAdapter(List<SubCategory> data) {
            mCommoditySubCategories = data;
        }

        @Override
        public int getCount() {
            return mCommoditySubCategories.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup arg2) {
            Content_ViewHolder holder = null;
            if (convertView == null) {
                holder = new Content_ViewHolder();
                convertView = inflater.inflate(
                        R.layout.goods_right_item_content, null);
                holder.goodsName = (TextView) convertView
                        .findViewById(R.id.id_good_right_tv);
//                holder.image = (ImageView) convertView
//                        .findViewById(R.id.id_goods_right_img);
                convertView.setTag(holder);
            } else {
                holder = (Content_ViewHolder) convertView.getTag();
            }

//            String icon = mCommoditySubCategories.get(position).getCategoryIcon();
//            if (!TextUtils.isEmpty(icon)) {
//                Picasso.with(mContext).load(icon)
//                        .placeholder(R.drawable.ic_default)
//                        .error(R.drawable.ic_default).into(holder.image);
//            } else {
//                Picasso.with(mContext).load(R.drawable.ic_default)
//                        .into(holder.image);
//            }
            holder.goodsName.setText(mCommoditySubCategories.get(position).getCategoryName());
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mListener.onItemClick(mCommoditySubCategories.get(position).getCategoryID(),mCommoditySubCategories.get(position).getCategoryName());
                }
            });

            return convertView;
        }
    }

    static class Content_ViewHolder {
//        ImageView image;
        TextView goodsName;
    }

}
