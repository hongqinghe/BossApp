package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PersonalAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<HashMap<String, Object>> mDataList;

    public PersonalAdapter(Context context,
            ArrayList<HashMap<String, Object>> data) {
        mInflater = LayoutInflater.from(context);
        mDataList = data;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mDataList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.personal_item, null);
            holder.icon = (ImageView) convertView.findViewById(R.id.id_personal_item_image);
            holder.title = (TextView) convertView.findViewById(R.id.id_personal_item_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.icon.setImageResource(Integer.valueOf(mDataList.get(position).get("imageUrl").toString()));
        holder.title.setText(mDataList.get(position).get("title").toString());
        return convertView;
    }
    
    class ViewHolder {
        ImageView icon;
        TextView title;
    }
}
