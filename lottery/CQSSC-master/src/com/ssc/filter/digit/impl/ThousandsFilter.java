package com.ssc.filter.digit.impl;

import java.util.List;

import com.ssc.filter.digit.DigitFilter;

public class ThousandsFilter extends DigitFilter {

	@Override
	public List dofilter() throws Exception {
		String lotteryNumber = getLotteryNumber();
		String keyNum = Character.toString(lotteryNumber.toCharArray()[1]);
		setFilterResult(keyNum);
		return null;
	}

}
