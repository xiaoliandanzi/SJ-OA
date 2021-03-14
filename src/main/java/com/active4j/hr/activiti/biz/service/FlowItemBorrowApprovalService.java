package com.active4j.hr.activiti.biz.service;

import com.active4j.hr.activiti.biz.entity.FlowItemBorrowApprovalEntity;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FlowItemBorrowApprovalService  extends IService<FlowItemBorrowApprovalEntity> {
    public void saveNewItemBorrow(WorkflowBaseEntity workflowBaseEntity, FlowItemBorrowApprovalEntity flowItemBorrowApprovalEntity);

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowItemBorrowApprovalEntity flowItemBorrowApprovalEntity);
}
