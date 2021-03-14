package com.active4j.hr.car.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.car.entity.OaCarEntity;
import com.active4j.hr.car.entity.OaDriverEntity;
import com.active4j.hr.car.service.OaDriverService;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("driver/manager")
@Slf4j
public class DriverManagerController extends BaseController {

    @Autowired
    private OaDriverService oaDriverService;
    /**
     * 驾驶员管理列表
     * @return
     */
    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("car/driverManager");
        return view;
    }

    /**
     * 查询数据
     *
     * @param oaDriverEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(OaDriverEntity oaDriverEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<OaDriverEntity> queryWrapper = QueryUtils.installQueryWrapper(oaDriverEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<OaDriverEntity> lstResult = oaDriverService.page(new Page<OaDriverEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }


    /**
     * 保存方法
     * @param oaDriverEntity
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(OaDriverEntity oaDriverEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{

            if(StringUtils.isEmpty(oaDriverEntity.getName())) {
                j.setSuccess(false);
                j.setMsg("司机姓名不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(oaDriverEntity.getId())) {
                //新增方法
                oaDriverService.save(oaDriverEntity);
            }else {
                //编辑方法
                OaDriverEntity tmp = oaDriverService.getById(oaDriverEntity.getId());
                MyBeanUtils.copyBeanNotNull2Bean(oaDriverEntity, tmp);
                oaDriverService.saveOrUpdate(tmp);
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("保存司机信息失败，错误信息:{}", e);
        }

        return j;
    }

    /**
     * 删除
     * @param oaDriverEntity
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(OaDriverEntity oaDriverEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isNotEmpty(oaDriverEntity.getId())) {
                oaDriverService.removeById(oaDriverEntity.getId());
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除驾驶员信息失败，错误信息:{}", e);
        }
        return j;
    }

    /**
     * 跳转到新增编辑页面
     * @param oaDriverEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdate")
    public ModelAndView addorupdate(OaDriverEntity oaDriverEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("car/driverAdd");

        if(StringUtils.isNotEmpty(oaDriverEntity.getId())) {
            oaDriverEntity = oaDriverService.getById(oaDriverEntity.getId());
            view.addObject("driver", oaDriverEntity);
        }

        return view;
    }

    /**
     * 跳转到新增编辑页面
     * @param oaDriverEntity
     * @param request
     * @return
     */
    @RequestMapping("/view")
    public ModelAndView view(OaDriverEntity oaDriverEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("car/viewDriver");

        if(StringUtils.isNotEmpty(oaDriverEntity.getId())) {
            oaDriverEntity = oaDriverService.getById(oaDriverEntity.getId());
            view.addObject("driver", oaDriverEntity);
        }

        return view;
    }
}
