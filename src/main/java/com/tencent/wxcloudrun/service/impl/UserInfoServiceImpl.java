package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.UserInfoMapper;
import com.tencent.wxcloudrun.model.UserInfo;
import com.tencent.wxcloudrun.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    final UserInfoMapper userInfoMapper;

    public UserInfoServiceImpl(@Autowired UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public Optional<UserInfo> getUser(Long id) {
        return Optional.ofNullable(userInfoMapper.selectByPrimaryKey(id));
    }

    @Override
    public int addUser(UserInfo userInfo) {
        int insert = userInfoMapper.insert(userInfo);
        return insert;
    }
}
