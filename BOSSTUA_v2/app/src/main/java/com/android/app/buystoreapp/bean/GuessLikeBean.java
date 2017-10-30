package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc 猜你喜欢实体类
 * Created by likaihang on 16/09/30.
 */
public class GuessLikeBean implements Serializable {
    private static final long serialVersionUID = -3039190059716687069L;
    private String result;
    private String resultNote;
    private List<LikeProBean> likePro;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<LikeProBean> getLikePro() {
        return likePro;
    }

    public void setLikePro(List<LikeProBean> likePro) {
        this.likePro = likePro;
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public static class LikeProBean implements Serializable{
        private static final long serialVersionUID = 4876333658766663728L;
        private String price;
        private String proImageUrl;
        private String proName;
        private String proId;

        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProImageUrl() {
            return proImageUrl;
        }

        public void setProImageUrl(String proImageUrl) {
            this.proImageUrl = proImageUrl;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }
    }
}
