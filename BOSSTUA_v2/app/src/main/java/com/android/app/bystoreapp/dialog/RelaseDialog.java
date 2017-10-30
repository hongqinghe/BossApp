package com.android.app.bystoreapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.managementservice.ExplainWebViewActivity;
import com.android.app.buystoreapp.widget.MyGridLayout;

/**
 * $desc
 * Created by likaihang on 16/10/06.
 */
public class RelaseDialog extends Dialog {
    private Dialog dialog = null;

    private Context context;
    private String[] head;
    private TypedArray imagehead;
    private String[] tag;
    private TypedArray imagetag;
    private MyGridLayout ml_head;
    private MyGridLayout ml_tag;
    private ImageView ivCloseDialog;
    private String tab;
    private String serveLabelName;
    private String[] label;
    private TextView tv_release_know;

    /**
     * 自定义Dialog监听器
     */
    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        void callback(String i, String serveLabelName);
    }

    private PriorityListener listener;
    /**
     * 带监听器参数的构造函数
     */
    public RelaseDialog(Context context, PriorityListener listener) {
        super(context,R.style.Dialog_Relase_Fullscreen);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relase_dialog_layout);
        head = context.getResources().getStringArray(R.array.relase_head);
        imagehead = context.getResources().obtainTypedArray(R.array.relase_head_icon);

        tag = context.getResources().getStringArray(R.array.relase_tag);
        imagetag = context.getResources().obtainTypedArray(R.array.relase_tag_icon);

        label = context.getResources().getStringArray(R.array.tab_tag);
        initview();
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        dialog = this;
        this.getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }

    private void initview() {
        tv_release_know = (TextView) findViewById(R.id.tv_release_know);
        tv_release_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExplainWebViewActivity.class);
                intent.putExtra("flag", 5000);
                context.startActivity(intent);
            }
        });
        ivCloseDialog = (ImageView)findViewById(R.id.iv_closs);
        ivCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmissDialog();
            }
        });
        ml_head = (MyGridLayout)findViewById(R.id.ml_relase_head);
        ml_head.setGridAdapter(new MyGridLayout.GridAdatper() {
            @Override
            public View getView(int index) {
                View v = LayoutInflater.from(context).inflate(R.layout
                        .person_mygrid_item_layout, null);
                ImageView iv = (ImageView) v.findViewById(R.id.iv_grid_item_pric);
                TextView tv = (TextView) v.findViewById(R.id.tv_grid_item_name);
                tv.setTextSize(15);
                iv.setImageResource(imagehead.getResourceId(index, 0));
                tv.setText(head[index]);
                return v;
            }

            @Override
            public int getCount() {
                return head.length;
            }
        });
        ml_head.setOnItemClickListener(new MyGridLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                switch (index){
                    case 0:
                        tab = label[1];
                        serveLabelName = head[0];
                        break;
                    case 1:
                        tab = label[2];
                        serveLabelName = head[1];
                        break;
                }
                dissmissDialog();
            }
        });
        ml_tag = (MyGridLayout)findViewById(R.id.ml_relase_tag);
        ml_tag.setGridAdapter(new MyGridLayout.GridAdatper() {
            @Override
            public View getView(int index) {
                View v = LayoutInflater.from(context).inflate(R.layout
                        .person_mygrid_item_layout, null);
                ImageView iv = (ImageView) v.findViewById(R.id.iv_grid_item_pric);
                TextView tv = (TextView) v.findViewById(R.id.tv_grid_item_name);
                tv.setTextSize(15);
                iv.setImageResource(imagetag.getResourceId(index, 0));
                tv.setText(tag[index]);
                return v;
            }

            @Override
            public int getCount() {
                return tag.length;
            }
        });
        ml_tag.setOnItemClickListener(new MyGridLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                switch (index){
                    case 0:
                        tab = label[3];
                        serveLabelName = tag[0];
                        break;
                    case 1:
                        tab = label[4];
                        serveLabelName = tag[1];
                        break;
                    case 2:
                        tab =label[5];
                        serveLabelName = tag[2];
                        break;
                    case 3:
                        tab = label[6];
                        serveLabelName = tag[3];
                        break;
                    case 4:
                        tab = label[7];
                        serveLabelName = tag[4];
                        break;
                    case 5:
                        tab = label[8];
                        serveLabelName = tag[5];
                        break;
                    case 6:
                        tab = label[9];
                        serveLabelName = tag[6];
                        break;
                    case 7:
                        tab = label[10];
                        serveLabelName = tag[7];
                        break;
                    case 8:
                        tab = label[11];
                        serveLabelName = tag[8];
                        break;
                }
                dissmissDialog();
            }
        });
    }
    public void dissmissDialog() {
        if (dialog != null && dialog.isShowing()) {
            listener.callback(tab,serveLabelName);
            dialog.dismiss();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dissmissDialog();
        }
        return false;
    }
}
