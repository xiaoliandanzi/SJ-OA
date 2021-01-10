package com.active4j.hr.activiti.biz.service;

import com.active4j.hr.activiti.biz.entity.FlowAssetApprovalEntity;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2021/1/10 下午11:40
 */
public interface FlowAssetApprovalService extends IService<FlowAssetApprovalEntity> {

    public void saveNewAsset(WorkflowBaseEntity workflowBaseEntity, FlowAssetApprovalEntity flowAssetApprovalEntity);

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowAssetApprovalEntity flowAssetApprovalEntity);


}
