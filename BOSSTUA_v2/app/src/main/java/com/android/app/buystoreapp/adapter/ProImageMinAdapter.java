package com.android.app.buystoreapp.adapter;

/**
 * Created by Administrator on 16/08/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.ProImageMinBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProImageMinAdapter extends
        RecyclerView.Adapter<ProImageMinAdapter.ViewHolder> {

    /**
     * ItemClick的回调接口
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private Context context;
    private LayoutInflater mInflater;
    private List<ProImageMinBean> mDatas = new ArrayList<ProImageMinBean>();

    public ProImageMinAdapter(Context context, List<ProImageMinBean> datats) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView mImg;
        TextView mTxt;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
//        return 9;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.my_gallery_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.id_gallery_item_image);
        viewHolder.mImg.setBackgroundResource(R.drawable.ic_about_boss);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
//        viewHolder.mImg.setImageResource(mDatas.get(i));
        Picasso.with(context).load(mDatas.get(i).getProImageMin()).placeholder(R.drawable.ic_default).into(viewHolder.mImg);
        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, i);
                }
            });

        }

    }

}
