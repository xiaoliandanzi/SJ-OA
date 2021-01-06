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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

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
        modelAndView = getMV(oaTopic,modelAndView);
        if (!StringUtil.isEmpty(params)) {
            modelAndView.addObject("params", params);
        }
        return modelAndView;
    }

    @RequestMapping(value = "auditModel")
    public ModelAndView auditModel(OaTopic oaTopic) {
        //创建人
        ModelAndView modelAndView = new ModelAndView("topic/topicaudit");
        modelAndView = getMV(oaTopic,modelAndView);
        return modelAndView;
    }


    /**
     * 新增修改议题
     *
     * @param oaTopic
     * @return topic/save
     */
    @RequestMapping(value = "saveOrUpdate")
    public AjaxJson saveOrUpdateOaTopic(OaTopic oaTopic) {
        System.err.println(oaTopic);
        AjaxJson ajaxJson = new AjaxJson();
        oaTopic = getUserName(oaTopic);
        oaTopic.setCreatTime(new Date());
        oaTopic.setStateId(0);
        try {
            topicService.saveOrUpdate(oaTopic);
        } catch (Exception e) {
            log.error("提交议题失败,错误信息:" + e.getMessage());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("提交议题失败");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    @RequestMapping(value = "getOne")
    public AjaxJson getOne(OaTopic oaTopic) {
        AjaxJson ajaxJson = new AjaxJson();
        System.err.println(oaTopic);
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
     * @param oaTopic
     * @return
     */
    private ModelAndView getMV(OaTopic oaTopic,ModelAndView modelAndView) {
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

