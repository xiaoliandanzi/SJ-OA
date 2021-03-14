package com.active4j.hr.activiti.biz.controller;

import com.active4j.hr.activiti.biz.entity.FlowAssetApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowAssetApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.activiti.util.WorkflowTaskUtil;
import com.active4j.hr.asset.entity.OaAssetStoreEntity;
import com.active4j.hr.asset.service.OaAssetService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.item.entity.RequisitionedItemEntity;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.model.SysUserModel;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysUserService;
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
import org.joda.time.DateTime;
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
 * @date 2021/1/10 下午7:28
 */
@Controller
@RequestMapping("flow/biz/assetapproval")
@Slf4j
public class FlowAssetApprovalController extends BaseController {
    @Autowired
    private WorkflowBaseService workflowBaseService;

    @Autowired
    private WorkflowMngService workflowMngService;

    @Autowired
    private FlowAssetApprovalService flowAssetApprovalService;

    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private OaAssetService oaAssetService;
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 跳转到表单页面
     * @param request
     * @param id  流程中心的ID
     * @return
     */
    @RequestMapping("/go")
    public ModelAndView go(String formId, String type, String workflowId, String id, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("flow/assetapproval/apply");

//        if(!StringUtils.isEmpty(id)){
//            type = "1";
//        }

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
            view = new ModelAndView("flow/assetapproval/apply");
        }else if(StringUtils.equals("1", type)) {
            view = new ModelAndView("flow/assetapproval/applyshow");

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
            view.addObject("action", "flow/biz/assetapproval/doApprove");
        }else if(StringUtils.equals("3", type)) {
            view = new ModelAndView("flow/assetapproval/applyshow");

            //根据businessKey查询任务list
            String currentName =ShiroUtils.getSessionUserName();
            List<Task> lstTasks = workflowService.findTaskListByBusinessKey(id, currentName);
            view.addObject("lstTasks", lstTasks);

            //查看历史审批意见
            List<Comment> lstComments =  workflowService.findCommentsListByBusinessKey(id);
            view.addObject("lstComments", lstComments);
            view.addObject("currentName", currentName);
            view.addObject("show", "1");
            view.addObject("action", "flow/biz/assetapproval/doApprove");
        }


