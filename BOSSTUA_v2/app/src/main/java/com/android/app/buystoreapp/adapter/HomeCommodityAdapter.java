package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.CommodityBean;
import com.android.app.buystoreapp.bean.ProImageBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeCommodityAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<CommodityBean> dataList;
    private Context context;
    private boolean mIsDisapperDistance;
    private List<ProImageBean> mDatas;
    private GalleryAdapter mAdapter;

    public HomeCommodityAdapter(Context context, List<CommodityBean> data) {
        this.dataList = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public HomeCommodityAdapter(Context context, List<CommodityBean> data, boolean disapperDistance) {
        this.dataList = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
        mIsDisapperDistance = disapperDistance;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.home_item, parent, false);
            holder = new ViewHolder();
            holder.mRecyclerView = (RecyclerView) convertView.findViewById(R.id.id_home_item_recyclerview_horizontal);
            holder.icon = (ImageView) convertView
                    .findViewById(R.id.id_home_item_image);
            holder.name = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.profession = (TextView) convertView.findViewById(R.id.tv_user_profession);
            holder.level = (ImageView) convertView.findViewById(R.id.iv_user_leve);
//            holder.scanNum = (TextView) convertView.findViewById(R.id.tv_scan_num);
            holder.money = (TextView) convertView.findViewById(R.id.tv_user_money);
            holder.browse = (TextView) convertView.findViewById(R.id.tv_browse);
            holder.surplus = (TextView) convertView.findViewById(R.id.tv_goods_surplus);
            holder.title = (TextView) convertView
                    .findViewById(R.id.id_home_item_title);
            holder.intro = (TextView) convertView
                    .findViewById(R.id.id_home_item_intro);
            holder.price = (TextView) convertView
                    .findViewById(R.id.id_home_item_price);
//            holder.marketPrice = (TextView) convertView
//                    .findViewById(R.id.id_home_item_marketprice);
//            holder.score = (TextView) convertView
//                    .findViewById(R.id.id_home_item_score);
            holder.salenum = (TextView) convertView
                    .findViewById(R.id.id_home_item_salenum);
//            holder.address = (TextView) convertView
//                    .findViewById(R.id.id_home_item_address);
            holder.distance = (TextView) convertView
                    .findViewById(R.id.id_home_item_distance);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String iconUrl = dataList.get(position).getUserHeadIcon();
        if (!TextUtils.isEmpty(iconUrl)) {
            Picasso.with(context).load(iconUrl)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default).into(holder.icon);
        } else {
            Picasso.with(context).load(R.drawable.ic_default).into(holder.icon);
        }
        holder.title.setText(dataList.get(position).getProName());
        holder.intro.setText(dataList.get(position).getProDes());
        String price = dataList.get(position).getProCurrentPrice();
        holder.price.setText(String.format("%1$s元", price));
//        String marketPrice = dataList.get(position).getCommodityMarketPrice();
       /* if (Double.valueOf(price) < Double.valueOf(marketPrice)) {
            holder.marketPrice.setVisibility(View.VISIBLE);
            holder.marketPrice.setText(String.format("%1$s元", marketPrice));
            holder.marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.price.setText(String.format("%1$s元", price));
        } else {
            holder.marketPrice.setVisibility(View.INVISIBLE);
            holder.price.setText(String.format("%1$s元", price));
        }*/

//        String score = String.format(
//                context.getResources().getString(R.string.home_item_score),
//                dataList.get(position).getCommodityScore());
//        holder.score.setText(score);

        String salenum = String.format(
                context.getResources().getString(R.string.home_item_salenum),
                dataList.get(position).getProSale());
        holder.salenum.setText("已售[" + salenum + "单]");

//        holder.address.setText(dataList.get(position).getCommodityAdress());

        if (mIsDisapperDistance) holder.distance.setVisibility(View.GONE);
        String distance = dataList.get(position).getProDistance();
        String distanceFormat = String.format(
                context.getResources().getString(
                        R.string.business_item_distance), TextUtils.isEmpty(distance) ? "0" : distance);
        holder.distance.setText(distanceFormat);

//        initDatas();
        mDatas = (List<ProImageBean>) dataList.get(position).getImageList();
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new GalleryAdapter(context, mDatas);
        holder.mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    /*private void initDatas() {
        mDatas = new ArrayList<Integer>(Arrays.asList(R.drawable.ic_default,
                R.drawable.ic_default, R.drawable.ic_default, R.drawable.ic_default, R.drawable.ic_default,
                R.drawable.ic_default, R.drawable.ic_default, R.drawable.ic_default));
    }*/

    static class ViewHolder {
        TextView name;
        TextView profession;
        ImageView level;
        ImageView icon;
        TextView scanNum;
        TextView money;
        TextView title;
        TextView intro;
        TextView price;
        TextView browse;
        TextView surplus;
        //        TextView marketPrice;
//        TextView score;
        TextView salenum;
        //        TextView address;
        TextView distance;
        RecyclerView mRecyclerView;
    }

}
