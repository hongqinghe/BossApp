package com.android.app.buystoreapp.managementservice;

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
import com.android.app.buystoreapp.adapter.MyGalleryAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 服务·资源的adapter
 * <p/>
 * weilin
 */

public class ServiceResourcesAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<CollectionBean.UcpListBean> dataList;
    private Context context;
    private List<CollectionBean.UcpListBean.PiListBean> mDatas;
    private MyGalleryAdapter mAdapter;
    private String price;

    List list = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L');
    private String serveLabel;
    private OnSerivceResourcesClick onSerivceResourcesClick;

    public ServiceResourcesAdapter(Context context, List<CollectionBean.UcpListBean> data) {
        this.dataList = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setOnSerivceResourcesClick(OnSerivceResourcesClick onSerivceResourcesClick) {
        this.onSerivceResourcesClick = onSerivceResourcesClick;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_service_resources_fragment, parent, false);
            holder = new ViewHolder();
            holder.id_service_resources_recyclerview_horizontal = (RecyclerView) convertView.findViewById(R.id.id_service_resources_recyclerview_horizontal);
            holder.id_service_resources_image = (ImageView) convertView
                    .findViewById(R.id.id_service_resources_image);
            holder.tv_service_resources_user_name = (TextView) convertView.findViewById(R.id.tv_service_resources_user_name);
            holder.tv_service_resources_user_profession = (TextView) convertView.findViewById(R.id.tv_service_resources_user_profession);
            holder.iv_service_resources_user_leve = (ImageView) convertView.findViewById(R.id.iv_service_resources_user_leve);
            holder.tv_service_resources_money = (TextView) convertView.findViewById(R.id.tv_service_resources_money);
            holder.tv_service_resources_browse = (TextView) convertView.findViewById(R.id.tv_service_resources_browse);
            holder.tv_service_resources_surplus = (TextView) convertView.findViewById(R.id.tv_service_resources_surplus);
            holder.tv_service_resources_title = (TextView) convertView
                    .findViewById(R.id.tv_service_resources_title);
            holder.id_service_resources_intro = (TextView) convertView
                    .findViewById(R.id.id_service_resources_intro);
            holder.id_service_resources_price = (TextView) convertView
                    .findViewById(R.id.id_service_resources_price);
            holder.id_service_resources_salenum = (TextView) convertView
                    .findViewById(R.id.id_service_resources_salenum);
            holder.id_service_resources_distance = (TextView) convertView
                    .findViewById(R.id.id_service_resources_distance);
            holder.iv_info_verified = (ImageView) convertView.findViewById(R.id.iv_info_verified);
            holder.iv_user_info_credit = (ImageView) convertView.findViewById(R.id.iv_user_info_credit);
            holder.iv_user_info_company = (ImageView) convertView.findViewById(R.id.iv_user_info_company);
            holder.iv_user_info_profession = (ImageView) convertView.findViewById(R.id.iv_user_info_profession);
            holder.tv_service_resources_about_single = (TextView) convertView.findViewById(R.id.tv_service_resources_about_single);
            holder.tv_service_resources_time = (TextView) convertView.findViewById(R.id.tv_service_resources_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        String iconUrl = dataList.get(position).getUserHeadicon();
        if (!TextUtils.isEmpty(iconUrl)) {
            Picasso.with(context)
                    .load(iconUrl)
                    //.resize(45, 45)
                    .placeholder(R.drawable.ic_default)
                    .into(holder.id_service_resources_image);
        }
        holder.tv_service_resources_user_name.setText(dataList.get(position).getNickname() + "・");
        holder.tv_service_resources_user_profession.setText(dataList.get(position).getUserPosition());
        holder.tv_service_resources_money.setText(dataList.get(position).getUserTreasure());


        /**时间设置*/
        holder.tv_service_resources_time.setText(dataList.get(position).getCreateDateFamt());
        if (dataList.get(position).getBindingMark1() == 1) {
            holder.iv_info_verified.setImageResource(R.drawable.iv_user_info_verified);
        } else {
            holder.iv_info_verified.setVisibility(View.GONE);
        }
        if (dataList.get(position).getBindingMark2() == 1) {
            holder.iv_user_info_credit.setImageResource(R.drawable.iv_user_info_credit_light);
        } else {
            holder.iv_user_info_credit.setVisibility(View.GONE);
        }
        if (dataList.get(position).getBindingMark3() == 1) {
            holder.iv_user_info_company.setImageResource(R.drawable.iv_user_info_company_light);
        } else {
            holder.iv_user_info_company.setVisibility(View.GONE);
        }
        if (dataList.get(position).getBindingMark4() == 1) {
            holder.iv_user_info_profession.setImageResource(R.drawable.iv_user_info_profession_light);
        } else {
            holder.iv_user_info_profession.setVisibility(View.GONE);
        }
        /**设置约单  转让 等等*/
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == dataList.get(position).getServeLabel()) {
                serveLabel = dataList.get(position).getServeLabel();
            }
        }
        if (dataList.get(position).getServeLabel() == serveLabel) {
            holder.tv_service_resources_about_single.setText(dataList.get(position).getServeLabelName());
        }
        holder.tv_service_resources_title.setText(dataList.get(position).getProName());
        holder.id_service_resources_intro.setText(dataList.get(position).getProDes());

        List list = new ArrayList();
        for (int i = 0; i < dataList.get(position).getMgList().size(); i++) {
            list.add(dataList.get(position).getMgList().get(i).getMoreGroPrice());
        }
        double maxPrice = ArrayListMax((ArrayList) list);
        double minPrice = ArrayListMin((ArrayList) list);
        if (list.size() != 1) {
            price = minPrice + "~" + maxPrice;
        } else {
            price = dataList.get(position).getMgList().get(0).getMoreGroPrice() + "";
        }

        holder.id_service_resources_price.setText(String.format("%1$s", price));
        String salenum = String.format(
                context.getResources().getString(R.string.home_item_salenum),
                dataList.get(position).getProSale());
        holder.id_service_resources_salenum.setText(salenum);
        String proSurplus = String.format(context.getResources().getString(R.string.home_item_proSurplus), dataList.get(position).getProSurplus());
        holder.tv_service_resources_surplus.setText(proSurplus);
        holder.tv_service_resources_browse.setText(dataList.get(position).getProSeeNum() + "");


        holder.id_service_resources_distance.setText(dataList.get(position).getDistanceKms() + "");

        mDatas = dataList.get(position).getPiList();
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.id_service_resources_recyclerview_horizontal.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new MyGalleryAdapter(context, mDatas);
        holder.id_service_resources_recyclerview_horizontal.setAdapter(mAdapter);
