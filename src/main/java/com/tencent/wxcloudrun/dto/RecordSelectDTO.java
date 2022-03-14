package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.common.bean.Page;
import lombok.Data;

/**
 * @author cf
 * @date 2022/3/4 16:38
 */
@Data
public class RecordSelectDTO {
    private String openId;

    private Page page;
}
