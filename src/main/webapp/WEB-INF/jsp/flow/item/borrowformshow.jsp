<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2021/1/7
  Time: 22:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="form-group">
    <label class="col-sm-3 control-label">借用科室*：</label>
    <div class="col-sm-5">
        <textarea id="departmentName" name="departmentName" readonly class="form-control">${biz.departmentName }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">借用人*：</label>
    <div class="col-sm-5">
        <textarea id="userName" name="userName" readonly class="form-control">${biz.userName }</textarea>
    </div>
</div>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">借用物品*：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <textarea id="itemName" name="itemName" class="form-control">${biz.itemName }</textarea>--%>
<%--    </div>--%>
<%--</div>--%>
<div class="form-group">
    <label class="col-sm-3 control-label">借用物品*：</label>
    <div class="col-sm-5">
        <textarea id="itemName" name="itemName" readonly class="form-control">${biz.itemName }</textarea>
    </div>
</div>

<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">备注：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <textarea id="quantity" name="quantity" class="form-control">${biz.quantity }</textarea>--%>
<%--    </div>--%>
<%--</div>--%>
<div class="form-group">
    <label class="col-sm-3 control-label">借用数量*：</label>
    <div class="col-sm-5">
        <input id="quantity" name="quantity" type="number" class="form-control" readonly required="" value="${biz.quantity }">
    </div>
</div>

<div class="form-group">
    <label class="col-sm-3 control-label m-b">使用日期*：</label>
    <div class="col-sm-4 m-b">
        <input class="laydate-icon form-control layer-date" id="useDay" name="useDay" readonly value='<fmt:formatDate value="${biz.useDay }" type="both" pattern="yyyy-MM-dd"/>'>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-3 control-label m-b">归还日期*：</label>
    <div class="col-sm-4 m-b">
        <input class="laydate-icon form-control layer-date" id="returnDay" name="returnDay" readonly value='<fmt:formatDate value="${biz.returnDay }" type="both" pattern="yyyy-MM-dd"/>'>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-3 control-label">借用事由*：</label>
    <div class="col-sm-5">
        <textarea id="reason" name="reason" readonly class="form-control">${biz.reason }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">备注：</label>
    <div class="col-sm-5">
        <textarea id="commit" name="commit" readonly class="form-control">${biz.commit }</textarea>
    </div>
</div>

