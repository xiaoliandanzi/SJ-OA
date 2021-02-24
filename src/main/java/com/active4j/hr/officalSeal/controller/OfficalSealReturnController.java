package com.active4j.hr.officalSeal.controller;

import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;
import com.active4j.hr.activiti.biz.entity.FlowMessageApprovalEntity;
import com.active4j.hr.activiti.biz.entity.FlowOfficalSealApprovalEntity;
import com.active4j.hr.activiti.biz.entity.FlowPaperApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowOfficalSealApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.entity.WorkflowCategoryEntity;
import com.active4j.hr.activiti.service.WorkflowCategoryService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.activiti.util.WorkflowConstant;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.common.constant.SysConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.officalSeal.entity.OaOfficalSealEntity;
import com.active4j.hr.officalSeal.service.OaOfficalSealRecordService;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.model.SysUserModel;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.system.util.SystemUtils;
import com.active4j.hr.work.entity.OaWorkMeetEntity;
import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
import com.active4j.hr.work.entity.OaWorkMeetTypeEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/12/09/23:17
 * @Description:
 */
@Controller
@RequestMapping("officalSeal/return")
@Slf4j
public class OfficalSealReturnController extends BaseController {

    @Autowired
    private OaOfficalSealRecordService oaOfficalSealRecordService;

