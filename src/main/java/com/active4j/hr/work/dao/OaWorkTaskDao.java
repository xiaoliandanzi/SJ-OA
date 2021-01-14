package com.active4j.hr.work.dao;

import com.active4j.hr.work.domain.OaWorkTaskStatusDomain;
import com.active4j.hr.work.entity.OaWorkTaskEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title OaWorkTaskDao.java
 * @description 
		  任务管理
 * @time  2020年4月6日 上午9:22:19
 * @author xfzhang
 * @version 1.0
*/
public interface OaWorkTaskDao extends BaseMapper<OaWorkTaskEntity>{

	public List<OaWorkTaskStatusDomain> queryOaWorkTaskStatusStat(@Param("userId") String userId);

	public List<OaWorkTaskStatusDomain> queryOaWorkTaskStatusStatByAppoint(@Param("appointUserId") String appointUserId);
}
