<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/22
  Time: 下午10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,laydate,ckeditor,icheck,prettyfile,webuploader,summernote"></t:base>
    <%--<script type="text/javascript">--%>
        <%--$(function() {--%>
            <%--laydate({elem:"#publicTime",event:"focus",istime: false, format: 'YYYY-MM-DD HH:mm'});--%>
        <%--});--%>

    <%--</script>--%>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>信息发布流程</h5>
                </div>
                <div class="ibox-content">
                    <t:formvalid beforeSubmit="setEditValue();" action="flow/biz/messageapproval/save" formid="commonForm"
                                 cssClass="form-horizontal m-t">
                    <%--<form class="form-horizontal m-t" id="commonForm" method="post">--%>
                        <input type="hidden" name="workflowId" id="workflowId" value="${workflowId }">
                        <input type="hidden" name="optType" id="optType">
                        <input type="hidden" name="id" id="id" value="${base.id }">
                        <input type="hidden" name="attachment" id="attachment" value="${biz.attachment }">

                        <%@include file="/WEB-INF/jsp/flow/messageapproval/form.jsp" %>

                        <div class="form-group" style="margin-top: 30px;">
                            <div class="col-sm-5 col-sm-offset-4">
                                <button class="btn btn-primary" type="button" onclick="doBtnSaveDraftAction();">保存草稿</button>
                                <button class="btn btn-primary" type="button" onclick="doBtnSaveApplyAction();">发起申请</button>
                            </div>
                        </div>
                    <%--</form>--%>
                    </t:formvalid>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
    var editor = CKEDITOR.replace('content');
    function getContenet(){
        return CKEDITOR.instances.content.getData();    //获取textarea的值
        //return  CKEDITOR.instances.content.document.getBody().getText();
    }



    $(function() {
        $("#summernote").summernote({
            lang : "zh-CN",
            height : 300,
            // 重写图片上传
            onImageUpload : function(files, editor, $editable) {
                uploadFile(files[0], editor, $editable);
            }
        });

        var content = '${biz.content}';

        $('#summernote').code(content);
    });

    function uploadFile(file, editor, $editable) {
        var filename = false;
        try {
            filename = file['name'];
        } catch (e) {
            filename = false;
        }
        if (!filename) {
            $(".note-alarm").remove();
        }

        //以上防止在图片在编辑器内拖拽引发第二次上传导致的提示错误
        data = new FormData();
        data.append("file", file);
        data.append("key", filename); //唯一性参数

        $.ajax({
            data : data,
            type : "POST",
            url : "func/upload/uploadImages?db=1",
            cache : false,
            contentType : false,
            processData : false,
            success : function(data) {
                var o = $.parseJSON(data);
                if(o.success) {
                    var filePath = o.attributes.filePath;
                    editor.insertImage($editable, filePath);
                }else {
                    qhTipError("上传失败!" + o.msg);
                }
            },
            error : function() {
                qhTipError("上传失败!");
                return;
            }
        });
    }

    function setEditValue() {

        var content = $('#summernote').code();

        $("#content").val(content);

        return true;
    }



    $(function() {
        //初始化Web Uploader
        var uploader2 = WebUploader.create({

            // 选完文件后，是否自动上传。
            auto : true,

            // swf文件路径
            swf : 'static/webuploader/Uploader.swf',

            // 文件接收服务端。
            server : 'func/upload/uploadFiles?db=1',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick : {
                id : '#filePicker'
            },

            fileSizeLimit: 5 * 1024 * 1024

        });


        // 文件上传过程中创建进度条实时显示。
        uploader2.on('uploadProgress', function(file, percentage) {
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader2.on('uploadSuccess', function(file, data) {
            var filePath = data.attributes.filePath;
            var count = $(".file-name").length;
            $("#fileList").append("<div class='file-name' id='file-name"+(count+1)+"'>"+file.name+"<a href='javascript:removeFile("+(count+1)+")'>删除</a></div>")
            // $("#fileList").html(file.name);
            var attachment = $("#attachment").val();
            if(attachment != ''){
                attachment = JSON.parse(attachment)
            }else{
                attachment = [];
            }
            attachment.push(filePath)

            $("#attachment").val(JSON.stringify(attachment));
        });

        // 文件上传失败，显示上传出错。
        uploader2.on('uploadError', function(file) {

        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader2.on('uploadComplete', function(file) {
            qhTipSuccess('上传完成....');
        });

    });

    //表单验证
    $(function() {
        $("#commonForm").validate({
            submitHandler : function(form) {
                $(form).ajaxSubmit({
                    success : function(o) {
                        if (o.success) {
                            qhTipSuccess('保存成功');
                            location.href='common/goSuccess';
                        } else {
                            qhTipWarning(o.msg);
                        }
                    },
                    error : function(data) {
                        qhTipError('系统错误，请联系系统管理员');
                    }
                });
            }
        });
    });

    function removeFile(index) {
        $("#file-name"+index).remove();
    }

    //保存草稿
    function doBtnSaveDraftAction() {
        var kcontent=getContenet();
        $("#content").val(kcontent);


        $("#optType").val("0");
        $("#commonForm").submit();
    }

    //保存申请
    function doBtnSaveApplyAction() {
        var kcontent=getContenet();
        $("#content").val(kcontent);

        $("#optType").val("1");
        $("#commonForm").submit();
    }
</script>
</html>
