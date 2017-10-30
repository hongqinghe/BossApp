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

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.LpdbListBean;
import com.android.app.buystoreapp.bean.ProImageMinBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchLpdbListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<LpdbListBean> dataList;
    private Context context;
    private List<ProImageMinBean> mDatas;
    private ProImageMinAdapter mAdapter;
    private OnMyMoreClick onMyMoreClick;

    public SearchLpdbListAdapter(Context context, List<LpdbListBean> data) {
        this.dataList = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setOnMyMoreClick(OnMyMoreClick onMyMoreClick) {
        this.onMyMoreClick = onMyMoreClick;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.home_item, parent, false);
            holder = new ViewHolder();
            holder.mRecyclerView = (RecyclerView) convertView.findViewById(R.id.id_home_item_recyclerview_horizontal);
            holder.icon = (ImageView) convertView
                    .findViewById(R.id.id_home_item_image);
            holder.lasttime = (TextView) convertView.findViewById(R.id.tv_last_line_time);
            holder.name = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.profession = (TextView) convertView.findViewById(R.id.tv_user_profession);
            holder.level = (ImageView) convertView.findViewById(R.id.iv_user_leve);
            holder.money = (TextView) convertView.findViewById(R.id.tv_user_money);
            holder.browse = (TextView) convertView.findViewById(R.id.tv_browse);
            holder.surplus = (TextView) convertView.findViewById(R.id.tv_goods_surplus);
            holder.tabl = (TextView) convertView.findViewById(R.id.tv_tab);
            holder.title = (TextView) convertView
                    .findViewById(R.id.id_home_item_title);
            holder.intro = (TextView) convertView
                    .findViewById(R.id.id_home_item_intro);
            holder.price = (TextView) convertView
                    .findViewById(R.id.id_home_item_price);
            holder.salenum = (TextView) convertView
                    .findViewById(R.id.id_home_item_salenum);
            holder.distance = (TextView) convertView
                    .findViewById(R.id.id_home_item_distance);
            holder.verifieo = (ImageView) convertView.findViewById(R.id.iv_info_verified);
            holder.credit = (ImageView) convertView.findViewById(R.id.iv_user_info_credit);
            holder.company = (ImageView) convertView.findViewById(R.id.iv_user_info_company);
            holder.professionico = (ImageView) convertView.findViewById(R.id.iv_user_info_profession);
            holder.attention = (TextView) convertView.findViewById(R.id.tv_attention_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMyMoreClick.toMyMoreClick(position);
            }
        });
        ViewGroup.LayoutParams params = holder.icon.getLayoutParams();
        String iconUrl = dataList.get(position).getHeadicon();
        if (!TextUtils.isEmpty(iconUrl)) {
            Picasso.with(context).load(iconUrl)
                    .resize(params.width, params.height)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default).into(holder.icon);
        } else {
            Picasso.with(context).load(R.drawable.ic_default).into(holder.icon);
        }
        holder.name.setText(dataList.get(position).getProName());
        if (!TextUtils.isEmpty(dataList.get(position).getUserPosition())) {
            holder.profession.setText("・" + dataList.get(position).getUserPosition());
        } else {
            holder.profession.setText("");
        }
        holder.lasttime.setText(dataList.get(position).getLoginTime());
//        holder.lasttime.setText(DateUtils.totime(dataList.get(position).getLoginTime())+"来过");
        holder.money.setText(dataList.get(position).getUserTreasure());
        if ("1".equals(dataList.get(position).getUserLevelMark())) {
            holder.level.setImageResource(R.drawable.vip_bojin);
        } else if ("2".equals(dataList.get(position).getUserLevelMark())) {
            holder.level.setImageResource(R.drawable.vip_zuanshi);
        } else if ("3".equals(dataList.get(position).getUserLevelMark())) {
            holder.level.setImageResource(R.drawable.vip_diwang);
        } else {
            holder.level.setVisibility(View.GONE);
        }
        if ("1".equals(dataList.get(position).getBindingMark1())) {
            holder.verifieo.setVisibility(View.VISIBLE);
            holder.verifieo.setImageResource(R.drawable.iv_user_info_verified);
        } else {
            holder.verifieo.setVisibility(View.GONE);
        }
        if ("1".equals(dataList.get(position).getBindingMark2())) {
            holder.credit.setVisibility(View.VISIBLE);
            holder.credit.setImageResource(R.drawable.iv_user_info_credit_light);
        } else {
            holder.credit.setVisibility(View.GONE);
        }
        if ("1".equals(dataList.get(position).getBindingMark3())) {
            holder.company.setVisibility(View.VISIBLE);
            holder.company.setImageResource(R.drawable.iv_user_info_company_light);
        } else {
            holder.company.setVisibility(View.GONE);
        }
        if ("1".equals(dataList.get(position).getBindingMark4())) {
            holder.professionico.setVisibility(View.VISIBLE);
            holder.professionico.setImageResource(R.drawable.iv_user_info_profession_light);
        } else {
            holder.professionico.setVisibility(View.GONE);
        }
        holder.tabl.setText(dataList.get(position).getServeLabelName());
        holder.title.setText(dataList.get(position).getProName());
        holder.intro.setText(dataList.get(position).getProDes());
        String price = dataList.get(position).getUserTreasure();
        holder.price.setText(String.format("%1$s", price));
        String salenum = String.format(
                context.getResources().getString(R.string.home_item_salenum),
                dataList.get(position).getProSale());
        holder.salenum.setText(salenum);
        String proSurplus = String.format(context.getResources().getString(R.string.home_item_proSurplus), dataList.get(position).getMoreGroSurplus());
        holder.surplus.setText(proSurplus);
        holder.browse.setText(dataList.get(position).getProSeeNum());

        holder.distance.setText(dataList.get(position).getDistanceKm());

        mDatas = dataList.get(position).getProImageMin();
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new ProImageMinAdapter(context, mDatas);
        holder.mRecyclerView.setAdapter(mAdapter);
//        holder.mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN)
//                    onMyMoreClick.toDetailClick(position);
//
//                return false;
//            }
//        });
        mAdapter.setOnItemClickLitener(new ProImageMinAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                onMyMoreClick.toDetailClick(position);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView profession;
        ImageView level;
        ImageView icon;
        TextView tabl;
        TextView money;
        ImageView verifieo;
        ImageView credit;
        ImageView company;
        ImageView professionico;
        TextView title;
        TextView intro;
        TextView price;
        TextView browse;
        TextView surplus;
        TextView attention;
        TextView lasttime;
        TextView salenum;
        TextView distance;
        RecyclerView mRecyclerView;
    }

    public interface OnMyMoreClick {
        void toDetailClick(int i);

        void toMyMoreClick(int i);
    }

}
