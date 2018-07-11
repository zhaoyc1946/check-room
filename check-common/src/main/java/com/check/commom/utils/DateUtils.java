package com.check.commom.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: 日期处理工具类
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 17:31:20
 */
public class DateUtils {
    /**
     * 时间格式（yyyy）
     */
    public final static String YEAR_PATTERN = "yyyy";

    /**
     * 时间格式（yyyy-MM）
     */
    public final static String MONTH_PATTERN = "yyyy-MM";

    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间格式(yyyy-MM-dd HH)
     */
    public final static String DATE_HOUR_PATTERN = "yyyy-MM-dd HH";

    /**
     * 时间格式(yyyy-MM-dd HH:mm)
     */
    public final static String DATE_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 根据指定的格式解析指定时间
     *
     * @param date    指定时间
     * @param pattern 解析格式
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * @Explanation: 某一天是一年中的第几周
     * * @param date
     * @DBTable:
     * @date: 18:31 2017/12/5
     * @anthor:ljh
     */
    public static int getWeek(String date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        try {
            cal.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    /**
     * @Explanation: 本月第一天
     * * @param
     * @DBTable:
     * @date: 18:32 2017/12/5
     * @anthor:ljh
     */
    public static Date getFirstDayOfMonth() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, now.getActualMinimum(Calendar.DATE));
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return now.getTime();
    }

    /**
     * @Explanation: 本月第一天是 一年中第几周
     * * @param
     * @DBTable:
     * @date: 18:37 2017/12/5
     * @anthor:ljh
     */
    public static int getWeeKNum() {
        Date firstDayOfMonth = getFirstDayOfMonth();
        Calendar firstDayOfMonthCal = Calendar.getInstance();
        firstDayOfMonthCal.setFirstDayOfWeek(Calendar.MONDAY);
        firstDayOfMonthCal.setTime(firstDayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        System.out.println("本月第一天" + format.format(firstDayOfMonth));

        return getWeek(format.format(firstDayOfMonth));
    }


    /**
     * @Explanation: 获取一天所在周的 第一天
     * * @param args
     * @DBTable:
     * @date: 9:55 2017/12/6
     * @anthor:ljh
     */
    public static String getWeekStart(Date date) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }

        cal.add(Calendar.DAY_OF_WEEK, d);

        return new SimpleDateFormat(DATE_PATTERN).format(cal.getTime());
    }

    /**
     * @Explanation: 某天是周几
     * * @param date
     * @DBTable:
     * @date: 10:24 2017/12/6
     * @anthor:ljh *@return  0 是周日 1-6  对应周-到周六
     */
    public static int getDayofweek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 给指定时间增加天数， 年中的天
     *
     * @param date 指定时间
     * @param day  增加天数
     * @return
     */
    public static Date addDayOfYear(Date date, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear + day);

        return calendar.getTime();
    }

}
