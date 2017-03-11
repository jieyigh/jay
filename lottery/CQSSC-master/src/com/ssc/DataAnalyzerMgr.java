package com.ssc;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.ssc.calc.DataAnalyzer;
import com.ssc.core.ApplicationServer;
import com.ssc.filter.Filter;
import com.ssc.filter.digit.DigitFilter;
import com.ssc.filter.digit.impl.TenThousandsFilter;
import com.ssc.filter.digit.impl.TensFilter;
import com.ssc.filter.digit.impl.UnitsFilter;
import com.ssc.filter.impl.SingleDoubleFilter;
import com.ssc.io.LotteryFileReader;

public class DataAnalyzerMgr {
	
	private Filter filter;
	
	public DataAnalyzerMgr(Filter filter) {
		this.filter = filter;
	}
	
	public List run() throws Exception {
		/*得到数据*/
		List data = ApplicationServer.getInstance().getData();
		/*执行数据分析*/
		DataAnalyzer analyzer = new DataAnalyzer(data, filter);
		analyzer.doAnalysis();
		/*展示分析结果*/
		return analyzer.getResult();
	}
}
