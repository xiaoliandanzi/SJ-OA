<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/11/22
  Time: 下午9:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,jqgrid,laydate"></t:base>
</head>
<body class="gray-bg">
<!-- 页面部分 -->
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="row">
                <div class="col-sm-12" id="searchGroupId">
                </div>
            </div>
            <div class="ibox">
                <div class="ibox-content">
                    <div id="oaCarBooksTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="car/carBooks/datagrid" tableContentId="oaCarBooksTable" searchGroupId="searchGroupId" fit="true" caption="车辆预定" name="oaCarBooksList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="Id" label="车辆" width="100" query="true" queryId="id" replace="${lstCars }"></t:dgCol>
    <t:dgCol name="userName" label="预定人" width="80"></t:dgCol>
    <%--<t:dgCol name="bookDate" label="预定日期" width="100" datefmt="yyyy-MM-dd" query="true" defval="${nowStrDate }" datePlugin="laydate"></t:dgCol>--%>
    <t:dgCol name="startDate" label="开始时间" width="80" datefmt="HH:mm"></t:dgCol>
    <t:dgCol name="endDate" label="结束时间" width="80" datefmt="HH:mm"></t:dgCol>
    <t:dgCol name="opt" label="操作" ></t:dgCol>
    <t:dgDelOpt label="删除" url="oa/carBooks/delete?id={id}"/>
    <t:dgToolBar url="car/carBooks/addorupdate" type="add" width="50%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="car/carBooks/addorupdate" type="edit" width="50%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="car/carBooks/addorupdate" type="view" width="50%" height="70%"></t:dgToolBar>
    <t:dgToolBar  type="refresh" ></t:dgToolBar>
</t:datagrid>
</body>
<%--<script type="text/javascript">--%>
    <%--$(function() {--%>
        <%--laydate({--%>
            <%--elem : "#bookDate",--%>
            <%--event : "focus",--%>
            <%--istime : false,--%>
            <%--format : 'YYYY-MM-DD'--%>
        <%--});--%>

        <%--$("#bookDate").val('${nowStrDate}');--%>
    <%--});--%>

<%--</script>--%>
</html>
