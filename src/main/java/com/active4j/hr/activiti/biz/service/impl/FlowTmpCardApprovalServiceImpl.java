package com.active4j.hr.activiti.biz.service.impl;

import com.active4j.hr.activiti.biz.dao.FlowItemBorrowApprovalDao;
import com.active4j.hr.activiti.biz.dao.FlowTmpCardApprovalDao;
import com.active4j.hr.activiti.biz.entity.FlowItemBorrowApprovalEntity;
import com.active4j.hr.activiti.biz.entity.FlowOfficalSealApprovalEntity;
import com.active4j.hr.activiti.biz.entity.FlowTmpCardApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowItemBorrowApprovalService;
import com.active4j.hr.activiti.biz.service.FlowTmpCardApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("flowTmpCardApprovalService")
@Transactional
public class FlowTmpCardApprovalServiceImpl extends ServiceImpl<FlowTmpCardApprovalDao, FlowTmpCardApprovalEntity>
        implements FlowTmpCardApprovalService {

    @Autowired
    private WorkflowBaseService workflowBaseService;

    public void saveNewTmpCard(WorkflowBaseEntity workflowBaseEntity, FlowTmpCardApprovalEntity flowTmpCardApprovalEntity) {

        this.save(flowTmpCardApprovalEntity);

        workflowBaseEntity.setBusinessId(flowTmpCardApprovalEntity.getId());
        workflowBaseService.save(workflowBaseEntity);
    }

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowTmpCardApprovalEntity flowTmpCardApprovalEntity) {

        this.saveOrUpdate(flowTmpCardApprovalEntity);

        workflowBaseService.saveOrUpdate(workflowBaseEntity);
    }
}
