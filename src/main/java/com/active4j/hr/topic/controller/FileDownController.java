package com.active4j.hr.topic.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.func.upload.entity.UploadAttachmentEntity;
import com.active4j.hr.func.upload.service.UploadAttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

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
}
