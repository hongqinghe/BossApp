package com.android.app.buystoreapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.app.buystoreapp.R;

import java.util.ArrayList;

/**
 * $desc
 * Created by likaihang on 16/10/23.
 */
public class CustomGridview extends LinearLayout {
    private ArrayList<?> allDataList, selDataList;
    private GridViewAdapter gridViewAdapter;

    public CustomGridview(Context context) {
        super(context);
        initView(context);
    }

    public CustomGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomGridview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context mContext) {
        this.setOrientation(LinearLayout.VERTICAL);

        /**很简单的自定义View也不算自定义吧 就是简单的封装了下*/
        GridView gridView = new GridView(mContext);
        gridView.setNumColumns(4);
        gridViewAdapter = new GridViewAdapter(mContext);
        gridView.setAdapter(gridViewAdapter);
        addView(gridView);
    }

    /**
     * 刷新界面
     */
    public void  setNotifyDataSetChanged() {
        if (gridViewAdapter != null) {
            gridViewAdapter.notifyDataSetChanged();
        }
    }

    /***
     * 删除所有选中的列
     */
    public void delSelData() {
        if (selDataList != null) {
            selDataList.clear();
            setNotifyDataSetChanged();
        }
    }

    /***
     * 设置所有的数据
     */
    public void setAllData(ArrayList<?> list) {
        this.allDataList = list;
    }

    /***
     * 设置选中的数据
     */
    public void setSelData(ArrayList<?> list) {
        this.selDataList = list;
    }

    private class GridViewAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public GridViewAdapter(Context mContext) {
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return allDataList != null ? allDataList.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return allDataList != null ? allDataList.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.filter_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            /**判断选中的ArrayList 中有没有当前点击选中的对象*/
            if (selDataList.contains(allDataList.get(position))) {
                viewHolder.btn.setChecked(true);
            } else {
                viewHolder.btn.setChecked(false);
            }
            viewHolder.btn.setText(allDataList.get(position).toString());
            /**设置点击事件*/
            viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSelClickListener != null) {
                        onSelClickListener.onSelListener(v, position);
                    }
                }
            });
            return convertView;
        }

        private class ViewHolder {
            CheckBox btn;

            public ViewHolder(View view) {
                btn = (CheckBox) view.findViewById(R.id.tb_filter_transaction_any);
            }
        }
    }

    private OnSelClickListener onSelClickListener;

    public void setOnSelClickListener(OnSelClickListener onSelClickListener) {
        this.onSelClickListener = onSelClickListener;
    }
    /**定义接口封装 点击事件的判断处理放在需要实现的界面中去实现或者处理*/
    public interface OnSelClickListener {
        void onSelListener(View v, int position);
    }
}