package com.ssc.calc;

import java.util.List;

import com.ssc.filter.Filter;

public class DataAnalyzer {
	
	private List data;
	
	private List filterData;
	
	private Filter filter;
	
	public DataAnalyzer(List data, Filter filter) {
		this.data = data;
		this.filter = filter;
	}
	
	public void doAnalysis() throws Exception {
		filter.loadData(data);
		filterData = filter.dofilter();
	}
	
	public void showResult() {
		if (filterData != null) {
			System.out.println(filterData.size());
		}
	}
	
	public List getResult() {
		return filterData;
	}
}
