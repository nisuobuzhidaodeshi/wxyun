package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class OpenIdSession {
    private String session_key;

    private String openid;
}
