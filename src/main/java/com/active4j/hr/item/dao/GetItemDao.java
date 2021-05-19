package com.active4j.hr.item.dao;

import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;
import com.active4j.hr.item.entity.GetItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/20/16:17
 * @Description:
 */
public interface GetItemDao extends BaseMapper<GetItemEntity> {
    void savegoodstaus(String id ,String goodstaus);

    List<GetItemEntity> getAllItemMessage(@Param("userDept") String userDept);
}
