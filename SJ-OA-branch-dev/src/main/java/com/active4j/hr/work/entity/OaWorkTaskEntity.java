package com.active4j.hr.work.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @title OaWorkTaskEntity.java
 * @description 工作任务
 * @time 2020年4月7日 下午3:13:22
 * @author xfzhang
 * @version 1.0
 */
@TableName("OA_WORK_TASK")
@Getter
@Setter
public class OaWorkTaskEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9013042261942343706L;

	// 任务标题
	@TableField("TITLE")
	@QueryField(queryColumn="TITLE", condition=QueryCondition.like)
	private String title;

	// 任务内容
	@TableField("CONTENT")
	private String content;

	// 回执内容
	@TableField("RETURNCONTENT")
	private String returnContent;

	// 备注
	@TableField("COMMIT")
	private String commit;

	// 审核意见
	@TableField("APPROVALCOMMIT")
	private String approvalCommit;

	// 回执备注
	@TableField("RETURNCOMMIT")
	private String returnCommit;

	// 附件
	@TableField("ATTACHMENT")
	private String attachment;

	// 回执附件
	@TableField("RETURNATTACHMENT")
	private String returnAttachment;

	// 任务分配人
	@TableField("APPOINT_USER_ID")
	private String appointUserId;

	@TableField("APPOINT_USER_NAME")
	private String appointUserName;

	// 任务责任人
	@TableField("USER_ID")
	private String userId;

	// 承办科室
	@TableField("DEPT")
	@QueryField(queryColumn="DEPT", condition=QueryCondition.like)
	private String dept;

	@TableField("USER_NAME")
	@QueryField(queryColumn="USER_NAME", condition=QueryCondition.like)
	private String userName;

	@TableField("CONTRACT_NAME")
	private String contractName;

	@TableField("CONTRACT_PHONE")
	private String contractPhone;

	@TableField("NUMBER")
	@QueryField(queryColumn="NUMBER", condition=QueryCondition.like)
	private String number;

	// 任务监控人
	@TableField("MONITOR_USER_ID")
	private String monitorUserId;

	@TableField("MONITOR_USER_NAME")
	private String monitorUserName;

	// 任务完成时间
	@TableField("FINISH_TIME")
	private Date finishTime;

	// 任务开始时间
	@TableField("START_TIME")
	private Date startTime;

	// 任务结束时间
	@TableField("END_TIME")
	private Date endTime;

	@TableField("ACT_START_TIME")
	private Date actStartTime;

	@TableField("ACT_END_TIME")
	private Date actEndTime;

	// 进度描述
	@TableField("PROGRESS")
	private int progress;

	// 状态
	@TableField("STATUS")
	@QueryField(queryColumn="STATUS", condition=QueryCondition.eq)
	private String status;
	
	@TableField("PARENT_TASK_ID")
	private String parentTaskId;

}
