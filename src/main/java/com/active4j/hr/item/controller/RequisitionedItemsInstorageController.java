package com.active4j.hr.item.controller;

import com.active4j.hr.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/18/16:05
 * @Description: Requisitioned items in storage
 */
@Controller
@RequestMapping("item/requisition/instorage")
@Slf4j
public class RequisitionedItemsInstorageController extends BaseController {

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("item/requisitioninstorage");
        return view;
    }
}