package com.ssc.filter.impl;

import com.ssc.filter.LocateDirElection;
import com.ssc.filter.digit.DigitFilter;

public class BigSmallFilter extends LocateDirElection {
	
	public BigSmallFilter(int lowerLimit, int upperLimit, DigitFilter digitfilter) {
		super(lowerLimit, upperLimit, digitfilter);
	}

	protected boolean matchRule(String newNum, String pushedNum) {
		if (Integer.valueOf(newNum) >= 5 && Integer.valueOf(pushedNum) >= 5) {
			return true;
		}
		if (Integer.valueOf(newNum) < 5 && Integer.valueOf(pushedNum) < 5) {
			return true;
		}
		return false;
	}
}
