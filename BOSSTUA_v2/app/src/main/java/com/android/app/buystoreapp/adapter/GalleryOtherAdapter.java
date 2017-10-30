package com.android.app.buystoreapp.adapter;

/**
 * Created by Administrator on 16/08/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.GuessLikeBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryOtherAdapter extends
        RecyclerView.Adapter<GalleryOtherAdapter.ViewHolder> {

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
    private List<GuessLikeBean.LikeProBean> mDatas;

    public GalleryOtherAdapter(Context context, List<GuessLikeBean.LikeProBean> datats) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = datats;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView mImg;
        TextView mTxt;
        TextView mPrice;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.goods_detail_gallery_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.id_index_gallery_item_image);
        viewHolder.mTxt = (TextView) view.findViewById(R.id.id_index_gallery_item_text);
        viewHolder.mPrice = (TextView) view.findViewById(R.id.id_gallery_item_money);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Log.e("--ImageResourse--", "==" + mDatas.size());
//        viewHolder.mImg.setImageResource(mDatas.get(i));
        viewHolder.mTxt.setText(mDatas.get(i).getProName());
        viewHolder.mPrice.setText(mDatas.get(i).getPrice());
        Picasso.with(context).load(mDatas.get(i).getProImageUrl()).placeholder(R.drawable
                .ic_default).into(viewHolder.mImg);
        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, i);
                }
            });

        }

    }

}
