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
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 订阅adapter
 * <p/>
 * Created by Administrator on 2016/10/10.
 */
public class SubscribeAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<SubscribeBean.GsabListBean> dataList;
    private Context context;
    private OnSubscribeClickInterface onSubscribeClickInterface;

    public void setOnSubscribeClickInterface(OnSubscribeClickInterface onSubscribeClickInterface) {
        this.onSubscribeClickInterface = onSubscribeClickInterface;
    }


    public SubscribeAdapter(Context context, List<SubscribeBean.GsabListBean> data) {
        this.dataList = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_subscribe, null);
            holder = new ViewHolder();
            holder.tv_subscribe_button = (TextView) convertView.findViewById(R.id.tv_subscribe_button);
            holder.id_subscribe_image = (ImageView) convertView.findViewById(R.id.id_subscribe_image);
            holder.tv_subscribe_user_name = (TextView) convertView.findViewById(R.id.tv_subscribe_user_name);
            holder.iv_subscribe_user_leve = (ImageView) convertView.findViewById(R.id.iv_subscribe_user_leve);
            holder.tv_subscribe_num = (TextView) convertView.findViewById(R.id.tv_subscribe_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String iconUrl = dataList.get(position).getHeadicon();
        if (!TextUtils.isEmpty(iconUrl)) {
            Picasso.with(context).load(iconUrl)
                    .resize(45, 45)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default).into(holder.id_subscribe_image);
        }
        holder.tv_subscribe_user_name.setText(dataList.get(position).getNickname());


        if (dataList.get(position).getUserLevelMark() == 1) {
            holder.iv_subscribe_user_leve.setImageResource(R.drawable.vip_bojin);
        } else if (dataList.get(position).getUserLevelMark() == 2) {
            holder.iv_subscribe_user_leve.setImageResource(R.drawable.vip_zuanshi);
        } else if (dataList.get(position).getUserLevelMark() == 3) {
            holder.iv_subscribe_user_leve.setImageResource(R.drawable.vip_diwang);
        } else {
            holder.iv_subscribe_user_leve.setVisibility(View.INVISIBLE);
        }
        holder.tv_subscribe_num.setText(dataList.get(position).getUserSubscribeNum() + "");

        holder.tv_subscribe_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubscribeClickInterface.doClick(position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_subscribe_button;//关注状态
        ImageView id_subscribe_image;//头像
        TextView tv_subscribe_user_name;//企业名字
        ImageView iv_subscribe_user_leve;//vip等级
        TextView tv_subscribe_num;//关注数

    }

    public interface OnSubscribeClickInterface {
        void doClick(int i);
    }

}
