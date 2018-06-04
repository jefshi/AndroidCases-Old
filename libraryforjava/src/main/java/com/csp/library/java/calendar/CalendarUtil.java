package com.csp.library.java.calendar;

import java.util.Calendar;
import java.util.Date;

/**
 * 通用时间类
 * @version 1.3
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-5-15 1:30:22
 * <p style='font-weight:bold'>AlterDate:</p> 2016-11-03 11:21:00
 */
public class CalendarUtil {
	public final static long	oneDay	= 86400000l;	// 一天的时间
	public final static long	oneWeek	= 604800000l;	// 一周的时间

	/**
	 * 星期名称
	 */
	static class WeekNames {
		public final static String[]	zhou	= new String[] { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };
		public final static String[]	xinQi	= new String[] { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
	}

	/**
	 * 获取昨天的日历情况
	 * @return Calendar 昨天的日历
	 */
	public static Calendar getPreCalendar(Calendar calendar) {
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar;
	}

	/**
	 * 闰年判断
	 * @param year 要检测的年份
	 * @return true: 是闰年. false: 不是闰年
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0 ? true : false;
	}

	/**
	 * 返回指定日期与当前日期的天数差
	 * @param calendar 指定日期
	 * @return int 指定日期 - 当前日期
	 */
	public static int diffNowDay(Calendar calendar) {
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		int nowday = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		return day - nowday;
	}

	/**
	 * 指定日期(含附加时间)与当前日期的比较
	 * @param calendar 指定日期
	 * @param addTime  附加时间
	 * @return int 指定日期 + 附加时间 - 当前日期
	 */
	public static long compareNowDay(Calendar calendar, long addTime) {
		// 指定日期
		long time = calendar.getTimeInMillis() + addTime;
		calendar.setTime(new Date(time));

		// 当前日期
		Calendar nowCalendar = Calendar.getInstance();

		return calendar.getTimeInMillis() - nowCalendar.getTimeInMillis();
	}

	/**
	 * 返回指定日期的星期名称
	 * @param calendar 指定日期
	 * @return String 星期一、周一等
	 */
	public static String getWeekName(Calendar calendar, String[] weekNames) {
		String weekName = null;

		int week = calendar.get(Calendar.DAY_OF_WEEK);
		switch (week) {
		case Calendar.MONDAY:
			weekName = weekNames[0];
			break;
		case Calendar.TUESDAY:
			weekName = weekNames[1];
			break;
		case Calendar.WEDNESDAY:
			weekName = weekNames[2];
			break;
		case Calendar.THURSDAY:
			weekName = weekNames[3];
			break;
		case Calendar.FRIDAY:
			weekName = weekNames[4];
			break;
		case Calendar.SATURDAY:
			weekName = weekNames[5];
			break;
		default:
			weekName = weekNames[6];
			break;
		}
		return weekName;
	}

	/**
	 * 返回指定日期的星期名称
	 * @param calendar 指定日期
	 * @return String 星期一、周一等
	 */
	public static String getWeekName(Calendar calendar) {
		return getWeekName(calendar, WeekNames.zhou);
	}
}
