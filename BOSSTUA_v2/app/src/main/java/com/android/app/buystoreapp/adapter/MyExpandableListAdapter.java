package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.GroupGoods;
import com.android.app.buystoreapp.bean.ShoppingCarBean;
import com.android.app.buystoreapp.bean.ShoppingCarSubmit;
import com.android.app.buystoreapp.bean.VerifyOrderBean;
import com.android.app.buystoreapp.listener.OnShoppingCartChangeListener;
import com.android.app.buystoreapp.listener.ShoppingCartBiz;
import com.android.app.buystoreapp.setting.ConfirmOrderActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.buystoreapp.widget.UIAlertView;
import com.android.app.utils.HttpUtils;
import com.android.app.utils.ToastHelper;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<ShoppingCarBean> mListGoods = new ArrayList<ShoppingCarBean>();
    private List<ShoppingCarBean> selectcarlist = new ArrayList<ShoppingCarBean>();
    //    private List<GroupGoods> grouplist = new ArrayList<GroupGoods>();
    private OnShoppingCartChangeListener mChangeListener;
    private boolean isSelectAll = false;
    private GroupGoods goods;
    private VerifyOrderBean data;

    public MyExpandableListAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<ShoppingCarBean> mListGoods) {
        this.mListGoods = mListGoods;
        setSettleInfo();
    }

    public void setOnShoppingCartChangeListener(OnShoppingCartChangeListener changeListener) {
        this.mChangeListener = changeListener;
    }

    public View.OnClickListener getAdapterListener() {
        return listener;
    }


    public int getGroupCount() {
        return mListGoods.size();
    }


    public int getChildrenCount(int groupPosition) {
        return mListGoods.get(groupPosition).getQueryCar().size();
    }


    public Object getGroup(int groupPosition) {
        return mListGoods.get(groupPosition);
    }


    public Object getChild(int groupPosition, int childPosition) {
        return mListGoods.get(groupPosition).getQueryCar().get(childPosition);
    }


    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public boolean hasStableIds() {
        return false;
    }


    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if (convertView == null) {
            holder = new GroupViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_elv_group_test, parent, false);
            holder.tvGroup = (TextView) convertView.findViewById(R.id.tvShopNameGroup);
//            holder.tvEdit = (TextView) convertView.findViewById(R.id.tvEdit);
            holder.ivCheckGroup = (ImageView) convertView.findViewById(R.id.ivCheckGroup);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.tvGroup.setText(mListGoods.get(groupPosition).getNickname());
        ShoppingCartBiz.checkItem(mListGoods.get(groupPosition).isGroupSelected(), holder.ivCheckGroup);
//        if (isEditing) {
//            holder.tvEdit.setText("完成");
//        } else {
//            holder.tvEdit.setText("编辑");
//        }
        holder.ivCheckGroup.setTag(groupPosition);
        holder.ivCheckGroup.setOnClickListener(listener);
//        holder.tvEdit.setTag(groupPosition);
//        holder.tvEdit.setOnClickListener(listener);
        holder.tvGroup.setOnClickListener(listener);
        return convertView;
    }

    /**
     * child view
     */

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_elv_child_test, parent, false);
            holder.tvChild = (TextView) convertView.findViewById(R.id.tvItemChild);
            holder.tvDel = (ImageView) convertView.findViewById(R.id.tvDel);
            holder.ivCheckGood = (ImageView) convertView.findViewById(R.id.ivCheckGood);
            holder.goodsIcon = (ImageView) convertView.findViewById(R.id.ivGoods);
//            holder.rlEditStatus = (RelativeLayout) convertView.findViewById(R.id.rlEditStatus);
            holder.llGoodInfo = (LinearLayout) convertView.findViewById(R.id.llGoodInfo);
            holder.ivAdd = (ImageView) convertView.findViewById(R.id.ivAdd);
            holder.ivReduce = (ImageView) convertView.findViewById(R.id.ivReduce);
            holder.tvGoodsParam = (TextView) convertView.findViewById(R.id.tvGoodsParam);
            holder.tvPriceNew = (TextView) convertView.findViewById(R.id.tvPriceNew);
//            holder.tvPriceOld = (TextView) convertView.findViewById(R.id.tvPriceOld);
//            holder.tvPriceOld.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//数字被划掉效果
//            holder.tvNum = (TextView) convertView.findViewById(R.id.tvNum);
            holder.tvNum2 = (TextView) convertView.findViewById(R.id.tvNum2);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        goods = mListGoods.get(groupPosition).getQueryCar().get(childPosition);
        boolean isChildSelected = goods.isChildSelected();
//        boolean isEditing = goods.isEditing();
        String priceNew = "¥" + goods.getMoreGroPrice() + "元";
