package com.ssc.filter;

import java.util.List;

public interface Filter {
	
	public void loadData(Object data);
	
	public List dofilter() throws Exception;
}
