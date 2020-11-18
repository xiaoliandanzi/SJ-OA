package com.active4j.hr.car.service.Impl;

import com.active4j.hr.car.dao.OaCarDao;
import com.active4j.hr.car.entity.OaCarEntity;
import com.active4j.hr.car.service.OaCarService;
import com.active4j.hr.common.constant.GlobalConstant;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/11/19 上午12:57
 */
@Service("oaCarServiceImpl")
@Transactional
public class OaCarServiceImpl extends ServiceImpl<OaCarDao, OaCarEntity> implements OaCarService {

    /*
    * 查看可用车辆
    * */
    public List<OaCarEntity> findNormalCar(){
        QueryWrapper<OaCarEntity> queryWrapper = new QueryWrapper<OaCarEntity>();
        queryWrapper.eq("STATUS", GlobalConstant.OA_CAR_STATUS_NORMAL);
        return this.list(queryWrapper);
    }

}
