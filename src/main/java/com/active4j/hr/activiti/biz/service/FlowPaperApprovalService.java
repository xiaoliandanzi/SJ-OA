package com.active4j.hr.activiti.biz.service;

import com.active4j.hr.activiti.biz.entity.FlowPaperApprovalEntity;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/9 下午10:13
 */
public interface FlowPaperApprovalService extends IService<FlowPaperApprovalEntity> {

    public void saveNewPaper(WorkflowBaseEntity workflowBaseEntity, FlowPaperApprovalEntity flowPaperApprovalEntity);

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowPaperApprovalEntity flowPaperApprovalEntity);

}
