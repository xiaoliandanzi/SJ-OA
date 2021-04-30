package com.active4j.hr.car.service;

import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;

import java.util.List;

public interface CarRecordService {
    List<FlowCarApprovalEntity> getAllCarMessage(String useDepatment,String applyDate_begin,String applyDate_end,String applyname);
//    List<FlowCarApprovalEntity> getCarMessageByDept(String userdept,String applyDate_begin,String applyDate_end);
}
