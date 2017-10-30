package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 尚帅波 on 2016/10/22.
 */
public class CheckArrerCmd implements Serializable{

    private static final long serialVersionUID = 5053958562391747879L;
    /**
     * cmd : checkArrer
     * userName : liulong
     * companyFull : liulongdegongsi
     * phone : 12334534
     * userId : MqSbXioihewoIWO3
     * businessCard : [{"id":"EXs32d532YEJ73","cardUrl":"bosstuan/b.png","cardType":"0"}]
     */

    private String cmd;
    private String userName;
    private String companyFull;
    private String phone;
    private String userId;
    /**
     * id : EXs32d532YEJ73
     * cardUrl : bosstuan/b.png
     * cardType : 0
     */

    private List<BusinessCardBean> businessCard;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyFull() {
        return companyFull;
    }

    public void setCompanyFull(String companyFull) {
        this.companyFull = companyFull;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<BusinessCardBean> getBusinessCard() {
        return businessCard;
    }

    public void setBusinessCard(List<BusinessCardBean> businessCard) {
        this.businessCard = businessCard;
    }

    public static class BusinessCardBean {
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
