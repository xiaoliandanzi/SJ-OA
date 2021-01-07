package com.active4j.hr.topic.controller;


import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.util.StringUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.entity.SysUserRoleEntity;
import com.active4j.hr.system.model.ActiveUser;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysRoleService;
import com.active4j.hr.system.service.SysUserRoleService;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.topic.entity.OaTopic;
import com.active4j.hr.topic.service.OaTopicService;
import com.active4j.hr.topic.until.DeptLeaderRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.inject.internal.cglib.proxy.$Callback;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        queryWrapper.setEntity(oaTopic);
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
        if (!StringUtil.isEmpty(params)) {
            modelAndView.addObject("params", params);
        }
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
        modelAndView = getMV(oaTopic, modelAndView);
        return modelAndView;
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
        System.err.println("审核:" + oaTopic);
        try {
            OaTopic dao = topicService.getById(oaTopic.getId());
            String auditLV = ShiroUtils.getSessionValue("auditLV");
            //审核结果处理
            dao = topicAndAuditLV(dao, auditLV, oaTopic.getOpinion(),
                    oaTopic.getIsOk(), oaTopic.getIsWorkingCommittee(), oaTopic.getIsDirector());
            topicService.saveOrUpdate(dao);
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
        try {
            oaTopic = getUserName(oaTopic);
            //判断是否是 纪委与财务创建的议题
            oaTopic = ifJWOrCW(oaTopic);
            //判断是否需要 纪委或综合办参与审核  根据是否选中两个科室的负责人id
            oaTopic = ifNeedJWOrCW(oaTopic);
            oaTopic.setCreatTime(new Date());
            oaTopic.setStateId(0);
            topicService.saveOrUpdate(oaTopic);
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
    private OaTopic topicAndAuditLV(OaTopic oaTopic, String auditLV, String opinion,
                                    Integer isOk, String isWorkingCommittee, String isDirector) {
        //五阶段审核
        Integer lv = Integer.parseInt(auditLV);
        if (lv == 1) {
            //科室负责人审核
            if (isOk == 1) {
                oaTopic.setIsPassOne(1);
                oaTopic.setOpinionDeptLeader(opinion);
                oaTopic.setStateId(1);
            } else {
                //负责人拒绝后直接打回
                oaTopic.setIsPassOne(2);
                oaTopic.setOpinionDeptLeader(opinion);
                oaTopic.setStateId(0);
            }
        } else if (lv == 2) {
            //主管领导
            if (isOk == 1) {
                oaTopic.setIsPassTwo(1);
                oaTopic.setOpinionLeader(opinion);
                oaTopic.setStateId(2);
            } else {
                oaTopic.setIsPassTwo(2);
                oaTopic.setOpinionLeader(opinion);
                oaTopic.setStateId(1);
            }
        } else if (lv == 3) {
            //综合办
            if (isOk == 1) {
                oaTopic.setIsPassThree(1);
                oaTopic.setOpinionGeneralOffice(opinion);
                oaTopic.setStateId(3);
                //修改上会建议
                oaTopic.setIsWorkingCommittee(isWorkingCommittee);
                oaTopic.setIsDirector(isDirector);
            } else {
                oaTopic.setIsPassThree(2);
                oaTopic.setOpinionGeneralOffice(opinion);
                oaTopic.setStateId(1);
            }
        } else if (lv == 4) {
            //财务
            if (isOk == 1) {
                oaTopic.setIsPassFour(1);
                oaTopic.setOpinionFinanceOffice(opinion);
                //财务同意后 判断是否还要经过纪委同意 两者都同意  审核通过
                // 且 纪委负责人还要审核本部门的提交的审核

            } else {
                oaTopic.setIsPassFour(2);
                oaTopic.setOpinionFinanceOffice(opinion);
                oaTopic.setStateId(1);
            }
        } else if (lv == 5) {
            //纪委
            if (isOk == 1) {
                oaTopic.setIsPassFive(1);
                oaTopic.setOpinionDisciplineOffice(opinion);
                //纪委同意后 判断
            } else {
                oaTopic.setIsPassFive(2);
                oaTopic.setOpinionDisciplineOffice(opinion);
                oaTopic.setStateId(1);
            }
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
        if (ShiroUtils.hasRole("011")) {
            //判断是否主管领导
            //03本科室主管领导  leaderId 通过科长审核
            oaTopic.setLeaderId(user.getId());
            oaTopic.setIsPassOne(1);
            ShiroUtils.setSessionValue("auditLV", "2");
        } else if (ShiroUtils.hasRole("01061")) {
            //判断是否纪委负责人
            //06纪委科长 disciplineOffice isPassThree choicePassFive
            /*oaTopic.setDisciplineOffice(user.getId());
            oaTopic.setIsPassThree(1);*/
            oaTopic.setChoicePassFive("true");
            ShiroUtils.setSessionValue("auditLV", "5");
        } else if (ShiroUtils.hasRole("01014")) {
            //判断是否财务负责人
            //05财务科科长 financeOffice isPassThree choicePassFour
           /* oaTopic.setFinanceOffice(user.getId());
            oaTopic.setIsPassThree(1);*/
            oaTopic.setChoicePassFive("true");
            ShiroUtils.setSessionValue("auditLV", "4");
        } else if (ShiroUtils.hasRole("topicadd")) {
            //判断是否议题发起人
            //01议题发起人  deptId查询条件
            oaTopic.setDeptId(userEntity.getDeptId());
        } else if (ShiroUtils.hasRole("topicaudit")) {
            //判断是否综合办议题审核人员
            //04综合办议题审核员 isPassOne isPassTwo
            oaTopic.setIsPassTwo(1);
            oaTopic.setIsPassThree(1);
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
     * 判断是否需要纪委与财务参与审核
     *
     * @param oaTopic
     * @return
     */
    private OaTopic ifNeedJWOrCW(OaTopic oaTopic) {
        if (!StringUtil.isEmpty(oaTopic.getFinanceOffice())) {
            oaTopic.setChoicePassFour("true");
        }
        if (!StringUtil.isEmpty(oaTopic.getDisciplineOffice())) {
            oaTopic.setChoicePassFive("true");
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
        if (!StringUtil.isEmpty(oaTopic.getReportId())) {
            oaTopic.setReportName(userService.findNameById(oaTopic.getReportId()));
        }
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
        //汇报人
        modelAndView.addObject("reportList", users);
        //提议领导 查询主要领导
        modelAndView.addObject("proposeLeaderList", userList("", "1e3124100e45ed3e9ec99bf3e35be2c0"));
        //科室负责人
        DeptLeaderRole deptLeaderRole = new DeptLeaderRole();
        String leaderRole = deptLeaderRole.getRoleForDept().get(userEntity.getDeptId());
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
        //
        return modelAndView;
    }
}

