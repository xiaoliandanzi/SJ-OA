package com.active4j.hr.activiti.biz.controller;


import com.active4j.hr.activiti.biz.entity.FlowAssetAddEntity;
import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowAssetAddService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.active4j.hr.activiti.service.WorkflowFormService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.activiti.util.WorkflowTaskUtil;
import com.active4j.hr.asset.entity.OaAssetStoreEntity;
import com.active4j.hr.asset.service.OaAssetService;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.model.SysUserModel;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.system.util.SystemUtils;
import com.active4j.hr.topic.entity.OaEditStore;
import com.active4j.hr.topic.service.OaEditStoreService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import org.activiti.engine.task.Comment;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author weizihao
 * @since 2021-03-19
 */
@RestController
@RequestMapping("/flow/biz/assetadd")
@Slf4j
public class FlowAssetAddController {
    @Autowired
    private FlowAssetAddService flowAssetAddService;
    @Autowired
    private WorkflowBaseService workflowBaseService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private OaEditStoreService oaEditStoreService;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private WorkflowMngService workflowMngService;
    @Autowired
    private WorkflowFormService workflowFormService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private OaAssetService oaAssetService;
    /**
     * 跳转到表单页面
     * @param request
     * @param id  流程中心的ID
     * @return
     */
    @RequestMapping("/addgo")
    public ModelAndView go(String formId, String type, String workflowId, String id, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("flow/assetapproval/assetaddapply");

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
            view = new ModelAndView("flow/assetapproval/assetaddapply");
        }else if(StringUtils.equals("1", type)) {
            view = new ModelAndView("flow/assetapproval/assetaddshow");

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
            view = new ModelAndView("flow/assetapproval/applyaddshow");

            //根据businessKey查询任务list
            String currentName =ShiroUtils.getSessionUserName();
            List<Task> lstTasks = workflowService.findTaskListByBusinessKey(id, currentName);
            view.addObject("lstTasks", lstTasks);

            //查看历史审批意见
            List<Comment> lstComments =  workflowService.findCommentsListByBusinessKey(id);
            view.addObject("lstComments", lstComments);
            view.addObject("currentName", currentName);
            view.addObject("show", "1");
            view.addObject("action", "flow/biz/assetadd/doApprove");
        }

        //业务ID
        if(StringUtils.isNotEmpty(id)) {
            WorkflowBaseEntity base = workflowBaseService.getById(id);
            view.addObject("base", base);

            FlowAssetAddEntity biz = flowAssetAddService.getById(base.getBusinessId());
            QueryWrapper<SysDeptEntity> wrapper = new QueryWrapper<>();
            wrapper.select("NAME").eq("ID",biz.getDept());
            List<SysDeptEntity> list = sysDeptService.list(wrapper);
            biz.setDept(list.get(0).getName());
            view.addObject("biz", biz);
            return view;
        }

        //获取当前用户id
        String userId = ShiroUtils.getSessionUserId();
        //获取当前用户个人资料
        SysUserModel user = sysUserService.getInfoByUserId(userId).get(0);
        FlowAssetAddEntity biz = new FlowAssetAddEntity();
        biz.setDept(user.getDeptName());

        view.addObject("biz", biz);

        view.addObject("dept", user.getDeptName());

