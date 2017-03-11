package com.ssc.filter.digit.impl;

import java.util.List;

import com.ssc.filter.digit.DigitFilter;

public class HundredsFilter extends DigitFilter {

	@Override
	public List dofilter() throws Exception {
		String lotteryNumber = getLotteryNumber();
		String keyNum = Character.toString(lotteryNumber.toCharArray()[2]);
		setFilterResult(keyNum);
		return null;
	}

}
