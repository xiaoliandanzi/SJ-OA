package com.active4j.hr.activiti.listener;

import com.active4j.hr.activiti.biz.entity.FlowAssetApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowAssetApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.active4j.hr.activiti.util.WorkflowTaskUtil;
import com.active4j.hr.core.beanutil.ApplicationContextUtil;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.service.SysUserService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2021/1/24 下午1:12
 */
public class TaskAssigneeAssetListener implements TaskListener {
    private static final long serialVersionUID = -2177362875314557955L;

    @Override
    public void notify(DelegateTask delegateTask){
        TaskService taskService = ApplicationContextUtil.getContext().getBean(TaskService.class);
        RuntimeService runtimeService = ApplicationContextUtil.getContext().getBean(RuntimeService.class);
        WorkflowBaseService workflowBaseService = ApplicationContextUtil.getContext().getBean(WorkflowBaseService.class);
        FlowAssetApprovalService flowAssetApprovalService = ApplicationContextUtil.getContext().getBean(FlowAssetApprovalService.class);
        SysUserService sysUserService = ApplicationContextUtil.getContext().getBean(SysUserService.class);

        String applyName = (String)delegateTask.getVariable("applyName");

        String processInstanceId = delegateTask.getProcessInstanceId();
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        String business_key = pi.getBusinessKey();
        WorkflowBaseEntity base = workflowBaseService.getById(business_key);

        FlowAssetApprovalEntity biz = flowAssetApprovalService.getById(base.getBusinessId());
        String approvalName = "";
        if ("监交人审批".equalsIgnoreCase(delegateTask.getName())) {
            approvalName = biz.getMonitor();
        } else if("接收人审批".equalsIgnoreCase(delegateTask.getName())){
            approvalName = biz.getReceiver();
        }

        Pattern p_str = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher m = p_str.matcher(approvalName);
        if(m.matches()){
            SysUserEntity egentUser = sysUserService.getUserByRealName(approvalName);
            approvalName = egentUser.getUserName();
        }
        taskService.setAssignee(delegateTask.getId(), approvalName);
        WorkflowTaskUtil.sendApplyMessage(approvalName,applyName,base.getApplyDate(), base.getWorkFlowName());
    }
}
