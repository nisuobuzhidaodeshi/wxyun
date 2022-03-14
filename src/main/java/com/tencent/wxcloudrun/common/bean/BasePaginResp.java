package com.tencent.wxcloudrun.common.bean;

import lombok.Data;

import java.util.List;

/**
 * @author cf
 * @date 2022/3/7 16:28
 */
@Data
public class BasePaginResp<T> {

    public BasePaginResp(Long total, List<T> data) {
        this.total = total;
        this.data = data;
    }

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页的结果集
     */
    private List<T> data;
}
