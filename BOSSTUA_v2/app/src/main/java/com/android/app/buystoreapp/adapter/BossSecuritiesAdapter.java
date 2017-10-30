package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.BossListBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 尚帅波 on 2016/9/27.
 */
public class BossSecuritiesAdapter extends BaseAdapter {
    private Context context;
    private List<BossListBean> datas;

    public BossSecuritiesAdapter(Context context, List<BossListBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return (datas == null) ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.ticket_list_item, null);
            holder = new ViewHolder();
            holder.iv_ticket = (ImageView) convertView.findViewById(R.id.iv_ticket);
            holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.iv_ticket.setImageResource(R.drawable.ic_ticket_wechat);
        if(!TextUtils.isEmpty(datas.get(position).getHeadicon())) {
            Picasso.with(context).load(datas.get(position).getHeadicon()).placeholder(R.drawable.ic_ticket_wechat).into(holder.iv_ticket);
        }
        holder.tv_date.setText(datas.get(position).getPayDate());
        if (datas.get(position).getStatus()==0){
            holder.tv_count.setText("修改手机号使用"+datas.get(position).getPayCount()+"张Boss券");
        }else if (datas.get(position).getStatus()==1){
            holder.tv_count.setText("查看"+datas.get(position).getNickname()+"的名片使用"+datas.get(position).getPayCount()+"张Boss券");
        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv_ticket;
        TextView tv_count, tv_date;
    }
}
