package com.active4j.hr.activiti.biz.controller;

import com.active4j.hr.activiti.biz.entity.FlowMessageApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowMessageApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.activiti.util.WorkflowTaskUtil;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.system.model.SysUserModel;
import com.active4j.hr.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/22 下午10:11
 */
@Controller
@RequestMapping("flow/biz/messageapproval")
@Slf4j
public class FlowMessageApprovalController extends BaseController {
    @Autowired
    private WorkflowBaseService workflowBaseService;

    @Autowired
    private WorkflowMngService workflowMngService;

    @Autowired
    private FlowMessageApprovalService flowMessageApprovalService;

    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    /**
     * 跳转到表单页面
     * @param request
     * @param id  流程中心的ID
     * @return
     */
    @RequestMapping("/go")
    public ModelAndView go(String formId, String type, String workflowId, String id, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("flow/messageapproval/apply");

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
            view = new ModelAndView("flow/messageapproval/apply");
        }else if(StringUtils.equals("1", type)) {
            view = new ModelAndView("flow/messageapproval/applyshow");

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
            view.addObject("action", "flow/biz/messageapproval/doApprove");
        }else if(StringUtils.equals("3", type)) {
            view = new ModelAndView("flow/messageapproval/applyshow");

            //根据businessKey查询任务list
            String currentName =ShiroUtils.getSessionUserName();
            List<Task> lstTasks = workflowService.findTaskListByBusinessKey(id, currentName);
            view.addObject("lstTasks", lstTasks);

            //查看历史审批意见
            List<Comment> lstComments =  workflowService.findCommentsListByBusinessKey(id);
            view.addObject("lstComments", lstComments);
            view.addObject("currentName", currentName);
            view.addObject("show", "1");
            view.addObject("action", "flow/biz/messageapproval/doApprove");
        }


        //业务ID
        if(StringUtils.isNotEmpty(id)) {
            WorkflowBaseEntity base = workflowBaseService.getById(id);
            view.addObject("base", base);

            FlowMessageApprovalEntity biz = flowMessageApprovalService.getById(base.getBusinessId());
            view.addObject("biz", biz);
        } else {

            //获取当前用户id
            String userId = ShiroUtils.getSessionUserId();
            //获取当前用户个人资料
            SysUserModel user = sysUserService.getInfoByUserId(userId).get(0);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
            WorkflowBaseEntity base = new WorkflowBaseEntity();
            base.setProjectNo(String.format("%s-%s", user.getUserName(), DateUtils.date2Str(DateUtils.getNow(), sdf)));
            base.setName("信息发布");
            view.addObject("base", base);


         /*   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
            WorkflowBaseEntity base = new WorkflowBaseEntity();
            base.setProjectNo(String.format("%s-%s", user.getUserName(), DateUtils.date2Str(DateUtils.getNow(), sdf)));
            base.setName("信息发布");
            view.addObject("base", base);*/

            FlowMessageApprovalEntity biz = new FlowMessageApprovalEntity();
            biz.setPublicMan(user.getRealName());
            biz.setDept(user.getDeptName());
            view.addObject("biz", biz);
        }

