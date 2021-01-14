package com.active4j.hr.activiti.biz.controller;

import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowCarApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.entity.WorkflowFormEntity;
import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.active4j.hr.activiti.service.WorkflowFormService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.system.model.SysUserModel;
import com.active4j.hr.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/8 下午10:21
 */
@Controller
@RequestMapping("flow/biz/carapproval")
@Slf4j
public class FlowCarApprovalController extends BaseController {

    @Autowired
    private WorkflowBaseService workflowBaseService;

    @Autowired
    private WorkflowMngService workflowMngService;

    @Autowired
    private FlowCarApprovalService flowCarApprovalService;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private WorkflowFormService workflowFormService;
    /**
     * 跳转到表单页面
     * @param request
     * @param id  流程中心的ID
     * @return
     */
    @RequestMapping("/go")
    public ModelAndView go(String formId, String type, String workflowId, String id, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("flow/carapproval/apply");

        if(StringUtils.isEmpty(formId)) {
            view = new ModelAndView("system/common/warning");
            view.addObject("message", "该流程没有配置相应表单");
            return view;
        }

        /**
         * 根据type值，绝对跳转到哪个页面，主要有两种页面
         * 0：新增，编辑页面
         * 1：审批时显示详情页面
         * 2: 直接办理流程
         * 3： 审批时显示详情页面，并附带审批功能
         */
        if(StringUtils.equals("0", type)) {
            view = new ModelAndView("flow/carapproval/apply");
        }else if(StringUtils.equals("1", type)) {
            view = new ModelAndView("flow/carapproval/applyshow");

            //根据businessKey查询任务list
            String currentName = ShiroUtils.getSessionUserName();

            //查看历史审批意见
            List<Comment> lstComments =  workflowService.findCommentsListByBusinessKey(id);
            view.addObject("lstComments", lstComments);
            view.addObject("currentName", currentName);
            view.addObject("show", "0");

        }else if(StringUtils.equals("2", type)) {
            view = new ModelAndView("flow/include/approve");

            //根据businessKey查询任务list
            String currentName = ShiroUtils.getSessionUserName();
            List<Task> lstTasks = workflowService.findTaskListByBusinessKey(id, currentName);
            view.addObject("lstTasks", lstTasks);
            view.addObject("action", "flow/biz/carapproval/doApprove");
        }else if(StringUtils.equals("3", type)) {
            view = new ModelAndView("flow/carapproval/applyshow");

            //根据businessKey查询任务list
            String currentName =ShiroUtils.getSessionUserName();
            List<Task> lstTasks = workflowService.findTaskListByBusinessKey(id, currentName);
            view.addObject("lstTasks", lstTasks);

            //查看历史审批意见
            List<Comment> lstComments =  workflowService.findCommentsListByBusinessKey(id);
            view.addObject("lstComments", lstComments);
            view.addObject("currentName", currentName);
            view.addObject("show", "1");
            view.addObject("action", "flow/biz/carapproval/doApprove");
        }


        //业务ID
        if(StringUtils.isNotEmpty(id)) {
            WorkflowBaseEntity base = workflowBaseService.getById(id);
            view.addObject("base", base);

            FlowCarApprovalEntity biz = flowCarApprovalService.getById(base.getBusinessId());
            view.addObject("biz", biz);
        }

        //获取当前用户id
        String userId = ShiroUtils.getSessionUserId();
        //获取当前用户个人资料
        SysUserModel user = sysUserService.getInfoByUserId(userId).get(0);
        view.addObject("dept", user.getDeptName());

        view.addObject("workflowId", workflowId);
        return view;
    }


    /**
     * 完成任务审批
     * @param id   业务ID
     * @param taskId  任务ID
     * @param comment  填写的审批意见
     * @param request
     * @return
     */
    @RequestMapping("/doApprove")
    @ResponseBody
    public AjaxJson doApprove(String id, String taskId, String comment, HttpServletRequest request){
        AjaxJson j = new AjaxJson();

        try{
            if(StringUtils.isEmpty(comment)) {
                j.setMsg("审批意见不能为空");
                j.setSuccess(false);
                return j;
            }
            if(StringUtils.isEmpty(taskId)) {
                j.setMsg("任务不能为空");
                j.setSuccess(false);
                return j;
            }

            Map<String, Object> map = new HashMap<String, Object>();
            workflowService.saveSubmitTask(taskId, id, comment, map);


        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.ERROR_MSG);
            log.error("审批报错，错误信息:{}", e);
        }

