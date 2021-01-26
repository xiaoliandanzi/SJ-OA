package com.active4j.hr.topic.service;

import com.active4j.hr.topic.entity.OaTopic;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.hssf.record.formula.functions.Int;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author weizihao
 * @since 2020-12-28
 */
public interface OaTopicService extends IService<OaTopic> {

    public void savetopic(OaTopic oa);

    Integer getDBCount(String userName);
}
