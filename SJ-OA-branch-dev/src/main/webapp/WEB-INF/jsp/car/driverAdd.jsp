<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2021/1/14
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,laydate,clock"></t:base>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <t:formvalid action="driver/manager/save">
                        <input type="hidden" name="id" id="id" value="${driver.id }">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">姓名*：</label>
                            <div class="col-sm-8">
                                <input id="name" name="name" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${driver.name }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">年龄*：</label>
                            <div class="col-sm-8">
                                <input id="age" name="age" type="digits" class="form-control" required="" value="${driver.age }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label m-b">出身年月*：</label>
                            <div class="col-sm-8 m-b">
                                <input class="laydate-icon form-control layer-date" id="birthday" name="birthday" required="" value='<fmt:formatDate value="${driver.birthday }" type="date" pattern="yyyy-MM-dd-HH-mm"/>'>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <textarea id="memo" name="memo" class="form-control" >${driver.memo}</textarea>
                            </div>
                        </div>
                    </t:formvalid>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">

    $(function() {
        laydate({
            elem : "#birthday",
            event : "focus",
            istime : false,
            format : 'YYYY-MM-DD'
        });


        $('.clockpicker').clockpicker();

    });
</script>
</html>
