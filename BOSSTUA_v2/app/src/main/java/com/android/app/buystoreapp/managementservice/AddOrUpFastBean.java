package com.android.app.buystoreapp.managementservice;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class AddOrUpFastBean implements Serializable{
    private static final long serialVersionUID = -6978498298419496401L;
    /**
     * cmd : addFastChat
     * userid : 6fMsIbCwfG5rJtbI
     * phone : 13022221111
     * tell : 010-124232
     * QQ : 716283821
     * we_chat : lp324rds
     * email : 87612@qq.com
     * imagepath : [{"id":"EXs32dweEUXK73","webrootpath":"saas.jpg","imageType":"0"}]
     * note : bf
     * status : 1
     */

    private String cmd;
    private String userid;
    private String phone;
    private String tell;
    private String QQ;
    private String we_chat;
    private String email;
    private String note;
    private int status;
    /**
     * id : EXs32dweEUXK73
     * webrootpath : saas.jpg
     * imageType : 0
     */

    public List<ImagepathBean> imagepath;

    public AddOrUpFastBean( String note, String we_chat, String tell, String cmd, String phone, String QQ, String email, int status, String userid,List<ImagepathBean> imagepath) {
        this.imagepath = imagepath;
        this.note = note;
        this.we_chat = we_chat;
        this.tell = tell;
        this.userid = userid;
        this.cmd = cmd;
        this.phone = phone;
        this.QQ = QQ;
        this.email = email;
        this.status = status;
    }

    public AddOrUpFastBean() {
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getWe_chat() {
        return we_chat;
    }

    public void setWe_chat(String we_chat) {
        this.we_chat = we_chat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ImagepathBean> getImagepath() {
        return imagepath;
    }

    public void setImagepath(List<ImagepathBean> imagepath) {
        this.imagepath = imagepath;
    }

    public static class ImagepathBean {
        private String id;
        private String webrootpath;
        private String imageType;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWebrootpath() {
            return webrootpath;
        }

        public void setWebrootpath(String webrootpath) {
            this.webrootpath = webrootpath;
        }

        public String getImageType() {
            return imageType;
        }

        public void setImageType(String imageType) {
            this.imageType = imageType;
        }
    }

//
//    /**
//     * cmd : UpFastChat
//     * chatId : 4gy3MrrA3R65KhBW
//     * phone : 9
//     * tell : 9
//     * qq : 9
//     * we_chat : 9
//     * email : 9
//     * note : 9
//     * status : 0
//     * userid : MqSbXB9uB7774I3Q
//     * imagepath : [{"webrootpath":"www.png","imageType":"1"}]
//     */
//
//    private String cmd;
//    private String chatId;
//    private String phone;
//    private String tell;
//    private String qq;
//    private String we_chat;
//    private String email;
//    private String note;
//    private int status;
//    private String userid;
//    /**
//     * webrootpath : www.png
//     * imageType : 1
//     */
//
//    public List<ImagepathBean> imagepath;
//
//    public AddOrUpFastBean() {
//    }
///**增加急速聊*/
//    public AddOrUpFastBean(String cmd, String phone, String tell, String qq, String we_chat, String email, String note, int status, String userid, List<ImagepathBean> imagepath) {
//        this.cmd = cmd;
//        this.phone = phone;
//        this.tell = tell;
//        this.qq = qq;
//        this.we_chat = we_chat;
//        this.email = email;
//        this.note = note;
//        this.status = status;
//        this.userid = userid;
//        this.imagepath = imagepath;
//    }
///**修改急速聊*/
//    public AddOrUpFastBean(String cmd, String chatId, String phone, String tell, String qq, String we_chat, String email, String note, int status, String userid, List<ImagepathBean> imagepath) {
//        this.cmd = cmd;
//        this.chatId = chatId;
//        this.phone = phone;
//        this.tell = tell;
//        this.qq = qq;
//        this.we_chat = we_chat;
//        this.email = email;
//        this.note = note;
//        this.status = status;
//        this.userid = userid;
//        this.imagepath = imagepath;
//    }
//
//    public String getCmd() {
//        return cmd;
//    }
//
//    public void setCmd(String cmd) {
//        this.cmd = cmd;
//    }
//
//    public String getChatId() {
//        return chatId;
//    }
//
//    public void setChatId(String chatId) {
//        this.chatId = chatId;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getTell() {
//        return tell;
//    }
//
//    public void setTell(String tell) {
//        this.tell = tell;
//    }
//
//    public String getQq() {
//        return qq;
//    }
//
//    public void setQq(String qq) {
//        this.qq = qq;
//    }
//
//    public String getWe_chat() {
//        return we_chat;
//    }
//
//    public void setWe_chat(String we_chat) {
//        this.we_chat = we_chat;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getNote() {
//        return note;
//    }
//
//    public void setNote(String note) {
//        this.note = note;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public String getUserid() {
//        return userid;
//    }
//
//    public void setUserid(String userid) {
//        this.userid = userid;
//    }
//
//    public List<ImagepathBean> getImagepath() {
//        return imagepath;
//    }
//
//    public void setImagepath(List<ImagepathBean> imagepath) {
//        this.imagepath = imagepath;
//    }
//
//    public static class ImagepathBean {
//        private String webrootpath;
//        private String imageType;
//
//        public String getWebrootpath() {
//            return webrootpath;
//        }
//
//        public void setWebrootpath(String webrootpath) {
//            this.webrootpath = webrootpath;
//        }
//
//        public String getImageType() {
//            return imageType;
//        }
//
//        public void setImageType(String imageType) {
//            this.imageType = imageType;
//        }
//    }
}
