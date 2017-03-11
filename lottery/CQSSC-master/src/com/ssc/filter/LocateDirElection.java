package com.ssc.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.ssc.bean.DataBean;
import com.ssc.filter.digit.DigitFilter;

public abstract class LocateDirElection implements Filter {
	
	int lowerLimit;
	
	int upperLimit;
	
	Stack datastack;
	
	List data;
	
	DigitFilter digitfilter;
	
	public LocateDirElection(int lowerLimit, int upperLimit, DigitFilter digitfilter) {
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.digitfilter = digitfilter;
	}
	
	public void loadData(Object data) {
		this.data = (List)data;
		this.datastack = new Stack();
	}

	public List dofilter() throws Exception {
		List filterData = new ArrayList();
		if (data == null || data.size() == 0) {
			throw new Exception("There no data for filter! You may be load data for this operation.");
		}
		Iterator it = data.iterator();
		while(it.hasNext()) {
			DataBean databean = (DataBean)it.next();
			checkData(databean, filterData);
		}
		return filterData;
	}

	private void checkData(DataBean databean, List filterData) {
		if (datastack.isEmpty() || needPushStack(databean)) {
			datastack.push(databean);
		} else {
			if (datastack.size() >= lowerLimit && datastack.size() <= upperLimit) {
				List sublist = new ArrayList();
				sublist.addAll(datastack.subList(0, datastack.size()));
				filterData.add(sublist);
			}
			datastack.clear();
			datastack.push(databean);
		}
	}

	private boolean needPushStack(DataBean databean) {
		String newNum = getKeyNum(databean.getLotteryNumbers());
		String pushedNum = getKeyNum(((DataBean)datastack.peek()).getLotteryNumbers());
		return matchRule(newNum, pushedNum);
	}

	private String getKeyNum(String lotteryNumbers) {
		try {
			digitfilter.loadData(lotteryNumbers);
			digitfilter.dofilter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return digitfilter.getResult();
	}

	protected abstract boolean matchRule(String newNum, String pushedNum);
}
