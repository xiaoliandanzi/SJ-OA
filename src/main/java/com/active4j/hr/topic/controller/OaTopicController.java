package com.active4j.hr.topic.controller;


import com.active4j.hr.activiti.util.WorkflowConstant;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.util.StringUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.func.upload.entity.UploadAttachmentEntity;
import com.active4j.hr.func.upload.service.UploadAttachmentService;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.entity.SysUserRoleEntity;
import com.active4j.hr.system.model.ActiveUser;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysRoleService;
import com.active4j.hr.system.service.SysUserRoleService;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.system.util.MessageUtils;
import com.active4j.hr.topic.entity.OaTopic;
import com.active4j.hr.topic.service.OaTopicService;
import com.active4j.hr.topic.until.DeptLeaderRole;
import com.active4j.hr.topic.until.OaTopicException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.inject.internal.cglib.proxy.$Callback;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.xpath.functions.FuncUnparsedEntityURI;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 议题前端控制器
 * </p>
 *
 * @author weizihao
 * @since 2020-12-28
 */
@RestController
@RequestMapping(value = "/topic")
@Slf4j
public class OaTopicController extends BaseController {

    @Autowired
    private OaTopicService topicService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysDeptService deptService;

    @Autowired
    private UploadAttachmentService uploadAttachmentService;

    @Autowired
    private OaTopicService oaTopicService;

    /**
     * list视图
     *
     * @return
     */
    @RequestMapping(value = "list")
    public ModelAndView topicList() {
        return new ModelAndView("topic/topiclist");
    }

    @RequestMapping(value = "audit/list")
    public ModelAndView auditTopicList() {
        return new ModelAndView("topic/topiclistaudit");
    }
    @RequestMapping(value = "view/list")
    public ModelAndView viewTopicList() {
        return new ModelAndView("topic/topiclistView");
    }


    /**
     * 表格数据
     *
     * @param oaTopic
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(value = "table")
    public void topicTable(OaTopic oaTopic, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        ActiveUser user = ShiroUtils.getSessionUser();
        if ("".equals(oaTopic.getTopicName())) {
            oaTopic.setTopicName(null);
        }
        //判断 表格显示数据 查询条件
        oaTopic = getSelectTopic(oaTopic, user);
        //纪委与财务 负责人查询数据为两种
        QueryWrapper<OaTopic> queryWrapper = new QueryWrapper<>();
        //名称模糊查询
        if (!StringUtil.isEmpty(oaTopic.getTopicName())) {
            queryWrapper.like("TOPIC_NAME", oaTopic.getTopicName());
            oaTopic.setTopicName(null);
        }
        queryWrapper.setEntity(oaTopic);
        //处理时间查询
        Map<String, String[]> paramsMap = request.getParameterMap();
        if (null != paramsMap) {
            String[] beginValue = paramsMap.get("creatTime_begin");
            if (null != beginValue && beginValue.length > 0) {
                if (StringUtils.isNotEmpty(beginValue[0].trim())) {
                    queryWrapper.ge("CREAT_TIME", beginValue[0].trim());
                }
            }
            String[] endValue = paramsMap.get("creatTime_end");
            if (null != endValue && endValue.length > 0) {
                if (StringUtils.isNotEmpty(endValue[0].trim())) {
                    queryWrapper.le("CREAT_TIME", endValue[0].trim());
                }
            }
        }
        queryWrapper.orderByDesc("CREAT_TIME");
        IPage<OaTopic> page = topicService.page(new Page<OaTopic>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        ResponseUtil.writeJson(response, dataGrid, page);
    }

    /**
     * 表格数据
     *
     * @param oaTopic
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(value = "audittable")
    public void topicAuditTable(OaTopic oaTopic, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        ActiveUser user = ShiroUtils.getSessionUser();
        if ("".equals(oaTopic.getTopicName())) {
            oaTopic.setTopicName(null);
        }
        Set sessionUserRole = ShiroUtils.getSessionUserRole();
        boolean contains = sessionUserRole.contains("0106");
        String role = null;
        if (contains){
            role = "0106";
        }
        //判断 表格显示数据 查询条件
        oaTopic = getSelectAuditTopic(oaTopic, user);
        //纪委与财务 负责人查询数据为两种
        QueryWrapper<OaTopic> queryWrapper = new QueryWrapper<>();
        //名称模糊查询
        if (!StringUtil.isEmpty(oaTopic.getTopicName())) {
            queryWrapper.like("TOPIC_NAME", oaTopic.getTopicName());
            oaTopic.setTopicName(null);
        }
        queryWrapper.setEntity(oaTopic);
        //处理时间查询
        Map<String, String[]> paramsMap = request.getParameterMap();
        if (null != paramsMap) {
            String[] beginValue = paramsMap.get("creatTime_begin");
            if (null != beginValue && beginValue.length > 0) {
                if (StringUtils.isNotEmpty(beginValue[0].trim())) {
                    queryWrapper.ge("CREAT_TIME", beginValue[0].trim());
                }
            }
            String[] endValue = paramsMap.get("creatTime_end");
            if (null != endValue && endValue.length > 0) {
                if (StringUtils.isNotEmpty(endValue[0].trim())) {
                    queryWrapper.le("CREAT_TIME", endValue[0].trim());
                }
            }
        }
        queryWrapper.orderByDesc("CREAT_TIME");

        IPage<OaTopic> page = topicService.page(new Page<OaTopic>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        ResponseUtil.writeJson(response, dataGrid, page);
    }

    /**
     * 表格数据
     *
     * @param oaTopic
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(value = "viewtable")
    public void topicViewTable(OaTopic oaTopic, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        QueryWrapper<OaTopic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("IS_PASS_FIVE",1).orderByDesc("CREAT_TIME");
        IPage<OaTopic> page = topicService.page(new Page<OaTopic>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        ResponseUtil.writeJson(response, dataGrid, page);
    }

    /**
     * 增改视图
     * topic/saveOrUpdateView
     *
     * @param oaTopic
     * @return
     */
    @RequestMapping(value = "saveOrUpdateView")
    public ModelAndView saveOrUpdateOa(OaTopic oaTopic, String params) {
        //创建人
        ModelAndView modelAndView = new ModelAndView("topic/topic");
        modelAndView = getMV(oaTopic, modelAndView);
        String sessionUserName = ShiroUtils.getSessionUserRealName();
        params = sessionUserName;
        if (!StringUtil.isEmpty(params)) {
            modelAndView.addObject("params", params);
        }
        if (!StringUtil.isEmpty(oaTopic.getId()))
            modelAndView = getFileList(modelAndView, oaTopic);
        return modelAndView;
    }

