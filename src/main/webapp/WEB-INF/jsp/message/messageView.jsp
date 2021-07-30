<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/23
  Time: 上午12:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,laydate,icheck"></t:base>
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
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="commonForm">
                        <input type="hidden" name="attachment" id="attachment" value="${biz.attachment }">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">信息类型：</label>
                            <div class="col-sm-8">
                                <%--<t:dictSelect name="messageType" type="select" typeGroupCode="messagetypes" defaultVal="${biz.messageType}"></t:dictSelect>--%>
                                    <input type="text" class="form-control" required="" readonly value="${type }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">科室：</label>
                            <div class="col-sm-8">
                                <input id="dept" name="dept"  type="text" class="form-control" required="" readonly value="${biz.dept }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">发布人：</label>
                            <div class="col-sm-8">
                                <input id="publicMan" name="publicMan"  type="text" class="form-control" required="" readonly value="${biz.publicMan }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">标题：</label>
                            <div class="col-sm-8">
                                <input readonly id="title" name="title" minlength="1" type="text" class="form-control" required="" value="${biz.title }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">内容：</label>
                            <div class="col-sm-8">
                                <textarea rows="4" readonly id="content" name="content" minlength="1" type="text" class="form-control" required="">${biz.content }</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label m-b">附件：</label>
                            <div class="col-sm-8">
                                <button class="btn btn-primary" type="button" onclick="doBtnDownloadFile();">下载附件</button>
                                <div id="fileList" class="uploader-list">${biz.attachment}</div>
                            </div>
                        </div>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-3 control-label">紧急程度：</label>--%>
                            <%--<div class="col-sm-8">--%>
                                <%--<c:choose>--%>
                                    <%--<c:when test="${empty base.level}">--%>
                                        <%--<t:dictSelect name="level" type="radio" typeGroupCode="workflowlevel" defaultVal="0"></t:dictSelect>--%>
                                    <%--</c:when>--%>
                                    <%--<c:otherwise>--%>
                                        <%--<t:dictSelect name="level" type="radio" typeGroupCode="workflowlevel" defaultVal="${base.level}"></t:dictSelect>--%>
                                    <%--</c:otherwise>--%>
                                <%--</c:choose>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>

<script type="text/javascript">
    // function doBtnDownloadFile() {
    //     var att = document.getElementById("attachment").value;
    //     if(!att) {
    //         qhAlert('该文件附件还未上传附件！');
    //         return;
    //     }
    //
    //     location.href = "func/upload/download?id=" + att;
    // };

    function doBtnDownloadFile() {
        var att = document.getElementById("attachment").value;

        if(!att) {
            qhAlert('该文件附件还未上传附件！');
            return;
        }

        var list=att.split(",");
        for (const url of list) {
            donw("func/upload/download?id="+url);
        }
    };

    function donw(url) {
        var iframe = document.createElement("iframe");
        iframe.src = url;
        iframe.style.display = "none";
        document.body.appendChild(iframe);
    };
</script>

</html>
