package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc 商品详情
 * Created by likaihang on 16/10/09.
 */
public class ShopDetailInfoBean implements Serializable {
    private static final long serialVersionUID = -6067932200242037643L;
    private String result;
    private String resultNote;
    private ComAndReply comAndReply;

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

    public ComAndReply getComAndReply() {
        return comAndReply;
    }

    public void setComAndReply(ComAndReply comAndReply) {
        this.comAndReply = comAndReply;
    }
/*
           "userAttentionNum;           ": 2,                   //关注量
           "userLevelMark;              ": 0,                        //用户VIP等级 0无等级  1.等级一 2.等级二  3.等级三
           "userPosition;           ": "IT",                        //用户职业
           "userTreasure;           ": "0",                       //用户财富值
           "weekEnd;            ": "周日",                         //服务结束时间  （单位:周）
           "weekStart;          ": "周一"                          //服务开始束时间  （单位:周）
           "badEvalNum;         ": 0,                           //差评个数
           "goodEvalNum;            ": 0,                         //好评个数
           "mediumEvalNum;      ": 0,                    //中评个数
           "bindingMark1;           ": 0,                          //实名认证：0未开通，1已开通
           "bindingMark2;       ": 0,                          //芝麻信用：0未开通，1已开通
           "bindingMark3;           ": 0,                          //职业认证：0未开通，1已开通
           "bindingMark4;           ": 0,                          //企业认证：0未开通，1已开通
           "dayTimeEnd;         ": "18:00",                 //服务结束始时间（单位:小时）
           "dayTimeStart;           ": "08:00",                //服务结束始时间（单位:小时）
           "freightMode;            ": 0,                             //货运方式  0  免运费1货到付款    	（int类型）
           "freightPrice;           ": "",                              //货运价格
           "headicon;               ": "http://192.168.1.122:8080/bossgroupimage/2016-10-26/userIcon/userIconMin/201610261040196M78.jpg",                                     //用户头像
           "isAttentionOff;             ": 1,                            //发布商品人是否被当前用户关注  0,未关注  1,已关注
           "isCollect;              ": 0,                                    //是否收藏  0未收藏1已收藏
           "isOpenfastChat;             ": 1,                        //是否开通急速了 0未开通1开通
           "modes;              ": 1,                                        //服务方式0线上 1 线下2 货运
           "moreGroSurplus;         ": 99999999,      //库存量
           "moreGroUnit;            ": "",                             //单位
           "nickName;           ": "liulong",                      //用户名称
           "proCurrentPrice;            ": "11",                   //商品价格（多个组合就 价格区间）
           "proDistance;            ": "7766.71km",          //距离
           "proId;          ": "bLTghxEbpkjoYY23",       //商品Id
           "proDes;         ":"测试来啦测试来了",        //商品描述
           "proSale;        ":0,                                       //商品售出量
           "userId;          ":"VzjCQFsgahm444ea",     //卖家ID
           "proSeeNum;      ":"2",                             //商品浏览量
           "proName;        ": "lp商品",                        //商品名称
           "serviceSubject;     ": 1,                          //服务主体   0.Boss1.机构2.个人*/
    public class ComAndReply implements Serializable {
        private static final long serialVersionUID = -7861937011514664869L;

  private int userAttentionNum;
  private int userLevelMark;
  private String userPosition;
  private String userTreasure;
  private String weekEnd;
  private String weekStart;
  private int badEvalNum;
  private int goodEvalNum;
  private int mediumEvalNum;
  private int bindingMark1;
  private int bindingMark2;
  private int bindingMark3;
  private int bindingMark4;
  private String dayTimeEnd;
  private String dayTimeStart;
  private int freightMode;
  private String freightPrice;
  private String headicon;
  private int isAttentionOff;
  private int isCollect;
  private int isOpenfastChat;
  private int modes;
  private int moreGroSurplus;
  private String moreGroUnit;
  private String nickName;
  private String proCurrentPrice;
  private String proDistance;
  private String proId;
  private String proDes;
  private int proSale;
  private String userId;
  private String proSeeNum;
  private String proName;
  private int serviceSubject;
  private List<ShopDetailImage> smallImg;

    public int getUserLevelMark() {
        return userLevelMark;
    }

