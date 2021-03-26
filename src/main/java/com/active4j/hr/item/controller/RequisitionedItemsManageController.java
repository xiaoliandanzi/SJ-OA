package com.active4j.hr.item.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.item.entity.GetItemEntity;
import com.active4j.hr.item.entity.RequisitionedItemEntity;
import com.active4j.hr.item.service.GetItemService;
import com.active4j.hr.item.service.RequisitionedItemService;
import com.active4j.hr.system.service.SysRoleService;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.system.util.MessageUtils;
import com.active4j.hr.topic.entity.OaEditStore;
import com.active4j.hr.topic.service.OaEditStoreService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private OaEditStoreService oaEditStoreService;

    @Autowired
    private GetItemService getItemService;

    /**
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
     *
     * @param requisitionedItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(RequisitionedItemEntity requisitionedItemEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if (StringUtils.isEmpty(requisitionedItemEntity.getName())) {
                j.setSuccess(false);
                j.setMsg("物品名称不能为空!");
                return j;
            }

            if (StringUtils.isEmpty(requisitionedItemEntity.getType())) {
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

            if ("null".equals(requisitionedItemEntity.getQuantity())) {
                j.setSuccess(false);
                j.setMsg("物品数量不能为空!");
                return j;
            }

            if (StringUtils.isEmpty(requisitionedItemEntity.getUnit())) {
                j.setSuccess(false);
                j.setMsg("物品单位不能为空!");
                return j;
            }

            if (StringUtils.isEmpty(requisitionedItemEntity.getKeeper())) {
                j.setSuccess(false);
                j.setMsg("物品保管人不能为空!");
                return j;
            }

            if (StringUtils.isEmpty(requisitionedItemEntity.getLocation())) {
                j.setSuccess(false);
                j.setMsg("物品存放地点不能为空!");
                return j;
            }

            if (StringUtils.isEmpty(requisitionedItemEntity.getMinQuantity())) {
                j.setSuccess(false);
                j.setMsg("物品最低预警数量不能为空!");
                return j;
            }

            if (Integer.parseInt(requisitionedItemEntity.getMinQuantity()) >= requisitionedItemEntity.getQuantity()) {
                j.setSuccess(false);
                j.setMsg("物品数量须大于物品最低预警数量，请正确入库!");
                return j;
            }
            requisitionedItemEntity.setStatus("0");

            if (StringUtils.isEmpty(requisitionedItemEntity.getId())) {
                //新增方法
                requisitionedItemService.save(requisitionedItemEntity);

                //同步保存到oa_edit_store表格
                requisitionedItemEntity.setId(null);
                OaEditStore editStore = new OaEditStore();
                BeanUtils.copyProperties(requisitionedItemEntity,editStore);
                editStore.setAddress(requisitionedItemEntity.getLocation());
                editStore.setReceiver(requisitionedItemEntity.getKeeper());
                oaEditStoreService.save(editStore);
            } else {
                //编辑方法
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = simpleDateFormat.format(new Date());
                RequisitionedItemEntity tmp = requisitionedItemService.getById(requisitionedItemEntity.getId());
                String record = recordcreate(tmp, requisitionedItemEntity);
                MessageUtils.SendSysMessage(sysUserService.getUserByUseName(roleService.findUserByRoleName("物品管理员").get(0).getUserName()).getId(), String.format("%s, %s 编辑了库存物品: %s,变更明细:%s", date, ShiroUtils.getSessionUser().getRealName(), tmp.getName(), record));
                MyBeanUtils.copyBeanNotNull2Bean(requisitionedItemEntity, tmp);

                //同步保存到oa_edit_store表格
                requisitionedItemEntity.setId(null);
                OaEditStore editStore = new OaEditStore();
                BeanUtils.copyProperties(requisitionedItemEntity,editStore);
                editStore.setAddress(requisitionedItemEntity.getLocation());
                editStore.setReceiver(requisitionedItemEntity.getKeeper());
                oaEditStoreService.save(editStore);
                requisitionedItemService.saveOrUpdate(tmp);


            }
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("保存领用物品失败，错误信息:{}", e);
        }

        return j;
    }

    /**
     * 删除
     *
     * @param requisitionedItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(RequisitionedItemEntity requisitionedItemEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if (StringUtils.isNotEmpty(requisitionedItemEntity.getId())) {
                requisitionedItemService.removeById(requisitionedItemEntity.getId());
            }
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除领用物品失败，错误信息:{}", e);
        }
        return j;
    }

    /**
     * 跳转到新增编辑页面
     *
     * @param requisitionedItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdate")
    public ModelAndView addorupdate(RequisitionedItemEntity requisitionedItemEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("item/requisitionadd");

        if (StringUtils.isNotEmpty(requisitionedItemEntity.getId())) {
            requisitionedItemEntity = requisitionedItemService.getById(requisitionedItemEntity.getId());
            view.addObject("item", requisitionedItemEntity);
        }

        return view;
    }

    /**
     * 跳转到新增编辑页面
     *
     * @param requisitionedItemEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdatenew")
    public ModelAndView addorupdatenew(GetItemEntity getItemEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("item/itemGetEdit");

        if (StringUtils.isNotEmpty(getItemEntity.getId())) {
            getItemEntity = getItemService.getById(getItemEntity.getId());
            view.addObject("item", getItemEntity);
        }

        return view;
    }

    /**
     * 查询库存历史数据
     *
     * @param
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/item_history/datagrid")
    public void item_history_datagrid(OaEditStore oaEditStore, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<OaEditStore> queryWrapper = QueryUtils.installQueryWrapper(oaEditStore, request.getParameterMap(), dataGrid);

        IPage<OaEditStore> lstResult = oaEditStoreService.page(new Page<OaEditStore>(dataGrid.getPage(), dataGrid.getRows()),
                queryWrapper.eq("NAME", oaEditStore.getName()).select("CREATE_DATE", "RECEIVER", "NAME", "MODEL","QUANTITY", "UNIT", "ADDRESS")
                        .orderByDesc("CREATE_DATE"));
        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }



    public String recordcreate(RequisitionedItemEntity oldEntity, RequisitionedItemEntity newEntity) {
        String res = "";
        if (oldEntity.getQuantity() != newEntity.getQuantity()) {
            res += String.format("数量由 [%d] 修改为 [%d];", oldEntity.getQuantity(), newEntity.getQuantity());
        }
        if (!oldEntity.getName().equals(newEntity.getName())) {
            res += String.format("名称由 [%s] 修改为 [%s];", oldEntity.getName(), newEntity.getName());
        }
        if (!oldEntity.getMinQuantity().equals(newEntity.getMinQuantity())) {
            res += String.format("低量预警由 [%s] 修改为 [%s];", oldEntity.getMinQuantity(), newEntity.getMinQuantity());
        }
        if (!oldEntity.getUnit().equals(newEntity.getUnit())) {
            res += String.format("单位由 [%s] 修改为 [%s];", oldEntity.getUnit(), newEntity.getUnit());
        }
        if (!oldEntity.getLocation().equals(newEntity.getLocation())) {
            res += String.format("存放地点由 [%s] 修改为 [%s];", oldEntity.getLocation(), newEntity.getLocation());
        }
        if (!oldEntity.getKeeper().equals(newEntity.getKeeper())) {
            res += String.format("保管人由 [%s] 修改为 [%s];", oldEntity.getKeeper(), newEntity.getKeeper());
        }
        if (!oldEntity.getItemId().equals(newEntity.getItemId())) {
            res += String.format("编号由 [%s] 修改为 [%s];", oldEntity.getItemId(), newEntity.getItemId());
        }
        if (!oldEntity.getModel().equals(newEntity.getModel())) {
            res += String.format("规格由 [%s] 修改为 [%s];", oldEntity.getModel(), newEntity.getModel());
        }

        return res;
    }

}