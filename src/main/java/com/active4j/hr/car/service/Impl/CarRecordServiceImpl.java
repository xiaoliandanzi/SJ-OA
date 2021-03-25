package com.active4j.hr.car.service.Impl;

import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;
import com.active4j.hr.car.dao.CarRecordDao;
import com.active4j.hr.car.service.CarRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CarRecordServiceImpl implements CarRecordService {

    @Resource
    private CarRecordDao carRecordDao;
    @Override
    public List<FlowCarApprovalEntity> getAllCarMessage() {
        return this.carRecordDao.getAllCarMessage();
    }

    @Override
    public List<FlowCarApprovalEntity> getCarMessageByDept(String userdept) {
        return this.carRecordDao.getCarMessageByDept(userdept);
    }
}
