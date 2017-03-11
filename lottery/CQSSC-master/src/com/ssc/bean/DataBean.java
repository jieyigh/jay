package com.ssc.bean;

public class DataBean {

	private String date;
	
	private String issue;
	
	private String lotteryNumbers;
	
	public DataBean() {
		
	}
	
	public DataBean(String date, String issue, String lotteryNumbers) {
		this.date = date;
		this.issue = issue;
		this.lotteryNumbers = lotteryNumbers;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getLotteryNumbers() {
		return lotteryNumbers;
	}

	public void setLotteryNumbers(String lotteryNumbers) {
		this.lotteryNumbers = lotteryNumbers;
	}
}
