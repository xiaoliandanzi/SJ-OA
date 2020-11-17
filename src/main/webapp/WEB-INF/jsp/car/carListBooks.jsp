<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/11/17
  Time: 下午10:54
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
                    <t:formvalid action="csar/list/save">
                        <input type="hidden" name="id" id="id" value="${car.id }">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">车牌号*：</label>
                            <div class="col-sm-8">
                                <input id="carId" name="carId" minlength="1" maxlength="30" type="text" class="form-control" required="" value="沪A88888">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">车名*：</label>
                            <div class="col-sm-8">
                                <input id="carName" name="carName" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${car.name }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">载人数：</label>
                            <div class="col-sm-8">
                                <input id="persons" name="persons" type="digits" class="form-control" required="" value="${car.persons }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态：</label>
                            <div class="col-sm-8">
                                <t:dictSelect name="status" type="select" typeGroupCode="oaworkmeet" defaultVal="${car.status}"></t:dictSelect>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <textarea id="memo" name="memo" class="form-control" >${car.memo}</textarea>
                            </div>
                        </div>
                    </t:formvalid>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>


