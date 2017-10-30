package com.android.app.buystoreapp.adapter;

import java.util.HashMap;
import java.util.List;

import com.android.app.buystoreapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<HashMap<String, Object>> mDatas;

    public MessageAdapter(Context context, List<HashMap<String, Object>> data) {
        mInflater = LayoutInflater.from(context);
        mDatas = data;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.message_layout, null);
            holder = new ViewHolder();
            holder.content = (TextView) convertView.findViewById(R.id.id_personal_message_content);
            holder.date = (TextView) convertView.findViewById(R.id.id_personal_message_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.content.setText(mDatas.get(position).get("messageContent").toString());
        
        return convertView;
    }
    
    class ViewHolder {
        TextView content;
        TextView date;
    }

}
