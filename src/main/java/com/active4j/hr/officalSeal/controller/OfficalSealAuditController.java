package com.active4j.hr.officalSeal.controller;

import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.entity.WorkflowCategoryEntity;
import com.active4j.hr.activiti.service.WorkflowCategoryService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.activiti.util.WorkflowConstant;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
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

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/19/18:56
 * @Description:
 */
@Controller
@RequestMapping("officalSeal/audit")
@Slf4j
public class OfficalSealAuditController extends BaseController {

    @Autowired
    private WorkflowCategoryService workflowCategoryService;

    @Autowired
    private WorkflowService workflowService;

//    /**9
//     * @param request
//     * @return
//     */
//    @RequestMapping("/show")
//    public ModelAndView show(HttpServletRequest request) {
//        ModelAndView view = new ModelAndView("officalSeal/officalSealAudit");
//        return view;
//    }

    /**
     * 跳转到待审批公章页面
     *
     * @param req
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest req) {
        ModelAndView view = new ModelAndView("officalSeal/officalSealAudit");

        // 获取流程类别数据
        List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
        List<WorkflowCategoryEntity> lstSeal = new ArrayList<WorkflowCategoryEntity>();
        int size = lstCatogorys.size();
        for (int i = size - 1; i >= 0; i--) {
            WorkflowCategoryEntity catogorys = lstCatogorys.get(i);
            if (catogorys.getName().equals("双井公章审批")) {
                lstSeal.add(catogorys);
            }
        }
        view.addObject("categoryReplace", ListUtils.listToReplaceStr(lstSeal, "name", "id"));

        return view;
    }

    /**
     * 删除
     *
     * @param workflowCategoryEntity
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(WorkflowCategoryEntity workflowCategoryEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if (StringUtils.isNotEmpty(workflowCategoryEntity.getId())) {
                workflowCategoryService.removeById(workflowCategoryEntity.getId());
            }
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除公章审批记录失败，错误信息:{}", e);
        }
        return j;
    }

    /**
     * 跳转到承接审批流程
     *
     * @param req
     * @return
     */
    @RequestMapping("/groupwaittasklist")
    public ModelAndView groupwaittasklist(HttpServletRequest req) {
        ModelAndView view = new ModelAndView("officalSeal/groupwaittasklist");

        // 获取流程类别数据
        List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();

        List<WorkflowCategoryEntity> lstSeal = new ArrayList<WorkflowCategoryEntity>();
        int size = lstCatogorys.size();
        for (int i = size - 1; i >= 0; i--) {
            WorkflowCategoryEntity catogorys = lstCatogorys.get(i);
            if (catogorys.getName().equals("双井公章审批")) {
                lstSeal.add(catogorys);
            }
        }
        view.addObject("categoryReplace", ListUtils.listToReplaceStr(lstSeal, "name", "id"));

        return view;
    }

    /**
     * 查询数据  -- 我的待办审批
     *
     * @param user
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        String startTime = request.getParameter("applyDate_begin");
        String endTime = request.getParameter("applyDate_end");
        if (startTime == null || startTime == "") {
            startTime = "2000-01-01";
        }

        if (endTime == null || endTime == "") {
            endTime = "2099-12-31";
        }

        // 执行查询
        IPage<WorkflowBaseEntity> lstResult = workflowService.findTaskStrsByUserName(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), workflowBaseEntity, startTime, endTime, ShiroUtils.getSessionUserName(), WorkflowConstant.Task_Category_approval);
        long size = lstResult.getTotal();
        if (size >= lstResult.getSize()) {
//            size = lstResult.getSize();
            size = lstResult.getRecords().size();
        }
        for (long i = size - 1; i >= 0; --i) {
            if (!lstResult.getRecords().get((int) i).getWorkFlowName().equals("双井公章申请")) {
                lstResult.getRecords().remove(lstResult.getRecords().get((int) i));
            }
        }
        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }


    /**
     * 查询数据  -- 我的待办审批  组任务
     *
     * @param user
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagridGroup")
    public void datagridGroup(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        String startTime = request.getParameter("applyDate_begin");
        String endTime = request.getParameter("applyDate_end");

        if (startTime == null || startTime == "") {
            startTime = "2000-01-01";
        }

        if (endTime == null || endTime == "") {
            endTime = "2099-12-31";
        }

        IPage<WorkflowBaseEntity> lstResult = workflowService.findGroupTaskStrsByUserName(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), workflowBaseEntity, startTime, endTime, ShiroUtils.getSessionUserName());
        long size = lstResult.getTotal();
        for (long i = size - 1; i >= 0; --i) {
            if (!lstResult.getRecords().get((int) i).getWorkFlowName().equals("双井公章申请")) {
                lstResult.getRecords().remove(lstResult.getRecords().get((int) i));
            }
        }

        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }


}

