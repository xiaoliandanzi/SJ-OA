<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,laydate,icheck,jqgrid"></t:base>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <t:formvalid action="item/get/savegoodstaus">
                        <input type="hidden" name="id" id="id" value="${item.id }">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">物品名称：</label>
                            <div class="col-sm-8">
                                <input id="itemName" name="itemName" minlength="1" maxlength="30" type="text" class="form-control"  value="${item.itemName }" disabled="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">领用人：</label>
                            <div class="col-sm-8">
                                <input id="userName" name="userName" minlength="1" maxlength="30" type="text" class="form-control"  value="${item.userName }" disabled="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">领用时间：</label>
                            <div class="col-sm-8">
                                <input class="laydate-icon form-control layer-date" id="getDay" name="getDay" value='<fmt:formatDate value="${item.getDay }" type="time" pattern="yyyy-MM-dd HH:mm"/>' disabled="true">
<%--                                <t:dictSelect name="type" type="select" typeGroupCode="itemkind" defaultVal="${item.type}"></t:dictSelect>--%>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">使用科室：</label>
                            <div class="col-sm-8">
                                <input id="departmentName" name="departmentName" minlength="1" maxlength="30" type="text" class="form-control" value="${item.departmentName }" disabled="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">数量：</label>
                            <div class="col-sm-8">
                                <input id="quantity" name="quantity" type="digits" class="form-control"  value="${item.quantity }" disabled="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <input id="memo" name="memo" type="digits" class="form-control"  value="${item.memo }" disabled="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">领用状态：</label>
<%--                            <div class="col-sm-8">--%>
<%--                                <input id="goodstaus" name="goodstaus" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${item.goodstaus }">--%>
<%--                            </div>--%>
                            <div class="col-sm-8">
                            <select name="goodstaus" class="form-control" required="">
                                <option value="已领取" <c:if test="${item.goodstaus =='已领取'}">selected="selected"</c:if>>已领取</option>
                                <option value="未领取" <c:if test="${item.goodstaus =='未领取'}">selected="selected"</c:if>>未领取</option>
                            </select>
                            </div>
                        </div>
                    </t:formvalid>
                </div>

                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>

