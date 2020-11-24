package com.active4j.hr.officalSeal.service.Impl;

import com.active4j.hr.officalSeal.dao.OaOfficalSealBookDao;
import com.active4j.hr.officalSeal.entity.OaOfficalSealBookEntity;
import com.active4j.hr.officalSeal.entity.OaOfficalSealEntity;
import com.active4j.hr.officalSeal.service.OaOfficalSealBookService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/24/20:16
 * @Description:
 */
@Service("OaOfficalSealBookServiceImpl")
@Transactional
public class OaOfficalSealBookServiceImpl  extends ServiceImpl<OaOfficalSealBookDao, OaOfficalSealBookEntity> implements OaOfficalSealBookService {

    /*
     * 查看公章预定
     * */
    public List<OaOfficalSealBookEntity> findSealBooks(OaOfficalSealEntity oaOfficalSealEntity){
        QueryWrapper<OaOfficalSealBookEntity> queryWrapper = new QueryWrapper<OaOfficalSealBookEntity>();
        queryWrapper.eq("SEAL_ID", oaOfficalSealEntity.getId());

        return this.list(queryWrapper);

    }

    public List<OaOfficalSealBookEntity> findSealBooks(String sealId, String strDate) {
        QueryWrapper<OaOfficalSealBookEntity> queryWrapper = new QueryWrapper<OaOfficalSealBookEntity>();
        queryWrapper.eq("SEAL_ID", sealId);
        queryWrapper.eq("STR_BOOK_DATE", strDate);

        return this.list(queryWrapper);
    }
}
