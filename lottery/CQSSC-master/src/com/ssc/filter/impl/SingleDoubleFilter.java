package com.ssc.filter.impl;

import com.ssc.filter.LocateDirElection;
import com.ssc.filter.digit.DigitFilter;

public class SingleDoubleFilter extends LocateDirElection {
	
	public SingleDoubleFilter(int lowerLimit, int upperLimit, DigitFilter digitfilter) {
		super(lowerLimit, upperLimit, digitfilter);
	}

	protected boolean matchRule(String newNum, String pushedNum) {
		if (Integer.valueOf(newNum)%2 == 1 && Integer.valueOf(pushedNum)%2 == 1) {
			return true;
		}
		if (Integer.valueOf(newNum)%2 == 0 && Integer.valueOf(pushedNum)%2 == 0) {
			return true;
		}
		return false;
	}
}
