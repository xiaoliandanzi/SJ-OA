package com.active4j.hr.item.controller;

import com.active4j.hr.activiti.entity.WorkflowCategoryEntity;
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
import com.active4j.hr.item.entity.GetItemEntity;
import com.active4j.hr.item.entity.RequisitionedItemEntity;
import com.active4j.hr.item.service.GetItemService;
import com.active4j.hr.item.service.RequisitionedItemService;
import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
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

@Controller
@RequestMapping("item/get")
@Slf4j
public class ItemGetController extends BaseController {

    @Autowired
    private RequisitionedItemService requisitionedItemService;

    @Autowired
    private GetItemService getItemService;


    /**
     * 跳转到新增编辑页面
     * @param getItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/apply")
    public ModelAndView addorupdate(GetItemEntity getItemEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("item/getitemapply");

        //查询领用物品
        List<RequisitionedItemEntity> lstItems = requisitionedItemService.findGetItem();
        view.addObject("lstItems", lstItems);

        if (StringUtils.isNotEmpty(getItemEntity.getId())) {
            getItemEntity = getItemService.getById(getItemEntity.getId());
            view.addObject("item", getItemEntity);
        }

        return view;
    }

    /**
     * 保存方法
     * @param getItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(GetItemEntity getItemEntity, HttpServletRequest request) {
//        ModelAndView view = new ModelAndView("system/common/success");
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isEmpty(getItemEntity.getItemName())) {
                j.setSuccess(false);
                j.setMsg("领用物品名称不能为空!");
                return j;
            }
            if(StringUtils.isEmpty(getItemEntity.getDepartmentName())) {
                j.setSuccess(false);
                j.setMsg("领用单位不能为空!");
                return j;
            }
            if(StringUtils.isEmpty(getItemEntity.getUserName())) {
                j.setSuccess(false);
                j.setMsg("领用人不能为空!");
                return j;
            }
            if(null == getItemEntity.getQuantity()) {
                j.setSuccess(false);
                j.setMsg("领用数量不能为空");
                return j;
            }

            int quantity = getItemEntity.getQuantity();
            List<RequisitionedItemEntity> stockEntity = requisitionedItemService.getItemByname(getItemEntity.getItemName());
            if (stockEntity.size() != 1) {
                j.setSuccess(false);
                j.setMsg("库存多项物品冲突");
                return j;
            }
            RequisitionedItemEntity entity = stockEntity.get(0);
             if (quantity > entity.getQuantity()) {
                j.setSuccess(false);
                j.setMsg("库存不足，剩余" + entity.getQuantity());
                return j;
            }

            entity.setQuantity(entity.getQuantity() - quantity);

            if (StringUtils.isEmpty(getItemEntity.getId())) {
                //新增方法
                getItemService.save(getItemEntity);
                requisitionedItemService.saveOrUpdate(entity);
            }else {
                //编辑方法
                GetItemEntity tmp = getItemService.getById(getItemEntity.getId());
                MyBeanUtils.copyBeanNotNull2Bean(getItemEntity, tmp);
                getItemService.saveOrUpdate(tmp);
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("保存领用登记失败，错误信息:{}", e);
        }

        return j;
    }

    /**
     * 跳转到已审批流程页面
     *
     * @param req
     * @return
     */
    @RequestMapping("/getrecord")
    public ModelAndView finishlist(HttpServletRequest req) {
        ModelAndView view = new ModelAndView("item/itemGetRecord");

        return view;
    }

    /**
     * 查询数据
     *
     * @param getItemEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(GetItemEntity getItemEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<GetItemEntity> queryWrapper = QueryUtils.installQueryWrapper(getItemEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<GetItemEntity> lstResult = getItemService.page(new Page<GetItemEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }
}
