package com.tencent.wxcloudrun.common.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author cf
 * @date 2022/3/4 16:22
 */
@Getter
@Setter
@ToString(callSuper = true)
public class BasePaginReq implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final long MAX_LIMIT = 20L;
    /**
     * 页码
     */
    private long page = 1L;

    /**
     * 分页大小，默认20
     */
    private long pageSize = 20L;

    /**
     * 转换分页参数
     *
     * @return
     */
    public Page toPage() {
        return new Page((this.getPage()-1)*this.getPageSize(), this.getPageSize());
    }
}
