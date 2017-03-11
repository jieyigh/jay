package com.ssc.core;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.ssc.DataAnalyzerMgr;
import com.ssc.io.LotteryFileReader;

public class ApplicationServer {

	private static ApplicationServer _instance;
	
	private List data;
	
	public static ApplicationServer getInstance() {
		synchronized (ApplicationServer.class) {
			if (_instance == null) {
				ApplicationServer svr = new ApplicationServer();
				try {
					svr.initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
				_instance = svr;
			}
			return _instance;
		}
	}
	
	private void initialize() throws Exception {
		/*获取文件路径*/
		Properties prop = new Properties();
		prop.load(ApplicationServer.class.getResourceAsStream("../conf/conf.properties"));
		String filepath = prop.getProperty("filepathB");
		/*读取数据*/
		LotteryFileReader filereader = new LotteryFileReader();
		data = filereader.readfile(filepath);
	}
	
	public List getData() {
		return data;
	}
	
	public static void releaseInstance() {
		synchronized (ApplicationServer.class) {
			if (_instance != null) {
				_instance.data = null;
			}
			_instance = null;
		}
	}
}
