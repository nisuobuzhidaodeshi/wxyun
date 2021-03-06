package com.tencent.wxcloudrun.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * @author ThinkGem
 * @version 2013-05-22
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);
    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";
    private static final String BLANK_STRING = "";
    /**
     * 匹配金额字符串 允许0-2位小数
     */
    public static final String MONEY = "^(\\d+(?:\\.\\d{0,2})?|-1)$";
	/**
	 * 转让名称前缀
	 */
	public static final String BONDNAME = "转让-";
	/**
	 * 短横线
	 */
	public static final String HYPHEN = "-";
	
	private StringUtils() {
	}
    
    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static byte[] getBytes(String str){
    	if (str != null){
    		try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
			}
    	}
    	return null;
    }
    
    /**
     * 转换为字节数组
     * @param bytes
     * @return
     */
    public static String toString(byte[] bytes){
    	try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(), e);
			return EMPTY;
		}
    }
    /**
     * 是否包含字符串
     * @param str 验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs){
    	if (str != null){
        	for (String s : strs){
        		if (str.equals(trim(s))){
        			return true;
        		}
        	}
    	}
    	return false;
    }
    
	/**
	 * 替换掉HTML标签方法
	 * @param html
	 * @return
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		return m.replaceAll("");
	}
	
	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html){
		if (html == null){
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}
	

	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return "";
	}
	
	/**
	 * 转换为Double类型
	 * @param val
	 * @return
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return 0D;
		}
	}

	/**
	 *  转换为Float类型
	 * @param val
	 * @return
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 * @param val
	 * @return
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 * @param val
	 * @return
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}
	
	/**
	 * String to int
	 * 
	 * @param str
	 * @return
	 */
	public static int toInt(String str) {
		if (isBlank(str))
			return 0;
		int ret = 0;
		try {
			ret = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			ret = 0;
		}
		return ret;
	}
	
	public static int toInt(Object str) {
		if (isBlank(str))
			return 0;
		int ret = 0;
		try {
			ret = Integer.parseInt(str.toString());
		} catch (NumberFormatException e) {
			ret = 0;
		}
		return ret;
	}
	
	/**
	 * 转换为Boolean类型
	 * @param val
	 * @return
	 */
	public static Boolean toBoolean(Object val){
		return toLong(val).intValue()==1?true:false;
	}
	
	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	/**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCamelCase(String name) {
        if (name == null) {
            return null;
        }

        name = name.toLowerCase();

        StringBuilder sb = new StringBuilder(name.length());
        boolean upperCase = false;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCapitalizeCamelCase(String name) {
        if (name == null) {
            return null;
        }
        String tempName = toCamelCase(name);
        return tempName.substring(0, 1).toUpperCase() + tempName.substring(1);
    }
    
    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if (i > 0 && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }
    
    /**
     * 如果不为空，则设置值
     * @param target
     * @param source
     */
    public static void setValueIfNotBlank(String target, String source) {
		if (isNotBlank(source)){
			target = source;
		}
	}
 
    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     * @param objectString 对象串
     *   例如：row.user.id
     *   返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString){
    	StringBuilder result = new StringBuilder();
    	StringBuilder val = new StringBuilder();
    	String[] vals = split(objectString, ".");
    	for (int i=0; i<vals.length; i++){
    		val.append("." + vals[i]);
    		result.append("!"+(val.substring(1))+"?'':");
    	}
    	result.append(val.substring(1));
    	return result.toString();
    }
    
    /**
	 * 字符串空处理，去除首尾空格 如果str为null，返回"",否则返回str
	 * 
	 * @param str
	 * @return
	 */
	public static String isNull(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}

	/**
	 * 将对象转为字符串
	 * 
	 * @param o
	 * @return
	 */
	public static String isNull(Object o) {
		if (o == null) {
			return "";
		}
		String str;
		if (o instanceof String) {
			str = (String) o;
		} else {
			str = o.toString();
		}
		return str.trim();
	}

	/**
	 * 检验是否为空或空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return isNull(str).length()==0;//.equals(BLANK_STRING);
	}

	public static boolean isBlank(Object o) {
		return isNull(o).length()==0;//.equals(BLANK_STRING);
	}

	/**
	 * 检验是否非空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return isNull(str).length()>0;//.equals(BLANK_STRING);
	}

	/**
	 * 检验手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(final String phone) {
		if(isBlank(phone)){
			return false;
		}
		Pattern regex = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9])|(14[0-9])|(19[0-9]))\\d{8}$");
		Matcher matcher = regex.matcher(phone);
		boolean isMatched = matcher.matches();
		return isMatched;
	}


	/**
	 * 校验身份证号码
	 * 
	 * @param cardId
	 * @return
	 */
	public static boolean isCard(final String cardId) {
		if(isBlank(cardId)){
			return false;
		}
		// 身份证正则表达式(15位)
		Pattern isIDCard1 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
		// 身份证正则表达式(18位)
		Pattern isIDCard2 = Pattern
				.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
		Matcher matcher1 = isIDCard1.matcher(cardId);
		Matcher matcher2 = isIDCard2.matcher(cardId);
		boolean isMatched = matcher1.matches() || matcher2.matches();
		return isMatched;
	}
	
	/**
	 * 首字母大写
	 *
	 * @param s
	 * @return
	 */
	public static String firstCharUpperCase(String s) {
		StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
		sb.append(s.substring(1, s.length()));
		return sb.toString();
	}
		
	/**
	 * 根据身份证Id获取性别
	 * @param cardId
	 * @return
	 */
	public static String getSex(String cardId) {
		int sexNum = 0;
		//15位的最后一位代表性别，18位的第17位代表性别，奇数为男，偶数为女
		if (cardId.length() == 15) {
			sexNum = cardId.charAt(cardId.length() - 1);
		} else {
			sexNum = cardId.charAt(cardId.length() - 2);
		}
		
		if (sexNum % 2 == 1) {
			return "M";
		} else {
			return "F";
		}
	}
	
	/**
	 * 根据身份证Id获取出生年月日
	 * @param cardId
	 * @return
	 */
	public static String getBirthday(String cardId) {
		String birthday ;
		//15位7-12位出生年月日 如590101 代表 19590101; 18位7-14位出生年月日如 19590101
		if (cardId.length() == 15) {
			birthday = "19" + cardId.substring(6, 12);
		} else {
			birthday = cardId.substring(6, 14);
		}
		return birthday;
	}
	
	/**
	 * 根据身份证Id获取出生年份
	 * @param cardId
	 * @return
	 */
	public static String getBirthYear(String cardId) {
		String birthYear ;
		//15位7-12位出生年月日 如590101 代表 19590101; 18位7-14位出生年月日如 19590101
		if (cardId.length() == 15) {
			birthYear = "19" + cardId.substring(6, 8);
		} else {
			birthYear = cardId.substring(6, 10);
		}
		return birthYear;
	}

	/**
	 * 判断字符串是否是浮点数
	 * @author QianPengZhan
	 * @date 2016年9月28日
	 * @param value
	 * @return
	 */
	public static boolean isDouble(String value) {
		if (isBlank(value)) {
			return false;
		}
		Pattern regex = Pattern.compile("[\\d]+\\.[\\d]+");
		Matcher matcher = regex.matcher(value);
		return matcher.matches();
	}
	
	

	/**
	 * 判断字符串是否为整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str, int len) {
		if (isBlank(str) || 0 >= len) {
			return false;
		}
		Pattern regex = Pattern.compile("\\d{"+len+"}");
		Matcher matcher = regex.matcher(str);
		return matcher.matches();
	}

	
	/**
	 * 判断字符串是否符合组织机构代码格式
	 * 
	 * @param code
	 * @return
	 */
	public static boolean isOrgCode(String code) {
		int[] ws = { 3, 7, 9, 10, 5, 8, 4, 2 };
		String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String regex = "^([0-9A-Z]){8}-[0-9|X]$";
		if (isBlank(code)) {
			return false;
		}
		if (!code.matches(regex)) {
			return false;
		}
		int sum = 0;
		for (int i = 0; i < 8; i++) {
			sum += str.indexOf(String.valueOf(code.charAt(i))) * ws[i];
		}
		int c9 = 11 - (sum % 11);

		String sc9 = String.valueOf(c9);
		if (11 == c9) {
			sc9 = "0";
		} else if (10 == c9) {
			sc9 = "X";
		}
		return sc9.equals(String.valueOf(code.charAt(9)));
	}
	
	/**
	 * 校验字段是否是只由数字和字母组成
	 */
	public static boolean isIntAndChar(String str, int len){
		if (isBlank(str)||len<=0) {
			return false;
		}
		Pattern regex = Pattern.compile("[a-zA-Z0-9]{"+len+"}");
		Matcher matcher = regex.matcher(str);
		return matcher.matches();
	}
	
	/**
	 * 判断字符串是否符合公司名称格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isCompanyName(String str) {
		if (isBlank(str)) {
			return false;
		}
		Pattern regex = Pattern.compile("[\\（\\）\\u4E00-\\u9FA5]{2,30}");
		Matcher matcher = regex.matcher(str);
		return matcher.matches();
	}

	/**
	 * 隐藏头几位
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String hideFirstChar(final String str, final int len) {
		if (str == null)
			return null;
		char[] chars = str.toCharArray();
		if (str.length() <= len) {
			for (int i = 0; i < chars.length; i++) {
				chars[i] = '*';
			}
		} else {
			for (int i = 0; i < 1; i++) {
				chars[i] = '*';
			}
		}
		return new String(chars);
	}

	/**
	 * 隐藏末几位
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String hideLastChar(String str, int len) {
		if (str == null)
			return null;
		char[] chars = str.toCharArray();
		if (str.length() <= len) {
			return hideLastChar(str, str.length() - 1);
		} else {
			for (int i = chars.length - 1; i > chars.length - len - 1; i--) {
				chars[i] = '*';
			}
		}
		return new String(chars);
	}

	/**
	 * 公司名称隐藏专用
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String hideCompanyName(final String str,final int lenMin, final int len) {
		if (str == null)
			return null;
		if (str.length() > len) {
			return "****"+str.substring(str.length()-len,str.length());
		} else if(str.length() > lenMin) {
			return "****"+str.substring(str.length()- lenMin,str.length());
		} else {
			return str;
		}
	}
	
	/**
	 * 指定起始位置字符串隐藏
	 * 
	 * @param str
	 * @param index1
	 * @param index2
	 * @return
	 */
	public static String hideStr(String str, int index1, int index2) {
		if (str == null)
			return null;
		String str1 = str.substring(index1, index2);
		String str2 = str.substring(index2);
		String str3 = "";
		if (index1 > 0) {
			str1 = str.substring(0, index1);
			str2 = str.substring(index1, index2);
			str3 = str.substring(index2);
		}
		char[] chars = str2.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			chars[i] = '*';
		}
		str2 = new String(chars);
		return str1 + str2 + str3;
	}

	/**
	 * Object数组拼接为字符串
	 * @param args
	 * @return
	 */
	public static String contact(Object[] args) {
		if(args == null){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i]);
			if (i < args.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Long数组拼接为字符串
	 * @param args
	 * @return
	 */
	public static String contact(long[] args) {
		if(args == null){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i]);
			if (i < args.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 数字数组拼接为字符串
	 * 
	 * @param arr
	 * @return
	 */
	public static String array2Str(int[] arr) {
		if(arr == null){
			return "";
		}
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			s.append(arr[i]);
			if (i < arr.length - 1) {
				s.append(",");
			}
		}
		return s.toString();
	}

	/**
	 * 字符串数组转换为数字数组
	 * 
	 * @param strarr
	 * @return
	 */
	public static int[] strarr2intarr(String[] strarr) {
		int[] result = new int[strarr.length];
		for (int i = 0; i < strarr.length; i++) {
			result[i] = Integer.parseInt(strarr[i]);
		}
		return result;
	}

	/**
	 * 大写字母转成“_”+小写 驼峰命名转换为下划线命名
	 * 
	 * @param str
	 * @return
	 */
	public static String toUnderline(String str) {
		char[] charArr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		sb.append(charArr[0]);
		for (int i = 1; i < charArr.length; i++) {
			if (charArr[i] >= 'A' && charArr[i] <= 'Z') {
				sb.append('_').append(charArr[i]);
			} else {
				sb.append(charArr[i]);
			}
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * 下划线改成驼峰样子
	 * 
	 * @param str
	 * @return
	 */
	public static String clearUnderline(String str) {
		char[] charArr = StringUtils.isNull(str).toLowerCase().toCharArray();
		StringBuffer sb = new StringBuffer();
		sb.append(charArr[0]);
		boolean isClear = false;
		for (int i = 1; i < charArr.length; i++) {
			if (charArr[i] == '_') {
				isClear = true;
				continue;
			}
			if (isClear && charArr[i] >= 'a' && charArr[i] <= 'z') {
				char c = (char) (charArr[i] - 32);
				sb.append(c);
				isClear = false;
			} else {
				sb.append(charArr[i]);
			}

		}
		return sb.toString();
	}

	public static byte toByte(String str) {
		if (StringUtils.isBlank(str))
			return 0;
		byte ret = 0;
		try {
			ret = Byte.parseByte(str);
		} catch (NumberFormatException e) {
			ret = 0;
		}
		return ret;
	}

	/**
	 * String to Long
	 * 
	 * @param str
	 * @return
	 */
	public static long toLong(String str) {
		if (StringUtils.isBlank(str))
			return 0L;
		long ret = 0;
		try {
			ret = Long.parseLong(str);
		} catch (NumberFormatException e) {
			ret = 0;
		}
		return ret;
	}

	/**
	 * String[] to long[]
	 * 
	 * @param str
	 * @return
	 */
	public static long[] toLongs(String[] str) {
		if (str == null || str.length < 1)
			return new long[] { 0L };
		long[] ret = new long[str.length];
		ret = (long[]) ConvertUtils.convert(str, long.class);
		return ret;
	}
	
	/**
	 * String[] to double[]
	 * 
	 * @param str
	 * @return
	 */
	public static double[] toDoubles(String[] str) {

		if (str == null || str.length < 1)
			return new double[] { 0L };
		double[] ret = new double[str.length];
		for (int i = 0; i < str.length; i++) {
			ret[i] = toDouble(str[i]);
		}
		return ret;
	}


	/**
	 * 生成指定长度的随机字符串，字母加数字组合
	 * @param length
	 * @return
	 */
    public static String getRandomString(int length) { 
        StringBuffer val = new StringBuffer();
//        Random random = new Random();
        //参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) { 
            String charOrNum =RandomUtil.securedRanddomInteger(2)% 2 == 0 ? "char" : "num";//random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp =RandomUtil.securedRanddomInteger(2) % 2 == 0 ? 65 : 97; // random.nextInt(2) % 2 == 0 ? 65 : 97;
				val.append((char)(RandomUtil.securedRanddomInteger(26) + temp));  //(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
				val.append(String.valueOf(RandomUtil.securedRanddomInteger(10))); //String.valueOf(random.nextInt(10));
            }  
        }  
        return val.toString();
    }
    
    /**
     * “参数=参数值”的模式用“&”字符拼接成字符串转换为map，例如："username=zhangsan&age=14"
     * @param str
     * @return
     */
    public static Map<String, Object> toMap(String str) {
    	Map<String, Object> data = new HashMap<String, Object>();
    	String[] str2 = str.split("&");
		for (String ss : str2) {
			String[] str3 = ss.split("=");
			data.put(str3[0], str3[1]);
		}
    	return data;
    }
    
    /**
     * “参数=参数值”的模式用“&”字符拼接成字符串转换为map，例如："username=zhangsan&age=14"
     * @param str
     * @return
     */
    public static Map<String, Object> mapToStringtoMap(String str) {
    	String[] str2 = str.replace("{", "").replace("}", "").split(",");
    	Map<String, Object> data = new HashMap<String, Object>(str2.length);
		for (String ss : str2) {
			String[] str3 = ss.split("=");
			data.put(str3[0].trim(), str3[1]);
		}
    	return data;
    }
    
    /**
     * 方法名称:toString
     * 传入参数:map
     * 返回值:String
    */
    public static String toString(Map<String, Object> map){
    	StringBuffer sb = new StringBuffer();
    	Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()) {
            Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = map.get(key);
            sb.append(key.trim()).append("=").append((String)value);
            if(iterator.hasNext()){
            	sb.append("&");
            }
        }
      return sb.toString();
    }

    
    /**
     * 判断字符串是否是金额  允许0到2位小数
     * @param str 匹配的字符串
     * @return
     */
    public static boolean isMoney(String str){
        return regular(str, MONEY);
    }
    
    /**
     * 匹配是否符合正则表达式pattern 匹配返回true
     *
     * @param str     匹配的字符串
     * @param pattern 匹配模式
     * @return boolean
     */
    private static boolean regular(final String str, final String pattern) {
        if (null == str || str.trim().length() <= 0)
            return false;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
	/**
	 * MD5加密规则，用于注册时 明文加密
	 * @param str
	 * @return
	 */
	public static String getMd5Sign(String str) {
		String md5Str = MD5Utils.encode("ifaes_"+str+"_erongdu");
		return md5Str;
	}
	
   /**
	*隐藏手机号码
 	*
 	*/
	public static String hideMobile(String mobile) {
		 if(isBlank(mobile)){
			 return "";
		 }
		 return mobile.substring(0,3) + "****" + mobile.substring(7);
	}
	
	  /**
     * 
     * 部分场合需要隐藏用户的真实姓名，格式为只显示姓。 如： 李**，张*
     * @author  FangJun
     * @date 2016年7月27日
     * @param realName 真实姓名 原本字符串
     * @return 隐藏后的姓名  
     */
	public  static String hideName(String realName) {
		 if(isBlank(realName)){
			 return "";
		 }
		  if(realName.length()==1){
			  return realName;
		  }
		  StringBuilder buffer=new StringBuilder(realName.length());
		  buffer.append(realName.charAt(0));
		  int len=realName.length();
		  for(int i = 1 ;i< len ;i++){
			  buffer.append("*");
		  }
		 return buffer.toString();
	}
	
	 /**
     * 
     * 部分场合需要隐藏用户的真实姓名，格式为只显示姓。 如： 李**，张*
     * @author  FangJun
     * @date 2016年7月27日
     * @param realNames 真实姓名 原本字符串
     * @return 隐藏后的姓名  
     */
	public  static String hideListName(String... realNames) {
		StringBuilder buffer=new StringBuilder("");
		 for(String realName:realNames){
			 if(isNotBlank(realName)){
				 buffer.append(hideName(realName));
				 buffer.append("，");
			 }
		 }
		 if(buffer.length() > 1){
			 buffer.deleteCharAt(buffer.length()-1);
		 }
		 return buffer.toString();
	}
    
	/**
	 * 去除字符串首尾出现的某个字符. 
	 * @author QianPengZhan
	 * @date 2016年7月27日
	 * @param source 源字符串
	 * @param element 需要去除的字符
	 * @return
	 */
	public static String trimFirstAndLastChar(String source, char element){
        boolean beginIndexFlag;  
        boolean endIndexFlag;  
        do{  
            int beginIndex = source.indexOf(element) == 0 ? 1 : 0;  
            int endIndex = source.lastIndexOf(element) + 1 == source.length() ? source.lastIndexOf(element) : source.length();  
            source = source.substring(beginIndex, endIndex);  
            beginIndexFlag = source.indexOf(element) == 0;  
            endIndexFlag = source.lastIndexOf(element) + 1 == source.length();  
        } while (beginIndexFlag || endIndexFlag);  
        return source;  
    }  
	
	  /**
     * 
     *  前台投资详情页显示[投资记录] 展示投资人用户名，
     *  只展示用户名第一与最后一个字符，其他置*展示,如：r******1
     * @author  FangJun
     * @date 2016年7月27日
     * @param userName  用户账号 原本字符串
     * @return 隐藏后的用户账号  
     */
	public  static String hideUserName(String userName) {
		 if(isBlank(userName)){
			 return null;
		 }
		  if(userName.length() < 3){
			  return userName;
		  }
		  StringBuffer buffer=new StringBuffer(userName.length());
		  buffer.append(userName.charAt(0));
		  int len=userName.length();
		  for(int i = 1 ;i< len-1 ;i++){
			  buffer.append("*");
		  }
		  buffer.append(userName.charAt(userName.length()-1));
		 return buffer.toString();
	}
	
	/**
	 * 获取小时列表
	 * @author fxl
	 * @date 2016年8月15日
	 * @return
	 */
	public static List<String> getHourList(){
		List<String> hourList = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			if(i<10){
				hourList.add("0"+i);
			}else{
				hourList.add(String.valueOf(i));
			}
		}
		return hourList;
	}
	
	/**
	 * Returns a formatted string using the specified format string and
	 * arguments.<br>
	 * 支持%s和{arg}两种占位的格式化操作
	 * 
	 * @param format
	 * @param args
	 * @return A formatted string
	 */
	public static String format(String format, Object... args) {
		if (format.contains("%s") || format.contains("%d")) {
			return String.format(format, args);
		} else if (format.contains("{")) {
			Pattern pattern = Pattern.compile("(?<=\\{)(.+?)(?=\\})");
			Matcher matcher = pattern.matcher(format);
			int index = 0;
			while (matcher.find()) {
				String gm = "{" + matcher.group() + "}";
				format = format.replace(gm, String.valueOf(args[index++]));
			}
		}
		return format;
	}
	
	/**
	 * 获取a标签中的href的url值
	 * @author QianPengZhan
	 * @date 2016年10月22日
	 * @param str
	 * @return
	 */
	public static String getAHTMLUrl(String str){
		Pattern pattern = Pattern.compile("<a[^>]*href=['\"]?([^'\"]*)['\"]?");
        String href = null;
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            href = matcher.group(1);
        }
        return href;
	}
	
	/**
	 * 字符串类型数字加法返回结果字符串
	 * @author ZhangBiao
	 * @date 2016年10月27日
	 * @param str
	 * @param num
	 * @return
	 */
	public static String stringAdd(String str,int num){
		int sum = Integer.valueOf(str)+num;
		return String.valueOf(sum);
	}
	




	/**
	 * 首字母小写
	 * @param s
	 * @return
	 */
	public static String firstCharLowerCase(String s) {
		StringBuffer sb = new StringBuffer(s.substring(0, 1).toLowerCase());
		sb.append(s.substring(1, s.length()));
		return sb.toString();
	}
	
	/**
     * 字符串解码UTF-8
     * @param str
     * @return
     */
	public static String decodeUTF8(String str) {
		try {
			if (isNotBlank(str)) {
				return URLDecoder.decode(str, CHARSET_NAME);
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("URL解码异常,str={}, {}", str, e.getMessage(),e);
		}
		return "";
	}
    
	/**
	 * 字符串编码UTF-8
	 * @param str
	 * @return
	 */
	public static String encodeUTF8(String str) {
		try {
			if (isNotBlank(str)) {
				return URLEncoder.encode(str, CHARSET_NAME);
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("URL编码异常,str={},{}", str, e.getMessage(),e);
		}
		return "";
	}
	
    /**
	 * 根据规定的长度拆分字符串改为字符串数组
	 * 
	 * @param string
	 *            字符串
	 * @param len
	 *            规定的长度
	 * @return 字符串数组
	 */
	public static String[] splitString(String string, int len) {
		int x = string.length() / len;
		int y = string.length() % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		String[] strings = new String[x + z];
		String str = "";
		for (int i = 0; i < x + z; i++) {
			if ((i == x + z - 1) && (y != 0)) {
				str = string.substring(i * len, i * len + y);
			} else {
				str = string.substring(i * len, i * len + len);
			}
			strings[i] = str;

		}
		return strings;
	}
    
	/**
	 * 生成默认密码8位:四位数字+两位大写+两位小写
	 * 
	 * @return
	 */
	public static String getDefultPWD() {
		String bigCharacter = "ASDFGHJKLMNBVCXZQWERTYUIOP";
		String smallCharacter = "asdfghjklmnbvcxzqwertyuiop";
		StringBuffer sb = new StringBuffer();
//		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			sb.append(String.valueOf(RandomUtil.securedRanddomInteger(10)));//sb.append(String.valueOf(r.nextInt(10)));
		}
		for (int i = 0; i < 2; i++) {
			sb.append(bigCharacter.charAt(RandomUtil.securedRanddomInteger(26)));//sb.append(bigCharacter.charAt(r.nextInt(26)));
		}
		for (int i = 0; i < 2; i++) {
			sb.append(smallCharacter.charAt(RandomUtil.securedRanddomInteger(26)));//sb.append(smallCharacter.charAt(r.nextInt(26)));
		}
		return sb.toString();
	}

	public static String addPercentSignRight(String param){
		return param + "%";
	}
}
