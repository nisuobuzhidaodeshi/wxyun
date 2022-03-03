package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserToken {
    private String openId;
    private String token;
    private Date expireTime;
    private Date createTime;
}
