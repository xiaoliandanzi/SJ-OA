<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2021/1/18
  Time: 22:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="form-group">
    <label class="col-sm-3 control-label">采购部门*：</label>
    <div class="col-sm-5">
        <textarea id="departmentName" name="departmentName" required="" class="form-control">${biz.departmentName }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">经办人*：</label>
    <div class="col-sm-5">
        <textarea id="agent" name="agent" required="" class="form-control">${biz.agent }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">电话*：</label>
    <div class="col-sm-5">
        <textarea id="telephone" name="telephone" required="" class="form-control">${biz.telephone }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">采购项目明细*：</label>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">品名*：</label>
    <div class="col-sm-5">
        <textarea id="itemName" name="itemName" required="" class="form-control">${biz.itemName }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">品牌、规格型号*：</label>
    <div class="col-sm-5">
        <textarea id="type" name="type" required="" class="form-control">${biz.type }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">数量*：</label>
    <div class="col-sm-5">
<%--        <textarea id="number" name="number" class="form-control">${biz.number }</textarea>--%>
        <input id="number" name="number" type="number" class="form-control" required="" value="${biz.number }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">单价*：</label>
    <div class="col-sm-5">
        <input id="unitPrice" name="unitPrice" type="number" class="form-control" required="" value="${biz.unitPrice }">
<%--        <textarea id="unitPrice" name="unitPrice" required="" class="form-control">${biz.unitPrice }</textarea>--%>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">用途*：</label>
    <div class="col-sm-5">
        <textarea id="purpose" name="purpose" required="" class="form-control">${biz.purpose }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">合计*：</label>
    <div class="col-sm-5">
        <textarea id="price" name="price" required="" class="form-control">${biz.price }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">资金来源*：</label>
    <div class="col-sm-5">
        <textarea id="foundSource" name="foundSource" required="" class="form-control">${biz.foundSource }</textarea>
    </div>
</div>