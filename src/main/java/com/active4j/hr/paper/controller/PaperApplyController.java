package com.active4j.hr.paper.controller;

import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.entity.WorkflowCategoryEntity;
import com.active4j.hr.activiti.entity.WorkflowFormEntity;
import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowCategoryService;
import com.active4j.hr.activiti.service.WorkflowFormService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.activiti.util.WorkflowConstant;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.service.SysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/15 下午11:43
 */
@Controller
@RequestMapping("paper/apply")
@Slf4j
public class PaperApplyController {


    @Autowired
    private WorkflowMngService workflowMngService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private WorkflowCategoryService workflowCategoryService;

    @Autowired
    private WorkflowFormService workflowFormService;

    @Autowired
    private WorkflowService workflowService;

    /**
     * @return
     */
    @RequestMapping("/go")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view;

        //当前用户ID
        String userId = ShiroUtils.getSessionUserId();

        List<SysRoleEntity> lstRoles = sysUserService.getUserRoleByUserId(userId);

        List<String> roleIds = new ArrayList<String>();
        if (null != lstRoles) {
            roleIds = lstRoles.stream().map(d -> d.getId()).collect(Collectors.toList());
        }

        List<WorkflowMngEntity> lstWorkflows = workflowMngService.findWorkflowMngByUserIdAndRoleIds(userId, roleIds);


        for (WorkflowMngEntity workflowMngEntity : lstWorkflows) {
            if (workflowMngEntity.getName().equals("发文申请")) {
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

    @RequestMapping("/myapply")
    public ModelAndView myApply(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("paper/waittasklist");
//        ModelAndView view = new ModelAndView("flow/task/waittasklist");
//
//        // 获取流程类别数据
//        List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
//
//        List<WorkflowCategoryEntity> result = new ArrayList<>();
//        for (WorkflowCategoryEntity entity : lstCatogorys) {
//            if (entity.getName().equalsIgnoreCase("发文申请")) {
//                result.add(entity);
//            }
//        }
//
//        view.addObject("categoryReplace", ListUtils.listToReplaceStr(result, "name", "id"));
        return view;
    }

    @RequestMapping("/datagrid")
    public void datagrid(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        String startTime = request.getParameter("applyDate_begin");
        String endTime = request.getParameter("applyDate_end");

        // 执行查询
        IPage<WorkflowBaseEntity> lstResult = workflowService.findTaskStrsByUserName(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), workflowBaseEntity, startTime, endTime, ShiroUtils.getSessionUserName(), WorkflowConstant.Task_Category_approval);

        long total = lstResult.getTotal();
        long tempTotal = 0;
        List<WorkflowBaseEntity> newList = new ArrayList<>();
        if (total > 0) {
            for (WorkflowBaseEntity entity : lstResult.getRecords()) {
                if (entity.getWorkFlowName().equalsIgnoreCase("发文申请")) {
                    newList.add(entity);
                    tempTotal++;
                }
            }
            lstResult.setTotal(tempTotal);
            lstResult.setRecords(newList);

        }

        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

    /*@RequestMapping("/myapply")
    public ModelAndView myApply(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("paper/waittasklist");
        return view;
    }*/

    @RequestMapping("/mycarry")
    public ModelAndView mycarry(HttpServletRequest req) {
        ModelAndView view = new ModelAndView("flow/task/groupwaittasklist");

        // 获取流程类别数据
        List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();

        List<WorkflowCategoryEntity> result = new ArrayList<>();
        for (WorkflowCategoryEntity entity : lstCatogorys) {
            if (entity.getName().equalsIgnoreCase("发文申请")) {
                result.add(entity);
            }
        }

        view.addObject("categoryReplace", ListUtils.listToReplaceStr(result, "name", "id"));

        return view;
    }
}
