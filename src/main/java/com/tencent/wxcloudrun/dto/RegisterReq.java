package com.tencent.wxcloudrun.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cf
 * @date 2022/2/17 16:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterReq extends CodeRequest{

    private String rawData;

    private String signature;
}
