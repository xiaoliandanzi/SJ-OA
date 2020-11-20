package com.active4j.hr.officalSeal.service;

import com.active4j.hr.officalSeal.entity.OaOfficalSealEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/20/10:01
 * @Description:
 */
public interface OaOfficalSealService extends IService<OaOfficalSealEntity> {

    public List<OaOfficalSealEntity> findNormalCar();

}