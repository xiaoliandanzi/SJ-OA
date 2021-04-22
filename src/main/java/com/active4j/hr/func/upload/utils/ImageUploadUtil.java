package com.active4j.hr.func.upload.utils;


import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.FileUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ImageUploadUtil
 * @Description: 图片上传工具类，包括ckeditor操作
 *
 * */
public class ImageUploadUtil {
    // 图片类型
    private static List<String> fileTypes = new ArrayList<String>();

    static {
        fileTypes.add(".jpg");
        fileTypes.add(".jpeg");
        fileTypes.add(".bmp");
        fileTypes.add(".gif");
        fileTypes.add(".png");
    }

    /**
     * 图片上传
     *
     * @Title upload
     * @param request
     * @param DirectoryName
     * @return
     * @throws IllegalStateException
     * @throws IOException
    **/
    public static String upload(MultipartHttpServletRequest request, String DirectoryName)
            throws IllegalStateException,IOException {
        String fileName="";
        try {

            // 创建一个通用的多部分解析器
            Map<String, MultipartFile> fileMap = request.getFileMap();
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                MultipartFile mf = entity.getValue();// 获取上传文件对象
                fileName = mf.getOriginalFilename();// 获取文件名
                String extend = FileUtils.getExtend(fileName);// 获取文件扩展名

                //String realPath = request.getSession().getServletContext().getRealPath("/") + "/upload/";// 文件的硬盘真实路径
                String realPath = request.getSession().getServletContext().getRealPath("/");
                String substr=realPath.substring(0,realPath.lastIndexOf("/",realPath.lastIndexOf("/")-1));
                realPath = substr + "/upload/";// 文件的硬盘真实路径
                String path = "upload/" ;

                File file = new File(realPath);
                if (!file.exists()) {
                    file.mkdirs();// 创建根目录
                }
                String noextfilename = DateUtils.getDataString(DateUtils.SDF_YYYYMMDDHHMMSS);//自定义文件名称
                fileName= noextfilename+"."+extend;//自定义文件名称
                String savePath = realPath + fileName;// 文件保存全路径
                //path = path + fileName;

                File savefile = new File(savePath);
                FileCopyUtils.copy(mf.getBytes(), savefile);
            }
        }catch (Exception e){
            throw e;
        }
        return fileName;

    }

    /**
     * ckeditor文件上传功能，回调，传回图片路径，实现预览效果。
     * @Title ckeditor
     * @param request
     * @param response
     * @param DirectoryName
     **/
    public static void ckeditor(MultipartHttpServletRequest request, HttpServletResponse response, String DirectoryName)
            throws IOException {
        String fileName = upload(request, DirectoryName);
        // 结合ckeditor功能
        // imageContextPath为图片在服务器地址，如upload/123.jpg,非绝对路径
        String imageContextPath =request.getContextPath()+"/"+DirectoryName+"/"+fileName;
        String substring = imageContextPath.substring(3);

        PrintWriter out = response.getWriter();
        request.setAttribute("image", substring);

        String remotefilePath = substring;
        String callback = request.getParameter("CKEditorFuncNum");
        out.println("<script type=\"text/javascript\">");
        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback
                + ",'" + remotefilePath + "',''" + ")");
        out.println("</script>");
        out.flush();
        out.close();
    }



}
