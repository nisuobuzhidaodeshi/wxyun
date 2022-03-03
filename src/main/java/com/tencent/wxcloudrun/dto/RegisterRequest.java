package com.tencent.wxcloudrun.dto;

import lombok.Data;

/**
 * @author cf
 * @date 2022/2/17 16:36
 */
@Data
public class RegisterRequest extends CodeRequest{

    private String rawData;

    private String signature;
}
