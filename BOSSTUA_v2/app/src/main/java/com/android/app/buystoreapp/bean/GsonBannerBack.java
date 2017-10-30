package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/10/24.
 */
/*{
  "proRecommendImg": [
    {
      "proId": "5O0KYMFU2AHa0TSi",                           //商品Id
      "proImageId": "5HSaKqur2iE061Q8",                   //图片Id
      "proImageUrl": "http://192.168.16.115:8080/bossgroupimage/2016-10-23/proImgPath/201610231030586NrL.jpg"                                                                 //图片路径
    }
  ]
  "imageCount": 2,                                                          //图片个数
  "result": "0",                                                                   //  0成功 1失败
  "resultNote": "展示成功"                                             //展示成功 或展示失败
}
*/
public class GsonBannerBack implements Serializable{
    private static final long serialVersionUID = 8456581128612886017L;
    private String result;
    private String resultNote;
    private int imageCount;
    private List<ProRecommendImg> proRecommendImg;

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

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public List<ProRecommendImg> getProRecommendImg() {
        return proRecommendImg;
    }

    public void setProRecommendImg(List<ProRecommendImg> proRecommendImg) {
        this.proRecommendImg = proRecommendImg;
    }

    public class ProRecommendImg {
       private String proId;
       private String proImageId;
       private String proImageUrl;
        private String proName;

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
        }

        public String getProImageId() {
            return proImageId;
        }

        public void setProImageId(String proImageId) {
            this.proImageId = proImageId;
        }

        public String getProImageUrl() {
            return proImageUrl;
        }

        public void setProImageUrl(String proImageUrl) {
            this.proImageUrl = proImageUrl;
        }
    }
}
