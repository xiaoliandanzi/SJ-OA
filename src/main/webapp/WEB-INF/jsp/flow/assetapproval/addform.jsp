<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/22
  Time: 下午10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="form-group">
    <label class="col-sm-3 control-label">资产管理部门*：</label>
    <div class="col-sm-8">
        <div class="input-group">
            <t:choose url="common/selectDepart" hiddenName="dept" hiddenValue="${biz.dept}" textValue="" textName="departLabel" hiddenId="departId" textId="departLabel"></t:choose>
        </div>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-2 control-label">固定资产*：</label>
    <div class="col-sm-3">
        <input id="assetName" name="assetName"  type="text" class="form-control" required="" value="${biz.assetName }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">数量*：</label>
    <div class="col-sm-3">
        <input id="quantity" name="quantity"  type="text" class="form-control" required="" value="${biz.quantity }">
    </div>
    <label class="col-sm-2 control-label">价格*：</label>
    <div class="col-sm-3">
        <input id="amount" name="amount"  type="text" class="form-control" required="" value="${biz.amount }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">规格型号*：</label>
    <div class="col-sm-3">
        <input id="model" name="model"  type="text" class="form-control" required="" value="${biz.model }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label m-b">入库日期*：</label>
    <div class="col-sm-4 m-b">
        <input class="laydate-icon form-control layer-date" id="time" name="time" value='<fmt:formatDate value="${biz.time }" type="time" pattern="yyyy-MM-dd"/>'>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">库存地址*：</label>
    <div class="col-sm-3">
        <input id="address" name="address"  type="text" class="form-control" required="" value="${biz.address }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">用途*：</label>
    <div class="col-sm-3">
        <input id="application" name="application"  type="text" class="form-control" required="" value="${biz.application }">
    </div>
</div>
<input type="hidden" name="jsonData" id="jsonData">

<div class="form-group">
    <label class="col-sm-3 control-label">备注：</label>
    <div class="col-sm-5">
        <input id="commit" name="commit" minlength="1" type="text" class="form-control" value="${biz.commit }">
    </div>
</div>