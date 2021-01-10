package com.active4j.hr.asset.service.Impl;

import com.active4j.hr.activiti.biz.entity.FlowAssetApprovalEntity;
import com.active4j.hr.asset.dao.OaAssetDao;
import com.active4j.hr.asset.service.OaAssetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2021/1/11 上午12:08
 */
@Service("oaAssetService")
@Transactional
public class OaPaperServiceImpl extends ServiceImpl<OaAssetDao, FlowAssetApprovalEntity> implements OaAssetService {
}
