package com.active4j.hr.topic.service.impl;

import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.topic.entity.OaMeeting;
import com.active4j.hr.topic.entity.OaTopic;
import com.active4j.hr.topic.dao.OaTopicMapper;
import com.active4j.hr.topic.service.OaTopicService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author weizihao
 * @since 2020-12-28
 */
@Service
public class OaTopicServiceImpl extends ServiceImpl<OaTopicMapper, OaTopic> implements OaTopicService {

    @Resource
    private OaTopicMapper oaTopicMapper;
    @Override
    public void savetopic(OaTopic oa) {
        QueryWrapper<OaTopic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ID", oa.getId());
        List<OaTopic> list = this.baseMapper.selectList(queryWrapper);
        this.baseMapper.update(null, new UpdateWrapper<OaTopic>().set("IS_HISTORY", oa.getIsHistory()).eq("ID", oa.getId()));
    }

    @Override
    public Integer getDBCount(String userName) {
        return this.baseMapper.getDBCount(userName);
    }

    //获取纪委上级主管领导部门名称用于字符串拼接
    @Override
    public String getDeptName(String disciplineOfficeId) {
        return this.oaTopicMapper.getDeptName(disciplineOfficeId);
    }

    @Override
    public List<SysUserEntity> getUserMessage(String username) {
        return this.oaTopicMapper.getUserMessage(username);
    }
}
