package com.tencent.wxcloudrun.convertor;

import com.tencent.wxcloudrun.dto.RecordSelectDTO;
import com.tencent.wxcloudrun.dto.RecordSelectReq;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author cf
 * @date 2022/3/4 16:39
 */
@Component
public class RecordSelectConvertor {
    public RecordSelectDTO toRecordSelectDTO(RecordSelectReq req){
        RecordSelectDTO dto = null;
        if (Objects.nonNull(req)){
            dto = new RecordSelectDTO();
            dto.setOpenId(req.getOpenId());
            dto.setPage(req.toPage());
        }
        return dto;
    }
}
