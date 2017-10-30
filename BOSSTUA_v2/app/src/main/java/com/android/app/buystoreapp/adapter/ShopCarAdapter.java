package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.CommodityBean;

import java.util.List;

public class ShopCarAdapter extends BaseAdapter {

    public interface ShopCarListener {
        void itemSelected(boolean isChecked, int position);

        boolean itemAddNum(String commodityID, int positionString );

        boolean itemMinusNum(String commodityID, int position);

        void itemDel(String commodityID);
    }

    private Context mContext;
    private LayoutInflater mInflater;
    private List<CommodityBean> mDatas;
    private ShopCarListener mListener;
    private SparseBooleanArray mItemSelectedState;

    public SparseBooleanArray getmItemSelectedState() {
        return mItemSelectedState;
    }

    public void setmItemSelectedState(SparseBooleanArray mItemSelectedState) {
        this.mItemSelectedState = mItemSelectedState;
    }

    public ShopCarAdapter(Context context, List<CommodityBean> data) {
        mInflater = LayoutInflater.from(context);
        mDatas = data;
        mContext = context;
        mListener = (ShopCarListener) context;
        mItemSelectedState = new SparseBooleanArray();
        for (int i = 0; i < mDatas.size(); i++) {
            mItemSelectedState.put(i, false);
        }
        mItemSelectedState.put(mDatas.size(),false);//全选与否
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public CommodityBean getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
         ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.shop_car_item, null);
            holder.itemSelect = (CheckBox) convertView
                    .findViewById(R.id.id_shop_car_item_select);
            holder.itemIcon = (ImageView) convertView
                    .findViewById(R.id.id_shop_car_item_icon);
            holder.itemMoney = (TextView) convertView
                    .findViewById(R.id.id_shop_car_item_money);
            holder.itemName = (TextView) convertView
                    .findViewById(R.id.id_shop_car_item_name);
            holder.itemPrice = (TextView) convertView
                    .findViewById(R.id.id_shop_car_item_price);
            holder.itemAdd = (ImageButton) convertView
                    .findViewById(R.id.id_shop_car_item_add);
            holder.itemNum = (TextView) convertView
                    .findViewById(R.id.id_shop_car_item_num);
            holder.itemMinus = (ImageButton) convertView
                    .findViewById(R.id.id_shop_car_item_minus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

       /* String icon = mDatas.get(position).getCommodityIcon().toString();
        if (!TextUtils.isEmpty(icon)) {
            Picasso.with(mContext).load(icon)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default).into(holder.itemIcon);
        } else {
            Picasso.with(mContext).load(R.drawable.ic_default)
                    .into(holder.itemIcon);
        }*/

        holder.itemName.setText(mDatas.get(position).getProName());

        final String price = mDatas.get(position).getProCurrentPrice()
                .toString();
        holder.itemPrice.setText(price);

        int commodityNum = Integer.valueOf(mDatas.get(position)
                .getCommodityNum().toString());
        holder.itemNum.setText(String.valueOf(commodityNum));

        float itemTotalMoney = commodityNum * Float.valueOf(price);
        holder.itemMoney.setText(String.format(
                mContext.getString(R.string.shop_car_item_money),
                itemTotalMoney));

        holder.itemAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mListener.itemAddNum(mDatas.get(position).getProId(),
                        position);
            }
        });

        holder.itemMinus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mListener.itemMinusNum(mDatas.get(position).getProId(),
                        position);
            }
        });

        holder.itemSelect.setTag(position);
        if (mItemSelectedState.get(position)) {
            holder.itemSelect.setChecked(true);
        } else {
            holder.itemSelect.setChecked(false);
        }
        holder.itemSelect
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton arg0,
                            boolean isChecked) {
                        mItemSelectedState.put((int) arg0.getTag(), isChecked);
                        if (mItemSelectedState.get(mDatas.size())) return;
                        mListener.itemSelected(isChecked, position);
                    }
                });

        return convertView;
    }

    static class ViewHolder {
        CheckBox itemSelect;
        ImageView itemIcon;
        TextView itemMoney;
        TextView itemName;
        TextView itemPrice;
        TextView itemNum;
        ImageButton itemMinus;
        ImageButton itemAdd;
    }
}
