package com.active4j.hr.activiti.biz.service.impl;

import com.active4j.hr.activiti.biz.dao.FlowAssetAddMapper;
import com.active4j.hr.activiti.biz.entity.FlowAssetAddEntity;

import com.active4j.hr.activiti.biz.service.FlowAssetAddService;

import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author weizihao
 * @since 2021-03-19
 */
@Service
public class FlowAssetAddServiceImpl extends ServiceImpl<FlowAssetAddMapper, FlowAssetAddEntity> implements FlowAssetAddService {

    @Autowired
    private WorkflowBaseService workflowBaseService;

    @Override
    public void saveNewAsset(WorkflowBaseEntity workflowBaseEntity, FlowAssetAddEntity flowAssetAddEntity) {
        this.save(flowAssetAddEntity);

        workflowBaseEntity.setBusinessId(flowAssetAddEntity.getId());
        workflowBaseService.save(workflowBaseEntity);
    }

    @Override
    public void saveUpdate(WorkflowBaseEntity base, FlowAssetAddEntity biz) {
        this.saveOrUpdate(biz);

        workflowBaseService.saveOrUpdate(base);
    }
}
