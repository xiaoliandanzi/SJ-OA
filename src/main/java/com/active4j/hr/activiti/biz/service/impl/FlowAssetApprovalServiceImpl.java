package com.active4j.hr.activiti.biz.service.impl;

import com.active4j.hr.activiti.biz.dao.FlowAssetApprovalDao;
import com.active4j.hr.activiti.biz.entity.FlowAssetApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowAssetApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2021/1/10 下午11:41
 */
@Service("flowAssetApprovalService")
@Transactional
public class FlowAssetApprovalServiceImpl extends ServiceImpl<FlowAssetApprovalDao, FlowAssetApprovalEntity>
        implements FlowAssetApprovalService {

    @Autowired
    private WorkflowBaseService workflowBaseService;

    public void saveNewAsset(WorkflowBaseEntity workflowBaseEntity, FlowAssetApprovalEntity flowAssetApprovalEntity) {

        this.save(flowAssetApprovalEntity);

        workflowBaseEntity.setBusinessId(flowAssetApprovalEntity.getId());
        workflowBaseService.save(workflowBaseEntity);
    }

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowAssetApprovalEntity flowAssetApprovalEntity) {

        this.saveOrUpdate(flowAssetApprovalEntity);

        workflowBaseService.saveOrUpdate(workflowBaseEntity);
    }

}
