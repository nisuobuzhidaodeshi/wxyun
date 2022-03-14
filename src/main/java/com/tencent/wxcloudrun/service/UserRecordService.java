package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.bean.BasePaginResp;
import com.tencent.wxcloudrun.common.bean.Page;
import com.tencent.wxcloudrun.model.UserRecord;
import com.tencent.wxcloudrun.model.UserRecordExample;

import java.util.List;

/**
 * @author cf
 * @date 2022/3/4 11:19
 */
public interface UserRecordService {
    BasePaginResp<UserRecord> getUser(UserRecord userRecord, Page page);

    int save(UserRecord userRecord);

    Long recordDeleted (Long id);
}
