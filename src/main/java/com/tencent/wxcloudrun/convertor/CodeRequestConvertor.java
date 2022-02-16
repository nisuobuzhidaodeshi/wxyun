package com.tencent.wxcloudrun.convertor;

import com.tencent.wxcloudrun.dto.CodeRequest;
import com.tencent.wxcloudrun.model.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CodeRequestConvertor {

    public UserInfo toUserInfo(CodeRequest request){
        UserInfo userInfo = null;
        if (Objects.nonNull(request)){
            userInfo = new UserInfo();
            userInfo.setAvatarUrl(request.getAvatarUrl());
            userInfo.setNickName(request.getNickName());
            userInfo.setDeleted(false);
        }
        return userInfo;
    }
}
