package com.android.app.buystoreapp;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.bean.GsonMessageBack;
import com.android.app.buystoreapp.bean.LeaveList;
import com.android.app.buystoreapp.bean.OrderList;
import com.android.app.buystoreapp.bean.SystemList;
import com.android.app.buystoreapp.im.ChatActivity;
import com.android.app.buystoreapp.im.Constant;
import com.android.app.buystoreapp.im.MessageListActivity;
import com.android.app.buystoreapp.managementservice.ExplainWebViewActivity;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.util.NetUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class TabMessage extends EaseConversationListFragment {
    private static final int GET_MESSAGE = 0x01;
    private TextView errorText;
    private String thisUser;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_MESSAGE:
                    if (leave != null) {
                        tv_leave_mesage.setText(leave.getTitle());
                        tv_leave_time.setText(leave.getFormatTime());
                    }
                    if (order != null) {
                        tv_order_mesage.setText(order.getMessage());
                        tv_order_time.setText(order.getFormatTime());
                    }
                    if (system != null) {
                        tv_sys_mesage.setText(system.getMessageContent());
                        tv_sys_time.setText(system.getFormatTime());
                    }
                    break;
            }
        }
    };
    private TextView tv_sys_mesage, tv_sys_time, tv_order_mesage, tv_order_time, tv_leave_mesage, tv_leave_time;
    private LeaveList leave;
    private OrderList order;
    private SystemList system;

    @Override
    protected void initView() {
        super.initView();
        View errorView = View.inflate(getActivity(), R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);

        tv_sys_mesage = (TextView) getView().findViewById(R.id.tv_sys_mesage);
        tv_sys_time = (TextView) getView().findViewById(R.id.tv_sys_time);

        tv_order_mesage = (TextView) getView().findViewById(R.id.tv_order_mesage);
        tv_order_time = (TextView) getView().findViewById(R.id.tv_order_time);

        tv_leave_mesage = (TextView) getView().findViewById(R.id.tv_leave_mesage);
        tv_leave_time = (TextView) getView().findViewById(R.id.tv_leave_time);

        getView().findViewById(com.hyphenate.easeui.R.id.rl_mesage_sys).setOnClickListener(this);
        getView().findViewById(com.hyphenate.easeui.R.id.rl_mesage_order).setOnClickListener(this);
        getView().findViewById(com.hyphenate.easeui.R.id.rl_mesage_leave).setOnClickListener(this);
        getView().findViewById(com.hyphenate.easeui.R.id.textView4).setOnClickListener(this);
        thisUser = SharedPreferenceUtils.getCurrentUserInfo(getContext()).getUserId();
        getMessageList();
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getActivity(), MessageListActivity.class);
        if (v.getId() == com.hyphenate.easeui.R.id.rl_mesage_sys) {
            i.putExtra("title", "系统消息");
            i.putExtra("state", 3);
        } else if (v.getId() == com.hyphenate.easeui.R.id.rl_mesage_order) {
            i.putExtra("title", "订单消息");
            i.putExtra("state", 1);
        } else if (v.getId() == com.hyphenate.easeui.R.id.rl_mesage_leave) {
            i.putExtra("title", "留言消息");
            i.putExtra("state", 2);
        }else if (v.getId() == com.hyphenate.easeui.R.id.textView4) {
            Intent intent = new Intent(getActivity(), ExplainWebViewActivity.class);
            intent.putExtra("flag", 6000);
            startActivity(intent);
            return;
        }
        startActivity(i);
    }

    /**
     * 获取消息列表
     *
     * @author likaihang
     * creat at @time 16/11/14 12:57
     */
    private void getMessageList() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "selMessageRecord");
            obj.put("userId", thisUser);
            obj.put("state", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(getContext(), obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("获取消息列表--", new String(bytes));
                Gson gson = new Gson();
                GsonMessageBack gsonMessageBack = gson.fromJson(new String(bytes),
                        new TypeToken<GsonMessageBack>() {
                        }.getType());
                if ("0".equals(gsonMessageBack.getResult())) {
                    if (gsonMessageBack.getLeaveList().size() > 0) {
                        leave = gsonMessageBack.getLeaveList().get(0);
                    }
                    if (gsonMessageBack.getOrderList().size() > 0) {
                        order = gsonMessageBack.getOrderList().get(0);
                    }
                    if (gsonMessageBack.getSystemList().size() > 0) {
                        system = gsonMessageBack.getSystemList().get(0);
                    }
                    mHandler.obtainMessage(GET_MESSAGE).sendToTarget();
                } else {
                    ToastUtil.showMessageDefault(getContext(), gsonMessageBack.getResultNote());
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

   /* @Override
    public void onResume() {
        super.onResume();
        refresh();
    }*/

    @Override
    protected void setUpView() {
        super.setUpView();
        // 注册上下文菜单
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
//                    listItemClickListener.onListItemClicked(conversation);
                String username = conversation.getUserName();
                String userId  = conversation.getLastMessage().getStringAttribute("userId","");
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    // 进入聊天页面
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, username);
                    if (username.equals(userId.toLowerCase())){
                        intent.putExtra(Constant.EXTRA_USER_NAME,conversation.getLastMessage().getStringAttribute("otherName",""));
                        intent.putExtra(Constant.EXTRA_USER_ICON, conversation.getLastMessage().getStringAttribute("otherPrice",""));
                    }else{
                        intent.putExtra(Constant.EXTRA_USER_NAME,conversation.getLastMessage().getStringAttribute("myName",""));
                        intent.putExtra(Constant.EXTRA_USER_ICON, conversation.getLastMessage().getStringAttribute("myPrice",""));
                    }
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())) {
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean handled = false;
        boolean deleteMessage = false;
       /* if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
            handled = true;
        } else */
        if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = true;
            EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
           Log.d("--getUserName()--",tobeDeleteCons.getUserName());
            EMClient.getInstance().chatManager()
                    .deleteConversation(tobeDeleteCons.getUserName(),true);
            refresh();
            /* // 删除此会话
            EMClient.getInstance().deleteConversation(tobeDeleteCons.getUserName(), tobeDeleteCons.isGroup(), deleteMessage);
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
            inviteMessgeDao.deleteMessage(tobeDeleteCons.getUserName());
            refresh();

            // 更新消息未读数
            getActivity().updateUnreadLabel();*/
        }
        return true;
    }

 /*   @Override
    public void onListItemClicked(EMConversation conversation) {
        String username = conversation.getUserName();
        if (username.equals(EMClient.getInstance().getCurrentUser()))
            Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
        else {
            // 进入聊天页面
            Log.d("main", "---username----" + username);
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            if (conversation.isGroup()) {
                if (conversation.getType() == EMConversationType.ChatRoom) {
                    // it's group chat
                    intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                } else {
                    intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                }

            }
            // it's single chat
            intent.putExtra(Constant.EXTRA_USER_ID, username);
            startActivity(intent);
        }
    }
*/

}
