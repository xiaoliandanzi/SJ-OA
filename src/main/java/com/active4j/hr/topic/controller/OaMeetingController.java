package com.active4j.hr.topic.controller;


import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.model.ActiveUser;
import com.active4j.hr.topic.entity.OaMeeting;
import com.active4j.hr.topic.entity.OaTopic;
import com.active4j.hr.topic.service.OaMeetingService;
import com.active4j.hr.topic.service.OaTopicService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author weizihao
 * @since 2021-01-04
 */
@RestController
@RequestMapping("/meeting")
public class OaMeetingController {

    @Autowired
    private OaMeetingService  meetingService;


    /**
     * list视图
     *
     * @return
     */
    @RequestMapping(value = "list")
    public ModelAndView meetingList() {
        return new ModelAndView("topic/meeting");
    }


    /**
     * 表格数据
     *
     * @param oaMeeting
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(value = "table")
    public void topicTable(OaMeeting oaMeeting, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        ActiveUser user = ShiroUtils.getSessionUser();
        System.err.println(user);
        QueryWrapper<OaMeeting> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(oaMeeting);
        IPage<OaMeeting> page = meetingService.page(new Page<OaMeeting>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        ResponseUtil.writeJson(response, dataGrid, page);
    }
}

