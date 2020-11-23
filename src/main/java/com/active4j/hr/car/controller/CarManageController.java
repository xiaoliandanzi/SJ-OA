package com.active4j.hr.car.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.car.domain.OaBookCarDomain;
import com.active4j.hr.car.entity.OaCarBooksEntity;
import com.active4j.hr.car.entity.OaCarEntity;
import com.active4j.hr.car.service.OaCarBooksService;
import com.active4j.hr.car.service.OaCarService;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.alibaba.fastjson.JSON;
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
import java.util.*;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/11/17 下午11:17
 */
@Controller
@RequestMapping("car/manage")
@Slf4j
public class CarManageController extends BaseController {

    @Autowired
    private OaCarService oaCarService;
    @Autowired
    private OaCarBooksService oaCarBooksService;



    /**
     * 车辆管理列表
     * @return
     */
    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("car/carManage");
        return view;
    }


    /**
     * 查询数据
     *
     * @param oaCarEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(OaCarEntity oaCarEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<OaCarEntity> queryWrapper = QueryUtils.installQueryWrapper(oaCarEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<OaCarEntity> lstResult = oaCarService.page(new Page<OaCarEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

    /**
     * 保存方法
     * @param oaCarEntity
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(OaCarEntity oaCarEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isEmpty(oaCarEntity.getCarId())) {
                j.setSuccess(false);
                j.setMsg("车牌号不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(oaCarEntity.getName())) {
                j.setSuccess(false);
                j.setMsg("车辆名称不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(oaCarEntity.getId())) {
                //新增方法
                oaCarService.save(oaCarEntity);
            }else {
                //编辑方法
                OaCarEntity tmp = oaCarService.getById(oaCarEntity.getId());
                MyBeanUtils.copyBeanNotNull2Bean(oaCarEntity, tmp);
                oaCarService.saveOrUpdate(tmp);
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("保存车辆失败，错误信息:{}", e);
        }

        return j;
    }

    /**
     * 删除
     * @param oaCarEntity
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(OaCarEntity oaCarEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isNotEmpty(oaCarEntity.getId())) {
                oaCarService.removeById(oaCarEntity.getId());
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除车辆失败，错误信息:{}", e);
        }
        return j;
    }

    /**
     * 跳转到新增编辑页面
     * @param oaCarEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdate")
    public ModelAndView addorupdate(OaCarEntity oaCarEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("car/carAdd");

        if(StringUtils.isNotEmpty(oaCarEntity.getId())) {
            oaCarEntity = oaCarService.getById(oaCarEntity.getId());
            view.addObject("car", oaCarEntity);
        }

        return view;
    }

    /**
     * 预定车辆
     * @param oaCarEntity
     * @param request
     * @return
     */
    @RequestMapping("/bookview")
    public ModelAndView bookview(OaCarEntity oaCarEntity, String currentDate, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("car/carBooks");

        if(StringUtils.isNotEmpty(oaCarEntity.getId())) {
            oaCarEntity = oaCarService.getById(oaCarEntity.getId());
            view.addObject("carId", oaCarEntity.getCarId());
            view.addObject("carName", oaCarEntity.getName());
            view.addObject("id", oaCarEntity.getId());
            if(StringUtils.isNotEmpty(currentDate)) {
                Date bookDate = DateUtils.str2Date(currentDate, DateUtils.SDF_YYYY_MM_DD);
                view.addObject("bookDate", bookDate);
            }
        }

        return view;
    }

    /**
     * 查看车辆预定情况
     *
     * @param oaCarEntity
     * @param request
     * @return
     */
    @RequestMapping("/view")
    public ModelAndView view(OaCarEntity oaCarEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("car/viewBooks");
        view.addObject("lstBooks", "-1");
        if(StringUtils.isNotEmpty(oaCarEntity.getId())) {
            oaCarEntity = oaCarService.getById(oaCarEntity.getId());
            view.addObject("carName", oaCarEntity.getName());
            view.addObject("id", oaCarEntity.getId());
            view.addObject("carId", oaCarEntity.getCarId());

            List<OaCarBooksEntity> lstBooks = oaCarBooksService.findCarBooks(oaCarEntity);
            List<OaBookCarDomain> lstBookDoamins = new ArrayList<OaBookCarDomain>();
            if(null != lstBooks && lstBooks.size() > 0) {
                for(OaCarBooksEntity book : lstBooks) {
                    OaBookCarDomain domain = new OaBookCarDomain();
                    domain.setId(book.getId());
                    domain.setTitle("预定人:" + book.getCreateName());
                    String startTime = DateUtils.date2Str(book.getStartDate(), DateUtils.SDF_HHMM);
                    String endTime = DateUtils.date2Str(book.getEndDate(), DateUtils.SDF_HHMM);
                    domain.setStart(book.getStrBookDate() + " " + startTime);
                    domain.setEnd(book.getStrBookDate() + " " + endTime);
                    lstBookDoamins.add(domain);
                }
                view.addObject("lstBooks", JSON.toJSONString(lstBookDoamins));
            }
        }

        //查询可用的会议室
        List<OaCarEntity> lstCars = oaCarService.findNormalCar();
        view.addObject("lstCars", lstCars);

        return view;
    }

    @RequestMapping("/getCarView")
    @ResponseBody
    public AjaxJson getCarView(OaCarEntity oaCarEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            Map<String, Object> map = new HashMap<String, Object>();

            if(StringUtils.isNotEmpty(oaCarEntity.getId())) {
                oaCarEntity = oaCarService.getById(oaCarEntity.getId());
                map.put("carName", oaCarEntity.getName());
                map.put("carId", oaCarEntity.getCarId());

                List<OaCarBooksEntity> lstBooks = oaCarBooksService.findCarBooks(oaCarEntity);
                List<OaBookCarDomain> lstBookDoamins = new ArrayList<OaBookCarDomain>();
                if(null != lstBooks && lstBooks.size() > 0) {
                    for(OaCarBooksEntity book : lstBooks) {
                        OaBookCarDomain domain = new OaBookCarDomain();
                        domain.setId(book.getId());
                        domain.setTitle("预定人:" + book.getCreateName());
                        String startTime = DateUtils.date2Str(book.getStartDate(), DateUtils.SDF_HHMM);
                        String endTime = DateUtils.date2Str(book.getEndDate(), DateUtils.SDF_HHMM);
                        domain.setStart(book.getStrBookDate() + " " + startTime);
                        domain.setEnd(book.getStrBookDate() + " " + endTime);
                        lstBookDoamins.add(domain);
                    }
                }
                map.put("lstBooks", lstBookDoamins);
            }

            j.setAttributes(map);

        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("查询车辆预定失败，错误信息:{}", e);
        }

        return j;
    }
}
