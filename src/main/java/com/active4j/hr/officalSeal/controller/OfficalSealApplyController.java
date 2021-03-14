package com.active4j.hr.officalSeal.controller;

import com.active4j.hr.activiti.entity.WorkflowFormEntity;
import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowCategoryService;
import com.active4j.hr.activiti.service.WorkflowFormService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/12/18/10:37
 * @Description:
 */
@Controller
@RequestMapping("officalseal/apply")
@Slf4j
public class OfficalSealApplyController {

    @Autowired
    private WorkflowMngService workflowMngService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private WorkflowFormService workflowFormService;

    /**
     *
     * @return
     */
    @RequestMapping("/go")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view;

        //当前用户ID
        String userId = ShiroUtils.getSessionUserId();

        List<SysRoleEntity> lstRoles = sysUserService.getUserRoleByUserId(userId);

        List<String> roleIds = new ArrayList<String>();
        if(null != lstRoles) {
            roleIds = lstRoles.stream().map(d -> d.getId()).collect(Collectors.toList());
        }

        List<WorkflowMngEntity> lstWorkflows = workflowMngService.findWorkflowMngByUserIdAndRoleIds(userId, roleIds);


        for (WorkflowMngEntity workflowMngEntity : lstWorkflows) {
            if (workflowMngEntity.getName().equals("双井公章申请")) {
                WorkflowFormEntity form = workflowFormService.getById(workflowMngEntity.getFormId());
                view = new ModelAndView("redirect:" + form.getPath() +
                        "?formId=" + workflowMngEntity.getFormId()
                        + "&workflowId=" + workflowMngEntity.getId());
                return view;
            }
        }
        view = new ModelAndView("system/common/warning");
        view.addObject("message", "系统不存在当前表单");
        return view;
    }
}
