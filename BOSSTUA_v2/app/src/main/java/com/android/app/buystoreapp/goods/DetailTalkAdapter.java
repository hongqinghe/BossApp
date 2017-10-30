package com.android.app.buystoreapp.goods;

import java.util.List;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.GsonShopDetailInfoBack.Talk;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailTalkAdapter extends BaseAdapter {
    private Context mContext;
    private List<Talk> mDatas;
    private LayoutInflater mInflater;

    public DetailTalkAdapter(Context context, List<Talk> data) {
        mContext = context;
        mDatas = data;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mDatas != null ? mDatas.get(position) : null;
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
            convertView = mInflater.inflate(R.layout.shop_detail_talk_adapter,
                    null);
            holder.icon = (ImageView) convertView
                    .findViewById(R.id.id_shop_detail_talk_icon);
            holder.nickName = (TextView) convertView
                    .findViewById(R.id.id_shop_detail_talk_nickname);
            holder.content = (TextView) convertView
                    .findViewById(R.id.id_shop_detail_talk_content);
            holder.date = (TextView) convertView
                    .findViewById(R.id.id_shop_detail_talk_date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String icon = mDatas.get(position).getTalkIcon();
        if (icon != null) {
            Picasso.with(mContext).load(icon).placeholder(R.drawable.ic_default).error(R.drawable.ic_default).into(holder.icon);
        } else {
            Picasso.with(mContext).load(R.drawable.ic_default)
                    .into(holder.icon);
        }

        holder.nickName.setText(mDatas.get(position).getTalkNickName());
        holder.date.setText(mDatas.get(position).getTalkDate());
        holder.content.setText(mDatas.get(position).getTalkContent());

        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView content;
        TextView date;
        TextView nickName;
    }
}