//        holder.id_service_resources_recyclerview_horizontal.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
//                    onSerivceResourcesClick.toDetails(position);
//                }
//                return false;
//            }
//        });
        mAdapter.setOnItemClickLitener(new MyGalleryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int i) {
                onSerivceResourcesClick.toDetails(position);
            }
        });

        return convertView;
    }

    class ViewHolder {
        RecyclerView id_service_resources_recyclerview_horizontal;//图片
        ImageView id_service_resources_image;//头像
        TextView tv_service_resources_user_name;//姓名 要加点
        TextView tv_service_resources_user_profession;//职业
        ImageView iv_service_resources_user_leve;//vip图标
        TextView tv_service_resources_money;//关注数
        ImageView iv_info_verified;//
        ImageView iv_user_info_credit;//
        ImageView iv_user_info_company;//
        ImageView iv_user_info_profession;//
        TextView tv_service_resources_about_single;//约单
        TextView tv_service_resources_time;//时间
        TextView tv_service_resources_title;//Title 商品名称
        TextView tv_service_resources_browse;//查看数
        TextView id_service_resources_distance;//距离
        TextView id_service_resources_intro;//商品描述
        TextView id_service_resources_salenum;//已售[69]
        TextView tv_service_resources_surplus;//仅剩[998]
        TextView id_service_resources_price;//价格


    }

    public interface OnSerivceResourcesClick {
        void toDetails(int i);
    }

    //获取ArrayList中的最大值
    public double ArrayListMax(ArrayList sampleList) {
        try {
            double maxDevation = 0.0;
            int totalCount = sampleList.size();
            if (totalCount >= 1) {
                double max = Double.parseDouble(sampleList.get(0).toString());
                for (int i = 0; i < totalCount; i++) {
                    double temp = Double.parseDouble(sampleList.get(i).toString());
                    if (temp > max) {
                        max = temp;
                    }
                }
                maxDevation = max;
            }
            return maxDevation;
        } catch (Exception ex) {
            throw ex;
        }
    }

    //获取ArrayList中的最小值
    public double ArrayListMin(ArrayList sampleList) {
        try {
            double mixDevation = 0.0;
            int totalCount = sampleList.size();
            if (totalCount >= 1) {
                double min = Double.parseDouble(sampleList.get(0).toString());
                for (int i = 0; i < totalCount; i++) {
                    double temp = Double.parseDouble(sampleList.get(i).toString());
                    if (min > temp) {
                        min = temp;
                    }
                }
                mixDevation = min;
            }
            return mixDevation;
        } catch (Exception ex) {
            throw ex;
        }
    }

}

