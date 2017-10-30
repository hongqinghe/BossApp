package com.android.app.buystoreapp.managementservice;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.app.buystoreapp.R;

import java.util.List;

/**
 * 街道列表adapter
 * weilin
 * Created by Administrator on 2016/10/12.
 */
public class MyChoiceListAdapter extends BaseAdapter {
    private Context ctx;
    private List<String> list;

    public MyChoiceListAdapter(Context context, List<String> provinceLists) {
        this.ctx = context;
        this.list = provinceLists;
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(ctx, R.layout.listview_item, null);
            holder.poititle = (TextView) convertView
                    .findViewById(R.id.gridview_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.poititle.setText(list.get(position));
        return convertView;
    }

    private class ViewHolder {
        TextView poititle;
    }
}

