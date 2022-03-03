package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.Interceptor.annotation.LoginUser;
import com.tencent.wxcloudrun.convertor.CodeRequestConvertor;
import com.tencent.wxcloudrun.convertor.RegisterRequestConvertor;
import com.tencent.wxcloudrun.convertor.UserInfoSecretConvertor;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.model.UserInfo;
import com.tencent.wxcloudrun.model.UserInfoExample;
import com.tencent.wxcloudrun.service.UserInfoService;
import com.tencent.wxcloudrun.service.UserTokenManager;
import com.tencent.wxcloudrun.util.AesCbcUtil;
import com.tencent.wxcloudrun.util.HttpUtil;
import com.tencent.wxcloudrun.util.JacksonUtils;
import com.tencent.wxcloudrun.util.SignatureUtil;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/user")
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
    @Autowired
    private RegisterRequestConvertor registerRequestConvertor;


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

    //微信调用接口获取数据后,将头像、昵称传回后端保存---接口未使用
    @PostMapping(value = "/api/saveUserInfo")
    ApiResponse saveUserInfo(@LoginUser String openId, @RequestBody  UserInfoSecret userInfoSecret, HttpServletRequest request) {
        logger.info("/api/saveUserInfo post 保存完整用户信息");
        if (openId == null || openId.length() == 0){
            ApiResponse.unLogin();
        }
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andOpenIdEqualTo(openId);
        List<UserInfo> userInfoList = userInfoService.getUser(userInfoExample);
        UserInfo userInfo = userInfoSecretConvertor.toUserInfo(userInfoSecret);
        if (CollectionUtils.isEmpty(userInfoList)){
            return ApiResponse.error("未获取用户信息");
        }
        UserInfo userInfoDB = userInfoList.get(0);
        //企业小程序可使用encryptedData解析获取unionID
//        String decrypt = AesCbcUtil.decrypt(request.getEncryptedData(),
//                userInfoDB.getSessionKey(), request.getIv());
//        System.out.println(decrypt);
        userInfo.setDeleted(true);
        userInfo.setOpenId(userInfoDB.getOpenId());
        int i = userInfoService.save(userInfo);
        return ApiResponse.ok(i);
    }

    //登录接口，接收保存头像、昵称信息
    @PostMapping(value = "/api/wxLogin")
    ApiResponse registerUser(@RequestBody RegisterRequest request) {

        //TODO
        logger.info("/api/wxLogin post 保存完整用户信息");

        //通过wx传入的code获取key_session和openid
        String code = request.getCode();
        ApiResponse ok = null;

        try {
            //创建Http get请求
            HttpGet httpGet = new HttpGet( "https://api.weixin.qq.com/sns/jscode2session?appid=" +appId + "&secret="
                    + secret + "&js_code=" + code + "&grant_type=authorization_code");
            OpenIdSession openIdSession = HttpUtil.execute(httpGet, OpenIdSession.class);

            UserInfo userInfo = registerRequestConvertor.toUserInfo(request);
            //根据openID查询数据库是否已有记录
//        UserInfoExample userInfoExample = new UserInfoExample();
//        userInfoExample.createCriteria().andOpenIdEqualTo(openIdSession.getOpenid());
//        List<UserInfo> userInfoList = userInfoService.getUser(userInfoExample);
//
//        if (!CollectionUtils.isEmpty(userInfoList)){
//            return ApiResponse.error("用户已注册");
//        }

            userInfo.setDeleted(true);
            userInfo.setOpenId(openIdSession.getOpenid());
            userInfo.setSessionKey(openIdSession.getSession_key());
            userInfoService.save(userInfo);
            //保存token
            UserTokenManager.generateToken(openIdSession.getOpenid());
            ok = ApiResponse.ok(openIdSession.getOpenid());
        } catch (Exception e) {
            return ApiResponse.error("登录失败");
        }
        ok.setMsg("登录成功");
        return ok;
    }
}