    @Autowired
    private FlowOfficalSealApprovalService flowOfficalSealApprovalService;


    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private WorkflowCategoryService workflowCategoryService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDeptService sysDeptService;

    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("officalSeal/officalSealReturnRecord");
        return view;
    }


    /**
     * 查询数据
     *
     * @param flowOfficalSealApprovalEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagrid")
    public void datagrid(FlowOfficalSealApprovalEntity flowOfficalSealApprovalEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        // 拼接查询条件
        QueryWrapper<FlowOfficalSealApprovalEntity> queryWrapper = QueryUtils.installQueryWrapper(flowOfficalSealApprovalEntity, request.getParameterMap(), dataGrid);
        // 执行查询
        IPage<FlowOfficalSealApprovalEntity> lstResult = flowOfficalSealApprovalService.page(new Page<FlowOfficalSealApprovalEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

        long total = lstResult.getTotal();
        long tempTotal = 0;
        List<FlowOfficalSealApprovalEntity> newList = new ArrayList<>();
        //获取当前用户id
        String userId = ShiroUtils.getSessionUserId();
        //获取当前用户个人资料
        SysUserEntity user = sysUserService.getById(userId);

        List<SysRoleEntity> roles = sysUserService.getUserRoleByUserId(userId);

        if (total > 0) {
            for (FlowOfficalSealApprovalEntity entity : lstResult.getRecords()) {
                //只显示审批完成的公章申请
                if (entity.getApplyStatus() == 1 && checkSameDept(entity, user, roles)) {
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


    private boolean checkSameDept(FlowOfficalSealApprovalEntity entity, SysUserEntity user, List<SysRoleEntity> roles) {
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


    /**
     * 跳转到新增编辑页面
     *
     * @param flowOfficalSealApprovalEntity
     * @param request
     * @return
     */
    @RequestMapping("/check")
    public ModelAndView sealCheck(FlowOfficalSealApprovalEntity flowOfficalSealApprovalEntity, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("officalSeal/officalSealRecordView");

        if (StringUtils.isNotEmpty(flowOfficalSealApprovalEntity.getId())) {
            flowOfficalSealApprovalEntity = flowOfficalSealApprovalService.getById(flowOfficalSealApprovalEntity.getId());
            view.addObject("seal", flowOfficalSealApprovalEntity);
        }

        return view;
    }

    /**
     * 删除
     *
     * @param flowOfficalSealApprovalEntity
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(FlowOfficalSealApprovalEntity flowOfficalSealApprovalEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if (StringUtils.isNotEmpty(flowOfficalSealApprovalEntity.getId())) {
                flowOfficalSealApprovalService.removeById(flowOfficalSealApprovalEntity.getId());
            }
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg(GlobalConstant.Err_Msg_All);
            log.error("删除公章借用记录失败，错误信息:{}", e);
        }
        return j;
    }

    /**
     * 跳转到已审批流程页面
     *
     * @param req
     * @return
     */
    @RequestMapping("/finishlist")
    public ModelAndView finishlist(HttpServletRequest req) {
        ModelAndView view = new ModelAndView("officalSeal/officalSealFinishtasklist");

        // 获取流程类别数据
        List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
        int size = lstCatogorys.size();
        for (int i = size-1; i >=0 ; i--) {
            if(!lstCatogorys.get(i).getName().equals("双井公章审批")){
                lstCatogorys.remove(i);
            }
        }
        view.addObject("categoryReplace", ListUtils.listToReplaceStr(lstCatogorys, "name", "id"));

        return view;
    }
    /**
     * Excel模板下载
     *
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/excelExport")
    public ResponseEntity<Resource> excel2007Export(WorkflowBaseEntity workflowBaseEntity , HttpServletResponse response, HttpServletRequest request, DataGrid dataGrid) {
        try {
            String startTime = request.getParameter("applyDate_begin");
            String endTime = request.getParameter("applyDate_end");
            if (startTime == null || startTime=="") {
                startTime = "2000-01-01";
            }

            if (endTime == null || endTime == "") {
                endTime = "2099-12-31";
            }

            ClassPathResource cpr = new ClassPathResource("/static/offical_recode.xlsx");

            InputStream is = cpr.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

//            //获取当前用户id
//            String userId = ShiroUtils.getSessionUserId();
//            //获取当前用户个人资料
//            SysUserModel user = sysUserService.getInfoByUserId(userId).get(0);
//            String dep = user.getDeptName();

            String userName = ShiroUtils.getSessionUserName();
            SysUserEntity user = sysUserService.getUserByUseName(userName);
            IPage<WorkflowBaseEntity> lstResult = new Page<>();
            if(SystemUtils.getDeptNameById(user.getDeptId()).equals("综合办公室")){
                Row row = sheet.getRow(1);
                Cell cell = row.getCell(0);
                cell.setCellValue("部门名称：双井街道全体");
                lstResult = workflowService.findFinishedTaskByALL(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), workflowBaseEntity, startTime, endTime, WorkflowConstant.Task_Category_approval);
            }else{
                Row row = sheet.getRow(1);
                Cell cell = row.getCell(0);
                SysDeptEntity paperDept = sysDeptService.getById(user.getDeptId());
                cell.setCellValue("部门名称："+paperDept.getName());
                lstResult = workflowService.findFinishedTaskByUserName(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), workflowBaseEntity, startTime, endTime, ShiroUtils.getSessionUserName(), WorkflowConstant.Task_Category_approval);
            }


//            QueryWrapper<FlowOfficalSealApprovalEntity> queryWrapper = QueryUtils.installQueryWrapper(flowOfficalSealApprovalEntity, request.getParameterMap(), dataGrid);
//
//            queryWrapper.isNull("UPDATE_DATE");
//            // 执行查询
//            IPage<FlowOfficalSealApprovalEntity> lstResult = flowOfficalSealApprovalService.page(new Page<FlowOfficalSealApprovalEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
//            List<FlowOfficalSealApprovalEntity> list = lstResult.getRecords();

            // 执行查询
            long size = lstResult.getRecords().size();
            for (long i = size - 1; i >= 0; --i) {
                if(!lstResult.getRecords().get((int) i).getWorkFlowName().equals("双井公章申请")){
                    lstResult.getRecords().remove(lstResult.getRecords().get((int) i));
                }else if(!lstResult.getRecords().get((int) i).getStatus().equals("3")){
                    lstResult.getRecords().remove(lstResult.getRecords().get((int) i));
                }
            }
            List<WorkflowBaseEntity> list = lstResult.getRecords();
//            FlowOfficalSealApprovalEntity biz = flowOfficalSealApprovalService.getById(base.getBusinessId());
            int i = 0;
            for (WorkflowBaseEntity base : list) {
                FlowOfficalSealApprovalEntity item = flowOfficalSealApprovalService.getById(base.getBusinessId());
                Row row = sheet.getRow(i + 3);
                Cell cell1 = row.getCell(0);
                cell1.setCellValue((i+1) + "");
                Cell cell2 = row.getCell(1);
                cell2.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(item.getUseDay()));
                Cell cell3 = row.getCell(2);
                cell3.setCellValue(item.getContent());
                Cell cell4 = row.getCell(3);
                cell4.setCellValue(item.getUserName());
                Cell cell5 = row.getCell(4);
                cell5.setCellValue(item.getUserName());
                Cell cell6 = row.getCell(5);
                cell6.setCellValue(item.getCommit());
                i++;
            }


            String fileName = "公章借用记录.xlsx";
            downLoadExcel(fileName, response, workbook);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Resource>(HttpStatus.OK);
    }
    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询数据  -- 我的已办审批
     * @param user
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagridFinish")
    public void datagridFinish(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String startTime = request.getParameter("applyDate_begin");
        String endTime = request.getParameter("applyDate_end");
        if (startTime == null || startTime=="") {
            startTime = "2000-01-01";
        }

        if (endTime == null || endTime == "") {
            endTime = "2099-12-31";
        }


        String userName = ShiroUtils.getSessionUserName();
        SysUserEntity user = sysUserService.getUserByUseName(userName);
        IPage<WorkflowBaseEntity> lstResult = new Page<>();
        if(SystemUtils.getDeptNameById(user.getDeptId()).equals("综合办公室")){
            lstResult = workflowService.findFinishedTaskByALL(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), workflowBaseEntity, startTime, endTime, WorkflowConstant.Task_Category_approval);
        }else{
            lstResult = workflowService.findFinishedTaskByUserName(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), workflowBaseEntity, startTime, endTime, ShiroUtils.getSessionUserName(), WorkflowConstant.Task_Category_approval);
        }
        // 执行查询
        long size = lstResult.getRecords().size();
        for (long i = size - 1; i >= 0; --i) {
            if(!lstResult.getRecords().get((int) i).getWorkFlowName().equals("双井公章申请")){
                lstResult.getRecords().remove(lstResult.getRecords().get((int) i));
            }else if(!lstResult.getRecords().get((int)i).getStatus().equals("3")){
                lstResult.getRecords().remove(lstResult.getRecords().get((int) i));
            }
        }
        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
    }

}
