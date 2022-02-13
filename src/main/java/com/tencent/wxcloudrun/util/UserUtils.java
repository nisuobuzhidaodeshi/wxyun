package com.tencent.wxcloudrun.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : cf
 * @date : 2022/2/13 22:40
 */
@Component
public class UserUtils {
    /**
     * 默认用户ID
     */
    public static final Long DEFAULT_USER_ID = 0L;
    /**
     * 默认用户名称
     */
    public static final String DEFAULT_USER_NAME = "系统";

    /**
     * 默认值0
     *
     * @return
     */
    public Long getIdWithDefault() {
        Long id = DEFAULT_USER_ID;
        return id;
    }

    /**
     * 默认值system
     *
     * @return
     */
    public String getNameWithDefault() {
        String userName = DEFAULT_USER_NAME;
        return userName;
    }


}
