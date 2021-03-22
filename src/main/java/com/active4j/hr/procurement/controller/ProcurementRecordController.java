package com.active4j.hr.procurement.controller;

import com.active4j.hr.activiti.biz.entity.FlowProcurementApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowProcurementApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.entity.WorkflowCategoryEntity;
import com.active4j.hr.activiti.service.WorkflowCategoryService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.activiti.util.WorkflowConstant;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2021/1/18/23:37
 * @Description:
 */
@Controller
@RequestMapping("govprocurement/return")
@Slf4j
public class ProcurementRecordController {

    @Autowired
    private FlowProcurementApprovalService flowProcurementApprovalService;


    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private WorkflowCategoryService workflowCategoryService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDeptService sysDeptService;


    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("procurement/procurementRecord");
        return view;
    }


    /**
     * 查询数据
     *
     * @param flowProcurementApprovalEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(FlowProcurementApprovalEntity flowProcurementApprovalEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<FlowProcurementApprovalEntity> queryWrapper = QueryUtils.installQueryWrapper(flowProcurementApprovalEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<FlowProcurementApprovalEntity> lstResult = flowProcurementApprovalService.page(new Page<FlowProcurementApprovalEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

        long total = lstResult.getTotal();
        long tempTotal = 0;
        List<FlowProcurementApprovalEntity> newList = new ArrayList<>();
        //获取当前用户id
        String userId = ShiroUtils.getSessionUserId();
        //获取当前用户个人资料
        SysUserEntity user = sysUserService.getById(userId);

        List<SysRoleEntity> roles = sysUserService.getUserRoleByUserId(userId);

        if (total > 0) {
            for (FlowProcurementApprovalEntity entity : lstResult.getRecords()) {
                //只显示审批完成的政采申请
                if (entity.getApplyStatus() == 1 && checkSameDept(entity, user, roles)) {
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


    private boolean checkSameDept(FlowProcurementApprovalEntity entity, SysUserEntity user, List<SysRoleEntity> roles) {
        try {

            boolean isManager = false;
            boolean isZhuguan = false;
            boolean isZhuyao = false;
            String leadDeptName = "";
            String deptName = "";

            for (SysRoleEntity role : roles) {
                if (role.getRoleName().contains("科室负责人")){
                    isManager = true;
                    deptName = StringUtils.substringBefore(role.getRoleName(), "科室负责人");
                }
                if (role.getRoleName().contains("主管领导")){
                    isZhuguan = true;
                    leadDeptName = StringUtils.substringBefore(role.getRoleName(), "主管领导");
                }
                if (role.getRoleName().contains("主要领导")){
                    isZhuyao = true;
                }
            }

            if (isZhuyao) {
                return true;
            }

            //发文信息
            // 科室
            SysUserEntity paperUser = sysUserService.getUserByUseName(entity.getCreateName());
            if (paperUser == null) {
                return false;
            }
            SysDeptEntity paperDept = sysDeptService.getById(paperUser.getDeptId());
            // 几处
            SysDeptEntity parentDept = sysDeptService.getById(paperDept.getParentId());

            //判断主管领导
            if (isZhuguan && parentDept.getName().contains(leadDeptName)) {
                return true;
            }

            //判断科室负责人
            if (isManager && paperDept.getName().contains(deptName)) {
                return true;
            }

            //判断科员
            return user.getId().equalsIgnoreCase(paperUser.getId());
        }catch (Exception ex) {
            log.error("checkSameDept", ex);
        }
        return false;
    }


    /**
     * 跳转到新增编辑页面
     *
     * @param flowProcurementApprovalEntity
     * @param request
     * @return
     */
    @RequestMapping("/check")
    public ModelAndView sealCheck(FlowProcurementApprovalEntity flowProcurementApprovalEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("procurement/procurementRecordView");

        if (StringUtils.isNotEmpty(flowProcurementApprovalEntity.getId())) {
            flowProcurementApprovalEntity = flowProcurementApprovalService.getById(flowProcurementApprovalEntity.getId());
            view.addObject("procurement", flowProcurementApprovalEntity);
        }

        return view;
    }

    /**
     * 删除
     *
     * @param flowProcurementApprovalEntity
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(FlowProcurementApprovalEntity flowProcurementApprovalEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if (StringUtils.isNotEmpty(flowProcurementApprovalEntity.getId())) {
                flowProcurementApprovalService.removeById(flowProcurementApprovalEntity.getId());
            }
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除政采申请记录失败，错误信息:{}", e);
        }
        return j;
    }

    /**
     * 跳转到已审批流程页面
     *
     * @param req
     * @return
     */
    @RequestMapping("/finishlist")
    public ModelAndView finishlist(HttpServletRequest req) {
        ModelAndView view = new ModelAndView("procurement/procurementFinishtasklist");

        // 获取流程类别数据
        List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
        int size = lstCatogorys.size();
        for (int i = size-1; i >=0 ; i--) {
            if(!lstCatogorys.get(i).getName().equals("政采审批")){
                lstCatogorys.remove(i);
            }
        }
        view.addObject("categoryReplace", ListUtils.listToReplaceStr(lstCatogorys, "name", "id"));

        return view;
    }

    /**
     * 查询数据  -- 我的已办审批
     * @param
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagridFinish")
    public void datagridFinish(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String startTime = request.getParameter("applyDate_begin");
        String endTime = request.getParameter("applyDate_end");
        if (startTime == null || startTime=="") {
            startTime = "2000-01-01";
        }

        if (endTime == null || endTime == "") {
            endTime = "2099-12-31";
        }
        String workflow_name="政采申请";
        // 执行查询
        IPage<WorkflowBaseEntity> lstResult = workflowService.findFinishedTaskByUserDept(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), workflowBaseEntity, startTime, endTime, ShiroUtils.getSessionUserDept(), WorkflowConstant.Task_Category_approval,workflow_name);

        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

}
