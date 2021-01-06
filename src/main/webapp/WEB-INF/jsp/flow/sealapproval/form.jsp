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
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label m-b">结束日期：</label>--%>
<%--    <div class="col-sm-4 m-b">--%>
<%--        <input class="laydate-icon form-control layer-date" id="endDay" name="endDay"  value='<fmt:formatDate value="${biz.endDay }" type="both" pattern="yyyy-MM-dd HH:mm"/>'>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">结束日期：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <input class="laydate-icon form-control layer-date" id="endDay" name="endDay" value='<fmt:formatDate value="${biz.endDay }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>--%>
<%--    </div>--%>
<%--</div>--%>
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
    <label class="col-sm-3 control-label">借用人：</label>
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
