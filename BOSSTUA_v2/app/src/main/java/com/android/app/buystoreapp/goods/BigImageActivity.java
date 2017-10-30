package com.android.app.buystoreapp.goods;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.adapter.BigImageRecyclerAdapter;
import com.android.app.buystoreapp.bean.ShopDetailImage;

import java.util.ArrayList;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/09/27.
 */
public class BigImageActivity extends Activity {
    private RecyclerView recl;
    private List<ShopDetailImage> imagelist = new ArrayList<ShopDetailImage>();
    private BigImageRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.goods_big_image_activity);
         imagelist = (List<ShopDetailImage>) getIntent().getSerializableExtra("imagelist");
        recl = (RecyclerView) findViewById(R.id.rv_big_image_list);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recl.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new BigImageRecyclerAdapter(this, imagelist);
        mAdapter.setOnItemClickLitener(new BigImageRecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view) {
                finish();
            }
        });
        recl.setAdapter(mAdapter);
    }
}
