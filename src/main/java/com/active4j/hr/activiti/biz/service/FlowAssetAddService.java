package com.active4j.hr.activiti.biz.service;

import com.active4j.hr.activiti.biz.entity.FlowAssetAddEntity;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author weizihao
 * @since 2021-03-19
 */
public interface FlowAssetAddService extends IService<FlowAssetAddEntity> {

    void saveNewAsset(WorkflowBaseEntity workflowBaseEntity, FlowAssetAddEntity flowAssetAddEntity);

    void saveUpdate(WorkflowBaseEntity base, FlowAssetAddEntity biz);
}
