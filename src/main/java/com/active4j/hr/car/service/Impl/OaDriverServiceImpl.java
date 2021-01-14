package com.active4j.hr.car.service.Impl;

import com.active4j.hr.car.dao.OaDriverDao;
import com.active4j.hr.car.entity.OaDriverEntity;
import com.active4j.hr.car.service.OaDriverService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("oaDriverService")
@Transactional
public class OaDriverServiceImpl  extends ServiceImpl<OaDriverDao, OaDriverEntity> implements OaDriverService {

}
