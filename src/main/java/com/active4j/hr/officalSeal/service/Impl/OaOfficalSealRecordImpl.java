package com.active4j.hr.officalSeal.service.Impl;

import com.active4j.hr.activiti.biz.entity.FlowOfficalSealApprovalEntity;
import com.active4j.hr.officalSeal.dao.OaOfficalSealRecordDao;
import com.active4j.hr.officalSeal.service.OaOfficalSealRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/12/18/11:21
 * @Description:
 */
@Service("oaOfficalSealRecordService")
@Transactional
public class OaOfficalSealRecordImpl extends ServiceImpl<OaOfficalSealRecordDao, FlowOfficalSealApprovalEntity> implements OaOfficalSealRecordService {
}
