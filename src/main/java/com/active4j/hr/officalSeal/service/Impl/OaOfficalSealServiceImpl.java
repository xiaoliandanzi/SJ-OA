package com.active4j.hr.officalSeal.service.Impl;

import com.active4j.hr.car.dao.OaCarDao;
import com.active4j.hr.car.entity.OaCarEntity;
import com.active4j.hr.car.service.OaCarService;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.officalSeal.dao.OaOfficalSealDao;
import com.active4j.hr.officalSeal.entity.OaOfficalSealEntity;
import com.active4j.hr.officalSeal.service.OaOfficalSealService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/20/10:01
 * @Description:
 */
@Service("OaOfficalSealServiceImpl")
@Transactional
public class OaOfficalSealServiceImpl extends ServiceImpl<OaOfficalSealDao, OaOfficalSealEntity> implements OaOfficalSealService {

    /*
     * 查看可用公章
     * */
    public List<OaOfficalSealEntity> findNormalCar(){
        QueryWrapper<OaOfficalSealEntity> queryWrapper = new QueryWrapper<OaOfficalSealEntity>();
        queryWrapper.eq("STATUS", GlobalConstant.OA_OFFICALSEAL_STATUS_NORMAL);
        return this.list(queryWrapper);
    }

}
