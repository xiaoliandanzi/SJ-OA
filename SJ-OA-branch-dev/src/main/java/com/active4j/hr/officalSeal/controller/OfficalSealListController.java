package com.active4j.hr.officalSeal.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.officalSeal.entity.OaOfficalSealEntity;
import com.active4j.hr.officalSeal.service.OaOfficalSealBookService;
import com.active4j.hr.officalSeal.service.OaOfficalSealService;
import com.active4j.hr.work.domain.OaWorkBookRoomDomain;
import com.active4j.hr.work.entity.OaWorkMeetRoomBooksEntity;

import com.active4j.hr.work.service.OaWorkMeetRoomBooksService;
import com.active4j.hr.work.service.OaWorkMeetRoomService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/20/18:56
 * @Description:
 */
@Controller
@RequestMapping("officalSeal/list")
@Slf4j
public class OfficalSealListController extends BaseController {
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
        ModelAndView view = new ModelAndView("officalSeal/officalSealList");
        return view;
    }

}
