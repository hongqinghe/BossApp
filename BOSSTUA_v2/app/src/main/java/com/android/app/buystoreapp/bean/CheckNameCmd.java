package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 尚帅波 on 2016/10/22.
 */
public class CheckNameCmd implements Serializable {


    private static final long serialVersionUID = 2345076952729479615L;
    /**
     * cmd : CheckName
     * userId : MqSbXioihewoIWO3
     * username : liulong
     * cardIdNumber : 12334534
     * cardList : [{"id":"1zQpBB7n58e4gA2e","cardUrl":"bosstuan/a.jpg","cardType":"1"}]
     */

    private String cmd;
    private String userId;
    private String username;
    private String cardIdNumber;
    /**
     * id : 1zQpBB7n58e4gA2e
     * cardUrl : bosstuan/a.jpg
     * cardType : 1
     */

    private List<CardListBean> cardList;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCardIdNumber() {
        return cardIdNumber;
    }

    public void setCardIdNumber(String cardIdNumber) {
        this.cardIdNumber = cardIdNumber;
    }

    public List<CardListBean> getCardList() {
        return cardList;
    }

    public void setCardList(List<CardListBean> cardList) {
        this.cardList = cardList;
    }

    public static class CardListBean {
        private String id;
        private String cardUrl;
        private String cardType;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCardUrl() {
            return cardUrl;
        }

        public void setCardUrl(String cardUrl) {
            this.cardUrl = cardUrl;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }
    }
}
