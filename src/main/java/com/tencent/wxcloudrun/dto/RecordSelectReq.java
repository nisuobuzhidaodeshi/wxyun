package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.common.bean.BasePaginReq;
import lombok.Data;

/**
 * @author cf
 * @date 2022/3/4 16:28
 */
@Data
public class RecordSelectReq extends BasePaginReq {
    private String openId;
}
