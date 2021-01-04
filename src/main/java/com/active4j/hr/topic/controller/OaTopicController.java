package com.active4j.hr.topic.controller;


import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.util.StringUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.model.ActiveUser;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.topic.entity.OaTopic;
import com.active4j.hr.topic.service.OaTopicService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.inject.internal.cglib.proxy.$Callback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * list视图
     *
     * @return
     */
    @RequestMapping(value = "list")
    public ModelAndView topicList() {
        return new ModelAndView("topic/topiclist");
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
        System.err.println(user);
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
     *
     * @param oaTopic
     * @return
     */
    @RequestMapping(value = "saveOrUpdateView")
    public ModelAndView saveOrUpdateOa(OaTopic oaTopic) {
        ModelAndView modelAndView = new ModelAndView("topic/topic");
        if (StringUtil.isEmpty(oaTopic.getId())) {
            oaTopic = new OaTopic();
            oaTopic.setId(UUID.randomUUID().toString());
        } else {
            oaTopic = topicService.getById(oaTopic.getId());
        }
        SysUserEntity userEntity = getUser();
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DEPT_ID", userEntity.getDeptId());
        List<SysUserEntity> users = userService.list(queryWrapper);
        modelAndView.addObject("oaTopic", oaTopic);
        //汇报人
        modelAndView.addObject("reportList", users);
        return modelAndView;
    }


    /**
     * 新增议题
     *
     * @param oaTopic
     * @return
     */
    @RequestMapping(value = "save")
    public AjaxJson saveOaTopic(OaTopic oaTopic) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            topicService.saveOrUpdate(oaTopic);
        } catch (Exception e) {
            log.error("新增议题失败,错误信息:" + e.getMessage());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("新增议题失败");
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
}

