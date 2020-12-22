package com.active4j.hr.activiti.biz.service;

import com.active4j.hr.activiti.biz.entity.FlowMessageApprovalEntity;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/22 下午10:22
 */
public interface FlowMessageApprovalService extends IService<FlowMessageApprovalEntity> {

    public void saveNewPaper(WorkflowBaseEntity workflowBaseEntity, FlowMessageApprovalEntity flowMessageApprovalEntity);

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowMessageApprovalEntity flowMessageApprovalEntity);

}
