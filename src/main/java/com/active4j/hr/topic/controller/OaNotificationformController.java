package com.active4j.hr.topic.controller;


import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.util.StringUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.model.ActiveUser;
import com.active4j.hr.topic.entity.OaMeeting;
import com.active4j.hr.topic.entity.OaNotificationform;
import com.active4j.hr.topic.entity.OaTopic;
import com.active4j.hr.topic.service.OaNotificationformService;
import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author weizihao
 * @since 2021-01-09
 */
@RestController
@RequestMapping("/notificationform")
public class OaNotificationformController {
    @Autowired
    private OaNotificationformService notificationformService;

    /**
     * 增改视图
     * meeting/saveOrUpdateView
     *
     * @param
     * @return
     */
    @RequestMapping(value = "saveOrUpdateView")
    public ModelAndView saveOrUpdateView(OaNotificationform oaNotificationform) {
        //创建人
        ModelAndView modelAndView = new ModelAndView("topic/notification");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.
                getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();//创建session对象
        session.setAttribute("onid", oaNotificationform.getHuiyiid());
        modelAndView.addObject("oaNotificationform", oaNotificationform);
        return modelAndView;
    }

    /**
     * 表格数据
     *
     * @param
     * @param
     * @param response
     * @param dataGrid
     */
    @RequestMapping(value = "table")
    public void tableAll(OaNotificationform oaNotificationform, HttpServletResponse response, DataGrid dataGrid) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.
                getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();//创建session对象
        String onid = (String) session.getAttribute("onid");
        QueryWrapper<OaNotificationform> queryWrapper = new QueryWrapper<>();
        oaNotificationform.setHuiyiid(onid);
        queryWrapper.setEntity(oaNotificationform);
        IPage<OaNotificationform> page = notificationformService.page(new Page<OaNotificationform>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        ResponseUtil.writeJson(response, dataGrid, page);
    }

    /**
     * 接收通知
     *
     * @param
     * @param
     * @param
     * @param
     */
    @RequestMapping(value = "caozuo")
    public AjaxJson caozuo(OaNotificationform oaNotificationform) {
        AjaxJson j = new AjaxJson();
        ActiveUser user = ShiroUtils.getSessionUser();
        String  id=user.getId();
        try {
            QueryWrapper<OaNotificationform> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("nameid",id).eq("huiyiid",oaNotificationform.getHuiyiid());
            OaNotificationform oaNotificationform1=notificationformService.list(queryWrapper).get(0);
            oaNotificationform1.setStatus("1");
            notificationformService.updateById(oaNotificationform1);
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("操作失败");
            e.printStackTrace();
        }
        return j;
    }


}