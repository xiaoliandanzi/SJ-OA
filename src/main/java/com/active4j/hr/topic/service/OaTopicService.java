package com.active4j.hr.topic.service;

import com.active4j.hr.topic.entity.OaTopic;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author weizihao
 * @since 2020-12-28
 */
public interface OaTopicService extends IService<OaTopic> {

    void savetopic(OaTopic oa);
}
