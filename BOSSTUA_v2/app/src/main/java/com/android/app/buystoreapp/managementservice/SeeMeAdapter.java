package com.android.app.buystoreapp.managementservice;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
public class SeeMeAdapter extends BaseAdapter {
    private Context context;
    private List<SeeMeBean.GetPproductToViewBeanListBean> list = new ArrayList<SeeMeBean.GetPproductToViewBeanListBean>();

    public SeeMeAdapter(Context context, List<SeeMeBean.GetPproductToViewBeanListBean> list) {
        this.context = context;
        this.list = list;
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
            convertView = View.inflate(context, R.layout.item_see_me, null);
            holder = new ViewHolder();
            holder.iv_see_me_headicon = (ImageView) convertView.findViewById(R.id.iv_see_me_headicon);
            holder.tv_see_me_name = (TextView) convertView.findViewById(R.id.tv_see_me_name);
            holder.tv_see_me_time = (TextView) convertView.findViewById(R.id.tv_see_me_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String icon = list.get(position).getThisUserHeadicon();
        if (!TextUtils.isEmpty(icon)) {
            Picasso.with(context)
                    .load(icon)
                    //.resize(45, 45)
                    .placeholder(R.drawable.ic_speed_chat_head_default)
                    .into(holder.iv_see_me_headicon);
        }
        holder.tv_see_me_name.setText(list.get(position).getNickname());
        holder.tv_see_me_time.setText(list.get(position).getToViewTime());
        return convertView;
    }

    class ViewHolder {
        ImageView iv_see_me_headicon;
        TextView tv_see_me_name;
        TextView tv_see_me_time;
    }
}
