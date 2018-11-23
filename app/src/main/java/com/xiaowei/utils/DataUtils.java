package com.xiaowei.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author jackson
 * @date 2018/11/16
 */
public class DataUtils {
    public static String t1chager(int num) {

        if (num / 10000 >= 1) {
            double money = num / 10000;
            return t3chager(money) + "W";
        }
        if (num / 1000 >= 1 && num / 10000 < 1) {
            double money = num / 1000;
            return t3chager(money) + "K";
        }
        return num + "";

    }

    /**
     * `deadline` int(5) DEFAULT '0' COMMENT '借款期限',
     * `deadlineType` int(1) DEFAULT '1' COMMENT '借款期限类型（1，为天，2为月）',
     *
     * @param deadline
     * @return
     */
    public static String t2chager(int deadline) {
        int deadlineType = -1;
        if (deadline >= 30) {
            deadlineType = 2;
            deadline = deadline / 30;
        } else {
            deadlineType = 1;
        }
        switch (deadlineType) {
            case 1:
                return deadline + "天";
            case 2:
                return deadline + "个月";
            default:
                break;
        }
        return deadline + "?";

    }

    /**
     * DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
     * String s = decimalFormat.format(d);
     *
     * @return
     */
    public static String t3chager(Double d) {

        DecimalFormat decimalFormat = new DecimalFormat("###,###.0");
        String s = decimalFormat.format(d);
        if (s.contains(".0")) {
            DecimalFormat decimalFormat1 = new DecimalFormat("###,###");
            s = decimalFormat1.format(d);

        }

        return s;

    }

    //获取系统当前时间
    public static String getCurDate(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);

    }

    //Day:日期字符串例如 2015-3-10  Num:需要减少的天数例如 7
    public static String getDateStr(String day, int Num) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //如果需要向后计算日期 -改为+
        Date newDate2 = new Date(nowDate.getTime() - (long) Num * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getTime(String curdate, String dateFormat) {//可根据需要自行截取数据显示
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date date= null;
        try {
            date = format.parse(curdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(date);
    }

    //两个日期相差几天
    public static long dateDiff(String startTime, String endTime, String format) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime()
                    - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            System.out.println("时间相差：" + day + "天" + hour + "小时" + min
                    + "分钟" + sec + "秒。");
            if (day >= 1) {
                return day;
            } else {
                if (day == 0) {
                    return 1;
                } else {
                    return 0;
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

    }

    //两个日期比较大小
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取指定日期所在周的第一天和最后一天,用下划线连接
     *
     * @param dataStr
     * @return
     * @throws ParseException
     */
    public static String getFirstAndLastOfMonth(String dataStr, String dateFormat, String resultDateFormat) throws ParseException {
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.setTime(new SimpleDateFormat(dateFormat).parse(dataStr));
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = new SimpleDateFormat(resultDateFormat).format(c.getTime());
        System.out.println("===============first:" + first);

        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = new SimpleDateFormat(resultDateFormat).format(ca.getTime());
        System.out.println("===============last:" + last);
        return first + "_" + last;
    }

    /**
     * 每周的第一天和最后一天
     *
     * @param dataStr
     * @param dateFormat
     * @param resultDateFormat
     * @return
     * @throws ParseException
     */
    public static String getFirstAndLastOfWeek(String dataStr, String dateFormat, String resultDateFormat) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat(dateFormat).parse(dataStr));
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        // 所在周开始日期
        String data1 = new SimpleDateFormat(resultDateFormat).format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        // 所在周结束日期
        String data2 = new SimpleDateFormat(resultDateFormat).format(cal.getTime());
        return data1 + "_" + data2;

    }
    //所在周开始日期
    public  static String getWeekLastDay(String dataStr,String dateFormat) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat(dateFormat).parse(dataStr));
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
//        cal.add(Calendar.DAY_OF_WEEK, 6);
        //所在周开始日期
        String data1 = new SimpleDateFormat(dateFormat).format(cal.getTime());
        return  data1;

    }

    //获取7天前的日期
    public static String getTheLast(String dateStr, String dateformat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        Calendar c = Calendar.getInstance();

        //过去七天
        c.setTime(format.parse(dateStr));
        c.add(Calendar.DATE, -7);
        Date d = c.getTime();
        String day = format.format(d);
        return day;
    }   //获取7天后的日期

    public static String getNextWeek(String dateStr, String dateformat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        Calendar c = Calendar.getInstance();

        //过去七天
        c.setTime(format.parse(dateStr));
        c.add(Calendar.DATE, 7);
        Date d = c.getTime();
        String day = format.format(d);
        return day;
    }
}
