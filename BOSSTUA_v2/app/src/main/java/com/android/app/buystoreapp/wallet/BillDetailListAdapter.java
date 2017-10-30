package com.android.app.buystoreapp.wallet;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.BilDetailsBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 尚帅波 on 2016/9/18.
 */
public class BillDetailListAdapter extends BaseAdapter {
    private Context context;
    private List<BilDetailsBean> list;

    public BillDetailListAdapter(Context context, List<BilDetailsBean> list) {
        this.context = context;
        this.list = list;
    }



    @Override
    public int getCount() {
        return (list == null) ? 0 : list.size();
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
            convertView = View.inflate(context, R.layout.bill_detail_list_item, null);
            holder = new ViewHolder();
//            holder.tv_bill_list_item_date = (TextView) convertView.findViewById(R.id
//                    .tv_bill_list_item_date);
            holder.tv_bill_list_item_time = (TextView) convertView.findViewById(R.id
                    .tv_bill_list_item_time);
            holder.tv_bill_list_item_type = (TextView) convertView.findViewById(R.id
                    .tv_bill_list_item_type);
            holder.tv_bill_list_item_money = (TextView) convertView.findViewById(R.id
                    .tv_bill_list_item_money);
            holder.tv_bill_list_item_other = (TextView) convertView.findViewById(R.id
                    .tv_bill_list_item_other);
            holder.iv_bill_detail_head = (ImageView) convertView.findViewById(R.id
                    .iv_bill_detail_head);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BilDetailsBean data = list.get(position);
//        holder.tv_bill_list_item_date.setText(data.getBilDate());
        holder.tv_bill_list_item_other.setText(data.getProTitle()+"");
        if (data.getBilState()==1){
            holder.tv_bill_list_item_type.setText("收入");
            if (!TextUtils.isEmpty(data.getHeadicon())){
                Picasso.with(context).load(data.getHeadicon())
                        .placeholder(R.drawable.ic_business_head)
                        .resize(50,50)
                        .into(holder.iv_bill_detail_head);
            }
//            holder.tv_bill_list_item_other.setText(data.getProTitle()+"-"+data.getNickname());
        }else if (data.getBilState()==2){
            holder.iv_bill_detail_head.setImageResource(R.drawable.ic_ticket_wechat);
            holder.tv_bill_list_item_type.setText("支出");
//            holder.tv_bill_list_item_other.setText(data.getProTitle()+"");
        }else if (data.getBilState()==3){
            holder.iv_bill_detail_head.setImageResource(R.drawable.ic_ticket_alipay);
            holder.tv_bill_list_item_type.setText("支出");
//            holder.tv_bill_list_item_other.setText(data.getProTitle()+"");
        }else if (data.getBilState()==4){
            holder.iv_bill_detail_head.setImageResource(R.drawable.ic_ticket_unionpay);
            holder.tv_bill_list_item_type.setText("提现");
//            holder.tv_bill_list_item_other.setText(""+data.getProTitle());


        }else if (data.getBilState()==5){
            holder.iv_bill_detail_head.setImageResource(R.drawable.ic_ticket_alipay);
            holder.tv_bill_list_item_type.setText("提现");
//            holder.tv_bill_list_item_other.setText(""+data.getProTitle());
        }
        holder.tv_bill_list_item_money.setText(data.getBilAmount()+"");
        holder.tv_bill_list_item_time.setText(data.getFamDateTime()+"");

        return convertView;
    }

    class ViewHolder {
        TextView tv_bill_list_item_date, tv_bill_list_item_time, tv_bill_list_item_type,
                tv_bill_list_item_money, tv_bill_list_item_other;
        ImageView iv_bill_detail_head;
    }
}
