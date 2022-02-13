package com.tencent.wxcloudrun.util;


import com.tencent.wxcloudrun.mybatis.CloumnEnum;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * @author ajia.zjj
 */
@Component
public class DomainUtil {
    @Autowired
    private UserUtils userUtils;

    /**
     * 默认用户ID
     */
    public static final Long DEFAULT_USER_ID = 0L;
    /**
     * 默认用户名称
     */
    public static final String DEFAULT_USER_NAME = "系统";

    public void setCommonValuesForCreate(Object object) {
        Date date = new Date();
        Long currentUserId = userUtils.getIdWithDefault();
        String currentUserName = userUtils.getNameWithDefault();
        setCommonValuesForCreate(object, date, currentUserId, currentUserName);
    }

    public void setCommonValuesForCreate(Object object, Date date, Long currentUserId, String
            currentUserName) {
        // 尝试设置创建时间
        try {
                BeanUtils.setProperty(object, CloumnEnum.CREATE_AT.getClazz(), date);
        } catch (Exception e) {
        }
        // 尝试设置修改时间
        try {
                BeanUtils.setProperty(object, CloumnEnum.UPDATE_AT.getClazz(), date);
        } catch (Exception e) {
        }
        // 当创建人ID或者创建人名称未设置时
        try {
            if (BeanUtils.getProperty(object, CloumnEnum.CREATE_BY.getClazz()) == null ||
                    BeanUtils.getProperty(object, CloumnEnum.CREATE_BY_NAME.getClazz()) == null) {
                // 尝试设置创建人ID
                BeanUtils.setProperty(object, CloumnEnum.CREATE_BY.getClazz(), currentUserId);
                // 尝试设置创建人名称
                BeanUtils.setProperty(object, CloumnEnum.CREATE_BY_NAME.getClazz(), currentUserName);

            }
        } catch (Exception e) {
        }
        // 尝试设置修改人ID
        try {
            if (BeanUtils.getProperty(object, CloumnEnum.UPDATE_BY.getClazz())==null) {
                BeanUtils.setProperty(object, CloumnEnum.UPDATE_BY.getClazz(), currentUserId);
            }
        } catch (Exception e) {
        }
        // 尝试设置修改人名称
        try {
            if (BeanUtils.getProperty(object, CloumnEnum.UPDATE_BY_NAME.getClazz())==null) {
                BeanUtils.setProperty(object, CloumnEnum.UPDATE_BY_NAME.getClazz(), currentUserName);
            }
        } catch (Exception e) {
        }
        // 尝试设置删除标识
        try {
            BeanUtils.setProperty(object, CloumnEnum.IS_DELETED.getClazz(), 0);
        } catch (Exception e) {
        }
    }

    /**
     * 根据ID类型，设置ID值，目前批量新增场景需要此操作
     * 
     * @param object
     */
    public void setIdValuesForCreate(Object object) {
        try {
            Field idFiled = object.getClass().getDeclaredField(CloumnEnum.ID.getClazz());
            Id id = idFiled.getAnnotation(Id.class);
            if (id != null && id.type() == Id.Type.ID_WORKER) {
                BeanUtils.setProperty(object, CloumnEnum.ID.getClazz(), IdWorker.nextId());
            }
        } catch (Exception e) {
            throw new RuntimeException("ID设置错误", e);
        }
    }

    public void setCommonValuesForCreate(List<Object> list) {
        setCommonValuesForCreate(list, false);
    }

    public void setCommonValuesForCreate(List<Object> list, boolean setId) {
        if (CollectionUtils.isNotEmpty(list)) {
            Date date = new Date();
            Long currentUserId = userUtils.getIdWithDefault();
            String currentUserName = userUtils.getNameWithDefault();
            list.stream().forEach(tmp -> {
                if (setId) {
                    setIdValuesForCreate(tmp);
                }
                setCommonValuesForCreate(tmp, date, currentUserId, currentUserName);
            });
        }
    }

    public void setCommonValuesForUpdate(Object object) {
        Date date = new Date();
        Long currentUserId = userUtils.getIdWithDefault();
        String currentUserName = userUtils.getNameWithDefault();
        setCommonValuesForUpdate(object, date, currentUserId, currentUserName);
    }

    public void setCommonValuesForUpdate(Object object, Date date, Long currentUserId, String
            currentUserName) {
        // 尝试设置创建时间
        try {
                BeanUtils.setProperty(object, CloumnEnum.UPDATE_AT.getClazz(), date);
        } catch (Exception e) {
        }
        // 尝试设置修改人id
        try {
            if (BeanUtils.getProperty(object, CloumnEnum.UPDATE_BY.getClazz())==null) {
                BeanUtils.setProperty(object, CloumnEnum.UPDATE_BY.getClazz(), currentUserId);
            }
        } catch (Exception e) {
        }
        // 尝试设置修改人名称
        try {
            if (BeanUtils.getProperty(object, CloumnEnum.UPDATE_BY_NAME.getClazz())==null) {
                BeanUtils.setProperty(object, CloumnEnum.UPDATE_BY_NAME.getClazz(), currentUserName);
            }
        } catch (Exception e) {
        }
        // 尝试设置删除标识
        try {
            BeanUtils.setProperty(object, CloumnEnum.IS_DELETED.getClazz(), 0);
        } catch (Exception e) {
        }
    }

    public void setCommonValuesForUpdate(List<Object> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            Date date = new Date();
            Long currentUserId = userUtils.getIdWithDefault();
            String currentUserName = userUtils.getNameWithDefault();
            list.stream().forEach(tmp -> {
                setCommonValuesForUpdate(tmp, date, currentUserId, currentUserName);
            });
        }
    }

    public void setCommonValuesForDelete(Object object) {
        Date date = new Date();
        Long currentUserId = userUtils.getIdWithDefault();
        String currentUserName = userUtils.getNameWithDefault();
        setCommonValuesForDelete(object, date, currentUserId, currentUserName);
    }

    public void setCommonValuesForDelete(Object object, Date date, Long currentUserId, String
            currentUserName) {
        // 尝试设置创建时间
        try {
                BeanUtils.setProperty(object, CloumnEnum.UPDATE_AT.getClazz(), date);
        } catch (Exception e) {
        }
        // 尝试设置修改时间
        try {
            if (BeanUtils.getProperty(object, CloumnEnum.UPDATE_BY.getClazz())==null) {
                BeanUtils.setProperty(object, CloumnEnum.UPDATE_BY.getClazz(), currentUserId);
            }
        } catch (Exception e) {
        }
        // 尝试设置修改人名称
        try {
            if (BeanUtils.getProperty(object, CloumnEnum.UPDATE_BY_NAME.getClazz())==null) {
                BeanUtils.setProperty(object, CloumnEnum.UPDATE_BY_NAME.getClazz(), currentUserName);
            }
        } catch (Exception e) {
        }
        // 尝试设置删除标识
        try {
            BeanUtils.setProperty(object, CloumnEnum.IS_DELETED.getClazz(), 1);
        } catch (Exception e) {
        }
    }
}
