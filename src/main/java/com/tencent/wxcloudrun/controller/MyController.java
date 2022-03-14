package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.common.bean.BasePaginResp;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.Interceptor.annotation.LoginUser;
import com.tencent.wxcloudrun.convertor.RecordSelectConvertor;
import com.tencent.wxcloudrun.convertor.UserRecordReqConvertor;
import com.tencent.wxcloudrun.convertor.UserRecordRespConvertor;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.model.UserRecord;
import com.tencent.wxcloudrun.service.RecordService;
import com.tencent.wxcloudrun.service.UserRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cf
 * @date 2022/3/3 15:06
 */
@RestController
@RequestMapping("/my")
public class MyController {
    final Logger logger;
    final UserRecordService userRecordService;
    @Autowired
    private RecordSelectConvertor recordSelectConvertor;
    @Autowired
    private RecordService recordService;
    @Autowired
    private UserRecordRespConvertor userRecordRespConvertor;

    public MyController(@Autowired UserRecordService userRecordService) {
        this.logger = LoggerFactory.getLogger(CounterController.class);
        this.userRecordService = userRecordService;
    }

    @Autowired
    private UserRecordReqConvertor userRecordReqConvertor;

    //微信调用接口获取数据后,将头像、昵称传回后端保存---接口未使用
    @PostMapping(value = "/api/saveRecord")
    ApiResponse saveRecord(@LoginUser String openId, @RequestBody UserRecordReq record) {
        logger.info("/api/saveRecord post 记录信息");
        if (openId == null || openId.length() == 0){
            return ApiResponse.unLogin();
        }

        UserRecord userRecord = userRecordReqConvertor.toUserRecord(record);
        userRecord.setOpenId(openId);
        userRecordService.save(userRecord);
        return ApiResponse.ok();
    }

    //分页查询个人记录
    @PostMapping(value = "/api/selectRecord")
    ApiResponse selectRecord(@LoginUser String openId, @RequestBody RecordSelectReq recordReq) {
        logger.info("/api/saveRecord post 分页查询记录");
        if (openId == null || openId.length() == 0){
            return ApiResponse.unLogin();
        }
        recordReq.setOpenId(openId);
        RecordSelectDTO dto = recordSelectConvertor.toRecordSelectDTO(recordReq);
        BasePaginResp<UserRecord> userRecordBasePaginResp = recordService.selectRecord(dto);
        List<RecordSelectResp> collect = userRecordBasePaginResp.getData().stream()
                .map(r -> userRecordRespConvertor.toRecordSelectResp(r)).collect(Collectors.toList());
        BasePaginResp<RecordSelectResp> basePaginResp = new BasePaginResp(userRecordBasePaginResp.getTotal(),collect);
        return ApiResponse.ok(basePaginResp);
    }

    //删除记录
    @PostMapping(value = "/api/deletedRecord")
    ApiResponse deletedRecord(@LoginUser String openId, @RequestBody RecordDeletedReq recordReq) {
        logger.info("/api/deletedRecord post 删除记录");
        if (openId == null || openId.length() == 0){
            return ApiResponse.unLogin();
        }

        Long aLong = recordService.recordDeleted(Long.parseLong(recordReq.getId()));
        return ApiResponse.ok(aLong);
    }

    //删除记录
    @PostMapping(value = "/api/updateRecord")
    ApiResponse updateRecord(@LoginUser String openId, @RequestBody RecordUpdateReq recordReq) {
        logger.info("/api/updateRecord post 修改记录");
        if (openId == null || openId.length() == 0){
            return ApiResponse.unLogin();
        }
        UserRecord userRecord = new UserRecord();
        userRecord.setId(Long.valueOf(recordReq.getId()));
        userRecord.setRecord(recordReq.getUpdateRecord());
        int save = userRecordService.save(userRecord);

        return ApiResponse.ok(save);
    }
}
