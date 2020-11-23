<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/11/22
  Time: 下午8:43
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
                    <t:formvalid action="car/carBooks/save">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">车辆*：</label>
                            <div class="col-sm-8">
                                <p class="form-control-static">${carName } ${carId }</p>
                                <input type="hidden" name="id" id="id" value="${id }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">开始时间：</label>
                            <div class="col-sm-4 m-b">
                                <div class="input-group clockpicker" data-autoclose="true">
                                    <input type="text" class="form-control" name="startDate" value='<fmt:formatDate value="${car.startDate }" type="time" pattern="HH:mm"/>'>
                                    <span class="input-group-addon">
			                                   <span class="fa fa-clock-o"></span>
			                            </span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">结束时间：</label>
                            <div class="col-sm-4 m-b">
                                <div class="input-group clockpicker" data-autoclose="true">
                                    <input type="text" class="form-control" name="endDate" value='<fmt:formatDate value="${car.endDate }" type="time" pattern="HH:mm"/>'>
                                    <span class="input-group-addon">
			                                   <span class="fa fa-clock-o"></span>
			                            </span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">备注：</label>
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
<%--<script type="text/javascript">

    $(function() {
        laydate({
            elem : "#bookDate",
            event : "focus",
            istime : false,
            format : 'YYYY-MM-DD'
        });


        $('.clockpicker').clockpicker();

    });




</script>--%>
</html>


