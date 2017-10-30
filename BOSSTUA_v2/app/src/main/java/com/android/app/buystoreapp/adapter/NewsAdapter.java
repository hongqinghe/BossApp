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
import com.android.app.buystoreapp.bean.NewsInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<NewsInfo> dataList;
    private Context mContext;

    public NewsAdapter(Context context, List<NewsInfo> data) {
        this.mContext = context;
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
            convertView = inflater.inflate(R.layout.news_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.id_news_item_image);
            holder.titleView = (TextView) convertView
                    .findViewById(R.id.id_news_item_title);
            holder.contentView = (TextView) convertView
                    .findViewById(R.id.id_news_item_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String newsImage = dataList.get(position).getNewsImage();
        if (!TextUtils.isEmpty(newsImage)) {
            Picasso.with(mContext).load(newsImage)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default).into(holder.imageView);
        } else {
            Picasso.with(mContext).load(R.drawable.ic_default)
                    .into(holder.imageView);
        }

        holder.titleView.setText(dataList.get(position).getNewsTitle());
        holder.contentView.setText(dataList.get(position).getNewsSubTitle());

        return convertView;
    }
    class ViewHolder {
        ImageView imageView;
        TextView titleView;
        TextView contentView;
    }

}
