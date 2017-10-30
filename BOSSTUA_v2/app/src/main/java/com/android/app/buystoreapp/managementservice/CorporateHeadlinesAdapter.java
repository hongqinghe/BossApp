package com.android.app.buystoreapp.managementservice;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.adapter.MyGalleryAdapter;
import com.android.app.buystoreapp.bean.UcnbListBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业头条的adapter
 * <p/>
 * weilin
 */

public class CorporateHeadlinesAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<UcnbListBean> dataList = new ArrayList<UcnbListBean>();
    private Context context;
    private boolean mIsDisapperDistance;
    private MyGalleryAdapter mAdapter;
    private OnClickfollowlister mOnClickfollowlister;

    public interface OnClickfollowlister {
        void onClickfollow(String thisUser, String proUser);
    }

    public void setmOnClickfollowlister(OnClickfollowlister mOnClickfollowlister) {
        this.mOnClickfollowlister = mOnClickfollowlister;
    }

    public CorporateHeadlinesAdapter(Context context, List<UcnbListBean> data) {
        this.dataList = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_corporate_headlines_fragment, null);
            holder = new ViewHolder();
            holder.iv_corporate_headlines_image = (ImageView) convertView.findViewById(R.id.iv_corporate_headlines_image);
            holder.tv_corporate_headlines_title = (TextView) convertView.findViewById(R.id.tv_corporate_headlines_title);
            holder.tv_corporate_headlines_browse = (TextView) convertView.findViewById(R.id.tv_corporate_headlines_browse);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String iconUrl = dataList.get(position).getNewsIcon();
        if (!TextUtils.isEmpty(iconUrl)) {
            Picasso.with(context).load(iconUrl)
                    //.resize(60, 60)
                    .placeholder(R.drawable.ic_default)
                    .into(holder.iv_corporate_headlines_image);
        }
        holder.tv_corporate_headlines_browse.setText(dataList.get(position).getNewsSeeNum() + "k");
        holder.tv_corporate_headlines_title.setText(dataList.get(position).getNewsTitle());
        return convertView;
    }

    class ViewHolder {
        ImageView iv_corporate_headlines_image;
        TextView tv_corporate_headlines_title;
        TextView tv_corporate_headlines_browse;

    }
}
