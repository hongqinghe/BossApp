package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.ShopDetailImage;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/09/27.
 */
public class BigImageRecyclerAdapter extends RecyclerView.Adapter<BigImageRecyclerAdapter.ViewHolder> {
    private List<ShopDetailImage> list;
    private Context mContext;
    /**
     * ItemClick的回调接口
     *
     */
    public interface OnItemClickLitener
    {
        void onItemClick(View view);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public BigImageRecyclerAdapter(Context context, List<ShopDetailImage> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.big_image_list_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.imageView = (ImageView) v.findViewById(R.id.iv_big_image_item);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Picasso.with(mContext).load(list.get(position).getProImageUrl()).placeholder(R.drawable.ic_pic_big).into(holder.imageView);
        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.imageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickLitener.onItemClick(holder.imageView);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
