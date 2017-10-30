package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.GroupGoods;
import com.android.app.buystoreapp.bean.ShoppingCarBean;
import com.android.app.utils.DecimalUtil;

import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/10/27.
 */
public class NewConfirmOrderAdapter extends BaseAdapter{
    private Context context;
    private List<ShoppingCarBean> list;
    private String modes;
    private int freightPrice;

    public NewConfirmOrderAdapter(Context context, List<ShoppingCarBean> list) {
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
        GroupViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.confirm_order_store, null);
            holder = new GroupViewHolder();
            holder.tvGroup = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tvmodus = (TextView) convertView.findViewById(R.id.tv_fireght_modes);
            holder.tvFright = (TextView) convertView.findViewById(R.id.tv_fireght_privce);
            holder.tvAllPrice = (TextView) convertView.findViewById(R.id.tv_order_all_price);
            holder.count = (TextView) convertView.findViewById(R.id.tv_order_count);
            holder.listview = (ListView) convertView.findViewById(R.id.lv_order_all);
            holder.message = (EditText) convertView.findViewById(R.id.et_order_leave_message);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.tvGroup.setText(list.get(position).getNickname());
        List<GroupGoods> itemList = list.get(position).getQueryCar();
        holder.listview.setAdapter(new ConfirmOrderItemAdapter(context, list.get(position).getQueryCar()));
        String selectedMoney = "0";
        int m = 0;
        int f = 0;
        for (int i = 0; i < itemList.size(); i++) {
            int mod = itemList.get(i).getModes();
            if (m < mod) {
                m = mod;
            }
            if (m == 2){
                f = itemList.get(i).getFreightMode();
            }
            if (!TextUtils.isEmpty(itemList.get(i).getFreightPrice())) {
                freightPrice += itemList.get(i).getCount() * Integer.valueOf(itemList.get(i).getFreightPrice());
            }
            String price = itemList.get(i).getMoreGroPrice();
            String num = String.valueOf(itemList.get(i).getCount());
            String countMoney = DecimalUtil.multiply(price, num);
            selectedMoney = DecimalUtil.add(selectedMoney, countMoney);
//            selectedCount = DecimalUtil.add(selectedCount, "1");
        }
        if (m == 2) {
            holder.tvFright.setVisibility(View.VISIBLE);
            modes = "货运:";
            if (f == 0){
                holder.tvFright.setText("免运费");
            }else if (f== 1){
                holder.tvFright.setText("货到付款");
            }else if (freightPrice !=0){
                holder.tvFright.setText("￥" + freightPrice*1d);
            }
        } else {
            holder.tvFright.setVisibility(View.GONE);
            if (m == 1) {
                modes = "线下";
            } else {
                modes = "线上";
            }
        }
        holder.tvmodus.setText(modes);
        holder.message.setTag(position);
        holder.count.setText(String.format(context.getString(R.string.confirm_order_goods_count), String.valueOf(itemList.size())));
        holder.tvAllPrice.setText("￥" + selectedMoney);
//        holder.message.addTextChangedListener(this);
        return convertView;
    }

   static class GroupViewHolder {
        TextView tvGroup;//商家名称
        TextView tvmodus;//服务方式
        TextView tvFright;//运费
        TextView tvAllPrice;//总价
        TextView count;//数量
        ListView listview;
        EditText message;
    }
}
