package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class GsonBuyTicketHistoryBack implements Serializable{
	private static final long serialVersionUID = 8065252036239189751L;
	private int result;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getResultNote() {
		return resultNote;
	}
	public void setResultNote(String resultNote) {
		this.resultNote = resultNote;
	}
	public List<BuyTicketHistoryBean> getBossadd() {
		return bossadd;
	}
	public void setBossadd(List<BuyTicketHistoryBean> bossadd) {
		this.bossadd = bossadd;
	}
	private String resultNote;
	private List<BuyTicketHistoryBean> bossadd;
}
