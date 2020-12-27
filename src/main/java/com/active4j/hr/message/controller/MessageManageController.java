package com.active4j.hr.message.controller;

import com.active4j.hr.activiti.biz.entity.FlowMessageApprovalEntity;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.SysConstant;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.message.service.OaMessageService;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.system.util.SystemUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/22 下午11:03
 */
@Controller
@RequestMapping("message/manage")
@Slf4j
public class MessageManageController extends BaseController {

    @Autowired
    private OaMessageService oaMessageService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDeptService sysDeptService;


    /**
     * 车辆管理列表
     * @return
     */
    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("message/messageManage");
        return view;
    }


    /**
     * 查询数据
     *
     * @param flowMessageApprovalEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(FlowMessageApprovalEntity flowMessageApprovalEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<FlowMessageApprovalEntity> queryWrapper = QueryUtils.installQueryWrapper(flowMessageApprovalEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<FlowMessageApprovalEntity> lstResult = oaMessageService.page(new Page<FlowMessageApprovalEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        long total = lstResult.getTotal();
        long tempTotal = 0;
        List<FlowMessageApprovalEntity> newList = new ArrayList<>();
        //获取当前用户id
        String userId = ShiroUtils.getSessionUserId();
        //获取当前用户个人资料
        SysUserEntity user = sysUserService.getById(userId);

        List<SysRoleEntity> roles = sysUserService.getUserRoleByUserId(userId);
        if (total > 0) {
            for(FlowMessageApprovalEntity entity : lstResult.getRecords()) {
                //只显示审批完成的文件
                if (checkSameDept(entity, user, roles)) {
                    newList.add(entity);
                    tempTotal++;
                }
            }
            lstResult.setTotal(tempTotal);
            lstResult.setRecords(newList);

        }
        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

    private boolean checkSameDept(FlowMessageApprovalEntity entity, SysUserEntity user, List<SysRoleEntity> roles) {
        try {

            boolean isManager = false;
            boolean isZhuguan = false;
            boolean isZhuyao = false;
            String leadDeptName = "";
            String deptName = "";

            for (SysRoleEntity role : roles) {
                if (role.getRoleName().contains("科室负责人")){
                    isManager = true;
                    deptName = StringUtils.substringBefore(role.getRoleName(), "科室负责人");
                }
                if (role.getRoleName().contains("主管领导")){
                    isZhuguan = true;
                    leadDeptName = StringUtils.substringBefore(role.getRoleName(), "主管领导");
                }
                if (role.getRoleName().contains("主要领导")){
                    isZhuyao = true;
                }
            }

            if (isZhuyao) {
                return true;
            }

            //发文信息
            // 科室
            SysUserEntity paperUser = sysUserService.getUserByUseName(entity.getCreateName());
            if (paperUser == null) {
                return false;
            }
            SysDeptEntity paperDept = sysDeptService.getById(paperUser.getDeptId());
            // 几处
            SysDeptEntity parentDept = sysDeptService.getById(paperDept.getParentId());

            //判断主管领导
            if (isZhuguan && parentDept.getName().contains(leadDeptName)) {
                return true;
            }

            //判断科室负责人
            if (isManager && paperDept.getName().contains(deptName)) {
                return true;
            }

            //判断科员
            return user.getId().equalsIgnoreCase(paperUser.getId());
        }catch (Exception ex) {
            log.error("checkSameDept", ex);
        }
        return false;
    }

    private String getMessageTypeName(int messageType) {
        List<SysDicValueEntity> types = SystemUtils.getDictionaryLst(SysConstant.DIC_MESSAGE_TYPE);
        for (SysDicValueEntity entity : types) {
            if (String.valueOf(messageType).equalsIgnoreCase(entity.getValue())) {
                return entity.getLabel();
            }
        }
        return String.valueOf(messageType);
    }

    /**
     * 跳转到新增编辑页面
     * @param flowMessageApprovalEntity
     * @param request
     * @return
     */
    @RequestMapping("/addorupdate")
    public ModelAndView paperView(FlowMessageApprovalEntity flowMessageApprovalEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("message/messageView");

        if(StringUtils.isNotEmpty(flowMessageApprovalEntity.getId())) {
            flowMessageApprovalEntity = oaMessageService.getById(flowMessageApprovalEntity.getId());
            view.addObject("biz", flowMessageApprovalEntity);
        }

        return view;
    }

}
