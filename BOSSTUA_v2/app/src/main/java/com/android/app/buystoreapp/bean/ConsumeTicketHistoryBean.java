package com.android.app.buystoreapp.bean;

import java.io.Serializable;

public class ConsumeTicketHistoryBean implements Serializable{
	private static final long serialVersionUID = 6602758639507338340L;
	private int payCount;
	public int getPayCount() {
		return payCount;
	}
	public void setPayCount(int payCount) {
		this.payCount = payCount;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	private String payDate;
	
}
