package com.active4j.hr.asset.controller;

import com.active4j.hr.asset.entity.OaAssetStoreEntity;
import com.active4j.hr.asset.service.OaAssetService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.topic.entity.OaEditStore;
import com.active4j.hr.topic.service.OaEditStoreService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
 * @author xfzhang
 * @version 1.0
 * @date 2021/1/11 上午12:11
 */
@Controller
@RequestMapping("asset/assetmanage")
@Slf4j
public class AssetManageController extends BaseController {

    @Autowired
    private OaAssetService oaAssetService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private OaEditStoreService oaEditStoreService;

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("asset/assetmanage");
        return view;
    }

    /**
     * 查询数据
     *
     * @param oaAssetStoreEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(OaAssetStoreEntity oaAssetStoreEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<OaAssetStoreEntity> queryWrapper = QueryUtils.installQueryWrapper(oaAssetStoreEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<OaAssetStoreEntity> lstResult = oaAssetService.page(new Page<OaAssetStoreEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

    /**
     * 保存方法
     * @param oaAssetStoreEntity
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxJson save(OaAssetStoreEntity oaAssetStoreEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
//
//            String jsonData = oaAssetStoreEntity.getJsonData();
//            JSONArray array;
//            if(StringUtils.isEmpty(jsonData)){
//                array = new JSONArray();
//            }else{
//               array = JSON.parseArray(jsonData);
//            }
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
//            array.add(sdf.format(new Date())+"，"+ShiroUtils.getSessionUser().getRealName()+"修改");
//
//            oaAssetStoreEntity.setJsonData(array.toJSONString());
//
            if(StringUtils.isEmpty(oaAssetStoreEntity.getAssetName())) {
                j.setSuccess(false);
                j.setMsg("资产名称不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(oaAssetStoreEntity.getAddress())) {
                j.setSuccess(false);
                j.setMsg("库存地址不能为空!");
                return j;
            }

            if("null" .equals(oaAssetStoreEntity.getQuantity())) {
                j.setSuccess(false);
                j.setMsg("物品数量不能为空!");
                return j;
            }
            if("null" .equals(oaAssetStoreEntity.getAmount())) {
                j.setSuccess(false);
                j.setMsg("价格不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(oaAssetStoreEntity.getDept())) {
                j.setSuccess(false);
                j.setMsg("科室不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(oaAssetStoreEntity.getModel())) {
                j.setSuccess(false);
                j.setMsg("规格/型号不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(oaAssetStoreEntity.getReceiver())) {
                j.setSuccess(false);
                j.setMsg("保管人不能为空!");
                return j;
            }

            if(StringUtils.isEmpty(oaAssetStoreEntity.getId())) {
                //新增方法
                oaAssetService.save(oaAssetStoreEntity);

                //同步保存到oa_edit_store表格
                oaAssetStoreEntity.setId(null);
                OaEditStore editStore = new OaEditStore();
                BeanUtils.copyProperties(oaAssetStoreEntity,editStore);
                oaEditStoreService.save(editStore);
            }else {
                //编辑方法
                OaAssetStoreEntity tmp = oaAssetService.getById(oaAssetStoreEntity.getId());
                MyBeanUtils.copyBeanNotNull2Bean(oaAssetStoreEntity, tmp);
                String userId =  ShiroUtils.getSessionUserId();
                SysUserEntity user = sysUserService.getById(userId);
                if(!StringUtils.equals(oaAssetStoreEntity.getReceiver(), user.getRealName())) {
                    j.setSuccess(false);
                    j.setMsg("不是您自己保管的资产不能编辑");
                    return j;
                }
                oaAssetService.saveOrUpdate(tmp);

                //同步保存到oa_edit_store表格
                oaAssetStoreEntity.setId(null);
                OaEditStore editStore = new OaEditStore();
                BeanUtils.copyProperties(oaAssetStoreEntity,editStore);
                oaEditStoreService.save(editStore);
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("资产入库失败，错误信息:{}", e);
        }

        return j;
    }

    /**
     * 删除
     * @param oaAssetStoreEntity
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(OaAssetStoreEntity oaAssetStoreEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try{
            if(StringUtils.isNotEmpty(oaAssetStoreEntity.getId())) {
                oaAssetStoreEntity = oaAssetService.getById(oaAssetStoreEntity.getId());
                String userId =  ShiroUtils.getSessionUserId();
                SysUserEntity user = sysUserService.getById(userId);
                if(!StringUtils.equals(oaAssetStoreEntity.getReceiver(), user.getRealName())) {
                    j.setSuccess(false);
                    j.setMsg("不是您自己保管的资产不能删除");
                    return j;
                }
                oaAssetService.removeById(oaAssetStoreEntity.getId());
            }
        }catch(Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除固定资产失败，错误信息:{}", e);
        }
        return j;
    }

    /**
     * 跳转到新增编辑页面
     * @param oaAssetStoreEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdate")
    public ModelAndView addorupdate(OaAssetStoreEntity oaAssetStoreEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("asset/assetadd");

        if(StringUtils.isNotEmpty(oaAssetStoreEntity.getId())) {
            oaAssetStoreEntity = oaAssetService.getById(oaAssetStoreEntity.getId());
            view.addObject("asset", oaAssetStoreEntity);
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
    @RequestMapping("/asset_history/datagrid")
    public void asset_history_datagrid(OaEditStore oaEditStore, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<OaEditStore> queryWrapper = QueryUtils.installQueryWrapper(oaEditStore, request.getParameterMap(), dataGrid);

        IPage<OaEditStore> lstResult = oaEditStoreService.page(new Page<OaEditStore>(dataGrid.getPage(), dataGrid.getRows()),
                queryWrapper.eq("ASSETNAME", oaEditStore.getAssetName()).select("CREATE_DATE", "RECEIVER", "ASSETNAME", "MODEL","QUANTITY", "AMOUNT", "ADDRESS")
                        .orderByDesc("CREATE_DATE"));
        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }
}
