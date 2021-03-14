package com.active4j.hr.paper.service.Impl;

import com.active4j.hr.activiti.biz.entity.FlowPaperApprovalEntity;
import com.active4j.hr.paper.dao.OaPaperDao;
import com.active4j.hr.paper.service.OaPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/10 下午11:23
 */
@Service("oaPaperService")
@Transactional
public class OaPaperServiceImpl extends ServiceImpl<OaPaperDao, FlowPaperApprovalEntity> implements OaPaperService {
}
