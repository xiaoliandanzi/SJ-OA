package com.active4j.hr.system.dao;

import com.active4j.hr.system.entity.SysDeptEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


public interface SysDeptDao extends BaseMapper<SysDeptEntity>{

    String getLeaderRoleIdByRole(@Param("leaderRole") String leaderRole);
}
