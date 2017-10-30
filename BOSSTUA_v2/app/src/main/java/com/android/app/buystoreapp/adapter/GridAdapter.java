package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.GuessLikeBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shangshuaibo on 2016/11/18 13:12
 */
public class GridAdapter extends BaseAdapter {
    private Context context;
    private List<GuessLikeBean.LikeProBean> homeData;

    public GridAdapter(Context context, List<GuessLikeBean.LikeProBean> homeData) {
        super();
        this.context = context;
        this.homeData = homeData;
    }

    @Override
    public int getCount() {
        return (homeData == null) ? 0 : homeData.size();
    }

    @Override
    public Object getItem(int position) {
        return homeData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.goods_detail_gallery_item, null);
            holder = new ViewHolder();
            holder.mImg = (ImageView) convertView.findViewById(R.id.id_index_gallery_item_image);
            holder.mTxt = (TextView) convertView.findViewById(R.id.id_index_gallery_item_text);
            holder.mPrice = (TextView) convertView.findViewById(R.id.id_gallery_item_money);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTxt.setText(homeData.get(position).getProName());
        holder.mPrice.setText(String.format("￥%1$s/元",homeData.get(position).getPrice()));
        Picasso.with(context).load(homeData.get(position).getProImageUrl()).placeholder(R.drawable
                .ic_default).into(holder.mImg);
        return convertView;
    }

    class ViewHolder {
        ImageView mImg;
        TextView mTxt;
        TextView mPrice;
    }


}
