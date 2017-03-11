package com.ssc.filter.digit.impl;

import java.util.List;

import com.ssc.filter.digit.DigitFilter;

public class TenThousandsFilter extends DigitFilter {

	@Override
	public List dofilter() throws Exception {
		String lotteryNumber = getLotteryNumber();
		String keyNum = Character.toString(lotteryNumber.toCharArray()[0]);
		setFilterResult(keyNum);
		return null;
	}

}
