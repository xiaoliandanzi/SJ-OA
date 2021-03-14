package com.active4j.hr.activiti.biz.controller;

import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.active4j.hr.activiti.service.WorkflowFormService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.work.entity.OaWorkTaskEntity;
import com.active4j.hr.work.service.OaWorkTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2021/2/22 上午12:59
 */
@Controller
@RequestMapping("flow/biz/taskapproval")
@Slf4j
public class FlowTaskApprovalController extends BaseController {


    @Autowired
    private WorkflowMngService workflowMngService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private WorkflowFormService workflowFormService;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private WorkflowBaseService workflowBaseService;

    @Autowired
    private OaWorkTaskService oaWorkTaskService;

    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(WorkflowBaseEntity workflowBaseEntity, OaWorkTaskEntity taskEntity,
                         String returnContent,
                         String returnCommit, String returnAttachment, HttpServletRequest request){

        AjaxJson j = new AjaxJson();
        try {

            WorkflowMngEntity workflow = workflowMngService.getById(workflowBaseEntity.getWorkflowId());
            if(null == workflow) {
                j.setSuccess(false);
                j.setMsg("参数错误，系统中没有该流程");
                return j;
            }

            taskEntity.setReturnAttachment(returnAttachment);
            taskEntity.setReturnCommit(returnCommit);
            taskEntity.setReturnContent(returnContent);

            //taskEntity.setApplyStatus(0);
            //直接申请流程
            if(StringUtils.isBlank(workflowBaseEntity.getId())) {
                workflowBaseEntity.setApplyDate(DateUtils.getDate());
                workflowBaseEntity.setApplyName(ShiroUtils.getSessionUser().getRealName());
                workflowBaseEntity.setUserName(ShiroUtils.getSessionUserName());
                workflowBaseEntity.setCategoryId(workflow.getCategoryId());
                workflowBaseEntity.setWorkflowId(workflow.getId());
                workflowBaseEntity.setWorkFlowName(workflow.getName());
                workflowBaseEntity.setStatus("1"); //草稿状态 0：草稿 1： 已申请  2： 审批中 3： 已完成 4： 已归档
                workflowBaseEntity.setName("督办审核-"+taskEntity.getTitle());
                //保存业务数据
                workflowBaseEntity.setBusinessId(taskEntity.getId());
                workflowBaseService.save(workflowBaseEntity);
                oaWorkTaskService.save(taskEntity);

                //启动流程
                //赋值流程变量
                Map<String, Object> variables = new HashMap<String, Object>();
                workflowService.startProcessInstanceByKey(workflow.getProcessKey(), workflowBaseEntity.getId(), true, workflowBaseEntity.getUserName(), variables);
            }else {
//                WorkflowBaseEntity base = workflowBaseService.getById(workflowBaseEntity.getId());
//                MyBeanUtils.copyBeanNotNull2Bean(workflowBaseEntity, base);
//
//                FlowMessageApprovalEntity biz = flowMessageApprovalService.getById(base.getBusinessId());
//                MyBeanUtils.copyBeanNotNull2Bean(flowMessageApprovalEntity, biz);
//                //已申请
//                base.setStatus("1");
//                flowMessageApprovalService.saveUpdate(base, biz);
//
//                //启动流程
//                //赋值流程变量
//                Map<String, Object> variables = new HashMap<String, Object>();
//                workflowService.startProcessInstanceByKey(workflow.getProcessKey(), biz.getId(), true, base.getUserName(), variables);

            }


        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg("督办审核保存失败，错误信息:" + e.getMessage());
            log.error("督办审核流程保存失败，错误信息:{}", e);
        }

        return j;
    }
}
