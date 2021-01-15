package com.active4j.hr.topic.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.func.upload.entity.UploadAttachmentEntity;
import com.active4j.hr.func.upload.service.UploadAttachmentService;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.topic.entity.OaTopic;
import com.active4j.hr.topic.service.OaTopicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
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
    public void test(OaTopic oaTopic, HttpServletRequest request, HttpServletResponse response) throws Exception {
        exportWord(request, response, getStr(oaTopic));
    }


    public void exportWord(HttpServletRequest request, HttpServletResponse response, String content) throws Exception {
        try {
            byte b[] = content.getBytes("utf-8");  //这里是必须要设置编码的，不然导出中文就会乱码。
            ByteArrayInputStream bais = new ByteArrayInputStream(b);//将字节数组包装到流中
            //生成word
            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            DocumentEntry documentEntry = directory.createDocument("exportWord", bais);
            //输出文件
            response.setCharacterEncoding("utf-8");
            //设置word格式
            response.setContentType("application/msword");
            response.setHeader("Content-disposition", "attachment;filename=exportWord.docx");
            OutputStream ostream = response.getOutputStream();
            poifs.writeFilesystem(ostream);
            bais.close();
            ostream.close();
        } catch (Exception e) {
            //异常处理
            System.err.println(e);
        }
    }


    private String getStr(OaTopic oaTopic) {
        oaTopic = topicService.getById(oaTopic.getId());
        String deptName = deptService.getById(oaTopic.getDeptId()).getName();
        String proposeLeaderName = oaTopic.getProposeLeaderName();
        String createUser = oaTopic.getCreateUserId();
        String reportName = oaTopic.getReportName();
        String topicName = oaTopic.getTopicName();
        String topicContent = oaTopic.getTopicContent();
        String topicRemark = oaTopic.getTopicRemark();
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" style=\"overflow: auto\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body style=\"overflow: auto\">\n" +
                "<div style=\"width: 680px;height: 750px;margin: 10px auto;\">\n" +
                "    <div><h1 style=\"text-align: center\">议 题 申 请 审 批 表</h1></div>\n" +
                "    <table width=\"670px\" hight=\"978px\" border=\"1px\"\n" +
                "           style=\"text-align: center;border: 1px black;color: black;border-collapse: collapse\">\n" +
                "        <tr>\n" +
                "            <td style=\"width: 180px\">申报科室</td>\n" +
                "            <td style=\"width: 180px\">" + deptName + "</td>\n" +
                "            <td style=\"width: 180px\">提议领导</td>\n" +
                "            <td style=\"width: 180px\">" + proposeLeaderName + "</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 180px\">负责人</td>\n" +
                "            <td style=\"width: 180px\">" + createUser + "</td>\n" +
                "            <td style=\"width: 180px\">汇报人</td>\n" +
                "            <td style=\"width: 180px\">" + reportName + "</td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "    <table width=\"670px\" hight=\"978px\" border=\"1px\" style=\"border: 1px black;color: black;border-collapse: collapse\">\n" +
                "        <tr>\n" +
                "            <td colspan=\"2\"\n" +
                "                style=\"height: 150px;text-align: left;vertical-align: text-top;position: relative;width: 220px;\">\n" +
                "                科室负责人意见:\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <div style=\"position: absolute;right: 10px;bottom: 10px;\">\n" +
                "                    &nbsp&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp日\n" +
                "                </div>\n" +
                "            </td>\n" +
                "            <td colspan=\"2\"\n" +
                "                style=\"height: 150px;text-align: left;vertical-align: text-top;position: relative;width: 220px;\">主管领导意见:\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <div style=\"position: absolute;right: 10px;bottom: 10px;\">\n" +
                "                    &nbsp&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp日\n" +
                "                </div>\n" +
                "            </td>\n" +
                "            <td colspan=\"2\"\n" +
                "                style=\"height: 150px;text-align: left;vertical-align: text-top;position: relative;width: 220px;\">综合办意见:\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <div style=\"position: absolute;right: 10px;bottom: 10px;\">\n" +
                "                    &nbsp&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp日\n" +
                "                </div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"3\"\n" +
                "                style=\" height: 150px;width: 300px;text-align: left;vertical-align: text-top;position: relative; \">\n" +
                "                财务科意见:\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <div style=\"position: absolute;right: 10px;bottom: 10px;\">\n" +
                "                    &nbsp&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp日\n" +
                "                </div>\n" +
                "            </td>\n" +
                "            <td colspan=\"3\"\n" +
                "                style=\" height: 150px;width: 300px;text-align: left;vertical-align: text-top;position: relative;\">纪委意见:\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <br/>\n" +
                "                <div style=\"position: absolute;right: 10px;bottom: 10px;\">\n" +
                "                    &nbsp&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp日\n" +
                "                </div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"6\">议题标题:" + topicName + "</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"6\" style=\"height: 200px;text-align: left;vertical-align: text-top;\">内容摘要:" + topicContent + "\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"6\">备注:" + topicRemark + "</td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }
}
