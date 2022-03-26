package com.tencent.wxcloudrun.convertor;

import com.tencent.wxcloudrun.dto.RegisterReq;
import com.tencent.wxcloudrun.model.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author cf
 * @date 2022/2/18 12:46
 */
@Component
public class RegisterReqConvertor {
    public UserInfo toUserInfo(RegisterReq request){
        UserInfo userInfo = null;
        if (Objects.nonNull(request)){
            userInfo = new UserInfo();
            userInfo.setAvatarUrl(request.getAvatarUrl());
            userInfo.setNickName(request.getNickName());
        }
        return userInfo;
    }
}
