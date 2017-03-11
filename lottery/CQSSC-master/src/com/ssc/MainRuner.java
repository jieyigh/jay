package com.ssc;

import java.util.List;
import java.util.Properties;

import com.ssc.calc.DataAnalyzer;
import com.ssc.filter.Filter;
import com.ssc.filter.digit.DigitFilter;
import com.ssc.filter.digit.impl.UnitsFilter;
import com.ssc.filter.impl.BigSmallFilter;
import com.ssc.filter.impl.SingleDoubleFilter;
import com.ssc.io.LotteryFileReader;

public class MainRuner {

	public static void main(String[] args) throws Exception {
		/*获取文件路径*/
		Properties prop = new Properties();
		prop.load(MainRuner.class.getResourceAsStream("conf/conf.properties"));
		String filepath = prop.getProperty("filepathB");
		/*读取数据*/
		LotteryFileReader filereader = new LotteryFileReader();
		List data = filereader.readfile(filepath);
		/*设置过滤器*/
		DigitFilter digitfilter = new UnitsFilter();
		Filter filter = new SingleDoubleFilter(9, 20, digitfilter);
//		Filter filter = new BigSmallFilter(9, 20, digitfilter);
		/*执行数据分析*/
		DataAnalyzer analyzer = new DataAnalyzer(data, filter);
		analyzer.doAnalysis();
		/*展示分析结果*/
		analyzer.showResult();
	}
}
