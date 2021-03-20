package com.active4j.hr.asset.controller;

import com.active4j.hr.activiti.biz.service.FlowAssetApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.entity.WorkflowCategoryEntity;
import com.active4j.hr.activiti.service.WorkflowCategoryService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.activiti.util.WorkflowConstant;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
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

/**
 * @author xfzhang
 * @version 1.0
 * @date 2021/1/11 上午12:11
 */
@Controller
@RequestMapping("asset/manage/approval")
@Slf4j
public class AssetApprovalController extends BaseController {

    @Autowired
    private FlowAssetApprovalService flowAssetApprovalService;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private WorkflowCategoryService workflowCategoryService;

    @RequestMapping("/list")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("asset/waittasklist");
        return view;
    }

    @RequestMapping("/addlist")
    public ModelAndView addshow(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("asset/assetAudit");

        // 获取流程类别数据
        List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
        List<WorkflowCategoryEntity> lstSeal = new ArrayList<WorkflowCategoryEntity>();
        int size = lstCatogorys.size();
        for (int i = size - 1; i >= 0; i--) {
            WorkflowCategoryEntity catogorys = lstCatogorys.get(i);
            if (catogorys.getName().equals("行政类")) {
                lstSeal.add(catogorys);
            }
        }
        view.addObject("categoryReplace", ListUtils.listToReplaceStr(lstSeal, "name", "id"));

        return view;
    }

    /**
     * 查询数据
     *
     * @param workflowBaseEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void adddatagrid(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String startTime = request.getParameter("applyDate_begin");
        String endTime = request.getParameter("applyDate_end");

        // 执行查询
        IPage<WorkflowBaseEntity> lstResult = workflowService.findTaskStrsByUserName(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), workflowBaseEntity, startTime, endTime, ShiroUtils.getSessionUserName(), WorkflowConstant.Task_Category_approval);

        long total = lstResult.getTotal();
        long tempTotal = 0;
        List<WorkflowBaseEntity> newList = new ArrayList<>();
        if (total > 0) {
            for(WorkflowBaseEntity entity : lstResult.getRecords()) {
                if (entity.getWorkFlowName().equalsIgnoreCase("固定资产移交申请")) {
                    newList.add(entity);
                    tempTotal++;
                }
            }
            lstResult.setTotal(tempTotal);
            lstResult.setRecords(newList);

        }

        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

    /**
     * 查询入库申请数据
     *
     * @param workflowBaseEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/adddatagrid")
    public void datagrid(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String startTime = request.getParameter("applyDate_begin");
        String endTime = request.getParameter("applyDate_end");

        // 执行查询
        IPage<WorkflowBaseEntity> lstResult = workflowService.findTaskStrsByUserName(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), workflowBaseEntity, startTime, endTime, ShiroUtils.getSessionUserName(), WorkflowConstant.Task_Category_approval);
        long size = lstResult.getTotal();
        for (long i = size - 1; i >= 0; --i) {
            if (!lstResult.getRecords().get((int) i).getWorkFlowName().equals("固定资产入库")) {
                lstResult.getRecords().remove(lstResult.getRecords().get((int) i));
            }
        }
        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

}
