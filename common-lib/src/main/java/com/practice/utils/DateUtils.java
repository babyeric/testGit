package com.practice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatException;

/**
 * Created by Eric on 10/10/2015.
 */
public class DateUtils {
    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static Date parseDate(String str) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(str);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public final static Date parseDateTime(String str) {
        try {
            return new SimpleDateFormat(DATE_TIME_FORMAT).parse(str);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public final static String formatDate(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    public final static String formatDateTime(Date date) {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(date);
    }

}
