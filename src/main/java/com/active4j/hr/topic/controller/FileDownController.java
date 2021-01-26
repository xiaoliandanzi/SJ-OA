package com.active4j.hr.topic.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.func.upload.entity.UploadAttachmentEntity;
import com.active4j.hr.func.upload.service.UploadAttachmentService;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.topic.entity.OaMeeting;
import com.active4j.hr.topic.entity.OaTopic;
import com.active4j.hr.topic.service.OaTopicService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author weiZiHao /topic/file/down
 * @date 2021/1/15
 */
@RestController
@RequestMapping(value = "topicFile")
@Slf4j
public class FileDownController extends BaseController {

    @Autowired
    private UploadAttachmentService uploadAttachmentService;

    @Autowired
    private OaTopicService topicService;

    @Autowired
    private SysDeptService deptService;

    @Autowired
    private SysUserService userService;

    private String init;

    /**
     * 删除文件
     *
     * @param oaTopic id fileId
     * @return
     */
    @RequestMapping(value = "remove")
    public AjaxJson removeFile(OaTopic oaTopic) {
        AjaxJson ajaxJson = new AjaxJson();
        /*try {*/
        String fileId = oaTopic.getFileId();
        oaTopic = topicService.getById(oaTopic.getId());
        String fileIds = oaTopic.getFileId();
        fileIds = fileIds.replace(fileId, "");
        oaTopic.setFileId(fileIds);
        topicService.saveOrUpdate(oaTopic);
        uploadAttachmentService.removeById(fileId);
        ajaxJson.setObj(oaTopic.getFileId());
       /* } catch (Exception e) {
            log.error("删除文件信息失败,错误信息:" + e.getMessage());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("删除文件信息失败");
            e.printStackTrace();
        }*/
        return ajaxJson;
    }

