package com.active4j.hr.info.controller;

import com.active4j.hr.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/11/15 下午11:32
 */
@Controller
@RequestMapping("info/publicFile")
@Slf4j
public class PublicFileController extends BaseController {
    /**
     *
     * @param request
     * @return
     */
    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("info/publicFile");
        return view;
    }
}
