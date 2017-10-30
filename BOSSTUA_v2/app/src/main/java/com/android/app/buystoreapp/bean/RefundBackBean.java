package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shangshuaibo on 2016/12/17 18:18
 */
public class RefundBackBean implements Serializable {


    /**
     * bean : [{"feedMage":"","feedbackId":"","feedbackWhy":"","image":[],"remainingTime":"",
     * "status":"","style":1},{"feedMage":"","feedbackId":"","feedbackWhy":"","image":[],
     * "remainingTime":"","status":"","style":2},{"feedMage":"","feedbackId":"","feedbackWhy":"",
     * "image":[],"remainingTime":"","status":"","style":3}]
     * result : 0
     * resultNote : 获取成功
     */

    private String result;
    private String resultNote;
    /**
     * feedMage :
     * feedbackId :
     * feedbackWhy :
     * image : []
     * remainingTime :
     * status :
     * style : 1
     * style = 1 ;//申请退款---如果身份为0 按钮为取消退款---身份为1 按钮为 同意退款，拒绝退款
     style = 2 ;//退款失败---如果身份为0 按钮为申请申诉---身份为1 按钮为 无
     style = 3 ;//申请申诉---如果身份为0 按钮为取消申诉---身份为1 按钮为 无
     style = 4 ; //申诉成功---如果身份为0 按钮为无----------身份为1 按钮为 无
     style = 5 ;//申诉失败---如果身份为0 按钮为无----------身份为1 按钮为 无
     style = 6 ;//退款成功---如果身份为0 按钮为无----------身份为1 按钮为 无
     isClick /0:不可点击   1:可以点击
     */

    private List<BeanBean> bean;

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

    public List<BeanBean> getBean() {
        return bean;
    }

    public void setBean(List<BeanBean> bean) {
        this.bean = bean;
    }

    public static class BeanBean implements Serializable{
        private String feedMage;
        private String feedbackId;
        private String feedbackWhy;
        private String remainingTime;
        private String status;
        private int style;
        private int isClick;
        private List<OrderFeedbackImageBean> image;

        public String getFeedMage() {
            return feedMage;
        }

        public void setFeedMage(String feedMage) {
            this.feedMage = feedMage;
        }

        public String getFeedbackId() {
            return feedbackId;
        }

        public void setFeedbackId(String feedbackId) {
            this.feedbackId = feedbackId;
        }

        public String getFeedbackWhy() {
            return feedbackWhy;
        }

        public void setFeedbackWhy(String feedbackWhy) {
            this.feedbackWhy = feedbackWhy;
        }

        public String getRemainingTime() {
            return remainingTime;
        }

        public void setRemainingTime(String remainingTime) {
            this.remainingTime = remainingTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getStyle() {
            return style;
        }

        public void setStyle(int style) {
            this.style = style;
        }

        public int getIsClick() {
            return isClick;
        }

        public void setIsClick(int isClick) {
            this.isClick = isClick;
        }

        public List<OrderFeedbackImageBean> getImage() {
            return image;
        }

        public void setImage(List<OrderFeedbackImageBean> image) {
            this.image = image;
        }
    }

    public class OrderFeedbackImageBean implements Serializable{
        private String feedbackImageUrl;//反馈图片地址
        private String feedbackImageMin;//等比例压缩后的图片路径
        private String feedbackImageId;//反馈图片表ID
        public String getFeedbackImageUrl() {
            return feedbackImageUrl;
        }
        public void setFeedbackImageUrl(String feedbackImageUrl) {
            this.feedbackImageUrl = feedbackImageUrl;
        }
        public String getFeedbackImageMin() {
            return feedbackImageMin;
        }
        public void setFeedbackImageMin(String feedbackImageMin) {
            this.feedbackImageMin = feedbackImageMin;
        }
        public String getFeedbackImageId() {
            return feedbackImageId;
        }
        public void setFeedbackImageId(String feedbackImageId) {
            this.feedbackImageId = feedbackImageId;
        }

    }

}
