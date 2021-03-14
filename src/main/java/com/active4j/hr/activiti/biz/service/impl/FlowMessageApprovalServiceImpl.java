package com.active4j.hr.activiti.biz.service.impl;

import com.active4j.hr.activiti.biz.dao.FlowMessageApprovalDao;
import com.active4j.hr.activiti.biz.entity.FlowMessageApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowMessageApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/22 下午10:26
 */
@Service("flowMessageApprovalService")
@Transactional
public class FlowMessageApprovalServiceImpl extends ServiceImpl<FlowMessageApprovalDao, FlowMessageApprovalEntity>
        implements FlowMessageApprovalService {

    @Autowired
    private WorkflowBaseService workflowBaseService;

    public void saveNewMessage(WorkflowBaseEntity workflowBaseEntity, FlowMessageApprovalEntity flowMessageApprovalEntity) {

        this.save(flowMessageApprovalEntity);

        workflowBaseEntity.setBusinessId(flowMessageApprovalEntity.getId());
        workflowBaseService.save(workflowBaseEntity);
    }

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowMessageApprovalEntity flowMessageApprovalEntity) {

        this.saveOrUpdate(flowMessageApprovalEntity);

        workflowBaseService.saveOrUpdate(workflowBaseEntity);
    }
}
