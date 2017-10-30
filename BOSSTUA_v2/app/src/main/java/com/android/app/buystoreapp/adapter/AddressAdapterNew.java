package com.android.app.buystoreapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.AddressBeanNew;
import com.android.app.buystoreapp.setting.AddAddressActivity;

import java.util.List;

/**
 * Created by 尚帅波 on 2016/10/5.
 */
public class AddressAdapterNew extends BaseAdapter {
    private Context context;
    private List<AddressBeanNew.AdressListBean> addressList;

    public AddressAdapterNew(Context context, List<AddressBeanNew.AdressListBean> addressList) {
        this.context = context;
        this.addressList = addressList;
    }
    private IsDefaultInterface isDefaultInterface;

    public void setIsDefaultInterface(IsDefaultInterface isDefaultInterface) {
        this.isDefaultInterface = isDefaultInterface;
    }

    @Override
    public int getCount() {
        return (addressList == null) ? 0 : addressList.size();
    }

    @Override
    public Object getItem(int position) {
        return addressList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      final   ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_address_list, null);
            holder = new ViewHolder();
            holder.tvReceiveName = (TextView) convertView.findViewById(R.id
                    .tv_item_address_recipients_name);
            holder.tvNumber = (TextView) convertView.findViewById(R.id
                    .tv_item_address_phone_number);
            holder.tvReceiveAddress = (TextView) convertView.findViewById(R.id
                    .tv_item_address_harvest_address);
            holder.addressEdit = (ImageButton) convertView.findViewById(R.id
                    .ib_item_address_editor);
            holder.rdBtn = (ImageButton) convertView.findViewById(R.id.iv_address_checked);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final AddressBeanNew.AdressListBean adressListBean = addressList.get(position);
        holder.tvReceiveName.setText(adressListBean.getName());
        holder.tvNumber.setText(adressListBean.getPhone());
        holder.tvReceiveAddress.setText(adressListBean.getReceiverArea() + adressListBean
                .getReceiverStreet() + adressListBean.getAdress());
        holder.addressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddAddressActivity.class);
                intent.putExtra("addressBean", adressListBean);
                intent.putExtra("size",1);
                context.startActivity(intent);
            }
        });
//        holder.rdBtn.setChecked(adressListBean.getIsDefault() == 1 ? true : false);
        if (adressListBean.getIsDefault() == 1){
            holder.rdBtn.setImageResource(R.drawable.ic_car_check);
        }else {
            holder.rdBtn.setImageResource(R.drawable.ic_car_uncheck);
        }
        holder.rdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDefaultInterface.change(position,holder.rdBtn,addressList);
            }
        });
        return convertView;
    }


    class ViewHolder {
        TextView tvReceiveName;
        TextView tvNumber;
        TextView tvReceiveAddress;
        ImageButton addressEdit;
        ImageButton rdBtn;
    }

    public interface IsDefaultInterface
    {
    void change(int i, View view, List<AddressBeanNew.AdressListBean> addressList);
    }
}
