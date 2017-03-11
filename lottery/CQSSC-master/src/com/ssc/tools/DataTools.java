package com.ssc.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ssc.bean.DataBean;

public class DataTools {
	
	/**
	 * 上午，开奖周期更改的期数，对应时间点为2点或10点
	 */
	static final int SEPARATE_ISSUE_AM = 24;
	
	/**
	 * 上午，开奖周期更改的时间
	 */
	static final int SEPARATE_HOUR_AM = 10;
	
	/**
	 * 下午，开奖周期更改的期数，对应时间点为22点
	 */
	static final int SEPARATE_ISSUE_PM = 96;
	
	/**
	 * 下午，开奖周期更改的时间
	 */
	static final int SEPARATE_HOUR_PM = 22;
	
	/**
	 * 十分钟周期
	 */
	static final int PERIOD_TEN = 10;
	
	/**
	 * 五分钟周期
	 */
	static final int PERIOD_FIVE = 5;
	
	static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	
	public static Date getTime(DataBean databean) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(format.parse(databean.getDate())); //设置年月日
		int issue = Integer.parseInt(databean.getIssue());
		if (isTenPeriod(databean)) {
			int totalminute = SEPARATE_HOUR_AM * 60 + (issue - SEPARATE_ISSUE_AM) * PERIOD_TEN;
			calendar.set(Calendar.HOUR, totalminute/60);
			calendar.set(Calendar.MINUTE, totalminute%60);
		} 
		if (isFivePeriod(databean)) {
			int totalminute;
			if (issue <= SEPARATE_ISSUE_AM) {
				totalminute = issue * PERIOD_FIVE;
			} else {
				totalminute = SEPARATE_HOUR_PM * 60 + (issue - SEPARATE_ISSUE_PM) * PERIOD_FIVE;
			}
			calendar.set(Calendar.HOUR, totalminute/60);
			calendar.set(Calendar.MINUTE, totalminute%60);
		}
		return calendar.getTime();
	}
	
	/**
	 * 开奖数据是否为十分钟一周期的数据
	 * @param databean
	 * @return
	 */
	public static boolean isTenPeriod(DataBean databean) {
		int issue = Integer.parseInt(databean.getIssue());
		if(issue > SEPARATE_ISSUE_AM && issue <= SEPARATE_ISSUE_PM) {
			return true;
		}
		return false;
	}
	
	public static boolean isFivePeriod(DataBean databean) {
		int issue = Integer.parseInt(databean.getIssue());
		if(issue <= SEPARATE_ISSUE_AM || issue > SEPARATE_ISSUE_PM) {
			return true;
		}
		return false;
	}
	
	/**
	 * 返回该数据在周几
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getWeek(DataBean databean) throws ParseException {
		String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		Date date = format.parse(databean.getDate());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return week_index;
	}
}
