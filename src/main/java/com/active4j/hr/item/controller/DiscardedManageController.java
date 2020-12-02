package com.active4j.hr.item.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.item.entity.DiscardedItemEntity;
import com.active4j.hr.item.service.DiscardedItemService;
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

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/18/15:56
 * @Description: Article scrap management
 */
@Controller
@RequestMapping("item/manage/discarded")
@Slf4j
public class DiscardedManageController extends BaseController {

    @Autowired
    private DiscardedItemService discardedItemService;

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("item/discardedmanage");
        return view;
    }

    /**
     * 查询数据
     *
     * @param discardedItemEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(DiscardedItemEntity discardedItemEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<DiscardedItemEntity> queryWrapper = QueryUtils.installQueryWrapper(discardedItemEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<DiscardedItemEntity> lstResult = discardedItemService.page(new Page<DiscardedItemEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

    /**
     * 保存方法
     * @param discardedItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(DiscardedItemEntity discardedItemEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isEmpty(discardedItemEntity.getName())) {
                j.setSuccess(false);
                j.setMsg("名称不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(discardedItemEntity.getType())) {
                j.setSuccess(false);
                j.setMsg("种类不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(discardedItemEntity.getBoard())) {
                j.setSuccess(false);
                j.setMsg("品牌不能为空!");
                return j;
            }


            if(StringUtils.isEmpty(discardedItemEntity.getId())) {
                //新增方法
                discardedItemService.save(discardedItemEntity);
            }else {
                //编辑方法
                DiscardedItemEntity tmp = discardedItemService.getById(discardedItemEntity.getId());
                MyBeanUtils.copyBeanNotNull2Bean(discardedItemEntity, tmp);
                discardedItemService.saveOrUpdate(tmp);
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("保存报废物品失败，错误信息:{}", e);
        }

        return j;
    }

    /**
     * 删除
     * @param discardedItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(DiscardedItemEntity discardedItemEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isNotEmpty(discardedItemEntity.getId())) {
                discardedItemService.removeById(discardedItemEntity.getId());
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除报废物品失败，错误信息:{}", e);
        }
        return j;
    }

    /**
     * 跳转到新增编辑页面
     * @param discardedItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdate")
    public ModelAndView addorupdate(DiscardedItemEntity discardedItemEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("item/discardedadd");

        if(StringUtils.isNotEmpty(discardedItemEntity.getId())) {
            discardedItemEntity = discardedItemService.getById(discardedItemEntity.getId());
            view.addObject("item", discardedItemEntity);
        }

        return view;
    }

}
