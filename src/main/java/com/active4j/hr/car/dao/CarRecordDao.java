package com.active4j.hr.car.dao;

import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;

import java.util.List;

public interface CarRecordDao {
    List<FlowCarApprovalEntity> getAllCarMessage();
    List<FlowCarApprovalEntity> getCarMessageByDept(String userdept);
}
