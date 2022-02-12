package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.UserInfo;
import com.tencent.wxcloudrun.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        logger.info("/api/register post request");
//        UserInfo userInfo = new UserInfo();
//        userInfo.setCreateTime(new Date());
//        userInfo.setUpdateTime(new Date());
//        userInfo.setName("user1");
//        userInfo.setTelephone("15967691873");
//        userInfo.setPassword("132564awd");
        int i = userInfoService.addUser(userInfo);

        return ApiResponse.ok(i);
    }

    @PostMapping(value = "/api/getSession")
    ApiResponse getSession(@RequestParam("code") String code) {
        logger.info("/api/getSession post request");

        return ApiResponse.ok(code);
    }
}
