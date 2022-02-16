package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class CodeRequest {

    private String code;

    private String nickName;

    private String avatarUrl;

    private String encryptedData;

    private String iv;
}
