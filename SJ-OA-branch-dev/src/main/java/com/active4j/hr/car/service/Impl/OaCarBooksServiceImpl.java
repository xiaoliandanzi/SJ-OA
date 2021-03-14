package com.active4j.hr.car.service.Impl;

import com.active4j.hr.car.dao.OaCarBooksDao;
import com.active4j.hr.car.entity.OaCarBooksEntity;
import com.active4j.hr.car.entity.OaCarEntity;
import com.active4j.hr.car.service.OaCarBooksService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/11/22 下午8:22
 */
@Service("OaCarBooksService")
@Transactional
public class OaCarBooksServiceImpl extends ServiceImpl<OaCarBooksDao, OaCarBooksEntity> implements OaCarBooksService {
    /**
     * 查看车辆的预定
     */
    public List<OaCarBooksEntity> findCarBooks(OaCarEntity oaCarEntity){
        QueryWrapper<OaCarBooksEntity> queryWrapper = new QueryWrapper<OaCarBooksEntity>();
        queryWrapper.eq("CAR_ID", oaCarEntity.getId());

        return this.list(queryWrapper);

    }

    public List<OaCarBooksEntity> findCarBooks(String carId, String strDate) {
        QueryWrapper<OaCarBooksEntity> queryWrapper = new QueryWrapper<OaCarBooksEntity>();
        queryWrapper.eq("CAR_ID", carId);
        queryWrapper.eq("STR_BOOK_DATE", strDate);

        return this.list(queryWrapper);
    }
}
