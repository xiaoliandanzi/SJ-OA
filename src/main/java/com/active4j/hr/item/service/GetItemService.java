package com.active4j.hr.item.service;

import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;
import com.active4j.hr.item.entity.GetItemEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/20/16:23
 * @Description:
 */
public interface GetItemService extends IService<GetItemEntity> {
    void savegoodstaus(String id,String goodstaus);

    public List<GetItemEntity> getAllItemMessage(String userDept);

}
