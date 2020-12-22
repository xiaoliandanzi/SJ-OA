package com.active4j.hr.message.service.Impl;

import com.active4j.hr.activiti.biz.entity.FlowMessageApprovalEntity;
import com.active4j.hr.message.dao.OaMessageDao;
import com.active4j.hr.message.service.OaMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/22 下午11:06
 */
@Service("oaMessageService")
@Transactional
public class OaMessageServiceImpl extends ServiceImpl<OaMessageDao, FlowMessageApprovalEntity> implements OaMessageService {
}
