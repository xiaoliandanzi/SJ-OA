package com.active4j.hr.work.service;

import com.active4j.hr.work.domain.OaWorkTaskStatusDomain;
import com.active4j.hr.work.entity.OaWorkTaskCommentsEntity;
import com.active4j.hr.work.entity.OaWorkTaskEntity;
import com.active4j.hr.work.entity.OaWorkTaskExcuteEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface OaWorkTaskService extends IService<OaWorkTaskEntity> {
	

	public List<OaWorkTaskStatusDomain> queryOaWorkTaskStatusStat(String userId);

	public List<OaWorkTaskStatusDomain> queryOaWorkTaskStatusStatByAppoint(String appointUserId);
	
	public List<OaWorkTaskEntity> findOaWorkTaskByUserId(String userId);
	
	public List<OaWorkTaskEntity> findChildrenTask(String taskId);
	
	public void saveOrUpdate(OaWorkTaskEntity oaWorkTaskEntity, String status);
	
	public List<OaWorkTaskCommentsEntity> findOaWorkTaskComments(OaWorkTaskEntity oaWorkTaskEntity);
	
	public List<OaWorkTaskExcuteEntity> findOaWorkTaskExcute(OaWorkTaskEntity oaWorkTaskEntity);
}
