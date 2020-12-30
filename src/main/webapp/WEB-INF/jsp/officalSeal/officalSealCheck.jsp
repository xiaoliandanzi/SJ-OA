<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2020/12/18
  Time: 16:13
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
                            <label class="col-sm-3 control-label">公章编号*：</label>
                            <div class="col-sm-8">
                                <input id="sealId" name="sealId" minlength="1" maxlength="30" type="text" class="form-control" readonly="readonly" required="" value="${seal.sealId }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">公章名称*：</label>
                            <div class="col-sm-8">
                                <input id="name" name="name" minlength="1" maxlength="30" type="text" class="form-control" readonly="readonly" required="" value="${seal.name }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态：</label>
                            <div class="col-sm-8">
                                <input id="status" name="status" minlength="1" maxlength="30" type="text" class="form-control" readonly="readonly" required="" value="${seal.status }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <textarea id="memo" name="memo" class="form-control" readonly="readonly" >${seal.memo}</textarea>
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