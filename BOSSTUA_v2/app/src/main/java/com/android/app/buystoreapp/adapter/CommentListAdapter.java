package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.GsonCommentBean;
import com.android.app.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 评价列表适配器
 * Created by likaihang on 16/09/13. alter by shangshuaibo on 16/10/07.
 */
public class CommentListAdapter extends BaseAdapter {
    private final LayoutInflater mInflater;
    private Context context;
    private List<GsonCommentBean.CommodityEvalList> datas;

    public CommentListAdapter(Context context, List<GsonCommentBean.CommodityEvalList> list) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.datas = list;
    }

    @Override
    public int getCount() {
        return (datas.size() == 0) ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.comment_list_item, null);
            holder.userIcon = (ImageView) convertView.findViewById(R.id.userIcon);
            holder.commentName = (TextView) convertView.findViewById(R.id.commentName);
            holder.commentDate = (TextView) convertView.findViewById(R.id.commentDate);
            holder.commentContent = (TextView) convertView.findViewById(R.id.commentContent);
            holder.imageContainer = (LinearLayout) convertView.findViewById(R.id.imageContainer);
            holder.replayContainer = (LinearLayout) convertView.findViewById(R.id.replayContainer);
            holder.payDate = (TextView) convertView.findViewById(R.id.payDate);
            holder.proName = (TextView) convertView.findViewById(R.id.proName);
            holder.moreName = (TextView) convertView.findViewById(R.id.moreName);
            holder.replyName = (TextView) convertView.findViewById(R.id.replyName);
            holder.replyContent = (TextView) convertView.findViewById(R.id.replyContent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GsonCommentBean.CommodityEvalList commodityEvalListBean = datas.get(position);

        if (!TextUtils.isEmpty(commodityEvalListBean.getUserIcon())) {
            Picasso.with(context).load(commodityEvalListBean.getUserIcon())
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default).into(holder.userIcon);
        } else {
            Picasso.with(context).load(R.drawable.ic_default).into(holder.userIcon);
        }
        //判断是否匿名评价
        if (commodityEvalListBean.getEvalAnonymity().equals("0")) {
            holder.commentName.setText("匿名评价");
        } else {
            holder.commentName.setText(commodityEvalListBean.getEvalUserNickname());
        }
        //holder.userIcon
        holder.commentContent.setText(commodityEvalListBean.getEvalContent());
        holder.commentDate.setText(commodityEvalListBean.getEvalDate());
        holder.payDate.setText(commodityEvalListBean.getPayDate());
        holder.proName.setText(commodityEvalListBean.getProName());
        //如果没有组合名称，显示商品标题
        if (TextUtils.isEmpty(commodityEvalListBean.getMoreName())) {
            holder.moreName.setText(commodityEvalListBean.getProName());
        } else {
            holder.moreName.setText(commodityEvalListBean.getMoreName());
        }
        //判断是否有图评价  如果有的话动态的向布局中添加图片
        List<GsonCommentBean.CommodityEvalList.ListimgBean> listimgBeans = new
                ArrayList<GsonCommentBean.CommodityEvalList.ListimgBean>();
        listimgBeans = commodityEvalListBean.getListimg();

        if (listimgBeans.size() > 0) {
            holder.imageContainer.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                    .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(Util.dip2px(context, 90), Util.dip2px(context, 90));
          /*  LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                    .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/
            lp.topMargin = 30;
            lp.bottomMargin = 30;
            lp2.rightMargin = 20;
          /*  lp2.width = 80;
            lp2.height = 80;*/
            holder.imageContainer.setLayoutParams(lp);

            //添加图片的时候把布局制空，要么会出现布局复用的情况
            holder.imageContainer.removeAllViews();
            for (int i = 0; i < listimgBeans.size(); i++) {
                if (!TextUtils.isEmpty(listimgBeans.get(i).getWebrootpath())) {
                    ImageView img = new ImageView(context);
                    //设置网络图片
                    Picasso.with(context).load(listimgBeans.get(i).getWebrootpath())
                            .placeholder(R.drawable.ic_default)
                            .error(R.drawable.ic_default).into(img);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img.setLayoutParams(lp2);
                    holder.imageContainer.addView(img);
                }
            }
        } /*else {
            //模拟如果没有图片的显示情况
            holder.imageContainer.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                    .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                    .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = 30;
            lp.bottomMargin = 30;
            lp2.rightMargin = 20;
            holder.imageContainer.setLayoutParams(lp);

            //添加图片的时候把布局制空，要么会出现布局复用的情况
            holder.imageContainer.removeAllViews();
            for (int i = 0; i < 3; i++) {
                ImageView img = new ImageView(context);
                //设置本地默认图片
                img.setImageResource(R.drawable.ic_default);
                img.setLayoutParams(lp2);
                holder.imageContainer.addView(img);
            }
        }*/
        if (!TextUtils.isEmpty(commodityEvalListBean.getReplyName())) {
            holder.replayContainer.setVisibility(View.VISIBLE);
            holder.replyName.setText(commodityEvalListBean.getReplyName());
            holder.replyContent.setText(commodityEvalListBean.getReplyContent());
        }
        return convertView;
    }

    static class ViewHolder {
        TextView payDate, proName, moreName, commentName, commentDate, commentContent,
                replyName, replyContent;
        LinearLayout imageContainer, replayContainer;
        ImageView userIcon;
    }
}
