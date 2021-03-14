<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/2
  Time: 上午12:21
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2020/11/18
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default"></t:base>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <t:formvalid action="item/manage/requisition/save">
                        <input type="hidden" name="id" id="id" value="${item.id }">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">名称*：</label>
                            <div class="col-sm-8">
                                <input id="name" name="name" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${item.name }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">物品类型*：</label>
                            <div class="col-sm-8">
                                <t:dictSelect name="type" type="select" typeGroupCode="itemkind" defaultVal="${item.type}"></t:dictSelect>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">编号：</label>
                            <div class="col-sm-8">
                                <input id="itemId" name="itemId" minlength="1" maxlength="30" type="text" class="form-control"  value="${item.itemId }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">规格/型号：</label>
                            <div class="col-sm-8">
                                <input id="model" name="model" minlength="1" maxlength="30" type="text" class="form-control" value="${item.model }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">数量*：</label>
                            <div class="col-sm-8">
                                <input id="quantity" name="quantity" type="digits" class="form-control" required="" value="${item.quantity }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">低量预警*：</label>
                            <div class="col-sm-8">
                                <input id="minQuantity" name="minQuantity" type="digits" class="form-control" required="" value="${item.minQuantity }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">数量单位*：</label>
                            <div class="col-sm-8">
                                <input id="unit" name="unit" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${item.unit }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">存放地点*：</label>
                            <div class="col-sm-8">
                                <input id="location" name="location" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${item.location }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">保管人*：</label>
                            <div class="col-sm-8">
                                <input id="keeper" name="keeper" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${item.keeper }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <textarea id="memo" name="memo" class="form-control" >${item.memo}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">记录：</label>
                            <div class="col-sm-8">
                                <input name="jsonData"  type="hidden"  value='${asset.jsonData }'>
                                <div id="record"></div>
                            </div>
                        </div>
<%--                        <label class="col-sm-3 control-label">状态：</label>--%>
<%--                        <div class="col-sm-8">--%>
<%--                            <t:dictSelect name="status" type="select" typeGroupCode="itemstatus" defaultVal="${item.status}"></t:dictSelect>--%>
<%--                        </div>--%>
                </div>
                    </t:formvalid>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>

