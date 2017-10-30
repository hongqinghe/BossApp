package com.android.app.buystoreapp.wallet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.BilDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 尚帅波 on 2016/9/18.
 */
public class BillAllFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ListView lv_fragment_list;
    private List<BilDetailsBean> datas = new ArrayList<BilDetailsBean>();
    public static BillDetailListAdapter adapter;


    public BillAllFragment() {
    }

    @SuppressLint("ValidFragment")
    public BillAllFragment(List<BilDetailsBean> datas) {
        this.datas = datas;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        if (view == null) {

            view = inflater.inflate(R.layout.fragment_list, null);
            initView();
        }
        return view;
    }


    private void initView() {
        lv_fragment_list = (ListView) view.findViewById(R.id.lv_fragment_list);
        adapter = new BillDetailListAdapter(getActivity(), datas);
        lv_fragment_list.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

    }


}
