package com.active4j.hr.car.controller;

import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;
import com.active4j.hr.activiti.biz.entity.FlowOfficalSealApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowCarApprovalService;
import com.active4j.hr.activiti.biz.service.FlowGetSpeRoleService;
import com.active4j.hr.activiti.biz.service.FlowOfficalSealApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.entity.WorkflowCategoryEntity;
import com.active4j.hr.activiti.service.WorkflowCategoryService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.activiti.util.WorkflowConstant;
import com.active4j.hr.activiti.util.WorkflowTaskUtil;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.car.service.CarRecordService;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.officalSeal.service.OaOfficalSealRecordService;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
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
import java.util.Set;

@Controller
@RequestMapping("car/record")
@Slf4j
public class CarRecordController extends BaseController {

    @Autowired
    private OaOfficalSealRecordService oaOfficalSealRecordService;

    @Autowired
    private FlowOfficalSealApprovalService flowOfficalSealApprovalService;


    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private FlowCarApprovalService flowCarApprovalService;

    @Autowired
    private WorkflowCategoryService workflowCategoryService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private FlowGetSpeRoleService flowGetSpeRoleService;

    @Autowired
    private CarRecordService carRecordService;

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
        ModelAndView view = new ModelAndView("car/carFinishtasklist");

        // 获取流程类别数据
        List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
        int size = lstCatogorys.size();
        for (int i = size-1; i >=0 ; i--) {
            if(!lstCatogorys.get(i).getName().equals("行政类")){
                lstCatogorys.remove(i);
            }
        }
        view.addObject("categoryReplace", ListUtils.listToReplaceStr(lstCatogorys, "name", "id"));

