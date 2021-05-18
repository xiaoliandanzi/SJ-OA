package com.active4j.hr.activiti.biz.controller;

import com.active4j.hr.activiti.biz.entity.FlowOfficalSealApprovalEntity;
import com.active4j.hr.activiti.biz.entity.FlowTmpCardApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowOfficalSealApprovalService;
import com.active4j.hr.activiti.biz.service.FlowTmpCardApprovalService;
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
import com.active4j.hr.item.entity.RequisitionedItemEntity;
import com.active4j.hr.item.service.RequisitionedItemService;
import com.active4j.hr.officalSeal.entity.OaOfficalSealEntity;
import com.active4j.hr.officalSeal.service.OaOfficalSealService;
import com.active4j.hr.system.model.SysUserModel;
import com.active4j.hr.system.service.SysRoleService;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.system.util.MessageUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2021/01/07/23:50
 * @Description:
 */
@Controller
@RequestMapping("flow/biz/tmpcard")
@Slf4j
public class FlowTmpCardApprovalController extends BaseController{
    @Autowired
    private WorkflowBaseService workflowBaseService;

    @Autowired
    private WorkflowMngService workflowMngService;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private WorkflowFormService workflowFormService;

    @Autowired
    private RequisitionedItemService requisitionedItemService;

    @Autowired
    private FlowTmpCardApprovalService flowTmpCardApprovalService;

    @Autowired
    private SysRoleService roleService;


    /**
     * 跳转到表单页面
     * @param request
     * @param id  流程中心的ID
     * @return
     */
    @RequestMapping("/go")
    public ModelAndView go(String formId, String type, String workflowId, String id, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("flow/item/tmpcarapply");


        //获取当前用户id
        String userId = ShiroUtils.getSessionUserId();
        //获取当前用户个人资料
        SysUserModel user = sysUserService.getInfoByUserId(userId).get(0);

        //查询正常餐卡
        List<RequisitionedItemEntity> lstItems = requisitionedItemService.findTmpCard();
        view.addObject("lstItems", lstItems);

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
            view = new ModelAndView("flow/item/tmpcarapply");
        }else if(StringUtils.equals("1", type)) {
            view = new ModelAndView("flow/item/tmpcardapplyshow");

            //根据businessKey查询任务list
            String currentName = ShiroUtils.getSessionUserName();

            //查看历史审批意见
            List<Comment> lstComments =  workflowService.findCommentsListByBusinessKey(id);
            List<Map> comments = new ArrayList<>();
            //Map map = new HashMap<>();
            for (Comment lstComment : lstComments) {
                Map map = new HashMap<>();
                map.put("id",lstComment.getId());
                map.put("userId",sysUserService.getUserByUseName(lstComment.getUserId()).getRealName());
                map.put("time",lstComment.getTime());
                map.put("taskId",lstComment.getTaskId());
                map.put("processInstanceId",lstComment.getProcessInstanceId());
                map.put("type",lstComment.getType());
                map.put("fullMessage",lstComment.getFullMessage());
                comments.add(map);
            }
            view.addObject("lstComments", comments);
            view.addObject("currentName", currentName);
            view.addObject("show", "0");

        }else if(StringUtils.equals("2", type)) {
            view = new ModelAndView("item/itemTmpCardApprove");

            //根据businessKey查询任务list
            String currentName = ShiroUtils.getSessionUserName();
            List<Task> lstTasks = workflowService.findTaskListByBusinessKey(id, currentName);
            view.addObject("lstTasks", lstTasks);
            view.addObject("action", "flow/biz/tmpcard/doApprove");
        }else if(StringUtils.equals("3", type)) {
            view = new ModelAndView("flow/item/tmpcardapplyshow");

            //根据businessKey查询任务list
            String currentName =ShiroUtils.getSessionUserName();
            List<Task> lstTasks = workflowService.findTaskListByBusinessKey(id, currentName);
            view.addObject("lstTasks", lstTasks);

            //查看历史审批意见
            List<Comment> lstComments =  workflowService.findCommentsListByBusinessKey(id);
            List<Map> comments = new ArrayList<>();
            //Map map = new HashMap<>();
            for (Comment lstComment : lstComments) {
                Map map = new HashMap<>();
                map.put("id",lstComment.getId());
                map.put("userId",sysUserService.getUserByUseName(lstComment.getUserId()).getRealName());
                map.put("time",lstComment.getTime());
                map.put("taskId",lstComment.getTaskId());
                map.put("processInstanceId",lstComment.getProcessInstanceId());
                map.put("type",lstComment.getType());
                map.put("fullMessage",lstComment.getFullMessage());
                comments.add(map);
            }
            view.addObject("lstComments", comments);
            view.addObject("currentName", currentName);
            view.addObject("show", "1");
            view.addObject("action", "flow/biz/tmpcard/doApprove");
        }


