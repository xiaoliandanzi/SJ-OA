package com.active4j.hr.activiti.biz.controller;

import com.active4j.hr.activiti.biz.service.FlowCarApprovalService;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/12/14/23:50
 * @Description:
 */
@Controller
@RequestMapping("flow/biz/sealapproval")
@Slf4j
public class FlowOfficalSealApprovalController  extends BaseController {
    @Autowired
    private WorkflowBaseService workflowBaseService;

    @Autowired
    private WorkflowMngService workflowMngService;

    @Autowired
    private FlowCarApprovalService flowCarApprovalService;

    @Autowired
    private WorkflowService workflowService;
}