        return view;
    }

    /**
     * 查询数据  -- 我的已办审批
     * @param user
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping("/datagridFinish")
    @ResponseBody
    public AjaxJson datagridFinish(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        AjaxJson j = new AjaxJson();
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
        List<String> lstUsers = WorkflowTaskUtil.getApprovalUserByRoleName("车辆管理员");
        IPage<WorkflowBaseEntity> lstResult = new Page<>();
        int totalCarApper = lstUsers.size();
        if(null == lstUsers || lstUsers.size() <= 0) {
            j.setMsg("车辆管理员不存在");
            j.setSuccess(false);
            return j;
        }else{
            if(lstUsers.contains(user.getUserName())){
                lstResult = workflowService.findFinishedCarTaskByALL(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), workflowBaseEntity, startTime, endTime, WorkflowConstant.Task_Category_approval);
            }else{
                lstResult = workflowService.findFinishedTaskCarByUserDept(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), workflowBaseEntity, startTime, endTime, ShiroUtils.getSessionUserDept(), WorkflowConstant.Task_Category_approval);
            }
        }
//        else {
//            j.setMsg("车辆管理员存在多人，请联系管理员");
//            j.setSuccess(false);
//            return j;
//        }
//        long size = lstResult.getRecords().size();
//        for (long i = size - 1; i >= 0; --i) {
//            if(!lstResult.getRecords().get((int) i).getWorkFlowName().equals("车辆申请")){
//                lstResult.getRecords().remove(lstResult.getRecords().get((int) i));
//
//        }
        // 输出结果
        ResponseUtil.writeJson(response, dataGrid, lstResult);
        return j;
    }


    /**
     * Excel模板下载
     *
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/excelExport")
    public ResponseEntity<Resource> excel2007Export(String useDepatment , HttpServletResponse response, HttpServletRequest request, String applyDate_begin,String applyDate_end,String applyName) {
        try {

            if (applyDate_begin == null || applyDate_begin=="") {
                applyDate_begin = "2000-01-01";
            }

            if (applyDate_end == null || applyDate_end == "") {
                applyDate_end = "2099-12-31";
            }
            ClassPathResource cpr = new ClassPathResource("/static/car_record.xlsx");

            InputStream is = cpr.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            XSSFFont contFont = (XSSFFont) workbook.createFont();
            // 加粗
            contFont.setBold(false);
            // 字体名称
            contFont.setFontName("楷体");
            // 字体大小
            contFont.setFontHeight(14);
            // 内容样式
            XSSFCellStyle contentStyle = (XSSFCellStyle) workbook.createCellStyle();
            // 设置字体css
            contentStyle.setFont(contFont);
            // 竖向居中
            contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
            // 边框
            contentStyle.setBorderBottom(BorderStyle.THIN);
            contentStyle.setBorderLeft(BorderStyle.THIN);
            contentStyle.setBorderRight(BorderStyle.THIN);
            contentStyle.setBorderTop(BorderStyle.THIN);



            Set userrole = ShiroUtils.getSessionUserRole();
            String carrole = this.flowGetSpeRoleService.getCarAdminrole();
            Boolean sign = false;
            for (Object role : userrole) {
                if (carrole.equals(role)){
                    sign = true;//是车辆管理员
                    break;
                }
            }
            List<FlowCarApprovalEntity> list = new ArrayList();
            if (sign){//车辆管理员查询全部已完成数据
                list = this.carRecordService.getAllCarMessage(useDepatment,applyDate_begin,applyDate_end,applyName);
            }else {//其他成员查询对应科室的数据
                String userdept = ShiroUtils.getSessionUserDept();
                list = this.carRecordService.getAllCarMessage(userdept,applyDate_begin,applyDate_end,applyName);
            }
//            QueryWrapper<FlowCarApprovalEntity> queryWrapper = QueryUtils.installQueryWrapper(flowCarApprovalEntity, request.getParameterMap(), dataGrid);
//
//            queryWrapper.isNull("UPDATE_DATE");
//            // 执行查询
//            IPage<FlowCarApprovalEntity> lstResult = flowCarApprovalService.page(new Page<FlowCarApprovalEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
//            List<FlowCarApprovalEntity> list = lstResult.getRecords();

            int i = 0;
            for (FlowCarApprovalEntity item : list) {
                Row row = sheet.getRow(i + 3);
                if (row == null) {
                    row = sheet.createRow(i+3);
                }
                Cell cell1 = row.getCell(0);
                if (cell1 == null){
                    cell1 = row.createCell(0);
                }
                cell1.setCellValue((i+1) + "");
                cell1.setCellStyle(contentStyle);
//                //创建日期
//                Cell cell2 = row.getCell(1);
//                cell2.setCellValue(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(item.getCreateDate()));
                //用车单位
                Cell cell2 = row.getCell(1);
                if (cell2 == null){
                    cell2 = row.createCell(1);
                }
                cell2.setCellValue(item.getUseDepatment());
                cell2.setCellStyle(contentStyle);

                //乘车人
                Cell cell3 = row.getCell(2);
                if (cell3 == null){
                    cell3 = row.createCell(2);
                }
                cell3.setCellValue(item.getUserName());
                cell3.setCellStyle(contentStyle);
                //乘车人数
                Cell cell4 = row.getCell(3);
                if (cell4 == null){
                    cell4 = row.createCell(3);
                }
                cell4.setCellValue(item.getPerson());
                cell4.setCellStyle(contentStyle);
                //用车类别
                Cell cell6 = row.getCell(4);
                if (cell6 == null){
                    cell6 = row.createCell(4);
                }
                cell6.setCellValue(item.getReason());
                cell6.setCellStyle(contentStyle);
                //用车事由
                Cell cell7 = row.getCell(5);
                if (cell7 == null){
                    cell7 = row.createCell(5);
                }
                cell7.setCellValue(item.getUsecarreason());
                cell7.setCellStyle(contentStyle);
                //目的地
                Cell cell8 = row.getCell(6);
                if (cell8 == null){
                    cell8 = row.createCell(6);
                }
                cell8.setCellValue(item.getDestination());
                cell8.setCellStyle(contentStyle);

                //用车时间
                Cell cell9 = row.getCell(7);
                if (cell9 == null){
                    cell9 = row.createCell(7);
                }
                cell9.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(item.getUseTime()));
                cell9.setCellStyle(contentStyle);

                //用车时间段
                Cell cell10 = row.getCell(8);
                if (cell10 == null){
                    cell10 = row.createCell(8);
                }
                cell10.setCellValue(item.getMorningOrAfternoon()==0?"上午":"下午");
                cell10.setCellStyle(contentStyle);

                //车牌
                Cell cell11 = row.getCell(9);
                if (cell11 == null){
                    cell11 = row.createCell(9);
                }
                cell11.setCellValue(item.getPlatenum());
                cell11.setCellStyle(contentStyle);

                //司机
                Cell cell12 = row.getCell(10);
                if (cell12 == null){
                    cell12 = row.createCell(10);
                }
                cell12.setCellValue(item.getPlateuser());
                cell12.setCellStyle(contentStyle);

                //etc使用情况
                Cell cell13 = row.getCell(11);
                if (cell13 == null){
                    cell13 = row.createCell(11);
                }
                cell13.setCellValue(item.getEtcmessage());
                cell13.setCellStyle(contentStyle);

                //备注
                Cell cell14 = row.getCell(12);
                if (cell14 == null){
                    cell14 = row.createCell(12);
                }
                cell14.setCellValue(item.getCommit());
                cell14.setCellStyle(contentStyle);
                i++;
            }
            Row row1 = sheet.getRow(i + 4);
            if (row1 == null) {
                row1 = sheet.createRow(i+3);
            }

            Cell cell15 = row1.getCell(1);
            if (cell15 == null){
                cell15 = row1.createCell(1);
            }
            cell15.setCellValue("科室负责人：");

            Cell cell16 = row1.getCell(5);
            if (cell16 == null){
                cell16 = row1.createCell(5);
            }
            cell16.setCellValue("主管领导：");

            String fileName = "车辆申请记录.xlsx";
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

}
