package com.active4j.hr.activiti.biz.service.impl;

import com.active4j.hr.activiti.biz.dao.FlowGetSpeRole;
import com.active4j.hr.activiti.biz.service.FlowGetSpeRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlowGetSpeRoleServiceImpl implements FlowGetSpeRoleService {

    @Autowired
    private FlowGetSpeRole flowGetSpeRole;
    @Override
    public String getCarAdminrole() {
        return this.flowGetSpeRole.getCarAdminrole();
    }
}
