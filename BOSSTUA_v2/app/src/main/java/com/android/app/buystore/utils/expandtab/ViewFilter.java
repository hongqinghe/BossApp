package com.android.app.buystore.utils.expandtab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.widget.CustomGridview;

import java.util.ArrayList;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/09/01.
 */
public class ViewFilter extends LinearLayout implements ViewBaseAction, CustomGridview.OnSelClickListener {
    private OnSelectListener mOnSelectListener;
    private EditText pricsFro;
    private Button finish;
    private RadioGroup serviceGroup;
    private RadioGroup transGroup;
    private EditText pricsTo;
    private String[] tab = getResources().getStringArray(R.array.tab_founds_tag);
    private String[] tag = getResources().getStringArray(R.array.tab_tag);
//    private FilterAdapter adapter;
    private CustomGridview gv;
    /***
     * 定义两个Arraylist一个存储所有的数据 一个存储选中的数据
     */
    private ArrayList<TabBean> allList, selList;

    public ViewFilter(Context context) {
        super(context);
        init(context);
        initData();
    }
    private void initData() {
        allList = new ArrayList<TabBean>();
        selList = new ArrayList<TabBean>();
        for (int i = 0; i < tab.length; i++) {
            TabBean c = new TabBean();
            c.setId(i + "");
            c.setCmbh(tag[i]);
            c.setCmname(tab[i]);
            allList.add(c);
        }
        selList.clear();
        for (int i = 1; i < 3; i++) {
            TabBean c = new TabBean();
            c.setId(i + "");
            c.setCmbh(tag[i]);
            c.setCmname(tab[i]);
            selList.add(c);
        }
        /**设置数据*/
        gv.setAllData(allList);
        gv.setSelData(selList);
    }
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_filter_layout, this, true);
        pricsFro = (EditText) findViewById(R.id.et_view_filter_prics_from);
        pricsTo = (EditText) findViewById(R.id.et_view_filter_prics_to);
        finish = (Button) findViewById(R.id.bt_filter_finish);
        serviceGroup = (RadioGroup) findViewById(R.id.rg_filter_service_body);
        transGroup = (RadioGroup) findViewById(R.id.rg_filter_transaction);
        serviceGroup.setOnCheckedChangeListener(serviceChangedLister);
        transGroup.setOnCheckedChangeListener(transChangedLister);
        finish.setOnClickListener(btOnClickListener);
        gv = (CustomGridview) findViewById(R.id.gv_filter);
        gv.setOnSelClickListener(this);
    }

    private int service;
    private RadioGroup.OnCheckedChangeListener serviceChangedLister = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int id = group.getCheckedRadioButtonId();
            switch (id){
                case R.id.rb_filter_service_body_any:
                    service = 0;
                    break;
                case R.id.rb_filter_service_body_boss:
                    service = 1;
                    break;
                case R.id.rb_filter_service_body_company:
                    service = 2;
                    break;
                case R.id.rb_filter_service_body_personal:
                    service = 3;
                    break;
            }
        }
    };
    private int trans;
    private RadioGroup.OnCheckedChangeListener transChangedLister = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int id = group.getCheckedRadioButtonId();
            switch (id){
                case R.id.rb_filter_transaction_any:
                    trans = 0;
                    break;
                case R.id.rb_filter_transaction_boss:
                    trans = 1;
                    break;
                case R.id.rb_filter_transaction_company:
                    trans = 2;
                    break;
                case R.id.rb_filter_transaction_personal:
                    trans = 3;
                    break;
            }
        }
    };

    private String severlable = "";
    private OnClickListener btOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String from = pricsFro.getText().toString().trim();
            String to = pricsTo.getText().toString().trim();
            List<TabBean> lab = new ArrayList<TabBean>();
            lab = selList;
            for (int i = 0; i < selList.size(); i++) {
                severlable += selList.get(i).getCmbh();
            }
            mOnSelectListener.getValue(service, trans, from, to,severlable);
            severlable="";
        }
    };

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    @Override
    public void onSelListener(View v, int position) {
        /**如果选中的list中存在这条数据 则删除  否则添加到选中list数据中*/
        if (selList.contains(allList.get(position))) {
            selList.remove(allList.get(position));
        } else {
            selList.add(allList.get(position));
        }
       /* if (selList.size()>3){
            selList.remove(allList.get(position-2));
        }*/
        if (position ==0){
            for (int i = 1; i < allList.size(); i++) {
                selList.remove(allList.get(i));
            }
        }else if (selList.size()==11){
            for (int i = 1; i < allList.size(); i++) {
                selList.remove(allList.get(i));
            }
            selList.add(allList.get(0));
        }else if (position != 0){
            selList.remove(allList.get(0));
        }
        /**刷新界面*/
        gv.setNotifyDataSetChanged();
    }

    public interface OnSelectListener {
        void getValue(int body, int transaction, String from, String to, String tag);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    private class TabBean {
        private String id;
        private String cmbh;
        private String cmname;

        public String getCmname() {
            return cmname;
        }

        public void setCmname(String cmname) {
            this.cmname = cmname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCmbh() {
            return cmbh;
        }

        public void setCmbh(String cmbh) {
            this.cmbh = cmbh;
        }
        @Override
        public boolean equals(Object o) {

            TabBean comment = (TabBean) o;
            return cmbh.equals(comment.getCmbh());

        }

        @Override
        public String toString() {  //toString返回项的名称
            return getCmname();
        }
    }
}
