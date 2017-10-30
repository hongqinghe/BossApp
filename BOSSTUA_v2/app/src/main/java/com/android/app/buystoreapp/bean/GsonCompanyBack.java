package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/10/24.
 */
/*{
   "cmd":"getCompanyNews"                                           //接口名称
}
返回格式：
{
  "companyNews": [
    {
      "companyNewsTitle": "企业头条的标题",                                                            //企业投胎标题
      "newsId": "1459",                                                                                                    //企业头条的Id
      "userIcon": "http://192.168.16.115:8080defalt_usericon.png",                     //用户的头像
      "userName": "Boss_W4tfW1U23uohmqsO"                                                    //用户昵称
    }
  ],
  "result": "0",                                                                                         //0成功 1 失败
  "resultNote": "企业头条查询成功"                                                   //查询成功或查询失败
}
*/
public class GsonCompanyBack implements Serializable{
    private static final long serialVersionUID = -5196052780851111486L;
    private String result;
    private String resultNote;
    private List<CompanyNews> companyNews;

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

    public List<CompanyNews> getCompanyNews() {
        return companyNews;
    }

    public void setCompanyNews(List<CompanyNews> companyNews) {
        this.companyNews = companyNews;
    }

    public class CompanyNews {
       private String companyNewsTitle;
       private String newsId;
       private String userIcon;
       private String userName;

        public String getCompanyNewsTitle() {
            return companyNewsTitle;
        }

        public void setCompanyNewsTitle(String companyNewsTitle) {
            this.companyNewsTitle = companyNewsTitle;
        }

        public String getNewsId() {
            return newsId;
        }

        public void setNewsId(String newsId) {
            this.newsId = newsId;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
