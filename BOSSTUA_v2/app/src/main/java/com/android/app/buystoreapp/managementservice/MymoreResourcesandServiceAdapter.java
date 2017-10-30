package com.android.app.buystoreapp.managementservice;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.ImgListBean;
import com.android.app.buystoreapp.bean.UserAllProductBean;

import java.util.List;


public class MymoreResourcesandServiceAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<UserAllProductBean> allProductList;
    private Context context;
    private MyMoreRVAdapter mAdapter;
    private OnMyMoreClick onMyMoreClick;

    public void setOnMyMoreClick(OnMyMoreClick onMyMoreClick) {
        this.onMyMoreClick = onMyMoreClick;
    }

    public MymoreResourcesandServiceAdapter(Context context, List<UserAllProductBean> allProductList) {
        this.allProductList = allProductList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (allProductList == null) ? 0 : allProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return allProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_more_resourcesand_service, null);
            holder = new ViewHolder();
            holder.mRecyclerView = (RecyclerView) convertView.findViewById(R.id
                    .id_home_item_recyclerview_horizontal);
            holder.tv_browse = (TextView) convertView.findViewById(R.id.tv_browse);
            holder.tv_goods_surplus = (TextView) convertView.findViewById(R.id.tv_goods_surplus);
            holder.tv_more_resoucesand_about_single = (TextView) convertView.findViewById(R.id
                    .tv_more_resoucesand_about_single);
            holder.tv_moreGroUnit = (TextView) convertView.findViewById(R.id.tv_moreGroUnit);
            holder.id_service_item_title = (TextView) convertView
                    .findViewById(R.id.id_service_item_title);
            holder.id_service_item_intro = (TextView) convertView
                    .findViewById(R.id.id_service_item_intro);
            holder.id_service_item_price = (TextView) convertView
                    .findViewById(R.id.id_service_item_price);
            holder.id_service_item_salenum = (TextView) convertView
                    .findViewById(R.id.id_service_item_salenum);
            holder.id_service_item_distance = (TextView) convertView
                    .findViewById(R.id.id_service_item_distance);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UserAllProductBean allProduct = allProductList.get
                (position);
        holder.id_service_item_title.setText(allProduct.getProName());
        holder.id_service_item_intro.setText(allProduct.getProDes());
        holder.id_service_item_price.setText(allProduct.getMoreGroPrice());
        holder.tv_browse.setText(allProduct.getProSeeNum());
        holder.id_service_item_salenum.setText(context.getResources().getString(R.string
                .proSale, allProduct.getProSale()));
        holder.tv_goods_surplus.setText(context.getResources().getString(R.string.proSurplus,
                allProduct.getProSurplus()));
        holder.id_service_item_distance.setText(allProduct.getDistance());
        String unit = allProduct.getMoreGroUnit();
        if (unit.equals("")){
            unit = "单";
        }
        holder.tv_moreGroUnit.setText(String.format("元/%1$s", unit));
        holder.tv_more_resoucesand_about_single.setText(allProduct.getServeLabelName() + "・");

        final List<ImgListBean> imgList =
                allProduct.getImgList();
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new MyMoreRVAdapter(context, imgList);
        holder.mRecyclerView.setAdapter(mAdapter);
//        holder.mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    onMyMoreClick.toDetailClick(position);
//                }
//                return false;
//            }
//        });
        mAdapter.setOnItemClickListener(new MyMoreRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                onMyMoreClick.toDetailClick(position);
            }
        });

        return convertView;
    }


    class ViewHolder {
        TextView tv_more_resoucesand_about_single;   //类型
        TextView id_service_item_title;   //商品名称
        TextView id_service_item_intro;    //商品描述
        TextView id_service_item_price;   //商品价格区间
        TextView tv_moreGroUnit;   //单位
        TextView tv_browse;   //浏览量
        TextView id_service_item_salenum;  //已售
        TextView tv_goods_surplus;  //仅剩
        TextView id_service_item_distance;  //距离
        RecyclerView mRecyclerView;
    }

    public interface OnMyMoreClick {
        void toDetailClick(int i);
    }

}
