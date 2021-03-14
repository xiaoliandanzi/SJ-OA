package com.active4j.hr.officalSeal.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.officalSeal.entity.OaOfficalSealBookEntity;
import com.active4j.hr.officalSeal.entity.OaOfficalSealEntity;
import com.active4j.hr.officalSeal.service.OaOfficalSealBookService;
import com.active4j.hr.officalSeal.service.OaOfficalSealService;
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
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/12/14/21:08
 * @Description:
 */
@Controller
@RequestMapping("officalSeal/officalSealBooks")
@Slf4j
public class officalSealBookController  extends BaseController {
    @Autowired
    private OaOfficalSealBookService oaOfficalSealBookService;
    @Autowired
    private OaOfficalSealService oaOfficalSealService;

    /**
     * 车辆预定列表
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) {

        ModelAndView view = new ModelAndView("officalSeal/officalBooksList");
        List<OaOfficalSealEntity> lstSeals = oaOfficalSealService.findNormalSeal();
        view.addObject("lstSeals", ListUtils.listToReplaceStr(lstSeals, "name", "id"));

        String nowStrDate = DateUtils.date2Str(DateUtils.SDF_YYYY_MM_DD);
        view.addObject("nowStrDate", nowStrDate);

        return view;
    }

    /**
     * 查询数据
     * @param oaOfficalSealBookEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(OaOfficalSealBookEntity oaOfficalSealBookEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<OaOfficalSealBookEntity> queryWrapper = QueryUtils.installQueryWrapper(oaOfficalSealBookEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<OaOfficalSealBookEntity> lstResult = oaOfficalSealBookService.page(new Page<OaOfficalSealBookEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

    /**
     * 跳转到新增编辑页面
     * @param oaOfficalSealBookEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdate")
    public ModelAndView addorupdate(OaOfficalSealBookEntity oaOfficalSealBookEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("officalSeal/officalSealApply");

        //查询可用的公章
        List<OaOfficalSealEntity> lstSeals = oaOfficalSealService.findNormalSeal();
        view.addObject("lstSeals", lstSeals);

        if(StringUtils.isNotEmpty(oaOfficalSealBookEntity.getId())) {
            oaOfficalSealBookEntity = oaOfficalSealBookService.getById(oaOfficalSealBookEntity.getId());
            view.addObject("seal", oaOfficalSealBookEntity);
        }

        return view;
    }


    /**
     * 保存方法
     * @param oaOfficalSealBookEntity
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(OaOfficalSealBookEntity oaOfficalSealBookEntity, String id, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{

            //预定人赋值
            oaOfficalSealBookEntity.setUserName(ShiroUtils.getSessionUser().getRealName());
            oaOfficalSealBookEntity.setUserId(ShiroUtils.getSessionUserId());

            if(null == oaOfficalSealBookEntity.getDepartmentName()) {
                j.setSuccess(false);
                j.setMsg("使用科室不能为空");
                return j;
            }

            if(null == oaOfficalSealBookEntity.getUseUnit()) {
                j.setSuccess(false);
                j.setMsg("主送单位不能为空");
                return j;
            }

            if(null == oaOfficalSealBookEntity.getContent()) {
                j.setSuccess(false);
                j.setMsg("内容不能为空");
                return j;
            }

            if(null == oaOfficalSealBookEntity.getBookDate()) {
                j.setSuccess(false);
                j.setMsg("预定日期不能为空");
                return j;
            }

            if(null == oaOfficalSealBookEntity.getStartDate() || null == oaOfficalSealBookEntity.getEndDate()) {
                j.setSuccess(false);
                j.setMsg("请填写完整的预定时间");
                return j;
            }

            //日期赋值
            oaOfficalSealBookEntity.setStrBookDate(DateUtils.date2Str(oaOfficalSealBookEntity.getBookDate(), DateUtils.SDF_YYYY_MM_DD));

            //时间的校验，借用公章，时间不能重合
            List<OaOfficalSealBookEntity> lstSeals = oaOfficalSealBookService.findSealBooks(id, oaOfficalSealBookEntity.getStrBookDate());
            if(null != lstSeals && lstSeals.size() > 0) {
                for(OaOfficalSealBookEntity bookSeal : lstSeals) {
                    if(oaOfficalSealBookEntity.getStartDate().after(bookSeal.getStartDate()) && oaOfficalSealBookEntity.getStartDate().before(bookSeal.getEndDate())) {
                        j.setSuccess(false);
                        j.setMsg("当前公章已被借用");
                        return j;
                    }
                    if(oaOfficalSealBookEntity.getEndDate().after(bookSeal.getStartDate()) && oaOfficalSealBookEntity.getEndDate().before(bookSeal.getEndDate())) {
                        j.setSuccess(false);
                        j.setMsg("当前公章已被借用");
                        return j;
                    }
                }
            }

            if(StringUtils.isEmpty(oaOfficalSealBookEntity.getId())) {
                if(StringUtils.isNotEmpty(id)) {
                    OaOfficalSealEntity room = oaOfficalSealService.getById(id);
                    if(StringUtils.equals(room.getStatus(), GlobalConstant.OA_OFFICALSEAL_STATUS_STOP)) {
                        j.setSuccess(false);
                        j.setMsg("公章不可用");
                        return j;
                    }
                    oaOfficalSealBookEntity.setId(id);
                }

                //新增方法
                oaOfficalSealBookService.save(oaOfficalSealBookEntity);


            }else {
                //编辑方法
                OaOfficalSealBookEntity tmp = oaOfficalSealBookService.getById(oaOfficalSealBookEntity.getId());
                MyBeanUtils.copyBeanNotNull2Bean(oaOfficalSealBookEntity, tmp);

                if(StringUtils.isNotEmpty(id)) {
                    OaOfficalSealEntity seal = oaOfficalSealService.getById(id);
                    if(StringUtils.equals(seal.getStatus(), GlobalConstant.OA_CAR_STATUS_STOP)) {
                        j.setSuccess(false);
                        j.setMsg("公章不可用");
                        return j;
                    }
                    tmp.setId(id);
                }

                oaOfficalSealBookService.saveOrUpdate(tmp);

            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("保存公章预定信息失败，错误信息:{}", e);
        }
        return j;
    }

    /**
     * 删除
     * @param oaOfficalSealBookEntity
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(OaOfficalSealBookEntity oaOfficalSealBookEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isNotEmpty(oaOfficalSealBookEntity.getId())) {
                oaOfficalSealBookEntity = oaOfficalSealBookService.getById(oaOfficalSealBookEntity.getId());

                //不是自己预订的车辆不能删除
                if(!StringUtils.equals(oaOfficalSealBookEntity.getUserId(), ShiroUtils.getSessionUserId())) {
                    j.setSuccess(false);
                    j.setMsg("不是自己预订的车辆不能删除");
                    return j;
                }
                oaOfficalSealBookService.removeById(oaOfficalSealBookEntity.getId());
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除公章借用信息失败，错误信息:{}", e);
        }

        return j;
    }
}
