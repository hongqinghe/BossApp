package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class GsonShopDetailInfoBack implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 8912309667913515289L;
    private String result;
    private String resultNote;
    /**
     * 0 收藏 1 未收藏
     */
    private String isFavourite;
    /**
     * 商品推荐图片
     */
    private List<commodityImage> commodityImageList;
    /**
     * 商品详细
     */
    private String commodityDetail;
    /**
     * 规格参数
     */
    private String commoditySize;
    /**
     * 评价
     */
    private List<Talk> talkList;
    
    /**
     * 关于
     */
    private Aboutus aboutus;
   

    public Aboutus getAboutus() {
		return aboutus;
	}

	public void setAboutus(Aboutus aboutus) {
		this.aboutus = aboutus;
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

    public String getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        this.isFavourite = isFavourite;
    }

    public List<commodityImage> getCommodityImageList() {
        return commodityImageList;
    }

    public void setCommodityImageList(List<commodityImage> commodityImageList) {
        this.commodityImageList = commodityImageList;
    }

    public String getCommodityDetail() {
        return commodityDetail;
    }

    public void setCommodityDetail(String commodityDetail) {
        this.commodityDetail = commodityDetail;
    }

    public String getCommoditySize() {
        return commoditySize;
    }

    public void setCommoditySize(String commoditySize) {
        this.commoditySize = commoditySize;
    }

    public List<Talk> getTalkList() {
        return talkList;
    }

    public void setTalkList(List<Talk> talkList) {
        this.talkList = talkList;
    }

    @Override
    public String toString() {
        return "GsonShopDetailInfoBack [result=" + result + ", resultNote="
                + resultNote + ", isFavourite=" + isFavourite
                + ", commodityImageList=" + commodityImageList
                + ", commodityDetail=" + commodityDetail + ", commoditySize="
                + commoditySize + ", talkList=" + talkList + "]";
    }

    public class commodityImage implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = -295711393000752079L;
        /**
         * 商品图片地址
         */
        private String commodityImageUrl;

        public String getCommodityImageUrl() {
            return commodityImageUrl;
        }

        public void setCommodityImageUrl(String commodityImageUrl) {
            this.commodityImageUrl = commodityImageUrl;
        }

        @Override
        public String toString() {
            return "commodityImage [commodityImageUrl=" + commodityImageUrl
                    + "]";
        }

    }
    
    public class Aboutus implements Serializable{
        private static final long serialVersionUID = 3785016258739292776L;
        private String androidAddress;
    	private String iosAddress;
    	private int isFree;
    	private int needNum;
    	private String pcAddress;
    	private String phone;
    	private String proId;
    	private String qq;
    	private String remark;
    	private String weixin;
		public String getAndroidAddress() {
			return androidAddress;
		}
		public void setAndroidAddress(String androidAddress) {
			this.androidAddress = androidAddress;
		}
		public String getIosAddress() {
			return iosAddress;
		}
		public void setIosAddress(String iosAddress) {
			this.iosAddress = iosAddress;
		}
		public int getIsFree() {
			return isFree;
		}
		public void setIsFree(int isFree) {
			this.isFree = isFree;
		}
		public int getNeedNum() {
			return needNum;
		}
		public void setNeedNum(int needNum) {
			this.needNum = needNum;
		}
		public String getPcAddress() {
			return pcAddress;
		}
		public void setPcAddress(String pcAddress) {
			this.pcAddress = pcAddress;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getProId() {
			return proId;
		}
		public void setProId(String proId) {
			this.proId = proId;
		}
		public String getQq() {
			return qq;
		}
		public void setQq(String qq) {
			this.qq = qq;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getWeixin() {
			return weixin;
		}
		public void setWeixin(String weixin) {
			this.weixin = weixin;
		}
    }

    public class Talk implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 5806578588224924573L;
        /**
         * 评论者账户
         */
        private String talkUserName;
        /**
         * 评论者昵称
         */
        private String talkNickName;
        /**
         * 评论者头像
         */
        private String talkIcon;
        /**
         * 用户评论的图片
         */
        private String talkImage;
        /**
         * 评论内容
         */
        private String talkContent;
        /**
         * 评论时间 年月日
         */
        private String talkDate;

        public String getTalkUserName() {
            return talkUserName;
        }

        public void setTalkUserName(String talkUserName) {
            this.talkUserName = talkUserName;
        }

        public String getTalkNickName() {
            return talkNickName;
        }

        public void setTalkNickName(String talkNickName) {
            this.talkNickName = talkNickName;
        }

        public String getTalkIcon() {
            return talkIcon;
        }

        public void setTalkIcon(String talkIcon) {
            this.talkIcon = talkIcon;
        }

        public String getTalkImage() {
            return talkImage;
        }

        public void setTalkImage(String talkImage) {
            this.talkImage = talkImage;
        }

        public String getTalkContent() {
            return talkContent;
        }

        public void setTalkContent(String talkContent) {
            this.talkContent = talkContent;
        }

        public String getTalkDate() {
            return talkDate;
        }

        public void setTalkDate(String talkDate) {
            this.talkDate = talkDate;
        }

        @Override
        public String toString() {
            return "talk [talkUserName=" + talkUserName + ", talkNickName="
                    + talkNickName + ", talkIcon=" + talkIcon + ", talkImage="
                    + talkImage + ", talkContent=" + talkContent
                    + ", talkDate=" + talkDate + "]";
        }

    }
}
