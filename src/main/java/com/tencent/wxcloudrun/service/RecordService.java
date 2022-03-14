package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.bean.BasePaginResp;
import com.tencent.wxcloudrun.dto.RecordSelectDTO;
import com.tencent.wxcloudrun.model.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cf
 * @date 2022/3/7 16:21
 */
@Service
public class RecordService {
    @Autowired
    private UserRecordService userRecordService;
    public BasePaginResp<UserRecord> selectRecord(RecordSelectDTO dto){
        UserRecord userRecord = new UserRecord();
        userRecord.setOpenId(dto.getOpenId());
        BasePaginResp<UserRecord> user = userRecordService.getUser(userRecord, dto.getPage());
        return user;
    }

    public Long recordDeleted(Long id){
        Long aLong = userRecordService.recordDeleted(id);
        return aLong;
    }

}
