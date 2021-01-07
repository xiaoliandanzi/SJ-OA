package com.active4j.hr.activiti.listener;

import com.active4j.hr.activiti.util.WorkflowConstant;
import com.active4j.hr.activiti.util.WorkflowTaskUtil;
import com.active4j.hr.core.beanutil.ApplicationContextUtil;
import com.active4j.hr.system.service.SysUserService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @title TaskAssigneeRoleNameListener.java
 * @description 根据角色名称确定的审批人
 * @time 2020年4月23日 下午9:20:46
 * @author xfzhang
 * @version 1.0
 */
public class TaskAssigneeRoleNameListener implements TaskListener {

	@Autowired
	private SysUserService sysUserService;

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
			roleName=WorkflowTaskUtil.getLeaderDept(applyName);
		}

		if(taskName.equalsIgnoreCase("书记审批")){
			roleName="书记";
		}

		if(taskName.equalsIgnoreCase("主任审批")){
			roleName="主任";
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
