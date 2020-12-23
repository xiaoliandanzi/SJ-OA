<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2020/11/25
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,select2,jqgrid"></t:base>
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
                    <div id="officalSealRecordTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="officalSeal/return/datagrid" tableContentId="officalSealRecordTable" searchGroupId="searchGroupId" fit="true" caption="公章借用记录" name="sealsRecordList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="sealName" label="公章名称" width="80"></t:dgCol>
    <t:dgCol name="userName" label="借用人" width="80" query="true"></t:dgCol>
    <t:dgCol name="departmentName" label="科室" width="80" query="true"></t:dgCol>
    <t:dgCol name="useUnit" label="主送单位" width="80"></t:dgCol>
    <t:dgCol name="content" label="内容" width="80"></t:dgCol>
    <t:dgCol name="bookDay" label="借用日期" width="80" datefmt="yyyy-MM-dd"></t:dgCol>
    <t:dgCol name="startDay" label="开始时间" width="80" datefmt="yyyy-MM-dd"></t:dgCol>
    <t:dgCol name="endDay" label="结束时间" width="80" datefmt="yyyy-MM-dd"></t:dgCol>
    <t:dgCol name="applyStatus" label="状态" width="60" dictionary="seal_approval_status"></t:dgCol>
    <t:dgCol name="memo" label="备注" width="80"></t:dgCol>
    <t:dgToolBar url="officalSeal/return/check" type="view" width="50%" height="70%"></t:dgToolBar>
    <t:dgToolBar  type="refresh" ></t:dgToolBar>
</t:datagrid>
</body>
</html>