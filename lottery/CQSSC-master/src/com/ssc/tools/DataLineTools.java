package com.ssc.tools;

public class DataLineTools {
	
	/**
	 * 将日期\期数\号码分开<p>
	 * e.g. 20150623-023	43633
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public static String[] splitDataLine(String DataLine) throws Exception {
		String[] data = new String[3];
		try {
			String[] TimeAndNum = splitData(DataLine);
			String[] DateAndIssue = splitTime(TimeAndNum[0]);
			data[0] = DateAndIssue[0];
			data[1] = DateAndIssue[1];
			data[2] = TimeAndNum[1];
		} catch (Exception e) {
			throw new Exception("数据格式不符合标准");
		}
		return data;
	}
	
	/**
	 * 行数据拆分第一步，拆成时间和中奖号码
	 * @param data
	 * @return
	 */
	public static String[] splitData(String data) {
		return data.split("\\t");
	}
	
	/**
	 * 行数据拆分第二步，拆成日期和期数
	 * @param data
	 * @return
	 */
	public static String[] splitTime(String data) {
		return data.split("-");
	}
}
