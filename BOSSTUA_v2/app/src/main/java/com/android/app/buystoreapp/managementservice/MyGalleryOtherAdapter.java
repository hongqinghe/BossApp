package com.android.app.buystoreapp.managementservice;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.GuessLikeBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class MyGalleryOtherAdapter extends BaseAdapter {
    private Context context;
    private List<GuessLikeBean.LikeProBean> list = new ArrayList<>();//猜你喜欢集合

    public MyGalleryOtherAdapter(Context context, List<GuessLikeBean.LikeProBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return (list == null) ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.goods_detail_gallery_item, null);
            holder = new ViewHolder();
            holder.id_index_gallery_item_image = (ImageView) view.findViewById(R.id
                    .id_index_gallery_item_image);
            holder.id_index_gallery_item_text = (TextView) view.findViewById(R.id
                    .id_index_gallery_item_text);
            holder.id_gallery_item_money = (TextView) view.findViewById(R.id
                    .id_gallery_item_money);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (!TextUtils.isEmpty(list.get(i).getProImageUrl())) {
            Picasso.with(context).load(list.get(i).getProImageUrl()).placeholder(R.drawable
                    .ic_boss_default).into(holder.id_index_gallery_item_image);
        }
        holder.id_index_gallery_item_text.setText(list.get(i).getProName() + "");
        holder.id_gallery_item_money.setText("￥"+list.get(i).getPrice());
        return view;
    }

    class ViewHolder {
        ImageView id_index_gallery_item_image;
        TextView id_index_gallery_item_text;
        TextView id_gallery_item_money;//要+￥
    }
}
