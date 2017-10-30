package com.android.app.buystoreapp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.android.app.buystore.utils.AlbumHelper;
import com.android.app.buystore.utils.Bimp;
import com.android.app.buystoreapp.adapter.ImageGridAdapter;
import com.android.app.buystoreapp.adapter.ImageGridAdapter.TextCallback;
import com.android.app.buystoreapp.bean.ImageItem;
import com.android.app.buystoreapp.wallet.ToastUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ImageGridActivity extends Activity {
    public static final String EXTRA_IMAGE_LIST = "imagelist";

    // ArrayList<Entity> dataList;
    List<ImageItem> dataList;
    GridView gridView;
    ImageGridAdapter adapter;
    AlbumHelper helper;
    Button bt;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ToastUtil.showMessageDefault(ImageGridActivity.this, "最多选择9张图片");
                    break;
                case 1:
                    ToastUtil.showMessageDefault(ImageGridActivity.this, "最多选择3张图片");
                    break;
                default:
                    break;
            }
        }
    };
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);

        flag = getIntent().getStringExtra("flag");

        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        dataList = (List<ImageItem>) getIntent().getSerializableExtra(
                EXTRA_IMAGE_LIST);

        initView();
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                Collection<String> c = adapter.map.values();
                Iterator<String> it = c.iterator();
                for (; it.hasNext(); ) {
                    list.add(it.next());
                }

                if (Bimp.act_bool) {
                    Bimp.act_bool = false;
                    finish();
                    /*Intent intent = new Intent();
					intent.setClassName("com.android.app.buystoreapp", "ReleaseStepTowActivity");
					if (getPackageManager().resolveActivity(intent, 0) == null) {
						// 说明系统中不存在这个activity
						Intent i = new Intent(ImageGridActivity.this,
								ReleaseStepTowActivity.class);
						startActivity(i);
						Bimp.act_bool = false;
					}else{
						finish();
					}*/

                }
                for (int i = 0; i < list.size(); i++) {
                    if (Bimp.drr.size() < 9) {
                        Bimp.drr.add(list.get(i));
                    }
                }
                finish();
            }

        });
    }

    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string.choose_image));
        findViewById(R.id.iv_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageGridActivity.this.finish();
            }
        });
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
                mHandler,flag);
        gridView.setAdapter(adapter);
        adapter.setTextCallback(new TextCallback() {
            public void onListen(int count) {
                bt.setText("完成" + "(" + count + ")");
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // if(dataList.get(position).isSelected()){
                // dataList.get(position).setSelected(false);
                // }else{
                // dataList.get(position).setSelected(true);
                // }
                adapter.notifyDataSetChanged();
            }

        });

    }
}
