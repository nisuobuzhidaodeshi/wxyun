package com.tencent.wxcloudrun.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
	
	public static final String PATTERN_YYMMDDHHMMSS="yyMMddHHmmss";
	
	/** 格式 ：yyyy-MM-dd HH:mm:ss */
	public static final String DATEFORMAT_STR_001 = "yyyy-MM-dd HH:mm:ss";
	/** 格式 ：yyyy-MM-dd */
	public static final String DATEFORMAT_STR_002 = "yyyy-MM-dd";
	/** 格式 ：MM-dd */
	public static final String DATEFORMAT_STR_003 = "MM-dd";
	/** 格式 ：HH:mm:ss */
	public static final String DATEFORMAT_STR_004 = "HH:mm:ss";
	
	/** 格式 ：yyyyMMddHHmmss */
	public static final String DATEFORMAT_STR_011 = "yyyyMMddHHmmss";
	/** 格式 ：yyyyMMdd */
	public static final String DATEFORMAT_STR_012 = "yyyyMMdd";
	/** 格式 ：yyyyMM */
	public static final String DATEFORMAT_STR_013 = "yyyyMM";
	/** 格式 ：HHmmss */
	public static final String DATEFORMAT_STR_014 = "HHmmss";
	/** 格式 ：yyyyMMddHH:mm:ss */
	public static final String DATEFORMAT_STR_015 = "yyyyMMdd HH:mm:ss";
	/** 格式 ：yyyy.MM */
	public static final String DATEFORMAT_STR_016 = "yyyy.MM";
	/** 格式 ：yyyy/MM/dd */
	public static final String DATEFORMAT_STR_017 = "yyyy/MM/dd";
	/** 格式 ：yyyy/MM */
	public static final String DATEFORMAT_STR_018 = "yyyy/MM";
	/** 格式 ：yyyy-MM */
	public static final String DATEFORMAT_STR_019 = "yyyy-MM";
	
	/** 格式 ：yyyy年MM月dd日 HH时mm分ss秒 */
	public static final String DATEFORMAT_STR_021 = "yyyy年MM月dd日 HH时mm分ss秒";
	/** 格式 ：yyyy年MM月dd日 */
	public static final String DATEFORMAT_STR_022 = "yyyy年MM月dd日";
	/** 格式 ：MM月dd日 hh:mm */
	public static final String DATEFORMAT_STR_023 = "MM月dd日 hh:mm";
	
	/** 日期开始 */
	public static final String DATE_START = " 00:00:00";
	/** 日期开始 */
	public static final String DATE_END = " 23:59:59";
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM","yyyyMMddHHmmss"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	public static void main(String[] args){
		System.out.println(DateUtils.getDate("yyyy年MM月dd日"));
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}
	
	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear(Date date) {
		return formatDate(date, "yyyy");
	}


	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String convertDateFormat(String date){
		if(StringUtils.isBlank(date)){
			return StringUtils.EMPTY;
		}
		SimpleDateFormat sf1 = new SimpleDateFormat(DATEFORMAT_STR_012);
	    SimpleDateFormat sf2 = new SimpleDateFormat(DATEFORMAT_STR_002);
	    String rtnDate = null;
	    try {
	    	rtnDate = sf2.format(sf1.parse(date));
		} catch (ParseException e) {
//			e.printStackTrace();
		}
	    return rtnDate;
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 获取过去的秒数
	 * @param date
	 * @return
	 */
	public static long pastSeconds(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = timeMillis/(60*60*1000)-day*24;
		long min = timeMillis/(60*1000)-day*24*60-hour*60;
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
    /** 
     * 计算两个日期之间相差的天数 
     * @param begin
     * @param end
     * @return 
     */  
    public static int daysBetween(Date begin, Date end){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long betweenDays = 0L;
		try {
			begin = sdf.parse(sdf.format(begin));
			end = sdf.parse(sdf.format(end));
			Calendar cal = Calendar.getInstance();
			cal.setTime(begin);
			long time1 = cal.getTimeInMillis();
			cal.setTime(end);
			long time2 = cal.getTimeInMillis();
			betweenDays = (time2 - time1) / (1000 * 3600 * 24);
		} catch (ParseException e) {
            LoggerFactory.getLogger(DateUtils.class).error("为了安全扫描而加的打印",e);
        }
		return Integer.parseInt(String.valueOf(betweenDays));
	}
	/**
	 * 获取两个日期之间的小时数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoHours(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60);
	}
	/**
	 * 比较日期大小 前者比后者大 返回TRUE 后者比前者大返回FALSE
	 * @param before
	 * @param after
	 * @return
	 */
	public static boolean compareDate (Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		if(beforeTime > afterTime){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 得到当前日期和时间字符串
	 */
	public static String getDateTime(String pattern) {
		if(StringUtils.isBlank(pattern)){
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return formatDate(new Date(), pattern);
	}
	
	/**
	 * 获得当前时间
	 * @return
	 */
	public static Date getNow() {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();
		return currDate;
	}
	/**
	 * 获得当前日期
	 * @return
	 */
	public static Date getToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date currDate = cal.getTime();
		return currDate;
	}
	
	/**
	 * 获得当前最后日期
	 * @return
	 */
	public static Date getTodayEnd() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		Date currDate = cal.getTime();
		return currDate;
	}
	
	/**
	 * 获得日期最后日期
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		Date currDate = cal.getTime();
		return currDate;
	}
	
	/**
	 * 截掉时分秒，到日
	 * @return
	 */
	public static Date trunc(Date date) {
		if(date==null){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date res = cal.getTime();
		return res;
	}
	/**
	 * 截掉时分秒，到日
	 * @return
	 */
	public static String trunc(String date, String pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.format(format.parse(date).getTime());
		} catch (ParseException e) {
			LOGGER.error(e.getMessage(),e);
		}
		return null;
	}
	
	/**
	 * 日期转换为字符串 格式自定义
	 * 
	 * @param date
	 * @param f
	 * @return
	 */
	public static String dateStr(Date date, String f) {
		SimpleDateFormat format = new SimpleDateFormat(f);
		String str = format.format(date);
		return str;
	}

	/**
	 * 日期转换为字符串 MM月dd日 hh:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr(Date date) {
		return dateStr(date, DATEFORMAT_STR_023);
	}

	/**
	 * 日期转换为字符串 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr2(Date date) {
		return dateStr(date, DATEFORMAT_STR_002);
	}

	/**
	 * yyyy年MM月dd日HH时mm分ss秒
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr5(Date date) {
		return dateStr(date, DATEFORMAT_STR_021);
	}

	/**
	 * yyyyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr3(Date date) {
		return dateStr(date, DATEFORMAT_STR_011);
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr4(Date date) {
		return dateStr(date, DATEFORMAT_STR_001);

	}

	/**
	 * yyyy年MM月dd日
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr6(Date date) {
		return dateStr(date, DATEFORMAT_STR_022);
	}

	/**
	 * yyyyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr7(Date date) {
		return dateStr(date, DATEFORMAT_STR_012);
	}

	/**
	 * MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr8(Date date) {
		return dateStr(date, DATEFORMAT_STR_003);
	}
	
	/**
	 * 默认的valueOf 方法，格式化 yyyy-mm-dd HH:mm:ss
	 * @param str 
	 * @return
	 */
	public static Date valueOf(String str){
		return valueOf(str, DATEFORMAT_STR_001);
	}
	
	/**
	 * 
	 * 自定义format格式化字符串为date
	 * @param str 要格式化的字符串
	 * @param dateFormatStr 
	 * @return
	 */
	public static Date valueOf(String str, String dateFormatStr){
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormatStr);
		ParsePosition pos = new ParsePosition(0);
		Date strtoDate = formatter.parse(str, pos);
		return strtoDate;
	}
	
	/**
	 * 前/后?分钟
	 * 
	 * @param d
	 * @param minute
	 * @return
	 */
	public static Date rollMinute(Date d, int minute) {
		return new Date(d.getTime() + minute * 60 * 1000);
	}
	
	/**
	 * 前/后?秒
	 * 
	 * @param d
	 * @param second
	 * @return
	 */
	public static Date rollSecond(Date d, int second) {
		return new Date(d.getTime() + second * 60 * 60 * 1000);
	}

	/**
	 * 前/后?天
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date rollDay(Date d, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
	
	/**
	 * 固定日期
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date fixDay(Date d, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int month = cal.get(Calendar.MONTH);
		cal.set(Calendar.DAY_OF_MONTH, day);
		int fixMonth = cal.get(Calendar.MONTH);
		if(month != fixMonth){
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return cal.getTime();
	}

	/**
	 * 前/后?月
	 * 
	 * @param d
	 * @param mon
	 * @return
	 */
	public static Date rollMon(Date d, int mon) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MONTH, mon);
		return cal.getTime();
	}
	
	/**
	 * 前/后?周
	 * 
	 * @param d
	 * @param week
	 * @return
	 */
	public static Date rollWeek(Date d, int week) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.WEEK_OF_YEAR, week);
		return cal.getTime();
	}

	/**
	 * 前/后?年
	 * 
	 * @param d
	 * @param year
	 * @return
	 */
	public static Date rollYear(Date d, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.YEAR, year);
		return cal.getTime();
	}

	public static Date rollDate(Date d, int year, int mon, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, mon);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
	/**
	 * 
	 * 获取Date的开始时间
	 * @author wyw
	 * @date 2016-8-6
	 * @param date
	 * @return
	 */
	public static  String getDateStart(Date date){
		return DateUtils.formatDate(date, DateUtils.DATEFORMAT_STR_002)+DATE_START;
	}
	/**
	 * 
	 * 获取Date的结束时间
	 * @author wyw
	 * @date 2016-8-6
	 * @param date
	 * @return
	 */
	public static  String getDateEnd(Date date){
		return DateUtils.formatDate(date, DateUtils.DATEFORMAT_STR_002)+DATE_END;
	}
	/**
	 * 
	 * 功能：得到年份月份月初 格式为： (eg: 2016-01-01 00:00:00)<br> 
	 * @author wyw
	 * @date 2016-8-19
	 * @param year
	 * @param month 从一月份开始
	 * @return
	 */
    public static String monthStartTime(String year,int month) {
        String strDay = null;
        strDay = month >= 10 ? String.valueOf(month) : "0" + month;
        return year + "-" + strDay + "-01"+DATE_START;
    }
    /**
     * 功能：得到年份月份月底 格式为： (eg: 2016-02-29 23:59:59)<br> 
     * @return String
     * @author pure
     */
    public static String monthEndTime(String year,int month) {
        String strY = null;
        String strZ = null;
        boolean leap = false;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 ||month == 12) {
            strZ = "31";
        }else if  (month == 4 ||month == 6 ||month == 9 || month == 11) {
            strZ = "30";
        }else if (month == 2) {
            leap = leapYear(Integer.valueOf(year));
            if (leap) {
                strZ = "29";
            }
            else {
                strZ = "28";
            }
        }
        strY = month >= 10 ? String.valueOf(month) : "0" + month;
        return year + "-" + strY + "-" + strZ+DATE_END;
    }
    /**
     * 功能：判断输入年份是否为闰年<br>
     * 
     * @param year
     * @return 是：true  否：false
     * @author pure
     */
    public static boolean leapYear(int year) {
        boolean leap;
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) leap = true;
                else {leap = false;}
            }
            else {leap = true;}
        }
        else {leap = false;}
        return leap;
    }

    /**
     * 校验是否同一个月
     * @author fxl
     * @date 2016年10月11日
     * @param date1
     * @param date2
     * @return
     */
    public static boolean checkSameMonth(Date date1,Date date2){
    	if((formatDate(date1, "MM")).equals(formatDate(date2, "MM"))){
    		return true;
    	}else{
    		return false;
    	}
	}
    
    /**
     * 获取今年的生日月第一天
     * @author fxl
     * @date 2016年10月11日
     * @param date
     * @return
     */
    public static Date getBirthMonth(Date date){
    	String month = formatDate(date, "MM");
    	String year = getYear();
    	return valueOf(year+"-"+month+"-"+"01",DATEFORMAT_STR_002);
	}
    
    /**
     * 动态取得一年的时间区间（以当前月往前推5个月，往后推6个月）
     * @return
     */
    public static String[] getYearDynamicSection(){
    	String[] section = new String[2];
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.MONTH, -5);
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	SimpleDateFormat format = new SimpleDateFormat(DATEFORMAT_STR_002);
    	section[0] = format.format(cal.getTime()) +" 00:00:00";
    	
    	cal.add(Calendar.MONTH, 12);
    	cal.add(Calendar.DAY_OF_MONTH, -1);
    	section[1] = format.format(cal.getTime()) + " 23:59:59";
    	return section;    	
    }
    
    /**
	 * @param Begin 开始日期 ，格式：yyyy-MM-dd
	 * @param end   结束时间， 格式：yyyy-MM-dd
	 * @return 返回Map 获取相隔多少年 get("YEAR")及为俩个时间年只差，月 天，类推 Key ： YEAR MONTH DAY 如果开始时间 晚于 结束时间 return null；
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Integer> getApartTime(String Begin, String end) {
		String[] temp = Begin.split("-");
		String[] temp2 = end.split("-");
		if (temp.length > 1 && temp2.length > 1) {
			Calendar ends = Calendar.getInstance();
			Calendar begin = Calendar.getInstance();

			begin.set(StringUtils.toInt(temp[0]), StringUtils.toInt(temp[1]), StringUtils.toInt(temp[2]));
			ends.set(StringUtils.toInt(temp2[0]), StringUtils.toInt(temp2[1]), StringUtils.toInt(temp2[2]));
			if (begin.compareTo(ends) < 0) {
				Map map = new HashMap<>();
				ends.add(Calendar.YEAR, -StringUtils.toInt(temp[0]));
				ends.add(Calendar.MONTH, -StringUtils.toInt(temp[1]));
				ends.add(Calendar.DATE, -StringUtils.toInt(temp[2]));
				map.put("YEAR", ends.get(Calendar.YEAR));
				map.put("MONTH", ends.get(Calendar.MONTH) + 1);
				map.put("DAY", ends.get(Calendar.DATE));
				return map;
			}
		}
		return null;
	}
	
	/**
	 * 获取自然周，格式:yyyyWW
	 * @param date
	 * @return
	 */
	public static String getYearWeek(Date date){
		if(date==null){
			return null;
		}
		
		Calendar cl = Calendar.getInstance();   
		cl.setTime(date);   
		//2012-12-31是2013年的第一周
		int week = cl.get(Calendar.WEEK_OF_YEAR);   
		cl.add(Calendar.DAY_OF_MONTH, -7);  
		int year = cl.get(Calendar.YEAR);  
		if(week<cl.get(Calendar.WEEK_OF_YEAR)){  
		    year+=1;  
		}  
		DecimalFormat df=new DecimalFormat("00");
		return year+df.format(week);
	}
	
	/**
	 * 获取当前周的第一天
	 * @param yearWeek  格式为"yyyyMM"
	 * @return
	 */
	public static Date getWeekFirstDay(int yearWeek){
		if(yearWeek<=100000 && yearWeek>=999999){
			return null;
		}
		
        Calendar cal = Calendar.getInstance();  
        //设置年份  
        cal.set(Calendar.YEAR,yearWeek/100);  
        //设置周  
        cal.set(Calendar.WEEK_OF_YEAR, yearWeek%100);  
        //设置该周第一天为星期日  
        cal.setFirstDayOfWeek(Calendar.SUNDAY);   
        //设置最后一天是星期日  
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek()); // Sunday  
        return cal.getTime();
	}
	
	/**
	 * 获取当前周的最后一天
	 * @param yearWeek  格式为"yyyyMM"
	 * @return
	 */
	public static Date getWeekLastDay(int yearWeek){
		if(yearWeek<=100000 && yearWeek>=999999){
			return null;
		}
		
        Calendar cal = Calendar.getInstance();  
        //设置年份  
        cal.set(Calendar.YEAR,yearWeek/100);  
        //设置周  
        cal.set(Calendar.WEEK_OF_YEAR, yearWeek%100);  
        //设置该周第一天为星期日  
        cal.setFirstDayOfWeek(Calendar.SUNDAY);   
        //设置最后一天是星期日  
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6); // Sunday  
        return cal.getTime();
	}
	
	/**
	 * 当前所在自然周的第一天的日期
	 * @param time
	 * @param parsePatterns
	 * @return
	 */
