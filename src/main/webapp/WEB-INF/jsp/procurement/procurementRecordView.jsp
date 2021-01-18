<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2021/1/18
  Time: 23:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,laydate,icheck"></t:base>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>公章审批流程</h5>
                </div>
                <div class="ibox-content">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">借用人：</label>
                        <p class="form-control-static">${seal.userName }</p>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">科室：</label>
                        <p class="form-control-static">${seal.departmentName }</p>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">主送单位：</label>
                        <p class="form-control-static">${seal.useUnit }</p>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">内容：</label>
                        <p class="form-control-static">${seal.content }</p>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">审批状态：</label>
                        <p class="form-control-static">${seal.applyStatus } </p>
                    </div>

                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