        //业务ID
        if(StringUtils.isNotEmpty(id)) {
            WorkflowBaseEntity base = workflowBaseService.getById(id);
            view.addObject("base", base);

            FlowTmpCardApprovalEntity biz = flowTmpCardApprovalService.getById(base.getBusinessId());
            view.addObject("biz", biz);
        } else {
            FlowTmpCardApprovalEntity biz = new FlowTmpCardApprovalEntity();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
            WorkflowBaseEntity base = new WorkflowBaseEntity();
            base.setProjectNo(String.format("%s-%s", user.getUserName(), DateUtils.date2Str(DateUtils.getNow(), sdf)));
            base.setName("物品借用申请");
            view.addObject("base", base);

            biz.setUserName(user.getRealName());
            biz.setDepartmentName(user.getDeptName());
            view.addObject("biz", biz);
        }

        view.addObject("dept", user.getDeptName());
        view.addObject("userName", user.getRealName());

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
//                j.setMsg("审批意见不能为空");
//                j.setSuccess(false);
//                return j;
                if ("N".equals(result)){
                    comment = "驳回";
                }else{
                    comment = "同意";
                }
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
            FlowTmpCardApprovalEntity flowTmpCardApprovalEntity = flowTmpCardApprovalService.getById(workflowBaseEntity.getBusinessId());
            flowTmpCardApprovalEntity.setApplyStatus(1);
            flowTmpCardApprovalService.saveOrUpdate(flowTmpCardApprovalEntity);
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
            if (pi == null) {
                // 更新请假单表的状态从2变成3（审核中-->审核完成）
                workflowBaseEntity.setStatus("3");
                FlowTmpCardApprovalEntity flowTmpCardApprovalEntity = flowTmpCardApprovalService.getById(workflowBaseEntity.getBusinessId());
                flowTmpCardApprovalEntity.setApplyStatus(1);
                flowTmpCardApprovalService.saveOrUpdate(flowTmpCardApprovalEntity);
            } else {
                workflowBaseEntity.setStatus("2");
                FlowTmpCardApprovalEntity flowTmpCardApprovalEntity = flowTmpCardApprovalService.getById(workflowBaseEntity.getBusinessId());
                flowTmpCardApprovalEntity.setApplyStatus(0);
                flowTmpCardApprovalService.saveOrUpdate(flowTmpCardApprovalEntity);
            }
            workflowBaseService.saveOrUpdate(workflowBaseEntity);
            log.info("流程:" + workflowBaseEntity.getName() + "完成审批，审批任务ID:" + taskId + "， 审批状态:" + workflowBaseEntity.getStatus());
        }
    }

    /**
     * 保存方法
     * @param flowTmpCardApprovalEntity
     * @param optType  0 : 保存草稿   1:直接申请
     * @param flowId   流程管理中心ID
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(WorkflowBaseEntity workflowBaseEntity, FlowTmpCardApprovalEntity flowTmpCardApprovalEntity, String optType, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if(!workflowBaseService.validWorkflowBase(workflowBaseEntity, j).isSuccess()) {
                return j;
            }

            //获取当前用户id
            String userId = ShiroUtils.getSessionUserId();
            //获取当前用户个人资料
            SysUserModel user = sysUserService.getInfoByUserId(userId).get(0);
            String departMentname = user.getDeptName();

            workflowBaseEntity.setLevel("0");

            WorkflowMngEntity workflow = workflowMngService.getById(workflowBaseEntity.getWorkflowId());
            if(null == workflow) {
                j.setSuccess(false);
                j.setMsg("参数错误，系统中没有该流程");
                return j;
            }

            if(null == flowTmpCardApprovalEntity.getJsonData()){
                j.setSuccess(false);
                j.setMsg("借用物品不能为空");
                return j;
            }

            if(null == flowTmpCardApprovalEntity.getDepartmentName()) {
                j.setSuccess(false);
                j.setMsg("借用科室不能为空");
                return j;
            }

            if(null == flowTmpCardApprovalEntity.getUseDay()) {
                j.setSuccess(false);
                j.setMsg("使用日期不能为空");
                return j;
            }

            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = df.format(new Date());
            Date now = df.parse(currentDate);
            if(flowTmpCardApprovalEntity.getUseDay().compareTo(now) == -1){
                j.setSuccess(false);
                j.setMsg("申请使用日期不能在当前日期之前");
                return j;
            }

            if(null == flowTmpCardApprovalEntity.getReason()) {
                j.setSuccess(false);
                j.setMsg("借用事由不能为空");
                return j;
            }

            if(StringUtils.equals(optType, "1")) {
                //直接申请流程
                if(StringUtils.isBlank(workflowBaseEntity.getId())) {
                    workflowBaseEntity.setApplyDate(DateUtils.getDate());
                    workflowBaseEntity.setApplyName(ShiroUtils.getSessionUser().getRealName());
                    workflowBaseEntity.setApplyerDepart(flowTmpCardApprovalEntity.getDepartmentName());
                    workflowBaseEntity.setUserName(ShiroUtils.getSessionUserName());
                    workflowBaseEntity.setCategoryId(workflow.getCategoryId());
                    workflowBaseEntity.setWorkflowId(workflow.getId());
                    workflowBaseEntity.setWorkFlowName(workflow.getName());
                    workflowBaseEntity.setStatus("1"); //草稿状态 0：草稿 1： 已申请  2： 审批中 3： 已完成 4： 已归档
                    //保存业务数据
                    flowTmpCardApprovalService.saveNewTmpCard(workflowBaseEntity, flowTmpCardApprovalEntity);

                    //==============减去库存==============
                    String json_data = flowTmpCardApprovalEntity.getJsonData();
                    JSONArray array = JSON.parseArray(json_data);
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject jo = array.getJSONObject(i);
                        String cardName = jo.getString("cardName");
                        int quantity = jo.getInteger("quantity");
                        List<RequisitionedItemEntity> stockEntity = requisitionedItemService.getItemByname(cardName);
                        if (stockEntity.size() != 1) {
                            j.setSuccess(false);
                            j.setMsg("库存多项物品冲突");
                            return j;
                        }
                        RequisitionedItemEntity entity = stockEntity.get(0);
                        if (quantity <= 0) {
                            j.setSuccess(false);
                            j.setMsg(cardName+" 借用数量须大于0");
                            return j;
                        }
                        if (quantity > entity.getQuantity()) {
                            j.setSuccess(false);
                            j.setMsg(cardName+" 数量不足，剩余" + entity.getQuantity());
                            return j;
                        }
                        entity.setQuantity(entity.getQuantity() - quantity);
                        //低于阈值，修改状态
                        if(entity.getQuantity() <= Integer.parseInt(entity.getMinQuantity()) && Integer.parseInt(entity.getStatus()) == 0){
                            entity.setStatus("1");
                        }
                        requisitionedItemService.saveOrUpdate(entity);
                    }


//                    List<RequisitionedItemEntity> stockEntity = requisitionedItemService.getItemByname(flowTmpCardApprovalEntity.getCardName());
//                    if (stockEntity.size() != 1) {
//                        j.setSuccess(false);
//                        j.setMsg("库存多项物品冲突");
//                        return j;
//                    }
//                    RequisitionedItemEntity entity = stockEntity.get(0);
//                    if (quantity > entity.getQuantity()) {
//                        j.setSuccess(false);
//                        j.setMsg("库存不足，剩余" + entity.getQuantity());
//                        return j;
//                    }
//                    entity.setQuantity(entity.getQuantity() - quantity);
//                    //低于阈值，修改状态
//                    if(entity.getQuantity() <= Integer.parseInt(entity.getMinQuantity()) && Integer.parseInt(entity.getStatus()) == 0){
//                        entity.setStatus("1");
//                    }

                    //============================

                    //启动流程
                    //赋值流程变量
                    Map<String, Object> variables = new HashMap<String, Object>();
                    workflowService.startProcessInstanceByKey(workflow.getProcessKey(), workflowBaseEntity.getId(), true, workflowBaseEntity.getUserName(), variables);
                }else {
                    WorkflowBaseEntity base = workflowBaseService.getById(workflowBaseEntity.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(workflowBaseEntity, base);

                    FlowTmpCardApprovalEntity biz = flowTmpCardApprovalService.getById(base.getBusinessId());
                    MyBeanUtils.copyBeanNotNull2Bean(flowTmpCardApprovalService, biz);
                    //已申请
                    base.setStatus("1");
                    flowTmpCardApprovalService.saveUpdate(base, biz);

                    //==============减去库存==============
                    String json_data = flowTmpCardApprovalEntity.getJsonData();
                    JSONArray array = JSON.parseArray(json_data);
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject jo = array.getJSONObject(i);
                        String cardName = jo.getString("cardName");
                        int quantity = jo.getInteger("quantity");
                        List<RequisitionedItemEntity> stockEntity = requisitionedItemService.getItemByname(cardName);
                        if (stockEntity.size() != 1) {
                            j.setSuccess(false);
                            j.setMsg("库存多项物品冲突");
                            return j;
                        }
                        RequisitionedItemEntity entity = stockEntity.get(0);
                        if (quantity <= 0) {
                            j.setSuccess(false);
                            j.setMsg(cardName+" 借用数量须大于0");
                            return j;
                        }
                        if (quantity > entity.getQuantity()) {
                            j.setSuccess(false);
                            j.setMsg(cardName+" 数量不足，剩余" + entity.getQuantity());
                            return j;
                        }
                        entity.setQuantity(entity.getQuantity() - quantity);
                        //低于阈值，修改状态
                        if(entity.getQuantity() <= Integer.parseInt(entity.getMinQuantity()) && Integer.parseInt(entity.getStatus()) == 0){
                            MessageUtils.SendSysMessage(sysUserService.getUserByUseName(roleService.findUserByRoleName("物品管理员").get(0).getUserName()).getId(),String.format("%s仅剩%s个，已低于低量预警线%s个，请及时补充",entity.getName(),entity.getQuantity(),entity.getMinQuantity()));
                            entity.setStatus("1");
                        }
                        requisitionedItemService.saveOrUpdate(entity);
                    }

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
                    workflowBaseEntity.setApplyerDepart(flowTmpCardApprovalEntity.getDepartmentName());
                    workflowBaseEntity.setUserName(ShiroUtils.getSessionUserName());
                    workflowBaseEntity.setCategoryId(workflow.getCategoryId());
                    workflowBaseEntity.setWorkflowId(workflow.getId());
                    workflowBaseEntity.setWorkFlowName(workflow.getName());
                    workflowBaseEntity.setStatus("0"); //草稿状态 0：草稿 1： 已申请  2： 审批中 3： 已完成 4： 已归档

                    flowTmpCardApprovalService.saveNewTmpCard(workflowBaseEntity, flowTmpCardApprovalEntity);
                }else {
                    WorkflowBaseEntity base = workflowBaseService.getById(workflowBaseEntity.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(workflowBaseEntity, base);

                    FlowTmpCardApprovalEntity biz = flowTmpCardApprovalService.getById(base.getBusinessId());
                    MyBeanUtils.copyBeanNotNull2Bean(flowTmpCardApprovalEntity, biz);

                    flowTmpCardApprovalService.saveUpdate(base, biz);
                }
            }


        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg("申请流程保存失败，错误信息:" + e.getMessage());
            log.error("申请用章审批流程保存失败，错误信息:{}", e);
        }

        return j;
    }

    /**
     * 被打回 重新提交方法
     *
     */
    @RequestMapping("/reSubmit")
    @ResponseBody
    public AjaxJson reSubmit(WorkflowBaseEntity workflowBaseEntity, FlowTmpCardApprovalEntity flowTmpCardApprovalEntity, String taskId, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(null == flowTmpCardApprovalEntity.getCardName()) {
                j.setSuccess(false);
                j.setMsg("餐卡类型不能为空");
                return j;
            }

            WorkflowBaseEntity base = workflowBaseService.getById(workflowBaseEntity.getId());
            MyBeanUtils.copyBeanNotNull2Bean(workflowBaseEntity, base);

            FlowTmpCardApprovalEntity biz = flowTmpCardApprovalService.getById(base.getBusinessId());
            biz.setDepartmentName(flowTmpCardApprovalEntity.getDepartmentName());
            biz.setCommit(flowTmpCardApprovalEntity.getCommit());
            biz.setDepartmentName(flowTmpCardApprovalEntity.getDepartmentName());
            //已申请
            base.setStatus("1");
            flowTmpCardApprovalService.saveUpdate(base, biz);


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
                flowTmpCardApprovalService.removeById(businessId);
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
