package com.active4j.hr.activiti.biz.service;

import com.active4j.hr.activiti.biz.entity.FlowOfficalSealApprovalEntity;
import com.active4j.hr.activiti.biz.entity.FlowProcurementApprovalEntity;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2021/01/18/23:56
 * @Description:
 */
public interface FlowProcurementApprovalService  extends IService<FlowProcurementApprovalEntity> {
    public void saveNewProcurement(WorkflowBaseEntity workflowBaseEntity, FlowProcurementApprovalEntity flowProcurementApprovalEntity);

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowProcurementApprovalEntity flowProcurementApprovalEntity);
}
