<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2020/12/14
  Time: 21:14
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
                    <div id="officalSealBooksTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="officalSeal/officalSealBooks/datagrid" tableContentId="officalSealBooksTable" searchGroupId="searchGroupId" fit="true" caption="公章借用" name="officalSealBooksList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="sealId" label="公章名称" width="100" query="true" queryId="id" replace="${lstSeals }"></t:dgCol>
    <t:dgCol name="userName" label="借用人" width="80"></t:dgCol>
    <t:dgCol name="departmentName" label="科室" width="80"></t:dgCol>
    <t:dgCol name="useUnit" label="主送单位" width="80"></t:dgCol>
    <t:dgCol name="content" label="内容" width="80"></t:dgCol>
    <t:dgCol name="bookDate" label="预定日期" width="100" datefmt="yyyy-MM-dd" query="true" datePlugin="laydate"></t:dgCol>
    <t:dgCol name="startDate" label="开始时间" width="80" datefmt="HH:mm"></t:dgCol>
    <t:dgCol name="endDate" label="结束时间" width="80" datefmt="HH:mm"></t:dgCol>
    <t:dgCol name="memo" label="备注" width="80"></t:dgCol>
    <t:dgCol name="applyStatus" label="状态" width="80"></t:dgCol>

    <t:dgToolBar url="officalSeal/officalSealBooks/addorupdate" type="add" width="50%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="officalSeal/officalSealBooks/addorupdate" type="edit" width="50%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="officalSeal/officalSealBooks/addorupdate" type="view" width="50%" height="70%"></t:dgToolBar>
    <t:dgToolBar  type="refresh" ></t:dgToolBar>
</t:datagrid>
</body>
<script type="text/javascript">
    $(function() {
        laydate({
            elem : "#bookDate",
            event : "focus",
            istime : false,
            format : 'YYYY-MM-DD'
        });

        $("#bookDate").val('${nowStrDate}');
    });
</html>