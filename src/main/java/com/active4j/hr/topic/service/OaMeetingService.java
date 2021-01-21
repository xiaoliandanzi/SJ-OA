package com.active4j.hr.topic.service;

import com.active4j.hr.topic.entity.OaMeeting;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author weizihao
 * @since 2021-01-04
 */
@Transactional
public interface OaMeetingService extends IService<OaMeeting> {

    public void savemeeting(OaMeeting oaMeeting);
}
