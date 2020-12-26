<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/22
  Time: 下午10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="form-group">
    <label class="col-sm-2 control-label">申请编号：</label>
    <div class="col-sm-3">
        <input id="projectNo" name="projectNo" minlength="2" type="text" class="form-control" required="" readonly value="${base.projectNo }">
    </div>
    <label class="col-sm-2 control-label">名称：</label>
    <div class="col-sm-3">
        <input id="name" name="name" minlength="2" type="text" class="form-control" required="" readonly value="${base.name }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">科室：</label>
    <div class="col-sm-3">
        <input id="dept" name="dept"  type="text" class="form-control" required="" readonly value="${biz.dept }">
    </div>
    <label class="col-sm-2 control-label">发布人：</label>
    <div class="col-sm-3">
        <input id="publicMan" name="publicMan"  type="text" class="form-control" required="" readonly value="${biz.publicMan }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">信息类型：*</label>
    <div class="col-sm-3">
        <t:dictSelect name="messageType" type="select" typeGroupCode="messagetypes" defaultVal="${biz.messageType}"></t:dictSelect>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-2 control-label">标题：*</label>
    <div class="col-sm-6">
        <input id="title" name="title" minlength="1" type="text" class="form-control" required="" value="${biz.title }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">内容：*</label>
    <div class="col-sm-6">
        <textarea rows=8 id="content" name="content" minlength="1" type="text" class="form-control" required="">${biz.content }</textarea>
    </div>
</div>
<c:if test="${empty biz.id }">
    <div class="form-group">
        <label class="col-sm-2 control-label m-b">附件:</label>
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
        <label class="col-sm-2 control-label m-b">附件:</label>
        <div class="col-sm-2">
            <div id="filePicker">上传附件</div>
        </div>
        <div class="col-sm-4">
            <div id="fileList" class="uploader-list">${attachment}</div>
        </div>
    </div>
</c:if>

<div class="form-group">
    <label class="col-sm-2 control-label">紧急程度：</label>
    <div class="col-sm-6">
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
