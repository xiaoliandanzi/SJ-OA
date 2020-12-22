package com.active4j.hr.message.controller;

import com.active4j.hr.activiti.biz.entity.FlowMessageApprovalEntity;
import com.active4j.hr.activiti.biz.entity.FlowPaperApprovalEntity;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.message.service.OaMessageService;
import com.active4j.hr.paper.service.OaPaperService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @date 2020/12/22 下午11:03
 */
@Controller
@RequestMapping("message/manage")
@Slf4j
public class MessageManageController extends BaseController {

    @Autowired
    private OaMessageService oaMessageService;


    /**
     * 车辆管理列表
     * @return
     */
    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("message/messageManage");
        return view;
    }


    /**
     * 查询数据
     *
     * @param flowPaperApprovalEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(FlowMessageApprovalEntity flowMessageApprovalEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<FlowMessageApprovalEntity> queryWrapper = QueryUtils.installQueryWrapper(flowMessageApprovalEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<FlowMessageApprovalEntity> lstResult = oaMessageService.page(new Page<FlowMessageApprovalEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        long total = lstResult.getTotal();
        long tempTotal = 0;
        List<FlowMessageApprovalEntity> newList = new ArrayList<>();
        if (total > 0) {
            for(FlowMessageApprovalEntity entity : lstResult.getRecords()) {
                //只显示审批完成的文件
                if (entity.getApplyStatus() == 1) {
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

    /**
     * 跳转到新增编辑页面
     * @param flowMessageApprovalEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdate")
    public ModelAndView paperView(FlowMessageApprovalEntity flowMessageApprovalEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("message/messageView");

        if(StringUtils.isNotEmpty(flowMessageApprovalEntity.getId())) {
            flowMessageApprovalEntity = oaMessageService.getById(flowMessageApprovalEntity.getId());
            view.addObject("biz", flowMessageApprovalEntity);
        }

        return view;
    }

}
