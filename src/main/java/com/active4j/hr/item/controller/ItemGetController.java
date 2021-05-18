package com.active4j.hr.item.controller;

import com.active4j.hr.activiti.biz.dao.FlowGetSpeRole;
import com.active4j.hr.activiti.biz.entity.FlowCarApprovalEntity;
import com.active4j.hr.activiti.entity.WorkflowCategoryEntity;
import com.active4j.hr.asset.entity.OaAssetStoreEntity;
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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.jexl3.parser.ASTJxltLiteral;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
import java.util.Date;
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

    @Autowired
    private FlowGetSpeRole flowGetSpeRole;




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
            String json_data = getItemEntity.getJsonData();
            JSONArray array = JSON.parseArray(json_data);
            if (getItemEntity.getGetDay().compareTo(DateUtils.getDayBegin()) == -1){
                j.setSuccess(false);
                j.setMsg("领用日期不能在当前日期之前");
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

            for (int i = 0; i < array.size(); i++) {
                JSONObject jo = array.getJSONObject(i);
                Integer quantity = jo.getInteger("quantity");
                String itemName = jo.getString("itemName");
                QueryWrapper<RequisitionedItemEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.select("NUMLIMIT").eq("TYPE",0).eq("NAME",itemName);
                List<RequisitionedItemEntity> list = requisitionedItemService.list(queryWrapper);
                if(StringUtils.isEmpty(itemName)) {
                    j.setSuccess(false);
                    j.setMsg("领用物品名称不能为空!");
                    return j;
                }
                if(null == quantity) {
                    j.setSuccess(false);
                    j.setMsg("领用数量不能为空");
                    return j;
                }
                if(quantity>list.get(0).getNumLimit()) {
                    j.setSuccess(false);
                    j.setMsg("领用数量不能大于限额："+ list.get(0).getNumLimit());
                    return j;
                }
                List<RequisitionedItemEntity> stockEntity = requisitionedItemService.getItemByname(itemName);
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

                getItemEntity.setId(null);
                if (StringUtils.isEmpty(getItemEntity.getId())) {
                    //新增方法
                    getItemEntity.setItemName(itemName);
                    getItemEntity.setQuantity(quantity);
                    getItemService.save(getItemEntity);
                    requisitionedItemService.saveOrUpdate(entity);
                }else {
                    //编辑方法
                    GetItemEntity tmp = getItemService.getById(getItemEntity.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(getItemEntity, tmp);
                    getItemService.saveOrUpdate(tmp);
                }
            }
            /*QueryWrapper<RequisitionedItemEntity> queryWrapper = new QueryWrapper<>();
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
            }*/
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

    @RequestMapping("savegoodstaus")
    @ResponseBody
    public AjaxJson savegoodstaus(String id,String goodstaus,HttpServletRequest request, HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        j.setMsg("修改成功");
        j.setSuccess(true);
        try{
            this.getItemService.savegoodstaus(id,goodstaus);
        }catch(Exception e){
            j.setSuccess(false);
            j.setMsg("操作失败，请联系管理员"+e);
            e.printStackTrace();
        }
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
    public ResponseEntity<Resource> excel2007Export(HttpServletResponse response, HttpServletRequest request, String applyDate_begin, String applyDate_end) {
        try {

            ClassPathResource cpr = new ClassPathResource("/static/item_record.xlsx");

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

            String userDept = ShiroUtils.getSessionUserDept();
            Set userRole = ShiroUtils.getSessionUserRole();
            //获取物品管理员编号
            String rolecode = this.flowGetSpeRole.getGoodsAdminrole();
            Boolean sign = false;
            for (Object item: userRole){
                if (rolecode.equals(item)){
                    sign = true;//是物品管理员
                    break;
                }
            }
            //物品管理员导出全部，其他科室导出自己科室的数据
            List<GetItemEntity> list = new ArrayList();
            if (sign){//物品管理员查询全部已完成数据
                list = this.getItemService.getAllItemMessage(null);
            }else {//其他成员查询对应科室的数据
                list = this.getItemService.getAllItemMessage(userDept);
            }
//            list = this.getItemService.getItemMessage(useDepatment,applyDate_begin,applyDate_end,applyName);

//            QueryWrapper<FlowCarApprovalEntity> queryWrapper = QueryUtils.installQueryWrapper(flowCarApprovalEntity, request.getParameterMap(), dataGrid);
//
//            queryWrapper.isNull("UPDATE_DATE");
//            // 执行查询
//            IPage<FlowCarApprovalEntity> lstResult = flowCarApprovalService.page(new Page<FlowCarApprovalEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
//            List<FlowCarApprovalEntity> list = lstResult.getRecords();

            int i = 0;
            for (GetItemEntity item : list) {
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
                //领用科室
                Cell cell2 = row.getCell(1);
                if (cell2 == null){
                    cell2 = row.createCell(1);
                }
                cell2.setCellValue(item.getDepartmentName());
                cell2.setCellStyle(contentStyle);

                //领用人
                Cell cell3 = row.getCell(2);
                if (cell3 == null){
                    cell3 = row.createCell(2);
                }
                cell3.setCellValue(item.getUserName());
                cell3.setCellStyle(contentStyle);
                //物品名称
                Cell cell4 = row.getCell(3);
                if (cell4 == null){
                    cell4 = row.createCell(3);
                }
                cell4.setCellValue(item.getItemName());
                cell4.setCellStyle(contentStyle);
                //数量
                Cell cell5 = row.getCell(4);
                if (cell5 == null){
                    cell5 = row.createCell(4);
                }
                cell5.setCellValue(item.getQuantity());
                cell5.setCellStyle(contentStyle);
                //领取时间
                Cell cell6 = row.getCell(5);
                if (cell6 == null){
                    cell6 = row.createCell(5);
                }
                cell6.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getGetDay()));
                cell6.setCellStyle(contentStyle);
                //备注
                Cell cell7 = row.getCell(6);
                if (cell7 == null){
                    cell7 = row.createCell(6);
                }
                cell7.setCellValue(item.getMemo());
                cell7.setCellStyle(contentStyle);
                //领用状态
                Cell cell8 = row.getCell(7);
                if (cell8 == null){
                    cell8 = row.createCell(7);
                }
                cell8.setCellValue(item.getGoodstaus());
                cell8.setCellStyle(contentStyle);

                i++;
            }
            Row row1 = sheet.getRow(i + 4);
            if (row1 == null) {
                row1 = sheet.createRow(i+3);
            }

            String fileName = "物品领用记录.xlsx";
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
