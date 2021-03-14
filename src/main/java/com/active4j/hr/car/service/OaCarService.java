package com.active4j.hr.car.service;

import com.active4j.hr.car.entity.OaCarEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/11/19 上午1:02
 */
public interface OaCarService extends IService<OaCarEntity> {

    public List<OaCarEntity> findNormalCar();

}

