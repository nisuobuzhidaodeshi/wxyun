package com.tencent.wxcloudrun.convertor;

import com.tencent.wxcloudrun.dto.RecordSelectResp;
import com.tencent.wxcloudrun.model.UserRecord;
import com.tencent.wxcloudrun.util.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author cf
 * @date 2022/3/9 15:10
 */
@Component
public class UserRecordRespConvertor {
    public RecordSelectResp toRecordSelectResp(UserRecord record){
        RecordSelectResp recordSelectResp = null;
        if (Objects.nonNull(record)){
            recordSelectResp = new RecordSelectResp();
            recordSelectResp.setId(record.getId().toString());
            recordSelectResp.setOpenId(record.getOpenId());
            recordSelectResp.setRecord(record.getRecord());
            recordSelectResp.setUpdateTime(DateUtils.formatDateTime(record.getUpdateTime()));
        }
        return recordSelectResp;
    }
}
