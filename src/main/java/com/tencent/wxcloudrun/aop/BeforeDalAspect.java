package com.tencent.wxcloudrun.aop;

import com.tencent.wxcloudrun.util.DomainUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName:DalAspect <br/>
 * Function: 数据接入层的通用操作，如更新创建时间，修改时间，修改人等. <br/>
 * 注意：可根据自己工程特点，自定制化
 *
 */
@Service
@Aspect
public class BeforeDalAspect {

    @Autowired
    private DomainUtil domainUtil;

    /**
     * 更新方法集合
     */
    private static final List<String> UPDATE_METHOD = Arrays.asList("updateByExampleSelective", "updateByExample",
            "updateByExampleWithBLOBs", "updateByPrimaryKeySelective", "updateByPrimaryKey", "updateByPrimaryKeyWithBLOBs");

    /**
     * 删除方法集合
     */
    private static final List<String> DELETE_METHOD = Arrays.asList("deleteByExample", "deleteByPrimaryKey");

    /**
     * 插入方法集合
     */
    private static final List<String> INSERT_METHOD = Arrays.asList("save", "insert", "insertSelective");

    /**
     * 批量更新方法集合
     */
    private static final List<String> INSERT_BATCH_METHOD = Arrays.asList("insertBatch");

    /**
     * 批量插入方法集合
     */
    private static final List<String> UPDATE_BATCH_METHOD = Arrays.asList("updateBatch");

    @Before("execution(* com.tencent.wxcloudrun..*dao.*.* (..))")
    public void updateRecord(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        if (args == null || args.length <= 0 || args[0] == null) {
            return;
        }

        if (UPDATE_METHOD.contains(methodName)) {
            domainUtil.setCommonValuesForUpdate(args[0]);
        } else if (INSERT_METHOD.contains(methodName)) {
            domainUtil.setCommonValuesForCreate(args[0]);
        } else if (DELETE_METHOD.contains(methodName)) {
            domainUtil.setCommonValuesForDelete(args[0]);
        } else if (INSERT_BATCH_METHOD.contains(methodName)) {
            domainUtil.setCommonValuesForCreate((List)args[0], true);
        } else if (UPDATE_BATCH_METHOD.contains(methodName)) {
            domainUtil.setCommonValuesForUpdate((List) args[0]);
        } else {
            // do nothing
        }
    }
}
