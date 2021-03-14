package com.active4j.hr.activiti.biz.service.impl;

import com.active4j.hr.activiti.biz.dao.FlowItemBorrowApprovalDao;
import com.active4j.hr.activiti.biz.dao.FlowOfficalSealApprovalDao;
import com.active4j.hr.activiti.biz.entity.FlowItemBorrowApprovalEntity;
import com.active4j.hr.activiti.biz.entity.FlowOfficalSealApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowItemBorrowApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("flowItemBorrowApprovalService")
@Transactional
public class FlowItemBorrowApprovalServiceImpl extends ServiceImpl<FlowItemBorrowApprovalDao, FlowItemBorrowApprovalEntity>
        implements FlowItemBorrowApprovalService {
    @Autowired
    private WorkflowBaseService workflowBaseService;

    public void saveNewItemBorrow(WorkflowBaseEntity workflowBaseEntity, FlowItemBorrowApprovalEntity flowItemBorrowApprovalEntity) {

        this.save(flowItemBorrowApprovalEntity);

        workflowBaseEntity.setBusinessId(flowItemBorrowApprovalEntity.getId());
        workflowBaseService.save(workflowBaseEntity);
    }

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowItemBorrowApprovalEntity flowItemBorrowApprovalEntity) {

        this.saveOrUpdate(flowItemBorrowApprovalEntity);

        workflowBaseService.saveOrUpdate(workflowBaseEntity);
    }
}
