package com.android.app.buystoreapp.bean;

import java.io.Serializable;

/**
 * $desc 留言item实体
 * Created by likaihang on 16/09/27.
 */
/**
 *
 "crComId": "AkqdPId7H0AB1igv", 留言人id
 "crContent": "nihao",          留言内容
 "crDate": "2016-10-13",        留言时间
 "crId": "z53t9YFIHAz0T9B4",    留言id
 "crNickIcon": "defalt_usericon.png", 留言人头像
 "crNickName": "Boss_琪琪",      留言人昵称
 "crStatus": "3",
 "objNickIcon": "defalt_usericon.png",
 "objNickName": "id要长才厉害",
 "proId": "n6OHEqaYHSaJir37"
 },
 */
public class LeaveMeassageBean implements Serializable {
    private static final long serialVersionUID = 4861556817273462424L;
   /* private String commentContent; //评论内容
    private String commentDate;//评论时间（date类型）
    private String commentIcon;//头像
    private String commentId;//留言人id
    private String commentNickname;//评论人名称
    private String replyContent;//回复内容
    private String replyDate;//回复时间（date类型）
    private String replyNickname;//回复人名称
    private String proCommentId;//留言ID*/
   private String crComId;
    private String crContent;
    private String crDate;
    private String crId;
    private String crNickIcon;
    private String crNickName;
    private String crStatus;
    private String objNickIcon;
    private String objNickName;
    private String proId;
    private String commentUserId;//留言人Id
    private String repleyUserId;//回复人Id

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getRepleyUserId() {
        return repleyUserId;
    }

    public void setRepleyUserId(String repleyUserId) {
        this.repleyUserId = repleyUserId;
    }

    public String getCrComId() {
        return crComId;
    }

    public void setCrComId(String crComId) {
        this.crComId = crComId;
    }

    public String getCrContent() {
        return crContent;
    }

    public void setCrContent(String crContent) {
        this.crContent = crContent;
    }

    public String getCrDate() {
        return crDate;
    }

    public void setCrDate(String crDate) {
        this.crDate = crDate;
    }

    public String getCrId() {
        return crId;
    }

    public void setCrId(String crId) {
        this.crId = crId;
    }

    public String getCrNickIcon() {
        return crNickIcon;
    }

    public void setCrNickIcon(String crNickIcon) {
        this.crNickIcon = crNickIcon;
    }

    public String getCrNickName() {
        return crNickName;
    }

    public void setCrNickName(String crNickName) {
        this.crNickName = crNickName;
    }

    public String getCrStatus() {
        return crStatus;
    }

    public void setCrStatus(String crStatus) {
        this.crStatus = crStatus;
    }

    public String getObjNickIcon() {
        return objNickIcon;
    }

    public void setObjNickIcon(String objNickIcon) {
        this.objNickIcon = objNickIcon;
    }

    public String getObjNickName() {
        return objNickName;
    }

    public void setObjNickName(String objNickName) {
        this.objNickName = objNickName;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }
}