    @RequestMapping(value = "printTopic")
    public ModelAndView printTopic(OaTopic oaTopic) {
        //创建人
        ModelAndView modelAndView = new ModelAndView("topic/topicprint");
        oaTopic = topicService.getById(oaTopic.getId());
        modelAndView.addObject("oaTopic", oaTopic);
        SysDeptEntity deptEntity = deptService.getById(oaTopic.getDeptId());
        modelAndView.addObject("deptName", deptEntity.getName());
        return modelAndView;
    }

    /**
     * 审核视图
     *
     * @param oaTopic
     * @return
     */
    @RequestMapping(value = "auditModel")
    public ModelAndView auditModel(OaTopic oaTopic) {
        //创建人
        ModelAndView modelAndView = new ModelAndView("topic/topicaudit");
        modelAndView.addObject("lookOrAdu", oaTopic.getOpinion());
        oaTopic = topicService.getById(oaTopic.getId());
        /**
         * 得到用户级别
         */
        String auditLV = ShiroUtils.getSessionValue("auditLV");
        //判断该级别是否已经进行审过
        //modelAndView = getIsPassMV(modelAndView, oaTopic, auditLV);
        modelAndView = getMVForStaud(oaTopic, modelAndView);
        modelAndView = getFileList(modelAndView, oaTopic);
        if (ShiroUtils.hasRole("topicaudit"))
            modelAndView.addObject("isGeneralOffice", 1);
        return modelAndView;
    }

    /**
     * 二次审核视图
     *
     * @param oaTopic
     * @return
     */
    @RequestMapping(value = "auditSecondModel")
    public ModelAndView auditSecondModel(OaTopic oaTopic) {
        ModelAndView modelAndView = new ModelAndView("topic/topicauditsecond");
        modelAndView = getMVForStaud(oaTopic, modelAndView);
        modelAndView = getFileList(modelAndView, oaTopic);
        if (ShiroUtils.hasRole("topicaudit"))
            modelAndView.addObject("isGeneralOffice", 1);
        return modelAndView;
    }

