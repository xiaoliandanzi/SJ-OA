package com.active4j.hr.activiti.biz.service.impl;

import com.active4j.hr.activiti.biz.dao.FlowOfficalSealApprovalDao;
import com.active4j.hr.activiti.biz.entity.FlowOfficalSealApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowOfficalSealApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/12/14/23:58
 * @Description:
 */
@Service("flowOfficalSealApprovalService")
@Transactional
public class FlowOfficalSealApprovalServiceImpl extends ServiceImpl<FlowOfficalSealApprovalDao, FlowOfficalSealApprovalEntity>
        implements FlowOfficalSealApprovalService {

    @Autowired
    private WorkflowBaseService workflowBaseService;

    public void saveNewSeal(WorkflowBaseEntity workflowBaseEntity, FlowOfficalSealApprovalEntity flowOfficalSealApprovalEntity) {

        this.save(flowOfficalSealApprovalEntity);

        workflowBaseEntity.setBusinessId(flowOfficalSealApprovalEntity.getId());
        workflowBaseService.save(workflowBaseEntity);
    }

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowOfficalSealApprovalEntity flowOfficalSealApprovalEntity) {

        this.saveOrUpdate(flowOfficalSealApprovalEntity);

        workflowBaseService.saveOrUpdate(workflowBaseEntity);
    }
}
