package com.active4j.hr.officalSeal.controller;

import com.active4j.hr.activiti.biz.entity.FlowMessageApprovalEntity;
import com.active4j.hr.activiti.biz.entity.FlowOfficalSealApprovalEntity;
import com.active4j.hr.activiti.biz.entity.FlowPaperApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowOfficalSealApprovalService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.officalSeal.entity.OaOfficalSealEntity;
import com.active4j.hr.officalSeal.service.OaOfficalSealRecordService;
import com.active4j.hr.work.entity.OaWorkMeetEntity;
import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
import com.active4j.hr.work.entity.OaWorkMeetTypeEntity;
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

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/12/09/23:17
 * @Description:
 */
@Controller
@RequestMapping("officalSeal/return")
@Slf4j
public class OfficalSealReturnController extends BaseController {

    @Autowired
    private OaOfficalSealRecordService oaOfficalSealRecordService;

    @Autowired
    private FlowOfficalSealApprovalService flowOfficalSealApprovalService;


    @Autowired
    private WorkflowService workflowService;


    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("officalSeal/officalSealReturnRecord");
        return view;
    }


    /**
     * 查询数据
     *
     * @param flowOfficalSealApprovalEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(FlowOfficalSealApprovalEntity flowOfficalSealApprovalEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<FlowOfficalSealApprovalEntity> queryWrapper = QueryUtils.installQueryWrapper(flowOfficalSealApprovalEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<FlowOfficalSealApprovalEntity> lstResult = flowOfficalSealApprovalService.page(new Page<FlowOfficalSealApprovalEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

        long total = lstResult.getTotal();
        long tempTotal = 0;
        List<FlowOfficalSealApprovalEntity> newList = new ArrayList<>();
        if (total > 0) {
            for(FlowOfficalSealApprovalEntity entity : lstResult.getRecords()) {
                //只显示审批完成的公章申请
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
     * @param flowOfficalSealApprovalEntity
     * @param request
     * @return
     */
    @RequestMapping("/check")
    public ModelAndView sealCheck(FlowOfficalSealApprovalEntity flowOfficalSealApprovalEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("officalSeal/officalSealRecordView");

        if(StringUtils.isNotEmpty(flowOfficalSealApprovalEntity.getId())) {
            flowOfficalSealApprovalEntity = flowOfficalSealApprovalService.getById(flowOfficalSealApprovalEntity.getId());
            view.addObject("seal", flowOfficalSealApprovalEntity);
        }

        return view;
    }
    /**
     * 删除
     * @param flowOfficalSealApprovalEntity
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(FlowOfficalSealApprovalEntity flowOfficalSealApprovalEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isNotEmpty(flowOfficalSealApprovalEntity.getId())) {
                flowOfficalSealApprovalService.removeById(flowOfficalSealApprovalEntity.getId());
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除公章借用记录失败，错误信息:{}", e);
        }
        return j;
    }
}
