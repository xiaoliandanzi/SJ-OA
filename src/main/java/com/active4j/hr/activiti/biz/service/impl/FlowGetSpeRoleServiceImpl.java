package com.active4j.hr.activiti.biz.service.impl;

import com.active4j.hr.activiti.biz.dao.FlowGetSpeRole;
import com.active4j.hr.activiti.biz.service.FlowGetSpeRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FlowGetSpeRoleServiceImpl implements FlowGetSpeRoleService {

    @Resource
    private FlowGetSpeRole flowGetSpeRole;
    @Override
    public String getCarAdminrole() {
        return this.flowGetSpeRole.getCarAdminrole();
    }

    /**
     * 根据用户id查找用户角色名称
     * */
    @Override
    public List getUserRoleName(String userId) {
        return this.flowGetSpeRole.getUserRoleName(userId);
    }
}
