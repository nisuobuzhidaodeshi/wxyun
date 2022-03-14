package com.tencent.wxcloudrun.service.impl;

import com.google.common.collect.Lists;
import com.tencent.wxcloudrun.common.bean.BasePaginResp;
import com.tencent.wxcloudrun.common.bean.Page;
import com.tencent.wxcloudrun.dao.UserRecordMapper;
import com.tencent.wxcloudrun.dao.UserRecordMapperExt;
import com.tencent.wxcloudrun.model.UserInfo;
import com.tencent.wxcloudrun.model.UserInfoExample;
import com.tencent.wxcloudrun.model.UserRecord;
import com.tencent.wxcloudrun.model.UserRecordExample;
import com.tencent.wxcloudrun.service.UserInfoService;
import com.tencent.wxcloudrun.service.UserRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author cf
 * @date 2022/3/4 11:19
 */
@Service
public class UserRecordServiceImpl implements UserRecordService {
    final UserRecordMapperExt userRecordMapperExt;

    public UserRecordServiceImpl(@Autowired UserRecordMapperExt userRecordMapperExt) {
        this.userRecordMapperExt = userRecordMapperExt;
    }


    //分页查询
    @Override
    public BasePaginResp<UserRecord> getUser(UserRecord userRecord, Page page) {
        UserRecordExample userRecordExample = new UserRecordExample();
        userRecordExample.setOrderByClause("id desc");
        UserRecordExample.Criteria criteria = userRecordExample.createCriteria();
        criteria.andOpenIdEqualTo(userRecord.getOpenId());
        userRecordExample.setPage(page);

        long count = userRecordMapperExt.countByExample(userRecordExample);
        List<UserRecord> userRecords = null;
        if (count>0){
            userRecords = userRecordMapperExt.selectByExample(userRecordExample);
        }
        return new BasePaginResp(count,count>0? userRecords : Lists.newArrayList());
    }

    @Override
    public int save(UserRecord userRecord) {
        UserRecordExample userRecordExample = new UserRecordExample();
        UserRecordExample.Criteria criteria = userRecordExample.createCriteria();
        int count = 0;
        //没有主键就是新增
        if (Objects.isNull(userRecord.getId())){
            count = userRecordMapperExt.insert(userRecord);
        }else {
            criteria.andIdEqualTo(userRecord.getId());
            count = userRecordMapperExt.updateByExampleSelective(userRecord, userRecordExample);
        }
        return count;
    }

    @Override
    public Long recordDeleted(Long id) {
        UserRecordExample userRecordExample = new UserRecordExample();
        UserRecordExample.Criteria criteria = userRecordExample.createCriteria();
        if (Objects.nonNull(id)){
            criteria.andIdEqualTo(id);
            UserRecord userRecord = new UserRecord();
            userRecord.setUpdateTime(new Date());
            userRecordMapperExt.deleteByPrimaryKey(userRecord, id);
            return id;
        }
        return 0L;
    }
}
