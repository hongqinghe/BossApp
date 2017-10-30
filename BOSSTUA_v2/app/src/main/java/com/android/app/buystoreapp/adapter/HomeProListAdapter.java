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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.HomeProBean;
import com.android.app.buystoreapp.bean.ProImageBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeProListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<HomeProBean> dataList;
    private Context context;
    private List<ProImageBean> mDatas;
    private GalleryAdapter mAdapter;
    private OnClickToMoreInterface onClickInterface;

    public void setOnClickInterface(OnClickToMoreInterface onClickInterface) {
        this.onClickInterface = onClickInterface;
    }

    public HomeProListAdapter(Context context, List<HomeProBean> data) {
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
            holder.mRecyclerView = (RecyclerView) convertView.findViewById(R.id
                    .id_home_item_recyclerview_horizontal);
            holder.icon = (ImageView) convertView
                    .findViewById(R.id.id_home_item_image);
            holder.ll_recyclerview = (LinearLayout) convertView.findViewById(R.id.ll_recyclerview);
            holder.lasttime = (TextView) convertView.findViewById(R.id.tv_last_line_time);
            holder.name = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.profession = (TextView) convertView.findViewById(R.id.tv_user_profession);
            holder.level = (ImageView) convertView.findViewById(R.id.iv_user_leve);
            holder.money = (TextView) convertView.findViewById(R.id.tv_user_money);
            holder.moreGroUnit = (TextView) convertView.findViewById(R.id.textView4);
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
            holder.professionico = (ImageView) convertView.findViewById(R.id
                    .iv_user_info_profession);
            holder.attention = (TextView) convertView.findViewById(R.id.tv_attention_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ViewGroup.LayoutParams params = holder.icon.getLayoutParams();
        String iconUrl = dataList.get(position).getUserHeadIcon();
        if (!TextUtils.isEmpty(iconUrl)) {
            Picasso.with(context).load(iconUrl)
                    .resize(params.width, params.height)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default).into(holder.icon);
        } else {
            Picasso.with(context).load(R.drawable.ic_default).into(holder.icon);
        }
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickInterface.toMoreClick(position);
            }
        });
        holder.name.setText(dataList.get(position).getUserName());
        if (!TextUtils.isEmpty(dataList.get(position).getUserPosition())) {
            holder.profession.setText("・" + dataList.get(position).getUserPosition());
        } else {
            holder.profession.setText("");
        }
        holder.lasttime.setText(dataList.get(position).getLoginTime());//最后上线时间
//        holder.lasttime.setText(DateUtils.totime(dataList.get(position).getLoginTime())+"来过");
        holder.money.setText(dataList.get(position).getUserTreasure());
        if (dataList.get(position).getUserLevelMark() == 1) {
            holder.level.setImageResource(R.drawable.vip_bojin);
        } else if (dataList.get(position).getUserLevelMark() == 2) {
            holder.level.setImageResource(R.drawable.vip_zuanshi);
        } else if (dataList.get(position).getUserLevelMark() == 3) {
            holder.level.setImageResource(R.drawable.vip_diwang);
        } else {
            holder.level.setVisibility(View.GONE);
        }
        if (dataList.get(position).getBindingMark1() == 1) {
            holder.verifieo.setVisibility(View.VISIBLE);
            holder.verifieo.setImageResource(R.drawable.iv_user_info_verified);
        } else {
            holder.verifieo.setVisibility(View.GONE);
        }
        if (dataList.get(position).getBindingMark2() == 1) {
            holder.credit.setVisibility(View.VISIBLE);
            holder.credit.setImageResource(R.drawable.iv_user_info_credit_light);
        } else {
            holder.credit.setVisibility(View.GONE);
        }
        if (dataList.get(position).getBindingMark3() == 1) {
            holder.company.setVisibility(View.VISIBLE);
            holder.company.setImageResource(R.drawable.iv_user_info_company_light);
        } else {
            holder.company.setVisibility(View.GONE);
        }
        if (dataList.get(position).getBindingMark4() == 1) {
            holder.professionico.setVisibility(View.VISIBLE);
            holder.professionico.setImageResource(R.drawable.iv_user_info_profession_light);
        } else {
            holder.professionico.setVisibility(View.GONE);
        }
        holder.tabl.setText(dataList.get(position).getServeLabelName());
        holder.title.setText(dataList.get(position).getProName());
        holder.intro.setText(dataList.get(position).getProDes());
        String price = dataList.get(position).getProCurrentPrice();
        holder.price.setText(String.format("%1$s", price));
        String unit = dataList.get(position).getMoreGroUnit();
        if (unit.equals("")) {
            unit = "单";
        }
        holder.moreGroUnit.setText(String.format("元/%1$s", unit));
        String salenum = String.format(
                context.getResources().getString(R.string.home_item_salenum),
                dataList.get(position).getProSale());
        holder.salenum.setText(salenum);
        String proSurplus = String.format(context.getResources().getString(R.string
                .home_item_proSurplus), dataList.get(position).getProSurplus());
        holder.surplus.setText(proSurplus);
        holder.browse.setText(dataList.get(position).getProSeeNum());

//        Double distance = Double.valueOf(dataList.get(position).getProDistance());
//        holder.distance.setText((Math.round(distance / 100000d) / 10d) + "km");
//        Double distance = Double.valueOf(dataList.get(position).getProDistance());
        holder.distance.setText(dataList.get(position).getProDistance());

        mDatas = dataList.get(position).getImageList();
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new GalleryAdapter(context, mDatas);
        holder.mRecyclerView.setAdapter(mAdapter);
//        holder.ll_recyclerview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("点击ll_recyclerview","点击了");
//                onClickInterface.toDetailClick(position);
//            }
//        });
       /* holder.mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.toDetailClick(position);
            }
        });*/
              /*  .setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//此处是点击按下时才执行跳转动作
                  onClickInterface.toDetailClick(position);
                }
                return false;
            }
        });*/
        mAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int i) {
                onClickInterface.toDetailClick(position);
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
        TextView moreGroUnit;
        LinearLayout ll_recyclerview;
        RecyclerView mRecyclerView;
    }

    public interface OnClickToMoreInterface {
        void toDetailClick(int i);

        void toMoreClick(int i);
    }

}
