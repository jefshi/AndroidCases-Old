package com.csp.eclipselibrary.com.csp.eclipselibrary.java.util.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期时间格式转换类
 * @version 1.0.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-5-15 1:30:22
 * <p style='font-weight:bold'>AlterDate:</p> 2016-09-03 10:21:16
 */
public class CalendarFormat {
	// 以下域禁止变更
	public final static String	datetimeFormat_1	= "yyyy-MM-dd HH:mm:ss";
	public final static String	datetimeFormat_2	= "yyyy/MM/dd HH:mm:ss";
	public final static String	datetimeFormat_3	= "yyyyMMdd_HHmmss";
	public final static String	dateFormat_1		= "yyyy-MM-dd";
	public final static String	dateFormat_2		= "yyyy/MM/dd";
	public final static String	timeFormat			= "HH:mm:ss";

	// ===================================================
	// 时间字符串 -> 时间(Calendar)
	// ===================================================
	/**
	 * 本地时间(字符串) -> 本地时间(Calendar)
	 * @version 1.0.0
	 * @param dateStr 本地时间(字符串)
	 * @param format 上述时间字符串格式
	 * @return Calendar 本地时间(Calendar)
	 * @author csp
	 * <p style='font-weight:bold'>Date:</p> 2016-09-03 10:23:44
	 * <p style='font-weight:bold'>AlterDate:</p>
	 * @throws ParseException 
	 */
	public static Calendar getCalendar(String dateStr, String format) throws ParseException {
		// 根据时间字符串，获取相应时间
		final SimpleDateFormat SDF = new SimpleDateFormat(format);
		Date date = SDF.parse(dateStr);

		// 根据时间，返回相应的 Calendar
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 本地时间(字符串) -> UTC时间(Calendar)
	 * @version 1.0.0
	 * @param dateStr 本地时间(字符串)
	 * @param format 上述时间字符串格式
	 * @return Calendar UTC时间(Calendar)
	 * @author csp
	 * <p style='font-weight:bold'>Date:</p> 2016-09-03 10:42:00
	 * <p style='font-weight:bold'>AlterDate:</p>
	 * @throws ParseException 
	 */
	public static Calendar getUTCCalendar(String dateStr, String format) throws ParseException {
		Calendar calendar = getCalendar(dateStr, format);
		TimeZone timeZone = TimeZone.getDefault();
		calendar.add(Calendar.MILLISECOND, -timeZone.getRawOffset());
		return calendar;
	}
	
	// ===================================================
	// 时间 -> 时间字符串
	// ===================================================
	/**
	 * 日期(Date) -> 日期字符串
	 * @version 1.0.0
	 * @param date 指定日期
	 * @param format 指定日期字符串的格式
	 * @return String 日期字符串
	 * @author csp
	 * <p style='font-weight:bold'>Date:</p> 2016-09-03 10:55:18
	 * <p style='font-weight:bold'>AlterDate:</p>
	 */
	public static String getDateFormat(Date date, String format) {
		final SimpleDateFormat SDF = new SimpleDateFormat(format);
		return SDF.format(date);
	}
	
	
	/**
	 * 当前日期 -> 日期字符串
	 * @version 1.0.1
	 * @param format 指定日期字符串的格式
	 * @return String 日期字符串
	 * @author csp
	 * <p style='font-weight:bold'>Date:</p> 2016-5-15 01:30:22
	 * <p style='font-weight:bold'>AlterDate:</p> 2016-09-03 11:02:54
	 */
	public static String getNowDateFormat(String format) {
		return getDateFormat(new Date(), format);
	}
	
	/**
	 * 当前日期 -> 日期字符串
	 * @version 1.0.1
	 * @return String 日期字符串(格式为: "yyyy-MM-dd HH:mm:ss")
	 * @author csp
	 * <p style='font-weight:bold'>Date:</p> 2016-09-03 11:02:54
	 * <p style='font-weight:bold'>AlterDate:</p> 
	 */
	public static String getNowDateFormat() {
		return getDateFormat(new Date(), datetimeFormat_1);
	}
}
