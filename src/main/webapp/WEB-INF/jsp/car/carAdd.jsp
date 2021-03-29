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
    <t:base type="default,laydate,clock"></t:base>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <t:formvalid action="car/manage/save">
                        <input type="hidden" name="id" id="id" value="${car.id }">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">车牌号*：</label>
                            <div class="col-sm-8">
                                <input id="carId" name="carId" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${car.carId }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">车辆使用状态*：</label>
                            <div class="col-sm-8">
<%--                                <input id="kind" name="kind" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${car.kind }">--%>
                                <select name="kind" class="form-control" required="">
                                    <option value="正在使用" <c:if test="${car.kind =='正在使用'}">selected="selected"</c:if>>正在使用</option>
                                    <option value="停用" <c:if test="${car.kind =='停用'}">selected="selected"</c:if>>停用</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label m-b">车辆购置时间*：</label>
                            <div class="col-sm-8 m-b">
                                <input class="laydate-icon form-control layer-date" id="onRoadTime" name="onRoadTime" required="" value='<fmt:formatDate value="${car.onRoadTime }" type="date" pattern="yyyy-MM"/>'>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label m-b">验车日期*：</label>
                            <div class="col-sm-8 m-b">
                                <input class="laydate-icon form-control layer-date" id="checkCarTime" name="checkCarTime" required="" value='<fmt:formatDate value="${car.checkCarTime }" type="date" pattern="yyyy-MM"/>'>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-3 control-label m-b">保险到期日期*：</label>
                            <div class="col-sm-8 m-b">
                                <input class="laydate-icon form-control layer-date" id="ensureTime" name="ensureTime" required="" value='<fmt:formatDate value="${car.ensureTime }" type="date" pattern="yyyy-MM"/>'>
                            </div>
                        </div>
<%--                        <div class="form-group">--%>
<%--                            <label class="col-sm-3 control-label">保险提醒频率/天*：</label>--%>
<%--                            <div class="col-sm-8">--%>
<%--                                <input id="ensureDay" name="ensureDay" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${car.ensureDay }">--%>
<%--                            </div>--%>
<%--                        </div>--%>
                        <div class="form-group">
                            <label class="col-sm-3 control-label m-b">维修日期：</label>
                            <div class="col-sm-8 m-b">
                                <input class="laydate-icon form-control layer-date" id="maintainTime" name="maintainTime"  value='<fmt:formatDate value="${car.maintainTime }" type="date" pattern="yyyy-MM-dd"/>'>
                            </div>
                        </div>
<%--                        <div class="form-group">--%>
<%--                            <label class="col-sm-3 control-label">保养提醒频率/天*：</label>--%>
<%--                            <div class="col-sm-8">--%>
<%--                                <input id="maintainDay" name="maintainDay" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${car.maintainDay }">--%>
<%--                            </div>--%>
<%--                        </div>--%>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <input id="memo" name="memo" type="digits" class="form-control"  value="${car.memo}">
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
            elem : "#onRoadTime",
            event : "focus",
            istime : false,
            format : 'YYYY-MM'
        });

        laydate({
            elem : "#ensureTime",
            event : "focus",
            istime : false,
            format : 'YYYY-MM'
        });

        laydate({
            elem : "#maintainTime",
            event : "focus",
            istime : false,
            format : 'YYYY-MM-DD'
        });
        laydate({
            elem : "#checkCarTime",
            event : "focus",
            istime : false,
            format : 'YYYY-MM'
        });

        $('.clockpicker').clockpicker();

    });
</script>
</html>
