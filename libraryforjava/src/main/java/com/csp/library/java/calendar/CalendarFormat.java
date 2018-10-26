package com.csp.library.java.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期时间格式转换类
 * Created by csp on 2016-5-15.
 * Modified by csp on 2016-5-15.
 *
 * @version 1.0.0
 */
public class CalendarFormat {
    // 以下域禁止变更
    public final static String datetimeFormat_1 = "yyyy-MM-dd HH:mm:ss";
    public final static String datetimeFormat_2 = "yyyy/MM/dd HH:mm:ss";
    public final static String datetimeFormat_3 = "yyyyMMdd_HHmmss";
    public final static String dateFormat_1 = "yyyy-MM-dd";
    public final static String dateFormat_2 = "yyyy/MM/dd";
    public final static String timeFormat = "HH:mm:ss";

    // ===================================================
    // 时间字符串 -> 时间(Calendar)
    // ===================================================

    /**
     * 本地时间(字符串) -> 本地时间(Calendar)
     *
     * @param dateStr 本地时间(字符串)
     * @param format  上述时间字符串格式
     * @return Calendar 本地时间(Calendar)
     * @throws ParseException 日期解析异常
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
     *
     * @param dateStr 本地时间(字符串)
     * @param format  上述时间字符串格式
     * @return Calendar UTC时间(Calendar)
     * @throws ParseException 日期解析异常
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
     *
     * @param date   指定日期
     * @param format 指定日期字符串的格式
     * @return String 日期字符串
     */
    public static String getDateFormat(Date date, String format) {
        final SimpleDateFormat SDF = new SimpleDateFormat(format);
        return SDF.format(date);
    }

    /**
     * 日期(long) -> 日期字符串
     *
     * @param time   指定日期
     * @param format 指定日期字符串的格式
     * @return String 日期字符串
     */
    public static String getDateFormat(long time, String format) {
        return getDateFormat(new Date(time), format);
    }

    /**
     * 当前日期 -> 日期字符串
     *
     * @param format 指定日期字符串的格式
     * @return String 日期字符串
     */
    public static String getNowDateFormat(String format) {
        return getDateFormat(new Date(), format);
    }

    /**
     * 当前日期 -> 日期字符串
     *
     * @return String 日期字符串(格式为: "yyyy-MM-dd HH:mm:ss")
     */
    public static String getNowDateFormat() {
        return getDateFormat(new Date(), datetimeFormat_1);
    }
}