//        String priceOld = "¥" + goods.getMkPrice();
        String num = String.valueOf(goods.getCount());
        if (goods.getCount() == 1) {
            holder.ivReduce.setImageResource(R.drawable.icon_minus);
        } else {
            holder.ivReduce.setImageResource(R.drawable.icon_minus_hid);
        }
//        String pdtDesc = goods.getPdtDesc();
        Picasso.with(mContext).load(goods.getProImageMin())
                //.resize(100,100)
                .placeholder(R.drawable.ic_default).into(holder.goodsIcon);
        String goodName = goods.getProName();

        holder.ivCheckGood.setTag(groupPosition + "," + childPosition);
        holder.tvChild.setText(goodName);
        holder.tvPriceNew.setText(priceNew);
//        holder.tvPriceOld.setText(priceOld);
//        holder.tvNum.setText("X " + num);
        holder.tvNum2.setText(num);
        holder.tvGoodsParam.setText(goods.getMoreGroName());

        holder.ivAdd.setTag(goods);
        holder.ivReduce.setTag(goods);
        holder.tvDel.setTag(groupPosition + "," + childPosition);
        holder.tvDel.setTag(groupPosition + "," + childPosition);

        ShoppingCartBiz.checkItem(isChildSelected, holder.ivCheckGood);