        /*//查询数据字典
        List<SysDicValueEntity> types = SystemUtils.getDictionaryLst(SysConstant.DIC_MESSAGE_TYPE);
        request.setAttribute("types", types);*/

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
    public AjaxJson doApprove(String id, String taskId, String comment, String result, HttpServletRequest request){
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
            //workflowService.saveSubmitTask(taskId, id, comment, map);

            if(StringUtils.equals("N", result)) {
                map.put("flag", "N");
                workflowService.saveBackTask(taskId, id, comment, map);
            }else if(StringUtils.equals("Y", result)){
                map.put("flag", "Y");

                saveSubmitTask(taskId, id, comment, map);
            }else {
                saveSubmitTask(taskId, id, comment, map);
            }

        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.ERROR_MSG);
            log.error("审批报错，错误信息:{}", e);
        }

        return j;
    }

    private void saveSubmitTask(String taskId, String businessKey, String comments, Map<String, Object> variables) {
        // 使用任务ID，查询任务对象，获取流程流程实例ID
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)// 使用任务ID查询
                .singleResult();

        // 获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();

        /**
         * 注意：添加批注的时候，由于Activiti底层代码是使用： String userId =
         * Authentication.getAuthenticatedUserId(); CommentEntity comment = new
         * CommentEntity(); comment.setUserId(userId);
         * 所有需要从Session中获取当前登录人，作为该任务的办理人（审核人），对应act_hi_comment表中的User_ID的字段，不过不添加审核人，该字段为null
         * 所以要求，添加配置执行使用Authentication.setAuthenticatedUserId();添加当前任务的审核人
         */
        Authentication.setAuthenticatedUserId(ShiroUtils.getSessionUserName());
        taskService.addComment(taskId, processInstanceId, comments);

        // 使用任务ID，完成当前人的个人任务，同时流程变量
        taskService.complete(taskId, variables, true);

        /**
         * 在完成任务之后，判断流程是否结束 如果流程结束了，更新请假单表的状态从1变成2（审核中-->审核完成）
         */
        WorkflowBaseEntity workflowBaseEntity = workflowBaseService.getById(businessKey);
        if (null != workflowBaseEntity) {

            ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                    .processInstanceId(processInstanceId)// 使用流程实例ID查询
                    .singleResult();
            // 流程结束了
            FlowMessageApprovalEntity flowMessageApprovalEntity = flowMessageApprovalService.getById(workflowBaseEntity.getBusinessId());
            if (pi == null) {
                // 更新请假单表的状态从2变成3（审核中-->审核完成）
                workflowBaseEntity.setStatus("3");
                flowMessageApprovalEntity.setPublicTime(DateUtils.getDate());
                flowMessageApprovalEntity.setApplyStatus(1);
                flowMessageApprovalService.saveOrUpdate(flowMessageApprovalEntity);
            } else {
                workflowBaseEntity.setStatus("2");
                flowMessageApprovalEntity.setApplyStatus(0);
                flowMessageApprovalService.saveOrUpdate(flowMessageApprovalEntity);
            }
            workflowBaseService.saveOrUpdate(workflowBaseEntity);
            log.info("流程:" + workflowBaseEntity.getName() + "完成审批，审批任务ID:" + taskId + "， 审批状态:" + workflowBaseEntity.getStatus());
            WorkflowTaskUtil.sendApprovalMessage(workflowBaseEntity.getCreateName(), task.getAssignee(),
                    workflowBaseEntity.getApplyDate(), workflowBaseEntity.getWorkFlowName());
        }
    }

    /**
     * 保存方法
     * @param
     * @param optType  0 : 保存草稿   1:直接申请
     * @param
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(WorkflowBaseEntity workflowBaseEntity, FlowMessageApprovalEntity flowMessageApprovalEntity,
                         String optType, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
//            if(!workflowBaseService.validWorkflowBase(workflowBaseEntity, j).isSuccess()) {
//                return j;
//            }

            workflowBaseEntity.setLevel("0");
            if(null == flowMessageApprovalEntity.getTitle()) {
                j.setSuccess(false);
                j.setMsg("标题为空");
                return j;
            }

            if(null == flowMessageApprovalEntity.getContent()) {
                j.setSuccess(false);
                j.setMsg("内容为空");
                return j;
            }

            if(flowMessageApprovalEntity.getContent().length() > 1999){
                j.setSuccess(false);
                j.setMsg("内容过长，请以附件上传");
                return j;
            }

            if(null == flowMessageApprovalEntity.getMessageType()) {
                j.setSuccess(false);
                j.setMsg("信息类型不能为空");
                return j;
            }

            if(null == flowMessageApprovalEntity.getAttachment()) {
                j.setSuccess(false);
                j.setMsg("附件不能为空!");
                return j;
            }

            WorkflowMngEntity workflow = workflowMngService.getById(workflowBaseEntity.getWorkflowId());
            if(null == workflow) {
                j.setSuccess(false);
                j.setMsg("参数错误，系统中没有该流程");
                return j;
            }

            if(StringUtils.equals(optType, "1")) {
                flowMessageApprovalEntity.setApplyStatus(0);
                //直接申请流程
                if(StringUtils.isBlank(workflowBaseEntity.getId())) {
                    workflowBaseEntity.setApplyDate(DateUtils.getDate());
                    workflowBaseEntity.setApplyName(ShiroUtils.getSessionUser().getRealName());
                    workflowBaseEntity.setUserName(ShiroUtils.getSessionUserName());
                    workflowBaseEntity.setCategoryId(workflow.getCategoryId());
                    workflowBaseEntity.setWorkflowId(workflow.getId());
                    workflowBaseEntity.setWorkFlowName(workflow.getName());
                    workflowBaseEntity.setStatus("1"); //草稿状态 0：草稿 1： 已申请  2： 审批中 3： 已完成 4： 已归档
                    workflowBaseEntity.setName("信息发布-"+flowMessageApprovalEntity.getTitle());
                    //保存业务数据
                    flowMessageApprovalService.saveNewMessage(workflowBaseEntity, flowMessageApprovalEntity);

                    //启动流程
                    //赋值流程变量
                    Map<String, Object> variables = new HashMap<String, Object>();
                    workflowService.startProcessInstanceByKey(workflow.getProcessKey(), workflowBaseEntity.getId(), true, workflowBaseEntity.getUserName(), variables);
                }else {
                    WorkflowBaseEntity base = workflowBaseService.getById(workflowBaseEntity.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(workflowBaseEntity, base);

                    FlowMessageApprovalEntity biz = flowMessageApprovalService.getById(base.getBusinessId());
                    MyBeanUtils.copyBeanNotNull2Bean(flowMessageApprovalEntity, biz);
                    //已申请
                    base.setStatus("1");
                    flowMessageApprovalService.saveUpdate(base, biz);

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
                    flowMessageApprovalEntity.setApplyStatus(3); //文件状态 3：草稿中
                    workflowBaseEntity.setName("信息发布-"+flowMessageApprovalEntity.getTitle());
                    flowMessageApprovalService.saveNewMessage(workflowBaseEntity, flowMessageApprovalEntity);
                }else {
                    WorkflowBaseEntity base = workflowBaseService.getById(workflowBaseEntity.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(workflowBaseEntity, base);

                    FlowMessageApprovalEntity biz = flowMessageApprovalService.getById(base.getBusinessId());
                    MyBeanUtils.copyBeanNotNull2Bean(flowMessageApprovalEntity, biz);

                    flowMessageApprovalService.saveUpdate(base, biz);
                }
            }


        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg("申请流程保存失败，错误信息:" + e.getMessage());
            log.error("信息审批流程保存失败，错误信息:{}", e);
        }

        return j;
    }

    /**
     * 被打回 重新提交方法
     *
     */
    @RequestMapping("/reSubmit")
    @ResponseBody
    public AjaxJson reSubmit(WorkflowBaseEntity workflowBaseEntity, FlowMessageApprovalEntity flowMessageApprovalEntity, String taskId, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{

            if(null == flowMessageApprovalEntity.getTitle()) {
                j.setSuccess(false);
                j.setMsg("标题为空");
                return j;
            }

            if(null == flowMessageApprovalEntity.getContent()) {
                j.setSuccess(false);
                j.setMsg("内容为空");
                return j;
            }

            if(null == flowMessageApprovalEntity.getMessageType()) {
                j.setSuccess(false);
                j.setMsg("信息类型不能为空");
                return j;
            }

            if(null == flowMessageApprovalEntity.getAttachment()) {
                j.setSuccess(false);
                j.setMsg("附件不能为空!");
                return j;
            }

            WorkflowBaseEntity base = workflowBaseService.getById(workflowBaseEntity.getId());
            MyBeanUtils.copyBeanNotNull2Bean(workflowBaseEntity, base);

            FlowMessageApprovalEntity biz = flowMessageApprovalService.getById(base.getBusinessId());
            biz.setAttachment(flowMessageApprovalEntity.getAttachment());
            biz.setContent(flowMessageApprovalEntity.getContent());
            biz.setTitle(flowMessageApprovalEntity.getTitle());
            //已申请
            base.setStatus("1");
            flowMessageApprovalService.saveUpdate(base, biz);


            Map<String, Object> map = new HashMap<String, Object>();
            workflowService.saveSubmitTask(taskId, base.getId(), "重新提交", map);

        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg("重新提交失败,错误信息:" + e.getMessage());
            log.error("重新提交失败,错误信息:{}", e);
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
                flowMessageApprovalService.removeById(businessId);
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg("删除失败，错误信息:" + e.getMessage());
            log.error("删除流程业务表失败，错误信息:{}", e);
        }

        return j;
    }
}
