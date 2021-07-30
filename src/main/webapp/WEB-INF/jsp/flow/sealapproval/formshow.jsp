<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2020/12/15
  Time: 23:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">申请编号：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <input id="projectNo"  hidden="true" name="projectNo" minlength="2" type="text" class="form-control" readonly required="" value="${base.projectNo }">--%>
<%--    </div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">申请流程名称：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <input id="name"  hidden="true" name="name" minlength="2" type="text" class="form-control" readonly required="" value="${base.name }">--%>
<%--    </div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label ">开始日期：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <input class="laydate-icon form-control layer-date" id="startDay" name="startDay" value='<fmt:formatDate value="${biz.startDay }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>--%>
<%--    </div>--%>
<%--</div>--%>
<div class="form-group">
    <label class="col-sm-3 control-label m-b">使用日期：</label>
    <div class="col-sm-4 m-b">
        <input class="laydate-icon form-control layer-date" id="useDay" name="useDay"  value='<fmt:formatDate value="${biz.useDay }" type="both" pattern="yyyy-MM-dd"/>'>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-3 control-label">科室：</label>
    <div class="col-sm-5">
        <textarea id="departmentName" name="departmentName" class="form-control">${biz.departmentName }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">主送单位：</label>
    <div class="col-sm-5">
        <textarea id="useUnit" name="useUnit" class="form-control">${biz.useUnit }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">公章类型：</label>
    <div class="col-sm-5">
                <textarea id="sealtype" name="sealtype" class="form-control">${biz.sealtype }</textarea>

    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">使用人：</label>
    <div class="col-sm-5">
        <textarea id="userName" name="userName" class="form-control">${biz.userName }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">用章内容：</label>
    <div class="col-sm-5">
        <textarea id="content" name="content" class="form-control">${biz.content }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">备注：</label>
    <div class="col-sm-5">
        <textarea id="commit" name="commit" class="form-control">${biz.commit }</textarea>
    </div>
</div>
<c:if test="${empty biz.id }">
    <div class="form-group">
        <label class="col-sm-3 control-label">附件:</label>
        <div class="col-sm-5">
            <div id="filePicker">上传附件</div>
        </div>
        <div class="col-sm-5">
            <div id="fileList" class="uploader-list"></div>
        </div>
    </div>
</c:if>
<c:if test="${not empty biz.id }">
    <div class="form-group">
        <label class="col-sm-3 control-label">附件:</label>
        <div class="col-sm-4">
            <button class="btn btn-primary" type="button" onclick="doBtnDownloadFile();">下载附件</button>
        </div>
    </div>
</c:if>
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
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">紧急程度：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <c:choose>--%>
<%--            <c:when test="${empty base.level}">--%>
<%--                <t:dictSelect name="level" type="radio" typeGroupCode="workflowlevel" defaultVal="0"></t:dictSelect>--%>
<%--            </c:when>--%>
<%--            <c:otherwise>--%>
<%--                <t:dictSelect name="level" type="radio" typeGroupCode="workflowlevel" defaultVal="${base.level}"></t:dictSelect>--%>
<%--            </c:otherwise>--%>
<%--        </c:choose>--%>
<%--    </div>--%>
<%--</div>--%>
