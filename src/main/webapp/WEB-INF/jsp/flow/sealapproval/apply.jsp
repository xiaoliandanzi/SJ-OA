<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2020/12/15
  Time: 23:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,laydate,icheck,summernote,prettyfile,webuploader"></t:base>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>公章使用申请</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="commonForm" action="flow/biz/sealapproval/save" method="post">
                        <input type="hidden" name="workflowId" id="workflowId" value="${workflowId }">
                        <input type="hidden" name="optType" id="optType">
                        <input type="hidden" name="id" id="id" value="${base.id }">
                        <input type="hidden" name="attachment" id="attachment" value="${biz.attachment }">
                        <%@include file="/WEB-INF/jsp/flow/sealapproval/form.jsp" %>
                        <div class="form-group" style="margin-top: 30px;">
                            <div class="col-sm-4 col-sm-offset-3">
                                <button class="btn btn-primary" type="button" onclick="doBtnSaveApplyAction();">发起申请</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">

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

    //时间控件初始化
    $(function() {
        laydate({
            elem : "#useDay",
            event : "focus",
            istime : false,
            format : 'YYYY-MM-DD'
        });
        // laydate({
        //     elem : "#endDay",
        //     event : "focus",
        //     istime : true,
        //     format : 'YYYY-MM-DD hh:mm'
        // });
    });

    //保存草稿
    function doBtnSaveDraftAction() {
        $("#optType").val("0");
        $("#commonForm").submit();
    }

    //保存申请
    function doBtnSaveApplyAction() {
        $("#optType").val("1");
        $("#commonForm").submit();
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
            // var filePath = data.attributes.filePath;
            // $("#fileList").html(file.name);
            // $("#attachment").val(filePath);

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

    function removeFile(index) {
        $("#file-name"+index).remove();
    }
</script>
</html>
