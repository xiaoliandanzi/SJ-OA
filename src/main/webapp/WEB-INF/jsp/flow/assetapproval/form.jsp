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
    <div class="col-sm-8">
        <input hidden="true" id="projectNo" name="projectNo" minlength="2" type="text" class="form-control" required="" readonly value="${base.projectNo }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">科室：</label>
    <div class="col-sm-3">
        <input id="dept" name="dept"  type="text" class="form-control" required="" readonly value="${biz.dept }">
    </div>
    <label class="col-sm-2 control-label">移交人：</label>
    <div class="col-sm-3">
        <input id="userName" name="userName"  type="text" class="form-control" required="" readonly value="${biz.userName }">
    </div>
</div>

<div class="form-group">
    <label class="col-sm-2 control-label">移交固定资产名称：*</label>
    <div class="col-sm-6">
        <input id="assetName" name="assetName" minlength="1" type="text" class="form-control" required="" value="${biz.assetName }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">数量：*</label>
    <div class="col-sm-3">
        <input id="quantity" name="quantity" type="number" class="form-control" required="" value="${biz.quantity }">
    </div>
    <label class="col-sm-2 control-label">价格：*</label>
    <div class="col-sm-3">
        <input id="amount" name="amount" type="number" class="form-control" required="" value="${biz.amount }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">规格/型号：*</label>
    <div class="col-sm-6">
        <input id="model" name="model" minlength="1" type="text" class="form-control" required="" value="${biz.model }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">移交地址：*</label>
    <div class="col-sm-6">
        <input id="address" name="address" minlength="1" type="text" class="form-control" required="" value="${biz.address }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">备注：</label>
    <div class="col-sm-6">
        <textarea id="commit" name="commit" minlength="1" type="text" class="form-control" required="">${biz.commit }</textarea>
    </div>
</div>