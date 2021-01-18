<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2021/1/18
  Time: 22:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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