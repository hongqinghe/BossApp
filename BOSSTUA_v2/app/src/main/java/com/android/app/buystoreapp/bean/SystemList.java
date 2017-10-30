package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc
 * Created by likaihang on 16/11/14.
 */
public class SystemList implements Serializable{
    private static final long serialVersionUID = 557928738272014484L;
    /*
    *           "dates         	//消息记录生成日期
                "formatTime       //消息记录生成日期【处理过】   	"image":"http://192.168.1.122:8080/bossgroupimage/bossTicketBuy/logo.png",
                "message           //消息标题
                "messageContent     //消息内容
                "messageRecordId    //消息记录ID

    * */
        private String dates;
        private String formatTime;
        private String message;
        private String messageContent;
        private String messageRecordId;
    private int orderState;

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getFormatTime() {
        return formatTime;
    }

    public void setFormatTime(String formatTime) {
        this.formatTime = formatTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageRecordId() {
        return messageRecordId;
    }

    public void setMessageRecordId(String messageRecordId) {
        this.messageRecordId = messageRecordId;
    }
}