    /**
     * 文件名
     *
     * @param uploadAttachmentEntity
     * @return
     */
    @RequestMapping(value = "name")
    public AjaxJson getName(UploadAttachmentEntity uploadAttachmentEntity) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            UploadAttachmentEntity tsAttachment = uploadAttachmentService.getById(uploadAttachmentEntity.getId());
            ajaxJson.setObj(tsAttachment.getName());
        } catch (Exception e) {
            log.error("获取文件信息失败,错误信息:" + e.getMessage());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("获取文件信息失败");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    /**
     * 下载
     *
     * @param id
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("down")
    public void download(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            UploadAttachmentEntity tsAttachment = uploadAttachmentService.getById(id);
            if (null == tsAttachment) {
                return;
            }//数据库
            if (StringUtils.equals(GlobalConstant.FILE_UPLOADER_SAVE_DB, tsAttachment.getType())) {
                byte[] data = tsAttachment.getContent();
                String fileName = tsAttachment.getName();
                String end = "." + tsAttachment.getExtendName();
                int line = fileName.lastIndexOf(".");
                fileName = fileName.substring(0, line);
                response.reset();
                response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + end + "\"");
                response.addHeader("Content-Length", "" + data.length);
                response.setContentType("application/octet-stream;charset=UTF-8");
                OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
                outputStream.write(data);
                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception e) {
            log.error("文件下载报错，错误原因：{}", e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * @param oaTopic  id
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getHtml")
    @ResponseBody
    public void test(OaTopic oaTopic, HttpServletRequest request, HttpServletResponse response) throws Exception {
        exportWord(request, response, getStr(oaTopic));
    }

    /**
     * @param
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getMeetingHtml")
    public void getMeetingHtml(OaMeeting oaMeeting, HttpServletRequest request, HttpServletResponse response) throws Exception {
        exportWord(request, response, getMeetingStr(oaMeeting));
    }

    /**
     * @param
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getprintqdHtml")
    public void getprintqdHtml(OaMeeting oaMeeting, HttpServletRequest request, HttpServletResponse response) throws Exception {
        exportWord(request, response, getprintqdStr(oaMeeting));
    }

    private String getprintqdStr(OaMeeting oaMeeting) throws ParseException {
        String meetingId = oaMeeting.getMeetingId();
        String meetingName = oaMeeting.getMeetingName();
        String str[] = oaMeeting.getConferee().split(",");
        String html = "";
        for (int i = 0; i < str.length; i++) {
            String THIS = "   <tr  style=\"height: 60px\">\n" +
                    "       <td style=\"border: 1px solid; \">" + str[i] + "</td>\n" +
                    "       <td  style=\"border: 1px solid; \" ></td>\n" +
                    "      </tr>\n";
            html = html + THIS;
        }
        return
                " <div><h1 style=\"text-align: center\">签到表</h1></div><br><br>\n" +
                        " <table width=\"600px\" height=\"40px\" border=\"1px\" style=\"text-align: center; border: 1px solid;\">\n" +
                        " <tr>\n" +
                        " <td style=\"width: 250px; border: 1px solid;\">姓名</td>\n" +
                        "   <td style=\"width: 250px; border: 1px solid; \">签到</td>\n" +
                        "  </tr>\n" +
                        "  " + html + " " +
                        "  </table>\n" +
                        "</div>\n";
    }

    private String getMeetingStr(OaMeeting oaMeeting) throws ParseException {
        String meetingId = oaMeeting.getMeetingId();
        String meetingName = oaMeeting.getMeetingName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat sdd = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = sdf.parse(oaMeeting.getMeetingTime());
        String datetime = sdd.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); //放入
        int hours = calendar.get(Calendar.HOUR_OF_DAY); //时（24小时制）
        String weekday = getWeekOfDate(date);
        String getDuringDay = getDuringDay(hours);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); //时（24小时制）
        String time = datetime + weekday + getDuringDay + ":" + hour + ":00";
        QueryWrapper<OaTopic> queryWrapper = new QueryWrapper<>();
        String str[] = oaMeeting.getIssueId().split(",");
        queryWrapper.in("id", str);
        List<OaTopic> list = topicService.list(queryWrapper);
        int rocunt = 2;
        String html = "";
        for (OaTopic oaTopic : list) {
            rocunt++;
            String THIS = "   <tr  style=\"height: 60px\">\n" +
                    "       <td  style=\"border: 1px solid;\" >" + rocunt + "</td>\n" +
                    "        <td   style=\"text-align: left; vertical-align:middle;position: relative; width:600px; border: 1px solid; \">" + oaTopic.getTopicName() + "</td>\n" +
                    "       <td style=\"width: 150px; border: 1px solid; \">" + oaTopic.getProposeLeaderName() + "</td>\n" +
                    "       <td style=\"width: 150px; border: 1px solid; \">" + oaTopic.getReportName() + "</td>\n" +
                    "        <td style=\"width: 150px; border: 1px solid; \"></td>\n" +
                    "      </tr>\n";
            html = html + THIS;

        }
        int rocount1 = rocunt + 1;
        int rocount2 = rocunt + 2;
        return
                "<div style=\"width: \"800px\";height: \"1000px\";margin: 10px auto;\">\n" +
                        " <div><h1 style=\"text-align: center\">年第&nbsp&nbsp&nbsp&nbsp次主任办公室议题</h1></div><br><br>\n" +
                        "<span>会议时间:" + time + "</span><br><br>\n" +
                        " <span>会议地点:" + meetingId + "</span><br><br>\n" +
                        "<span>会议标题:" + meetingName + "</span><br><br>\n" +
                        " <table width=\"800px\" height=\"1000px\"  style=\"text-align: center;border: 1px solid;\">\n" +
                        "  <tr style=\"height: 60px\">\n" +
                        "  <td style=\"width: 150px; border: 1px solid;\">序号</td>\n" +
                        "  <td style=\"width:600px; border: 1px solid;\">议题\n" +
                        "         <td style=\"width: 150px; border: 1px solid;\">提议领导</td>\n" +
                        "    <td style=\"width: 150px; border: 1px solid;\">汇报人</td>\n" +
                        "    <td style=\"width: 150px; border: 1px solid;\">列席</td>\n" +
                        " </tr>\n" +
                        "  <tr  style=\"height: 60px\">\n" +
                        "  <td  style=\"border: 1px solid; \">一</td>\n" +
                        "   <td  style=\"text-align: left; vertical-align:middle;position: relative; border: 1px solid;\"  colspan=\"5\">工作通报</td>\n" +
                        " </tr>\n" +
                        "  <tr style=\"height: 60px\">\n" +
                        "     <td style=\" border: 1px solid;\">1</td>\n" +
                        "     <td style=\"width:600px; border: 1px solid;\"></td>\n" +
                        "     <td style=\"width: 150px; border: 1px solid;\"></td>\n" +
                        "    <td style=\"width: 150px;border: 1px solid;\"></td>\n" +
                        "    <td style=\"width: 150px; border: 1px solid;\"></td>\n" +
                        "  </tr>\n" +
                        " <tr  style=\"height: 60px\">\n" +
                        "    <td style=\"border: 1px solid;\">2</td>\n" +
                        "   <td style=\"border: 1px solid;\"  style=\"width:600px\"></td>\n" +
                        "   <td style=\"width: 150px; border: 1px solid;\"></td>\n" +
                        "   <td style=\"width: 150px; border: 1px solid;\"></td>\n" +
                        "   <td style=\"width: 150px; border: 1px solid;\"></td>\n" +
                        " </tr>\n" +
                        " <tr  style=\"height: 60px\">\n" +
                        "     <td style=\"border: 1px solid;\">二</td>\n" +
                        "    <td   style=\"text-align: left; vertical-align:middle;position: relative; border: 1px solid;\" colspan=\"5\">审议事项</td>\n" +
                        " </tr >\n" +
                        "  " + html + " " +
                        " <tr style=\"height: 60px\">\n" +
                        "    <td  style=\"border: 1px solid;\">三</td>\n" +
                        "    <td   style=\"text-align: left; vertical-align:middle;position: relative;border: 1px solid;\" colspan=\"5\">研究部署</td>\n" +
                        " </tr>\n" +
                        "  <tr  style=\"height: 60px\">\n" +
                        "  <td style=\"border: 1px solid; \" >" + rocount1 + "</td>\n" +
                        "   <td  style=\"width:600px;border: 1px solid;\"></td>\n" +
                        "   <td style=\"width: 150px;border: 1px solid;\"></td>\n" +
                        "    <td style=\"width: 150px;border: 1px solid;\"></td>\n" +
                        "   <td style=\"width: 150px;border: 1px solid;\"></td>\n" +
                        " </tr>\n" +
                        " <tr  style=\"height: 60px\">\n" +
                        "   <td  style=\"border: 1px solid; \">" + rocount2 + "</td>\n" +
                        "   <td  style=\"width:600px;border: 1px solid;\"></td>\n" +
                        "   <td style=\"width: 150px; border: 1px solid;\"></td>\n" +
                        "    <td style=\"width: 150px; border: 1px solid;\"></td>\n" +
                        "   <td style=\"width: 150px; border: 1px solid;\"></td>\n" +
                        "</tr>\n" +
                        "  </table>\n" +
                        "</div>\n";
    }

    /**
     * 根据日期获得星期的方法
     *
     * @param date
     * @return
     * @author zhangsq
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        //String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    /**
     * 根据小时判断是否为上午、中午、下午
     *
     * @param hour
     * @return
     * @author zhangsq
     */
    public static String getDuringDay(int hour) {
        if (hour >= 7 && hour < 11) {
            return "上午";
        }
        if (hour >= 11 && hour <= 13) {
            return "正午";
        }
        if (hour >= 14 && hour <= 18) {
            return "下午";
        }
        return null;
    }

    String wordHtmlHead = "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
            "xmlns:w=\"urn:schemas-microsoft-com:office:word\" xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\"\n" +
            "xmlns=\"http://www.w3.org/TR/REC-html40\"><head>\n" +
            "    <!--[if gte mso 9]><xml><w:WordDocument><w:View>Print</w:View><w:TrackMoves>false</w:TrackMoves><w:TrackFormatting/><w:ValidateAgainstSchemas/><w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid><w:IgnoreMixedContent>false</w:IgnoreMixedContent><w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText><w:DoNotPromoteQF/><w:LidThemeOther>EN-US</w:LidThemeOther><w:LidThemeAsian>ZH-CN</w:LidThemeAsian><w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript><w:Compatibility><w:BreakWrappedTables/><w:SnapToGridInCell/><w:WrapTextWithPunct/><w:UseAsianBreakRules/><w:DontGrowAutofit/><w:SplitPgBreakAndParaMark/><w:DontVertAlignCellWithSp/><w:DontBreakConstrainedForcedTables/><w:DontVertAlignInTxbx/><w:Word11KerningPairs/><w:CachedColBalance/><w:UseFELayout/></w:Compatibility><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><m:mathPr><m:mathFont m:val=\"Cambria Math\"/><m:brkBin m:val=\"before\"/><m:brkBinSub m:val=\"--\"/><m:smallFrac m:val=\"off\"/><m:dispDef/><m:lMargin m:val=\"0\"/> <m:rMargin m:val=\"0\"/><m:defJc m:val=\"centerGroup\"/><m:wrapIndent m:val=\"1440\"/><m:intLim m:val=\"subSup\"/><m:naryLim m:val=\"undOvr\"/></m:mathPr></w:WordDocument></xml><![endif]-->\n" +
            "</head>";

    public void exportWord(HttpServletRequest request, HttpServletResponse response, String content) throws Exception {
        OutputStream ostream = response.getOutputStream();
        content = wordHtmlHead + "<body>" + content + "</body></html>";
        String fileName = "议题申请审批表.doc";
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ostream.write(content.getBytes("utf-8"));
            ostream.flush();
            /*byte b[] = content.getBytes("UTF-8");  //这里是必须要设置编码的，不然导出中文就会乱码。
            ByteArrayInputStream bais = new ByteArrayInputStream(b);//将字节数组包装到流中
            //生成word
            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
            //输出文件
            response.setCharacterEncoding("UTF-8");
            //设置word格式
            response.setContentType("application/msword");
            response.setHeader("Content-disposition", "attachment;filename=WordDocument.doc");
            OutputStream ostream = response.getOutputStream();
            poifs.writeFilesystem(ostream);
            bais.close();
            ostream.close();*/
        } catch (Exception e) {
            //异常处理
            System.err.println(e);
        }
    }


    private String getStr(OaTopic oaTopic) {
        oaTopic = topicService.getById(oaTopic.getId());
        String deptName = deptService.getById(oaTopic.getDeptId()).getName();
        String proposeLeaderName = oaTopic.getProposeLeaderName();
        String createUser = oaTopic.getCreateUserName();
        String reportName = oaTopic.getReportName();
        String topicName = oaTopic.getTopicName();
        String topicContent = oaTopic.getTopicContent();
        String topicRemark = oaTopic.getTopicRemark();
        return "<div style=\"width: 720px;height: 1000px;margin: 10px auto;\">\n" +
                "    <div><h1 style=\"text-align: center\">议 题 申 请 审 批 表</h1></div>\n" +
                "    <table width=\"670px\" border=\"1px\" \n" +
                "           style=\"text-align: center;border: 1px solid;border-collapse: collapse\">\n" +
                "        <tr>\n" +
                "            <td style=\"width: 180px;border: 1px solid\">申报科室</td>\n" +
                "            <td style=\"width: 180px;border: 1px solid\">" + deptName + "</td>\n" +
                "            <td style=\"width: 180px;border: 1px solid\">提议领导</td>\n" +
                "            <td style=\"width: 180px;border: 1px solid\">" + proposeLeaderName + "</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 180px;border: 1px solid\">负责人</td>\n" +
                "            <td style=\"width: 180px;border: 1px solid\">" + createUser + "</td>\n" +
                "            <td style=\"width: 180px;border: 1px solid\">汇报人</td>\n" +
                "            <td style=\"width: 180px;border: 1px solid\">" + reportName + "</td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "    <table width=\"720px\" border=\"1px\" style=\"border: 1px solid;border-collapse: collapse\">\n" +
                "        <tr>\n" +
                "            <td colspan=\"2\"\n" +
                "                style=\"height: 150px;text-align: left;vertical-align: text-top;position: relative;width: 240px;border: 1px solid\">\n" +
                "                科室负责人意见:\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <div style=\"position: absolute;right: 10px;bottom: 10px;\">      年  月  日</div>\n" +
                "            </td>\n" +
                "            <td colspan=\"2\"\n" +
                "                style=\"height: 150px;text-align: left;vertical-align: text-top;position: relative;width: 240px;border: 1px solid\">主管领导意见:\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <div style=\"position: absolute;right: 10px;bottom: 10px;\">      年  月  日</div></td>\n" +
                "            <td colspan=\"2\"\n" +
                "                style=\"height: 150px;text-align: left;vertical-align: text-top;position: relative;width: 240px;border: 1px solid\">综合办意见:\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <div style=\"position: absolute;right: 10px;bottom: 10px;\">      年  月  日</div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"3\"\n" +
                "                style=\" height: 150px;width: 360px;text-align: left;vertical-align: text-top;position: relative;border: 1px solid \">\n" +
                "                财务科意见:\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <div style=\"position: absolute;right: 10px;bottom: 10px;\">      年  月  日</div>\n" +
                "            </td>\n" +
                "            <td colspan=\"3\"\n" +
                "                style=\" height: 150px;width: 360px;text-align: left;vertical-align: text-top;position: relative;border: 1px solid\">纪委意见:\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <div style=\"position: absolute;right: 10px;bottom: 10px;\">      年  月  日</div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"6\" style=\"border: 1px solid\">议题标题:" + topicName + "</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"6\" style=\"height: 200px;text-align: left;vertical-align: text-top;border: 1px solid\">内容摘要:" + topicContent + "\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"6\" style=\"border: 1px solid\">备注:" + topicRemark + "</td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</div>\n";
    }
}