        view.addObject("workflowId", workflowId);
        return view;
    }

    /**
     * 保存方法
     * @param flowAssetAddEntity
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(WorkflowBaseEntity workflowBaseEntity, FlowAssetAddEntity flowAssetAddEntity, String optType, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if(!workflowBaseService.validWorkflowBase(workflowBaseEntity, j).isSuccess()) {
                return j;
            }
//            String jsonData = oaAssetStoreEntity.getJsonData();
//            JSONArray array;
//            if(StringUtils.isEmpty(jsonData)){
//                array = new JSONArray();
//            }else{
//               array = JSON.parseArray(jsonData);
//            }
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
//            array.add(sdf.format(new Date())+"，"+ShiroUtils.getSessionUser().getRealName()+"修改");
//
//            oaAssetStoreEntity.setJsonData(array.toJSONString());
//
            if (StringUtils.isEmpty(flowAssetAddEntity.getDept())) {
                j.setSuccess(false);
                j.setMsg("科室不能为空!");
                return j;
            }

            if (StringUtils.isEmpty(flowAssetAddEntity.getAssetName())) {
                j.setSuccess(false);
                j.setMsg("资产名称不能为空!");
                return j;
            }

            if (null == flowAssetAddEntity.getTime()) {
                j.setSuccess(false);
                j.setMsg("入库时间不能为空!");
                return j;
            }

            if (StringUtils.isEmpty(flowAssetAddEntity.getAddress())) {
                j.setSuccess(false);
                j.setMsg("库存地址不能为空!");
                return j;
            }

            if ("null".equals(flowAssetAddEntity.getQuantity())) {
                j.setSuccess(false);
                j.setMsg("物品数量不能为空!");
                return j;
            }
            if ("null".equals(flowAssetAddEntity.getAmount())) {
                j.setSuccess(false);
                j.setMsg("价格不能为空!");
                return j;
            }
            if (StringUtils.isEmpty(flowAssetAddEntity.getModel())) {
                j.setSuccess(false);
                j.setMsg("规格/型号不能为空!");
                return j;
            }
            if (StringUtils.isEmpty(flowAssetAddEntity.getApplication())) {
                j.setSuccess(false);
                j.setMsg("用途不能为空!");
                return j;
            }


            WorkflowMngEntity workflow = workflowMngService.getById(workflowBaseEntity.getWorkflowId());
            if (null == workflow) {
                j.setSuccess(false);
                j.setMsg("参数错误，系统中没有该流程");
                return j;
            }

            if (StringUtils.equals(optType, "1")) {
                //直接申请流程
                if (StringUtils.isBlank(workflowBaseEntity.getId())) {
                    workflowBaseEntity.setApplyDate(DateUtils.getDate());
                    workflowBaseEntity.setApplyName(ShiroUtils.getSessionUser().getRealName());
                    workflowBaseEntity.setUserName(ShiroUtils.getSessionUserName());
                    workflowBaseEntity.setCategoryId(workflow.getCategoryId());
                    workflowBaseEntity.setWorkflowId(workflow.getId());
                    workflowBaseEntity.setWorkFlowName(workflow.getName());
                    workflowBaseEntity.setStatus("1"); //草稿状态 0：草稿 1： 已申请  2： 审批中 3： 已完成 4： 已归档
                    //保存业务数据
                    Map<String, Object> variables = new HashMap<String, Object>();
                    variables.put("deptId",flowAssetAddEntity.getDept());


                    flowAssetAddService.saveNewAsset(workflowBaseEntity, flowAssetAddEntity);

                    //启动流程
                    //赋值流程变量
                    workflowService.startProcessInstanceByKey(workflow.getProcessKey(), workflowBaseEntity.getId(), true, workflowBaseEntity.getUserName(), variables);
                } else {
                    WorkflowBaseEntity base = workflowBaseService.getById(workflowBaseEntity.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(workflowBaseEntity, base);

                    FlowAssetAddEntity biz = flowAssetAddService.getById(base.getBusinessId());
                    MyBeanUtils.copyBeanNotNull2Bean(flowAssetAddEntity, biz);
                    //已申请
                    base.setStatus("1");
                    flowAssetAddService.saveUpdate(base, biz);

                    //启动流程
                    //赋值流程变量
                    Map<String, Object> variables = new HashMap<String, Object>();
                    workflowService.startProcessInstanceByKey(workflow.getProcessKey(), biz.getId(), true, base.getUserName(), variables);

                }

            } else {
                //保存草稿
                //新增
                if (StringUtils.isEmpty(workflowBaseEntity.getId())) {
                    workflowBaseEntity.setApplyDate(DateUtils.getDate());
                    workflowBaseEntity.setApplyName(ShiroUtils.getSessionUser().getRealName());
                    workflowBaseEntity.setUserName(ShiroUtils.getSessionUserName());
                    workflowBaseEntity.setCategoryId(workflow.getCategoryId());
                    workflowBaseEntity.setWorkflowId(workflow.getId());
                    workflowBaseEntity.setWorkFlowName(workflow.getName());
                    workflowBaseEntity.setStatus("0"); //草稿状态 0：草稿 1： 已申请  2： 审批中 3： 已完成 4： 已归档

                    flowAssetAddService.saveNewAsset(workflowBaseEntity, flowAssetAddEntity);
                } else {
                    WorkflowBaseEntity base = workflowBaseService.getById(workflowBaseEntity.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(workflowBaseEntity, base);

                    FlowAssetAddEntity biz = flowAssetAddService.getById(base.getBusinessId());
                    MyBeanUtils.copyBeanNotNull2Bean(flowAssetAddEntity, biz);

                    flowAssetAddService.saveUpdate(base, biz);
                }
            }


        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("申请流程保存失败，错误信息:" + e.getMessage());
            log.error("申请固定资产入库审批流程保存失败，错误信息:{}", e);
        }

        return j;
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
    public AjaxJson doApprove(String id, String taskId, String comment,String result, HttpServletRequest request){
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
//            workflowService.saveSubmitTask(taskId, id, comment, map);
            if(StringUtils.equals("N", result)) {
                map.put("flag", "N");
                workflowService.saveBackTask(taskId, id, comment, map);
            }else if(StringUtils.equals("Y", result)){
                map.put("flag", "Y");

                saveSubmitTask(taskId, id, comment, map);
            }else if (StringUtils.equals("O", result)) {
                map.put("flag", "O");
                saveSubmitOverTask(taskId, id, comment, map);
            }
            else {
                saveSubmitTask(taskId, id, comment, map);
            }

        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.ERROR_MSG);
            log.error("审批报错，错误信息:{}", e);
        }

        return j;
    }
    private void saveSubmitOverTask(String taskId, String businessKey, String comments, Map<String, Object> variables) {
        // 使用任务ID，查询任务对象，获取流程流程实例ID
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)// 使用任务ID查询
                .singleResult();

        // 获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        Authentication.setAuthenticatedUserId(ShiroUtils.getSessionUserName());
        taskService.addComment(taskId, processInstanceId, comments);

        // 使用任务ID，完成当前人的个人任务，同时流程变量
        taskService.complete(taskId, variables, true);
        WorkflowBaseEntity workflowBaseEntity = workflowBaseService.getById(businessKey);
        if (null != workflowBaseEntity){
            workflowBaseEntity.setStatus("3");
            FlowAssetAddEntity flowCarApprovalEntity = flowAssetAddService.getById(workflowBaseEntity.getBusinessId());
            flowAssetAddService.saveOrUpdate(flowCarApprovalEntity);
        }
        workflowBaseService.saveOrUpdate(workflowBaseEntity);
        log.info("流程:" + workflowBaseEntity.getName() + "完成审批，审批任务ID:" + taskId + "， 审批状态:" + workflowBaseEntity.getStatus());
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
        taskService.complete(taskId, variables, true);//就是这

        /**
         * 在完成任务之后，判断流程是否结束 如果流程结束了，更新请假单表的状态从1变成2（审核中-->审核完成）
         */
        WorkflowBaseEntity workflowBaseEntity = workflowBaseService.getById(businessKey);
        if (null != workflowBaseEntity) {

            ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                    .processInstanceId(processInstanceId)// 使用流程实例ID查询
                    .singleResult();
            // 流程结束了
            if (pi == null) {
                // 更新请假单表的状态从2变成3（审核中-->审核完成）
                workflowBaseEntity.setStatus("3");
                FlowAssetAddEntity flowAssetAddEntity = flowAssetAddService.getById(workflowBaseEntity.getBusinessId());
                flowAssetAddService.saveOrUpdate(flowAssetAddEntity);

                /*Date date = new Date();
                String strDateFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);*/
                //添加到固定资产库存
                OaAssetStoreEntity oaAssetStoreEntity = new OaAssetStoreEntity();
                BeanUtils.copyProperties(flowAssetAddEntity,oaAssetStoreEntity);
                String dept = SystemUtils.getDeptNameById(flowAssetAddEntity.getDept());
                oaAssetStoreEntity.setDept(dept);
                oaAssetStoreEntity.setReceiver(flowAssetAddEntity.getCreateName());
                oaAssetStoreEntity.setId(null);
                oaAssetStoreEntity.setChangeTime(new Date());
                oaAssetService.save(oaAssetStoreEntity);

                //同步保存到oa_edit_store表格
                oaAssetStoreEntity.setId(null);
                OaEditStore editStore = new OaEditStore();
                BeanUtils.copyProperties(oaAssetStoreEntity,editStore);
                oaEditStoreService.save(editStore);

                //添加到系统信息
                WorkflowTaskUtil.sendApprovalMessage(workflowBaseEntity.getCreateName(), task.getAssignee(),
                        workflowBaseEntity.getApplyDate(), workflowBaseEntity.getWorkFlowName());
            } else {
                workflowBaseEntity.setStatus("2");
                FlowAssetAddEntity flowAssetAddEntity = flowAssetAddService.getById(workflowBaseEntity.getBusinessId());
                flowAssetAddService.saveOrUpdate(flowAssetAddEntity);
            }
            workflowBaseService.saveOrUpdate(workflowBaseEntity);
            log.info("流程:" + workflowBaseEntity.getName() + "完成审批，审批任务ID:" + taskId + "， 审批状态:" + workflowBaseEntity.getStatus());
        }
    }
}

