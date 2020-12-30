package com.active4j.hr.activiti.listener;

import com.active4j.hr.activiti.util.WorkflowConstant;
import com.active4j.hr.activiti.util.WorkflowTaskUtil;
import com.active4j.hr.core.beanutil.ApplicationContextUtil;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.util.List;


/**
 * 审批任务节点为部门经理，审批人指定
 * @author chenxl
 *
 */
public class TaskAssigneeDepartManagerListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5712195450830995062L;

	@Override
	public void notify(DelegateTask delegateTask) {
		TaskService taskService = ApplicationContextUtil.getContext().getBean(TaskService.class);
		
		//申请人
		String applyName = (String)delegateTask.getVariable("applyName");
		
		List<String> lstUsers = WorkflowTaskUtil.getDepartManagerByApplyName(applyName);
		
		if(null == lstUsers || lstUsers.size() <= 0) {
			taskService.setAssignee(delegateTask.getId(), WorkflowConstant.Str_Admin);
		}else if(lstUsers.size() == 1) {
			taskService.setAssignee(delegateTask.getId(), lstUsers.get(0));
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
