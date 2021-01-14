package com.active4j.hr.car.controller;

import com.active4j.hr.activiti.entity.WorkflowFormEntity;
import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowFormService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.work.entity.OaWorkMeetRoomBooksEntity;
import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
import com.active4j.hr.work.service.OaWorkMeetRoomBooksService;
import com.active4j.hr.work.service.OaWorkMeetRoomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/11/15 下午9:09
 */
@Controller
@RequestMapping("car/apply")
@Slf4j
public class CarApplyController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private WorkflowFormService workflowFormService;

    @Autowired
    private WorkflowMngService workflowMngService;
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
            if (workflowMngEntity.getName().equals("车辆申请")) {
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
