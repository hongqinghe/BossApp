package com.android.app.buystoreapp.bean;

import java.io.Serializable;
import java.util.List;

public class GsonConsumeTicketHistoryBack implements Serializable{
	private static final long serialVersionUID = -4701960864134919853L;
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
	public List<ConsumeTicketHistoryBean> getBosscut() {
		return bosscut;
	}
	public void setBosscut(List<ConsumeTicketHistoryBean> bosscut) {
		this.bosscut = bosscut;
	}
	private String resultNote;
	private List<ConsumeTicketHistoryBean> bosscut;
}
