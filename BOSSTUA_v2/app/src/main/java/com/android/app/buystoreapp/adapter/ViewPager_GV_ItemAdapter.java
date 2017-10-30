package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.ChanceInfoBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewPager_GV_ItemAdapter extends BaseAdapter {

	 private List<ChanceInfoBean> list_info;
	    private Context context;
	/** ViewPager页码 */
	private int index;
	/** 根据屏幕大小计算得到的每页item个数 */
	private int pageItemCount;
	/** 传进来的List的总长度 */
	private int totalSize;

	/** 当前页item的实际个数 */
	// private int itemRealNum;
	    @SuppressWarnings("unchecked")  
	    public ViewPager_GV_ItemAdapter(Context context, List<ChanceInfoBean> list) {
	        this.context = context;  
	        this.list_info = list;
	    }  
	  
	    public ViewPager_GV_ItemAdapter(Context context, List<ChanceInfoBean> list, int index, int pageItemCount) {
	        this.context = context;  
	        this.index = index;
	        this.pageItemCount = pageItemCount;
	        list_info = new ArrayList<ChanceInfoBean>();
	        totalSize = list.size();
			// itemRealNum=list.size()-index*pageItemCount;
			// 当前页的item对应的实体在List<?>中的其实下标
	        int list_index = index * pageItemCount;  
	        for (int i = list_index; i < list.size(); i++) {  
	            list_info.add(list.get(i));
	        }  
	  
	    }  
	  
	    @Override  
	    public int getCount() {  
	        int size = totalSize / pageItemCount;  
	        if (index == size)  
	            return totalSize - pageItemCount * index;  
	        else  
	            return pageItemCount;  
	        // return itemRealNum;  
	    }  
	  
	    @Override  
	    public Object getItem(int position) {  
	       // return null;  
	    	return list_info.get(position);
	    }  
	  
	    @Override  
	    public long getItemId(int position) {  
	        return position;  
	    }  
	  
	    @Override  
	    public View getView(int position, View convertView, ViewGroup parent) {  
	  
	        ViewHolder iv;  
	        if (convertView == null) { 
	            iv = new ViewHolder();  
	            convertView = LayoutInflater.from(context).inflate(R.layout.channel_gridview_item, null);
	        	iv.iv_icon = (ImageView) convertView.findViewById(R.id.iv_gv_item_icon);  
	        	iv.tv_name = (TextView) convertView.findViewById(R.id.tv_gv_item_Name);  
	        	convertView.setTag(iv);
	        }else { 
	            iv = (ViewHolder) convertView.getTag();  
	        }
	        iv.updateViews(position, null);
			return convertView;
	    }  
	  
	    class ViewHolder{  
	        ImageView iv_icon;  
	        TextView tv_name;  
	      
	        protected void updateViews(int position, Object inst) {
				if (!TextUtils.isEmpty(list_info.get(position).getCategoryIcon())) {
					Picasso.with(context)
							.load(list_info.get(position).getCategoryIcon())
							//.resize(50,50)
							.placeholder(R.drawable.ic_default)
							.into(iv_icon);
				}else {
					iv_icon.setBackgroundResource(R.drawable.ic_launcher);
				}
					tv_name.setText(list_info.get(position).getCategoryName());
	        }  
	    } 
	  

}
