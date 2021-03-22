package com.active4j.hr.activiti.biz.controller;

import com.active4j.hr.activiti.biz.service.FlowGetSpeRoleService;
import com.active4j.hr.core.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Set;

@Controller
@RequestMapping("flow/biz/getSpe")

public class FlowGetSpeRoleController {
    @Autowired
    private FlowGetSpeRoleService flowGetSpeRoleService;

    @RequestMapping("getcaradminrole")
    @ResponseBody
    public Boolean getCarAdminrole(){
        Set userRole = ShiroUtils.getSessionUserRole();
        Boolean sign=false;
        for (Object str:userRole){
            String rolenum= this.flowGetSpeRoleService.getCarAdminrole();
            if (str.equals(rolenum)){
                sign=true;
            }
        }
        return sign;
    }


}
