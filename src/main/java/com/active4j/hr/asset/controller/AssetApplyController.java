package com.active4j.hr.asset.controller;

import com.active4j.hr.activiti.biz.entity.FlowAssetApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowAssetApprovalService;
import com.active4j.hr.activiti.entity.WorkflowFormEntity;
import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowFormService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.entity.SysRoleEntity;
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
import java.util.stream.Collectors;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2021/1/11 上午12:10
 */
@Controller
@RequestMapping("asset/manage/apply")
@Slf4j
public class AssetApplyController extends BaseController {

    @Autowired
    private FlowAssetApprovalService flowAssetApprovalService;

    @Autowired
    private WorkflowMngService workflowMngService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private WorkflowFormService workflowFormService;

    @Autowired
    private WorkflowService workflowService;

    @RequestMapping("/list")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("asset/assetapplymanage");
        return view;
    }

    /**
     * 查询数据
     *
     * @param flowAssetApprovalEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(FlowAssetApprovalEntity flowAssetApprovalEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<FlowAssetApprovalEntity> queryWrapper = QueryUtils.installQueryWrapper(flowAssetApprovalEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<FlowAssetApprovalEntity> lstResult = flowAssetApprovalService.page(new Page<FlowAssetApprovalEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);



        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

    @RequestMapping("/go")
    public ModelAndView apply(HttpServletRequest request) {
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
            if (workflowMngEntity.getName().equals("固定资产移交申请")) {
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

    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(FlowAssetApprovalEntity flowAssetApprovalEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isNotEmpty(flowAssetApprovalEntity.getId())) {
                flowAssetApprovalService.removeById(flowAssetApprovalEntity.getId());
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除车辆失败，错误信息:{}", e);
        }
        return j;
    }


}
