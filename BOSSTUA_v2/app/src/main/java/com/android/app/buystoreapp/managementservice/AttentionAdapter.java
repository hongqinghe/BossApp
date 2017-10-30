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
 * 关注adapter
 * <p/>
 * weilin
 */
public class AttentionAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<AlreadyConcernedBena.GaabListBean> dataList;
    private Context context;
    private OnAttentionListener onAttentionListener;

    public void setOnAttentionListener(OnAttentionListener onAttentionListener) {
        this.onAttentionListener = onAttentionListener;
    }

    public AttentionAdapter(Context context, List<AlreadyConcernedBena.GaabListBean> data) {
        this.dataList = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
//        return 3;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_attention, parent, false);
            holder = new ViewHolder();
            holder.tv_attention_button = (TextView) convertView.findViewById(R.id.tv_attention_button);
            holder.id_home_item_image = (ImageView) convertView.findViewById(R.id.id_home_item_image);
            holder.tv_attention_user_name = (TextView) convertView.findViewById(R.id.tv_attention_user_name);
            holder.tv_user_profession = (TextView) convertView.findViewById(R.id.tv_user_profession);
            holder.tv_user_attention_num = (TextView) convertView.findViewById(R.id.tv_user_attention_num);
            holder.iv_user_leve = (ImageView) convertView.findViewById(R.id.iv_user_leve);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_attention_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAttentionListener.doCheck(position);
            }
        });

        String iconUrl = dataList.get(position).getHeadicon();
        if (!TextUtils.isEmpty(iconUrl)) {
            Picasso.with(context).load(iconUrl)
                    //.resize(45,45)
                    .placeholder(R.drawable.ic_default)
                    .into(holder.id_home_item_image);
        }
        holder.tv_attention_user_name.setText(dataList.get(position).getNickname());
        holder.tv_user_attention_num.setText(String.valueOf(dataList.get(position).getUserAttentionNum()));
        if (dataList.get(position).getUserLevelMark() == 0) {
            holder.iv_user_leve.setVisibility(View.GONE);
        }
        if (dataList.get(position).getUserLevelMark() == 1) {
            holder.iv_user_leve.setImageResource(R.drawable.vip_bojin);
        }
        if (dataList.get(position).getUserLevelMark() == 2) {
            holder.iv_user_leve.setImageResource(R.drawable.vip_zuanshi);
        }
        if (dataList.get(position).getUserLevelMark() == 3) {
            holder.iv_user_leve.setImageResource(R.drawable.vip_diwang);
        }
        if ("".equals(dataList.get(position).getUserPosition())) {
            holder.tv_user_profession.setText("");
        } else {
            holder.tv_user_profession.setText("・" + dataList.get(position).getUserPosition());
        }
//        HomeProBean homeProBean = dataList.get(position);
//        holder.tv_attention_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.tv_attention_button.setText("关注");
//                holder.tv_attention_button.setTextColor(context.getResources().getColor(R.color.c_515151));
//                holder.tv_attention_button.setTextSize(18);
//                holder.tv_attention_button.setBackgroundResource(R.drawable.corners_orange_bg);
//                ToastUtil.showMessageDefault(context, "显示下效果");
//            }
//        });
        return convertView;
    }

    static class ViewHolder {
        TextView tv_attention_button;//关注状态
        ImageView id_home_item_image;//头像
        TextView tv_attention_user_name;//用户名加“・”
        TextView tv_user_profession;//职业
        TextView tv_user_attention_num;//关注数
        ImageView iv_user_leve;//VIP等级
    }

    public interface OnAttentionListener {
        void doCheck(int i);
    }
}
