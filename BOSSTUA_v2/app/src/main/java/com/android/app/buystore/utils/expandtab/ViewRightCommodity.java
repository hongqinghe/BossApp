package com.android.app.buystore.utils.expandtab;

import com.android.app.buystoreapp.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;


public class ViewRightCommodity extends RelativeLayout implements ViewBaseAction{

    private ListView mListView;
    private final String[] items = new String[] { "离我最近","销量由高到低","销量由低到高","价格由高到低","价格由低到高"};
    private final String[] itemsVaule = new String[] { "0","1","2","3","4"};
    
    private OnSelectListener mOnSelectListener;
    private TextAdapter adapter;
    private String mDistance = itemsVaule[0];
    private String showText = items[0];

    public String getShowText() {
        return showText;
    }

    public ViewRightCommodity(Context context) {
        super(context);
        init(context);
    }

    public ViewRightCommodity(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ViewRightCommodity(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_distance, this, true);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_mid));
        mListView = (ListView) findViewById(R.id.listView);
        adapter = new TextAdapter(context, items, R.drawable.choose_item_right, R.drawable.choose_eara_item_selector);
        adapter.setTextSize(17);
        if (mDistance != null) {
            for (int i = 0; i < itemsVaule.length; i++) {
                if (itemsVaule[i].equals(mDistance)) {
                    adapter.setSelectedPositionNoNotify(i);
                    showText = items[i];
                    break;
                }
            }
        }
        mListView.setAdapter(adapter);
        adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                if (mOnSelectListener != null) {
                    showText = items[position];
                    mOnSelectListener.getValue(itemsVaule[position], items[position]);
                }
            }
        });
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void getValue(String distance, String showText);
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void show() {
        
    }

}
