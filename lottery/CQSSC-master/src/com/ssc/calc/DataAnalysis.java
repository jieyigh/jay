package com.ssc.calc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Stack;


public class DataAnalysis {
	
	int checkcount = 9;

	final int amIssue = 24;
	
	final int pmIssue = 96;
	
	/**
	 * 10分钟一次
	 */
	final int amTime = 10;
	
	/**
	 * 5分钟一次
	 */
	final int pmTime = 5;
	
	Stack<String> dataStack = new Stack<String>();
	
	/**
	 * 哪一位
	 */
	int digit = 0;
	
	/**
	 * 单双或大小
	 */
	String type;
	
	public static final String TYPE_BIG_SMALL = "大小";
	public static final String TYPE_SINGLE_DOUBLE = "单双";
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException {
		String filepath = "d:/" + "重庆时时彩B" + ".txt";
		DataAnalysis analysisTools = new DataAnalysis();
		analysisTools.checkcount = 9;
		analysisTools.digit = 5;
		analysisTools.type = TYPE_BIG_SMALL;
//		analysisTools.type = TYPE_SINGLE_DOUBLE;
//		System.out.println("\t星期日\t星期一\t星期二\t星期三\t星期四\t星期五\t星期六");
		analysisTools.readfile(filepath);
	}
	
	/**
	 * 将日期期数与号码分开<p>
	 * e.g. 20150623-023	43633
	 * @param data
	 * @return
	 */
	public String[] splitData(String data) {
		return data.split("\\t");
	}
	
	/**
	 * 期数转换为时间
	 * @param issue
	 * @param oper
	 * @return
	 */
	public String issue2time(int issue, String oper) {
		int hour = 0;
		int minute = 0;
		//10分钟一期
		if (issue > amIssue && issue < pmIssue) {
			int time = (issue - amIssue) * amTime;
			if(time != 0) {
				hour = 10 + time/60;
				minute = time%60;
			} else {
				hour = 10;
				minute = 0;
			}
		} else {
			int time = (issue - pmIssue) * pmTime;
			if (time < 0) {
				time = time + (120 * pmTime);
			}
			if(time != 0) {
				hour = 22 + time/60;
				minute = time%60;
			} else {
				hour = 22;
				minute = 0;
			}
			if (hour >= 24) {
				hour = hour - 24;
			}
		}
		if ("all".equals(oper)) {
			return hour + ":" + minute;
		}
		if ("hour".equals(oper)) {
			return String.valueOf(hour);
		}
		if ("minute".equals(oper)) {
			return String.valueOf(minute);
		}
		return null;
	}
	
	public void readfile(String filepath) {
		File file = new File(filepath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			int i = 0;
			System.out.println("-------------开始读取文件-----------");
			while ((line=reader.readLine()) != null) {
				analysisLineData(line);
				i++;
			}
			countWeekAndHour();
			System.out.println("第" + digit + "位出现" + checkcount + "连" + type + "以上的次数为" + appearcount + "次");
			System.out.println("最大连杀为" + max + "次,数据为" + maxdata);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 统计周和小时连续出现N次的次数
	 */
	private void countWeekAndHour() {
		System.out.println("\t星期日\t星期一\t星期二\t星期三\t星期四\t星期五\t星期六\t总数\t平均值");
		int[] avgWeek = new int[7]; 
		for (int hour = 0; hour < 24; hour++) {
			int avgHour = 0;
			System.out.print(hour);
			System.out.print("\t");
			for (int week = 0; week < 7; week++) {
				if (analysisData[week][hour] != null) {
					int count = analysisData[week][hour].size();
					avgHour = avgHour + count;
					if (hour > 17 && hour < 22)
					avgWeek[week] = avgWeek[week] + count;
					System.out.print(count);
				}
				System.out.print("\t");
			}
			System.out.print(avgHour);
			System.out.print("\t");
			if (avgHour != 0) {
				System.out.print(avgHour/7.0);
			}
			System.out.println();
		}
		for (int week = 0; week < 7; week++) {
			System.out.print("\t");
			System.out.print(avgWeek[week]);
		}
		System.out.println();
	}

	String enddate = null;
	String endissue = null;
	int appearcount = 0;
	int max = 0;
	String maxdata = null; 
	int period = 0; 
	List[][] analysisData = new ArrayList[7][24];

	private String begindate;

	private String beginissue;
	
	/**
	 * 解析一行数据
	 * @param linedata
	 * @throws ParseException 
	 */
	public void analysisLineData(String linedata) throws ParseException {
		String[] issueAndNumber = splitData(linedata);
		String date = null;
		String issue = null;
		String number = null;
		if (issueAndNumber.length == 2) {
			number = issueAndNumber[1];
			number = number.substring(digit-1, digit);
			String[] dateAndIssue = issueAndNumber[0].split("-");
			date = dateAndIssue[0];
			issue = dateAndIssue[1];
		}
		if (dataStack.isEmpty()) {
			enddate = date;
			endissue = issue;
			dataStack.push(number);
		} else if ((TYPE_SINGLE_DOUBLE.equals(type) && needPushDS(number)) || (TYPE_BIG_SMALL.equals(type) && needPushDX(number))){
			begindate = date;
			beginissue = issue;
			dataStack.push(number);
		} else {
			if (dataStack.size() >= checkcount) {
				String info = "时间: " + begindate + " " + issue2time(Integer.valueOf(beginissue),"all") + ", 期数: " + beginissue + "连续出现" + dataStack.size() + "期";
				System.out.println(info);
				System.out.println("分别是:");
				if (max < dataStack.size()) {
					max = dataStack.size();
					maxdata = info;
				}
				while (dataStack.size() != 0) {
					System.out.println(dataStack.pop());
				}
				int week = getWeek(begindate);
				int hour = Integer.valueOf(issue2time(Integer.valueOf(beginissue),"hour"));
				if (analysisData[week][hour] == null) {
					analysisData[week][hour] = new ArrayList();
				}
				analysisData[week][hour].add(info);
				appearcount++;
			}
			dataStack.clear();
			begindate = date;
			beginissue = issue;
			dataStack.push(number);
		}
	}

	/**
	 * 大小-是否需要压入栈
	 * @param number
	 * @return
	 */
	private boolean needPushDX(String number) {
		String top = dataStack.peek();
		int unit_stack = Integer.valueOf(top)%10;
		int unit_num = Integer.valueOf(number)%10;
		if (unit_stack >= 5 && unit_num >= 5) {
			return true;
		}
		if (unit_stack < 5 && unit_num < 5) {
			return true;
		}
		return false;
	}
	
	/**
	 * 单双-是否需要压入栈
	 * @param number
	 * @return
	 */
	private boolean needPushDS(String number) {
		String top = dataStack.peek();
		int unit_stack = Integer.valueOf(top)%10;
		int unit_num = Integer.valueOf(number)%10;
		if (unit_stack%2 == 1 && unit_num%2 == 1) {
			return true;
		}
		if (unit_stack%2 == 0 && unit_num%2 == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 给定字符串型的日期结构，返回该天是周几
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	private int getWeek(String date) throws ParseException {
		String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"}; 
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date2 = format.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date2);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return week_index;
	}
	
}
