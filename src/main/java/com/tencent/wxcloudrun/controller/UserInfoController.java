package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CodeRequest;
import com.tencent.wxcloudrun.model.UserInfo;
import com.tencent.wxcloudrun.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
public class UserInfoController {

    final UserInfoService userInfoService;
    final Logger logger;

    public UserInfoController(@Autowired UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
        this.logger = LoggerFactory.getLogger(CounterController.class);
    }

    @GetMapping(value = "/api/user/id")
    ApiResponse get() {
        logger.info("/api/user/id get request");
        Optional<UserInfo> counter = userInfoService.getUser(1L);
        UserInfo userInfo = null;
        if (counter.isPresent()) {
            userInfo = counter.get();
        }

        return ApiResponse.ok(userInfo);
    }

    @PostMapping(value = "/api/register")
    ApiResponse addUser(@RequestBody UserInfo userInfo) {
        logger.info("/api/register post 用户注册");
        Date date = new Date();
        userInfo.setCreateTime(date);
        userInfo.setUpdateTime(date);
        userInfo.setTelephone("15967691873");
        userInfo.setPassword("132564awd");
        int i = userInfoService.addUser(userInfo);

        return ApiResponse.ok(i);
    }

    @PostMapping(value = "/api/getSession")
    ApiResponse getSession(@RequestBody CodeRequest code) {
        logger.info("/api/getSession post 发送code");

        return ApiResponse.ok(code.getCode());
    }
}
