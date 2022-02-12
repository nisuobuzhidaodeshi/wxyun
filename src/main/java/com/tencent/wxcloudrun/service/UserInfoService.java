package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.UserInfo;

import java.util.Optional;

public interface UserInfoService {
    Optional<UserInfo> getUser(Long id);

    int addUser(UserInfo userInfo);
}
