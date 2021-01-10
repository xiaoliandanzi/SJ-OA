package com.active4j.hr.item.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.item.entity.RequisitionedItemEntity;
import com.active4j.hr.item.service.RequisitionedItemService;
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
 * @Date: 2020/11/18/16:05
 * @Description: Requisitioned items in storage
 */
@Controller
@RequestMapping("item/manage/requisition")
@Slf4j
public class RequisitionedItemsManageController extends BaseController {

    @Autowired
    private RequisitionedItemService requisitionedItemService;

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("item/requisitioninmanage");
        return view;
    }

    /**
     * 查询数据
     *
     * @param requisitionedItemEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(RequisitionedItemEntity requisitionedItemEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<RequisitionedItemEntity> queryWrapper = QueryUtils.installQueryWrapper(requisitionedItemEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<RequisitionedItemEntity> lstResult = requisitionedItemService.page(new Page<RequisitionedItemEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

    /**
     * 保存方法
     * @param requisitionedItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(RequisitionedItemEntity requisitionedItemEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isEmpty(requisitionedItemEntity.getName())) {
                j.setSuccess(false);
                j.setMsg("物品名称不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(requisitionedItemEntity.getType())) {
                j.setSuccess(false);
                j.setMsg("物品类型种类不能为空!");
                return j;
            }

//            if(StringUtils.isEmpty(requisitionedItemEntity.getModel())) {
//                j.setSuccess(false);
//                j.setMsg("规格不能为空!");
//                return j;
//            }

//            if(StringUtils.isEmpty(requisitionedItemEntity.getItemId())) {
//                j.setSuccess(false);
//                j.setMsg("编号不能为空!");
//                return j;
//            }

            if("null" .equals(requisitionedItemEntity.getQuantity())) {
                j.setSuccess(false);
                j.setMsg("物品数量不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(requisitionedItemEntity.getUnit())) {
                j.setSuccess(false);
                j.setMsg("物品单位不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(requisitionedItemEntity.getKeeper())) {
                j.setSuccess(false);
                j.setMsg("物品保管人不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(requisitionedItemEntity.getLocation())) {
                j.setSuccess(false);
                j.setMsg("物品存放地点不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(requisitionedItemEntity.getMinQuantity())) {
                j.setSuccess(false);
                j.setMsg("物品最低预警数量不能为空!");
                return j;
            }


            if(StringUtils.isEmpty(requisitionedItemEntity.getId())) {
                //新增方法
                requisitionedItemService.save(requisitionedItemEntity);
            }else {
                //编辑方法
                RequisitionedItemEntity tmp = requisitionedItemService.getById(requisitionedItemEntity.getId());
                MyBeanUtils.copyBeanNotNull2Bean(requisitionedItemEntity, tmp);
                requisitionedItemService.saveOrUpdate(tmp);
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("保存领用物品失败，错误信息:{}", e);
        }

        return j;
    }

    /**
     * 删除
     * @param requisitionedItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(RequisitionedItemEntity requisitionedItemEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isNotEmpty(requisitionedItemEntity.getId())) {
                requisitionedItemService.removeById(requisitionedItemEntity.getId());
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除领用物品失败，错误信息:{}", e);
        }
        return j;
    }

    /**
     * 跳转到新增编辑页面
     * @param requisitionedItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdate")
    public ModelAndView addorupdate(RequisitionedItemEntity requisitionedItemEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("item/requisitionadd");

        if(StringUtils.isNotEmpty(requisitionedItemEntity.getId())) {
            requisitionedItemEntity = requisitionedItemService.getById(requisitionedItemEntity.getId());
            view.addObject("item", requisitionedItemEntity);
        }

        return view;
    }

}