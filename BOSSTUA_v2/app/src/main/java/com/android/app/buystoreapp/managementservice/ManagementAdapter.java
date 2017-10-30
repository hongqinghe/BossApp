package com.android.app.buystoreapp.managementservice;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.ProductBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 管理服务碎片的Item
 * <p/>
 * Created by Administrator on 2016/9/27.
 */
public class ManagementAdapter extends BaseAdapter {
    private Context context;
    private List<ProductBean.GpbpsListBean> gpbpsLists;
    private OnClickInterface onClickInterface;
    private int refreshCount;
    private int userLevelMark;
    private int remainingRecommended;

    public ManagementAdapter(Context context, List<ProductBean.GpbpsListBean> gpbpsLists, int
            refreshCount, int userLevelMark, int remainingRecommended) {
        this.context = context;
        this.gpbpsLists = gpbpsLists;
        this.refreshCount = refreshCount;
        this.userLevelMark = userLevelMark;
        this.remainingRecommended = remainingRecommended;
    }

    public void setOnClickInterface(OnClickInterface onClickInterface) {
        this.onClickInterface = onClickInterface;
    }

    @Override
    public int getCount() {
        return gpbpsLists.size();
    }

    @Override
    public Object getItem(int position) {
        return gpbpsLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.management_fragmen_item,
                    null);
            holder = new ViewHolder();
            holder.ll_managment = (LinearLayout) convertView.findViewById(R.id.ll_managment);
            holder.iv_management_photo = (ImageView) convertView.findViewById(R.id
                    .iv_management_photo);

            holder.tv_management_unit_price = (TextView) convertView.findViewById(R.id
                    .tv_management_unit_price);
            holder.tv_management_sold_orders = (TextView) convertView.findViewById(R.id
                    .tv_management_sold_orders);
            holder.tv_management_remaining_orders = (TextView) convertView.findViewById(R.id
                    .tv_management_remaining_orders);
            holder.tv_management_title = (TextView) convertView.findViewById(R.id
                    .tv_management_title);

            holder.btn_mangement_recommend = (Button) convertView.findViewById(R.id
                    .btn_mangement_recommend);
            holder.btn_mangement_uping = (Button) convertView.findViewById(R.id
                    .btn_mangement_uping);
            holder.btn_mangement_edit = (Button) convertView.findViewById(R.id.btn_mangement_edit);
            holder.btn_mangement_up_or_down = (Button) convertView.findViewById(R.id
                    .btn_mangement_up_or_down);
            holder.btn_mangement_delete = (Button) convertView.findViewById(R.id
                    .btn_mangement_delete);
            holder.btn_mangement_share = (Button) convertView.findViewById(R.id
                    .btn_mangement_share);
            holder.btn_mangement_freshen = (Button) convertView.findViewById(R.id
                    .btn_mangement_freshen);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductBean.GpbpsListBean gpbpsList = gpbpsLists.get(position);
        if (gpbpsList.getProStatus() == 0) {
            holder.btn_mangement_uping.setText("已下架");
            holder.btn_mangement_uping.setBackgroundResource(R.drawable.management_down);
            holder.btn_mangement_up_or_down.setText("上架");
            holder.btn_mangement_recommend.setVisibility(View.INVISIBLE);
            holder.btn_mangement_freshen.setVisibility(View.GONE);
            holder.btn_mangement_edit.setVisibility(View.GONE);
            holder.btn_mangement_delete.setVisibility(View.VISIBLE);
        }

        //刷新
        if (refreshCount > 0 && userLevelMark > 0) {
            holder.btn_mangement_freshen.setBackgroundResource(R.drawable
                    .management_btn_press_blue);
            holder.btn_mangement_freshen.setTextColor(Color.parseColor("#168fef"));
        } else {
            holder.btn_mangement_freshen.setBackgroundResource(R.drawable
                    .management_btn_press_hui);
            holder.btn_mangement_freshen.setTextColor(Color.parseColor("#404040"));
        }

        //推荐
        if (userLevelMark > 2 && remainingRecommended > 0) {
            holder.btn_mangement_recommend.setBackgroundResource(R.drawable
                    .management_btn_press_blue);
            holder.btn_mangement_recommend.setTextColor(Color.parseColor("#168fef"));
        } else {
            holder.btn_mangement_recommend.setBackgroundResource(R.drawable
                    .management_btn_press_hui);
            holder.btn_mangement_recommend.setTextColor(Color.parseColor("#404040"));
        }

        if (gpbpsList.getIsRecommend() == 0) {
            holder.btn_mangement_recommend.setText("推荐");
        } else {
            holder.btn_mangement_recommend.setText("取消推荐");
        }

        String uri = gpbpsList.getProCoverImag();
        if (!TextUtils.isEmpty(uri)) {
            Picasso.with(context).load(uri)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default).into(holder.iv_management_photo);
        }

        holder.tv_management_unit_price.setText(gpbpsList.getProductPrice());//单价
        holder.tv_management_sold_orders.setText(context.getResources().getString(R.string
                .proSale, gpbpsList.getProSale()));//已售
        holder.tv_management_remaining_orders.setText(context.getResources().getString(R.string
                .proSurplus, gpbpsList.getProSurplus()));//仅剩
        holder.tv_management_title.setText(gpbpsList.getProName());//标题

        holder.btn_mangement_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.doClic(holder.btn_mangement_recommend, position);
            }
        });

        holder.btn_mangement_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.doClic(holder.btn_mangement_edit, position);
            }
        });

        holder.btn_mangement_up_or_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.doClic(holder.btn_mangement_up_or_down, position);
            }
        });
        holder.btn_mangement_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.doClic(holder.btn_mangement_delete, position);
            }
        });
        holder.btn_mangement_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.doClic(holder.btn_mangement_share, position);
            }
        });
        holder.btn_mangement_freshen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.doClic(holder.btn_mangement_freshen, position);
            }
        });
        holder.ll_managment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.doClic(holder.ll_managment, position);
            }
        });
        return convertView;
    }

    class ViewHolder {

        ImageView iv_management_photo;

        LinearLayout ll_managment;

        TextView tv_management_title, tv_management_unit_price, tv_management_sold_orders,
                tv_management_remaining_orders;

        Button btn_mangement_uping, btn_mangement_edit, btn_mangement_up_or_down,
                btn_mangement_delete, btn_mangement_share, btn_mangement_recommend,
                btn_mangement_freshen;
    }

    public interface OnClickInterface {
        /**
         *
         */
        void doClic(View view, int i);
    }
}
