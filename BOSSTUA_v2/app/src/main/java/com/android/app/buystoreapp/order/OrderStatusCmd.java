package com.android.app.buystoreapp.order;

import java.io.Serializable;

/**
 * Created by 尚帅波 on 2016/10/11.
 */
public class OrderStatusCmd implements Serializable {
    private static final long serialVersionUID = 840283782424834556L;


    /**
     * cmd : OrderStatus
     * status : 0
     * userId : yW1lG6E2vxoQLsj7
     * style : 0
     */

    private String cmd;
    private String status;
    private String userId;
    private int style;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }
}
