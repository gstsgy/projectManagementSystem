package com.guyue.pms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    /**
     * 国际化时间格式 2017-11-7T17:02:07+08:00
     *
     * @Date:17:01 2017/11/7
     */
    public static final String YMDHMSXXX = "yyyy-MM-dd'T'HH:mm:ssXXX";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    /**
     * dd-MM-yyyy
     */
    public static final String DMY = "dd-MM-yyyy";
    /**
     * MM-dd-yyyy
     */
    public static final String MDY = "MM-dd-yyyy";
    /**
     * dd-MM-yyyy HH:mm
     */
    public static final String DMYHM = "dd-MM-yyyy HH:mm";

    /**
     * 时间格式化
     *
     * @param date
     * @param formatPattern
     * @return java.lang.String
     * @Date:17:10 2017/11/7
     */
    public static String format(Date date, String formatPattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat f = new SimpleDateFormat(formatPattern);
        return f.format(date);
    }

    public static String getNow(){
        return format(new Date(),  "yyyy-MM-dd HH:mm");
    }

    public static String getSeq(){
        return format(new Date(),  "yyyyMMddHHmmss");
    }


    /**
     * 文本转换成时间
     *
     * @param text
     * @param formatPattern
     * @return java.util.Date
     * @Date:17:11 2017/11/7
     */
    public static Date parse(String text, String formatPattern) throws ParseException {
        if (text.isEmpty()) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat(formatPattern);
        return f.parse(text);
    }

    /**
     * 当前时间+day,指定hour，分秒置为零
     *
     * @param day
     * @param hour
     * @return
     */
    public static Date addDay(int day, int hour) {
        // 取时间
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 当前时间+day,时分秒置为零
     *
     * @param day
     * @return
     */
    public static Date addDay(int day) {
        // 取时间
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
        // 这个时间就是日期往后推一天的结果
        return calendar.getTime();
    }

    /**
     * 指定时间+day 时分秒置为零
     *
     * @param day
     * @param date
     * @return
     */
    public static Date addDay(int day, Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
        // 这个时间就是日期往后推一天的结果
        return calendar.getTime();
    }

    /**
     * 指定时间+day 时分秒不变
     *
     * @param day
     * @param date
     * @return
     */
    public static Date addDay2(int day, Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
        // 这个时间就是日期往后推一天的结果
        return calendar.getTime();
    }
    /**
     * String类型日期格式转String型毫秒数
     *
     * @param time 需要转换的String类型时间
     * @return	String 返回String型毫秒数
     * @author rongbj
     * @throws ParseException
     */
    public static String timeToMillisecond(String time) throws Exception {
        return parse(time.replaceAll("T", "").replaceAll("Z", ""), "yyyy-MM-ddHH:mm:ss.S").getTime()+"";
    }

    public static String getStringDate(String string) {
        try {
            string = string.replace("Z", " UTC");
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Date date = utcFormat.parse(string);
            return (defaultFormat.format(date));
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }

    public static String getStringDateAdd1Day(String string) {
        try {
            string = string.replace("Z", " UTC");
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Date date = utcFormat.parse(string);
            Date addDay2 = addDay2(1, date);
            return (defaultFormat.format(addDay2));
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }
    public static String getStringDateAdd2Day(String string) {
        try {
            string = string.replace("Z", " UTC");
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = utcFormat.parse(string);
            Date addDay2 = addDay2(1, date);
            return (defaultFormat.format(addDay2));
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }
    /**
     * @Title: getStringDate
     * @Description: 日期选择器转成北京时间，以"yyyy-MM-dd"的格式输出
     * @param @param string
     * @param @return
     * @return String
     * @throws
     * @author wangf
     * @date 2019年7月30日
     */
    public static String getStringDate1(String string) {
        try {
            string = string.replace("Z", " UTC");
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = utcFormat.parse(string);
            return (defaultFormat.format(date));
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }


}
