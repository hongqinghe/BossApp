package com.android.app.buystoreapp.order;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.BitmapListBean;
import com.android.app.buystoreapp.bean.CommentBean;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 尚帅波 on 2016/10/13.
 */
public class RatedListAdapter extends BaseAdapter {

    private Context context;
    private List<OrderProduct> productList;
    private Handler handler;
    private List<CommentBean> listBean = new ArrayList<CommentBean>();

    private List<BitmapListBean> list;

    private ViewHolder holder = null;


    public RatedListAdapter(Context context, List<OrderProduct>
             productList, Handler handler,List<BitmapListBean> list) {
         this.context = context;
         this.productList = productList;
         this.handler = handler;
         this.list = list;
     }
    /*public RatedListAdapter(Context context, List<CeShi>
            list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
    }*/

    @Override
    public int getCount() {
        return (productList == null) ? 0 : productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.rated_item_list, null);
            holder = new ViewHolder();
            holder.order_image = (ImageView) convertView.findViewById(R.id.order_image);
            holder.add_image = (ImageView) convertView.findViewById(R.id.add_image);
            holder.rated_context = (EditText) convertView.findViewById(R.id.rated_context);
            holder.text_number = (TextView) convertView.findViewById(R.id.text_number);
            holder.rg_chacked = (RadioGroup) convertView.findViewById(R.id.rg_chacked);
            holder.rb_praise = (RadioButton) convertView.findViewById(R.id.rb_praise);
            holder.rb_comment = (RadioButton) convertView.findViewById(R.id.rb_comment);
            holder.rb_bad = (RadioButton) convertView.findViewById(R.id.rb_bad);
            holder.image_container = (LinearLayout) convertView.findViewById(R.id.image_container);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderProduct order = productList.get(position);
        if (!TextUtils.isEmpty(order.getProImgUrl())) {
            Picasso.with(context).load(order.getProImgUrl())
                    .placeholder(R.drawable.ic_boss_default)
                    .error(R.drawable.ic_boss_default).into(holder.order_image);
        } else {
            Picasso.with(context).load(order.getProImgUrl()).into(holder.order_image);
        }

        final View finalConvertView = convertView;



        holder.rated_context.addTextChangedListener(new TextWatcher() {
            private CharSequence wordNum;//记录输入的字数
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;//实时记录输入的字数
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (wordNum.length() >= 100) {
                    ToastUtil.showMessageDefault(context, "只能输入100个字");
                }
                int number = s.length();
                //TextView显示剩余字数
                holder.text_number.setText(number + "/100");
                selectionStart = holder.rated_context.getSelectionStart();
                selectionEnd = holder.rated_context.getSelectionEnd();
                if (wordNum.length() > 100) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    holder.rated_context.setText(s);
                    holder.rated_context.setSelection(tempSelection);//设置光标在最后
                }

            }
        });
        CommentBean data = new CommentBean();

        holder.add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = handler.obtainMessage();
                msg.arg1 = position;
                msg.what = 101;
                handler.sendMessage(msg);
            }
        });

        List<Bitmap> bitmaps = list.get(position).getBitmaps();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int width = (screenWidth - 20*4)/3;
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(width, width);
        lp.topMargin = 30;
        lp.bottomMargin = 30;
        lp2.leftMargin = 20;
          /*  lp2.width = 80;
            lp2.height = 80;*/
        holder.image_container.removeAllViews();
        holder.image_container.setLayoutParams(lp);
        if (bitmaps.size() > 0 && bitmaps.size() != 3) {
            for (int i = 0; i < bitmaps.size(); i++) {
                ImageView img = new ImageView(context);
                img.setBackgroundColor(Color.parseColor("#eeeeee"));

                img.setImageBitmap(bitmaps.get(i));
                img.setLayoutParams(lp2);
                holder.image_container.addView(img);
            }
        } else if (bitmaps.size() == 3) {
            for (int i = 0; i < bitmaps.size(); i++) {
                ImageView img = new ImageView(context);
                img.setBackgroundColor(Color.parseColor("#eeeeee"));

                img.setImageBitmap(bitmaps.get(i));
                img.setLayoutParams(lp2);
                holder.image_container.addView(img);
            }
            holder.add_image.setVisibility(View.GONE);
        }


        return convertView;
    }

    static class ViewHolder {
        ImageView order_image, add_image;
        EditText rated_context;
        TextView text_number;
        RadioGroup rg_chacked;
        RadioButton rb_praise, rb_comment, rb_bad;
        LinearLayout image_container;
    }


}
