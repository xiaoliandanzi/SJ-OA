package com.active4j.hr.car.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.car.entity.OaCarBooksEntity;
import com.active4j.hr.car.entity.OaCarEntity;
import com.active4j.hr.car.service.OaCarBooksService;
import com.active4j.hr.car.service.OaCarService;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ListUtils;
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
import java.util.List;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/11/22 下午8:45
 */
@Controller
@RequestMapping("/car/carBooks")
@Slf4j
public class CarBooksController extends BaseController {
    @Autowired
    private OaCarBooksService oaCarBooksService;
    @Autowired
    private OaCarService oaCarService;

    /**
     * 车辆预定列表
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) {

        ModelAndView view = new ModelAndView("car/carBooksList");
        List<OaCarEntity> lstRooms = oaCarService.findNormalCar();
        view.addObject("lstCars", ListUtils.listToReplaceStr(lstRooms, "name", "Id"));

        String nowStrDate = DateUtils.date2Str(DateUtils.SDF_YYYY_MM_DD);
        //view.addObject("nowStrDate", nowStrDate);

        return view;
    }

    /**
     * 查询数据
     * @param oaCarBooksEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(OaCarBooksEntity oaCarBooksEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<OaCarBooksEntity> queryWrapper = QueryUtils.installQueryWrapper(oaCarBooksEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<OaCarBooksEntity> lstResult = oaCarBooksService.page(new Page<OaCarBooksEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

    /**
     * 跳转到新增编辑页面
     * @param oaCarBooksEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdate")
    public ModelAndView addorupdate(OaCarBooksEntity oaCarBooksEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("car/carBooks");

        //查询可用的会议室
        List<OaCarEntity> lstCars = oaCarService.findNormalCar();
        view.addObject("lstCars", lstCars);

        if(StringUtils.isNotEmpty(oaCarBooksEntity.getId())) {
            oaCarBooksEntity = oaCarBooksService.getById(oaCarBooksEntity.getId());
            view.addObject("car", oaCarBooksEntity);
        }

        return view;
    }


    /**
     * 保存方法
     * @param oaCarBooksEntity
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(OaCarBooksEntity oaCarBooksEntity, String id, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{

            //预定人赋值
            oaCarBooksEntity.setUserName(ShiroUtils.getSessionUser().getRealName());
            oaCarBooksEntity.setUserId(ShiroUtils.getSessionUserId());

            if(null == oaCarBooksEntity.getBookDate()) {
                j.setSuccess(false);
                j.setMsg("预定日期不能为空");
                return j;
            }

            if(null == oaCarBooksEntity.getStartDate() || null == oaCarBooksEntity.getEndDate()) {
                j.setSuccess(false);
                j.setMsg("请填写完整的预定时间");
                return j;
            }

            //日期赋值
            oaCarBooksEntity.setStrBookDate(DateUtils.date2Str(oaCarBooksEntity.getBookDate(), DateUtils.SDF_YYYY_MM_DD));

            //时间的校验，预定的车辆，时间不能重合
            List<OaCarBooksEntity> lstCars = oaCarBooksService.findCarBooks(id, oaCarBooksEntity.getStrBookDate());
            if(null != lstCars && lstCars.size() > 0) {
                for(OaCarBooksEntity bookCar : lstCars) {
                    if(oaCarBooksEntity.getStartDate().after(bookCar.getStartDate()) && oaCarBooksEntity.getStartDate().before(bookCar.getEndDate())) {
                        j.setSuccess(false);
                        j.setMsg("当前车辆已经被预定");
                        return j;
                    }
                    if(oaCarBooksEntity.getEndDate().after(bookCar.getStartDate()) && oaCarBooksEntity.getEndDate().before(bookCar.getEndDate())) {
                        j.setSuccess(false);
                        j.setMsg("当前车辆已经被预定");
                        return j;
                    }
                }
            }

            if(StringUtils.isEmpty(oaCarBooksEntity.getId())) {
                if(StringUtils.isNotEmpty(id)) {
                    OaCarEntity room = oaCarService.getById(id);
                    if(StringUtils.equals(room.getStatus(), GlobalConstant.OA_CAR_STATUS_STOP)) {
                        j.setSuccess(false);
                        j.setMsg("车辆不可用");
                        return j;
                    }
                    oaCarBooksEntity.setId(id);
                }

                //新增方法
                oaCarBooksService.save(oaCarBooksEntity);


            }else {
                //编辑方法
                OaCarBooksEntity tmp = oaCarBooksService.getById(oaCarBooksEntity.getId());
                MyBeanUtils.copyBeanNotNull2Bean(oaCarBooksEntity, tmp);

                if(StringUtils.isNotEmpty(id)) {
                    OaCarEntity room = oaCarService.getById(id);
                    if(StringUtils.equals(room.getStatus(), GlobalConstant.OA_CAR_STATUS_STOP)) {
                        j.setSuccess(false);
                        j.setMsg("车辆不可用");
                        return j;
                    }
                    tmp.setId(id);
                }

                oaCarBooksService.saveOrUpdate(tmp);

            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("保存车辆预定信息失败，错误信息:{}", e);
        }
        return j;
    }

    /**
     * 删除
     * @param oaCarBooksEntity
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(OaCarBooksEntity oaCarBooksEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isNotEmpty(oaCarBooksEntity.getId())) {
                oaCarBooksEntity = oaCarBooksService.getById(oaCarBooksEntity.getId());

                //不是自己预订的车辆不能删除
                if(!StringUtils.equals(oaCarBooksEntity.getUserId(), ShiroUtils.getSessionUserId())) {
                    j.setSuccess(false);
                    j.setMsg("不是自己预订的车辆不能删除");
                    return j;
                }
                oaCarBooksService.removeById(oaCarBooksEntity.getId());
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除车辆预定信息失败，错误信息:{}", e);
        }

        return j;
    }
}
