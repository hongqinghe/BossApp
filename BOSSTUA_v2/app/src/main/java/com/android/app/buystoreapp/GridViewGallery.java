package com.android.app.buystoreapp;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.app.buystoreapp.adapter.ViewPager_GV_ItemAdapter;
import com.android.app.buystoreapp.adapter.ViewPager_GridView_Adapter;
import com.android.app.buystoreapp.bean.ChanceInfoBean;

import java.util.ArrayList;
import java.util.List;



public class GridViewGallery extends LinearLayout {

    private Context context;
    /** 保存实体对象链表 */
    private List<ChanceInfoBean> list;
    private ViewPager viewPager;
    private LinearLayout ll_dot;
    private ImageView[] dots;
    /** ViewPager当前页 */
    private int currentIndex;
    /** ViewPager页数 */
    private int viewPager_size =1;
    /** 默认一页10个item */
    private int pageItemCount = 10;

    /** 保存每个页面的GridView视图 */
    private List<View> list_Views;  
    public static final String TAG = "GridViewGallery";
  
    public GridViewGallery(Activity context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.list = null;
        initView();
    }

    /**
     * 一定一个接口
     */
    public interface ICoallBack{
        void onPageChange(String s);
    }

    /**
     * 初始化接口变量
     */
    ICoallBack icallBack = null;

    /**
     * 自定义控件的自定义事件
     * @param iBack 接口类型
     */
    public void setonClick(ICoallBack iBack)
    {
        icallBack = iBack;
    }
	public GridViewGallery(Activity context, List<ChanceInfoBean> list) {
        super(context);
        this.context = context;
        this.list = list;
        initView();  
        initDots();  
        setAdapter();  
    }  
  
    private void setAdapter() {  
        list_Views = new ArrayList<View>();
        for (int i = 0; i < viewPager_size; i++) {
            list_Views.add(getViewPagerItem(i));  
        }  
        viewPager.setAdapter(new ViewPager_GridView_Adapter(list_Views));
    }  
  
    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.channel_activity, (ViewGroup) getParent(),false);
        viewPager = (ViewPager) view.findViewById(R.id.vPager);  
        ll_dot = (LinearLayout) view.findViewById(R.id.ll_channel_dots);  
        addView(view);
    }

    // 初始化底部小圆点
    private void initDots() {

//        int col = (width / 200) > 2 ? (width /200) :2;
//        int row = (high/400) >4 ? (high/400):2;
//        pageItemCount = col * row; //每一页可装item
        int t=1;
        if(list.size() % pageItemCount==0){
        	t=0;
        }
        viewPager_size = list.size() / pageItemCount + t;  
  
        if (0 < viewPager_size) {  
            ll_dot.removeAllViews();  
            if (1 == viewPager_size) {  
                ll_dot.setVisibility(View.GONE);  
            } else if (1 < viewPager_size) {  
                ll_dot.setVisibility(View.VISIBLE);  
                for (int j = 0; j < viewPager_size; j++) {  
                    ImageView image = new ImageView(context);  
                    LayoutParams params = new LayoutParams(10, 10);  //
                    params.setMargins(3, 0, 3, 0);  
                    image.setBackgroundResource(R.drawable.play_hide);  
                    ll_dot.addView(image, params);  
                }  
            }  
        }  
        if (viewPager_size != 1) {  
            dots = new ImageView[viewPager_size];  
            for (int i = 0; i < viewPager_size; i++) {
                //从布局中填充dots数组
                dots[i] = (ImageView) ll_dot.getChildAt(i);  
//                dots[i].setEnabled(true);
//                dots[i].setTag(i);
            }  
            currentIndex = 0;  //当前页
//            dots[currentIndex].setEnabled(false);
//            if (currentIndex>0) {
                dots[currentIndex].setBackgroundResource(R.drawable.play_display);
//            }
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int arg0) {
                    setCurDot(arg0);
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                    // TODO Auto-generated method stub

                }
            });
        }
    }

    /** 当前底部小圆点 */
    private void setCurDot(int positon) {  
        if (positon < 0 || positon > viewPager_size - 1 || currentIndex == positon) {  
            return;  
        } 
        for(int i=0;i<dots.length;i++){
        	dots[i].setBackgroundResource(R.drawable.play_hide);
        }
//        dots[positon].setEnabled(false);
//        dots[currentIndex].setEnabled(true);
        dots[positon].setBackgroundResource(R.drawable.play_display);
        currentIndex = positon;  
    }

    //ViewPager中每个页面的GridView布局
    private View getViewPagerItem(int index) {  
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        View layout = inflater.inflate(R.layout.channel_viewpage_gridview, null);  
        GridView gridView = (GridView) layout.findViewById(R.id.vp_gv);  
  
        //每个页面GridView的Adpter
        ViewPager_GV_ItemAdapter adapter = new ViewPager_GV_ItemAdapter(context, list, index, pageItemCount);
  
        gridView.setAdapter(adapter);  
        gridView.setOnItemClickListener(new OnItemClickListener() {  

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
                if (null != list.get(position + currentIndex * pageItemCount).getOnClickListener()) {
                    list.get(position + currentIndex * pageItemCount).getOnClickListener().ongvItemClickListener(position + currentIndex * pageItemCount);
                }
            }

        });
        return gridView;  
    }

    //宽
    public int getViewWidth(View view){
        view.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        return view.getMeasuredWidth();
    }
    //高
    public int getViewHeight(View view){
        view.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        return view.getMeasuredHeight();
    }
}
