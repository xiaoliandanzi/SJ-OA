package com.active4j.hr.asset.service.Impl;

import com.active4j.hr.activiti.biz.entity.FlowAssetApprovalEntity;
import com.active4j.hr.asset.dao.OaAssetDao;
import com.active4j.hr.asset.entity.OaAssetStoreEntity;
import com.active4j.hr.asset.service.OaAssetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2021/1/11 下午9:51
 */
@Service("oaAssetService")
@Transactional
public class OaAssetServiceImpl extends ServiceImpl<OaAssetDao, OaAssetStoreEntity> implements OaAssetService {
}
