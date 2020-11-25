package com.active4j.hr.officalSeal.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.officalSeal.domain.OaBookSealDomain;
import com.active4j.hr.officalSeal.entity.OaOfficalSealBookEntity;
import com.active4j.hr.officalSeal.entity.OaOfficalSealEntity;
import com.active4j.hr.officalSeal.service.OaOfficalSealBookService;
import com.active4j.hr.officalSeal.service.OaOfficalSealService;
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


    /**
     * 保存方法
     * @param oaOfficalSealEntity
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(OaOfficalSealEntity oaOfficalSealEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isEmpty(oaOfficalSealEntity.getSealid())) {
                j.setSuccess(false);
                j.setMsg("公章编号不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(oaOfficalSealEntity.getName())) {
                j.setSuccess(false);
                j.setMsg("公章名称不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(oaOfficalSealEntity.getId())) {
                //新增方法
                oaOfficalSealService.save(oaOfficalSealEntity);
            }else {
                //编辑方法
                OaOfficalSealEntity tmp = oaOfficalSealService.getById(oaOfficalSealEntity.getId());
                MyBeanUtils.copyBeanNotNull2Bean(oaOfficalSealEntity, tmp);
                oaOfficalSealService.saveOrUpdate(tmp);
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("公章信息保存失败，错误信息:{}", e);
        }

        return j;
    }

    /**
     * 删除
     * @param oaOfficalSealEntity
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(OaOfficalSealEntity oaOfficalSealEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isNotEmpty(oaOfficalSealEntity.getId())) {
                oaOfficalSealService.removeById(oaOfficalSealEntity.getId());
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除公章失败，错误信息:{}", e);
        }
        return j;
    }

    /**
     * 跳转到新增编辑页面
     * @param oaOfficalSealEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdate")
    public ModelAndView addorupdate(OaOfficalSealEntity oaOfficalSealEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("officalSeal/officalAdd");

        if(StringUtils.isNotEmpty(oaOfficalSealEntity.getId())) {
            oaOfficalSealEntity = oaOfficalSealService.getById(oaOfficalSealEntity.getId());
            view.addObject("seal", oaOfficalSealEntity);
        }

        return view;
    }

    /**
     * 借用公章
     * @param oaOfficalSealEntity
     * @param request
     * @return
     */
    @RequestMapping("/bookview")
    public ModelAndView bookview(OaOfficalSealEntity oaOfficalSealEntity, String currentDate, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("officalSeal/sealBooks");

        if(StringUtils.isNotEmpty(oaOfficalSealEntity.getSealid())) {
            oaOfficalSealEntity = oaOfficalSealService.getById(oaOfficalSealEntity.getSealid());
            view.addObject("sealId", oaOfficalSealEntity.getSealid());
            view.addObject("sealName", oaOfficalSealEntity.getName());
            view.addObject("sealName", oaOfficalSealEntity.getName());
            view.addObject("id", oaOfficalSealEntity.getId());
            if(StringUtils.isNotEmpty(currentDate)) {
                Date bookDate = DateUtils.str2Date(currentDate, DateUtils.SDF_YYYY_MM_DD);
                view.addObject("bookDate", bookDate);
            }
        }

        return view;
    }

    /**
     * 查看公章借用情况
     *
     * @param oaOfficalSealEntity
     * @param request
     * @return
     */
    @RequestMapping("/view")
    public ModelAndView view(OaOfficalSealEntity oaOfficalSealEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("officalSeal/viewBooks");
        view.addObject("lstBooks", "-1");
        if(StringUtils.isNotEmpty(oaOfficalSealEntity.getSealid())) {
            oaOfficalSealEntity = oaOfficalSealService.getById(oaOfficalSealEntity.getSealid());
            view.addObject("sealName", oaOfficalSealEntity.getName());
            view.addObject("sealId", oaOfficalSealEntity.getSealid());

            List<OaOfficalSealBookEntity> lstBooks = oaOfficalSealBookService.findSealBooks(oaOfficalSealEntity);
            List<OaBookSealDomain> lstBookDoamins = new ArrayList<OaBookSealDomain>();
            if(null != lstBooks && lstBooks.size() > 0) {
                for(OaOfficalSealBookEntity book : lstBooks) {
                    OaBookSealDomain domain = new OaBookSealDomain();
                    domain.setId(book.getSealId());
                    domain.setTitle("借用人:" + book.getCreateName());
                    String startTime = DateUtils.date2Str(book.getStartDate(), DateUtils.SDF_HHMM);
                    String endTime = DateUtils.date2Str(book.getEndDate(), DateUtils.SDF_HHMM);
                    domain.setStart(book.getStrBookDate() + " " + startTime);
                    domain.setEnd(book.getStrBookDate() + " " + endTime);
                    lstBookDoamins.add(domain);
                }
                view.addObject("lstBooks", JSON.toJSONString(lstBookDoamins));
            }
        }

        //查询可用的公章
        List<OaOfficalSealEntity> lstSeals = oaOfficalSealService.findNormalSeal();
        view.addObject("lstSeals", lstSeals);

        return view;
    }

    @RequestMapping("/getSealView")
    @ResponseBody
    public AjaxJson getSealView(OaOfficalSealEntity oaOfficalSealEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            Map<String, Object> map = new HashMap<String, Object>();

            if(StringUtils.isNotEmpty(oaOfficalSealEntity.getSealid())) {
                oaOfficalSealEntity = oaOfficalSealService.getById(oaOfficalSealEntity.getSealid());
                map.put("sealName", oaOfficalSealEntity.getName());
                map.put("sealId", oaOfficalSealEntity.getSealid());

                List<OaOfficalSealBookEntity> lstBooks = oaOfficalSealBookService.findSealBooks(oaOfficalSealEntity);
                List<OaBookSealDomain> lstBookDoamins = new ArrayList<OaBookSealDomain>();
                if(null != lstBooks && lstBooks.size() > 0) {
                    for(OaOfficalSealBookEntity book : lstBooks) {
                        OaBookSealDomain domain = new OaBookSealDomain();
                        domain.setId(book.getSealId());
                        domain.setTitle("借用人:" + book.getCreateName());
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
            log.error("查询公章借用失败，错误信息:{}", e);
        }

        return j;
    }

}