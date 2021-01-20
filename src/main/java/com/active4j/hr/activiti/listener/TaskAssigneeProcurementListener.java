package com.active4j.hr.activiti.listener;


import com.active4j.hr.activiti.biz.entity.FlowProcurementApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowProcurementApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.active4j.hr.activiti.util.WorkflowConstant;
import com.active4j.hr.activiti.util.WorkflowTaskUtil;
import com.active4j.hr.core.beanutil.ApplicationContextUtil;
import com.active4j.hr.system.service.SysUserService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * @title TaskAssigneeRoleNameListener.java
 * @description 政采审批人确认方法
 * @time 2021年1月18日 下午23:59:46
 * @author jinxin
 * @version 1.0
 */
public class TaskAssigneeProcurementListener implements TaskListener {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private WorkflowBaseService workflowBaseService;
    @Autowired
    private FlowProcurementApprovalService flowProcurementApprovalService;

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    RuntimeService runtimeService;
    /**
     *
     */
    private static final long serialVersionUID = 8080200516893238424L;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskService taskService = ApplicationContextUtil.getContext().getBean(TaskService.class);

        // 获取节点名称
        String taskName = delegateTask.getName();

        String roleName = "-1";
        if(StringUtils.contains(taskName, WorkflowConstant.STR_TASK_NAME_APPROVE)) {
            roleName = StringUtils.substringBefore(taskName, WorkflowConstant.STR_TASK_NAME_APPROVE);
        }else if(StringUtils.contains(taskName, WorkflowConstant.STR_TASK_NAME_HANDLE)) {
            roleName = StringUtils.substringBefore(taskName, WorkflowConstant.STR_TASK_NAME_HANDLE);
        }

        String applyName = (String)delegateTask.getVariable("applyName");
        if(taskName.equalsIgnoreCase("主管领导审批")){
            roleName= WorkflowTaskUtil.getLeaderDept(applyName);
        }

        if(taskName.equalsIgnoreCase("财务审批")){
            roleName="财务科室负责人";
        }

        if(taskName.equalsIgnoreCase("采购管理部门审批")){
            roleName="综合办公室科员";
        }

        if(taskName.equalsIgnoreCase("经办人审批")){

            String processInstanceId = delegateTask.getProcessInstanceId();

//            RuntimeService runtimeService = processEngine.getRuntimeService();
//            TaskService taskServicetmp = processEngine.getTaskService();
//            Task task = taskServicetmp.createTaskQuery().processInstanceId(processInstanceId).singleResult();
//            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
//            Task task  =  taskService.createTaskQuery().taskId(taskId).singleResult();
//            String pid = task.getProcessInstanceId();
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String business_key = pi.getBusinessKey();

            WorkflowBaseEntity base = workflowBaseService.getById(business_key);
            FlowProcurementApprovalEntity biz = flowProcurementApprovalService.getById(base.getBusinessId());
            String agent = biz.getAgent();
            taskService.setAssignee(delegateTask.getId(), agent);
            WorkflowTaskUtil.sendSystemMessage(agent, applyName);
            return;
        }

        List<String> lstUsers = WorkflowTaskUtil.getApprovalUserByRoleName(roleName);

         if(null == lstUsers || lstUsers.size() <= 0) {
            taskService.setAssignee(delegateTask.getId(), WorkflowConstant.Str_Admin);
        }else if(lstUsers.size() == 1) {
            taskService.setAssignee(delegateTask.getId(), lstUsers.get(0));
            WorkflowTaskUtil.sendSystemMessage(lstUsers.get(0), applyName);
        }else {
            for(String user : lstUsers) {
                taskService.addCandidateUser(delegateTask.getId(), user);
            }
        }
    }

}
