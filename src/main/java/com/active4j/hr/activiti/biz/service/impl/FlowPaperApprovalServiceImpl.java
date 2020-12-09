package com.active4j.hr.activiti.biz.service.impl;

import com.active4j.hr.activiti.biz.dao.FlowPaperApprovalDao;
import com.active4j.hr.activiti.biz.entity.FlowPaperApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowPaperApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/9 下午10:15
 */
@Service("flowPaperApprovalService")
@Transactional
public class FlowPaperApprovalServiceImpl extends ServiceImpl<FlowPaperApprovalDao, FlowPaperApprovalEntity>
        implements FlowPaperApprovalService {
    @Autowired
    private WorkflowBaseService workflowBaseService;

    public void saveNewCar(WorkflowBaseEntity workflowBaseEntity, FlowPaperApprovalEntity flowPaperApprovalEntity) {

        this.save(flowPaperApprovalEntity);

        workflowBaseEntity.setBusinessId(flowPaperApprovalEntity.getId());
        workflowBaseService.save(workflowBaseEntity);
    }

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowPaperApprovalEntity flowPaperApprovalEntity) {

        this.saveOrUpdate(flowPaperApprovalEntity);

        workflowBaseService.saveOrUpdate(workflowBaseEntity);
    }

}
