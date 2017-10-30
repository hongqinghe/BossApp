package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.LeaveMeassageBean;
import com.android.app.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;

/**
 * $desc留言列表适配器
 * Created by likaihang on 16/09/27.
 */
public class LeaveMeassageAdapter extends BaseAdapter {
    private Context mContext;
    private List<LeaveMeassageBean> list;
    private int size;

    public LeaveMeassageAdapter(Context context, List<LeaveMeassageBean> list,int size) {
        this.mContext = context;
        this.list = list;
        this.size = size;
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.leave_mesage_item, null);
            holder.head = (ImageView) convertView.findViewById(R.id.iv_header);
            holder.responder = (TextView) convertView.findViewById(R.id.tv_user_1);
            holder.commenter = (TextView) convertView.findViewById(R.id.tv_user_2);
            holder.respondtime = (TextView) convertView.findViewById(R.id.tv_reply_time);
            holder.respondcont = (TextView) convertView.findViewById(R.id.tv_leave_message_body);
            holder.res = (TextView) convertView.findViewById(R.id.tv_respon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LeaveMeassageBean data = list.get(position);
        if (data.getCrStatus().equals("1")){
            Picasso.with(mContext).load(data.getCrNickIcon())
                    .resize(30,30)
                    .placeholder(R.drawable.iv_header_def)
                    .into(holder.head);
            holder.res.setVisibility(View.GONE);
            holder.commenter.setVisibility(View.GONE);
            holder.responder.setText(data.getCrNickName());
            holder.respondcont.setText(data.getCrContent());
            try {
                holder.respondtime.setText(DateUtils.longtotime(data.getCrDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            Picasso.with(mContext).load(data.getCrNickIcon()).placeholder(R.drawable.iv_header_def).into(holder.head);
            holder.res.setVisibility(View.VISIBLE);
            holder.commenter.setVisibility(View.VISIBLE);
            holder.responder.setText(data.getCrNickName());
            holder.commenter.setText(data.getObjNickName());
            try {
                holder.respondtime.setText(DateUtils.longtotime(data.getCrDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.respondcont.setText(data.getCrContent());
        }

      /*  if (!TextUtils.isEmpty(data.getReplyNickname())){

        }else{

        }*/

        return convertView;
    }

    class ViewHolder {
        ImageView head;
        TextView responder;
        TextView commenter;
        TextView respondtime;
        TextView respondcont;
        TextView res;
    }
}
