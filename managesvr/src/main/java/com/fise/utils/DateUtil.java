package com.fise.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 日期相关的操作
 * 
 * @author Dawei
 * 
 */

public class DateUtil {
	
	public static final String STFM = "yyyy-MM-dd HH:mm:ss";
	
	public static final String STFM_DATE = "yyyy-MM-dd";

	/**
	 * 将一个字符串转换成日期格式
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date toDate(String date, String pattern) {
		if (("" + date).equals("")) {
			return null;
		}
		if (pattern == null) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date newDate = new Date();
		try {
			newDate = sdf.parse(date);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return newDate;
	}

	/**
	 * 把日期转换成字符串型
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toString(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		if (pattern == null) {
			pattern = "yyyy-MM-dd";
		}
		String dateString = "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			dateString = sdf.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateString;
	}

	public static String toString(Long time, String pattern) {
		if (time > 0) {
			if (time.toString().length() == 10) {
				time = time * 1000;
			}
			Date date = new Date(time);
			String str = DateUtil.toString(date, pattern);
			return str;
		}
		return "";
	}

	/**
	 * 获取上个月的开始结束时间
	 * 
	 * @return
	 */
	public static String[] getLastMonth() {
		// 取得系统当前时间
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;

		// 取得系统当前时间所在月第一天时间对象
		cal.set(Calendar.DAY_OF_MONTH, 1);

		// 日期减一,取得上月最后一天时间对象
		cal.add(Calendar.DAY_OF_MONTH, -1);

		// 输出上月最后一天日期
		int day = cal.get(Calendar.DAY_OF_MONTH);

		String months = "";
		String days = "";

		if (month > 1) {
			month--;
		} else {
			year--;
			month = 12;
		}
		if (!(String.valueOf(month).length() > 1)) {
			months = "0" + month;
		} else {
			months = String.valueOf(month);
		}
		if (!(String.valueOf(day).length() > 1)) {
			days = "0" + day;
		} else {
			days = String.valueOf(day);
		}
		String firstDay = "" + year + "-" + months + "-01";
		String lastDay = "" + year + "-" + months + "-" + days;

		String[] lastMonth = new String[2];
		lastMonth[0] = firstDay;
		lastMonth[1] = lastDay;

		// //System.out.println(lastMonth[0] + "||" + lastMonth[1]);
		return lastMonth;
	}

	/**
	 * 获取当月的开始结束时间
	 * 
	 * @return
	 */
	public static String[] getCurrentMonth() {
		// 取得系统当前时间
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		// 输出下月第一天日期
		int notMonth = cal.get(Calendar.MONTH) + 2;
		// 取得系统当前时间所在月第一天时间对象
		cal.set(Calendar.DAY_OF_MONTH, 1);

		// 日期减一,取得上月最后一天时间对象
		cal.add(Calendar.DAY_OF_MONTH, -1);

		String months = "";
		String nextMonths = "";

		if (!(String.valueOf(month).length() > 1)) {
			months = "0" + month;
		} else {
			months = String.valueOf(month);
		}
		if (!(String.valueOf(notMonth).length() > 1)) {
			nextMonths = "0" + notMonth;
		} else {
			nextMonths = String.valueOf(notMonth);
		}
		String firstDay = "" + year + "-" + months + "-01";
		String lastDay = "" + year + "-" + nextMonths + "-01";
		String[] currentMonth = new String[2];
		currentMonth[0] = firstDay;
		currentMonth[1] = lastDay;

		// //System.out.println(lastMonth[0] + "||" + lastMonth[1]);
		return currentMonth;
	}

	public static long getDateline() {
		return System.currentTimeMillis() / 1000;
	}

	public static long getDateline(String date) {
		return (long) (toDate(date, "yyyy-MM-dd").getTime() / 1000);
	}

	public static long getDateline(String date, String pattern) {
		return (long) (toDate(date, pattern).getTime() / 1000);
	}
	
	/**
	 * 获取包括date在内的trace天的时间的集合
	 * @param date 从什么时间开始追溯
	 * @param sdf 时间格式,缺省形如20151023
	 * @param trace 向前追溯多少天
	 * @param containsDate 是否包含今天
	 * @return 时间的字符串集合
	 * 
	 * @author 水墨
	 * @version 2015-10-23
	 */
	public static List<String> getRecentDays(Date date,SimpleDateFormat sdf,int trace,boolean containsDate) {
		List<String> list = new ArrayList<String>();
		if (sdf == null) {
			sdf = new SimpleDateFormat("yyyyMMdd");
		}
		Calendar calendar = new GregorianCalendar();
		if (date != null) {
			calendar.setTime(date);
		}
		for (int i = 0; i < trace; i++) {
			if (containsDate) {
				list.add(sdf.format(calendar.getTime()));				
				calendar.add(Calendar.DATE, -1);
			} else {
				calendar.add(Calendar.DATE, -1);
				list.add(sdf.format(calendar.getTime()));
			}
		}
		return list;
	}
	
	/**
	 * 获取两个时间的间隔天数
	 * @param start
	 * @param end
	 * @return
	 * @author 水墨
	 */
	public static int getIntervalDays(Date start, Date end) {
		int interval = 0;
		if (start.getTime() > end.getTime()) {
			Date temp = start;
			start = end;
			end = temp;
		}
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(start);
		startCalendar.set(Calendar.MILLISECOND,0);
		startCalendar.set(Calendar.SECOND,0);
		startCalendar.set(Calendar.MINUTE,0);
		startCalendar.set(Calendar.HOUR_OF_DAY,0);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(end);
		endCalendar.set(Calendar.MILLISECOND,0);
		endCalendar.set(Calendar.SECOND,0);
		endCalendar.set(Calendar.MINUTE,0);
		endCalendar.set(Calendar.HOUR_OF_DAY,0);
		while (endCalendar.after(startCalendar)) {
			startCalendar.add(Calendar.DATE, 1);
			interval++;
		}
		return interval;
	}
	
	/**
	 * 时间戳字符串返回
	 * @param timeStamp
	 * @return
	 */
	public static String dateTimeFormat(Long timeStamp){
		SimpleDateFormat tf = new SimpleDateFormat(STFM);
		String date = tf.format(new Date(timeStamp*1000));
		return date;
	}
	
	/**
	 * 获取时间日期
	 * @param timeStamp
	 * @return
	 */
	public static String dateString(Long timeStamp){
		SimpleDateFormat tf = new SimpleDateFormat(STFM_DATE);
		String date = tf.format(new Date(timeStamp*1000));
		return date;
	}
	
	public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
	
	/**
	* 字符串转换成日期
	* @param str
	* @return date
	*/
	public static Date StrToDate(String str) {
	  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}
	
	// 获取当天零点时间
	public static Date getTodayBeginDate() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.HOUR_OF_DAY, 0);  
        cal.set(Calendar.SECOND, 0);  
        cal.set(Calendar.MINUTE, 0);  
        cal.set(Calendar.MILLISECOND, 0);  
        return cal.getTime();  
    }  
	
	// 获得本周一0点时间  
    public static Date getWeekBeginDate() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
        return cal.getTime();  
    }  
    
    // 获得本月第一天0点时间  
    public static Date getMonthBeginDate() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));  
        return cal.getTime();  
    }  
	
	public static void main(String[] args) throws ParseException {
		System.out.println("当天零点：" + getTodayBeginDate());
		System.out.println("本周零点: " + getWeekBeginDate());
		System.out.println("本月零点: " + getMonthBeginDate());
	}
}
