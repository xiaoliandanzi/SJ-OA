package com.active4j.hr.car.service;

import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;

import java.util.List;

public interface CarRecordService {
    List<FlowCarApprovalEntity> getAllCarMessage();
    List<FlowCarApprovalEntity> getCarMessageByDept(String userdept);
}
