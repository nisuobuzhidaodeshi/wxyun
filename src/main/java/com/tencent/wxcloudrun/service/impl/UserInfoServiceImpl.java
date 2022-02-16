package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.UserInfoMapper;
import com.tencent.wxcloudrun.model.UserInfo;
import com.tencent.wxcloudrun.model.UserInfoExample;
import com.tencent.wxcloudrun.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    final UserInfoMapper userInfoMapper;

    public UserInfoServiceImpl(@Autowired UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public List<UserInfo> getUser(UserInfoExample example) {
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        return userInfos;
    }

    @Override
    public int save(UserInfo userInfo) {
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
        int count = 0;
        if (Objects.nonNull(userInfo.getOpenId())){
            criteria.andOpenIdEqualTo(userInfo.getOpenId());
            List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);
            if (Objects.isNull(userInfos) || userInfos.size()==0){
                userInfoMapper.insert(userInfo);
            }else if (userInfos.size() == 1){
                userInfoMapper.updateByExampleSelective(userInfo,userInfoExample);
            }else {
                RuntimeException e = new RuntimeException("用户数据异常");
                throw e;
            }
        }else {
            RuntimeException e = new RuntimeException("用户数据保存异常");
            throw e;
        }
        return count;
    }
}
