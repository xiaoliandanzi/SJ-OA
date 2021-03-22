package com.active4j.hr.item.controller;

import com.active4j.hr.activiti.biz.entity.FlowOfficalSealApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowOfficalSealApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.entity.WorkflowCategoryEntity;
import com.active4j.hr.activiti.service.WorkflowCategoryService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.activiti.util.WorkflowConstant;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.item.entity.BorrowedItemEntity;
import com.active4j.hr.item.entity.GetItemEntity;
import com.active4j.hr.officalSeal.service.OaOfficalSealRecordService;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysUserService;
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

@Controller
@RequestMapping("itemcard/return")
@Slf4j
public class ItemCardRecordController extends BaseController {

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private WorkflowCategoryService workflowCategoryService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 跳转到已审批流程页面
     *
     * @param req
     * @return
     */
    @RequestMapping("/finishlist")
    public ModelAndView finishlist(HttpServletRequest req) {
        ModelAndView view = new ModelAndView("item/itemFinishtasklist");

        // 获取流程类别数据
        List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
        int size = lstCatogorys.size();
        for (int i = size-1; i >=0 ; i--) {
            if (!lstCatogorys.get(i).getName().equals("物品审批")) {
                lstCatogorys.remove(i);
            }
        }
        view.addObject("categoryReplace", ListUtils.listToReplaceStr(lstCatogorys, "name", "id"));

        return view;
    }

    /**
     * 查询数据  -- 我的已办审批
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagridFinish")
    public void datagridFinish(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String startTime = request.getParameter("applyDate_begin");
        String endTime = request.getParameter("applyDate_end");
        if (startTime == null || startTime == "") {
            startTime = "2000-01-01";
        }

        if (endTime == null || endTime == "") {
            endTime = "2099-12-31";
        }

        String workflow_name="物品借用申请";
        // 执行查询
        IPage<WorkflowBaseEntity> lstResult = workflowService.findFinishedTaskGoodsByUserDept(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows())
                , workflowBaseEntity, startTime, endTime, ShiroUtils.getSessionUserDept(), WorkflowConstant.Task_Category_approval);
//        long size = lstResult.getRecords().size();

//        for (long i = size - 1; i >= 0; --i) {
//            if (!lstResult.getRecords().get((int) i).getWorkFlowName().equals("物品借用申请") && !lstResult.getRecords().get((int) i).getWorkFlowName().equals("临时餐卡申请")) {
//                lstResult.getRecords().remove(lstResult.getRecords().get((int) i));
//            }
//        }
        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }
}
