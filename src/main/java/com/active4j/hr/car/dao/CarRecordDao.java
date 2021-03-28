package com.active4j.hr.car.dao;

import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;

import java.util.List;

public interface CarRecordDao {
    List<FlowCarApprovalEntity> getAllCarMessage(String useDepatment,String applyDate_begin,String applyDate_end);
//    List<FlowCarApprovalEntity> getCarMessageByDept(String userdept,String applyDate_begin,String applyDate_end);
}
