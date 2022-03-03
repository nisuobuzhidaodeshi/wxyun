package com.tencent.wxcloudrun.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 对wx.getUserProfile返回的参数进行校验
 * @author cf
 * @date 2022/2/18 13:27
 */
public class SignatureUtil {
    public static String getSignature(String rawData, String sessionKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String stringASCII = rawData + sessionKey;
        String signature = null;
        try {
            //指定sha1算法
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(stringASCII.getBytes("UTF-8"));
            //获取字节数组
            byte messageDigest[] = digest.digest();
            // 创建 Hex 字符串
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
                signature = hexString.toString().toLowerCase();
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw e;
        }
        return signature;
    }
}
