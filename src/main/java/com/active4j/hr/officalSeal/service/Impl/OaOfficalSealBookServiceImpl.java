package com.active4j.hr.officalSeal.service.Impl;

import com.active4j.hr.common.constant.GlobalConstant;
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
    public List<OaOfficalSealBookEntity> findNormalCar(){
        QueryWrapper<OaOfficalSealBookEntity> queryWrapper = new QueryWrapper<OaOfficalSealBookEntity>();
        queryWrapper.eq("STATUS", GlobalConstant.OA_OFFICALSEAL_RETURN_NORMAL);
        return this.list(queryWrapper);
    }
}
