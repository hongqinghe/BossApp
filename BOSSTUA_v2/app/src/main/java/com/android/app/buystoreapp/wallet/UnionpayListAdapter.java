package com.android.app.buystoreapp.wallet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.app.buystoreapp.R;

import java.util.List;

/**
 * Created by 尚帅波 on 2016/9/19.
 */
public class UnionpayListAdapter extends BaseAdapter {
    private Context context;
    private List<UnionpayInfoBeen> datas;

    public UnionpayListAdapter(Context context, List<UnionpayInfoBeen> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return (datas == null) ? 0 : datas.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.unionpay_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.radioButton = (RadioButton) convertView.findViewById(R.id.radioBtn);
            viewHolder.tv_unionpayInfo = (TextView) convertView.findViewById(R.id.tv_unionpayInfo);
            viewHolder.tv_other = (TextView) convertView.findViewById(R.id.tv_other);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        UnionpayInfoBeen data = datas.get(position);
        viewHolder.tv_unionpayInfo.setText(data.getUnionpayName());
        viewHolder.tv_other.setText(data.getOther());

        viewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重置，确保最多只有一项被选中
                for (UnionpayInfoBeen item : datas) {
                    item.setCheck(false);
                }
                datas.get(position).setCheck(true);
                UnionpayListAdapter.this.notifyDataSetChanged();
            }
        });

        viewHolder.radioButton.setChecked(data.isCheck());
        return convertView;
    }

    class ViewHolder {
        RadioButton radioButton;
        TextView tv_unionpayInfo, tv_other;
    }
}
