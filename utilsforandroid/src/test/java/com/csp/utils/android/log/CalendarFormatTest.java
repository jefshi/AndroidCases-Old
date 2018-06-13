package com.csp.utils.android.log;

import com.csp.library.java.calendar.CalendarFormat;

import org.junit.Test;

import java.util.Date;

/**
 * Created by chenshp on 2018/6/4.
 */
public class CalendarFormatTest {
    @Test
    public void getCalendar() throws Exception {
    }

    @Test
    public void getUTCCalendar() throws Exception {
    }

    @Test
    public void getDateFormat() throws Exception {
        String dateFormat = CalendarFormat.getDateFormat(new Date(), CalendarFormat.Format.DATE_FORMAT_0);
        System.out.println(dateFormat);
    }

    @Test
    public void getNowDateFormat() throws Exception {
    }
}