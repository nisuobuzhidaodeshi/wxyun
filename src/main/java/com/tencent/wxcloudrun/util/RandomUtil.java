package com.tencent.wxcloudrun.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomUtil {
	private static final Logger log = LoggerFactory.getLogger(RandomUtil.class);
	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String numberChar = "0123456789";

	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}
	
	/**
	 * 返回一个定长的随机字符串(只包含数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateNumberString(int length) {
		StringBuffer sb = new StringBuffer();
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(letterChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	/**
	 * 生成一个定长的纯0字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return 纯0字符串
	 */
	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
					+ "的字符串发生异常！");
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(int num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
					+ "的字符串发生异常！");
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 安全的随机数,min到max（包括min，不包括max）
	 * @param max
	 * @param min
	 * @return
	 */
	public static int securedRanddomInteger(int max,int min)
	{
		return securedRanddomInteger(max-min)+min;
	}

	/**
	 * 安全的随机数，0到max(包括0，不包括max)
	 * @param max
	 * @return
	 */
	public static int securedRanddomInteger(int max)
	{
		SecureRandom secureRandom= null;
		int result = 0;
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
			if(secureRandom != null){
                result =  secureRandom.nextInt(max);
            }else{
			    throw new NoSuchAlgorithmException("生成安全的随机数出错");
            }
		} catch (NoSuchAlgorithmException e) {
			log.error("生成安全的随机数出错", e);
		}
		return result;
	}
	/*public static void main(String[] args) {
//		System.out.println(generateString(15));
		System.out.println(generateNumberString(8));
//		System.out.println(generateMixString(15));
//		System.out.println(generateLowerString(15));
//		System.out.println(generateUpperString(15));
//		System.out.println(generateZeroString(8));
//		System.out.println(toFixdLengthString(123, 8));
//		System.out.println(toFixdLengthString(123L, 8));
	}*/

}
