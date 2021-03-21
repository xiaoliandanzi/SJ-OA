package com.active4j.hr.activiti.biz.service;

import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.info.controller.PublicFileController;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.List;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/8 下午11:24
 */
public interface FlowCarApprovalService extends IService<FlowCarApprovalEntity> {
    public void saveNewCar(WorkflowBaseEntity workflowBaseEntity, FlowCarApprovalEntity flowCarApprovalEntity);

    public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowCarApprovalEntity flowCarApprovalEntity);

    public List getplate();

    public List getdriver();

}
