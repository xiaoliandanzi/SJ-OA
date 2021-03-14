package com.active4j.hr.topic.service.impl;

import com.active4j.hr.topic.dao.OaMeetingMapper;
import com.active4j.hr.topic.entity.OaMeeting;
import com.active4j.hr.topic.service.OaMeetingService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author weizihao
 * @since 2021-01-04
 */
@Service
public class OaMeetingServiceImpl extends ServiceImpl<OaMeetingMapper, OaMeeting> implements OaMeetingService {

    @Override
    public void savemeeting(OaMeeting oaMeeting) {
        QueryWrapper<OaMeeting> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("ID",oaMeeting.getId());
        List<OaMeeting> list=this.baseMapper.selectList(queryWrapper);
        this.baseMapper.update(null,new UpdateWrapper<OaMeeting>().set("STATE_ID",oaMeeting.getStateId()).eq("ID",oaMeeting.getId()));
    }
}
