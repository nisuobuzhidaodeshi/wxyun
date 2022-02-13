package com.tencent.wxcloudrun.common.bean;

import java.util.List;

/**
 * PageData
 *
 * @author ajia.zjj
 * @date 2017/03/10
 */
public class PageData<T> {

    /**
     * 数据总量
     */
    private long total;

    /**
     * 当页数据
     */
    private List<T> items;

    public PageData() {
    }

    public PageData(long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
