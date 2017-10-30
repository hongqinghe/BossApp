package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.android.app.buystoreapp.R;

import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/10/04.
 */
public class AddressListAdapter extends BaseAdapter{
    private Context ctx;
    private List<PoiItem> list;
    public AddressListAdapter(Context context, List<PoiItem> poiList) {
        this.ctx = context;
        this.list = poiList;
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


//        SubPoiItem item = list.get(position);
        String item = list.get(position).toString();
        holder.poititle.setText(item);
        return convertView;
    }
    private class ViewHolder {
        TextView poititle;
    }
}
