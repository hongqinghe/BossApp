package com.android.app.buystoreapp.managementservice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.ImgListBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 尚帅波 on 2016/10/19.
 */
public class MyMoreRVAdapter extends RecyclerView.Adapter<MyMoreRVAdapter.ViewHolder> {
    Context context;
    List<ImgListBean> imgList;
    OnItemClickListener onItemClickListener;

    public MyMoreRVAdapter(Context context, List<ImgListBean> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.gallery_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.iv = (ImageView) view.findViewById(R.id.id_gallery_item_image);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        ViewGroup.LayoutParams params = viewHolder.iv.getLayoutParams();
//        viewHolder.mImg.setImageResource(mDatas.get(i));
        Picasso.with(context).load(imgList.get(i).getProImageMin()).resize(params.width, params.height).placeholder(R.drawable.ic_default).into(viewHolder.iv);
        //如果设置了回调，则设置点击事件
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(viewHolder.iv, i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (imgList == null) ? 0 : imgList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        ImageView iv;
    }
}
