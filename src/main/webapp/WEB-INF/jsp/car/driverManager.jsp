<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2021/1/14
  Time: 19:45
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
                    <div id="driverManager" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="driver/manager/datagrid" tableContentId="driverManager" searchGroupId="searchGroupId" fit="true" caption="驾驶员管理" name="driverManageList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="name" label="姓名" width="100"></t:dgCol>
    <t:dgCol name="age" label="年龄" width="80"></t:dgCol>
    <t:dgCol name="memo" label="备注" width="80"></t:dgCol>
    <t:dgCol name="opt" label="操作" ></t:dgCol>
    <t:dgDelOpt label="删除" url="driver/manage/delete?id={id}"/>
    <t:dgToolBar url="driver/manager/addorupdate" type="add" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="driver/manager/addorupdate" type="edit" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="driver/manager/view" type="view" width="40%" height="70%"></t:dgToolBar>
</t:datagrid>
</body>

</body>
</html>