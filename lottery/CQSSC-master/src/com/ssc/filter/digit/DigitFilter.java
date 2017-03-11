package com.ssc.filter.digit;

import java.util.List;

import com.ssc.filter.Filter;

public abstract class DigitFilter implements Filter {
	
	public static final String DIGIT_UNITS = "5";
	public static final String DIGIT_TENS = "4";
	public static final String DIGIT_HUNDREDS = "3";
	public static final String DIGIT_THOUSANDS = "2";
	public static final String DIGIT_TENTHOUSANDS = "1";

	private String lotteryNumber;
	
	private String filterResult;
	
	public void loadData(Object data) {
		lotteryNumber = (String)data;
	}

	public abstract List dofilter() throws Exception;

	public String getResult() {
		return filterResult;
	}
	
	public String getLotteryNumber() {
		return lotteryNumber;
	}
	
	public void setFilterResult(String filterResult) {
		this.filterResult = filterResult;
	}
}
