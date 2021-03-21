package com.active4j.hr.activiti.biz.service.impl;

import com.active4j.hr.activiti.biz.dao.FlowCarApprovalDao;
import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowCarApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/8 下午11:25
 */
@Service("flowCarApprovalService")
@Transactional
public class FlowCarApprovalServiceImpl extends ServiceImpl<FlowCarApprovalDao, FlowCarApprovalEntity>
        implements FlowCarApprovalService {

    @Autowired
    private WorkflowBaseService workflowBaseService;
    @Resource
    private FlowCarApprovalDao flowCarApprovalDao;

    public void saveNewCar(WorkflowBaseEntity workflowBaseEntity, FlowCarApprovalEntity flowCarApprovalEntity) {

        this.save(flowCarApprovalEntity);

        workflowBaseEntity.setBusinessId(flowCarApprovalEntity.getId());
        workflowBaseService.save(workflowBaseEntity);
    }

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowCarApprovalEntity flowCarApprovalEntity) {

        this.saveOrUpdate(flowCarApprovalEntity);

        workflowBaseService.saveOrUpdate(workflowBaseEntity);
    }

    public List getplate(){

        return this.flowCarApprovalDao.getplate();
    }
    public List getdriver(){
        return this.flowCarApprovalDao.getdriver();
    }


}
