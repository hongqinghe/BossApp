package com.android.app.buystoreapp.search;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.lidroid.xutils.util.LogUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class SearchAllAdapter extends BaseAdapter {

    public static final int TYPE_SHOP = 0;
    public static final int TYPE_COMMODITY = 1;

    private int commoditySize = 0;
    private int shopSize = 0;

    private HashMap<Integer, List<HashMap<String, Object>>> mDatas;
    private LayoutInflater mInflater;
    private Context mContext;

    public SearchAllAdapter(Context context,
            HashMap<Integer, List<HashMap<String, Object>>> data) {
        mDatas = data;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getViewTypeCount() {
        if (commoditySize == 0 || shopSize == 0) {
            return 1;
        }
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (commoditySize == 0) {
            return TYPE_SHOP;
        } else if (shopSize == 0) {
            return TYPE_COMMODITY;
        } else {
            if (position < shopSize) {
                return TYPE_SHOP;
            } else {
                return TYPE_COMMODITY;
            }
        }
    }

    @Override
    public int getCount() {
        LogUtils.d("SearchAllAdapter count: commodity ="
                + mDatas.get(TYPE_COMMODITY).size() + ",shop ="
                + mDatas.get(TYPE_SHOP).size());
        commoditySize = mDatas.get(TYPE_COMMODITY).size();
        shopSize = mDatas.get(TYPE_SHOP).size();
        return commoditySize + shopSize;
    }

    @Override
    public Object getItem(int position) {
        int type = getItemViewType(position);
        switch (type) {
        case TYPE_COMMODITY:
            return mDatas.get(TYPE_COMMODITY);
        case TYPE_SHOP:
            return mDatas.get(TYPE_SHOP);
        default:
            break;
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder_Shop holder_Shop = null;
        ViewHolder_Commodity holder_Commodity = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
            case TYPE_COMMODITY:
                convertView = mInflater.inflate(R.layout.home_item, parent,
                        false);
                holder_Commodity = new ViewHolder_Commodity();
                holder_Commodity.icon = (ImageView) convertView
                        .findViewById(R.id.id_home_item_image);
                holder_Commodity.title = (TextView) convertView
                        .findViewById(R.id.id_home_item_title);
                holder_Commodity.intro = (TextView) convertView
                        .findViewById(R.id.id_home_item_intro);
                holder_Commodity.price = (TextView) convertView
                        .findViewById(R.id.id_home_item_price);
//                holder_Commodity.marketPrice = (TextView) convertView
//                        .findViewById(R.id.id_home_item_marketprice);
//                holder_Commodity.score = (TextView) convertView
//                        .findViewById(R.id.id_home_item_score);
                holder_Commodity.salenum = (TextView) convertView
                        .findViewById(R.id.id_home_item_salenum);
//                holder_Commodity.address = (TextView) convertView
//                        .findViewById(R.id.id_home_item_address);
                holder_Commodity.distance = (TextView) convertView
                        .findViewById(R.id.id_home_item_distance);
                convertView.setTag(holder_Commodity);
                break;
            case TYPE_SHOP:
                convertView = mInflater.inflate(R.layout.business_item, parent,
                        false);
                holder_Shop = new ViewHolder_Shop();
                holder_Shop.itemImage = (ImageView) convertView
                        .findViewById(R.id.id_business_item_image);
                holder_Shop.itemName = (TextView) convertView
                        .findViewById(R.id.id_business_item_name);
                holder_Shop.itemIntro = (TextView) convertView
                        .findViewById(R.id.id_business_item_intro);
                holder_Shop.itemRating = (RatingBar) convertView
                        .findViewById(R.id.id_business_item_rating_bar);
                holder_Shop.itemAddress = (TextView) convertView
                        .findViewById(R.id.id_business_item_address);
                holder_Shop.itemTalkNum = (TextView) convertView
                        .findViewById(R.id.id_business_item_talknum);
                holder_Shop.itemDistance = (TextView) convertView
                        .findViewById(R.id.id_business_item_distance);
                convertView.setTag(holder_Shop);
                break;
            default:
                break;
            }
        } else {
            switch (type) {
            case TYPE_COMMODITY:
                holder_Commodity = (ViewHolder_Commodity) convertView.getTag();
                break;
            case TYPE_SHOP:
                holder_Shop = (ViewHolder_Shop) convertView.getTag();
                break;
            default:
                break;
            }
        }

        int A_index = position;
        if (commoditySize > 0 && shopSize > 0) {
            if (position < shopSize) {
                A_index = position;
            } else {
                A_index = position - shopSize;
            }
        }

        LogUtils.d("getView type = " + type + ",position=" + position
                + ",index=" + A_index);
        switch (type) {
        case TYPE_COMMODITY:
            List<HashMap<String, Object>> commodityData = mDatas
                    .get(TYPE_COMMODITY);
            String iconUrl = commodityData.get(A_index).get("commodityIcon")
                    .toString();
            if (!TextUtils.isEmpty(iconUrl)) {
                Picasso.with(mContext).load(iconUrl)
                        .placeholder(R.drawable.ic_default)
                        .error(R.drawable.ic_default)
                        .into(holder_Commodity.icon);
            } else {
                Picasso.with(mContext).load(R.drawable.ic_default)
                        .into(holder_Commodity.icon);
            }
            holder_Commodity.title.setText(commodityData.get(A_index)
                    .get("commodityName").toString());
            holder_Commodity.intro.setText(commodityData.get(A_index)
                    .get("commodityIntro").toString());
            String price = commodityData.get(A_index).get("commodityPrice")
                    .toString();
            String marketPrice = commodityData.get(A_index)
                    .get("commodityMarketPrice").toString();
//            if (Double.valueOf(price) < Double.valueOf(marketPrice)) {
//                holder_Commodity.marketPrice.setVisibility(View.VISIBLE);
//                holder_Commodity.marketPrice.setText(String.format("%1$s元",
//                        marketPrice));
//                holder_Commodity.marketPrice.getPaint().setFlags(
//                        Paint.STRIKE_THRU_TEXT_FLAG);
//                holder_Commodity.price.setText(String.format("%1$s元", price));
//            } else {
//                holder_Commodity.marketPrice.setVisibility(View.INVISIBLE);
//                holder_Commodity.price.setText(String.format("%1$s元", price));
//            }

//            String commodityScore = String
//                    .format(mContext.getResources().getString(
//                            R.string.home_item_score),
//                            commodityData.get(A_index).get("commodityScore")
//                                    .toString());
//            holder_Commodity.score.setText(commodityScore);

            String salenum = String.format(
                    mContext.getResources().getString(
                            R.string.home_item_salenum),
                    commodityData.get(A_index).get("commoditySaleNum")
                            .toString());
            holder_Commodity.salenum.setText(salenum);

            String distance = commodityData
                    .get(A_index).get("commodityDistance").toString();
            String distanceFormat = String.format(
                    mContext.getResources().getString(
                            R.string.business_item_distance), TextUtils.isEmpty(distance) ? "0" : distance);
            holder_Commodity.distance.setText(distanceFormat);//mikes

            break;
        case TYPE_SHOP:
            List<HashMap<String, Object>> shopData = mDatas.get(TYPE_SHOP);
            String icon = shopData.get(A_index).get("shopIcon").toString();
            if (!TextUtils.isEmpty(icon)) {
                Picasso.with(mContext).load(icon)
                        .placeholder(R.drawable.ic_default)
                        .error(R.drawable.ic_default)
                        .into(holder_Shop.itemImage);
            } else {
                Picasso.with(mContext).load(R.drawable.ic_default)
                        .into(holder_Shop.itemImage);
            }

            holder_Shop.itemName.setText(shopData.get(A_index).get("shopName")
                    .toString());
            holder_Shop.itemIntro.setText(shopData.get(A_index)
                    .get("shopIntro").toString());

            String talkNum = shopData.get(A_index).get("shopTalkNum")
                    .toString();
            String formatTalkNum = String.format(mContext.getResources()
                    .getString(R.string.business_item_talknum), TextUtils
                    .isEmpty(talkNum) ? "0" : talkNum);
            holder_Shop.itemTalkNum.setText(formatTalkNum);

            try {
                String rating = shopData.get(A_index).get("shopScore")
                        .toString();
                float formatRating = Float
                        .valueOf(TextUtils.isEmpty(rating) ? "0" : rating);
                holder_Shop.itemRating.setRating(formatRating);
            } catch (NumberFormatException e) {
                holder_Shop.itemRating.setRating(0f);
            }

            // String shopDistance =
            // shopData.get(A_index).get("shopDistance").toString();
            // String formatDistance = String.format(
            // mContext.getResources().getString(
            // R.string.business_item_distance),
            // TextUtils.isEmpty(shopDistance) ? "0.0" : shopDistance);
            // holder_Shop.itemDistance.setText(formatDistance);

            String address = shopData.get(A_index).get("shopAddress") == null ? ""
                    : shopData.get(A_index).get("shopAddress").toString();
            holder_Shop.itemAddress.setText(address);
            break;
        default:
            break;
        }

        return convertView;
    }

    public static class ViewHolder_Shop {
        ImageView itemImage;
        TextView itemName;
        TextView itemIntro;
        RatingBar itemRating;
        TextView itemTalkNum;
        TextView itemAddress;
        TextView itemDistance;
    }

    public static class ViewHolder_Commodity {
        ImageView icon;
        TextView title;
        TextView intro;
        TextView price;
//        TextView marketPrice;
//        TextView score;
        TextView salenum;
//        TextView address;
        TextView distance;
    }
}
