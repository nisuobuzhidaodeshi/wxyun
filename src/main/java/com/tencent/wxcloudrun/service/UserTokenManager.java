package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.UserToken;
import com.tencent.wxcloudrun.util.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserTokenManager {
    private static Map<String, UserToken> tokenMap = new HashMap<>();
    //private static Map<String, UserToken> idMap = new HashMap<>();

    public static String getOpenId(String token) {


        UserToken userToken = tokenMap.get(token);
        if(userToken == null){
            return null;
        }

        if(userToken.getExpireTime().before(new Date())){
            tokenMap.remove(token);
            //idMap.remove(userToken.getOpenId());
            return null;
        }else {
            //重置token过期时间
            Date update = new Date();
            Date expire = DateUtils.addMinutes(update,60);//测试用时间1分钟过期
            userToken.setExpireTime(expire);
            tokenMap.put(userToken.getToken(),userToken);
        }

        return userToken.getOpenId();
    }


    public static UserToken generateToken(String openId){
        UserToken userToken = null;


//        String token = CharUtil.getRandomString(32);
//        while (tokenMap.containsKey(token)) {
//            token = CharUtil.getRandomString(32);
//        }

        //token过期时间24小时
        Date create = new Date();
        Date expire = DateUtils.addMinutes(create,60);//测试用时间1分钟过期

        userToken = new UserToken();
        userToken.setToken(openId);
        userToken.setCreateTime(create);
        userToken.setExpireTime(expire);
        userToken.setOpenId(openId);
        tokenMap.put(openId, userToken);
        //idMap.put(openId, userToken);
        return userToken;
    }
}
