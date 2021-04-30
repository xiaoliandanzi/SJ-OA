package com.active4j.hr.activiti.biz.dao;

import com.active4j.hr.activiti.biz.entity.FlowOfficalSealApprovalEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/12/14/23:54
 * @Description:
 */
public interface FlowOfficalSealApprovalDao extends BaseMapper<FlowOfficalSealApprovalEntity> {
    List<FlowOfficalSealApprovalEntity> getAllOfficalMessage(String userdept, String startTime, String endTime, String sealtype, String username);
}
