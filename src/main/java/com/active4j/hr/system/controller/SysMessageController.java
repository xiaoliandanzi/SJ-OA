package com.active4j.hr.system.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.entity.SysMessageEntity;
import com.active4j.hr.system.service.SysMessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xfzhang
 * @version 1.0
 * @title SysMessageController.java
 * @description 系统消息管理
 * @time 2020年4月3日 上午10:53:32
 */
@Controller
@RequestMapping("/sys/message")
public class SysMessageController extends BaseController {

    @Autowired
    private SysMessageService sysMessageService;

    /**
     * @return ModelAndView
     * @description 列表显示
     * @author xfzhang
     * @time 2020年4月3日 上午10:53:54
     */
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView view = new ModelAndView("system/message/msglist");


        return view;
    }

    /**
     * @return void
     * @description 表格数据显示
     * @params
     * @author guyp
     * @time 2020年2月4日 上午9:45:39
     */
    @RequestMapping("/datagrid")
    public void datagrid(SysMessageEntity sysMessageEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        //拼接查询条件
        QueryWrapper<SysMessageEntity> queryWrapper = QueryUtils.installQueryWrapper(sysMessageEntity, request.getParameterMap(), dataGrid);

        //执行查询
        IPage<SysMessageEntity> lstResult = sysMessageService.page(new Page<SysMessageEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        long total = lstResult.getTotal();
        long tempTotal = 0;
        //获取当前用户id
        String userId = ShiroUtils.getSessionUserId();
        List<SysMessageEntity> newList = new ArrayList<>();
        if (total > 0) {
            for (SysMessageEntity entity : lstResult.getRecords()) {
                //本科室的文件
                if (entity.getUserId().equalsIgnoreCase(userId)) {
                    newList.add(entity);
                    tempTotal++;
                }
            }
            lstResult.setTotal(tempTotal);
            lstResult.setRecords(newList);

        }

        //输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }


    /**
     * 跳转到新增编辑页面
     *
     * @param sysMessageEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdate")
    public ModelAndView addorupdate(SysMessageEntity sysMessageEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("system/message/msg");

        if (StringUtils.isNotEmpty(sysMessageEntity.getId())) {
            sysMessageEntity = sysMessageService.getById(sysMessageEntity.getId());
            view.addObject("msg", sysMessageEntity);

            sysMessageEntity.setStatus(GlobalConstant.SYS_MSG_READED);
            sysMessageEntity.setTip(GlobalConstant.SYS_MSG_READED);

            sysMessageService.saveOrUpdate(sysMessageEntity);

        }

        return view;
    }


    /**
     * 跳转到新增编辑页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/view")
    public ModelAndView view(SysMessageEntity sysMessageEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("system/message/msg");

        if (StringUtils.isNotEmpty(sysMessageEntity.getId())) {
            sysMessageEntity = sysMessageService.getById(sysMessageEntity.getId());
            view.addObject("msg", sysMessageEntity);

            sysMessageEntity.setStatus(GlobalConstant.SYS_MSG_READED);
            sysMessageEntity.setTip(GlobalConstant.SYS_MSG_READED);

            sysMessageService.saveOrUpdate(sysMessageEntity);

        }

        return view;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(SysMessageEntity sysMessageEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if (StringUtils.isNotEmpty(sysMessageEntity.getId())) {

                sysMessageService.removeById(sysMessageEntity.getId());
            }
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);

        }

        return j;
    }
}