//        if (isEditing) {
//            holder.llGoodInfo.setVisibility(View.GONE);
//            holder.rlEditStatus.setVisibility(View.VISIBLE);
//        } else {
//            holder.llGoodInfo.setVisibility(View.VISIBLE);
//            holder.rlEditStatus.setVisibility(View.GONE);
//        }
        if (mListGoods.get(groupPosition).getQueryCar().get(childPosition).getProStatus() == 1) {
            holder.ivCheckGood.setOnClickListener(listener);
        } else {
            holder.ivCheckGood.setImageResource(R.drawable.ic_car_no_check);
            holder.ivCheckGood.setOnClickListener(null);
        }
        holder.tvDel.setOnClickListener(listener);
        holder.ivAdd.setOnClickListener(listener);
        holder.ivReduce.setOnClickListener(listener);
        holder.llGoodInfo.setOnClickListener(listener);
        return convertView;
    }

    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                //main
                case R.id.ivSelectAll:
                    isSelectAll = ShoppingCartBiz.selectAll(mListGoods, isSelectAll, (ImageView) v);
                    setSettleInfo();
                    notifyDataSetChanged();
                    break;
                case R.id.btnSettle:
                    if (ShoppingCartBiz.hasSelectedGoods(mListGoods)) {
                        selectcarlist.clear();
                        ToastHelper.getInstance()._toast("结算跳转");
//                        List<ShoppingCarSubmit> submit = new ArrayList<>();
                        data = new VerifyOrderBean();
                        data.vnList = new ArrayList<ShoppingCarSubmit>();
                        for (int i = 0; i < mListGoods.size(); i++) {
                            List<GroupGoods> grouplist = new ArrayList<GroupGoods>();
                            if (mListGoods.get(i).isGroupSelected()) {
                                for (int k = 0; k < mListGoods.get(i).getQueryCar().size(); k++) {
                                    goods = mListGoods.get(i).getQueryCar().get(k);
                                    if (goods.isChildSelected()) {
                                        //添加比对信息
                                        ShoppingCarSubmit bean = new ShoppingCarSubmit();
                                        bean.count = goods.getCount();//数量
                                        bean.moreGroPrice = String.valueOf(goods.getCount() * Double.valueOf(goods.getMoreGroPrice()));//
                                        bean.shopCarId = goods.getShopCarId();
                                        data.vnList.add(bean);
                                    }
                                }
                                //添加商家下的所有信息
                                ShoppingCarBean selectcar = new ShoppingCarBean();
                                selectcar.setUserid(mListGoods.get(i).getUserid());
                                selectcar.setNickname(mListGoods.get(i).getNickname());
                                grouplist.addAll(mListGoods.get(i).getQueryCar());
                                selectcar.setQueryCar(grouplist);
                                selectcarlist.add(selectcar);
                            } else {
                                for (int j = 0; j < mListGoods.get(i).getQueryCar().size(); j++) {
                                    goods = mListGoods.get(i).getQueryCar().get(j);
                                    if (goods.isChildSelected()) {
                                        ShoppingCarSubmit bean = new ShoppingCarSubmit();
                                        bean.count = goods.getCount();//数量
                                        bean.moreGroPrice = String.valueOf(goods.getCount() * Double.valueOf(goods.getMoreGroPrice()));//
                                        bean.shopCarId = goods.getShopCarId();
                                        data.vnList.add(bean);

                                        //t添加当前商品商家信息
                                        ShoppingCarBean selectcar = new ShoppingCarBean();
                                        selectcar.setUserid(mListGoods.get(i).getUserid());
                                        selectcar.setNickname(mListGoods.get(i).getNickname());
//                                        selectcar.setQueryCar(mListGoods.get(i).getQueryCar());

                                        //添加商品信息
                                        GroupGoods group = new GroupGoods();
                                        group.setCount(goods.getCount());//组合数量
                                        group.setFreightMode(goods.getFreightMode());//货运方式
                                        group.setFreightPrice(goods.getFreightPrice());//运费
                                        group.setModes(goods.getModes());//服务方式
                                        group.setMoreGroId(goods.getMoreGroId());//组合id
                                        group.setMoreGroName(goods.getMoreGroName());//组合名称
                                        group.setMoreGroPrice(goods.getMoreGroPrice());//组合价格
                                        group.setProImageMin(goods.getProImageMin());//缩略图
                                        group.setProName(goods.getProName());//商品名称
                                        group.setProStatus(goods.getProStatus());//商品状态 0下架1上架2已删除
                                        group.setShopCarId(goods.getShopCarId());//购物车id
                                        group.setDayTimeEnd(goods.getDayTimeEnd());//时间
                                        group.setDayTimeStart(goods.getDayTimeStart());
                                        group.setWeekEnd(goods.getWeekEnd());
                                        group.setWeekStart(goods.getWeekStart());
                                        grouplist.add(group);
                                        selectcar.setQueryCar(grouplist);
                                        selectcarlist.add(selectcar);
                                    }
                                }
                            }

                        }
                        verifyOrder(data.vnList);
//                        Intent i = new Intent(mContext, ConfirmOrderActivity.class);
//                        i.putExtra("mListGoods", (Serializable) mListGoods);
//                        mContext.startActivity(i);
                    } else {
                        ToastHelper.getInstance()._toast("亲，先选择商品！");
                    }
                    //group
                    break;
               /* case R.id.tvEdit://切换界面，属于特殊处理，假如没打算切换界面，则不需要这块代码
                    int groupPosition2 = Integer.parseInt(String.valueOf(v.getTag()));
                    boolean isEditing = !(mListGoods.get(groupPosition2).isEditing());
                    mListGoods.get(groupPosition2).setIsEditing(isEditing);
                    for (int i = 0; i < mListGoods.get(groupPosition2).getGoods().size(); i++) {
                        mListGoods.get(groupPosition2).getGoods().get(i).setIsEditing(isEditing);
                    }
                    notifyDataSetChanged();
                    break;*/
                case R.id.ivCheckGroup:
                    int groupPosition3 = Integer.parseInt(String.valueOf(v.getTag()));
                    isSelectAll = ShoppingCartBiz.selectGroup(mListGoods, groupPosition3);
                    selectAll();
                    setSettleInfo();
                    notifyDataSetChanged();
                    break;
                //child
                case R.id.ivCheckGood:
                    String tag = String.valueOf(v.getTag());
                    if (tag.contains(",")) {
                        String s[] = tag.split(",");
                        int groupPosition = Integer.parseInt(s[0]);
                        int childPosition = Integer.parseInt(s[1]);
                        isSelectAll = ShoppingCartBiz.selectOne(mListGoods, groupPosition, childPosition);
                        selectAll();
                        setSettleInfo();
                        notifyDataSetChanged();
                    }
                    break;
                case R.id.tvDel:
                    String tagPos = String.valueOf(v.getTag());
                    if (tagPos.contains(",")) {
                        String s[] = tagPos.split(",");
                        int groupPosition = Integer.parseInt(s[0]);
                        int childPosition = Integer.parseInt(s[1]);
                        showDelDialog(groupPosition, childPosition);
                    }
                    break;
                case R.id.ivAdd:
                    String countNum = ((TextView) (((View) (v.getParent())).findViewById(R.id.tvNum2))).getText().toString();
                    if (Integer.valueOf(countNum) >= goods.getMoreGroSurplus()) {
                        ToastUtil.showMessageDefault(mContext, "没有那么多库存了！");
                        break;
                    }
                    ShoppingCartBiz.addOrReduceGoodsNum(true, (GroupGoods) v.getTag(), ((TextView) (((View) (v.getParent())).findViewById(R.id.tvNum2))));
                    setSettleInfo();
                    break;
                case R.id.ivReduce:
                    ShoppingCartBiz.addOrReduceGoodsNum(false, (GroupGoods) v.getTag(), ((TextView) (((View) (v.getParent())).findViewById(R.id.tvNum2))));
                    setSettleInfo();
                    break;
                case R.id.llGoodInfo:
                    ToastHelper.getInstance()._toast("商品详情，暂未实现");
                    break;
                case R.id.tvShopNameGroup:
                    ToastHelper.getInstance()._toast("商铺详情，暂未实现");
                    break;
            }
        }
    };

    /**
     * 结算比对数据
     *
     * @author likaihang
     * creat at @time 16/10/26 15:23
     */
    private void verifyOrder(List<ShoppingCarSubmit> mlist) {
        data = new VerifyOrderBean("verifyPriceOrNumber", 1, mlist);
        JSONObject obj = null;
        try {
            obj = new JSONObject(new Gson().toJson(data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("结算提交数据--", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("verifyPriceOrNumber---", new String(bytes));
                JSONObject obj = null;
                try {
                    obj = new JSONObject(new String(bytes));
                    String result = obj.getString("result");
                    String resultNote = obj.getString("resultNote");
                    String moreGroSurplus = obj.getString("moreGroSurplus");
                    if ("0".equals(result)) {
                        String[] infos = ShoppingCartBiz.getShoppingCount(mListGoods);
                        Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                        intent.putExtra("totalPrice", Double.valueOf(infos[1]));
                        intent.putExtra("selectcarlist", (Serializable) selectcarlist);
//                        intent.putExtra("",);
                        mContext.startActivity(intent);
                    } else {
                        String shopCarId = obj.getString("shopCarId");
                        Log.d("result----", shopCarId + "\n" + moreGroSurplus);
                        ToastUtil.showMessageDefault(mContext, shopCarId + "\n" + resultNote);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {

            }
        });
    }

    private void selectAll() {
        if (mChangeListener != null) {
            mChangeListener.onSelectItem(isSelectAll);
        }
    }

    private void setSettleInfo() {
        String[] infos = ShoppingCartBiz.getShoppingCount(mListGoods);
        //删除或者选择商品之后，需要通知结算按钮，更新自己的数据；
        if (mChangeListener != null && infos != null) {
            mChangeListener.onDataChange(infos[0], infos[1],infos[2]);
        }
    }

    private void showDelDialog(final int groupPosition, final int childPosition) {
        final UIAlertView delDialog = new UIAlertView(mContext, "温馨提示", "确认删除该商品吗?",
                "取消", "确定");
        delDialog.show();

        delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {


                                       public void doLeft() {
                                           delDialog.dismiss();
                                       }


                                       public void doRight() {
                                           String productID = mListGoods.get(groupPosition).getQueryCar().get(childPosition).getMoreGroId();
                                           String shopcarID = mListGoods.get(groupPosition).getQueryCar().get(childPosition).getShopCarId();
                                           deletGoods(shopcarID);
                                           ShoppingCartBiz.delGood(productID);
                                           delGoods(groupPosition, childPosition);
                                           setSettleInfo();
                                           notifyDataSetChanged();
                                           delDialog.dismiss();
                                       }
                                   }
        );
    }

    //删除商品
    private void deletGoods(String id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "deleteShopCar");
            obj.put("shop_car_id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("删除信息", "");
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("deleteShopCar--", new String(bytes));
                Gson gson = new Gson();
                try {
                    JSONObject obj = new JSONObject(gson.toJson(new String(bytes)));
                    if ("0".equals(obj.getString("result"))) {
                        ToastUtil.showMessageDefault(mContext, "删除成功！");
                    } else {
                        ToastUtil.showMessageDefault(mContext, obj.getString("resultNote"));
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                ToastUtil.showMessageDefault(mContext, mContext.getString(R.string.network_is_bad));
                return;
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                return;
            }
        });
    }

    private void delGoods(int groupPosition, int childPosition) {
        mListGoods.get(groupPosition).getQueryCar().remove(childPosition);
        if (mListGoods.get(groupPosition).getQueryCar().size() == 0) {
            mListGoods.remove(groupPosition);
        }
        notifyDataSetChanged();
    }

    class GroupViewHolder {
        TextView tvGroup;
        //        TextView tvEdit;
        ImageView ivCheckGroup;
    }

    class ChildViewHolder {
        /**
         * 商品名称
         */
        TextView tvChild;
        /**
         * 商品规格
         */
        TextView tvGoodsParam;
        /**
         * 选中
         */
        ImageView ivCheckGood;
        /**
         * 非编辑状态
         */
        LinearLayout llGoodInfo;
//        /** 编辑状态 */
//        RelativeLayout rlEditStatus;
        /**
         * +1
         */
        ImageView ivAdd;
        /**
         * -1
         */
        ImageView ivReduce;
        /**
         * 删除
         */
        ImageView tvDel;
        /**
         * 新价格
         */
        TextView tvPriceNew;
//        /** 旧价格 */
//        TextView tvPriceOld;
//        /** 商品状态的数量 */
//        TextView tvNum;
        /**
         * 编辑状态的数量
         */
        TextView tvNum2;
        /**
         * 商品图片
         */
        ImageView goodsIcon;
    }
}
