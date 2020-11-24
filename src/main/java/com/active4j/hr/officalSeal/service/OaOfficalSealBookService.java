package com.active4j.hr.officalSeal.service;

import com.active4j.hr.officalSeal.entity.OaOfficalSealBookEntity;
import com.active4j.hr.officalSeal.entity.OaOfficalSealEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/24/20:15
 * @Description:
 */
public interface OaOfficalSealBookService extends IService<OaOfficalSealBookEntity> {
    public List<OaOfficalSealBookEntity> findNormalCar();
}
