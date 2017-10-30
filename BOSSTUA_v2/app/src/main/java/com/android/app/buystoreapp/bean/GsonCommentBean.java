package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 尚帅波 on 2016/10/6.
 */
public class GsonCommentBean implements Serializable{
    private static final long serialVersionUID = 3965183483970831942L;


    /**
     * badEvalNum : 0
     * goodEvalNum : 2
     * imgIsNotNullNum : 1
     * mediumEvalNum : 0
     * allEvalNum : 122
     * commodityEvalList : [{"userIcon":"asdasdasdasd","payDate":"2016-09-30","proName":"鞋子",
     * "moreName ":"一批鞋子","replyId":"123","replyName":"李四","replyContent":"回复回复",
     * "evalAnonymity":"0","evalContent":"aadfff","evalDate":"2016-09-30",
     * "evalId":"P19FmRMAG8yTSn04","evalLevel":"2","evalUserNickname":"boss_32",
     * "listimg":[{"id":"dfdsgeee28yTSn04","webrootpath":"20160930181348h5pl.jpg"}]}]
     * result : 0
     * resultNote : 商品评价加载成功
     */
    private int totaPage;  //  总页数
    private int badEvalNum;
    private int goodEvalNum;
    private int imgIsNotNullNum;
    private int mediumEvalNum;
    private int allEvalNum;
    private String result;
    private String resultNote;

    public int getTotaPage() {
        return totaPage;
    }

    public void setTotaPage(int totaPage) {
        this.totaPage = totaPage;
    }

    /**
     * userIcon : asdasdasdasd
     * payDate : 2016-09-30
     * proName : 鞋子
     * moreName  : 一批鞋子
     * replyId : 123
     * replyName : 李四
     * replyContent : 回复回复
     * evalAnonymity : 0
     * evalContent : aadfff
     * evalDate : 2016-09-30
     * evalId : P19FmRMAG8yTSn04
     * evalLevel : 2
     * evalUserNickname : boss_32
     * listimg : [{"id":"dfdsgeee28yTSn04","webrootpath":"20160930181348h5pl.jpg"}]
     */

    private List<CommodityEvalList> commodityEvalList;

    public int getBadEvalNum() {
        return badEvalNum;
    }

    public void setBadEvalNum(int badEvalNum) {
        this.badEvalNum = badEvalNum;
    }

    public int getGoodEvalNum() {
        return goodEvalNum;
    }

    public void setGoodEvalNum(int goodEvalNum) {
        this.goodEvalNum = goodEvalNum;
    }

    public int getImgIsNotNullNum() {
        return imgIsNotNullNum;
    }

    public void setImgIsNotNullNum(int imgIsNotNullNum) {
        this.imgIsNotNullNum = imgIsNotNullNum;
    }

    public int getMediumEvalNum() {
        return mediumEvalNum;
    }

    public void setMediumEvalNum(int mediumEvalNum) {
        this.mediumEvalNum = mediumEvalNum;
    }

    public int getAllEvalNum() {
        return allEvalNum;
    }

    public void setAllEvalNum(int allEvalNum) {
        this.allEvalNum = allEvalNum;
    }

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

    public List<CommodityEvalList> getCommodityEvalList() {
        return commodityEvalList;
    }

    public void setCommodityEvalList(List<CommodityEvalList> commodityEvalList) {
        this.commodityEvalList = commodityEvalList;
    }

    /**
     * 评价信息
     */
    public static class CommodityEvalList implements Serializable{
        private static final long serialVersionUID = 270433028811445825L;
        private String userIcon;
        private String payDate;
        private String proName;
        private String moreName;
        private String replyId;
        private String replyName;
        private String replyContent;
        private String evalAnonymity;
        private String evalContent;
        private String evalDate;
        private String evalId;
        private String evalLevel;
        private String evalUserNickname;
        /**
         * id : dfdsgeee28yTSn04
         * webrootpath : 20160930181348h5pl.jpg
         */

        private List<ListimgBean> listimg;

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getPayDate() {
            return payDate;
        }

        public void setPayDate(String payDate) {
            this.payDate = payDate;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getMoreName() {
            return moreName;
        }

        public void setMoreName(String moreName) {
            this.moreName = moreName;
        }

        public String getReplyId() {
            return replyId;
        }

        public void setReplyId(String replyId) {
            this.replyId = replyId;
        }

        public String getReplyName() {
            return replyName;
        }

        public void setReplyName(String replyName) {
            this.replyName = replyName;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public String getEvalAnonymity() {
            return evalAnonymity;
        }

        public void setEvalAnonymity(String evalAnonymity) {
            this.evalAnonymity = evalAnonymity;
        }

        public String getEvalContent() {
            return evalContent;
        }

        public void setEvalContent(String evalContent) {
            this.evalContent = evalContent;
        }

        public String getEvalDate() {
            return evalDate;
        }

        public void setEvalDate(String evalDate) {
            this.evalDate = evalDate;
        }

        public String getEvalId() {
            return evalId;
        }

        public void setEvalId(String evalId) {
            this.evalId = evalId;
        }

        public String getEvalLevel() {
            return evalLevel;
        }

        public void setEvalLevel(String evalLevel) {
            this.evalLevel = evalLevel;
        }

        public String getEvalUserNickname() {
            return evalUserNickname;
        }

        public void setEvalUserNickname(String evalUserNickname) {
            this.evalUserNickname = evalUserNickname;
        }

        public List<ListimgBean> getListimg() {
            return listimg;
        }

        public void setListimg(List<ListimgBean> listimg) {
            this.listimg = listimg;
        }

        /**
         * 评价中的图片信息
         */
        public static class ListimgBean implements Serializable{
            private static final long serialVersionUID = 1035487288013110593L;
            private String id;
            private String webrootpath;

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
        }
    }
}
