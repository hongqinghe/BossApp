package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class GsonTalkAssessCmd implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 76969074518122414L;
    private String cmd;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 商品ID
     */
    private String commodityID;
    /**
     * 评论内容
     */
    private String talkContent;
    /**
     * 评论图片
     */
    private String talkImage;
    /**
     * 得分 满分5分
     */
    private String rateScore;

    public GsonTalkAssessCmd(String cmd, String userName, String commodityID,
            String talkContent, String talkImage, String rateScore) {
        super();
        this.cmd = cmd;
        this.userName = userName;
        this.commodityID = commodityID;
        this.talkContent = talkContent;
        this.talkImage = talkImage;
        this.rateScore = rateScore;
    }

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

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
    }

    public String getTalkContent() {
        return talkContent;
    }

    public void setTalkContent(String talkContent) {
        this.talkContent = talkContent;
    }

    public String getTalkImage() {
        return talkImage;
    }

    public void setTalkImage(String talkImage) {
        this.talkImage = talkImage;
    }

    public String getRateScore() {
        return rateScore;
    }

    public void setRateScore(String rateScore) {
        this.rateScore = rateScore;
    }

    @Override
    public String toString() {
        return "GsonTalkAssessCmd [cmd=" + cmd + ", userName=" + userName
                + ", commodityID=" + commodityID + ", talkContent="
                + talkContent + ", talkImage=" + talkImage + ", rateScore="
                + rateScore + "]";
    }

}
