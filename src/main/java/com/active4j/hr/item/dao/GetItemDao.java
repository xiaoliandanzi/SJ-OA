package com.active4j.hr.item.dao;

import com.active4j.hr.item.entity.GetItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.context.annotation.Primary;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/20/16:17
 * @Description:
 */
public interface GetItemDao extends BaseMapper<GetItemEntity> {
    void savegoodstaus(String id ,String goodstaus);
}
