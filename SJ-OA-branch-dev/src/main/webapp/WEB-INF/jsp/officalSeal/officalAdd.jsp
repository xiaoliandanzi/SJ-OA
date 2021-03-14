<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2020/11/24
  Time: 20:28
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
                    <t:formvalid action="officalSeal/manager/save">
                        <input type="hidden" name="id" id="id" value="${seal.id }">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">部门编号*：</label>
                            <div class="col-sm-8">
                                <input id="departmentId" name="departmentId" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${seal.departmentId }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">部门名称*：</label>
                            <div class="col-sm-8">
                                <input id="name" name="name" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${seal.name }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态：</label>
                            <div class="col-sm-8">
                                <t:dictSelect name="status" type="select" typeGroupCode="officalsealstatus" defaultVal="${seal.status}"></t:dictSelect>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <textarea id="memo" name="memo" class="form-control" >${seal.memo}</textarea>
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
