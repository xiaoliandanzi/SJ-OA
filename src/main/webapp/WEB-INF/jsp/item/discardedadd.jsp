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
                    <t:formvalid action="item/manage/discarded/save">
                        <input type="hidden" name="id" id="id" value="${item.id }">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">名称*：</label>
                            <div class="col-sm-8">
                                <input id="name" name="name" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${item.name }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">种类*：</label>
                            <div class="col-sm-8">
                                <input id="type" name="type" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${item.type }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">品牌*：</label>
                            <div class="col-sm-8">
                                <input id="board" name="board" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${item.board }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">数量：</label>
                            <div class="col-sm-8">
                                <input id="quantity" name="quantity" type="digits" class="form-control" required="" value="${item.quantity }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态：</label>
                            <div class="col-sm-8">
                                <t:dictSelect name="status" type="select" typeGroupCode="itemstatus" defaultVal="${item.status}"></t:dictSelect>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <textarea id="memo" name="memo" class="form-control" >${item.memo}</textarea>
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

