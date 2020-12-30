package com.active4j.hr.topic.dao;

import com.active4j.hr.topic.entity.OaTopic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author weizihao
 * @since 2020-12-28
 */
public interface OaTopicMapper extends BaseMapper<OaTopic> {

    <T> IPage<OaTopic> findTopicList(OaTopic oaTopic);

}