/*	public static String getFirstDateOfWeek(Date time, String parsePatterns){
        Calendar cal = Calendar.getInstance();  
        cal.setTime(time);  
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if (1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        cal.setFirstDayOfWeek(Calendar.SUNDAY);// 设置一个星期的第一天，按国际标准星期日是星期一  
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        
        SimpleDateFormat sdf = new SimpleDateFormat(parsePatterns); // 设置时间格式  
        return sdf.format(cal.getTime());  
	}*/
	
	public static String getFirstAndLastOfWeek(Date time, String parsePatterns){
        Calendar cal = Calendar.getInstance();  
        cal.setTime(time); 

        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 0) {
            d = -7;
        } else {
            d = 1 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        // 所在周开始日期
        String data1 = new SimpleDateFormat(parsePatterns).format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        // 所在周结束日期
        String data2 = new SimpleDateFormat(parsePatterns).format(cal.getTime());
        return data1 + "-" + data2;

    }
	
	/**
	 * 当前所在自然周的最后一天的日期
	 * @param time
	 * @param parsePatterns
	 * @return
	 */
/*	public static String getLastDateOfWeek(Date time, String parsePatterns){
        SimpleDateFormat sdf = new SimpleDateFormat(parsePatterns); // 设置时间格式  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(time);  
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if (1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        cal.setFirstDayOfWeek(Calendar.SUNDAY);// 设置一个星期的第一天，按国际标准星期日是星期一  
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        cal.add(Calendar.DATE, 6);  
        return sdf.format(cal.getTime());  
	}*/
	
	/**
	 * 当前所在自然月的第一天的日期
	 * @param date
	 * @param parsePatterns
	 * @return
	 */
	public static String getFirstDateOfMonth(Date date, String parsePatterns){
        SimpleDateFormat df = new SimpleDateFormat(parsePatterns);  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        Date theDate = calendar.getTime();  
        // 上个月第一天  
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();  
        gcLast.setTime(theDate);  
        gcLast.set(Calendar.DAY_OF_MONTH, 1);  
        return df.format(gcLast.getTime());  
	}
	
	/**
	 * 当前所在自然月的最后一天的日期
	 * @param date
	 * @param parsePatterns
	 * @return
	 */
	public static String getLastDateOfMonth(Date date, String parsePatterns){
        SimpleDateFormat df = new SimpleDateFormat(parsePatterns);  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        // 上个月最后一天  
        calendar.add(Calendar.MONTH, 1); // 加一个月  
        calendar.set(Calendar.DATE, 1); // 设置为该月第一天  
        calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天  
        return df.format(calendar.getTime());  
	}
    
    /** 
     *  获取两个日期相差的月数 
     * @param d1    较大的日期 
     * @param d2    较小的日期 
     * @return  如果d1>d2返回 月数差 否则返回0 
     */  
    public static int getMonthDiff(Date d1, Date d2) {  
        Calendar c1 = Calendar.getInstance();  
        Calendar c2 = Calendar.getInstance();  
        c1.setTime(d1);  
        c2.setTime(d2);  
        if(c1.getTimeInMillis() < c2.getTimeInMillis()) return 0;  
        int year1 = c1.get(Calendar.YEAR);  
        int year2 = c2.get(Calendar.YEAR);  
        int month1 = c1.get(Calendar.MONTH);  
        int month2 = c2.get(Calendar.MONTH);  
        int day1 = c1.get(Calendar.DAY_OF_MONTH);  
        int day2 = c2.get(Calendar.DAY_OF_MONTH);  
        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30  
        int yearInterval = year1 - year2;  
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数  
        if(month1 < month2 || month1 == month2 && day1 < day2) yearInterval --;  
        // 获取月数差值  
        int monthInterval =  (month1 + 12) - month2  ;  
        if(day1 < day2) {monthInterval --;}
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;  
    }
    
    /**
     * 计算两个时间相差秒数
     * @param startDate
     * @param endDate
     * @return
     */
    public static String getDistanceSecond(Date startDate,Date endDate){
    	Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
    	return String.valueOf((time2 - time1) / 1000L);
    }
    
    /**
     * 判断当前时间是周几
     * @param date
     * @return
     * @throws Exception
     */
	public static int dayForWeek(Date date) throws Exception {
		String pTime = DateUtils.dateStr4(date);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}
	
	/**
	 * 当前时间为一周的第几天
	 * @param date
	 * @return
	 */
	public static int dayOfWeek(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取距离当前时间的月份
	 * @param firstDate
	 * @return
	 */
	public static int getMonthDiff(Date firstDate) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(getNow());
		c2.setTime(firstDate);
		if (c1.getTimeInMillis() < c2.getTimeInMillis())
			return 0;
		int year1 = c1.get(Calendar.YEAR);
		int year2 = c2.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int month2 = c2.get(Calendar.MONTH);
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		int day2 = c2.get(Calendar.DAY_OF_MONTH);
		// 获取年的差值 假设 d1 = 2015-8-16 d2 = 2011-9-30
		int yearInterval = year1 - year2;
		// 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
		if (month1 < month2 || month1 == month2 && day1 < day2)
			yearInterval--;
		// 获取月数差值
		int monthInterval = (month1 + 12) - month2;
		monthInterval %= 12;
		return yearInterval * 12 + monthInterval;
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyyMMdd）
	 */
	public static String getyyyyMMdd() {
		return getDate(DATEFORMAT_STR_012);
	}
	
	/**
	 * 日期是否超过当天，大于则返回1,小于返回-1,等于返回0
	 * @param day  必须为yyyyMMdd格式的日期
	 * @return
	 */
	public static int beyondToday(String day){
		Date compareDate;
		try {
			compareDate = parseDate(day, "yyyyMMdd");
		} catch (ParseException e) {
//			e.printStackTrace();
			return 0;
		}
		Date today = getToday();
		if(compareDate==null)
		{
			return 0;
		}
		else if(compareDate.getTime()==today.getTime()){
			return 0;
		}else if(compareDate.getTime()>today.getTime()){
			return 1;
		}else{
			return -1;
		}
	}
	
	/**
	 * 日期格式转换
	 * @param dateStr
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 */
	public static String transfer(String dateStr, String fromFormat,String toFormat){
		Date date;
		try {
			date = parseDate(dateStr, fromFormat);
		} catch (ParseException e) {
			return dateStr;
		}
		return dateStr(date, toFormat);
	}
}
