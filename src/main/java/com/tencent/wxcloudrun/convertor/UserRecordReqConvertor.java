package com.tencent.wxcloudrun.convertor;

import com.tencent.wxcloudrun.dto.UserRecordReq;
import com.tencent.wxcloudrun.model.UserRecord;
import com.tencent.wxcloudrun.util.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * @author cf
 * @date 2022/3/4 11:11
 */
@Component
public class UserRecordReqConvertor {
    public UserRecord toUserRecord(UserRecordReq recordRequest){
        UserRecord userRecord = null;
        if (Objects.nonNull(recordRequest)){
            userRecord = new UserRecord();
            userRecord.setRecord(recordRequest.getRecord());
            String executeTime = recordRequest.getDate()+" "+recordRequest.getTime()+":00";
            Date date = DateUtils.valueOf(executeTime, DateUtils.DATEFORMAT_STR_001);
            userRecord.setExecuteTime(date);
        }
        return userRecord;
    }
}
