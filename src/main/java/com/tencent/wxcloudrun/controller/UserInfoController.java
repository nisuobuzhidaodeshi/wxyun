package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.convertor.CodeRequestConvertor;
import com.tencent.wxcloudrun.convertor.UserInfoSecretConvertor;
import com.tencent.wxcloudrun.dto.CodeRequest;
import com.tencent.wxcloudrun.dto.OpenIdSession;
import com.tencent.wxcloudrun.dto.UserInfoSecret;
import com.tencent.wxcloudrun.model.UserInfo;
import com.tencent.wxcloudrun.model.UserInfoExample;
import com.tencent.wxcloudrun.service.UserInfoService;
import com.tencent.wxcloudrun.util.AesCbcUtil;
import com.tencent.wxcloudrun.util.JacksonUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class UserInfoController {

    final UserInfoService userInfoService;
    final Logger logger;

    @Value("${wx.appId}")
    public String appId;
    @Value("${wx.secret}")
    public String secret;

    @Autowired
    private CodeRequestConvertor codeRequestConvertor;

    @Autowired
    private UserInfoSecretConvertor userInfoSecretConvertor;


    public UserInfoController(@Autowired UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
        this.logger = LoggerFactory.getLogger(CounterController.class);
    }

    @GetMapping(value = "/api/user/id")
    ApiResponse get() {
        logger.info("/api/user/id get request");
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andIdEqualTo(1L);
        List<UserInfo> counter = userInfoService.getUser(userInfoExample);
        UserInfo userInfo = null;
        if (!CollectionUtils.isEmpty(counter)){
            userInfo = counter.get(0);
        }

        return ApiResponse.ok(userInfo);
    }

    @PostMapping(value = "/api/register")
    ApiResponse addUser(@RequestBody UserInfo userInfo) {
        logger.info("/api/register post 用户注册");
        userInfo.setDeleted(true);
        int i = userInfoService.save(userInfo);
        return ApiResponse.ok(i);
    }

    //进入小程序后先获取用户openId
    @PostMapping(value = "/api/getSession")
    ApiResponse getSession(@RequestBody CodeRequest codeRequest) {
        logger.info("/api/getSession post 发送code");

        //通过wx传入的code获取key_session和openid
        String code = codeRequest.getCode();
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建Http get请求
        HttpGet httpGet = new HttpGet( "https://api.weixin.qq.com/sns/jscode2session?appid=" +appId + "&secret="
                + secret + "&js_code=" + code + "&grant_type=authorization_code");
        //接收返回值
        CloseableHttpResponse response = null;
        String content = "";
        try {
            //请求执行
            response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode()==200){
                content = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println("--------->" + code);
                System.out.println("--------->" + content);
                //"session_key":"lYnV6U9agZWlDNzrFQdS+w==","openid":"oQIC95NvIRaZHmknEm6oCFaQvcLc"
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            System.out.println("getSession-ClientProtocolException");
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        UserInfo userInfo = codeRequestConvertor.toUserInfo(codeRequest);
        OpenIdSession openIdSession = JacksonUtils.readValue(content, OpenIdSession.class);
        userInfo.setOpenId(openIdSession.getOpenid());
        userInfo.setSessionKey(openIdSession.getSession_key());

        //根据openid有则修改，无则新增
        userInfoService.save(userInfo);
        return ApiResponse.ok(openIdSession.getOpenid());
    }

    @PostMapping(value = "/api/saveUserInfo")
    ApiResponse saveUserInfo(@RequestBody UserInfoSecret secret) {
        logger.info("/api/saveUserInfo post 保存完整用户信息");
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andOpenIdEqualTo(secret.getOpenId());
        List<UserInfo> userInfoList = userInfoService.getUser(userInfoExample);
        UserInfo userInfo = userInfoSecretConvertor.toUserInfo(secret);
        if (CollectionUtils.isEmpty(userInfoList)){
            return ApiResponse.error("未获取用户信息");
        }
        UserInfo userInfoDB = userInfoList.get(0);
        String decrypt = AesCbcUtil.decrypt(secret.getEncryptedData(),
                userInfoDB.getSessionKey(), secret.getIv());
        System.out.println(decrypt);
        userInfo.setDeleted(true);
        userInfo.setOpenId(userInfoDB.getOpenId());
        int i = userInfoService.save(userInfo);
        return ApiResponse.ok(i);
    }

    //获取用户授权后的信息
    @PostMapping(value = "/api/getUserInfo")
    ApiResponse getUserInfo(@RequestBody UserInfoSecret request) {
        logger.info("/api/saveUserInfo post 保存完整用户信息");
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andOpenIdEqualTo(request.getOpenId());
        List<UserInfo> userInfoList = userInfoService.getUser(userInfoExample);
        if (CollectionUtils.isEmpty(userInfoList)){
            return ApiResponse.error("获取用户信息失败");
        }
        UserInfo userInfoDB = userInfoList.get(0);
        return ApiResponse.ok(userInfoDB);
    }
}
