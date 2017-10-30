package com.android.app.buystoreapp.order;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 尚帅波 on 2016/9/21.
 */
public class OrderProductAdapter extends BaseAdapter {
    private Context context;
    private List<OrderProduct> productList;

    public OrderProductAdapter(Context context, List<OrderProduct>
            productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return (productList == null) ? 0 : productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.order_list_child_item, null);
            holder = new ViewHolder();
            holder.iv_orderImg = (ImageView) convertView.findViewById(R.id.iv_orderImg);
            holder.tv_orderName = (TextView) convertView.findViewById(R.id.tv_orderName);
            holder.tv_groupName = (TextView) convertView.findViewById(R.id.tv_groupName);
            holder.tv_serviceTime = (TextView) convertView.findViewById(R.id.tv_serviceTime);
            holder.tv_orderPrice = (TextView) convertView.findViewById(R.id.tv_orderPrice);
            holder.tv_orderNum = (TextView) convertView.findViewById(R.id.tv_orderNum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderProduct product = productList.get(position);
        String uri = product.getProImgUrl();
        if (!TextUtils.isEmpty(uri)) {
            Picasso.with(context).load(uri)
                    //.resize(100,100)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default).into(holder.iv_orderImg);
        } else {
            Picasso.with(context).load(R.drawable.ic_default).into(holder.iv_orderImg);
        }
        holder.tv_orderName.setText(product.getProName());
        holder.tv_groupName.setText(product.getMoreName());
        holder.tv_serviceTime.setText(product.getWeekStart() + "至" + product.getWeekEnd() + "(" +
                product.getTimeStart() + "-" + product.getTimeEnd() + ")");
        holder.tv_orderPrice.setText(product.getProPrice() + "");
        holder.tv_orderNum.setText(product.getProCount() + "");
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_orderImg;
        private TextView tv_orderName, tv_groupName, tv_serviceTime, tv_orderPrice,
                tv_orderNum;
    }
}
