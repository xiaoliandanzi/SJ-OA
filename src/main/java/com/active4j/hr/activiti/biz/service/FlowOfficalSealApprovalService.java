package com.active4j.hr.activiti.biz.service;

import com.active4j.hr.activiti.biz.entity.FlowOfficalSealApprovalEntity;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/12/14/23:56
 * @Description:
 */
public interface FlowOfficalSealApprovalService  extends IService<FlowOfficalSealApprovalEntity> {
    public void saveNewSeal(WorkflowBaseEntity workflowBaseEntity, FlowOfficalSealApprovalEntity flowOfficalSealApprovalEntity);

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowOfficalSealApprovalEntity flowOfficalSealApprovalEntity);
}
