package com.active4j.hr.car.dao;

import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarRecordDao {
    List<FlowCarApprovalEntity> getAllCarMessage(
            @Param("useDepatment") String useDepatment,
            @Param("applyDate_begin") String applyDate_begin,
            @Param("applyDate_end") String applyDate_end,
            @Param("applyname") String applyname
    );
//    List<FlowCarApprovalEntity> getCarMessageByDept(String userdept,String applyDate_begin,String applyDate_end);
}
