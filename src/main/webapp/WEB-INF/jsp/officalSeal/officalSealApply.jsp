<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2020/12/17
  Time: 19:04
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
    <div class="row"><%----%>
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <t:formvalid action="officalSeal/officalSealBooks/save">
                        <input type="hidden" name="id" id="id" value="${seal.id }">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">公章名称*：</label>
                            <div class="col-sm-8">
                                <select name="sealId" class="form-control" required="">
                                    <c:forEach items="${lstSeals }" var="c">
                                        <option value="${c.id }" <c:if test="${seal.sealId == c.id }">selected="selected"</c:if>>${c.name }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">借用日期：</label>
                            <div class="col-sm-4 m-b">
                                <input class="laydate-icon form-control layer-date" id="bookDate" name="bookDate" required="" value='<fmt:formatDate value="${seal.bookDate }" type="date" pattern="yyyy-MM-dd-HH-mm"/>'>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">科室：</label>
                            <div class="col-sm-4 m-b">
                                <input id="departmentName" name="departmentName" type="text" class="form-control" required="" value="${seal.departmentName }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">主送单位：</label>
                            <div class="col-sm-4 m-b">
                                <input id="useUnit" name="useUnit" type="text" class="form-control" required="" value="${seal.useUnit }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">内容：</label>
                            <div class="col-sm-4 m-b">
                                <input id="content" name="content" type="text" class="form-control" required="" value="${seal.content }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">开始时间：</label>
                            <div class="col-sm-4 m-b">
                                <div class="input-group clockpicker" data-autoclose="true">
                                    <input type="text" class="form-control" name="startDate" value='<fmt:formatDate value="${seal.startDate }" type="time" pattern="HH:mm"/>'>
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
                                    <input type="text" class="form-control" name="endDate" value='<fmt:formatDate value="${seal.endDate }" type="time" pattern="HH:mm"/>'>
                                    <span class="input-group-addon">
			                                   <span class="fa fa-clock-o"></span>
			                            </span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">备注：</label>
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
<script type="text/javascript">

    $(function() {
        laydate({
            elem : "#bookDate",
            event : "focus",
            istime : false,
            format : 'YYYY-MM-DD'
        });


        $('.clockpicker').clockpicker();

    });
</script>
</html>


