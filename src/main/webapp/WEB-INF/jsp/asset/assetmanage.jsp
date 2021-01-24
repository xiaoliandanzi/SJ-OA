<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2021/1/11
  Time: 上午12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,select2,jqgrid,datetimePicker,laydate"></t:base>
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
                    <div id="assetManageTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="asset/assetmanage/datagrid" tableContentId="assetManageTable" searchGroupId="searchGroupId" fit="true" caption="库存列表" name="assetManageList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="assetName" label="资产名称" width="60" query="true"></t:dgCol>
    <t:dgCol name="quantity" label="数量" width="40"></t:dgCol>
    <t:dgCol name="amount" label="价格" width="40"></t:dgCol>
    <t:dgCol name="model" label="规格/型号" width="60"></t:dgCol>
    <t:dgCol name="changeTime" label="时间" width="60" datefmt="yyyy-MM-dd" queryModel="group" datePlugin="laydate"></t:dgCol>
    <t:dgCol name="address" label="库存地点" width="60"></t:dgCol>
    <t:dgCol name="receiver" label="保管人" width="40"></t:dgCol>
    <t:dgCol name="opt" label="操作" ></t:dgCol>
    <t:dgDelOpt label="删除" url="asset/assetmanage/delete?id={id}"/>
    <t:dgToolBar url="asset/assetmanage/addorupdate" type="add" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="asset/assetmanage/addorupdate" type="edit" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar type="refresh" ></t:dgToolBar>
</t:datagrid>
</body>


</html>

