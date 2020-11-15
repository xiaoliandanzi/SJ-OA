package com.active4j.hr.car.controller;

import com.active4j.hr.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/11/16 上午1:29
 */
@Controller
@RequestMapping("car/return")
@Slf4j
public class CarReturnController extends BaseController {

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("car/carReturn");
        return view;
    }
}
