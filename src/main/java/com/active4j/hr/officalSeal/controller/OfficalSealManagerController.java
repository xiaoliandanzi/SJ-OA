package com.active4j.hr.officalSeal.controller;

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
 * @Date: 2020/11/19/18:28
 * @Description:
 */
@Controller
@RequestMapping("officalSeal/manager")
@Slf4j
public class OfficalSealManagerController extends BaseController {

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

}