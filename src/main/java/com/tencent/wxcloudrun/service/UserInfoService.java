package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.UserInfo;
import com.tencent.wxcloudrun.model.UserInfoExample;

import java.util.List;
import java.util.Optional;

public interface UserInfoService {
    List<UserInfo> getUser(UserInfoExample example);

    int save(UserInfo userInfo);
}
