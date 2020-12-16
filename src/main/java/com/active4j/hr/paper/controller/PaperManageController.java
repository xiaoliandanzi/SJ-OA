package com.active4j.hr.paper.controller;

import com.active4j.hr.activiti.biz.entity.FlowPaperApprovalEntity;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
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
 * @date 2020/12/10 下午11:18
 */
@Controller
@RequestMapping("paper/manage")
@Slf4j
public class PaperManageController extends BaseController {

    @Autowired
    private OaPaperService oaPaperService;
    @Autowired
    private WorkflowService workflowService;

    /**
     * 车辆管理列表
     * @return
     */
    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("paper/paperManage");
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
    public void datagrid(FlowPaperApprovalEntity flowPaperApprovalEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<FlowPaperApprovalEntity> queryWrapper = QueryUtils.installQueryWrapper(flowPaperApprovalEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<FlowPaperApprovalEntity> lstResult = oaPaperService.page(new Page<FlowPaperApprovalEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        long total = lstResult.getTotal();
        List<FlowPaperApprovalEntity> newList = new ArrayList<>();
        if (total > 0) {
            for(FlowPaperApprovalEntity entity : lstResult.getRecords()) {
                //只显示不为草稿状态的文件
                if (entity.getApplyStatus() != 3) {
                    newList.add(entity);
                    total++;
                }
            }
            lstResult.setTotal(total);
            lstResult.setRecords(newList);

        }
        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

    /**
     * 跳转到新增编辑页面
     * @param flowPaperApprovalEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdate")
    public ModelAndView paperView(FlowPaperApprovalEntity flowPaperApprovalEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("paper/paperView");

        if(StringUtils.isNotEmpty(flowPaperApprovalEntity.getId())) {
            flowPaperApprovalEntity = oaPaperService.getById(flowPaperApprovalEntity.getId());
            view.addObject("base", flowPaperApprovalEntity);
        }

        return view;
    }

}
