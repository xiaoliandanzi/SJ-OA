package com.active4j.hr.asset.controller;

import com.active4j.hr.activiti.biz.entity.FlowAssetApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowAssetApprovalService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @RequestMapping("/list")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("asset/assetapprovalmanage");
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

}