        return j;
    }


    /**
     * 保存方法
     * @param workflowCategoryEntity
     * @param optType  0 : 保存草稿   1:直接申请
     * @param flowId   流程管理中心ID
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(WorkflowBaseEntity workflowBaseEntity, FlowCarApprovalEntity flowCarApprovalEntity, String optType, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if(!workflowBaseService.validWorkflowBase(workflowBaseEntity, j).isSuccess()) {
                return j;
            }

            if(null == flowCarApprovalEntity.getUserName()) {
                j.setSuccess(false);
                j.setMsg("乘车人为空");
                return j;
            }

            if(null == flowCarApprovalEntity.getPerson()) {
                j.setSuccess(false);
                j.setMsg("人数为空");
                return j;
            }

            if(null == flowCarApprovalEntity.getDestination()) {
                j.setSuccess(false);
                j.setMsg("目的地不能为空");
                return j;
            }

            if(null == flowCarApprovalEntity.getUseTime()) {
                j.setSuccess(false);
                j.setMsg("使用时间不能为空");
                return j;
            }


            if(StringUtils.isBlank(flowCarApprovalEntity.getReason())) {
                j.setSuccess(false);
                j.setMsg("事由不能为空");
                return j;
            }

            WorkflowMngEntity workflow = workflowMngService.getById(workflowBaseEntity.getWorkflowId());
            if(null == workflow) {
                j.setSuccess(false);
                j.setMsg("参数错误，系统中没有该流程");
                return j;
            }

            if(StringUtils.equals(optType, "1")) {
                //直接申请流程
                if(StringUtils.isBlank(workflowBaseEntity.getId())) {
                    workflowBaseEntity.setApplyDate(DateUtils.getDate());
                    workflowBaseEntity.setApplyName(ShiroUtils.getSessionUser().getRealName());
                    workflowBaseEntity.setUserName(ShiroUtils.getSessionUserName());
                    workflowBaseEntity.setCategoryId(workflow.getCategoryId());
                    workflowBaseEntity.setWorkflowId(workflow.getId());
                    workflowBaseEntity.setWorkFlowName(workflow.getName());
                    workflowBaseEntity.setStatus("1"); //草稿状态 0：草稿 1： 已申请  2： 审批中 3： 已完成 4： 已归档
                    //保存业务数据
                    flowCarApprovalService.saveNewCar(workflowBaseEntity, flowCarApprovalEntity);

                    //启动流程
                    //赋值流程变量
                    Map<String, Object> variables = new HashMap<String, Object>();
                    workflowService.startProcessInstanceByKey(workflow.getProcessKey(), workflowBaseEntity.getId(), true, workflowBaseEntity.getUserName(), variables);
                }else {
                    WorkflowBaseEntity base = workflowBaseService.getById(workflowBaseEntity.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(workflowBaseEntity, base);

                    FlowCarApprovalEntity biz = flowCarApprovalService.getById(base.getBusinessId());
                    MyBeanUtils.copyBeanNotNull2Bean(flowCarApprovalEntity, biz);
                    //已申请
                    base.setStatus("1");
                    flowCarApprovalService.saveUpdate(base, biz);

                    //启动流程
                    //赋值流程变量
                    Map<String, Object> variables = new HashMap<String, Object>();
                    workflowService.startProcessInstanceByKey(workflow.getProcessKey(), biz.getId(), true, base.getUserName(), variables);

                }

            }else {
                //保存草稿
                //新增
                if(StringUtils.isEmpty(workflowBaseEntity.getId())) {
                    workflowBaseEntity.setApplyDate(DateUtils.getDate());
                    workflowBaseEntity.setApplyName(ShiroUtils.getSessionUser().getRealName());
                    workflowBaseEntity.setUserName(ShiroUtils.getSessionUserName());
                    workflowBaseEntity.setCategoryId(workflow.getCategoryId());
                    workflowBaseEntity.setWorkflowId(workflow.getId());
                    workflowBaseEntity.setWorkFlowName(workflow.getName());
                    workflowBaseEntity.setStatus("0"); //草稿状态 0：草稿 1： 已申请  2： 审批中 3： 已完成 4： 已归档

                    flowCarApprovalService.saveNewCar(workflowBaseEntity, flowCarApprovalEntity);
                }else {
                    WorkflowBaseEntity base = workflowBaseService.getById(workflowBaseEntity.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(workflowBaseEntity, base);

                    FlowCarApprovalEntity biz = flowCarApprovalService.getById(base.getBusinessId());
                    MyBeanUtils.copyBeanNotNull2Bean(flowCarApprovalEntity, biz);

                    flowCarApprovalService.saveUpdate(base, biz);
                }
            }


        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg("申请流程保存失败，错误信息:" + e.getMessage());
            log.error("申请用车审批流程保存失败，错误信息:{}", e);
        }

        return j;
    }

    /**
     *
     * @description
     *  	删除业务数据
     * @return AjaxJson
     * @author xfzhang
     * @time 2020年4月23日 下午1:14:48
     */
    @RequestMapping("/del")
    @ResponseBody
    public AjaxJson del(String businessId, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if(StringUtils.isNotBlank(businessId)) {
                flowCarApprovalService.removeById(businessId);
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg("删除失败，错误信息:" + e.getMessage());
            log.error("删除流程业务表失败，错误信息:{}", e);
        }

        return j;
    }

    /**
     * 直接办理任务
     * @param baseActivitiEntity
     * @param request
     * @return
     * @throws ClassNotFoundException
     */
    @RequestMapping("/approve")
    public ModelAndView approve(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request) throws ClassNotFoundException {
        ModelAndView view = new ModelAndView("");

        if(StringUtils.isNotEmpty(workflowBaseEntity.getId())) {

            workflowBaseEntity = workflowBaseService.getById(workflowBaseEntity.getId());

            //根据主表中的流程ID，查询流程中心的流程配置
            WorkflowMngEntity workflow = workflowMngService.getById(workflowBaseEntity.getWorkflowId());

            if(null != workflow) {
                //查询表单
                WorkflowFormEntity form = workflowFormService.getById(workflow.getFormId());
                //系统表单
                if(StringUtils.equals("0", form.getType())) {
                    view = new ModelAndView("redirect:" + form.getPath() + "?formId=" + form.getId() + "&type=2" + "&workflowId=" + workflow.getId() + "&id=" + workflowBaseEntity.getId());
                    return view;
                }
            }
        }
        return view;
    }
}