    /**
     * 开启二次审核
     *
     * @param oaTopic
     * @return
     */
    @RequestMapping(value = "auditSecond")
    @Transactional(propagation = Propagation.REQUIRED)
    public AjaxJson auditSecond(OaTopic oaTopic) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            //二次审核 回溯 科室负责人 选中部门再次审核 财务 纪委
            oaTopic.setIsHistory(0);
            oaTopic.setStateId(0);
            oaTopic.setIsPassOne(0);
            oaTopic.setIsPassTwo(0);
            oaTopic.setIsPassThree(0);
            oaTopic.setIsPassFour(0);
            oaTopic.setIsPassFive(0);
            oaTopic.setAllPass(0);
            oaTopic.setChoicePassFive("false");
            oaTopic.setChoicePassFour("false");
            //二次审核字段
            oaTopic.setAuditSecond(1);
            //覆盖 原有 财务 与 纪检
            oaTopic = ifHaveJWOrCW(oaTopic);
            topicService.saveOrUpdate(oaTopic);
            //发消息
            sendSecondAuditMsg(topicService.getById(oaTopic.getId()));
        } catch (Exception e) {
            log.error("提交二次审核失败,错误信息:" + e.getMessage());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("提交二次审核失败");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    /**
     * 审核
     *
     * @param oaTopic id 与 opinion
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @RequestMapping("audit")
    public AjaxJson audit(OaTopic oaTopic) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            OaTopic dao = topicService.getById(oaTopic.getId());
            //是否已经审核过
            String auditLV = ShiroUtils.getSessionValue("auditLV");
            //审核结果处理
            dao = topicAndAuditLV(dao, auditLV, oaTopic.getOpinion(),
                    oaTopic.getIsOk(), oaTopic.getIsWorkingCommittee(), oaTopic.getIsDirector());
            topicService.saveOrUpdate(dao);
        } catch (OaTopicException e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("提交审核失败,错误信息:" + e.getMessage());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("提交审核失败");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    /**
     * 新增修改议题
     *
     * @param oaTopic
     * @return topic/save
     */
    @RequestMapping(value = "saveOrUpdate")
    public AjaxJson saveOrUpdateOaTopic(OaTopic oaTopic) {
        AjaxJson ajaxJson = new AjaxJson();
        ActiveUser user = ShiroUtils.getSessionUser();
        try {
            //判断是否已经经过科室领导审核
            if (!StringUtil.isEmpty(oaTopic.getId())) {
                OaTopic dao = topicService.getById(oaTopic.getId());
                if (dao.getIsPassOne() == 1)
                    throw new OaTopicException("审核已在进行,不可修改");
            }

            if ((!StringUtils.isEmpty(oaTopic.getFinanceOffice()) && StringUtils.isEmpty(oaTopic.getDisciplineOffice())) ||
                    (StringUtils.isEmpty(oaTopic.getFinanceOffice()) && !StringUtils.isEmpty(oaTopic.getDisciplineOffice()))) {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("财务与纪委审核人需同时存在或同时为空");
                return ajaxJson;
            }
            oaTopic.setIsPassOne(0);
            oaTopic.setIsPassTwo(0);
            oaTopic.setIsPassThree(0);
            if (StringUtils.isEmpty(oaTopic.getFinanceOffice())) {
                oaTopic.setChoicePassFour("false");
                oaTopic.setIsPassFour(1);
                oaTopic.setOpinionFinanceOffice("无需审核，默认通过");
            } else {
                oaTopic.setChoicePassFour("true");
                oaTopic.setIsPassFour(0);
            }
            if(StringUtils.isEmpty(oaTopic.getDisciplineOffice())){
                oaTopic.setChoicePassFive("false");
                oaTopic.setIsPassFive(1);
                oaTopic.setOpinionDisciplineOffice("无需审核，默认通过");
            }else{
                oaTopic.setChoicePassFive("true");
                oaTopic.setIsPassFive(0);
            }
//            //纪委科长与纪委主管领导并存
//            if (StringUtils.isEmpty(oaTopic.getDisciplineOffice())&&StringUtils.isEmpty(oaTopic.getManagerOffice())) {
//                oaTopic.setChoicePassFive("false");
//                oaTopic.setIsPassFive(1);
//                oaTopic.setOpinionDisciplineOffice("无需审核，默认通过");
//                oaTopic.setIsPassSix(1);
//                oaTopic.setManagerOfficeIdea("无需审核，默认通过");
//            } else {
//                oaTopic.setChoicePassFive("true");
//                oaTopic.setIsPassFive(0);
//                oaTopic.setIsPassSix(0);
//            }

            oaTopic.setCreateUserId(user.getId());
            oaTopic.setCreateUserName(user.getRealName());
            oaTopic = getUserName(oaTopic);
            //判断是否是 纪委与财务创建的议题
            oaTopic = ifJWOrCW(oaTopic);
            if (StringUtil.isEmpty(oaTopic.getId())) {
                oaTopic.setStateId(0);
                oaTopic.setCreatTime(new Date());
            }
            topicService.saveOrUpdate(oaTopic);
            //审核消息发送
            sendAuditMsg(oaTopic.getDeptLeaderId(), topicService.getById(oaTopic.getId()));
        } catch (OaTopicException e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("提交议题失败,错误信息:" + e.getMessage());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("提交议题失败");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    /**
     * 获取单个议题
     *
     * @param oaTopic
     * @return
     */
    @RequestMapping(value = "getOne")
    public AjaxJson getOne(OaTopic oaTopic) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            ajaxJson.setObj(topicService.getById(oaTopic.getId()));
        } catch (Exception e) {
            log.error("获取议题失败,错误信息:" + e.getMessage());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("获取议题失败");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    /**
     * 删除
     *
     * @param oaTopic
     * @return
     */
    @RequestMapping(value = "remove")
    @Transactional(propagation = Propagation.REQUIRED)
    public AjaxJson remove(OaTopic oaTopic) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            topicService.removeById(oaTopic.getId());
        } catch (Exception e) {
            log.error("删除议题失败,错误信息:" + e.getMessage());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("删除议题失败");
            e.printStackTrace();
        }
        return ajaxJson;
    }


    @RequestMapping(value = "test")
    public AjaxJson testGet(OaTopic oaTopic) {
        AjaxJson json = new AjaxJson();
        oaTopic = topicService.getById(oaTopic.getId());
        json.setObj(oaTopic);
        return json;
    }

    /**
     * 处理审核
     *
     * @param oaTopic            审核议题 数据库数据
     * @param auditLV            审核级别
     * @param opinion            意见
     * @param isOk               是否通过
     * @param isWorkingCommittee 是否工委会
     * @param isDirector         是否主任会
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public OaTopic topicAndAuditLV(OaTopic oaTopic, String auditLV, String opinion,
                                   Integer isOk, String isWorkingCommittee, String isDirector) {
        //五阶段审核
        Integer lv = Integer.parseInt(auditLV);
        if (lv == 1) {
            //一次审核 上级已审核通过 禁止修改
            oaTopic = deptLeaderAudit(oaTopic, opinion, isOk);
        } else if (lv == 2) {
            //综合办已经审核通过 禁止修改
            if (oaTopic.getIsPassThree() == 1) {
                throw new OaTopicException("审核已提交,禁止修改");
            }
            //主管领导
            if (isOk == 1) {
                oaTopic.setIsPassTwo(1);
                oaTopic.setOpinionLeader(opinion);
                oaTopic.setStateId(2);
                //向综合办发送审核消息
//                sendAuditMsg(oaTopic.getGeneralOffice(), oaTopic);
                //判断是否需要财务&纪委审批
                if (oaTopic.getIsPassFour().equals(1) && oaTopic.getIsPassFive().equals(1)) {
                    sendAuditMsg(oaTopic.getGeneralOffice(), oaTopic);
                } else {
                    if (oaTopic.getIsPassFour().equals(0)) {
                        sendAuditMsg(oaTopic.getFinanceOffice(), oaTopic);
                    }
                    if (oaTopic.getIsPassFive().equals(0)) {
                        sendAuditMsg(oaTopic.getDisciplineOffice(), oaTopic);
                    }
                }
            } else {
                oaTopic.setIsPassTwo(2);
                oaTopic.setOpinionLeader(opinion);
                oaTopic.setStateId(1);
                //拒绝消息
                sendRejectMsg(oaTopic, oaTopic.getLeaderName());
            }
        } else if (lv == 3) {
            //综合办
            //财务 或 纪委 均 通过审核 综合办给与审批
            if (oaTopic.getIsPassFour() != 1 || oaTopic.getIsPassFive() != 1) {
                throw new OaTopicException("财务&纪委为同时审批通过，请联系财务&纪委审批人完成审批");
            }
            if (isOk == 1) {
                //审批流程结束
                oaTopic.setIsPassThree(1);
                oaTopic.setOpinionGeneralOffice(opinion);
                oaTopic.setStateId(4);
                oaTopic.setAllPass(1);
//                //判断是否需要 纪委或综合办参与审核  根据是否选中两个科室的负责人id
//                //发送审核通知
//                oaTopic = ifNeedJWOrCW(oaTopic);
                //修改上会建议
                oaTopic.setIsWorkingCommittee(isWorkingCommittee);
                oaTopic.setIsDirector(isDirector);
                sendAllPassMsg(oaTopic);
            } else {
                oaTopic.setIsPassThree(2);
                oaTopic.setOpinionGeneralOffice(opinion);
                oaTopic.setStateId(1);
                //驳回通知
                sendRejectMsg(oaTopic, oaTopic.getGeneralOfficeName());
            }
        } else if (lv == 4) {
            //财务审批
            if (oaTopic.getChoicePassFour().equals("false")) {
                throw new OaTopicException("该申请无须财务审批");
            }
            if (oaTopic.getIsPassFour().equals(1)) {
                throw new OaTopicException("财务审批流程已结束，请勿重复审批");
            }
            if (oaTopic.getStateId() == 2) {
                //主管领导审批完成，财务可以审批
                if (isOk == 1) {
                    //后两个级别审核通过
                    oaTopic.setIsPassFour(1);
                    oaTopic.setOpinionFinanceOffice(opinion);
                    if(oaTopic.getIsPassFive().equals(0)){
                        sendAuditMsg(oaTopic.getDisciplineOffice(), oaTopic);
                    }
                    if (oaTopic.getIsPassFive().equals(1)){
                        sendAuditMsg(oaTopic.getGeneralOffice(), oaTopic);
                    }
//                    if (!"".equals(oaTopic.getDisciplineOffice()) && oaTopic.getIsPassFive() == 1) {
//                        //纪委ID不为空 且通过审核
//                        oaTopic.setStateId(4);
//                        oaTopic.setAllPass(1);
//                        //发审核通过通知
//                        sendAllPassMsg(oaTopic);
//                        //oaTopic.setIsHistory(1);
//                    } else if ("".equals(oaTopic.getDisciplineOffice())) {
//                        //纪委ID空 通过审核  其他不修改状态
//                        oaTopic.setStateId(4);
//                        oaTopic.setAllPass(1);
//                        //发审核通过通知
//                        sendAllPassMsg(oaTopic);
//                        //oaTopic.setIsHistory(1);
//                    }
                } else {
                    oaTopic.setIsPassFour(2);
                    oaTopic.setOpinionFinanceOffice(opinion);
                    oaTopic.setStateId(1);
                    //审核不通过信息
                    sendRejectMsg(oaTopic, oaTopic.getFinanceName());
                }
            } else {
                //没通过主管领审核的 是作为科室负责人 审核本科室提交议题
                //一次审核 上级已审核通过 禁止修改
                oaTopic = deptLeaderAudit(oaTopic, opinion, isOk);
            }
        } else if (lv == 5) {
            /***
             *
             * 问题点
             *
             *
             * ***/
            if (oaTopic.getChoicePassFive().equals("false")) {
                throw new OaTopicException("该申请无须纪委审批");
            }
            if (oaTopic.getIsPassFive().equals(1)) {
                throw new OaTopicException("纪委科长审批流程已结束，请勿重复审批");
            }
            //纪委
            if (oaTopic.getStateId() == 2) {
                //主管领导审批完成，纪委可以审批
                if (isOk == 1) {
                    //后两个级别审核通过
                    oaTopic.setIsPassFive(1);
                    oaTopic.setOpinionDisciplineOffice(opinion);

//                    sendAuditMsg(oaTopic.getManagerOffice(), oaTopic); //给纪委主管领导传消息

                    if(oaTopic.getIsPassFour().equals(0)){
                        sendAuditMsg(oaTopic.getFinanceOffice(), oaTopic);
                    }
                    // //给综合办发消息
                    if (oaTopic.getIsPassFour().equals(1)){
                        sendAuditMsg(oaTopic.getGeneralOffice(), oaTopic);
                    }
//                    if (!"".equals(oaTopic.getFinanceOffice()) && oaTopic.getIsPassFour() == 1) {
//                        //纪委ID不为空 且通过审核
//                        oaTopic.setStateId(4);
//                        oaTopic.setAllPass(1);
//                        //发审核通过通知
//                        sendAllPassMsg(oaTopic);
//                        //oaTopic.setIsHistory(1);
//                    } else if ("".equals(oaTopic.getFinanceOffice())) {
//                        //纪委ID空 通过审核  其他不修改状态
//                        oaTopic.setStateId(4);
//                        oaTopic.setAllPass(1);
//                        //发审核通过通知
//                        sendAllPassMsg(oaTopic);
//                        //oaTopic.setIsHistory(1);
//                    }
                } else {
                    oaTopic.setIsPassFive(2);
                    oaTopic.setOpinionDisciplineOffice(opinion);
                    oaTopic.setStateId(1);
                    //审核不通过信息
                    sendRejectMsg(oaTopic, oaTopic.getDisciplineName());
                }
            } else{
                //没通过综合办审核的 是作为科室负责人 审核本科室提交议题
                //一次审核 上级已审核通过 禁止修改
                oaTopic = deptLeaderAudit(oaTopic, opinion, isOk);
            }
//        }else if(lv == 6){
//            if (oaTopic.getChoicePassFive().equals("false")) {
//                throw new OaTopicException("该申请无须纪委审批");
//            }
//            if (oaTopic.getIsPassSix().equals(1)) {
//                throw new OaTopicException("纪委主管领导审批流程已结束，请勿重复审批");
//            }
//            //纪委主管领导
//            if(oaTopic.getStateId() == 2){
//                if (oaTopic.getIsPassFive() != 1){
//                    throw new OaTopicException("纪委科长暂未审批");
//                }else{
//                    //主管领导审批完成，纪委可以审批
//                    if (isOk == 1) {
//                        //后两个级别审核通过
//                        oaTopic.setIsPassSix(1);
//                        oaTopic.setManagerOfficeIdea(opinion);
//                        //给综合办发消息
//                        sendAuditMsg(oaTopic.getGeneralOffice(), oaTopic);
//
//                    } else {
//                        oaTopic.setIsPassSix(2);
//                        oaTopic.setManagerOfficeIdea(opinion);
//                        oaTopic.setStateId(1);
//                        //审核不通过信息
//                        sendRejectMsg(oaTopic, oaTopic.getManagerName());
//                    }
//                }
//            }else{
//                //没通过综合办审核的 是作为科室负责人 审核本科室提交议题
//                //一次审核 上级已审核通过 禁止修改
//                oaTopic = deptLeaderAudit(oaTopic, opinion, isOk);
//            }
        }
        return oaTopic;
    }

    /**
     * 科室负责人审核
     *
     * @param oaTopic
     * @param opinion
     * @param isOk
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public OaTopic deptLeaderAudit(OaTopic oaTopic, String opinion, Integer isOk) {
        //一次审核 上级已审核通过 禁止修改
        if (oaTopic.getIsPassTwo() == 1 && oaTopic.getAuditSecond() == 0) {
            throw new OaTopicException("审核已提交,禁止修改");
        } else if (oaTopic.getIsPassThree() == 1 && oaTopic.getAuditSecond() == 1) {
            //二次审核 综合办审核通过
            throw new OaTopicException("审核已提交,禁止修改");
        }
        if (isOk == 1) {
            oaTopic.setIsPassOne(1);
            oaTopic.setOpinionDeptLeader(opinion);
            oaTopic.setStateId(1);
            //审核通过 向主管领导发送审核消息
            sendAuditMsg(oaTopic.getLeaderId(), oaTopic);
            //如果是二次审核 默认通过主管领导审核
            if (oaTopic.getAuditSecond() == 1) {
                oaTopic.setIsPassTwo(1);
                oaTopic.setStateId(2);
                oaTopic.setOpinionLeader("二次审核,默认通过");
                if(!oaTopic.getIsPassFour().equals(1) && oaTopic.getChoicePassFour().equals("true")){
                    //向财务发送 审核信息
                    sendAuditMsg(oaTopic.getFinanceOffice(), oaTopic);
                }else{
                    oaTopic.setIsPassFour(1);
//                    oaTopic.setOpinionFinanceOffice("二次审核,默认通过");
                }
                if(!oaTopic.getIsPassFour().equals(1) && oaTopic.getChoicePassFour().equals("true")){
                    //向纪委发送 审核信息
                    sendAuditMsg(oaTopic.getDisciplineOffice(), oaTopic);
                }else{
                    oaTopic.setIsPassFive(1);
//                    oaTopic.setOpinionDisciplineOffice("二次审核,默认通过");
                }
                //不需要财务纪委审批 直接转综合办科员
                if(oaTopic.getChoicePassFour().equals("false") && oaTopic.getChoicePassFour().equals("false")){
                    sendAuditMsg(oaTopic.getGeneralOffice(), oaTopic);
                }
            }
        } else {
            //负责人拒绝后直接打回
            oaTopic.setIsPassOne(2);
            oaTopic.setOpinionDeptLeader(opinion);
            oaTopic.setStateId(0);
            //驳回消息
            sendRejectMsg(oaTopic, oaTopic.getDeptLeaderName());
        }
        return oaTopic;
    }

    /**
     * 判断登录角色的查询条件
     *
     * @param oaTopic
     * @param user
     * @return
     */
    private OaTopic getSelectTopic(OaTopic oaTopic, ActiveUser user) {
        SysUserEntity userEntity = getUser();
        if (ShiroUtils.hasRole("0106")) {
            //纪委科室上级主管领导
            oaTopic.setChoicePassFive("true");
            ShiroUtils.setSessionValue("auditLV", "6");
        } else if (ShiroUtils.hasRole("011")) {
            //判断是否主管领导
            //03本科室主管领导  leaderId 通过科长审核
            oaTopic.setLeaderId(user.getId());
            oaTopic.setIsPassOne(1);
            ShiroUtils.setSessionValue("auditLV", "2");
        } else if (ShiroUtils.hasRole("01014")) {
            //判断是否财务负责人
            //05财务科科长 financeOffice isPassThree choicePassFour
           /* oaTopic.setFinanceOffice(user.getId());
            oaTopic.setIsPassThree(1);*/
            oaTopic.setChoicePassFour("true");
            ShiroUtils.setSessionValue("auditLV", "4");
        } else if (ShiroUtils.hasRole("topicadd")) {
            //判断是否议题发起人
            //01议题发起人  deptId查询条件
            //oaTopic.setDeptId(userEntity.getDeptId());
            oaTopic.setAllPass(0);
        } else if (ShiroUtils.hasRole("topicaudit")) {
            //判断是否综合办议题审核人员
            //04综合办议题审核员 isPassOne isPassTwo
            oaTopic.setIsPassTwo(1);
            ShiroUtils.setSessionValue("auditLV", "3");
        } else {
            //剩下的 为 本科室 负责人
            //02本科室科长  deptLeaderId
            oaTopic.setDeptLeaderId(user.getId());
            ShiroUtils.setSessionValue("auditLV", "1");
        }
        return oaTopic;
    }

    /**
     * 判断登录角色的查询条件
     *
     * @param oaTopic
     * @param user
     * @return
     */
    private OaTopic getSelectAuditTopic(OaTopic oaTopic, ActiveUser user) {
        SysUserEntity userEntity = getUser();
        if (ShiroUtils.hasRole("0106")) {
            //纪委科室上级主管领导
            //oaTopic.setChoicePassFive("true");
            ShiroUtils.setSessionValue("auditLV", "6");
        } else if (ShiroUtils.hasRole("011")) {
            //判断是否主管领导
            //03本科室主管领导  leaderId 通过科长审核
            oaTopic.setLeaderId(user.getId());
            oaTopic.setIsPassOne(1);
            ShiroUtils.setSessionValue("auditLV", "2");
        } else if (ShiroUtils.hasRole("01014")) {
            //判断是否财务负责人
            //05财务科科长 financeOffice isPassThree choicePassFour
           /* oaTopic.setFinanceOffice(user.getId());
            oaTopic.setIsPassThree(1);*/
            oaTopic.setIsPassTwo(1);
            oaTopic.setIsPassFour(0);
            oaTopic.setChoicePassFour("true");
            ShiroUtils.setSessionValue("auditLV", "4");
        } else if (ShiroUtils.hasRole("012")) {
            //判断是否纪委
            //04纪委
            oaTopic.setIsPassTwo(1);
            //oaTopic.setIsPassFour(1);
            oaTopic.setIsPassFive(0);
            oaTopic.setChoicePassFive("true");
            ShiroUtils.setSessionValue("auditLV", "5");
        } else if (ShiroUtils.hasRole("topicaudit")) {
            //判断是否综合办议题审核人员
            //04综合办议题审核员 isPassOne isPassTwo
            oaTopic.setIsPassTwo(1);
//            oaTopic.setIsPassSix(1);
            ShiroUtils.setSessionValue("auditLV", "3");
        } else if (ShiroUtils.hasRole("topicadd")) {
            //判断是否议题发起人
            //01议题发起人  deptId查询条件
            oaTopic.setDeptId(userEntity.getDeptId());
        } else {
            //剩下的 为 本科室 负责人
            //02本科室科长  deptLeaderId
            oaTopic.setDeptLeaderId(user.getId());
            ShiroUtils.setSessionValue("auditLV", "1");
        }
        return oaTopic;
    }

    /**
     * 判断是否纪委与财务创建
     *
     * @param oaTopic
     * @return
     */
    private OaTopic ifJWOrCW(OaTopic oaTopic) {
        SysUserEntity userEntity = getUser();
        if ("c1150728449e35b42fbe86db549477e8".equals(userEntity.getDeptId())) {
            //纪检监察组
            oaTopic.setChoicePassFive("true");
        } else if ("088c84560ed47db5d1ce11696a4915e3".equals(userEntity.getDeptId())) {
            //财务处
            oaTopic.setChoicePassFour("true");
        }
        return oaTopic;
    }

    /**
     * 综合办 二次审核后
     * 覆盖原有的纪委与财务审核id
     *
     * @param oaTopic
     * @return
     */
    private OaTopic ifHaveJWOrCW(OaTopic oaTopic) {
        OaTopic dao = topicService.getById(oaTopic.getId());
        if (oaTopic.getIsFO() == 2) {
            oaTopic.setFinanceOffice("");
            oaTopic.setIsPassFour(1);
            if (!"088c84560ed47db5d1ce11696a4915e3".equals(dao.getDeptId()))
                oaTopic.setChoicePassFour("false");
        }
        if (oaTopic.getIsDO() == 2) {
            oaTopic.setDisciplineOffice("");
            oaTopic.setIsPassFive(1);
            if (!"c1150728449e35b42fbe86db549477e8".equals(dao.getDeptId()))
                oaTopic.setChoicePassFive("false");

        }
        return oaTopic;
    }

    /**
     * 综合办审核后
     * 判断是否需要纪委与财务参与审核
     *
     * @param oaTopic
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public OaTopic ifNeedJWOrCW(OaTopic oaTopic) {
        if (!StringUtil.isEmpty(oaTopic.getFinanceOffice()) && !"".equals(oaTopic.getFinanceOffice())) {
            oaTopic.setChoicePassFour("true");
            //向财务办发送审核通知
            sendAuditMsg(oaTopic.getFinanceOffice(), oaTopic);
        }
        if (!StringUtil.isEmpty(oaTopic.getDisciplineOffice()) || !"".equals(oaTopic.getDisciplineOffice())) {
            oaTopic.setChoicePassFive("true");
            //向纪委办发送审核通知
            sendAuditMsg(oaTopic.getDisciplineOffice(), oaTopic);
        }
        return oaTopic;
    }

    /**
     * 得到用户
     *
     * @return
     */
    private SysUserEntity getUser() {
        ActiveUser activeUser = ShiroUtils.getSessionUser();
        return userService.getById(activeUser.getId());
    }

    /**
     * roleID 换 users
     *
     * @return
     */
    private List<SysUserEntity> userList(String deptId, String roleId) {
        QueryWrapper<SysUserRoleEntity> userRoleEntityQueryWrapper = new QueryWrapper<>();
        userRoleEntityQueryWrapper.eq("ROLE_ID", roleId);
        List<SysUserRoleEntity> URList = userRoleService.list(userRoleEntityQueryWrapper);
        List<SysUserEntity> users = new ArrayList<>();
        if (URList.size() > 0)
            URList.forEach(URDao -> {
                SysUserEntity user = userService.getById(URDao.getUserId());
                users.add(user);
            });
        return users;
    }

    /**
     * 追溯上级
     *
     * @param userEntity
     * @return
     */
    private List<SysUserEntity> leaderList(SysUserEntity userEntity) {
        return null;
    }

    /**
     * id换真名
     *
     * @return
     */
    private OaTopic getUserName(OaTopic oaTopic) {
        if (!StringUtil.isEmpty(oaTopic.getProposeLeader())) {
            oaTopic.setProposeLeaderName(userService.findNameById(oaTopic.getProposeLeader()));
        }
        /*if (!StringUtil.isEmpty(oaTopic.getReportId())) {
            oaTopic.setReportName(userService.findNameById(oaTopic.getReportId()));
        }*/
        if (!StringUtil.isEmpty(oaTopic.getDeptLeaderId())) {
            oaTopic.setDeptLeaderName(userService.findNameById(oaTopic.getDeptLeaderId()));
        }
        if (!StringUtil.isEmpty(oaTopic.getLeaderId())) {
            oaTopic.setLeaderName(userService.findNameById(oaTopic.getLeaderId()));
        }
        if (!StringUtil.isEmpty(oaTopic.getGeneralOffice())) {
            oaTopic.setGeneralOfficeName(userService.findNameById(oaTopic.getGeneralOffice()));
        }
        if (!StringUtil.isEmpty(oaTopic.getFinanceOffice())) {
            oaTopic.setFinanceName(userService.findNameById(oaTopic.getGeneralOffice()));
        }
        if (!StringUtil.isEmpty(oaTopic.getDisciplineOffice())) {
            oaTopic.setDisciplineName(userService.findNameById(oaTopic.getDisciplineOffice()));
        }
//        if (!StringUtil.isEmpty(oaTopic.getManagerOffice())){
//            oaTopic.setManagerName(userService.findNameById(oaTopic.getManagerOffice()));
//        }
        return oaTopic;
    }

    /**
     * 返回视图
     *
     * @param oaTopic
     * @return
     */
    private ModelAndView getMV(OaTopic oaTopic, ModelAndView modelAndView) {
        SysUserEntity userEntity = getUser();
        if (StringUtil.isEmpty(oaTopic.getId())) {
            oaTopic = new OaTopic();
            oaTopic.setDeptId(userEntity.getDeptId());
            //oaTopic.setId(UUID.randomUUID().toString());
        } else {
            oaTopic = topicService.getById(oaTopic.getId());
        }
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DEPT_ID", userEntity.getDeptId());
        List<SysUserEntity> users = userService.list(queryWrapper);
        modelAndView.addObject("oaTopic", oaTopic);
        //
        SysDeptEntity dept = deptService.getById(userEntity.getDeptId());
        modelAndView.addObject("deptName", dept.getName());
        //DeptLeaderRole deptLeaderRole = new DeptLeaderRole();

        // 确定科室负责人角色名称
        String leaderRoleName = dept.getName() + WorkflowConstant.Str_Dept_Manager;
        String leaderRole = deptService.getLeaderRoleIdByRole(leaderRoleName);
        //String leaderRole = deptLeaderRole.getRoleForDept().get(userEntity.getDeptId());
        //汇报人
        modelAndView.addObject("reportList", users);
        //提议领导 各处主管领导
        modelAndView.addObject("proposeLeaderList", userList("", "e30ab91d21471d32f425950a60bd1eaa"));
        //科室负责人
        modelAndView.addObject("deptLeader", userList("", leaderRole));
        //主管领导
        SysRoleEntity roleEntity = roleService.getById(leaderRole);
        modelAndView.addObject("lv2Leader", userList("", roleEntity.getParentId()));
        //综合办
        modelAndView.addObject("generalOffice", roleService.findUserByRoleName("综合办议题审核员"));
        //财务科科长
        modelAndView.addObject("financeOffice", roleService.findUserByRoleName("财务科室负责人"));
        //纪委科长
        modelAndView.addObject("disciplineOffice", roleService.findUserByRoleName("纪检监察组科室负责人"));
        //纪委主管领导
        String disciplineOfficeId = roleService.findUserByRoleName("纪检监察组科室负责人").get(0).getDeptId();//纪委部门id
        String departmentName = this.oaTopicService.getDeptName(disciplineOfficeId);
        String username = departmentName.substring(0,2);
        modelAndView.addObject("managerOffice",this.oaTopicService.getUserMessage(username));
        return modelAndView;
    }

    private ModelAndView getMVForStaud(OaTopic oaTopic, ModelAndView modelAndView) {
        SysUserEntity userEntity = getUser();
        oaTopic = topicService.getById(oaTopic.getId());
        String deptId = oaTopic.getDeptId();
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DEPT_ID", deptId);
        List<SysUserEntity> users = userService.list(queryWrapper);
        modelAndView.addObject("oaTopic", oaTopic);
        //
        SysDeptEntity dept = deptService.getById(deptId);
        modelAndView.addObject("deptName", dept.getName());

        // 确定科室负责人角色名称
        String leaderRoleName = dept.getName() + WorkflowConstant.Str_Dept_Manager;
        String leaderRole = deptService.getLeaderRoleIdByRole(leaderRoleName);

        //汇报人
        modelAndView.addObject("reportList", users);
        //提议领导 查询主要领导
        modelAndView.addObject("proposeLeaderList", userList("", "e30ab91d21471d32f425950a60bd1eaa"));
        //科室负责人
        modelAndView.addObject("deptLeader", userList("", leaderRole));
        //主管领导
        SysRoleEntity roleEntity = roleService.getById(leaderRole);
        modelAndView.addObject("lv2Leader", userList("", roleEntity.getParentId()));
        //综合办
        modelAndView.addObject("generalOffice", roleService.findUserByRoleName("综合办议题审核员"));
        //财务科科长
        modelAndView.addObject("financeOffice", roleService.findUserByRoleName("财务科室负责人"));
        //纪委科长
        modelAndView.addObject("disciplineOffice", roleService.findUserByRoleName("纪检监察组科室负责人"));
        //纪委主管领导
        String disciplineOfficeId = roleService.findUserByRoleName("纪检监察组科室负责人").get(0).getDeptId();//纪委部门id
        String departmentName = this.oaTopicService.getDeptName(disciplineOfficeId);
        String username = departmentName.substring(0,2);
        modelAndView.addObject("managerOffice",this.oaTopicService.getUserMessage(username));
        return modelAndView;
    }

    /**
     * 文件列表
     *
     * @param modelAndView
     * @param oaTopic
     * @return
     */
    private ModelAndView getFileList(ModelAndView modelAndView, OaTopic oaTopic) {
        oaTopic = topicService.getById(oaTopic.getId());
        oaTopic.setFileId(",");
        if (!StringUtil.isEmpty(oaTopic.getFileId())) {
            //System.err.println("getFileList");
            String[] fileIds = oaTopic.getFileId().split(",");
            if (0 != fileIds.length){
                QueryWrapper<UploadAttachmentEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.in("ID", fileIds);
                List<UploadAttachmentEntity> list = uploadAttachmentService.list(queryWrapper);
                modelAndView.addObject("uploadList", list);
            }
        }
        return modelAndView;
    }

    /**
     * 提议领导
     *
     * @return
     */
    private List<SysUserEntity> getAllLeader(String leaderRole) {
        SysRoleEntity roleEntity = roleService.getById(leaderRole);
        List<SysUserEntity> list = userList("", "1e3124100e45ed3e9ec99bf3e35be2c0");
        list.addAll(userList("", leaderRole));
        list.addAll(userList("", roleEntity.getParentId()));
        return list;
    }

    /**
     * 判断用户是否已经审核
     *
     * @param modelAndView
     * @param oaTopic
     * @param lv
     * @return
     */
    private ModelAndView getIsPassMV(ModelAndView modelAndView, OaTopic oaTopic, String lv) {
        if ("1".equals(lv) && oaTopic.getIsPassOne() == 0) {

        } else if ("2".equals(lv)) {

        } else if ("3".equals(lv)) {

        } else if ("4".equals(lv)) {

        } else if ("5".equals(lv)) {

        }
        return modelAndView;
    }

    /**
     * 向审核人发布消息
     *
     * @param id      审核人Id 发往审核人
     * @param oaTopic createUserName creatTime
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendAuditMsg(String id, OaTopic oaTopic) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String date = simpleDateFormat.format(oaTopic.getCreatTime());
        try {
            MessageUtils.SendSysMessage(id, String.format("您好,%s于%s提出议题申请,请审批", oaTopic.getCreateUserName(), date));
        } catch (Exception e) {
            log.error("sendAuditMsg", e.getMessage());
        }
    }

    /**
     * 全部审批通过的消息
     *
     * @param oaTopic 发往申请人
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendAllPassMsg(OaTopic oaTopic) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String date = simpleDateFormat.format(oaTopic.getCreatTime());
        try {
            MessageUtils.SendSysMessage(oaTopic.getCreateUserId(), String.format("您好,您于%s提出的议题申请,已审批通过", date));
        } catch (Exception e) {
            log.error("sendAuditMsg", e.getMessage());
        }
    }

    /**
     * 申请被驳回
     *
     * @param oaTopic  发往申请人
     * @param thisUser
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendRejectMsg(OaTopic oaTopic, String thisUser) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String date = simpleDateFormat.format(oaTopic.getCreatTime());
        try {
            MessageUtils.SendSysMessage(oaTopic.getCreateUserId(), String.format("您好,您于%s提出的议题申请,被%s驳回", date, thisUser));
        } catch (Exception e) {
            log.error("sendAuditMsg", e.getMessage());
        }
    }

    /**
     * 二次审核开启
     *
     * @param oaTopic 发往申请人
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendSecondAuditMsg(OaTopic oaTopic) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String date = simpleDateFormat.format(oaTopic.getCreatTime());
        try {
            MessageUtils.SendSysMessage(oaTopic.getCreateUserId(), String.format("您好,您于%s提出的议题申请,已开启二次审核", date));
        } catch (Exception e) {
            log.error("sendAuditMsg", e.getMessage());
        }
    }

}

