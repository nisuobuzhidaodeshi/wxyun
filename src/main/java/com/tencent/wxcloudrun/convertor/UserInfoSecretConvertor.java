package com.tencent.wxcloudrun.convertor;

import com.tencent.wxcloudrun.dto.CodeRequest;
import com.tencent.wxcloudrun.dto.UserInfoSecret;
import com.tencent.wxcloudrun.model.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author cf
 * @date 2022/2/15 16:10
 */
@Component
public class UserInfoSecretConvertor {
    public UserInfo toUserInfo(UserInfoSecret secret){
        UserInfo userInfo = null;
        if (Objects.nonNull(secret)){
            userInfo = new UserInfo();
            userInfo.setAvatarUrl(secret.getAvatarUrl());
            userInfo.setNickName(secret.getNickName());
            userInfo.setDeleted(false);
        }
        return userInfo;
    }
}
