package com.active4j.hr.item.service;

import com.active4j.hr.item.entity.RequisitionedItemEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/20/16:24
 * @Description:
 */
public interface RequisitionedItemService extends IService<RequisitionedItemEntity> {
    public List<RequisitionedItemEntity> findNormalRequisitionItem();
}
