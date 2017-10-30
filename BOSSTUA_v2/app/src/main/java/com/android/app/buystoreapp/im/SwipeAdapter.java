
package com.android.app.buystoreapp.im;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.LeaveList;
import com.android.app.buystoreapp.bean.OrderList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SwipeAdapter<T> extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context mContext = null;

    /**
     *
     */
    private int mRightWidth = 0;

    /**
     * 单击事件监听器
     */
    private IOnItemRightClickListener mListener = null;

    private List<T> mList = new ArrayList<T>();
    private int state;

    public interface IOnItemRightClickListener {
        void onRightClick(View v, int position);
    }
    public SwipeAdapter(Context context, int state, List<T> list, int rightWidth, IOnItemRightClickListener l) {
        mContext = context;
        mRightWidth = rightWidth;
        mListener = l;
        this.mList = list;
        this.state = state;
    }

    public void updateListView(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {

        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder item;
        final int thisPosition = position;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
            item = new ViewHolder();
            item.item_left = convertView.findViewById(R.id.item_left);
            item.item_right = convertView.findViewById(R.id.item_right);
//            item.item_left_txt = (TextView)convertView.findViewById(R.id.item_left_txt);
            item.item_right_txt = (TextView) convertView.findViewById(R.id.item_right_txt);
            item.iv_message_head = (ImageView) convertView.findViewById(R.id.iv_message_head);
            item.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            item.tv_message_time = (TextView) convertView.findViewById(R.id.tv_message_time);
            item.tv_message_context = (TextView) convertView.findViewById(R.id.tv_message_context);
            convertView.setTag(item);
        } else {// 有直接获得ViewHolder
            item = (ViewHolder) convertView.getTag();
        }
        LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        item.item_left.setLayoutParams(lp1);
        LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
        item.item_right.setLayoutParams(lp2);
//        item.item_left_txt.setText("item " + thisPosition);
        item.item_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRightClick(v, thisPosition);
                }
            }
        });
        if (state == 1) {
            OrderList date = (OrderList) mList.get(position);
            item.tv_title.setText(date.getNickname());
            item.tv_message_time.setText(date.getFormatTime());
            item.tv_message_context.setText(date.getMessage());
            Picasso.with(mContext).load(date.getImage())
                    //.resize(50,50)
                    .placeholder(R.drawable.ic_default).into(item.iv_message_head);
        } else if (state == 2) {
            LeaveList date = (LeaveList) mList.get(position);
            item.tv_title.setText(date.getTitle());
            item.tv_message_time.setText(date.getFormatTime());
            item.tv_message_context.setText(date.getContent());
            Picasso.with(mContext).load(date.getImage())
                    //.resize(50,50)
                    .placeholder(R.drawable.ic_default).into(item.iv_message_head);
        } else {
            //系统消息
        }

        return convertView;
    }

    private class ViewHolder {
        View item_left;
        View item_right;
        TextView item_right_txt;
        ImageView iv_message_head;
        TextView tv_title;
        TextView tv_message_time;
        TextView tv_message_context;
    }
}
