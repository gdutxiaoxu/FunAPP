package com.xujun.funapp.common.util;

/**
 * @explain
 * @author xujun
 * @time 2016/6/16 16:57.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private DateUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    static final String formatPattern = "yyyy-MM-dd";
    static final String DateAndTimePattern = "yyyy-MM-dd HH:mm:ss";
    static final String DateAndTimePatternWithWeek = "yyyy-MM-dd HH:mm:ss EEEE";

    static final String formatPattern_Short = "yyyy-MM-dd";
    static String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(new Date());

    }

    /**
     * @explain 获取时间差
     * @author xujun
     * @time 2016/4/12 14:06.
     */
    public static long getTimeDistance(long startTime,long endTime){
        return endTime-startTime;
    }

    /**
     * 获取指定日期是星期几
     * 参数为null时表示获取当前日期是星期几
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK)-1 ;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /**
     * 更具毫秒数获取当前是星期几
     * @param timeMiss
     * @return
     */
    public static String getWeekOfDate(long timeMiss) {
        Date date = new Date(timeMiss);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int w = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    public static String getCurrentDateAndTime() {
        SimpleDateFormat format = new SimpleDateFormat(DateAndTimePattern);
        return format.format(new Date());
    }

    public static String getDesignedDateAndTimeWithWeek(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DateAndTimePatternWithWeek);
        return format.format(date);
    }

    public static String getDesignedDataAndTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DateAndTimePattern);

        return format.format(date);

    }



    public static String getDesignedDataAndTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat(DateAndTimePattern);
        return format.format(time);

    }

    /**
     * 获取制定毫秒数之前的日期
     *
     * @param timeDiff
     * @return
     */
    public static String getDesignatedDate(long timeDiff) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        long nowTime = System.currentTimeMillis();
        long designTime = nowTime - timeDiff;
        return format.format(designTime);
    }

    /**
     * 获取前几天的日期
     */
    public static String getPrefixDate(String count) {
        Calendar cal = Calendar.getInstance();
        int day = 0 - Integer.parseInt(count);
        cal.add(Calendar.DATE, day);   // int amount   代表天数
        Date datNew = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(datNew);
    }

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(date);
    }

    /**
     * 字符串转换日期
     *
     * @param str
     * @return
     */
    public static Date stringToDate(String str) {
        //str =  " 2008-07-10 19:20:00 " 格式
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        if (!str.equals("") && str != null) {
            try {
                return format.parse(str);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    //java中怎样计算两个时间如：“21:57”和“08:20”相差的分钟数、小时数 java计算两个时间差小时 分钟 秒 .
    public void timeSubtract() {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date begin = null;
        Date end = null;
        try {
            begin = dfs.parse("2004-01-02 11:30:24");
            end = dfs.parse("2004-03-26 13:31:40");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60;
        System.out.println("" + day1 + "天" + hour1 + "小时" + minute1 + "分"
                + second1 + "秒");
    }

}