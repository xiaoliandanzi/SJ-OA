<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/8
  Time: 下午10:56
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
    <label class="col-sm-3 control-label">所申车辆：</label>
    <%--<div class="col-sm-8">
        <div class="input-group">
            <t:choose url="oa/hr/recruit/plan/selectNeed" hiddenName="needJobIds" hiddenValue="${needIds }" textValue="${needJobs }" textName="needJobs" width="80%" height="80%" hiddenId="needJobIds" textId="needJobs"></t:choose>
        </div>
    </div>--%>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">用车单位：</label>
    <p class="form-control-static">${dept}</p>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">乘车人：</label>
    <div class="col-sm-5">
        <input id="userName" name="userName" minlength="1" type="text" class="form-control" required="" value="${biz.userName }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">乘车人数：</label>
    <div class="col-sm-5">
        <input id="person" name="person" type="number" class="form-control" required="" value="${biz.person }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">开始日期：</label>
    <div class="col-sm-5">
        <input class="laydate-icon form-control layer-date" id="startDay" name="startDay" value='<fmt:formatDate value="${biz.startDay }" type="date" pattern="yyyy-MM-dd"/>'>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">结束日期：</label>
    <div class="col-sm-5">
        <input class="laydate-icon form-control layer-date" id="endDay" name="endDay" value='<fmt:formatDate value="${biz.endDay }" type="date" pattern="yyyy-MM-dd"/>'>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">目的地：</label>
    <div class="col-sm-5">
        <input id="destination" name="destination" minlength="1" type="text" class="form-control" required="" value="${biz.destination }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">用车事由：</label>
    <div class="col-sm-5">
        <input id="reason" name="reason" minlength="1" type="text" class="form-control" required="" value="${biz.reason }">
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
