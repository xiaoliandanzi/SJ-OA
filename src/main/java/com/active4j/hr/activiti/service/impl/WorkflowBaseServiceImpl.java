package com.active4j.hr.activiti.service.impl;

import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.system.model.SysUserModel;
import com.active4j.hr.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.activiti.dao.WorkflowBaseDao;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.active4j.hr.core.model.AjaxJson;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

/**
 * 
 * @title WorkflowAuthService.java
 * @description 
		  工作流 业务
 * @time  2020年4月3日 下午2:24:38
 * @author xfzhang
 * @version 1.0
 */
@Service("workflowBaseService")
@Transactional
@Slf4j
public class WorkflowBaseServiceImpl extends ServiceImpl<WorkflowBaseDao, WorkflowBaseEntity> implements WorkflowBaseService {
	@Autowired
	private SysUserService sysUserService;
	public AjaxJson validWorkflowBase(WorkflowBaseEntity workflowBaseEntity, AjaxJson j) {
		//获取当前用户id
		String userId = ShiroUtils.getSessionUserId();
		//获取当前用户个人资料
		SysUserModel user = sysUserService.getInfoByUserId(userId).get(0);
		if(workflowBaseEntity.getName()==null || workflowBaseEntity.getProjectNo()==null){
			workflowBaseEntity.setName("双井公章借用申请");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
			workflowBaseEntity.setProjectNo(String.format("%s-%s", user.getUserName(), DateUtils.date2Str(DateUtils.getNow(), sdf)));
		}
		if(StringUtils.isBlank(workflowBaseEntity.getName())) {
			j.setSuccess(false);
			j.setMsg("流程名称不能为空");
			return j;
		}
		
		if(StringUtils.isBlank(workflowBaseEntity.getProjectNo())) {
			j.setSuccess(false);
			j.setMsg("流程编号不能为空");
			return j;
		}
		
		if(StringUtils.isBlank(workflowBaseEntity.getWorkflowId())) {
			j.setSuccess(false);
			j.setMsg("流程参数不能为空");
			return j;
		}
		workflowBaseEntity.setLevel("0");
		if(StringUtils.isBlank(workflowBaseEntity.getLevel())) {
			j.setSuccess(false);
			j.setMsg("流程紧急程度不能为空");
			return j;
		}
		return j;
	}
	
}