    public void setUserLevelMark(int userLevelMark) {
        this.userLevelMark = userLevelMark;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getUserTreasure() {
        return userTreasure;
    }

    public void setUserTreasure(String userTreasure) {
        this.userTreasure = userTreasure;
    }

    public int getBindingMark1() {
        return bindingMark1;
    }

    public void setBindingMark1(int bindingMark1) {
        this.bindingMark1 = bindingMark1;
    }

    public int getBindingMark2() {
        return bindingMark2;
    }

    public void setBindingMark2(int bindingMark2) {
        this.bindingMark2 = bindingMark2;
    }

    public int getBindingMark3() {
        return bindingMark3;
    }

    public void setBindingMark3(int bindingMark3) {
        this.bindingMark3 = bindingMark3;
    }

    public int getBindingMark4() {
        return bindingMark4;
    }

    public void setBindingMark4(int bindingMark4) {
        this.bindingMark4 = bindingMark4;
    }

    public String getHeadicon() {
        return headicon;
    }

    public void setHeadicon(String headicon) {
        this.headicon = headicon;
    }

    public int getMoreGroSurplus() {
        return moreGroSurplus;
    }

    public void setMoreGroSurplus(int moreGroSurplus) {
        this.moreGroSurplus = moreGroSurplus;
    }

    public String getMoreGroUnit() {
        return moreGroUnit;
    }

    public void setMoreGroUnit(String moreGroUnit) {
        this.moreGroUnit = moreGroUnit;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProCurrentPrice() {
        return proCurrentPrice;
    }

    public void setProCurrentPrice(String proCurrentPrice) {
        this.proCurrentPrice = proCurrentPrice;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProDes() {
        return proDes;
    }

    public void setProDes(String proDes) {
        this.proDes = proDes;
    }

    public int getProSale() {
        return proSale;
    }

    public void setProSale(int proSale) {
        this.proSale = proSale;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProSeeNum() {
        return proSeeNum;
    }

    public void setProSeeNum(String proSeeNum) {
        this.proSeeNum = proSeeNum;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getIsAttentionOff() {
            return isAttentionOff;
        }

        public void setIsAttentionOff(int isAttentionOff) {
            this.isAttentionOff = isAttentionOff;
        }

        public int getUserAttentionNum() {
            return userAttentionNum;
        }

        public void setUserAttentionNum(int userAttentionNum) {
            this.userAttentionNum = userAttentionNum;
        }

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public int getIsOpenfastChat() {
            return isOpenfastChat;
        }

        public void setIsOpenfastChat(int isOpenfastChat) {
            this.isOpenfastChat = isOpenfastChat;
        }

        public List<ShopDetailImage> getSmallImg() {
            return smallImg;
        }

        public void setSmallImg(List<ShopDetailImage> smallImg) {
            this.smallImg = smallImg;
        }

        public int getBadEvalNum() {
            return badEvalNum;
        }

        public void setBadEvalNum(int badEvalNum) {
            this.badEvalNum = badEvalNum;
        }

        public String getDayTimeEnd() {
            return dayTimeEnd;
        }

        public void setDayTimeEnd(String dayTimeEnd) {
            this.dayTimeEnd = dayTimeEnd;
        }

        public String getDayTimeStart() {
            return dayTimeStart;
        }

        public void setDayTimeStart(String dayTimeStart) {
            this.dayTimeStart = dayTimeStart;
        }

        public int getFreightMode() {
            return freightMode;
        }

        public void setFreightMode(int freightMode) {
            this.freightMode = freightMode;
        }

        public String getFreightPrice() {
            return freightPrice;
        }

        public void setFreightPrice(String freightPrice) {
            this.freightPrice = freightPrice;
        }

        public int getGoodEvalNum() {
            return goodEvalNum;
        }

        public void setGoodEvalNum(int goodEvalNum) {
            this.goodEvalNum = goodEvalNum;
        }

        public int getMediumEvalNum() {
            return mediumEvalNum;
        }

        public void setMediumEvalNum(int mediumEvalNum) {
            this.mediumEvalNum = mediumEvalNum;
        }

        public int getModes() {
            return modes;
        }

        public void setModes(int modes) {
            this.modes = modes;
        }

        public String getProDistance() {
            return proDistance;
        }

        public void setProDistance(String proDistance) {
            this.proDistance = proDistance;
        }

        public int getServiceSubject() {
            return serviceSubject;
        }

        public void setServiceSubject(int serviceSubject) {
            this.serviceSubject = serviceSubject;
        }

        public String getWeekEnd() {
            return weekEnd;
        }

        public void setWeekEnd(String weekEnd) {
            this.weekEnd = weekEnd;
        }

        public String getWeekStart() {
            return weekStart;
        }

        public void setWeekStart(String weekStart) {
            this.weekStart = weekStart;
        }
    }

}
