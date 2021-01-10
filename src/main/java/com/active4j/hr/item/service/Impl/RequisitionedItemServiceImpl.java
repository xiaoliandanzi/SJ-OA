package com.active4j.hr.item.service.Impl;

import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.item.dao.RequisitionedItemDao;
import com.active4j.hr.item.entity.RequisitionedItemEntity;
import com.active4j.hr.item.service.RequisitionedItemService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/20/16:27
 * @Description:
 */
@Service("requisitionedItemService")
@Transactional
public class RequisitionedItemServiceImpl extends ServiceImpl<RequisitionedItemDao, RequisitionedItemEntity>
        implements RequisitionedItemService {
    /*
     * 查看领用物品
     * */
    public List<RequisitionedItemEntity> findBorrowItem(){
        QueryWrapper<RequisitionedItemEntity> queryWrapper = new QueryWrapper<RequisitionedItemEntity>();
        queryWrapper.eq("TYPE", GlobalConstant.ITEM_BORROW);
        return this.list(queryWrapper);
    }
    /*
     * 查看领用物品
     * */
    public List<RequisitionedItemEntity> findTmpCard(){
        QueryWrapper<RequisitionedItemEntity> queryWrapper = new QueryWrapper<RequisitionedItemEntity>();
        queryWrapper.eq("TYPE", GlobalConstant.ITEM_TMPCARD);
        return this.list(queryWrapper);
    }



}
