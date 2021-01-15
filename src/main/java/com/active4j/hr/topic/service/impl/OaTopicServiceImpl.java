package com.active4j.hr.topic.service.impl;

import com.active4j.hr.topic.entity.OaMeeting;
import com.active4j.hr.topic.entity.OaTopic;
import com.active4j.hr.topic.dao.OaTopicMapper;
import com.active4j.hr.topic.service.OaTopicService;
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
 * @since 2020-12-28
 */
@Service
public class OaTopicServiceImpl extends ServiceImpl<OaTopicMapper, OaTopic> implements OaTopicService {


    @Override
    public void savetopic(OaTopic oa) {
        QueryWrapper<OaTopic> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("ID",oa.getId());
        List<OaTopic> list=this.baseMapper.selectList(queryWrapper);
        this.baseMapper.update(null,new UpdateWrapper<OaTopic>().set("IS_HISTORY",oa.getIsHistory()).eq("ID",oa.getId()));
    }
}
