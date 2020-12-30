package com.active4j.hr.car.service;

import com.active4j.hr.car.entity.OaCarBooksEntity;
import com.active4j.hr.car.entity.OaCarEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/11/22 下午8:12
 */
public interface OaCarBooksService extends IService<OaCarBooksEntity> {
    /**
     * 查看车辆的预定
     */
    public List<OaCarBooksEntity> findCarBooks(OaCarEntity oaCarEntity);

    public List<OaCarBooksEntity> findCarBooks(String carId, String strDate);

}
