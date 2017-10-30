package com.android.app.buystoreapp.managementservice;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/15.
 */
public class RapidlyBean implements Serializable{

    private static final long serialVersionUID = 9186206254310660796L;
    /**
     * fastChatList : [{"chatId":"hF1mv0Mu267hCOVP","creatTime":"2016-10-22","email":"fhvh","imgList":[{"createTime":"2016-10-22","id":"4p4cI44E7qyVl5ye","imageType":"0","relevanceId":"hF1mv0Mu267hCOVP","webrootpath":"http://192.168.16.115:8080/bossgroupimage/2016-10-22/FastChatImage/20161022184118Aoav.jpg"},{"createTime":"2016-10-22","id":"9aChbMhDNwBE8Pqt","imageType":"1","relevanceId":"hF1mv0Mu267hCOVP","webrootpath":"http://192.168.16.115:8080/bossgroupimage/2016-10-22/FastChatImage/201610221841176A1x.jpg"}],"note":"ghgg","phone":"13693034344","qq":"555","status":3,"tell":"5555","userid":"MqSbXB9uB7774I3Q","weChat":"rgfg"}]
     * result : 0
     * resultNote : 加载成功
     */

    private String result;
    private String resultNote;
    /**
     * chatId : hF1mv0Mu267hCOVP
     * creatTime : 2016-10-22
     * email : fhvh
     * imgList : [{"createTime":"2016-10-22","id":"4p4cI44E7qyVl5ye","imageType":"0","relevanceId":"hF1mv0Mu267hCOVP","webrootpath":"http://192.168.16.115:8080/bossgroupimage/2016-10-22/FastChatImage/20161022184118Aoav.jpg"},{"createTime":"2016-10-22","id":"9aChbMhDNwBE8Pqt","imageType":"1","relevanceId":"hF1mv0Mu267hCOVP","webrootpath":"http://192.168.16.115:8080/bossgroupimage/2016-10-22/FastChatImage/201610221841176A1x.jpg"}]
     * note : ghgg
     * phone : 13693034344
     * qq : 555
     * status : 3
     * tell : 5555
     * userid : MqSbXB9uB7774I3Q
     * weChat : rgfg
     */

    private List<FastChatListBean> fastChatList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public List<FastChatListBean> getFastChatList() {
        return fastChatList;
    }

    public void setFastChatList(List<FastChatListBean> fastChatList) {
        this.fastChatList = fastChatList;
    }

    public static class FastChatListBean implements Serializable{
        private static final long serialVersionUID = 1349050061832983684L;
        private String chatId;
        private String creatTime;
        private String email;
        private String note;
        private String phone;
        private String qq;
        private int status;
        private String tell;
        private String userid;
        private String weChat;
        /**
         * createTime : 2016-10-22
         * id : 4p4cI44E7qyVl5ye
         * imageType : 0
         * relevanceId : hF1mv0Mu267hCOVP
         * webrootpath : http://192.168.16.115:8080/bossgroupimage/2016-10-22/FastChatImage/20161022184118Aoav.jpg
         */

        private List<ImgListBean> imgList;

        public String getChatId() {
            return chatId;
        }

        public void setChatId(String chatId) {
            this.chatId = chatId;
        }

        public String getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(String creatTime) {
            this.creatTime = creatTime;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTell() {
            return tell;
        }

        public void setTell(String tell) {
            this.tell = tell;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getWeChat() {
            return weChat;
        }

        public void setWeChat(String weChat) {
            this.weChat = weChat;
        }

        public List<ImgListBean> getImgList() {
            return imgList;
        }

        public void setImgList(List<ImgListBean> imgList) {
            this.imgList = imgList;
        }

        public static class ImgListBean implements Serializable{
            private static final long serialVersionUID = -7793396784416527737L;
            private String createTime;
            private String id;
            private String imageType;
            private String relevanceId;
            private String webrootpath;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImageType() {
                return imageType;
            }

            public void setImageType(String imageType) {
                this.imageType = imageType;
            }

            public String getRelevanceId() {
                return relevanceId;
            }

            public void setRelevanceId(String relevanceId) {
                this.relevanceId = relevanceId;
            }

            public String getWebrootpath() {
                return webrootpath;
            }

            public void setWebrootpath(String webrootpath) {
                this.webrootpath = webrootpath;
            }
        }
    }
}
