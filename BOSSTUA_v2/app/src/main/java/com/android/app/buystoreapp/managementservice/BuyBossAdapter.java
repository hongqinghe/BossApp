package com.android.app.buystoreapp.managementservice;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.BuyBossListBean;

import java.util.List;

/**
 * Created by 尚帅波 on 2016/9/27.
 */
public class BuyBossAdapter extends BaseAdapter {
    private Context context;
    private List<BuyBossListBean> datas;

    public BuyBossAdapter(Context context, List<BuyBossListBean> datas) {
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
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

        BuyBossListBean data = datas.get(position);
        if (data.getStatus()==1){
            holder.iv_ticket.setImageResource(R.drawable.ic_ticket_wechat);
            holder.tv_count.setText("购买"+data.getBuyCount()+"张Boss券");
        }else if (data.getStatus()==2){
            holder.iv_ticket.setImageResource(R.drawable.ic_ticket_alipay);
            holder.tv_count.setText("购买"+data.getBuyCount()+"张Boss券");
        }else if (data.getStatus()==3){
            holder.iv_ticket.setImageResource(R.drawable.ic_haoyouzengsong);
            holder.tv_count.setText("邀请好友 奖励一张Boss券");
        }else if (data.getStatus()==4){
            holder.iv_ticket.setImageResource(R.drawable.ic_haoyouzengsong);
            holder.tv_count.setText("好友邀请 奖励一张Boss券");
        }else if (data.getStatus()==5){

        }
        holder.tv_date.setText(data.getPayDate());
        return convertView;
    }

    class ViewHolder {
        ImageView iv_ticket;
        TextView tv_count, tv_date;
    }
}
