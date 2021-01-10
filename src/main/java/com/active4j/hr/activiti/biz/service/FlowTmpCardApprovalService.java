package com.active4j.hr.activiti.biz.service;

import com.active4j.hr.activiti.biz.entity.FlowTmpCardApprovalEntity;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FlowTmpCardApprovalService  extends IService<FlowTmpCardApprovalEntity> {
    public void saveNewTmpCard(WorkflowBaseEntity workflowBaseEntity, FlowTmpCardApprovalEntity flowTmpCardApprovalEntity);

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowTmpCardApprovalEntity flowTmpCardApprovalEntity);
}
