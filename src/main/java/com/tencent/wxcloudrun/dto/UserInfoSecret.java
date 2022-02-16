package com.tencent.wxcloudrun.dto;

import lombok.Data;

/**
 * @author cf
 * @date 2022/2/15 12:59
 */
@Data
public class UserInfoSecret extends CodeRequest{
    private String openId;
}
