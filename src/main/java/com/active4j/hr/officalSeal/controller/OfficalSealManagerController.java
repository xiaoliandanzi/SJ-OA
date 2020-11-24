package com.active4j.hr.officalSeal.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.car.entity.OaCarEntity;
import com.active4j.hr.car.service.OaCarBooksService;
import com.active4j.hr.car.service.OaCarService;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.officalSeal.entity.OaOfficalSealEntity;
import com.active4j.hr.officalSeal.service.OaOfficalSealBookService;
import com.active4j.hr.officalSeal.service.OaOfficalSealService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/19/18:28
 * @Description:
 */
@Controller
@RequestMapping("officalSeal/manager")
@Slf4j
public class OfficalSealManagerController extends BaseController {

    @Autowired
    private OaOfficalSealService oaOfficalSealService;
    @Autowired
    private OaOfficalSealBookService oaOfficalSealBookService;


    /**
     *
     * @param request
     * @return
     */
    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("officalSeal/officalSealManager");
        return view;
    }

    /**
     * 查询数据
     *
     * @param oaOfficalSealEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(OaOfficalSealEntity oaOfficalSealEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<OaOfficalSealEntity> queryWrapper = QueryUtils.installQueryWrapper(oaOfficalSealEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<OaOfficalSealEntity> lstResult = oaOfficalSealService.page(new Page<OaOfficalSealEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

}