package com.active4j.hr.activiti.biz.service.impl;

import com.active4j.hr.activiti.biz.dao.FlowProcurementApprovalDao;
import com.active4j.hr.activiti.biz.entity.FlowOfficalSealApprovalEntity;
import com.active4j.hr.activiti.biz.entity.FlowProcurementApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowProcurementApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("flowProcurementApprovalServiceImpl")
@Transactional
public class FlowProcurementApprovalServiceImpl  extends ServiceImpl<FlowProcurementApprovalDao, FlowProcurementApprovalEntity>
        implements FlowProcurementApprovalService {
    @Autowired
    private WorkflowBaseService workflowBaseService;

    public void saveNewProcurement(WorkflowBaseEntity workflowBaseEntity, FlowProcurementApprovalEntity flowProcurementApprovalEntity) {

        this.save(flowProcurementApprovalEntity);

        workflowBaseEntity.setBusinessId(flowProcurementApprovalEntity.getId());
        workflowBaseService.save(workflowBaseEntity);
    }

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowProcurementApprovalEntity flowProcurementApprovalEntity) {

        this.saveOrUpdate(flowProcurementApprovalEntity);

        workflowBaseService.saveOrUpdate(workflowBaseEntity);
    }
}
