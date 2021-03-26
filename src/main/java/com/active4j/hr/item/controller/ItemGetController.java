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
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.model.SysUserModel;
import com.active4j.hr.system.service.SysRoleService;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.system.util.MessageUtils;
import com.active4j.hr.system.util.SystemUtils;
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
import java.util.Set;

@Controller
@RequestMapping("item/get")
@Slf4j
public class ItemGetController extends BaseController {

    @Autowired
    private RequisitionedItemService requisitionedItemService;

    @Autowired
    private GetItemService getItemService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService roleService;



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
        GetItemEntity tmpEntity = new GetItemEntity();
        //获取当前用户id
        String userId = ShiroUtils.getSessionUserId();
        //获取当前用户个人资料
        SysUserModel user = sysUserService.getInfoByUserId(userId).get(0);
//        SysDeptEntity department = sysUserService.getUserDepart(userId);


        if (StringUtils.isNotEmpty(getItemEntity.getId())) {
            getItemEntity = getItemService.getById(getItemEntity.getId());
            getItemEntity.setUserName(user.getRealName());
            getItemEntity.setDepartmentName(user.getDeptName());
            view.addObject("item", getItemEntity);
        }else {
            GetItemEntity item = new GetItemEntity();
            getItemEntity.setUserName(user.getRealName());
            getItemEntity.setDepartmentName(user.getDeptName());
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
            if (!SystemUtils.getWeekOfDate(getItemEntity.getGetDay()).equals("星期二")){
                j.setSuccess(false);
                j.setMsg("领用日期只能为星期二!");
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
            QueryWrapper<RequisitionedItemEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("NUMLIMIT").eq("TYPE",0).eq("NAME",getItemEntity.getItemName());
            List<RequisitionedItemEntity> list = requisitionedItemService.list(queryWrapper);
            if(getItemEntity.getQuantity()>list.get(0).getNumLimit()) {
                j.setSuccess(false);
                j.setMsg("领用数量不能大于限额："+ list.get(0).getNumLimit());
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
            //低于阈值，修改状态
            if(entity.getQuantity() <= Integer.parseInt(entity.getMinQuantity()) && Integer.parseInt(entity.getStatus()) == 0){
                MessageUtils.SendSysMessage(sysUserService.getUserByUseName(roleService.findUserByRoleName("物品管理员").get(0).getUserName()).getId(),String.format("%s仅剩%s个，已低于低量预警线%s个，请及时补充",entity.getName(),entity.getQuantity(),entity.getMinQuantity()));
                entity.setStatus("1");
            }

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
        //获取当前用户的部门
        String userDept = ShiroUtils.getSessionUserDept();
        Set userRole = ShiroUtils.getSessionUserRole();

        QueryWrapper<SysRoleEntity> wrapper = new QueryWrapper<>();
        wrapper.select("ROLE_CODE").eq("ROLE_NAME", "物品管理员");
        List<SysRoleEntity> list = roleService.list(wrapper);
        if (userRole.contains(list.get(0).getRoleCode())){
            // 拼接查询条件
            QueryWrapper<GetItemEntity> queryWrapper = QueryUtils.installQueryWrapper(getItemEntity, request.getParameterMap(), dataGrid);
            // 执行查询
            IPage<GetItemEntity> lstResult = getItemService.page(new Page<GetItemEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

            // 输出结果
            ResponseUtil.writeJson(response, dataGrid, lstResult);
        }else {
            // 拼接查询条件
            QueryWrapper<GetItemEntity> queryWrapper = QueryUtils.installQueryWrapper(getItemEntity, request.getParameterMap(), dataGrid);
            // 执行查询
            IPage<GetItemEntity> lstResult = getItemService.page(new Page<GetItemEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper.eq("DEPARTMENTNAME",userDept));

            // 输出结果
            ResponseUtil.writeJson(response, dataGrid, lstResult);
        }

    }
}