        //业务ID
        if(StringUtils.isNotEmpty(id)) {
            WorkflowBaseEntity base = workflowBaseService.getById(id);
            view.addObject("base", base);

            FlowAssetApprovalEntity biz = flowAssetApprovalService.getById(base.getBusinessId());
            view.addObject("biz", biz);
        } else {
            FlowAssetApprovalEntity biz = new FlowAssetApprovalEntity();
            //获取当前用户id
            String userId = ShiroUtils.getSessionUserId();
            //获取当前用户个人资料
            SysUserModel user = sysUserService.getInfoByUserId(userId).get(0);


            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
            WorkflowBaseEntity base = new WorkflowBaseEntity();
            base.setProjectNo(String.format("%s-%s", user.getUserName(), DateUtils.date2Str(DateUtils.getNow(), sdf)));
            base.setName("资产移交申请");

            view.addObject("base", base);

            view.addObject("base", base);

            biz.setUserName(user.getRealName());
            biz.setDept(user.getDeptName());
            view.addObject("biz", biz);
        }

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
            FlowAssetApprovalEntity flowAssetApprovalEntity = flowAssetApprovalService.getById(workflowBaseEntity.getBusinessId());
            // 流程结束了
            if (pi == null) {
                // 更新请假单表的状态从2变成3（审核中-->审核完成）
                workflowBaseEntity.setStatus("3");
                flowAssetApprovalEntity.setApplyStatus(1);
                flowAssetApprovalService.saveOrUpdate(flowAssetApprovalEntity);
                updateAssetStore(flowAssetApprovalEntity);
            } else {
                workflowBaseEntity.setStatus("2");
                flowAssetApprovalEntity.setApplyStatus(0);
                flowAssetApprovalService.saveOrUpdate(flowAssetApprovalEntity);
            }
            workflowBaseService.saveOrUpdate(workflowBaseEntity);
            log.info("流程:" + workflowBaseEntity.getName() + "完成审批，审批任务ID:" + taskId + "， 审批状态:" + workflowBaseEntity.getStatus());
            WorkflowTaskUtil.sendApprovalMessage(workflowBaseEntity.getCreateName(), task.getAssignee(),
                    workflowBaseEntity.getApplyDate(), workflowBaseEntity.getWorkFlowName());
        }
    }

    private void updateAssetStore(FlowAssetApprovalEntity flowAssetApprovalEntity) {
        try {
            SysUserEntity receiver = sysUserService.getUserByRealName(flowAssetApprovalEntity.getReceiver());
            SysDeptEntity dept = sysDeptService.getById(receiver.getDeptId());
            OaAssetStoreEntity oaAssetStoreEntity = convert2StrotEntity(flowAssetApprovalEntity,dept.getName());
            oaAssetService.saveOrUpdate(oaAssetStoreEntity);
        } catch (Exception ex) {
            log.error("updateAssetStore", ex);
        }
    }

    private OaAssetStoreEntity convert2StrotEntity(FlowAssetApprovalEntity flowAssetApprovalEntity, String dept) {
        OaAssetStoreEntity oaAssetStoreEntity = new OaAssetStoreEntity();
        oaAssetStoreEntity.setDept(dept);
        oaAssetStoreEntity.setAddress(flowAssetApprovalEntity.getAddress());
        oaAssetStoreEntity.setAmount(flowAssetApprovalEntity.getAmount());
        oaAssetStoreEntity.setAssetName(flowAssetApprovalEntity.getAssetName());
        oaAssetStoreEntity.setChangeTime(DateTime.now().toDate());
        oaAssetStoreEntity.setCommit(flowAssetApprovalEntity.getCommit());
        oaAssetStoreEntity.setModel(flowAssetApprovalEntity.getModel());
        oaAssetStoreEntity.setQuantity(flowAssetApprovalEntity.getQuantity());
        oaAssetStoreEntity.setReceiver(flowAssetApprovalEntity.getReceiver());
        return oaAssetStoreEntity;
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
    public AjaxJson save(WorkflowBaseEntity workflowBaseEntity, FlowAssetApprovalEntity flowAssetApprovalEntity,
                         String optType, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            workflowBaseEntity.setLevel("0");

            if(StringUtils.isBlank(workflowBaseEntity.getWorkflowId())) {
                j.setSuccess(false);
                j.setMsg("流程参数不能为空");
                return j;
            }


            if(null == flowAssetApprovalEntity.getAddress()) {
                j.setSuccess(false);
                j.setMsg("移交地址为空");
                return j;
            }

            String json_data = flowAssetApprovalEntity.getJsonData();
            JSONArray array = JSON.parseArray(json_data);
            JSONObject jo = array.getJSONObject(0);
            String assetName = jo.getString("assetName");
            String quantity = jo.getString("quantity");
            String unitPrice = jo.getString("unitPrice");
            String model = jo.getString("model");

            flowAssetApprovalEntity.setAssetName(assetName);
            flowAssetApprovalEntity.setModel(model);


            if(-1 == flowAssetApprovalEntity.getAmount()) {
                j.setSuccess(false);
                j.setMsg("价格为空");
                return j;
            }

            if(null == flowAssetApprovalEntity.getAssetName()) {
                j.setSuccess(false);
                j.setMsg("资产名称为空");
                return j;
            }

//            if(null == flowAssetApprovalEntity.getModel()) {
//                j.setSuccess(false);
//                j.setMsg("资产类型为空");
//                return j;
//            }
//
//            if(null == flowAssetApprovalEntity.getQuantity()) {
//                j.setSuccess(false);
//                j.setMsg("数量不能为空");
//                return j;
//            }




            WorkflowMngEntity workflow = workflowMngService.getById(workflowBaseEntity.getWorkflowId());
            if(null == workflow) {
                j.setSuccess(false);
                j.setMsg("参数错误，系统中没有该流程");
                return j;
            }

            if(StringUtils.equals(optType, "1")) {
                flowAssetApprovalEntity.setApplyStatus(0);
                //直接申请流程
                if(StringUtils.isBlank(workflowBaseEntity.getId())) {
                    workflowBaseEntity.setApplyDate(DateUtils.getDate());
                    workflowBaseEntity.setApplyName(ShiroUtils.getSessionUser().getRealName());
                    workflowBaseEntity.setUserName(ShiroUtils.getSessionUserName());
                    workflowBaseEntity.setCategoryId(workflow.getCategoryId());
                    workflowBaseEntity.setWorkflowId(workflow.getId());
                    workflowBaseEntity.setWorkFlowName(workflow.getName());
                    workflowBaseEntity.setStatus("1"); //草稿状态 0：草稿 1： 已申请  2： 审批中 3： 已完成 4： 已归档
                    workflowBaseEntity.setName("固定资产移交-"+flowAssetApprovalEntity.getAssetName());
                    //保存业务数据
                    flowAssetApprovalService.saveNewAsset(workflowBaseEntity, flowAssetApprovalEntity);

                    //启动流程
                    //赋值流程变量
                    Map<String, Object> variables = new HashMap<String, Object>();
                    workflowService.startProcessInstanceByKey(workflow.getProcessKey(), workflowBaseEntity.getId(), true, workflowBaseEntity.getUserName(), variables);
                }else {
                    WorkflowBaseEntity base = workflowBaseService.getById(workflowBaseEntity.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(workflowBaseEntity, base);

                    FlowAssetApprovalEntity biz = flowAssetApprovalService.getById(base.getBusinessId());
                    MyBeanUtils.copyBeanNotNull2Bean(flowAssetApprovalEntity, biz);
                    //已申请
                    base.setStatus("1");
                    flowAssetApprovalService.saveUpdate(base, biz);

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
                    flowAssetApprovalEntity.setApplyStatus(3); //文件状态 3：草稿中
                    workflowBaseEntity.setName("固定资产移交-"+flowAssetApprovalEntity.getAssetName());
                    flowAssetApprovalService.saveNewAsset(workflowBaseEntity, flowAssetApprovalEntity);
                }else {
                    WorkflowBaseEntity base = workflowBaseService.getById(workflowBaseEntity.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(workflowBaseEntity, base);

                    FlowAssetApprovalEntity biz = flowAssetApprovalService.getById(base.getBusinessId());
                    MyBeanUtils.copyBeanNotNull2Bean(flowAssetApprovalEntity, biz);

                    flowAssetApprovalService.saveUpdate(base, biz);
                }
            }


        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg("申请流程保存失败，错误信息:" + e.getMessage());
            log.error("固定资产移交流程保存失败，错误信息:{}", e);
        }

        return j;
    }

    /**
     * 被打回 重新提交方法
     *
     */
    @RequestMapping("/reSubmit")
    @ResponseBody
    public AjaxJson reSubmit(WorkflowBaseEntity workflowBaseEntity, FlowAssetApprovalEntity flowAssetApprovalEntity, String taskId, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{

            if(null == flowAssetApprovalEntity.getAddress()) {
                j.setSuccess(false);
                j.setMsg("移交地址为空");
                return j;
            }

            if(-1 == flowAssetApprovalEntity.getAmount()) {
                j.setSuccess(false);
                j.setMsg("价格为空");
                return j;
            }

            if(null == flowAssetApprovalEntity.getAssetName()) {
                j.setSuccess(false);
                j.setMsg("资产名称为空");
                return j;
            }

            if(null == flowAssetApprovalEntity.getModel()) {
                j.setSuccess(false);
                j.setMsg("资产类型为空");
                return j;
            }

            if(null == flowAssetApprovalEntity.getQuantity()) {
                j.setSuccess(false);
                j.setMsg("数量不能为空");
                return j;
            }

            WorkflowBaseEntity base = workflowBaseService.getById(workflowBaseEntity.getId());
            MyBeanUtils.copyBeanNotNull2Bean(workflowBaseEntity, base);

            FlowAssetApprovalEntity biz = flowAssetApprovalService.getById(base.getBusinessId());
            biz.setCommit(flowAssetApprovalEntity.getCommit());
            //已申请
            base.setStatus("1");
            flowAssetApprovalService.saveUpdate(base, biz);


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
                flowAssetApprovalService.removeById(businessId);
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg("删除失败，错误信息:" + e.getMessage());
            log.error("删除流程业务表失败，错误信息:{}", e);
        }

        return j;
    }

}
