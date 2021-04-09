package com.active4j.hr.activiti.util;

import com.active4j.hr.core.beanutil.ApplicationContextUtil;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysRoleService;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.system.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @title WorkflowTaskUtil.java
 * @description 工作流审批 关于审批人的工具类
 * @time 2020年4月23日 下午8:55:23
 * @author xfzhang
 * @version 1.0
 */
@Slf4j
public class WorkflowTaskUtil {

	private static SysUserService sysUserService = ApplicationContextUtil.getContext().getBean(SysUserService.class);
	private static SysDeptService sysDeptService = ApplicationContextUtil.getContext().getBean(SysDeptService.class);
	private static SysRoleService sysRoleService = ApplicationContextUtil.getContext().getBean(SysRoleService.class);

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 
	 * @description
	 *  	查询申请人的部门经理
	 * @return List<String>
	 * @author xfzhang
	 * @time 2020年4月23日 下午9:29:33
	 */
	public static List<String> getDepartManagerByApplyName(String applyName) {

		// 确定用户
		SysUserEntity user = sysUserService.getUserByUseName(applyName);
		// 部门查询
		SysDeptEntity dept = sysDeptService.getById(user.getDeptId());
		// 确定角色名称
		String roleName = dept.getName() + WorkflowConstant.Str_Dept_Manager;
		// 根据角色名称查询角色
		List<SysUserEntity> lstUsers = sysRoleService.findUserByRoleName(roleName);

		return lstUsers.stream().map(u -> u.getUserName()).collect(Collectors.toList());

	}

	/**
	 *
	 * @description
	 *  	查询指定部门的部门经理
	 * @return List<String>
	 * @author xfzhang
	 * @time 2020年4月23日 下午9:29:33
	 */
	public static List<String> getDepartManagerByDeptId(String deptId) {

		List<SysUserEntity> users = sysDeptService.getUsersByDept(deptId);
		// 确定用户
		SysUserEntity user = sysUserService.getUserByUseName(users.get(0).getUserName());
		// 部门查询
		SysDeptEntity dept = sysDeptService.getById(user.getDeptId());
		// 确定角色名称
		String roleName = dept.getName() + WorkflowConstant.Str_Dept_Manager;
		// 根据角色名称查询角色
		List<SysUserEntity> lstUsers = sysRoleService.findUserByRoleName(roleName);

		return lstUsers.stream().map(u -> u.getUserName()).collect(Collectors.toList());

	}

	public static String getLeaderDept(String applyName) {
		// 确定用户
		SysUserEntity user = sysUserService.getUserByUseName(applyName);
		// 部门查询
		SysDeptEntity dept = sysDeptService.getById(user.getDeptId());
		//主管部门查询
		SysDeptEntity parentDept = sysDeptService.getById(dept.getParentId());
		String roleName = StringUtils.substringBefore(parentDept.getName(), "分管") + "主管领导";//取分管前字符与主管领导拼接
		return roleName;
	}

	/**
	 * 
	 * @description
	 *  	TODO
	 * @return List<String>
	 * @author xfzhang
	 * @time 2020年4月23日 下午9:29:50
	 */
	public static List<String> getApprovalUserByRoleName(String roleName) {
		// 根据角色名称查询角色
		List<SysUserEntity> lstUsers = sysRoleService.findUserByRoleName(roleName);

		return lstUsers.stream().map(u -> u.getUserName()).collect(Collectors.toList());
	}

	public static void sendSystemMessage(String approvalName, String applyName) {
		try {
			MessageUtils.SendSysMessage(sysUserService.getUserByUseName(approvalName).getId(),
					"您收到一条来自" + sysUserService.getUserByUseName(applyName).getRealName() + "的审批需求");
		} catch (Exception ex) {
			log.error("发送信息", ex);
		}

	}

	//申请
	public static void sendApplyMessage(String approvalName, String applyName, Date taskTime , String taskName) {
		try {
			MessageUtils.SendSysMessage(sysUserService.getUserByUseName(approvalName).getId(),
					String.format("您好，%s于%s提出%s，请审批。",
							sysUserService.getUserByUseName(applyName).getRealName(), formatter.format(taskTime), taskName));
		} catch (Exception ex) {
			log.error("sendApplyMessage", ex);
		}
	}

	//承接申请
	public static void sendAllApplyMessage(String approvalName, String applyName, Date taskTime , String taskName) {
		try {
			MessageUtils.SendSysMessage(sysUserService.getUserByUseName(approvalName).getId(),
					String.format("您好，%s于%s提出%s，请承接。",
							sysUserService.getUserByUseName(applyName).getRealName(), formatter.format(taskTime), taskName));
		} catch (Exception ex) {
			log.error("sendAllApplyMessage", ex);
		}
	}

	//审批通过
	public static void sendApprovalMessage(String applyName, String approvalName, Date taskTime, String taskName) {
		System.out.println(applyName);
		System.out.println(approvalName);
		try {
			MessageUtils.SendSysMessage(sysUserService.getUserByUseName(applyName).getId(),
					String.format("您好，您于%s提出的%s，%s已审批通过，请查收",
							formatter.format(taskTime), taskName, sysUserService.getUserByUseName(approvalName).getRealName()));
		} catch (Exception ex) {
			log.error("sendApprovalMessgae", ex);
		}
	}


	//审批驳回
	public static void sendRejectMessage(String applyName, String approvalName, Date taskTime, String taskName) {
		try {
			MessageUtils.SendSysMessage(sysUserService.getUserByUseName(applyName).getId(),
					String.format("您好，您于%s提出的%s，被%s驳回，请查收",
							formatter.format(taskTime), taskName, sysUserService.getUserByUseName(approvalName).getRealName()));
		} catch (Exception ex) {
			log.error("sendRejectMessage", ex);
		}
	}

	//车辆验车消息提醒
	public static void sendCarMessageCheck(String carId,String applyName, Date time) {
		System.out.println(applyName);
		//System.out.println(approvalName);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		try {
			MessageUtils.SendSysMessage(sysUserService.getUserByUseName(applyName).getId(),
					String.format("您好，%s将于%s验车，请查收",carId,df.format(time),
							formatter.format(new Date())));
		} catch (Exception ex) {
			log.error("sendApprovalMessgae", ex);
		}
	}

	//车辆保险消息提醒
	public static void sendCarMessageInsure(String carId,String applyName, Date time) {
		System.out.println(applyName);
		//System.out.println(approvalName);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		try {
			MessageUtils.SendSysMessage(sysUserService.getUserByUseName(applyName).getId(),
					String.format("您好，%s将于%s保险过期，请查收",carId,df.format(time),
							formatter.format(new Date())));
		} catch (Exception ex) {
			log.error("sendApprovalMessgae", ex);
		}
	}


}

