package com.android.app.buystoreapp.order;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.other.CustomListView;

import java.util.List;

/**
 * Created by 尚帅波 on 2016/9/21.
 */
public class OrderListAdapter extends BaseAdapter {

    private List<OrderBean.OrderlistBean> orderList;
    private Context context;
    private Handler handler;
    private int userStatus;

    public OrderListAdapter(Context context, List<OrderBean.OrderlistBean> orderList, int userStatus, Handler
            handler) {
        this.orderList = orderList;
        this.context = context;
        this.handler = handler;
        this.userStatus = userStatus;
    }

    @Override
    public int getCount() {
        return (orderList == null) ? 0 : orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.order_list_item, null);
            holder = new ViewHolder();
            holder.tv_shopName = (TextView) convertView.findViewById(R.id.tv_shopName);
            holder.tv_orderStatus = (TextView) convertView.findViewById(R.id.tv_orderStatus);
            holder.tv_order_total = (TextView) convertView.findViewById(R.id.tv_order_total);
            holder.lv_order_all = (CustomListView) convertView.findViewById(R.id.lv_order_all);
            holder.btn1_order = (Button) convertView.findViewById(R.id.btn1_order);
            holder.btn2_order = (Button) convertView.findViewById(R.id.btn2_order);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final OrderBean.OrderlistBean order = orderList.get(position);
        holder.tv_shopName.setText(orderList.get(position).getNickname());
        final int status = order.getStatus();
        switch (status) {
            case 0:  //全部

                break;
            case 1:   //待付款
                if (userStatus == 0) {//买家
                    holder.tv_orderStatus.setText("您有订单未付款");
                    holder.btn1_order.setVisibility(View.VISIBLE);
                    holder.btn1_order.setText("取消订单");
                    holder.btn2_order.setText("前去支付");
                    holder.btn1_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    });

                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }
                    });
                } else {//卖家
                    holder.tv_orderStatus.setText("买家拍下未付款");
                    holder.btn1_order.setVisibility(View.VISIBLE);
                    holder.btn1_order.setText("取消订单");
                    holder.btn2_order.setText("聊一聊");
                    holder.btn1_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    });

                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.arg2 = userStatus;
                            msg.what = 4;
                            handler.sendMessage(msg);
                        }
                    });
                }
                break;
            case 2:  //待发货
                if (userStatus == 0) {
                    holder.tv_orderStatus.setText("等待卖家发货");
                    holder.btn1_order.setVisibility(View.VISIBLE);
                    holder.btn1_order.setText("退款");
                    holder.btn2_order.setText("聊一聊");
                    holder.btn1_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 3;
                            handler.sendMessage(msg);
                        }
                    });

                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.arg2 = userStatus;
                            msg.what = 4;
                            handler.sendMessage(msg);
                        }
                    });
                } else {
                    holder.tv_orderStatus.setText("买家已付款，请尽快发货");
                    holder.btn1_order.setVisibility(View.VISIBLE);
                    holder.btn1_order.setText("确认发货");
                    holder.btn2_order.setText("聊一聊");
                    holder.btn1_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 14;
                            handler.sendMessage(msg);
                        }
                    });

                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.arg2 = userStatus;
                            msg.what = 4;
                            handler.sendMessage(msg);
                        }
                    });
                }
                break;
            case 3:  //待收货
                if (userStatus == 0) {
                    holder.tv_orderStatus.setText("您有订单已发货,30天后自动确认收货");
                    holder.btn1_order.setVisibility(View.VISIBLE);
                    holder.btn1_order.setText("查看物流");
                    holder.btn2_order.setText("确认收货");
                    holder.btn1_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 5;
                            handler.sendMessage(msg);
                        }
                    });

                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 6;
                            handler.sendMessage(msg);
                        }
                    });
                } else {
                    holder.tv_orderStatus.setText("等待买家确认收货,30天后自动确认收货");
                    holder.btn1_order.setVisibility(View.GONE);
                    holder.btn2_order.setText("聊一聊");

                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.arg2 = userStatus;
                            msg.what = 4;
                            handler.sendMessage(msg);
                        }
                    });
                }
                break;
            case 4:  //待评价
                if (userStatus == 0) {
                    holder.tv_orderStatus.setText("您有订单待评价");
                    holder.btn1_order.setVisibility(View.VISIBLE);
                    //holder.btn1_order.setText("退款");
                    holder.btn1_order.setVisibility(View.GONE);
                    holder.btn2_order.setText("评价");
                    /*holder.btn1_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 3;
                            handler.sendMessage(msg);
                        }
                    });
*/
                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 8;
                            handler.sendMessage(msg);
                        }
                    });
                } else {
                    holder.tv_orderStatus.setText("买家已确认收货");
                    holder.btn1_order.setVisibility(View.GONE);
                    holder.btn2_order.setText("聊一聊");

                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.arg2 = userStatus;
                            msg.what = 4;
                            handler.sendMessage(msg);
                        }
                    });
                }
                break;
            case 5:  //退款
                if (userStatus == 0) {
                    holder.tv_orderStatus.setText("已申请退款");
                    holder.btn1_order.setVisibility(View.GONE);
                   /* holder.btn2_order.setText("聊一聊");
                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 4;
                            handler.sendMessage(msg);
                        }
                    });*/
                    holder.btn2_order.setText("退款详情");
                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 16;
                            handler.sendMessage(msg);
                        }
                    });
                } else {
                    holder.tv_orderStatus.setText("买家申请退款");
                    /*holder.btn1_order.setVisibility(View.VISIBLE);
                    holder.btn1_order.setText("拒绝退款");
                    holder.btn2_order.setText("确认退款");
                    holder.btn1_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 11;
                            handler.sendMessage(msg);
                        }
                    });
                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 13;
                            handler.sendMessage(msg);
                        }
                    });*/
                    holder.btn1_order.setVisibility(View.GONE);
                    holder.btn2_order.setText("退款详情");
                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 16;
                            handler.sendMessage(msg);
                        }
                    });
                }
                break;
            case 6://发起申诉
                holder.tv_orderStatus.setText("申诉中");
                holder.btn1_order.setVisibility(View.GONE);
               /* holder.btn2_order.setText("聊一聊");
                holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message msg = handler.obtainMessage();
                        msg.arg1 = position;
                        msg.what = 4;
                        handler.sendMessage(msg);
                    }
                });*/
                holder.btn2_order.setText("退款详情");
                holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message msg = handler.obtainMessage();
                        msg.arg1 = position;
                        msg.what = 16;
                        handler.sendMessage(msg);
                    }
                });
                break;
            case 7://处理超时
                holder.tv_orderStatus.setText("处理超时");
                holder.btn1_order.setVisibility(View.GONE);
                holder.btn2_order.setText("删除订单");
                holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message msg = handler.obtainMessage();
                        msg.arg1 = position;
                        msg.what = 7;
                        handler.sendMessage(msg);
                    }
                });
                break;
            case 8://交易完成
                if (userStatus == 0) {
                    holder.tv_orderStatus.setText("交易完成");
                    holder.btn1_order.setVisibility(View.VISIBLE);
                    holder.btn1_order.setText("聊一聊");
                    holder.btn2_order.setText("查看评价");
                    holder.btn1_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.arg2 = userStatus;
                            msg.what = 4;
                            handler.sendMessage(msg);
                        }
                    });

                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 15;
                            handler.sendMessage(msg);
                        }
                    });
                } else {
                    holder.tv_orderStatus.setText("交易完成，您有新的评价");
                    holder.btn1_order.setVisibility(View.VISIBLE);
                    holder.btn1_order.setText("聊一聊");
                    holder.btn2_order.setText("回复评价");
                    holder.btn1_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.arg2 = userStatus;
                            msg.what = 4;
                            handler.sendMessage(msg);
                        }
                    });

                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 9;
                            handler.sendMessage(msg);
                        }
                    });
                }
                break;
            case 9://申诉成功
                holder.tv_orderStatus.setText("申诉成功");
                holder.btn1_order.setVisibility(View.GONE);
                /*holder.btn2_order.setText("聊一聊");
                holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message msg = handler.obtainMessage();
                        msg.arg1 = position;
                        msg.what = 4;
                        handler.sendMessage(msg);
                    }
                });*/
                holder.btn2_order.setText("退款详情");
                holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message msg = handler.obtainMessage();
                        msg.arg1 = position;
                        msg.what = 16;
                        handler.sendMessage(msg);
                    }
                });
                break;
            case 10://申诉失败
                holder.tv_orderStatus.setText("申诉失败");
                holder.btn1_order.setVisibility(View.GONE);
               /* holder.btn2_order.setText("聊一聊");
                holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message msg = handler.obtainMessage();
                        msg.arg1 = position;
                        msg.what = 4;
                        handler.sendMessage(msg);
                    }
                });*/
                holder.btn2_order.setText("退款详情");
                holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message msg = handler.obtainMessage();
                        msg.arg1 = position;
                        msg.what = 16;
                        handler.sendMessage(msg);
                    }
                });
                break;
            case 11://取消订单
                holder.tv_orderStatus.setText("订单已取消");
                holder.btn1_order.setVisibility(View.VISIBLE);
                holder.btn1_order.setText("删除订单");
                holder.btn2_order.setText("聊一聊");
                holder.btn1_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message msg = handler.obtainMessage();
                        msg.arg1 = position;
                        msg.what = 7;
                        handler.sendMessage(msg);
                    }
                });

                holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message msg = handler.obtainMessage();
                        msg.arg1 = position;
                        msg.arg2 = userStatus;
                        msg.what = 4;
                        handler.sendMessage(msg);
                    }
                });
                break;
            case 12://删除订单
                //删除订单状态不显示不作任何处理
                break;
            case 13://拒绝退款
                if (userStatus == 0) {
                    holder.tv_orderStatus.setText("卖家拒绝退款");
                   /* holder.btn1_order.setVisibility(View.VISIBLE);
                    holder.btn1_order.setText("发起申诉");
                    holder.btn2_order.setText("聊一聊");
                    holder.btn1_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 10;
                            handler.sendMessage(msg);
                        }
                    });

                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 4;
                            handler.sendMessage(msg);
                        }
                    });*/
                    holder.btn1_order.setVisibility(View.GONE);
                    holder.btn2_order.setText("退款详情");
                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 16;
                            handler.sendMessage(msg);
                        }
                    });
                } else {
                    holder.tv_orderStatus.setText("您拒绝了买家的退款");
                    holder.btn1_order.setVisibility(View.GONE);
                   /* holder.btn2_order.setText("聊一聊");
                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 4;
                            handler.sendMessage(msg);
                        }
                    });*/
                    holder.btn2_order.setText("退款详情");
                    holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = position;
                            msg.what = 16;
                            handler.sendMessage(msg);
                        }
                    });
                }
                break;
            case 14://退款成功
                holder.tv_orderStatus.setText("退款成功");
                holder.btn1_order.setVisibility(View.GONE);
               /* holder.btn2_order.setText("聊一聊");
                holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message msg = handler.obtainMessage();
                        msg.arg1 = position;
                        msg.what = 4;
                        handler.sendMessage(msg);
                    }
                });*/
                holder.btn2_order.setText("退款详情");
                holder.btn2_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message msg = handler.obtainMessage();
                        msg.arg1 = position;
                        msg.what = 16;
                        handler.sendMessage(msg);
                    }
                });
                break;
        }
        holder.tv_order_total.setText("共" + order.getProNum() + "件商品  合计：￥" +
                order.getOrderAmount() + "(含运费￥" + order.getFreightTotalPrice() + ")");
        final List<OrderProduct> productList = order.getOrderProductList();
        holder.lv_order_all.setAdapter(new OrderProductAdapter(context, productList));

        final String orderId = order.getOrderId();
        holder.lv_order_all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("status", status);
                intent.putExtra("userStatus", userStatus);
                String theAwb = orderList.get(position).getTheAwb();
                String logisticsCode = orderList.get(position).getTheAwb();
                if (!TextUtils.isEmpty(theAwb) && TextUtils.isEmpty(logisticsCode)) {
                    intent.putExtra("theAwb", theAwb);
                    intent.putExtra("logisticsCode", logisticsCode);
                }
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        private TextView tv_shopName, tv_orderStatus, tv_order_total;
        private CustomListView lv_order_all;
        private Button btn1_order, btn2_order;
    }
}
