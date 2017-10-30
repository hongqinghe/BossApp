package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.RefundBackBean;
import com.android.app.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shangshuaibo on 2016/12/16 18:59
 */
public class RefundInfoAdapter extends BaseAdapter {
    private Context context;
    private int userStatus;//0买家 1卖家
    private List<RefundBackBean.BeanBean> refundList = new ArrayList<>();
    private OnRefundBtnClickListener onRefundBtnClickListener;

    public RefundInfoAdapter(Context context, List<RefundBackBean.BeanBean> refundList, int
            userStatus) {
        this.context = context;
        this.refundList = refundList;
        this.userStatus = userStatus;
    }

    @Override
    public int getCount() {
        return (refundList == null) ? 0 : refundList.size();
    }

    @Override
    public Object getItem(int position) {
        return refundList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.refund_info_item, null);
            holder.ll_refund_state = (LinearLayout) convertView.findViewById(R.id.ll_refund_state);
            holder.refund_state = (TextView) convertView.findViewById(R.id.refund_state);
            holder.refund_state_content = (TextView) convertView.findViewById(R.id
                    .refund_state_content);
            holder.ll_refund_content = (LinearLayout) convertView.findViewById(R.id
                    .ll_refund_content);
            holder.refund_timer = (TextView) convertView.findViewById(R.id.refund_timer);
            holder.refund_cause = (TextView) convertView.findViewById(R.id.refund_cause);
            holder.ll_refund_image = (LinearLayout) convertView.findViewById(R.id.ll_refund_image);
            holder.ll_refund_btn = (LinearLayout) convertView.findViewById(R.id.ll_refund_btn);
            holder.btn_cancel = (TextView) convertView.findViewById(R.id.btn_cancel);
            holder.btn_confirm = (TextView) convertView.findViewById(R.id.btn_confirm);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RefundBackBean.BeanBean refundBean = refundList.get(position);
        List<RefundBackBean.OrderFeedbackImageBean> imageList = refundBean.getImage();
        final int style = refundBean.getStyle();
        final int isClick = refundBean.getIsClick();
        if (style == 4 || style == 5 || style == 6) {//退款提交提示
            holder.ll_refund_state.setVisibility(View.VISIBLE);
            holder.ll_refund_content.setVisibility(View.GONE);
            holder.refund_state.setText(refundBean.getStatus());
            holder.refund_state_content.setText(refundBean.getFeedMage());
        } else if (userStatus == 0) {
            holder.ll_refund_content.setVisibility(View.VISIBLE);
            holder.ll_refund_state.setVisibility(View.GONE);
            holder.refund_timer.setText(refundBean.getRemainingTime());
            holder.refund_cause.setText(refundBean.getFeedbackWhy());
            if (style == 1) {
                holder.ll_refund_btn.setVisibility(View.GONE);
            } else if (style == 2) {
                holder.btn_cancel.setText("聊一聊");
                holder.btn_confirm.setText("申请申诉");
//                holder.ll_refund_content.setBackgroundColor(Color.parseColor("#cee2ff"));
                holder.ll_refund_content.setBackgroundResource(R.drawable.refund_shap_blue);
            } else if (style == 3) {
                holder.ll_refund_btn.setVisibility(View.GONE);
            }
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            holder.ll_refund_image.measure(w, h);
            if (imageList.size() > 0) {
                holder.ll_refund_image.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Util.dip2px(context,
                        90), Util.dip2px(context, 90));
                lp.rightMargin = 10;

                //添加图片的时候把布局制空，要么会出现布局复用的情况
                holder.ll_refund_image.removeAllViews();
                for (int i = 0; i < imageList.size(); i++) {
                    if (!TextUtils.isEmpty(imageList.get(i).getFeedbackImageMin())) {
                        ImageView img = new ImageView(context);
                        //设置网络图片
                        Picasso.with(context).load(imageList.get(i).getFeedbackImageMin())
                                .placeholder(R.drawable.ic_default)
                                .error(R.drawable.ic_default).into(img);
                        img.setLayoutParams(lp);
                        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        holder.ll_refund_image.addView(img);
                    }
                }
            }
            holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRefundBtnClickListener.onRefundBtnClick(holder.btn_cancel, userStatus, style ,isClick);
                }
            });
            holder.btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRefundBtnClickListener.onRefundBtnClick(holder.btn_confirm, userStatus,
                            style,isClick);
                }
            });

        } else {
            holder.ll_refund_content.setVisibility(View.VISIBLE);
            holder.ll_refund_state.setVisibility(View.GONE);
            holder.refund_timer.setText(refundBean.getRemainingTime());
            holder.refund_cause.setText(refundBean.getFeedbackWhy());
            if (style == 1) {
                holder.ll_refund_content.setBackgroundResource(R.drawable.refund_shap_blue);
                    holder.btn_cancel.setText("同意退款");
                    holder.btn_confirm.setText("拒绝退款");
            } else if (style == 2) {
                holder.ll_refund_btn.setVisibility(View.GONE);
            } else if (style == 3) {
                holder.ll_refund_content.setBackgroundResource(R.drawable.refund_shap_blue);
                holder.ll_refund_btn.setVisibility(View.GONE);
            }

            if (imageList.size() > 0) {
                holder.ll_refund_image.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Util.dip2px(context,
                        90), Util.dip2px(context, 90));
                lp.rightMargin = 10;

                //添加图片的时候把布局制空，要么会出现布局复用的情况
                holder.ll_refund_image.removeAllViews();
                for (int i = 0; i < imageList.size(); i++) {
                    if (!TextUtils.isEmpty(imageList.get(i).getFeedbackImageMin())) {
                        ImageView img = new ImageView(context);
                        //设置网络图片
                        Picasso.with(context).load(imageList.get(i).getFeedbackImageMin())
                                .placeholder(R.drawable.ic_default)
                                .error(R.drawable.ic_default).into(img);
                        img.setLayoutParams(lp);
                        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        holder.ll_refund_image.addView(img);
                    }
                }
            }

            holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRefundBtnClickListener.onRefundBtnClick(holder.btn_cancel, userStatus, style ,isClick);
                }
            });
            holder.btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRefundBtnClickListener.onRefundBtnClick(holder.btn_confirm, userStatus,
                            style ,isClick);
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        LinearLayout ll_refund_state;//退款状态布局
        TextView refund_state;//退款状态
        TextView refund_state_content;//退款状态内容
        LinearLayout ll_refund_content;//退款过程的内容
        TextView refund_timer;//退款倒计时
        TextView refund_cause;//退款理由
        LinearLayout ll_refund_image;//退款图片
        LinearLayout ll_refund_btn;//退款操作按钮的布局
        TextView btn_cancel;//取消退款
        TextView btn_confirm;//提起申诉
    }

    public interface OnRefundBtnClickListener {
        void onRefundBtnClick(View view, int userStatus, int style ,int isClick);
    }

    public void setOnRefundBtnClickListener(OnRefundBtnClickListener onRefundBtnClickListener) {
        this.onRefundBtnClickListener = onRefundBtnClickListener;
    }
}
