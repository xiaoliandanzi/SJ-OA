package com.active4j.hr.asset.controller;

import com.active4j.hr.activiti.biz.dao.FlowAssetAddMapper;
import com.active4j.hr.activiti.biz.entity.FlowAssetAddEntity;
import com.active4j.hr.asset.dao.OaAssetDao;
import com.active4j.hr.asset.entity.OaAssetStoreEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fr.opensagres.xdocreport.core.XDocReportException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author liiuliang
 * @version 1.0
 * @date 2021/3/12
 */
@RestController
@RequestMapping(value = "asset/export")
@Slf4j
public class AssetWordController  {

    @Resource
    private OaAssetDao oaAssetDao;

    @Resource
    private FlowAssetAddMapper flowAssetAddMapper;



//    @Test
//    public void test() throws IOException, XDocReportException ,ParseException{
//        generateWord();
//    }

//    public void generateWord() throws IOException, XDocReportException , ParseException {
//
//                // 获取Word模板，模板存放路径在项目的resources目录下
//                InputStream ins = this.getClass().getResourceAsStream("/word.docx");
//
//                //注册xdocreport实例并加载FreeMarker模板引擎
//                IXDocReport report = XDocReportRegistry.getRegistry().loadReport(ins,
//                        TemplateEngineKind.Freemarker);
//                //创建xdocreport上下文对象
//                IContext context = report.createContext();
//
//                //创建要替换的文本变量
////        context.put("city", "北京市");
////        context.put("startDate", "2020-09-17");
//
//                List<OaAssetStoreEntity> list = new ArrayList<OaAssetStoreEntity>();
//                OaAssetStoreEntity as = new OaAssetStoreEntity();
////                as.setAssetName("资产名字");
////                as.setAddress("buzhidao");
////                as.setAmount(12333);
////                as.setQuantity(999);
////                as.setCommit("备注");
////                as.setModel("类型");
//                as.setChangeTime(new Date());
//                OaAssetStoreEntity a2 = new OaAssetStoreEntity();
//                DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date myDate2 = dateFormat2.parse("2010-09-13 22:36:01");
//                a2.setChangeTime(myDate2);
//                for (int i=0;i<30;i++){
//                    OaAssetStoreEntity a =new OaAssetStoreEntity();
//                   a.setChangeTime(dateFormat2.parse("2010-09-13 22:36:"+String.valueOf(i)));
//                   list.add(a);
//                }
//                list.add(as);
//                list.add(a2);
//                context.put("list",list);
//                //创建字段元数据
//                FieldsMetadata fm = report.createFieldsMetadata();
//                //Word模板中的表格数据对应的集合类型
//                fm.load("list", OaAssetStoreEntity.class, true);
//
//                //输出到本地目录
//                OutputStream out = new FileOutputStream(new File("D://word.docx"));
//                report.process(context,out);
//                out.close();
//
//    }

    @RequestMapping(value = "exportWord",method = RequestMethod.GET)
    public void generateWord(HttpServletResponse response) throws IOException, XDocReportException , ParseException {

        try {
        // 获取Word模板，模板存放路径在项目的resources目录下
        InputStream ins = this.getClass().getResourceAsStream("/apply.docx");

        //注册xdocreport实例并加载FreeMarker模板引擎
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(ins,
                TemplateEngineKind.Freemarker);
        //创建xdocreport上下文对象
        IContext context = report.createContext();

        //创建要替换的文本变量
//        context.put("city", "北京市");
//        context.put("startDate", "2020-09-17");
        Date dt=new Date();
        String year=String.format("%tY", dt);
        String mouth=String .format("%tm", dt);
        String day=String .format("%td", dt);
        context.put("year",year);
        context.put("mouth",mouth);
        context.put("day",day);
            QueryWrapper<OaAssetStoreEntity> queryWrapper = new QueryWrapper<OaAssetStoreEntity>();
            queryWrapper.eq("APPLYSTATUS",3);
        List<OaAssetStoreEntity> listall=oaAssetDao.selectList(queryWrapper);
        context.put("list",listall);
        //创建字段元数据
        FieldsMetadata fm = report.createFieldsMetadata();
        //Word模板中的表格数据对应的集合类型
        fm.load("list", OaAssetStoreEntity.class, true);

//        //输出到本地目录
//        OutputStream out = new FileOutputStream(new File("D://word.docx"));
//        report.process(context,out);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            String fileName = "双井街道固定资产入库表.docx";
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
            report.process(context, response.getOutputStream());
            ins.close();
        }catch (Exception e){
            e.printStackTrace();
            log.error("打印失败，错误原因：{}", e.getMessage());
        }

    }

    @RequestMapping(value = "exportApplyWord",method = RequestMethod.GET)
    public void generateApplyWord(HttpServletResponse response) throws IOException, XDocReportException , ParseException {

        try {
        // 获取Word模板，模板存放路径在项目的resources目录下
        InputStream ins = this.getClass().getResourceAsStream("/apply.docx");

        //注册xdocreport实例并加载FreeMarker模板引擎
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(ins,
                TemplateEngineKind.Freemarker);
        //创建xdocreport上下文对象
        IContext context = report.createContext();

        //创建要替换的文本变量
//        context.put("city", "北京市");
//        context.put("startDate", "2020-09-17");
        Date dt=new Date();
        String year=String.format("%tY", dt);
        String mouth=String .format("%tm", dt);
        String day=String .format("%td", dt);
        context.put("year",year);
        context.put("mouth",mouth);
        context.put("day",day);
            QueryWrapper<OaAssetStoreEntity> queryWrapper = new QueryWrapper<OaAssetStoreEntity>();
            queryWrapper.eq("APPLYSTATUS",1);
        List<OaAssetStoreEntity> listall=oaAssetDao.selectList(queryWrapper);
        context.put("list",listall);
        //创建字段元数据
        FieldsMetadata fm = report.createFieldsMetadata();
        //Word模板中的表格数据对应的集合类型
        fm.load("list", OaAssetStoreEntity.class, true);

//        //输出到本地目录
//        OutputStream out = new FileOutputStream(new File("D://apply.docx"));
//        report.process(context,out);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            String fileName = "双井街道固定资产入库表.docx";
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
            report.process(context, response.getOutputStream());
            ins.close();
        }catch (Exception e){
            e.printStackTrace();
            log.error("打印失败，错误原因：{}", e.getMessage());
        }

    }

}
