<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/10
  Time: 上午12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,laydate,icheck,prettyfile,webuploader"></t:base>
    <script type="text/javascript">
        $(function() {
            laydate({elem:"#paperDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
        });

    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>发文审批流程</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="commonForm" action="flow/biz/paperapproval/save" method="post">
                        <input type="hidden" name="workflowId" id="workflowId" value="${workflowId }">
                        <input type="hidden" name="optType" id="optType">
                        <input type="hidden" name="id" id="id" value="${base.id }">
                        <input type="hidden" name="attachment" id="attachment" value="${biz.attachment }">

                        <div class="form-group">
                            <label class="col-sm-3 control-label">申请编号：</label>
                            <div class="col-sm-5">
                                <input id="projectNo" name="projectNo" minlength="2" type="text" class="form-control" required="" value="${base.projectNo }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">名称：</label>
                            <div class="col-sm-5">
                                <input id="name" name="name" minlength="2" type="text" class="form-control" required="" value="${base.name }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">科室：</label>
                            <p class="form-control-static">${dept}</p>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">起草人：</label>
                            <div class="col-sm-5">
                                <input id="draftMan" name="draftMan"  type="text" class="form-control" required="" readonly value="${userName }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">文件份数：</label>
                            <div class="col-sm-5">
                                <input id="paperCount" name="paperCount" type="number" class="form-control" required="" value="${biz.paperCount }">
                            </div>
                        </div>
                        <%--toto--%>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">公开选择：</label>
                            <div class="col-sm-5">
                                <t:dictSelect name="paperPublic" type="select" typeGroupCode="paperpublicstatus" defaultVal="${biz.paperPublic}"></t:dictSelect>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">保密级别：</label>
                            <div class="col-sm-5">
                                <input id="secretLevel" name="secretLevel" minlength="1" type="text" class="form-control" required="" value="${biz.secretLevel }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">发文日期：</label>
                            <div class="col-sm-8">
                                <input id="paperDate" name="paperDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${biz.paperDate }" type="date" pattern="yyyy-MM-dd"/>'>
                            </div>
                            <%--<div class="col-sm-5">
                                <input class="laydate-icon form-control layer-date" id="paperDate" name="paperDate" value='<fmt:formatDate value="${biz.paperDate }" type="date" pattern="yyyy-MM-dd"/>'>
                            </div>--%>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">发文文号：</label>
                            <div class="col-sm-5">
                                <input id="paperNumber" name="paperNumber" minlength="1" type="text" class="form-control" required="" value="${biz.paperNumber }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">发文范围：</label>
                            <div class="col-sm-5">
                                <input id="paperArea" name="paperArea" minlength="1" type="text" class="form-control" required="" value="${biz.paperArea }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">文件标题：</label>
                            <div class="col-sm-5">
                                <input id="title" name="title" minlength="1" type="text" class="form-control" required="" value="${biz.title }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">内容摘要：</label>
                            <div class="col-sm-5">
                                <input id="paperAbstract" name="paperAbstract" minlength="1" type="text" class="form-control" required="" value="${biz.paperAbstract }">
                            </div>
                        </div>
                        <c:if test="${empty biz.id }">
                            <div class="form-group">
                                <label class="col-sm-3 control-label m-b">附件:</label>
                                <div class="col-sm-2">
                                    <div id="filePicker">上传附件</div>
                                </div>
                                <div class="col-sm-4">
                                    <div id="fileList" class="uploader-list"></div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${not empty biz.id }">
                            <div class="form-group">
                                <label class="col-sm-3 control-label m-b">附件:</label>
                                <div class="col-sm-2">
                                    <div id="filePicker">上传附件</div>
                                </div>
                                <div class="col-sm-4">
                                    <div id="fileList" class="uploader-list">${attachment}</div>
                                </div>
                            </div>
                        </c:if>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-5">
                                <input id="commit" name="commit" minlength="1" type="text" class="form-control" required="" value="${biz.commit }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">紧急程度：</label>
                            <div class="col-sm-5">
                                <c:choose>
                                    <c:when test="${empty base.level}">
                                        <t:dictSelect name="level" type="radio" typeGroupCode="workflowlevel" defaultVal="0"></t:dictSelect>
                                    </c:when>
                                    <c:otherwise>
                                        <t:dictSelect name="level" type="radio" typeGroupCode="workflowlevel" defaultVal="${base.level}"></t:dictSelect>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>


                        <div class="form-group" style="margin-top: 30px;">
                            <div class="col-sm-4 col-sm-offset-3">
                                <button class="btn btn-primary" type="button" onclick="doBtnSaveDraftAction();">保存草稿</button>
                                <button class="btn btn-primary" type="button" onclick="doBtnSaveApplyAction();">发起申请</button>
                                <button type="button"  class="btn btn-primary" onclick="printIt()">打印</button>
                                <button type="button"  class="btn btn-primary" onclick="printPreView()">预览</button>
                                <button type="button"  class="btn btn-primary" onclick="printSetUp()">打印设置</button>
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

    var printPreView = function(){
        // 打印页面预览    　　
        wb.execwb(7,1);
    }
    var printIt = function(){
        if (confirm('确定打印吗？')) {
            //wb.execwb(6,6)   ;
            window.print();//update by liuguocheng

        }
    }
    var printSetUp = function(){
        // 打印页面设置
        wb.execwb(8,1);
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
            $("#fileList").html(file.name);
            $("#attachment").val(filePath);
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
</script>
</html>
