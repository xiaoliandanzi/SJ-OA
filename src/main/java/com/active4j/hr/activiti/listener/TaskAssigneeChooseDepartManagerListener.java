package com.active4j.hr.activiti.listener;

import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.active4j.hr.activiti.util.WorkflowConstant;
import com.active4j.hr.activiti.util.WorkflowTaskUtil;
import com.active4j.hr.core.beanutil.ApplicationContextUtil;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.List;


/**
 * 审批任务节点为科室负责人，审批人自己指定指定
 * @author chenxl
 *
 */
public class TaskAssigneeChooseDepartManagerListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5712195450830995062L;

	@Override
	public void notify(DelegateTask delegateTask) {
		TaskService taskService = ApplicationContextUtil.getContext().getBean(TaskService.class);
		RuntimeService runtimeService = ApplicationContextUtil.getContext().getBean(RuntimeService.class);
		WorkflowBaseService workflowBaseService = ApplicationContextUtil.getContext().getBean(WorkflowBaseService.class);
		//申请人
		String applyName = (String)delegateTask.getVariable("applyName");

		String deptId = (String)delegateTask.getVariable("deptId");
		
		List<String> lstUsers = WorkflowTaskUtil.getDepartManagerByDeptId(deptId);
		
		if(null == lstUsers || lstUsers.size() <= 0) {
			taskService.setAssignee(delegateTask.getId(), WorkflowConstant.Str_Admin);
		}else if(lstUsers.size() == 1) {
			taskService.setAssignee(delegateTask.getId(), lstUsers.get(0));

			ProcessInstance pi = runtimeService.createProcessInstanceQuery()
					.processInstanceId(delegateTask.getProcessInstanceId())
					.singleResult();
			String business_key = pi.getBusinessKey();
			WorkflowBaseEntity base = workflowBaseService.getById(business_key);
			WorkflowTaskUtil.sendApplyMessage(lstUsers.get(0),applyName,base.getApplyDate(), base.getWorkFlowName());
		}else {
			//并非审批人，候选人，需要自己去承接组任务
			for(String userName : lstUsers) {
				delegateTask.addCandidateUser(userName);
				taskService.addCandidateUser(delegateTask.getId(), userName);
			}
		}
		//测试组任务
//		taskService.addCandidateUser(delegateTask.getId(), "zhangsan");
//		taskService.addCandidateUser(delegateTask.getId(), "lisi");
	}

}
