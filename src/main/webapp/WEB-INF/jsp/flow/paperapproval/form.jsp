<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/10
  Time: 上午12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
        <input id="paperPublic" name="paperPublic" type="number" class="form-control" required="" value="${biz.paperPublic }">
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

<div class="form-group">
    <label class="col-sm-3 control-label m-b">附件:</label>
    <div class="col-sm-2">
        <div id="filePicker">上传附件</div>
    </div>
    <div class="col-sm-4">
        <div id="fileList" class="uploader-list"></div>
    </div>
</div>

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

