package com.active4j.hr.item.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.item.entity.BorrowedItemEntity;
import com.active4j.hr.item.service.BorrowedItemService;
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
 * @Date: 2020/11/18/16:08
 * @Description: Borrowed item inventory
 */
@Controller
@RequestMapping("item/manage/borrowed")
@Slf4j
public class BorrowedIManageController extends BaseController {

    @Autowired
    private BorrowedItemService borrowedItemService;

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("item/borrowedmanage");
        return view;
    }

    /**
     * 查询数据
     *
     * @param borrowedItemEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(BorrowedItemEntity borrowedItemEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<BorrowedItemEntity> queryWrapper = QueryUtils.installQueryWrapper(borrowedItemEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<BorrowedItemEntity> lstResult = borrowedItemService.page(new Page<BorrowedItemEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

    /**
     * 保存方法
     * @param borrowedItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(BorrowedItemEntity borrowedItemEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isEmpty(borrowedItemEntity.getName())) {
                j.setSuccess(false);
                j.setMsg("名称不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(borrowedItemEntity.getType())) {
                j.setSuccess(false);
                j.setMsg("种类不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(borrowedItemEntity.getBoard())) {
                j.setSuccess(false);
                j.setMsg("品牌不能为空!");
                return j;
            }


            if(StringUtils.isEmpty(borrowedItemEntity.getId())) {
                //新增方法
                borrowedItemService.save(borrowedItemEntity);
            }else {
                //编辑方法
                BorrowedItemEntity tmp = borrowedItemService.getById(borrowedItemEntity.getId());
                MyBeanUtils.copyBeanNotNull2Bean(borrowedItemEntity, tmp);
                borrowedItemService.saveOrUpdate(tmp);
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("保存借用物品失败，错误信息:{}", e);
        }

        return j;
    }

    /**
     * 删除
     * @param borrowedItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(BorrowedItemEntity borrowedItemEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isNotEmpty(borrowedItemEntity.getId())) {
                borrowedItemService.removeById(borrowedItemEntity.getId());
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除借用物品失败，错误信息:{}", e);
        }
        return j;
    }

    /**
     * 跳转到新增编辑页面
     * @param borrowedItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdate")
    public ModelAndView addorupdate(BorrowedItemEntity borrowedItemEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("item/borrowedadd");

        if(StringUtils.isNotEmpty(borrowedItemEntity.getId())) {
            borrowedItemEntity = borrowedItemService.getById(borrowedItemEntity.getId());
            view.addObject("item", borrowedItemEntity);
        }

        return view;
    }

}
